package com.github.surpassm.common.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mc
 * Create date 2019/6/24 17:32
 * Version 1.0
 * Description
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResult extends AbstractBaseResult {
	private int code;
	private String title;
	private String detail;
}
