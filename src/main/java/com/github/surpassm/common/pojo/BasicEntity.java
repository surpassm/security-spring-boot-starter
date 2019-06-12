package com.github.surpassm.common.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author mc
 * Create date 2019/3/5 14:44
 * Version 1.0
 * Description
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class BasicEntity implements Serializable {

	private Integer isDelete;

	/*@ApiModelProperty(hidden = true)
	@Column(columnDefinition="datetime COMMENT '创建时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime createTime;

	@ApiModelProperty(hidden = true)
	@Column(columnDefinition="datetime COMMENT '更新时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime updateTime;

	@ApiModelProperty(hidden = true)
	@Column(columnDefinition="datetime COMMENT '删除时间'")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private LocalDateTime deleteTime;

	@ApiModelProperty(hidden = true)
	@Column(columnDefinition="int(11) COMMENT '创建用户编号'")
	private Integer createUserId;

	@ApiModelProperty(hidden = true)
	@Column(columnDefinition="int(11) COMMENT '更新用户编号'")
	private Integer updateUserId;

	@ApiModelProperty(hidden = true)
	@Column(columnDefinition="int(11) COMMENT '删除用户编号'")
	private Integer deleteUserId;*/
}
