<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.newco.marketplace.security.ActivityMapper"%>
<%@page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
<c:set var="noJs" value="false" scope="request" />
<%-- tell header not to insert any JS --%>
<c:set var="noCss" value="false" scope="request" />
<%-- tell header not to insert any CSS --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title>ServiceLive [Service Pro Profile]</title>
<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

<style>
#content_nav {
	font-size: 100%;
}

#content_right {
	background: none;
	width: 705px;
	height: auto;
	padding: 12px 0px 0px 15px;
	font-size: 100%;
}

#content_right_header {
	padding: 0px 0px 10px 0px;
}

.content_right_row_label {
	width: 265px;
}

.content_right_row_box {
	width: 420px;
}

#buttons {
	float: left;
	padding: 20px 0px 40px 0px;
}

#skills {
	float: left;
	width: 400px;
	margin: 10px 0px 0px 0px;
}

.skills_header_row {
	float: left;
	width: 800px;
	font-weight: bold;
	text-align: center;
}

.skills_row {
	float: left;
	width: 800px;
	border-color: #CCCCCC;
	border-style: solid;
	border-top: 1px solid #CCCCCC;
	border-left: 0px none;
	border-right: 0px none;
	border-bottom: 0px none;
	vertical-align: top;
}

.skills_label {
	float: left;
	width: 210px;
}

.skills_item {
	float: left;
	width: 69px;
	text-align: center;
}

.skills_header_item {
	float: left;
	width: 69px;
}

.skills_header_item_blank {
	float: left;
	width: 210px;
}

.lang_item {
	float: left;
	width: 120px;
	text-align: left;
}
</style>
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
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dijit.layout.LinkPane");
			dojo.require("newco.jsutils");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
			
			var contextPath = '${pageContext.request.contextPath}';
			// adduser_all_tabs.file
			newco.jsutils.setGlobalContext('${contextPath}');
			newco.jsutils.setGlobalStaticContext('${staticContextPath}');
			
			dojo.addOnLoad(function(){
				var w =dijit.byId("mainTabContainer");   
    			dojo.connect(w, "selectChild", tabClickFunction);   			
  			});
			function tabClickFunction(linkPane){	
			    currtab = linkPane.id;

			    <c:choose><c:when test="${sessionScope.userStatus=='editUser'}">
			    	clickRequestOmniture("EditServicePro." + currtab);
			    </c:when>
			    <c:otherwise>
			    	clickRequestOmniture("AddServicePro." + currtab); 
			   	</c:otherwise></c:choose> 			
    		}
		</script>
