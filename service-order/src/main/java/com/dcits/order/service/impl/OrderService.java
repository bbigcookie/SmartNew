package com.dcits.order.service.impl;

import com.dcits.order.dao.IOrderDao;
import com.dcits.order.exception.DaoException;
import com.dcits.order.exception.ServiceException;
import com.dcits.order.model.Order;
import com.dcits.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Override
    public int addOrder(Order order) throws ServiceException {
        try {
            return orderDao.addOrder(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int deleteOrderByPrimaryKey(String order_no) throws ServiceException{
        try {
            return orderDao.deleteOrderByPrimaryKey(order_no);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int updateOrder(Order order) throws ServiceException{
        try {
            return orderDao.updateOrder(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Order getOrderByPrimaryKey(String order_no) throws ServiceException{
        try {
            return orderDao.getOrderByPrimaryKey(order_no);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

}