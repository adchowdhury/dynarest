package com.lcs.rservice.value;

import java.util.List;

public class RestResponseObject {

	private int													responseCode						= 0;
	private String												responseMessage						= null;
	private Object												resposeObject						= null;
	private transient List<RestServiceEntityConversionConfig>	responseEntityConversionConfigList	= null;

	public List<RestServiceEntityConversionConfig> getResponseEntityConversionConfigList() {
		return responseEntityConversionConfigList;
	}

	public void setResponseEntityConversionConfigList(List<RestServiceEntityConversionConfig> responseEntityConversionConfigList) {
		this.responseEntityConversionConfigList = responseEntityConversionConfigList;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public Object getResposeObject() {
		return resposeObject;
	}

	public void setResposeObject(Object resposeObject) {
		this.resposeObject = resposeObject;
	}
}