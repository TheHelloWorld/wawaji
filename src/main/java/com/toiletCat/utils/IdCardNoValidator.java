package com.toiletCat.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * 类说明:身份证合法性校验
 * --15位身份证号码：第7、8位为出生年份(两位数)，第9、10位为出生月份，第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。
 * --18位身份证号码
 * ：第7、8、9、10位为出生年份(四位数)，第11、第12位为出生月份，第13、14位代表出生日期，第17位代表性别，奇数为男，偶数为女。
 */
public class IdCardNoValidator {

	/**
	 * 身份证前6位【ABCDEF】为行政区划数字代码（简称数字码）说明（参考《GB/T 2260-2007 中华人民共和国行政区划代码》）：
	 * 该数字码的编制原则和结构分析，它采用三层六位层次码结构，按层次分别表示我国各省（自治区，直辖市，特别行政区）、
	 * 市（地区，自治州，盟）、县（自治县、县级市、旗、自治旗、市辖区、林区、特区）。
	 * 数字码码位结构从左至右的含义是：
	 * 第一层为AB两位代码表示省、自治区、直辖市、特别行政区；
	 * 第二层为CD两位代码表示市、地区、自治州、盟、直辖市所辖市辖区、县汇总码、省（自治区）直辖县级行政区划汇总码，其中：
	 * ——01~20、51~70表示市，01、02还用于表示直辖市所辖市辖区、县汇总码；
	 * ——21~50表示地区、自治州、盟；
	 * ——90表示省（自治区）直辖县级行政区划汇总码。
	 * 第三层为EF两位表示县、自治县、县级市、旗、自治旗、市辖区、林区、特区，其中：
	 * ——01~20表示市辖区、地区（自治州、盟）辖县级市、市辖特区以及省（自治区）直辖县级行政区划中的县级市，01通常表示辖区汇总码；
	 * ——21~80表示县、自治县、旗、自治旗、林区、地区辖特区；
	 * ——81~99表示省（自治区）辖县级市。
	 */

	private static String cityCode[] = {"11", "12", "13", "14", "15", "21", "22",
			"23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
			"44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
			"64", "65", "71", "81", "82", "91"};

	// 每位加权因子
	private static int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

	/**
	 * 验证所有的身份证的合法性
	 *
	 * @param idCardNo 身份证号
	 * @return
	 */
	public static boolean isValidatedAllIdCardNo(String idCardNo) {
		if (idCardNo.length() == 15) {
			return isValidate15IdCardNo(idCardNo);
		}
		return isValidate18IdCardNo(idCardNo);
	}

	/**
	 * 判断18位身份证的合法性
	 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
	 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
	 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
	 * 1.前1、2位数字表示:所在省份的代码;
	 * 2.第3、4位数字表示:所在城市的代码;
	 * 3.第5、6位数字表示:所在区县的代码;
	 * 4.第7~14位数字表示:出生年、月、日;
	 * 5.第15、16位数字表示:所在地的派出所的代码;
	 * 6.第17位数字表示性别:奇数表示男性，偶数表示女性;
	 * 7.第18位数字是校检码,也有的说是个人信息码,一般是随计算机的随机产生,用来检验身份证的正确性。校检码可以是0~9的数字,有时也用x表示。
	 * <p>
	 * 第十八位数字(校验码)的计算方法为:
	 * 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为:7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
	 * 2.将这17位数字和系数相乘的结果相加。
	 * 3.用加出来和除以11，看余数是多少？
	 * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。
	 * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10,身份证的最后一位号码就是2。
	 *
	 * @param idCardNo 身份证号
	 * @return
	 */
	private static boolean isValidate18IdCardNo(String idCardNo) {
		// 非18位为假
		if (idCardNo.length() != 18) {
			return false;
		}
		// 获取前17位
		String idCard17 = idCardNo.substring(0, 17);
		// 获取第18位
		String idCard18Code = idCardNo.substring(17, 18);

		char c[];
		String checkCode;
		// 是否都为数字
		if (isDigital(idCard17)) {
			c = idCard17.toCharArray();
		} else {
			return false;
		}

		int[] bit = convertCharToInt(c);

		int sum17 = getPowerSum(bit);

		// 将和值与11取模得到余数进行校验码判断
		checkCode = getCheckCodeBySum(sum17);
		if (null == checkCode) {
			return false;
		}
		// 将身份证的第18位与算出来的校码进行匹配，不相等就为假
		return idCard18Code.equalsIgnoreCase(checkCode);
	}

