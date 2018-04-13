package com.dcits.shoppingcart.controller;

import com.alibaba.fastjson.JSON;
import com.dcits.shoppingcart.model.ShoppingCart;
import com.dcits.shoppingcart.reposity.ShoppingCartDao;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/shoppingcart")

public class ShoppingCartController {
    Logger log = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartDao dao;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ApiOperation(value = "添加ShoppingCart", notes = "添加ShoppingCart")
//    @ApiImplicitParam(dataType = "ShoppingCart", name = "ShoppingCart", value = "ShoppingCart对象")
    public boolean create(@RequestParam("username") String username) {
        return dao.add(username);
    }


    @ApiOperation(value = "查詢ShoppingCart", notes = "查詢ShoppingCart")
    @ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "username")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String query(@RequestParam("username") String username) {
        String result = dao.get(username);
        return result;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    @ApiOperation(value = "修改ShoppingCart", notes = "根据ShoppingCart对象修改")
    public boolean update(@RequestParam String username,
    					 @RequestParam String commodityId,
    					 @RequestParam String op) {
        return dao.update(username,commodityId,op);
    }

}
