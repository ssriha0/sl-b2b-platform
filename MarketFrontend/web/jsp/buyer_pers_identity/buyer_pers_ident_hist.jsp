<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<head>
<title>ServiceLive - Buyer Personal Identification Information History</title>
</head>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
		.ff3 .bannerDiv{margin-left:0px;}  
		.ff2 .bannerDiv{margin-left:0px;}
		.ie7 .bannerDiv{margin-left:0px;position: relative;}
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
		<!-- SL-18907 Code changes to fix Browser compatibility message displayed in not 100% in View Audit History/Notes Page-->
<body>
<!-- <body onload="displayBanner();">
 <div id="bannerDiv" class="bannerDiv" style="display:none;width: 100%;">
	     <span class="spanText" id="spanText"></span>
	     <a href="javascript:void(0);" onclick="removeBanner();" style="text-decoration: underline;"> Dismiss </a>
  </div> -->
<div style="padding-top:15px;">
 
<c:if test="${not empty piiHistory}">
<table class="auditWorkflow auditHistory" style="width:100%; overflow: scroll;" cellspacing="0px">
<tr>
	<td style="font-size:12px;font-weight: bold; background: #666; color: #FFF; padding: 2px; text-align: left; border-right: 2px solid #FFFFFF;">
		Version No.
	</td>
	<td style="font-size:12px;font-weight: bold; background: #666; color: #FFF; padding: 2px; text-align: left; border-right: 2px solid #FFFFFF;">
		Legal Business Name
	</td>
	<td style="font-size:12px;font-weight: bold; background: #666; color: #FFF; padding: 2px; text-align: center; border-right: 2px solid #FFFFFF;">
		ID Type
	</td>
	<td style="font-size:12px;font-weight: bold; background: #666; color: #FFF; padding: 2px; text-align: center; border-right: 2px solid #FFFFFF;">
		Country Of Issuance
	</td>
	<td style="font-size:12px;font-weight: bold; background: #666; color: #FFF; padding: 2px; text-align: center; border-right: 2px solid #FFFFFF;">
		ID Number
	</td>
	<td style="font-size:12px;font-weight: bold; background: #666; color: #FFF; padding: 2px; text-align: center; border-right: 2px solid #FFFFFF;">
		Date of Birth
	</td>
	<td style="font-size:12px;font-weight: bold; background: #666; color: #FFF; padding: 2px; text-align: left; border-right: 2px solid #FFFFFF;">
		Modified Date
	</td>
	<td style="font-size:12px;font-weight: bold; background: #666; color: #FFF; padding: 2px; text-align: left; border-right: 2px solid #FFFFFF;">
		Modified By
	</td>
</tr>


<c:set var="start" value="${fn:length(piiHistory)}"></c:set>
<c:forEach items="${piiHistory}" var="history"  begin="0" step="1" end="${start}" varStatus="totalcount">
<tr>
			<td style="border-bottom: 1px solid #ccc; text-align: center;" >
			<c:set var="totalcount" value="${totalcount.count}"></c:set>
      <c:set var="j" value="${start-totalcount+1}" scope="page"></c:set>
     <c:out value="${j}"/>
</td>
			<td style="border-bottom: 1px solid #ccc; text-align: left;">
				<c:if test="${not empty history.businessName}">
					${history.businessName}
				</c:if>
				<c:if test="${empty history.businessName}">
					&nbsp;
				</c:if>			
			</td>
			<td style="border-bottom: 1px solid #ccc; text-align: center;">
				<c:if test="${not empty history.idType}">
					${history.idType}
				</c:if>
				<c:if test="${empty history.idType}">
					&nbsp;
				</c:if>				
			</td>
			<td style="border-bottom: 1px solid #ccc; text-align: center;">
				<c:if test="${not empty history.country}">
					${history.country}
				</c:if>
				<c:if test="${empty history.country}">
					&nbsp;
				</c:if>				
			</td>
			<td style="border-bottom: 1px solid #ccc; text-align: center;">
				<c:if test="${not empty history.idNumber}">
					${history.idNumber}
				</c:if>
				<c:if test="${empty history.idNumber}">
					&nbsp;
				</c:if>			
			</td>
			<c:if test="${history.dateOfBirth==null}">
			<td style="border-bottom: 1px solid #ccc; text-align: center;">
			N/A
			</td>
			</c:if>
			<c:if test="${history.dateOfBirth!=null}">
			<td style="border-bottom: 1px solid #ccc; text-align: center;">
			${history.dateOfBirth}
			</td>
			</c:if>
			<td style="border-bottom: 1px solid #ccc; text-align: center;">
				<c:if test="${not empty history.createdDate}">
					<fmt:formatDate value="${history.createdDate}" pattern="MMM dd, yyyy h:mm a" />
				</c:if>
				<c:if test="${empty history.createdDate}">
					&nbsp;
				</c:if>				
			</td>
			<td style="border-bottom: 1px solid #ccc; text-align: center;">
				<c:if test="${not empty history.modifiedBy}">
					${history.modifiedBy}
				</c:if>
				<c:if test="${empty history.modifiedBy}">
					&nbsp;
				</c:if>			
			</td>
</tr>

</c:forEach>
</table>
</c:if>
<c:if test="${empty piiHistory}">
<div style="padding-top:15px;">
	You have no Personal Identification Information History.
	</div>
</c:if>

</div>
</body>