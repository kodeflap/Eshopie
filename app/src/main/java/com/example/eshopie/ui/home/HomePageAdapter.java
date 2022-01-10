package com.example.eshopie.ui.home;

import android.graphics.Color;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.eshopie.R;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.ui.home.gridProduct.GridProductLayoutAdapter;
import com.example.eshopie.ui.home.horizontalProductScroll.HorizontalProductScrollAdapter;
import com.example.eshopie.ui.home.slider.SliderAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout, parent, false);
                return new BannerSliderViewHolder(bannerView);
            case HomePageModel.STRIP_AD_BANNER:
                View stripView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdBannerViewHolder(stripView);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalProductViewHolder(horizontalProductView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewHolder(gridProductView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder) holder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                int resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewHolder) holder).setStripAd(resource, color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalLayoutTitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModal> horizontalProductScrollModalList = homePageModelList.get(position).getHorizontalProductScrollModalList();
                ((HorizontalProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollModalList,horizontalLayoutTitle);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutTitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModal> gridProductScrollModalList = homePageModelList.get(position).getHorizontalProductScrollModalList();
                ((GridProductViewHolder) holder).setGridProductLayout(gridProductScrollModalList,gridLayoutTitle);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    /*---------------------------Banner slider----------------------------------------------*/
    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager bannerSliderViewPager;
        private int currentPage = 2;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.home_banner_slider_view_pager);

        }

        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {

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
                        pageLooper(sliderModelList);
                    }
                }
            };

            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerAnimation(sliderModelList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(sliderModelList);
                    stopBannerAnimation();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerAnimation(sliderModelList);
                    }
                    return false;
                }

            });
        }

        private void startBannerAnimation(final List<SliderModel> sliderModelList) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stopBannerAnimation() {
            timer.cancel();
        }

        private void pageLooper(final List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentPage, false);
            }
        }
    }

    /*----------------------------Strip ad layout--------------------------------------------*/
    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_ad_container);
        }

        private void setStripAd(int resource, String color) {
            stripAdImage.setImageResource(resource);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    /*----------------------------horizontal product scroll----------------------------------*/
    private class HorizontalProductViewHolder extends RecyclerView.ViewHolder {
        private TextView horizontalLayoutTitle;
        private Button horizontalLayoutViewAllButton;
        private RecyclerView horizontalRecyclerView;

        public HorizontalProductViewHolder(View itemView) {
            super(itemView);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalLayoutViewAllButton = itemView.findViewById(R.id.horozontal_scroll_view_all);
            horizontalRecyclerView = itemView.findViewById(R.id.horiizontal_scroll_layout_recycler_view);
        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModal> horizontalProductScrollModalList,String title) {

            horizontalLayoutTitle.setText(title);

            if(horizontalProductScrollModalList.size() > 8) {
                horizontalLayoutViewAllButton.setVisibility(View.VISIBLE);
            }
            else {
                horizontalLayoutViewAllButton.setVisibility(View.INVISIBLE);
            }

            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModalList);
            LinearLayoutManager horizontalLinearLayoutManager = new LinearLayoutManager(itemView.getContext());
            horizontalLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(horizontalLinearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }


    /*----------------------------Grid product----------------------------------*/
    private class GridProductViewHolder extends RecyclerView.ViewHolder {
        private TextView gridLayoutTitle;
        private Button gridLayoutViewAll;
        private GridView gridView;

        public GridProductViewHolder(View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAll = itemView.findViewById(R.id.grid_product_layout_view_all);
            gridView = itemView.findViewById(R.id.grid_product_layout_gridview);
        }

        private void setGridProductLayout(List<HorizontalProductScrollModal> horizontalProductScrollModalList,String title) {
            gridLayoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModalList));
        }
    }
}
