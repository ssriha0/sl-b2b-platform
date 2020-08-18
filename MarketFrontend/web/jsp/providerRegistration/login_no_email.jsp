<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath()%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProviderRegistration.loginNoEmail"/>
	</jsp:include>	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

	<%@ taglib prefix="s" uri="/struts-tags"%>
	<head>
		<title>ServiceLive [Log In]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modules.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/contact.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/login.css" />

		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>



	</head>
<body id="fixaux" class="acquity tundra">
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Provider Registration - Login No Email"/>
		</jsp:include>	
	    
		<div id="page_margins">
			<div id="page">
				<div id="content_right_header_text">
					<%@ include file="message.jsp"%>
				</div>
				<!-- START HEADER -->
				<div id="headerShort-rightRail">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					<div id="pageHeader">
						<img
							src="${staticContextPath}/images/btn/hdr_login.gif"
							alt="Log In" title="Log In" width="53" height="17">
					</div>
					
				</div>
				<!-- END HEADER -->
				<s:form action="forgetUsernameAction" theme="simple">
					<div class="colRight255 clearfix">
					</div>
					<div class="colLeft711" style="padding-bottom: 50px;">
						<div class="content">
							<h3>
								Your e-mail address/ username is not in our system.
							</h3>
							<p class="paddingBtm">
								If you entered the wrong e-mail address, select 'try again.' If
								you haven't yet created a ServiceLive account, decide which kind
								of account you'd like to establish, and click the 'Join Now'
								button.
							<p>
							<a href="${loginActionPath}/loginAction.action">
								<img width="74" type="image" height="20" class="btnPSContinue continue"
									style="background-image: url(${staticContextPath}/images/btn/tryAgain.gif);"
									src="${staticContextPath}/images/common/spacer.gif" />
							</a>		
							</p>
						</div>
						<div class="landingContent">
							<div class="landingBucket marginRight">
								<img
									src="${staticContextPath}/images/icons/Businesses.gif"
									width="230" height="29" alt="">
								<div class="content">
									<p>
										Whether you own a small business or are a services buyer for a
										large corporation, ServiceLive can help you find service
										providers who have background checks, insurance and the exact
										set of skills you need. Hire the workforce you need as you
										need them.rrr
									</p><br>
									<p align="center">
									
										<a href="${contextPath}/buyerRegistrationAction.action">
										<image width="78" type="image" height="23" class="btn26Bevel"
											style="background-image: url(${staticContextPath}/images/btn/btn_join_now.gif);"
											src="${staticContextPath}/images/common/spacer.gif"/>
										</a>
									</p>
								</div>

							</div>

							<div class="landingBucket marginRight">
								<img
									src="${staticContextPath}/images/icons/homeowners.gif"
									width="230" height="29" alt="">
								<div class="content">
									<p>
										ServiceLive is a one-stop source for finding service providers
										who come highly recommended by other members of the
										ServiceLive community. Fill out a service
										<br>
										order and we'll recommend providers who can do the job on your
										schedule and within your budget.
									</p>
									<p align="center">
										<a href="${contextPath}/csoCreateAccount_displayPage.action?fromHome=y">
										<image width="78" type="image" height="23" class="btn26Bevel"
											style="background-image: url(${staticContextPath}/images/btn/btn_join_now.gif);"
											src="${staticContextPath}/images/common/spacer.gif" />
										</a>
									</p>

								</div>

							</div>
							<div class="landingBucket">
								<img
									src="${staticContextPath}/images/icons/serviceProviders.gif"
									width="230" height="30" alt="">
								<div class="content">
									<p>
										ServiceLive can help you maximize profitability while
										minimizing advertising expenses. Log on when work is slow to
										connect with homeowners and other service buyers who need your
										specific set of skills. Accept work when you need it, pass on
										it when you don't.
									</p><br>
									<p align="center">
									  <a href="${contextPath}/providerRegistrationAction.action">
										<image width="78" type="image" height="23" class="btn26Bevel"
											style="background-image: url(${staticContextPath}/images/btn/btn_join_now.gif);"
											src="${staticContextPath}/images/common/spacer.gif" />
									  </a>
									</p>
								</div>

							</div>
						</div>
					</div>
					<div class="clear"></div>
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			</div>
			</s:form>
		</div>
		
	</body>
</html>
