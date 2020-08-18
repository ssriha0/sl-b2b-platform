/*
 * Copyright 2005-2008 hdiv.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hdiv.config;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hdiv.validator.IValidation;

/**
 * Class containing HDIV configuration initialized from Spring Factory.
 * 
 * @author Roberto Velasco
 * @author Gorka Vicente
 */
public class SLHDIVConfig extends HDIVConfig{

	/**
	 * Map with the pages that *will* be Treated by the HDIV filter. The init pages
	 * are initialized by the Spring factory.
	 */
	private Hashtable startPages;
 
	/**
	 * Map of the validated actions that are init pages. It is necessary to create
	 * this map to improve the efficiency at checking init-pages.
	 * 
	 * @since HDIV 1.1.1
	 */
	private Hashtable matchedPages; 

	/**
	 * Error page to which HDIV will redirect the request if it doesn't pass the HDIV
	 * validation.
	 */
	private String errorPage;
  

	/**
	 * Checks if <code>target</code> is an init action, in which case it will not
	 * be treated by HDIV.
	 * 
	 * @param target target name
	 * @return True if <code>target</code> is an init action. False otherwise.
	 */
	@Override
	public boolean isStartPage(String target) {

		if (this.matchedPages.containsKey(target)) {
			return false;
		}

		String value = this.checkValue(target, this.startPages);
		if (value == null) {
			return true;
		}

		this.matchedPages.put(target, value);
		return false;
	}

	@Override
	public String getErrorPage() {
		return errorPage;
	}

	@Override
	public void setErrorPage(String errorPage) {
		
		if (!errorPage.startsWith("/")) {
			errorPage = "/" + errorPage;
		}		
		this.errorPage = errorPage; 
	} 
	 
	/**
	 * It creates a map from the list of start pages defined by the user.
	 * 
	 * @param userStartPages list of start pages defined by the user
	 */
	@Override
	public void setUserStartPages(List userStartPages) {

		this.matchedPages = new Hashtable();
		this.startPages = new Hashtable();

		String currentPattern;
		for (int i = 0; i < userStartPages.size(); i++) {

			currentPattern = (String) userStartPages.get(i);
			this.startPages.put(currentPattern, Pattern.compile(currentPattern));
		}
	}
 
}
