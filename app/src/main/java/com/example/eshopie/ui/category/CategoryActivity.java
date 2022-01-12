package com.example.eshopie.ui.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.example.eshopie.R;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.ui.home.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar)findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recyclerview);


        /*----------------------------------- Banner Slider--------------------------------------*/

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

        sliderModelList.add(new SliderModel(R.drawable.horizontal_banner_ad,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.home,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.mail_green,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.banner_add,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_slideshow,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.ic_menu_camera,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.horizontal_banner_ad,"#077AE4"));

        sliderModelList.add(new SliderModel(R.drawable.home,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.mail_green,"#077AE4"));
        sliderModelList.add(new SliderModel(R.drawable.banner_add,"#077AE4"));


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

        LinearLayoutManager testingLinearLayoutManager = new LinearLayoutManager(this);
        testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLinearLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();

        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner_add,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(1,R.drawable.horizontal_banner_ad,"#ff0000"));
        homePageModelList.add(new HomePageModel(3,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(2,"Deals of the day",horizontalProductScrollModalList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner_add,"#ff0000"));

        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        else if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}