<script type="text/javascript">
			
			function submitDeleteUser()
			{
				if ( window.confirm('You are about to remove the user.Removing the user may will prevent this user from logging into service live with this user ID and removes the users for your business service live users list.select ok to remove and cancel to cancel') ){
		        	
				document.getElementById("generalInfoForm").action = "<s:url action="%{#request['contextPath']}/generalInfoAction!removeUser.action"/>";			
				return true;
				}
				else
					return false;
			}

			function changeCaptionForCredentialNumber()
			{
				 var type = document.getElementById("teamCredentialsDto.typeId").value;
				 var categoryId = document.getElementById("teamCredentialsDto.categoryId").value;			 
				 if(type == 3 && categoryId == 24)
				 {
	            	document.getElementById("credentialNumberReqd").style.visibility="visible";
	            	document.getElementById("credentialNumber").style.visibility="hidden";
	      		 }
	      		 else
	        	 {
	          		document.getElementById("credentialNumberReqd").style.visibility="hidden";
	         		document.getElementById("credentialNumber").style.visibility="visible";
	        	 }
      		}

			function updateCredentialNew()
			{	
				saveCredentialDetail();		
				if(document.getElementById("checkTrue").value=='true')
				{
					saveCredentilDetail();
				}else
				{
					return false;
				}
			}

      		function saveCredentialDetail()
      		{
				if(document.getElementById("isFirstClick").value=='true')
				{
					validateLegalBarNumber();
				}
				else
				{
					document.getElementById("checkTrue").value='true';
				}
      		}

      		function validateLegalBarNumber()
			{
      		  var errorString = '';
   			  document.getElementById("widgetWarningMessageID").style.display="none"; 
   			  
   			  if(document.getElementById("teamCredentialsDto.typeId").value=='3' && document.getElementById("teamCredentialsDto.categoryId").value=='24')
   			  {
	   				var credentialNumber = document.getElementById("teamCredentialsDto.credentialNumber").value;   				 
	            	var isNotValidValue='false';					
	            	var regEx = /^[0-9a-zA-Z\s-]*$/;	             	
	           		if(regEx.test(credentialNumber))
	           		{
	   					isNotValidValue = 'false';	
	   				}					
	   				else
	   				{
	           			isNotValidValue = 'true';
	          		}	
           
		           	if(credentialNumber == null || credentialNumber == '')
		           	{
		              isNotValidValue = 'false';	
		           	}         
		   			if(isNotValidValue=='true')
		   			{
		   				document.getElementById("checkTrue").value='false';
	   					errorString = errorString + 
	   			    	  'The credential number you provided includes symbols. Please verify you have entered the correct information before saving to your profile';	
	   					validationSuccess = true;					
		   										
		   				document.getElementById("warningMsgPara").innerHTML = errorString;
		   				document.getElementById("widgetWarningMessageID").style.display="";
		   				document.location.href = '#widgetWarningMessageID';
					}
		   			else
		   			{			
		   				document.getElementById("widgetWarningMessageID").style.display="none";
		            	document.getElementById("checkTrue").value='true';          				
		   			}
   				 	document.getElementById("isFirstClick").value='false';	   				    		
   			 }
   			 else{
   				document.getElementById("checkTrue").value='true';
   				document.getElementById("isFirstClick").value='false';	
   			 }
		}	
      		 function clearField(id)
       		{
       		  
       			var textValue = jQuery("#"+id).val(); 
       			jQuery("#"+id).css('color','#000000');
       			jQuery("#"+id).css('font-style','normal');
       			if(textValue=='Enter your LEGAL first name'){
       				jQuery("#"+id).val('');     				 
       			}
       			
       		     if(textValue=='Enter your LEGAL last name'){      			
       			 	jQuery("#"+id).val(''); 
       			}
       		}
 		      
 		    function checkIfEmpty(id,name){
 		    	var textValue = jQuery("#"+id).val(); 
 		    	if(textValue==''){
 		    		jQuery("#"+id).val('Enter your LEGAL '+name+' name');
 		    		jQuery("#"+id).css('font-style','italic');
 		    		jQuery("#"+id).css('color','#666666');
            	}
 		    }
 		   
 		   function licenseConfirmationMethod(){
		    	 var selected = [];
		    	 jQuery('#stateNewPopUp input:checked').each(function() {
		    	     selected.push(jQuery(this).attr('name'));
		    	 });
				// alert(selected.length);
				 var x = selected.toString();
				// document.getElementsByName("generalInfoDto.selectedlicenseCheckbox")[0].value=x;
				//$.session.set("popUpInd","0") ;
				 var updateProfileInd  ='${fromUpdateProfile}';
			   if(updateProfileInd==1){
				   window.location.href="${contextPath}/generalInfoActionsaveUpdateProfile.action?selectedCheckBox="+x;
		       }
			   else{
				   window.location.href="${contextPath}/generalInfoActionsaveDetails.action?selectedCheckBox="+x;
			   }
				
		    }
		   function successMethod(){
			   var updateProfileInd  ='${fromUpdateProfile}';
			   if(updateProfileInd==1){
				   window.location.href="${contextPath}/generalInfoActionsaveUpdateProfile.action";
		       }
			   else{
				   window.location.href="${contextPath}/generalInfoActionsaveDetails.action";
			   }
		}
		   
		</script>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
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
	href="${staticContextPath}/css/service_order_wizard.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/styles/plugins/buttons.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/global.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/acquity.css" />

<script type="text/javascript"
	src="${staticContextPath}/javascript/prototype.js"></script>
<script type="text/javascript"
	src="${staticContextPath}/javascript/js_registration/tooltip.js"></script>
<script type="text/javascript"
	src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
<script type="text/javascript"
	src="${staticContextPath}/javascript/js_registration/utils.js"></script>
<script type="text/javascript"
	src="${staticContextPath}/javascript/js_registration/calendar.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>

<script type="text/javascript"
	src="${staticContextPath}/javascript/vars.js"></script>
<style type="text/css">
#yesButton,#noButton {
	background:
		url("${staticContextPath}/images/common/button-action-bg.png");
	border: 1px solid #b1770b;
	color: #222;
	font-family: Arial, Tahoma, sans-serif;
	font-size: 1.1em;
	font-weight: bold;
	padding: 3px 10px;
	cursor: pointer;
	-moz-border-radius: 5px 5px 5px 5px;
	margin-top: -5px;
	text-align: center;
	width: 80px;
}
</style>
<script type="text/javascript">
jQuery.noConflict();
jQuery(document).ready(function($){
	var popUpIndicator = '${popUpInd}';
	if(popUpIndicator==1||popUpIndicator==2)
		{
		providerRegPopup();
		};
		if(popUpIndicator==3){
			providernonDispatchStatePopUp();
		};
});

