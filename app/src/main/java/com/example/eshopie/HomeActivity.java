package com.example.eshopie;

import static com.example.eshopie.ui.register.RegisterActivity.setSignUpFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eshopie.databinding.ActivityHomeBinding;
import com.example.eshopie.db.DBQueries;
import com.example.eshopie.ui.cart.CartFragment;
import com.example.eshopie.ui.home.HomeFragment;
import com.example.eshopie.ui.register.RegisterActivity;
import com.example.eshopie.ui.register.registerFragments.SignIn;
import com.example.eshopie.ui.register.registerFragments.SignUp;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    public static Boolean showCart = false;

    private FrameLayout frameLayout;
    private int currentFragment;
    private NavigationView navigationView;
    public static DrawerLayout drawer;
    public NavController navController;
    private Window window;
    public static Dialog signInDialog;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.appBarHome.toolbar;
        setSupportActionBar(toolbar);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_orders, R.id.nav_rewards, R.id.nav_cart, R.id.nav_wishlist, R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getMenu().findItem(R.id.nav_sign_out).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseAuth.getInstance().signOut();
                DBQueries.clearData();
                Intent registerIntent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
                return true;
            }
        });

        frameLayout = findViewById(R.id.home_frameLayout);

        if (showCart) {
           // drawer.setDrawerLockMode(drawer.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            destinationFragments("Cart", new CartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }
        signInDialogFun(HomeActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //checking the current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(currentUser != null);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {
                if (showCart) {
                    showCart = false;
                    finish();
                } else {
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                }
            }
        }
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
        } else if (id == R.id.home_notification) {
            return true;
        } else if (id == R.id.home_cart) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                destinationFragments("Cart", new CartFragment(), CART_FRAGMENT);
            }
            return true;
        } else if (id == android.R.id.home) {
            if (showCart) {
                showCart = false;
                finish();
                return true;
            }
        }
        //  drawer.closeDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }

    private void destinationFragments(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
      /*  if (fragmentNo == ) {
        }
        navigationView.getMenu().getItem(2).setChecked(true);*/

    }



    @Override
    public boolean onSupportNavigateUp() {
        if (currentUser != null){
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
        }
        else {
            drawer.closeDrawer(GravityCompat.START);
            signInDialog.show();
            return false;
        }
    }

    private void setFragment(Fragment fragment, int fragmentNo) {

        if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }

    }

    public static void signInDialogFun(Context context) {
        /*--------------------cart dialog-----------------------*/
        signInDialog = new Dialog(context);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);

        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signInDialogBtn = signInDialog.findViewById(R.id.qty_cancel_btn);
        Button signUpDialogBtn = signInDialog.findViewById(R.id.qty_ok_btn);

        Intent registerIntent = new Intent(context, RegisterActivity.class);

        signInDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn.disableCloseBtn = true;
                SignUp.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                context.startActivity(registerIntent);
            }
        });

        signUpDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn.disableCloseBtn = true;
                SignUp.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                context.startActivity(registerIntent);
            }
        });

    }

}