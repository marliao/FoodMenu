package com.marliao.foodmenu.db.doman;

public class Steps {
    private int stepid;
    private String description;
    private int menuid;
    private int islike;
    private int iscolleck;

    public int getIscolleck() {
        return iscolleck;
    }

    public void setIscolleck(int iscolleck) {
        this.iscolleck = iscolleck;
    }

    public int getIslike() {
        return islike;
    }

    public void setIslike(int islike) {
        this.islike = islike;
    }

    public int getStepid() {
        return stepid;
    }

    public void setStepid(int stepid) {
        this.stepid = stepid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    private String pic;

    @Override
    public String toString() {
        return "Steps{" +
                "stepid=" + stepid +
                ", description='" + description + '\'' +
                ", menuid=" + menuid +
                ", islike=" + islike +
                ", iscolleck=" + iscolleck +
                ", pic='" + pic + '\'' +
                '}';
    }
}
