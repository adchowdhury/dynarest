package app.arin.dynarest.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import app.arin.dynarest.value.RestServiceMethod;
import app.arin.dynarest.value.RestServiceModule;

public class RestServiceDAOImpl extends HibernateDaoSupport implements RestServiceDAO {

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
		List<RestServiceMethod> restServiceMethods =  getHibernateTemplate().find("from RestServiceMethod where moduleId = ? and methodUrl = ?", new Object[] {module.getModuleId(), methodURL});
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