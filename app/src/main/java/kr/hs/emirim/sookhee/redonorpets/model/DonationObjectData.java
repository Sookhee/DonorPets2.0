package kr.hs.emirim.sookhee.redonorpets.model;

import java.io.Serializable;

public class DonationObjectData implements Serializable {
    private String object;
    private boolean isDonation;
    private int point;
    private int count;
    private String img;

    public DonationObjectData() {
    }

    public DonationObjectData(String object, int point, String img) {
        this.object = object;
        this.isDonation = false;
        this.point = point;
        this.count = 1;
        this.img = img;
    }

    public DonationObjectData(String object, int point, boolean isDonation) {
        this.object = object;
        this.isDonation = isDonation;
        this.point = point;
        this.count = 1;
        this.img = null;
    }


    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
