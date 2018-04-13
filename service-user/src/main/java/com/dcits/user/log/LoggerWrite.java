package com.dcits.user.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author unknown
 */
public class LoggerWrite implements ILogger {

    private Class<?> clazz = null;

    public LoggerWrite(Class<?> clazz) {
        this.clazz = clazz;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#trace(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void trace(String message, Object... objects) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.trace(message, objects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#debug(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void debug(String message, Object... objects) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.debug(message, objects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#info(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void info(String message, Object... objects) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message, objects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#warn(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void warn(String message, Object... objects) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.warn(message, objects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#error(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void error(String message, Object... objects) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, objects);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#trace(java.lang.String,
     * java.lang.Throwable)
     */
    @Override
    public void trace(String message, Throwable e) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.trace(message, e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#debug(java.lang.String,
     * java.lang.Throwable)
     */
    @Override
    public void debug(String message, Throwable e) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.debug(message, e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#info(java.lang.String,
     * java.lang.Throwable)
     */
    @Override
    public void info(String message, Throwable e) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message, e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#warn(java.lang.String,
     * java.lang.Throwable)
     */
    @Override
    public void warn(String message, Throwable e) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.warn(message, e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.digitalchina.framework.base.logger.IDCLogger#error(java.lang.String,
     * java.lang.Throwable)
     */
    @Override
    public void error(String message, Throwable e) {
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.error(message, e);
    }

}
