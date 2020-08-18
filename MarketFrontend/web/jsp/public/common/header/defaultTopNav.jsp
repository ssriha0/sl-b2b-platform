<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.newco.marketplace.web.dto.ServiceOrdersCriteria"%>
<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
<!--<jsp:include page="/jsp/public/blueprint/browserCompatibilityBanner.jsp"></jsp:include>-->

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath()%>" />
<%
	ServiceOrdersCriteria commonCriteria = (ServiceOrdersCriteria) session
			.getAttribute("SERVICE_ORDER_CRITERIA_KEY");

	boolean isLoggedIn = false; //SecurityContext != null
	if (commonCriteria != null
			&& commonCriteria.getSecurityContext() != null) {
		isLoggedIn = true;
	}

	boolean simpleOverride = false;//passed from the jsp including global header
	boolean isSimpleBuyer = false;//roleID = 5
	Boolean sesSimpleOverride = (Boolean) session
			.getAttribute("simpleBuyerOverride");
	if (sesSimpleOverride != null && sesSimpleOverride.booleanValue()) {
		simpleOverride = true;
	}
	if ((isLoggedIn && commonCriteria.getSecurityContext().getRoleId()
			.intValue() == 5)) {
		isSimpleBuyer = true;
	}
	boolean isProvider;//roleID = 1
	boolean isProBuyer;//roleID = 3
%>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
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
		<title>ServiceLive : ${pageTitle}</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	<c:if test="${!noJs}">
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script> 
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
	</c:if>
	<c:if test="${!empty currentMenu}">
		<script language="javascript" type="text/javascript">
		if (typeof jQuery != 'undefined') {
			jQuery(document).ready(function($){
			$("a#<c:out value="${currentMenu}"/>").addClass("selected");
			<c:out value="${jQueryjs}"/>
		});
		}
		</script>
	</c:if>

	<c:if test="${!noCss}">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
	</c:if>

		<script language="JavaScript" type="text/javascript">
			function clear_enter_zip()
			{
				if (document.getElementById('zipCode').value == 'Enter Zip Code')
				{
					document.getElementById('zipCode').value = '';
				}
			}

			function add_enter_zip()
			{
				if (document.getElementById('zipCode').value == '')
				{
					document.getElementById('zipCode').value = 'Enter Zip Code';
				}
			}


		function getSoUrl(action){
		    var path=encodeURI(window.location.href);	
		      
		    if(path.indexOf("soId") > -1 && path.indexOf("soWizardController.action") > -1){
		    	so=path.match(/soId=(.+)/)[1];
		    	var soId=so.substring(0,16);
		    	window.location.href='${contextPath}/'+action+'?soId='+soId;		    	
		    }
		    else{
		    	window.location.href='${contextPath}/'+action;
		    }
		   	  	  
		}
		
			function submitZipCode()
			{
				if (document.getElementById('zipCode') && document.getElementById('zipCode').value != '')
				{	
					var zipCode=document.getElementById('zipCode').value;
					var queryParams='?ZipCodeEntered='+zipCode;
					var buyerTypeId=document.getElementById('buyerId').value;
					queryParams=queryParams+'&buyerTypeId='+buyerTypeId;
					var form = document.getElementById("submitZip");
					form.action="homepage_submitZip.action"+queryParams;
					form.submit();
				}
			}
	</script>


<div id="logo">
	<c:choose>
		<c:when test="${IS_LOGGED_IN}">
			<c:choose>
				<c:when test="${isOmPage}">
					<a href="${contextPath}/dashboardAction.action" title="ServiceLive" >
						<img src="${staticContextPath}/images/artwork/common/sl_logo_order_management.gif" alt="ServiceLive"/>
					</a>
				</c:when>
				<c:otherwise>
					<a title="ServiceLive1" onclick ="getSoUrl('dashboardAction.action');" style="cursor: pointer;">
						<img src="${staticContextPath}/images/artwork/common/logo.png" alt="ServiceLive"/>
					</a>
				</c:otherwise>
			</c:choose>	
		</c:when>
		<c:otherwise>
			<a href="${contextPath}" title="ServiceLive">
				<img src="${staticContextPath}/images/artwork/common/logo.png" alt="ServiceLive"/>
			</a>
		</c:otherwise>
	</c:choose>
</div>

