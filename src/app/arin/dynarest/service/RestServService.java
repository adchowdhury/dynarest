package app.arin.dynarest.service;

import java.util.List;

import app.arin.dynarest.value.RestServiceMethod;
import app.arin.dynarest.value.RestServiceModule;

public interface RestServService {
	public RestServiceModule findRestServiceModule(String moduleURL);
	
	public RestServiceMethod findRestServiceMethod(RestServiceModule module, String methodURL);
	
	public List<RestServiceModule> getAllServices();
	
	public List<RestServiceMethod> getAllMethods(Integer moduleId);
}