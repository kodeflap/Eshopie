package com.example.eshopie.ui.product;

import static com.example.eshopie.HomeActivity.showCart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.Toast;

import com.example.eshopie.HomeActivity;
import com.example.eshopie.R;
import com.example.eshopie.model.ProductSpecificationModel;
import com.example.eshopie.model.RewardModel;
import com.example.eshopie.ui.delivery.DeliveryActivity;
import com.example.eshopie.ui.product.adapter.ProductDetailsAdapter;
import com.example.eshopie.ui.product.adapter.ProductImageAdapter;
import com.example.eshopie.ui.product.fragment.ProductDescriptionFragment;
import com.example.eshopie.ui.product.fragment.ProductSpecificationFragment;
import com.example.eshopie.ui.rewards.RewardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    private ViewPager productImageViewPager;
    private TextView productTitle;
    private TabLayout viewPagerIndicator;
    private TextView avgRatingView;
    private TextView productPrice;
    private TextView totalRatings;
    private TextView cuttedPrice;
    private ImageView cod;
    private TextView codAvailability;
    private Button couponRedeemBtn;

    /*-----------------rewards section variable--------------------------*/
    private TextView rewardTitle;
    private TextView rewardBody;

    /*-----------------Coupon Dialog variable--------------------------*/
    public static TextView couponTitle;
    public static TextView couponBody;
    public static TextView couponExpDate;

    private static RecyclerView couponRecyclerView;
    private static LinearLayout selectedCoupon;

    /*-----------------Product Description variable---------------------*/
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private TextView productOnlyDescriptionBody;
    public static String productDescription;
    public static String productOtherDetails;
    public static int tabPosition = -1;

    /*---------------------rating layout variable-------------------------*/
    private RatingBar rateNowContainer;
    private TextView totalRating;
    private TextView totalRatingFigure;
    private LinearLayout ratingNumContainer;

    /*---------------------buy now variable------------------------------*/
    private Button buyNowBtn;

    private FloatingActionButton addToWishlistButton;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;

    /*-------------------- Firebase Firestore------------------------------*/
    private FirebaseFirestore firebaseFirestore;


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
        productTitle = findViewById(R.id.product_title);
        avgRatingView = findViewById(R.id.product_rating_view);
        totalRatings = findViewById(R.id.total_rating_mini_view);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        cod = findViewById(R.id.cod_indicator_imageview);
        codAvailability = findViewById(R.id.tv_cod_indicator);

        //product Description
        productDetailsTabsContainer = findViewById(R.id.product_detals_tabs_container);
        productDetailsOnlyContainer = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);

        //rewards
        rewardTitle = findViewById(R.id.reward_title);
        rewardBody = findViewById(R.id.reward_body);

        //ratings
        totalRatings = findViewById(R.id.total_ratings);
        ratingNumContainer = findViewById(R.id.rating_numbers_container);
        totalRatingFigure = findViewById(R.id.total_rating_figure);

        //firebase
        firebaseFirestore = FirebaseFirestore.getInstance();
        List<String> proImages = new ArrayList<>();
        firebaseFirestore.collection("products").document("eoptcV23rvZiu1iPy38O")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (long i = 0; i < (long) documentSnapshot.get("num_of_pro_img") + 1; i++) {
                        proImages.add(documentSnapshot.get("pro_img_" + i).toString());
                    }
                    ProductImageAdapter productImageAdapter = new ProductImageAdapter(proImages);
                    productImageViewPager.setAdapter(productImageAdapter);

                    productTitle.setText(documentSnapshot.get("pro_title").toString());
                    avgRatingView.setText(documentSnapshot.get("avg_rating").toString());
                    totalRatings.setText((long) documentSnapshot.get("total_rating") + " ratings");
                    productPrice.setText("Rs." + documentSnapshot.get("pro_price").toString());
                    cuttedPrice.setText("Rs." + documentSnapshot.get("cutted_price").toString());
                    if ((boolean) documentSnapshot.get("cod")) {
                        cod.setVisibility(View.VISIBLE);
                        codAvailability.setVisibility(View.VISIBLE);
                    } else {
                        cod.setVisibility(View.INVISIBLE);
                        codAvailability.setVisibility(View.INVISIBLE);
                    }
                    rewardTitle.setText((long) documentSnapshot.get("free_coupon") + documentSnapshot.get("free_coupon_title").toString());
                    rewardBody.setText(documentSnapshot.get("free_coupon_body").toString());

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabsContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("pro_description").toString();
                        ProductSpecificationFragment.productSpecificationModelList = new ArrayList<>();
                        productOtherDetails = documentSnapshot.get("pro_other_details").toString();

                        for (long i = 0; i < (long) documentSnapshot.get("total_spec_title") + 1; i++) {
                            ProductSpecificationFragment.productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + i).toString()));
                            for (long j = 1; j < (long) documentSnapshot.get("spec_title_" + j + "_total_field") + 1; j++) {
                                ProductSpecificationFragment.productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + i + "_field_" + j + "_name").toString(), documentSnapshot.get("spec_title_" + i + "_field_" + j + "_value").toString()));
                            }
                        }
                    } else {
                        productDetailsTabsContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("pro_description").toString());
                    }
                    totalRating.setText((long) documentSnapshot.get("total_rating") + " ratings");

                    for (int k = 1; k < 6; k++) {
                        TextView rating = (TextView) ratingNumContainer.getChildAt(k);
                        rating.setText(String.valueOf((long) documentSnapshot.get((6 - k) + "_star")));
                    }
                    totalRatingFigure.setText(String.valueOf((long) documentSnapshot.get("total_rating")));
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetails.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                tabPosition = tab.getPosition();
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