	/**
	 * 验证15位身份证的合法性,该方法验证不准确，最好是将15转为18位后再判断，该类中已提供。
	 *
	 * @param idCardNo 15位身份证号
	 * @return
	 */
	private static boolean isValidate15IdCardNo(String idCardNo) {
		// 非15位为假
		if (idCardNo.length() != 15) {
			return false;
		}

		// 是否全都为数字
		if (isDigital(idCardNo)) {
			String provinceId = idCardNo.substring(0, 2);
			String birthday = idCardNo.substring(6, 12);
			int year = Integer.parseInt(idCardNo.substring(6, 8));
			int month = Integer.parseInt(idCardNo.substring(8, 10));
			int day = Integer.parseInt(idCardNo.substring(10, 12));

			// 判断是否为合法的省份
			boolean flag = false;
			for (String id : cityCode) {
				if (id.equals(provinceId)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				return false;
			}
			// 该身份证生出日期在当前日期之后时为假
			Date birthDate = null;
			try {
				birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (birthDate == null || new Date().before(birthDate)) {
				return false;
			}

			// 判断是否为合法的年份
			GregorianCalendar curDay = new GregorianCalendar();
			int curYear = curDay.get(Calendar.YEAR);
			int year2bit = Integer.parseInt(String.valueOf(curYear)
					.substring(2));

			// 判断该年份的两位表示法，小于50的和大于当前年份的，为假
			if ((year < 50 && year > year2bit)) {
				return false;
			}

			// 判断是否为合法的月份
			if (month < 1 || month > 12) {
				return false;
			}

			// 判断是否为合法的日期
			boolean mFlag = false;
			curDay.setTime(birthDate); // 将该身份证的出生日期赋于对象curDay
			switch (month) {
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					mFlag = (day >= 1 && day <= 31);
					break;
				case 2: // 公历的2月非闰年有28天,闰年的2月是29天。
					if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
						mFlag = (day >= 1 && day <= 29);
					} else {
						mFlag = (day >= 1 && day <= 28);
					}
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					mFlag = (day >= 1 && day <= 30);
					break;
			}
			if (!mFlag) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 将15位的身份证转成18位身份证
	 *
	 * @param idCardNo 15位身份证号
	 * @return
	 */
	private static String convertIdCardBy15bit(String idCardNo) {
		String idCard17;
		// 非15位身份证
		if (idCardNo.length() != 15) {
			return null;
		}

		if (isDigital(idCardNo)) {
			// 获取出生年月日
			String birthday = idCardNo.substring(6, 12);
			Date birthDate = null;
			try {
				birthDate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cDay = Calendar.getInstance();
			cDay.setTime(birthDate);
			String year = String.valueOf(cDay.get(Calendar.YEAR));

			idCard17 = idCardNo.substring(0, 6) + year + idCardNo.substring(8);

			char c[] = idCard17.toCharArray();
			String checkCode;

			// 将字符数组转为整型数组
			int[] bit = convertCharToInt(c);
			int sum17 = getPowerSum(bit);

			// 获取和值与11取模得到余数进行校验码
			checkCode = getCheckCodeBySum(sum17);
			// 获取不到校验位
			if (null == checkCode) {
				return null;
			}

			// 将前17位与第18位校验码拼接
			idCard17 += checkCode;

		} else { // 身份证包含数字
			return null;
		}
		return idCard17;
	}

	/**
	 * 15位和18位身份证号码的基本数字和位数验校
	 *
	 * @param idCardNo 身份证号
	 * @return
	 */
	public static boolean isIdCardNo(String idCardNo) {
		return idCardNo == null || "".equals(idCardNo) ? false : Pattern.matches(
				"(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", idCardNo);
	}

	/**
	 * 15位身份证号码的基本数字和位数验校
	 *
	 * @param idCardNo 身份证号
	 * @return
	 */
	public static boolean is15IdCardNo(String idCardNo) {
		return idCardNo == null || "".equals(idCardNo) ? false : Pattern.matches(
				"^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$", idCardNo);
	}

	/**
	 * 18位身份证号码的基本数字和位数验校
	 *
	 * @param idCardNo 身份证号
	 * @return
	 */
	public static boolean is18IdCardNo(String idCardNo) {
		return Pattern.matches(
				"^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
				idCardNo);
	}

	/**
	 * 数字验证
	 *
	 * @param str 身份证号
	 * @return
	 */
	private static boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 *
	 * @param bit 身份证数组
	 * @return
	 */
	private static int getPowerSum(int[] bit) {

		int sum = 0;

		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}

	/**
	 * 将和值与11取模得到余数进行校验码判断
	 *
	 * @param sum17 入参
	 * @return 校验位
	 */
	private static String getCheckCodeBySum(int sum17) {
		String checkCode = null;
		switch (sum17 % 11) {
			case 10:
				checkCode = "2";
				break;
			case 9:
				checkCode = "3";
				break;
			case 8:
				checkCode = "4";
				break;
			case 7:
				checkCode = "5";
				break;
			case 6:
				checkCode = "6";
				break;
			case 5:
				checkCode = "7";
				break;
			case 4:
				checkCode = "8";
				break;
			case 3:
				checkCode = "9";
				break;
			case 2:
				checkCode = "x";
				break;
			case 1:
				checkCode = "0";
				break;
			case 0:
				checkCode = "1";
				break;
		}
		return checkCode;
	}

	/**
	 * 将字符数组转为整型数组
	 *
	 * @param c 身份证数组
	 * @return
	 * @throws NumberFormatException
	 */
	private static int[] convertCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}

}
