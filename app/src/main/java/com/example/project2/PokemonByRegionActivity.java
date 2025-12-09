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

public class PokemonByRegionActivity extends AppCompatActivity {

    private static final String TAG = "PokemonByRegionActivity";
    private RecyclerView pokemonRecyclerView;
    private Spinner regionSpinner;
    private TextView noDataTextView;
    private PokemonRegionAdapter adapter;
    private PokemonDao pokemonDao;
    private Executor executor;
    private List<Region> allRegions;
    private List<String> regionNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_by_region);

        pokemonRecyclerView = findViewById(R.id.pokemonByRegionRecyclerView);
        regionSpinner = findViewById(R.id.regionSpinner);
        noDataTextView = findViewById(R.id.noDataTextView);
        executor = Executors.newSingleThreadExecutor();

        AppDatabase db = AppDatabase.getInstance(this);
        pokemonDao = db.pokemonDao();

        allRegions = new ArrayList<>();
        regionNames = new ArrayList<>();

        loadRegions();
    }

    private void loadRegions() {
        executor.execute(() -> {
            allRegions = pokemonDao.getAllRegions();
            regionNames.clear();
            regionNames.add("Select a region");
            for (Region region : allRegions) {
                regionNames.add(region.getName());
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                        PokemonByRegionActivity.this,
                        android.R.layout.simple_spinner_item,
                        regionNames
                );
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                regionSpinner.setAdapter(spinnerAdapter);
                regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                        if (position > 0) {
                            Region selectedRegion = allRegions.get(position - 1);
                            loadPokemonByRegion(selectedRegion.getRegionId());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            });
        });
    }

    private void loadPokemonByRegion(int regionId) {
        executor.execute(() -> {
            try {
                List<Pokemon> pokemonList = pokemonDao.getPokemonsByRegion(regionId);
                runOnUiThread(() -> {
                    if (pokemonList.isEmpty()) {
                        noDataTextView.setText("No Pokémon found in this region");
                        noDataTextView.setVisibility(android.view.View.VISIBLE);
                        pokemonRecyclerView.setVisibility(android.view.View.GONE);
                    } else {
                        noDataTextView.setVisibility(android.view.View.GONE);
                        pokemonRecyclerView.setVisibility(android.view.View.VISIBLE);
                        adapter = new PokemonRegionAdapter(pokemonList, pokemon -> {
                            Log.d(TAG, "Clicked: " + pokemon.getName());
                        });
                        pokemonRecyclerView.setAdapter(adapter);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error loading Pokémon by region", e);
            }
        });
    }
}
