package com.example.project2.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project2.db.pokemon.Pokemon;
import com.example.project2.db.pokemon.PokemonDao;
import com.example.project2.db.pokemon.PokemonType;
import com.example.project2.db.pokemon.Type;

@Database(
        entities = {
                User.class,
                Pokemon.class,
                Type.class,
                PokemonType.class,
                WishlistItem.class,
                TeamMember.class
        },
        version = 3,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    // DAO declarations
    public abstract UserDao userDao();
    public abstract PokemonDao pokemonDao();
    public abstract WishlistDao wishlistDao();
    public abstract TeamDao teamDao();

    // Singleton pattern
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "pokedex_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}