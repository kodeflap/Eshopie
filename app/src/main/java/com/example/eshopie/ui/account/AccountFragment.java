package com.example.eshopie.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eshopie.R;
import com.example.eshopie.ui.address.UserAddressActivity;

public class AccountFragment extends Fragment {

    public AccountFragment() {

    }

    public static final int MANAGE_ADDRESS = 1;
    private Button viewAllAddressBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        viewAllAddressBtn = view.findViewById(R.id.address_view_all_btn);
        viewAllAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountIntent = new Intent(getContext(), UserAddressActivity.class);
                accountIntent.putExtra("MODE",MANAGE_ADDRESS);
                getContext().startActivity(accountIntent);
            }
        });
        return view;
    }
}