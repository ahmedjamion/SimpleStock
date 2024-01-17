package com.ahmedjamion.simplestock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedjamion.simplestock.R;
import com.ahmedjamion.simplestock.database.Category;
import com.ahmedjamion.simplestock.database.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ProductHolder>{
    private List<Product> products = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    @NonNull
    @Override
    public ProductViewAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_view, parent, false);
        return new ProductViewAdapter.ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewAdapter.ProductHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.textview_product_name.setText(currentProduct.getProductName());
        holder.textview_product_price.setText(String.valueOf(currentProduct.getProductPrice()));
        holder.textview_product_quantity.setText(String.valueOf(currentProduct.getProductQuantity()));

        int categoryId = currentProduct.getCategoryId();
        holder.textview_product_category.setText(String.valueOf(getCategoryName(categoryId)));
    }

    private String getCategoryName(int categoryId) {
        for (Category category : categories) {
            if (category.getCategoryId() == categoryId) {
                return category.getCategoryName();
            }
        }
        return ""; // Handle case where category is not found
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    public void setCategories(List<Category> categories){
        this.categories = categories;
    }


    class ProductHolder extends RecyclerView.ViewHolder{
        private TextView textview_product_name;
        private TextView textview_product_category;
        private TextView textview_product_price;
        private TextView textview_product_quantity;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            textview_product_name = itemView.findViewById(R.id.textview_product_name);
            textview_product_category = itemView.findViewById(R.id.textview_product_category);
            textview_product_price = itemView.findViewById(R.id.textview_product_price);
            textview_product_quantity = itemView.findViewById(R.id.textview_product_quantity);

        }
    }
}
