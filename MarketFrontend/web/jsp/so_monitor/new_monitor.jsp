<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils,org.owasp.esapi.ESAPI;"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="role" value="${roleType}" />
<c:set var="omTabViewPermission" scope="session" value="<%=session.getAttribute("omTabView")%>" />
<% String disTabVar=(String)request.getParameter("");
   	String dispTab1 = ESAPI.encoder().canonicalize(disTabVar);
   	String dispTab = ESAPI.encoder().encodeForHTML(dispTab1);
   	%>
<c:set var="displayTab" scope="page" value="<%=dispTab%>" />
<c:set var="defaultTab" scope="page" value="Saved" />
<% String tabVar=(String)request.getParameter("displayTab");
   String tabVuln1=ESAPI.encoder().canonicalize(tabVar);
   String tabVuln = ESAPI.encoder().encodeForHTML(tabVuln1);
%>
<c:set var="tab" scope="request" value="<%=tabVuln%>" />
<c:set var="RECEIVED" value="Received" />
<c:set var="pageTitle" scope="request" value="Service Order Monitor" />
<c:set var="noJs" value="true" scope="request"/> <%-- tell header not to insert any JS --%>
<c:set var="noCss" value="true" scope="request"/><%-- tell header not to insert any CSS --%>
<c:set var="currentMenu" scope="request" value="serviceOrderMonitor" /> <%--to be consumed by the header nav to highlight the SOM tab --%>
<c:set var="provider" value="false"  scope="request"/><%-- ss: needed for presentation logic brevity --%>
<c:set var="sladmin" value="false"  scope="request"/>
<c:if test="${roleType == 1}"><c:set var="provider" value="true" scope="request" /></c:if>
<c:if test="${SecurityContext.slAdminInd}"><c:set var="sladmin" value="true" scope="request" /></c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml">

	<head>	
	    <meta http-equiv="Content-Type" content="text/html;charset=utf-8;no-cache" />
		<title>ServiceLive: Service Order Monitor</title>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/CalendarControl.css">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_monitor.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/bulletinBoard/main.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_details.css"/>
	<link href="${staticContextPath}/javascript/confirm.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
	<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("newco.rthelper");
		    dojo.require("newco.jsutils");
			dojo.require("dijit.form.DateTextBox");		    
		</script>
	<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/serviceLiveDojoBase.js" djConfig="isDebug: false, parseOnLoad: true"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
	
		<style type="text/css">
	       .ie7 .bannerDiv{margin-left:-1020px;}  
		</style>
			
		
	

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/modalVideo.css" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
<script type="text/javascript" src="${staticContextPath}/javascript/CalendarControl.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/animatedcollapse.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
</head>
<style type="text/css">
.provPopup{
width: 460px;height: auto;
display: none;
}
</style>
<div id="cancellationDiv" name="cancellationDiv" style="display:none"></div>
	<body class="tundra acquity simple">
	  
		<div id="page_margins">
			<div id="page">
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				</div>

				<br />
				<form id="formHandler">
					<input type="hidden" name="adminCheck" id="adminCheck" value="${isPrimaryInd}"/>
					<input type="hidden" name="resourceID" id="resourceID" value="${SecurityContext.vendBuyerResId}" />
					<input type="hidden" id="soID" name="selectedSO" />
					<input type="hidden" id="groupInd" name="groupInd" value="false"/>
					<input type="hidden" id="groupId" name="groupId"/>
					<input type="hidden" id="resId" name="resId" />
					<input type="hidden" id="selectedRowIndex" name="selectedRowIndex" />
					<input type="hidden" id="currentSpendLimit" name="currentSpendLimit" />
					<input type="hidden" id="currentLimitLabor" name="currentLimitLabor"/>
					<input type="hidden" id="currentLimitParts" name="currentLimitParts"/>
					<input type="hidden" id="totalSpendLimit" name="totalSpendLimit" />
					<input type="hidden" id="totalSpendLimitParts"
						name="totalSpendLimitParts" />
					<input type="hidden" id="increasedSpendLimitComment"
						name="increasedSpendLimitComment" />
					<input type="hidden" name="increasedSpendLimitReasonWidget" id="increasedSpendLimitReasonWidget"
																		value="" />	
					<input type="hidden" name="increasedSpendLimitReasonIdWidget" id="increasedSpendLimitReasonIdWidget"
																		value="" />	
					<input type="hidden" name="increasedSpendLimitNotesWidget" id="increasedSpendLimitNotesWidget"
																		value=""/>
					<input type="hidden" id="reasonId" name="reasonId" />
					<input type="hidden" id="requestFrom" name="requestFrom"
						value="SOM" />
					<input type="hidden" id="cancelComment" name="cancelComment" />
					<input type="hidden" id="subject" name="subject" />
					<input type="hidden" id="message" name="message" />
					<input type="hidden" id="theRole" name="theRole" value="${role}" />
					<input type="hidden" id="tab" name="tab" value="${tab}" />
					<input type="hidden" id="pendingCancelInd" name="pendingCancelInd" value="none" />
					<input type="hidden" id="cancelInd" name="cancelInd" value="none"/>
					
				</form>
				
				<s:form id="detailViewer" action="serviceOrderDetailsView">
					<input type="hidden" id="soId" name="soId" />
					<input type="hidden" id="rid" name="rid" />
					<input type="hidden" id="goD" name="goD" />
				</s:form>
				
				<div align="right">
					<c:if test="${empty dateString}">					
						<c:set var="dateString"
							value="<%=com.newco.marketplace.utils.DateUtils
									.getHeaderDate()%>"></c:set>
					</c:if>
					<span id="date_dashboard">${dateString}</span>
					<c:choose><c:when test="${roleType == 5}">					
						<s:action namespace="wallet" name="serviceLiveWallet"
							executeResult="true" />
					</c:when>
					<c:otherwise>
						<p>
						<c:if test="${roleType==3 ||(roleType==1 && viewOrderPricing==true)}">
							Available Balance:
							<span id="available_balance">${AvailableBalance}</span>
							</c:if>
							<br />
							<c:if test="${roleType == 3}">
					Current Balance: <span id="current_balance">${CurrentBalance}</span>
							</c:if>
						</p>
					</c:otherwise></c:choose>
				</div>
				<div align="center">
					<c:if test="${oneDraft != null && oneDraft == true}">
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="som.simpleBuyer.oneDraftMessage" />
						<a href="soWizardController.action?soId=${soOrderList[0].id}&action=edit&tab=draft"> <fmt:message
								bundle="${serviceliveCopyBundle}"
								key="som.simpleBuyer.oneDraftMessageView" /> </a>
					</c:if>
				</div>
				<br style="clear:both;"/>
		
		<c:if test="${RECEIVED==displayTab || displayTab == 'Search'}">
			<s:if test="%{#request.soDetailsMessage == 'soMessage'}"> 
				<c:choose>
				<c:when test="${!empty somMessageOrderCancelled}">
				<div class="somErrorMsg">
				    <span>Order <c:out value="${somMessageOrderCancelled}" /> has been canceled by the buyer.</span>
				</div>	
				<c:remove var="somMessageOrderCancelled" scope="request" />	
				<c:set var="soDetailsMessage" scope="session" value="" />	
				</c:when>
				<c:when test="${!empty somMessageOrderAlreadyAccepted}">
				<div class="somErrorMsg">
				    <span>Order <c:out value="${somMessageOrderAlreadyAccepted}" /> has been accepted by another provider</span>
				</div>	
				<c:remove var="somMessageOrderAlreadyAccepted" scope="request" />
				<c:set var="soDetailsMessage" scope="session" value="" />		
				</c:when>
				<c:when test="${!empty somMessageGrpOrderAlreadyAccepted}">
				<div class="somErrorMsg">
				    <span>Group Order <c:out value="${somMessageGrpOrderAlreadyAccepted}" /> has been accepted by another provider</span>
				</div>	
				<c:remove var="somMessageGrpOrderAlreadyAccepted" scope="request" />
				<c:set var="soDetailsMessage" scope="session" value="" />		
				</c:when>
				<%-- <c:otherwise>
				<div class="somErrorMsg">
				    <span><fmt:message bundle="${serviceliveCopyBundle}" key="som.provider.message" /></span>
				</div>	
					<c:set var="soDetailsMessage" scope="session" value="" />	
				</c:otherwise> --%>
				</c:choose>
			</s:if> 
		</c:if>
				<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
					style="height: 1250px;" class="serviceOrderMon">					
					<c:forEach var="tab" items="${theSomtabList}">
						<c:choose>
							<c:when test="${tab.id == 'Search'}">
								<c:if
									test="${(roleType != 5 && tab.id == 'Search') || tab.id != 'Search'}">
									<div id="${tab.id}" dojoType="dijit.layout.ContentPane"
										title="${tab.title}"
										href="${contextPath}/monitor/gridHolder.action?tab=${tab.id}&msg=${msg}&pendingReschedule=${pendingReschedule}&dateNum=${dateNum}"
										preventCache="true" selected="${tab.tabSelected}" style="overflow: visible;">
									</div>
								</c:if>
							</c:when>
							<%-- Simple Buyers (5) should not be shown tabs of zero--%>
							<c:otherwise>
								<c:if
									test="${(roleType == 5 && tab.tabCount != '0') || roleType != 5}">
									<div id="${tab.id}" dojoType="dijit.layout.ContentPane"
										title="${tab.title} (${tab.tabCount})"
										href="${contextPath}/monitor/gridHolder.action?tab=${tab.id}&msg=${msg}&dateNum=${dateNum}"
										selected="${tab.tabSelected}">
									</div>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
		<div  class="jqmWindow modalDefineTe rms" id="videoModal" style="background-color:#000000;width:520px;">
		      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
		      <div class="modalContent" style="padding-left: 20px;">
		        <object width="480" height="295">
		        <param name="movieId" id="movieId" value="http://www.youtube.com/v/q3T_-gaV_j8&hl=en&fs=1&rel=0"></param><param id="all" name="allowFullScreen" value="true"></param><param id="ddd" name="allowscriptaccess" value="always"></param>
		        <embed src="http://www.youtube.com/v/q3T_-gaV_j8&hl=en&fs=1&rel=0" id="embedBid" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" 
		        width="480" height="295"></embed>
		        </object>
		      </div>
			</div>
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			</div>
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="simple.dashboard"/>
		</jsp:include>	
		<div id="ordermanagementpopup" class="provPopup modal" style="font-size: 12px; background-color: #FFFFF; width: 520px;">
				<a href="javascript: void(0)" id="closeButton"
				class="btnBevel simplemodal-close" style="color: white; float: right;">
				<img src="${staticContextPath}/images/widgets/tabClose.png" alt="X">
				</a>	
				<br/>
				<div style="margin-top: 10px; text-align: left; margin-left: 5px;">
					<b>${SERVICE_ORDER_CRITERIA_KEY.FName},&nbsp;we have something new for you!</b>	
					 
	<div style="margin-top: 10px; text-align: left;">Your wishes are granted. The new <b>Order Management (OM)</b> tool will replace the Service Order Monitor to manage service orders, better and faster!</div>

	<div style="margin-top: 10px; text-align: left;">The Service Order Monitor (SOM) will be deactivated sometime soon. Until then, you may continue to use Search functionality from SOM.</div>

	<div style="margin-top: 10px; text-align: left;">You can start using Order Management today to take advantage of all the new features.</div>

				</div>																		
				<br />
				<table style="margin-left: 0px;">
					<tr>
						<td width="55%" align="left"  style="padding-left: 10px; font-size: 13px;">
							<a id="newDocumentWindow" style="text-decoration: underline; cursor: pointer; color: #00A0D2" >What's New&nbsp;/&nbsp;View Training Documents</a>
						</td>
					</tr>
					<tr>
						<td>
						<br/>
						<span>
							<br/>
							<a href="/MarketFrontend/serviceOrderMonitor.action?displayTab=Search" style="margin-left: 10px; font-size: 13px;text-decoration: underline;">Continue to Service Order Monitor</a>
							<input id="omButton" class="button action" type="button" value="Go to Order Management" style="border:none; margin-left: 250px; width: 200px; text-transform: capitalize; font-size: 11px; margin-top: -20px;"/>
							</span>
							<br><br>
						</td>
					</tr>
				</table>						
			</div>
			<!-- SLT-2235:New notice for existing Firms to alert about the new T&C -->					
			<div id="newTermsNotice" class="newTermsNoticePopUp">
				<div id="headingNewTC" class="headingNewTCPopup">
					<span class="headerSpanScrollDivTCPopup"><b>Important Notice of a Change in our Terms and Conditions</b></span>
					</br>
				</div>
				<a href='' class='reviewLink' style="margin-left: 20px; font-size: 11px;text-decoration: underline;color: black"><p><b>Click to review the important changes to the ServiceLive Bucks Agreement</br>Clause 2.6-Liens,Garnishments,and Other Notifications</b></p></a>
				</br></br>
				Email Support@servicelive.com if you have questions related to changes.										
			</div>
		<div id="increaseSpendLimitModal" class="modal"></div> 	
			<!--  SLT-2235:ends -->
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript">
		jQuery.noConflict(); //reassign "$" to prototype
		</script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/plugins/tabs_jquery.js"  charset="utf-8"></script>
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/soMonitorAdvSearchDriver.js"></script>
		<script src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/creditCardValidation.js"></script>
		<script type="text/javascript">
            jQuery.noConflict();
            function showVideo(videoId){
            	var url = "http://www.youtube.com/v/"+videoId+"&hl=en&fs=1&rel=0";
	    		jQuery(document).ready(function($) {
	    	    	var vidObj = $('#videoModal');
			 		$(vidObj).find('#movieId').attr('value',url);
		     		$(vidObj).find('embed').attr('src',url);
		     		var htmlToRewite = $(vidObj).html();
		     		$(vidObj).html(htmlToRewite); //needed for IE
		     		$(vidObj).jqm({modal:true, toTop: true});
		     		$(vidObj).jqmShow();
				 });
		}
     	</script>
		<script type="text/javascript">
		
		jQuery(document).ready(function($){
		//jQuery("#auxNav").css("z-index","-200");
			// displayBanner();
			
			<c:if test="${initialSomLoadInd=='true' && omTabViewPermission == 'true'}">
			 setTimeout(function(){
				jQuery("#ordermanagementpopup").modal({
	                onOpen: modalOpen,
	                onClose: modalOnClose,
	                persist: true,
	                containerCss: ({ width: "530px", height: "auto", marginLeft: "-220px",marginTop: "200px"})
	            });
				jQuery.modal.defaults = {
						overlay: 50,
						overlayId: 'modalOverlay',
						overlayCss: {},
						containerId: 'modalContainer',
						containerCss: {},
						close: false,
						closeTitle: 'Close',
						closeClass: 'modalClose',
						persist: false,
						onOpen: null,
						onShow: null,
						onClose: null
						};
				function modalOpen(dialog) {
		            dialog.overlay.fadeIn('fast', function() {
		            	dialog.container.fadeIn('fast', function() {
		            		dialog.data.hide().slideDown('slow');
		            	});
		        	});
		 		}
		  
		   		function modalOnClose(dialog) {
		       		dialog.data.fadeOut('fast', function() {
		            	dialog.container.slideUp('fast', function() {
		            		dialog.overlay.fadeOut('fast', function() {
		            			jQuery.modal.close(); 
		            		});
		          		});
		       		});
		    	}
		    	
		    	jQuery('#omButton').click(function(){
		    		window.location.href="${contextPath}/orderManagementController.action?omDisplayTab=Inbox";
		    	});
		    	jQuery('#newDocumentWindow').click(function(){
					var href = $('#obj').attr("href");
					newwindow=window.open("http://training.servicelive.com/release6-0/",'_blank');
					if (window.focus) {
						newwindow.focus();
						}
				   	});	
				},4000);
 			</c:if>
			
 			//SLT-2235:New popup for existing firms to accept the new T&C of court notice
 		   	<c:if test="${provider && termsLegalNoticeChecked && not isSLAdmin && isPrimaryResource}">	
 		   		$("#header").css('z-index','');
 		   		$('.provPopup').hide();
 		    	var overlay = $('<div id="overlayNew"></div>');
 		    	overlay.show();
 		    	overlay.appendTo(document.body);
 		    	$('.newTermsNoticePopUp').show();
 		    	$('.headerSpanScrollDivTCPopup').show();
 		    	$('.reviewLink').click(function() {
 		    			$('.newTermsNoticePopUp').hide();
 		    			overlay.appendTo(document.body).remove();
 		    			window.location.href='/MarketFrontend/allTabView.action?tabView=tab5';
 		    			return false;
 		    	});	
 		   </c:if>
 	    	
		}); 
		
		
		function buyerReportProblem(tab)
		{
		document.getElementById('action'+tab).value="buyerReportProblem";
		submitPendingCancel(tab);
		}
		
		function replaceAll(txt){
			for(i=0; i<3;++i){
				txt = txt.replace(',', '');
			}
			return txt;
		}
		
		function providerReportProblem(tab)
		{
		var soId=document.getElementById('serviceOrderId'+tab).value;
		window.location.href="soDetailsController.action?soId="+soId+"&displayTab="+tab+"#ui-tabs-9";
		
		}
		
		function buyerWithdraw(tab)
		{		
		
			var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};		
			var buyerPrice=document.getElementById('buyerPrice'+tab).innerHTML;
			buyerPrice= replaceAll(buyerPrice);
			buyerPrice = parseFloat(buyerPrice, 10);
	        var buyerPvsAmount= document.getElementById('buyerPrvsAmount'+tab).value;
	        buyerPvsAmount = replaceAll(buyerPvsAmount);
	        buyerPvsAmount = parseFloat(buyerPvsAmount, 10);
	        var err=false;
	        var errMsg="";
	         /*Wallet and spend limit check, only when buyer's current amount is less than the previous amount*/
	        if(buyerPvsAmount > buyerPrice){
	        	var increasedAmount= buyerPvsAmount-buyerPrice;	
				if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO)
				{
					errMsg=errMsg+"The total maximum price exceeds the amount your profile allows.";
					document.getElementById('errorMessageW'+tab).innerHTML=errMsg;
					document.getElementById('errorMessageW'+tab).style.display='block';
					err=true;
				}
				var ach=${SecurityContext.autoACH};
				var balance=document.getElementById('balance'+tab).value;
				var soLevelACH=document.getElementById('soLevelAutoACH'+tab).value;
				balance = replaceAll(balance);
				if(soLevelACH=='false' && balance!='' && increasedAmount>balance)
				{	
					if(err)
					{
						errMsg=errMsg+"\n Your wallet does not have enough funding to cover this new combined maximum.";
						document.getElementById('errorMessageW'+tab).innerHTML=errMsg;
						document.getElementById('errorMessageW'+tab).style.display='block';
						err=true;
					}
					else
					{
					errMsg=errMsg+"Your wallet does not have enough funding to cover this new combined maximum.";
						document.getElementById('errorMessageW'+tab).innerHTML=errMsg;
						document.getElementById('errorMessageW'+tab).style.display='block';
						err=true;
					
					}
				
				}
	        }
			if(!err)
			{
				document.getElementById('action'+tab).value="buyerWithdraw";
				submitPendingCancel(tab);
			}
		
		}
		
		function buyerAgree(tab)
		{
		var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};		
		var buyerPrice=document.getElementById('buyerPrice'+tab).innerHTML;
		buyerPrice= replaceAll(buyerPrice);
        var buyerPricelastRequest=document.getElementById('buyerPricelastRequest'+tab).innerHTML;
        buyerPricelastRequest = replaceAll(buyerPricelastRequest);
		var increasedAmount=buyerPrice-buyerPricelastRequest;
		var err=false;	
		var errMsg="";	
		if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO)
		{		
			errMsg=errMsg+"The total maximum price exceeds the amount your profile allows.";
			document.getElementById('errorMsge'+tab).innerHTML=errMsg;
			document.getElementById('errorMsge'+tab).style.display='block';
			err=true;
		}
		var ach=${SecurityContext.autoACH};
		var balance=document.getElementById('balance'+tab).value;
		var soLevelACH=document.getElementById('soLevelAutoACH'+tab).value;
		balance = replaceAll(balance);
		if(soLevelACH=='false' && balance!='' && increasedAmount>balance)
		{
			if(err)
			{
			errMsg=errMsg+"\n Your wallet does not have enough funding to cover this new combined maximum.";
				document.getElementById('errorMsge'+tab).innerHTML=errMsg;
				document.getElementById('errorMsge'+tab).style.display='block';
				err=true;
			}
			else
			{
			errMsg=errMsg+"Your wallet does not have enough funding to cover this new combined maximum.";
				document.getElementById('errorMsge'+tab).innerHTML=errMsg;
				document.getElementById('errorMsge'+tab).style.display='block';
				err=true;
			
			}
		
		}
		if(!err)
		{
		document.getElementById('action'+tab).value="buyerAgree";
		document.getElementById('cancelInd').value= "agreedToCancel";
		
		submitPendingCancel(tab);
		}
		
		}
		function showGlossaryItem(tab){	
			jQuery("#explainer"+tab).css("position","absolute");
			jQuery("#explainer"+tab).show();
		}
		
		function hideGlossaryItem(tab){
			 jQuery("#explainer"+tab).hide();
		}
		
		
		
		function buyerDisagree(tab)
		{
		document.getElementById('action'+tab).value="buyerDisagree";
		var validation=calculateMaxSpendLimit(tab);
		
		if(validation)
		{
		submitPendingCancel(tab);
		}
		}
		function providerWithdraw(tab)
		{
		
		document.getElementById('action'+tab).value="providerWithdraw";
		submitPendingCancel(tab);
		}
		function providerAgree(tab)
		{
		document.getElementById('action'+tab).value="providerAgree";
		document.getElementById('cancelInd').value= "agreedToCancel";
		
		submitPendingCancel(tab);
		}
		function providerDisagree(tab)
		{
		document.getElementById('action'+tab).value="providerDisagree";
		var validation=calculateAmount(tab);
		
		if(validation)
		{
		submitPendingCancel(tab);
		}
		}
		function setCancellationAmount(id)
		{
		var value=$(id).val();
		$(".cancellationAmount").val(value);
		}
		function setCancelAmt(id)
		{
		var value=$(id).val();
		$(".cancelAmt").val(value);
		}
		function setCancelComment(id)
		{
		var value=$(id).val();
		$(".cancelComment").val(value);
		}
		
		
		function calculateMaxSpendLimit(tab)
		{
		
		//var errorMessage='#errorMessage'+tab;
		//alert(errorMessage);
		//$(errorMessage).html("");
		document.getElementById('errorMessage'+tab).innerHTML="";
		var wholeNumberValue=document.getElementById('cancellationAmount'+tab).value;
		var decimalValue=document.getElementById('cancelAmt'+tab).value;
		wholeNumberValue =replaceAll(wholeNumberValue);		
		var error=false; 
	    var comment=document.getElementById('cancelComment'+tab).value;
		comment=jQuery.trim(comment);
		comment = escape(comment);
		var length=comment.length;
		var errMsg="";
		
		if(comment!='')
		{
		if(length>600)
		{
		//$("#errorMessage"+tab).append("Field validation error occurred.");
		//$("#errorMessage"+tab).show();
		errMsg=errMsg+"Comment length should not be greater than 600 characters.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		
		error=true;
		}		
		}
		else
		{
		errMsg=errMsg+"Please fill out all required fields.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		error=true;
		}		
	
		
		if(wholeNumberValue!='' )
		{
			if(decimalValue ==''){
				 decimalValue = 00;
			}
			var decimallength=decimalValue.length;
			if(decimallength==1)
			{
				decimalValue=decimalValue * 10;
			}
			if(wholeNumberValue<1 && decimalValue<1)
			{
			
				if(error)
				{
				errMsg=errMsg+"\n Please enter a price greater than $0.00.";
				document.getElementById('errorMessage'+tab).innerHTML=errMsg;
				document.getElementById('errorMessage'+tab).style.display='block';
				error=true;
				}
				else
				{
				errMsg=errMsg+"Please enter a price greater than $0.00.";
				document.getElementById('errorMessage'+tab).innerHTML=errMsg;
				document.getElementById('errorMessage'+tab).style.display='block';
				error=true;
				}
			}
		var cancelAmount= (1 * wholeNumberValue)+ (0.01 * decimalValue)
		document.getElementById('cancelAmount'+tab).value=cancelAmount;
		var buyerPricelastRequest=document.getElementById('buyerPricelastRequest'+tab).innerHTML;
		buyerPricelastRequest = replaceAll(buyerPricelastRequest);
		var increasedAmount=cancelAmount-buyerPricelastRequest;
		var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};		
		
		if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO)
		{
			if(error)
			{
			errMsg=errMsg+"\n The total maximum price exceeds the amount your profile allows.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}
			else
			{
			errMsg=errMsg+"The total maximum price exceeds the amount your profile allows.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}	
		}
		var ach=${SecurityContext.autoACH};
		var soLevelACH=document.getElementById('soLevelAutoACH'+tab).value;
		var balance=document.getElementById('balance'+tab).value;
		balance = replaceAll(balance);
		if(soLevelACH=='false' && balance!='' && increasedAmount>balance)
		{
		if(error)
			{
			errMsg=errMsg+"\n  Your wallet does not have enough funding to cover this new combined maximum.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}
			else
			{
			errMsg=errMsg+" Your wallet does not have enough funding to cover this new combined maximum.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}	
		}
		}
		else
		{
		if(error)
		{
		errMsg=errMsg+"\nInvalid Input amount. Please use the format $0.00.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		
		}
		else
		{
		errMsg=errMsg+"Invalid Input amount. Please use the format $0.00.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		}
		return false;
		}
		if(error)
		{
		return false;
		}
		
		return true;
		}
	
		
		
		function calculateAmount(tab)
		{
		
		//var errorMessage='#errorMessage'+tab;
		//alert(errorMessage);
		//$(errorMessage).html("");
		document.getElementById('errorMessage'+tab).innerHTML="";
		var wholeNumberValue=document.getElementById('cancellationAmount'+tab).value;
		var decimalValue=document.getElementById('cancelAmt'+tab).value;
		var error=false; 
	    var comment=document.getElementById('cancelComment'+tab).value;
		comment=jQuery.trim(comment);
		comment = escape(comment);
		var length=comment.length;
		var errMsg="";
		
		if(comment!='')
		{
		if(length>600)
		{
		//$("#errorMessage"+tab).append("Field validation error occurred.");
		//$("#errorMessage"+tab).show();
		errMsg=errMsg+"Comment length should not be greater than 600 characters.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		
		error=true;
		}		
		}
		else
		{
			errMsg=errMsg+"Please fill out all required fields.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
		}		
	
		
		if(wholeNumberValue!='' )
		{
			if(decimalValue ==''){
				 decimalValue = 00;
			}
			var decimallength=decimalValue.length;
			if(decimallength==1)
			{
				decimalValue=decimalValue * 10;
			}
			if(wholeNumberValue<1 && decimalValue<1)
			{
				if(error)
				{
					errMsg=errMsg+"\n Please enter a price greater than $0.00.";
					document.getElementById('errorMessage'+tab).innerHTML=errMsg;
					document.getElementById('errorMessage'+tab).style.display='block';
					error=true;
				}
				else
				{
					errMsg=errMsg+"Please enter a price greater than $0.00.";
					document.getElementById('errorMessage'+tab).innerHTML=errMsg;
					document.getElementById('errorMessage'+tab).style.display='block';
					error=true;
				}
			}
			var cancelAmount= (1 * wholeNumberValue)+ (0.01 * decimalValue)
			document.getElementById('cancelAmount'+tab).value=cancelAmount;
		
		}
		else
		{
			if(error)
			{
			errMsg=errMsg+"\nInvalid Input amount. Please use the format $0.00.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			
			}
			else
			{
			errMsg=errMsg+"Invalid Input amount. Please use the format $0.00.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			}
			return false;
		}
		if(error)
		{
		return false;
		}
		
		return true;
		}
		
		function showHistory(e, tab)
		{
		
		var soId=document.getElementById('serviceOrderId'+tab).value;
		jQuery("#"+"history"+tab).load("serviceOrderPendingCancelHistory_display.action?servicOrderId="+soId);
		
	/*	var evt = e ? e:window.event; 
		var x = 0;
		var y = 0;
		
		x=evt.screenX;
		var width = screen.width;
		if(x >= (width - 240)){
		   x= width - 250;
		}	
		if (evt.pageY) y= evt.clientY;
		else if (evt.clientY)
   		y  = evt.clientY + (document.documentElement.scrolTop ? document.documentElement.scrollTop : document.body.scrollTop);
   		
      	jQuery("#"+"history"+tab).css("top",y-5);
      	jQuery("#"+"history"+tab).css("left",x-5); */
		jQuery("#"+"history"+tab).show();		
		
		} 
		function hideHistory(tab)
		{
		jQuery("#"+"history"+tab).hide();
		}
		
		function showHistoryDiv(tab)
		{		
		jQuery("#"+"history"+tab).show();
		
		}
		
		function showWidget(tab)
		{
		jQuery("#"+"pendingCancelExpand"+tab).hide();
		jQuery("#"+"pendingCancelCollapse"+tab).show();
		jQuery("#"+"frmPendingCancel"+tab).show();
		
		}
		function hideWidget(tab)
		{
		jQuery("#"+"pendingCancelExpand"+tab).show();
		jQuery("#"+"pendingCancelCollapse"+tab).hide();
		jQuery("#"+"frmPendingCancel"+tab).hide();
		
		}
		
		function showProviderRequestDiv(tab)
		{
		document.getElementById('newRequest'+tab).style.display = 'block';
		document.getElementById('reportAproblem'+tab).style.display = 'none';
		document.getElementById('disagreeSubmit'+tab).style.display = 'block';
		document.getElementById('agreeSubmit'+tab).style.display = 'none';
		jQuery("#"+'cancellationAmount'+tab).val('');
		jQuery("#"+'cancelAmt'+tab).val('');
		jQuery("#"+'cancelComment'+tab).val('');
		document.getElementById('cancelComntCtr').innerHTML = '600';
		}
		
		
		
		function hideProviderRequestDiv(tab)
		{
		document.getElementById('newRequest'+tab).style.display = 'none';
		document.getElementById('reportAproblem'+tab).style.display = 'block';
		document.getElementById('agreeSubmit'+tab).style.display = 'none';
		document.getElementById('disagreeSubmit'+tab).style.display = 'none';
		jQuery("#"+"errorMessage"+tab).hide();
		jQuery("#"+"errorMsge"+tab).hide();	
			
		}
		
		function showRequestDiv(tab)
		{
		
		document.getElementById('newRequest'+tab).style.display = 'block';
		document.getElementById('submit'+tab).style.display = 'none';
		document.getElementById('disagreeSubmit'+tab).style.display = 'block';
		document.getElementById('comment'+tab).style.display = 'none';
		document.getElementById('cancellationAmount'+tab).value = "";
		document.getElementById('cancelAmt'+tab).value = "";
		document.getElementById('cancelComment'+tab).value = "";
		document.getElementById('cancelComntCtr').innerHTML = '600';
				jQuery("#"+"errorMessage"+tab).hide();	
						jQuery("#"+"errorMsge"+tab).hide();	
					
		
		}
		
		
		
		function hideRequestDiv(tab)
		{	
			
		document.getElementById('newRequest'+tab).style.display = 'none';
		document.getElementById('submit'+tab).style.display = 'block';
		document.getElementById('disagreeSubmit'+tab).style.display = 'none';
		jQuery("#"+"errorMessage"+tab).hide();
		jQuery("#"+"errorMsge"+tab).hide();	
				
		}
		
		function validateAmount(tab){
			var amtInt=	jQuery("#"+"cancellationAmount"+tab).val();
			var amtDec = jQuery("#"+"cancelAmt"+tab).val();
			if(amtInt != amtInt.replace(/[^0-9]/g, '')){
				jQuery("#"+"cancellationAmount"+tab).val("");
			}
			if(amtDec != amtDec.replace(/[^0-9]/g, '')){
				jQuery("#"+"cancelAmt"+tab).val("");
			}
		}
		
		function submitPendingCancel(tab)
		{
			var formname='frmPendingCancel'+tab;                
			fnSubmit('serviceOrderPendingCancel_somPendingCancelSO.action',pendingCancelCB,null,formname);
      	}
		function setCursorWait(isWait) {
	document.body.style.cursor = isWait ? "wait" : "default";
	    }
		function showDisagreeDiv(tab)
		{
		document.getElementById('providerDisagree'+tab).style.display = 'block';
		// document.getElementById('reportAproblem'+tab).style.display = 'block';
		document.getElementById('agreeSubmit'+tab).style.display = 'none';
		document.getElementById('disagreeSubmit'+tab).style.display = 'none';
		document.getElementById('comment'+tab).style.display = 'none';
		document.getElementById("disagreeComplete"+tab).checked="";
		document.getElementById("disagreeRequest"+tab).checked="";
		
        
		}
		
		
		function hideDisagreeDiv(tab)
		{
		document.getElementById('providerDisagree'+tab).style.display = 'none';
		document.getElementById('newRequest'+tab).style.display = 'none';
		document.getElementById('agreeSubmit'+tab).style.display = 'block';
		document.getElementById('disagreeSubmit'+tab).style.display = 'none';
		document.getElementById('reportAproblem'+tab).style.display = 'none';
		document.getElementById('comment'+tab).style.display = 'block';
		jQuery("#"+"errorMessage"+tab).hide();
		jQuery("#"+"errorMsge"+tab).hide();	
		document.getElementById("disagreeComplete"+tab).checked="";
		document.getElementById("disagreeRequest"+tab).checked="";				
				
		}
		
		function textCounter( field, countfield, maxlimit ) {
	 		if ( field.value.length > maxlimit ) {
	  			field.value = field.value.substring( 0, maxlimit );
	  			document.getElementById(countfield).innerHTML = maxlimit - field.value.length;
	  			field.blur();
	  			field.focus();
	  			return false;
	 		} else {	 		
	 			 document.getElementById(countfield).innerHTML = maxlimit - field.value.length;
	 		}
		}
		
		function isNumberKey(evt)
    	{
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
           return false;
        return true;
     	}
		
		
		
    function modalOpenAddCustomer(dialog) {
            dialog.overlay.fadeIn('fast', function() {
            dialog.container.fadeIn('fast', function() {
            dialog.data.hide().slideDown('slow');
            });
        });

 }
  
     function modalOnClose(dialog) {
       dialog.data.fadeOut('slow', function() {
           dialog.container.slideUp('slow', function() {
               dialog.overlay.fadeOut('slow', function() {
                   jQuery.modal.close(); 
               });
           });
       });
    }  
     function saveCriteria(){
    		
    		var iFrameRef = document.getElementById("SearchmyIframe");
    		var searchForm = null;
    		var filterName = document.getElementById("filterName");
    		var selectedCriteria = document.getElementById("searchSelectionsList");
    		var selectedHTML = selectedCriteria.innerHTML;
    				
    		if(!dojo.isIE){
    			searchForm = iFrameRef.contentDocument.getElementById("searchHandler");
    		}else{
    			searchForm = iFrameRef.contentWindow.document.getElementById("searchHandler");		
    		}	
    		if( jQuery(selectedHTML).find('a').text().indexOf("[x]") != -1){
    				if(filterName && filterName.value != ''){
    				    searchCriteriaTreeNodesParser(searchForm);
    			 		
    					var filterNames = document.getElementById("filterName");
    					var filterNameData = filterNames.value;
    					filterNameData = jQuery.trim(filterNameData);
    					filterNameData=filterNameData.toLowerCase();
    					var filterExists =0;
    					var savedFilterName ='';
    					 <c:forEach var="searchFilters" items="${userSearchFilters}">  
     							savedFilterName = '${searchFilters.filterName}';
     							savedFilterName = jQuery.trim(savedFilterName);
     							savedFilterName=savedFilterName.toLowerCase();

     						if(savedFilterName == filterNameData){
    							filterExists=filterExists+1;
     						}
    					</c:forEach> 

    					if(filterExists != 0){
    						document.getElementById('saveFilterWarning').style.display="block";
    					}
    					else{
    					searchForm.searchFilterName.value = filterNameData;
    					searchForm.action="soSearch_saveSearchFilters.action";
    					searchForm.submit();
    					}
    				}else{
    					alert("Please select a Filter Name");
    				}		
    		}else{
    			alert("Please add atleast one search criteria");
    		}
    	}
                     
</script>
	<script language="JavaScript" type="text/javascript">
	jQuery.noConflict(); //reassign "$" to prototype
	newco.jsutils.setGlobalStaticContext('${staticContextPath}');
	newco.jsutils.setGlobalContext('${contextPath}'); 
	
	
	//ss: check XHR status and stop periodic refresh if session expires
	var stopRefresh = function(){
		if (_commonSOMgr._commonAjaxCallback != null) {
			try{
				if(_commonSOMgr._commonAjaxCallback.ioArgs.xhr.status != 200){
					_commonSOMgr.forceShutDown();
					return true;
				}
			}
			catch(e){}
		}
	}
	
	var cb = function callBack(data) {
		newco.jsutils._getResultsAsXML(data,_commonSOMgr.widgetId+"myIframe", document.getElementById("theRole").value );	
	}
	var timeoutInterval = 30000;
	var _commonSOMgr = newco.rthelper.initNewSOMRTManager('${contextPath}/monitor/refreshTabs.action', timeoutInterval, cb);
	// registers the default tab
	if ('${tab}' != '') {
		_commonSOMgr.setSelectedWidget('${tab}', false);
	}
	else {
		_commonSOMgr.setSelectedWidget('${defaultTab}', false);
	}
	
	//ss: run stopRefresh; kill periodic execution if session expires
	var refreshIntervalId = setInterval('if(stopRefresh()){clearInterval(refreshIntervalId);}', timeoutInterval);
	
	var cancelSOCB = function(data){
	      newco.jsutils.updateWithXmlData('cancelServiceOrderResponseMessage'+_commonSOMgr.widgetId,'message', data);
	      var passFailRst = newco.jsutils.handleCBData(data);
		  if(passFailRst != null ){
		  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
		  	 								  passFailRst.pass_fail_status,
		  	 								  passFailRst.resultMessage );
		  }
		  newco.jsutils.clearCancelData();
                setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",4000);
	}

	function cancelServiceOrder(){
              newco.jsutils.doAjaxSubmit('serviceOrderCancel.action',
              							cancelSOCB,null,
              							'formHandler');   
          }
          
          function hiddenCancelComment(){
              $('cancelComment').value = $('cancelComment'+_commonSOMgr.widgetId).value;
          }
          
          function fnSubmitVoidSO(){
	            
	            var tab = _commonSOMgr.widgetId;
	            var soId = document.getElementById('soid'+tab).innerHTML;
	  			var staticContextPath='${staticContextPath}';
	  	        jQuery("#cancellationDiv").html("<img src=\"" +  staticContextPath+"/images/loading.gif\" width=\"200px\"/>");
	  			jQuery('#cancellationDiv').load("serviceOrderMonitorAction_loadDataForCancellation.action?soId="+soId,function() {
	  			jQuery("#cancelHeading").html("Void Service Order");
	          	jQuery("#cancelButtonDiv> input:button").val("Void Service Order");
				jQuery("#action").val("void");
	  	 	 		jQuery("#cancellationDiv").modal({
	  		            onOpen: modalOpenAddCustomer,
	  		            onClose: modalOnClose,
	  		            persist: true,
	  		            close: false,
	  		            containerCss: ({ width: "650px", height: "450px", marginLeft: "40px" })
	  	            });
	              	window.scrollTo(1,1);
	  			});	    	
          }
                  
                 var voidCB = function voidSOCB(data){
			          jQuery.modal.impl.close(true);
				      newco.jsutils.updateWithXmlData('voidServiceOrderResponseMessage'+_commonSOMgr.widgetId,'message', data);
				      var passFailRst = newco.jsutils.handleCBData(data);
					  if(passFailRst != null ){
					  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
					  	 								  passFailRst.pass_fail_status,
					  	 								  passFailRst.resultMessage );
				    	
				    	jQuery(document).ready(function($) {
							$.post("dashboardPeriodicRefresh.action", function(data) {
								var availBalance = data.getElementsByTagName('available_balance')[0].childNodes.item(0).data;
								var currBalance = data.getElementsByTagName('current_balance')[0].childNodes.item(0).data;
								$('#available_balance').html(availBalance);
								$('#current_balance').html(currBalance);
							}, 'xml');
							
						});
					  }
					  newco.jsutils.clearCancelData();
			          var iframeId=newco.jsutils.getSelectedIfm();
					  $('#'+iframeId).attr('src', $('#'+iframeId).attr('src'));
          	}
          
          
           var pendingCancelCB = function pendingCancelCB(data){
	      newco.jsutils.updateWithXmlData('voidServiceOrderResponseMessage'+_commonSOMgr.widgetId,'message', data);
	      var passFailRst = newco.jsutils.handleCBData(data);
		  if(passFailRst != null ){
		  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
		  	 								  passFailRst.pass_fail_status,
		  	 								  passFailRst.resultMessage );
	    	
	    	jQuery(document).ready(function($) {
				$.post("dashboardPeriodicRefresh.action", function(data) {
					var availBalance = data.getElementsByTagName('available_balance')[0].childNodes.item(0).data;
					var currBalance = data.getElementsByTagName('current_balance')[0].childNodes.item(0).data;
					$('#available_balance').html(availBalance);
					$('#current_balance').html(currBalance);
				}, 'xml');
				
			});
		  }
			  newco.jsutils.hideDiv('rightMenu_');
		 	  newco.jsutils.hideDivInParent('rightMenu_');
			  newco.jsutils.clearCancelData();
			  setTimeout("refreshIframe()",4000);
	          jQuery('#pendingCancelInd').val('success');
	          
          }
          function refreshIframe(){
         	var iframeId = newco.jsutils.getSelectedIfm();
         	var tabId = jQuery("#tab").val();
         	var iFrameWindow = document.getElementById(iframeId).contentWindow;
            var refreshForm = iFrameWindow.document.getElementById("pagingForm");
            if(iframeId=="SearchmyIframe"){
         		refreshForm.action="searchGridLoader.action";
         	}
         	jQuery('#pendingCancelInd').val('success');
            refreshForm.submit();
         }
          
         function fnSubmitSOMDeleteDraft(){
	            
	            var tab = _commonSOMgr.widgetId;
	            var soId = document.getElementById('soid'+tab).innerHTML;
	  			var staticContextPath='${staticContextPath}';
	  	        jQuery("#cancellationDiv").html("<img src=\"" +  staticContextPath+"/images/loading.gif\" width=\"200px\"/>");
	  			jQuery('#cancellationDiv').load("serviceOrderMonitorAction_loadDataForCancellation.action?soId="+soId,function() {
	  			jQuery("#cancelHeading").html("Delete Service Order");
	  			jQuery("#cancelButtonDiv> input:button").val("Delete Service Order");
	  			jQuery("#action").val("delete");
	  	 	 		jQuery("#cancellationDiv").modal({
	  		            onOpen: modalOpenAddCustomer,
	  		            onClose: modalOnClose,
	  		            persist: true,
	  		            close: false,
	  		            containerCss: ({ width: "650px", height: "450px", marginLeft: "40px" })
	  	            });
	              	window.scrollTo(1,1);
	  			});	    	
          }
          var deleteDraftCB = function deleteDraftCB(data){
		      jQuery.modal.close();
		      newco.jsutils.updateWithXmlData('deleteDraftResponseMessage'+_commonSOMgr.widgetId,'message', data);
		      var passFailRst = newco.jsutils.handleCBData(data);
		      if(passFailRst != null ){
			  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
			  	 								  passFailRst.pass_fail_status,
			  	 								  passFailRst.resultMessage );
			  }
			  newco.jsutils.clearCancelData();
			  var iframeId=newco.jsutils.getSelectedIfm();
			  $('#'+iframeId).attr('src', $('#'+iframeId).attr('src'));
			  
          }
          
          function fnSubmitWithdrawCondOffer(){
          	fnSubmit('serviceOrderWithdrawCondOffer.action',condOfferCB,null,'formHandler');
          }
          
          var condOfferCB = function condOfferCB(data){
	      newco.jsutils.updateWithXmlData('condOfferResponseMessage'+_commonSOMgr.widgetId,'message', data);
	      var passFailRst = newco.jsutils.handleCBData(data);
		  if(passFailRst != null ){
		  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
		  	 								  passFailRst.pass_fail_status,
		  	 								  passFailRst.resultMessage );
		  }
		  newco.jsutils.clearCancelData();
                setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",4000);
          }
          function checkAllInRejectWidget(){
         
         
         var rejResources = document.getElementsByName("resource_reject");
			
			for(var i = 0; i < rejResources.length; i++){
			
			rejResources[i].checked = true;
			
			}
          	            
          }
          
	 	function clearAllInRejectWidget(){
         
         
         var rejResources = document.getElementsByName("resource_reject");
			
			for(var i = 0; i < rejResources.length; i++){
			
			rejResources[i].checked = false;
			
			}
          	   
          
          }
	 	
	 	
          var clearResources="";
          function fnSubmitRejectSO(actionURL,tab) {
          jQuery("#rejectServiceOrderResponseMessage"+_commonSOMgr.widgetId).html("");
          clearResources="";
          	var adminCheck = document.getElementById('adminCheck').value;
         
         if(document.getElementById("priceModel"))
         {
      		 var priceModel=document.getElementById("priceModel").value;
         }
                  
          	if(adminCheck=='true' && (tab=='Received'||priceModel=='NAME_PRICE')){  
				var rejResources = document.getElementsByName("resource_reject");
				var checkedResources="";
				for(var i = 0; i < rejResources.length; i++){
			
				if(rejResources[i].checked) {
					var val1= rejResources[i].id;			
					var resource_id=val1.substring(16,val1.length);			
					checkedResources=checkedResources+","+resource_id;				
				}
				}
		
			}else{
				checkedResources= document.getElementById('resourceID').value;
			}
			
			if(checkedResources=='' && adminCheck == 'true' && (tab=='Received'||priceModel=='NAME_PRICE')){
			document.getElementById('rejectServiceOrderResponseMessage'+tab).innerHTML= "Please select 1 or more providers first";
			return false;
			}
			var submitForm = document.getElementById('formHandler');
			submitForm.resId.value = checkedResources;	
			
			if(document.getElementById("groupID"))
			{	
			submitForm.groupId.value =document.getElementById("groupID").value;	
			}	
						
			if (document.getElementById("rejectReasonId"+tab).value == "" || document.getElementById("rejectReasonId"+tab).value == "null" || document.getElementById("rejectReasonId"+tab).value == "0") {
            	document.getElementById('rejectServiceOrderResponseMessage'+tab).innerHTML = "Please select reason to reject.";
          	} else {
          		document.getElementById("reasonId").value=document.getElementById("rejectReasonId"+tab).value;
          		clearResources=checkedResources;
            	newco.jsutils.doAjaxSubmit(actionURL, rejectCallBackFunction, null, 'formHandler');
				
	      			}
				 	}
		  var rejectCallBackFunction = function(data) {
		  
			var rCb = newco.jsutils.handleCBData(data);
			
			if(rCb != null ) {
			  	 newco.jsutils.doIfrModalMessage(newco.jsutils.getSelectedIfm(), rCb.pass_fail_status, rCb.resultMessage);
			}
		    newco.jsutils.updateWithXmlData('rejectServiceOrderResponseMessage'+_commonSOMgr.widgetId, 'message', data);
		   
		    var adminCheck = document.getElementById('adminCheck').value;
		    var htmlVal = jQuery("#rejectServiceOrderResponseMessage"+_commonSOMgr.widgetId).html();		    
		    if(htmlVal=='Request Successfully executed.'){
		    if(adminCheck=='true'){		    
		     
	            	var resourceList=clearResources.split(",");	            	
				 	for(var i = 0; i < resourceList.length; i++){ 				 	
				 	if(resourceList[i]!=""){				
				 		var node= "row_"+resourceList[i];
						jQuery("#row_"+resourceList[i]).remove();
				
	      			}
				 	}
			 	}
			}
		    setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",4000);
          }
          
	    function countWidgetTextAreaChars(limit, evnt, tab)
		{
			var counter = document.getElementById("message_leftChars");	
			var areaName = document.getElementById("message" +tab);
			if (areaName.value.length>limit) {
				areaName.value=areaName.value.substring(0,limit);
				alert("The field limit is " + limit + " characters.");
				
				//Stop all further events generated (Event Bubble) for the characters user has already typed in .
				//For IE
				if (!evnt) var evnt = window.event;
				evnt.cancelBubble = true;
				//For FireFox
				if (evnt.stopPropagation) evnt.stopPropagation();
			}
			else
				counter.value = limit - areaName.value.length;
		}
          
         function fnSubmitAddNote(url,cbFnc,formId,tab){
		    var success = false;
		    var subject = document.getElementById("subject" +tab).value;
		    var message = document.getElementById("message" +tab).value;
		    var ccSubjectFlag=validateCreditCardNumber(subject);
		    var ccMessageFlag=validateCreditCardNumber(message);	   		
	   		var radioValue = (document.getElementsByName("radioId"))
	   		var selectedRadioValue = getCheckedValue(radioValue);
	   		document.getElementById("radioSelection").value = selectedRadioValue;
	        document.getElementById(tab + "subjectLabelMsg").style.display="none";
			document.getElementById("subjectLabelMsg" + tab).style.display="none";
			document.getElementById(tab+"subjectCCValidateLabelMsg").style.display="none";
			document.getElementById("messageCCValidateLabelMsg" + tab).style.display="none";			
	     	if(subject == null || subject == "" || subject == "[subject]" || subject == "[Subject]")
			{
      			document.getElementById(tab+"subjectLabelMsg").style.display="";
      			document.getElementById(tab+"subjectLabelMsg").style.color="red";
 			}
 			else if(ccSubjectFlag){
				document.getElementById(tab+"subjectCCValidateLabelMsg").style.display="";
      			document.getElementById(tab+"subjectCCValidateLabelMsg").style.color="red";
			}
	      	else if (message == null || message == "" || message == "[Message]" || message == "[message]")
		    {
			    document.getElementById("subjectLabelMsg" + tab).style.display="";
			    document.getElementById("subjectLabelMsg"+tab).style.color="red";
		   	}
		   	else if(ccMessageFlag){
				document.getElementById("messageCCValidateLabelMsg" + tab).style.display="";
			    document.getElementById("messageCCValidateLabelMsg"+tab).style.color="red";
			}      
			else
			{   
			    success = true;
			    url = url+"&radioSelection="+selectedRadioValue;
	            fnSubmit(url,cbFnc,null,formId);
		    }
		    return success;
		}
                
         function getCheckedValue(radioObj) {
			if(!radioObj)
				return "";
			var radioLength = radioObj.length; 
			if(radioLength == undefined)
				if(radioObj.checked)
					return radioObj.value;
				else
					return "";
			for(var i = 0; i < radioLength; i++) {
				if(radioObj[i].checked) {
					return radioObj[i].value;
				}
			}				
		}      
                
	     function cancel(tab)
		 {
		 	document.getElementById('addNoteWidgetResponseMessage'+tab).style.display = 'block';
			document.getElementById('addNoteWidgetResponseMessage'+tab).style.visibility = 'hidden';
			document.getElementById('addNoteWidgetResponseMessage'+tab).innerHTML = "";
		   	document.getElementById("subject" + tab).value = "";
            document.getElementById("message" + tab).value = "";
            document.getElementById(tab + "subjectLabelMsg").style.display="none";
            document.getElementById("subjectLabelMsg" + tab).style.display="none";
	     }
	     
	var addNoteWidgetCallBackFunction = function(data){
		//if(dojo.isIE)
		//{
		//	alert("doing ie test addNoteWidgetCallBackFunction");
		//}
		  var passFailRst = newco.jsutils.handleCBData(data);
		  if(passFailRst != null ){
		  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm(),
		  	 								  passFailRst.pass_fail_status,
		  	 								  passFailRst.resultMessage );
		  }
	      newco.jsutils.updateWithXmlData('addNoteWidgetResponseMessage'+_commonSOMgr.widgetId,'message',data); 
	      
	      newco.jsutils.clearNoteData();
	}
	
	function captureNote() 
	{
		 $('subject').value = $('subject'+_commonSOMgr.widgetId).value;
		 $('message').value = $('message'+_commonSOMgr.widgetId).value;
	}
	
	function resetAddNoteSubject() 
	{
		if($('subject'+_commonSOMgr.widgetId).value == '[Subject]' ){
		 	$('subject'+_commonSOMgr.widgetId).value = '';
		}
	}
	
	function resetAddNoteMessage() 
	{
		if($('message'+_commonSOMgr.widgetId).value == '[Message]'){
		 $('message'+_commonSOMgr.widgetId).value = '';
		}
	}
	
	var incSpendLimitCallBackFunction = function(data) {
		if(document.getElementById('titlePaneBtns')!=null)
			document.getElementById('titlePaneBtns').style.display = "block";
		if(document.getElementById('disabledDepositFundsDiv')!=null)
			document.getElementById('disabledDepositFundsDiv').style.display = "none";
		var incSlPF = newco.jsutils.handleCBData(data);
		if (incSlPF != null ) {
			newco.jsutils.doIfrModalMessage(newco.jsutils.getSelectedIfm(), incSlPF.pass_fail_status, incSlPF.resultMessage);
		}
		newco.jsutils.updateWithXmlData('increaseSPendLimitResponseMessage'+_commonSOMgr.widgetId,'message',data);
		if (incSlPF.pass_fail_status == 1) {
			newco.jsutils.clearSpendLimitData();
			newco.jsutils.updateOrderExpressMenu(_commonSOMgr.widgetId, incSlPF.addtional1, incSlPF.addtional2,incSlPF.addtional3);
		}
		
	}

	function fnCalcSpendLimit() {
		var status = newco.jsutils.calcSpendLimit();
	}
	function fnEnable(){
    	var theTab = _commonSOMgr.widgetId;
		var reasonCode = document.getElementById('reason_widget'+theTab).value;
    	if(reasonCode == "-2"){
    		document.getElementById('comment_widget'+theTab).disabled = false;
    		document.getElementById('comment_widget'+theTab).value = "";
    		document.getElementById('comment_widget'+theTab).style.background = '#FFFFFF';


    	}else{
    		document.getElementById('comment_widget'+theTab).disabled = true;
    		document.getElementById('comment_widget'+theTab).value = "";
    		document.getElementById('comment_widget'+theTab).style.background = '#E3E3E3';



    	}
	}
	function limitText(limitField,limitNum) {
		if (limitField.value.length > limitNum) {
			limitField.value = limitField.value.substring(0, limitNum);
		}
		}
	function fnSubmitIncreaseSpendLimit(actionName,callBackfunction,param3,formname, textAreaId) {
		document.getElementById('titlePaneBtns').style.display = "none";
		document.getElementById('disabledDepositFundsDiv').style.display = "block";
		//First calculate the Maximum Price and then Submit
		var status = newco.jsutils.calcSpendLimit();	
		if(status){
			var theTab = _commonSOMgr.widgetId;
			var totalAmt = 0.0;
			var totalAmtParts = 0.0;
			
			var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};		
			totalAmt = parseFloat($('increaseLimit'+theTab).value);			
			totalAmtParts = parseFloat($('increaseLimitParts'+theTab).value);	
					
			var currentLabor=parseFloat($('currentLimitLabor'+theTab).value);			
			var currentParts=parseFloat($('currentLimitParts'+theTab).value);
					
			var totalAmtFinal = totalAmt + totalAmtParts;		      
			var currentFinal = currentLabor+currentParts;			
			var  increasedPrice  = totalAmtFinal - currentFinal;			
	
			if(maxSpendLimitPerSO != 0 && maxSpendLimitPerSO < increasedPrice){
	     		$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "You have requested a transaction amount that exceeds your limit. Please contact your administrator for further action.";        	
	        	status = false;
	       }
			var reasonCode = "";
			var reasonCodeId = "";
			  if($('reason_widget'+theTab)!= null){
			  reasonCodeId = document.getElementById('reason_widget'+theTab).value;
			  var selected_index = document.getElementById('reason_widget'+theTab).selectedIndex;
			  reasonCode = document.getElementById('reason_widget'+theTab).options[selected_index].text;
			  }
              var reasonComment = document.getElementById('comment_widget'+theTab).value;
              reasonComment = jQuery.trim(reasonComment);
              document.getElementById('increasedSpendLimitReasonWidget').value = reasonCode;
              document.getElementById('increasedSpendLimitReasonIdWidget').value = reasonCodeId;
              document.getElementById('increasedSpendLimitNotesWidget').value = reasonComment;
              if(reasonCodeId == "-1"){
              	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Select a Reason for Spend Limit Increase";
                $('increaseSPendLimitResponseMessage'+theTab).style.display= "block";
                status = false;
              }
              if(reasonCodeId == "-2" && (reasonComment == null || reasonComment == "")){
              	$('increaseSPendLimitResponseMessage'+theTab).innerHTML ="Enter Notes for Spend Limit Increase";
                $('increaseSPendLimitResponseMessage'+theTab).style.display= "block";
                status = false;
              }
              if(reasonCode == "" && (reasonComment == null || reasonComment == "")){
            	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Provide a Reason for Spend Limit Increase";
              	$('increaseSPendLimitResponseMessage'+theTab).style.display= "block";
              	status = false;
              }
		}
	
		if(status == true ){
		document.getElementById(textAreaId).value='';
		fnSubmit(actionName,callBackfunction,param3,formname);
		}else{
			document.getElementById('titlePaneBtns').style.display = "block";
			document.getElementById('disabledDepositFundsDiv').style.display = "none";
	}
	}
	
		
	function fnSubmitIncSpendLimit(){
                  document.getElementById('frmIncreaseSpendLimit').action ="${contextPath}/incSpendLimitAction.action" ;
                  document.getElementById('frmIncreaseSpendLimit').method="POST"; 
                  document.getElementById('frmIncreaseSpendLimit').submit();
        }  
        function submitCancellation(){
        	var errorMsg = fnValidateCancellation();
			if(errorMsg!=""){
				return false;
			}
	        var action = jQuery("#action").val();
	        var reason = jQuery("#reasonCode :selected").text();
	        jQuery("#reason").val(reason);
	        if(action=="delete"){
	        	 fnSubmit('somDeleteDraft.action',deleteDraftCB,null,'#frmCancelFromSOM');
	        }else{
	        	 fnSubmit('serviceOrderVoid.action',voidCB,null,'#frmCancelFromSOM');
	        }
                
        } 
	function fnValidateCancellation(){
				var errorMsg = "";
				jQuery("#cancelE1").html("");
				jQuery("#cancelE1").css("display","none");
				var reasonCode = jQuery("#reasonCode").val();
				if(reasonCode=="-1"){
					errorMsg = "Please select a reason for canceling this order.<br/>";
				}
				var comments = jQuery("#comments").val();
				var commentsTrim=trim(comments);
				jQuery("#comments").val(commentsTrim);
				if(commentsTrim==""){
					errorMsg = errorMsg + "Please enter comments describing why you are canceling this order.<br/>";
				}
				
				if(errorMsg!=""){
					jQuery("#cancelE1").html(errorMsg);
					jQuery("#cancelE1").css("display","block");
				}				
				return errorMsg;
			}
		function closeModalPopup(){
			jQuery.modal.close();
		}

	/*
	//Post the total Spend limit.			
	*/	
	function fnLoadCurrentSpendLimit(){
		newco.jsutils.updateTheNode('currentAmt', $('currentSpendLimit').value);
	}
	
	function fnSubmit(actionName,callBackfunction,param3,formname){
		newco.jsutils.doAjaxSubmit(actionName,callBackfunction,param3,formname);
	}
	
	
		
	function open_popup(page)
	{
		var id = document.getElementById('soID')
		page = page + id.value;				
    	window.open(page,'_blank','width=600,height=450,resizable=1,scrollbar=1');
   	}
   	
   	function fnCopyServiceOrder()
   	{	   	  		
   		var id = document.getElementById('soID');		   		
   		var path = "soWizardController.action?soId=";
   		path = path + id.value;
   		path = path + "&action=copy&tab=draft";
   		window.location = path;
   	}
   	
	var p_topFilterList ;
	var p_filterList ;
	var wasOnTab ;//variable to hold the tab last loaded

	function getSelectedStatus(mySelStatusObj,mySelSubStatusObj,serviceProName,marketName,myiFrameWindow,tabName )
    {
     var selObj = document.getElementById(mySelStatusObj);
     var selectedStatusIndex = selObj.selectedIndex; 
	 var subList;
     var subStatus = document.getElementById(mySelSubStatusObj);
     selectedStatus = selObj.options[selectedStatusIndex].value;
     if (selectedStatusIndex != 0) 
     {
     	subStatus.disabled = false;
        subList = p_topFilterList[selectedStatus];
        var boolean = 'N';
        if (selectedStatus =='Today' || selectedStatus == 'Inactive' ) 
        {
        	boolean ='Y';
        }
        if (boolean =='N' )
		{	
        	subStatus.options.length = 0;
            var o ;
			if(subList.length>0){
				o = new Option('Show All','0',false,false);
				subStatus.options[0]=o;
			}
            for(var i=0;i<subList.length;i++)
            {
            	o = new Option(subList[i].val2,subList[i].val1,false,false);
                subStatus.options[i+1] = o;
            }
        }
       }else{
       		subStatus.length=0;
        	var f = new Option('Show All','0',false,false);
	  	 	subStatus.options[0]=f;
      }
      newco.jsutils.clearAllActionTiles();
      var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;
      if(document.getElementById(serviceProName) != null && document.getElementById(marketName) != null ){
	      var serviceProSelObj = document.getElementById(serviceProName);
          var marketSelObj = document.getElementById(marketName);
          var selectedServiceProIndex = serviceProSelObj.selectedIndex;
          var selectedServicePro = serviceProSelObj.options[selectedServiceProIndex].value;
          var  selectedMarketNameIndex = marketSelObj.selectedIndex;
          var selectedMarketName = marketSelObj.options[selectedMarketNameIndex].value;
          iFrameWindow.doStatusSubmit(selectedStatus,null,selectedServicePro,selectedMarketName);
     }else{
          iFrameWindow.doStatusSubmit(selectedStatus,null,null,null);
     }
   }

   function getSelectedSubStatus(status, subStatus, serviceProName,marketName,myiFrameWindow, buyerRoleId){ 
   		var statusSelObj = document.getElementById(status); 
     	var subStatusSelObj = document.getElementById(subStatus); 
     	var selectedStatusIndex = statusSelObj.selectedIndex; 
        var selectedStatus = statusSelObj.options[selectedStatusIndex].value;
	  	var  selectedSubStatusIndex = subStatusSelObj.selectedIndex; 
	  	var selectedSubStatus = subStatusSelObj.options[selectedSubStatusIndex].value; 	
        var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;

       	var buyerRoleIdObj = null;
       	var buyerRoleIdIndex = null;
       	var buyerRoleIdValue = null;
        
        if(buyerRoleId != null)
        {
        	//alert('buyerRoleId=' + buyerRoleId);
        	buyerRoleIdObj = document.getElementById(buyerRoleId);
        	buyerRoleIdIndex = buyerRoleIdObj.selectedIndex;
        	buyerRoleIdValue = buyerRoleIdObj.options[buyerRoleIdIndex].value;
        	//alert('buyerRoleId=' + buyerRoleIdValue);
        }
        newco.jsutils.clearAllActionTiles();
        if(document.getElementById(serviceProName) != null && document.getElementById(marketName) != null )
        {
	        var serviceProSelObj = document.getElementById(serviceProName);
            var marketSelObj = document.getElementById(marketName);
            var selectedServiceProIndex = serviceProSelObj.selectedIndex;
            var selectedServicePro = serviceProSelObj.options[selectedServiceProIndex].value;
            var  selectedMarketNameIndex = marketSelObj.selectedIndex;
            var selectedMarketName = marketSelObj.options[selectedMarketNameIndex].value;
            iFrameWindow.doStatusSubmit(selectedStatus,selectedSubStatus,selectedServicePro,selectedMarketName, buyerRoleIdValue);
        }
        else
        {
            iFrameWindow.doStatusSubmit(selectedStatus,selectedSubStatus,null,null, buyerRoleIdValue);
       	}
   }




   
	/* START FILTER BY ORDER TYPE */

   function getSelectedPriceModel(mySelTypeObj,serviceProName,marketName,myiFrameWindow,tabName )
   {
    var selObj = document.getElementById(mySelTypeObj);
    var selectedTypeIndex = selObj.selectedIndex; 
    selectedType = selObj.options[selectedTypeIndex].value;
    
     newco.jsutils.clearAllActionTiles();
     var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;
     if(document.getElementById(serviceProName) != null && document.getElementById(marketName) != null ){
	      var serviceProSelObj = document.getElementById(serviceProName);
         var marketSelObj = document.getElementById(marketName);
         var selectedServiceProIndex = serviceProSelObj.selectedIndex;
         var selectedServicePro = serviceProSelObj.options[selectedServiceProIndex].value;
         var selectedMarketNameIndex = marketSelObj.selectedIndex;
         var selectedMarketName = marketSelObj.options[selectedMarketNameIndex].value;
         iFrameWindow.doPriceModelSubmit(selectedType,null,selectedServicePro,selectedMarketName);
    }else{
    	iFrameWindow.doPriceModelSubmit(selectedType,null,null,null);
    }
  }
   
   /* END FILTER BY ORDER TYPE */
   
   
     		
     		function sortByColumn(tabName, sortColumnName, statusSortForm){
     			sortByColumn(tabName, sortColumnName, statusSortForm, '');
     		}
     		function sortByColumn(tabName, sortColumnName, statusSortForm, filterId)
     		{	
     			var buyerRoleIdObj = document.getElementById('buyerRoleId'+tabName);     			
     			var marketSelObj = document.getElementById('marketName'+tabName);     			
     			var serviceProSelObj = document.getElementById('serviceProName'+tabName);
     			
     	       	var buyerRoleIdIndex = null;
     	       	var buyerRoleIdValue = null;
     	        
     	        if(buyerRoleIdObj != null) {     	        	    	        	
     	        	buyerRoleIdIndex = buyerRoleIdObj.selectedIndex;
     	        	buyerRoleIdValue = buyerRoleIdObj.options[buyerRoleIdIndex].value;     	        
     	        } else {
     	        	buyerRoleIdValue = 3;
     	        }    	             	       
    	        if(serviceProSelObj != null) {
    	            var selectedServiceProIndex = serviceProSelObj.selectedIndex;
    	            var selectedServicePro = serviceProSelObj.options[selectedServiceProIndex].value;
    	        } else {
    	            var selectedServicePro ="";
    	        }
         	    
				if(marketSelObj != null){       
     	            var  selectedMarketNameIndex = marketSelObj.selectedIndex;
     	            var selectedMarketName = marketSelObj.options[selectedMarketNameIndex].value;
         	    } else {
   	             	var selectedMarketName ="";
   	            }

	  			var statusSelObj = document.getElementById('statusId'+tabName); 
     		  	var subStatusSelObj = document.getElementById('subStatusId'+tabName); 
     		  	if (statusSelObj != null) {
     				var selectedStatusIndex = statusSelObj.selectedIndex; 
     				var selectedStatus = statusSelObj.options[selectedStatusIndex].value;
     			} else {
     				var selectedStatus = 110;
     			}
     			if (subStatusSelObj != null) {
     				var selectedSubStatusIndex = subStatusSelObj.selectedIndex; 
     				var selectedSubStatus = subStatusSelObj.options[selectedSubStatusIndex].value; 
     			} else {
     				var selectedSubStatus = 0;
     			}
            	var iFrameWindow = document.getElementById(tabName+'myIframe').contentWindow;
            	var iFrameWindowForm = iFrameWindow.document.getElementById(statusSortForm);
            
            	iFrameWindowForm.status.value = selectedStatus;
            	iFrameWindowForm.subStatus.value = selectedSubStatus;

            	if(iFrameWindowForm.sortColumnName.value == sortColumnName)
            	{
            		if(iFrameWindowForm.sortOrder.value == 'ASC'){
            		iFrameWindowForm.sortOrder.value = 'DESC';
            	}
            	else
            	{
            		iFrameWindowForm.sortOrder.value = 'ASC';
            	}
            }
            else
            {
            	iFrameWindowForm.sortOrder.value = 'ASC';
            }
            iFrameWindowForm.sortColumnName.value = sortColumnName;
                 
            
            try{
            	sortImageFlip(sortColumnName, iFrameWindowForm.sortOrder.value, tabName);
            }catch(e){
            }
            newco.jsutils.clearAllActionTiles();
            
	  iFrameWindow.newco.jsutils.displayModal('loadingMsg'+ _commonSOMgr.widgetId);
	  iFrameWindow.doStatusSubmit(selectedStatus,selectedSubStatus,selectedServicePro,selectedMarketName, buyerRoleIdValue);	
	
	}
	
	
	
	
	/*
	Added for bulletin board.
	*/
	
	
	function sortByColumnBullBoard(tabName, sortColumnName, statusSortForm){
		 	
            	var iFrameWindow = document.getElementById(tabName+'myIframe').contentWindow;
            	var iFrameWindowForm = iFrameWindow.document.getElementById(statusSortForm);
            
            	
            	if(iFrameWindowForm.sortColumnName.value == sortColumnName)
            	{
            		if(iFrameWindowForm.sortOrder.value == 'ASC'){
            		iFrameWindowForm.sortOrder.value = 'DESC';
            	}
            	else
            	{
            		iFrameWindowForm.sortOrder.value = 'ASC';
            	}
            }
            else
            {
            	iFrameWindowForm.sortOrder.value = 'ASC';
            }
            iFrameWindowForm.sortColumnName.value = sortColumnName;
                 
            
            try{
            	sortImageFlip(sortColumnName, iFrameWindowForm.sortOrder.value, tabName);
            }catch(e){
            }
            newco.jsutils.clearAllActionTiles();
            
	  iFrameWindow.newco.jsutils.displayModal('loadingMsg'+ _commonSOMgr.widgetId);
	  iFrameWindowForm.submit();
	
	}
	
	function trim(myString){
		return myString.replace( /^\s+/g,'').replace(/\s+$/g,''); 
	}	
	function clearDefault(el) {
  		if (el.defaultValue==el.value) el.value = ""
	}
	
	
	function getSelectedProviderBullBoard(serviceProName,myiFrameWindow, buyerRoleId, searchWords, button){ 
   	 	
        var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;
		var searchWordsValue = '';

        newco.jsutils.clearAllActionTiles();
          
        if(document.getElementById(searchWords) != null )
        {
	        searchWordsValue = document.getElementById(searchWords).value; 
        	searchWordsValue = this.trim(searchWordsValue);
        	
        }
       
       /*
      	if(button=="true" && searchWordsValue.length<1){
        	alert("Please enter a valid search word");
        	return false;
        }
        */
        if(document.getElementById(serviceProName) != null )
        {
	        var serviceProSelObj = document.getElementById(serviceProName);
            var selectedServiceProIndex = serviceProSelObj.selectedIndex;
            var selectedServicePro = serviceProSelObj.options[selectedServiceProIndex].value;
          
            iFrameWindow.doStatusSubmit(null,null,selectedServicePro,null, null,searchWordsValue);
        }
        else
        {
            iFrameWindow.doStatusSubmit(null,null,null,null,null,searchWordsValue);
       	}
   }      
     	
	
	
	function sortImageFlip(sortColumnName, sortOrder, currentTab){
	  if(sortOrder == 'ASC'){
            	$('sortBy'+sortColumnName+currentTab).src = "${staticContextPath}/images/grid/arrow-up-white.gif";
            }
            else{
            	$('sortBy'+sortColumnName+currentTab).src = "${staticContextPath}/images/grid/arrow-down-white.gif";
            }
            
            if(currentTab == 'Posted' || currentTab == 'Received'){
            	$('sortBySpendLimit'+currentTab).style.display = "none";
            	$('sortByTimeToAppointment'+currentTab).style.display = "none";
            	$('sortByAgeOfOrder'+currentTab).style.display = "none";
            }
            else{
            	$('sortByServiceDate'+currentTab).style.display = "none";
            }
            
            $('sortByStatus'+currentTab).style.display = "none";
            $('sortBySoId'+currentTab).style.display = "none";
            
            
         	  $('sortBy'+sortColumnName+currentTab).style.display = "block";
   }
	
	
