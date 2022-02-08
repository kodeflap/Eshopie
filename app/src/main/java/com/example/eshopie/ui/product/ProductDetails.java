package com.example.eshopie.ui.product;

import static com.example.eshopie.HomeActivity.showCart;
import static com.example.eshopie.HomeActivity.signInDialog;
import static com.example.eshopie.HomeActivity.signInDialogFun;
import static com.example.eshopie.db.DBQueries.loadRatingList;
import static com.example.eshopie.db.DBQueries.loadWishList;
import static com.example.eshopie.db.DBQueries.wishList;
import static com.example.eshopie.db.DBQueries.wishlistModelList;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.eshopie.HomeActivity;
import com.example.eshopie.R;
import com.example.eshopie.db.DBQueries;
import com.example.eshopie.model.ProductSpecificationModel;
import com.example.eshopie.model.RewardModel;
import com.example.eshopie.model.WishlistModel;
import com.example.eshopie.ui.delivery.DeliveryActivity;
import com.example.eshopie.ui.product.adapter.ProductDetailsAdapter;
import com.example.eshopie.ui.product.adapter.ProductImageAdapter;
import com.example.eshopie.ui.rewards.RewardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {

    public static boolean runningWishlistQuery = false;

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
    private LinearLayout couponRedeemLayout;

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

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    private TextView productOnlyDescriptionBody;
    private String productDescription;
    private String productOtherDetails;

    /*---------------------progress dialog variable-------------------------*/
    public static Dialog loadingDialog;

    /*---------------------rating layout variable-------------------------*/
    public static RatingBar rateNowContainer;
    private TextView totalRating;
    private TextView totalRatingFigure;
    private LinearLayout ratingNumContainer;
    private LinearLayout ratingProgressBarContainer;
    private TextView avgRating;

    /*---------------------buy now variable------------------------------*/
    private Button buyNowBtn;

    /*---------------------add to cart variable------------------------------*/
    private LinearLayout addToCartBtn;

    public static FloatingActionButton addToWishlistButton;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static boolean ALREADY_ADDED_TO_CART = false;

    /*-------------------- Firebase Firestore------------------------------*/
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;
    public static String proId;
    private DocumentSnapshot documentSnapshot;


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
        couponRedeemLayout = findViewById(R.id.cart_coupen_redemption_layout);
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
        rateNowContainer = findViewById(R.id.rating_bar_container);
        totalRating = findViewById(R.id.total_ratings);
        ratingNumContainer = findViewById(R.id.rating_numbers_container);
        totalRatingFigure = findViewById(R.id.total_rating_figure);
        ratingProgressBarContainer = findViewById(R.id.rating_progressbar_container);
        avgRating = findViewById(R.id.average_rating);

        //add to cart
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        loadingDialogFun(ProductDetails.this);

        //firebase
        firebaseFirestore = FirebaseFirestore.getInstance();

        List<String> proImages = new ArrayList<>();
        proId = getIntent().getStringExtra("pro_id");
        firebaseFirestore.collection("products").document("eoptcV23rvZiu1iPy38O")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();
                    for (long i = 1; i < (long) documentSnapshot.get("num_of_pro_img") + 1; i++) {
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
                        productOtherDetails = documentSnapshot.get("pro_other_details").toString();

                        for (long i = 1; i < (long) documentSnapshot.get("total_spec_title") + 1; i++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + i).toString()));
                            for (long j = 1; j < (long) documentSnapshot.get("spec_title_" + i + "_total_fields") + 1; j++) {
                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + i + "_field_" + j + "_name").toString(), documentSnapshot.get("spec_title_" + i + "_field_" + j + "_value").toString()));
                            }
                        }
                    } else {
                        productDetailsTabsContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("pro_description").toString());
                    }
                    totalRating.setText((long) documentSnapshot.get("total_rating") + " ratings");

                    for (int k = 0; k < 5; k++) {
                        TextView rating = (TextView) ratingNumContainer.getChildAt(k);
                        rating.setText(String.valueOf((long) documentSnapshot.get((5 - k) + "_star")));

                        //rating Progress
                        ProgressBar progressBar = (ProgressBar) ratingProgressBarContainer.getChildAt(k);
                        int maxProgress = Integer.parseInt(String.valueOf((long) documentSnapshot.get("total_rating")));
                        progressBar.setMax(maxProgress);
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long) documentSnapshot.get((5 - k) + "_star"))));
                    }
                    totalRatingFigure.setText(String.valueOf((long) documentSnapshot.get("total_rating")));
                    avgRating.setText(documentSnapshot.get("avg_rating").toString());
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));
                    if (currentUser != null) {
                        if (DBQueries.rating.size() == 0) {
                            loadRatingList(ProductDetails.this);
                        }
                        if (wishList.size() == 0) {
                            loadWishList(ProductDetails.this, loadingDialog, false);
                        } else {
                            loadingDialog.dismiss();
                        }
                    } else {
                        loadingDialog.dismiss();
                    }
                    if (wishList.contains(proId)) {
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.red));
                    } else {
                        ALREADY_ADDED_TO_WISHLIST = false;
                        addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#736F6F")));
                    }
                } else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetails.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);



