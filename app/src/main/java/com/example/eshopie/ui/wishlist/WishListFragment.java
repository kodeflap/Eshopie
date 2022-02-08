package com.example.eshopie.ui.wishlist;

import static com.example.eshopie.ui.product.ProductDetails.loadingDialog;
import static com.example.eshopie.ui.product.ProductDetails.loadingDialogFun;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshopie.R;
import com.example.eshopie.db.DBQueries;

public class WishListFragment extends Fragment {

    public WishListFragment() {
    }

    private RecyclerView wishlistRecyclerView;
    public static  WishlistAdapter wishlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        ///////////////////////loading dialog///////////////////////////////////
        loadingDialogFun(getContext());

        wishlistRecyclerView = view.findViewById(R.id.wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        if (DBQueries.wishlistModelList.size() == 0) {
            DBQueries.wishList.clear();
            DBQueries.loadWishList(getContext(), loadingDialog, true);
        } else {
            loadingDialog.dismiss();
        }
        wishlistAdapter = new WishlistAdapter(DBQueries.wishlistModelList, true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }
}