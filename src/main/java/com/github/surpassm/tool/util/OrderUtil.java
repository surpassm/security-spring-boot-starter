package com.github.surpassm.tool.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mc
 * Create date 2019/1/11 14:09
 * Version 1.0
 * Description 自定义订单编号，防并发
 */
public class OrderUtil extends Thread{

	private static long orderNum = 0L;
	private static String date ;
	/**
	 * 生成订单编号
	 * @return
	 */
	public static synchronized String getOrderNo() {
		String str = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
		if(date==null||!date.equals(str)){
			date = str;
			orderNum  = 0L;
		}
		orderNum ++;
		long orderNo = Long.parseLong((date)) * 10000;
		orderNo += orderNum;
		return orderNo+"";
	}

}
