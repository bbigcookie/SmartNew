package com.dcits.user.log;

public interface ILogger {

    public abstract void trace(String message, Object... objects);

    public abstract void debug(String message, Object... objects);

    public abstract void info(String message, Object... objects);

    public abstract void warn(String message, Object... objects);

    public abstract void error(String message, Object... objects);

    public abstract void trace(String message, Throwable e);

    public abstract void debug(String message, Throwable e);

    public abstract void info(String message, Throwable e);

    public abstract void warn(String message, Throwable e);

    public abstract void error(String message, Throwable e);

}