function getSummaryTabCount (tabId){
	var widgetTitle = dijit.byId(tabId).title;
	var index1 = widgetTitle.indexOf('(');
	var index2 = widgetTitle.indexOf(')');
	
	var tabCount = widgetTitle.substring(index1+1,index2);
	
	//alert ("widgettitle " + index1 + ": " + index2);

	//alert ("TabCount: " + tabCount);

	return tabCount;
	
}

function open_history_notes(vendorId)
{
	if (document.openProvURL != null)
	{
		document.openProvURL.close();
	}
	var url = "powerAuditorWorkflowAction_getHistoryNotes.action?resourceID=-1&vendorID="+vendorId;
	newwindow=window.open(url,'_publicNotesHistory','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
	if (window.focus) {newwindow.focus()}
	document.openProvURL = newwindow;
}

var sortingDone = false;
var clickedObject, clickedColNum; 
var sortArrowClass;

function sortByThisColumn(obj,num){
	window.frames['bidRequestsIframe'].clickColumn(num);
	clickedObject = obj;
	clickedColNum = num;
	
	var newClass = "headerSortDown";
	jQuery("#bidsTabGridHeader > thead > tr > th").each(function(i){
	if(jQuery(this).attr('class').indexOf('headerSortDown')>0){
		newClass = "headerSortUp";
	}
	jQuery(this).removeClass("header");
	jQuery(this).removeClass("headerSortUp");
	jQuery(this).removeClass("headerSortDown");
	});
	//jQuery(obj).addClass(newClass);
}



function sortOnThisColumn(obj,num){
	window.frames['bulletinIframe'].clickColumn(num);
	clickedObject = obj;
	clickedColNum = num;
	
	var newClass = "headerSortDown";
	jQuery("#bulletinBoardGridHeader > thead > tr > th").each(function(i){
	if(jQuery(this).attr('class').indexOf('headerSortDown')>0){
		newClass = "headerSortUp";
	}
	jQuery(this).removeClass("header");
	jQuery(this).removeClass("headerSortUp");
	jQuery(this).removeClass("headerSortDown");
	});
	//jQuery(obj).addClass(newClass);
}

function addSortArrow(){
	jQuery("#bidsTabGridHeader >thead >tr >th:eq("+clickedColNum+")").addClass(sortArrowClass);
}

function calculateSpendLimit(){
	var soId = document.getElementById('IncSLSOid').value;
	var tab = document.getElementById('tab').value;
	jQuery('#increaseSpendLimitModal').load("serviceOrderMonitorAction_loadDataForIncSL.action?soId="+soId+"&wfFlag=0",function() {
	 	jQuery("#increaseSpendLimitModal").modal({
				 onOpen: modalOpenAddCustomer,
				 onClose: modalOnClose,
				persist: true,
				 	containerCss: ({ width: "380px", height: "600px", marginLeft: "40px", top: "250px"})
		});
		window.scrollTo(1,1);
	});	    		   
}

	
   
</script>	

<style type="text/css">
.provPopup{
width: 460px;height: auto;
display: none;
}
/* SLT-2235*/
#overlayNew {
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: #FFFFFF;
			filter: alpha(opacity = 50);
			-moz-opacity: 0.7;
			-khtml-opacity: 0.7;
			opacity: 0.7;
			z-index: 299;
			display: block;
		} 
		.newTermsNoticePopUp{
			width: 500px;
			height: 120px;
			background-color: #f5b7b19e;
			color: #333;
			font-size: 11px;
			display: none;
			position:relative;
			padding-top: 20px;
    		padding-left: 28px;
    		padding-right: 27px;
    		margin-top: -1083px;
    		margin-left: 250px;
    		box-shadow: 0 2px 5px #000;
    		z-index: 301;
			
		}	
		.headingNewTCPopup {
			padding-bottom: 9px;
		}
		.headerSpanScrollDivTCPopup {
			color: black;
			font-size: 11px;
			margin-left: 60px;
		}
		/* SLT-2235 */	
</style>			
	</body>
</html>
