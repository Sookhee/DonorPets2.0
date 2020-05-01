package kr.hs.emirim.sookhee.redonorpets.model;

public class ShelterData {
    public String name;
    public String phone;
    public String profileImg;
    public String shelterPosition;
    public int storyCount;

    public ShelterData(){

    }

    public ShelterData(String name, String phone, String profileImg, String shelterPosition, int storyCount){
        this.name = name;
        this.phone = phone;
        this.profileImg = profileImg;
        this.shelterPosition = shelterPosition;
        this.storyCount = storyCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getShelterPosition() {
        return shelterPosition;
    }

    public void setShelterPosition(String shelterPosition) {
        this.shelterPosition = shelterPosition;
    }

    public int getStoryCount(){
        return storyCount;
    }

    public void setStoryCount(int storyCount){
        this.storyCount = storyCount;
    }


}
