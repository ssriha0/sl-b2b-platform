<%@ page import="org.apache.commons.lang.StringEscapeUtils,org.owasp.esapi.ESAPI;" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="pageTitle" scope="request" value="" />
<c:set var="editMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.EDIT_MODE %>"/>
<c:set var="copyMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.COPY_MODE %>"/>
<c:set var="staticContextPath" scope="request" value="/ServiceLiveWebUtil" />

<%--don't import additional css and js files in header --%>
<c:set var="noJs" value="true" scope="request" />
<c:set var="noCss" value="true" scope="request" />
<% 
String errorMessageVar=(String)request.getParameter("errorMessage");
String vulnerrorMessageVar = ESAPI.encoder().canonicalize(errorMessageVar);
String vulnerrorMessage = ESAPI.encoder().encodeForHTML(vulnerrorMessageVar);
%>
<c:set var="vulnerrorMessageVar" value="<%=vulnerrorMessage%>" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive : ${pageTitle}</title>
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script language="javascript" type="text/javascript">
			if (top.location.href.indexOf('errorAction.action') < 0) {
			top.location = '<c:out value="${contextPath}"/>/errorAction.action?errorMessage=<c:out value="<%=vulnerrorMessage%>"/>';
			}
		</script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	         .ie7 .bannerDiv{margin-left:-940px;}  
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
	</head>
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity">
		</c:otherwise>
	</c:choose>
	
	<div id="page_margins">
		<div id="page">

			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
			</div>

			<div id="hpWrap" class="shaded clearfix">

				<div class="colLeft711" style="height: 150px; padding-top: 50px">
					<div class="content">
						We apologize for the error.
						<br /><br />
						<c:out value="${vulnerrorMessageVar}" />
						<br />
						Return URL:
						<a href="${returnURL }">Return</a>
					</div>
				</div>

			</div>

		</div>
		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
	</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		<jsp:param name="PageName" value="Common Error" />
	</jsp:include>
	</body>
</html>
