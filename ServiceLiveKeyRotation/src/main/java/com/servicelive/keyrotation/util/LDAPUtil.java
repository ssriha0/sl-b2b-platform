package com.servicelive.keyrotation.util;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 * @author Infosys : Jun, 2014
 */
public class LDAPUtil {
	public static LdapContext getLdapContext(String authType, String principal,
			String credentials, String url, boolean referralRequired)
			throws NamingException {
		LdapContext ctx = null;
		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, authType); // sample value
																// 'Simple'
			env.put(Context.SECURITY_PRINCIPAL, principal); // sample value
															// 'domain\\username'
			env.put(Context.SECURITY_CREDENTIALS, credentials); // sample value
																// 'password'
			env.put(Context.PROVIDER_URL, url); // sample value
												// 'ldap://172.17.10.8:389'
			if (referralRequired) {
				// add this to search with attribute other than sAMAccountName
				env.put(Context.REFERRAL, "follow");
			}
			// returns the LDAP context
			ctx = new InitialLdapContext(env, null);
		} catch (NamingException nex) {
			throw nex;
		}
		return ctx;
	}

	public static Attributes getUserAttributesSubtreeScope(String searchField,
			String searchValue, LdapContext ctx) throws Exception {
		Attributes attrs = null;
		try {
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// the following attributes would be fetched for the valid user
			String[] attrIDs = { "distinguishedName", "sn", "givenName",
					"sAMAccountName", "mail", "displayName", "manager",
					"title", "mobile", "department" };
			constraints.setReturningAttributes(attrIDs);

			// First parameter is search based, it can be
			// "CN=Users,DC=YourDomain,DC=com"
			NamingEnumeration<SearchResult> answer = ctx.search("", searchField
					+ "=" + searchValue, constraints);

			if (answer.hasMore()) {
				attrs = ((SearchResult) answer.next()).getAttributes();
			} else {
				throw new Exception("Search didn't yield a result!");
			}
		} catch (Exception ex) {
			throw ex;
		}
		return attrs;
	}
}