<div id="topNav">
	<c:choose>
		<c:when test="${IS_LOGGED_IN}">
			<div class="smallText">
				Welcome, ${SERVICE_ORDER_CRITERIA_KEY.FName}
				${SERVICE_ORDER_CRITERIA_KEY.LName}!
				<c:if test="${isSLAdmin}">
					<em>#-1</em>
				</c:if>
				<c:if test="${!isSLAdmin}">
					<em>#${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}</em>
				</c:if>
				<c:choose>
					<c:when test="${lifetimeRatingStarsNumber == null}"></c:when>
					<c:when test="${lifetimeRatingStarsNumber == 0}">Not Rated</c:when>
					<c:otherwise>
						<img
							src="${staticContextPath}/images/common/stars_<c:out value="${lifetimeRatingStarsNumber}"/>.gif"
							alt="" border="0" />
					</c:otherwise>
				</c:choose>

				<br />
				<a title="Training" href="http://training.servicelive.com" target="_blank">Training</a> |
				<a href="http://community.servicelive.com" target="_blank">Community</a> |				
				<a href="http://blog.servicelive.com" title="Blog" target="_blank">Blog</a> |
				<a href="${contextPath}/jsp/public/support/support_faq.jsp" target="_blank">Support</a>	|				
				<a href="${contextPath}/contactUsAction.action" target="_blank">Contact Us</a> |
				<c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
					<a href="${contextPath}/adminSearch_execute.action">Search Portal</a> |
				</c:if>
				<a onclick="getSoUrl('doLogout.action');" style="cursor: pointer;color: #00A0D2;">Logout</a>
			</div>
		</c:when>
		<c:otherwise>
			<div id="tollfreenumber"
				style="text-align: right; font-size: 16px; color: #85191a; font-weight: 600; padding-right: 10px; margin-top: -8px">
				<B>1-888-549-0640</B>
			</div>
			<div
				style="text-align: right;  padding-bottom: 5px; padding-top:10px">
				<a href="http://blog.servicelive.com" title="Blog" target="_blank" >Blog</a> |
				<a href="http://community.servicelive.com" target="_blank">Community</a> |
				<a href="${contextPath}/jsp/public/support/support_faq.jsp" target="_blank">Support</a>
				|
				<a href="${contextPath}/contactUsAction.action" target="_blank">Contact Us</a><br>
			
			<div style="maring-top:10px; padding-top:10px" ><a class="btn17" style="float: none; display: inline-block; width:47px; height: 17px; background-image: url('${staticContextPath}/images/buttons/login_header.gif');" href="${loginActionPath}/loginAction.action"><img src="${staticContextPath}/images/spacer.gif" width="47" height="17" alt="Login" style="margin-bottom: -5px;"></a></div>
     </div>
		</c:otherwise>
	</c:choose>
</div>


<div class="jqmWindow" id="exploreMarketPlace">
	<div class="modalHomepage">
		<a href="#" class="jqmClose">Close</a>
	</div>
	<form name="mp" id="submitZip" action="homepage_submitZip.action" method="POST"
		theme="simple">
		<div class="modalContent">
			<h2>
				Please enter the Zip Code of the location where work will be done.
			</h2>
			<input type="text" onclick="clear_enter_zip();" onblur="add_enter_zip();"
				name="zipCode" id="zipCode" value="Enter Zip Code" />			
			<a href="#" onclick="javascript:submitZipCode();" alt="Submit">
				<img style='float: right;' src="${staticContextPath}/images/simple/button-find-sp.png" />
			</a>			
		</div>
		<input id="pop_name" type="hidden"
			name="popularSimpleServices[0].name"
			value="<c:out value="${name}"/>" />
		<input id="mainCategoryId" type="hidden"
			name="popularSimpleServices[0].mainCategoryId"
			value="<c:out value="${mainCategoryId}"/>" />
		<input id="categoryId" type="hidden"
			name="popularSimpleServices[0].categoryId"
			value="<c:out value="${categoryId}"/>" />
		<input id="subCategoryId" type="hidden"
			name="popularSimpleServices[0].subCategoryId"
			value="<c:out value="${sategoryId}"/>" />
		<input id="serviceTypeTemplateId" type="hidden"
			name="popularSimpleServices[0].serviceTypeTemplateId"
			value="<c:out value="${serviceTypeTemplateId}"/>" />
		<input id="buyerId" type="hidden"
			name="popularSimpleServices[0].buyerTypeId"
			value="<c:out value="${buyerTypeId}"/>" />
	</form>
</div>
<!-- SL-18979 -->
<script>
displaySmartBanner();
</script>
<!-- SL-18979 -->