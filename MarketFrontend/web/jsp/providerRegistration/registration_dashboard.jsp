<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
	<head>
		<title>ServiceLive [Service Provider Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.Slider");
			dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modules.css" />
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
			
			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 		<jsp:param name="PageName" value="ProDashboard"/>
			</jsp:include>	
	</head>
	<body class="tundra">
	    
		<div id="page_margins">
			<div id="page" class="clearfix">

				<!-- BEGIN HEADER -->
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>			                        
					 
					
				<div id="pageHeader">
				  <div> <img src="${staticContextPath}/images/images_registration/sp_firm_registration/hdr_welcomeToSL.gif" title="Welcome to ServiceLive" alt="Welcome to ServiceLive" /> </div>
				</div>
						
				</div>
				<!-- END HEADER -->
				<style>
</style>
				<!-- BEGIN CONTENT WELL -->
				<div class="colLeft711">
					<%@ include file="message.jsp"%>
					<div class="content clearfix">
						<p class="paddingBtm noTopPad">
							Your password has been accepted, and you are now logged on to the
							ServiceLive network. To complete the registration process, you
							will need to build a profile for your provider firm and a profile
							for at least one service pro.
						</p>
						<!-- PROVIDER FIRM PROFILE -->
						<tags:security actionName="allTabView">

							<div class="regDashboardHdr">
								<img
									src="${staticContextPath}/images/images_registration/sp_registration/txt_prov_firm_profile.gif"
									width="106" height="11" alt="Provider Firm Profile">
							</div>
							<div class="regDashboardContent">
								<div class="column1">
									<img
										src="${staticContextPath}/images/images_registration/sp_registration/icon_registration_business.gif"
										width="36" height="36" alt="">
								</div>
								<div class="column2">
									<b>Completion Status</b>
									<p>
										<c:if test="${providerStatus == 'complete'}">
											<a href="${contextPath}/allTabView.action">Completed</a><br /><br />
											<a href="${contextPath}/allTabView.action"><img src="${staticContextPath}/images/images_registration/icons/completeIcon.gif" /></a>
										</c:if>
										<c:if test="${providerStatus == 'incomplete'}">
											<a href="${contextPath}/allTabView.action">Incomplete</a><br /><br />
											<a href="${contextPath}/allTabView.action"><img src="${staticContextPath}/images/images_registration/icons/incIcon.gif" /></a>
										</c:if>
										<c:if
											test="${providerStatus ==null or providerStatus == 'notstarted'}">
											<a href="${contextPath}/allTabView.action">Not started</a><br /><br />
											<a href="${contextPath}/allTabView.action"><img src="${staticContextPath}/images/images_registration/icons/incIcon.gif" /></a>
										</c:if>

									</p>
								</div>
								<div class="column3">
									Your provider firm profile tells us about the organization your
									service providers work for. The more service providers registered
									through your firm, the more opportunities your firm will have
									to accept work.
									<br>
									<p align="right">
										<a
											href="<s:url action="allTabView.action" includeParams="none"/>">
											<img type="image"
												src="${staticContextPath}/images/images_registration/common/spacer.gif"
												width="119" height="20"
												style="background-image: url(${staticContextPath}/images/images_registration/btn/buildYourProfile.gif);"
												class="btn20Bevel" /> </a>
									</p>
								</div>
								<div class="clear"></div>
							</div>
							<div class="regDashboardFooter"></div>
						</tags:security>
						<!-- SERVICE PRO PROFILE -->
						<tags:security actionName="addServiceProAction">
							<div class="regDashboardHdr">
								<img
									src="${staticContextPath}/images/images_registration/sp_registration/txt_service_pro_profile.gif"
									width="95" height="11" alt="Service Provider Profile">
							</div>
							<div class="regDashboardContent">
								<div class="column1">
									<img
										src="${staticContextPath}/images/images_registration/sp_registration/icon_registration_service.gif"
										width="36" height="36" alt="">
								</div>
								<div class="column2">
									<b>Completion Status</b>
									<p>

										<c:if test="${resourceStatus == 'complete'}">
											<a href="${contextPath}/addServiceProAction.action">Completed</a><br /><br />
											<a href="${contextPath}/addServiceProAction.action"><img src="${staticContextPath}/images/images_registration/icons/completeIcon.gif" /></a>
										</c:if>
										<c:if test="${resourceStatus == 'incomplete'}">
											<a href="${contextPath}/addServiceProAction.action">Incomplete</a><br /><br />
											<a href="${contextPath}/addServiceProAction.action"><img src="${staticContextPath}/images/images_registration/icons/incIcon.gif" /></a>
										</c:if>
										<c:if test="${resourceStatus ==null or resourceStatus == 'notstarted'}">
											<a href="${contextPath}/addServiceProAction.action">Not started</a><br /><br />
											<a href="${contextPath}/addServiceProAction.action"><img src="${staticContextPath}/images/images_registration/icons/incIcon.gif" /></a>
										</c:if>

									</p>
								</div>

								<!-- <tags:security actionName="addServiceProAction" > -->
								<div class="column3">
									You will need to build a profile for each service pro in your
									provider firm. Remember, accepted service orders constitute an
									agreement between you and the buyer. Work can't be accepted by
									one service pro and then completed by another.
									<br>


									<p align="right">
										<a
											href="<s:url action="addServiceProAction" includeParams="none"/>">
											<img type="image"
												src="${staticContextPath}/images/images_registration/common/spacer.gif"
												width="119" height="20"
												style="background-image: url(${staticContextPath}/images/images_registration/btn/buildYourProfile.gif);"
												class="btn20Bevel" /> </a>
									</p>
								</div>
								<!-- </tags:security> -->

								<div class="clear"></div>
							</div>
							<div class="regDashboardFooter"></div>
						</tags:security>
					</div>
				</div>
				<div>&nbsp;</div> 
				<!-- BEGIN RIGHT RAIL -->
				<div class="colRight255 clearfix">

					<!-- #INCLUDE FILE="html_sections/modules/reg_status.html" -->
					<jsp:include flush="true" page="modules/reg_status.jsp"></jsp:include>
					<c:if test="${SecurityContext.slAdminInd}">    
						<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" />
					</c:if>
				</div>
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
		</div>
	</body>
</html>
