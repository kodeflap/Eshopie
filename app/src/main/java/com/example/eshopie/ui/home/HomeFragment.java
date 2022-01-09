package com.example.eshopie.ui.home;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.eshopie.R;
import com.example.eshopie.databinding.FragmentHomeBinding;
import com.example.eshopie.model.CategoryModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.ui.home.category.CategoryAdapter;
import com.example.eshopie.ui.home.gridProduct.GridProductLayoutAdapter;
import com.example.eshopie.ui.home.horizontalProductScroll.HorizontalProductScrollAdapter;
import com.example.eshopie.ui.home.slider.SliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    /////////////Banner Slider
    private ViewPager bannerSliderViewPager;
    private List<SliderModel> sliderModelList;
    private int currentPage = 2;
    private Timer timer;
    final private long DELAY_TIME = 3000;
    final private long PERIOD_TIME = 3000;
    /////////// Banner Slider


    ////////// Strip ad
    private ImageView stripAdImage;
    private ConstraintLayout stripAdContainer;
    ///////// strip ad

    ////////horizontal product Layout
    private TextView horizontalLayoutTitle;
    private Button horizontalLayoutViewAllButton;
    private RecyclerView horizontalRecyclerView;
    ///////horizontal product layout

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

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
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

        /////////// Banner Slider

        bannerSliderViewPager = root.findViewById(R.id.home_banner_slider_view_pager);

        sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.ic_menu_gallery,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.home,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.mail_green,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.mail_red,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_slideshow,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_gallery,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.home,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.mail_green,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.mail_red,"#077AE4"));


        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        bannerSliderViewPager.setCurrentItem(currentPage);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    pageLooper();
                }
            }
        };

        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

        startBannerAnimation();

        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pageLooper();
                stopBannerAnimation();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    startBannerAnimation();
                }
                return false;
            }

        });
        ////////// Banner slider

        ////////// Strip ad
        stripAdImage = root.findViewById(R.id.strip_ad_image);
        stripAdContainer = root.findViewById(R.id.strip_ad_container);

        stripAdImage.setImageResource(R.drawable.nav_cart);
        stripAdContainer.setBackgroundColor(Color.parseColor("#000000"));
        ////////// Strip ad

        ////////horizontal product Layout
        horizontalLayoutTitle = root.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalLayoutViewAllButton = root.findViewById(R.id.horozontal_scroll_view_all);
        horizontalRecyclerView = root.findViewById(R.id.horiizontal_scroll_layout_recycler_view);

        List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.nav_signout,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.nav_cart,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.home,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.mail_green,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.ic_menu_gallery,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.add_circle_outline,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.error_icon,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.close_button,"Redmi","625 processor","Rs.6999/-"));
        horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.ic_menu_camera,"Redmi","625 processor","Rs.6999/-"));

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModalList);
        LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(root.getContext());
        horizontalLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(horizontalLinearLayoutManager);

        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

        ////////horizontal product Layout

        ////////Grid product Layout

        TextView gridLayoutTitle = root.findViewById(R.id.grid_product_layout_title);
        Button gridLayoutViewAll = root.findViewById(R.id.grid_product_layout_view_all);
        GridView gridView = root.findViewById(R.id.grid_product_layout_gridview);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModalList));
        ////////Grid product Layout

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    ////////////////Banner Slider
    private void startBannerAnimation() {
        Handler handler = new Handler();
        Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage >= sliderModelList.size()) {
                    currentPage = 1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++,true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }

    private void stopBannerAnimation() {
        timer.cancel();
    }

    private  void pageLooper() {
        if(currentPage == sliderModelList.size()-2) {
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
        if(currentPage == 1) {
            currentPage = sliderModelList.size() - 3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
    }

    ///////////////Banner Slider
}