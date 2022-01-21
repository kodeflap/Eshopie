package com.example.eshopie.ui.product;

import static com.example.eshopie.HomeActivity.showCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.eshopie.HomeActivity;
import com.example.eshopie.R;
import com.example.eshopie.model.RewardModel;
import com.example.eshopie.ui.delivery.DeliveryActivity;
import com.example.eshopie.ui.product.adapter.ProductDetailsAdapter;
import com.example.eshopie.ui.product.adapter.ProductImageAdapter;
import com.example.eshopie.ui.rewards.RewardAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;
    private Button couponRedeemBtn;

    /*-----------------Coupon Dialog variable--------------------------*/
    public static TextView couponTitle;
    public static TextView couponBody;
    public static TextView couponExpDate;

    private static RecyclerView couponRecyclerView;
    private static LinearLayout selectedCoupon;

    /*-----------------Product Details variable--------------------------*/
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;

    /*---------------------rating layout variable-------------------------*/
    private RatingBar rateNowContainer;

    /*---------------------buy now variable------------------------------*/
    private Button buyNowBtn;

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

        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTabLayout = findViewById(R.id.product_details_tablayout);

        buyNowBtn = findViewById(R.id.buy_now_btn);
        couponRedeemBtn = findViewById(R.id.redemption_button);

        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.home);
        productImages.add(R.drawable.profile);
        productImages.add(R.drawable.add_circle_outline);
        productImages.add(R.drawable.ic_baseline_shopping_basket_24);
        productImages.add(R.drawable.phone);
        productImages.add(R.drawable.delete);

        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
        productImageViewPager.setAdapter(productImageAdapter);

        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);

        addToWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ALREADY_ADDED_TO_WISHLIST) {
                    ALREADY_ADDED_TO_WISHLIST = false;
                    addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#736F6F")));
                } else {
                    ALREADY_ADDED_TO_WISHLIST = true;
                    addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.purple_500));
                }
            }
        });

        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /*------------rating--------------*/
        rateNowContainer = findViewById(R.id.rating_bar_container);
        //todo: rating bar code

        /*-----------------------------buy now-------------------*/
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyNowIntent = new Intent(ProductDetails.this, DeliveryActivity.class);
                startActivity(buyNowIntent);
            }
        });
        /*----------------------coupon dialog section-----------------------------------*/
        Dialog checkCouponPriceDialog = new Dialog(ProductDetails.this);
        checkCouponPriceDialog.setContentView(R.layout.coupon_dialog);
        checkCouponPriceDialog.setCancelable(true);
        checkCouponPriceDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView toggleRecyclerView = checkCouponPriceDialog.findViewById(R.id.toggle_recyclerview);
        couponRecyclerView = checkCouponPriceDialog.findViewById(R.id.coupon_recyclerview);
        selectedCoupon = checkCouponPriceDialog.findViewById(R.id.selected_coupon);


        couponTitle = checkCouponPriceDialog.findViewById(R.id.mr_title);
        couponExpDate = checkCouponPriceDialog.findViewById(R.id.mr_validity);
        couponBody = checkCouponPriceDialog.findViewById(R.id.mr_body);

        TextView originalPrice = checkCouponPriceDialog.findViewById(R.id.coupon_original_price);
        TextView discountedPrice = checkCouponPriceDialog.findViewById(R.id.coupon_discount_price);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetails.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        couponRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback", "Till 2nd feb 2022", "get 50 % off on purchase above Rs.500/-"));
        rewardModelList.add(new RewardModel("Discount", "Till 2nd feb 2022", "get 50 % off on purchase above Rs.500/-"));
        rewardModelList.add(new RewardModel("Buy one get one free", "Till 2nd feb 2022", "get 50 % off on purchase above Rs.500/-"));
        rewardModelList.add(new RewardModel("Cashback", "Till 2nd feb 2022", "get 50 % off on purchase above Rs.500/-"));
        rewardModelList.add(new RewardModel("discount", "Till 2nd feb 2022", "get 50 % off on purchase above Rs.500/-"));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModelList, true);
        couponRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogRecyclerView();
            }
        });
///////////////////////////////coupon Dialog//////////////////////////////////////////////////////


        couponRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCouponPriceDialog.show();
            }
        });
    }

    public static void showDialogRecyclerView() {

        if (couponRecyclerView.getVisibility() == View.GONE) {
            couponRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);
        } else {
            couponRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
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
            Intent cartIntent = new Intent(ProductDetails.this, HomeActivity.class);
            showCart = true;
            startActivity(cartIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}