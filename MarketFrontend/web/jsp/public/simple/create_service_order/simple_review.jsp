
<jsp:directive.page import="com.newco.marketplace.interfaces.OrderConstants"/><%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page import="com.newco.marketplace.util.PropertiesUtils" %>
<%@ page import="com.newco.marketplace.constants.Constants" %>
<% String waivePostingFee = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.WAIVE_POSTING_FEE); %>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="pageTitle" scope="request" value="Review Service Order" />
<c:set var="editMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.EDIT_MODE %>"/>
<c:set var="createMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.CREATE_MODE %>"/>
<c:set var="copyMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.COPY_MODE %>"/>

<c:set var="draftStatus" scope="page" value="<%=new Integer(com.newco.marketplace.interfaces.OrderConstants.DRAFT_STATUS) %>"/>
<%
	session.putValue("simpleBuyerOverride", new Boolean(true));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive : ${pageTitle}</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/vars.js"></script>



		<script language="javascript" type="text/javascript">
		
		jQuery.noConflict();
		jQuery(document).ready(function($){
			$("a#<c:out value="${currentMenu}"/>").addClass("selected");
			<c:out value="${jQueryjs}"/>				
		});
		
		<c:out value="${classicJs}"/>
		
	</script>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/global.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />

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

		<script language="javascript" type="text/javascript">
			var djConfig = {
				isDebug: true, 
				parseOnLoad: true
			};
		</script>

		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>

		<!-- acquity: escape script tags with a forward slash, make sure they all have relationship and type set -->
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />

		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/mootools.v1.11.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script>
			jQuery.noConflict(); 
			jQuery(document).ready(function() {
				jQuery('#viewList').jqm({ajax:'csoReview_viewProvidersList.action', modal:true, trigger: 'a.jqModal'});
			});	
