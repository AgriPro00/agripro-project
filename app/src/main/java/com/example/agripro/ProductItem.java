package com.example.agripro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductItem extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<Product> productList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_item);

        Intent intent = getIntent();
        String productName = intent.getStringExtra("ProductName");
        String category = intent.getStringExtra("Category");

        // Display the data (assuming you have TextViews and an ImageView in your layout)
        TextView nameTextView = findViewById(R.id.textView);

        nameTextView.setText(productName);


        ////////////////////////////////recycleView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize the data list and adapter
        productList = new ArrayList<>();
        adapter = new ProductAdapter(this, productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product item) {
                Intent intent = new Intent(ProductItem.this, ProductDetail.class);
                intent.putExtra("ProductName", item.getProductName());
                intent.putExtra("Description", item.getDescription());
                intent.putExtra("Category", item.getCategory());
                intent.putExtra("Unit", item.getUnit());
                intent.putExtra("UnitImg", item.getUnitImg());
                intent.putExtra("Quantity", item.getQuantity());
                intent.putExtra("RetailPrice", item.getRetailPrice());
                intent.putExtra("WholeSale", item.getWholeSale());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        // Fetch data from Firestore
        showProduct(productName, category);

    }

    private void showProduct(String productName, String category) {
        db.collection("postProduct")
                .whereEqualTo("Category",category)
                .whereEqualTo("ProductName", productName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            productList.clear(); // Clear the list before adding new items
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product item = document.toObject(Product.class);
                                productList.add(item);
                            }
                            adapter.notifyDataSetChanged(); // Notify the adapter about data changes
                        } else {
                            // Handle the error here
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}