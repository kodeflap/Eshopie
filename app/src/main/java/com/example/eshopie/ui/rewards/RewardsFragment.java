package com.example.eshopie.ui.rewards;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eshopie.R;
import com.example.eshopie.model.RewardModel;

import java.util.ArrayList;
import java.util.List;

public class RewardsFragment extends Fragment {

    public RewardsFragment() {

    }

    private RecyclerView rewardRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        rewardRecyclerView = view.findViewById(R.id.reward_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback","Till 2nd feb 2022","get 50 % off on purchase above Rs.500/-"));
        rewardModelList.add(new RewardModel("Discount","Till 2nd feb 2022","get 50 % off on purchase above Rs.500/-"));
        rewardModelList.add(new RewardModel("Buy one get one free","Till 2nd feb 2022","get 50 % off on purchase above Rs.500/-"));
        rewardModelList.add(new RewardModel("Cashback","Till 2nd feb 2022","get 50 % off on purchase above Rs.500/-"));
        rewardModelList.add(new RewardModel("discount","Till 2nd feb 2022","get 50 % off on purchase above Rs.500/-"));

        RewardAdapter rewardAdapter = new RewardAdapter(rewardModelList,false);
        rewardRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.notifyDataSetChanged();

        return view;
    }
}