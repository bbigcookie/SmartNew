package com.dcits.commodity.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.dcits.commodity.exception.ServiceException;
import com.dcits.commodity.service.ICommodityService;


@RestController
@RequestMapping("/commodity")
@Api(value="/commodity")
public class CommodityController {

	@Autowired
	public ICommodityService commodityService;

    @RequestMapping(value="/getCommodity",method = RequestMethod.GET)
    @ApiOperation(value="获取commodity", notes="根据输入条件模糊查询commodity")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "commodity_id",value = "商品id",required=false),
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "commodity_name",value = "商品名称",required=false),
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "commodity_type",value = "商品类型",required=false),
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "commodity_price",value = "商品价格",required=false),
    	@ApiImplicitParam(paramType = "query",dataType="int",name = "pageNum",value = "页数",required=false,defaultValue="1"),
    	@ApiImplicitParam(paramType = "query",dataType="int",name = "pageSize",value = "页码",required=false,defaultValue="10")
    })
    public String getCommodity(@RequestParam(name = "commodity_id",required = false) String commodity_id,
    						   @RequestParam(name = "commodity_name",required = false) String commodity_name,
				    		   @RequestParam(name = "commodity_type",required = false) String commodity_type,
				    		   @RequestParam(name = "commodity_price",required = false) String commodity_price,
				    		   @RequestParam(name= "pageNum",required=false,defaultValue="1") int pageNum,
				    		   @RequestParam(name= "pageSize",required=false,defaultValue="10") int pageSize){
    	
    	Map<String,Object> paraMap = new HashMap<String,Object>();
    	paraMap.put("commodity_id", commodity_id);
    	paraMap.put("commodity_name", commodity_name);
    	paraMap.put("commodity_type", commodity_type);
    	paraMap.put("commodity_price", commodity_price);
    	paraMap.put("pageNum", pageNum);
    	paraMap.put("pageSize", pageSize);
    	
    	List<Map<String, Object>> returnList = null;
		try {
			returnList = commodityService.getCommodityList(paraMap);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    	return JSON.toJSONString(returnList,SerializerFeature.WriteDateUseDateFormat);
    }
    
    @RequestMapping(value="/getCommodityDetail",method = RequestMethod.GET)
    @ApiOperation(value="获取commodity的详细信息", notes="根据输入条件查询commodity详细信息")
	@ApiImplicitParam(paramType = "query",dataType="String",name = "commodity_id",value = "商品id",required=true)
    public String getCommodityDetail(@RequestParam(name = "commodity_id",required = true) String commodity_id){
    	Map<String,Object> returnMap = null;
    	try {
    		returnMap = commodityService.getCommodityDetail(commodity_id);
    	} catch (ServiceException e) {
    		e.printStackTrace();
    	}
    	return JSON.toJSONString(returnMap,SerializerFeature.WriteDateUseDateFormat);
    }
    
    @RequestMapping(value="/getAllCommodityType",method = RequestMethod.GET)
    @ApiOperation(value="获取commodity种类", notes="根据输入条件查询commodity种类")
    public String getAllCommodityType(){
    	List<String> returnList = null;
    	try {
    		returnList = commodityService.getAllCommodityType();
    	} catch (ServiceException e) {
    		e.printStackTrace();
    	}
    	return JSON.toJSONString(returnList,SerializerFeature.WriteDateUseDateFormat);
    }


}