package com.dcits.commodity.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dcits.commodity.base.dao.impl.BaseDao;
import com.dcits.commodity.dao.ICommodityDao;
import com.dcits.commodity.exception.DaoException;

@Component
public class CommodityDao extends BaseDao implements ICommodityDao{

	@Override
	public List getCommodityListByPage(Map<String, Object> param,int pageNum,int pageSize) throws DaoException{
		
		return super.findListBySqlId("commodityMapping.getCommodityList",param,pageNum,pageSize,List.class);
	}
	
	@Override
	public List getCommodityList(Map<String, Object> param) throws DaoException{
		
		return super.findListBySqlId("commodityMapping.getCommodityList",param,List.class);
	}

	@Override
	public Map<String, Object> getCommodityDetail(String commodity_id)
			throws DaoException {
		
		return super.findBySqlId("commodityMapping.getCommodityDetail", commodity_id, Map.class);
	}

	@Override
	public List<String> getAllCommodityType() throws DaoException {
		return super.findListBySqlId("commodityMapping.getAllCommodityType",null,String.class);
	}

}
