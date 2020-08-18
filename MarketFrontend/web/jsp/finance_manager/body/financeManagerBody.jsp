<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page import="com.newco.marketplace.constants.Constants" %>
<%@ page import="com.newco.marketplace.util.PropertiesUtils" %>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="searsBuyerId" scope="session" value="<%= PropertiesUtils.getPropertyValue(Constants.AppPropConstants.SEARS_BUYER_ID)%>" />
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager"/>
	</jsp:include>	

<script type="text/javascript">

//SL-19207: modifying the submit from dojo to jQuery.
//moving the jQuery import to financeManagerTemplate.jsp and handling the issue with two conflicting jQuery versions
//SL-20087, Sl-20090
jQuery(document).ready(
		function($) {
			$('#btnSubmit').click(
					function() {
						$('#resDiv').css("display", "none");
						$('#msg').hide();
						$('#successmsg').hide();
						$('#formNavButtonsDiv').css("display", "none");
						$('#disabledDepositFundsDiv').css("display",
								"block");
						var formValues = $('#fmRefundsAction')
								.serializeArray();
						jQuery('#resDiv').load(
								'fmRefundsAction_save.action', formValues,
								function(data) {
									var xml = data,
									xmlDoc = jQuery_1_10_2.parseXML( xml ),
									$xml = $( xmlDoc ),
									$result_tag = $xml.find( "pass_fail" );
									$message_tag = $xml.find( "message" );
									var passFailRst=$result_tag.text();
									var message=$message_tag.text();

									$('#msg').empty();
									$('#successmsg').empty();
									if (passFailRst != null) {
									if (passFailRst == 1) {
											$('#disabledDepositFundsDiv').css("display",
											"none");
											$('#msg').css("display",
											"block");
											refundSubmit();

										} else {
											$('#disabledDepositFundsDiv').css("display",
											"none");
											$('#msg').text(message);
											$('#msg').css("display",
											"block");
											$('#successmsg').css("display",
											"none");
											$('#formNavButtonsDiv').css("display",
											"block");
										}
									}

								});
					});
		});
		
		
	function refundSubmit(){
		document.getElementById('fmRefundsAction').action = "${contextPath}/financeManagerController_execute.action";
		document.getElementById('fmRefundsAction').method="POST"; 
		document.getElementById('fmRefundsAction').submit();
	}
	
	function fnSubmitRefundsAction(actionName,callBackfunction,param3,formname){
		disableSubmitButton();
		document.getElementById(param3).innerHTML = "";
		fnSubmit(actionName,callBackfunction,param3,formname);		
	}
	
	function disableSubmitButton() {
		 document.getElementById('formNavButtonsDiv').style.display = 'none';
		 // show submitting message
   		document.getElementById('disabledDepositFundsDiv').style.display = 'block';
	}
	
	function fnSubmit(actionName,callBackfunction,param3,formname){
		newco.jsutils.doAjaxSubmit(actionName,callBackfunction,param3,formname);
	}
	
	var issueRefundsCallBackFunction = function(data){
		  var passFailRst = newco.jsutils.handleCBData(data);
		  document.getElementById('msg').innerHTML="";
		  document.getElementById('successmsg').innerHTML="";
		  if(passFailRst != null ){
		  	 if(passFailRst.pass_fail_status == 1) {
		  	    newco.jsutils.updateWithXmlData('successmsg','message',data);
		  	    document.getElementById('disabledDepositFundsDiv').style.display = 'none';
		  	    document.getElementById('msg').style.display = 'block';
		  	 	refundSubmit();
		  	 	
		  	 }
		  	 else{
		  	    newco.jsutils.updateWithXmlData('msg','message',data);
				document.getElementById('disabledDepositFundsDiv').style.display = 'none';
		  	    document.getElementById('msg').style.display = 'block';
		  	 	document.getElementById('successmsg').style.display = 'none';
		  	 	document.getElementById('formNavButtonsDiv').style.display = 'block';

		  	 }
		  }
	}		

newco.jsutils.updateWithXmlData = function(/*element you want updated*/ element1, /*root node of xml*/ rootNode, /*xml data*/ xml){
		if( newco.jsutils.isExist(element1) && rootNode != null){
			theData =    xml.getElementsByTagName( rootNode )
			if(theData == null){
				$(element1).innerHTML = "Error occured";
			}
			if(theData[0].childNodes){
				if(theData[0].childNodes[0]){
					if(theData[0].childNodes[0].nodeValue){
						if(xml.getElementsByTagName( rootNode )[0].childNodes[0].nodeValue != null) 
					       document.getElementById(element1).innerHTML = theData[0].childNodes[0].nodeValue;
					}
				}
			}
	 	}
	} 
	
