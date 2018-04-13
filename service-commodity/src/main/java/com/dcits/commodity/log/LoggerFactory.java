package com.dcits.commodity.log;

public class LoggerFactory {

    public static <T> ILogger getLogger(Class<T> clazz) {
        return new LoggerWrite(clazz);

    }

}
