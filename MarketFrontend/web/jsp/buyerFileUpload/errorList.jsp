<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<!-- acquity: modified meta tag to set charset -->
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<title><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.page.title" /></title>

	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
	<!--[if IE]>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
	<![endif]-->

	<!-- acquity: here is the new js, please minify/pack the toolbox and rename as you wish -->
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
	<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>	
</head>
<body class="tundra acquity">
    
	<div id="page_margins">
		<div id="page">
		<!-- START HEADER -->
		<div id="headerShort">
			<tiles:insertDefinition name="newco.base.topnav"/>
			<tiles:insertDefinition name="newco.base.blue_nav"/>
			<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
		</div>
		<!-- END HEADER -->
<div id="hpWrap" class="clearfix">
	<div id="hpContent" class="pdtop20" style="width:auto">
		<div id="hpIntro">
			<c:if test="${not empty soDocDTO}">	
				<img class="right" src="${contextPath}/buyerFileUploadAction_buyerLogoDisplay.action"	alt="default Image"/>
			</c:if>
			
			<h2><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.label.erroredSO" /></h2>
			<p><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.label.descr" /></p>
		</div>
		<a href="<s:url action='buyerFileUploadAction.action'/>">&laquo; <fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.label.backToImportTool" /></a>
		<br /><br />
		<div class="buyerUploadError clearfix">
			<table class="buyerUploadError" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<th colspan="5">&nbsp;</th>
					<th colspan="5"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskOne" /></th>
					<th colspan="5"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskTwo" /></th>
					<th colspan="5"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskThree" /></th>
					<th colspan="9"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partOne" /></th>
					<th colspan="9"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partTwo" /></th>
					<th colspan="9"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partThree" /></th>
					<th colspan="15"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.customerInformation" /></th>
					<th colspan="10"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.soDetails" /></th>
					<th colspan="6"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.documents" /></th>
					<th colspan="11"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerCustomReference" /></th>
					<th colspan="2"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.pricing.terms" /></th>
					<th colspan="1">&nbsp;</th>
				</tr>
				<tr>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.recordId" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.mainServiceCategory" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.title" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.zipCode" /></th>					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.state" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.category" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.subCategory" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.skill" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskComments" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.category" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.subCategory" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.skill" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskComments" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.category" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.subCategory" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.skill" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskComments" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.manufacturer" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partName" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.modelNumber" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.quantity" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundTracking" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundTracking" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.manufacturer" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partName" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.modelNumber" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.quantity" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundTracking" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundTracking" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.manufacturer" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partName" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.modelNumber" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.quantity" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundTracking" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundTracking" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.part.Material" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.locationType" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.businessName" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.firstName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.lastName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.streetName1" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.streetName2" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.apt" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.city" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.email" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.phoneNumber" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.phoneType" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.altPhoneNumber" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.altPhoneType" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.fax" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.locationNotes" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.overview" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerTerms" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.specialInst" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.dateType" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.date" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.time" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.date" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.time" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.provToConfirm" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.attachment" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.attachment" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.attachment" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.brandingInfo" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef1" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef2" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef3" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef4" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef5" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef6" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef7" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef8" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef9" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef10" /></th>	
					

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.laborLimit" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partLimit" /></th>
				
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.templateId" /></th>
				</tr>

				<c:if test="${not empty errorRecordList}">
					<c:forEach items="${errorRecordList}" var="errorRecord">
						<tr><td class="errormsg" colspan="100">${errorRecord.errorNotes}</td></tr>
						<tr>
							<td>${errorRecord.rowId}</td>
							<td>${errorRecord.mainServiceCategory}</td>
							<td>${errorRecord.title}</td>
							<td>${errorRecord.zip}</td>
							<td>${errorRecord.state}</td>
														
							<td>${errorRecord.taskOneName}</td>
							<td>${errorRecord.taskOneCategory}</td>
							<td>${errorRecord.taskOneSubCategory}</td>
							<td>${errorRecord.taskOneSkill}</td>
							<td>${errorRecord.taskOneComments}</td>
	
							<td>${errorRecord.taskTwoName}</td>
							<td>${errorRecord.taskTwoCategory}</td>
							<td>${errorRecord.taskTwoSubCategory}</td>
							<td>${errorRecord.taskTwoSkill}</td>
							<td>${errorRecord.taskTwoComments}</td>
		
							<td>${errorRecord.taskThreeName}</td>
							<td>${errorRecord.taskThreeCategory}</td>
							<td>${errorRecord.taskThreeSubCategory}</td>
							<td>${errorRecord.taskThreeSkill}</td>
							<td>${errorRecord.taskThreeComments}</td>
	
							<td>${errorRecord.partOneManufacturer}</td>	
							<td>${errorRecord.partOneName}</td>	
							<td>${errorRecord.partOneModel}</td>	
							<td>${errorRecord.partOneDesc}</td>
							<td>${errorRecord.partOneQuantity}</td>	
							<td>${errorRecord.partOneInboundCarrier}</td>	
							<td>${errorRecord.partOneInboundTrackingId}</td>	
							<td>${errorRecord.partOneOutboundCarrier}</td>	
							<td>${errorRecord.partOneOutboundTrackingId}</td>
	
							<td>${errorRecord.partTwoManufacturer}</td>	
							<td>${errorRecord.partTwoName}</td>	
							<td>${errorRecord.partTwoModel}</td>	
							<td>${errorRecord.partTwoDesc}</td>
							<td>${errorRecord.partTwoQuantity}</td>	
							<td>${errorRecord.partTwoInboundCarrier}</td>	
							<td>${errorRecord.partTwoInboundTrackingId}</td>	
							<td>${errorRecord.partTwoOutboundCarrier}</td>	
							<td>${errorRecord.partTwoOutboundTrackingId}</td>
	
							<td>${errorRecord.partThreeManufacturer}</td>	
							<td>${errorRecord.partThreeName}</td>	
							<td>${errorRecord.partThreeModel}</td>	
							<td>${errorRecord.partThreeDesc}</td>
							<td>${errorRecord.partThreeQuantity}</td>	
							<td>${errorRecord.partThreeInboundCarrier}</td>	
							<td>${errorRecord.partThreeInboundTrackingId}</td>	
							<td>${errorRecord.partThreeOutboundCarrier}</td>	
							<td>${errorRecord.partThreeOutboundTrackingId}</td>
		
							<td>${errorRecord.partMaterial}</td>	
							<td>${errorRecord.locationType}</td>	
							<td>${errorRecord.businessName}</td>	
							<td>${errorRecord.firstName}</td>
							<td>${errorRecord.lastName}</td>
							<td>${errorRecord.street1}</td>
							<td>${errorRecord.street2}</td>
							<td>${errorRecord.aptNo}</td>	
							<td>${errorRecord.city}</td>							
							<td>${errorRecord.email}</td>
							<td>${errorRecord.phone}</td>
							<td>${errorRecord.phoneType}</td>
							<td>${errorRecord.altPhone}</td>
							<td>${errorRecord.altPhoneType}</td>
							<td>${errorRecord.fax}</td>
		
							<td>${errorRecord.soLocNotes}</td>					
							<td>${errorRecord.overview}</td>
							<td>${errorRecord.buyerTc}</td>
							<td>${errorRecord.specialInst}</td>
							<td>${errorRecord.serviceDateType}</td> 
							<td>${errorRecord.serviceDate}</td>
							<td>${errorRecord.serviceTime}</td>
							<td>${errorRecord.serviceEndDate}</td>
							<td>${errorRecord.serviceEndTime}</td>
							<td>${errorRecord.provConfTimeInd}</td>
		
							<td>${errorRecord.attachment1}</td>
							<td>${errorRecord.desc1}</td>
							<td>${errorRecord.attachment2}</td>
							<td>${errorRecord.desc2}</td>
							<td>${errorRecord.attachment3}</td>
							<td>${errorRecord.desc3}</td>
		
							<td>${errorRecord.brandingInfo}</td>
							<td>${errorRecord.custRef1Value}</td>	
							<td>${errorRecord.custRef2Value}</td>	
							<td>${errorRecord.custRef3Value}</td>	
							<td>${errorRecord.custRef4Value}</td>
							<td>${errorRecord.custRef5Value}</td>	
							<td>${errorRecord.custRef6Value}</td>	
							<td>${errorRecord.custRef7Value}</td>	
							<td>${errorRecord.custRef8Value}</td>	
							<td>${errorRecord.custRef9Value}</td>
							<td>${errorRecord.custRef10Value}</td>	
						
							<td>${errorRecord.laborSpendLimit}</td>
							<td>${errorRecord.partSpendLimit}</td>				
							<td>${errorRecord.templateId}</td> 
						</tr>
					</c:forEach>
				</c:if>
				
				<tr><td class="blank" colspan="110">&nbsp;</td></tr>
				<tr>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.recordId" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.mainServiceCategory" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.title" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.zipCode" /></th>					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.state" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.category" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.subCategory" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.skill" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskComments" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.category" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.subCategory" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.skill" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskComments" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.category" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.subCategory" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.skill" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskComments" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.manufacturer" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partName" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.modelNumber" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.quantity" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundTracking" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundTracking" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.manufacturer" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partName" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.modelNumber" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.quantity" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundTracking" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundTracking" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.manufacturer" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partName" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.modelNumber" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.quantity" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.inboundTracking" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundCarrier" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.outboundTracking" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.part.Material" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.locationType" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.businessName" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.firstName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.lastName" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.streetName1" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.streetName2" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.apt" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.city" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.email" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.phoneNumber" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.phoneType" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.altPhoneNumber" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.altPhoneType" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.fax" /></th>
					
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.locationNotes" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.overview" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerTerms" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.specialInst" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.dateType" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.date" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.time" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.date" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.time" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.provToConfirm" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.attachment" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.attachment" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.attachment" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.description" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.brandingInfo" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef1" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef2" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef3" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef4" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef5" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef6" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef7" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef8" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef9" /></th>	
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerRef10" /></th>	

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.laborLimit" /></th>
					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partLimit" /></th>

					<th><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.templateId" /></th>
				</tr>

				<tr>
					<th colspan="5">&nbsp;</th>
					<th colspan="5"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskOne" /></th>
					<th colspan="5"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskTwo" /></th>
					<th colspan="5"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.taskThree" /></th>
					<th colspan="9"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partOne" /></th>
					<th colspan="9"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partTwo" /></th>
					<th colspan="9"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.partThree" /></th>
					<th colspan="15"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.customerInformation" /></th>
					<th colspan="10"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.soDetails" /></th>
					<th colspan="6"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.documents" /></th>
					<th colspan="11"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.buyerCustomReference" /></th>
					<th colspan="2"><fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.header.pricing.terms" /></th>
					<th colspan="1">&nbsp;</th>
				</tr>
			</table>
		</div>
		<a href="<s:url action='buyerFileUploadAction.action'/>">&laquo; <fmt:message bundle="${serviceliveCopyBundle}" key="buyerFileUpload.label.backToImportTool" /></a>
		
	</div>
</div>
	<!-- START FOOTER -->
		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
	<!-- END FOOTER -->

	
	</div></div><!-- acquity: empty divs to ajax the modal content into -->
	<div id="reportProblem" class="jqmWindow"></div>
	<div id="serviceFinder" class="jqmWindow"></div>
	<div id="modal123" class="jqmWindowSteps"></div>
	<div id="zipCheck" class="jqmWindow"></div>
</body>
</html>
