package com.github.surpassm.common.pojo;

import lombok.*;


/**
 * @author mc
 * Create date 2019/1/4 11:32
 * Version 1.0
 * Description
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurpassmFile {

	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件后缀
	 */
	private String fileSuffix;
	/**
	 * 文件路径
	 */
	private String url;

}
