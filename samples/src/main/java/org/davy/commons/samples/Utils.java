package org.davy.commons.samples;

/**
 * Created by davy on 2017/6/9.
 */

public class Utils {

    public Utils() {
        Class ss = this.getClass();
        System.out.println(ss.getPackage().getName());
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
