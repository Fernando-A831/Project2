package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.pokemon.Pokemon;
import com.example.project2.db.pokemon.PokemonDao;
import com.example.project2.db.pokemon.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GenerationSearchActivity extends AppCompatActivity {

    private static final String TAG = "GenerationSearchActivity";
    private RecyclerView pokemonRecyclerView;
    private Spinner generationSpinner;
    private TextView generationInfoTextView;
    private TextView noDataTextView;
    private GenerationSearchAdapter adapter;
    private PokemonDao pokemonDao;
    private Executor executor;
    private List<Region> allRegions;
    private List<String> regionNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generation_search);

        pokemonRecyclerView = findViewById(R.id.generationPokemonRecyclerView);
        generationSpinner = findViewById(R.id.generationSpinner);
        generationInfoTextView = findViewById(R.id.generationInfoTextView);
        noDataTextView = findViewById(R.id.noDataTextView);
        executor = Executors.newSingleThreadExecutor();

        AppDatabase db = AppDatabase.getInstance(this);
        pokemonDao = db.pokemonDao();

        allRegions = new ArrayList<>();
        regionNames = new ArrayList<>();

        loadGenerations();
    }

    private void loadGenerations() {
        executor.execute(() -> {
            allRegions = pokemonDao.getAllRegions();
            regionNames.clear();
            regionNames.add("Select a generation");
            for (Region region : allRegions) {
                regionNames.add(region.getName());
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                        GenerationSearchActivity.this,
                        android.R.layout.simple_spinner_item,
                        regionNames
                );
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                generationSpinner.setAdapter(spinnerAdapter);
                generationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                        if (position > 0) {
                            Region selectedRegion = allRegions.get(position - 1);
                            loadPokemonForGeneration(selectedRegion);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            });
        });
    }

    private void loadPokemonForGeneration(Region region) {
        executor.execute(() -> {
            try {
                List<Pokemon> pokemonList = pokemonDao.getPokemonsByRegion(region.getRegionId());
                runOnUiThread(() -> {
                    generationInfoTextView.setText("Generation: " + region.getName() + " - " + pokemonList.size() + " Pokémon");
                    
                    if (pokemonList.isEmpty()) {
                        noDataTextView.setText("No Pokémon found in this generation");
                        noDataTextView.setVisibility(android.view.View.VISIBLE);
                        pokemonRecyclerView.setVisibility(android.view.View.GONE);
                    } else {
                        noDataTextView.setVisibility(android.view.View.GONE);
                        pokemonRecyclerView.setVisibility(android.view.View.VISIBLE);
                        adapter = new GenerationSearchAdapter(pokemonList, pokemon -> {
                            Log.d(TAG, "Clicked: " + pokemon.getName());
                        });
                        pokemonRecyclerView.setAdapter(adapter);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error loading Pokémon for generation", e);
            }
        });
    }
}
