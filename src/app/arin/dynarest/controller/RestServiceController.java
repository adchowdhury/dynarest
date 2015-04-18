package app.arin.dynarest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.arin.dynarest.service.RestServService;
import app.arin.dynarest.service.RestServiceValidator;
import app.arin.dynarest.util.TransformationUtil;
import app.arin.dynarest.value.RestServiceMethod;
import app.arin.dynarest.value.RestServiceMethodException;
import app.arin.dynarest.value.RestServiceMethodInputParameter;
import app.arin.dynarest.value.RestServiceModule;

import flexjson.JSONDeserializer;

@Controller
@RequestMapping(value={"/rest/"})
public class RestServiceController {
	
	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(RestServiceController.class);
	
	@Autowired
	private RestServService restServService;
	
	@Autowired
	private ApplicationContext	appContext;
	
	@RequestMapping(value="/call/**")
	public ModelAndView getRestResult(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String requestPath = request.getRequestURI();
		String restResponse = null, reqeustInputType = null, responseOutputType = null;
		Map<String, Object> inputParamMap = new HashMap<String, Object>(0);
		JSONDeserializer deserializer =  new JSONDeserializer();
		
		log.debug("\n\n========================================================================================");
		log.debug("Request for: " + requestPath);
		log.debug("========================================================================================");
		
		reqeustInputType = request.getHeader("Content-Type");
		responseOutputType = request.getHeader("Accept");
		
		if(reqeustInputType == null){
			reqeustInputType = "json";
		}else{
			reqeustInputType = reqeustInputType.indexOf("json") >= 0 ? "json" : (reqeustInputType.indexOf("xml") >= 0 ? "xml" : "json");
		}
		
		if(responseOutputType == null){
			responseOutputType = "json";
		}else{
			responseOutputType = responseOutputType.indexOf("json") >= 0 ? "json" : (responseOutputType.indexOf("xml") >= 0 ? "xml" : "json");
		}
		
		log.debug("ReqeustMethod : " + request.getMethod());
		log.debug("ReqeustInputType : " + reqeustInputType);
		log.debug("ResponseOutputType : " + responseOutputType);
		
		//preparing the input
		try {
			if(request.getMethod().equalsIgnoreCase("GET")){
				Map<String, String[]> requestInput = request.getParameterMap();
				for (Entry<String, String[]> reqElement : requestInput.entrySet()) {
					inputParamMap.put(reqElement.getKey(), reqElement.getValue()[0]);
				}
			}else{
				StringBuffer requestBody = new StringBuffer();
				String line = null;
				  try {
				    BufferedReader reader = request.getReader();
				    while ((line = reader.readLine()) != null){
				    	requestBody.append(line);
				    }
				  } catch(Exception ex) {
					  log.error(ex.getMessage(), ex);
				  }
				  inputParamMap = (HashMap<String, Object>)deserializer.deserialize(requestBody.toString());
			}
		} catch (Throwable a_th) {
			log.debug(a_th.getMessage(), a_th);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, a_th.getMessage());
			return null;
		}
		
