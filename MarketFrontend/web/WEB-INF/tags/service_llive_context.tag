<%@tag import="com.newco.marketplace.constants.Constants"%>
<%@tag import="org.apache.commons.lang.StringUtils"%>


<%@ attribute name="scheme" required="false"%>


<% 
	String context="";
		
	context = StringUtils.isBlank(scheme)  ? request.getScheme()+"://"+request.getServerName(): scheme +"://"+request.getServerName();
	out.print(context);

%>

