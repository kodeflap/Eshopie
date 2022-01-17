package com.example.eshopie.ui.delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.eshopie.R;
import com.example.eshopie.model.CartItemModel;
import com.example.eshopie.ui.cart.MyCartFragment;
import com.example.eshopie.ui.cart.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddAddressBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Toolbar toolbar = (Toolbar)findViewById(R.id.d_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        /*-----------------cart section-------------------------*/
        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);
        changeOrAddAddressBtn = findViewById(R.id.change_or_add_address_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone,"iphone",2,"Rs.6999/-","RS.8999/-",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone,"iphone",0,"Rs.6999/-","RS.8999/-",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.phone,"iphone",2,"Rs.6999/-","RS.8999/-",1,2,0));
        cartItemModelList.add(new CartItemModel(1,"price(3 items","Rs/6999/-","Free","Rs.70000/-","Rs.999/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddAddressBtn.setVisibility(View.VISIBLE);
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