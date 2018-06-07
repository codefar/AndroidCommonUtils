package org.greenleaf.utils;

import java.text.DecimalFormat;

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: 数字格式化操作类
 */

public class NumberUtils {

    private static final DecimalFormat oneDec = new DecimalFormat("##0.0");
    private static final DecimalFormat twoDec = new DecimalFormat("##0.00");

    public static String formatOneDecimal(float number) {
        return oneDec.format(number);
    }

    public static String formatTwoDecimal(float number) {
        DecimalFormat twoDec = new DecimalFormat("##0.00");
        return twoDec.format(number);
    }
}
