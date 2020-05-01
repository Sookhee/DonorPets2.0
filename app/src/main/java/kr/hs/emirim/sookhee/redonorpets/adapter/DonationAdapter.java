package kr.hs.emirim.sookhee.redonorpets.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.hs.emirim.sookhee.redonorpets.DonationActivity;
import kr.hs.emirim.sookhee.redonorpets.model.DonationObjectData;
import kr.hs.emirim.sookhee.redonorpets.R;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    private Context context;
    private List<DonationObjectData> donationList;

    public DonationAdapter(Context context, List donationList) {
        this.context = context;
        this.donationList = donationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.donation_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(donationList.get(position));

        DonationObjectData d = donationList.get(position);
        int count = d.getCount();

        holder.tvObjectName.setText(d.getObject());
        holder.tvObjectPoint.setText(d.getPoint() + "p");
        holder.tvCountObject.setText(Integer.toString(d.getCount()));

    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvObjectName;
        public TextView tvObjectPoint;
        public EditText tvCountObject;
        public Button btnAdd;
        public Button btnMinus;
        public View pView;

        public ViewHolder(final View itemView) {
            super(itemView);

            pView = itemView;
            tvObjectName = (TextView) itemView.findViewById(R.id.objectNameTextView);
            tvObjectPoint = (TextView) itemView.findViewById(R.id.pointTextView);
            tvCountObject =  (EditText) itemView.findViewById(R.id.countObjectTextView);
            btnAdd = (Button) itemView.findViewById(R.id.addObjectCountButton);
            btnAdd.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int count = Integer.parseInt(tvCountObject.getText().toString());
                    setCountInAdapter(pView, count+1);
                    ((DonationActivity)DonationActivity.mContext).setTotalPoint(getPointInAdpater(pView), true);
                }
            });
            btnMinus = (Button) itemView.findViewById(R.id.minusObjectCountButton);
            btnMinus.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    int count = Integer.parseInt(tvCountObject.getText().toString());
                    if(count > 1) {
                        setCountInAdapter(pView, count-1);
                        ((DonationActivity)DonationActivity.mContext).setTotalPoint(getPointInAdpater(pView), false);
                    }
                }
            });

        }
    }

    public void setCountInAdapter(View v, int count){
        DonationObjectData d = (DonationObjectData) v.getTag();
        EditText textView = (EditText) v.findViewById(R.id.countObjectTextView);
        textView.setText(Integer.toString(count));
        d.setCount(count);
    }

    public int getPointInAdpater(View v){
        DonationObjectData d = (DonationObjectData) v.getTag();
        return d.getPoint();
    }

}