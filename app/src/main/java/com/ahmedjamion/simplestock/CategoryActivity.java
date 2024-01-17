package com.ahmedjamion.simplestock;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmedjamion.simplestock.adapter.CategoryAdapter;
import com.ahmedjamion.simplestock.adapter.InventoryAdapter;
import com.ahmedjamion.simplestock.database.Category;
import com.ahmedjamion.simplestock.database.Inventory;
import com.ahmedjamion.simplestock.viewmodel.CategoryViewModel;
import com.ahmedjamion.simplestock.viewmodel.InventoryViewModel;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private CategoryViewModel categoryViewModel;
    private Button buttonCreateNewCategory;
    private InventoryViewModel inventoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Intent data = getIntent();
        int inventoryId = data.getIntExtra("inventoryId", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        inventoryViewModel.getInventoryNameById(inventoryId).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                getSupportActionBar().setTitle(s);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CategoryAdapter categoryAdapter = new CategoryAdapter();
        recyclerView.setAdapter(categoryAdapter);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.getCategoryByInventory(inventoryId).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setCategories(categories);
            }
        });

        buttonCreateNewCategory = findViewById(R.id.button_create_category);
        buttonCreateNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, CreateCategoryActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                startActivity(intent);
                finish();
            }
        });

        categoryAdapter.setOnClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category category) {
                Intent intent = new Intent(CategoryActivity.this, CreateProductActivity.class);
                intent.putExtra("categoryId", category.getCategoryId());
                intent.putExtra("inventoryId", category.getInventoryId());
                startActivity(intent);
                finish();
            }

            @Override
            public void onIconDeleteClick(Category category) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                builder.setMessage("Are you sure you want to delete this Category?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                categoryViewModel.delete(category);
                                Toast.makeText(CategoryActivity.this, "Inventory Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked No button
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
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

            Intent intent = new Intent(CategoryActivity.this, SearchActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.icon_store) {
            Intent data = getIntent();
            int inventoryId = data.getIntExtra("inventoryId", -1);
            String inventoryName = data.getStringExtra("inventoryName");

            Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}