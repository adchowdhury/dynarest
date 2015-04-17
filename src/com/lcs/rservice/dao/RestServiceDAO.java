package com.lcs.rservice.dao;

import java.io.Serializable;
import java.util.List;

import com.lcs.core.common.dao.BaseDAO;
import com.lcs.rservice.value.RestServiceMethod;
import com.lcs.rservice.value.RestServiceModule;

public interface RestServiceDAO extends BaseDAO<RestServiceModule, Serializable> {

	public RestServiceModule findRestServiceModule(String moduleURL);

	public RestServiceMethod findRestServiceMethod(RestServiceModule module, String methodURL);

	public List<RestServiceModule> getAllServices();
	
	public List<RestServiceMethod> getAllMethods(Integer moduleId);
}