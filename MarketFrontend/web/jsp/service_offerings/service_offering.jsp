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
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>

<script type="text/javascript">




function saveServiceOfferings(){
	
	 document.getElementById('serviceOfferingForm').submit();
	
}
function offerStatus(status){
	if(status=='ON'){
		jQuery("#offeringStatusOn").attr('checked','checked');
		jQuery("#offeringStatusOff").removeAttr('checked');

		jQuery("#offeringStatusPause").removeAttr('checked');

		jQuery("#offeringStatus").val('ON');

	}else if(status=='OFF'){
		jQuery("#offeringStatusOn").removeAttr('checked');

		jQuery("#offeringStatusOff").attr('checked','checked');
		jQuery("#offeringStatusPause").removeAttr('checked');

		jQuery("#offeringStatus").val('OFF');


	}else if(status=='PAUSE'){
		jQuery("#offeringStatusOn").removeAttr('checked');

		jQuery("#offeringStatusOff").removeAttr('checked');

		jQuery("#offeringStatusPause").attr('checked','checked');
		jQuery("#offeringStatus").val('PAUSE');

	}


}
</script>
<html>
	<head> 
	<title>ServiceLive - Manage Auto Acceptance</title>
	
		<tiles:insertDefinition name="blueprint.base.meta" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/public.css"/>
		<link href="${staticContextPath}/javascript/confirm.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css"/>
	

		<style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;}  
		</style>

	<style type="text/css">
		.superfish ul li {
		width:97%;
	}
	</style>
		
</head>
	<body>
		
			<div id="wrap" class="container">
				<tiles:insertDefinition name="blueprint.base.header" />
				<tiles:insertDefinition name="blueprint.base.navigation" />
				<div id="content" class="span-24 clearfix">
								<div id="serviceOfferingDeatils" style="margin-left:30px;">
				
					<br><br>
					<h2 style="margin-left: 30px;">Manage Service Offerings</h2>
					
					<div class="buyersDiv">
         <form id="serviceOfferingForm" name="serviceOfferingForm" action="manageServiceOfferingsAction_save.action"method="POST">
    	         <input type="hidden" id ="title" name="title" value=""/>
    	          <input type="hidden" id ="image" name="image" value=""/>
    	          <input type="hidden" id ="serviceDescription" name="serviceDescription" value=""/>
    	           <input type="hidden" id ="availabilityList" name="availabilityList" value=""/>
    	           <input type="hidden" id ="providerRevenue" name="providerRevenue" value=""/>
    	           <input type="hidden" id ="skuId" name="skuId" value="${skuId}"/>
    	             <input type="hidden" id ="offeringId" name="offeringId" value=""/>
    	            <input type="hidden" id ="createdBy" name="createdBy" value=""/>
    	            
         <div>	ZipCode<input id="zipCode" type="text" name="zipcode"  value="${serviceOfferingDto.zipcode}"style="width:40px" onblur=""
										onkeypress="" class="shadowBox grayText"size="10" maxlength="7" value=""/></div>
			<div>List Price<input id="price" type="text" name="price" style="width:40px" onblur=""
										onkeypress="" class="shadowBox grayText"size="10" maxlength="7" value="${serviceOfferingDto.price}"/></div>
			<div>Daily Limit Of Service Offerings <input id="dailyLimit" type="text" name="dailyLimit" style="width:40px" onblur=""
										onkeypress="" class="shadowBox grayText"size="10" maxlength="7" value="${serviceOfferingDto.dailyLimit}"/></div>
					<div>Radius <input id="serviceRadius" type="text" name="serviceRadius" style="width:40px" onblur=""
										onkeypress="" class="shadowBox grayText"size="10" maxlength="7" value="${serviceOfferingDto.serviceRadius}"/></div>	
											
														
					<div>Select your availibility on arriving at customer appointments</div>					
										
										  <c:forEach var="i" begin="0" end="6" varStatus="status">
										                <c:set var="allInd" value=""/>  
										                <c:set var="earlyMorningInd" value=""/>  
										                <c:set var="morningInd" value=""/>  
										                <c:set var="afterNoonInd" value=""/>  
										                <c:set var="eveningInd" value=""/>  
										              <c:forEach items="${availabilityList}" var="availiability" varStatus="avStatus">
										            
										            <c:set var="inc" value="${i}"/> 
												 <c:if test="${avStatus.count-1 == inc }">
												 <c:set var="avallInd" value="${availiability.allInd}"/>  
										                <c:set var="avearlyMorningInd" value="${availiability.earlyMorningInd}"/>  
										                <c:set var="avmorningInd" value="${availiability.morningInd}"/>  
										                <c:set var="avafterNoonInd" value="${availiability.afterNoonInd}"/>  
										                <c:set var="aveveningInd"  value="${availiability.eveningInd}"/>  
												 </c:if>
										</c:forEach>    
										                  <div>
										                  <c:if test="${i == 0 }">
										                  		Monday
