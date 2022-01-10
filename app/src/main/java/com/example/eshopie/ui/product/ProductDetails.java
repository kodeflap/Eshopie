package com.example.eshopie.ui.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;

import com.example.eshopie.R;
import com.example.eshopie.ui.product.adapter.ProductImageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;

    private FloatingActionButton addToWishlistButton;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager = findViewById(R.id.product_image_viewpager);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishlistButton = findViewById(R.id.add_to_wishlist_button);

        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.home);
        productImages.add(R.drawable.profile);
        productImages.add(R.drawable.add_circle_outline);
        productImages.add(R.drawable.ic_baseline_shopping_basket_24);
        productImages.add(R.drawable.ic_menu_gallery);
        productImages.add(R.drawable.ic_menu_slideshow);

        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
        productImageViewPager.setAdapter(productImageAdapter);

        viewPagerIndicator.setupWithViewPager(productImageViewPager,true);

        addToWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ALREADY_ADDED_TO_WISHLIST) {
                    ALREADY_ADDED_TO_WISHLIST = false;
                    addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#736F6F")));
                }
                else {
                    ALREADY_ADDED_TO_WISHLIST = true;
                    addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.purple_500));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            return true;
        } else if (id == R.id.search) {
            return true;
        } else if (id == R.id.cart) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}