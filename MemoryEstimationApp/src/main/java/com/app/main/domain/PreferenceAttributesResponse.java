package com.app.main.domain;

import java.util.List;



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
public class PreferenceAttributesResponse {

	private String countryCd;
	private String featureCd;
	private String locationCd;
	private String prefernceLevel;
	List<FeatureAttributeData> attributeList;

}
