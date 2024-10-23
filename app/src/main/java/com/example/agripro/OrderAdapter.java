package com.example.agripro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Order> orderList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onRemoveClick(int position);
    }

    public OrderAdapter(Context context, ArrayList<Order> orderList, OnItemClickListener listener) {
        this.context = context;
        this.orderList = orderList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);

        return new OrderAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order product = orderList.get(position);
        holder.ProductName.setText(product.getProductName());
        holder.Unit.setText(product.getUnit());
        holder.Category.setText(product.getCategory());
        holder.RetailPrice.setText(String.valueOf(product.getRetailPrice()));
        Picasso.get().load(product.getProductImg()).into(holder.ProductImg);
        holder.editButton.setOnClickListener(v -> listener.onEditClick(position));
        holder.removeButton.setOnClickListener(v -> listener.onRemoveClick(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ProductName, Unit, Category, RetailPrice;
        Button editButton, removeButton;
        ImageView ProductImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.textProduct);
            ProductImg = itemView.findViewById(R.id.imageProduct);
            Unit = itemView.findViewById(R.id.textUnit);
            Category = itemView.findViewById(R.id.textCategory);
            RetailPrice = itemView.findViewById(R.id.textPrice);
            editButton = itemView.findViewById(R.id.editButton);
            removeButton = itemView.findViewById(R.id.removeButton);

        }
    }
}
