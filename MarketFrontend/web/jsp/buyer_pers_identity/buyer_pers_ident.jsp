<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="successMessage" scope="request"
	value="<%=session.getAttribute("savePIISuccessMsg")%>" />
<c:set var="piiSaveErrorMessage" scope="request"
	value="<%=session.getAttribute("savePIIErrorMsg")%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ServiceLive - Buyer PII</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />

		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
	
</style>
		
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/jquery-form.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.Dialog");
			dojo.require("dojo.parser");
			dojo.require("dijit.layout.LinkPane");
			dojo.require("dijit.TitlePane");
		</script>

		
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<link rel="stylesheet" href="${staticContextPath}/css/acquity.css">  
		

		 <style type="text/css">
				 .ui-datepicker-week-end a {
			    background-color: #ECF7E6 !important;
			}
			.ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default, .ui-button, html .ui-button.ui-state-disabled:hover, html .ui-button.ui-state-disabled:active {
		    border: 1px solid #c5c5c5;
		    background: #FFF;
		    font-weight: normal;
		    color: #000;
		}
		</style>

		<script type="text/javascript">
	
				jQuery(document).ready(function($) {
						
						if(${buyerPIIDTO.einSsnInd} == 1 ){
						
							$("#ein").attr("checked",true);
							
							showEin();
							
							if(jQuery('#einTaxPayerId').val()!=""){
								if (${disableInd} ==true)
								{								
									jQuery('#einTaxPayerId').attr("disabled", true);
									jQuery('#confirmEinTaxPayerId').attr("disabled", true);
									jQuery('#editEIN').show();
								}
								else
								{
									jQuery('#einTaxPayerId').attr("disabled", false);
									jQuery('#confirmEinTaxPayerId').attr("disabled", false);
									
									if (${taxIdInd}==1){
									document.getElementById("editEIN").innerHTML = "Cancel";
									document.getElementById("ssnSaveInd").value='true';
									}else{jQuery('#editEIN').hide();}
								}
							}
							else 
							{
								if (${taxIdInd}==1){
									document.getElementById("editEIN").innerHTML = "Cancel";
									document.getElementById("ssnSaveInd").value='true';
									jQuery('#editEIN').show();
								}else{jQuery('#editEIN').hide();}
							}
							if(jQuery('#ssnTaxPayerId').val()!=""){
								
								if (${taxIdInd}==2){
									jQuery('#ssnTaxPayerId').attr("disabled", true);
									jQuery('#confirmSsnTaxPayerId').attr("disabled", true);
									jQuery('#editSSN').show();
								}else{
									jQuery('#ssnTaxPayerId').attr("disabled", false);
									jQuery('#confirmSsnTaxPayerId').attr("disabled", false);
									jQuery('#editSSN').hide();
								}
							}
							
							if(jQuery('#altIdNo').val()!=""){
								
								if (${taxIdInd}==3){
									jQuery('#altIdNo').attr("disabled", true);
									jQuery('#editALTNO').show();
								}else{
									jQuery('#altIdNo').attr("disabled", false);
									jQuery('#editALTNO').hide();
								}	
							}
							
							
						}else if (${buyerPIIDTO.einSsnInd} == 2){
							$("#ssn").attr("checked",true);
							
							showSsn();
							
							if(jQuery('#ssnTaxPayerId').val()!=""){
								
								if (${disableInd} ==true)
								{								
									jQuery('#ssnTaxPayerId').attr("disabled", true);
									jQuery('#confirmSsnTaxPayerId').attr("disabled", true);
									jQuery('#editSSN').show();
								}
								else
								{
									jQuery('#ssnTaxPayerId').attr("disabled", false);
									jQuery('#confirmSsnTaxPayerId').attr("disabled", false);
									if (${taxIdInd}==2){
									document.getElementById("editSSN").innerHTML = "Cancel";
									document.getElementById("ssnSaveInd").value='true';
									}else{jQuery('#editSSN').hide();}
								}
							}
							else
							{
								if (${taxIdInd}==2){
									if (document.getElementById("ssnSaveInd").value=='false'){
																			
										var idVal = jQuery('#ssnTaxPayerIdHidden').val();
										jQuery('#ssnTaxPayerId').val(idVal);
										jQuery('#confirmSsnTaxPayerId').val(idVal);
										jQuery('#ssnTaxPayerId').attr("disabled", true);
										jQuery('#confirmSsnTaxPayerId').attr("disabled", true);
										
										document.getElementById("editSSN").innerHTML = "Edit";
										document.getElementById("ssnSaveInd").value='false';
										
									} else{
										document.getElementById("editSSN").innerHTML = "Cancel";
										document.getElementById("ssnSaveInd").value='true';
									}									
									jQuery('#editSSN').show();
								}else{jQuery('#editSSN').hide();}
							}
							
							if(jQuery('#einTaxPayerId').val()!=""){
								if (${taxIdInd}==1){
									jQuery('#einTaxPayerId').attr("disabled", true);
									jQuery('#confirmEinTaxPayerId').attr("disabled", true);
									jQuery('#editEIN').show();
								}else{
									jQuery('#einTaxPayerId').attr("disabled", false);
									jQuery('#confirmEinTaxPayerId').attr("disabled", false);
									jQuery('#editEIN').hide();
								}																
							}
							
							if(jQuery('#altIdNo').val()!=""){
								if (${taxIdInd}==3){
									jQuery('#altIdNo').attr("disabled", true);
									jQuery('#editALTNO').show();
								}else{
									jQuery('#altIdNo').attr("disabled", false);
									jQuery('#editALTNO').hide();
								}	
							}
							
						}else if (${buyerPIIDTO.einSsnInd} == 3){
							$("#alt").attr("checked",true);
							
							showDivAlternateIdInfo();
							
							if(jQuery('#altIdNo').val()!=""){
							
								if (${disableInd} ==true)
								{								
									jQuery('#altIdNo').attr("disabled", true);
									jQuery('#editALTNO').show();
								}
								else
								{
									jQuery('#altIdNo').attr("disabled", false);
									if (${taxIdInd}==3){
									document.getElementById("editSSN").innerHTML = "Cancel";
									document.getElementById("ssnSaveInd").value='true';
									}else{jQuery('#editALTNO').hide();}
								}
							} 
							else 
							{
								if (${taxIdInd}==3){
								if (document.getElementById("ssnSaveInd").value=='false'){
																			
										var idVal = document.getElementById("documentIdNoHidden").value;
										document.getElementById("altIdNo").value = idVal;
										jQuery('#altIdNo').attr("disabled", true);
																				
										document.getElementById("editALTNO").innerHTML = "Edit";
										document.getElementById("ssnSaveInd").value='false';
										
									} else{
										document.getElementById("editALTNO").innerHTML = "Cancel";
										document.getElementById("ssnSaveInd").value='true';
									}					
								jQuery('#editALTNO').show();
								}else{jQuery('#editALTNO').hide();}
							}
							if(jQuery('#ssnTaxPayerId').val()!=""){
								if (${taxIdInd}==2){
									jQuery('#ssnTaxPayerId').attr("disabled", true);
									jQuery('#confirmSsnTaxPayerId').attr("disabled", true);
									jQuery('#editSSN').show();
								}else{
									jQuery('#ssnTaxPayerId').attr("disabled", false);
									jQuery('#confirmSsnTaxPayerId').attr("disabled", false);
									jQuery('#editSSN').hide();
								}
								
							}
						
							if(jQuery('#einTaxPayerId').val()!=""){
								if (${taxIdInd}==1){
									jQuery('#einTaxPayerId').attr("disabled", true);
									jQuery('#confirmEinTaxPayerId').attr("disabled", true);
									jQuery('#editEIN').show();
								} else{
									jQuery('#einTaxPayerId').attr("disabled", false);
									jQuery('#confirmEinTaxPayerId').attr("disabled", false);
									jQuery('#editEIN').hide();
								}
								
							}
							
						}else if (${buyerPIIDTO.einSsnInd} == 0){
						
							$("#ein").attr("checked",true);
							jQuery('#ssnSaveInd').val(true);
							
							jQuery("#einTaxPayerId").unmask();
							jQuery("#einTaxPayerId").val("");
							jQuery("#einTaxPayerId").mask('99-9999999');
							
							jQuery("#confirmEinTaxPayerId").unmask();
							jQuery("#confirmEinTaxPayerId").val("");
							jQuery("#confirmEinTaxPayerId").mask('99-9999999');
							
							jQuery("#ssnTaxPayerId").unmask();
							jQuery("#ssnTaxPayerId").val("");
							jQuery("#ssnTaxPayerId").mask('999-99-9999');
							
							jQuery("#confirmSsnTaxPayerId").unmask();
							jQuery("#confirmSsnTaxPayerId").val("");
							jQuery("#confirmSsnTaxPayerId").mask('999-99-9999');
							showEin();
						}
						if(jQuery('#einTaxPayerId').val()==""){
							jQuery("#einTaxPayerId").unmask();
							jQuery("#einTaxPayerId").val("");
							jQuery("#einTaxPayerId").mask('99-9999999');
						}
						if(jQuery('#confirmEinTaxPayerId').val()==""){
							jQuery("#confirmEinTaxPayerId").unmask();
							jQuery("#confirmEinTaxPayerId").val("");
							jQuery("#confirmEinTaxPayerId").mask('99-9999999');
						}
						if(jQuery('#ssnTaxPayerId').val()==""){
							jQuery("#ssnTaxPayerId").unmask();
							jQuery("#ssnTaxPayerId").val("");
							jQuery("#ssnTaxPayerId").mask('999-99-9999');
						}
						if(jQuery('#confirmSsnTaxPayerId').val()==""){
							jQuery("#confirmSsnTaxPayerId").unmask();
							jQuery("#confirmSsnTaxPayerId").val("");
							jQuery("#confirmSsnTaxPayerId").mask('999-99-9999');
						}
						
						
						
				});
				
				//Show the div to capture alternate id info
 				function showDivAlternateIdInfo(){
 					document.getElementById("alternateIdInfoDivBlock").style.display = "block";
 					document.getElementById("altIdDobBlock").style.display = "block";
 					document.getElementById("countryOfIssuanceBlock").style.display = "block";
 					document.getElementById("dateOfBirth").style.display = "block";
 					
 					document.getElementById("ssnBlock").style.display = "none";
 					document.getElementById("einBlock").style.display = "none"; 				
 					document.getElementById("ssnDobBlock").style.display = "none";
 					document.getElementById("faqLinkEin").style.display = "none";
 				}
 				//Show the div to capture DOB
 				function showSsn(){
  					document.getElementById("ssnBlock").style.display = "block";
 					document.getElementById("dateOfBirth").style.display = "block";
 					document.getElementById("ssnDobBlock").style.display = "block";
 					
 					document.getElementById("einBlock").style.display = "none";
 					document.getElementById("faqLinkEin").style.display = "none";
 					document.getElementById("altIdDobBlock").style.display = "none";
 					document.getElementById("alternateIdInfoDivBlock").style.display = "none";
 					document.getElementById("countryOfIssuanceBlock").style.display = "none";
 				}
 				//Hide the DOB div and alternate TaxId div on click on EIN
 				function showEin(){
 					document.getElementById("einBlock").style.display = "block";
 					document.getElementById("faqLinkEin").style.display = "block";
 					 					
 					document.getElementById("ssnBlock").style.display = "none";
 					document.getElementById("dateOfBirth").style.display = "none";
 					document.getElementById("alternateIdInfoDivBlock").style.display = "none";
 					document.getElementById("countryOfIssuanceBlock").style.display = "none";										
 				}
 				function hideMsgs(){
 				
 					jQuery('#errorMsgDiv').hide();
 					jQuery('#successMsgDiv').hide();
 					jQuery('#piiSaveErrorMsgDiv').hide();
 				
 				}
 				function showDatepicker(clickedId){ 
 					jQuery('#'+clickedId).datepicker({dateFormat:'mm/dd/yy', changeMonth: true, changeYear: true, showButtonPanel: true, minDate: new Date(1900, 12 - 1, 1), yearRange: '-200:+200', maxDate: new Date() }).datepicker( "show" );
 				}
 				
 				function disableSaveButton() {
		    		document.getElementById('formNavButtonsDiv').style.display = 'none';
		  			// show submitting message
		    		document.getElementById('disabledSaveDiv').style.display = 'block';
				}
				
				function closeFAQ()
				{
					jQuery('#popUpfaq').jqmHide();
				}
				
				function showFirstAnswer()
				{
				
				      jQuery("#firstAnswer").slideToggle("slow"); 
				      jQuery("#firstCloseArrow").hide();
				      jQuery("#firstOpenArrow").show();
				      jQuery("#firstQuestion").hide();
				      jQuery("#firstQuestionOpen").show();
				
				}
				function showFirstAnswerOpen()
				{
				
				      jQuery("#firstAnswer").slideToggle("slow"); 
				      jQuery("#firstOpenArrow").hide();
				      jQuery("#firstCloseArrow").show();
				      jQuery("#firstQuestionOpen").hide();
				      jQuery("#firstQuestion").show();
				      
				
				}
				function showSecondAnswer()
				{
				
				      jQuery("#secondAnswer").slideToggle("slow");
				      jQuery("#secondCloseArrow").hide();
				      jQuery("#secondOpenArrow").show();
				      jQuery("#secondQuestion").hide();
				      jQuery("#secondQuestionOpen").show(); 
				      
				
				}
				function showSecondAnswerOpen()
				{
				
				      jQuery("#secondAnswer").slideToggle("slow");
				      jQuery("#secondOpenArrow").hide();
				      jQuery("#secondCloseArrow").show();
				      jQuery("#secondQuestionOpen").hide();
				      jQuery("#secondQuestion").show(); 
				
				}							
				function showPopUpFAQ()
				{
				jQuery("#firstOpenArrow").hide();
				      jQuery("#firstCloseArrow").show();
				      jQuery("#firstQuestionOpen").hide();
				      jQuery("#firstQuestion").show();
				      jQuery("#secondOpenArrow").hide();
				      jQuery("#secondCloseArrow").show();
				      jQuery("#secondQuestionOpen").hide();
				      jQuery("#secondQuestion").show(); 
				      jQuery("#firstAnswer").hide();
				      jQuery("#secondAnswer").hide();
				      
				      
							jQuery('#popUpfaq').jqm({modal:true, toTop: true});
							jQuery('#popUpfaq').jqmShow();
							
				}								
				function popitup(url) {
					newwindow=window.open("<s:url value='buyerEditPIIAction_loadHistory.action'/>",'piihistory','location=1,status=1,scrollbars=1,width=1000,height=600');
					if (window.focus) {newwindow.focus()}
					return false;
				}
				function editEIN(){
					var linkValue = document.getElementById('editEIN').innerHTML;
					if(linkValue == 'Edit'){
					
						jQuery("#einTaxPayerId").unmask();
						document.getElementById("einTaxPayerId").value = "";
						jQuery("#einTaxPayerId").mask('99-9999999');
						
						jQuery("#confirmEinTaxPayerId").unmask();
						document.getElementById("confirmEinTaxPayerId").value = "";
						jQuery("#confirmEinTaxPayerId").mask('99-9999999');
						
						document.getElementById("editEIN").innerHTML = "Cancel";
						jQuery('#einTaxPayerId').attr("disabled", false);
						jQuery('#confirmEinTaxPayerId').attr("disabled", false);
						document.getElementById("ssnSaveInd").value='true';
					}
					if(linkValue == 'Cancel'){
						var idVal = document.getElementById("einTaxPayerIdHidden").value;
						document.getElementById("einTaxPayerId").value = idVal;
						document.getElementById("confirmEinTaxPayerId").value = idVal;
						document.getElementById("editEIN").innerHTML = "Edit";
						jQuery('#einTaxPayerId').attr("disabled", true);
						jQuery('#confirmEinTaxPayerId').attr("disabled", true);
						document.getElementById("ssnSaveInd").value='false';
					}	
				}
				function editSSN(){
					var linkValue = document.getElementById('editSSN').innerHTML;
					if(linkValue == 'Edit'){
					
						jQuery("#ssnTaxPayerId").unmask();
						document.getElementById("ssnTaxPayerId").value = "";
						jQuery("#ssnTaxPayerId").mask('999-99-9999');
						
						jQuery("#confirmSsnTaxPayerId").unmask();
						document.getElementById("confirmSsnTaxPayerId").value = "";
						jQuery("#confirmSsnTaxPayerId").mask('999-99-9999');
						
						document.getElementById("editSSN").innerHTML = "Cancel";
						jQuery('#ssnTaxPayerId').attr("disabled", false);
						jQuery('#confirmSsnTaxPayerId').attr("disabled", false);						
						document.getElementById("ssnSaveInd").value='true';
						
					}
					if(linkValue == 'Cancel'){
						var idVal = document.getElementById("ssnTaxPayerIdHidden").value;
						document.getElementById("ssnTaxPayerId").value = idVal;
						document.getElementById("confirmSsnTaxPayerId").value = idVal;
						document.getElementById("editSSN").innerHTML = "Edit";
						jQuery('#ssnTaxPayerId').attr("disabled", true);
						jQuery('#confirmSsnTaxPayerId').attr("disabled", true);						
						document.getElementById("ssnSaveInd").value='false';
					}	
				}
				function editALTNO(){
					var linkValue = document.getElementById('editALTNO').innerHTML;
					if(linkValue == 'Edit'){
						document.getElementById("altIdNo").value = "";
						document.getElementById("editALTNO").innerHTML = "Cancel";
						jQuery('#altIdNo').attr("disabled", false);
						document.getElementById("ssnSaveInd").value='true';
					}
					if(linkValue == 'Cancel'){
						var idVal = document.getElementById("documentIdNoHidden").value;
						document.getElementById("altIdNo").value = idVal;
						document.getElementById("editALTNO").innerHTML = "Edit";
						jQuery('#altIdNo').attr("disabled", true);
						document.getElementById("ssnSaveInd").value='false';
					}	
				}
				
				function maskInput(clickedId){
					jQuery('#'+clickedId).unmask();
					jQuery('#'+clickedId).val("");
					if (clickedId == "einTaxPayerId" || clickedId =="confirmEinTaxPayerId"){jQuery('#'+clickedId).mask('99-9999999');}
					if (clickedId == "ssnTaxPayerId" || clickedId == "confirmSsnTaxPayerId"){jQuery('#'+clickedId).mask('999-99-9999');}
					
				}
	</script>
	</head>
	<body class="tundra">
	   
		<div id="page_margins">

			<!--BEGIN PAGE -->
			<div id="page" class="clearfix">

				<!-- BEGIN HEADER -->
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />


					<div id="pageHeader">
						<h2>
							<b>Personal Identification Information</b>
						</h2>
					</div>
				</div>
				
				<!-- BEGIN RIGHT PANE -->
				<div class="colRight255 clearfix"></div>

				<s:form action="buyerEditPIIAction_savePII" theme="simple"
					method="POST" onsubmit="javascript:disableSaveButton()">
					<div class="colLeft711">

						<div class="content" style="width: 800px;">

							<div class="darkGrayModuleHdr">
								Taxpayer/Personal Identification Information
							</div>
							<div id="errorBlock" style="display: none">

							</div>
							<div class="grayModuleContent">


								<c:if test="${errors[0] != null}">
									<div class="errorBox clearfix" width="100%" id ="errorMsgDiv">
										<p class="errorMsg">
											<b>Error: Please check the following and try again</b>
										</p>
										<c:forEach items="${errors}" var="error">
											<p style="padding-left: 10px">
												${error.fieldId} * ${error.msg}
											</p>
										</c:forEach>
									</div>
								</c:if>
								<s:if test="%{#session.piiSaveStatus == 'fail'}">
									<div id ="piiSaveErrorMsgDiv" class="errorBox clearfix" width="100%">
										<p class="errorMsg">
											<b>Error: Please check the following and try again</b>
										</p>
										 <p style="padding-left: 10px">
										 	${piiSaveErrorMessage.fieldId} * ${piiSaveErrorMessage.msg}
										 </p>	 
									</div>
										<%
											session.removeAttribute("piiSaveStatus");
										%>
										<%
											session.removeAttribute("savePIIErrorMsg");
										%>
								</s:if>
								<c:if test="${message != null}">
									<p>
										${message}
									</p>
									<br>
								</c:if>
								<s:if test="%{#session.piiSaveStatus == 'success'}">
								<div id ="successMsgDiv">
									<font color="blue" size="1"> ${successMessage} </font>
								</div>
									
									<%
										session.removeAttribute("piiSaveStatus");
									%>
									<%
										session.removeAttribute("savePIISuccessMsg");
									%>
								</s:if>

								<div class="piihistory" align="right">
									<a class="active" href="#"
										onclick="popitup('buyerEditPIIAction_loadHistory.action');">Taxpayer/Personal
										Identification History</a>
								</div>

								<p>

								<div class="jqmWindow2" id="popUpfaq"
									style="width: 800px; display: none;margin-top: 10%;">

									<div class="modalHomepage" style="background-color: white;">

										<div style="float: left;">

										</div>

										<a id="closeLink" href="#" style="color: red;"
											onclick="closeFAQ();">Close</a>

									</div>
									<div style="margin-left: 10px;">
										<h3>
											Frequently Asked Questions - Personal Information
										</h3>	
										<br>
										<br>
										<img id="firstCloseArrow" onclick="showFirstAnswer();"
											style="padding: 0px 4px 0px 5px; position: relative; top: 2px;"
											src="${staticContextPath}/images/widgets/blueRightArrow.gif" />
	
										<img id="firstOpenArrow" onclick="showFirstAnswerOpen();"
											style="display: none; padding: 0px 4px 0px 5px; position: relative; top: 2px;"
											src="${staticContextPath}/images/widgets/grayDownArrow.gif" />
	
										<span id="firstQuestion" class="firstQuestion"
											onclick="showFirstAnswer();"
											style="color: #00A0D2; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: bolder;cursor: pointer;">Why
											do I have to provide Personal Identification Information?</span>
	
										<span id="firstQuestionOpen" class="firstQuestion"
											onclick="showFirstAnswerOpen();"
											style="display: none; color: #666666; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: bolder;cursor: pointer;">Why
											do I have to provide Personal Identification Information?</span>
	
										<br>
										<div id="firstAnswer" style="display: none; border: 1px solid #cecece;margin: 10px;">
	
											<p style="font-size: 10px; padding: 2px 0 6px;">
												To help the government fight the funding of terrorism and
												money laundering activities, Federal Law (31 CFR Parts 1010
												and 1022 of the Bank Secrecy Act Regulations) requires us to
												ask for your name, address, date of birth, and your taxpayer
												identification number.
											</p>
	
										</div>
	
										<br>
										<img id="secondCloseArrow" onclick="showSecondAnswer();"
											style="padding: 0px 4px 0px 5px; position: relative; top: 2px;"
											src="${staticContextPath}/images/widgets/blueRightArrow.gif" />
	
										<img id="secondOpenArrow" onclick="showSecondAnswerOpen();"
											style="display: none; padding: 0px 4px 0px 5px; position: relative; top: 2px;"
											src="${staticContextPath}/images/widgets/grayDownArrow.gif" />
	
										<span id="secondQuestion" class="secondQuestion"
											onclick="showSecondAnswer();"
											style="color: #00A0D2; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: bolder;cursor: pointer;">What if I do not have a U.S. Taxpayer Identification Number? </span>
	
										<span id="secondQuestionOpen" class="secondQuestion"
											onclick="showSecondAnswerOpen();"
											style="display: none; color: #666666; font-family: Verdana, Arial, Helvetica, sans-serif; font-weight: bolder;cursor: pointer;">What if I do not have a U.S. Taxpayer Identification Number? </span>
	
										<br>
										<div id="secondAnswer"	style="display: none; border: 1px solid #cecece;margin: 10px;">
	
											<p style="font-size: 10px; padding: 2px 0 6px;">
												You must provide us with an alternate form of personal
												identification, such as a Passport issued by a recognized
												foreign government.
											</p>	
										</div>
										<br>
										<br>	
									</div>									

								</div>




								<input type="radio" name="buyerPIIDTO.einSsnInd" id="ein"
									value="1" onclick="hideMsgs();showEin()" />
								EIN
								<input type="radio" name="buyerPIIDTO.einSsnInd" id="ssn"
									value="2" onclick="hideMsgs();showSsn()" />
								SSN
								<input type="radio" name="buyerPIIDTO.einSsnInd" id="alt"
									value="3" onclick="hideMsgs();showDivAlternateIdInfo()" />
								I do not have a U.S. Taxpayer ID


								</br>
								</p>
							<input type="hidden" value="${buyerPIIDTO.country}" id="countrySelected" name="countrySelected"/>
							<table width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td span="50%">
											<!-- For ein start -->
											<div id="einBlock" style="display: none;">
												
													Taxpayer ID (EIN)
													<em class="req">*</em>
													<br />
													<tags:fieldError id="Taxpayer ID(EIN or SSN)"
														oldClass="errorBox">
														<s:textfield id = "einTaxPayerId" name="einTaxPayerId" value="%{einTaxPayerId}"
															theme="simple" maxlength="9" onfocus="maskInput(this.id)"/>
														<c:choose>
														<c:when test="%{${buyerPIIDTO.einSsnInd} == 1 && ${einTaxPayerId}!=null}">
														&nbsp;&nbsp;<a id="editEIN" href="#" style="color: blue;"
															onclick="editEIN()">Edit</a>														
														</c:when>
														<c:otherwise>
														&nbsp;&nbsp;<a id="editEIN" href="#" style="color: blue;display:none"
															onclick="editEIN()">Edit</a>
														</c:otherwise>
														</c:choose>
														<s:hidden id="einTaxPayerIdHidden" name="einTaxPayerIdHidden" value="%{einTaxPayerIdHidden}"/>														
													</tags:fieldError>
												
													<br><br>
												
													Confirm Taxpayer ID (EIN)
													<em class="req">*</em>
													<br />
													<tags:fieldError id="Confirm Taxpayer ID(EIN or SSN)"
														oldClass="errorBox">
														<s:textfield id = "confirmEinTaxPayerId" name="confirmEinTaxPayerId"
															value="%{confirmEinTaxPayerId}" theme="simple" maxlength="9" onfocus="maskInput(this.id)"/>
													</tags:fieldError>
												<span style="padding-left:300px;">	
												<img src="${staticContextPath}/images/s_icons/help.png" /><a class="trigger" id="download" onclick="showPopUpFAQ();" href="#">Why do I need to provide this information?</a>
												</span>
												
											<table style="padding-left:503px;padding-bottom:10px;height:0px;" id="faqLinkEin">
												<tbody>
													<tr>											
														<td></td>
														<td></td>
													</tr>
												</tbody>
											</table>
											
											</div>
											<!-- For ein end -->
											<!-- SSN block -->
												<div id="ssnBlock" style="display: none;">
												
													Taxpayer ID (SSN)
													<em class="req">*</em>
													<br />
													<tags:fieldError id="Taxpayer ID(EIN or SSN)"
														oldClass="errorBox">
														<s:textfield id = "ssnTaxPayerId" name="ssnTaxPayerId" value="%{ssnTaxPayerId}"
															theme="simple" maxlength="9" onfocus="maskInput(this.id)"/>
														<c:choose>
														<c:when test="%{${buyerPIIDTO.einSsnInd} == 2 && ${ssnTaxPayerId}!=null}">
														&nbsp;&nbsp;<a id="editSSN" href="#" style="color: blue;"
															onclick="editSSN()">Edit</a>														
														</c:when>
														<c:otherwise>
															&nbsp;&nbsp;<a id="editSSN" href="#" style="color: blue;display:none"
															onclick="editSSN()">Edit</a>
														</c:otherwise>
														</c:choose>
														<s:hidden id="ssnTaxPayerIdHidden" name="ssnTaxPayerIdHidden" value="%{ssnTaxPayerIdHidden}"/>
														<s:hidden id="ssnSaveInd" name="ssnSaveInd"value="%{ssnSaveInd}"/>
													</tags:fieldError>
											
													<br><br>
												
													Confirm Taxpayer ID (SSN)
													<em class="req">*</em>
													<br />
													<tags:fieldError id="Confirm Taxpayer ID(EIN or SSN)"
														oldClass="errorBox">
														<s:textfield id = "confirmSsnTaxPayerId" name="confirmSsnTaxPayerId"
															value="%{confirmSsnTaxPayerId}" theme="simple" maxlength="9" onfocus="maskInput(this.id)"/>
													</tags:fieldError>
												
											</div>		
										<!-- End of SSN block -->
										<!-- Alt id block -->								
											<div id="alternateIdInfoDivBlock" style="display: none;">

												
													Document type
													<em class="req">*</em>
													<br />
													<tags:fieldError id="Type of Document" oldClass="errorBox">
														<s:textfield name="documentType" value="%{documentType}"
															theme="simple" maxlength="35" tabindex="1"/>
													</tags:fieldError>
													<br><br>
												
													Document Identification Number
													<em class="req">*</em>
													<br />
													<tags:fieldError id="Document ID Number"
														oldClass="errorBox">
														<s:textfield name="documentIdNo" id ="altIdNo" value="%{documentIdNo}"
															theme="simple" maxlength="15" tabindex="3"/>
														<c:choose>
														<c:when test="%{${buyerPIIDTO.einSsnInd} == 3 && ${documentIdNo}!=null}">
														&nbsp;&nbsp;<a id="editALTNO" href="#" style="color: blue;"
															onclick="editALTNO()">Edit</a>														
														</c:when>
														<c:otherwise>
															&nbsp;&nbsp;<a id="editALTNO" href="#" style="color: blue;display:none"
															onclick="editALTNO()">Edit</a>
														</c:otherwise>
														</c:choose>
														<s:hidden id="documentIdNoHidden" name="documentIdNoHidden" value="%{documentIdNoHidden}"/>
													</tags:fieldError>
												
											</div>
										</td>
										<td span="50%">
											<div id="countryOfIssuanceBlock" style="display: none;">
												
													Country of Issuance
													<em class="req">*</em>

													<br />


													<tags:fieldError id="Country of Issuance"
														oldClass="paddingBtm">
														<s:select list="countryList" headerValue="Select One"
															name="country" headerKey="-1" tabindex="2"></s:select>
													</tags:fieldError>
												
											</div>
										</td>

									</tr>
								</table>


							<table width="100%" cellpadding="0" cellspacing="0" id="dateOfBirth" style="display:none">
									<tr>
										<td>
											<div id="ssnDobBlock" style="display: none;">
													<br>
													What is your Date of Birth?
													<em class="req">*</em>
													<br />
													<tags:fieldError id="Date of Birth" oldClass="errorBox">
														<s:textfield name="ssnDob" id="ssnDob" value="%{ssnDob}"
													theme="simple" maxlength="10" onfocus="showDatepicker(this.id);" />
													(Note: Date of Birth should be in MM/DD/YYYY format)
													</tags:fieldError>
												
											</div>
											<div id="altIdDobBlock" style="display: none;">
													<br>
													What is your Date of Birth?
													<em class="req">*</em>
													<br />
													<tags:fieldError id="Date of Birth" oldClass="errorBox">
														<s:textfield name="altIdDob" id="altIdDob" value="%{altIdDob}"
													theme="simple" maxlength="10" onfocus="showDatepicker(this.id);" tabindex="4"/>
													(Note: Date of Birth should be in MM/DD/YYYY format)
													</tags:fieldError>
												
											</div>				
									</td>
									
									<td style="padding-top: 3px;padding-left:55px;"><div><br><br>	
										<img src="${staticContextPath}/images/s_icons/help.png" />
										<a class="trigger" id="download" onClick="showPopUpFAQ();" href="#" >Why do I need to provide this information?</a>	
									</div></td>
						</div>
									</tr>
								</table>

							<!-- End of Alt id block -->	



							<div class="clearfix" style="width: 50%; padding: 10px 0px;">
								<div id="formNavButtonsDiv" style= "width: 500px; float: left; padding: 10px 0px;">
									<table cellpadding="0" cellspacing="0" style="width: 30%;">
										<tr>
											<td>
												<s:submit type="input"
													src="%{#request['staticContextPath']}/images/common/spacer.gif"
													cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/save.gif); width:46px; height:18px;"
													cssClass="btn20Bevel" theme="simple" value=" "/>
											</td>
											<td>
												<s:a href="dashboardAction.action">
													<img src="${staticContextPath}/images/common/spacer.gif"
														width="55" height="18"
														style="background-image: url(${staticContextPath}/images/btn/cancel.gif);" />
												</s:a>
											</td>
										</tr>
									</table>
								</div>
							</div>
							<div id="disabledSaveDiv" style="display: none">
								Saving Buyer Personal Identification Information ....
							</div>


						</div>
					</div>
				</s:form>
			</div>

			<!-- BEGIN FOOTER -->
			<div class="clear"></div>
			<c:import url="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</body>
</html>
