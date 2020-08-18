<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ page import="com.newco.marketplace.util.PropertiesUtils" %>
<%@ page import="com.newco.marketplace.constants.Constants" %>
<% String waivePostingFee = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.WAIVE_POSTING_FEE); %>

<c:set var="pageTitle" scope="request" value="Add Funds" />
<c:set var="editMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.EDIT_MODE %>"/>
<c:set var="copyMode" scope="page" value="<%=com.newco.marketplace.interfaces.OrderConstants.COPY_MODE %>"/>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<title>ServiceLive : ${pageTitle}</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

	

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
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />

		<!-- acquity: here is the new stylesheet, rename to whatever you'd like -->		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<!-- acquity: make sure they all have language and type set -->		
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>


		<script>
		function fnShowDiv(theId, divId){			
			var selectRef = document.getElementById(theId);
			var selectedValue = selectRef.options[selectRef.selectedIndex].value;
			
			if(selectedValue == 0){
				document.getElementById(divId).style.display="block";
			}else if(selectedValue == 1){
				document.getElementById(divId).style.display="none";
			}
		}
		
		function fnSubmitToAction(theActionName){			
		    // hide submit
		    document.getElementById('submitDiv').style.display = 'none';
		    // show submitting message

		    document.getElementById('disabledSubmitDiv').style.display = 'block';
			var addDundsForm = document.getElementById('addFundsForm');
			
			addDundsForm.action='csoAddFunds_'+theActionName+'.action';
			addDundsForm.submit();
			
		}
		</script>
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="SSO - Add Funds"/>
		</jsp:include>
