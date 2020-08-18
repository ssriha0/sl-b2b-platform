<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="noJs" value="true" scope="request"/> <%-- tell header not to insert any JS --%>
<c:set var="noCss" value="true" scope="request"/><%-- tell header not to insert any CSS --%>
<c:set var="currentMenu" scope="request" value="dashboard" /><%--to be consumed by the header nav to highlight the dashboard tab --%>
<c:if test="${userRole == 'Provider'}"><c:set var="provider" value="true" scope="request"/></c:if> <%-- needed for brevity in presentation logic --%>
<c:set var="newTandCContent" value="<%=request.getAttribute("newTandCContent")%>"/>
<%-- <c:set var="termsLegalNoticeChecked" value="<%=request.getAttribute("termsLegalNoticeChecked")%>"/> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ServiceLive Dashboard</title>
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

            	<meta http-equiv="Cache-Control" content="no-cache " />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
       <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
		   .ff2 .bannerDiv{margin-left:-190px;}
		   .gecko .bannerDiv{margin-left:-190px;}
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dashboard.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/memberOffer.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/autoAcceptance.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/toggle-btn.css"/>	
		<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
		
		<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
		<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
		
				
		<style type="text/css">
		.button{
			background: url("${staticContextPath}/images/common/button-action-bg.png");
			border:1px solid #b1770b;
			color:#222;
			font-family:Arial,Tahoma,sans-serif;
			font-size:1.1em;
			font-weight:bold;
			padding:3px 10px;
			cursor: pointer;
			-moz-border-radius:5px 5px 5px 5px;
			margin-top:  -5px;
			text-align: center;
			width: 80px;
		}
		.popUp{
			display: none;		
			border:3px solid #C6D880;
		}
		#modalOverlay {background-color:#000; cursor:wait;}
		
		
		.newTandCPopup{
			width: 925px;
			height: 450px;
			background-color: #EEE;
			color: #333;
			z-index: 10000;
			font-size: 12px;
			display: none;
			position:fixed;
			border-top-left-radius: 10px;
			border-top-right-radius: 10px; 
			border-bottom-left-radius: 10px;
			border-bottom-right-radius: 10px;
			
		}
		.newTandCContentDiv{
			overflow-y: auto; 
			height:290px;
			width: 820px;
			padding-bottom:1%; 
			padding-left:3%;
			padding-right:3%;
			padding-top: 2%;
			margin-left:3%;
			background-color: white;
			font-size: 12px;
			text-align:justify;
			text-justify:inter-word;
			
		}.heading1 {
			border-bottom: 1px solid #9F9F9F;
			padding-bottom: 5px;
			padding-top: 5px;
			background-color: #dadada;
			text-align: center;
			border-top-left-radius: 10px;
			border-top-right-radius: 10px;
		}
		.headerSpanScrollDiv {
			color: black;
			font-size: 15px;
			margin-left: 8px;
		}
		.introSentenceStyle{
			margin-top:10px;
			margin-left:25px;
			margin-bottom:10px;
		}.iFrameContentStyle{
			margin-left:3%;
			width:870px;
			height:350px;
		}.agreeButtonTable{
		 	margin-left: 25px; 
		 	width: 770px;
		} 
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
			display: none;
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
    		margin-top: 150px;
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
		</style>
		
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
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
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
	</head>

	<body class="tundra">
	        
			<div id="page_margins">
			<div id="page">
				<table width="100%" border=0 cellspacing=0 cellpaddig=0>
					<tr>
						<td colspan=2>

							<!-- START HEADER -->
				<div id="headerShort" style="z-index: 300">
					
						<tiles:insertDefinition name="newco.base.topnav" />
						<tiles:insertDefinition name="newco.base.blue_nav" />
					
						<tiles:insertDefinition name="newco.base.dark_gray_nav" />
								
					<div id="pageHeader">
									<div  class="left">
										<img
											src="${staticContextPath}/images/dashboard/dashboard_header.gif" />
									</div>
									
									
		<c:if test="${provider}">			
			<div class="right" style="text-align:right;">
				<c:if test="${empty dateString}">
					<c:set var="dateString" value="<%=com.newco.marketplace.utils.DateUtils.getHeaderDate()%>"></c:set>
				</c:if>
				<span id="date_dashboard">${dateString}</span>
				<c:if test="${viewOrderPricing==true}">
				<p id="dashboardAvailableBalance">Available Wallet Balance: ${dashboardDTO.availableBalanceFormat}</p>
				</c:if>
			</div>
				
			<div id="popup" class="provPopup">	
				<br>
				<!-- <div style="margin-left: 35px;">
					<img style="padding-left: 30px;" src="${staticContextPath}/images/member_offers/member_offer_widget_image.png"/><br>
				</div>	 -->
				<div style="margin-top: 60px;">
					<b >	We have new Member offers. Would you like us to take you there?</b>
				</div>																		
				<br /><br><br>
				<table style="margin-left: 170px;">
					<tr>
						<td width="55%">
							<input id="noButton" class="button simplemodal-close" type="button" value="No" />
						</td>
						<td>
							<input id="yesButton" class="button" type="button" value="Yes" />
							<br><br>
						</td>
					</tr>
				</table>						
			</div>
			<div id="newpopup" class="newTandCPopup">
				<div id="heading" class="heading1">
					<span class="headerSpanScrollDiv"><b>Updated SL Leads Addendum</b></span>
					</br>
				</div>	
				<div class="introSentenceStyle">
					<b> </b>
				</div>
					
					<div class="newTandCContentDiv">
							${newTandCContent}
					</div>
					</br>
				<div>
					</br>
					<table class="agreeButtonTable">
						<tr>
							<td ><a href="${slLeadsAddendumLink}" target="_blank" style="font-size: 12px;"><b>Printable Version(PDF)</b></a></td>

							<td >
							<table>
							<tr><td>If you are an active ServiceLive Leads provider, please take a moment to read and continue. </td></tr>
							<tr><td>If you are not an active ServiceLive Leads provider, simply continue.</td></tr>
							</table>
							</td>							  
							<td >
								<input id="acceptButton" class="button" type="button" value="Continue" />
							</td>
						</tr>
					</table>		
				</div>																
			</div>
			
			<div id="carNotificationDiv" class = "modal" style="width:105%"></div>			
					
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
			<!--  SLT-2235:ends -->
		</c:if>
					</div>
				</div>
					</td>
					</tr>
					<tr>
						<td>
							<div class="dashboardLeftColumn">
								<c:forEach var="module" items="${moduleList}">
									<jsp:include page="/jsp/dashboard/body/html_sections/modules/${module}" />
								</c:forEach>
							</div>
						</td>
						<td>
						
							<div class="dashboardRightColumn">
								<%-- Commented out, for now as instructed by KristinA 
								<s:include value="/jsp/dashboard/body/html_sections/center/news.jsp" />
								--%>
								<s:include value="/jsp/dashboard/body/html_sections/center/vital_stats.jsp" />
