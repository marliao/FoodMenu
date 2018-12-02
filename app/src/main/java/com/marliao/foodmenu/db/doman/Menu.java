package com.marliao.foodmenu.db.doman;

public class Menu {
    private String spic;
    private String assistmaterial;
    private int notlikes;
    private String menuname;
    private String abstracts;
    private String mainmaterial;
    private int menuid;
    private int typeid;
    private int likes;

    public String getSpic() {
        return spic;
    }

    public void setSpic(String spic) {
        this.spic = spic;
    }

    public String getAssistmaterial() {
        return assistmaterial;
    }

    public void setAssistmaterial(String assistmaterial) {
        this.assistmaterial = assistmaterial;
    }

    public int getNotlikes() {
        return notlikes;
    }

    public void setNotlikes(int notlikes) {
        this.notlikes = notlikes;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getMainmaterial() {
        return mainmaterial;
    }

    public void setMainmaterial(String mainmaterial) {
        this.mainmaterial = mainmaterial;
    }

    public int getMenuid() {
        return menuid;
    }

    public void setMenuid(int menuid) {
        this.menuid = menuid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "spic='" + spic + '\'' +
                ", assistmaterial='" + assistmaterial + '\'' +
                ", notlikes=" + notlikes +
                ", menuname='" + menuname + '\'' +
                ", abstracts='" + abstracts + '\'' +
                ", mainmaterial='" + mainmaterial + '\'' +
                ", menuid=" + menuid +
                ", typeid=" + typeid +
                ", likes=" + likes +
                '}';
    }
}