/////////////////////add to wishlist button////////////////////////////////////////////
        addToWishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    //addToWishlistButton.setEnabled(false);
                    if (!runningWishlistQuery) {
                        runningWishlistQuery = true;
                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = wishList.indexOf(proId);
                            DBQueries.removeFromWishlist(index, ProductDetails.this);
                            addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#736F6F")));
                        } else {
                            addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.red));
                            Map<String, Object> addProId = new HashMap<>();
                            addProId.put("pro_id_" + String.valueOf(wishList.size()), proId);
                            firebaseFirestore.collection("users").document(currentUser.getUid()).collection("user_data").document("my_wishlist")
                                    .update(addProId).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        if (wishlistModelList.size() != 0) {
                                            wishlistModelList.add(new WishlistModel(proId
                                                    , documentSnapshot.get("pro_img_1").toString()
                                                    , documentSnapshot.get("pro_title").toString()
                                                    , (long) documentSnapshot.get("free_coupons")
                                                    , documentSnapshot.get("avg_rating").toString()
                                                    , (long) documentSnapshot.get("total_rating")
                                                    , documentSnapshot.get("pro_price").toString()
                                                    , documentSnapshot.get("cutted_price").toString()
                                                    , (boolean) documentSnapshot.get("cod")));
                                        }
                                        Map<String, Object> updateListSize = new HashMap<>();
                                        updateListSize.put("list_size", (long) (wishList.size() + 1));
                                        firebaseFirestore.collection("users").document(currentUser.getUid()).collection("user_data").document("my_wishlist")
                                                .update(updateListSize).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    ALREADY_ADDED_TO_WISHLIST = true;
                                                    addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.red));
                                                    wishList.add(proId);
                                                    Toast.makeText(ProductDetails.this, "Product Added to wishlist", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#736F6F")));
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(ProductDetails.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                                //   addToWishlistButton.setEnabled(true);
                                                runningWishlistQuery = true;
                                            }
                                        });
                                    } else {
                                        //  addToWishlistButton.setEnabled(true);
                                        runningWishlistQuery = false;
                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetails.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });

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

        /*-----------------------------rating-------------------*/

        /*-----------------------------buy now-------------------*/
        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent buyNowIntent = new Intent(ProductDetails.this, DeliveryActivity.class);
                    startActivity(buyNowIntent);
                }
            }
        });

        /*---------------------add to cart------------------------------*/
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    ///todo:add to cart
                }
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
        ///////////////////////sign In dialog///////////////////////////////////
        signInDialogFun(ProductDetails.this);
    }

    ///////////////////////loading dialog///////////////////////////////////
    public static void loadingDialogFun(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.slider_bg));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
    }

    ///////////////////////Rating//////////////////////////////////
    public static void setRating(int starPosition) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            couponRedeemLayout.setVisibility(View.GONE);
        } else {
            couponRedeemLayout.setVisibility(View.VISIBLE);
        }
        if (currentUser != null) {
            if (DBQueries.rating.size() == 0) {
                loadRatingList(ProductDetails.this);
            }
            if (wishList.size() == 0) {
                loadWishList(ProductDetails.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
        } else {
            loadingDialog.dismiss();
        }
        if (wishList.contains(proId)) {
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishlistButton.setSupportImageTintList(getResources().getColorStateList(R.color.red));
        } else {
            ALREADY_ADDED_TO_WISHLIST = false;
            addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#736F6F")));

        }
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
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetails.this, HomeActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}