//SL-19459
//Code added for new alert message for 'Remove Credential'
function removeCred()
{
	jQuery("#declinemodal").addClass("jqmWindow");
	jQuery("#declinemodal").css("width", "625px");
	jQuery("#declinemodal").css("height", "auto");
	jQuery("#declinemodal").css("top", "750px");
	jQuery("#declinemodal").css("border","3px solid lightgrey");
	jQuery("#declinemodal").css("marginLeft", "-350px");
	jQuery("#declinemodal").css("zIndex",1000);
	jQuery("#declinemodal").jqm({modal:true,overlay:0});
	jQuery("#declinemodal").fadeIn('slow');
	jQuery('#declinemodal').css('display', 'block');
	
	jQuery("#declinemodal").jqmShow();
	jQuery(".jqmOverlay").css("opacity",0.5);
	
}	

function closeRemoveCred(){
	
	jQuery("#declinemodal").jqmHide();
}


function providernonDispatchStatePopUp(){
	jQuery("#outOfStatePopUp").addClass("jqmWindow");
	jQuery("#outOfStatePopUp").css("width", "800px");
	jQuery("#outOfStatePopUp").css("height", "auto");
	jQuery("#outOfStatePopUp").css("marginLeft", "-405px");
	jQuery("#outOfStatePopUp").css("zIndex",1000);
	jQuery("#outOfStatePopUp").jqm({modal:true});
	jQuery("#outOfStatePopUp").fadeIn('slow');
	jQuery('#outOfStatePopUp').css('display', 'block');

	
	jQuery("#outOfStatePopUp").jqmShow();
	jQuery(".jqmOverlay").css("opacity",0.5);
}

function providerRegPopup(){
	var legalFullName= '${providerLegalName}';
	var ssn='${legalSSN}';

	//jQuery('#popUpWindow').show();
	jQuery("#popUpWindow").addClass("jqmWindow");
	jQuery("#popUpWindow").css("width", "600px");
	jQuery("#popUpWindow").css("height", "auto");
	jQuery("#popUpWindow").css("marginLeft", "-350px");
	jQuery("#popUpWindow").css("zIndex",1000);
	jQuery("#popUpWindow").jqm({modal:true});
	jQuery("#popUpWindow").fadeIn('slow');
	jQuery('#popUpWindow').css('display', 'block');
	
	jQuery("#popUpWindow").jqmShow();
	var popUpIndicator='${popUpInd}';
	if(popUpIndicator==1){
		jQuery('#newPopUp').css('display', 'block');
		jQuery('div#popUpWindow div#newPopUp b#newProviderFullName').text(legalFullName);
		jQuery('div#popUpWindow div#newPopUp b#legalSocialSecurityNumber').text(ssn);
		}
	else if(popUpIndicator==2){

		jQuery('#savedPopUp').css('display', 'block');
		jQuery('div#popUpWindow div#savedPopUp b#legalSocialSecurityNumber').text(ssn);
		}
	jQuery("#popUpConfirm").css("top", "0px");
	jQuery("#popUpConfirm").css("background-color","#FFFFFF");
	jQuery("#popUpConfirm").css("border-left-color","#A8A8A8");
	jQuery("#popUpConfirm").css("border-right-color","#A8A8A8");
	jQuery("#popUpConfirm").css("border-bottom-color","#A8A8A8");
	jQuery("#popUpConfirm").css("border-top-color","#A8A8A8");
	jQuery(".jqmOverlay").css("opacity",0.5);
};
function closeConfirmPopup(){
	jQuery("#popNoIndicator").val(true);
	jQuery("#popUpWindow").jqmHide();
}
function setAction(action,dto){
	jQuery("#"+dto+"Action").val(action);
}
function setActionTest(action1,action2,dto){
	jQuery("#"+dto+"Action").val(action1);
	jQuery("#"+dto+"activityList").val(action2);
}


