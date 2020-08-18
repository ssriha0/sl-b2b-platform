
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page import="com.newco.marketplace.util.PropertiesUtils" %>
<%@ page import="com.newco.marketplace.constants.Constants" %>
<% String waivePostingFee = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.WAIVE_POSTING_FEE); %>


<c:set var="pageTitle" scope="request" value="Describe and Schedule" />
<c:set var="editMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.EDIT_MODE %>"/>
<c:set var="copyMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.COPY_MODE %>"/>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
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
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/contextualHelp.css" />

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


		<!-- acquity: escape script tags with a forward slash, make sure they all have relationship and type set -->
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />


		<!-- acquity: make sure they all have language and type set -->
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>



		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/mootools.v1.11.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>

		<script type="text/javascript">

 				function selectRangeDate(){
 					document.getElementById('fixedServiceDate').disabled=true;
 					document.getElementById('serviceDate2Text').disabled=false;
 					document.getElementById('serviceDate1Text').disabled=false;
 					
 					document.getElementById('conditionalStartTime').disabled=true;
 					document.getElementById('conditionalEndTime').disabled=true;
 				
 				}
 				function selectFixedDate(){
  					document.getElementById("fixedServiceDate").disabled=false;
 					document.getElementById("serviceDate1Text").disabled=true;
 					document.getElementById("serviceDate2Text").disabled=true;
 					
 					document.getElementById("conditionalStartTime").disabled=false;
 					document.getElementById("conditionalEndTime").disabled=false;
 				
 				}
 				
 							
			function submitForm(method){
				var loadForm = document.getElementById('csoDescribeAndScheduleAction');

				loadForm.action = '${contextPath}' + "/csoDescribeAndSchedule_" + method + ".action";
				try {
				loadForm.submit();
				} catch (error) {
				alert ('An error occurred while submitting');
				}
			}
			
			
			function addSpendLimits(){
				var laborAmt = document.getElementById('laborLimit');
				var materialAmt = document.getElementById('materialsLimit');
				var postingAmt = document.getElementById('postingFee');
				var materialCheckbox = document.getElementById('provideAllMaterials');
				var laborAmtValue;
				var materialAmtValue;
				var postingAmtValue;
				var materialAmtFloat;
				var laborAmtFloat;
				var postingAmtFloat;
								
				var total = 0;
				
				
				clearAllValidationMsgs();
				
				if(laborAmt == null || !isNumeric(laborAmt.value)){
						document.getElementById('laborPricingMsg').style.display = "inline";
						document.getElementById('totalSpendLimit').innerHTML = '$' + total.toFixed(2);
						document.getElementById('totalLimit').value = total;
						return;
				}
			
				
				var materialCheckbox = document.getElementById('provideAllMaterials');
				if(materialCheckbox!= null){
					if(materialCheckbox.checked == false){
						if(materialAmt == null || !isNumeric(materialAmt.value)){
							document.getElementById('materialsPricingMsg').style.display = "inline";
							return;
						}
						total = parseFloat(materialAmt.value);
					}
				
				}
				
				total = total + parseFloat(laborAmt.value) + parseFloat(postingAmt.value);
				document.getElementById('totalSpendLimit').innerHTML = '$' + total.toFixed(2);
				document.getElementById('totalLimit').value = total;
			
			}
			
			function materialsCheck(){
				var materialCheckbox = document.getElementById('provideAllMaterials');
				if(materialCheckbox!= null){
					if(materialCheckbox.checked == true){
						var defaultValue=0;
						document.getElementById('materialsLimit').value=defaultValue.toFixed(2);
						document.getElementById('materialsLimit').disabled=true;
						
					}
					if(materialCheckbox.checked == false){
						document.getElementById('materialsLimit').disabled=false;
					}
				}
				addSpendLimits();
			
			}
			
		
			function isNumeric(sText){
				var ValidChars = "0123456789.";
				var IsNumber=true;
				var Char;
				if(sText.length == 0){
					IsNumber = false;
				}
			    for (i = 0; i < sText.length && IsNumber == true; i++){ 
				      Char = sText.charAt(i); 
				      if (ValidChars.indexOf(Char) == -1) {
				         IsNumber = false;
				         }
				      }
				   return IsNumber;
			   }
		
		function clearAllValidationMsgs(){
				document.getElementById('laborPricingMsg').style.display = "none";
				document.getElementById('materialsPricingMsg').style.display = "none";
			}		
		
		function hideElement() {
			document.getElementById('conditionalStartTime').style.visibility = 'hidden';
			document.getElementById('conditionalEndTime').style.visibility = 'hidden';			
		}	 
 		function showElement() {			
			document.getElementById('conditionalStartTime').style.visibility = 'visible';
			document.getElementById('conditionalEndTime').style.visibility = 'visible';
		}
		
        var periodicUpdaterTimer;
        function startPeriodicUpdater()
        {
            addSpendLimits();
            periodicUpdaterTimer = window.setTimeout(startPeriodicUpdater,500);
        }
        
        function stopPeriodicUpdater()
        {
            if(periodicUpdaterTimer)
                clearTimeout(periodicUpdaterTimer);
        }
