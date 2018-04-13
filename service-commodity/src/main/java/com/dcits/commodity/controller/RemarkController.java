package com.dcits.commodity.controller;

import com.alibaba.druid.util.StringUtils;
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
import com.dcits.commodity.service.IRemarkService;
import com.dcits.commodity.util.MessageHelper;
import com.dcits.commodity.util.UUIDGenerator;


@RestController
@RequestMapping("/remark")
@Api(value="/remark")
public class RemarkController {

	@Autowired
	public IRemarkService remarkService;
	
    @RequestMapping(value="/getRemark",method = RequestMethod.GET)
    @ApiOperation(value="获取评论列表", notes="根据输入条件获取评论")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "remark_id",value = "评论id",required=false),
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "commodity_id",value = "商品id",required=false),
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "user_id",value = "用户id",required=false)
    })
    public String getRemark(@RequestParam(name = "remark_id",required = false) String remark_id,
				    	    @RequestParam(name = "commodity_id",required = false) String commodity_id,
				    		@RequestParam(name = "user_id",required = false) String user_id){
    	
    	Map<String,Object> paraMap = new HashMap<String,Object>();
    	paraMap.put("remark_id", remark_id);
    	paraMap.put("commodity_id", commodity_id);
    	paraMap.put("user_id", user_id);
    	
    	List<Map<String, Object>> returnList = null;
		try {
			returnList = remarkService.getRemarkList(paraMap);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    	return JSON.toJSONString(returnList,SerializerFeature.WriteDateUseDateFormat);
    }
    
    @RequestMapping(value="/addRemark",method = RequestMethod.POST)
    @ApiOperation(value="发表评论", notes="用户对某商品发表评论")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "commodity_id",value = "商品id",required=true),
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "remark_desc",value = "评论内容",required=true),
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "user_id",value = "用户id",required=true)
    })
    public String addRemark(@RequestParam(name = "remark_desc",required = true) String remarj_desc,
				    		@RequestParam(name = "commodity_id",required = true) String commodity_id,
				    		@RequestParam(name = "user_id",required = true) String user_id){
    	
    	Map<String,Object> paraMap = new HashMap<String,Object>();
    	paraMap.put("remarj_desc", remarj_desc);
    	paraMap.put("commodity_id", commodity_id);
    	paraMap.put("user_id", user_id);
    	paraMap.put("remark_id", UUIDGenerator.getUUID());
    	
    	try {
    		remarkService.addRemark(paraMap);
    	} catch (ServiceException e) {
    		e.printStackTrace();
    	}
    	return MessageHelper.wrap("result",true,"message","评论成功");
    }
    
    @RequestMapping(value="/delteRemark",method = RequestMethod.DELETE)
    @ApiOperation(value="删除评论", notes="用户对已发表的评论进行删除操作")
    @ApiImplicitParams({
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "remark_id",value = "评论id(多条用逗号分隔)",required=false),
    	@ApiImplicitParam(paramType = "query",dataType="String",name = "user_id",value = "用户id",required=true)
    })
    public String delteRemark(@RequestParam(name = "remark_id",required = false) String remark_id,
				    		  @RequestParam(name = "user_id",required = true) String user_id){
    	
    	Map<String,Object> paraMap = new HashMap<String,Object>();
    	String[] remark_ids = null;
    	if(remark_id!=null && !"".equals(remark_id)){
    		remark_ids = remark_id.split(",");
    	}
    	paraMap.put("remark_ids", remark_ids);
    	paraMap.put("user_id", user_id);
    	
    	try {
    		remarkService.delteRemark(paraMap);
    	} catch (ServiceException e) {
    		e.printStackTrace();
    	}
    	return MessageHelper.wrap("result",true,"message","删除评论成功");
    }
    
    
}