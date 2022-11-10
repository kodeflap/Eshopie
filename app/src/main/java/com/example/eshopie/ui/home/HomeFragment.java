package com.example.eshopie.ui.home;

import static com.example.eshopie.HomeActivity.drawer;
import static com.example.eshopie.db.DBQueries.categoryModelList;
import static com.example.eshopie.db.DBQueries.getCategories;
import static com.example.eshopie.db.DBQueries.homeList;
import static com.example.eshopie.db.DBQueries.loadedCategoryList;
import static com.example.eshopie.db.DBQueries.setFragmentData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.eshopie.R;
import com.example.eshopie.databinding.FragmentHomeBinding;
import com.example.eshopie.model.CategoryModel;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.model.WishlistModel;
import com.example.eshopie.ui.category.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    private RecyclerView categoryRecyclerView;
    private RecyclerView homePageRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<CategoryModel> categoryModelFakeList = new ArrayList<>();
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    private HomePageAdapter adapter;
    private FragmentHomeBinding binding;
    private ImageView noInternetConnection;
    private Button retryBtn;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        noInternetConnection = root.findViewById(R.id.no_internet_connection);
        categoryRecyclerView = root.findViewById(R.id.category_recyclerview);
        homePageRecyclerView = root.findViewById(R.id.home_page_recyclerView);
        retryBtn = root.findViewById(R.id.retry_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        /*-------------------------------------Main Recycler view--------------------------------------*/

        LinearLayoutManager testingLinearLayoutManager = new LinearLayoutManager(getContext());
        testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLinearLayoutManager);

        //category Fake list
        categoryModelFakeList.add(new CategoryModel("null", " "));
        categoryModelFakeList.add(new CategoryModel("", " "));
        categoryModelFakeList.add(new CategoryModel("", " "));
        categoryModelFakeList.add(new CategoryModel("", " "));
        categoryModelFakeList.add(new CategoryModel("", " "));
        categoryModelFakeList.add(new CategoryModel("", " "));
        categoryModelFakeList.add(new CategoryModel("", " "));
        categoryModelFakeList.add(new CategoryModel("", " "));
        categoryModelFakeList.add(new CategoryModel("", " "));

        //home page fake list
        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));

        List<HorizontalProductScrollModal> horizontalProductScrollModalFakeList = new ArrayList<>();
        horizontalProductScrollModalFakeList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalFakeList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalFakeList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalFakeList.add(new HorizontalProductScrollModal("", "", "", "", ""));
        horizontalProductScrollModalFakeList.add(new HorizontalProductScrollModal("", "", "", "", ""));

        homePageModelFakeList.add(new HomePageModel(0, sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1, "", "#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2, "", "#ffffff", horizontalProductScrollModalFakeList, new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3, "", "#ffffff", horizontalProductScrollModalFakeList));

        /*-------------------------- Category horizontal view--------------------------------------*/

        categoryAdapter = new CategoryAdapter(categoryModelFakeList);
        adapter = new HomePageAdapter(homePageModelFakeList);

        /*------------------------network connectivity checking--------------------------------*/
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            //drawer.setDrawerLockMode(drawer.LOCK_MODE_UNLOCKED);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);

            if (categoryModelList.size() == 0) {
                getCategories(categoryRecyclerView, getContext());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelFakeList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);

            /*-------------------------------------Main Recycler view--------------------------------------*/

            if (homeList.size() == 0) {
                loadedCategoryList.add("HOME");
                homeList.add(new ArrayList<HomePageModel>());
                setFragmentData(homePageRecyclerView, getContext(), 0, "home");
            } else {
                adapter = new HomePageAdapter(homeList.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);
        } else {
          //  drawer.setDrawerLockMode(drawer.LOCK_MODE_LOCKED_CLOSED);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_internet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }
        /*---------------------------------------swipe refresh layout------------------------------------*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                reloadPage();
            }
        });
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadPage();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //reloading page method
    private void reloadPage() {
        networkInfo = connectivityManager.getActiveNetworkInfo(); //network checking
        //clearing the lists
        categoryModelList.clear();
        homeList.clear();
        loadedCategoryList.clear();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            //drawer.setDrawerLockMode(drawer.LOCK_MODE_UNLOCKED);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);
            noInternetConnection.setVisibility(View.GONE);
            categoryAdapter = new CategoryAdapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);
            categoryRecyclerView.setAdapter(categoryAdapter);
            homePageRecyclerView.setAdapter(adapter);

            getCategories(categoryRecyclerView, getContext());
            loadedCategoryList.add("HOME");                                       //loads category
            homeList.add(new ArrayList<HomePageModel>());
            setFragmentData(homePageRecyclerView, getContext(), 0, "home");  //set data in fragment

        } else {
            //drawer.setDrawerLockMode(drawer.LOCK_MODE_LOCKED_CLOSED);
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
            Glide.with(getContext()).load(R.drawable.no_internet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}