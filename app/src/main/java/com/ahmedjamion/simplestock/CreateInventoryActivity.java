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

import com.ahmedjamion.simplestock.database.Inventory;
import com.ahmedjamion.simplestock.viewmodel.InventoryViewModel;

public class CreateInventoryActivity extends AppCompatActivity {
    private EditText editTextInventoryName;

    private Button buttonCreate;
    private InventoryViewModel inventoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Inventory");

        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        editTextInventoryName = findViewById(R.id.edittext_inventory_name);

        buttonCreate = findViewById(R.id.button_create_inventory);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createInventory();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(CreateInventoryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);
    }
    private void createInventory(){
        String inventoryName = editTextInventoryName.getText().toString();
        if(inventoryName.trim().isEmpty()){
            Toast.makeText(this, "Incomplete input", Toast.LENGTH_SHORT).show();
            return;
        }
        Inventory inventory = new Inventory(inventoryName);
        inventoryViewModel.insert(inventory);

        Intent intent = new Intent(CreateInventoryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateInventory(){
        String newInventoryName = editTextInventoryName.getText().toString();
        if(newInventoryName.trim().isEmpty()){
            Toast.makeText(this, "Incomplete input", Toast.LENGTH_SHORT).show();
            return;
        }
        Inventory inventory = new Inventory(newInventoryName);
        inventoryViewModel.update(inventory);
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
            Toast.makeText(this, "You can't do this to me", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.icon_store) {
            Intent data = getIntent();

            Intent intent = new Intent(CreateInventoryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}