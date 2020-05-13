package kr.hs.emirim.sookhee.redonorpets.model;

public class StoryData {

    public String title;
    public String shelterName;
    public String mainImg;
    public String date;
    public String shelterPosition;
    public String storyPosition;
    public int likeCount;
    public int commentCount;


    public StoryData() {
    }

    public StoryData(String title, String shelterName, String mainImg, String date, String shelterPosition, int likeCount, int commentCount) {
        this.title = title;
        this.shelterName = shelterName;
        this.mainImg = mainImg;
        this.date = date;
        this.shelterPosition = shelterPosition;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
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

    public String getShelterPosition() {
        return shelterPosition;
    }

    public void setShelterPosition(String shelterPosition) {
        this.shelterPosition = shelterPosition;
    }

    public String getStoryPosition() {
        return storyPosition;
    }

    public void setStoryPosition(String storyPosition) {
        this.storyPosition = storyPosition;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCommentCount() {
        return commentCount;
    }
}