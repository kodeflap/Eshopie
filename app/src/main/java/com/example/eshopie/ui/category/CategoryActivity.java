package com.example.eshopie.ui.category;

import static com.example.eshopie.db.DBQueries.list;
import static com.example.eshopie.db.DBQueries.loadedCategoryList;
import static com.example.eshopie.db.DBQueries.setFragmentData;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshopie.R;
import com.example.eshopie.model.HomePageModel;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.SliderModel;
import com.example.eshopie.model.WishlistModel;
import com.example.eshopie.ui.home.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private HomePageAdapter adapter;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

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


        categoryRecyclerView = findViewById(R.id.category_recyclerview);

        /*-------------------------------------Main Recycler view--------------------------------------*/

        LinearLayoutManager testingLinearLayoutManager = new LinearLayoutManager(this);
        testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLinearLayoutManager);

        adapter = new HomePageAdapter(homePageModelFakeList);
        categoryRecyclerView.setAdapter(adapter);

        int listPos = 0;
        for (int i = 0; i < loadedCategoryList.size(); i++) {
            if (loadedCategoryList.get(i).equals(title.toUpperCase())) {
                listPos = i;
            }
        }
        if (listPos == 0) {
            loadedCategoryList.add(title.toUpperCase());
            list.add(new ArrayList<HomePageModel>());
            setFragmentData(categoryRecyclerView, this, loadedCategoryList.size() - 1,title);
        } else {
            adapter = new HomePageAdapter(list.get(listPos));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}