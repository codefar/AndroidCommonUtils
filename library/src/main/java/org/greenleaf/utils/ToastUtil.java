package org.greenleaf.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * @author wlj
 * @date 2017/3/28
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils
 * @description Toast封装类
 */
public class ToastUtil {

    private static Toast toast;

    public static void showToast(@NonNull Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    public static void showToast(@NonNull Context context, @StringRes int resId) {
        if (toast == null) {
            toast = Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT);
        } else {
            toast.setText(context.getString(resId));
        }
        toast.show();
    }
}
