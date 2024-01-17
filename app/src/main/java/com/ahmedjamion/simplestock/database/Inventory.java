package com.ahmedjamion.simplestock.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity
public class Inventory {
    @PrimaryKey(autoGenerate = true)
    private int inventoryId;


    private String inventoryName;


    public Inventory(String inventoryName) {
        this.inventoryName = inventoryName;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public String getInventoryName() {
        return inventoryName;
    }

}