function doBackgroundCheckcertify(action,dto) {
	
	var i=jQuery("#"+dto+"Action").val(action);
     		
}

function enableBackgroundCheckFun(plusOneUrl,resourceId,encryptedPlusOneKey){
	
	var url="'"+ plusOneUrl +resourceId+"&parm3="+encryptedPlusOneKey+"&parm4="+"N"+"'";
	window.open(eval(url),'mywindow','width=800,height=800,toolbar=yes, location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,copyhistory=yes, resizable=yes');
}
</script>
</head>

<body class="tundra">
	<div id="page_margins">
	    
		<div id="page" class="clearfix">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				<div id="pageHeader">
					<c:choose>
					<c:when test="${sessionScope.userStatus=='editUser'}">
						<img
							src="${staticContextPath}/images/images_registration/admin/hdr_manageUsersEditUser.gif"
							alt="Manage Users | Edit User" title="Manage Users | Edit User">
					</c:when>
					<c:otherwise>
						<img
							src="${staticContextPath}/images/images_registration/admin/hdr_manageUsersAddNew.gif"
							alt="Manage Users | Add New User"
							title="Manage Users | Add New User">
					</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!-- END HEADER -->

			<jsp:include page="/jsp/sl_login/common/errorMessage.jsp"
				flush="true" />

			<div class="colRight255 clearfix">
				<c:if
					test="${SecurityContext.regComplete != null && SecurityContext.regComplete == false}">
					<jsp:include flush="true" page="modules/reg_status.jsp"></jsp:include>
				</c:if>

				<tags:security actionName="auditAjaxAction">
					<c:if test="${SecurityContext.slAdminInd}">
						<form id="theFormContainer" name="theFormContainer">
							<jsp:include
								page="/jsp/so_monitor/menu/widgetAdminServiceProMemberInfo.jsp">
								<jsp:param name="widgetDisplayName" value="true" />
							</jsp:include>
							<input type="hidden" id="companyOrServicePro.currentKey"
								name="companyOrServicePro.currentKey" /> <input type="hidden"
								id="companyOrServicePro.currentVal"
								name="companyOrServicePro.currentVal" /> <input type="hidden"
								id="companyOrServicePro.selectType"
								name="companyOrServicePro.selectType" /> <input type="hidden"
								id="companyOrServicePro.sendEmail"
								name="companyOrServicePro.sendEmail" value="1" /> <input
								type="hidden" id="companyOrServicePro.subSelectName"
								name="companyOrServicePro.subSelectName" /> <input
								type="hidden" id="commonNoteSubject"
								name="noteReq.commonNoteSubject" /> <input type="hidden"
								id="commonMessageNote" name="noteReq.commonMessageNote" /> <input
								type="hidden" id="companyOrServicePro.actionSubmitType"
								name="actionSubmitType" value="ServiceProCompany" /> <input
								type="hidden" id="companyOrServicePro.theResourceId"
								name="companyOrServicePro.theResourceId"
								value="${sessionScope.resourceId}" />
						</form>
					</c:if>
				</tags:security>
			</div>


			<div id="LicenseRightPane" style="display: none; float: right;">
				<jsp:include flush="true" page="modules/license_right_pane.jsp" />
			</div>


			<!-- START TAB PANE -->

			<!-- START TAB PANE -->
			<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
				style="height: 1900px; width: 711px; margin: 0;"
				class="spProfileTabs">
				<!-- TAB 1 -->
				<a class="" dojoType="dijit.layout.LinkPane"
					<c:if test="${tabView == null or tabView == 'tab1'}">selected="true"</c:if>
					href=<c:choose>
						<c:when test="${(tabView == null || tabView == 'tab1') && nexturl != null}">
      						"${nexturl }"
      					</c:when>
						<c:otherwise>
	      					"<s:url action="generalInfoAction" method="doLoad" />"
	      				</c:otherwise>
	      				</c:choose>>
						      				<div
											class="tabIcon 
					      		<c:choose><c:when test="${sessionScope.tabDto.tabStatus['General Information'] == null}">
					      			incomplete
					      		</c:when>
					      		<c:otherwise>
					      			${sessionScope.tabDto.tabStatus['General Information']}
					      		</c:otherwise></c:choose>
					      	">
						General<br>Information
					</div></a>

				<c:choose>
				<c:when
					test="${sessionScope.tabDto.tabStatus['General Information'] =='complete'}">
					<!-- TAB 2 -->
					<a class="" dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab2'}">selected="true"
						</c:if>
						href=<c:choose>
						<c:when test="${(tabView == null || tabView == 'tab2') && nexturl != null}">
	      			"${nexturl }"
	      				</c:when>
						<c:otherwise>
	      			"<s:url action="marketPlaceAction" method="doLoad"/>"
	      				</c:otherwise>
	      				</c:choose>>
								<div
									class="tabIcon 
			      		<c:choose><c:when test="${sessionScope.tabDto.tabStatus['Marketplace Preferences'] == null}">
			      			incomplete
			      		</c:when>
			      		<c:otherwise>
			      			${sessionScope.tabDto.tabStatus['Marketplace Preferences'] }
			      		</c:otherwise></c:choose>
			      	">
							Marketplace<br>Preferences
						</div>
					</a>
					<!-- TAB 3 -->


					<a class="tab3" dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab3'}">selected="true"</c:if>
						href=<c:if test="${(tabView == null || tabView == 'tab3') && nexturl != null}">
			      			"${nexturl }"
			      		</c:if>
						<c:choose>
						<c:when test="${(tabView == null || tabView == 'tab3') && tabType=='level32'}">
			      			<s:url action="jsp/providerRegistration/resourceSkillAssignAction!getSkills.action"/>
			      		</c:when>
						<c:otherwise>
			      			<s:url action="jsp/providerRegistration/skillAssignGeneralAction!loadSkills.action"/>
			      		</c:otherwise>
			      		</c:choose>>
						<div
							class="tabIcon 
			      		<c:choose>
			      		<c:when test="${sessionScope.tabDto.tabStatus['Skills'] == null}">
			      			incomplete
			      		</c:when>
			      		<c:otherwise>
			      			${sessionScope.tabDto.tabStatus['Skills'] }
			      		</c:otherwise>
			      		</c:choose>
			      	">
							Skills &amp;<br>Services
						</div>
					</a>


