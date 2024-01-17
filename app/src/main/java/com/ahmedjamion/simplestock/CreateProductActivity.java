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
import android.window.OnBackInvokedDispatcher;

import com.ahmedjamion.simplestock.database.Product;
import com.ahmedjamion.simplestock.viewmodel.CategoryViewModel;
import com.ahmedjamion.simplestock.viewmodel.ProductViewModel;

public class CreateProductActivity extends AppCompatActivity {

    private EditText editTextCreateProductName;
    private EditText editTextCreateProductPrice;
    private EditText editTextCreateProductQuantity;
    private Button buttonCreateProduct;
    private ProductViewModel productViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        editTextCreateProductName = findViewById(R.id.edittext_create_product_name);
        editTextCreateProductPrice = findViewById(R.id.edittext_create_product_price);
        editTextCreateProductQuantity = findViewById(R.id.edittext_create_product_quantity);
        buttonCreateProduct = findViewById(R.id.button_create_product);

        Intent data = getIntent();
        int categoryId = data.getIntExtra("categoryId", -1);
        int inventoryId = data.getIntExtra("inventoryId", -1);

        getSupportActionBar().setTitle("Create New Product");

        buttonCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextCreateProductName.getText().toString().isEmpty() ||
                editTextCreateProductPrice.getText().toString().isEmpty() ||
                editTextCreateProductQuantity.getText().toString().isEmpty()){
                    Toast.makeText(CreateProductActivity.this, "Incomplete Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                createProduct();
                Intent intent = new Intent(CreateProductActivity.this, SearchActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                startActivity(intent);
                finish();
                Toast.makeText(CreateProductActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(CreateProductActivity.this, SearchActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                startActivity(intent);
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);

    }

    public void createProduct(){
        String productName = editTextCreateProductName.getText().toString();
        double productPrice = Double.parseDouble(editTextCreateProductPrice.getText().toString());
        int productQuantity = Integer.parseInt(editTextCreateProductQuantity.getText().toString());

        Intent data = getIntent();
        int categoryId = data.getIntExtra("categoryId", -1);
        int inventoryId = data.getIntExtra("inventoryId", -1);

        Product product = new Product(productName, categoryId, inventoryId, productPrice, productQuantity);
        productViewModel.insert(product);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.icon_search) {
            Intent data = getIntent();
            int inventoryId = data.getIntExtra("inventoryId", -1);
            String inventoryName = data.getStringExtra("inventoryName");

            Intent intent = new Intent(CreateProductActivity.this, SearchActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            intent.putExtra("inventoryName", inventoryName);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.icon_store) {
            Intent data = getIntent();
            int inventoryId = data.getIntExtra("inventoryId", -1);
            String inventoryName = data.getStringExtra("inventoryName");

            Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            intent.putExtra("inventoryName", inventoryName);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}