<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.newco.batch.ABatchProcess" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Service Order - Active and Expire Batch Job</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
 

  </head>
  
  <body>
    <%
		ABatchProcess theProcess = null;
		
		try {
			  System.out.println("Batch Process Running");
			 theProcess = (ABatchProcess) Class.forName("com.newco.batch.serviceorder.UpdateServiceOrderProcessor").newInstance();
	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		//theProcess.setArgs(args);
		theProcess.process();
	%>
  </body>
</html>
