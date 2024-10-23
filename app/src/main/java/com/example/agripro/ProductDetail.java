package com.example.agripro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProductDetail extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);
        db = FirebaseFirestore.getInstance();

        TextView ProductName = findViewById(R.id.productName);
        TextView Unit = findViewById(R.id.productUnit);
        TextView Category = findViewById(R.id.textCategory);
        TextView Price = findViewById(R.id.textPrice);
        TextView Description = findViewById(R.id.textDescription);
        ImageView UnitImg = findViewById(R.id.imageViewProduct);
        ImageView Add = findViewById(R.id.Add);
        ImageView Minus = findViewById(R.id.Minus);
        EditText Quantity = findViewById(R.id.Quantity);
        Button BtnOrder = findViewById(R.id.btnOrder);
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        Intent intent = getIntent();
        String productName = intent.getStringExtra("ProductName");
        String unit = intent.getStringExtra("Unit");
        String unitImg = intent.getStringExtra("UnitImg");
        String category = intent.getStringExtra("Category");
        String description = intent.getStringExtra("Description");
        String retailPrice = intent.getStringExtra("RetailPrice");
        String wholeSale = intent.getStringExtra("WholeSale");
        Long quantity = intent.getLongExtra("Quantity", 0);
        String id = sharedPreferences.getString("Id", "Unknown");

        Quantity.setText("0");
        ProductName.setText(productName);
        Unit.setText(unit);
        Category.setText(category);
        Price.setText(retailPrice);
        Description.setText(description);
        Picasso.get()
                .load(unitImg)
                .into(UnitImg);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = Quantity.getText().toString();
                int value = Integer.parseInt(currentValue);
                if (value < quantity) {
                    int addValue = value + 1;
                    Quantity.setText(Integer.toString(addValue));
                } else {
                    Quantity.setText(currentValue);
                }
            }
        });
        Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = Quantity.getText().toString();
                int value = Integer.parseInt(currentValue);
                if (value == 0) {
                    Quantity.setText(currentValue);
                } else {
                    int minusValue = Integer.parseInt(currentValue) - 1;
                    Quantity.setText(Integer.toString(minusValue));
                }
            }
        });
        BtnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = Quantity.getText().toString();
                int value = Integer.parseInt(currentValue);
                int retail = Integer.parseInt(retailPrice);
                int wholesale = Integer.parseInt(wholeSale);

                int totalRetail = value * retail;
                int totalWholeSale = value * wholesale;
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("ProductName", productName);
                orderData.put("Category", category);
                orderData.put("Unit", unit);
                orderData.put("UnitImg", unitImg);
                orderData.put("RetailPrice", totalRetail);
                orderData.put("Quantity",currentValue);
                orderData.put("ProdQuantity",quantity);
                orderData.put("WholeSale", totalWholeSale);

                db.collection("customer").document(id).collection("order")
                        .add(orderData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(ProductDetail.this, "Saved", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProductDetail.this, "Error can't save", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}