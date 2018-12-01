package com.marliao.foodmenu.db.doman;

import java.util.List;

public class MenuDetail {
    private String result;
    private List<Menu> menuList;
    private List<Steps> stepsList;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Steps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }
}
