package com.dcits.order.controller;

import com.dcits.order.exception.ServiceException;
import com.dcits.order.log.ILogger;
import com.dcits.order.log.LoggerFactory;
import com.dcits.order.model.Order;
import com.dcits.order.service.IOrderService;
import com.dcits.order.util.JsonManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Api(value="/order")
public class OrderController {

    private static ILogger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;

    @RequestMapping(value="/addOrder",method = RequestMethod.POST)
    @ApiOperation(value="添加Order", notes="添加Order")
    @ApiImplicitParam(dataType="Order",name = "order",value = "order对象")
    @HystrixCommand(fallbackMethod = "addFallback")
    public String addOrder(@RequestBody Order order){
        Map<String,Object> resMap = new HashMap<String,Object>();
        try {
            int result = orderService.addOrder(order);
            resMap.put("rtnCode","2000");
            resMap.put("rtnMsg","success");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.debug(e.getMessage(),e);
            resMap.put("rtnCode","9999");
            resMap.put("rtnMsg",e.getMessage());
        }
        return JsonManager.toJson(resMap);
    }

    @RequestMapping(value="/deleteOrderByPrimaryKey",method = RequestMethod.GET)
    @ApiOperation(value="删除Order", notes="根据主键删除Order")
    @ApiImplicitParam(paramType = "query",dataType="String",name = "order-no",value = "order-no")
    @HystrixCommand(fallbackMethod = "deleteFallback")
    public String deleteOrderByPrimaryKey(@RequestParam String order_no){
        Map<String,Object> resMap = new HashMap<String,Object>();
        try {
            int result = orderService.deleteOrderByPrimaryKey(order_no);
            resMap.put("rtnCode","2000");
            resMap.put("rtnMsg","success");
        } catch (ServiceException e) {
            LOG.debug(e.getMessage(),e);
            resMap.put("rtnCode","9999");
            resMap.put("rtnMsg",e.getMessage());
        }
        return JsonManager.toJson(resMap);
    }

    @RequestMapping(value="/updateOrder",method = RequestMethod.POST)
    @ApiOperation(value="修改Order", notes="根据Order对象修改")
    @ApiImplicitParam(dataType="Order",name = "order",value = "order对象")
    @HystrixCommand(fallbackMethod = "updateFallback")
    public String updateOrder(@RequestBody Order order){
        Map<String,Object> resMap = new HashMap<String,Object>();
        try {
            int result = orderService.updateOrder(order);
            resMap.put("rtnCode","2000");
            resMap.put("rtnMsg","success");
        } catch (ServiceException e) {
            e.printStackTrace();
            LOG.debug(e.getMessage(),e);
            resMap.put("rtnCode","9999");
            resMap.put("rtnMsg",e.getMessage());
        }
        return JsonManager.toJson(resMap);
    }

    @RequestMapping(value="/getOrderByPrimaryKey",method = RequestMethod.GET)
    @ApiOperation(value="获取Order", notes="根据主键获取Order对象")
    @ApiImplicitParam(paramType = "query",dataType="String",name = "order-no",value = "order-no")
    @HystrixCommand(fallbackMethod = "getFallback")
    public String getOrderByPrimaryKey(@RequestParam String order_no){
        Map<String,Object> resMap = new HashMap<String,Object>();
        try {
            Order resOrder = orderService.getOrderByPrimaryKey(order_no);
            if(resOrder == null){
                resMap.put("rtnCode","2000");
                resMap.put("rtnMsg","Order对象为空");
                return JsonManager.toJson(resMap);
            }
            resMap.put("rtnCode","2000");
            resMap.put("user",resOrder);
        } catch (Throwable e) {
            e.printStackTrace();
            LOG.debug(e.getMessage(),e);
            resMap.put("rtnCode","9999");
            resMap.put("rtnMsg",e.getMessage());
        }
        return JsonManager.toJson(resMap);
    }

    public String addFallback(@RequestBody Order order){return "添加Order超时";}
    public String deleteFallback(@RequestParam String order_no){return "删除Order超时";}
    public String updateFallback(@RequestBody Order order){return "修改Order超时";}
    public String getFallback(@RequestParam String order_no){return "查询Order超时";}

}