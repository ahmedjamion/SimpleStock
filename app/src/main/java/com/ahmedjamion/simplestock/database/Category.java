package com.ahmedjamion.simplestock.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = Inventory.class,
            parentColumns = "inventoryId",
            childColumns = "inventoryId",
            onDelete = ForeignKey.CASCADE
        )},
            indices = { @Index(value = "inventoryId")}
)
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int categoryId;

    private String categoryName;

    private int inventoryId;


    public Category(String categoryName, int inventoryId) {
        this.categoryName = categoryName;
        this.inventoryId = inventoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getInventoryId() {
        return inventoryId;
    }

}
