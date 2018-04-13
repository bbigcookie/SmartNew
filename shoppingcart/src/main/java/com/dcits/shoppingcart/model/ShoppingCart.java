package com.dcits.shoppingcart.model;


import com.alibaba.fastjson.JSON;

import java.util.List;

public class ShoppingCart {
    //购物车标识
//    private long id;
    //商品ID列表
    private List commodityIDs;
    //購物車主人標示
    private String username;

//    public ShoppingCart(){
//        id = System.currentTimeMillis();
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setCommodityIDs(List commodityIDs) {
        this.commodityIDs = commodityIDs;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public List getCommodityIDs() {
        return commodityIDs;
    }

//    public long getId() {
//        return id;
//    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
