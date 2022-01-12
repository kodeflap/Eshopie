package com.example.eshopie.ui.home.gridProduct;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eshopie.R;
import com.example.eshopie.model.HorizontalProductScrollModal;
import com.example.eshopie.ui.product.ProductDetails;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalProductScrollModal> horizontalProductScrollModalList;

    public GridProductLayoutAdapter(List<HorizontalProductScrollModal> horizontalProductScrollModalList) {
        this.horizontalProductScrollModalList = horizontalProductScrollModalList;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(parent.getContext(), ProductDetails.class);
                    parent.getContext().startActivity(productDetailIntent);
                }
            });

            ImageView productImage = view.findViewById(R.id.horizontal_scroll_product_image);
            TextView productTitle = view.findViewById(R.id.horizonyal_scroll_product_title);
            TextView productDescription = view.findViewById(R.id.horizontal_scroll_product_description);
            TextView productPrice = view.findViewById(R.id.horizonyal_scroll_product_title);

            productImage.setImageResource(horizontalProductScrollModalList.get(position).getProductImage());
            productTitle.setText(horizontalProductScrollModalList.get(position).getProductTitle());
            productDescription.setText(horizontalProductScrollModalList.get(position).getProductDescription());
            productPrice.setText(horizontalProductScrollModalList.get(position).getProductPrice());

        }
        else {
            view = convertView;
        }
        return view;
    }
}
