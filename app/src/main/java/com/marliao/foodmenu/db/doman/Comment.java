package com.marliao.foodmenu.db.doman;

public class Comment {
    private int menuid;
    private String region;
    private String content;
    private Ptime ptime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Ptime getPtime() {
        return ptime;
    }

    public void setPtime(Ptime ptime) {
        this.ptime = ptime;
    }
}
