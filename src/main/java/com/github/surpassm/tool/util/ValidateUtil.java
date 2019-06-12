package com.github.surpassm.tool.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author mc
 * Create date 2019/1/8 16:18
 * Version 1.0
 * Description 效验工具集
 */
public class ValidateUtil {
    /**
     * 效验手机号码是否为1开头和11位
     */
    private static final Pattern P_MOBILE_PHONE = Pattern.compile("^1(3|4|5|7|8)\\d{9}$");

    /**
     * 用户名校验:字母、中文、点组成2-20位
     * ^[\u4e00-\u9fa5]{2,6}|(?=[a-zA-Z])[a-zA-Z\\s.]{2,20}$/g
     */
    private static final Pattern REALRNAME = Pattern.compile("^[\u4e00-\u9fa5]{2,6}|(?=[a-zA-Z])[a-zA-Z\\s.]{2,20}$/g");
    /**
     * 密码校验：匹配小写字母、大写字母、数字、特殊符号的两种及两种以上【非中文】
     */
    private static final Pattern PASSWORD = Pattern.compile("^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)[^\u4e00-\u9fa5]\\S{5,17}$");
    /**
     * 银行卡简单校验（16或19位）
     */
    private static final Pattern BANKCODE = Pattern.compile("(([1-9])[\\d]{18})|([1-9])[\\d]{15}");
    /**
     * 身份证
     */
    private static final Pattern IDENTITY_NO = Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}$)");


    /**
     * 校验身份证是否合法(包含15 18位)
     */
    public static boolean isIdentityNo(String identityNo) {
        return StringUtils.isNotBlank(identityNo) && IDENTITY_NO.matcher(identityNo).matches();
    }

    /**
     * 效验手机号码是否为1开头和11位
     *
     * @param str
     * @return
     */
    public static boolean isMobilePhone(String str) {
        return StringUtils.isNotBlank(str) && P_MOBILE_PHONE.matcher(str).matches();
    }

    /**
     * @param str
     * @return
     * @描述：真实姓名校验
     */
    public static boolean isRealName(String str) {
        return str != null && REALRNAME.matcher(str).matches();
    }

    /**
     * 密码校验：匹配小写字母、大写字母、数字、特殊符号的两种及两种以上【非中文】
     */
    public static boolean isPassword(String str) {
        return str != null && PASSWORD.matcher(str).matches();
    }

    /**
     * @param str
     * @return
     * @描述：银行卡号校验
     */
    public static boolean isBankCode(String str) {
        return str != null && BANKCODE.matcher(str).matches();
    }
}
