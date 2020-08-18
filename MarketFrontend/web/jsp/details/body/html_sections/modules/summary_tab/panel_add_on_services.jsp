<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
function expandAddOnOrderExpressMenu(path)
{
var imgId='orderExpress';
var ob = jQuery("."+imgId).attr("src");
	var menu ="";
	menu = imgId+ "Menu";
	if(ob.indexOf('arrowDown')!=-1){
		jQuery("."+imgId).attr("src",path+"/images/widgets/arrowRight.gif");
		jQuery("."+menu).slideToggle("slow");
	}
}

function resetAddOnTotalsForHSR(){
	
	jQuery('div#toggle').toggle();
	jQuery('td.col3').children('select').val('0');
	jQuery('.col4,.col5').children('[type=text]').val('');
	jQuery('#togglePayment').css('display', 'none');
	//addOnObject.doTotals();
	if(jQuery('#toggle').css('display')=="block"){			
			var custprice ='${model.addonCustomerPrice}';
			var customerPrice=jQuery(".customerTotalVal").text();			
			if(customerPrice=="" || parseFloat(customerPrice)==0.00){
				jQuery('#togglePayment').css('display', 'none');
				document.getElementById('endCustomerDueFinalTotal').value = custprice;
				jQuery(".addOnValue").text('0.00');
				jQuery(".customerTotalVal").text('0.00');
				jQuery("#addonServicesDTO.endCustomerSubtotalTotal").val('0.00');
			}else{
				document.getElementById('endCustomerDueFinalTotal').value = customerPrice;
			} 
		}
    else
    {
    	//jQuery('#togglePayment').css('display', 'none');
    	jQuery(".customerTotalVal").text('0.00');
    	document.getElementById('endCustomerDueFinalTotal').value='0.00';
    	jQuery(".addOnPricing").empty();
		jQuery(".addOnEstPricing").empty();
		jQuery('.addOnPrice').hide();
		if(jQuery('#pricingsummary').length > 0){
			calculatPricingSummary();
		}	
	} 
		document.getElementById('endCustomerDueFinalTotal').value = '0.00';
		jQuery(".customerTotalVal").text('0.00')
		var objEndCustomerSubtotalTotal = document.getElementById('addonServicesDTO.endCustomerSubtotalTotal');
        objEndCustomerSubtotalTotal.value = "";
        var providerPaidTotal = document.getElementById('addonServicesDTO.providerPaidTotal');
        providerPaidTotal.value = "";
        var checkNumber = document.getElementById('addonServicesDTO.checkNumber');
        checkNumber.value = "";
        var checkAmount = document.getElementById('addonServicesDTO.checkAmount');
        checkAmount.value = "";
        var endCustomerAddOnSubtotalTotal = document.getElementById('endCustomerAddOnSubtotalTotal');
        endCustomerAddOnSubtotalTotal.value = "0.00";
        var endHidCustomerAddOnSubtotalTotal = document.getElementById('hidEndCustomerAddOnSubtotalTotal');
        endHidCustomerAddOnSubtotalTotal.value = "";        
}

function calcOnPricingSummary()
{
var laborNetPrice=0.0;
var materialNetPrice=0.0;
var partsNetPrice=0.0;
var addOnPrice=0.0;
var netTotal1 =0.0;
var netTotal=0.0;
addOnPrice=jQuery(".addOnPricing").text();
laborNetPrice = jQuery(".laborPricing").text();
materialNetPrice=jQuery(".partsPricing").text();
partsNetPrice=jQuery(".partsInPricing").text();
laborNetPrice=laborNetPrice.replace("$", "");
materialNetPrice=materialNetPrice.replace("$", "");
partsNetPrice=partsNetPrice.replace("$", "");
addOnPrice=addOnPrice.replace("$", "");
if(laborNetPrice =='')
{
laborNetPrice=0.0; 
}
if(materialNetPrice=='')
{
materialNetPrice=0.0;

}
if(partsNetPrice=='')
{
partsNetPrice=0.0;
}
if(addOnPrice=='')
{
addOnPrice=0.0;
}

var netTotal = parseFloat(laborNetPrice) + parseFloat(materialNetPrice)+parseFloat(partsNetPrice)+parseFloat(addOnPrice);
var net=addOnObject.fmtMoney(netTotal);
jQuery(".totalServiceOrderPrice").html("$"+net);
jQuery(".serviceLiveFee").html("- $"+addOnObject.fmtMoney(net*0.1));
jQuery(".netPaymentFee").html("$"+addOnObject.fmtMoney(net-net*0.1));

}

