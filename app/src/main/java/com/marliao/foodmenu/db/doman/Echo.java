package com.marliao.foodmenu.db.doman;

public class Echo {
    private int menuid;
    private int isLike;
    private int NotisLike;
    private int isColleck;



    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public int getIsLike() {
        return isLike;
    }

    public void setIsLike(int isLike) {
        this.isLike = isLike;
    }

    public int getNotisLike() {
        return NotisLike;
    }

    public void setNotisLike(int notisLike) {
        NotisLike = notisLike;
    }

    public int getIsColleck() {
        return isColleck;
    }

    public void setIsColleck(int isColleck) {
        this.isColleck = isColleck;
    }
}
