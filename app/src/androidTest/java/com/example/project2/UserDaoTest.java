package com.example.project2;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.project2.db.AppDatabase;
import com.example.project2.db.User;
import com.example.project2.db.UserDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    private AppDatabase db;
    private UserDao userDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext();

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()   // ok for tests
                .build();

        userDao = db.userDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insertAndGetUserByUsername() {
        User user = new User("testuser", "secretpw", false);
        userDao.insert(user);

        User loaded = userDao.getUserByUsername("testuser");

        assertNotNull(loaded);
        assertEquals("testuser", loaded.getUsername());
        assertEquals("secretpw", loaded.getPassword());
        assertFalse(loaded.isAdmin());
    }
}
