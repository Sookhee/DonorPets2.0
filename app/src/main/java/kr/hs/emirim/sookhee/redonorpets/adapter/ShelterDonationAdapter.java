package kr.hs.emirim.sookhee.redonorpets.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.loopeer.shadow.ShadowView;
import com.squareup.picasso.Picasso;

import java.util.List;

import kr.hs.emirim.sookhee.redonorpets.DonationActivity;
import kr.hs.emirim.sookhee.redonorpets.R;
import kr.hs.emirim.sookhee.redonorpets.model.DonationObjectData;

public class ShelterDonationAdapter extends RecyclerView.Adapter<ShelterDonationAdapter.ViewHolder> {

    private Context context;
    private List<DonationObjectData> donationList;

    public ShelterDonationAdapter(Context context, List donationList) {
        this.context = context;
        this.donationList = donationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shelter_donation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(donationList.get(position));

        DonationObjectData d = donationList.get(position);

        String img = d.getImg();
        holder.tvObjectName.setText(d.getObject());
        Picasso.get().load(img).into(holder.ivObjectImg);

    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvObjectName;
        public ImageView ivObjectImg;
        public ShadowView ivObjectCheck;
        public View pView;

        public ViewHolder(final View itemView) {
            super(itemView);

            pView = itemView;
            tvObjectName = (TextView) itemView.findViewById(R.id.shelterDonationObjectTextView);
            ivObjectImg = (ImageView) itemView.findViewById(R.id.shelterDonationObjectImageView);
            ivObjectCheck = (ShadowView) itemView.findViewById(R.id.shelterDonationObjectCheckImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(getCheckInAdapter(pView) == false){
                        ivObjectCheck.setVisibility(View.VISIBLE);
                        setCheckInAdapter(pView);
//                        ((ShelterProfileActivity)ShelterProfileActivity.mContext).addRealDonationObjectList(getObjectInAdapter(pView), getPointInAdapter(pView), getTagInAdpater(pView));
                    }
                    else{
                        ivObjectCheck.setVisibility(View.INVISIBLE);
                        setCheckInAdapter(pView);
//                        ((ShelterProfileActivity)ShelterProfileActivity.mContext).removeRealDonationObjectList(getObjectInAdapter(pView), getTagInAdpater(pView));
                    }
                }
            });
        }
    }

    private boolean getCheckInAdapter(View view){
        DonationObjectData d = (DonationObjectData) view.getTag();
        return d.getIsDonation();
    }

    private String getObjectInAdapter(View view){
        DonationObjectData d = (DonationObjectData) view.getTag();
        return d.getObject();
    }

    private int getPointInAdapter(View view){
        DonationObjectData d = (DonationObjectData) view.getTag();
        return d.getPoint();
    }

    private int getTagInAdpater(View view){
        DonationObjectData d = (DonationObjectData) view.getTag();
        return donationList.indexOf(view.getTag());
    }

    private void setCheckInAdapter(View view){
        DonationObjectData d = (DonationObjectData) view.getTag();
        if(d.getIsDonation() == true){
            d.setIsDonation(false);
        }
        else{
            d.setIsDonation(true);
        }
    }

}