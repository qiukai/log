package top.qiuk.log.util;

import java.util.Date;

import top.qiuk.log.constant.DateTypeEnum;

/**
 * 每秒生成1亿不同的数字
 * 
 * @author 邱凯
 *
 */
public class Id {

	private int numsSize = 1000 * 1000;
	private String[] nums = new String[numsSize];
	private static Id id = new Id();
	private int i = 0;

	private Id() {
		for (int i = 0; i < numsSize; i++) {
			String tmp = String.valueOf(numsSize + i);
			nums[i] = tmp.substring(tmp.length() - (String.valueOf(numsSize).length()));
		}
	}

	private synchronized String createId() {
		if (i == numsSize) {
			i = 0;
		} else {
			i++;
		}
		return DateUtil.getString(new Date(), DateTypeEnum.yMdHmsS) + nums[i];
	}

	/**
	 * 生成String类型id
	 * 
	 * @return
	 */
	public static synchronized String next() {
		return id.createId();
	}
}