function addOnClick(path)
{
    jQuery("#addon p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next('#addOnId').slideToggle(300);
       var ob=document.getElementById('addonImg').src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('addonImg').src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('addonImg').src=path+"/images/widgets/arrowRight.gif";
}
}

</script>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<s:set name="totalElms" value="%{addonServicesDTO.addonServicesList.size()}" />
<c:set var="permitCount" value="0"/>
<c:if test="${!empty model.addonServicesDTO.addonServicesList}">
<input type="hidden" name="displayTax" id="displayTax" value="${displayTax}" />
	
	<div id="addon" class="menugroup_list" >
	<div  id="addOnId">
		<div id="addon-services">
		 
			<div class="confirm">
				<c:if test="${currentSO.buyerID == '3000'}"> 
					<hr color="black"/>
					<div style="background-color: silver; width: 25%; padding-left: 15px; color: white; font-weight: bold;">
	                  Additional Services
				    </div>				
				</c:if>												
				<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%" width="85%" cellspacing="0" cellpadding="0">
						<thead>
							<tr style=" background: #00A0D2; color: #FFF;text-align: left;height:30px;">
								<td align="left" colspan="5" style="text-align: left;" >
			<c:choose>				
			<c:when test="${currentSO.buyerID != '3000'}"> 
				<c:choose>
				<c:when test="${addonServicesDTO.addonCheckbox || model.addonCustomerPrice>0}" >			
				<input type="checkbox"
					id="addonServicesDTO.addonCheckbox" style="border: 1px solid #C6C6C6;"
					name="addonServicesDTO.addonCheckbox"
					checked 
					onclick="resetAddOnTotals();"
				/> 
				</c:when>
				<c:otherwise>
				<input type="checkbox"
					id="addonServicesDTO.addonCheckbox" style="border: 1px solid #C6C6C6;"
					name="addonServicesDTO.addonCheckbox"
					onclick="resetAddOnTotals();"
				/>
				</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:choose>
				<c:when test="${addonServicesDTO.addonCheckbox || model.addonCustomerPrice>0}" >			
				<input type="checkbox"
					id="addonServicesDTO.addonCheckbox" style="border: 1px solid #C6C6C6;"
					name="addonServicesDTO.addonCheckbox"
					checked 
					onclick="resetAddOnTotalsForHSR();"
				/> 
				</c:when>
				<c:otherwise>
				<input type="checkbox"
					id="addonServicesDTO.addonCheckbox" style="border: 1px solid #C6C6C6;"
					name="addonServicesDTO.addonCheckbox"
					onclick="resetAddOnTotalsForHSR();"
				/>
				</c:otherwise>
				</c:choose>
			</c:otherwise>
			</c:choose>
				<span style="text-align:left;font-family:sans-serif; font-size: 11px;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message bundle="${serviceliveCopyBundle}" key="details.completepayment.addon.msg2" />(customer paid on-site)**</b></span></td>
								
							</tr>
						</thead>
			</table>
			
			</div>
			<s:set name="endCustomerChargeTotal" value="%{addonServicesDTO.sumOfEndCustomerChargesMinusMisc}" />
							<s:set name="providerPaidTotal" value="%{addonServicesDTO.providerPaidTotal}" />
							<s:set name="endCustomerSubtotalTotal" value="%{addonServicesDTO.endCustomerSubtotalTotal}" />
							<s:hidden id="endCustomerSubtotalTotal" name="addonServicesDTO.hiddenEndCustomerSubtotalTotal" /> 
			<c:choose>
			<c:when test="${addonServicesDTO.addonCheckbox || addonServicesDTO.endCustomerSubtotalTotal>0}" >
				<div id="toggle" style="display:block">
			</c:when>
			<c:otherwise>
				<div id="toggle" style="display:none">
			</c:otherwise>
 			</c:choose>
			
			
			<div class="upsell">
					<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%" width="85%" cellspacing="0" cellpadding="0">
						<thead>
							<tr style="border-bottom: 0px">
								
								<td style="border-bottom: 0px" colspan="3"></td>
								<td style="text-align:center;font-size: 11px;border-bottom: 0px"><b>Provider Price <c:if test="${ displayTax }"> (Inc. Tax) </c:if></b></td>
								<c:if test="${ displayTax }"><td style="text-align:center;font-size: 11px;border-bottom: 0px"><b>Provider Tax</b></td></c:if>
