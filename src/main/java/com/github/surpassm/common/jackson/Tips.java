package com.github.surpassm.common.jackson;

/**
 * @author mc
 * date 2019/1/4 10:12
 * version 1.0
 * description
 */
public enum Tips {
	/**
	 *
	 */
    FAIL(400,"失败"),
    SUCCESS(200,"成功"),

    DISABLED_TOEK(401,"登陆过期,请重新登陆"),
    USER_EMAIL_HAD("该邮箱已注册"),
    USER_PASSWORD_FALSE("用户名或密码错误"),
    USER_PASSWORD_F("旧密码错误"),
    USER_PASSWORD_ERROE("密码错误"),
	USER_INFO_ERROR("用户信息不存在"),
    MSG_NOT("信息不存在"),
    MSG_YES("信息已存在"),
    CODE_FALSE("验证码错误"),
    PARAMETER_ERROR("参数有误"),

	passwordFormatError("密码格式错误，请填写小写字母、大写字母、数字、特殊符号的两种及两种以上"),
	departmentDataNull("组织不存在"),
	regionDataNull("区域不存在"),
	bankCodeError("银行卡号有误"),
	CarNumberRepeat("车牌号重复"),
	NotRepeatDelete("不能重复删除"),
	nameRepeat("名称重复"),
	illegalIdentifier("非法标识符"),
	AssociatedDataExistsAndCannotBeDeleted("存在关联数据，无法删除"),
	phoneFormatError("手机号码格式错误"),
	parentError("父级不存在"),
	childrenError("存在下级关联，无法删除"),




    ;
    public Integer code;
    public String msg;


    Tips(String msg) {
        /**
         * 消息
         */
        this.msg = msg;
    }

    Tips(Integer code, String msg) {
        /**
         * 状态码
         */
        this.code = code;
        /**
         * 消息
         */
        this.msg = msg;
    }
}
