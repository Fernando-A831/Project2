package com.example.project2;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project2.db.AppDatabase;
import com.example.project2.db.pokemon.Pokemon;
import com.example.project2.db.pokemon.PokemonDao;
import com.example.project2.db.pokemon.PokemonType;
import com.example.project2.db.pokemon.Type;
import com.example.project2.network.PokeApiService;
import com.example.project2.network.RetrofitClient;
import com.example.project2.network.models.FlavorTextEntry;
import com.example.project2.network.models.PokemonResponse;
import com.example.project2.network.models.PokemonSpeciesResponse;
import com.example.project2.network.models.PokemonTypeResponse;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPokemonActivity extends AppCompatActivity {

    private static final String TAG = "RandomPokemonActivity";
    private static final int MAX_POKEMON_ID = 1010;
    private ImageView pokemonSpriteImageView;
    private TextView pokemonNameTextView;
    private TextView pokemonHeightTextView;
    private TextView pokemonWeightTextView;
    private TextView pokemonTypesTextView;
    private TextView pokedexEntryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_pokemon);

        pokemonSpriteImageView = findViewById(R.id.pokemonSpriteImageView);
        pokemonNameTextView = findViewById(R.id.pokemonNameTextView);
        pokemonHeightTextView = findViewById(R.id.pokemonHeightTextView);
        pokemonWeightTextView = findViewById(R.id.pokemonWeightTextView);
        pokemonTypesTextView = findViewById(R.id.pokemonTypesTextView);
        pokedexEntryTextView = findViewById(R.id.pokedexEntryTextView);
        pokedexEntryTextView.setMovementMethod(new ScrollingMovementMethod());

        pokemonNameTextView.setText("Loading...");

        String pokemonName = getIntent().getStringExtra("pokemon_name");

        if (pokemonName != null && !pokemonName.trim().isEmpty()) {
            fetchAndSavePokemonByName(pokemonName.trim().toLowerCase());
        } else {
            handleFailure("No Pokémon name provided");
        }


    }

    private void fetchAndSavePokemonByName(String name) {
        PokeApiService service = RetrofitClient.getClient().create(PokeApiService.class);

        Log.d(TAG, "Searching Pokémon: " + name);

        Call<PokemonResponse> pokemonCall = service.getPokemonByName(name);
        Call<PokemonSpeciesResponse> speciesCall = service.getPokemonSpeciesByName(name);

        pokemonCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> pokemonDetailsResponse) {
                if (!pokemonDetailsResponse.isSuccessful() || pokemonDetailsResponse.body() == null) {
                    Log.e(TAG, "Details error (search): code=" + pokemonDetailsResponse.code()
                            + " message=" + pokemonDetailsResponse.message());
                    handleFailure("Pokémon not found");
                    return;
                }

                speciesCall.enqueue(new Callback<PokemonSpeciesResponse>() {
                    @Override
                    public void onResponse(Call<PokemonSpeciesResponse> call, Response<PokemonSpeciesResponse> speciesDetailsResponse) {
                        if (!speciesDetailsResponse.isSuccessful() || speciesDetailsResponse.body() == null) {
                            Log.e(TAG, "Species error (search): code=" + speciesDetailsResponse.code()
                                    + " message=" + speciesDetailsResponse.message());
                            handleFailure("Failed to load Pokémon species");
                            return;
                        }

                        // Reuse existing DB + UI logic
                        saveDataAndDisplay(pokemonDetailsResponse.body(), speciesDetailsResponse.body());
                    }

                    @Override
                    public void onFailure(Call<PokemonSpeciesResponse> call, Throwable t) {
                        Log.e(TAG, "Species failure (search)", t);
                        handleFailure("Network error");
                    }
                });
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "Details failure (search)", t);
                handleFailure("Network error");
            }
        });
    }


    private void fetchAndSaveRandomPokemon() {
        PokeApiService service = RetrofitClient.getClient().create(PokeApiService.class);

        int randomId = new Random().nextInt(MAX_POKEMON_ID) + 1;

        Log.d(TAG, "Random Pokémon ID: " + randomId);

        Call<PokemonResponse> pokemonCall = service.getPokemonById(randomId);
        Call<PokemonSpeciesResponse> speciesCall = service.getPokemonSpeciesById(randomId);

        pokemonCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> pokemonDetailsResponse) {
                if (!pokemonDetailsResponse.isSuccessful() || pokemonDetailsResponse.body() == null) {
                    Log.e(TAG, "Details error: code=" + pokemonDetailsResponse.code()
                            + " message=" + pokemonDetailsResponse.message());
                    handleFailure("Failed to load Pokémon details");
                    return;
                }

                speciesCall.enqueue(new Callback<PokemonSpeciesResponse>() {
                    @Override
                    public void onResponse(Call<PokemonSpeciesResponse> call, Response<PokemonSpeciesResponse> speciesDetailsResponse) {
                        if (!speciesDetailsResponse.isSuccessful() || speciesDetailsResponse.body() == null) {
                            Log.e(TAG, "Species error: code=" + speciesDetailsResponse.code()
                                    + " message=" + speciesDetailsResponse.message());
                            handleFailure("Failed to load Pokémon species");
                            return;
                        }

                        saveDataAndDisplay(pokemonDetailsResponse.body(), speciesDetailsResponse.body());
                    }

                    @Override
                    public void onFailure(Call<PokemonSpeciesResponse> call, Throwable t) {
                        Log.e(TAG, "Species failure", t);
                        handleFailure("Network Error (Species)");
                    }
                });
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, "Details failure", t);
                handleFailure("Network Error (Details)");
            }
        });
    }


    private void saveDataAndDisplay(PokemonResponse pokemonResponse, PokemonSpeciesResponse speciesResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            PokemonDao dao = db.pokemonDao();

            // Find the English Pokedex entry
            String entry = "No English Pokédex entry found.";
            for (FlavorTextEntry flavorText : speciesResponse.getFlavorTextEntries()) {
                if (flavorText.getLanguage().getName().equals("en")) {
                    entry = flavorText.getFlavorText().replace('\n', ' ');
                    break;
                }
            }

            Pokemon pokemon = new Pokemon();
            pokemon.setPokemonId(pokemonResponse.getId());
            pokemon.setName(pokemonResponse.getName());
            pokemon.setHeight(pokemonResponse.getHeight());
            pokemon.setWeight(pokemonResponse.getWeight());
            pokemon.setSpriteUrl(pokemonResponse.getSprites().getFrontDefault());
            pokemon.setPokedexEntry(entry);
            dao.insert(pokemon);

            for (PokemonTypeResponse typeResponse : pokemonResponse.getTypes()) {
                Type existingType = dao.getTypeByName(typeResponse.getType().getName());
                long typeId;
                if (existingType == null) {
                    Type newType = new Type();
                    newType.setName(typeResponse.getType().getName());
                    typeId = dao.insert(newType);
                } else {
                    typeId = existingType.getTypeId();
                }
                PokemonType pokemonType = new PokemonType();
                pokemonType.setPokemonId(pokemon.getPokemonId());
                pokemonType.setTypeId((int) typeId);
                dao.insert(pokemonType);
            }

            runOnUiThread(() -> displayPokemonFromDb(pokemon.getPokemonId()));
        });
    }

    private void displayPokemonFromDb(int pokemonId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            PokemonDao dao = AppDatabase.getInstance(getApplicationContext()).pokemonDao();
            Pokemon pokemon = dao.getPokemonById(pokemonId);
            List<String> types = dao.getTypesForPokemon(pokemonId);

            runOnUiThread(() -> {
                pokemonNameTextView.setText(pokemon.getName());
                pokemonHeightTextView.setText("Height: " + pokemon.getHeight());
                pokemonWeightTextView.setText("Weight: " + pokemon.getWeight());
                pokemonTypesTextView.setText("Types: " + String.join(", ", types));
                pokedexEntryTextView.setText(pokemon.getPokedexEntry());

                Glide.with(this)
                     .load(pokemon.getSpriteUrl())
                     .into(pokemonSpriteImageView);
            });
        });
    }

    private void handleFailure(String message) {
        runOnUiThread(() -> pokemonNameTextView.setText(message));
        Log.e(TAG, message);
    }
}