</head>
<c:choose>
	<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
		<body class="tundra acquity simple" onload="fnShowDiv('sameAddressAsBilling','newAddress')">
	</c:when>
	<c:otherwise>
		<body class="tundra acquity" onload="fnShowDiv('sameAddressAsBilling','newAddress')">
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
					
			
			<s:form name="addFundsForm" id="addFundsForm" theme="simple"
				action="csoAddFunds_">
				<div id="hpContent">
					
					
					<div id="hpWorkflow">
						<img src="${staticContextPath}/images/simple/step4.png"
							alt="Step 4 of 4" />
					</div>
					
					
					
					
					<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />
										
					<%-- 
					<s:if test="%{errors.size > 0}">
						<label>
							<font size=1.5 color="red"> &nbsp; &nbsp;&nbsp; Mandatory
								fields required</font>
						</label>
						<s:iterator value="errors" status="error">
							<p class="errorMsg">
								<!-- ${errors[error.index].fieldId} -  -->
								&nbsp; &nbsp; <a href="${errors[error.index].url}">${errors[error.index].msg}</a> ${errors[error.index].msg}
							</p>

						</s:iterator>
					</s:if>
					--%>
					<c:choose><c:when test="${hasError == 'true'}">
						<div style="color: red" align="center">
							${addFundsMsg}
						</div>
					</c:when>
					<c:otherwise>
						<div style="color: blue" align="center">
							${addFundsMsg}
						</div>
					</c:otherwise></c:choose>
					
					
					
					
								<div class="clearfix">
									<div id="hpFundingInfo" class="hpDescribe clearfix">
										<h2>
											Funding Information
										</h2>
										<dl>
											<dt>
												Maximum Price For Labor and Materials:
											</dt>
											<dd>
												$
												<fmt:formatNumber
													value="${(describeDTO.laborLimit + describeDTO.materialsLimit)}"
													type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
											</dd>
											<dt>
												ServiceLive Posting Fee
											</dt>
											<dd>
												<%-- start the promo here --%><c:if test="${PromoDto.promoActive == true}"><del style="color: red;"></c:if><%-- end the promo here --%>
													$<fmt:formatNumber value="${describeDTO.postingFee}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
													<%-- start the promo here --%><c:if test="${PromoDto.promoActive == true}"></del> <span style="color: green;">*</span></c:if><%-- end the promo here --%>
											</dd>
											<dt>
												Total For This Project:
											</dt>
											<dd>
												$
												<fmt:formatNumber value="${totalSpendLimit}" type="NUMBER"
													minFractionDigits="2" maxFractionDigits="2" />
											</dd>
											<dt>
												Your Available Balance:
											</dt>
											<dd>
												$
												<fmt:formatNumber value="${availableBalance}" type="NUMBER"
													minFractionDigits="2" maxFractionDigits="2" />
											</dd>
											<dt>
												Funds Needed:
											</dt>
											<dd>
												$
												<s:if test="%{transactionAmount >= 0}">
													<fmt:formatNumber value="${transactionAmount}" type="NUMBER"
														minFractionDigits="2" maxFractionDigits="2" />
												</s:if>
												<s:if test="%{transactionAmount < 0}">
													0.0
												</s:if>	
											</dd>
											<dt>
												Amount to be charged:
											</dt>
											<dd>
												$
												<s:if test="%{transactionAmount >= 0}">
													<fmt:formatNumber value="${transactionAmount}" type="NUMBER"
														minFractionDigits="2" maxFractionDigits="2" />
												</s:if>
												<s:if test="%{transactionAmount < 0}">
													0.0
												</s:if>
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
									<div id="hpSinfo" class="hpDescribe clearfix">
										<h2>
											Service Information
										</h2>


										<h3 style="margin-top: 0px;">
											Service Location
										</h3>
										<div class="defined">
											${describeDTO.locationName}
											<br />
											${describeDTO.street1}&nbsp;
											<c:if
												test="${describeDTO.street2 != null && describeDTO.street2 != ''}">
						,&nbsp;${describeDTO.street2}
					</c:if>
											<c:if
												test="${describeDTO.aptNo != null && describeDTO.aptNo != ''}">
						,&nbsp;#${describeDTO.aptNo}
					</c:if>
											<br />
											${describeDTO.city}
											<c:if
												test="${describeDTO.stateCd != null && describeDTO.stateCd != ''}">,</c:if>
											${describeDTO.stateCd}
											<c:if test="${describeDTO.zip != null && describeDTO.zip != ''}">,</c:if>
											${describeDTO.zip}
											<br />
										</div>


										<h3>
											Requested Service Date(s):
										</h3>

										<div class="defined">
											<c:if
												test="${serviceDate1Text != null && serviceDate1Text != ''}">
						${serviceDate1Text} - ${serviceDate2Text}
					</c:if>
											<c:if
												test="${fixedServiceDate != null && fixedServiceDate != ''}">
						${fixedServiceDate} 
					</c:if>
										</div>

										<c:if
											test="${describeDTO.startTime != null && describeDTO.startTime != ''}">
											<h3>
												Service Time
											</h3>
											<div class="defined">
												<c:if
													test="${describeDTO.endTime != null && describeDTO.endTime != ''}">
								Between 
							</c:if>
												${describeDTO.startTime}
												<c:if
													test="${describeDTO.endTime != null && describeDTO.endTime != ''}">
								and ${describeDTO.endTime}
							</c:if>
											</div>
										</c:if>
									</div>
								</div>					
					
					
					
					
					
					
					<div class="hpDescribe clearfix">
						<h2>Your Account Address </h2>
						<div class="addy">
							<strong>${locationName}</strong>
							<br />
							${street1}
							<c:if test="${street2 != null}">
								<br />${street2}
							</c:if>
							<c:if test="${apartmentNo != null}">
								<br />${apartmentNo}
							</c:if>
							<br />
							${city},&nbsp; ${state}&nbsp; ${zipcode}
						</div>
						Is this the same as your billing address?
						<s:select name="sameAddressAsBilling" id="sameAddressAsBilling" list="#{'1':'Yes','0':'No'}" size="1" theme="simple" value="sameAddressAsBilling" onchange="fnShowDiv('sameAddressAsBilling','newAddress')" />
					</div>

					<div id="newAddress" style="display: none">
						<!-- <h3>Add New Billing Address</h3>	-->
						<label>
							<span>Location Name <em class="req">*</em>
							</span>
							<tags:fieldError id="Location Name" oldClass="errorBox">
								<s:textfield name="newCreditCard.billingLocationName"
									theme="simple" value="%{newCreditCard.billingLocationName}"
									cssClass="shadowBox" cssStyle="width: 200px" /> <em>(ex. Vacation Home or Moms House)</em>
							</tags:fieldError>
						</label>
						<label>
							<span>Street Address <em class="req">*</em>
							</span>
							<tags:fieldError id="Street Address" oldClass="errorBox">
								<s:textfield name="newCreditCard.billingAddress1" theme="simple"
									value="%{newCreditCard.billingAddress1}" cssClass="shadowBox"
									cssStyle="width: 200px" />
							</tags:fieldError>
						</label>
						<label>
							<span>Street Address 2
							</span>
							<s:textfield name="newCreditCard.billingAddress2" theme="simple"
								value="%{newCreditCard.billingAddress2}" cssClass="shadowBox"
								cssStyle="width: 200px" />
						</label>
						<label>
							<span>Apartment Number
							</span>
							<s:textfield name="newCreditCard.billingApartmentNumber"
								theme="simple" value="%{newCreditCard.billingApartmentNumber}"
								cssClass="shadowBox" cssStyle="width: 200px" />
						</label>
						<label>
							<span>City <em class="req">*</em>
							</span>
							<tags:fieldError id="City" oldClass="errorBox">
								<s:textfield name="newCreditCard.billingCity" theme="simple"
									value="%{newCreditCard.billingCity}" cssClass="shadowBox"
									cssStyle="width: 200px" />
							</tags:fieldError>
						</label>

						<label>
							<span>State <em class="req">*</em>
							</span>
							<!-- <s:textfield cssClass="shadowBox" cssStyle="width: 100px" /> -->
							<tags:fieldError id="State" oldClass="errorBox">
								<s:select name="newCreditCard.billingState" headerKey="-1"
									headerValue="Select One" cssStyle="width: 200px;" size="1"
									theme="simple" list="#application['stateCodes']" listKey="type"
									listValue="descr" value="newCreditCard.billingState" />
							</tags:fieldError>
						</label>
						<label>
							<span>Zip <em class="req">*</em>
							</span>
							<tags:fieldError id="Zip" oldClass="errorBox">
								<s:textfield name="newCreditCard.billingZipCode" theme="simple"
									value="%{newCreditCard.billingZipCode}" cssClass="shadowBox"
									cssStyle="width: 200px" />
							</tags:fieldError>
						</label>
					</div>
				

			  	<s:if test="${transactionAmount > 0.0}"> 
			 		<c:if test="%{hasExistingCreditCard}"> 
						<div id="existingAddress" class="hpDescribe clearfix">
							<h2>
								Add Funds
							</h2>

							<p></p>

							<div class="clearfix">
								<div class="left creditcard">
									<s:checkbox name="useExistingCard" value="%{useExistingCard}"
										theme="simple" />
									<strong>${existingCreditCard.creditCardName}</strong>
									<br />
									${existingCreditCard.creditCardNumber}
									<br />
									Exp:
									${existingCreditCard.expirationMonth}/${existingCreditCard.expirationYear}
									<br />
									Security Code
									<tags:fieldError id="Security Code" oldClass="errorBox">
										<s:textfield name="existingCardSecurityCode" theme="simple"
											value="%{existingCardSecurityCode}"
											cssClass="shadowBox fifty"  cssStyle="width: 50px" maxLength="4" />
									</tags:fieldError>
								</div>
							</div>
							<a name="uNew" id="createNew" style="clear: both; font-weight: bold; font-size: 14px; margin-bottom: 10px;" href="#uNew">Use New Card</a>
						</div>
					</c:if> 

				<c:choose><c:when test="%{hasExistingCreditCard}">
					<div id="creditCardDetails" class="hidden clearfix">
				</c:when>
				<c:otherwise>
					<div id="creditCardDetails" class="clearfix">
				</c:otherwise></c:choose>
						<label>
							<span>Credit Card Type: <em class="req">*</em>
							</span>
							<tags:fieldError id="Credit Card Type" oldClass="errorBox">
								<s:select name="newCreditCard.creditCardType" headerKey="-1"
									headerValue="Select One" size="1" theme="simple"
									list="creditCardTypeList" listKey="id" listValue="type"
									value="newCreditCard.creditCardType" />
							</tags:fieldError>
						</label>
						<label>
							<span>Name on Card: <em class="req">*</em>
							</span>
							<tags:fieldError id="Name on Card" oldClass="errorBox">
								<s:textfield name="newCreditCard.creditCardHolderName"
									theme="simple" value="%{newCreditCard.creditCardHolderName}"
									cssClass="shadowBox" cssStyle="width: 200px" />
							</tags:fieldError>
						</label>
						<label>
							<span>Credit Card #: <em class="req">*</em>
							</span>
							<tags:fieldError id="Credit Card #" oldClass="errorBox">
								<s:textfield name="newCreditCard.creditCardNumber" theme="simple"
									value="%{newCreditCard.creditCardNumber}" cssClass="shadowBox"
									maxlength="16" cssStyle="width: 200px" />
							</tags:fieldError>
						</label>

						<label>
							<span>Security Code: <em class="req">*</em>
							</span>
							<tags:fieldError id="Security Code" oldClass="errorBox">
								<s:textfield name="newCreditCard.securityCode"
									value="%{newCreditCard.securityCode}" theme="simple"
									cssClass="shadowBox" cssStyle="width: 50px" maxLength="4" />
							</tags:fieldError>
						</label>
						<label>
							<span>Expiration Month:<em class="req">*</em> </span>
							<tags:fieldError id="Expiration Month" oldClass="errorBox">
								<s:if test="%{newCreditCard.isSearsWhiteCard}">	
									<s:select name="newCreditCard.expirationMonth"
										id="expirationMonth" headerKey="-1" headerValue="MM"
										list="monthList" size="1" theme="simple" 										
										value="-1" />
									</s:if>
									<s:else>
									<s:select name="newCreditCard.expirationMonth"
										id="expirationMonth" headerKey="-1" headerValue="MM"
										list="monthList" size="1" theme="simple"
										value="newCreditCard.expirationMonth" />
									</s:else>	
							</tags:fieldError>
						</label>
						<label>
							<span>Expiration Year:<em class="req">*</em> </span>
							<tags:fieldError id="Expiration Year" oldClass="errorBox">
								<s:select name="newCreditCard.expirationYear" id="expirationYear" headerKey="-1" headerValue="YYYY" list="yearList" size="1" theme="simple" value="newCreditCard.expirationYear" />
							</tags:fieldError>
						</label>
						<label>
							<span><s:checkbox name="checkboxSaveThisCard" value="%{checkboxSaveThisCard}" theme="simple" /> </span> Save This Card
						</label>					
					</div>
					<div class="clearfix"></div>
					
					<c:if test="${showTaxPayerWidget == true}">
						<div class="hpDescribe clearfix" style="clear: both;">
							<h2>Taxpayer Identification </h2>
							<p>
								You have reached a certain funding level in ServiceLive which
								requires us to ask you for your taxpayer ID number. Your
								taxpayer identification number is either your employer
								identification number (EIN) if you are a business, or your
								Social Security number if you are a Sole Proprietor or
								individual.
							</p>
 							<p>
                  				<input type="radio" name="einSsnInd" checked value="1" /> EIN <input type="radio" name="einSsnInd" value="2" /> SSN <br/>
             				</p>
							<label style="display: block;"> Taxpayer ID (EIN or SSN) <em class="req">*</em>
							    <br />
								<tags:fieldError id="Taxpayer ID(EIN or SSN)" oldClass="errorBox">
									<s:textfield name="taxPayerId" value="%{taxPayerId}" theme="simple" maxlength="9" />
								</tags:fieldError>
							</label>

							<label style="display: block;"> Confirm Taxpayer ID (EIN or SSN) <em class="req">*</em>
								<br />
								<tags:fieldError id="Confirm Taxpayer ID(EIN or SSN)" oldClass="errorBox">
									<s:textfield name="confirmTaxPayerId" value="%{confirmTaxPayerId}" theme="simple" maxlength="9" />
								</tags:fieldError>
							</label>
							<h4> Since you have reached a certain level of payment transaction in the ServiceLive platform, you need to provide us with this additional information.</h4>
						</div>
					</c:if>
					<div style="margin-left: 146px; margin-bottom: 9px;">
					
							<tags:fieldError id="Buyer Terms And Conditions" oldClass="paddingBtm">	
								<s:checkbox name="buyerTermsAndConditionAgreeInd" id="simpleBuyerTermsCondChk" value="%{buyerTermsAndConditionAgreeInd}"  cssStyle="vertical-align: middle; margin-right: 7px" />							
								<label style="margin-bottom:5px;">I agree to the <a href="#" class="buyerTermsCondA" >Buyer Terms and Conditions</a></label>
							</tags:fieldError>
					
     				</div>
     				<div style="margin-left: 146px; margin-bottom: 12px;">
							<tags:fieldError id="Payment Services offered on ServiceLive" oldClass="paddingBtm">	
									<s:checkbox name="slBucksAgreeInd"	id="simpleBuyerServiceLiveBucksChk"		value="%{slBucksAgreeInd}"  cssStyle="vertical-align: middle; margin-right: 7px"/>
									<input type="hidden" name="slBucksAgreeId"	id="simpleBuyerServiceLiveBucksChkHidden"		value="${accountDTO.slBucksAgreeId}" />							
								<label style="margin-bottom:5px;">I agree to the <a href="#" class="serviceLiveBucksA" >Payment Services*</a> offered on ServiceLive</label>
					  </tags:fieldError>
     				</div>										
				<div style="margin-bottom:20px;">
				<div class="left" style="height:50px;" >									
				  <span style="color: red; margin-right: 5px;">NOTE:</span>
				</div>
				<div class="left" style="width:75%" >
				
				Please submit only once.  Your credit card will be charged once for each time you submit the form.
				If your card does not have an expiration date, you do not need to enter. 				
				
			
				</div>	
				</div>
		 </s:if>
		 <br/>
			<div class="textcenter">
				<div id="submitDiv" class="clearfix buttnNav" style="display: block;>
					<a href="javascript:fnSubmitToAction('previous')"><img src="${staticContextPath}/images/simple/button-back.png" class="leftinput" /></a>
					<a href="javascript:fnSubmitToAction('next')"><img src="${staticContextPath}/images/simple/button-submit-order.png" class="rightinput" /></a>				
				</div>
				<div id="disabledSubmitDiv" style="display: none">
					Submitting Order ....
				</div>
				<c:if test="${(appMode == editMode)  ||(appMode == copyMode)}">
					<div class="right">	
						<s:a href="serviceOrderMonitor.action?displayTab=Saved">Cancel Edit</s:a>
					</div>	
				</c:if>
			</div>
			<div>
			* Powered by Integrated Payments Systems, Inc. Licensed as a Money Transmitter by the Banking Department of the State of the New York.
			</div>
			<!-- Modal dialogs for Terms Conditions and ServiceLivbe Bucks Agreements -->
			<div id="buyerTermsCondModal" class="jqmWindowTerms">
				<div style="height: 300px; overflow: auto">
					${accountDTO.buyerTermsAndConditionText}
				</div>
				<p align="center">
					<input type="button" value="Agree" id="simpleBuyerTermsCondAgreeBtn"
						class="jqmClose" onclick="showElement('accountState');" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="Cancel" id="cancelBtn" class="jqmClose" onclick="showElement('accountState');" />
				</p>
			</div>
			<div id="serviceLiveBucksModal" class="jqmWindowTerms_slb">
			<div style="height: 300px; overflow: auto">
				${accountDTO.slBucksText}
			</div>
			<p align="center">
				<input type="button" value="Agree"
					id="simpleBuyerServiceLiveBucksAgreeBtn" class="jqmClose" onclick="showElement('accountState');" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="Cancel" id="cancelBtn" class="jqmClose" onclick="cancelElement('accountState','simpleBuyerServiceLiveBucksChk');" />
			</p>
		</div>
		</s:form>
		
	</div>


		<div id="hpSidebar">
			<s:action namespace="wallet" name="serviceLiveWallet" executeResult="true" />

			<div id="hpTip" class="widget tips">
				<div class="i46">
					<h2>Records FYI</h2>
					<p>You'll always have your records available to you, which makes it easier to track, budget, and plan your home maintenance and upgrades.</p>
				</div>
				<div class="i15">
					<p><br /><br />
						Payment services powered by:
						<br />
						<small>Integrated Payment Systems, Inc.</small>
					</p>
				</div>
												
			</div>
		</div>
		</div>

		<!-- acquity: empty divs to ajax the modal content into -->
		
		<div id="serviceFinder" class="jqmWindow"></div>
		<div id="modal123" class="jqmWindowSteps"></div>
		<div id="zipCheck" class="jqmWindow"></div>

		<!-- START FOOTER -->
		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		<!-- END FOOTER -->
</body>
</html>

