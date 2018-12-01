package com.marliao.foodmenu.db.doman;

public class Comment {
    private int menuid;
    private String region;
    private Ptime ptime;

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
