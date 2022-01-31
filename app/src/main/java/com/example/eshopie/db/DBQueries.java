package com.example.eshopie.db;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.eshopie.model.CategoryModel;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.model.WishlistModel;
import com.example.eshopie.ui.category.CategoryAdapter;
import com.example.eshopie.ui.home.HomePageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBQueries {
    /*-------------------------------firebase firestore section--------------------------------*/
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();

    //List for storing data : parent list
    public static List<List<HomePageModel>> list = new ArrayList<>();
    //List for storing the all the categories from firebase
    public static List<String> loadedCategoryList = new ArrayList<>();

    public static void getCategories(final CategoryAdapter categoryAdapter, final Context context) {
        //categoryModelList = new ArrayList<CategoryModel>();
        firebaseFirestore.collection("categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void setFragmentData(HomePageAdapter adapter, Context context, final int index, String categoryName) {

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
                                    list.get(index).add(new HomePageModel(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    //strip ad banner
                                    list.get(index).add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString()
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
                                        viewAllproductsList.add(new WishlistModel(documentSnapshot.get("pro_img_" + i).toString()
                                                , documentSnapshot.get("pro_full_title_" + i).toString()
                                                , (long) documentSnapshot.get("free_coupons_" + i)
                                                , documentSnapshot.get("avg_rating_" + i).toString()
                                                , (long) documentSnapshot.get("total_rating_" + i)
                                                , documentSnapshot.get("pro_price_" + i).toString()
                                                , documentSnapshot.get("cutted_price_" + i).toString()
                                                , (boolean) documentSnapshot.get("cod_" + i)));
                                    }
                                    list.get(index).add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_bg").toString(), horizontalProductScrollModalList,viewAllproductsList));

                                } else if ((long) documentSnapshot.get("view_type") == 3) {
                                    //Grid view
                                    List<HorizontalProductScrollModal> gridLayout = new ArrayList<>();
                                    long num_of_products = (long) documentSnapshot.get("num_of_products");
                                    for (long i = 1; i < num_of_products; i++) {
                                        gridLayout.add(new HorizontalProductScrollModal(documentSnapshot.get("pro_id_" + i).toString()
                                                , documentSnapshot.get("pro_img_" + i).toString()
                                                , documentSnapshot.get("pro_title_" + i).toString()
                                                , documentSnapshot.get("pro_subtitle_" + i).toString()
                                                , documentSnapshot.get("pro_price_" + i).toString()));
                                    }
                                    list.get(index).add(new HomePageModel(3, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_bg").toString(), gridLayout));

                                }
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
