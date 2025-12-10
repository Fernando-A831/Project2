package com.example.project2;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.User;
import com.example.project2.db.UserDao;
import com.example.project2.db.pokemon.Pokemon;
import com.example.project2.db.pokemon.PokemonDao;
import com.example.project2.db.pokemon.UserPokemonList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class PokemonDaoTest {

    private AppDatabase db;
    private UserDao userDao;
    private PokemonDao pokemonDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext();

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();

        userDao = db.userDao();
        pokemonDao = db.pokemonDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void wishlistReturnsPokemonForUser() {
        // create a user
        User user = new User("wishlistUser", "pw", false);
        userDao.insert(user);
        User fromDb = userDao.getUserByUsername("wishlistUser");
        int userId = fromDb.getId();

        //  create a Pokémon
        Pokemon pikachu = new Pokemon();
        pikachu.setPokemonId(25);
        pikachu.setName("pikachu");
        pikachu.setHeight(4);
        pikachu.setWeight(60);
        pikachu.setSpriteUrl("test-url");
        pikachu.setPokedexEntry("test entry");
        pikachu.setRegionId(1);

        pokemonDao.insert(pikachu);

        //link user + pokemon in wishlist table
        UserPokemonList link = new UserPokemonList();
        link.setUserId(userId);
        link.setPokemonId(25);
        pokemonDao.insert(link);

        List<Pokemon> wishlist = pokemonDao.getWishlistForUser(userId);

        assertEquals(1, wishlist.size());
        assertEquals("pikachu", wishlist.get(0).getName());
        assertEquals(25, wishlist.get(0).getPokemonId());
    }
}
