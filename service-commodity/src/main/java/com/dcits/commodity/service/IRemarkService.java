package com.dcits.commodity.service;

import java.util.List;
import java.util.Map;

import com.dcits.commodity.exception.ServiceException;

public interface IRemarkService {

	public List getRemarkList(Map<String,Object> param) throws ServiceException;

	public void addRemark(Map<String, Object> paraMap) throws ServiceException;

	public void delteRemark(Map<String, Object> paraMap) throws ServiceException;
}
