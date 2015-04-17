package com.lcs.rservice.exception;

/**
 * @author: Aniruddha Dutta Chowdhury (adchowdhury@gmail.com)
 * @since: Dec 16, 2014
 */

public class MouduleNotFoundException extends RuntimeException {

	public MouduleNotFoundException(String string) {
		super(string);
	}
}