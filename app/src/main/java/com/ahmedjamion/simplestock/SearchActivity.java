package com.ahmedjamion.simplestock;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedjamion.simplestock.database.Inventory;
import com.ahmedjamion.simplestock.database.Product;
import com.ahmedjamion.simplestock.viewmodel.InventoryViewModel;
import com.ahmedjamion.simplestock.viewmodel.ProductViewModel;

public class SearchActivity extends AppCompatActivity {

    public static final int SEARCH_ITEM_REQUEST = 1;
    public static final int ALL_ITEM_REQUEST = 2;
    private EditText editTextSearch;
    private ImageButton buttonSearch;
    private Button buttonAddProduct;

    private Button buttonAll;

    private TextView textViewTotalAmount;

    private TextView textViewTotalQuantity;

    private TextView textViewInventoryAmountLabel;
    private TextView textViewInventoryQuantityLabel;
    private TextView textViewAmountPhp;
    private TextView textViewQuantityPcs;
    private Button buttonLowStocks;

    private ProductViewModel productViewModel;
    private InventoryViewModel inventoryViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextSearch = findViewById(R.id.edittext_search);
        buttonSearch = findViewById(R.id.button_search);
        buttonAddProduct = findViewById(R.id.button_add_product);
        buttonAll = findViewById(R.id.button_all);
        textViewTotalAmount = findViewById(R.id.textview_total_inventory);
        textViewTotalQuantity = findViewById(R.id.textview_total_quantity);
        textViewInventoryAmountLabel = findViewById(R.id.textview_total_inventory_label);
        textViewInventoryQuantityLabel = findViewById(R.id.textview_total_quantity_label);
        textViewAmountPhp = findViewById(R.id.textview_total_inventory_php);
        textViewQuantityPcs = findViewById(R.id.textview_total_quantity_pcs);
        buttonLowStocks = findViewById(R.id.button_low_stocks);

        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        Intent data = getIntent();
        int inventoryId = data.getIntExtra("inventoryId", -1);




        inventoryViewModel.getInventoryNameById(inventoryId).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                getSupportActionBar().setTitle(s);
            }
        });

        productViewModel.getSumOfPrice(inventoryId).observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                if(aDouble == null){
                    textViewTotalAmount.setText("");
                    return;
                }
                textViewTotalAmount.setText(String.valueOf(aDouble));
                textViewInventoryAmountLabel.setText("Total Inventory Amount");
                textViewAmountPhp.setText("Php");
            }
        });



        productViewModel.getSumOfQuantity(inventoryId).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer anInteger) {
                if(anInteger == null){
                    textViewTotalAmount.setText("");
                    return;
                }
                textViewTotalQuantity.setText(String.valueOf(anInteger));
                textViewInventoryQuantityLabel.setText("Total Inventory Quantity");
                textViewQuantityPcs.setText("Pcs");
            }
        });


        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, CategoryActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                startActivity(intent);
                finish();
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchProductName = editTextSearch.getText().toString();
                if(searchProductName.isEmpty()){
                    Toast.makeText(SearchActivity.this, "Empty Search", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SearchActivity.this, ProductActivity.class);
                intent.putExtra("productName", searchProductName);
                intent.putExtra("inventoryId", inventoryId);
                intent.putExtra("buttonClicked", "search");
                startActivity(intent);
                finish();
            }
        });

        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ProductActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                intent.putExtra("buttonClicked", "all");
                startActivity(intent);
                finish();
            }
        });

        buttonLowStocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ProductActivity.class);
                intent.putExtra("inventoryId", inventoryId);
                intent.putExtra("buttonClicked", "low");
                startActivity(intent);
                finish();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.icon_store) {
            Intent data = getIntent();
            int inventoryId = data.getIntExtra("inventoryId", -1);
            String inventoryName = data.getStringExtra("inventoryName");

            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
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