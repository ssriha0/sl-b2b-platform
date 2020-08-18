<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="pageTitle" scope="request" value="ServiceLive Support" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title><c:out value="${pageTitle}"/></title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/support.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>

		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>



		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-945px;}
		</style>
	</head>					
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction};">
			<!--  <body class="tundra acquity simple" onload="${onloadFunction};displayBanner();">-->
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction};">
			<!-- <body class="tundra acquity" onload="${onloadFunction};displayBanner();"> -->				
		</c:otherwise>
	</c:choose>

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Support - Main"/>
	</jsp:include>	


	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
			</div>
				<div style="padding: 20px 0;"><a href="support_faq.jsp">FAQ</a> | <a href="tutorial_main.jsp">Tutorials</a></div>
				
				<!-- BEGIN CENTER -->
				<div class="hero clearfix" style="margin-top: 30px;" id="supportHero">
					<div class="content">
						<p class="paddingBtm">
							<img src="${staticContextPath}/images/support/hdr_supportHero.gif"
								alt="At Your Service" title="At Your Service" />
						</p>
						<p>
							ServiceLive has simplified customer support in the same way we've streamlined the service business. 
							The ServiceLive support team is never more than a phone call away. If you have questions or need 
							technical support, contact us by telephone at 1-888-549-0640. You may be able to 
							save time by searching for your answer in the FAQ's and tutorials below.
						</p>
					</div>
				</div>
				<div class="landingContent">
					<div class="content">
						<p>
							Take a step-by-step tour through various ServiceLive features with our 
							<a title="Tutorials" href="tutorial_main.jsp">Tutorials</a>, and find the answer to 
							commonly-asked questions in our <a title="FAQ" href="support_faq.jsp">FAQ</a>. 
							In addition, you can post questions and share your expertise with 
							other members when you join ServiceLive's online community.
						</p>
					</div>
					<div class="clearfix">
						<div class="landingBox">
							<img src="${staticContextPath}/images/support/hdr_faq.gif" />
							<div class="content">
								<p>
									From service orders and contracts to account information and fees, we've compiled the questions and 
									answers of ServiceLive users for your reference. 
									<br /><strong><a title="FAQ" href="support_faq.jsp">&raquo; More</a></strong>
								</p>
							</div>
						</div>
						<div class="landingBox" style="width: 310px; float: right;">
							<img src="${staticContextPath}/images/support/hdr_tutorials.gif" />
							<div class="content">
								<p>
									Need a general overview or specific instructions for ServiceLive tasks? Try our tutorials! 
									Check back often for updates and special programs.
									<br /><strong><a title="Tutorials" href="tutorial_main.jsp">&raquo; More</a></strong>
								</p>
							</div>
						</div>
					</div>
					<div style="margin-left: -5px; padding-top: 20px;" class="content clearfix">
						<img src="${staticContextPath}/images/support/hdr_call.gif" />
						<p>
							If you need an immediate referral or help finding a qualified service provider, 
							call the ServiceLive Support Team at 1.888.549.0640, weekdays between 7 am and 7 
							pm Central Standard Time.
						</p>
					</div>
				</div>
			</div>
			<!-- END CENTER   -->
		
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</body>
</html>
