package kr.hs.emirim.sookhee.redonorpets.model;

public class CommentData {
    String name;
    String content;
    String img;

    public CommentData(){}
    public CommentData(String name, String content, String img){
        this.name = name;
        this.content = content;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
