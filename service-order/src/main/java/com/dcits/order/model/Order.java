
package com.dcits.order.model;

public class Order {

    private String order_no;
    private String userName;
    private String commodityName;
    private double commodityPrice;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public String getUserName() {
        return userName;
    }
}