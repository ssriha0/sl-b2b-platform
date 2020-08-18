/*
 * Created on Feb 19, 2007
 *
 * Author: dgold1
 * 
 * Revisions:
 * 
 */
package com.sears.os.factory;

import com.sears.os.context.ContextValue;

public class BeanFactory {

	static public Object getBean(String name) {
		return ContextValue.getApplicationContext().getBean(name);
	}
}
