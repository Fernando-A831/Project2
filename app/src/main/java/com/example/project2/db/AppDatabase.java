package com.example.project2.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.project2.db.pokemon.Evolution;
import com.example.project2.db.pokemon.Move;
import com.example.project2.db.pokemon.Pokemon;
import com.example.project2.db.pokemon.PokemonDao;
import com.example.project2.db.pokemon.PokemonMove;
import com.example.project2.db.pokemon.PokemonType;
import com.example.project2.db.pokemon.Region;
import com.example.project2.db.pokemon.Type;
import com.example.project2.db.pokemon.UserPokemonList;

@Database(entities = {User.class, Pokemon.class, Region.class, Type.class, Move.class, PokemonType.class, PokemonMove.class, Evolution.class, UserPokemonList.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "PROJECT2_DB";
    private static volatile AppDatabase instance;

    public abstract UserDao userDao();
    public abstract PokemonDao pokemonDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
