package com.example.eshopie.ui.rewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshopie.R;
import com.example.eshopie.model.RewardModel;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList;

    public RewardAdapter(List<RewardModel> rewardModelList) {
        this.rewardModelList = rewardModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = rewardModelList.get(position).getTitle();
        String expiryDate = rewardModelList.get(position).getExpiryDate();
        String body = rewardModelList.get(position).getRewardBody();

        holder.setData(title,expiryDate,body);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView rewardTitle;
        private TextView rewardExpiryDate;
        private TextView rewardBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardTitle = itemView.findViewById(R.id.reward_item_title);
            rewardExpiryDate = itemView.findViewById(R.id.reward_item_validity);
            rewardBody = itemView.findViewById(R.id.reward_item_body);
        }
        private void setData(String title, String date, String body) {
            rewardTitle.setText(title);
            rewardExpiryDate.setText(date);
            rewardBody.setText(body);
        }
    }
}
