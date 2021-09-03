package xin.spring.file.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author spring
 * @Package xin.spring.file.common.util
 * @ClassName DateUtils.java
 * @descript
 * @date 2021/9/2 18:37
 */
public class DateUtils {


    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

}
