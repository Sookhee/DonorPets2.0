package kr.hs.emirim.sookhee.redonorpets.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kr.hs.emirim.sookhee.redonorpets.R;
import kr.hs.emirim.sookhee.redonorpets.model.BadgeData;

public class BadgeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBadge;
        TextView tvBadgeTitle;

        MyViewHolder(View view){
            super(view);
            ivBadge = view.findViewById(R.id.BadgeImageView);
            tvBadgeTitle = view.findViewById(R.id.BadgeTitleTextView);
        }
    }

    public ArrayList<BadgeData> badgeDataArrayList;
    public BadgeAdapter(ArrayList<BadgeData> badgeDataArrayList){
        this.badgeDataArrayList = badgeDataArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_badge_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.ivBadge.setImageResource(badgeDataArrayList.get(position).getBadgeId());
        myViewHolder.tvBadgeTitle.setText(badgeDataArrayList.get(position).getBadgeTitle());
    }

    @Override
    public int getItemCount() {
        return badgeDataArrayList.size();
    }
}
