package com.lcs.rservice.value;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "lcs_tbl_rservice_method_input_parameters")
public class RestServiceMethodInputParameter implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "parameter_id", nullable = false)
	private String parameterId;
	
	@Column(name = "method_id")
	private String methodId;
	
	@Column(name = "sequence")
	private Integer sequence = 0;
	
	@Column(name = "parameter_name")
	private String parameterName;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "entity_id", referencedColumnName = "entity_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private RestServiceEntity restServiceEntity;
	
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public RestServiceEntity getRestServiceEntity() {
		return restServiceEntity;
	}

	public void setRestServiceEntity(RestServiceEntity restServiceEntity) {
		this.restServiceEntity = restServiceEntity;
	}
}
