package com.example.project2.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WishlistDao {
    @Insert
    void insert(WishlistItem item);

    @Query("SELECT * FROM wishlist_items ORDER BY id DESC")
    List<WishlistItem> getAll();

    @Delete
    void delete(WishlistItem item);

    @Query("DELETE FROM wishlist_items")
    void deleteAll();

    @Query("SELECT * FROM wishlist_items WHERE name = :pokemonName LIMIT 1")
    WishlistItem findByName(String pokemonName);
}