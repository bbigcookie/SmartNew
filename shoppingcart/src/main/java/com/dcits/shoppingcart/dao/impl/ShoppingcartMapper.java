package com.dcits.shoppingcart.dao.impl;

import com.dcits.shoppingcart.dao.bean.Shoppingcart;

public interface ShoppingcartMapper {
    int deleteByPrimaryKey(String userid);

    int insert(Shoppingcart record);

    int insertSelective(Shoppingcart record);

    Shoppingcart selectByPrimaryKey(String userid);

    int updateByPrimaryKeySelective(Shoppingcart record);

    int updateByPrimaryKey(Shoppingcart record);
}