package com.example.project2;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project2.db.AppDatabase;
import com.example.project2.db.WishlistItem;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomPokemonActivity extends AppCompatActivity {

    private static final int MAX_POKEMON_ID = 1010;

    private ImageView pokemonSpriteImageView;
    private TextView pokemonNameTextView;
    private TextView pokemonHeightTextView;
    private TextView pokemonWeightTextView;
    private TextView pokemonTypesTextView;
    private TextView pokedexEntryTextView;
    private Button addToWishlistButton;

    // Wishlist integration fields
    private ExecutorService executorService;
    private String currentPokemonName;
    private String currentPokemonImageUrl;
    private String currentPokemonTypes;

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

        addToWishlistButton = findViewById(R.id.btnAddToWishlist);

        pokemonNameTextView.setText("Loading...");

        // Initialize executor for wishlist
        executorService = Executors.newSingleThreadExecutor();
        addToWishlistButton.setOnClickListener(v -> addToWishlist());

        fetchAndSaveRandomPokemon();
    }

    private void fetchAndSaveRandomPokemon() {
        PokeApiService service = RetrofitClient.getClient().create(PokeApiService.class);

        int randomId = new Random().nextInt(MAX_POKEMON_ID) + 1;

        Call<PokemonResponse> pokemonCall = service.getPokemonById(randomId);
        Call<PokemonSpeciesResponse> speciesCall = service.getPokemonSpeciesById(randomId);

        pokemonCall.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> pokemonDetailsResponse) {
                if (!pokemonDetailsResponse.isSuccessful() || pokemonDetailsResponse.body() == null) {
                    pokemonNameTextView.setText("Failed to load Pokémon");
                    return;
                }

                speciesCall.enqueue(new Callback<PokemonSpeciesResponse>() {
                    @Override
                    public void onResponse(Call<PokemonSpeciesResponse> call, Response<PokemonSpeciesResponse> speciesDetailsResponse) {
                        if (!speciesDetailsResponse.isSuccessful() || speciesDetailsResponse.body() == null) {
                            pokemonNameTextView.setText("Failed to load species data");
                            return;
                        }

                        saveDataAndDisplay(pokemonDetailsResponse.body(), speciesDetailsResponse.body());
                    }

                    @Override
                    public void onFailure(Call<PokemonSpeciesResponse> call, Throwable t) {
                        pokemonNameTextView.setText("Species fetch error");
                    }
                });
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                pokemonNameTextView.setText("Details fetch error");
            }
        });
    }

    private void saveDataAndDisplay(PokemonResponse pokemonResponse, PokemonSpeciesResponse speciesResponse) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            PokemonDao dao = db.pokemonDao();

            String entry = "No English Pokédex entry.";
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

            // Store types for wishlist
            List<String> typeNames = new ArrayList<>();

            for (PokemonTypeResponse typeResponse : pokemonResponse.getTypes()) {
                Type existingType = dao.getTypeByName(typeResponse.getType().getName());
                long typeId;

                if (existingType == null) {
                    Type newType = new Type();
                    newType.setName(typeResponse.getType().getName());
                    typeId = dao.insert(newType);
                    typeNames.add(typeResponse.getType().getName());
                } else {
                    typeId = existingType.getTypeId();
                    typeNames.add(existingType.getName());
                }

                PokemonType pokemonType = new PokemonType();
                pokemonType.setPokemonId(pokemon.getPokemonId());
                pokemonType.setTypeId((int) typeId);
                dao.insert(pokemonType);
            }

            // Store data for wishlist feature
            String typesString = String.join(", ", typeNames);

            runOnUiThread(() -> {
                displayPokemonFromDb(pokemon.getPokemonId());
                storePokemonData(pokemon.getName(), pokemon.getSpriteUrl(), typeNames);
            });
        });
    }

    private void displayPokemonFromDb(int pokemonId) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            PokemonDao dao = db.pokemonDao();

            Pokemon pokemon = dao.getPokemonById(pokemonId);

            runOnUiThread(() -> {
                if (pokemon == null) return;

                pokemonNameTextView.setText(pokemon.getName());
                pokemonHeightTextView.setText("Height: " + pokemon.getHeight());
                pokemonWeightTextView.setText("Weight: " + pokemon.getWeight());
                pokedexEntryTextView.setText(pokemon.getPokedexEntry());

                Glide.with(this)
                        .load(pokemon.getSpriteUrl())
                        .into(pokemonSpriteImageView);
            });
        });
    }

    // Store Pokémon data for wishlist
    private void storePokemonData(String name, String imageUrl, List<String> types) {
        this.currentPokemonName = name;
        this.currentPokemonImageUrl = imageUrl;

        if (types != null && !types.isEmpty()) {
            StringBuilder typesBuilder = new StringBuilder();
            for (int i = 0; i < types.size(); i++) {
                // Capitalize first letter
                String type = types.get(i);
                type = type.substring(0, 1).toUpperCase() + type.substring(1);
                typesBuilder.append(type);
                if (i < types.size() - 1) {
                    typesBuilder.append(", ");
                }
            }
            this.currentPokemonTypes = typesBuilder.toString();
        } else {
            this.currentPokemonTypes = "Unknown";
        }
    }

    // Add to Wishlist functionality
    private void addToWishlist() {
        if (currentPokemonName == null || currentPokemonName.isEmpty()) {
            Toast.makeText(this, "No Pokémon loaded!", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.execute(() -> {
            AppDatabase database = AppDatabase.getInstance(this);

            // Check if already in wishlist
            WishlistItem existing = database.wishlistDao().findByName(currentPokemonName);
            if (existing != null) {
                runOnUiThread(() ->
                        Toast.makeText(this, currentPokemonName + " is already in your wishlist!",
                                Toast.LENGTH_SHORT).show()
                );
                return;
            }

            // Create and insert new wishlist item
            WishlistItem item = new WishlistItem(
                    currentPokemonName,
                    currentPokemonImageUrl,
                    currentPokemonTypes
            );

            database.wishlistDao().insert(item);

            runOnUiThread(() ->
                    Toast.makeText(this, currentPokemonName + " added to wishlist!",
                            Toast.LENGTH_SHORT).show()
            );
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}