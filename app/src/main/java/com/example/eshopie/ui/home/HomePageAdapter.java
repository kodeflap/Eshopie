package com.example.eshopie.ui.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eshopie.R;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.model.WishlistModel;
import com.example.eshopie.ui.home.horizontalProductScroll.HorizontalProductScrollAdapter;
import com.example.eshopie.ui.home.slider.SliderAdapter;
import com.example.eshopie.ui.product.ProductDetails;
import com.example.eshopie.ui.viewall.ViewAllActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPos = -1;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
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
                String resource = homePageModelList.get(position).getResource();
                String color = homePageModelList.get(position).getBackgroundColor();
                ((StripAdBannerViewHolder) holder).setStripAd(resource, color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String layout_bg = homePageModelList.get(position).getBackgroundColor();
                String horizontalLayoutTitle = homePageModelList.get(position).getTitle();
                List<WishlistModel> viewAllProductList = homePageModelList.get(position).getViewAllProductList();
                List<HorizontalProductScrollModal> horizontalProductScrollModalList = homePageModelList.get(position).getHorizontalProductScrollModalList();
                ((HorizontalProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollModalList, horizontalLayoutTitle, layout_bg, viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridColor = homePageModelList.get(position).getBackgroundColor();
                String gridLayoutTitle = homePageModelList.get(position).getTitle();
                List<HorizontalProductScrollModal> gridProductScrollModalList = homePageModelList.get(position).getHorizontalProductScrollModalList();
                ((GridProductViewHolder) holder).setGridProductLayout(gridProductScrollModalList, gridLayoutTitle, gridColor);
                break;
            default:
                return;
        }
        //animation
        if (lastPos < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    /*---------------------------Banner slider----------------------------------------------*/
    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private ViewPager bannerSliderViewPager;
        private int currentPage;
        private Timer timer;
        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.home_banner_slider_view_pager);

        }

        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {

            currentPage = 2;
            if (timer != null) {
                timer.cancel();
            }
            //Infinite scrolling effect in banner slider logic
            arrangedList = new ArrayList<>();
            for (int i = 0; i < sliderModelList.size(); i++) {
                arrangedList.add(i, sliderModelList.get(i));
            }
            //add new item
            arrangedList.add(0, sliderModelList.get(sliderModelList.size() - 2));
            arrangedList.add(1, sliderModelList.get(sliderModelList.size() - 1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
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
                        pageLooper(arrangedList);
                    }
                }
            };

            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);

            startBannerAnimation(arrangedList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangedList);
                    stopBannerAnimation();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerAnimation(arrangedList);
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

        private void setStripAd(String resource, String color) {
            /*---------------------------glide-----------------------------*/
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.default_img)).into(stripAdImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    /*----------------------------horizontal product scroll----------------------------------*/
    private class HorizontalProductViewHolder extends RecyclerView.ViewHolder {
        private TextView horizontalLayoutTitle;
        private Button horizontalLayoutViewAllButton;
        private RecyclerView horizontalRecyclerView;
        private ConstraintLayout container;

        public HorizontalProductViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.hs_container);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalLayoutViewAllButton = itemView.findViewById(R.id.horozontal_scroll_view_all);
            horizontalRecyclerView = itemView.findViewById(R.id.horiizontal_scroll_layout_recycler_view);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);

        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModal> horizontalProductScrollModalList, String title, String color, List<WishlistModel> viewAllProductList) {

            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalLayoutTitle.setText(title);

            if (horizontalProductScrollModalList.size() > 6) {
                horizontalLayoutViewAllButton.setVisibility(View.VISIBLE);
                horizontalLayoutViewAllButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.wishlistModelList = viewAllProductList;
                        Intent horizontalViewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        horizontalViewAllIntent.putExtra("layout_code", 0);
                        horizontalViewAllIntent.putExtra("title", title);
                        itemView.getContext().startActivity(horizontalViewAllIntent);
                    }
                });
            } else {
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
        private Button gridLayoutViewAllBtn;
        private GridLayout gridProductLayout;
        private ConstraintLayout container;

        public GridProductViewHolder(View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_view_all);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);
            container = itemView.findViewById(R.id.grid_container);
        }

        private void setGridProductLayout(List<HorizontalProductScrollModal> horizontalProductScrollModalList, String title, String color) {
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);

            for (int i = 0; i < 4; i++) {
                ImageView productImage = gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_image);
                TextView productTitle = gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_title);
                TextView productDescription = gridProductLayout.getChildAt(i).findViewById(R.id.hs_product_description);
                TextView productPrice = gridProductLayout.getChildAt(i).findViewById(R.id.hs__product_price);
                gridProductLayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));

                //Glide
                Glide.with(itemView.getContext()).load(horizontalProductScrollModalList.get(i).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.default_img)).into(productImage);
                productTitle.setText(horizontalProductScrollModalList.get(i).getProductTitle());
                productDescription.setText(horizontalProductScrollModalList.get(i).getProductDescription());
                productPrice.setText("Rs." + horizontalProductScrollModalList.get(i).getProductPrice());
                gridProductLayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));

                if (!title.equals("")) {
                    int finalI = i;
                    gridProductLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent productDetailIntent = new Intent(itemView.getContext(), ProductDetails.class);
                            productDetailIntent.putExtra("pro_id", horizontalProductScrollModalList.get(finalI).getProductId());
                            itemView.getContext().startActivity(productDetailIntent);
                        }
                    });
                }
            }
            if (!title.equals("")) {
                gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.horizontalProductScrollModalList = horizontalProductScrollModalList;

                        Intent gridViewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        gridViewAllIntent.putExtra("layout_code", 1);
                        gridViewAllIntent.putExtra("title", title);
                        itemView.getContext().startActivity(gridViewAllIntent);
                    }
                });
            }
        }
    }
}
