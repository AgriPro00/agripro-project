package com.example.agripro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAccount extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        ////Input
        EditText name = findViewById(R.id.nameInput);
        EditText number = findViewById(R.id.numberInput);
        EditText email = findViewById(R.id.emailInput);
        EditText password = findViewById(R.id.passwordInput);
        EditText confirm = findViewById(R.id.confirmInput);

        ////Submit Button
        Button submitBtn = findViewById(R.id.submitButton);

        ////Click Submit
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String getName = name.getText().toString();
                final String getNumber = number.getText().toString();
                final String getEmail = email.getText().toString();
                final String getPassword = password.getText().toString();
                final String getConfirm = confirm.getText().toString();
                if (getName.isEmpty()){
                    name.setBackgroundResource(R.drawable.box_error);
                } else{
                    name.setBackgroundResource(R.color.white);
                }
                if (getNumber.isEmpty()){
                    number.setBackgroundResource(R.drawable.box_error);
                } else{
                    number.setBackgroundResource(R.color.white);
                }
                if (getEmail.isEmpty()){
                    email.setBackgroundResource(R.drawable.box_error);
                } else{
                    email.setBackgroundResource(R.color.white);
                }
                if (getPassword.isEmpty()){
                    password.setBackgroundResource(R.drawable.box_error);
                } else{
                    password.setBackgroundResource(R.color.white);
                }
                if (getConfirm.isEmpty() || !getConfirm.equals(getPassword)){
                    confirm.setBackgroundResource(R.drawable.box_error);
                } else{
                    confirm.setBackgroundResource(R.color.white);
                }
                if (!getName.isEmpty() && !getNumber.isEmpty() && !getEmail.isEmpty() && !getPassword.isEmpty() && !getConfirm.isEmpty() && getConfirm.equals(getPassword)) {
                    saveAccount(getName,getNumber,getEmail,getPassword);
                    name.setText("");
                    number.setText("");
                    email.setText("");
                    password.setText("");
                    confirm.setText("");
                }
            }
        });

    }

    private void saveAccount(String name, String number, String email, String password) {
        Map<String, Object> account = new HashMap<>();
        account.put("name", name);
        account.put("number", number);
        account.put("email", email);
        account.put("password", password);

        db.collection("customer")
                .add(account)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateAccount.this, "Account Saved", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateAccount.this, "Can't Save Account", Toast.LENGTH_LONG).show();
                    }
                });

    }
}