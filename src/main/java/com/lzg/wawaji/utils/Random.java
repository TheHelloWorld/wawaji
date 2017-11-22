package com.lzg.wawaji.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Random {

	public static String getRandom() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append((int) Math.floor(Math.random() * 10));
		}

		return sb.toString();
	}

	public static String getRandomDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = sdf.format(date);
		return s;
	}

	public static String getRandomName(String str) {
		String ss = str.substring(str.lastIndexOf("."), str.length());
		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = sdf.format(date);
		return s + getRandom() + ss;
	}

	public static String getRandomString(int length) {

		String val = "";
		java.util.Random random = new java.util.Random();

		//参数length，表示生成几位随机数
		for (int i = 0; i < length; i++) {

			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			//输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				//输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	public static void main(String[] args) {
		System.out.println(getRandomString(18));
	}

}
