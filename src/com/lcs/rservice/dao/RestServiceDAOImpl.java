package com.lcs.rservice.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lcs.core.common.dao.BaseAbstractDAO;
import com.lcs.rservice.value.RestServiceMethod;
import com.lcs.rservice.value.RestServiceModule;

@Repository("restServiceModule")
public class RestServiceDAOImpl extends BaseAbstractDAO<RestServiceModule, Serializable> implements RestServiceDAO {

	@Override
	public RestServiceModule findRestServiceModule(String moduleURL) {
		List<RestServiceModule> restServiceModules =  getHibernateTemplate().find("from RestServiceModule where moduleUrl = ?", moduleURL);
		if(restServiceModules != null && restServiceModules.size() > 0){
			return restServiceModules.get(0);
		}
		return null;
	}

	@Override
	public RestServiceMethod findRestServiceMethod(RestServiceModule module, String methodURL) {
		List<RestServiceMethod> restServiceMethods =  getHibernateTemplate().find("from RestServiceMethod where moduleId = ? and methodUrl = ?", module.getModuleId(), methodURL);
		if(restServiceMethods != null && restServiceMethods.size() > 0){
			return restServiceMethods.get(0);
		}
		return null;
	}

	@Override
	public List<RestServiceModule> getAllServices() {
		return getHibernateTemplate().find("from RestServiceModule");
	}

	@Override
	public List<RestServiceMethod> getAllMethods(Integer moduleId) {
		List<RestServiceMethod> restServiceMethods =  getHibernateTemplate().find("from RestServiceMethod where moduleId = ? ", moduleId);
		return restServiceMethods;
	}
}