<!-- 								<td style="text-align: center;font-size: 11px;border-bottom: 0px"><b>Customer<br/>&nbsp;&nbsp;Charge</b></td> -->
								<td style="text-align: center;font-size: 11px;border-bottom: 0px"><b>Customer  Charge <c:if test="${ displayTax }"> (Inc. Tax) </c:if></b></td>
							</tr>
						</thead>
						<tbody id="addOnDetails">
							
						
							<s:iterator value="addonServicesDTO.addonServicesList" status="status">
							<s:hidden
									id="addonServicesDTO.addonServicesList[%{#status.index}].addonId"
									name="addonServicesDTO.addonServicesList[%{#status.index}].addonId"
									value="%{addonServicesDTO.addonServicesList[#status.index].addonId}"
									/>
								<s:set name="index" value="#status.index" />
								<s:set name="description" value="%{addonServicesDTO.addonServicesList[#status.index].description}" />
								
								<s:hidden
									id="addonServicesDTO.addonServicesList[%{#status.index}].sku"
									name="addonServicesDTO.addonServicesList[%{#status.index}].sku"
									value="%{addonServicesDTO.addonServicesList[#status.index].sku}"
									/>
								
								<s:hidden
									id="addonServicesDTO.addonServicesList[%{#status.index}].skuGroupType"
									name="addonServicesDTO.addonServicesList[%{#status.index}].skuGroupType"
									value="%{addonServicesDTO.addonServicesList[#status.index].skuGroupType}"
									/>
								<s:if test="%{addonServicesDTO.addonServicesList[#status.index].misc == true && addonServicesDTO.addonServicesList[#status.index].autoGenInd!=true}">
									<tr class="addOnClass">
										<td  class="textcenter" style="width:10%">
											<s:label value="%{addonServicesDTO.addonServicesList[#status.index].sku}" theme="simple"/>
										</td>
										<s:if  test="%{addonServicesDTO.addonServicesList[#status.index].sku == '99888'}">
										<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].description"
												name="addonServicesDTO.addonServicesList[%{#status.index}].description"
												value=""
												/>
										<td class="textleft" style="width:45%">
											<label>PERMIT</label>
											
										<s:select cssStyle="width: 180px;" cssClass="addonPermitClass" id="addonServicesDTO.addonServicesList[%{#status.index}].permitType" headerKey="-1" headerValue="-Select Permit Type-"
														 name="addonServicesDTO.addonServicesList[%{#status.index}].permitType" list="#attr.permitTypeList"
														listKey="id" listValue="type" multiple="false" 
														size="1" theme="simple" onchange="addOnObject.doTotals(%{totalElms})"/>
										<c:set var="permitCount" value="${permitCount+1}"/>
										<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].addonPermitTypeId"
												name="addonServicesDTO.addonServicesList[%{#status.index}].addonPermitTypeId"
												value="%{addonServicesDTO.addonServicesList[#status.index].addonPermitTypeId}"
												/>
										</td>
										</s:if>
										<s:else>
										<td class="textleft" style="width:45%">
											<label>Description</label>
											
											<s:textarea
												id="addonServicesDTO.addonServicesList[%{#status.index}].description"
												name="addonServicesDTO.addonServicesList[%{#status.index}].description"
												theme="simple" cssClass="text" cssStyle="width: 250px" rows="1" 
												/>
										</td>
										</s:else>
										<td style="width:12%" class="col3">
											<s:select
												id="addonServicesDTO.addonServicesList[%{#status.index}].quantity"
												name="addonServicesDTO.addonServicesList[%{#status.index}].quantity"
												theme="simple" cssClass="text" cssStyle="width: 50px"
												onchange="addOnObject.resetPermitType(%{index});addOnObject.calcNewSubtotalForMisc( %{index}, %{totalElms}, this, true )"
												list="#{'0':'Qty.','1':'1'}"
												/>
											<s:hidden
												id="qty_%{#status.index}"
												name="qty_%{#status.index}"
												value="%{addonServicesDTO.addonServicesList[#status.index].quantity}"											 
											/>
												
										</td>
										<td style="width:11%;text-align:right;" class="col4">
										<s:if test="%{getText('format.money', '0.00', {addonServicesDTO.addonServicesList[#status.index].providerPaid}) =='null'}"> 
											<s:textfield
												id="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid"
												name="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid" 
												
												theme="simple" cssClass="text" cssStyle="color: #666; padding: 0px; width: 60px; border: 0px; text-align:right; background: transparent;"
												readonly="true"
												/>
										</s:if> 
										<s:else> 
											<s:textfield
												id="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid"
												name="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid" 
												value="%{getText('format.money', '0.00', {addonServicesDTO.addonServicesList[#status.index].providerPaid})}"
												theme="simple" cssClass="text" cssStyle="color: #666; padding: 0px; width: 60px; border: 0px; text-align:right; background: transparent;"
												readonly="true"
												/>
										</s:else> 
										</td>
										
										<c:if test="${displayTax}">
											<td style="width:11%;text-align:right;" class="col4"> 
												<s:hidden
													id="addonServicesDTO.addonServicesList[%{#status.index}].taxPercentage"
													name="addonServicesDTO.addonServicesList[%{#status.index}].taxPercentage"
													theme="simple" cssClass="text" cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													value="%{addonServicesDTO.addonServicesList[#status.index].taxPercentage}" disabled="true"
													/>
													<s:hidden
													id="addonServicesDTO.addonServicesList[%{#status.index}].tax"
													name="addonServicesDTO.addonServicesList[%{#status.index}].taxPercentage"
													theme="simple" cssClass="text"  cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													value="%{addonServicesDTO.addonServicesList[#status.index].providerTax}"
													/>
													
												<!-- start	code changes for SLT-2572 -->
													<s:hidden
													id="addonServicesDTO.addonServicesList[%{#status.index}].misc"
													name="addonServicesDTO.addonServicesList[%{#status.index}].misc"
													theme="simple" cssClass="text"  cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													value="%{addonServicesDTO.addonServicesList[#status.index].misc}"
													/>
													<s:hidden
													id="addonServicesDTO.addonServicesList[%{#status.index}].tempEnsCust"
													name="addonServicesDTO.addonServicesList[%{#status.index}].tempEnsCust"
													value ="0.0"
													theme="simple" cssClass="text"  cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													/>
														<!-- end	code changes for SLT-2572 -->
												<!-- <s:textfield
													id="addonServicesDTO.addonServicesList[%{#status.index}].providerTax"
													name="addonServicesDTO.addonServicesList[%{#status.index}].providerTax"
													value="%{getText('format.money', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal * addonServicesDTO.addonServicesList[#status.index].taxPercentage / (1 + addonServicesDTO.addonServicesList[#status.index].taxPercentage)})}"
													theme="simple" cssClass="text" cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													disabled="true" /> -->
											</td>
										</c:if>
										
										
										<td style="width:11%" class="colAddOn col5" id="changeAddon">
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerCharge"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerCharge"
												theme="simple" cssClass="text"  cssStyle="text-align:right"
												/>
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].margin"
												name="addonServicesDTO.addonServicesList[%{#status.index}].margin"
												/>
											<s:if test="%{getText('format.money', '0.00', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal}) =='null'}"> 
												<s:textfield
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"	
												
												onchange="addOnObject.resetPermitType(%{index});addOnObject.calcNewSubtotalForMisc(%{index}, %{totalElms}, this);"
												theme="simple" cssClass="text"  cssStyle="width: 60px; text-align:right"
												/>
											</s:if>
											<s:else>
												<s:textfield
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"	
												value="%{getText('format.money','0.00', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal})}"
												onchange="addOnObject.resetPermitType(%{index});addOnObject.calcNewSubtotalForMisc(%{index}, %{totalElms}, this);"
												theme="simple" cssClass="text"  cssStyle="width: 60px; text-align:right"
												/>
											</s:else>

										</td>
									</tr>									
								</s:if>
								<s:else>
								<s:if test="%{addonServicesDTO.addonServicesList[#status.index].misc != true }">
									<c:set var="fontWeight" value=""/>
									<c:set var="strikePriceStyle" value=""/>
									<s:if test="%{addonServicesDTO.addonServicesList[#status.index].coverage == 'CC'}">
										<c:set var="fontWeight" value="font-weight:bold"/>
									</s:if>
									<s:if test="%{addonServicesDTO.addonServicesList[#status.index].skipReqAddon}">
										<c:set var="strikePriceStyle" value="text-decoration:line-through"/>
									</s:if>
									<tr class="addOnClass">
										<td style="width:10%" class="textcenter">
											<s:label value="%{addonServicesDTO.addonServicesList[#status.index].sku}" theme="simple"
												cssStyle="%{fontWeight}"/>
										</td>
										<td style="width:45%" class="textleft">
										
										
											<s:label
												id="addonServicesDTO.addonServicesList[%{#status.index}].description"
												name="addonServicesDTO.addonServicesList[%{#status.index}].description"
												value="%{description}"
												theme="simple"
												cssClass="text"
												cssStyle="width: 250px;%{fontWeight}"
											/>
										
										</td>
										<s:if test="%{addonServicesDTO.addonServicesList[#status.index].coverage == 'CC'}">
										<td style="width:12%" class="col3">
											<s:label
												value="%{addonServicesDTO.addonServicesList[#status.index].quantity}"
												theme="simple"
												cssClass="text"
												cssStyle="width: 40px;font-weight:bold"
											/>
											
											<s:hidden
												id="qty_%{#status.index}"
												name="qty_%{#status.index}"
												value="%{addonServicesDTO.addonServicesList[#status.index].quantity}"											 
											/>
											<!-- 
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].quantity"
												name="addonServicesDTO.addonServicesList[%{#status.index}].quantity"
												value="%{addonServicesDTO.addonServicesList[#status.index].quantity}"											 
											/>
											-->
										</td>
										<td style="width:11%;text-align:right;" class="col4">
											<s:label
												id="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid"
												name="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid"
												value="%{addonServicesDTO.addonServicesList[#status.index].providerPaid}"
												theme="simple"
												cssClass="text"
												cssStyle="width: 60px;font-weight:bold;%{strikePriceStyle}"/>
											
											<s:if test="%{addonServicesDTO.addonServicesList[#status.index].skipReqAddon}" > 
											   (Included in Maximum Price)
											</s:if>
																						
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].skipReqAddon"
												name="addonServicesDTO.addonServicesList[%{#status.index}].skipReqAddon"
												value="%{addonServicesDTO.addonServicesList[#status.index].skipReqAddon}"
											/>
	
										</td>
										
										<c:if test="${displayTax}">
											<td style="width:11%;text-align:right;" class="col4"> 
												<s:hidden
													id="addonServicesDTO.addonServicesList[%{#status.index}].taxPercentage"
													name="addonServicesDTO.addonServicesList[%{#status.index}].taxPercentage"
													theme="simple" cssClass="text"  cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													value="%{addonServicesDTO.addonServicesList[#status.index].taxPercentage}" disabled="true"
													/>
												<s:textfield
													id="addonServicesDTO.addonServicesList[%{#status.index}].providerTax"
													name="addonServicesDTO.addonServicesList[%{#status.index}].providerTax"
													value="%{getText('format.money', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal * addonServicesDTO.addonServicesList[#status.index].taxPercentage / (1 + addonServicesDTO.addonServicesList[#status.index].taxPercentage)})}"
													theme="simple" cssClass="text" cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													disabled="true" />
											</td>
										</c:if>
										
										<td style="width:11%" class="col5">
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].quantity"
												name="addonServicesDTO.addonServicesList[%{#status.index}].quantity"
												/>
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerCharge"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerCharge"
												/>
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].margin"
												name="addonServicesDTO.addonServicesList[%{#status.index}].margin"
												/>
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"
												value="%{addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal}"
												/>
											<s:if test="%{getText('format.money', '0.00', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal}) =='null'}"> 
												<s:label
													value="%{addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal}"
													theme="simple"
													cssClass="text"
													cssStyle="width: 60px;font-weight:bold"
												/>
											</s:if>
											<s:else>
												<s:label
													value="%{getText('format.money','0.00', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal})}"
													theme="simple"
													cssClass="text"
													cssStyle="width: 60px;font-weight:bold"
												/>
											</s:else>
										</td>
										</s:if>
										<s:else>
										<td style="width:12%" class="col3">
											<s:select
												id="addonServicesDTO.addonServicesList[%{#status.index}].quantity"
												name="addonServicesDTO.addonServicesList[%{#status.index}].quantity"
												onchange="addOnObject.calcNewSubtotal( %{index}, %{totalElms} )"
												theme="simple" cssClass="text" cssStyle="width: 50px"
												list="#{'0':'Qty.','1':'1','2':'2','3':'3','4':'4','5':'5'}"
												/>
												
											<s:hidden
												id="qty_%{#status.index}"
												name="qty_%{#status.index}"
												value="%{addonServicesDTO.addonServicesList[#status.index].quantity}"											 
											/>
												
										</td>
										<td style="width:11%;text-align:right;" class="col4">
											<s:if test="%{getText('format.money', '0.00', {addonServicesDTO.addonServicesList[#status.index].providerPaid}) =='null'}"> 
											<s:textfield
												id="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid"
												name="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid"
												
												theme="simple" cssClass="text" cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
												disabled="true"
												/>
											</s:if>
											<s:else>
											<s:textfield
												id="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid"
												name="addonServicesDTO.addonServicesList[%{#status.index}].providerPaid"
												value="%{getText('format.money', {addonServicesDTO.addonServicesList[#status.index].providerPaid})}"
												theme="simple" cssClass="text" cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
												disabled="true"
												/>

											</s:else>
										</td>
										
										<c:if test="${displayTax}">
											<td style="width:11%;text-align:right;" class="col4"> 
												<s:hidden
													id="addonServicesDTO.addonServicesList[%{#status.index}].taxPercentage"
													name="addonServicesDTO.addonServicesList[%{#status.index}].taxPercentage"
													theme="simple" cssClass="text"  cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													value="%{addonServicesDTO.addonServicesList[#status.index].taxPercentage}" disabled="true"
													/>
													
												<s:textfield
													id="addonServicesDTO.addonServicesList[%{#status.index}].providerTax"
													name="addonServicesDTO.addonServicesList[%{#status.index}].providerTax"
													value="%{getText('format.money', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal * addonServicesDTO.addonServicesList[#status.index].taxPercentage / (1 + addonServicesDTO.addonServicesList[#status.index].taxPercentage)})}"
													theme="simple" cssClass="text" cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													disabled="true" />
											</td>
										</c:if>
										
										<td style="width:11%" class="col5" id="ch">
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerCharge"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerCharge"
												/>
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].margin"
												name="addonServicesDTO.addonServicesList[%{#status.index}].margin"
												/>
											<s:if test="%{getText('format.money', '0.00', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal}) =='null'}"> 
											<s:textfield
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"
												
												theme="simple" cssClass="text"  cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"												 
												/>
											</s:if>
											<s:else>
												<s:textfield
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerSubtotal"
												value="%{getText('format.money', {addonServicesDTO.addonServicesList[#status.index].endCustomerSubtotal})}"
												theme="simple" cssClass="text"  cssStyle="color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"												 
												/>
											</s:else>
										</td>
										</s:else>								
									</tr>
									</s:if>
									<s:if test="%{addonServicesDTO.addonServicesList[#status.index].misc == true && addonServicesDTO.addonServicesList[#status.index].autoGenInd==true}">
									<s:hidden cssClass="addOnClass"
												id="qty_%{#status.index}"
												name="qty_%{#status.index}"
												value="0"											 
											/>
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerCharge"
												name="addonServicesDTO.addonServicesList[%{#status.index}].endCustomerCharge"
												theme="simple" cssClass="text"  cssStyle="text-align:right"
												/>
											<s:hidden
												id="addonServicesDTO.addonServicesList[%{#status.index}].margin"
												name="addonServicesDTO.addonServicesList[%{#status.index}].margin" value="0.0"
												/>
									</s:if>
								</s:else>
																	
							</s:iterator>
							
							<s:hidden
												id="countVar"
												name="countVar"
												value="%{index}"											 
											/>
											
							<c:if test="${model.taskLevelPricing && permitCount<4}">
							<tr>
								<td colspan="2"  style="border-bottom: 2px solid #CCCCCC;text-align: left;"><a href="javascript:void(0)" id="addPermit" name="addPermit" onclick="addPermitAddon();return false;">MORE ADD-ON PERMITS (+)</a></td>
								<td style="border-bottom: 2px solid #CCCCCC;" colspan="3"></td>
								
							<tr>
							</c:if>							
							
							<c:choose>
								<c:when test="${empty model.addonServicesDTO.addonServicesList}">
									<tr>
										<td colspan="6" class="odd textleft" style="border-left: 1px solid #CCC; border-right: 1px solid #CCC">
											<fmt:message bundle="${serviceliveCopyBundle}" key="details.completepayment.addon.msg4" />
										</td>
									</tr>
								</c:when>
								<c:otherwise>							
									<tr style="background: #CCCCCC; color: #FFF; border-bottom: 1px solid #CCCCCC;">
										<td colspan="2" style="width:55%;border: 0px; text-align: right;border-right: 1px solid #CCCCCC">
										</td>
										
										<td style="width:11%;border: 0px; text-align: right;border-right: 1px solid #CCC;color: #666">
											<strong>Totals</strong>
										</td>
										
										<td style="width:11%;border: 0px;border-right: 1px solid #CCC;text-align:right;">
											<strong>
												<s:set name="providerPaidTotal" value="%{addonServicesDTO.providerPaidTotal}" />
												<input type="text" id="addonServicesDTO.providerPaidTotal"
													name="addonServicesDTO.providerPaidTotal"
													value='<fmt:formatNumber value="${model.addonProviderPrice}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>'
													theme="simple" class="text"  style="font-weight: bold; color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
													disabled="true"
													/>
	
											</strong>
										</td>
										
										<c:if test="${displayTax}">
											<td style="width:11%;text-align:right;" class="col4"> 
												<strong>
													<s:set name="providerTaxPaidTotal" value="%{addonServicesDTO.providerTaxPaidTotal}" />
													<input type="text" id="addonServicesDTO.providerTaxPaidTotal"
														name="addonServicesDTO.providerTaxPaidTotal"
														value='<fmt:formatNumber value="${model.providerTaxPaidTotal}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>'
														theme="simple" class="text"  style="font-weight: bold; color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
														disabled="true"
														/>
	
												</strong>
											</td>
										</c:if>
										
										<td style="display: none">
											<input type="hidden"  id="addOnValue" />
                                           	<span  class="customerTotalVal"></span>
                                            
											<span name="slf" id="addOn" class="addOnValue" ></span>
										</td>
										
										<td style="width:15%;border: 0px; text-align: right;border-right: 1px solid #CCCCCC" id="foo" >
											<strong>
													<s:set name="endCustomerSubtotalTotal" value="%{addonServicesDTO.endCustomerSubtotalTotal}" />	
													<input type="text" id="addonServicesDTO.endCustomerSubtotalTotal"
														name="addonServicesDTO.endCustomerSubtotalTotal"
														value='<fmt:formatNumber value="${model.addonCustomerPrice}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>'
														theme="simple" class="text"  style="font-weight: bold; color: #666; padding: 0px; width: 60px; text-align:right; border: 0px; background: transparent;"
														disabled="true"/>
													
											</strong>
										</td>
								</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				
				
			</div>




			
			</div>
			
			
		</div>
		</div>

