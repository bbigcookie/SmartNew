package com.dcits.order.base.dialect.impl;


import com.dcits.order.base.dialect.Dialect;

/**
 * Oracle数据库方言
 */
public class OracleDialect extends Dialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM (SELECT e$$.*, ROWNUM rn FROM (");
		str.append(sql);
		str.append(") e$$ where ROWNUM <= ?" + ") where rn > ?");
		return str.toString();
	}

}