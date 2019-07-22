package com.gqz.SMSVerify;

import java.util.Random;

/**
 * 
 * @ClassName: RandomUtil
 * @Description: 生成6位数的随机数，并且第一位数不能为0
 * @author ganquanzhong
 * @date 2018年4月7日 下午1:14:53
 */
public  class RandomUtil {
	
	public static String getRandom() {
		String randNum = new Random().nextInt(1000000) + "";
		//如果获取的随机数的第一位是以0开头的则重新获取
		if (randNum.length() != 6) {
			// 递归调用
			return getRandom();
		}
		return randNum;
	}
}