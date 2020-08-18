<%@tag import="com.newco.marketplace.constants.Constants"%>
<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@ attribute name="scheme" required="false"%>
<% 	String context="";
	context = StringUtils.isBlank(scheme) ? request.getScheme().trim()+"://"+request.getServerName().trim()+":"+request.getServerPort() : 
	scheme.trim() +"://"+request.getServerName().trim()+":"+request.getServerPort();
	out.print(context);
%>