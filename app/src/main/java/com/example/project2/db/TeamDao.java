package com.example.project2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TeamDao {
    @Insert
    void insert(TeamMember member);

    @Query("SELECT * FROM team_members ORDER BY id ASC")
    List<TeamMember> getAll();

    @Delete
    void delete(TeamMember member);

    @Query("DELETE FROM team_members")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM team_members")
    int getTeamCount();

    @Query("SELECT * FROM team_members WHERE name = :pokemonName LIMIT 1")
    TeamMember findByName(String pokemonName);
}