		String[] requestPathIdentifiers = requestPath.split("/");
		if(requestPathIdentifiers == null || requestPathIdentifiers.length < 6) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
			return null;
		}
		String moduleUrl = requestPathIdentifiers[4];
		String methodUrl = requestPathIdentifiers[5];
		
		RestServiceModule restModule = restServService.findRestServiceModule(moduleUrl);
		
		if(restModule == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No module registered for URL: " + moduleUrl);
			return null;
		}
		
		RestServiceMethod restMethod = restServService.findRestServiceMethod(restModule, methodUrl);
		
		if(restMethod == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No method registered for URL: " + methodUrl + ", in module : " + moduleUrl);
			return null;
		}
		
		if(restMethod.getHttpRequestType().toString().equalsIgnoreCase(request.getMethod()) == false) {
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method : " + methodUrl + ", is allowed only with " + restMethod.getHttpRequestType() + ". Not with : " + request.getMethod());
			return null;
		}
		
		/*if(isRBacAllowed(restMethod, UserUtil.getCurrentUser()) == false) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "User : " + UserUtil.getCurrentUser() + ", do not have access to " + moduleUrl);
			return null;
		}*/
		
		String apiClass = restModule.getApiClass();
		if (appContext.containsBean(apiClass) == false) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No service available for beanName : " + apiClass);
			return null;
		}
		
		Object targetApi = appContext.getBean(apiClass);
		
		Method targetMethod = null;
		try {
			targetMethod = getMethod(targetApi.getClass(), restMethod);
		} catch (SecurityException excp) {
			log.error("User : " + UserUtil.getCurrentUser() + ", do not have access to " + moduleUrl, excp);
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "User : " + UserUtil.getCurrentUser() + ", do not have access to " + moduleUrl);
			return null;
		} catch (ClassNotFoundException excp) {
			log.error(moduleUrl + ":" + methodUrl, excp);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, excp.getMessage());
			return null;
		} catch (NoSuchMethodException excp) {
			log.error("No method registered for URL: " + methodUrl + ", in module : " + moduleUrl, excp);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No method registered for URL: " + methodUrl + ", in module : " + moduleUrl);
			return null;
		}
		
		if(targetMethod == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "No method registered for URL: " + methodUrl + ", in module : " + moduleUrl);
			return null;
		}
		
		List<RestServiceMethodInputParameter> rMethodParams = restMethod.getRestServiceMethodParameters();
		Object[] parameterValues = new Object[rMethodParams.size()];
		int iParamCounter = 0;
		Class<?>[] parameterTypes = targetMethod.getParameterTypes();
		for(RestServiceMethodInputParameter p : rMethodParams) {
			if(inputParamMap.containsKey(p.getParameterName()) == false) {
				response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "No parameter found named : " + p.getParameterName());
				return null;
			}
			if(parameterTypes[iParamCounter].isPrimitive() == false) {
				parameterValues[iParamCounter] = deserializer.deserialize(inputParamMap.get(p.getParameterName()).toString(), parameterTypes[iParamCounter]);
			}else {
				parameterValues[iParamCounter] = inputParamMap.get(p.getParameterName());
			}
			
			iParamCounter++;
		}
		
		if(appContext.containsBean("restValidator")) {
			RestServiceValidator validator = (RestServiceValidator)appContext.getBean("restValidator");
			
			if(validator != null && validator.isAllowed(restModule, restMethod, parameterValues)== false) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "User : " + UserUtil.getCurrentUser() + ", do not have access to " + moduleUrl);
				return null;
			}
		}
		
		Object returnValue = null;
		try {
			returnValue = targetMethod.invoke(targetApi, parameterValues);
		} catch (Throwable a_th) {
			a_th.printStackTrace();
			log.error(moduleUrl + ":" + methodUrl, a_th);
			Throwable th = a_th;
			if(th instanceof InvocationTargetException) {
				th = ((InvocationTargetException) th).getTargetException();
			}
			for(RestServiceMethodException excp : restMethod.getExceptions()) {
				if(th.getClass().getName().equalsIgnoreCase(excp.getExceptionClassName())) {
					response.sendError(excp.getReturnCode(), th.getMessage());
					return null;
				}
			}
			
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, a_th.getMessage());
			return null;
		}
		
		if(restMethod.getOutputParameter() != null) {
			if(returnValue == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "No record found in method: " + methodUrl + ", in module : " + moduleUrl);
				return null;
			}
			restResponse = TransformationUtil.getJsonString(returnValue, restMethod.getOutputParameter().getEntityConversionConfigList());
		}else {
			restResponse = "Call executed successfully";
		}
		
		Map model = new HashMap();
		model.put(Constants.RESPONSE_STRING_DATA, restResponse);
		
		if("json".equalsIgnoreCase(responseOutputType)) {
			model.put(Constants.RESPONSE_CONTENT_TYPE, "application/json;charset=UTF-8");
		}
		
   		return new ModelAndView(Constants.RESPONSE_STRING_VIEW, model);	
	}
	
	private Method getMethod(Class a_class, RestServiceMethod a_restMethod) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
		Method returnMethod = null;
		List<RestServiceMethodInputParameter> rMethodParams = a_restMethod.getRestServiceMethodParameters();
		
		for(Method m : a_class.getMethods()) {
			if(m.getName().equalsIgnoreCase(a_restMethod.getMethodName())) {
				boolean isFound = true;
				Class<?>[] parameterTypes = m.getParameterTypes();
				int counter = 0;
				for(Class c : parameterTypes) {
					if(c.getName().equalsIgnoreCase(rMethodParams.get(counter++).getRestServiceEntity().getEntityClassName()) == false) {
						isFound = false;
						break;
					}
				}
				
				if((parameterTypes == null || parameterTypes.length == 0)&& (rMethodParams != null && rMethodParams.isEmpty() == false)) {
					isFound = false;
				}
				
				if(parameterTypes.length != rMethodParams.size()) {
					isFound = false;
				}
				
				if(isFound) {
					returnMethod = m;
					break;
				}
			}
		}
		
		return returnMethod;
	}
	
	@RequestMapping(value="/showServices/**")
	public String listServices(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException{
		model.addAttribute("ModuleList", restServService.getAllServices());
		return "serviceList";	
	}
}