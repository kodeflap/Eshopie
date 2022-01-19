package com.example.eshopie.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    private RecyclerView categoryRecyclerView;
    private RecyclerView homePageRecyclerView;
    private CategoryAdapter categoryAdapter;
    private FragmentHomeBinding binding;

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

        final List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Appliances"));
        categoryModelList.add(new CategoryModel("link","Furniture"));
        categoryModelList.add(new CategoryModel("link","Fashion"));
        categoryModelList.add(new CategoryModel("link","Toys"));
        categoryModelList.add(new CategoryModel("link","Sports"));
        categoryModelList.add(new CategoryModel("link","Wall Arts"));
        categoryModelList.add(new CategoryModel("link","Books"));
        categoryModelList.add(new CategoryModel("link","Shoes"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        /*----------------------------------- Banner Slider--------------------------------------*/

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.horizontal_banner_ad,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.mail_red,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.arrow_down,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.profile,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_logo,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.phone,"#077AE4"));

        /*------------------------horizontal product Layout-------------------------------------------*/

        List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone,"Redmi","625 processor","Rs.6999/-"));


        /*-------------------------------------Main Recycler view--------------------------------------*/

        homePageRecyclerView = root.findViewById(R.id.home_page_recyclerView);
        LinearLayoutManager testingLinearLayoutManager = new LinearLayoutManager(getContext());
        testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLinearLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();

        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner_add,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(1,R.drawable.horizontal_banner_ad,"#ff0000"));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner_add,"#ff0000"));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner_add,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(1,R.drawable.horizontal_banner_ad,"#ff0000"));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner_add,"#ff0000"));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        homePageRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}