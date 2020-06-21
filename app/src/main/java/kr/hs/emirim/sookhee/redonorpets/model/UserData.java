package kr.hs.emirim.sookhee.redonorpets.model;

public class UserData {
    private String id;
    private String area;
    private String email;
    private String profileImg;
    private int donationCount;
    private int point;

    public UserData(){}
    public UserData(String id, String email, String area){
        this.id = id;
        this.email = email;
        this.area = area;
        this.profileImg = "https://firebasestorage.googleapis.com/v0/b/donorpetsver2.appspot.com/o/default-profile.png?alt=media&token=9913d8d6-552d-49d2-82a0-948e2bb85a27";
        this.donationCount = 0;
        this.point = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDonationCount() {
        return donationCount;
    }

    public void setDonationCount(int donationCount) {
        this.donationCount = donationCount;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
