package top.qiuk.log.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import top.qiuk.log.constant.DateTypeEnum;
import top.qiuk.log.constant.ExceptionTypeEnum;
import top.qiuk.log.exception.UtilRuntimeException;

/**
 * @author : 邱凯 Function: 日期的帮助类
 */
public class DateUtil {

	private static HashMap<DateTypeEnum, SimpleDateFormat> formatters = new HashMap<DateTypeEnum, SimpleDateFormat>();

	/**
	 * 将日期转成指定的格式的字符串
	 * @param date Date类型  目标日期
	 * @param pattern DateTypeEnum类型  转换的类型
	 * @return 返回pattern格式的字符串
	 */
	public static String getString(Date date, DateTypeEnum pattern) {
		if (date != null) {
			SimpleDateFormat formatter = formatters.get(pattern);
			if (formatter == null) {
				formatter = new SimpleDateFormat(DateTypeEnum.patternFromType(pattern));
				formatters.put(pattern, formatter);
			}
			return formatter.format(date);
		}
		throw new UtilRuntimeException(ExceptionTypeEnum.DATE_FORMATTER, "日期转字符串错误！");
	}

	/**
	 * 将String类型Date类型Long类型转换成Date类型
	 * @param obj 目标对象
	 * @param pattern DateTypeEnum类型  转换的类型
	 * @return 返回pattern格式的Date类型对象
	 */
	public static Date getDate(Object obj, DateTypeEnum pattern) {
		if (obj == null) {
			throw new UtilRuntimeException(ExceptionTypeEnum.DATE_FORMATTER, "目标对象为null");
		}
		if (obj instanceof String) {
			try {
				SimpleDateFormat formatter = formatters.get(pattern);
				if (formatter == null) {
					formatter = new SimpleDateFormat(DateTypeEnum.patternFromType(pattern));
					formatters.put(pattern, formatter);
				}
				return formatter.parse((String) obj);
			} catch (ParseException e) {
				throw new UtilRuntimeException(ExceptionTypeEnum.DATE_FORMATTER, obj.toString() + "目标对象无法转换成日期");
			}
		} else if (obj instanceof Date) {
			return (Date) obj;
		} else if (obj instanceof Long) {
			return new Date(((Long) obj).longValue());
		}
		throw new UtilRuntimeException(ExceptionTypeEnum.DATE_FORMATTER, obj.toString() + "目标对象类型错误！");
	}
}
