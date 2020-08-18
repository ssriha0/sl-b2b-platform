<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>


<html style="">
<head>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dashboard.css" />
<style type="text/css">
 .explainerCompletion {
    background: none repeat scroll 0 0 #FCFAE6;
    border: 3px solid #ADAAAA;
    border-radius: 10px 10px 10px 10px;
    display: none;
    height: auto;
    padding: 10px;
    width: 220px;
}
</style>
<script type="text/javascript">
jQuery.noConflict();
var RetailPriceHoverDef = "&ldquo;<strong>Retail Price</strong>&rdquo; is the actual retail price sold through Sears Parts Direct.";
var EstNtProvPymtHoverHoverDef = "&ldquo;<strong>Est. Net Provider Payment</strong>&rdquo; is the remaining amount after fees** have been applied.</br><Strong><i>**Fees may change without notice.</i></Strong>";

jQuery(".glossaryItem").mouseover(function(e){
	jQuery("#explainerCompletion").css("position","absolute");
   	    if(jQuery(this).attr("id") == "EstNtProvPaymtHoverHover") {
    	 var position = jQuery("#EstNtProvPaymtHoverHover").offset();
    	 jQuery("#explainerCompletion").html(EstNtProvPymtHoverHoverDef);
    	 jQuery("#explainerCompletion").css("top",position.top-329);
    	 jQuery("#explainerCompletion").css("left",position.left-123);
    	}
   	 if(jQuery(this).attr("id") == "RetailPriceHover") {
    	 var position = jQuery("#RetailPriceHover").offset();
    	 jQuery("#explainerCompletion").html(RetailPriceHoverDef);
    	 jQuery("#explainerCompletion").css("top",position.top-329);
    	 jQuery("#explainerCompletion").css("left",position.left-123);
    	}
    	jQuery("#explainerCompletion").show();   
	}); 
jQuery(".glossaryItem").mouseout(function(e){
	jQuery("#explainerCompletion").hide();
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



	<table id="addtable" width="99%" class="installed_parts"
		cellpadding="0" border="1" bordercolor="grey" >

		<thead>
			<tr>
				<td class="installed_parts_odd" align="left" width="12%" style="padding-left:4px">Part Number</td>
				<td class="installed_parts_odd" align="center" width="12%">Part Name</td>
				<td class="installed_parts_odd" align="center" width="14%">Part Status</td>
				<td class="installed_parts_odd" align="center" width="12%">Invoice #</td>
				<td class="installed_parts_odd" align="center" width="10%">Proof Of Invoice</td>
				<td class="installed_parts_odd" align="center" width="10%">Qty</td>
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">Unit Cost</td>
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">
				<span id="RetailPriceHover" class="glossaryItem"
					   style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">
				Retail Price
				</span>
				</td>
				<td align="right" width="10%" style="cursor: pointer; background-color: #00A0D2; padding-right: 4px;" colspan="2">
				   <span id="EstNtProvPaymtHoverHover" class="glossaryItem"
					   style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">Est.&nbsp;&nbsp;Net
						Provider Payment
				   </span>
					<div id="explainerCompletion" class="explainerCompletion" style="z-index: 1000;"></div>
				</td>			
				
			</tr>
		</thead>
		<tbody>
			<c:set var="totalNetPartPayment"
						value="0.00"></c:set>
						<c:set var="totalFinalPartPayment"
						value="0.00"></c:set>
<c:forEach items="${soCloseDto.invoiceParts}" var="parts" varStatus="status">
				<tr id='tablerow${status.index+1}'>
				
					
					<td class='' id="partsNumber_${#status.index+1}" style="padding-top:5px;padding-bottom:5px;padding-left:4px"
						align="left" width="12%">${parts.partNo}</td>
						
						
						
					<td id='description_${status.index+1}' class='' style="padding-top:5px;padding-bottom:5px;"
						align="center" width="12%">
						<div id='descriptionPart_${status.index+1}'  style='word-wrap: break-word'>
						<c:choose>
						<c:when test="${not empty parts.description && fn:length(parts.description) > 10}">
											    						${fn:substring(parts.description,0,10)} <strong>...</strong>
											    					</c:when>
											    					<c:when test="${not empty parts.description && fn:length(parts.description) <= 10}">
											    						${parts.description}
											    					</c:when>
											    					<c:otherwise>&nbsp;</c:otherwise>
						
					</c:choose>
							
							</div>
					</td>
						
					<td id='partStatus_${status.index+1}' class='' style="padding-top:5px;padding-bottom:5px;"
						align="center" width="14%">
						<div style='word-wrap: break-word'>
						${parts.partStatus}</div>
					</td>
					
					<td id='invoiceNumber_${status.index+1}' class='' style="padding-top:5px;padding-bottom:5px;"
						align="center" width="12%">
						<div style='word-wrap: break-word'>
							${parts.invoiceNo}</div>
					</td>
					<td class="buyer_parts_table_edit" id="invoiceProof_${status.index}" align="center" width="10%">
				     <div>
				        <c:choose>
				          <c:when test="${not empty parts.invoiceDocExists &&(parts.invoiceDocExists== 'true')}">
						       <img src="${staticContextPath}/images/common/status-green.png"/>
					      </c:when>
					      <c:otherwise>
					           <img src="${staticContextPath}/images/common/status-red.png"/>
					      </c:otherwise>
					    </c:choose>
					</div>
				  </td>
					
						<td id='quantity_${status.index}' class='' style="padding-top:5px;padding-bottom:5px;"    align="center" width="10%">
							<div style='word-wrap: break-word'>
							${parts.qty}</div>			
					</td>
					
					

					
					<td id='unitCost_${status.index}'
						class='' style="padding-top:5px;padding-bottom:5px;padding-right: 4px;"   align="right" width="10%">
						<c:if test="${parts.unitCost != '0.00'}">
				    		$${parts.unitCost}
				    	</c:if>
					</td>
						
					<td class="buyer_parts_table_edit retailPrice" id="retailPrice_${status.index}" align="right"  width="10%;" style="padding-top:5px;padding-bottom:5px;padding-right: 4px;">
				         $${parts.retailPrice}
				    </td>	
						
						<td colspan="2" class="" align="right"
						id='currencysymbol_${#status.index+1}' style="padding-top:5px;padding-bottom:5px;padding-right: 4px;" width="10%">
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
				<td colspan="8" style="padding-left: 490px; padding-top:5px;padding-bottom:5px;"><b>Sub Total
						for Parts</b></td>
				<td colspan="1" style="padding-top:5px;padding-bottom:5px;padding-right: 4px;" align="right"><b>$ <span
						class="payment" id="totalPayment" bgcolor="#FFFFFF"> <fmt:formatNumber
								value="${totalNetPartPayment}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" /></span></b></td>
			</tr>
		</tfoot>
	</table>
	<div id="loadingImage"></div>
	<div id="reImbursementRate" style="visibility: hidden;"></div>
	<div id="finalTotalPartPrice" style="visibility: hidden;">
		<fmt:formatNumber value="${totalFinalPartPayment}" type="NUMBER"
			minFractionDigits="2" maxFractionDigits="2" />
				
			
	</div>
</div>
</div>		
	
    