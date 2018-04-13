package com.dcits.order.service;


import com.dcits.order.exception.ServiceException;
import com.dcits.order.model.Order;

public interface IOrderService{
    /**
    * 描述：添加
    * @param order
    */
    public int addOrder(Order order) throws ServiceException;
    /**
    * 描述：删除
    * @param order_no
    */
    public int deleteOrderByPrimaryKey(String order_no) throws ServiceException;
    /**
    * 描述：修改
    * @param order
    */
    public int updateOrder(Order order) throws ServiceException;
    /**
    * 描述：查找
    * @param order_no
    */
    public Order getOrderByPrimaryKey(String order_no) throws ServiceException;
}