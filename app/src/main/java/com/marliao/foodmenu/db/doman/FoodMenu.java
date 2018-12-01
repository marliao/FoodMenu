package com.marliao.foodmenu.db.doman;

import java.util.List;

public class FoodMenu {
    private String result;
    private List<Menu> menuList;

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
}
