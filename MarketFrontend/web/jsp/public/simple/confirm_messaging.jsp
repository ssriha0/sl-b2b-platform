<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="wizardInProgress" scope="session"	value="" />
<c:set var="pageTitle" scope="request" value="Thank You" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String mmhURL = "", mmhURLParameters = "";
	try {
		mmhURL = (String) request.getAttribute("mmhURL");
		if (!(mmhURL.length() > 0))
			throw new NullPointerException(
					"mmhURL attribute not present");
		try {
			mmhURLParameters += "user.firstName="
					+ java.net.URLEncoder.encode(request.getAttribute(
							"firstName").toString(), "UTF-8");
		} catch (Exception ex) {
		}
		try {
			mmhURLParameters += "&user.lastName="
					+ java.net.URLEncoder.encode(request.getAttribute(
							"lastName").toString(), "UTF-8");
		} catch (Exception ex) {
		}
		try {
			mmhURLParameters += "&user.maintZipCode="
					+ java.net.URLEncoder.encode(request.getAttribute(
							"zipCode").toString(), "UTF-8");
		} catch (Exception ex) {
		}
		try {
			mmhURLParameters += "&user.email="
					+ java.net.URLEncoder.encode(request.getAttribute(
							"email").toString(), "UTF-8");
		} catch (Exception ex) {
		}
		mmhURL += "?" + mmhURLParameters;
	} catch (Exception ex) {
		mmhURL = "http://www.managemyhome.com";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title><c:out value="${pageTitle}" />
		</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript" src="${staticContextPath}/js/vars.js"></script>

		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<c:if
			test="${messageType == 'Signup Confirmation' || messageType == 'Order Confirmation'}">
			<script language="javascript" type="text/javascript">
			jQuery.noConflict();
		    jQuery(document).ready(function($) {
	     		$('#mmh').jqm({modal:true, toTop: true});
	     		$('#mmh').jqmShow();
			 });
			</script>
		</c:if>
		<style>
#mmh-form label {
	display: block;
	clear: left;
	margin: 2px 0;
}

#mmh-form label span {
	margin-right: 10px;
	color: #fff;
	display: block;
	float: left;
	clear: left;
	width: 150px;
	text-align: right;
	font-weight: bold;
}