<input type="hidden"id="day${i}" name="availabilityList[${status.count}].day" value="Monday" />
										                  		
										                  </c:if>
										                  <c:if test="${i == 1 }">
										                  		Tuesday
	<input type="hidden"id="day${i}" name="availabilityList[${status.count}].day" value="Tuesday" />
										                  		
										                  </c:if>
										                  <c:if test="${i == 2 }">
										                  		Wednesday
	<input type="hidden"id="day${i}" name="availabilityList[${status.count}].day" value="Wednesday" />
										                  </c:if>
										                  <c:if test="${i == 3 }">
										                  		Thursday
		<input type="hidden"id="day${i}" name="availabilityList[${status.count}].day" value="Thursday" />
										                  </c:if>
										                  	<c:if test="${i == 4 }">
										                  		Friday
		<input type="hidden"id="day${i}" name="availabilityList[${status.count}].day" value="Friday" />
										                  </c:if>
										                  <c:if test="${i == 5}">
										                  		Saturday
		<input type="hidden"id="day${i}" name="availabilityList[${status.count}].day" value="Saturday" />
										                  </c:if>
										                   <c:if test="${i == 6}">
										                  		Sunday
		<input type="hidden"id="day${i}" name="availabilityList[${status.count}].day" value="Sunday" />
										                  </c:if>
									                  
		<input type="radio" id="allInd${i}" name="availabilityList[${status.count}].allInd" <c:if test="${avallInd == 'on' }">checked="checked" </c:if>  />All     
   		<input type="checkbox" id="earlyMorningInd${i}" <c:if test="${avearlyMorningInd == 'on' }">checked </c:if>name="availabilityList[${status.count}].earlyMorningInd"  />Early morning(before 8 AM)
		<input type="checkbox" id="morningInd${i}" name="availabilityList[${status.count}].morningInd" <c:if test="${avmorningInd == 'on' }">checked </c:if> />Morning(8-12)
		<input type="checkbox" id="afterNoonInd${i}" name="availabilityList[${status.count}].afterNoonInd" <c:if test="${avafterNoonInd == 'on' }">checked </c:if> />Afternoon(12-4)
		<input type="checkbox" id="eveningInd${i}" name="availabilityList[${status.count}].eveningInd" <c:if test="${avafterNoonInd == 'on' }">checked </c:if> />Evening(4-8)
															</c:forEach> 
															
															     
										                        <div>
										                  
										       Offering Status
										       <input type="checkbox" id="offeringStatusOn"  onclick="offerStatus('ON');" <c:if test="${offeringStatus == 'ON' }">checked </c:if> /> ON
										        <input type="checkbox" id="offeringStatusOff" onclick="offerStatus('OFF');" <c:if test="${offeringStatus == 'OFF' }">checked </c:if> />OFF
										        <input type="checkbox" id="offeringStatusPause" onclick="offerStatus('PAUSE');" <c:if test="${offeringStatus == 'PAUSE' }">checked </c:if> /> PAUSE
										          <input type="hidden" id ="offeringStatus" name="offeringStatus" value=""/>
										                        
									       <input type="button"
											onclick="javascript:saveServiceOfferings()"
											theme="simple" value="Submit" />
									</div>
										
         	
		</form>
							</div>
					<!-- This hyperlink is added to display service offering history -->
					<c:if test="${null!= serviceOfferingDto.offeringId && not empty serviceOfferingDto.offeringId}">		
						<div id="serviceOfferingHistory"> 
						  <a href="manageServiceOfferingsAction_viewhistory.action?offeringId=${serviceOfferingDto.offeringId}" target="_blank">View Change History</a>
						</div>
					</c:if>	
					</div>

					
				</div>
				<tiles:insertDefinition name="blueprint.base.footer" />
			</div>
		
		
		
		
	</body>
</html>
