package org.davy.utils;

public class DumpUtils {

    private DumpUtils() {
    }

    public static int getLineNumber( ){
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace[1].getLineNumber( );
    }

    public static String getMethodName( ){
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace[1].getMethodName( );
    }

    public static String getFileName( ){
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace[1].getFileName( );
    }

    public static String getClassName( ){
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace[1].getClassName();
    }
}