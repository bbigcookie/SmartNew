package com.dcits.commodity.dao;

import java.util.List;
import java.util.Map;

import com.dcits.commodity.exception.DaoException;

public interface IRemarkDao {
 
	public List getRemarkList(Map<String,Object> param) throws DaoException;

	public void addRemark(Map<String, Object> paraMap) throws DaoException;

	public void delteRemark(Map<String, Object> paraMap) throws DaoException;
}
