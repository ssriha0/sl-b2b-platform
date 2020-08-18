<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<jsp:directive.page import="com.newco.marketplace.interfaces.OrderConstants"/>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">                  <jsp:param name="PageName" value="ServiceOrderDetails.buyerCompletionDocs"/>            </jsp:include><link href="${staticContextPath}/javascript/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ui-personalized-1.5.2.packed.js"></script>
<script src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js" type="text/javascript"></script>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link href="${staticContextPath}/javascript/style.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript">
  var closeandpay="true";
  jQuery("#buyerquicklink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}&closeAndPayTab="+ closeandpay);
  //For priority 5B
  jQuery("#modelSerialSummaryDiv").html("");
 function clickCloseInfo(path)
{
    jQuery("#buyercompdocs p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroup_body").slideToggle(300);
    var ob=document.getElementById('closeImg').src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('closeImg').src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('closeImg').src=path+"/images/widgets/arrowRight.gif";
	}
}
function autocloseclickCompInfo(path)
{

    jQuery("#autocloseinfo p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroup_body").slideToggle(300);
    var ob=document.getElementById('compImg').src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('compImg').src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('compImg').src=path+"/images/widgets/arrowRight.gif";
	}
}

//Priority 5B changes
//save custom reference
function saveCustRef(refType){
	jQuery("#custRefErr").html("");
	jQuery("#custRefErr").hide();
	
	var soId = '${SERVICE_ORDER_ID}';
	var refValue = jQuery.trim(jQuery("#"+refType).val());
	jQuery("#"+refType).val(refValue);
	refValue = refValue.replace(/%/g, "-prcntg-");
	refValue = encodeURIComponent(refValue);
	var refValueOld = jQuery("#"+refType+"Old").html();
	refValueOld = refValueOld.replace(/%/g, "-prcntg-");
	refValueOld = encodeURIComponent(refValueOld);
	var modelSerialInd = jQuery("#modelSerialInd").val();
	var requiredInd = jQuery("#"+refType+"Req").val();
	
	if('1' == requiredInd && "" == refValue){
		var msg = "";
		if('MODEL' == refType){
			msg = "Model Number";
		}else if('SERIALNUMBER' == refType){
			msg = "Serial Number";
		}
		if("" != msg){
			msg = msg + " is a required custom reference.";
		} 
		jQuery("#custRefErr").html("<span class='errorMsg'>" + msg + "</span>");
		jQuery("#custRefErr").show();
	}
	else{
		jQuery("#"+refType+"Save").hide();
		jQuery("#"+refType+"Cancel").hide();
		jQuery("#"+refType+"Disable").show();
		var url = 'soDetailsSummaryCustRefChange.action?refType='+refType+'&refVal='+refValue+'&refValOld='+refValueOld+'&invalidInd='+modelSerialInd+'&soId='+soId;
		jQuery('#modelSerialCloseDiv').load(url, function(data) {});
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
<!-- START RIGHT SIDE MODULES -->
<!--  
<div id="rightsidemodules" dojoType="dijit.layout.ContentPane"
	href="soDetailsQuickLinks.action"
	class="colRight255 clearfix"> </div>
	-->
	 <div class="soNote">
	<div id="rightsidemodules" class="colRight255 clearfix" >
         <p id="buyerquicklink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
    </div>
<!-- END RIGHT SIDE MODULES -->
<!-- Display server side message -->
	<c:if test="${!empty request.msg}" >
		<div class="error" style="width: 690px;" >
	   		<c:set var="msg" value="${msg}"/>
	   		<c:out value="${msg}" /><br />
		</div>
		<%request.removeAttribute("msg"); %>
	</c:if>	
	
	<c:if test="${!empty session.spendLimitMsg}" >
		<div class="errorBox clearfix" style="width: 694px;height: 20px" >
			<p class="errorMsg">
		   		<c:set var="spendLimitMsg" value="${spendLimitMsg}"/>
		   		<c:out value="${spendLimitMsg}" /><br />
	   		</p>
		</div>
		<%session.removeAttribute("spendLimitMsg"); %>
	</c:if>	
<div class="contentPane">
<c:set	var="soCloseDto" value="${soCloseDto}" scope="request" />
	<p>
		<strong>
		The service provider has submitted the following completion
		documentation and is awaiting payment. Review and submit additional
		information below and then release funds.
		</strong>
	</p>
  <!--  
	<div dojoType="dijit.TitlePane" title="General Completion Information"
		class="contentWellPane" style="width: 700px;">-->
		<c:if test="${SecurityContext.roleId != 1}">
<c:if test="${not empty soAutoCloseInfoDto}">
		<div id="autocloseinfo" class="menugroup_list">
  <p class="menugroup_head" onClick="autocloseclickCompInfo('${staticContextPath}');">&nbsp;<img id="compImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Auto Close Information</p>
    <div class="menugroup_body">
    Auto Close & Pay has been applied to this Service Order. When one or more of the Auto Close exceptions occurs, the
    Service Order requires manual review.
       <br><b> Rules Applied to Auto Close this Order:</b>
   <table>
    <c:forEach var="fieldType" items="${soAutoCloseInfoDto}">
   			<tr height="15px">
  					<td style="line-height: 18px;">
   <c:if test="${fieldType.ruleStatus == 'Yes'}">
							&nbsp;&nbsp;<img alt="Auto Close" title="This Auto Close exception exists." src="/ServiceLiveWebUtil/images/common/status-yellow.png" style="height:15px;  cursor: pointer;">
   </c:if>
  					</td>
  					<td width="630px" style="line-height: 18px;">
   						
						<b>${fieldType.ruleName}</b>&nbsp;-&nbsp;<c:if test="${fieldType.ruleName != 'SERVICE INCOMPLETE'}">${fieldType.ruleDescription}</c:if>
						<c:if test="${fieldType.ruleName == 'SERVICE INCOMPLETE'}">The Provider has indicated that the Service Order is incomplete, but submitted a claim greater than $0. </c:if>
   <c:if test="${fieldType.ruleName == 'MAX PRICE'}">
     <fmt:formatNumber value="${fieldType.criteriaValue}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
   </c:if>
  					</td>
   </tr>
   </c:forEach> 
   </table>
    </div>
    </div> 
     </c:if>
     </c:if>
		<div id="buyercompdocs" class="menugroup_list">
  <p class="menugroup_head" onClick="clickCloseInfo('${staticContextPath}');">&nbsp;<img id="closeImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;General Completion Information</p>
    <div class="menugroup_body">
		
		<p>
			<strong>Resolution Comments: </strong> ${soCloseDto.resComments}
		</p>
		<c:choose>
		<c:when test="${!soCloseDto.taskLevelPricing}">
		<p>
			<tr>
				<td />
				<td align="left">
					Maximum Price for Parts:&nbsp $<fmt:formatNumber value="${soCloseDto.partSpLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/>
					<br />
					Maximum Price for Labor:&nbsp $<fmt:formatNumber value="${soCloseDto.laborSpLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/>
					<br />
				</td>
			</tr>
			<strong>Total Maximum Price: $<fmt:formatNumber value="${soCloseDto.totalSpLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/> </strong>
			<br />
		</p>
		<p>
			<tr>
				<td align="left">
					Final Parts Price:&nbsp $<fmt:formatNumber value="${soCloseDto.finalPartPrice}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/>
					<br />
				</td>
			</tr>
			<tr>
				<td align="left">
					Final Labor Price:&nbsp $<fmt:formatNumber value="${soCloseDto.finalLaborPrice}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/>
					<br />
				</td>
			</tr>
			<c:if test = "${soCloseDto.addonServicesDTO.upsellPaymentIndicator}" >
			<tr>
				Add On Services Amount: &nbsp $<fmt:formatNumber value="${soCloseDto.addonServicesDTO.providerPaidTotal}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/> 
				<br />
			</tr>
			</c:if>
			<tr>
				<strong> Total Amount: &nbsp $<fmt:formatNumber value="${soCloseDto.finalPrice+ soCloseDto.addonServicesDTO.providerPaidTotal}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/> </strong>
				<br />
			</tr>
		</p>
		
		<p>
			<c:if test = "${soCloseDto.addonServicesDTO.upsellPaymentIndicator}" >
			<strong>Additional Services Funding</strong>	</p>
			<div class="additionalPaymentBox">
					   <strong>Customer Payment Type: </strong> 
					   <c:choose>
					   <c:when test="${soCloseDto.addonServicesDTO.paymentByCash}" >
					   		Cash
					   </c:when>
					   <c:when test="${soCloseDto.addonServicesDTO.paymentByCheck}" >
					   		Check
					   </c:when>
					   <c:when test="${soCloseDto.addonServicesDTO.paymentByCC}" >
					   		Credit Card
					   </c:when>
					   </c:choose><br/>
					   <strong>Payment Collection Amount: </strong>${soCloseDto.addonServicesDTO.paymentAmountDisplayStr}<br/><br/>
					   
					   <c:choose>
					   <c:when test="${soCloseDto.addonServicesDTO.paymentByCheck}" >
					   		 <strong>Check Number:</strong>${soCloseDto.addonServicesDTO.checkNumber}<br/>
					   </c:when>
					   <c:when test="${soCloseDto.addonServicesDTO.paymentByCC}">
					   		 <strong>Card Type: </strong> ${soCloseDto.addonServicesDTO.selectedCreditCardTypeStr}<br/>
							 <strong>Card Number: </strong>${soCloseDto.addonServicesDTO.creditCardNumber}<br/>
							 <strong>Expiration Date: </strong>${soCloseDto.addonServicesDTO.expMonth}/${soCloseDto.addonServicesDTO.expYear}<br/>
							  <c:if test="${soCloseDto.addonServicesDTO.preAuthNumber != null}" >
							 	<strong>Authorization Number: </strong>${soCloseDto.addonServicesDTO.preAuthNumber}<br/>
							  </c:if>
					   </c:when>
					   </c:choose>
				        <br/>
				        <strong>Payment Submitted Date: </strong>${soCloseDto.addonServicesDTO.paymentReceivedDateDisplayFormattedStr}
			</div>
		
			<p>
				<strong>Add-on Services</strong>
				<br/>
			</p>
			<div style="border: 1px solid #CCC; border-bottom: 0px;">
				<table class="globalTableLook" cellspacing="0" cellpadding="0" border="0" width="95%" style="margin-bottom: 0px;">
					<thead>
						<tr>
							<th class="col1 odd first">SKU#</th>
							<th class="col2 even textleft">Description</th>
							<th class="col3 odd">Qty</th>
							<th class="col4 even">Provider Paid</th>
							<th class="col5 odd">End Customer Charge</th>						
						</tr>
					</thead>
					<tbody>					
						<c:forEach var="addonServiceRowDTO" items="${soCloseDto.addonServicesDTO.addonServicesList}" varStatus="rowCounter">
							<c:if test="${addonServiceRowDTO.quantity > 0}">
								<tr>
									<td class="col1 odd first">${addonServiceRowDTO.sku}</td>
									<td class="col2 even textleft">${addonServiceRowDTO.description}</td>
									<td class="col3 odd">${addonServiceRowDTO.quantity}</td>
									<td class="col4 even" style="text-align: right;">
										
										<c:choose>
										<c:when test="${addonServiceRowDTO.skipReqAddon}" >
											<s>  <fmt:formatNumber value="${addonServiceRowDTO.providerPaid}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"  /> </s>
										    <br/>  (Included in Maximum Price)
										</c:when>
										<c:otherwise>
											<fmt:formatNumber value="${addonServiceRowDTO.providerPaid}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/>
										</c:otherwise>
										</c:choose>
								    </td>
									<td class="col5 odd" style="text-align: right;"><fmt:formatNumber value="${addonServiceRowDTO.endCustomerCharge}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
								</tr>		
							</c:if>			
						</c:forEach>
						<tr>
							<td style="border: 0px; text-align: right; font-weight: bold;"colspan="3"> Totals:</td>
							<td style="border: 0px; font-weight: bold; text-align: right;"><fmt:formatNumber value="${soCloseDto.upsellInfo.providerPaidTotal}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
							<td style="border: 0px; font-weight: bold; text-align: right;"><fmt:formatNumber value="${soCloseDto.upsellInfo.endCustomerSubtotalTotal}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
						</tr>							
					</tbody>
				</table>
			</div>
		</c:if>
		</c:when>
		<c:otherwise>
		
				<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%;" width="85%" cellspacing="0" cellpadding="0">
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
								<td style="width:12%;text-align:right;">$<fmt:formatNumber value="${soCloseDto.soMaxLabor}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
					</td>
					<td style="width:18%;text-align:right">
				$<fmt:formatNumber value="${soCloseDto.soFinalMaxLabor}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
					<td style="width:15%;text-align:right">&nbsp;</td>
							</tr>
							<tr>	
								
								
								<td colspan="2" style="text-align:left;">Parts</td>
								<td style="width:12%;text-align:right">$<fmt:formatNumber value="${soCloseDto.partSpLimit}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
					</td>
								<td style="width:18%;text-align:right">
				$<fmt:formatNumber value="${soCloseDto.finalPartPrice}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
	


</td>
								<td style="width:15%">&nbsp;</td>
							</tr>
							
<c:if test="${!empty soCloseDto.permitTaskList}">


								
								<c:forEach var="permitTask" items="${soCloseDto.permitTaskList}" varStatus="rowCounter">
							<c:set var="index" value="${rowCounter.count-1}" />	
						
							<tr class="permitTaskRow" height="25px">
<td colspan="2" style="text-align:left" align="left">
				<b>Permits</b>(Customer Pre-paid) - ${permitTask.permitTaskDesc}
			
			</td>
			<td style="width:12%;text-align:right;">$
			<fmt:formatNumber value="${permitTask.sellingPrice}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" /></td>
					<td style="width:18%;text-align:right">$
								<fmt:formatNumber value="${permitTask.finalPrice}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />						
					
					
														</td>
														<c:choose>
														<c:when test="${permitTask.finalPrice-permitTask.sellingPrice>0}">
														<c:set var="custCharge" value="${permitTask.finalPrice-permitTask.sellingPrice}"/>
														</c:when>
														<c:otherwise>
														<c:set var="custCharge" value="0.00"/>
														</c:otherwise>
														</c:choose>
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
					<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%;" width="85%" cellspacing="0" cellpadding="0">
<tr>
<th colspan="5" style="text-align: left;"><fmt:message bundle="${serviceliveCopyBundle}" key="details.completepayment.addon.msg2" />(customer paid on-site)**</th>
					</tr>
					<tr style="">
					<td colspan="3">&nbsp;</td>
								
								<td style="width:18%;text-align:right;"><b>Provider Price</b></td>
								<td style="width:15%;text-align:right;"><b>Customer<br/>&nbsp;&nbsp;Charge</b></td>
							</tr>
							<c:forEach var="addonServiceRowDTO" items="${soCloseDto.addonServicesDTO.addonServicesList}" varStatus="rowCounter">
							<c:if test="${addonServiceRowDTO.quantity > 0 && !addonServiceRowDTO.autoGenInd}">
							<tr>
							<c:choose>
							<c:when test="${addonServiceRowDTO.sku=='99888'}">
								<td colspan="3" style="text-align:left">99888 ADD-ON PERMIT - ${addonServiceRowDTO.description}</td>
								</c:when>
								<c:otherwise>
								<td colspan="3" style="text-align:left">${addonServiceRowDTO.description}</td>
								</c:otherwise>
								</c:choose>
								<td style="width:12%;text-align:right"><fmt:formatNumber value="${addonServiceRowDTO.providerPaid}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
								
								<td style="width:15%;text-align:right" align="right"><label id="custChargeWithoutAddons"><fmt:formatNumber value="${addonServiceRowDTO.endCustomerCharge}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
								</tr>
								</c:if>
								</c:forEach>
								<tr style="background: #CCCCCC; color: #FFF; border-bottom: 1px solid #CCCCCC;">
							
								<td colspan="3" style="color: #666;text-align:right"><b>Totals</b></td>
								<td style="width:18%;color: #666;text-align:right" align="right"><fmt:formatNumber value="${soCloseDto.upsellInfo.providerPaidTotal-soCloseDto.permitTaskAddonPrice}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
								<td style="width:15%;color: #666;text-align:right" align="right"><label id="custChargeWithoutAddons"><fmt:formatNumber value="${soCloseDto.upsellInfo.endCustomerSubtotalTotal-soCloseDto.permitTaskAddonPrice}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
							</tr>
							</table>
							</c:if>
							</c:if>
							<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%;" width="85%" cellspacing="0" cellpadding="0">
				<tr style="border-bottom: 1px solid #CCCCCC;">
							
								<td colspan="3" style="text-align:right;color: #666;border-right: 1px solid #CCC;"><b>Combined Total Final</b></td>
								<td style="width:18%;background: #BDEDFF; color: #666;text-align:right;border-right: 1px solid #CCC;" align="right"><label id="finalTotal" name="finalTotal"><fmt:formatNumber value="${soCloseDto.finalPrice+soCloseDto.upsellInfo.providerPaidTotal}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
								
								<c:choose>
								<c:when test="${empty soCloseDto.permitTaskList && (soCloseDto.upsellInfo==null||soCloseDto.upsellInfo.endCustomerSubtotalTotal<=0)}">
								<td style="width:15%;background: #BDEDFF; color: #666;text-align:right" align="right"><label id="custChargeFinal" name="custChargeFinal"></label></td>
								</c:when>
								<c:otherwise>
								<td style="width:15%;background: #BDEDFF; color: #666;text-align:right" align="right"><label id="custChargeFinal" name="custChargeFinal"><fmt:formatNumber value="${soCloseDto.upsellInfo.endCustomerSubtotalTotal}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></label></td>
								</c:otherwise>
								</c:choose>
							</tr>	
</table>
				
		
		<br/>
		<br/>
		
		
			<c:if test = "${soCloseDto.addonServicesDTO.upsellPaymentIndicator}" >
			
			<div class="additionalPaymentBox" style="width: 85%">
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
					   <td style="width: 10%">
					   </td>
					  
					   
					   <td style="width: 30%">
				        <strong>Submitted </strong>
				       <br/>
				       <br/>
				        ${soCloseDto.addonServicesDTO.paymentReceivedDateDisplayFormattedStr}
				        </td>
				        </tr>
				        </table>
			</div>
			</c:if>
</c:otherwise>
</c:choose>

	<!-- Priority 5B changes -->
	<!-- Invalid model no and serial no details -->
    <c:if test="${'3000' == soCloseDto.buyerID}">
		<div id="modelSerialCloseDiv">
			<jsp:include page="panel_model_serial_number.jsp"></jsp:include>
		</div>
	</c:if>
		
	</div>
</div>

	<c:if test="${!empty soCloseDto.invoiceParts}">	
			<jsp:include page="buyer_comp_part_hsr.jsp"></jsp:include>
	</c:if>	
	

	
<jsp:include page="../../../../wizard/body/sections/modules/panels_scope_of_work/panel_documentsAndPhotos.jsp" />
<!-- if theres upsell -->
<c:if test = "${soCloseDto.addonServicesDTO.paymentByCheck}" >
<style type="text/css">
	#candpay { display: none; }
</style>

<div id="upsellwarn">
<h3>Payment for this Service Order has not been received</h3>
<p>Check number ${soCloseDto.addonServicesDTO.checkNumber} for 
$<fmt:formatNumber value="${soCloseDto.addonServicesDTO.checkAmount}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/>
 must be received before this Service Order can be closed</p>

<div class="clearfix">
	<input type="checkbox" class="checkbox" onclick="jQuery('#candpay').toggle();"> <label>I've verified that payment for this Service Order has been received <span class="req">*</span></label>
</div>
</c:if>
<!-- end if there is upsell -->
<form id="foo" action="soDetailsSubmitCloseAndPay.action?soId=${SERVICE_ORDER_ID}" method="POST">

	<input id="candpay" type="image" src="${staticContextPath}/images/common/spacer.gif" width="120" height="30" 
							style="background-image:url(${staticContextPath}/images/btn/closeOrderAndPay_green.gif);"  
							class="btnBevel2 inlineBtn" onclick="disableCloseButton();"/>
	
	<img id ="candpayImage"src="${staticContextPath}/images/common/spacer.gif" width="115" height="30" 
							style="display: none; 
							background-image:url(${staticContextPath}/images/btn/closeOrderAndPay_green.gif);"
							/>
	
</form>

<!-- if there is upsell -->		
</div>
</div>
<!-- end if there is upsell -->
