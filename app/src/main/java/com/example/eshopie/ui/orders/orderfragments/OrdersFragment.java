package com.example.eshopie.ui.orders.orderfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eshopie.R;
import com.example.eshopie.model.OrderItemModel;
import com.example.eshopie.ui.orders.adapter.OrdersAdapter;

import java.util.ArrayList;
import java.util.List;


public class OrdersFragment extends Fragment {

    private RecyclerView orderRecyclerView;

    public OrdersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_orders, container, false);
       orderRecyclerView = view.findViewById(R.id.order_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderRecyclerView.setLayoutManager(linearLayoutManager);

        List<OrderItemModel> orderItemModelList = new ArrayList<>();
        orderItemModelList.add(new OrderItemModel(R.drawable.phone,"iphone","Delivered on Monday, 10th Jan 2020",5));
        orderItemModelList.add(new OrderItemModel(R.drawable.mail_green,"iphone","Delivered on Monday, 10th Jan 2020",2));
        orderItemModelList.add(new OrderItemModel(R.drawable.phone,"iphone","Delivered on Monday, 17th Jan 2020",5));
        orderItemModelList.add(new OrderItemModel(R.drawable.phone,"iphone","Cancelled",4));
        orderItemModelList.add(new OrderItemModel(R.drawable.nav_cart,"iphone","Delivered on Monday, 20th Jan 2020",0));
        orderItemModelList.add(new OrderItemModel(R.drawable.phone,"iphone","Cancelled",1));

        OrdersAdapter ordersAdapter = new OrdersAdapter(orderItemModelList);
        orderRecyclerView.setAdapter(ordersAdapter);
        ordersAdapter.notifyDataSetChanged();

       return view;
    }
}