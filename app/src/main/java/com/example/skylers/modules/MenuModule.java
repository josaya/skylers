package com.example.skylers.modules;

public class MenuModule {

    String menuId, menuName;

    public MenuModule(String menuId, String menuName){
        this.menuId = menuId;
        this.menuName = menuName;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }
}
