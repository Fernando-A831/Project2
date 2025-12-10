package com.example.project2;

import static org.junit.Assert.*;

import com.example.project2.db.User;

import org.junit.Test;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("testuser", "password123", false);

        assertNotNull("User should not be null", user);
        assertEquals("Username should be testuser", "testuser", user.getUsername());
        assertEquals("Password should be password123", "password123", user.getPassword());
        assertFalse("User should not be admin", user.isAdmin());
    }

    @Test
    public void testAdminUser() {
        User admin = new User("admin", "admin123", true);

        assertTrue("User should be admin", admin.isAdmin());
        assertEquals("Username should be admin", "admin", admin.getUsername());
    }

    @Test
    public void testUserSetters() {
        User user = new User("user1", "pass1", false);

        user.setId(10);
        user.setUsername("updatedUser");
        user.setPassword("newPassword");
        user.setAdmin(true);

        assertEquals("ID should be 10", 10, user.getId());
        assertEquals("Username should be updatedUser", "updatedUser", user.getUsername());
        assertEquals("Password should be newPassword", "newPassword", user.getPassword());
        assertTrue("User should now be admin", user.isAdmin());
    }
}
