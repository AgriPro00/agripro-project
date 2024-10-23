package com.example.agripro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ImageView splashLogo = findViewById(R.id.imageView);
        ImageView textLogo = findViewById(R.id.imageText);
        // Load the animation
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_screen);

        // Start the animation
        splashLogo.startAnimation(scaleAnimation);
        textLogo.startAnimation(scaleAnimation);

        // Use a Handler to transition to the main activity after the animation
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish(); // Close the splash activity
        }, 2000);
    }
}