function limitText(limitField) {
	if (limitField.value.length > 255) {
		alert(limitField.value.length);
		limitField.value = limitField.value.substring(0, 255);
	} 
}

function limitSLText(limitField) {
	var str = document.getElementById(limitField).value;
	if (str.length > 255) {
		alert(str.length);
		document.getElementById(limitField).value= str.substring(0, 255);
	} 
}
	
    
</script>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

	<style type="text/css">
			body.acquity  #finMgrModules { margin-right: -300px;}
			<!--[if IE]>
			body.acquity #finMgrModules { margin-right: 0px;}
			<![endif]-->
	</style>


<!-- START TAB PANE -->
			<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
				style="height: 1450px;  float: left; width: 711px; margin: 80px 0 0 0;" class="tabPaneShort" >
				<c:set var="tabtitle" value="${title}"/>
				<c:set var="withdrawsuccess" value="${WithdrawSuccessMsg}" scope="session" />
				<c:set var="leadgenfeeslink" value="${leadGenFeesLink}"/>
				<c:forEach items="${tabList}" var="tab">
					<a class="${tab.className}" dojoType="dijit.layout.LinkPane"
						href="${tab.action}" selected="${tab.selected}" 
						<c:if test="${tab.className == 'tab4'}">			
						 	 refreshOnShow="true" 
						</c:if>
					>
						<span class="${tab.icon}">
							${tab.title}
						</span>
					</a>
				</c:forEach>		
			</div>
			<div style="width: 240px; float: right; margin-top: 100px">
						<c:if test="${SecurityContext.roleId == '5'}">
						<p style="color: #514721; font-size: 11px; border: 1px solid #FFD324; padding: 5px;  background: #FFF6BF; -moz-border-radius: 5px; margin-bottom: 30px;">Once you have posted a service order, your ServiceLive Wallet will subtract that amount from your Available Balance.  While that amount is still in your Wallet, it is not available to purchase additional services unless the order is cancelled.</p>
						</c:if>
						<p style="color: #514721; font-size: 11px; border: 1px solid #FFD324; padding: 5px;  background: #FFF6BF; -moz-border-radius: 5px; margin-bottom: 50px;">
						<br /><b style="font-size: 12px;">Integrated Payment Systems Inc.</b><br/><br/>
						We take consumer protection and online fraud issues seriously. As a consumer, you can be at risk for consumer fraud if you don't know the person or company you are sending money to. If you think you've been a victim of fraud, please call 1-866-678-5010.
						</p>
						
						<c:if test="${SecurityContext.slAdminInd}">
								<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" />
								<tags:security  actionName="adminFinanceWidgets">
												
								<c:if test="${SecurityContext.roleId != '2'}">	
									<jsp:include page="/jsp/so_monitor/menu/widgetTransferSLBucks.jsp" />
									
									<c:if test="${SecurityContext.roleId != '1' && SecurityContext.companyId != searsBuyerId}">
										<jsp:include page="/jsp/so_monitor/menu/widgetAdminIssueRefund.jsp" />
									</c:if> 		
								</c:if>
							</tags:security>				
						</c:if>
							<h3>Information</h3>
						<ul style="list-style-position: inside;">
							<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && providerLeadManagementPermission == 'true' && newLeadsTCIndicator != null && showLeadsSignUp == 1}">
								<li><a href="${leadgenfeeslink}">Lead Gen. Fees</a></li>
							</c:if> 
							<li><a href="http://community.servicelive.com/docs/sw.pdf">Managing ServiceLive Wallet</a></li>
							<li><a href="financeManagerController_displayBucksAgreement.action">Payment Terms Agreement</a></li>
							<li><a href="financeManagerController_displayLicenses.action">Concerns, Complaints, Licensing & Fraud Awareness</a></li>
						</ul>
			
				<c:if test="${SecurityContext.roleId != '5'}">
						<p>
							<img src="${staticContextPath}/images/finance_mgr/check_sample.gif"
								alt="Check Sample" title="Check Sample" />
						</p>

						<p>
							<img src="${staticContextPath}/images/finance_mgr/security_codes.gif"
								alt="Security Codes" title="Security Codes" />
						</p>
				</c:if>

						<h3>Status Legend</h3>
						<p><img src="${staticContextPath}/images/icons/incIcon.gif"> Needs Attention</p>
						<p><img src="${staticContextPath}/images/icons/completeIcon.gif"> Complete</p>
				</div>
<!-- END TAB PANE -->
