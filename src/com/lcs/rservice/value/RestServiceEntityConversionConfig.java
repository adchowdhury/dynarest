package com.lcs.rservice.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lcs_tbl_rservice_entity_conversion_config")
public class RestServiceEntityConversionConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "config_id" , nullable = false)
	private String configId;
	
	@Column(name = "config_type")
	private RestServiceEntityConversionConfigTypeEnum configType;
	
	@Column(name = "entity_id")
	private String entityId;
	
	@Column(name = "property_name")
	private String propertyName;
	
	@Column(name = "transformer_class")
	private String transformerClass;

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public RestServiceEntityConversionConfigTypeEnum getConfigType() {
		return configType;
	}

	public void setConfigType(RestServiceEntityConversionConfigTypeEnum configType) {
		this.configType = configType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getTransformerClass() {
		return transformerClass;
	}

	public void setTransformer_class(String transformerClass) {
		this.transformerClass = transformerClass;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
}
