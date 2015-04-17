package com.lcs.rservice.service;

import java.util.List;

import com.lcs.rservice.value.RestServiceMethod;
import com.lcs.rservice.value.RestServiceModule;

public interface RestServService {
	public RestServiceModule findRestServiceModule(String moduleURL);
	
	public RestServiceMethod findRestServiceMethod(RestServiceModule module, String methodURL);
	
	public List<RestServiceModule> getAllServices();
	
	public List<RestServiceMethod> getAllMethods(Integer moduleId);
}