</c:if>	
<%--Changes for Only HSR --%>
<c:if test="${currentSO.buyerID == '3000'}">
	<script >
	var myAddOn;
	jQuery("#endCustomerDueFinalTotal").val('${model.addonCustomerPrice}');
	myAddOn=setTimeout(function(){addOnShow()},5000);
	function addOnShow()
	{
		var price= '${model.addonProviderPrice}';
		var custprice ='${model.addonCustomerPrice}';
		if(price>0.0)
		{
		var ab=addOnObject.fmtMoney(price);
		var addsOn=price-(price*0.1);
		var addprice=addOnObject.fmtMoney(addsOn);
		var custPrice=addOnObject.fmtMoney(custprice);
		jQuery('.pricewidget').show();jQuery('.addOnPrice').show();
		jQuery(".addOnPricing").html("$"+ab);
		jQuery(".addOnEstPricing").html("$"+addprice);
		jQuery("#endCustomerDueFinalTotal").val(custprice);
		calcOnPricingSummary();
		jQuery('.pricewidget').show();jQuery('.addOnPrice').show();
		}
	}
	
	jQuery('.colAddOn').children("input.text").change(function() {
		var addonprice=jQuery(".addOnValue").text();
		var customerPrice=jQuery(".customerTotalVal").text();
		if(addonprice>=0.0)
		{
		jQuery('.pricewidget').show();jQuery('.addOnPrice').show();
		jQuery(".addOnPricing").html("$"+formatAsMoney(addonprice));
		var a=formatAsMoney(addonprice);
		jQuery(".addOnPricing").html("$"+a);
		jQuery(".addOnEstPricing").html("$"+formatAsMoney(a-a*0.1));
		jQuery("#endCustomerDueFinalTotal").val(customerPrice);
		var checkAmount = document.getElementById('addonServicesDTO.checkAmount');
		checkAmount.value = "";
		calcOnPricingSummary();
		expandAddOnOrderExpressMenu('${staticContextPath}');
		}		
		var finalAmt= jQuery("#endCustomerDueFinalTotal").val();
		if(finalAmt =="" || parseFloat(finalAmt) ==0.00){
			jQuery("#endCustomerDueFinalTotal").val('0.00');
			jQuery('#togglePayment').css('display', 'none');
		}else{
			jQuery('#togglePayment').css('display', 'block');
		}
	 });
	</script>
</c:if>