<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>


<html style="">
<head>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard.css" />

<script type="text/javascript">
jQuery.noConflict();
var RetailPriceHoverDef = "&ldquo;<strong>Retail Price</strong>&rdquo; is the actual retail price sold through Sears Parts Direct.";
var EstNtProvPymtHoverHoverDef = "&ldquo;<strong>Est. Net Provider Payment</strong>&rdquo; is the remaining amount after fees** have been applied.</br><Strong><i>**Fees may change without notice.</i></Strong>";

jQuery(".glossaryItem").mouseover(function(e){
	jQuery("#explainer").css("position","absolute");
   	    
    	
    	if(jQuery(this).attr("id") == "EstNtProvPaymtHoverHover") {
    	 var position = jQuery("#EstNtProvPaymtHoverHover").offset();
    	 jQuery("#explainer").html(EstNtProvPymtHoverHoverDef);
    	 jQuery("#explainer").css("top",position.top-300);
    	 jQuery("#explainer").css("left",position.left-224);
    	}
    	
    	if(jQuery(this).attr("id") == "RetailPriceHover") {
       	 var position = jQuery("#RetailPriceHover").offset();
       	 jQuery("#explainer").html(RetailPriceHoverDef);
       	 jQuery("#explainer").css("top",position.top-300);
       	 jQuery("#explainer").css("left",position.left-224);
       	}
    	
    	jQuery("#explainer").show();   
	}); 
jQuery(".glossaryItem").mouseout(function(e){
	jQuery("#explainer").hide();
	}); 
  	
  
  	
  	function goToSummaryPages(){
  		var selected=$ta.tabs('option', 'selected');
		// 0 is the summary tab
		if("0" == selected){
			moveToDocuments('${SERVICE_ORDER_ID}');
			return;
		}else{
			
			jQuery("#PartInd").val("Part");
						
		}
		$ta.tabs("select",0);
 		return false;
  		
  	}
  	
  	function viewEditPartsDocument(documentId){
 		var loadForm = document.getElementById('buyerViewDocForm');
        loadForm.action = 'viewPartsDocument.action?editDocId='+documentId;
        loadForm.submit();
		/* jQuery.ajax({
        	url: 'viewPartsDocument.action?documentId='+documentId,
			success: function(data) {
				
			}
		});	 */
 	}
  	
  	function expandCompInvParts(path)
  	{
  	jQuery("#compInvParts p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#compInvPartsid").slideToggle(300);

  	var ob=document.getElementById('compInvPartsImg').src;
  	if(ob.indexOf('arrowRight')!=-1){
  	document.getElementById('compInvPartsImg').src=path+"/images/widgets/arrowDown.gif";
  	}
  	if(ob.indexOf('arrowDown')!=-1){
  	document.getElementById('compInvPartsImg').src=path+"/images/widgets/arrowRight.gif";
  	}
  	}
  	
</script>

<style type="text/css">

td.buyer_parts_table {     
		padding-top: 4px;
		padding-bottom:4px;
	}
</style>

<div id="compInvParts" class="menugroup_list">
<p class="menugroup_head" onclick="expandCompInvParts('${staticContextPath}')">&nbsp;<img id="compInvPartsImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Invoice Parts</p>
<div class="menugroup_body" id="compInvPartsid">
Installed parts covered by In-Warranty or Protection Agreement.Go to 
<a style="text-decoration: underline; margin-left: 0px;color:#0033FF; padding-left: 0px; border: 0px;cursor:pointer" onclick="goToSummaryPages()">Parts </a>Section for more details.


<form method="POST" id="buyerViewDocForm">
	<table id="addtable" width="99%" class="installed_parts"
		cellpadding="0" border="1" bordercolor="grey" >
