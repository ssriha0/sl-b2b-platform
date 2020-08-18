<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	<title>ServiceLive - Member Offers</title>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/memberOffer.css"/>
	<tiles:insertDefinition name="blueprint.base.meta" />
	<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;}  
		</style>
	<script type="text/javascript">
	   jQuery(document).ready(function (){
		   $('#footer').css({"font-size":"10px"});});
	     function viewOfferDetails(memberOfferId){
	 	  		window.location.href="memberOffers_displayOfferDetails.action?memberOfferId="+memberOfferId;
	     }
	</script>
</head>
<body>	
	<div id="memberOffers">
		<div id="wrap" class="container">

			<tiles:insertDefinition name="blueprint.base.header" />
			<tiles:insertDefinition name="blueprint.base.navigation" />
				
			<div id="content" class="span-24 clearfix">
			
			<div style="margin-left: 40px;">
				<img src="${staticContextPath}/images/member_offers/member_offer_see_all_logo.png" />
			</div>	
			
			<!-- Implementation of Left side panels for member offers -->
			
			<div id="leftMemberOfferSide" class="leftMemberOfferSide">
			<c:if test="${fn:length(offerList) != 0}">
				<div id="leftMostPopularHdr" class="leftMostPopularHdr">
					<!-- <img src="${staticContextPath}/images/member_offers/left_hdr.png"/> 
					<h4 style="padding-top: 10px;padding-left: 10px;"><b>The Most popular offer!!!</b></h4>-->
				</div>
				<div id="leftMostPopularMiddle" class="leftMostPopularMiddle">
				
					<div id="leftMostPopularImg" class="leftMostPopularImg">
						<a onclick="viewOfferDetails(${dealOfDay.offerId});" style="cursor:pointer;">
						<img border="1" src="${contextPath}/memberOffers_retrieveOfferImage.action?max_width=125&max_height=125&imageURL=<c:out value="${dealOfDay.offerImagePath}"/>"/>
						</a>
					</div>
					
					<div id ="leftMostPopularCompanyName" class="leftMostPopularCompanyName">
						<a onclick="viewOfferDetails(${dealOfDay.offerId});"style="cursor:pointer;">
						${dealOfDay.companyName}
						</a>
					</div>					
					<div id="leftMostPopulartxt" class="leftMostPopulartxt" align="justify">
						${dealOfDay.description}					
					</div>
					<div style="float: right;margin-right: 20px;">
						<a onclick="viewOfferDetails(${dealOfDay.offerId});"style="cursor:pointer;">More >></a>
					</div>					
				</div>				
			</c:if>	
			</div>
			
			<!-- Implementation of Right side panels for member offers -->
			<div id="rightMemberOfferSide" class="rightMemberOfferSide">				
				<jsp:include page="memberOffersRightView.jsp"></jsp:include>
				<br />
				<br />							
			</div>
			
				<!-- Implementation of SL page footer -->
				<div id="greyFooter">
			        <jsp:include page="/jsp/public/common/defaultFooter.jsp" ></jsp:include>
			    </div>
				
				
			</div>
			
		</div>
	</div>
		
</body>
</html>
