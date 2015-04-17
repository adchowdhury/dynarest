package com.lcs.rservice.value;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lcs_tbl_rservice_method_input_parameters")
public class RestServiceMethodInputParameters implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "parameter_id")
	private String parameterId;
	
	@Column(name = "sequence")
	private Integer sequence = 0;
	
	@Column(name = "parameter_name")
	private String parameterName;

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

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
	
}
