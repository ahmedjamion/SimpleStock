package com.ahmedjamion.simplestock.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedjamion.simplestock.MainActivity;
import com.ahmedjamion.simplestock.R;
import com.ahmedjamion.simplestock.database.Inventory;
import com.ahmedjamion.simplestock.database.InventoryDao;
import com.ahmedjamion.simplestock.database.InventoryRepository;
import com.ahmedjamion.simplestock.viewmodel.InventoryViewModel;
import com.ahmedjamion.simplestock.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryHolder> {
    private List<Inventory> inventories = new ArrayList<>();
    private OnItemClickListener listener;



    @NonNull
    @Override
    public InventoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_item, parent, false);
        return new InventoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryHolder holder, int position) {
        Inventory currentInventory = inventories.get(position);
        holder.textViewInventoryName.setText(currentInventory.getInventoryName());

        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    listener.onDeleteIconClick(currentInventory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inventories.size();
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
        notifyDataSetChanged();
    }


    class InventoryHolder extends RecyclerView.ViewHolder {
        private TextView textViewInventoryName;
        private ImageView iconDelete;

        public InventoryHolder(@NonNull View itemView) {
            super(itemView);
            textViewInventoryName = itemView.findViewById(R.id.text_view_inventory_name);
            iconDelete = itemView.findViewById(R.id.icon_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(inventories.get(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Inventory inventory);
        void onDeleteIconClick(Inventory inventory);
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
