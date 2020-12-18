package com.github.spring.expand.data.search;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author wx
 * @date 2020/12/11 8:35
 */
public final class SearchUtil {

    private static Logger logger = LoggerFactory.getLogger(SearchUtil.class);

    private SearchUtil() {
        super();
    }

    /**
     * @param source
     * @return
     */
    public static Date toDate(String source) {
        return toDate(source, new String[0]);
    }

    /**
     * 字符串转时间
     *
     * @param source
     * @param formats
     * @return
     */
    public static Date toDate(String source, String... formats) {
        if (source == null) {
            return null;
        }
        String value = StringUtils.trim(source);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        if (formats != null && formats.length > 0) {
            for (String format : formats) {
                Date date = parseDate(value, format);
                if (date != null) {
                    return date;
                }
            }
        }
        if (value.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(value, "yyyy-MM");
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(value, "yyyy-MM-dd");
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(value, "yyyy-MM-dd HH:mm");
        } else if (value.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(value, "yyyy-MM-dd HH:mm:ss");
        } else {
            logger.warn("Invalid boolean value '{}'", value);
            return null;
        }
    }

    /**
     * 格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    private static Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            date = FastDateFormat.getInstance(format).parse(dateStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid boolean value ");
        }
        return date;
    }

}
