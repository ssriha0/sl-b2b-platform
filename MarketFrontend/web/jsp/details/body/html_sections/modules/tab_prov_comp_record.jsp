<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:directive.page import="com.newco.marketplace.interfaces.OrderConstants"/>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link href="${staticContextPath}/javascript/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ui-personalized-1.5.2.packed.js"></script>
<script src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js" type="text/javascript"></script>
     <script type="text/javascript">
      var compRecord="true";
      jQuery("#completionRecord").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}&closeAndPayTab="+ compRecord);
function expandCompRecParts(path)
{
jQuery("#generalcompRecord p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#generalcompRecordId").slideToggle(300);

var ob=document.getElementById('compRecPartImg').src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('compRecPartImg').src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('compRecPartImg').src=path+"/images/widgets/arrowRight.gif";
}
}

      </script>
<style>

td.installed_parts_odd {     
		border-bottom: 1px solid #CCC;
		padding-top: 10px;
		padding-bottom:10px;
		background-color:#00A0D2;
        color:white;
	}
	td.installed_parts_even{
    padding-top: 10px;
	padding-bottom:10px;
	background-color:#386BB7;
	border-bottom: 1px solid #CCC;
	color:white;
	}

</style>
       <div class="soNote">

            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">                  <jsp:param name="PageName" value="ServiceOrderDetails.provCompletionRecord"/>            </jsp:include>            <!-- START RIGHT SIDE MODULES --><!-- END RIGHT SIDE MODULES --> <div id="rightsidemodules" class="colRight255 clearfix" >
           
         
         <p id="completionRecord" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span></span></p>
         
      
       
    </div>


<div class="contentPane">
<c:set	var="soCloseDto" value="${soCloseDto}" scope="request" />
<c:set	var="status" value="${soStatus}" scope="request" />
<!-- Display server side message -->
<c:if test="${soStatus == 180}">
		<div  style="background-color:#F0FFF0; color: #006400; border:solid 2px #B4EEB4;">
		<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${msg}</b>
 </div>
 </c:if>
 <c:if test="${soStatus == 120}">
		<div  style="background-color:#F0FFF0; color: #006400; border:solid 2px #B4EEB4;">
		<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${msg}</b>
 </div>
 </c:if>
 <c:if test="${Message == 'You have successfully completed the order. The buyer will review this order for payment processing.'}">
		<div  style="background: #ffc url(${staticContextPath}/images/icons/conditional_exclamation.gif) no-repeat 3px 6px; border: solid 1px #fc6;">
		<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${Message}"></c:out></b> 
 </div>
 </c:if>
  <c:if test="${Message != 'You have successfully completed the order. The buyer will review this order for payment processing.'}">
 <div class="errorBox">
     <c:set var="msg" value="${msg}"/>
   <c:out value="${msg}" />
	<p class="errorMsg"> &nbsp;&nbsp;&nbsp;&nbsp;
		Your documents have been submitted to the buyer for payment. A summary
		of your completion record is below.
	</p>
</div>
</c:if>
<%request.removeAttribute("msg"); %>
<c:remove var="Message"/>
<c:if test="${viewOrderPricing==true}">
	<div id="generalcompRecord" class="menugroup_list">
  <p class="menugroup_head" onclick="expandCompRecParts('${staticContextPath}')">&nbsp;<img id="compRecPartImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;General Completion Information</p>
    <div class="menugroup_body" id="generalcompRecordId">	
		<p>
			<strong>Resolution Comments: </strong>${soCloseDto.resComments}
		</p>		
		<tr>
		<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%" width="85%" cellspacing="0" cellpadding="0">
