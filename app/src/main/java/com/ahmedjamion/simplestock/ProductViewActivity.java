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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedjamion.simplestock.adapter.ProductAdapter;
import com.ahmedjamion.simplestock.adapter.ProductViewAdapter;
import com.ahmedjamion.simplestock.database.Category;
import com.ahmedjamion.simplestock.database.Product;
import com.ahmedjamion.simplestock.viewmodel.CategoryViewModel;
import com.ahmedjamion.simplestock.viewmodel.ProductViewModel;

import java.util.List;

public class ProductViewActivity extends AppCompatActivity {

    private Button buttonDeleteProductById;
    private Button buttonUpdateProduct;

    private EditText editTextUpdateProductName;
    private EditText editTextUpdatePrice;
    private EditText editTextUpdateQuantity;
    private Button buttonUpdateName;
    private Button buttonUpdatePrice;
    private Button buttonUpdateQuantity;

    private EditText editTextAddToQuantity;
    private EditText editTextDeductFromQuantity;
    private Button buttonAddToQuantity;
    private Button buttonDeductFromQuantity;

    private ProductViewModel productViewModel;
    private CategoryViewModel categoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_product_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        final ProductViewAdapter productViewAdapter = new ProductViewAdapter();
        recyclerView.setAdapter(productViewAdapter);

        Intent data = getIntent();
        int productId = data.getIntExtra("productId", -1);
        int inventoryId = data.getIntExtra("inventoryId", -1);
        String inventoryName = data.getStringExtra("inventoryName");

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        productViewModel.getProductById(productId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if(products != null && !products.isEmpty()) {
                    productViewAdapter.setProducts(products);
                    Product product = products.get(0);
                    getSupportActionBar().setTitle("Update " + product.getProductName());
                }
            }
        });

        categoryViewModel.getCategoryByInventory(inventoryId).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                productViewAdapter.setCategories(categories);
            }
        });

        buttonUpdateProduct = findViewById(R.id.button_update);
        buttonUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductViewActivity.this, SearchActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                intent.putExtra("inventoryName", inventoryName);
                startActivity(intent);
                finish();
            }
        });
        buttonDeleteProductById = findViewById(R.id.button_delete);
        buttonDeleteProductById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductViewActivity.this);
                builder.setMessage("Are you sure you want to delete this product record?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked Yes button
                                productViewModel.deleteById(productId);
                                Intent intent = new Intent(ProductViewActivity.this, SearchActivity.class);
                                intent.putExtra("inventoryId", inventoryId);
                                intent.putExtra("inventoryName", inventoryName);
                                startActivity(intent);
                                finish();
                                Toast.makeText(ProductViewActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
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

        editTextUpdateProductName = findViewById(R.id.edittext_update_product_name);
        buttonUpdateName = findViewById(R.id.button_update_name);
        buttonUpdateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextUpdateProductName.getText().toString().isEmpty()){
                    Toast.makeText(ProductViewActivity.this, "No Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                productViewModel.updateName(productId, editTextUpdateProductName.getText().toString());
                editTextUpdateProductName.setText("");
                closeKeyboard();
            }
        });

        editTextUpdatePrice = findViewById(R.id.edittext_update_price);
        buttonUpdatePrice = findViewById(R.id.button_update_price);
        buttonUpdatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextUpdatePrice.getText().toString().isEmpty()){
                    Toast.makeText(ProductViewActivity.this, "No Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                productViewModel.updatePrice(productId, Double.valueOf(editTextUpdatePrice.getText().toString()));
                editTextUpdatePrice.setText("");
                closeKeyboard();
            }
        });

        editTextUpdateQuantity = findViewById(R.id.edittext_update_quantity);
        buttonUpdateQuantity = findViewById(R.id.button_update_quantity);
        buttonUpdateQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextUpdateQuantity.getText().toString().isEmpty()){
                    Toast.makeText(ProductViewActivity.this, "No Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                productViewModel.updateQuantity(productId, Integer.valueOf(editTextUpdateQuantity.getText().toString()));
                editTextUpdateQuantity.setText("");
                closeKeyboard();
            }
        });

        editTextAddToQuantity = findViewById(R.id.edittext_add_to_quantity);
        buttonAddToQuantity = findViewById(R.id.button_add_to_quantity);
        buttonAddToQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextAddToQuantity.getText().toString().isEmpty()){
                    Toast.makeText(ProductViewActivity.this, "No Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                productViewModel.addToQuantity(productId, Integer.valueOf(editTextAddToQuantity.getText().toString()));
                editTextAddToQuantity.setText("");
                closeKeyboard();
            }
        });

        editTextDeductFromQuantity = findViewById(R.id.edittext_deduct_from_quantity);
        buttonDeductFromQuantity = findViewById(R.id.button_deduct_from_quantity);
        buttonDeductFromQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextDeductFromQuantity.getText().toString().isEmpty()){
                    Toast.makeText(ProductViewActivity.this, "No Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                productViewModel.deductFromQuantity(productId, Integer.valueOf(editTextDeductFromQuantity.getText().toString()));
                editTextDeductFromQuantity.setText("");
                closeKeyboard();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(ProductViewActivity.this, SearchActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                intent.putExtra("inventoryName", inventoryName);
                startActivity(intent);
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
            String inventoryName = data.getStringExtra("inventoryName");

            Intent intent = new Intent(ProductViewActivity.this, SearchActivity.class);
            intent.putExtra("inventoryId", inventoryId);
            intent.putExtra("inventoryName", inventoryName);
            startActivity(intent);
            finish();
            return true;
        } else if (item.getItemId() == R.id.icon_store) {
            Intent data = getIntent();
            int inventoryId = data.getIntExtra("inventoryId", -1);
            String inventoryName = data.getStringExtra("inventoryName");

            Intent intent = new Intent(ProductViewActivity.this, MainActivity.class);
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