package com.lcs.rservice.value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "lcs_tbl_rservice_method")
public class RestServiceMethod implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "method_id", nullable = false)
	private String methodId;
	
	@Column(name = "method_name")
	private String methodName;
	
	@Column(name = "method_url")
	private String methodUrl;
	
	@Column(name = "module_id")
	private Integer moduleId;
	
	@Column(name = "method_display_name")
	private String methodDisplayName;
	
	@Column(name = "method_description")
	private String methodDescription;
	
	@Column(name = "http_request_type_id")
	@Enumerated(EnumType.ORDINAL)
	private RestServiceHttpRequestTypeEnum httpRequestType;
	
	@Column(name = "rbac_module_operation_id")
	private Integer rbacModuleOperationId;
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "method_id", referencedColumnName = "method_id")
	@OrderBy(clause = "sequence")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RestServiceMethodInputParameter> restServiceMethodParameters;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "output_parameter", referencedColumnName = "entity_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private RestServiceEntity outputParameter;
	
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "method_id", referencedColumnName = "method_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<RestServiceMethodException> exceptions;
	
	public Set<RestServiceMethodException> getExceptions() {
		if(exceptions == null) {
			return new HashSet<RestServiceMethodException>();
		}
		return exceptions;
	}
	
	public void setExceptions(Set<RestServiceMethodException> exceptions) {
		this.exceptions = exceptions;
	}
	
	public RestServiceEntity getOutputParameter() {
		return outputParameter;
	}
	
	public void setOutputParameter(RestServiceEntity outputParameter) {
		this.outputParameter = outputParameter;
	}

	public String getMethodId() {
		return methodId;
	}


	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}


	public String getMethodName() {
		return methodName;
	}


	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}


	public String getMethodUrl() {
		return methodUrl;
	}


	public void setMethodUrl(String methodUrl) {
		this.methodUrl = methodUrl;
	}


	public Integer getModuleId() {
		return moduleId;
	}


	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}


	public String getMethodDisplayName() {
		return methodDisplayName;
	}


	public void setMethodDisplayName(String methodDisplayName) {
		this.methodDisplayName = methodDisplayName;
	}


	public String getMethodDescription() {
		return methodDescription;
	}


	public void setMethodDescription(String methodDescription) {
		this.methodDescription = methodDescription;
	}


	public RestServiceHttpRequestTypeEnum getHttpRequestType() {
		return httpRequestType;
	}


	public void setHttpRequestType(
			RestServiceHttpRequestTypeEnum httpRequestType) {
		this.httpRequestType = httpRequestType;
	}


	public Integer getRbacModuleOperationId() {
		return rbacModuleOperationId;
	}


	public void setRbacModuleOperationId(Integer rbacModuleOperationId) {
		this.rbacModuleOperationId = rbacModuleOperationId;
	}


	public List<RestServiceMethodInputParameter> getRestServiceMethodParameters() {
		if(restServiceMethodParameters == null) {
			return new ArrayList<RestServiceMethodInputParameter>();
		}
		return restServiceMethodParameters;
	}


	public void setRestServiceMethodParameters(
			List<RestServiceMethodInputParameter> restServiceMethodParameters) {
		this.restServiceMethodParameters = restServiceMethodParameters;
	}
	
}
