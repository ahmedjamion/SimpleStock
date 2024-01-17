package com.ahmedjamion.simplestock;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmedjamion.simplestock.database.Category;
import com.ahmedjamion.simplestock.database.Inventory;
import com.ahmedjamion.simplestock.viewmodel.CategoryViewModel;
import com.ahmedjamion.simplestock.viewmodel.InventoryViewModel;

public class CreateCategoryActivity extends AppCompatActivity {
    private EditText editTextCategoryName;
    private Button buttonCreateCategory;

    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent data = getIntent();
        int inventoryId = data.getIntExtra("inventoryId", -1);
        getSupportActionBar().setTitle("Create New Category");

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        editTextCategoryName = findViewById(R.id.edittext_category_name);
        buttonCreateCategory = findViewById(R.id.button_create_category);

        buttonCreateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(CreateCategoryActivity.this, SearchActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                startActivity(intent);
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);

    }

    private void createCategory(){
        String categoryName = editTextCategoryName.getText().toString();
        if(categoryName.trim().isEmpty()){
            Toast.makeText(this, "Incomplete input", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = getIntent();
        int inventoryId = data.getIntExtra("inventoryId", -1);

        Category category = new Category(categoryName, inventoryId);
        categoryViewModel.insert(category);

        Intent intent = new Intent(CreateCategoryActivity.this, CategoryActivity.class);
        intent.putExtra("inventoryId", inventoryId);
        startActivity(intent);
        finish();
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

            Intent intent = new Intent(CreateCategoryActivity.this, SearchActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.icon_store) {
            Intent data = getIntent();
            int inventoryId = data.getIntExtra("inventoryId", -1);

            Intent intent = new Intent(CreateCategoryActivity.this, MainActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}