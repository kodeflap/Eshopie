package com.example.eshopie.ui.viewall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.eshopie.R;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.model.WishlistModel;
import com.example.eshopie.ui.home.gridProduct.GridProductLayoutAdapter;
import com.example.eshopie.ui.wishlist.WishlistAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView viewAllRecyclerView;
    private GridView viewAllGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar = (Toolbar) findViewById(R.id.v_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Deals of the day");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewAllRecyclerView = findViewById(R.id.view_all_recyclerview);
        viewAllGridView = findViewById(R.id.view_all_gridview);

        int layout_code = getIntent().getIntExtra("layout_code", -1);
        if (layout_code == 0) {
            /*-------------------------------Recycler view-----------------------------------------*/
            viewAllRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            viewAllRecyclerView.setLayoutManager(linearLayoutManager);

            List<WishlistModel> wishlistModelList = new ArrayList<>();
            wishlistModelList.add(new WishlistModel(R.drawable.phone, "iphone", 1, "3", 145, "Rs.7999/-", "Rs.9999", "Cash on delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.phone, "iphone", 0, "3", 145, "Rs.7999/-", "Rs.9999", "Cash on delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.phone, "iphone", 1, "3", 145, "Rs.7999/-", "Rs.9999", "Cash on delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.phone, "iphone", 1, "3", 145, "Rs.7999/-", "Rs.9999", "Cash on delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.phone, "iphone", 4, "3", 145, "Rs.7999/-", "Rs.9999", "Cash on delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.phone, "iphone", 1, "3", 145, "Rs.7999/-", "Rs.9999", "Cash on delivery"));
            wishlistModelList.add(new WishlistModel(R.drawable.phone, "iphone", 1, "3", 145, "Rs.7999/-", "Rs.9999", "Cash on delivery"));

            WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList, true);
            viewAllRecyclerView.setAdapter(wishlistAdapter);
            wishlistAdapter.notifyDataSetChanged();
        } else if (layout_code == 1) {
            /*-------------------------------Grid view-----------------------------------------*/
            viewAllGridView.setVisibility(View.VISIBLE);

            List<HorizontalProductScrollModal> horizontalProductScrollModalList = new ArrayList<>();
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));
            horizontalProductScrollModalList.add(new HorizontalProductScrollModal(R.drawable.phone, "Redmi", "625 processor", "Rs.6999/-"));

            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModalList);
            viewAllGridView.setAdapter(gridProductLayoutAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}