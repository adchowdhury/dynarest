package com.lcs.rservice.value;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "lcs_tbl_rservice_entity")
public class RestServiceEntity implements Serializable {

	private static final long						serialVersionUID	= 1L;

	@Id
	@Column(name = "entity_id", nullable = false)
	private String									entityId;

	@Column(name = "entity_class_name")
	private String									entityClassName;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "entity_id", referencedColumnName = "entity_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RestServiceEntityConversionConfig>	entityConversionConfigList;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public List<RestServiceEntityConversionConfig> getEntityConversionConfigList() {
		return entityConversionConfigList;
	}

	public void setEntityConversionConfigList(List<RestServiceEntityConversionConfig> entityConversionConfigList) {
		this.entityConversionConfigList = entityConversionConfigList;
	}

}
