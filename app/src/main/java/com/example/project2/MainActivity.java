package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.User;
import com.example.project2.db.UserDao;
import com.example.project2.db.pokemon.Pokemon;
import com.example.project2.db.pokemon.PokemonType;
import com.example.project2.db.pokemon.Type;
import com.example.project2.network.PokeApiService;
import com.example.project2.network.RetrofitClient;
import com.example.project2.network.models.PokemonListResponse;
import com.example.project2.network.models.PokemonResponse;
import com.example.project2.network.models.PokemonTypeResponse;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UserDao userDao = AppDatabase.getInstance(getApplicationContext()).userDao();
            if (userDao.getUserByUsername("testuser1") == null) {
                userDao.insert(new User("testuser1", "testuser1", false));
            }
            if (userDao.getUserByUsername("admin2") == null) {
                userDao.insert(new User("admin2", "admin2", true));
            }
        });

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("loggedIn", false);

        if(loggedIn) {
            startActivity(new Intent(MainActivity.this, LandingPageActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);

            Button loginButton = findViewById(R.id.loginButton);
            loginButton.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            });

            Button createAccountButton = findViewById(R.id.createAccountButton);
            createAccountButton.setOnClickListener(v -> {
                Toast.makeText(MainActivity.this, "Create account not implemented yet", Toast.LENGTH_SHORT).show();
            });

            // Example PokeAPI call
            fetchRandomPokemon(executor);
        }
    }

    private void fetchRandomPokemon(Executor executor) {
        PokeApiService service = RetrofitClient.getClient().create(PokeApiService.class);
        Call<PokemonListResponse> listCall = service.getPokemonList(1, 0);

        listCall.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful()) {
                    PokemonListResponse listResponse = response.body();
                    if (listResponse != null) {
                        int count = listResponse.getCount();
                        Random random = new Random();
                        int randomId = random.nextInt(count) + 1;

                        Call<PokemonResponse> pokemonCall = service.getPokemonById(randomId);
                        pokemonCall.enqueue(new Callback<PokemonResponse>() {
                            @Override
                            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                                if (response.isSuccessful()) {
                                    PokemonResponse pokemonResponse = response.body();
                                    if (pokemonResponse != null) {
                                        // Map the response to our database entity
                                        Pokemon pokemon = new Pokemon();
                                        pokemon.setPokemonId(pokemonResponse.getId());
                                        pokemon.setName(pokemonResponse.getName());
                                        pokemon.setHeight(pokemonResponse.getHeight());
                                        pokemon.setWeight(pokemonResponse.getWeight());

                                        // Save to database in the background
                                        executor.execute(() -> {
                                            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                                            db.pokemonDao().insert(pokemon);

                                            for (PokemonTypeResponse typeResponse : pokemonResponse.getTypes()) {
                                                Type existingType = db.pokemonDao().getTypeByName(typeResponse.getType().getName());
                                                long typeId;
                                                if (existingType == null) {
                                                    Type newType = new Type();
                                                    newType.setName(typeResponse.getType().getName());
                                                    typeId = db.pokemonDao().insert(newType);
                                                } else {
                                                    typeId = existingType.getTypeId();
                                                }

                                                PokemonType pokemonType = new PokemonType();
                                                pokemonType.setPokemonId(pokemon.getPokemonId());
                                                pokemonType.setTypeId((int) typeId);
                                                db.pokemonDao().insert(pokemonType);
                                            }
                                        });

                                        Toast.makeText(MainActivity.this, "Fetched and saved: " + pokemon.getName(), Toast.LENGTH_LONG).show();
                                    } else {
                                        Log.e(TAG, "onResponse: PokemonResponse is null");
                                    }
                                } else {
                                    Log.e(TAG, "onResponse: " + response.errorBody());
                                }
                            }

                            @Override
                            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
