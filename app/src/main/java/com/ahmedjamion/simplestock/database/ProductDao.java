package com.ahmedjamion.simplestock.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product product);

    @Delete
    void delete(Product product);

    @Update
    void update(Product product);

    @Query("UPDATE Product SET productName = :newProductName WHERE productId = :productId")
    void updateName(int productId, String newProductName);

    @Query("UPDATE Product SET productPrice = :newProductPrice WHERE productId = :productId")
    void updatePrice(int productId, Double newProductPrice);

    @Query("UPDATE Product SET productQuantity = :newProductQuantity WHERE productId = :productId")
    void updateQuantity(int productId, int newProductQuantity);

    @Query("UPDATE Product SET productQuantity = productQuantity + :quantityToAdd WHERE productId = :productId")
    void addToQuantity(int productId, int quantityToAdd);

    @Query("UPDATE Product SET productQuantity =  CASE WHEN (productQuantity - :quantityToDeduct) >= 0 THEN (productQuantity - :quantityToDeduct) ELSE productQuantity END WHERE productId = :productId")
    void deductFromQuantity(int productId, int quantityToDeduct);

    @Query("DELETE FROM Product WHERE productId = :productId")
    void deleteById(int productId);

    @Query("SELECT * FROM Product WHERE productId = :productId")
    LiveData<List<Product>> getProductById(int productId);

    @Query("SELECT SUM(productPrice * productQuantity) FROM Product WHERE inventoryId = :inventoryId")
    LiveData<Double> getSumOfPrice(int inventoryId);

    @Query("SELECT SUM (productQuantity) FROM Product WHERE inventoryId = :inventoryId")
    LiveData<Integer> getSumOfQuantity(int inventoryId);

    @Query("DELETE FROM Product")
    void deleteAllProduct();

    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllProduct();

    @Query("SELECT * FROM Product WHERE inventoryId = :inventoryId")
    LiveData<List<Product>> getAllProductInInventory(int inventoryId);

    @Query("SELECT * FROM Product WHERE productName = :productName")
    LiveData<List<Product>> getProductByName(String productName);

    @Query("SELECT * FROM Product WHERE inventoryId = :inventoryId AND productName LIKE '%' || :productName || '%'")
    LiveData<List<Product>> getProductByInventoryAndName(int inventoryId, String productName);

    @Query("SELECT * FROM Product WHERE productQuantity < 5 AND inventoryId = :inventoryId")
    LiveData<List<Product>> getLowQuantityProducts(int inventoryId);
}
