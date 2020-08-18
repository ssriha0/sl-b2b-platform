<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
 
 <%
Boolean incSpendLimit = (Boolean)session.getAttribute("incSpendLimit");
%>
 
<!-- NEW MODULE/ WIDGET-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
     <script type="text/javascript">
function expandPriceBuyer(id,path){
var hidId="group"+id;
var testGroup=document.getElementById(hidId).value;
var divId="priceBuyer"+id;
var bodyId="priceBuyer_menu_body"+id;
if(testGroup=="menu_list"){
jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}else{
jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}
var ob=document.getElementById('priceBuyerImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('priceBuyerImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('priceBuyerImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}

</script>

</head>
<body>
<input type="hidden" name="displayTax" id="displayTax" value="${displayTax}"/>
<input type="hidden" name="partsTaxPer" id="partsTaxPer" value="${partsTaxPercentage}">
<input type="hidden" name="laborTaxPer" id="laborTaxPer" value="${laborTaxPercentage}"/>
<c:set var="divName" value="priceBuyer"/>
 <c:set var="divName" value="${divName}${summaryDTO.id}"/>
 <c:set var="group" value="group"/>
 <c:if test="${checkGroup==group}">
<c:set var="groupVal" value="menu_list"/>
<c:set var="bodyClass" value="menu_body"/>
<c:set var="headClass" value="menu_head"/>
</c:if>
<c:if test="${checkGroup!=group}">
<c:set var="groupVal" value="menugroup_list"/>
<c:set var="bodyClass" value="menugroup_body"/>
<c:set var="headClass" value="menugroup_head"/>
</c:if>
<div id="${divName}" class="${groupVal }">
<c:set var="bodyName" value="priceBuyer_menu_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
  <c:set var="priceBuyerImage" value="priceBuyerImage"/>
    <c:set var="priceBuyerImage" value="${priceBuyerImage}${summaryDTO.id}"/>
  <p class="${headClass}" onclick="expandPriceBuyer('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${priceBuyerImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Service Order Pricing</p>

    <div id="${bodyName}" class="${bodyClass}">
    
    <p>
    <c:if test="${SecurityContext.roleId == 3 || SecurityContext.slAdminInd == true}">
	  <div id="priceHistory"  style="position: relative;float:right;right:15px;"> 
	     <a href="javascript:void(0)"  onclick="getTabForId('Price History');" style="text-decoration: underline;">View Price History</a>
	</div>
	<br/> 
    </c:if>
    <c:choose>
    <c:when test="${(summaryDTO.priceModel == 'ZERO_PRICE_BID' && summaryDTO.status == 110)}">
	You have requested that the providers who received this service order submit a bid for pricing.
	</c:when><c:otherwise>
	Service order pricing outlined below.
	</c:otherwise>
	</c:choose>
	<c:if test="${(incSpendLimit == true || summaryDTO.taskLevelPriceInd == false || SecurityContext.slAdminInd == true) && (summaryDTO.status == 150 || summaryDTO.status == 155 || summaryDTO.status == 170)}">
	You may <a href="javascript:void(0)" onclick="calculateSpendLimit();">increase your maximum price </a> using the button below.
	</c:if>
	</p>

<style type="text/css">
.price-breakdown { width: 300px;}
.price-breakdown dt {
	font-weight: bold;
	display: block;
	float: left;
	width: 180px;
	text-align: right;
	padding: 3px 0px;
}

.price-breakdown dd {
	padding-left: 20px;
	float: right;
	text-align: right;
	width: 120px;
	padding: 3px 0px;
}

.price-breakdown dd.total, .price-breakdown dt.total  { border-top: 1px solid #CCC; margin-bottom: 10px;}

#pricing .left {
	border-right: 1px solid silver;
	padding-right: 30px;
	margin-right: 30px;
}

#pricing .last { border-right: 0px; padding-right: 0px; margin-right: 0px;}

#pricing .top { padding-bottom: 20px; margin-bottom: 20px; border-bottom: 1px dotted silver;}

</style>
	<c:set var="routed_status" scope="page" value="110" />
	<div id="pricing" class="clearfix">
		<div class="clearfix top">
	<table width="80%" cellspacing="0">
			<c:choose>
				<c:when test="${summaryDTO.priceModel == 'ZERO_PRICE_BID' && summaryDTO.status == routed_status}">
					<tr>	
						<c:choose>		
							<c:when test="${summaryDTO.sealedBidInd == true}">
								<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;"><strong>Pricing Type</strong></td>
							    <td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;">Sealed Bid Request</td>
							</c:when>
							<c:otherwise>
							 	<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;"><strong>Pricing Type</strong></td>
							 	<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;">Bid Request</td>
							</c:otherwise>
						</c:choose>
					</tr>
					<tr>
						<td style="padding-bottom: 12px; padding-top: 12px;border-bottom: 1px solid #ccc;">
							<strong>+ Posting Fee</strong>
						</td>
						<td style="padding-bottom: 12px;padding-top: 12px;border-bottom: 1px solid #ccc;">
							$<fmt:formatNumber value="${summaryDTO.postingFee}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
						</td>
					</tr>
					<tr>
						<td style="padding-bottom: 12px; padding-top: 12px;border-bottom: 1px solid #ccc;">
							<strong>Buyer Total</strong>
						</td>
						<td style="padding-bottom: 12px;padding-top: 12px;border-bottom: 1px solid #ccc;">
							$<fmt:formatNumber value="${summaryDTO.postingFee}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
						</td>					
					</tr>					
			</c:when>
			<c:otherwise>
		<tr>
			<td width="60%" style="border-bottom: 1px solid #ccc;">
			</td>
			<td align="right" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;">
				<strong>Maximum Price</strong>
			</td>
			<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
			<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;" align ="right">
			<strong>&nbsp;&nbsp;&nbsp;&nbsp;Final Price</strong>
			</td>
			</c:if>
		</tr>
		<tr>
			<td align="left" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			<strong>Labor</strong>
			</td>
			<td align="right" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			
			<c:choose>
			<c:when test="${summaryDTO.status == 105 || summaryDTO.status == 125}">
				$0.00
			</c:when>
			<c:otherwise>
				$<fmt:formatNumber value="${summaryDTO.soTaskMaxLabor / (1 + laborTaxPercentage)}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</c:otherwise>
			</c:choose>			
			</td>
			<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
			<td align="right" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			$<fmt:formatNumber value="${summaryDTO.soFinalMaxLabor / (1 + laborTaxPercentage)}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</td>
			</c:if>
		</tr>
		<c:set var="laborTaxTotalUI" value="0.00"></c:set>
					<c:if test="${ displayTax }">
						<tr>	
							<td  align="left" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" ><strong>Labor Tax: (<fmt:formatNumber value="${laborTaxPercentage * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%)</strong></td>
							<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
								$<fmt:formatNumber value="${(summaryDTO.soTaskMaxLabor * laborTaxPercentage) / (1 + laborTaxPercentage)}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
							</td>
							<td align="right">
							
								<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 180}">
									<c:set var="laborTaxTotalUI" value="${(summaryDTO.soFinalMaxLabor * laborTaxPercentage) / (1 + laborTaxPercentage)}"></c:set>
									<p style="border-bottom: 1px solid #ccc;padding: 10px 0 10px 0;"><fmt:formatNumber value="${(summaryDTO.soFinalMaxLabor * laborTaxPercentage) / (1 + laborTaxPercentage)}"	type="currency" minFractionDigits="2" maxFractionDigits="2" /></p>
								</c:if>
								<c:if test="${summaryDTO.status == 120}">
									<p style="border-bottom: 1px solid #ccc;padding: 10px 0 10px 0;"><fmt:formatNumber value="0.00" type="currency"	minFractionDigits="2" maxFractionDigits="2" /></p>
								</c:if>
							</td>
						</tr>
					</c:if>
		<tr>
		
			<td align="left" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			<strong>Materials</strong>
			</td>
			<td align="right" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			<c:choose>
			<c:when test="${summaryDTO.status == 105 || summaryDTO.status == 125}">
				$0.00
			</c:when>
			<c:otherwise>
			$<fmt:formatNumber value="${summaryDTO.partsSpendLimit  / (1 + partsTaxPercentage)}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</c:otherwise>
			</c:choose>
			</td>
			<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
			<td align="right" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			$<fmt:formatNumber value="${summaryDTO.finalPartsPrice / (1 + partsTaxPercentage)}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</td>
			</c:if>
		</tr>
		<c:set var="materialsTaxTotalUI" value="0.00"></c:set>
					<c:if test="${ displayTax }">
						<tr>
							<td  align="left" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
								<strong>Materials Tax: (<fmt:formatNumber value="${partsTaxPercentage * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%)</strong>
							</td>
							<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
								$<fmt:formatNumber value="${(summaryDTO.partsSpendLimit * partsTaxPercentage) / (1 + partsTaxPercentage)}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
							</td>
							<td align="right">
								<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 180}">
									<c:set var="materialsTaxTotalUI" value="${(summaryDTO.finalPartsPrice * partsTaxPercentage) / (1 + partsTaxPercentage)}"></c:set>
									<p style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0"><fmt:formatNumber value="${(summaryDTO.finalPartsPrice * partsTaxPercentage) / (1 + partsTaxPercentage)}"	type="currency" minFractionDigits="2" maxFractionDigits="2" /></p>
								</c:if>
								<c:if test="${summaryDTO.status == 120}">
									<p style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" ><fmt:formatNumber value="0.00" type="currency"	minFractionDigits="2" maxFractionDigits="2" /></p>
								</c:if>
							</td>
						</tr>
					</c:if>
		<c:set  var="finalPartsPriceTotal" value="0"></c:set>
		<c:if test="${!empty summaryDTO.invoiceParts}">
						
					<c:forEach items="${summaryDTO.invoiceParts}" var="parts">
					
																				<c:if test="${parts.partStatus == 'Installed'}">
					
					<c:set  var="finalPartsPriceTotal" value="${finalPartsPriceTotal+parts.finalPrice}"></c:set>
					</c:if>
					</c:forEach>
		</c:if>
		<c:if test="${summaryDTO.buyerID == '3000'}">
		<tr>
		
			<td align="left" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			<strong>Parts</strong>
			</td>
		
		
			<c:choose>
			<c:when test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
			<td align="right" style="d 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;"></td>

			<td align="right" style="d 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			$<fmt:formatNumber value="${finalPartsPriceTotal}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</td>
			</c:when>
			
			<c:otherwise>
			<td align="right" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
			$<fmt:formatNumber value="0.00"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</td>
			</c:otherwise>
			
			</c:choose>
			
		</tr>
		</c:if>
		
		<tr>
		<c:forEach var="task" items="${summaryDTO.taskList}">
		<c:if test="${task.taskType==1 && (summaryDTO.status == 110 || summaryDTO.status == 150 || summaryDTO.status == 155 || summaryDTO.status == 100 || summaryDTO.status == 170 || summaryDTO.status == 165)}">
		<tr>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="left">
				<strong>Permits (Customer Pre-paid)</strong>
		</td>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="right">
						<c:choose>
						<c:when test="${summaryDTO.status == 165}">
							$<fmt:formatNumber value="0.00" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
						</c:when>
						<c:otherwise>
							$<fmt:formatNumber value="${task.sellingPrice}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
						</c:otherwise>
						</c:choose>							
		</td>
						<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 180}">
						<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="right">
							$<fmt:formatNumber value="${task.finalPrice}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
						</td>
		</c:if>
						<c:if test="${summaryDTO.status == 120}">
						<td align="right" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
							$<fmt:formatNumber value="0.00"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
						</td>
		</c:if>
					</tr>
			</c:if>
		</c:forEach>		
		</tr>
		<c:if test="${summaryDTO.status == 110 || summaryDTO.status == 155 || summaryDTO.status == 150 || summaryDTO.status == 165 || summaryDTO.status == 130 || summaryDTO.status == 170}">
		
		
		<tr>
			<td align="right" style="padding-bottom: 12px; padding-top: 12px;">
				<strong style="font-size: small">Buyer Total</strong>
				<strong style="border-bottom: 1px solid #ccc;padding-bottom: 12px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</strong>
				<strong style="border-bottom: 1px solid #ccc;padding-bottom: 12px;">+ Posting Fee</strong>
			</td>
			<td align="right" style="padding-bottom: 12px;padding-top: 12px;border-bottom: 1px solid #ccc;">
				$<fmt:formatNumber value="${summaryDTO.postingFee}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</td>			
		</tr>
		<tr>
			<td align="right" style="padding-bottom: 12px;padding-top: 12px;">
				<strong>Posting Total</strong>
			</td>
			<td align="right" style="padding-bottom: 12px;padding-top: 12px;background-color: #C9E6F4;">
					$<fmt:formatNumber value="${summaryDTO.totalSpendLimit+summaryDTO.postingFee}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />		
			</td>	
		</tr>
		</c:if>
	<c:if test="${(summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180)}">
		<c:if test="${summaryDTO.taskLevelPriceInd == true || summaryDTO.buyerID == '3333' || summaryDTO.buyerID == '7777' || ( summaryDTO.taskLevelPriceInd == false && summaryDTO.buyerID == '3000')}">
		<c:forEach var="task" items="${summaryDTO.taskList}">
		<c:if test="${task.taskType==1}">
		<tr>
			<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="left">
				<strong>Permits (Customer Pre-paid)</strong>
					<strong>-</strong>
					<strong>${task.permitTaskDesc}</strong>
			</td>
						<c:choose>
						<c:when test="${summaryDTO.status == 165 || summaryDTO.status == 120 || summaryDTO.status == 125 || summaryDTO.status == 130}">
							<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="right">
							$<fmt:formatNumber value="${task.finalPrice}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
							</td>
						</c:when>
						<c:otherwise>
							<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="right">
							$<fmt:formatNumber value="${task.sellingPrice}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
							</td>
						</c:otherwise>
						</c:choose>						
						<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 180}">
						<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="right">
							$<fmt:formatNumber value="${task.finalPrice}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
						</td>
						</c:if>
						<c:if test="${summaryDTO.status == 120}">
						<td align="right" style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
							$<fmt:formatNumber value="0.00"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
						</td>
						</c:if>
					</tr>
			</c:if>
		</c:forEach>
		<c:forEach var="addons" items="${summaryDTO.upsellInfo.addonServicesList}">		
		<c:if test="${addons.sku == '99888' && addons.quantity >0 && addons.autoGenInd == false}">	
		<tr>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="left">
				<strong>Permits (Add-on)</strong>
									<strong>-</strong>	
					<strong>${addons.description}</strong>
		</td>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
		</td>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="right">
					$<fmt:formatNumber value="${addons.providerPaid}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
		</td>
		</tr>
		</c:if>	
		</c:forEach>	
		<tr>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="left">
				<strong>Add-on Services <c:if test="${summaryDTO.buyerID != '3000'}">(Total add-ons without permits)</c:if></strong>				
		</td>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
		</td>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="right">
				
									<c:if test="${displayTax}">
										<fmt:formatNumber value="${summaryDTO.nonPermitTaskAddonPrice - (providerAddonTaxTotal * 1)}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
									</c:if>
									<c:if test="${null == displayTax || !displayTax}">
										<fmt:formatNumber value="${summaryDTO.nonPermitTaskAddonPrice}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
									</c:if>
		</td>
		</tr>
		</c:if>	
		
		<c:if test="${displayTax}">
		<tr>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="left">
				<strong>Add-on Services <c:if test="${summaryDTO.buyerID!= '3000'}">(Total add-ons without permits)</c:if> Tax</strong>				
		</td>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;">
		</td>
		<td style="border-bottom: 1px solid #ccc;padding-bottom: 12px;padding-top: 12px;" align="right">
					$<fmt:formatNumber value="${providerAddonTaxTotal}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />		
		</td>
		</tr>
		</c:if>
		<tr style="background-color: #cccccc;">
		<td align="right" style="padding-bottom: 12px;padding-top: 12px;">
				<strong>Totals</strong>
		</td>
		<td align="right" style="padding-bottom: 12px;padding-top: 12px;">
					$<fmt:formatNumber value="${summaryDTO.totalSpendLimit}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />		
		</td>
		<td align="right" style="padding-bottom: 12px;padding-top: 12px;">
					$<fmt:formatNumber value="${summaryDTO.totalFinalPrice +summaryDTO.permitTaskAddonPrice+summaryDTO.nonPermitTaskAddonPrice+summaryDTO.addOnPermitPrice}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />			
		</td>
		</tr>
		
		<tr height="20">
		</tr>
		
		<tr>
					<td align="center" style="padding-bottom: 12px; padding-top: 12px;">
					<strong style="font-size: small">Buyer Total</strong>
					</td>
				<td align="right" style="padding-bottom: 12px;padding-top: 12px;border-bottom: 1px solid #ccc;">
				<strong>+ Posting Fee</strong>
		</td>
		<td align="right" style="padding-bottom: 12px;padding-top: 12px;border-bottom: 1px solid #ccc;">
					$<fmt:formatNumber value="${summaryDTO.postingFee}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />			
		</tr>
		<tr>
		<td>
		</td>
			<td style="padding-bottom: 12px;padding-top: 12px;" align="right">
				<strong>Closing Total</strong>
		</td>
		<td align="right" style="padding-bottom: 12px;padding-top: 12px;background-color: #C9E6F4;">
					$<fmt:formatNumber value="${summaryDTO.postingFee + summaryDTO.totalFinalPrice+summaryDTO.permitTaskAddonPrice+summaryDTO.nonPermitTaskAddonPrice+summaryDTO.addOnPermitPrice}"
								type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />		
		</td>	
		</tr>
	</c:if>
				
			</c:otherwise>
			</c:choose>
	</table>
</div>
		</div>

			<c:if
				test="${(incSpendLimit == true ||summaryDTO.taskLevelPriceInd == false || SecurityContext.slAdminInd == true) && (summaryDTO.status == 150 || summaryDTO.status == 155 || summaryDTO.status == 170)}">
				<c:if test="${summaryDTO.nonFundedInd == false}">
					<img style="cursor: pointer;"
						src="${staticContextPath}/images/buttons/increase-maximum-price.png"
						onclick="calculateSpendLimit();" />
				</c:if>
			</c:if>
		</div>
</div>
</body>
</html>
