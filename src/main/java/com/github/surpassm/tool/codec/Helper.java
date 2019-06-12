package com.github.surpassm.tool.codec;

/**
 * @author mc
 * Create date 2019/4/15 10:41
 * Version 1.0
 * Description
 */
public class Helper {

	private static String key = "surpassm!@#$%";

	public static boolean isStringInArray(String str, String[] array){
		for (String val:array){
			if(str.equals(val)){
				return true;
			}
		}
		return false;
	}

	public static String encode(String str){
		String enStr = "";
		try {
			enStr = DesEncryptUtil.encrypt(str, key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return enStr;
	}

	public static String decode(String str) {
		String deStr = "";
		try {
			deStr = DesEncryptUtil.decrypt(str, key);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return deStr;
	}
}
