package com.example.eshopie.ui.product.fragment;

import static com.example.eshopie.ui.product.ProductDetails.productDescription;
import static com.example.eshopie.ui.product.ProductDetails.productOtherDetails;
import static com.example.eshopie.ui.product.ProductDetails.tabPosition;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eshopie.R;

public class ProductDescriptionFragment extends Fragment {

    public ProductDescriptionFragment() {
        // Required empty public constructor
    }

    private TextView descriptionBody;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        descriptionBody = view.findViewById(R.id.tv_product_description);

        if (tabPosition == 0) {
            descriptionBody.setText(productDescription);
        } else {
           descriptionBody.setText(productOtherDetails);
        }
        return view;
    }
}