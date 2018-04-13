package com.dcits.commodity.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.commodity.dao.IRemarkDao;
import com.dcits.commodity.exception.DaoException;
import com.dcits.commodity.exception.ServiceException;
import com.dcits.commodity.service.IRemarkService;

@Service
public class RemarkService implements IRemarkService{

	@Autowired
	public IRemarkDao remarkDao;

	@Override
	public List getRemarkList(Map<String, Object> param)
			throws ServiceException {
		
		List returnList = null;
		try {
			returnList = remarkDao.getRemarkList(param);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public void addRemark(Map<String, Object> paraMap) throws ServiceException {
		 try {
			remarkDao.addRemark(paraMap);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delteRemark(Map<String, Object> paraMap)
			throws ServiceException {
		 try {
			remarkDao.delteRemark(paraMap);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
}
