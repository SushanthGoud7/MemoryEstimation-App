package com.app.main.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class FeatureAttributeData {

	private String attributeCd;
	private String attributeDesc;
	private String attributeValue;
}
