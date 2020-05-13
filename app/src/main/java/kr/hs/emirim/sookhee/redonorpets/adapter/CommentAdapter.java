package kr.hs.emirim.sookhee.redonorpets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

import kr.hs.emirim.sookhee.redonorpets.R;
import kr.hs.emirim.sookhee.redonorpets.model.CommentData;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder> {

    private Context mCtx;
    private HashMap<String, CommentData> mData;

    public CommentAdapter(Context mCtx) {
        this.mCtx = mCtx;
        mData = new HashMap<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.comment_item, parent, false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        CommentData chat = (CommentData) mData.values().toArray()[position];

        String img = chat.getImg();

        holder.tvName.setText(chat.getName());
        holder.tvContent.setText(chat.getContent());
        Picasso.get().load(img).into(holder.ivProfile);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvContent;
        ImageView ivProfile;
        public View pView;

        public CustomViewHolder(View itemView) {
            super(itemView);

            this.pView = itemView;
            tvName = itemView.findViewById(R.id.commentNameTextView);
            tvContent = itemView.findViewById(R.id.commentContentTextView);
            ivProfile = itemView.findViewById(R.id.commentProfileImageView);

        }

    }


    public void addDataAndUpdate(String key, CommentData p){
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