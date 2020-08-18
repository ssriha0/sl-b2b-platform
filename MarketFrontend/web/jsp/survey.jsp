<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyVO" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <base href="<%=basePath%>">
    
    <title>My JSP 'survey.jsp' starting page</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
	
<%
	SurveyVO vo = (SurveyVO)request.getAttribute("surveyvo");
 	ArrayList questionList=vo.getQuestions() ;
 	request.setAttribute("questionList",questionList);
 %>
  </head>
  
  <body>
  <%System.out.println("Print the value for imn8uuyg");%>
  <c:forEach var="question" items="${questionList}">
  <c:out value="${question.questionText}"></c:out>
  <br>
  <%System.out.println("Print the value for ");%>
  <c:forEach var="answer" items="${question.answers}">
  <c:out value="${answer.answerText}"></c:out>
  <br>
  </c:forEach>
  </c:forEach>
     <br>
  </body>
</html>




