package com.app.main.utility;

import java.util.List;

import org.springframework.stereotype.Component;

import com.app.main.domain.FeatureResponse;
import com.app.main.domain.PreferenceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MemoryEstimationUtil {

	public static long estimateMemorySizeBeforeTrasformation(FeatureResponse featureResponse) {
		return estimateMemorySize(featureResponse);

	}

	public static long estimateMemorySizeAfterTrasformation(List<PreferenceResponse> preferenceResponses) {
		return estimateMemorySize(preferenceResponses);

	}

	private static long estimateMemorySize(Object obj) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(obj);
			return json.getBytes().length;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return 0;

	}

}
