package com.example.eshopie.ui.product.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eshopie.model.ProductSpecificationModel;
import com.example.eshopie.ui.product.fragment.ProductDescriptionFragment;
import com.example.eshopie.ui.product.fragment.ProductSpecificationFragment;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    private String productDes;
    private String productOtherDetail;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailsAdapter(@NonNull FragmentManager fm, int totalTabs, String productDes, String productOtherDetail, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.totalTabs = totalTabs;
        this.productDes = productDes;
        this.productOtherDetail = productOtherDetail;
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment();
                productDescriptionFragment.body = productDes;
                return productDescriptionFragment;

            case 1:
                ProductSpecificationFragment productSpecificationFragment = new ProductSpecificationFragment();
                productSpecificationFragment.productSpecificationModelList = productSpecificationModelList;
                return productSpecificationFragment;

            case 2:
                ProductDescriptionFragment productDescriptionFragment2 = new ProductDescriptionFragment();
                productDescriptionFragment2.body = productOtherDetail;
                return productDescriptionFragment2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
