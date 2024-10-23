package com.example.agripro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Login extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        //Buttons
        TextView registerBtn = findViewById(R.id.registerButton);
        Button loginBtn = findViewById(R.id.loginButton);
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        //Edit Text
        EditText user = findViewById(R.id.User);
        EditText password = findViewById(R.id.Password);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getUser = user.getText().toString();
                final String getPassword = password.getText().toString();

                if(isInternetAvailable()){
                    ///////////////////////////Authentication
                    if (getUser.isEmpty()){
                        user.setBackgroundResource(R.drawable.box_error);
                    } else{
                        user.setBackgroundResource(R.color.white);
                    }
                    if (getPassword.isEmpty()){
                        password.setBackgroundResource(R.drawable.box_error);
                    } else {
                        password.setBackgroundResource(R.color.white);
                    }
                    if (!getUser.isEmpty() && !getPassword.isEmpty()){
                        db.collection("customer")
                                .whereEqualTo("email", getUser)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            user.setBackgroundResource(R.color.white);
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String Password = document.getString("password");
                                                if(Objects.equals(Password, getPassword)) {
                                                    String documentId = document.getId();
                                                    // Put data in SharedPreferences
                                                    String accountId = documentId.toString();
                                                    editor.putString("Id", accountId);
                                                    editor.apply();
                                                    password.setBackgroundResource(R.color.white);
                                                    Intent intent = new Intent(Login.this, SwitchPage.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    password.setBackgroundResource(R.drawable.box_error);
                                                    Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        } else {
                                            user.setBackgroundResource(R.drawable.box_error);
                                            Toast.makeText(Login.this, "Can't Find Email", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        db.collection("customer")
                                .whereEqualTo("number", getUser)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            user.setBackgroundResource(R.color.white);
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                String Password = document.getString("password");
                                                if(Objects.equals(Password, getPassword)) {
                                                    String documentId = document.getId();
                                                    // Put data in SharedPreferences
                                                    String accountId = documentId.toString();
                                                    editor.putString("Id", accountId);
                                                    editor.apply();
                                                    password.setBackgroundResource(R.color.white);
                                                    Intent intent = new Intent(Login.this, SwitchPage.class);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    password.setBackgroundResource(R.drawable.box_error);
                                                    Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        } else {
                                            user.setBackgroundResource(R.drawable.box_error);
                                            Toast.makeText(Login.this, "Can't Find Number", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                    } else {
                        Toast.makeText(Login.this, "Can't Login", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(Login.this, "No internet connection available", Toast.LENGTH_LONG).show();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAccount.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}