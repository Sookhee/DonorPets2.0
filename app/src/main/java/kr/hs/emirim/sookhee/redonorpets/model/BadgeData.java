package kr.hs.emirim.sookhee.redonorpets.model;

public class BadgeData {
    private boolean isClear;
    private int badgeId;
    private String badgeTitle;
    private String badgeSub;

    public BadgeData(){}

    public BadgeData(boolean isClear, int badgeId, String badgeTitle, String badgeSub){
        this.isClear = isClear;
        this.badgeId = badgeId;
        this.badgeTitle = badgeTitle;
        this.badgeSub = badgeSub;
    }


    public String getBadgeTitle() {
        return badgeTitle;
    }

    public void setBadgeTitle(String badgeTitle) {
        this.badgeTitle = badgeTitle;
    }

    public String getBadgeSub() {
        return badgeSub;
    }

    public void setBadgeSub(String badgeSub) {
        this.badgeSub = badgeSub;
    }

    public boolean isClear() {
        return isClear;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public int getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(int badgeId) {
        this.badgeId = badgeId;
    }
}