<c:choose>
<c:when test="${soCloseDto.invoicePartsPricingModel=='COST_PLUS' && soCloseDto.roleId == 3}">
<thead>
			<tr>
				<td class="installed_parts_odd" align="center" width="10%">Invoice #</td>
				<td class="installed_parts_odd" align="left" width="10%" style="padding-left:4px">Part Number</td>
				<td class="installed_parts_odd" align="center" width="8%">Qty</td>
				<td class="installed_parts_odd" align="center" width="10%">Part Status</td>	
				<td class="installed_parts_odd" align="center" width="10%">Proof of Invoice</td>
				<td class="installed_parts_odd" align="right" width="8%" style="padding-right: 4px;">Unit Cost</td>			
				<td align="right" width="16%" style="cursor: pointer; background-color: #00A0D2;padding-right: 4px;" colspan="2">
				<span id="EstNtProvPaymtHoverHover" class="glossaryItem"
					style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">Est.&nbsp;&nbsp;Net
						Provider Payment</span>
						<div id="explainer" style="z-index: 1000"></div>
						</td>		
					
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">Parts Direct/Commercial Discounted Price	</td>
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">Provider Margin</td>
				<td class="installed_parts_odd" align="center" width="8%">Claim Status</td>
				
			</tr>
		</thead>
		<tbody>
			<c:set var="totalNetPartPayment"
						value="0.00"></c:set>
						<c:set var="totalFinalPartPayment"
						value="0.00"></c:set>
           <c:forEach items="${soCloseDto.invoiceParts}" var="parts" varStatus="status">
				 <tr id='tablerow${status.index+1}'>
				
				  	<td id='invoiceNumber_${status.index+1}' class='buyer_parts_table'
						align="center">
						<div style='word-wrap: break-word'>
							${parts.invoiceNo}</div>
					</td>
					
					
					<td class='buyer_parts_table' id="partsNumber_${#status.index+1}"
						style="padding-left:4px" align="left">${parts.partNo}</td>
						
						
					<td id='quantity_${status.index}' class='buyer_parts_table' align="center">
							<div style='word-wrap: break-word'>
							${parts.qty}</div>			
					</td>
					
					<td id='partStatus_${status.index+1}' class='buyer_parts_table'
						align="center" >
						<div style='word-wrap: break-word'>
							 ${parts.partStatus}</div>
					</td>
					
					<td style="padding-top: 5px;padding-bottom: 5px;padding-left: 5px"> 
					
					<c:forEach items="${parts.invoiceDocuments}" var="invoiceParts" varStatus="status">
						<c:if test="${not empty invoiceParts.invoicePartId}">
							<c:if test="${not empty invoiceParts.fileName && fn:length(invoiceParts.fileName) > 8}">
						 		<div style="width:65px;"><a title="${invoiceParts.fileName}" href="javascript:void(0);" style='word-wrap: break-word' onclick="viewEditPartsDocument(${invoiceParts.documentId});">
						 		${fn:substring(invoiceParts.fileName,0,6)}<strong>...</strong></a></div>
						 	</c:if>
						 	<c:if test="${not empty invoiceParts.fileName && fn:length(invoiceParts.fileName) <= 8}">
						 		<div style="width:65px;"><a href="javascript:void(0);" style='word-wrap: break-word' onclick="viewEditPartsDocument(${invoiceParts.documentId});">
						 		${invoiceParts.fileName}</a></div>
						 	</c:if>
						<br>
						</c:if>
					</c:forEach>
					</td>
				
					<td  id='unitCost_${status.index}'
						class='buyer_parts_table' align="right" style="padding-right: 4px;">$${parts.unitCost}</td>
						
						
					
					
	
						
					<td colspan="2" class="buyer_parts_table" align="right"
						id='currencysymbol_${#status.index+1}' style="padding-top: 5px;padding-bottom: 5px;padding-right: 4px;">
						<c:if test="${parts.partStatus == 'Installed'}">
						$
						<span id='netPayment_${#status.index+1}' class='netPayment'
						bgcolor="#FFFFFF" ; > <fmt:formatNumber
								value="${parts.estProviderPartsPayment}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
					</span>
					<c:set var="totalNetPartPayment"
						value="${totalNetPartPayment+parts.estProviderPartsPayment}"></c:set>
					</c:if>
					
										<c:if test="${parts.partStatus != 'Installed'}">
								<span id='netPayment_${#status.index+1}' class='netPayment'
					align="center"	> NA
					</span>
										
					</c:if>
					
					</td>

					<td  id='spdPrice_${status.index}'
						class='buyer_parts_table' align="right" style="padding-right: 4px;">
					<div style='word-wrap: break-word'>
							$${parts.retailPrice} / $${parts.commercialPrice}</div>
					</td>
					
					<td  id='provMargin_${status.index}'
						class='buyer_parts_table' align="right" style="padding-right: 4px;">
					<div style='word-wrap: break-word'>
							$${parts.providerMargin}</div>
					</td>
					
					<td  id='claimStatus_${status.index}'
						class='buyer_parts_table' align="center" >
					<c:if test="${parts.claimStatus == 'Approved'}">
					<img src="${staticContextPath}/images/icons/icn_auto_approved.png"
								width="20" height="20" alt="" title="Auto Approved">
					</c:if> 
					
					<c:if test="${parts.claimStatus == 'Pending'}">
					<img src="${staticContextPath}/images/icons/icn_manual_review.png"
								width="20" height="20" alt="" title="Manual Review Needed"> 
					</c:if>
					</td>	
					
					<!-- End of table  -->
					
					<!-- Start of unwanted section..to be deleted??  -->
					
					 <span id='finalPaymentConvRate_${#status.index+1}'
						style="display: none" class='finalPaymentConvRate'> <fmt:formatNumber
								value="${parts.finalPrice/parts.qty}" type="NUMBER" minFractionDigits="2"
								maxFractionDigits="2" />
					</span> <span id='netPaymentConvRate_${#status.index+1}'
						style="display: none" class='netPaymentConvRate'> <fmt:formatNumber
								value="${parts.estProviderPartsPayment/parts.qty}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
					</span>
					
					
					<td id='finalPayment_${#status.index+1}' class='finalPayment'
						style='display: none;' align="center"><fmt:formatNumber
							value="${parts.finalPrice}" type="NUMBER" minFractionDigits="2"
							maxFractionDigits="2" /></td>
																
							
							
					<td style="display: none" class="variableInputs"><input
						type="hidden" id="invoiceParts[${status.index}].finalPrice"
						value="${parts.finalPrice}"
						
						cssClass="finalPriceInput" />
						
						 <input type="hidden"
						id="invoiceParts[${status.index}].estProviderPartsPayment"
						value="${parts.estProviderPartsPayment}"
						
						cssClass="netPaymentInput" /> 
						
						<input type="hidden"
						id="invoiceParts[${status.index}].qty" value="${parts.qty}"
						cssClass="qtyInput" />
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailCostToInventory"
						value="${parts.retailCostToInventory}"
						 /> 
						 
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailReimbursement"
						value="${parts.retailReimbursement}"
						 /> 
						 
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailPriceSLGrossUp"
						value="${parts.retailPriceSLGrossUp}"
						/>
						
						 <input
						type="hidden" id="invoiceParts[${status.index}].retailSLGrossUp"
						value="${parts.retailSLGrossUp}"
						 />
						 
						 
						 </td>

					<c:if test="${parts.partStatus == 'Installed'}">
					<c:set var="totalFinalPartPayment"
						value="${totalFinalPartPayment+parts.finalPrice}"></c:set>
						</c:if> 
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="6" style="padding-left: 35%;"><b>Sub Total
						for Parts</b></td>
				<td colspan="5" align="right" style="padding-right: 4px;"><b style="padding-right:226px;">$ <span
						class="payment" id="totalPayment" bgcolor="#FFFFFF"
						; align="right;"> <fmt:formatNumber
								value="${totalNetPartPayment}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" /></span></b></td>
			</tr>
		</tfoot>