<!-- TAB 4 -->
					<a class="tab4" dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab4'}">selected="true"</c:if>
						href=<c:choose>
						<c:when test="${(tabView == null || tabView == 'tab4') && nexturl != null}">
		      			"${nexturl }"
		      			</c:when>
						<c:otherwise>
						<c:choose>
						<c:when test="${tabType == 'level2'}">	 	 
		      	 			"<s:url action="teamCredentialAction" method="loadCredentialDetails">		      	 			
		      	 			<s:param name="auditTimeLoggingId" value="{#request['auditTimeLoggingId']}"></s:param>
		      	 			</s:url>
		      	 			"
		      	 		</c:when>
		      	 		<c:when test="${tabType == 'level3'}">	 	 
		      	 			"<s:url action="teamCredentialAction" method="changeCredCatType">		      	 			
		      	 			<s:param name="auditTimeLoggingId" value="{#request['auditTimeLoggingId']}"></s:param>
		      	 			</s:url>
		      	 			"
		      	 		</c:when>
		      			<c:otherwise>
		      	 			"<s:url action="teamCredentialAction" method="loadCredentialList"/>"
		      	 		</c:otherwise>
		      	 		</c:choose>
		      	 		</c:otherwise>
		      	 		</c:choose>
						>
					
						<div
							class="tabIcon 
	      			<c:choose>	
	      			<c:when test="${sessionScope.tabDto.tabStatus['Credentials'] == null}">
	      			incomplete
	      			</c:when>
	      			<c:otherwise>
	      			${sessionScope.tabDto.tabStatus['Credentials'] }
	      			</c:otherwise>
	      			</c:choose>
	      	">
							Licenses &amp; <br> Certifications
						</div></a>

					<!-- TAB 5 -->
					<a class="" dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab5'}">selected="true"</c:if>
						href=<c:choose>
						<c:when test="${(tabView == null || tabView == 'tab5') && nexturl != null}">
	      			"${nexturl }"
	      		</c:when>
						<c:otherwise>
	      			"<s:url action="backgroundCheckAction" method="doLoad"/>"
	      		</c:otherwise>
	      		</c:choose>>

						<div
							class="tabIcon 
	      		<c:choose>
	      		<c:when test="${sessionScope.tabDto.tabStatus['Background Check'] == null}">
	      			incomplete
	      		</c:when>
	      		<c:otherwise>
	      			${sessionScope.tabDto.tabStatus['Background Check'] }
	      		</c:otherwise>
	      		</c:choose>
	      	">
							Background<br>Check
						</div>
					</a>
					<!-- TAB 6 -->
					<a class="tab6" dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab6'}">selected="true"</c:if>
						href=<c:choose>
						<c:when test="${(tabView == null || tabView == 'tab6') && nexturl != null}">
	      			"${nexturl }"
	      			</c:when>
					<c:otherwise>
	      			"<s:url action="termsAction" method="doLoad"/>"
	      			</c:otherwise>
	      			</c:choose>>
						<div
							class="tabIcon 
	      		<c:choose>
	      		<c:when test="${sessionScope.tabDto.tabStatus['Terms & Conditions'] == null}">
	      			incomplete
	      		</c:when>
	      		<c:otherwise>
	      			${sessionScope.tabDto.tabStatus['Terms & Conditions'] }
	      		</c:otherwise>
	      		</c:choose>
	      	">
							Terms &amp; <br>Conditions
						</div>
					</a>
			</div>
  			</c:when>
			<c:otherwise>
			<!-- -General info completion check else starts -->
				<!-- TAB 2 -->
				<a class=""
					="dijit.layout.LinkPane" 
	      			<c:if test="${tabView == 'tab2'}">selected="true"</c:if>
					href="${contextPath}/jsp/providerRegistration/tabError.jsp">

					<div
						class="tabIcon 
	      		<c:choose>
	      		<c:when test="${sessionScope.tabDto.tabStatus['Marketplace Preferences'] == null}">
	      			incomplete
	      		</c:when>
	      		<c:otherwise>
	      			${sessionScope.tabDto.tabStatus['Marketplace Preferences'] }
	      		</c:otherwise>
	      		</c:choose>">
						Marketplace<br>Preferences
					</div>
				</a>
				<!-- TAB 3 -->
				<a class="tab3" dojoType="dijit.layout.LinkPane"
					<c:if test="${tabView == 'tab3'}">selected="true"</c:if>
					href="${contextPath}/jsp/providerRegistration/tabError.jsp">
					<div
						class="tabIcon 
	      		<c:choose>
	      		<c:when test="${sessionScope.tabDto.tabStatus['Skills'] == null}">
	      			incomplete
	      		</c:when>
	      		<c:otherwise>
	      			${sessionScope.tabDto.tabStatus['Skills'] }
	      		</c:otherwise>
	      		</c:choose>
	      	">
						Skills &amp;<br>Services
					</div>
				</a>


				<!-- TAB 4 -->
				<a class="tab4" dojoType="dijit.layout.LinkPane"
					<c:if test="${tabView == 'tab4'}">selected="true"</c:if>
					href="${contextPath}/jsp/providerRegistration/tabError.jsp">
					<div
						class="tabIcon 
	      		<c:choose>
	      		<c:when test="${sessionScope.tabDto.tabStatus['Credentials'] == null}">
	      			incomplete
	      		</c:when>
	      		<c:otherwise>
	      			${sessionScope.tabDto.tabStatus['Credentials'] }
	      		</c:otherwise>
	      		</c:choose>
	      	">
						Licenses &amp; <br> Certifications
					</div>
				</a>

				<!-- TAB 5 -->
				<a class="" dojoType="dijit.layout.LinkPane"
					<c:if test="${tabView == 'tab5'}">selected="true"</c:if>
					href="${contextPath}/jsp/providerRegistration/tabError.jsp">

					<div
						class="tabIcon 
	      		<c:choose>
	      		<c:when test="${sessionScope.tabDto.tabStatus['Background Check'] == null}">
	      			incomplete
	      		</c:when>
	      		<c:otherwise>
	      			${sessionScope.tabDto.tabStatus['Background Check'] }
	      		</c:otherwise>
	      		</c:choose>
	      	">
						Background<br>Check
					</div>
				</a>
				<!-- TAB 6 -->
				<a class="tab6" dojoType="dijit.layout.LinkPane"
					<c:if test="${tabView == 'tab6'}">selected="true"</c:if>
					href="${contextPath}/jsp/providerRegistration/tabError.jsp">
					<div
						class="tabIcon 
	      		<c:choose>
	      		<c:when test="${sessionScope.tabDto.tabStatus['Terms & Conditions'] == null}">
	      			incomplete
	      		</c:when>
	      		<c:otherwise>
	      			${sessionScope.tabDto.tabStatus['Terms & Conditions'] }
	      		</c:otherwise>
	      		</c:choose>
	      	">
						Terms &amp; <br>Conditions
					</div>
				</a>
		</div>

		</c:otherwise>
		</c:choose>
		<!-- -General info completion check else ends -->
		<!-- END TAB PANE -->

		<div class="clear"></div>


	</div>

	<!-- START FOOTER -->
	<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
	<!-- END FOOTER -->
	</div>
	<div dojoType="dijit.Dialog" id="widgetActionSuccess"
		title="Quick Action Response">
		<b>Action Completed Successfully</b><br>
		<div id="messageS"></div>
	</div>
	<div dojoType="dijit.Dialog" id="widgetActionError"
		title="Quick Action Response">
		<b>Action Failed please review</b><br>
		<div id="message"></div>
	</div>

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		<jsp:param name="PageName" value="AddPro" />
	</jsp:include>

	<!-- SL-18360  pop up for confirmation-->
	<div  id="popUpWindow" style="display: none; margin-top: -275px;">

         <div style="font-family: Arial,Helvetica,sans-serif; font-size: 13px ;height: auto;" >
         <div id="newPopUp" style="display: none;" >
  <div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;" id="modalTitleBlack">
		<span style="font-size: 17px; font-weight: bold;">Legal Name and SSN validation</span> 
	</div>
	<br />
	         <span class="popText" style="position: relative; left: 6px;line-height: 1.5;"> <div style="word-wrap: break-word;">Verify the team member's legal information as entered:<br/></div>
	          <ul style="padding-left: 15px;">
	       <li><b>Legal Full Name:</b>&nbsp<b  id="newProviderFullName"></b><br></li>
	       <li><b>Social Security Number:</b>&nbsp<b  id="legalSocialSecurityNumber"></b><br/></li>
	         </ul>
	         <br/>
	          This information will be used to conduct any background screenings. If it is incorrect, you may delay being Approved for Market.</span></span>
 		</div>
  		 
		  <div id="savedPopUp" style="display: none; height: 165px; padding-left: 7px;">
		   
		  <div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;margin-left: -7px;" id="modalTitleBlack">
		<span style="font-size: 17px; font-weight: bold;">SSN validation</span> 
	</div><br/>
		         <span class="popText" style="padding-left;line-height: 1.5;">Verify the team member's legal information as entered: <br/>
		        <ul style="padding-left: 15px;">
		        <li><b>Social Security Number:</b>&nbsp<b  id="legalSocialSecurityNumber"></b><br/></li>
		        </ul>
		        <br/>
		        This information will be used to conduct any background  screenings. If it is incorrect, you may delay being Approved for Market</span></span>
 		</span>
		 </div>
         <br/>
         <table style="padding-right ;position: relative; left: 8px" width="100%">
			<tr>
				<td width="72%">
					<input id="noButton" type="button" value="Cancel" onclick = "closeConfirmPopup()"/>
					<br/>
				</td>
				<td width="28%">
					<input id="yesButton" type="button" value="The legal info is correct" style="width:150px" onclick="successMethod()"/>	
				</td>
			</tr>
		</table>
		<br/>
         
         </div>
				
				</div>

