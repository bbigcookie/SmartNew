package com.dcits.user.base.dialect.impl;


import com.dcits.user.base.dialect.Dialect;

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