<tr>
<th colspan="5" style="text-align: left;">Service Order Pricing</th>
					</tr>
					
							<tr>	
								
								
								<td colspan="3" style="text-align:right;"><b>Maximum Price</b></td>
								<td style="width:18%;text-align:right;"><b>Final Price</b></td>
								<c:if test="${!empty soCloseDto.permitTaskList}">
								<td style="width:15%;text-align:right;"><b>Customer<br/>&nbsp;&nbsp;Charge</b></td>
								</c:if>
								<c:if test="${empty soCloseDto.permitTaskList}">
								<td style="width:15%;text-align:right;">&nbsp;</td>
								</c:if>
							</tr>	
							
							<tr>	
								
								
								<td colspan="2" style="text-align:left;">Labor</td>
								<td style="width:12%;text-align:right;">$<fmt:formatNumber value="${soCloseDto.soMaxLabor / (1 + laborTaxPercentage)}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
					</td>
					<td style="width:18%;text-align:right">
				$<fmt:formatNumber value="${soCloseDto.soFinalMaxLabor/ (1 + laborTaxPercentage)}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
					<td style="width:15%"></td>
							</tr>
							
							<c:if test="${ displayTax }">
							<tr>	
								<td colspan="2" style="text-align:left;">Labor Tax: (<fmt:formatNumber value="${laborTaxPercentage * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%)</td>
								<td style="width:12%;text-align:right;">$<fmt:formatNumber value="${(soCloseDto.soMaxLabor * laborTaxPercentage) / (1 + laborTaxPercentage)}" type="NUMBER"
										minFractionDigits="2" maxFractionDigits="2" />
								</td>
								<td style="width:18%;text-align:right">
									$<fmt:formatNumber value="${(soCloseDto.soFinalMaxLabor * laborTaxPercentage) / (1 + laborTaxPercentage)}" type="NUMBER"
									minFractionDigits="2" maxFractionDigits="2" />
								<td style="width:15%"></td>
							</tr>
							</c:if>
							
							<tr>	
								
								
								<td colspan="2" style="text-align:left;">Parts</td>
								<td style="width:12%;text-align:right">$<fmt:formatNumber value="${soCloseDto.partSpLimit / (1+ partsTaxPercentage)}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
					</td>
								<td style="width:18%;text-align:right">
				$<fmt:formatNumber value="${soCloseDto.finalPartPrice / (1+ partsTaxPercentage)}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
	


</td>
								<td style="width:15%"></td>
							</tr>
							
							<c:if test="${ displayTax }">
							<tr>	
								<td colspan="2" style="text-align:left;">Parts Tax: (<fmt:formatNumber value="${partsTaxPercentage * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%)</td>
								<td style="width:12%;text-align:right">$<fmt:formatNumber value="${(soCloseDto.partSpLimit * partsTaxPercentage) / (1+ partsTaxPercentage)}" type="NUMBER"
									minFractionDigits="2" maxFractionDigits="2" />
								</td>
								<td style="width:18%;text-align:right">
									$<fmt:formatNumber value="${(soCloseDto.finalPartPrice * partsTaxPercentage) / (1+ partsTaxPercentage)}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
								</td>
								<td style="width:15%"></td>
							</tr>
							</c:if>
							
<c:if test="${!empty soCloseDto.permitTaskList}">


								
								<c:forEach var="permitTask" items="${soCloseDto.permitTaskList}" varStatus="rowCounter">
							<c:set var="index" value="${rowCounter.count-1}" />	
						
							<tr class="permitTaskRow" height="25px">
<td colspan="2" style="text-align:left" align="left">
				<b>Permit</b>(Customer Pre-paid)- ${permitTask.permitTaskDesc}
			
			</td>
			<td style="width:12%;text-align:right;">$
			<fmt:formatNumber value="${permitTask.sellingPrice}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" /></td>
					<td style="width:18%;text-align:right">$
								<fmt:formatNumber value="${permitTask.finalPrice}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />						
					
					
														</td>
														<c:if test="${permitTask.finalPrice-permitTask.sellingPrice>0}">
														<c:set var="custCharge" value="${permitTask.finalPrice-permitTask.sellingPrice}"/>
														</c:if>
														<c:if test="${permitTask.finalPrice-permitTask.sellingPrice<=0}">
														<c:set var="custCharge" value="0.00"/>
														</c:if>
														<td style="width:15%;text-align:right"><label name="permitTaskList[${index}].custCharge" id="permitTaskList[${index}].custCharge"><fmt:formatNumber value="${custCharge}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
			
</tr>
							</c:forEach>
</c:if>
							<tr style="background: #CCCCCC; color: #FFF; border-bottom: 1px solid #CCCCCC;">
							
								<td colspan="2" style="text-align:right;color: #666"><b>Totals</b></td>
								<td style="width:12%;text-align:right;color: #666"><fmt:formatNumber value="${soCloseDto.laborSpLimit+soCloseDto.partSpLimit}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
								<td style="width:18%;color: #666;text-align:right" align="right"><label id="finalWithoutAddons"><fmt:formatNumber value="${soCloseDto.finalPrice+soCloseDto.permitTaskAddonPrice}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
								<c:if test="${!empty soCloseDto.permitTaskList}">
								<td style="width:15%;color: #666;text-align:right" align="right"><label id="custChargeWithoutAddons"><fmt:formatNumber value="${soCloseDto.permitTaskAddonPrice}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
								</c:if>
								<c:if test="${empty soCloseDto.permitTaskList}">
								<td style="width:15%;color: #666;text-align:right" align="right"><label id="custChargeWithoutAddons"></label></td>
								</c:if>
							</tr>


			</table>
			<c:if test = "${soCloseDto.addonServicesDTO.upsellPaymentIndicator}" >
			<c:set var="addonBox" value="0"/>
			<c:forEach var="addonServiceRowDTO" items="${soCloseDto.addonServicesDTO.addonServicesList}" varStatus="rowCounter">
							<c:if test="${addonServiceRowDTO.quantity > 0 && !addonServiceRowDTO.autoGenInd}">
							<c:set var="addonBox" value="1"/>
							</c:if>
							</c:forEach>
			<c:if test="${addonBox=='1'}">
					<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%" width="85%" cellspacing="0" cellpadding="0">
<tr>
<th colspan="5" style="text-align: left;"><fmt:message bundle="${serviceliveCopyBundle}" key="details.completepayment.addon.msg2" />(customer paid on-site)**</th>
					</tr>
					<tr>
					<td colspan="3"></td>
								
								<td style="width:18%;text-align:right;"><b>Provider Price <c:if test="${ displayTax }">(Inc. Tax)</c:if></b></td>
								<c:if test="${ displayTax }">
									<td style="width:18%;text-align:right;"><b>Provider Tax</b></td>
								</c:if>
								<td style="width:15%;text-align:right;"><b>Customer<br/>&nbsp;&nbsp;Charge</b></td>
							</tr>
							<c:forEach var="addonServiceRowDTO" items="${soCloseDto.addonServicesDTO.addonServicesList}" varStatus="rowCounter">
							<c:if test="${addonServiceRowDTO.quantity > 0 && !addonServiceRowDTO.autoGenInd}">
							<tr>
							<c:if test="${addonServiceRowDTO.sku=='99888'}">
								<td colspan="3" style="text-align:left">99888 ADD-ON PERMIT - ${addonServiceRowDTO.description}</td>
								</c:if>
								<c:if test="${addonServiceRowDTO.sku!='99888'}">
								<td colspan="3" style="text-align:left">${addonServiceRowDTO.description}</td>
								</c:if>
								<td style="width:12%;text-align:right"><fmt:formatNumber value="${addonServiceRowDTO.providerPaid}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
								<c:if test="${ displayTax }">
									<td style="width:12%;text-align:right"><fmt:formatNumber value="${addonServiceRowDTO.endCustomerCharge * addonServiceRowDTO.taxPercentage / (1 + addonServiceRowDTO.taxPercentage)}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
								</c:if>
								<td style="width:15%;text-align:right" align="right"><label id="custChargeWithoutAddons"><fmt:formatNumber value="${addonServiceRowDTO.endCustomerCharge}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
								</tr>
								</c:if>
								</c:forEach>
								<tr style="background: #CCCCCC; color: #FFF; border-bottom: 1px solid #CCCCCC;">
							
								<td colspan="3" style="color: #666;text-align:right"><b>Totals</b></td>
								<td style="width:18%;color: #666;text-align:right" align="right"><fmt:formatNumber value="${soCloseDto.addonServicesDTO.paymentAmount-soCloseDto.permitTaskAddonPrice}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
								<c:if test="${ displayTax }">
									<td style="width:18%;color: #666;text-align:right" align="right"><fmt:formatNumber value="${soCloseDto.addonServicesDTO.providerTaxPaidTotal}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
								</c:if>
								<td style="width:15%;color: #666;text-align:right" align="right"><label id="custChargeWithoutAddons"><fmt:formatNumber value="${soCloseDto.addonServicesDTO.endCustomerSubtotalTotal-soCloseDto.permitTaskAddonPrice}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
							</tr>
							</table>
							</c:if>
							</c:if>
							<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%" width="85%" cellspacing="0" cellpadding="0">
				<tr style="border-bottom: 1px solid #CCCCCC;">
							<td colspan="2" style="text-align:right;color: #666;border-right: 1px solid #CCC;"><b>Combined Total Final</b></td>
							<td style="width:11%;background: #BDEDFF; color: #666;text-align:right;border-right: 1px solid #CCC;" align="right"><label id="finalTotal" name="finalTotal"><fmt:formatNumber value="${soCloseDto.finalPrice+soCloseDto.addonServicesDTO.paymentAmount }" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
							<c:if test="${ displayTax }">
								<td style="width: 11%; text-align: right" align="right"></td>
							</c:if>
							<c:choose>
							<c:when
								test="${empty soCloseDto.permitTaskList &&( soCloseDto.addonServicesDTO==null || soCloseDto.addonServicesDTO.endCustomerSubtotalTotal<=0)}">
								<td
									style="width: 11%; background: #BDEDFF; color: #666; text-align: right"
									align="right">
									<label id="custChargeFinal" name="custChargeFinal">
							
									</label>
								</td>
							</c:when>
							<c:otherwise>
								<td
									style="width: 15%; background: #BDEDFF; color: #666; text-align: right"
									align="right">
									<label id="custChargeFinal" name="custChargeFinal">
										
										<fmt:formatNumber
											value="${soCloseDto.addonServicesDTO.endCustomerSubtotalTotal}"
											type="currency" minFractionDigits="2" maxFractionDigits="2"
											currencySymbol="$" />
									</label>
								</td>
							</c:otherwise>
							</c:choose>

							</tr>	
</table>
										
		
	</tr>
	<br><br>	
	<c:if test="${!empty soCloseDto.invoiceParts}">	
			<jsp:include page="prov_comp_part_hsr.jsp"></jsp:include>
		</c:if>	
			
		<p>
		<p>
		<c:if test="${ null == displayTax || !displayTax }">
			<c:if test = "${soCloseDto.addonServicesDTO.upsellPaymentIndicator}" >
			
			<div class="additionalPaymentBox" style="width: 90%">
			<strong>Add-on Services and Permits Funding</strong>
			<br/>
			<br/>
			<table>
			<tr>
			<td style="width: 55%">	
					   <strong>Customer Payment Method: </strong>  
		
					   <br/>
					   <br/>
					   <c:choose>
					   <c:when test="${soCloseDto.addonServicesDTO.paymentByCheck}" >
					   		 <strong>Check</strong>&nbsp;(${soCloseDto.addonServicesDTO.checkNumber})
					   		 ,${soCloseDto.addonServicesDTO.paymentAmountDisplayStr}
					   </c:when>
					   
					   
					   
					   <c:when test="${soCloseDto.addonServicesDTO.paymentByCC}">
					   		 <strong>${soCloseDto.addonServicesDTO.selectedCreditCardTypeStr}</strong>
							 ${soCloseDto.addonServicesDTO.creditCardNumber}
							 
					   </c:when>
					   </c:choose>
					   </td>
					   <td style="width: 9%">
					   </td>
					  
					   
					   <td style="width: 31%">
				        <strong>Submitted </strong>
				       <br/>
				       <br/>
				        ${soCloseDto.addonServicesDTO.paymentReceivedDateDisplayFormattedStr}
				        </td>
				        
				        </tr>
				        </table>
			</div>
			</c:if>
			</c:if>
		
		<c:if test="${status == 160}">
			<table cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td align="left" colspan="2">						 
						<a href="${contextPath}/soDetailsProvCompletionRecordUpdate.action?soId=${SERVICE_ORDER_ID}">
							<img src="${staticContextPath}/images/common/spacer.gif" width="123" height="47"
							style="background-image:url(${staticContextPath}/images/btn/edit_my_completion.gif);"  
							class="btn17"
							/>						 
						</a>							
					</td>
				</tr>
			</table>
		</c:if>	
	</div>
	</div>
</c:if>
	<!-- NEW MODULE/ WIDGET for Document Management-->
	<c:set var="docSource" scope="request" value="<%=(OrderConstants.COMPLETE_FOR_PAYMENT) %>" />
	<jsp:include page="../../../../wizard/body/sections/modules/panels_scope_of_work/panel_documentsAndPhotos.jsp" />
	
</div>
</div>
