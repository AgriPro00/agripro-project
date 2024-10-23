package com.example.agripro;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{

    Context context;
    ArrayList<Product> dataProduct;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Product item);
    }

    public ProductAdapter(Context context, ArrayList<Product> dataProduct, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataProduct = dataProduct;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);

        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = dataProduct.get(position);
        holder.ProductName.setText(product.getProductName());
        holder.Unit.setText(product.getUnit());
        holder.Category.setText(product.getCategory());
        holder.RetailPrice.setText(product.getRetailPrice());
        Picasso.get().load(product.getUnitImg()).into(holder.ProductImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataProduct.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ProductName, Unit, Category, RetailPrice;
        ImageView ProductImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductName = itemView.findViewById(R.id.textProduct);
            ProductImg = itemView.findViewById(R.id.imageProduct);
            Unit = itemView.findViewById(R.id.textUnit);
            Category = itemView.findViewById(R.id.textCategory);
            RetailPrice = itemView.findViewById(R.id.textPrice);
        }
    }

}
