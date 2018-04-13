package com.dcits.order.dao.impl;

import com.dcits.order.base.dao.impl.BaseDao;
import com.dcits.order.dao.IOrderDao;
import com.dcits.order.exception.DaoException;
import com.dcits.order.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends BaseDao implements IOrderDao {

    @Override
    public int addOrder(Order order) throws DaoException {
        return insertBySqlId("mybatis.mapper.OrderMapper.addOrder",order);
    }

    @Override
    public int deleteOrderByPrimaryKey(String order_no) throws DaoException{
        return deleteBySqlId("mybatis.mapper.OrderMapper.deleteOrderByPrimaryKey",order_no);
    }

    @Override
    public int updateOrder(Order order) throws DaoException{
        return updateBySqlId("mybatis.mapper.OrderMapper.updateOrder",order);
    }

    @Override
    public Order getOrderByPrimaryKey(String order_no) throws DaoException{
        return findBySqlId("mybatis.mapper.OrderMapper.getOrderByPrimaryKey",order_no, Order.class);
//        Order order = new Order();
//        order.setCommodityName("XXX");
//        order.setOrder_no("212312222");
//        order.setCommodityPrice(400);
//        order.setUserName("lisi");
//        return order;

    }

}