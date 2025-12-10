package com.example.project2;

import static org.junit.Assert.*;

import com.example.project2.db.TeamMember;

import org.junit.Test;

public class TeamMemberTest {

    @Test
    public void testTeamMemberCreation() {
        TeamMember member = new TeamMember("Bulbasaur", "http://example.com/bulb.png", "Grass, Poison");

        assertNotNull("TeamMember should not be null", member);
        assertEquals("Name should be Bulbasaur", "Bulbasaur", member.getName());
        assertEquals("Types should be Grass, Poison", "Grass, Poison", member.getTypes());
        assertNotNull("Image URL should not be null", member.getImageUrl());
    }

    @Test
    public void testTeamMemberSetters() {
        TeamMember member = new TeamMember("Squirtle", "http://example.com/squirt.png", "Water");

        member.setId(5);
        member.setName("Blastoise");
        member.setTypes("Water");

        assertEquals("ID should be 5", 5, member.getId());
        assertEquals("Name should be Blastoise", "Blastoise", member.getName());
        assertEquals("Types should be Water", "Water", member.getTypes());
    }

    @Test
    public void testTeamMemberEmptyValues() {
        TeamMember member = new TeamMember("", "", "");

        assertEquals("Name should be empty", "", member.getName());
        assertEquals("Image URL should be empty", "", member.getImageUrl());
        assertEquals("Types should be empty", "", member.getTypes());
    }
}
