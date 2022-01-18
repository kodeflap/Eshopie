package com.example.eshopie.ui.address;

import static com.example.eshopie.ui.delivery.DeliveryActivity.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.eshopie.R;
import com.example.eshopie.model.AddressModel;
import com.example.eshopie.ui.address.adapter.AddressAdapter;
import com.example.eshopie.ui.delivery.DeliveryActivity;

import java.util.ArrayList;
import java.util.List;

public class UserAddressActivity extends AppCompatActivity {

    private RecyclerView userAddressRecyclerView;
    private static AddressAdapter addressAdapter;
    private Button deliverHearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address);

        Toolbar toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deliverHearBtn = findViewById(R.id.deliver_here_btn);
        userAddressRecyclerView = findViewById(R.id.user_address_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userAddressRecyclerView.setLayoutManager(linearLayoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("abc","abc address","123456",true));
        addressModelList.add(new AddressModel("abc sda","abc address2","123456",false));
        addressModelList.add(new AddressModel("DA abc","abc address","123456",false));
        addressModelList.add(new AddressModel("ADAE abc","abc address","123456",false));
        addressModelList.add(new AddressModel("SDFS abc","abc address","123456",false));

        int mode =getIntent().getIntExtra("MODE",-1);
        if (mode == SELECT_ADDRESS) {
            deliverHearBtn.setVisibility(View.VISIBLE);
        }
        else {
            deliverHearBtn.setVisibility(View.GONE);
        }
        addressAdapter = new AddressAdapter(addressModelList,mode);
        userAddressRecyclerView.setAdapter(addressAdapter);
        //disable animation
        ((SimpleItemAnimator)userAddressRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();//refreshes full list
    }
    /*------------------------refreshing layout function------------------------*/
    public static void refreshItem(int deselect, int select) {
        addressAdapter.notifyItemChanged(deselect);//refreshes item
        addressAdapter.notifyItemChanged(select);
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