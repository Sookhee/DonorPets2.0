package kr.hs.emirim.sookhee.redonorpets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.CustomViewHolder> {

    private Context mCtx;
    private HashMap<String, StoryData> mData;
    private ArrayList<String> shelterPosition = new ArrayList<>();

    public StoryAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mData = new HashMap<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.story_item, parent, false);


        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        StoryData story = (StoryData) mData.values().toArray()[position];

        String img = story.getMainImg();
        shelterPosition.add(story.getShelterPosition());

        holder.tvTitle.setText(story.getTitle());
        holder.tvShelter.setText(story.getShelterName());
        Picasso.get().load(img).into(holder.ivMainImg);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvShelter;
        ImageView ivMainImg;


        public CustomViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.storyTitle);
            tvShelter = itemView.findViewById(R.id.storyShelter);
            ivMainImg = itemView.findViewById(R.id.storyMainImg);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    Intent intent;
                    intent = new Intent(v.getContext(), StoryDetailActivity.class);
                    intent.putExtra("storyPosition", String.valueOf(getAdapterPosition()));
                    intent.putExtra("shelterPosition", shelterPosition.get(getAdapterPosition()));
                    v.getContext().startActivity(intent);
                }
            });
        }

    }


    public void addDataAndUpdate(String key, StoryData p){
        mData.put(key, p);
        notifyDataSetChanged();
    }

    public void deleteDataAndUpdate(String key){
        mData.remove(key);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
        notifyDataSetChanged();

    }

}