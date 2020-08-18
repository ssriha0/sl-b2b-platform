<%@tag import="com.newco.marketplace.security.ActivityMapper"%>
<%@tag import="com.newco.marketplace.constants.Constants"%>
<%@tag import="com.newco.marketplace.auth.SecurityContext"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<%@ attribute name="actionName" required="true"%>
<%@ attribute name="isAdmin" required="false"%>


<% 
	boolean result = false;
	SecurityContext sc =  (SecurityContext) session.getAttribute(Constants.SESSION.SECURITY_CONTEXT);
	if(sc == null)
		result = false;
	else{
	
	 if(isAdmin != null && "true".equalsIgnoreCase(isAdmin)) {
	    result = ActivityMapper.canAdminDoAction(actionName,sc.getSlAdminUName());	 	 
	 }
	 else{
		Integer role_id = sc.getRoleId();
		result = ActivityMapper.canDoAction(actionName+"-"+role_id,sc );
	 }
	}	
	
	if(result){ %>
			<jsp:doBody/>
<% }  %>

