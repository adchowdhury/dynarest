package com.lcs.rservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lcs.rservice.dao.RestServiceDAO;
import com.lcs.rservice.value.RestServiceMethod;
import com.lcs.rservice.value.RestServiceModule;

@Service("restServService")
@Transactional(readOnly = true)
public class RestServServiceImpl implements RestServService {
	
	private static final Logger log = LoggerFactory.getLogger(RestServServiceImpl.class);

	@Autowired
	private RestServiceDAO		restServiceDAO;

	@Override
	public RestServiceModule findRestServiceModule(String moduleURL) {
		return restServiceDAO.findRestServiceModule(moduleURL);
	}

	@Override
	public RestServiceMethod findRestServiceMethod(RestServiceModule module, String methodURL) {
		return restServiceDAO.findRestServiceMethod(module, methodURL);
	}

	@Override
	public List<RestServiceModule> getAllServices() {
		return restServiceDAO.getAllServices();
	}

	@Override
	public List<RestServiceMethod> getAllMethods(Integer moduleId) {
		return restServiceDAO.getAllMethods(moduleId);
	}
}