<!-- begin modal -->
<!-- SL-19459
	Code added for new alert message for 'Remove Credential' -->

<div id="declinemodal" class="modal" style="display: none" >
 <div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 15px; padding-left: 8px; padding-top: 5px;font-size:11px;font-weight:bold" id="modalTitleBlack">
	CONFIRMATION
 </div>	
		<p style="padding: 8px;font-size:11px">
			<strong>		
				Warning: Once you remove this credential, it will no longer be visible to ServiceLive buyers and could negatively impact your membership in certain Select Provider Networks (SPN's).
			</strong>
		</p>
	
		
		   <table style="padding-right ;position: relative; left: 8px; padding-top:10px;padding-bottom:10px" width="100%">
			<tr>
				<td width="72%">
					<input id="noButton" type="button" value="Cancel" style="margin-left: 10px;margin-top: 5px;" onclick = "javascript:closeRemoveCred();"/>
					<br/>
				</td>
				<td width="28%">
					<input id="yesButton" type="button" value="OK" style="width:90px;margin-left:50px;margin-top: 5px;" onclick="javascript:removeCredentilDetail();"/>	
				</td>
			</tr>
		</table>
	<!-- 	
		<div style="background-color: lightgray;height: 50px;text-align:right;">
		
			
			

						<input type="submit" onclick="javascript:removeCredentilDetail();" value="OK" style="margin-top:10px;margin-right:25px;width:60px">	
						<input type="submit" style="margin-right:25px;margin-top:10px;" value="Cancel" onclick="javascript:closeRemoveCred();">					
		
		</div> -->
	</div>
	<!-- SLT-2699  pop up for confirmation for out of state-->
	<div  id="outOfStatePopUp" style="display: none; margin-top: -275px; background-color:#fbd9d9; color:#e85353; font-size: 13px " >

         <div style="font-family: Arial,Helvetica,sans-serif; font-size: 13px ;height: auto; " >
         <div id="stateNewPopUp"  >

	<br /><table>
	<tr>
	<td width="3%"><img src="${staticContextPath}/images/excl.png" alt="" height=100></img></td>
	<td style="font-size: 13px;  word-break: break-word;>
	         <span class="popText" style="position: relative; left: 6px; line-height: 1.5;"> <div style="word-wrap: break-word; padding-right: 2px;"><b>You have selected one or more zip code(s) in following state(s) outside of your dispatch state.  Check the box to confirm you or your company are licensed to do work in the specific state(s) ? </b><br/></div>
			
	        <c:forEach var="stateZipList" items="${providerdetailsDto.stateZipCodeList}" varStatus="stateZip">
			<c:if test="${stateZip.count%10==1}"><br/><br/></c:if>
						<span class="states" style="word-break: break-all;margin-right: 30px">

						<input type="checkbox" name="checkbox_${stateZipList.stateCd}" class="chb" data-bind="checked: params.availToStream" checked/>&nbsp;&nbsp;<b>${stateZipList.stateCd}</b>
						</span>
					</c:forEach>	
			
	         <br/>
	         </span></span>
 		</div>
  		 
		 
         <br/>

         <table style="padding-centre ;position: relative; left: 8px" width="50%">
			<tr>
				
				<td >
				<s:submit  value="" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/save.gif);width:50px;height:20px;"  cssClass="btn20Bevel"  onclick="licenseConfirmationMethod()"> </s:submit>
						
				</td>
			</tr>
		</table>

		<br/>
         
         </div>
				</td>
				</tr></table>
				</div>	
<!-- end modal -->
		
</body>
</html>