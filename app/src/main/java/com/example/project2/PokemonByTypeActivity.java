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
import com.example.project2.db.pokemon.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PokemonByTypeActivity extends AppCompatActivity {

    private static final String TAG = "PokemonByTypeActivity";
    private RecyclerView pokemonRecyclerView;
    private Spinner typeSpinner;
    private TextView noDataTextView;
    private PokemonTypeAdapter adapter;
    private PokemonDao pokemonDao;
    private Executor executor;
    private List<Type> allTypes;
    private List<String> typeNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_by_type);

        pokemonRecyclerView = findViewById(R.id.pokemonByTypeRecyclerView);
        typeSpinner = findViewById(R.id.typeSpinner);
        noDataTextView = findViewById(R.id.noDataTextView);
        executor = Executors.newSingleThreadExecutor();

        AppDatabase db = AppDatabase.getInstance(this);
        pokemonDao = db.pokemonDao();

        allTypes = new ArrayList<>();
        typeNames = new ArrayList<>();

        loadTypes();
    }

    private void loadTypes() {
        executor.execute(() -> {
            allTypes = pokemonDao.getAllTypes();
            typeNames.clear();
            typeNames.add("Select a type");
            for (Type type : allTypes) {
                typeNames.add(type.getName());
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                        PokemonByTypeActivity.this,
                        android.R.layout.simple_spinner_item,
                        typeNames
                );
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typeSpinner.setAdapter(spinnerAdapter);
                typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                        if (position > 0) {
                            String selectedType = typeNames.get(position);
                            loadPokemonByType(selectedType);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
            });
        });
    }

    private void loadPokemonByType(String typeName) {
        executor.execute(() -> {
            try {
                List<Pokemon> pokemonList = pokemonDao.getPokemonsByType(typeName);
                runOnUiThread(() -> {
                    if (pokemonList.isEmpty()) {
                        noDataTextView.setText("No Pokémon found for type: " + typeName);
                        noDataTextView.setVisibility(android.view.View.VISIBLE);
                        pokemonRecyclerView.setVisibility(android.view.View.GONE);
                    } else {
                        noDataTextView.setVisibility(android.view.View.GONE);
                        pokemonRecyclerView.setVisibility(android.view.View.VISIBLE);
                        adapter = new PokemonTypeAdapter(pokemonList, pokemon -> {
                            Log.d(TAG, "Clicked: " + pokemon.getName());
                        });
                        pokemonRecyclerView.setAdapter(adapter);
                    }
                });
            } catch (Exception e) {
                Log.e(TAG, "Error loading Pokémon by type", e);
            }
        });
    }
}
