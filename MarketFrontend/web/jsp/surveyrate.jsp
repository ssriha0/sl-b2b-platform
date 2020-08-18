<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%@page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyRatingSummaryVO"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  
    <base href="<%=basePath%>">
    
    <title>My JSP 'surveyrate.jsp' starting page</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<%
 	ArrayList voBuyerRating= (ArrayList)request.getAttribute("buyerratings");
 	System.out.println("Size of Array in JSP="+voBuyerRating.size());
 %>
  </head>
  
  <body>
  <%System.out.println("Print the value for score");%>
  	<c:forEach var="buyerRating" items="${voBuyerRating}">
  	<c:out value="${buyerRating.overallScore}"></c:out>
  	<%System.out.println("Print the buyerrating ");%>
  	</c:forEach>
    
  </body>
</html>
