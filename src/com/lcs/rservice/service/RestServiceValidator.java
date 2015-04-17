package com.lcs.rservice.service;

import com.lcs.rservice.value.RestServiceMethod;
import com.lcs.rservice.value.RestServiceModule;

/**
 * The application can use thread local or other mechanism to get <br />current user and based on 
 * that, detect which user can get access <br />to which data & method
 * 
 * @author: Aniruddha Dutta Chowdhury (adchowdhury@gmail.com)
 * @since: Dec 18, 2014
 */

public interface RestServiceValidator {

	boolean isAllowed(RestServiceModule module, RestServiceMethod method, Object[] parameters);
}