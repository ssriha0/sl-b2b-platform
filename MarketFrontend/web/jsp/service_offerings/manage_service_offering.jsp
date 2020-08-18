<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="error" scope="session" value="<%=session.getAttribute("ErrorMessage")%>" />
<script type="text/javascript">

function displayServiceOfferings(){
	alert('1');
	 document.getElementById('serviceOfferingForm').submit();
	 alert('2'); 
}
</script>
<html>
	<head> 
	<title>ServiceLive - Manage Auto Acceptance</title>
	
	<tiles:insertDefinition name="blueprint.base.meta" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
	
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/confirm.css"/>	
	

		<style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;}  
		</style>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>

	<style type="text/css">
		.superfish ul li {
		width:97%;
	}
	</style>
		
</head>
	<body>
		<div id="manageServiceOffering">
			<div id="wrap" class="container">
				<tiles:insertDefinition name="blueprint.base.header" />
				<tiles:insertDefinition name="blueprint.base.navigation" />
				<div id="content" class="span-24 clearfix">
					<br><br>
					<h2 style="margin-left: 30px;">Manage Service Offerings</h2>
					
					<div class="buyersDiv">
						<table id="spn-monitor-background" border="0" cellpadding="0" cellspacing="0"
	style="width: 100%;text-align:left;display: block">
	<thead>
		<tr>
		
			<th class="tabledHdr firmHdr" id="Service" style="cursor: pointer !important;">
				Service 
				</th>
			<th class="tabledHdr marketHdr" id="SKU" style="cursor: pointer !important;">
				SKU 
			</th>
			<th class="tabledHdr stateHdr" id="SkillType" style="width: 68px;cursor: pointer !important;" >
				SkillType
 
			</th>
			<th class="tabledHdr statusHdr" id="Status" style="width:250px;cursor: pointer !important;text-align:left;">
				Status
  
			</th>
			
			<th class="tabledHdr statusHdr" id="ListPrice" style="width:250px;cursor: pointer !important;text-align:left;">
				ListPrice
  
			</th>

			<th class="tabledHdr statusHdr" id="Availability" style="width:250px;cursor: pointer !important;text-align:left;">
				Availability
  
			</th>	
		</tr> 
	</thead>				
	<tbody>
		<c:forEach items="${serviceOfferingList}" var="serviceOffer" varStatus="status">
		<tr>
								<td><span><a id="${serviceOffer.skuId}"  href="${contextPath}/manageServiceOfferingsAction_display.action?skuId=${serviceOffer.skuId}">${serviceOffer.service}</a></span></td>
							<td>${serviceOffer.sku}</td>
							<td>${serviceOffer.serviceType}</td>
							<td>${serviceOffer.status}</td>
							<td>${serviceOffer.price}</td>
							<td></td>
								</tr>
							</c:forEach>
	
	
	</tbody>
	
	</table>
							
						
					</div>

					<tiles:insertDefinition name="blueprint.base.footer" />
				</div>
			</div>
		</div>
		
		
		
	</body>
</html>
