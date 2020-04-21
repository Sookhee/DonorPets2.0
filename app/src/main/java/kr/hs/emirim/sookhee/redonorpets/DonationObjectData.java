package kr.hs.emirim.sookhee.redonorpets;

import java.io.Serializable;

import kotlin.properties.ReadOnlyProperty;

public class DonationObjectData implements Serializable {
    private String object;
    private boolean isDonation;
    private int point;
    private int count;

    public DonationObjectData() {
    }

    public DonationObjectData(String object, int point) {
        this.object = object;
        this.isDonation = false;
        this.point = point;
        this.count = 1;
    }

    public DonationObjectData(String object, int point, boolean isDonation) {
        this.object = object;
        this.isDonation = isDonation;
        this.point = point;
        this.count = 1;
    }


    public String getObject() {
        return object;
    }

    public void getObject(String object) {
        this.object = object;
    }

    public boolean getIsDonation() {
        return isDonation;
    }

    public void setIsDonation(boolean isDonation) {
        this.isDonation = isDonation;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public boolean equals(DonationObjectData d){
        if(this == d)
            return true;
        return false;
    }

}
