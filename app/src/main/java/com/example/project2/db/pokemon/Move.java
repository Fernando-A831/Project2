package com.example.project2.db.pokemon;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "move",
        foreignKeys = @ForeignKey(entity = Type.class,
                                  parentColumns = "type_id",
                                  childColumns = "type_id",
                                  onDelete = ForeignKey.CASCADE))
public class Move {
    @PrimaryKey
    @ColumnInfo(name = "move_id")
    private int moveId;

    private String name;
    private int power;

    @ColumnInfo(name = "type_id", index = true)
    private int typeId;

    // Getters and Setters
    public int getMoveId() {
        return moveId;
    }

    public void setMoveId(int moveId) {
        this.moveId = moveId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
