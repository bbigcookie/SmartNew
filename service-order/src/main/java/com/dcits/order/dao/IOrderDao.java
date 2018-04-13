package com.dcits.order.dao;

import com.dcits.order.exception.DaoException;
import com.dcits.order.model.Order;

public interface IOrderDao{
    /**
    * 描述：添加
    * @param order
    */
    public int addOrder(Order order) throws DaoException;
    /**
    * 描述：删除
    * @param order_no
    */
    public int deleteOrderByPrimaryKey(String order_no) throws DaoException;
    /**
    * 描述：修改
    * @param order
    */
    public int updateOrder(Order order) throws DaoException;
    /**
    * 描述：查找
    * @param order_no
    */
    public Order getOrderByPrimaryKey(String order_no) throws DaoException;
}