package com.wiredcraft.java.backend.UserManagement.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * Class to assist actuator to show the 
 * features that were implemented as part of this
 * service
 * @author swat
 *
 */
@Component
@Endpoint(id="features")
public class FeatureEndpoint {
	
	private final Map<String, Feature> featureMap = new ConcurrentHashMap<>();
	
	/**
	 * Default constructor with ability to store 
	 * the feature information that are enabled/disabled into the Map
	 */
	public FeatureEndpoint() {
		featureMap.put("Users", new Feature(true));
		featureMap.put("Basic Authentication", new Feature(true));
		featureMap.put("OAuth/ODIC ", new Feature(false));
	}
	
	@ReadOperation
	public Map<String ,Feature> features(){
		return featureMap;
	}
	
	@ReadOperation
	public Feature feature(@Selector String featureName) {
		return featureMap.get(featureName);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	private static class Feature {
		private boolean isEnabled;
	}
}
