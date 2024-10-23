package com.example.agripro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agripro.databinding.ActivitySwitchPageBinding;

public class SwitchPage extends AppCompatActivity {

    private ActivitySwitchPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_switch_page);

        // Load HomeFragment by default
        if (savedInstanceState == null) { // Prevent loading on orientation changes
            replaceFragment(new HomeFragment());
        }

        Intent intent = getIntent();
        String receivedData = intent.getStringExtra("Page");
        if (receivedData != null) {
            switch (receivedData) {
                case "Home":
                    replaceFragment(new HomeFragment());
                    break;
                case "ChatBot":
                    replaceFragment(new ChatBotFragment());
                    break;
                case "Info":
                    replaceFragment(new InfoFragment());
                    break;
                case "Cart":
                    replaceFragment(new CartFragment());
                    break;
                case "Setting":
                    replaceFragment(new SettingFragment());
                    break;
                default:
                    // Handle unknown page case if necessary
                    break;
            }
        }
        ///////////////////////////////////////////////////////Navigation Button
        final int navigationHomeId = R.id.navigation_home;
        final int navigationChatId = R.id.navigation_chat;
        final int navigationInfoId = R.id.navigation_info;
        final int navigationCartId = R.id.navigation_cart;
        final int navigationSettingId = R.id.navigation_setting;
        binding = ActivitySwitchPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.navView.setOnNavigationItemSelectedListener(menuItem -> {
            if (menuItem.getItemId() == navigationHomeId) {
                replaceFragment(new HomeFragment());
                return true;
            } else if (menuItem.getItemId() == navigationChatId) {
                replaceFragment(new ChatBotFragment());
                return true;
            } else if (menuItem.getItemId() == navigationInfoId) {
                replaceFragment(new InfoFragment());
                return true;
            } else if (menuItem.getItemId() == navigationCartId) {
                replaceFragment(new CartFragment());
                return true;
            } else if (menuItem.getItemId() == navigationSettingId) {
                replaceFragment(new SettingFragment());
                return true;
            }
            // Add conditions for other menu items as needed
            return false;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
}