package com.dcits.shoppingcart.reposity;

import com.alibaba.fastjson.JSON;
import com.dcits.shoppingcart.model.ShoppingCart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ShoppingCartDao {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private String port;

    @Value("${redis.auth}")
    private String auth;

    @Value("${redis.cartsName}")
    private String cartsName;

    public boolean add(String username) {
        Jedis jedis = new Jedis(host,Integer.parseInt(port));
        jedis.auth(auth);
        addRedisDB(username,jedis);
        {
            List<String> carStr = jedis.hmget(cartsName,username);
            if (carStr==null||carStr.size()<=0||carStr.get(0)==null){
                ShoppingCart obj = new ShoppingCart();
                obj.setUsername(username);
                obj.setCommodityIDs(new ArrayList<String>());
                Map<String,String> cartsMap = jedis.hgetAll(cartsName);
                cartsMap.put(username,obj.toString());
                jedis.hmset(cartsName,cartsMap);
            }
        }
        jedis.close();
        return true;
    }

    public String get(String username) {
        Jedis jedis = new Jedis(host,Integer.parseInt(port));
        jedis.auth(auth);
        List<String> carStrs = jedis.hmget(cartsName,username);
        if (carStrs!=null&&carStrs.size()>0){
            jedis.close();
            return carStrs.get(0);
        }
        jedis.close();
        return null;
    }

    public boolean del(Long shoppingCartId) {
        Jedis jedis = new Jedis(host,Integer.parseInt(port));
        jedis.auth(auth);
        Map<String,String> cartsMap = jedis.hgetAll(cartsName);
        String remvalue = cartsMap.remove(Long.toString(shoppingCartId));
        jedis.hmset(cartsName,cartsMap);
//        RedisPool.returnResource(jedis);
        jedis.close();
        return remvalue != null;
    }

    public boolean update(String username,String commodityId,String op){
        Jedis jedis = new Jedis(host,Integer.parseInt(port));
        jedis.auth(auth);
        addRedisDB(username,jedis);
        ShoppingCart sc = new ShoppingCart();
        List<String> carStrs = jedis.hmget(cartsName,username);
    	if("add".equals(op)){
            ShoppingCart shoppingCart = JSON.parseObject(carStrs.get(0),ShoppingCart.class);
            shoppingCart.getCommodityIDs().add(commodityId);
            Map<String,String> cartsMap = jedis.hgetAll(cartsName);
            cartsMap.put(username,shoppingCart.toString());
            jedis.hmset(cartsName,cartsMap);
    	}else if("remove".equals(op)){
            ShoppingCart shoppingCart = JSON.parseObject(carStrs.get(0),ShoppingCart.class);
            shoppingCart.getCommodityIDs().remove(commodityId);
            Map<String,String> cartsMap = jedis.hgetAll(cartsName);
            cartsMap.put(username,shoppingCart.toString());
            jedis.hmset(cartsName,cartsMap);
    	}
    	jedis.close();
    	return true;
    }

    public String addCommodity(Long cartID, List<Long> ids) {

        ShoppingCart shoppingCart = ShoppingCartCache.cache.get(cartID);
        if (shoppingCart != null) {
            shoppingCart.getCommodityIDs().add(ids);
        }
        return shoppingCart.toString();
    }

    public String delCommodity(Long cartID, List<Long> ids) {
        ShoppingCart shoppingCart = ShoppingCartCache.cache.get(cartID);
        if (shoppingCart != null) {
            shoppingCart.getCommodityIDs().remove(ids);
        }
        return shoppingCart.toString();
    }

    public boolean addRedisDB(String username,Jedis jedis){
        if (!jedis.exists(cartsName)){
            ShoppingCart obj = new ShoppingCart();
            obj.setUsername(username);
            obj.setCommodityIDs(new ArrayList<String>());
            Map<String,String> cartsMap = new HashMap<String, String>();
            cartsMap.put(username,obj.toString());
            jedis.hmset(cartsName,cartsMap);
        }
        return true;
    }

}
