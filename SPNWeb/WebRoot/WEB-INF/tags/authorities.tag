<%@tag import="com.servicelive.spn.constants.SPNActionConstants"%>
<%@tag import="com.servicelive.domain.userprofile.User" %>
<%@tag import="com.servicelive.domain.userprofile.SPNUser" %>
<%@tag import="java.util.Set" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<%@ attribute name="authorityName" required="true"%>



<% 
	boolean result = false;
     if(session.getAttribute(com.servicelive.spn.constants.SPNActionConstants.USER_OBJECT_IN_SESSION) == null ){ 
    	 result =  false;
     }
     else {
    	SPNUser user =  (SPNUser)session.getAttribute(com.servicelive.spn.constants.SPNActionConstants.USER_OBJECT_IN_SESSION);
    	if(user.getUserDetails() != null) {
    		Set authorities = user.getUserDetails().getAuthorities();
    		if(authorities.contains(authorityName)) result = true;
    	}
     }
     
	
	if(result){ %>
			<jsp:doBody/>
<% }  %>

