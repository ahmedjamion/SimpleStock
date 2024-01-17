package com.ahmedjamion.simplestock.database;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = Category.class,
            parentColumns = "categoryId",
            childColumns = "categoryId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Inventory.class,
                parentColumns = "inventoryId",
                childColumns = "inventoryId",
                onDelete = ForeignKey.CASCADE
        )},
            indices = { @Index(value = "categoryId"),
                        @Index(value = "inventoryId"),
                        @Index(value = "productName", unique = true)
            }
)
public class Product {

    @PrimaryKey(autoGenerate = true)
    private int productId;

    private String productName;

    private int categoryId;


    private int inventoryId;


    private double productPrice;

    private int productQuantity;


    public Product(String productName, int categoryId, int inventoryId, double productPrice, int productQuantity) {
        this.productName = productName;
        this.categoryId = categoryId;
        this.inventoryId = inventoryId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getInventoryId() {
        return inventoryId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }
}
