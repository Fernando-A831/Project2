package com.example.project2;

import static org.junit.Assert.*;

import com.example.project2.db.WishlistItem;

import org.junit.Test;

public class WishlistDaoTest {

    @Test
    public void testWishlistItemCreation() {
        WishlistItem item = new WishlistItem("Pikachu", "http://example.com/pikachu.png", "Electric");

        assertNotNull("WishlistItem should not be null", item);
        assertEquals("Name should be Pikachu", "Pikachu", item.getName());
        assertEquals("Types should be Electric", "Electric", item.getTypes());
        assertNotNull("Image URL should not be null", item.getImageUrl());
    }

    @Test
    public void testWishlistItemSetters() {
        WishlistItem item = new WishlistItem("Charmander", "http://example.com/char.png", "Fire");

        item.setId(1);
        item.setName("Charizard");
        item.setTypes("Fire, Flying");

        assertEquals("ID should be 1", 1, item.getId());
        assertEquals("Name should be Charizard", "Charizard", item.getName());
        assertEquals("Types should be Fire, Flying", "Fire, Flying", item.getTypes());
    }

    @Test
    public void testWishlistItemNullValues() {
        WishlistItem item = new WishlistItem(null, null, null);

        assertNull("Name should be null", item.getName());
        assertNull("Image URL should be null", item.getImageUrl());
        assertNull("Types should be null", item.getTypes());
    }
}
