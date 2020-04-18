package kr.hs.emirim.sookhee.redonorpets;

public class StoryData {

    public String title;
    public String shelterName;
    public String mainImg;
    public String date;


    public StoryData() {
    }

    public StoryData(String title, String shelterName, String mainImg, String date) {
        this.title = title;
        this.shelterName = shelterName;
        this.mainImg = mainImg;
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}