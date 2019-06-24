package com.github.surpassm.common.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mc
 * Create date 2019/6/24 17:30
 * Version 1.0
 * Description
 */
@Data
public class AbstractBaseResult implements Serializable {

	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected static class Links {
		private String self;
		private String next;
		private String last;
	}

	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected static class DataBean<T extends AbstractBaseDomain> {
		private String type;
		private Long id;
		private T attributes;
		private T relationships;
		private Links links;
	}
}
