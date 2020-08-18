<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="role" value="${roleType}"/>


<style type="text/css">
		body.acquity #finMgrModules  { margin-right: -300px;}
		<!--[if IE]>
		body.acquity #finMgrModules { margin-right: 0px;}
		<![endif]-->
			
</style>

<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1010px;}
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
			z-index: 106;
			display: block;
		} 
		.newTermsNoticePopUp{
			width: 500px;
			height: 200px;
			background-color: #f5b7b19e;
			color: #333;
			font-size: 11px;
			display: block;
			position:relative;
			padding-top: 20px;
    		padding-left: 28px;
    		padding-right: 27px;
    		margin-top: 0px;
    		margin-left: 250px;
    		box-shadow: 0 2px 5px #000;
    		z-index: 200;
			
		}	
		.headingNewTCPopup {
			padding-bottom: 0px;
		}
		.headerSpanScrollDivTCPopup {
			color: black;
			font-size: 11px;
			margin-left: 60px;
		}
		/* SLT-2235 */
		</style>
<div id="pageHeader">
	<h2>ServiceLive Wallet</h2>
</div>
	<tiles:insertDefinition name="newco.base.headerDate"/> 


	<div id="date" align="right">
	
	<c:if test="${(role ==1 && viewOrderPricing==true)||role == 3 || role == 5}">
		<p>Available Balance: <span id ="available_balance">${AvailableBalance}</span>
		</c:if>
			<br />
			<c:if test="${role == 3 || role == 5}">
				Current Balance: <span id ="current_balance">${CurrentBalance}</span>
			</c:if>
		<br />
		</p>
	</div>
		
<br><br><br><br><br>
<script language="javascript" type="text/javascript">
jQuery.noConflict(); 
 jQuery(document).ready(function(){
		//SLT-2235:New popup for existing firms to accept the new T&C of court notice
	   	<c:if test="${termsLegalNoticeChecked && not isSLAdmin && isPrimaryResource}">	
	   	jQuery("#header").css('z-index','unset');
	   	jQuery("#auxNav").css('z-index','unset');
	    	var overlayNew = jQuery('<div id="overlayNew"></div>');
	    	overlayNew.show();
	    	overlayNew.appendTo(document.body);
	    	jQuery('.newTermsNoticePopUp').show();
	    	jQuery('.headerSpanScrollDivTCPopup').show();
	    	jQuery('.reviewLink').click(function() {
	    		jQuery('.newTermsNoticePopUp').hide();
	    			overlayNew.appendTo(document.body).remove();
	    			window.location.href='/MarketFrontend/allTabView.action?tabView=tab5';
	    			return false;
	    	});	
	   </c:if>
	
	});
</script>
<c:if test="${termsLegalNoticeChecked && not isSLAdmin && isPrimaryResource}">
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
			</c:if>
			<!--  SLT-2235:ends -->
			