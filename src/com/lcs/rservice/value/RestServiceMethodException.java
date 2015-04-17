package com.lcs.rservice.value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lcs_tbl_rservice_method_exception")
public class RestServiceMethodException {

	private static final long	serialVersionUID	= 1L;

	@Id
	@Column(name = "exception_id", nullable = false)
	private String				exceptionId			= null;

	@Column(name = "method_id")
	private String				methodId			= null;

	@Column(name = "exception_class_name")
	private String				exceptionClassName	= null;

	@Column(name = "return_code")
	private Integer				returnCode			= 500;

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	public String getExceptionClassName() {
		return exceptionClassName;
	}

	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}

	public Integer getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}
}