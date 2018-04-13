package com.dcits.commodity.service;

import java.util.List;
import java.util.Map;

import com.dcits.commodity.exception.ServiceException;

public interface ICommodityService {

	public List getCommodityList(Map<String,Object> param) throws ServiceException;

	public Map<String, Object> getCommodityDetail(String commodity_id) throws ServiceException;

	public List<String> getAllCommodityType() throws ServiceException;
}
