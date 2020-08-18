<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@page import="com.newco.marketplace.web.dto.ServiceOrdersCriteria"%>
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






		<script language="javascript" type="text/javascript">

		jQuery.noConflict();
		jQuery(document).ready(function($){
			$("a#<c:out value="${currentMenu}"/>").addClass("selected");
			<c:out value="${jQueryjs}"/>
		});

		<c:out value="${classicJs}"/>

	</script>


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


	</script>

<div id="logo">
	<c:choose>
		<c:when test="${IS_LOGGED_IN}">
			<a href="${contextPath}/dashboardAction.action" title="ServiceLive">
				<img src="${staticContextPath}/images/artwork/common/logo.png" alt="ServiceLive"/>
			</a>
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
								<em>#${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}</em>
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
								<a href="http://community.servicelive.com" target="_blank">Community</a> 	|
									<a href="http://blog.servicelive.com" title="Blog" target="_blank">Blog</a> | 
								<a href="${contextPath}/jsp/public/support/support_main.jsp" target="_blank">Support</a> 	|
								<a href="${contextPath}/contactUsAction.action" target="_blank">Contact Us</a> 		|
								<a href="${contextPath}/doLogout.action">Logout</a>
							</div>
						</c:when>
						<c:otherwise>
						<div id="tollfreenumber" style="z-index=999;text-align:right; font-size:16px; color: #85191a; font-weight:600;padding-right:10px;margin-top:-8px"><B >1-888-549-0640</B></div>
						<div style="text-align:right;padding-right:10px; padding-bottom:5px">
							<a href="http://blog.servicelive.com" title="Blog" target="_blank">Blog</a> | 
							<a href="http://community.servicelive.com" target="_blank">Community</a> |
							<a href="${contextPath}/jsp/public/support/support_main.jsp" target="_blank">Support</a> |
							<a href="${contextPath}/contactUsAction.action" target="_blank">Contact Us</a><br />
							
<div style="maring-top:10px; padding-top:10px" ><a class="btn17" style="float: none; display: inline-block; width:47px; height: 17px; background-image: url('${staticContextPath}/images/buttons/login_header.gif');" href="${loginActionPath}/loginAction.action"><img src="${staticContextPath}/images/spacer.gif" width="47" height="17" alt="Login" style="margin-bottom: -5px;"></a></div>
						</div>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="jqmWindow" id="exploreMarketPlace">
					<div class="modalHomepage">
						<a href="#" class="jqmClose">Close</a>
					</div>
					<s:form name="mp" action="homepage_submitZip.action" method="POST" theme="simple">
						<div class="modalContent">
							<h2>Please enter the Zip Code of the location where work will be done.</h2>
							<s:textfield onclick="clear_enter_zip();" onblur="add_enter_zip();" name="zipCode" id="zipCode" value="Enter Zip Code" />
							<s:submit type="image" cssClass="findProv" src="%{#request['staticContextPath']}/images/simple/button-find-sp.png" />
						</div>
						<input id="pop_name" type="hidden" name="popularSimpleServices[0].name" value="<s:property value='name'/>" />
						<input id="mainCategoryId" type="hidden" name="popularSimpleServices[0].mainCategoryId" value="<s:property value='mainCategoryId'/>" />
						<input id="categoryId" type="hidden" name="popularSimpleServices[0].categoryId" value="<s:property value='categoryId'/>" />
						<input id="subCategoryId" type="hidden" name="popularSimpleServices[0].subCategoryId" value="<s:property value='sategoryId'/>" />
						<input id="serviceTypeTemplateId" type="hidden" name="popularSimpleServices[0].serviceTypeTemplateId" value="<s:property value='serviceTypeTemplateId'/>" />
						<input id="buyerId" type="hidden" name="popularSimpleServices[0].buyerTypeId" value="<s:property value='buyerTypeId'/>" />
					</s:form>
				</div>

