package org.greenleaf.utils.plugin

/**
 * Created by davy on 2017/6/8.
 */
public class InjectExtension {

    List<String> injectPackages
    String message

    @Override
    public String toString() {
        return "InjectExtension{" +
                "injectPackages=" + injectPackages +
                ", message='" + message + '\'' +
                '}'
    }
}