package com.dcits.commodity.dao;

import java.util.List;
import java.util.Map;

import com.dcits.commodity.exception.DaoException;

public interface ICommodityDao {
 
	public List getCommodityListByPage(Map<String, Object> param,int pageNum, int pageSize) throws DaoException; 
	
	public List getCommodityList(Map<String,Object> param) throws DaoException;

	public Map<String, Object> getCommodityDetail(String commodity_id) throws DaoException;

	public List<String> getAllCommodityType() throws DaoException;
}