</script>





<script>

	jQuery.noConflict(); 
	jQuery(document).ready(function() {
 
			jQuery('#serviceDate1Text').datepicker({dateFormat:'yy-mm-dd', minDate: new Date() });
			jQuery('#serviceDate2Text').datepicker({dateFormat:'yy-mm-dd', minDate: new Date() });
			jQuery('#fixedServiceDate').datepicker({dateFormat:'yy-mm-dd', minDate: new Date() });
     
	 });


</script>

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
       	 <jsp:param name="PageName" value="SSO - Describe and Schedule"/>
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
					<div id="hpWorkflow">
						<img src="${staticContextPath}/images/simple/anon-step2.png"
							alt="Step 2 of 4" />
					</div>
					<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />


					<s:form action="csoDescribeAndSchedule" theme="simple"
						id="csoDescribeAndScheduleAction">

						<div style="margin: 10px 0pt; display: none;" id="laborPricingMsg"
							class="errorBox clearfix">
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="pricing.validation.labor.msg" />
						</div>
						<div style="margin: 10px 0pt; display: none;"
							id="materialsPricingMsg" class="errorBox clearfix">
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="pricing.validation.materials.msg" />
						</div>

						<c:if
							test="${!fromSelectLocationPage && (firstServiceOrderLoggedIn == null || !firstServiceOrderLoggedIn)}">

							<div id="newAddress" class=" clearfix">
								<!-- <h3>Add New Location</h3>	-->

								<label>
									<span>Location Name <em class="req">*</em> </span>
									<tags:fieldError id="Location Name" oldClass="paddingBtm">
										<input type="text" id="locationName" name="locationName"
											value="${locationName}" class="shadowBox"
											style="width: 200px" /> <em>(ex. Vacation Home or Moms House)</em>
									</tags:fieldError>
								</label>
								<label>
									<span>Street Address 1 <em class="req">*</em> </span>
									<tags:fieldError id="Street Address1" oldClass="paddingBtm">
										<input type="text" id="street1" name="street1"
											value="${street1}" class="shadowBox" style="width: 200px" />
									</tags:fieldError>
								</label>
								<label>
									<span>Street Address 2 <em class="req">&nbsp;</em> </span>
									<tags:fieldError id="Street Address2" oldClass="paddingBtm">
										<input type="text" id="street2" name="street2"
											value="${street2}" class="shadowBox" style="width: 200px" />
									</tags:fieldError>
								</label>
								<label>
									<span>Apartment Number <em class="req">&nbsp;</em> </span>
									<tags:fieldError id="Apt" oldClass="paddingBtm">
										<input type="text" id="aptNo" name="aptNo" value="${aptNo}"
											class="shadowBox" style="width: 200px" />
									</tags:fieldError>
								</label>

								<label>
									<span>City <em class="req">*</em> </span>
									<tags:fieldError id="City" oldClass="paddingBtm">
										<input type="text" id="city" name="city" class="shadowBox"
											value="${city}" style="width: 200px" />
									</tags:fieldError>
								</label>
								<label>
									<span>State <em class="req">*</em> </span>
									<tags:fieldError id="State" oldClass="paddingBtm">
										<input type="text" id="stateDesc" name="stateDesc"
											class="shadowBox" value="${stateDesc}" readonly="readonly"
											style="width: 200px" />
									</tags:fieldError>
								</label>
								<label>
									<span>Zip <em class="req">*</em> </span>
									<input type="text" id="zip" name="zip" class="shadowBox"
										value="${zip}" readonly="readonly" style="width: 200px" />
								</label>
							</div>

						</c:if>

						<div class="hpDescribe clearfix">

							<h2>
								What else should the service provider know about this Project?
								<a id="one" class="jTip" name="Helpful Information"
									href="jsp/public/simple/tooltips/tooltip.jsp?bundlekey=simplebuyer.DNS.what.else.pro.text&width=400"><img
										class="imgtip" alt="Helpful Tips"
										src="${staticContextPath}/images/simple/tooltip-dark.png" />
								</a>
							</h2>
							<h3>
								Type Of Project
								<span>(<a
									href="${contextPath}/csoDescribeAndSchedule_previous.action">Go
										back &amp; change this</a>)</span>
							</h3>
							<div class="defined">
								${taskName}
							</div>

							<h3>
								Name For This Project
							</h3>

							<div class="defined" style="font-style: normal">

								Required
								<em class="req">*</em>
								<br />
								<tags:fieldError id="Project Name" oldClass="paddingBtm">
									<input type="text" class="shadowBox three00" name="projectName"
										style="width: 300px;" id="projectName" maxlength="75"
										value="${projectName}" />
								</tags:fieldError>
							</div>

							<jsp:include page="simple_panel_documents_and_photos.jsp" />

							<h3>
								Describe This Project
								<span id="charlimitinfo">(<strong>5000</strong>
									characters remaining)</span><small class="right"><a
									href="${contextPath}/examples!displayPage.action"
									target="new"
									style="color: #00A0D2; ont-size: 10px; font-weight: normal">&raquo;
										See Sample</a> </small>
							</h3>

						<!-- Contextual Help Start -->
							<c:set var="proj_desc"><fmt:message bundle="${serviceliveCopyBundle}" key="contextualhelp.cat.proj_desc.${mainCatId}" /></c:set>
							<jsp:include flush="true" page="${proj_desc}" />
						<!-- Contextual Help End -->

							<div class="defined">
								<p style="margin: 0px; padding: 0px; padding-bottom: 5px;">
									<span style="color: red;">Note:</span> <strong>Do not include any personal or private information in
									this section.</strong>
									<br />
									This information will be distributed to all providers you have
									selected and intended for project details only.
								</p>
								<tags:fieldError id="Project Description" oldClass="paddingBtm">
									<textarea id="projectDesc" name="projectDesc"
										class="textarea grow" maxlength="5000"
										onkeyup="limitChars(this, 5000, 'charlimitinfo')"
										style="width: 585px;">${projectDesc}</textarea>
								</tags:fieldError>
							</div>

							<h3>
								Additional Instructions for Selected Service Provider
								<span id="addtnlInstruCharlimitinfo">(<strong>5000</strong>
									characters remaining)</span>
							</h3>

							<div class="defined">
								<p style="margin: 0px; padding: 0px; padding-bottom: 5px;">
									<span style="color: red;">Note:</span> The Additional Instructions are only available to the one Service Provider who accepts your Service Order and will do the work.
								</p>
								<tags:fieldError id="Additional Instructions"
									oldClass="paddingBtm">
									<textarea id="addnlInstructions" name="addnlInstructions"
										class="textarea grow" maxlength="5000"
										value="${addnlInstructions}"
										onkeyup="limitChars(this, 5000, 'addtnlInstruCharlimitinfo')"
										style="width: 585px;">${addnlInstructions} </textarea>
								</tags:fieldError>
							</div>


						</div>

						<div class="hpDescribe">
							<h2>
								When do you want the project done?
								<a id="two" class="jTip" name="Helpful Information"
									href="jsp/public/simple/tooltips/tooltip.jsp?bundlekey=simplebuyer.DNS.when.work.done.text&width=400" 
										onmouseover="hideElement();" onmouseout="showElement();"><img
										class="imgtip" alt="Helpful Tips"
										src="${staticContextPath}/images/simple/tooltip-dark.png" />
								</a>
							</h2>

							<div class="clearfix defined" style="font-style: normal;">
								Required
								<em class="req">*</em>

								<table class="opt1" border="0" cellpadding="0" cellspacing="4">
									<c:if test="${serviceDateType == 1}">
										<tr>
											<td width="195">
												<input name="serviceDateType" type="radio"
													id="serviceDateType" onclick="selectFixedDate()"
													checked="checked" value="1" />

												On This Date:
												<tags:fieldError id="Service Date" oldClass="paddingBtm">
													<input type="text" id="fixedServiceDate"
														name="fixedServiceDate" style="width: 70px;"
														class="shadowBox" value="${fixedServiceDate}" />
												</tags:fieldError>
											</td>
											<td>
												Starting Between
												<tags:fieldError id="Start Time" oldClass="paddingBtm">
													<tags:fieldError id="Service Time" oldClass="paddingBtm">
														<s:select cssStyle="width: 90px;"
															id="conditionalStartTime" name="startTime"
															headerKey="[HH:MM]" headerValue="[HH:MM]"
															list="#application['time_intervals']" listKey="descr"
															listValue="descr" multiple="false" size="1"
															theme="simple" />
													</tags:fieldError>
												</tags:fieldError>
												and
												<tags:fieldError id="End Time" oldClass="paddingBtm">
													<tags:fieldError id="Service Time" oldClass="paddingBtm">
														<s:select cssStyle="width: 90px;" id="conditionalEndTime"
															name="endTime" headerKey="[HH:MM]" headerValue="[HH:MM]"
															list="#application['time_intervals']" listKey="descr"
															listValue="descr" multiple="false" size="1"
															theme="simple" />
													</tags:fieldError>
												</tags:fieldError>
											</td>
										</tr>
									</c:if>
									<c:if test="${serviceDateType != 1}">
										<tr>
											<td width="195">
												<input name="serviceDateType" type="radio"
													id="serviceDateType" onclick="selectFixedDate()" value="1" />
												On This Date:
												<input type="text" id="fixedServiceDate"
													name="fixedServiceDate" style="width: 70px;"
													class="shadowBox" value="${fixedServiceDate}"
													disabled="disabled" />
											</td>
											<td>
												Starting Between
												<s:select cssStyle="width: 90px;" id="conditionalStartTime"
													name="startTime" headerKey="[HH:MM]" headerValue="[HH:MM]"
													list="#application['time_intervals']" listKey="descr"
													listValue="descr" multiple="false" size="1" theme="simple"
													disabled="true" />

												and

												<s:select cssStyle="width: 90px;" id="conditionalEndTime"
													name="endTime" headerKey="[HH:MM]" headerValue="[HH:MM]"
													list="#application['time_intervals']" listKey="descr"
													listValue="descr" multiple="false" size="1" theme="simple"
													disabled="true" />

											</td>
										</tr>
									</c:if>

								</table>

								<table class="opt2" border="0" cellpadding="0" cellspacing="4"
									width="100%">
									<c:if test="${serviceDateType == 1}">
										<tr>
											<td>
												<input name="serviceDateType" id="serviceDateType"
													type="radio" onclick="selectRangeDate()" value="2"
													id="serviceDateType" />
												Anytime during the following date range:
												<tags:fieldError id="Start service Date"
													oldClass="paddingBtm">

													<input type="text" id="serviceDate1Text"
														name="serviceDate1Text" style="width: 70px;"
														class="shadowBox" value="${serviceDate1Text }"
														disabled="disabled" />
												</tags:fieldError>
												to
												<tags:fieldError id="End service Date" oldClass="paddingBtm">
													<input type="text" id="serviceDate2Text"
														name="serviceDate2Text" style="width: 70px;"
														class="shadowBox" value="${serviceDate2Text}"
														disabled="disabled" />
												</tags:fieldError>

											</td>
										</tr>

									</c:if>
									<c:if test="${serviceDateType != 1}">
										<tr>
											<td>
												<input name="serviceDateType" id="serviceDateType"
													type="radio" onclick="selectRangeDate()" value="2"
													checked="checked" id="serviceDateType" />
												Anytime during the following date range:
												<tags:fieldError id="Start service Date"
													oldClass="paddingBtm">
													<input type="text" id="serviceDate1Text"
														name="serviceDate1Text" style="width: 70px;"
														class="shadowBox" value="${serviceDate1Text }" />
												</tags:fieldError>
												to
												<tags:fieldError id="End service Date" oldClass="paddingBtm">
													<input type="text" id="serviceDate2Text"
														name="serviceDate2Text" style="width: 70px;"
														class="shadowBox" value="${serviceDate2Text}" />
												</tags:fieldError>
											</td>
										</tr>
									</c:if>
								</table>
							</div>
						</div>
						<div class="hpDescribe mCheck">
							<h2>
								How much do you want to pay for this service?
								<a id="three" class="jTip" name="Helpful Information"
									href="jsp/public/simple/tooltips/tooltip.jsp?bundlekey=simplebuyer.DNS.how.much.pay.text&width=400"><img
										class="imgtip" alt="Helpful Tips"
										src="${staticContextPath}/images/simple/tooltip-dark.png" />
								</a>
							</h2>
							<div class="clearfix defined" style="font-style: normal;">

							<!-- Contextual Help Start -->
							<c:set var="price_tips"><fmt:message bundle="${serviceliveCopyBundle}" key="contextualhelp.cat.price_tips.${mainCatId}" /></c:set>
							<jsp:include flush="true" page="${price_tips}" />
                           	<!-- Contextual Help End -->

							<p>After you determine the price you would like to pay, you will be asked to fund your account before the service order is routed.  While your credit card or bank account will be charged, you maintain control of your account, and you only pay the service provider when you are satisfied.</p>
			              	<br />
			              	<br />

								<table>
									<tr>
										<td style="text-align: right; padding-right: 10px;">
											Maximum Price for Labor
											<em class="req">*</em>
										</td>

										<td>
											<tags:fieldError id="Labor Maximum Price" oldClass="paddingBtm">
												<c:if test="${laborLimit == null}">
													$ <input type="text" id="laborLimit" name="laborLimit"
													onkeyup="addSpendLimits()" 	onfocus="startPeriodicUpdater(event)" onblur="stopPeriodicUpdater(event)" class="shadowBox"
													value="${laborLimit}"/>
												</c:if>
												<c:if test="${laborLimit != null}">
													$ <input type="text" id="laborLimit" name="laborLimit"
														onkeyup="addSpendLimits()" onfocus="startPeriodicUpdater(event)" onblur="stopPeriodicUpdater(event)" class="shadowBox"
														value="${laborLimit}"/>
												</c:if>
											</tags:fieldError>
										</td>

									</tr>
									<tr>
										<td style="text-align: right; padding-right: 10px;">
											Maximum Price for Materials
										</td>
										<td>
											<c:if test="${provideAllMaterials}">
													$ <input type="text" id="materialsLimit"
													name="materialsLimit" disabled="disabled"
													onkeyup="addSpendLimits()" class="shadowBox"
													value=<fmt:formatNumber value="${materialsLimit}" type="NUMBER"
													minFractionDigits="2" maxFractionDigits="2" /> />
													<s:checkbox id="provideAllMaterials" fieldValue="true"
													name="provideAllMaterials" cssClass="checked"
													onclick="materialsCheck()" theme="simple" />
										I will purchase and provide all materials needed.						
											</c:if>
											<c:if test="${!provideAllMaterials}">
												<tags:fieldError id="Materials Maximum Price"
													oldClass="paddingBtm">
													$ <input type="text" id="materialsLimit"
														name="materialsLimit" onkeyup="addSpendLimits()"
														class="shadowBox" value="${materialsLimit}"/>

													<s:checkbox id="provideAllMaterials"
														name="provideAllMaterials" cssClass="checked"
														onclick="materialsCheck()" theme="simple" />
											I will purchase and provide all materials needed.
													</tags:fieldError>
											</c:if>
										</td>

									</tr>
									<tr>
										<td style="text-align: right; padding-right: 10px;">
											ServiceLive Posting Fee
										</td>
										<input type="hidden" id="postingFee" name="postingFee"
											value="${postingFee}" />
										<td>
											
											<%-- start the promo here --%><c:choose><c:when test="${PromoDto.promoActive == true}"><del style="color: red;"><fmt:message bundle="${serviceliveCopyBundle}"
								            key="posting.fee.value" /></c:when><%-- end the promo here --%>
												<c:otherwise>$<fmt:formatNumber value="${postingFee}" type="NUMBER"
												minFractionDigits="2" maxFractionDigits="2" /></c:otherwise></c:choose>
												<%-- start the promo here --%><c:if test="${PromoDto.promoActive == true}"></del> <span style="color: green;">Free </span></c:if> <%-- end the promo here --%>
										</td>


									</tr>
									<tr>
										<td style="text-align: right; padding-right: 10px;">
											Total
										</td>
										<input type="hidden" id="totalLimit" name="totalLimit"
											value="${totalLimit}" />
										<td>
											<span id="totalSpendLimit">$<fmt:formatNumber
													value="${totalLimit}" type="NUMBER" minFractionDigits="2"
													maxFractionDigits="2" /> </span>
										</td>
									</tr>
								</table>

								<%-- start the promo here --%>
								<c:if test="${PromoDto.promoActive == true}">
								<div style="padding: 10px 0; font-weight: bold">
									<span style="color: green;">*</span> Limited time offer:  <p>Our usually low-price of $10.00 per service order Posting Fee is waived for all service orders posted <%=waivePostingFee%> for all buyer accounts. </p>									
								</div>
								</c:if>
								<%-- end the promo here --%>


							</div>
						</div>
						


						<div class="textcenter">
							<div class="clearfix buttnNav">

								<s:submit type='image' value="" cssClass="leftinput"
									action="csoDescribeAndSchedule" method="previous"
									src="%{#request['staticContextPath']}/images/simple/button-back.png"
									theme="simple" />
								<s:submit type='image' value="" cssClass="rightinput"
									action="csoDescribeAndSchedule" method="next"
									src="%{#request['staticContextPath']}/images/simple/button-next.png"
									theme="simple" />
								
							</div>
							<c:if test="${(appMode == editMode)  ||(appMode == copyMode)}">
									<div class="right">	
										<s:a href="serviceOrderMonitor.action?displayTab=Saved">
										Cancel Edit
										</s:a>
									</div>	
								</c:if>
						</div>
					</s:form>
				</div>


				<div id="hpSidebar">

					<%-- start the promo here --%>
					<c:if test="${PromoDto.promoActive == true}">
					<div id="hpPromo">
						<a id="promoLink" href="#">
						<img src="${staticContextPath}/images/homepage/free-posting-promo.png">
						</a>
					</div>
					</c:if>
					<%-- end the promo here --%>

					
				<c:if test="${loggedIn}">
				<%--	<s:action namespace="wallet" name="serviceLiveWallet" executeResult="true" /> --%>
				</c:if>


				
				
					<div id="hpTip" class="widget tips">

						<p>
							<em>We've made explaining your project simple! Our easy-to-use
								tools allow you to provide as much information as possible, so
								you get the provider you need.</em>
						</p>
						<div class="i44">
							<h2>
								It's all in the details
							</h2>
							<p>
								Upload images and other documents to help describe your specific
								service needs. Provide detailed explanations of tasks, working
								conditions (room dimensions, pets, hindrances, etc.) and final
								project expectations.
								<br /><br /><br />
								<center><a href="${contextPath}/examples!displayPage.action" target="new"><img style="border: 5px solid silver" src="${staticContextPath}/images/example_so/thumbnail-1.jpg"></a>
								<br /><br />Need help figuring out what to fill in the form?  Check out our <a href="${contextPath}/examples!displayPage.action" target="new">Sample Service Orders</a> for some tips and ideas.</a>
							</center>
							</p>
						</div>
					</div>
				</div>
			</div>

			
			<div id="zipCheck" class="jqmWindow"></div>

			<div class="jqmWindowThin" id="promoTerms">
				<div class="modalHomepage">
					<a href="#" class="jqmClose">Close</a>
				</div>
				<div class="modalContent">
					<center><img src="${staticContextPath}/images/homepage/freePosting.png" style="margin: 10px;"></center>
						<p>Our usually low-price of $10.00 per service order Posting Fee is waived for all service orders posted <%=waivePostingFee%> for all buyer accounts. </p>
				</div>
			</div>

<!-- Contextual Help Container -->
	
       <script>
              
			jQuery(document).ready(function($){

				$("textarea#projectDesc").mouseover(function(){
					$('div.contextualDescribeProject').show();
				});
				
				$("textarea#projectDesc").mouseout(function(){
					$('div.contextualDescribeProject').hide();
				});						


				$("input#laborLimit").mouseover(function(){
					$('div.contextualMaximumPrice').show();
				});
				
				$("input#laborLimit").mouseout(function(){
					$('div.contextualMaximumPrice').hide();
				});								
				
				});
				
		</script>
										
<!-- Contextual Help Container -->


			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
		</body>
</html>
