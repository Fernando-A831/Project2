package com.example.project2;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.TeamMember;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TeamBuilderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TeamBuilderAdapter adapter;
    private List<TeamMember> teamMembers;
    private AppDatabase database;
    private ExecutorService executorService;
    private Button addButton;
    private TextView emptyTextView;
    private static final int MAX_TEAM_SIZE = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_builder);

        // Initialize views
        recyclerView = findViewById(R.id.teamRecyclerView);
        addButton = findViewById(R.id.addTeamMemberButton);
        emptyTextView = findViewById(R.id.emptyTeamText);

        // Initialize database and executor
        database = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        // Setup RecyclerView
        teamMembers = new ArrayList<>();
        adapter = new TeamBuilderAdapter(teamMembers, this::onDeleteClick);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup add button
        addButton.setOnClickListener(v -> showAddPokemonDialog());

        // Load team
        loadTeam();
    }

    private void loadTeam() {
        executorService.execute(() -> {
            List<TeamMember> members = database.teamDao().getAll();
            runOnUiThread(() -> {
                teamMembers.clear();
                teamMembers.addAll(members);
                adapter.notifyDataSetChanged();
                updateUI();
            });
        });
    }

    private void updateUI() {
        if (teamMembers.isEmpty()) {
            emptyTextView.setVisibility(TextView.VISIBLE);
            recyclerView.setVisibility(RecyclerView.GONE);
        } else {
            emptyTextView.setVisibility(TextView.GONE);
            recyclerView.setVisibility(RecyclerView.VISIBLE);
        }

        // Disable add button if team is full
        if (teamMembers.size() >= MAX_TEAM_SIZE) {
            addButton.setEnabled(false);
            addButton.setText("Team Full (6/6)");
        } else {
            addButton.setEnabled(true);
            addButton.setText("Add Pokémon to Team (" + teamMembers.size() + "/6)");
        }
    }

    private void showAddPokemonDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Pokémon to Team");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Enter Pokémon name");
        builder.setView(input);

        builder.setPositiveButton("Add", (dialog, which) -> {
            String pokemonName = input.getText().toString().trim().toLowerCase();
            if (!pokemonName.isEmpty()) {
                addPokemonToTeam(pokemonName);
            } else {
                Toast.makeText(this, "Please enter a Pokémon name", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void addPokemonToTeam(String pokemonName) {
        executorService.execute(() -> {
            int currentTeamSize = database.teamDao().getTeamCount();

            if (currentTeamSize >= MAX_TEAM_SIZE) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Team is full! Maximum 6 Pokémon allowed.", Toast.LENGTH_SHORT).show()
                );
                return;
            }

            // Check if Pokémon already in team
            TeamMember existing = database.teamDao().findByName(pokemonName);
            if (existing != null) {
                runOnUiThread(() ->
                        Toast.makeText(this, pokemonName + " is already in your team!", Toast.LENGTH_SHORT).show()
                );
                return;
            }

            // Create new team member with placeholder image
            TeamMember newMember = new TeamMember(
                    pokemonName,
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
                            Math.abs(pokemonName.hashCode() % 898 + 1) + ".png",
                    "Unknown"
            );

            database.teamDao().insert(newMember);

            runOnUiThread(() -> {
                Toast.makeText(this, pokemonName + " added to team!", Toast.LENGTH_SHORT).show();
                loadTeam();
            });
        });
    }

    private void onDeleteClick(TeamMember member) {
        executorService.execute(() -> {
            database.teamDao().delete(member);
            runOnUiThread(() -> {
                Toast.makeText(this, member.getName() + " removed from team", Toast.LENGTH_SHORT).show();
                loadTeam();
            });
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