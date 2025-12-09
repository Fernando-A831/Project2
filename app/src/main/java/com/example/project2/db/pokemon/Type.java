package com.example.project2.db.pokemon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "type")
public class Type {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "type_id")
    private int typeId;

    private String name;

    // Getters and Setters
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