<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && providerLeadManagementPermission == 'true' && showLeadsSignUp == 1}">
	<s:include value="/jsp/dashboard/body/html_sections/center/lead_vital_stats.jsp" />
	</c:if>
<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3 && buyerLeadManagementPermission == 'true'}">
			<s:include value="/jsp/dashboard/body/html_sections/center/lead_vital_stats.jsp" />
</c:if>
	<c:if test="${SecurityContext.slAdminInd}">
	<div class="monitorTab-rightCol">
		<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" />
	</div>
</c:if>    
								<br>
								<div id="successDiv" class="successDiv">
									Your changes to Auto Acceptance are successful. 
									<a href="${contextPath}/manageAutoOrderAcceptanceAction_execute.action">
										<u style="padding-left:5px;color:green">View</u>
									</a>
								</div>
		
							<div class="clearfix"></div>
								<%-- Communications Monitor (Comm Monitor) --%>
								<c:if test="${displayCommMonitor}">
									<s:include value="/jsp/dashboard/body/DBCOMM_monitor_for_PA.jsp"/>
								</c:if>
							</div>
							<!-- Added for Mobile App Banner -->
							<div style="float:right;margin-right:15px;padding-top:10px;">
								<a href="http://mobile.servicelive.com/provider" target="_blank">
							<img width="300" height="150" alt="Mobile Banner" src="${staticContextPath}/images/mobileBanner/banner_MarketFrontend.gif"/></a>
							</div>
						</td>
					</tr>

					<tr>
						<td colspan=2>
							<!-- START FOOTER -->
							<s:include value="/jsp/public/common/defaultFooter.jsp" />
							<!-- END FOOTER -->

						</td>
					</tr>
				</table>
			</div>
		</div>
		<s:include value="/jsp/public/common/omitInclude.jsp">
			 <s:param name="PageName" value="common.dashboard"/>
		</s:include>	
		<div id="explainer"></div>
		<div id="carNotificationModal" class="heading modal"></div>
		
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		
		<script type="text/javascript" src="${staticContextPath}/javascript/tooltip.js" ></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery.simplemodal.1.4.4.min.js" ></script>		
		
		<script type="text/javascript">
	 	jQuery.noConflict();
		jQuery(document).ready(function($){
			var lifetimeRatingDef = "<strong>Lifetime Rating</strong> is your overall rating given by your customers since joining ServiceLive.<br/><br/><strong>Current Rating</strong> spans the last 90 days.";
			var currentRatingDef = "<strong>Lifetime Rating</strong> is your overall rating given by your customers since joining ServiceLive.<br/><br/><strong>Current Rating</strong> spans the last 90 days.";
			var bidRequestsDef = "<strong>Bid Requests</strong> are orders that the buyer posted without a price, requesting 'Bids' or estimates from providers.";
			var completedLead=$("#completedLead").val();
			var totalLead=$("#totalLead").val();
			var goal=$("#goal").val();
			
			var convRateDef="<strong>"+completedLead+" leads</strong> completed out of <strong>"+totalLead+"</strong> received";
			var avgResponseDef="<strong>Your Goal :</strong>"+goal+" min";
	
		
			$(".glossaryItem").mouseover(function(e){
	      	var x = e.pageX;
	      	var y = e.pageY;
	      	$("#explainer").css("top",y-50);
	      	$("#explainer").css("left",x+20);
	
	      	$("#explainer").css("position","absolute");
	      	var explDefId = eval($(this).attr("id") +"Def");
	      	
	      	var arrow = '<div id="pointerArrow" style="position:absolute; float:left; top:30%; left:-13px; width:16px; height:19px; background: url(/ServiceLiveWebUtil/images/icons/explainerArrow.gif) no-repeat 0 0;" />';
	      	$("#explainer").html(arrow + explDefId);
	      	
	      	if($(this).attr("id") == "bidRequests") {
	      	$("#explainer").css("left",x-260);
	      	$("#pointerArrow").hide();
	      	}
	      	
	      	$("#explainer").show();
	     
	 		}); 
	 		$(".glossaryItem").mouseout(function(e){
	 		$("#explainer").hide();
	 		});
			
			<c:set var='newTandC' value='0'/>
			 <c:if test="${newLeadsTCIndicator!=null && newLeadsTCIndicator==0 && SERVICE_ORDER_CRITERIA_KEY.roleId==1}">
			 
			 <c:set var='newTandC' value='1'/>
	 			jQuery("#newpopup").modal({
	                onOpen: modalOpen,
	                onClose: modalOnClose,
	                persist: true,
	                containerCss: ({ width: "700px", height: "auto", marginLeft: "235px",marginTop: "-30px" })
	            });
	 		</c:if>
	 		
	 		<c:if test="${newTandC==0}">
	 		
	 				<c:set var='cars' value='0'/>
	 				
	 				<c:if test="${SecurityContext.activePendingCARRulesPresent > 0 && 1 == permission}">
			 		<c:set var='cars' value='1'/>	  	
			 		jQuery("#carNotificationDiv").load('manageAutoOrderAcceptanceAction_fetchCARRulesForProvider.action',function(){
			 				
							jQuery("#carNotificationDiv").fadeIn('slow');
							jQuery('#carNotificationDiv').css('display', 'block');
			 				jQuery("#carNotificationModal").modal({
			                	onOpen: modalOpen,
			                	onClose: modalOnClose,
			                	persist: true,
			                	containerCss: ({ width: "850px", height: "auto", margin: "auto", bottom: "0", left: "0", right: "0"})
			            	}); 
			 			});
			 		</c:if>
			 		
			 		<c:if test="${cars==0}">
				 			<c:if test="${indicator==1}">
				 			jQuery("#popup").modal({
				                onOpen: modalOpen,
				                onClose: modalOnClose,
				                persist: true,
				                containerCss: ({ width: "540px", height: "auto", marginLeft: "500px",marginTop: "100px" })
				            });
				 			</c:if>
			 		</c:if>
	 		</c:if>
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
	    	
	    	jQuery('#yesButton').click(function(){
	    		//window.location.href="https://business.servicelive.com/MarketFrontend/memberOffers_fetchAllOffers.action";
	    		jQuery('#popup').hide();
	    		jQuery('#newpopup').hide();
	    		jQuery('#modalOverlay').hide();
	            window.open("http://offers.servicelive.com/?auth=1","_blank");
	            });
	    		
			jQuery(document).click(function(e) {
				var id = jQuery(e.target).attr("id");
	        	if (id != 'successDiv') {
	        		jQuery('#successDiv').hide();
	        	}
	    	});
	          	jQuery('#acceptButton').click(function(){
	          	 $.ajax({
	          	 url: 'updateNewTandC.action',
	            type: "POST",
	            success: function(data) {
	            	jQuery('#newpopup').hide();
	            	jQuery('#modalOverlay').hide();
	            	
				 		
				 		<c:set var='carsFromNewTandC' value='0'/>
						<c:if test="${SecurityContext.activePendingCARRulesPresent > 0 && 1 == permission}"> 
						<c:set var='carsFromNewTandC' value='1'/> 	
				 			jQuery("#carNotificationDiv").load('manageAutoOrderAcceptanceAction_fetchCARRulesForProvider.action',function(){
				 				jQuery("#carNotificationModal").modal({
				                	onOpen: modalOpen,
				                	onClose: modalOnClose,
				                	persist: true,
				                	containerCss: ({ width: "850px", height: "auto", marginLeft: "250px",marginTop: "-50px" })
				            	}); 
				 			});		 				       
				 		</c:if>	
				 		
				 		
				 		<c:if test="${carsFromNewTandC==0}">
				 			<c:if test="${indicator==1}">
				 			jQuery("#popup").modal({
				                onOpen: modalOpen,
				                onClose: modalOnClose,
				                persist: true,
				                containerCss: ({ width: "540px", height: "auto", marginLeft: "500px",marginTop: "100px" })
				            });
				 			</c:if>
			 		</c:if>		 		
	            	
	            }
	             });
	    	});
	    	
	    	jQuery('#noButton').click(function(){
	    		jQuery('#newpopup').hide();
	    		jQuery('#modalOverlay').hide();
	    	});
	    	
	    	//SLT-2235:New popup for existing firms to accept the new T&C of court notice
	   	<c:if test="${provider && termsLegalNoticeChecked && not isSLAdmin && isPrimaryResource}">	
	   		$("#headerShort").css('z-index','');
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
		
		</script>
		
		
	</body>
</html>