package com.app.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.main.domain.FeatureAttributeData;
import com.app.main.domain.FeatureResponse;
import com.app.main.domain.LocationResponse;
import com.app.main.domain.PreferenceAttributesResponse;
import com.app.main.domain.PreferenceResponse;
import com.app.main.utility.MemoryEstimationUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class MemoryEstimationAppApplication {

//	@Autowired
//	MemoryEstimationUtil memoryEstimationUtil;

	public static void main(String[] args) {
		SpringApplication.run(MemoryEstimationAppApplication.class, args);

		FeatureResponse featureResponse = createFeatureResponse();
		log.info("FeatureResposne: " + featureResponse);

		long beforeTransformSize = MemoryEstimationUtil.estimateMemorySizeBeforeTrasformation(featureResponse);
		log.info("Memory size before: " + beforeTransformSize);

		List<PreferenceResponse> preferenceResponses = transformAndFilter(featureResponse);
		long afterTransformSize = MemoryEstimationUtil.estimateMemorySizeAfterTrasformation(preferenceResponses);
		for (PreferenceResponse pereference : preferenceResponses) {
			log.info("Preferennce list: " + pereference);
		}
		log.info("Memory size after: " + afterTransformSize);

		long memoryDifference = beforeTransformSize - afterTransformSize;
		log.info("Memory size Difference bw before and after transmission: " + memoryDifference);

	}

	private static FeatureResponse createFeatureResponse() {
		List<FeatureAttributeData> featureAttributeDataList = new ArrayList<FeatureAttributeData>();
		featureAttributeDataList.add(FeatureAttributeData.builder().attributeCd("SDOC_FLG")
				.attributeDesc("Same Day On Call").attributeValue("N").build());

		featureAttributeDataList.add(FeatureAttributeData.builder().attributeCd("EXECUTION_EFFECTIVE_DATE")
				.attributeDesc("Execution Effective Date").attributeValue("01/05/2024").build());

		featureAttributeDataList.add(FeatureAttributeData.builder().attributeCd("FXG_LOCATION_ID")
				.attributeDesc("FXG Location ID").attributeValue("4763").build());

		featureAttributeDataList.add(FeatureAttributeData.builder().attributeCd("PLANNING_DATA_EFFECTIVE_DATE")
				.attributeDesc("Planning Data Effective Date").attributeValue("01/05/2024").build());

		List<PreferenceResponse> preferenceResponseList = new ArrayList<PreferenceResponse>();

		PreferenceAttributesResponse attributesResponse = PreferenceAttributesResponse.builder().featureCd("FRO")
				.prefernceLevel("Location").countryCd("US").locationCd("ONYA").attributeList(featureAttributeDataList)
				.build();
		PreferenceResponse preferenceResponse = new PreferenceResponse();
		preferenceResponse.setPreferenceResponse(attributesResponse);
		preferenceResponseList.add(preferenceResponse);

		LocationResponse locationResponse = LocationResponse.builder().locationresponse(preferenceResponseList).build();

		return FeatureResponse.builder().featureResponse(locationResponse).build();

	}

	private static List<PreferenceResponse> transformAndFilter(FeatureResponse featureResponse) {
		List<PreferenceResponse> preferenceResponseList = featureResponse.getFeatureResponse().getLocationresponse();
		preferenceResponseList.forEach(preferenceResponse -> {
			List<FeatureAttributeData> filteredAttributeDataList = preferenceResponse.getPreferenceResponse()
					.getAttributeList().stream().filter(e -> "EXECUTION_EFFECTIVE_DATE".equals(e.getAttributeCd())
							|| "FXG_LOCATION_ID".equals(e.getAttributeCd()))
					.collect(Collectors.toList());
			preferenceResponse.getPreferenceResponse().setAttributeList(filteredAttributeDataList);
		});

		return preferenceResponseList;

	}

}
