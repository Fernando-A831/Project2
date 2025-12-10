package com.example.project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.db.TeamMember;

import java.util.List;

public class TeamBuilderAdapter extends RecyclerView.Adapter<TeamBuilderAdapter.TeamViewHolder> {
    private List<TeamMember> teamMembers;
    private OnDeleteClickListener deleteListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(TeamMember member);
    }

    public TeamBuilderAdapter(List<TeamMember> teamMembers, OnDeleteClickListener deleteListener) {
        this.teamMembers = teamMembers;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team_member, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        TeamMember member = teamMembers.get(position);
        holder.bind(member, position + 1);
    }

    @Override
    public int getItemCount() {
        return teamMembers.size();
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {
        private ImageView pokemonImage;
        private TextView pokemonName;
        private TextView pokemonTypes;
        private TextView positionNumber;
        private ImageButton deleteButton;

        public TeamViewHolder(@NonNull View itemView) {
            super(itemView);
            pokemonImage = itemView.findViewById(R.id.teamMemberImage);
            pokemonName = itemView.findViewById(R.id.teamMemberName);
            pokemonTypes = itemView.findViewById(R.id.teamMemberTypes);
            positionNumber = itemView.findViewById(R.id.teamMemberPosition);
            deleteButton = itemView.findViewById(R.id.teamDeleteButton);
        }

        public void bind(TeamMember member, int position) {
            // Set position number
            positionNumber.setText("#" + position);

            // Capitalize first letter
            String name = member.getName();
            if (name != null && !name.isEmpty()) {
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
            }
            pokemonName.setText(name);
            pokemonTypes.setText(member.getTypes());

            // Load image with Glide
            Glide.with(itemView.getContext())
                    .load(member.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(pokemonImage);

            // Handle delete button click
            deleteButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(member);
                }
            });
        }
    }
}