package com.dcits.commodity.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.commodity.dao.ICommodityDao;
import com.dcits.commodity.exception.DaoException;
import com.dcits.commodity.exception.ServiceException;
import com.dcits.commodity.service.ICommodityService;

@Service
public class CommodityService implements ICommodityService{

	@Autowired
	public ICommodityDao commodityDao;

	@Override
	public List getCommodityList(Map<String, Object> param)
			throws ServiceException {
		
		int pageNum = Integer.parseInt(""+param.get("pageNum"));
		int pageSize = Integer.parseInt(""+param.get("pageSize"));
		List returnList = null;
		if(pageNum == 0 || pageSize == 0){
			try {
				returnList = commodityDao.getCommodityList(param);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}else{
			try {
				returnList = commodityDao.getCommodityListByPage(param, pageNum, pageSize);
			} catch (DaoException e) {
				e.printStackTrace();
			}
		}
		return returnList;
	}

	@Override
	public Map<String, Object> getCommodityDetail(String commodity_id)
			throws ServiceException {
		Map<String, Object> returnMap = null;
		try {
			returnMap = commodityDao.getCommodityDetail(commodity_id);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public List<String> getAllCommodityType()
			throws ServiceException {
		
		List<String> returnList = null;
		try {
			returnList = commodityDao.getAllCommodityType();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return returnList;
	}

}
