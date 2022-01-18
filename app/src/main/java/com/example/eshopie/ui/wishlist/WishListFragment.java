package com.example.eshopie.ui.wishlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eshopie.R;
import com.example.eshopie.model.WishlistModel;

import java.util.ArrayList;
import java.util.List;

public class WishListFragment extends Fragment {

    public WishListFragment() {
    }

    private RecyclerView wishlistRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_wish_list, container, false);

        wishlistRecyclerView = view.findViewById(R.id.wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();
        wishlistModelList.add(new WishlistModel(R.drawable.phone,"iphone",1,"3",145,"Rs.7999/-","Rs.9999","Cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.phone,"iphone",0,"3",145,"Rs.7999/-","Rs.9999","Cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.phone,"iphone",1,"3",145,"Rs.7999/-","Rs.9999","Cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.phone,"iphone",1,"3",145,"Rs.7999/-","Rs.9999","Cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.phone,"iphone",4,"3",145,"Rs.7999/-","Rs.9999","Cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.phone,"iphone",1,"3",145,"Rs.7999/-","Rs.9999","Cash on delivery"));
        wishlistModelList.add(new WishlistModel(R.drawable.phone,"iphone",1,"3",145,"Rs.7999/-","Rs.9999","Cash on delivery"));

        WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList,true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }
}