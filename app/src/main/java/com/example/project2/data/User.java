package com.example.project2.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String username;

    @NonNull
    public String password;

    public boolean isAdmin;

    public User(@NonNull String username, @NonNull String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
