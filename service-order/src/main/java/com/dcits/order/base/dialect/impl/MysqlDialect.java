package com.dcits.order.base.dialect.impl;


import com.dcits.order.base.dialect.Dialect;

/**
 * Mysql数据库方言
 */
public class MysqlDialect extends Dialect {

    @Override
    public String getLimitString(String sql, int offset, int limit) {
    	StringBuilder str = new StringBuilder();   
    	str.append(sql).append(" limit ? , ?");   
    	return str.toString();
    }

}