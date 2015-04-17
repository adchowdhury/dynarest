package com.lcs.rservice.util;

import java.util.ArrayList;
import java.util.List;

import com.lcs.rservice.value.RestServiceEntityConversionConfig;
import com.lcs.rservice.value.RestServiceEntityConversionConfigTypeEnum;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

/**
 * @author: Aniruddha Dutta Chowdhury (adchowdhury@gmail.com)
 * @since: Dec 16, 2014
 */

public final class TransformationUtil {
	
	public static Object getObjectFromJson(String source, Class targetCass, List<RestServiceEntityConversionConfig> responseEntityConversionConfigList) {
		List<String> excludeList = new ArrayList<String>(0);
		List<String> includeList = new ArrayList<String>(0);
		//Map<String, String> transformerClassList = new HashMap<String, String>(0);
		
		if (responseEntityConversionConfigList != null && responseEntityConversionConfigList.size() > 0) {
			for (RestServiceEntityConversionConfig restServiceEntityConversionConfig : responseEntityConversionConfigList) {
				if (restServiceEntityConversionConfig.getConfigType() == RestServiceEntityConversionConfigTypeEnum.EXCLUDE) {
					excludeList.add(restServiceEntityConversionConfig.getPropertyName());
				} else {
					includeList.add(restServiceEntityConversionConfig.getPropertyName());
					/*if (restServiceEntityConversionConfig.getTransformerClass() != null) {
						transformerClassList.put(restServiceEntityConversionConfig.getPropertyName(), restServiceEntityConversionConfig.getTransformerClass());
					}*/
				}
			}
		}
		
		JSONDeserializer deserializer =  new JSONDeserializer();
		return deserializer.deserialize(source, targetCass);
	}
	
	public static String getJsonString(Object sourceObject, List<RestServiceEntityConversionConfig>	responseEntityConversionConfigList) {
		List<String> excludeList = new ArrayList<String>(0);
		List<String> includeList = new ArrayList<String>(0);
		//Map<String, String> transformerClassList = new HashMap<String, String>(0);
		
		if (responseEntityConversionConfigList != null && responseEntityConversionConfigList.size() > 0) {
			for (RestServiceEntityConversionConfig restServiceEntityConversionConfig : responseEntityConversionConfigList) {
				if (restServiceEntityConversionConfig.getConfigType() == RestServiceEntityConversionConfigTypeEnum.EXCLUDE) {
					excludeList.add(restServiceEntityConversionConfig.getPropertyName());
				} else {
					includeList.add(restServiceEntityConversionConfig.getPropertyName());
					/*if (restServiceEntityConversionConfig.getTransformerClass() != null) {
						transformerClassList.put(restServiceEntityConversionConfig.getPropertyName(), restServiceEntityConversionConfig.getTransformerClass());
					}*/
				}
			}
		}
		
		if (sourceObject != null) {
			JSONSerializer serializer = new JSONSerializer();
			if (excludeList != null && excludeList.isEmpty() == false) {
				for (String excludeItem : excludeList) {
					serializer.exclude("*." + excludeItem);
				}
			}
			serializer.exclude("responseEntityConversionConfigList");
			if (includeList != null && includeList.isEmpty() == false) {
				for (String includeItem : includeList) {
					serializer.include("*." + includeItem);
				}
			}

			/*if (transformerClassList != null && transformerClassList.isEmpty() == false) {
				for (String tranformerItem : transformerClassList.keySet()) {
					serializer.transform((Transformer) Class.forName(transformerClassList.get(tranformerItem)), (Class) tranformerItem);
				}
			}*/
			return serializer.deepSerialize(sourceObject);
		}
		return null;
	}
}