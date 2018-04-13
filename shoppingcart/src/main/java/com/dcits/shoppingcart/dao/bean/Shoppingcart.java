package com.dcits.shoppingcart.dao.bean;

public class Shoppingcart {
    private String username;

    private String commodityid;

    public String getUsername() {
        return username;
    }

    public void setUserid(String userid) {
        this.username = username == null ? null : username.trim();
    }

    public String getCommodityid() {
        return commodityid;
    }

    public void setCommodityid(String commodityid) {
        this.commodityid = commodityid == null ? null : commodityid.trim();
    }
}