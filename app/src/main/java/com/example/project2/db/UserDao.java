package com.example.project2.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User... users);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();
}
