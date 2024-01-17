package com.ahmedjamion.simplestock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedjamion.simplestock.ProductActivity;
import com.ahmedjamion.simplestock.R;
import com.ahmedjamion.simplestock.database.Category;
import com.ahmedjamion.simplestock.database.CategoryDao;
import com.ahmedjamion.simplestock.database.Inventory;
import com.ahmedjamion.simplestock.database.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private List<Product> products = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();

    private ProductAdapter.OnItemClickListener listener;


    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product currentProduct = products.get(position);
        holder.textViewProductName.setText(currentProduct.getProductName());
        holder.textViewProductPrice.setText(String.valueOf(currentProduct.getProductPrice()));
        holder.textViewProductQuantity.setText(String.valueOf(currentProduct.getProductQuantity()));

        int categoryId = currentProduct.getCategoryId();
        holder.textViewProductCategory.setText(String.valueOf(getCategoryName(categoryId)));


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
        private TextView textViewProductName;
        private TextView textViewProductPrice;
        private TextView textViewProductCategory;
        private TextView textViewProductQuantity;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textview_product_name);
            textViewProductCategory = itemView.findViewById(R.id.textview_product_category);
            textViewProductPrice = itemView.findViewById(R.id.textview_product_price);
            textViewProductQuantity = itemView.findViewById(R.id.textview_product_quantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(products.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnClickListener(ProductAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
