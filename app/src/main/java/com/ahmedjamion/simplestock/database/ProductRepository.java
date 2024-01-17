package com.ahmedjamion.simplestock.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {
    private ProductDao productDao;

    private LiveData<List<Product>> allProduct;

    private LiveData<List<Product>> allProductInInventory;

    private LiveData<List<Product>> productByName;
    private LiveData<List<Product>> productByInventoryAndName;

    private LiveData<List<Product>> productById;
    private LiveData<Double> sumOfPrice;
    private LiveData<Integer> sumOfQuantity;
    private ExecutorService executorService;

    private LiveData<List<Product>> lowQuantityProducts;

    public ProductRepository(Application application){
        SimpleStockDatabase database = SimpleStockDatabase.getInstance(application);
        productDao = database.productDao();
        allProduct = productDao.getAllProduct();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(Product product) {
        executorService.submit(() -> productDao.insert(product));
    }

    public void delete(Product product) {
        executorService.submit(() -> productDao.delete(product));
    }

    public void deleteById(int productId){
        executorService.submit(() -> productDao.deleteById(productId));
    }

    public void updateName(int productId, String newProductName){
        executorService.submit(() -> productDao.updateName(productId, newProductName));
    }

    public void updatePrice(int productId, double newProductPrice){
        executorService.submit(() -> productDao.updatePrice(productId, newProductPrice));
    }

    public void updateQuantity(int productId, int newProductQuantity){
        executorService.submit(() -> productDao.updateQuantity(productId, newProductQuantity));
    }

    public void addToQuantity(int productId, int quantityToAdd){
        executorService.submit(() -> productDao.addToQuantity(productId, quantityToAdd));
    }

    public void deductFromQuantity(int productId, int quantityToDeduct){
        executorService.submit(() -> productDao.deductFromQuantity(productId, quantityToDeduct));
    }

    public LiveData<List<Product>> getProductById(int productId){
        productById = productDao.getProductById(productId);
        return productById;
    }
    public LiveData<Double> getSumOfPrice(int inventoryId){
        sumOfPrice =  productDao.getSumOfPrice(inventoryId);
        return sumOfPrice;
    }

    public LiveData<Integer> getSumOfQuantity(int inventoryId){
        sumOfQuantity =  productDao.getSumOfQuantity(inventoryId);
        return sumOfQuantity;
    }



    public LiveData<List<Product>> getAllProductInInventory(int inventoryId){
        allProductInInventory = productDao.getAllProductInInventory(inventoryId);
        return allProductInInventory;
    }

    public LiveData<List<Product>> getProductByName(String productName){
        productByName = productDao.getProductByName(productName);
        return productByName;
    }

    public LiveData<List<Product>> getProductByInventoryAndName(int inventoryId, String productName){
        productByInventoryAndName = productDao.getProductByInventoryAndName(inventoryId, productName);
        return productByInventoryAndName;
    }

    public LiveData<List<Product>> getLowQuantityProducts(int inventoryId){
        lowQuantityProducts = productDao.getLowQuantityProducts(inventoryId);
        return lowQuantityProducts;
    }
}