</script>

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
       	 <jsp:param name="PageName" value="SSO - Review"/>
	</jsp:include>

	</head>

	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction}">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction}">
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

				<div id="hpContent">
					<div id="hpIntro">
						<h2>
							Review Service Order
						</h2>
						<h3>
							${describeDTO.projectName}
						</h3>
						<c:if test="${orderNumber != null}">
							<h3>
								Service Order #${orderNumber}
							</h3>
						</c:if>
					</div>

					<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />
					<c:if test="${message != null}">
						<p>
							${message}
						</p>
						<br>
					</c:if>

					<div class="hpDescribe">
						<h2>
							Project Details
						</h2>
						<h3>
							Name For This Project
						</h3>
						<div class="defined">
							${describeDTO.projectName}
						</div>

						<h3>
							Location
						</h3>
						<div class="defined">
							${location }
						</div>

						<c:if test="${orderNumber != null}">
							<h3>
								Service Order #
							</h3>
							<div class="defined">
								${orderNumber}
							</div>
						</c:if>

						<h3>
							Project to be done
						</h3>
						<div class="defined">
							${taskName}
						</div>

						<h3>
							Description
							<span id="charlimitinfo"></span>
						</h3>
						<div class="defined">
							<p style="margin: 0px; padding: 0px; padding-bottom: 5px;">
								Note: This information will be distributed to all providers you
								have selected and intended for project details only.
							</p>

							<textarea id="describe" name="describe" readonly="readonly"
								class="textarea grow" maxlength="5000"
								onkeyup="limitChars(this, 5000, 'charlimitinfo')"
								style="width: 585px;">${describeDTO.projectDesc }</textarea>
						</div>

						<h3>
							Additional Instructions
							<a id="four" class="jTip" name="Tips For Additional Instructions"
								href="jsp/public/simple/tooltips/tooltip.jsp?bundlekey=simplebuyer.review.addInstr.text"><img
									class="imgtip"
									src="${staticContextPath}/images/simple/tooltip.png" />
							</a>
						</h3>
						<div class="defined">
							<p style="margin: 0px; padding: 0px; padding-bottom: 5px;">
								Note: The Additional Instructions are only available to the one
								Service Provider who accepts your Service Order and will do the
								work.
								<textarea id="instruct" name="instruct" readonly="readonly"
									class="textarea grow" style="width: 585px;">${describeDTO.addnlInstructions}</textarea>
						</div>

						<jsp:include page="simple_panel_documents_and_photos.jsp?simplePageName=review" />

						<h3>
							Requested Service Date
						</h3>
						<div class="defined">
							${serviceDates}
						</div>

						<h3>
							Requested Arrival
						</h3>

						<div class="defined">
							${serviceTime}
							<br />
							Service Provider will call to ${primaryPhone} confirm arrival
							time
						</div>

						<h3>
							Distributed To
						</h3>
						<div class="defined">
							<tags:fieldError id="Provider">
						${numProvidersSelected} Service Providers (<a id="clickViewList"
									class="jqModal" href="#top">View List</a>) 
		 			</tags:fieldError>
						</div>

						<h3>
							Amount To Be Funded
						</h3>
						<div class="defined fundingfees">
							<dl>

								<dt>
									Labor &amp; Materials
								</dt>
								<dd style="width: 70px">
									<fmt:formatNumber maxFractionDigits="2" type="currency"
										currencySymbol="$" value="${totalCost}" />
								</dd>
								<dt style="border-bottom: 1px solid silver;">
									Posting Fee
								</dt>
								<dd style="border-bottom: 1px solid silver; width: 70px;">
									<%-- start the promo here --%><c:if test="${PromoDto.promoActive == true}"><del style="color: red;"></c:if><%-- end the promo here --%>									
									<fmt:formatNumber maxFractionDigits="2" type="currency" currencySymbol="$" value="${postingFee}" />
									<%-- start the promo here --%><c:if test="${PromoDto.promoActive == true}"></del> <span style="color: green;">*</span> </c:if><%-- end the promo here --%>	
								</dd>
								<dt>
									Total
								</dt>
								<dd style="width: 70px">
									<fmt:formatNumber maxFractionDigits="2" type="currency"
										currencySymbol="$" value="${total}" />
								</dd>

							</dl>

							<%-- start the promo here --%>
							<c:if test="${PromoDto.promoActive == true}">
							<div style="padding: 10px 0; font-weight: bold">
								<span style="color: green;">*</span> Limited time offer:  <p>Our usually low-price of $10.00 per service order Posting Fee is waived for all service orders posted <%=waivePostingFee%> for all buyer accounts. </p>									
							</div>
							</c:if>
							<%-- end the promo here --%>

						</div>
					</div>
					<s:form theme="simple" action="csoReview_displayPage">
						<div class="clearfix" style="margin-top: 10px;">
						<c:if test="${appMode != editMode ||
						        soStatusId == draftStatus}" >
							<div class="left submittext">
								<h3>
									<c:if test="${appMode == createMode}" >
										I want to SAVE this Service Order
									</c:if>
									<c:if test="${appMode == editMode}" >
										I want to SAVE this Service Order as a draft
									</c:if>
								</h3>
								<p>
									and submit it later.
									<a id="five" class="jTip" name="SAVE your order"
										href="jsp/public/simple/tooltips/tooltip.jsp?bundlekey=simplebuyer.review.saveOrder.text"><img
											class="imgtip"
											src="${staticContextPath}/images/simple/tooltip.png" />
									</a>
								</p>
								<s:submit type='image' value="" action="csoReview" method="save"
									src="%{#request['staticContextPath']}/images/simple/button-save-order.png"
									theme="simple" />
							</div>
	 					</c:if> 
								<div class="right submittext">
								<h3>
							<c:if test="${appMode == createMode}" >
									I want to CONTINUE this Service Order
							</c:if>
							<c:if test="${appMode == editMode}" >
								<c:if test="${soStatusId == draftStatus}"  >
									I want to CONTINUE EDITING this Service Order
								</c:if>
								<c:if test="${soStatusId != draftStatus}"  >
									I want to REPOST this Service Order
								</c:if>

							</c:if>
								</h3>
								<p>
									and start receiving responses.
									<a id="six" class="jTip" name="CONTINUE your order"
										href="jsp/public/simple/tooltips/tooltip.jsp?bundlekey=simplebuyer.review.continueOrder.text"><img
											class="imgtip"
											src="${staticContextPath}/images/simple/tooltip.png" />
									</a>
								</p>
								<s:submit type='image' value="" action="csoReview" method="next"
									src="%{#request['staticContextPath']}/images/simple/button-continue.png"
									theme="simple" />
							</div>
						</div>
						<br />
						<s:submit type='image' value="" action="csoReview"
							method="previous"
							src="%{#request['staticContextPath']}/images/simple/button-back.png"
							theme="simple" />
						<c:if test="${(appMode == editMode)  ||(appMode == copyMode)}">
								<div class="right">	
									<s:a href="serviceOrderMonitor.action?displayTab=Saved">
									Cancel Edit
									</s:a>
								</div>	
						</c:if>
					</s:form>
				</div>

				<div id="hpSidebar">
					<div>
						<s:action namespace="wallet" name="serviceLiveWallet"
							executeResult="true" />
					</div>
					<div id="hpTip" class="widget tips">

						<div class="i44">
							<h2>
								You are almost done!
							</h2>
							<p>
								<em>Please review your service order!</em>
								<br />
								After funding your account in next page, post your order,
								receive instant responses from service professionals, and get
								the project done!
							</p>
						</div>
					</div>
				</div>

			</div>
			<div id="viewList" class="jqmWindowList">


			</div>
			
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
			
		</div>
	</div>
	</body>
</html>

