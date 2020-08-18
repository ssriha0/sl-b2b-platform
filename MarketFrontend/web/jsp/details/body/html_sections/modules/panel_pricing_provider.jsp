<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<script type="text/javascript">
function expandPriceProvider(id,path){
var hidId="group"+id;
var testGroup=document.getElementById(hidId).value;
var divId="priceProvider"+id;
var bodyId="priceProvider_menu_body"+id;
if(testGroup=="menu_list"){
jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}else{
jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}
var ob=document.getElementById('priceProvImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('priceProvImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('priceProvImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}


</script>

</head>
<body>
	<c:set var="divName" value="priceProvider" />
	<c:set var="divName" value="${divName}${summaryDTO.id}" />
	<c:set var="group" value="group" />
 <c:if test="${checkGroup==group}">
		<c:set var="groupVal" value="menu_list" />
		<c:set var="bodyClass" value="menu_body" />
		<c:set var="headClass" value="menu_head" />
	</c:if>
	<c:if test="${checkGroup!=group}">
		<c:set var="groupVal" value="menugroup_list" />
		<c:set var="bodyClass" value="menugroup_body" />
		<c:set var="headClass" value="menugroup_head" />
	</c:if>
	<div id="${divName}" class="${groupVal}">
		<c:set	var="flag" value="false"/>
		<c:set var="bodyName" value="priceProvider_menu_body" />
		<c:set var="bodyName" value="${bodyName}${summaryDTO.id}" />
		<c:set var="priceProvImage" value="priceProvImage" />
		<c:set var="priceProvImage" value="${priceProvImage}${summaryDTO.id}" />
		<p class="${headClass }"
			onclick="expandPriceProvider('${summaryDTO.id}','${staticContextPath}')">
			&nbsp;
			<img id="${priceProvImage}"
				src="${staticContextPath}/images/widgets/arrowDown.gif" />
			&nbsp;Service Order Pricing
		</p>	
	<c:if test="${summaryDTO.taskLevelPriceInd == true || summaryDTO.buyerID == '3333' || summaryDTO.buyerID == '7777' || (summaryDTO.taskLevelPriceInd == false && summaryDTO.buyerID == '3000')}">
	<c:set	var="flag" value="true"/>
	<c:set	var="serviceFeePercentage" value="0.1"/>
	<div id="${bodyName}" class="${bodyClass}">
				<p> 
					Service order pricing outlined below.
				</p>
				
					<table width="90%" cellspacing="0">
				
		<tr>
									<td width="60%" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
									</td>
									<td	width="22%" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right" >
										<strong>Maximum Price</strong>	
									</td>
									<td width="18%" align="right">
		<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
											<p style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0"><strong>Final Price</strong></p>
		</c:if>
		</td>
		</tr>
								
					<tr>
									<td  align="left" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" >
							<strong>Labor:</strong>
						</td>
									<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
										<fmt:formatNumber value="${summaryDTO.soTaskMaxLabor / (1 + laborTaxPercentage)}"	type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>
									<td align="right">
						<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 180}">
											<p style="border-bottom: 1px solid #ccc;padding: 10px 0 10px 0;"><fmt:formatNumber value="${summaryDTO.soFinalMaxLabor / (1 + laborTaxPercentage)}"	type="currency" minFractionDigits="2" maxFractionDigits="2" /></p>
						</c:if>
						<c:if test="${summaryDTO.status == 120}">
											<p style="border-bottom: 1px solid #ccc;padding: 10px 0 10px 0;"><fmt:formatNumber value="0.00" type="currency"	minFractionDigits="2" maxFractionDigits="2" /></p>
						</c:if>
									</td>
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
						<td  align="left" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
							<strong>Materials:</strong>
						</td>
									<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
										<fmt:formatNumber value="${summaryDTO.partsSpendLimit  / (1 + partsTaxPercentage)}"	type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>
									<td align="right">
						<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 180}">
											<p style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0"><fmt:formatNumber value="${summaryDTO.finalPartsPrice / (1 + partsTaxPercentage)}"	type="currency" minFractionDigits="2" maxFractionDigits="2" /></p>
						</c:if>
						<c:if test="${summaryDTO.status == 120}">
											<p style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" ><fmt:formatNumber value="0.00" type="currency"	minFractionDigits="2" maxFractionDigits="2" /></p>
						</c:if>
									</td>
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
					
					<c:if test="${!empty summaryDTO.invoiceParts}">
                                  <c:set  var="finalPartsPriceTotal" value="0"></c:set>
					<c:forEach items="${summaryDTO.invoiceParts}" var="parts">
						 <c:if test="${parts.partStatus == 'Installed'}">
							<c:set  var="finalPartsPriceTotal" value="${finalPartsPriceTotal+parts.finalPrice}"></c:set>
						</c:if>
					</c:forEach>
					<tr>
						<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
							<strong>Parts</strong>
						</td>
							<td style="border-bottom: 1px solid #ccc;">
						</td>
						<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
							<td align="right"
								style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
								<fmt:formatNumber value="${finalPartsPriceTotal}"
								type="currency" minFractionDigits="2" maxFractionDigits="2" />
							</td>
						</c:if>
					</tr>
                    </c:if>
								
					<c:forEach var="task" items="${summaryDTO.taskList}">
					<c:if test="${task.taskType==1}">
					<tr>
					<td  align="left" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
							<strong>Permits (Customer Pre-paid)</strong>
							<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
								<strong>-</strong>
								<strong>${task.permitTaskDesc}</strong>
							</c:if>
						</td>						
						<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
						<c:choose>
						<c:when test="${summaryDTO.status == 165}">
							<fmt:formatNumber value="0.00"	type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</c:when>
						<c:when test="${summaryDTO.status == 120}">
							<fmt:formatNumber value="${task.finalPrice}"	type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</c:when>
						<c:otherwise>
							<c:choose>
							<c:when test="${task.sellingPrice == null}">
								<fmt:formatNumber value="0.00"	type="currency" minFractionDigits="2" maxFractionDigits="2" />
							</c:when>
							<c:otherwise>
								<fmt:formatNumber value="${task.sellingPrice}"	type="currency" minFractionDigits="2" maxFractionDigits="2" />
							</c:otherwise>
							</c:choose>
						</c:otherwise>
						</c:choose>																										
						</td>
									<td align="right">
						<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 180}">
											<p style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" ><fmt:formatNumber value="${task.finalPrice}"	type="currency" minFractionDigits="2" maxFractionDigits="2" /></p>
						</c:if>
						<c:if test="${summaryDTO.status == 120}">
											<p style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" ><fmt:formatNumber value="0.00" type="currency"	minFractionDigits="2" maxFractionDigits="2" /></p>
						</c:if>
									</td>
					</tr>
			</c:if>
					</c:forEach>
								
					<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
								<c:forEach var="addons"	items="${summaryDTO.upsellInfo.addonServicesList}">
								<c:if test="${addons.sku == '99888' && addons.quantity >0 && addons.autoGenInd == false}">
		<tr>
									<td  align="left" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
				<strong>Permits (Add-on)</strong>
										<strong>-</strong>	
					<strong>${addons.description}</strong>
		</td>
									<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
										
		</td>
									<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
											<fmt:formatNumber value="${addons.providerPaid}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
		</td>
								</tr>	
								</c:if>
								</c:forEach>
					
							<tr>
								<td  align="left" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
									<strong>Add-on Services<c:if test="${summaryDTO.buyerID != '3000'}"> (Total add-ons without
														permits)</c:if></strong>
								</td>
								<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
								</td>
								<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
									<c:if test="${displayTax}">
										<fmt:formatNumber value="${summaryDTO.nonPermitTaskAddonPrice - (providerAddonTaxTotal * 1)}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
									</c:if>
									<c:if test="${null == displayTax || !displayTax}">
										<fmt:formatNumber value="${summaryDTO.nonPermitTaskAddonPrice}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
									</c:if>
								</td>
								
							</tr> 
							<c:if test="${displayTax}">
								<tr>
									<td  align="left" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
										<strong>Add-on Services<c:if test="${summaryDTO.buyerID != '3000'}"> (Total add-ons without
															permits)</c:if> Tax</strong>
									</td>
									<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
									</td>
									<td  style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
											<fmt:formatNumber value="${providerAddonTaxTotal}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
									</td>
								</tr> 
							</c:if>
																
								<tr style="background-color: #cccccc">
									<td  align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
						<strong>Totals</strong>
						</td>
									<td  align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
										<fmt:formatNumber value="${summaryDTO.totalSpendLimit}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
						</td>
									<td  align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
										<fmt:formatNumber value="${summaryDTO.totalFinalPrice+summaryDTO.permitTaskAddonPrice+summaryDTO.nonPermitTaskAddonPrice+summaryDTO.addOnPermitPrice}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
						</td>
								</tr>	
								
								<c:set var="combinedPriceWithTax" value="0.00"></c:set>
								
								<tr>
									<td  rowspan="5" style="vertical-align: middle;text-align: center;">
										<strong style="font-size: small">Provider Total</strong>
					</td>
									<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right" height="50">
										<strong>Combined Total Final</strong>
						</td>
									<td align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" bgcolor="#C9E6F4">
										<c:set var="combinedPriceWithTax" value="${summaryDTO.totalFinalPrice+summaryDTO.permitTaskAddonPrice+summaryDTO.nonPermitTaskAddonPrice+summaryDTO.addOnPermitPrice}"></c:set>
										<fmt:formatNumber value="${summaryDTO.totalFinalPrice+summaryDTO.permitTaskAddonPrice+summaryDTO.nonPermitTaskAddonPrice+summaryDTO.addOnPermitPrice}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
									</td>
					</tr>
					
					<tr>
								<c:if test="${fn:length(summaryDTO.permitTaskList)!=0 || summaryDTO.addOnPermitPrice!=0.00}">
									<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
						<strong>Total Without Permits</strong>
					</td>
									<td align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
										<fmt:formatNumber value="${((summaryDTO.totalFinalPrice)-(summaryDTO.finalPermitPrice))+summaryDTO.nonPermitTaskAddonPrice}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
					</td>
							
								<c:if test="${fn:length(summaryDTO.permitTaskList)==0 && summaryDTO.addOnPermitPrice==0.00}">
									<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right" height="50">
										<strong>Combined Total Final</strong>
									</td>
									<td align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
										<c:set var="combinedPriceWithTax" value="${summaryDTO.totalFinalPrice+summaryDTO.permitTaskAddonPrice+summaryDTO.nonPermitTaskAddonPrice+summaryDTO.addOnPermitPrice}"></c:set>
										<fmt:formatNumber value="${summaryDTO.totalFinalPrice+summaryDTO.permitTaskAddonPrice+summaryDTO.nonPermitTaskAddonPrice+summaryDTO.addOnPermitPrice}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
									</td>								
								</c:if>
									</c:if>
					</tr>
					<c:set var="combinedPriceWithoutTax" value="0.00"></c:set>
					<c:if test="${displayTax}">
						<c:set var="combinedPriceWithoutTax" value="${combinedPriceWithTax - (laborTaxTotalUI * 1) - (materialsTaxTotalUI * 1) - (providerAddonTaxTotal * 1)}"></c:set>
						<tr>
								<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
									<strong>Combined Total Final (Without Tax)</strong>																			
								</td>
								<td align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
									<fmt:formatNumber value="${combinedPriceWithoutTax * 1}"
											type="currency" minFractionDigits="2" maxFractionDigits="2" />
								</td>
						</tr>
					</c:if>
					
					<tr>
									<c:if test="${fn:length(summaryDTO.permitTaskList)==0 && summaryDTO.addOnPermitPrice==0.00}">
									<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
										<strong>-10% Service Fee <c:if test="${displayTax}"> (on base price) </c:if></strong>																			
									</td>									
									</c:if>
									<c:if test="${fn:length(summaryDTO.permitTaskList)!=0 || summaryDTO.addOnPermitPrice!=0.00}">
									<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
						<strong>-10% Service Fee<c:if test="${displayTax}">(on base price)</c:if>** </strong>
					</td>
									</c:if>
									<td align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
						<c:if test="${ null == displayTax || !displayTax}">
						<c:if test="${summaryDTO.taskLevelPriceInd==true}">
										<fmt:formatNumber value="${summaryDTO.serviceFeeDisplay}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</c:if>
						<c:if test="${summaryDTO.taskLevelPriceInd==false}">
										<fmt:formatNumber value="${summaryDTO.serviceFeeDisplay}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</c:if>
						</c:if>
						<c:if test="${displayTax}">
							<fmt:formatNumber value="${combinedPriceWithoutTax * (serviceFeePercentage * 1)}"
													type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</c:if>
						
									</td>
					</tr>
								<c:if test="${fn:length(summaryDTO.permitTaskList)!=0 || summaryDTO.addOnPermitPrice!=0.00}">								
					<tr>
									<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right">
						<strong>+ Permits</strong>
					</td>
									<td align="right" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
										<fmt:formatNumber value="${(summaryDTO.addOnPermitPrice + summaryDTO.finalPermitPrice + summaryDTO.permitTaskAddonPrice)}" type="currency"	minFractionDigits="2" maxFractionDigits="2" />
									</td>
					</tr>
								</c:if>
					<tr>
									<td style="padding: 10px 0 10px 0" align="right">
						<strong>Total Provider Payment</strong>
					</td>
									<td align="right" bgcolor="#C9E6F4" style="padding: 10px 0 10px 0">
						<c:if test="${ displayTax }">
							<fmt:formatNumber value="${(summaryDTO.providerPaymentDisplay * 1) + (summaryDTO.serviceFeeDisplay * 1) - (combinedPriceWithoutTax * (serviceFeePercentage * 1))}"
									type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</c:if>
						<c:if test="${ null == displayTax || !displayTax }">
										<fmt:formatNumber value="${summaryDTO.providerPaymentDisplay}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</c:if>
					</td>
					</tr>
					<tr>
								<c:if test="${fn:length(summaryDTO.permitTaskList)!=0 || summaryDTO.addOnPermitPrice!=0.00}">
						<td>
										** Service Fee applies to total excluding permits
						</td>
								</c:if>
							</tr> 
						</c:if>
			
								<c:if test="${summaryDTO.status == 155 || summaryDTO.status == 150 || summaryDTO.status == 165 || summaryDTO.status == 110 || summaryDTO.status == 130 || summaryDTO.status == 170}">
			<tr>
									<td align="right" style="padding: 10px 0 10px 0">
											<strong>Combined Maximum:</strong>
							</td>
									<td  align="right" bgcolor="#C9E6F4" style="padding: 10px 0 10px 0">
										<fmt:formatNumber value="${summaryDTO.totalSpendLimit}"
														type="currency" minFractionDigits="2" maxFractionDigits="2" />
										</td>
									<td ></td>
									</tr>
								</c:if>
								</table>
			</div>
	</c:if>


	<c:if test="${summaryDTO.taskLevelPriceInd == false && flag==false}">
    <div id="${bodyName}" class="${bodyClass}">

	<p>
		Service order pricing outlined below.
	</p>


				<table width="90%" border=0 cellspacing="0">
		<tr>
									<td width="60%" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
									</td>
									<td	width="22%" style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0" align="right" >
										<strong>Maximum Price</strong>	
									</td>
									
									<td width="18%" align="right">
		<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
											<p style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0"><strong>Final Price</strong></p>
		</c:if>
		</td>
		</tr>

		<tr>
									<td
										style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
							<strong>Labor:</strong>
						</td>
									<td align="right"
										style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
							<fmt:formatNumber value="${summaryDTO.laborSpendLimit}"
											type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>
									<c:if
										test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">

										<td align="right"
											style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
											<fmt:formatNumber value="${summaryDTO.finalLaborPrice}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>
						</c:if>
					</tr>
					<tr>
									<td
										style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
							<strong>Materials:</strong>
						</td>
									<td
										style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0"
										align="right">
							<fmt:formatNumber value="${summaryDTO.partsSpendLimit}"
											type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>
									<c:if
										test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">

										<td align="right"
											style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
											<fmt:formatNumber value="${summaryDTO.finalPartsPrice}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>
						</c:if>
					</tr>

					<c:if test="${!empty summaryDTO.invoiceParts}">
                                   <c:set  var="finalPartsPriceTotal" value="0"></c:set>
					<c:forEach items="${summaryDTO.invoiceParts}" var="parts">
					
					<c:if test="${parts.partStatus == 'Installed'}">
					
					<c:set  var="finalPartsPriceTotal" value="${finalPartsPriceTotal+parts.finalPrice}"></c:set>
					</c:if>
	                           </c:forEach>
					<tr>
							<td style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
							<strong>Parts</strong>
							</td>
							<td style="border-bottom: 1px solid #ccc;">
							</td>
							<c:if test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
								<td align="right"
								style="border-bottom: 1px solid #ccc; padding: 10px 0 10px 0">
								<fmt:formatNumber value="${finalPartsPriceTotal}"
								type="currency" minFractionDigits="2" maxFractionDigits="2" />
							</td>
							</c:if>
					</tr>

                                        </c:if>




								<c:if
									test="${summaryDTO.status == 160 || summaryDTO.status == 120 || summaryDTO.status == 180}">
									<tr style="background-color: #cccccc;">
										<td align="right"
											style="padding: 10px 0 10px 0">
							<strong>Totals:</strong>
						</td>
										
										<td align="right"
											style="padding: 10px 0 10px 0">
							<fmt:formatNumber value="${summaryDTO.totalSpendLimit}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>

											<td align="right"
												style="padding: 10px 0 10px 0">
												<fmt:formatNumber
													value="${summaryDTO.totalFinalPrice + addonServicesDTO.paymentAmount}"
													type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>
									</tr>
									<tr height="30">
										<td align="right" colspan="2" style="padding: 10px 0 10px 0;">
											<strong>Combined Total Final</strong> &nbsp;
						</td>
										<td align="right" style="padding: 10px 0 10px 0; background-color: #C9E6F4">
											<fmt:formatNumber
												value="${summaryDTO.totalFinalPrice + addonServicesDTO.paymentAmount}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
										</td>
					</tr>
								
								<tr height="20"> </tr>	
								<tr height="30">
									<td>
						</td>
									<td align="right" style="border-top: 1px solid #ccc;padding: 10px 0 10px 0;">
										<strong>Combined Total Final&nbsp;</strong>
									</td>
									<td align="right" style="border-top: 1px solid #ccc;padding: 10px 0 10px 0;">
										<fmt:formatNumber
											value="${summaryDTO.totalFinalPrice + addonServicesDTO.paymentAmount}"
											type="currency" minFractionDigits="2" maxFractionDigits="2" />
									</td>
					</tr>
					<tr>
									<td colspan=1 align="center"
										style="padding: 10px 0 10px 0">
					<strong style="font-size: small">Provider Total</strong>
					</td>
									<td align="right"
										style="padding: 10px 0 10px 0; border-bottom: 1px solid #ccc; border-top: 1px solid #ccc;"
										colspan=1>
										<strong>-10% Service Fee</strong>
					</td>
									<td width="40" align="right"
										style="padding: 10px 0 10px 0; border-bottom: 1px solid #ccc; border-top: 1px solid #ccc;">
										<fmt:formatNumber value="${summaryDTO.serviceFeeDisplay}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
					</td>
					</tr>
					<tr>
										<td>
					</td>
									<td align="left"
										style="padding-bottom: 12px; padding-top: 12px;" colspan=1>
						<strong>Total Provider Payment</strong>
					</td>
									<td align="right"
										style="padding: 10px 0 10px 0;background-color: #C9E6F4">
										<fmt:formatNumber value="${summaryDTO.providerPaymentDisplay}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
						</td>
					</tr>
					</c:if>
				<c:if
									test="${summaryDTO.status == 100 || summaryDTO.status == 110 || summaryDTO.status == 150 || summaryDTO.status == 155}">
						<tr>
										<td align="right" style="padding: 10px 0 10px 0;">
											<strong>Combined Maximum:</strong>
							</td>
										<td align="right" bgcolor="#C9E6F4" style="padding: 10px 0 10px 0;">
											<fmt:formatNumber value="${summaryDTO.totalSpendLimit}"
												type="currency" minFractionDigits="2" maxFractionDigits="2" />
							</td>
						</tr>
						</c:if>

					</table>
			</div>
				</c:if>
	</div>