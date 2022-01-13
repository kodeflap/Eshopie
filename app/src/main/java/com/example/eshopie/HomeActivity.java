package com.example.eshopie;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eshopie.databinding.ActivityHomeBinding;
import com.example.eshopie.ui.cart.MyCartFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;

    private FrameLayout frameLayout;
    private static int currentFragment;
    private NavigationView navigationView;
    private ImageView actionBarLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        actionBarLogo = findViewById(R.id.actionbar_logo);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_orders, R.id.nav_rewards, R.id.nav_cart,R.id.nav_wishlist,R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getMenuInflater().inflate(R.menu.home, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_search) {
            return true;
        }
        else if (id == R.id.home_notification) {
            return true;
        }
        else if (id == R.id.home_cart) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void myCart() {
        actionBarLogo.setVisibility(View.GONE);
        invalidateOptionsMenu();
        navigationView.getMenu().getItem(3).setChecked(true);

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setFragment(Fragment fragment,int fragmentNo) {

    }
}