#mmh-form input {
	font-size: 10px;
}
</style>

	<c:choose>
		<c:when test="${messageType == 'Signup Confirmation'}">
			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
				 <jsp:param name="PageName" value="JoinNow.simple.registrationComplete"/>
			</jsp:include>
		</c:when>
		<c:when test="${messageType == 'First Order Confirmation'}">
			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
				 <jsp:param name="PageName" value="home.orderPosted"/>
			</jsp:include>
		</c:when>
		<c:when test="${messageType == 'Order Confirmation'}">
			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
				 <jsp:param name="PageName" value="home.orderPosted"/>
			</jsp:include>
		</c:when>
	</c:choose>
	
	</head>

	<body class="tundra acquity">


		<div id="page_margins">
			<div id="page">
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				</div>

				<!-- START CENTER -->
				<div id="hpWrap" class="clearfix">
					<div id="hpContent">
						<div id="hpIntro">
							<!-- Include this if they just signed up (ANON|SIMPLE) (loggedout state, include loggedout header on page) -->
							<c:choose>
								<c:when test="${messageType == 'Signup Confirmation'}">
									<h2>
										<fmt:message bundle="${serviceliveCopyBundle}"
											key="confirmMessage.email.sent" />
										${email}
									</h2>
									<fmt:message bundle="${serviceliveCopyBundle}"
										key="confirmMessage.check.email" />
								<div id="mmhLink">
									<a href="<%=mmhURL%>" target="new">Join MMH</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${contextPath}/">Home</a>
								</div>
								<div id="mmhBanner">
								<a href="<%=mmhURL%>" target="new"><img src="${staticContextPath}/images/mmh.png" width="166" height="27" border="0" alt="ManageMyHome"/></a>
									<span id="mmhSignupMessage" style="">Signup and receive instant access to:</span> 
									<br /><br />
									<div id="mmhVerbiage" style="clear: left;">
									<ul>
									<li>- Creating a secure home inventory</li>
									<li>- Protection Agreement planning &amp; tracking</li>
									<li>- Custom home maintenance plans</li>
									<li>- Personalized expert advice</li>
									<li style="padding-left: 80px;">.... and much, much more!</li>
									</ul>
								</div>
								</c:when>
								<c:when test="${messageType == 'First Order Confirmation'}">
									<!-- Include this if they are (ANON|SIMPLE|CONTINUE) (loggedout state, include loggedout header on page) -->
									<fmt:message bundle="${serviceliveCopyBundle}"
										key="confirmMessage.posted.first" />
								</c:when>
								<c:when test="${messageType == 'Order Confirmation'}">
									<c:choose>
										<c:when test="${firstorder}">
											<fmt:message bundle="${serviceliveCopyBundle}"
												key="confirmMessage.posted.first">
												<fmt:param value="${SERVICE_ORDER_ID}" />
											</fmt:message>
								<div id="mmhLink">
									<a href="<%=mmhURL%>" target="new">Join MMH</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${contextPath}/">Home</a>
								</div>
								<div id="mmhBanner">
								<a href="<%=mmhURL%>" target="new"><img src="${staticContextPath}/images/mmh.png" width="166" height="27" border="0" alt="ManageMyHome"/></a>
									<span id="mmhSignupMessage" style="">Signup and receive instant access to:</span> 
									<br /><br />
									<div id="mmhVerbiage" style="clear: left;">
									<ul>
									<li>- Creating a secure home inventory</li>
									<li>- Protection Agreement planning &amp; tracking</li>
									<li>- Custom home maintenance plans</li>
									<li>- Personalized expert advice</li>
									<li style="padding-left: 80px;">.... and much, much more!</li>
									</ul>
								</div>
										</c:when>
										<c:otherwise>
											<fmt:message bundle="${serviceliveCopyBundle}"
												key="confirmMessage.posted.subsequent">
												<fmt:param value="${SERVICE_ORDER_ID}" />
											</fmt:message>
										</c:otherwise>
									</c:choose>
								</c:when>
							</c:choose>
						</div>
					</div>
				</div>
				<!-- END CENTER   -->

				<c:if test="${messageType == 'Signup Confirmation'}">
				<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
				<!-- Activity Name for this tag is:Home/Office Conversion Tag -->
				<!-- Web site URL where tag should be placed: Home/Office confirmation/thank you Page -->
				<!-- This tag must be placed within the opening <body> tag, as close to the beginning of it as possible-->
				<!-- Creation Date:12/4/2008 -->
				<SCRIPT language="JavaScript">
				var axel = Math.random()+"";
				var a = axel * 10000000000000;
				document.write('<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=homeo657;ord=1;num='+ a + '?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>');
				</SCRIPT>
				<NOSCRIPT>
				<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=homeo657;ord=1;num=1?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>
				</NOSCRIPT>
				<!-- End of DoubleClick Spotlight Tag: Please do not remove -->
				</c:if>
				<c:if test="${messageType == 'First Order Confirmation' or messageType == 'Order Confirmation'}">
				<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
				<!-- Activity Name for this tag is:Simple-Posting-Conversion -->
				<!-- This tag must be placed within the opening <body> tag, as close to the beginning of it as possible-->
				<!-- Creation Date:2/6/2009 -->
				<SCRIPT language="JavaScript">
				var axel = Math.random()+"";
				var a = axel * 10000000000000;
				document.write('<IFRAME SRC="https://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=simpl049;ord=1;num='+ a + '?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>');
				</SCRIPT>
				<NOSCRIPT>
				<IFRAME SRC="https://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=simpl049;ord=1;num=1?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>
				</NOSCRIPT>
				<!-- End of DoubleClick Spotlight Tag: Please do not remove-->
				</c:if>

				<!-- START FOOTER NAV -->
				<jsp:include page="/jsp/public/common/globalFooter.jsp" />
				<!-- END FOOTER NAV -->
			</div>
			</div>
		</div>
	</body>
</html>
