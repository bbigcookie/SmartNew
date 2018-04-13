package com.dcits.commodity.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dcits.commodity.base.dao.impl.BaseDao;
import com.dcits.commodity.dao.IRemarkDao;
import com.dcits.commodity.exception.DaoException;

@Component
public class RemarkDao extends BaseDao implements IRemarkDao{

	@Override
	public List getRemarkList(Map<String, Object> param) throws DaoException{
		
		return super.findListBySqlId("remarkMapping.getRemarkList",param,List.class);
	}

	@Override
	public void addRemark(Map<String, Object> paraMap) throws DaoException {
		insertBySqlId("remarkMapping.addRemark",paraMap);
	}

	@Override
	public void delteRemark(Map<String, Object> paraMap) throws DaoException {
		deleteBySqlId("remarkMapping.delteRemark", paraMap);
	}

}
