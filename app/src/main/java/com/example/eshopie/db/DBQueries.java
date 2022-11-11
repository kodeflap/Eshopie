package com.example.eshopie.db;

import static com.example.eshopie.ui.home.HomeFragment.swipeRefreshLayout;
import static com.example.eshopie.ui.product.ProductDetails.addToWishlistButton;
import static com.example.eshopie.ui.wishlist.WishListFragment.wishlistAdapter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshopie.R;
import com.example.eshopie.model.CartItemModel;
import com.example.eshopie.model.CategoryModel;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.model.WishlistModel;
import com.example.eshopie.ui.category.CategoryAdapter;
import com.example.eshopie.ui.home.HomePageAdapter;
import com.example.eshopie.ui.product.ProductDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBQueries {
    /*-------------------------------firebase firestore section--------------------------------*/
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();

    //List for storing data : parent list
    public static List<List<HomePageModel>> homeList = new ArrayList<>();
    //List for storing the all the categories from firebase
    public static List<String> loadedCategoryList = new ArrayList<>();
    //wishlist list
    public static List<String> wishList = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();
    //rating list
    public static List<String> rateIds = new ArrayList<>();
    public static List<Long> rating = new ArrayList<>();
    //
    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartModelList = new ArrayList<>();

    public static void getCategories(RecyclerView categoryRecyclerView, final Context context) {
        categoryModelList.clear();
        firebaseFirestore.collection("categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void setFragmentData(RecyclerView homepageRecyclerView, Context context, final int index, String categoryName) {

        firebaseFirestore.collection("categories")
                .document(categoryName.toUpperCase())
                .collection("top_deals").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                if ((long) documentSnapshot.get("view_type") == 0) {
                                    //Banner slider
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long num_of_banners = (long) documentSnapshot.get("num_of_banners");
                                    for (long i = 1; i < num_of_banners + 1; i++) {
                                        sliderModelList.add(new SliderModel(documentSnapshot.get("banner_" + i).toString()
                                                , documentSnapshot.get("banner_" + i + "_bg").toString()));
                                    }
                                    homeList.get(index).add(new HomePageModel(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    //strip ad banner
                                    homeList.get(index).add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString()
                                            , documentSnapshot.get("bg_color").toString()));
                                } else if ((long) documentSnapshot.get("view_type") == 2) {
                                    //Main recycler view list
                                    List<WishlistModel> viewAllproductsList = new ArrayList<>();
                                    //Horizontal scroll view
                                    List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
                                    long num_of_products = (long) documentSnapshot.get("num_of_products");
                                    for (long i = 1; i < num_of_products; i++) {
                                        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(documentSnapshot.get("pro_id_" + i).toString()
                                                , documentSnapshot.get("pro_img_" + i).toString()
                                                , documentSnapshot.get("pro_title_" + i).toString()
                                                , documentSnapshot.get("pro_subtitle_" + i).toString()
                                                , documentSnapshot.get("pro_price_" + i).toString()));

                                        //view all products section
                                        viewAllproductsList.add(new WishlistModel(documentSnapshot.get("pro_id_" + i).toString()
                                                , documentSnapshot.get("pro_img_" + i).toString()
                                                , documentSnapshot.get("pro_full_title_" + i).toString()
                                                , (long) documentSnapshot.get("free_coupons_" + i)
                                                , documentSnapshot.get("avg_rating_" + i).toString()
                                                , (long) documentSnapshot.get("total_rating_" + i)
                                                , documentSnapshot.get("pro_price_" + i).toString()
                                                , documentSnapshot.get("cutted_price_" + i).toString()
                                                , (boolean) documentSnapshot.get("cod_" + i)));
                                    }
                                    homeList.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_bg").toString(), horizontalProductScrollModalList, viewAllproductsList));

                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    //Grid view
                                    List<HorizontalProductScrollModal> gridLayoutModelList = new ArrayList<>();
                                    long num_of_products = (long) documentSnapshot.get("num_of_products");
                                    for (long i = 1; i < num_of_products; i++) {
                                        gridLayoutModelList.add(new HorizontalProductScrollModal(documentSnapshot.get("pro_id_" + i).toString()
                                                , documentSnapshot.get("pro_img_" + i).toString()
                                                , documentSnapshot.get("pro_title_" + i).toString()
                                                , documentSnapshot.get("pro_subtitle_" + i).toString()
                                                , documentSnapshot.get("pro_price_" + i).toString()));
                                    }
                                    homeList.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_bg").toString(), gridLayoutModelList));

                                }
                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(homeList.get(index));
                            homepageRecyclerView.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            //swipe refresh layout
                            swipeRefreshLayout.setRefreshing(false);

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadWishList(Context context, Dialog dialog, boolean loadProductData) {
        wishList.clear();
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data").document("my_wishlist")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long i = 0; i < (long) task.getResult().get("list_size"); i++) {
                        wishList.add(task.getResult().get("pro_id_" + i).toString());
                        if (wishList.contains(ProductDetails.proId)) {
                            ProductDetails.ALREADY_ADDED_TO_WISHLIST = true;
                            if (addToWishlistButton != null) {
                                ProductDetails.addToWishlistButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.red));
                            }
                        } else {
                            ProductDetails.ALREADY_ADDED_TO_WISHLIST = false;
                            if (addToWishlistButton != null) {
                                ProductDetails.addToWishlistButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#736F6F")));
                            }
                        }
                        //fetch data and added to list
                        if (loadProductData) {
                            wishlistModelList.clear();
                            String productId = task.getResult().get("pro_id_" + i).toString();
                            firebaseFirestore.collection("products").document(productId)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        wishlistModelList.add(new WishlistModel(productId
                                                , task.getResult().get("pro_img_1").toString()
                                                , task.getResult().get("pro_title").toString()
                                                , (long) task.getResult().get("free_coupons")
                                                , task.getResult().get("avg_rating").toString()
                                                , (long) task.getResult().get("total_rating")
                                                , task.getResult().get("pro_price").toString()
                                                , task.getResult().get("cutted_price").toString()
                                                , (boolean) task.getResult().get("cod")));

                                        wishlistAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public static void loadCartList(final Context context,Dialog dialog, boolean loadProductData) {
        cartList.clear();
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data").document("cart")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (long i = 0; i < (long) task.getResult().get("list_size"); i++) {
                                cartList.add(task.getResult().get("pro_id_" + i).toString());
                                if (cartList.contains(ProductDetails.proId)) {
                                    ProductDetails.ALREADY_ADDED_TO_CART = true;
                            if (addToWishlistButton != null) {
                                ProductDetails.addToWishlistButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.red));
                            }
                        } else {
                            ProductDetails.ALREADY_ADDED_TO_CART = false;
                        }
                        //fetch data and added to list
                        if (loadProductData) {
                            cartModelList.clear();
                            String productId = task.getResult().get("pro_id_" + i).toString();
                            firebaseFirestore.collection("products").document(productId)
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        wishlistModelList.add(new WishlistModel(productId
                                                , task.getResult().get("pro_img_1").toString()
                                                , task.getResult().get("pro_title").toString()
                                                , (long) task.getResult().get("free_coupons")
                                                , task.getResult().get("avg_rating").toString()
                                                , (long) task.getResult().get("total_rating")
                                                , task.getResult().get("pro_price").toString()
                                                , task.getResult().get("cutted_price").toString()
                                                , (boolean) task.getResult().get("cod")));

                                        wishlistAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                        dialog.dismiss();
            }
        });
    }

    public static void removeFromWishlist(int index, Context context) {
        wishList.remove(index);
        Map<String, Object> updateWishlist = new HashMap<>();
        for (int i = 0; i < wishList.size(); i++) {
            updateWishlist.put("pro_id_" + i, wishList.get(i));
        }
        updateWishlist.put("list_size", (long) wishList.size());
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data").document("my_wishlist")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (wishlistModelList.size() != 0) {
                        wishlistModelList.remove(index);
                        wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetails.ALREADY_ADDED_TO_WISHLIST = false;
                    Toast.makeText(context, "Removed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    if (addToWishlistButton != null) {
                        addToWishlistButton.setSupportImageTintList(context.getResources().getColorStateList(R.color.red));
                    }
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
//                if (addToWishlistButton != null) {
//                    addToWishlistButton.setEnabled(true);
//                }
                ProductDetails.runningWishlistQuery = false;
            }
        });
    }

    public static void loadRatingList(Context context) {
        rateIds.clear();
        rating.clear();
        firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid()).collection("user_data").document("ratings")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long i = 0; i < (long) task.getResult().get("list_size"); i++) {
                        rateIds.add(task.getResult().get("pro_id_" + i).toString());
                        rating.add((long) task.getResult().get("rating_" + i));

                        if (task.getResult().get("pro_id_" + i).toString().equals(ProductDetails.proId) && ProductDetails.rateNowContainer != null) {
                            ProductDetails.setRating(Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + i))) - 1);
                        }
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void clearData() {
        categoryModelList.clear();
        homeList.clear();
        loadedCategoryList.clear();
        wishList.clear();
        wishlistModelList.clear();
    }
}
