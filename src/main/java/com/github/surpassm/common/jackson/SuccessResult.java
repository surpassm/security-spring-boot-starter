package com.github.surpassm.common.jackson;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * @author mc
 * Create date 2019/6/24 17:31
 * Version 1.0
 * Description
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SuccessResult<T extends AbstractBaseDomain> extends AbstractBaseResult {
	private Links links;
	private List<DataBean<T>> data;

	public SuccessResult(String self, T attributes) {
		links = new Links();
		links.setSelf(self);

		createDataBean(null, attributes);
	}

	public SuccessResult(String self, int next, int last, List<T> attributes) {
		links = new Links();
		links.setSelf(self);
		links.setNext(self + "?page=" + next);
		links.setLast(self + "?page=" + last);

		attributes.forEach(attribute -> createDataBean(self, attribute));
	}

	private void createDataBean(String self, T attributes) {
		if (data == null) {
			data = Lists.newArrayList();
		}

		DataBean<T> dataBean = new DataBean<>();
		dataBean.setId(attributes.getId());
		dataBean.setType(attributes.getClass().getSimpleName());
		dataBean.setAttributes(attributes);

		if (self != null && !"".equals(self)) {
			Links links = new Links();
			links.setSelf(self + "/" + attributes.getId());
			dataBean.setLinks(links);
		}

		data.add(dataBean);
	}
}
