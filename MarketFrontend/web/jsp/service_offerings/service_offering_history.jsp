<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="error" scope="session" value="<%=session.getAttribute("ErrorMessage")%>" />
<script type="text/javascript">
</script>
<html>
	<head> 
	<tiles:insertDefinition name="blueprint.base.meta" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/public.css"/>
		<link href="${staticContextPath}/javascript/confirm.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css"/>


	<style type="text/css">
		.superfish ul li {
		width:97%;
	}
	.leftPadded{padding-left: 20px;}
	</style>
	<title>ServiceLive - Service Offering History</title>
		
</head>
	<body>
	   <div id="wrap" class="container">
				<tiles:insertDefinition name="blueprint.base.header" />
				<tiles:insertDefinition name="blueprint.base.navigation" />
				<div id="content" class="span-24 clearfix">
				  <div id="serviceOfferingHistory">
				
					<br><br>
					<h2 style="margin-left: 30px;">Service Offering History</h2>
					
					<div class="buyersDiv">
		                <span class="leftPadded"> This is the page used to display service offering history,service price history and service offering availability history</span>
		                   <c:if test="${fn:length(serviceOfferingHistory.serviceOfferingsHistoryList) != 0}">
		                      <c:set var="serviceOfferingHistorySize" value="${fn:length(serviceOfferingHistory.serviceOfferingsHistoryList)}"/>
		                       <div class="leftPadded">
		                          The size of serviceOfferingHistory is ${serviceOfferingHistorySize}
		                       </div>
		                  </c:if>
		                  <c:if test="${fn:length(serviceOfferingHistory.serviceOfferingspricingHistoryList) != 0}">
		                    <c:set var="serviceOfferingpriceHistorySize" value="${fn:length(serviceOfferingHistory.serviceOfferingspricingHistoryList)}"/>
			                    <div class="leftPadded">
			                         The size of serviceOfferingpriceHistory is ${serviceOfferingpriceHistorySize}
			                    </div>
		                 </c:if>
		                 <c:if test="${fn:length(serviceOfferingHistory.serviceOfferingsAvailabilityHistoryList) != 0}">
                            <c:set var="serviceOfferingAvailabilityHistorySize" value="${fn:length(serviceOfferingHistory.serviceOfferingsAvailabilityHistoryList)}"/>
		                         <div class="leftPadded">
		                              The size of serviceOfferingAvailability History is ${serviceOfferingAvailabilityHistorySize}
		                         </div>	
		                </c:if>
		            </div><!--end of buyer div  -->
		        </div><!--end of serviceOfferingHistory div  -->
		      </div><!--end of content div  -->
		      <tiles:insertDefinition name="blueprint.base.footer" />
		   </div><!--end of wrap div  -->
	</body>
</html>
