package com.dcits.order.log;

public class LoggerFactory {

    public static <T> ILogger getLogger(Class<T> clazz) {
        return new LoggerWrite(clazz);

    }

}