</c:when>
<c:otherwise>
<thead>
			<tr>
				<td class="installed_parts_odd" align="center" width="16%">Invoice #</td>
				<td class="installed_parts_odd" align="left" width="10%" style="padding-left:4px">Part Number</td>
				<td class="installed_parts_odd" align="center" width="10%">Qty</td>
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">Unit Cost</td>			
				<td class="installed_parts_odd" align="center" width="15%">Part Status</td>
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">Retail Price</td>						
				<td align="right" width="15%" style="cursor: pointer; background-color: #00A0D2;padding-right: 4px;" colspan="2">
				<span id="EstNtProvPaymtHoverHover" class="glossaryItem"
					style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">Est.&nbsp;&nbsp;Net
						Provider Payment</span>
						<div id="explainer" style="z-index: 1000"></div>
						</td>						
				<td class="installed_parts_odd" align="center" width="15%">Documents</td>	
				
			</tr>
		</thead>
		<tbody>
			<c:set var="totalNetPartPayment"
						value="0.00"></c:set>
						<c:set var="totalFinalPartPayment"
						value="0.00"></c:set>
           <c:forEach items="${soCloseDto.invoiceParts}" var="parts" varStatus="status">
				 <tr id='tablerow${status.index+1}'>
				
				  	<td id='invoiceNumber_${status.index+1}' class='buyer_parts_table'
						align="center">
						<div style='word-wrap: break-word'>
							${parts.invoiceNo}</div>
					</td>
					
					
					<td class='buyer_parts_table' id="partsNumber_${#status.index+1}"
						align="left" style="padding-left:4px">${parts.partNo}</td>
						
						
					<td id='quantity_${status.index}' class='buyer_parts_table' align="center">
							<div style='word-wrap: break-word'>
							${parts.qty}</div>			
					</td>
					
				
					<td  id='unitCost_${status.index}'
						class='buyer_parts_table' align="right" style="padding-right: 4px;">$${parts.unitCost}</td>
						
						
					<td id='partStatus_${status.index+1}' class='buyer_parts_table'
						align="center" >
						<div style='word-wrap: break-word'>
							${parts.partStatus}</div>
					</td>
					<td  id='retailPrice_${status.index}'
						class='buyer_parts_table' align="right" style="padding-right: 4px;">$${parts.retailPrice}</td>
						
	
						
					<td colspan="2" class="buyer_parts_table" align="right"
						id='currencysymbol_${#status.index+1}' style="padding-right: 4px; padding-top: 5px;padding-bottom: 5px">
						<c:if test="${parts.partStatus == 'Installed'}">
						$
						<span id='netPayment_${#status.index+1}' class='netPayment'
						bgcolor="#FFFFFF" ; align="left"> <fmt:formatNumber
								value="${parts.estProviderPartsPayment}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
					</span>
					<c:set var="totalNetPartPayment"
						value="${totalNetPartPayment+parts.estProviderPartsPayment}"></c:set>
					</c:if>
					
					<c:if test="${parts.partStatus != 'Installed'}">
								<span id='netPayment_${#status.index+1}' class='netPayment'
					align="right"	> NA
					</span>
										
					</c:if>
					
					</td>
					
					<td style="padding-top: 5px;padding-bottom: 5px;padding-left: 5px"> 
					
					<c:forEach items="${parts.invoiceDocuments}" var="invoiceParts" varStatus="status">
						<c:if test="${not empty invoiceParts.invoicePartId}">
						
						 	<c:if test="${not empty invoiceParts.fileName && fn:length(invoiceParts.fileName) > 6}">
						 		<div style="width:57px;">
						 		<a title="${invoiceParts.fileName}" href="javascript:void(0);" style='word-wrap: break-word' onclick="viewEditPartsDocument(${invoiceParts.documentId});">
						 		${fn:substring(invoiceParts.fileName,0,6)}<strong>...</strong></a></div>
						 	</c:if>
						 	<c:if test="${not empty invoiceParts.fileName && fn:length(invoiceParts.fileName) <= 6}">
						 		<div style="width:57px;"><a href="javascript:void(0);" style='word-wrap: break-word' onclick="viewEditPartsDocument(${invoiceParts.documentId});">
						 		${invoiceParts.fileName}</a></div>
						 	</c:if>
						
						</c:if>
					</c:forEach>
					</td>
					
					<!-- End of table  -->
					
					<!-- Start of unwanted section..to be deleted??  -->
					
					 <span id='finalPaymentConvRate_${#status.index+1}'
						style="display: none" class='finalPaymentConvRate'> <fmt:formatNumber
								value="${parts.finalPrice/parts.qty}" type="NUMBER" minFractionDigits="2"
								maxFractionDigits="2" />
					</span> <span id='netPaymentConvRate_${#status.index+1}'
						style="display: none" class='netPaymentConvRate'> <fmt:formatNumber
								value="${parts.estProviderPartsPayment/parts.qty}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
					</span>
					
					
					<td id='finalPayment_${#status.index+1}' class='finalPayment'
						style='display: none;' align="right"><fmt:formatNumber
							value="${parts.finalPrice}" type="NUMBER" minFractionDigits="2"
							maxFractionDigits="2" /></td>
																
							
							
					<td style="display: none" class="variableInputs"><input
						type="hidden" id="invoiceParts[${status.index}].finalPrice"
						value="${parts.finalPrice}"
						
						cssClass="finalPriceInput" />
						
						 <input type="hidden"
						id="invoiceParts[${status.index}].estProviderPartsPayment"
						value="${parts.estProviderPartsPayment}"
						
						cssClass="netPaymentInput" /> 
						
						<input type="hidden"
						id="invoiceParts[${status.index}].qty" value="${parts.qty}"
						cssClass="qtyInput" />
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailCostToInventory"
						value="${parts.retailCostToInventory}"
						 /> 
						 
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailReimbursement"
						value="${parts.retailReimbursement}"
						 /> 
						 
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailPriceSLGrossUp"
						value="${parts.retailPriceSLGrossUp}"
						/>
						
						 <input
						type="hidden" id="invoiceParts[${status.index}].retailSLGrossUp"
						value="${parts.retailSLGrossUp}"
						 />
						 
						 
						 </td>

					<c:if test="${parts.partStatus == 'Installed'}">
					<c:set var="totalFinalPartPayment"
						value="${totalFinalPartPayment+parts.finalPrice}"></c:set>
						</c:if> 
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td style="padding-left: 54%;" colspan="6"><b>Sub Total
						for Parts</b></td>
				<td align="right" style="padding-right: 94px;" colspan="5">
				<b>$ <span align="right;" bgcolor="#FFFFFF" id="totalPayment" class="payment"> 
					<fmt:formatNumber
								value="${totalNetPartPayment}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" /></span></b></td>
			</tr>
		</tfoot>
</c:otherwise>
</c:choose>
</table>
	</form>
	<div id="loadingImage"></div>
	<div id="reImbursementRate" style="visibility: hidden;"></div>
	<div id="finalTotalPartPrice" style="visibility: hidden;">
		<fmt:formatNumber value="${totalFinalPartPayment}" type="NUMBER"
			minFractionDigits="2" maxFractionDigits="2" />
				
			
	</div>
	</div>
</div>	
	
    