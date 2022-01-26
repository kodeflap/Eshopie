package com.example.eshopie.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshopie.R;
import com.example.eshopie.databinding.FragmentHomeBinding;
import com.example.eshopie.model.CategoryModel;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.ui.category.CategoryAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    private RecyclerView categoryRecyclerView;
    private RecyclerView homePageRecyclerView;
    private CategoryAdapter categoryAdapter;
    private HomePageAdapter adapter;
    private FragmentHomeBinding binding;
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        categoryRecyclerView = root.findViewById(R.id.category_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        /*-------------------------------firebase firestore section--------------------------------*/
        firebaseFirestore = FirebaseFirestore.getInstance();

        /*-------------------------- Category horizontal view--------------------------------------*/
        categoryModelList = new ArrayList<CategoryModel>();

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);

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
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        /*----------------------------------- Banner Slider--------------------------------------

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.horizontal_banner_ad,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.mail_red,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.arrow_down,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.profile,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_logo,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.phone,"#077AE4"));*/

        /*------------------------horizontal product Layout-------------------------------------------

        List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));*/

        /*-------------------------------------Main Recycler view--------------------------------------*/

        homePageRecyclerView = root.findViewById(R.id.home_page_recyclerView);
        LinearLayoutManager testingLinearLayoutManager = new LinearLayoutManager(getContext());
        testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLinearLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();

        adapter = new HomePageAdapter(homePageModelList);
        homePageRecyclerView.setAdapter(adapter);

        firebaseFirestore.collection("categories")
                .document("HOME")
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
                                    homePageModelList.add(new HomePageModel(0, sliderModelList));
                                } else if ((long) documentSnapshot.get("view_type") == 1) {
                                    //strip ad banner
                                    homePageModelList.add(new HomePageModel(1, documentSnapshot.get("strip_ad_banner").toString()
                                            , documentSnapshot.get("bg_color").toString()));
                                } else if ((long) documentSnapshot.get("view_type") == 2) {
                                    //Horizontal scroll view
                                    List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
                                    long num_of_products = (long) documentSnapshot.get("num_of_products");
                                    for (long i = 1; i < num_of_products; i++) {
                                        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(documentSnapshot.get("pro_id_" + i).toString()
                                                , documentSnapshot.get("pro_img_" + i).toString()
                                                , documentSnapshot.get("pro_title_" + i).toString()
                                                , documentSnapshot.get("pro_subtitle_" + i).toString()
                                                , documentSnapshot.get("pro_price_" + i).toString()));
                                    }
                                    homePageModelList.add(new HomePageModel(2, documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_bg").toString(),horizontalProductScrollModalList));

                                } else if ((long) documentSnapshot.get("view_type") == 3) {

                                }
                            }
                            adapter.notifyDataSetChanged();

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}