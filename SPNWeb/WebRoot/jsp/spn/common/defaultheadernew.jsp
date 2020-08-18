<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>



<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<c:set var="selectLocnPage" scope="request"
	value="<%=request.getAttribute("SELECT_LOCATION_PAGE")%>" />
<c:set var="findProvidersPage" scope="request"
	value="<%=request.getAttribute("FIND_PROVIDERS_PAGE")%>" />
<c:set var="simpleBuyerPaymentPage" scope="request"
	value="<%=request.getAttribute("SB_PAYMENT_PAGE")%>" />
<c:set var="descAndSchedulePage" scope="request"
	value="<%=request.getAttribute("DESC_SCHE_PAGE")%>" />
<c:set var="simpleBuyerReviewPage" scope="request"
	value="<%=request.getAttribute("SB_REVIEW_PAGE")%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request"
	value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath()
					+ "/doLogin.action"%>" />
					
					
<!-- SL-18979 -->
<meta name="apple-itunes-app" content="app-id=899811332" />
<meta name="google-play-app" content="app-id=com.servicelive.android.servicelivepro" />
<meta name="msApplication-ID" content="microsoft.build.App"/>
<meta name="msApplication-PackageFamilyName"content="microsoft.build_8wekyb3d8bbwe"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- SL-18979 -->
<!-- SL-18979 -->
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/smart-app-banner.css" />
<link rel="apple-touch-icon" href="${staticContextPath}/images/icons/mobile_icon.png" />
<link rel="android-touch-icon" href="${staticContextPath}/images/icons/mobile_icon.png" />
<script type="text/javascript" src="${staticContextPath}/javascript/smart-app-banner.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/smart_banner.js"></script>
<!-- SL-18979 -->

<style type="text/css">
  div.contain{
   font-size: 10px;  
}</style>
<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
<!--<jsp:include page="browserCompatibilityBanner.jsp"/>-->
<div id="header" class="span-24 clearfix">
   <div id="title" class="span-6 first">
		<h1>
		 <a href=
		<c:if test="${not empty USER_OBJECT_IN_SESSION.userDetails.username}">
				"${contextPath}/spnLoginAction_loginUser.action?targetAction=spnMonitorNetwork_display" 
		</c:if>
		<c:if test="${empty USER_OBJECT_IN_SESSION.userDetails.username}">
				"/MarketFrontend/homepage.action" 
		</c:if>
		title="ServiceLive">ServiceLive</a>
		
		</h1>
	</div>	
	<div id="utilities" class="span-18 last">
		<div class="right contain">
		<c:if test="${not empty USER_OBJECT_IN_SESSION.userDetails.contact}">
		<div  style="padding-right:120px;"><p class="textright">Welcome 
		<c:out value="${USER_OBJECT_IN_SESSION.userDetails.contact.firstName}"></c:out>
		<c:out value="${USER_OBJECT_IN_SESSION.userDetails.contact.lastName}"></c:out>
		! #<c:out value="${USER_OBJECT_IN_SESSION.userDetails.userChildId}"></c:out>
		</p></div>	

		</c:if>
		<c:if test="${not empty USER_OBJECT_IN_SESSION.userDetails.username}">
			<a title="Training" target="_blank" href="http://training.servicelive.com">Training</a> |
		</c:if>
			<a title="Community Forums" target="_blank" href="http://community.servicelive.com">Community</a> |
			<a href="http://blog.servicelive.com" target="_blank" title="Blog">Blog</a> |
			<a title="Support" target="_blank" href="/MarketFrontend/jsp/public/support/support_main.jsp">Support</a> |
			<a title="Contact ServiceLive" target="_blank" href="/MarketFrontend/contactUsAction.action">Contact Us</a> 
			<c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
				| <a href="/MarketFrontend/adminSearch_execute.action">Search Portal</a>
			</c:if>
			<c:if test="${not empty USER_OBJECT_IN_SESSION.userDetails.username}">
				| <a title="Logout of your Account" href="spnLoginAction_logoutUser.action">Logout</a>
			</c:if>		
</div>
	</div>	
</div>
<div id="navigation" class="span-24 clearfix">
	<ul class="superfish">
			<li class="nav-level1" id="nav-dashboard"><a href="/MarketFrontend/dashboardAction.action" title="Dashboard">Dashboard</a></li>
	</ul>
</div>
<script>
<!--displayBanner();-->
displaySmartBanner();
</script>