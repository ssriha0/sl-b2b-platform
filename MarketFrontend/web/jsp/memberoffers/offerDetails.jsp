<%@ page language="java"
	import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		<title>ServiceLive - Member Offers</title>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/memberOffer.css"/>
		<tiles:insertDefinition name="blueprint.base.meta" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
	    <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
		</style>
	<script type="text/javascript">
		jQuery(document).ready(function (){
		   $('#footer').css({"font-size":"10px"});
		   $('#footer p').css({"margin":"0 10px"});
		   var memberOfferId = ${offerId};
			 $.ajax({
		    		url: 'memberOffersUpdateView.action?memberOfferId='+memberOfferId });
		});
	function joinNow(){
	 	jQuery("#popup").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ width: "700px", height: "auto", marginLeft: "325px",position:"fixed"})
            });
	
	}
	
 		jQuery.modal.defaults = {
		overlay: 50,
		overlayId: 'modalOverlay',
		overlayCss: {},
		containerId: 'modalContainer',
		containerCss: {},
		close: true,
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
	
	</script>
	</head>
	<body>
		<div id="memberOffers">
			<div id="wrap" class="container">

				<tiles:insertDefinition name="blueprint.base.header" />
				<tiles:insertDefinition name="blueprint.base.navigation" />
				<div id="content" class="span-24 clearfix">
					<div>
						<img style="float: right;"
									src="${staticContextPath}/images/member_offers/offer_details_hdr.png" />
					</div>				
					<div class="viewAllOffers">
						<div style="padding-left: 25px;padding-top: 7px;">
						<a style="color: #FFFFFF;font-size: 14px;font-weight: bold" href="memberOffers_fetchAllOffers.action?loadPreviousPage=true" /><< View all offers</a>
					</div>
					</div>
					<div class="offerDetailsTable">					
					<table>
						<tr>
							<td style="vertical-align: top;">
								<div class="offerDetailsLeftSide">	
									<div id="offerDetailImg1" class="offerDetailImg1">
										<img src="${contextPath}/memberOffers_retrieveOfferImage.action?max_width=250&max_height=250&imageURL=<c:out value="${specificDetails.staticImageOnePath}"/>" />								
									</div>				
									<div id="offerJoinNow" class="offerJoinNow" onclick="joinNow();">
									<div class="joinNowTxt" >
										JOIN NOW
									</div>
									<!-- <a  />JOIN NOW</a>									
										<img id="joinnow"
													src="${staticContextPath}/images/member_offers/join_now_button.png"
													onclick="joinNow();" /> -->
									</div>
									<c:if test="${not empty specificDetails.staticImageTwoPath }">
									<div id="offerDetailImg2" class="offerDetailImg2">
										<img src="${contextPath}/memberOffers_retrieveOfferImage.action?max_width=250&max_height=250&imageURL=<c:out value="${specificDetails.staticImageTwoPath}"/>" />
									</div>
									</c:if>
									<c:if test="${not empty specificDetails.staticImageThreePath}">
									<div id="offerDetailImg3" class="offerDetailImg3">
										<img src="${contextPath}/memberOffers_retrieveOfferImage.action?max_width=250&max_height=250&imageURL=<c:out value="${specificDetails.staticImageThreePath}"/>" />
									</div>	
									</c:if>
								</div>				
							</td>
							<td style="vertical-align: top;">
								<div class="offerDetailsRightSide">	
									<div id="detailsCompName" class="detailsCompName">
										${specificDetails.offerDetailsCompanyName}
									</div>
									<div id="mainTextOne" class="mainTextOne" align="justify">
										${specificDetails.mainTextOne}
									</div>
									<div id="mainTextTwo" class="mainTextTwo" align="justify">
										${specificDetails.mainTextTwo}
									</div>
									
									<div class="listhdr">
								        ${specificDetails.listHeader}
									</div>
									<div id="offerList" class="offerList">
										<ul>
											<c:forEach items="${specificDetails.valueList}" varStatus="rowcounter" var="detail">
												<li>${detail}</li>
											</c:forEach>
										</ul>
									</div>									
									<div id="mainTextThree" class="mainTextThree" align="justify">
										${specificDetails.mainTextThree}
									</div>
								</div>							
							</td>
						</tr>
					</table>
					</div>
					<!-- <div style="margin-top: 30px;">
						<img src="${staticContextPath}/images/member_offers/offer_details_footer.png"/>
					</div> -->
					<div class="viewAllOffersBottom">
						<div style="padding-left: 20px;padding-top: 7px;" >
							<a  href="memberOffers_fetchAllOffers.action?loadPreviousPage=true" />
								<a style="color: #FFFFFF;font-size: 14px;font-weight: bold" href="memberOffers_fetchAllOffers.action?loadPreviousPage=true" /><< View all offers</a>
						</div>
					</div>
					<div id="popup" class="redirectDiv">
					  <!--  <div class="spanPopUp">						
							<span class="leaveSpan">Leaving ServiceLive </span>
							<span class="closeSpan"><a><img
										class="closeIcon"
										src="${staticContextPath}/images/btn/x.png"
										onclick="jQuery.modal.close(true);"></a>
							</span>
					 	</div> -->
						<div class="leavingLogo">	   
	   						<img src="${staticContextPath}/images/member_offers/leaving_sl.png"/>					      
					    </div>
						<div class="greyDiv">
						<div class="companyNameDiv">
						<span class="companyName"><b>And on your way to
						                        <span style="color:#159fd3"> <c:out value="${specificDetails.popUpCompanyName}" /></b></span>
						</span>                        
					<!--  <span class="spinner"><img src="${staticContextPath}/images/spinner_small.gif"/></span>-->
						</div>
							<div class="whiteDiv" align="left">
								<div style="position:relative;margin-left:20px;margin-top: 10px;margin-bottom: 20px;">
									<b>Please note that Sears Holdings Corporation or any of its affiliates cannot be held responsible for any external web sites’ privacy policies or content.
										<p>Please confirm your agreement by clicking on Continue.</p></b>
								</div>
							</div>
						</div>
						<br/>
						<div class="cancelOk">
							<span class="okdiv">
								<a class="cancellink" href="#" onclick="jQuery.modal.close(true);">Cancel</a>
							</span>
							<span class="cancelsdiv">
								<a href="http://<c:out value="${specificDetails.targetSite}"/>" target="_blank" rel="nofollow" onclick="jQuery.modal.close(true);">
								<img src="${staticContextPath}/images/buttons/continue.jpg"></a>
							</span>
						</div>
						</div>	
						
			</div>
			    <div id="greyFooter">
			        <jsp:include page="/jsp/public/common/defaultFooter.jsp" ></jsp:include>
			    </div>
			</body>
</html>