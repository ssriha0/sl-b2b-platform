<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="com.newco.marketplace.security.ActivityMapper"%>
<%@page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="INSURANCE_STAT" value="<%=(String)session.getAttribute(OrderConstants.INSURANCE_STATUS)%>"></c:set>
<c:set var="FROM_MODAL_IND" value="<%=(String)session.getAttribute("frompopup")%>"></c:set>
<c:set var="TAB_NAME" value="<%=(String)session.getAttribute(OrderConstants.CURRENT_TAB)%>"></c:set>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="isPA" value="${sessionScope.tabDto.tabStatus['Insurance'] != null || isFromPA}"/>
<c:set var="FOR_WIDGET_IND" value="<%=(String)session.getAttribute("forApprovalWidget")%>"></c:set>
	
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<c:set var="noJs" value="true" scope="request"/> <%-- tell header not to insert any JS --%>
<c:set var="noCss" value="true" scope="request"/><%-- tell header not to insert any CSS --%>
<html>
	<head>

		<meta http-equiv="Content-Type"
			content="text/html; charset=iso-8859-1" />
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">

		<title>ServiceLive [Provider Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>

		
			<!-- SL-19459 Code added for new alert message for 'Remove Credential' -->
			<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		
		<script type="text/javascript">
			jQuery.noConflict();
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dijit.form.Button");
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dojo.parser");
			dojo.require("dijit.layout.LinkPane");
			dojo.require("newco.jsutils");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
			var insuranceStatus = '${INSURANCE_STAT}'; 
			var currentTab = '${TAB_NAME}';
			var fromModalFlag = '<%=(String)session.getAttribute("frompopup")%>'; 
			var isAdmin='${FOR_WIDGET_IND}'; 
			var contextPath = '${pageContext.request.contextPath}';
			newco.jsutils.setGlobalContext('${contextPath}');
			newco.jsutils.setGlobalStaticContext('${staticContextPath}');
			 <!-- page prov_reg_all_tabs.file -->
			
			dojo.addOnLoad(function(){
				jQuery.noConflict();
    			var w =dijit.byId("mainTabContainer");      					 			
    			 if(currentTab =='tab4'){    		 	 
    				 document.getElementById("InsuranceRightPane").style.display = 'block';
    				 if(insuranceStatus == '0' && fromModalFlag != '1'){
    				 	handleInsuranceInformationModal();
    				 }
    			 }
    			 else{  	       			
  	       			document.getElementById("InsuranceRightPane").style.display = 'none';
  	       		}
    			dojo.connect(w, "selectChild", tabClickFunction);   			
  			});
			function tabClickFunction(linkPane){	
			    currtab = linkPane.id;  	      		  
  	       		if (currtab == 'dijit_layout_LinkPane_3' ){  	       			
  	       			document.getElementById("InsuranceRightPane").style.display = 'block';
  	       		}else{  	       			
  	       			document.getElementById("InsuranceRightPane").style.display = 'none';
  	       		}	       		
  	       		if (currtab == 'dijit_layout_LinkPane_2' ){  	       			
  	       			document.getElementById("LicenseRightPane").style.display = 'block';
  	       		}else{  	       			
  	       			document.getElementById("LicenseRightPane").style.display = 'none';
  	       		}
			    if (currtab == 'dijit_layout_LinkPane_3' && insuranceStatus == '0'){
			    	handleInsuranceInformationModal();
    			}
    			
    			clickRequestOmniture("ProFirmProfile." + currtab);  			
    		}
			  
		</script>
		<script type="text/javascript">
		function copyValue1()
		{	
			document.getElementById("mailingStreet1").value = document.getElementById("businessStreet1").value;
			document.getElementById("mailingStreet2").value = document.getElementById("businessStreet2").value;
			document.getElementById("mailingAprt").value = document.getElementById("businessAprt").value;
			document.getElementById("mailingCity").value = document.getElementById("businessCity").value;
			document.getElementById("mailingState").value = document.getElementById("businessState").value;
			document.getElementById("mailingZip").value = document.getElementById("businessZip").value;
		}
		</script>
		
		<!-- SL-19459 Code added for new alert message for 'Remove Credential' -->
	<link href="${staticContextPath}/javascript/confirm.css" rel="stylesheet" type="text/css" />
	
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_order_wizard.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.datepicker.css"/>

	<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/js_registration/tooltip.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/js_registration/utils.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/calendar.css" media="screen"></link>
	<script type="text/javascript" src="${staticContextPath}/javascript/js_registration/calendar.js"></script>
			<script type="text/javascript">
			function openBucks(){
				newco.jsutils.displayModal('Bucks');
			}
			function closeBucks(){
				newco.jsutils.closeModal('Bucks');
			}
			function agreeBucks(){
				document.getElementById("termsAndCondDto.acceptBucksTerms").checked="checked"				
				newco.jsutils.closeModal('Bucks');
			}
			function setCheckBox(action){
			acceptTermsValue=$("termsAndCondDto.acceptTerms").checked;
			acceptBucksTermsValue=$("termsAndCondDto.acceptBucksTerms").checked;
			//SLT-2236:New checkbox
			acceptNewBucksTermsValue=$("termsAndCondDto.acceptNewBucksTerms").checked;
			if(acceptTermsValue){
				$("termsAndCondDto.acceptTermsInd").value=1;
			}else{				
				$("termsAndCondDto.acceptTermsInd").value=0;
			}
			if(acceptBucksTermsValue){
				$("termsAndCondDto.acceptBucksTermsInd").value=1;
			}else{
				$("termsAndCondDto.acceptBucksTermsInd").value=0;
			}
			//SLT-2236:New checkbox
			if(acceptNewBucksTermsValue){
				$("termsAndCondDto.acceptNewBucksTermsInd").value=1;
			}else{
				$("termsAndCondDto.acceptNewBucksTermsInd").value=0;
			}
			var mainForm = document.getElementById('termsAndCondForm');
			mainForm.action=action;
			mainForm.submit();	
			}
				//To check for the special chars('<' and '>') - for XSS
			function checkSpecialKeys(e) {		
				var evtobj=window.event? event : e ;
				var keyVal =(window.event) ? event.keyCode : e.which;		
				if(evtobj.shiftKey){
					if (keyVal == 188 || keyVal == 190 || keyVal == 60 || keyVal == 62 ){//188 - <,190 - > --- In IE & 60 - <,62 - > --- in Mozilla
		            	return false;
		        	}else{
		            	return true;
		       		 }
				}       
		     }
		     function checkSpecialChars(e){
		     	var pattern= new RegExp("[<>{}\\[\\]\\&()]");
		     	var el = document.getElementById('businessinfoDto.description');
			    if(el){
			        var commentsVal = el.value;
					var isMatch= commentsVal.match(pattern);
					if(isMatch == null){
						document.getElementById('descriptionError').style.visibility = "hidden";
						return true;
					}else{
						if(document.getElementById('descriptionError')){
							//if(document.getElementById('commonError'))
								//document.getElementById('commonError').style.visibility = "hidden";
							document.getElementById('descriptionError').style.visibility = "visible";
							el.value = "";//If there is a match then clear the input.
							return false;
						}
					}		
				}
		     }  
			</script>

	<style type="text/css">
		.modalOverlay {
			width: 100%;
			height: 100%;
			position:absolute;
			z-index: 400;
			background: #000;
			top:0px;
			left: 0px;
			filter:alpha(opacity=50);
			-moz-opacity:0.5;
			-khtml-opacity: 0.5;
			opacity: 0.5;
			display: none;
			}
	</style>
	</head>
	
	<body class="tundra">
	
	<div class="modalOverlay"></div>
		<div id="page_margins">
			<div id="page" class="clearfix">
				<!-- BEGIN HEADER -->
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>			                        

					<div id="pageHeader">
						<div>
							<img
								src="${staticContextPath}/images/images_registration/sp_firm_registration/hdr_editProvFirmProfile.gif"
								alt="Edit | Provider Firm Profile"
								title="Edit | Provider Firm Profile" />
						</div>
					</div>
				</div>

				<!-- END HEADER -->
				<div id="serviceLiveBucksModal" class="jqmWindow">
					<div style="height:300px;overflow:auto"> ${termsAndCondDto.slBucksText} </div>
					<p align="center">
						<input type="button" value="Agree" id="providerServiceLiveBucksAgreeBtn" class="jqmClose" />
						&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Cancel" id="cancelBtn" class="jqmClose" />
					</p>
				</div>

				<div class="colRight255 clearfix">
					<tags:security actionName="auditAjaxAction">
					<c:if test="${SecurityContext.slAdminInd}">		
						<form id="theFormContainer" name="theFormContainer">
						<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" >
							<jsp:param name="widgetDisplayName" value="true" />
						</jsp:include>
						    <input type="hidden" id="companyOrServicePro.currentKey" name="companyOrServicePro.currentKey" />
							<input type="hidden" id="companyOrServicePro.currentVal" name="companyOrServicePro.currentVal" />
							<input type="hidden" id="companyOrServicePro.selectType" name="companyOrServicePro.selectType" />
							<input type="hidden" id="companyOrServicePro.sendEmail" name="companyOrServicePro.sendEmail" value="1" />
							<input type="hidden" id="companyOrServicePro.subSelectName" name="companyOrServicePro.subSelectName" />
							<input type="hidden" id="commonNoteSubject" name="noteReq.commonNoteSubject" />
							<input type="hidden" id="commonMessageNote" name="noteReq.commonMessageNote" />
							<input type="hidden" id="companyOrServicePro.actionSubmitType" name="actionSubmitType" value="ProviderCompany"/>
						</form>
					</c:if>			
				   </tags:security>
				   
					<c:if test="${SecurityContext.regComplete !=null && SecurityContext.regComplete == false}">								
						<jsp:include flush="true" page="modules/reg_status.jsp"></jsp:include>
					</c:if>	
					<div id="LicenseRightPane" style="display: none;" >										
						<jsp:include flush="true" page="modules/license_right_pane.jsp"/>					
					</div>
					<div id="InsuranceRightPane" style="display: none;" >										
						<jsp:include flush="true" page="modules/insurance_right_pane.jsp"/>					
					<div class="clear"></div>


<!-- Modal dialog for the Insurance tab -->
<div id="providerInsuranceDetailsModal" class="jqmInsuranceWindow" style="z-index: 500; height: 880px;">
	<div class="modalHomepage">
		<div id="insurancePopupHeader" align="left" ></div>
		<a href="#" class="jqmClose">Close</a>
	</div>
	<c:if test="${sessionScope.tabDto.tabStatus['Insurance'] != null || isFromPA == true}">
		<div id="insuranceApprovalWidget" style="display:none">
		<tags:security actionName="auditAjaxAction">
			<%@ include file="/jsp/auditor/commonCredentialApprovalWidget.jsp"%>
		</tags:security>
		</div>
	</c:if>
	<jsp:include page="/jsp/providerRegistration/modules/tab_insurance_add_edit_modal.jsp" />
</div>

<!-- Modal dialog for the Insurance Information -->
<div id="newProviderInsuranceInformationModal" class="jqmInsuranceInformationWindow" style="z-index: 500;overflow: auto;">
	<div class="modalHomepage">
		<div align="left" style="font-size: 12px">
			<label style="color:#FCFCFB" >
				Insurance FAQs
			</label>
		</div>
		<a href="#" class="jqmClose">Close</a> 
	</div>
	<jsp:include page="/jsp/providerRegistration/modules/insurance_information_modal.jsp" />
</div>

				</div>							
				</div>
				<!-- START TAB PANE -->
				<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
					style="height: 1000px; width: 711px; margin: 0;"
					class="provProfileTabs">
					<!-- TAB 1 -->
					<a dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == null or tabView == 'tab1'}">selected="true"</c:if>
						href=<c:choose><c:when test="${(tabView == null || tabView == 'tab1') && nexturl != null}">
      			"${nexturl }"
      		</c:when>
						<c:otherwise>
      			"<s:url action="jsp/providerRegistration/businessinfoActiondoLoad.action"/>"
      		</c:otherwise></c:choose>>
						<span
						class="tabIcon 
      		<c:choose><c:when test="${tabDto.tabStatus['Business Information'] == null}">
      			incomplete
      		</c:when>
      		<c:otherwise>
      			${tabDto.tabStatus['Business Information'] }
      		</c:otherwise></c:choose>
      	"
						id="tab1">Business<br> Information</span> </a>

					<!-- TAB 2 -->
					<a dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab2'}">selected="true"</c:if>
						href=<c:choose><c:when test="${(tabView == null || tabView == 'tab2') && nexturl != null}">
      			"${nexturl }"
      		</c:when>
						<c:otherwise>
      			"<s:url action="jsp/providerRegistration/doWarrantydoLoad.action"/>"
      		</c:otherwise></c:choose>>
						<span
						class="tabIcon 
      		<c:choose><c:when test="${tabDto.tabStatus['Warranty & Policies'] == null}">
      			incomplete
      		</c:when>
      		<c:otherwise>
      			${tabDto.tabStatus['Warranty & Policies'] }
      		</c:otherwise></c:choose>
      	"
						id="tab2">Warranties &<br> Policies</span> </a>

					<!-- TAB 3 -->
					<a dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab3'}">selected="true"</c:if>
						href=<c:choose><c:when test="${(tabView == null || tabView == 'tab3') && nexturl != null}">
      			"${nexturl}"
      		</c:when>
						<c:otherwise>
      			"<s:url action="listLicenceAndCertdoViewList1.action">
      			</s:url>"
      		</c:otherwise></c:choose>><span
						class="tabIcon 
		<c:choose><c:when test="${tabDto.tabStatus['Credentials'] == null}">
      			incomplete
      		</c:when>
      		<c:otherwise>
      			${tabDto.tabStatus['Credentials'] }
      		</c:otherwise></c:choose>
      	"
						id="tab3">Licenses &<br> Certifications</span> </a>
					
					<!-- TAB 4 -->
					<a dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab4'}">selected="true"</c:if>
						href="
      	 	<c:if test="${tabType == 'level1'}">
      	 		<s:url action="jsp/providerRegistration/addInsuranceActionsaveInsuranceList.action"/>
      	 	</c:if>
      	 	<c:if test="${tabType == 'level2'}">	 	 
      	 		<s:url action="jsp/providerRegistration/listInsuranceActionlistInsurance.action"/>
      	 	</c:if>
      		<c:if test="${tabType =='level4'}">
      			<s:url action="/jsp/providerRegistration/loadInsuranceActiondoLoad.action"/>
      		</c:if>
			<c:if test="${tabType =='level5'}">
      			<s:url action="/jsp/providerRegistration/addAdditionalInsuranceActiondoLoadAdditionalInsurance.action">
      			<s:param name="auditTimeLoggingId" value="{#request['auditTimeLoggingId']}"></s:param>
      			</s:url>
      		</c:if>
      		<c:if test="${tabType !='level4' && tabType !='level5'}">
      	 		<s:url action="addInsuranceActionloadInsuranceList.action"/>
      	 	</c:if>      	 
       ">
						<span
						class="tabIcon 
		<c:choose><c:when test="${tabDto.tabStatus['Insurance'] == null}">
      			incomplete
      		</c:when>
      		<c:otherwise>
      			${tabDto.tabStatus['Insurance'] }
      		</c:otherwise></c:choose>
      	"
						id="tab4" style="">Insurance</span> </a>

			
					<!-- 
      <c:if test="${tabType == 'level1'}">
      	<a dojoType="dijit.layout.LinkPane" <c:if test="${tabView == 'tab4'}">selected="true"</c:if> href="<s:url action="jsp/jsp_registration/html/addInsuranceActionsaveInsuranceList.do"/>"><span class="tabIcon incomplete" id="tab4" style="">Insurance</span></a>
      </c:if>
      
      <c:if test="${tabType == 'level2'}">
      	<a dojoType="dijit.layout.LinkPane" <c:if test="${tabView == 'tab4'}">selected="true"</c:if> href="<s:url action="jsp/jsp_registration/html/listInsuranceActionlistInsurance.do"/>"><span class="tabIcon incomplete" id="tab4" style="">Insurance</span></a>
      </c:if>
      <c:if test="${tabType =='level4'}">
      		<a dojoType="dijit.layout.LinkPane" <c:if test="${tabView == 'tab4'}">selected="true"</c:if> href="<s:url action="/jsp/jsp_registration/html/loadInsuranceActiondoLoad.do"/>"><span class="tabIcon incomplete" id="tab4" style="">Insurance</span></a>
      </c:if>
      
      <c:if test="${tabType !='level4'}">
      		<a dojoType="dijit.layout.LinkPane" <c:if test="${tabView == 'tab4'}">selected="true"</c:if> href="<s:url action="jsp/jsp_registration/html/addInsuranceActionloadInsuranceList.do"/>"><span class="tabIcon incomplete" id="tab4" style="">Insurance</span></a>
      </c:if>
       -->

					<!-- TAB 5 -->
					<a dojoType="dijit.layout.LinkPane"
						<c:if test="${tabView == 'tab5'}">selected="true"</c:if>
						href=<c:choose><c:when test="${(tabView == null || tabView == 'tab5') && nexturl != null}">
      			"${nexturl}"
      		</c:when>
						<c:otherwise>
      			"<s:url action="jsp/providerRegistration/termsAndCondActiondoLoad.action"/>"
      		</c:otherwise></c:choose>>
						<span
						class="tabIcon 
		<c:choose><c:when test="${tabDto.tabStatus['Terms & Conditions'] == null}">
      			incomplete
      		</c:when>
      		<c:otherwise>
      			${tabDto.tabStatus['Terms & Conditions'] }
      		</c:otherwise></c:choose>
      	"
						id="tab5">Terms &<br> Conditions</span> </a>

				</div>
				<!-- END TAB PANE -->





				
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
		<div dojoType="dijit.Dialog" id="widgetActionSuccess" title="Quick Action Response">
			<b>Action Completed Successfully</b><br>
			<div id="messageS"></div>
		</div>
		<div dojoType="dijit.Dialog" id="widgetActionError" title="Quick Action Response">
			<b>Action Failed please review</b><br>
			<div id="message"></div>
		</div>
			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProFirmProfile"/>
	</jsp:include>	
		
	</body>
</html>