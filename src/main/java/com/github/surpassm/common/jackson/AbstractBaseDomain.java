package com.github.surpassm.common.jackson;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mc
 * Create date 2019/6/24 17:31
 * Version 1.0
 * Description
 */
@Data
public abstract class AbstractBaseDomain implements Serializable{
	private Long id;
}
