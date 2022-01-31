package com.example.eshopie.ui.home;


import static com.example.eshopie.db.DBQueries.categoryModelList;
import static com.example.eshopie.db.DBQueries.getCategories;
import static com.example.eshopie.db.DBQueries.list;
import static com.example.eshopie.db.DBQueries.loadedCategoryList;
import static com.example.eshopie.db.DBQueries.setFragmentData;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.eshopie.R;
import com.example.eshopie.databinding.FragmentHomeBinding;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.ui.category.CategoryAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    private RecyclerView categoryRecyclerView;
    private RecyclerView homePageRecyclerView;
    private CategoryAdapter categoryAdapter;
    private HomePageAdapter adapter;
    private FragmentHomeBinding binding;
    private ImageView noInternetConnection;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        noInternetConnection = root.findViewById(R.id.no_internet_connection);

        /*------------------------network connectivity checking--------------------------------*/
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected() == true) {
            noInternetConnection.setVisibility(View.GONE);

            categoryRecyclerView = root.findViewById(R.id.category_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(linearLayoutManager);

            /*-------------------------- Category horizontal view--------------------------------------*/

            categoryAdapter = new CategoryAdapter(categoryModelList);
            categoryRecyclerView.setAdapter(categoryAdapter);

            if (categoryModelList.size() == 0) {
                getCategories(categoryAdapter, getContext());
            } else {
                categoryAdapter.notifyDataSetChanged();
            }

            /*-------------------------------------Main Recycler view--------------------------------------*/

            homePageRecyclerView = root.findViewById(R.id.home_page_recyclerView);
            LinearLayoutManager testingLinearLayoutManager = new LinearLayoutManager(getContext());
            testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homePageRecyclerView.setLayoutManager(testingLinearLayoutManager);

            if (list.size() == 0) {
                loadedCategoryList.add("HOME");
                list.add(new ArrayList<HomePageModel>());
                adapter = new HomePageAdapter(list.get(0));
                setFragmentData(adapter, getContext(), 0, "home");
            } else {
                adapter = new HomePageAdapter(list.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

        } else {
            Glide.with(this).load(R.drawable.no_internet).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}