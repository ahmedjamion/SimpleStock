package com.ahmedjamion.simplestock.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ahmedjamion.simplestock.database.Product;
import com.ahmedjamion.simplestock.database.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository productRepository;

    private LiveData<List<Product>> allProductInInventory;

    private LiveData<List<Product>> productByName;

    private LiveData<Double> sumOfPrice;

    private LiveData<Integer> sumOfQuantity;

    private LiveData<List<Product>> productById;

    private LiveData<List<Product>> productByInventoryAndName;

    private LiveData<List<Product>> lowQuantityProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProductRepository(application);
    }

    public void insert(Product product){
        productRepository.insert(product);
    }

    public void delete(Product product){
        productRepository.delete(product);
    }

    public void deleteById(int productId){
        productRepository.deleteById(productId);
    }

    public void updateName(int productId, String newProductName){
        productRepository.updateName(productId, newProductName);
    }

    public void updatePrice(int productId, double newProductPrice){
        productRepository.updatePrice(productId, newProductPrice);
    }

    public void updateQuantity(int productId, int newProductQuantity){
        productRepository.updateQuantity(productId, newProductQuantity);
    }

    public void addToQuantity(int productId, int quantityToAdd){
        productRepository.addToQuantity(productId, quantityToAdd);
    }

    public void deductFromQuantity(int productId, int quantityToDeduct){
        productRepository.deductFromQuantity(productId, quantityToDeduct);
    }


    public LiveData<List<Product>> getProductById(int productId){
        productById = productRepository.getProductById(productId);
        return productById;
    }
    public LiveData<Double> getSumOfPrice(int inventoryId){
        sumOfPrice = productRepository.getSumOfPrice(inventoryId);
        return sumOfPrice;
    }

    public LiveData<Integer> getSumOfQuantity(int inventoryId){
        sumOfQuantity = productRepository.getSumOfQuantity(inventoryId);
        return sumOfQuantity;
    }

    public LiveData<List<Product>> getAllProductInInventory(int inventoryId) {
        allProductInInventory = productRepository.getAllProductInInventory(inventoryId);
        return allProductInInventory;
    }

    public LiveData<List<Product>> getProductByName(String productName) {
        productByName = productRepository.getProductByName(productName);
        return productByName;
    }

    public LiveData<List<Product>> getProductByInventoryAndName(int inventoryId, String productName) {
        productByInventoryAndName = productRepository.getProductByInventoryAndName(inventoryId, productName);
        return productByInventoryAndName;
    }

    public LiveData<List<Product>> getLowQuantityProducts(int inventoryId){
        lowQuantityProducts = productRepository.getLowQuantityProducts(inventoryId);
        return lowQuantityProducts;
    }
}
