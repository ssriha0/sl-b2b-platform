<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
<body>
<!-- <body onload="displayBanner()">
   <div id="bannerDiv" class="bannerDiv" style="display:none; width:100%;position:relative">
	    <span class="spanText" id="spanText"></span>
	    <a href="javascript:void(0);" onclick="removeBanner();" style="text-decoration: underline;"> Dismiss </a>
   </div> -->
<div id="w9historyDiv">
<c:if test="${not empty w9History }">
<table class="auditWorkflow auditHistory" cellspacing="0px">

<tr>
	<td style=" font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Version No.
	</td>
	<td style=" font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Legal Business Name
	</td>
	<td style=" font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		DBA
	</td>
	<td style="font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Tax Payer ID
	</td>
	<td style="font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Tax Status
	</td>
	<td style="font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Tax Exempt
	</td>
	<td style="font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Address
	</td>
	<td style="font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Modified Date
	</td>
	<td style="font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Modified By
	</td>
	<td style="font-size: 12px;font-weight: bold;background: #666; color: #FFF; padding: 5px; text-align: left; border-right: 2px solid #FFFFFF;">
		Date Of Birth
	</td>
</tr>

<c:set var="start" value="${fn:length(w9History)}"></c:set>
<c:forEach items="${w9History}" var="w9item_version" begin="0" step="1" end="${start}" varStatus="totalcount">
	<c:forEach items="${w9item_version.value}" var="w9item">
		<tr>
			<td style="border-bottom: 1px solid #ccc;text-align: center;">
				<c:set var="totalcount" value="${totalcount.count}"></c:set>
      			<c:set var="version" value="${start-totalcount+1}" scope="page"></c:set>
     			<c:out value="${version}"/>
     		</td>
			<td style="border-bottom: 1px solid #ccc;">
				<c:if test="${not empty w9item.legalBusinessName}">
					${w9item.legalBusinessName}
				</c:if>		
				<c:if test="${empty w9item.legalBusinessName}">
					&nbsp;
				</c:if>					
			</td>
			<td style="border-bottom: 1px solid #ccc;">
				<c:if test="${not empty w9item.doingBusinessAsName}">
					${w9item.doingBusinessAsName}
				</c:if>
				<c:if test="${empty w9item.doingBusinessAsName}">
					&nbsp;
				</c:if>
			</td>
			<td style="border-bottom: 1px solid #ccc;">
				<c:if test="${not empty w9item.ein}">
					${w9item.ein}
				</c:if>			
				<c:if test="${empty w9item.ein}">
					&nbsp;
				</c:if>
			</td>
			<td style="border-bottom: 1px solid #ccc;">
				<c:if test="${not empty w9item.taxStatus.descr}">
					${w9item.taxStatus.descr}
				</c:if>
				<c:if test="${empty w9item.taxStatus.descr}">
					&nbsp;
				</c:if>			
			</td>
			<td style="border-bottom: 1px solid #ccc;">
				<c:if test="${not empty w9item.isTaxExempt}">
					${w9item.isTaxExempt}
				</c:if>	
				<c:if test="${empty w9item.isTaxExempt}">
					&nbsp;
				</c:if>						
			</td>
			<td style="border-bottom: 1px solid #ccc;">
					${w9item.address.street1} ${w9item.address.aptNo}<br>
					${w9item.address.street2}
					<c:if test="${not empty w9item.address.street2 }">
					<br>
					</c:if>
					${w9item.address.city}, ${w9item.address.state} ${w9item.address.zip}
			</td style="border-bottom: 1px solid #ccc;">
			<td style="border-bottom: 1px solid #ccc;">
				<c:if test="${not empty w9item.modifiedDate}">
					<fmt:formatDate value="${w9item.modifiedDate}" pattern="MMM dd, yyyy h:mm a" />
				</c:if>	
				<c:if test="${empty w9item.modifiedDate}">
					&nbsp;
				</c:if>				
			</td>
			<td style="border-bottom: 1px solid #ccc;">
				<c:if test="${not empty w9item.modifiedBy}">
					${w9item.modifiedBy}
				</c:if>		
				<c:if test="${empty w9item.modifiedBy}">
					&nbsp;
				</c:if>						
			</td>
			<c:if test="${w9item.dateOfBirth == null}">
				<td style="border-bottom: 1px solid #ccc;text-align: center;">N/A</td>
			</c:if>	
			<c:if test="${w9item.dateOfBirth != null}">
				<td style="border-bottom: 1px solid #ccc;text-align: center;"><fmt:formatDate value="${w9item.dateOfBirth}" pattern="MM/dd/yyyy" /></td>
			</c:if>
		</tr>
	</c:forEach>
</c:forEach>
</table>
</c:if>
<c:if test="${empty w9History }">
	You have no W9 version history.
</c:if>

</div>
</body>