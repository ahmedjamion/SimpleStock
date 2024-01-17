package com.ahmedjamion.simplestock;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.ahmedjamion.simplestock.adapter.InventoryAdapter;
import com.ahmedjamion.simplestock.adapter.ProductAdapter;
import com.ahmedjamion.simplestock.database.Category;
import com.ahmedjamion.simplestock.database.CategoryDao;
import com.ahmedjamion.simplestock.database.Inventory;
import com.ahmedjamion.simplestock.database.Product;
import com.ahmedjamion.simplestock.database.SimpleStockDatabase;
import com.ahmedjamion.simplestock.viewmodel.CategoryViewModel;
import com.ahmedjamion.simplestock.viewmodel.InventoryViewModel;
import com.ahmedjamion.simplestock.viewmodel.ProductViewModel;

import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private CategoryViewModel categoryViewModel;
    private InventoryViewModel inventoryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        final ProductAdapter productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);

        Intent data = getIntent();
        int inventoryId = data.getIntExtra("inventoryId", -1);
        String inventoryName = data.getStringExtra("inventoryName");
        String productName = data.getStringExtra("productName");
        String buttonClicked = data.getStringExtra("buttonClicked");

        getSupportActionBar().setTitle(inventoryName);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        if("search".equals(buttonClicked)) {

            productViewModel.getProductByInventoryAndName(inventoryId, productName).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    productAdapter.setProducts(products);
                }
            });

            categoryViewModel.getCategoryByInventory(inventoryId).observe(this, new Observer<List<Category>>() {
                @Override
                public void onChanged(List<Category> categories) {
                    productAdapter.setCategories(categories);
                }
            });

            inventoryViewModel.getInventoryNameById(inventoryId).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    getSupportActionBar().setTitle(s + " Search Results");
                }
            });
        }else if("all".equals(buttonClicked)) {
            productViewModel.getAllProductInInventory(inventoryId).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    productAdapter.setProducts(products);
                }
            });
            categoryViewModel.getCategoryByInventory(inventoryId).observe(this, new Observer<List<Category>>() {
                @Override
                public void onChanged(List<Category> categories) {
                    productAdapter.setCategories(categories);
                }
            });

            inventoryViewModel.getInventoryNameById(inventoryId).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    getSupportActionBar().setTitle(s + " All Products");
                }
            });

        }else if("low".equals(buttonClicked)) {
            productViewModel.getLowQuantityProducts(inventoryId).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    productAdapter.setProducts(products);
                }
            });

            categoryViewModel.getCategoryByInventory(inventoryId).observe(this, new Observer<List<Category>>() {
                @Override
                public void onChanged(List<Category> categories) {
                    productAdapter.setCategories(categories);
                }
            });

            inventoryViewModel.getInventoryNameById(inventoryId).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    getSupportActionBar().setTitle(s + " Low Stocks");
                }
            });
    }


        productAdapter.setOnClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(ProductActivity.this, ProductViewActivity.class);
                intent.putExtra("productId", product.getProductId());
                intent.putExtra("inventoryId", product.getInventoryId());
                startActivity(intent);
                finish();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ProductActivity.this, SearchActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                startActivity(intent);
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.icon_search) {
            Intent data = getIntent();
            int inventoryId = data.getIntExtra("inventoryId", -1);

            Intent intent = new Intent(ProductActivity.this, SearchActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.icon_store) {
            Intent data = getIntent();
            int inventoryId = data.getIntExtra("inventoryId", -1);
            String inventoryName = data.getStringExtra("inventoryName");

            Intent intent = new Intent(ProductActivity.this, MainActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}