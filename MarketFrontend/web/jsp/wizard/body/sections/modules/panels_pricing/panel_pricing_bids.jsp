<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<div dojoType="dijit.TitlePane" title="Pricing" class="contentWellPane" executeScripts="true" scriptSeparation="false">


<script type="text/javascript">
jQuery(document).ready(function($){
	$(".soWizardPriceDetails").hide();
    if ($("#radioNYP").attr("checked")) { checkNamePrice(); } else { checkBid(); }
});
</script>


<c:set var="disabled" value="${not empty groupOrderId and fn:length(groupOrderId)>0 }"/>

<table width=100% border=0>
	<tr>
		<td width=100%>

                    <label>What is your preference for funding your Service Order?</label>
					
					<s:if test="%{sowPricingTabDTO.draftOrder != true}">
					<s:hidden name="orderType" />
					</s:if>
					
    				<br>
				    <div id="projectTypeNYP" class="formFieldOffset soWizardPriceOption">
					<input type="radio" class="antiRadioOffsets" name="orderType" id="radioNYP" value="NAME_PRICE" <s:if test="%{sowPricingTabDTO.orderType == 'NAME_PRICE'}">checked="checked" </s:if><s:if test="%{sowPricingTabDTO.draftOrder != true}">disabled="disabled" </s:if>/> I would like to <strong>Name My Own Price</strong><br/>
						<div class="soWizardPriceDetails">
						
							<tags:fieldError id="Maximum Price for Labor" oldClass="">
								<p><label>Maximum Price for Labor</label>
									<br>
									$ <input type="text" ${disabled?"disabled":""} name="laborSpendLimit" id="laborSpendLimit" class="shadowBox grayText" style="width: 100px;" maxlength="10" value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.laborSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
								</p>
							</tags:fieldError>
							
							<c:if test="${showPartsSpendLimit}">
								<tags:fieldError id="Maximum Price for Parts" oldClass="">
									<p><label>Maximum Price for Parts</label>
										<br>
										$ <input type="text" ${disabled?"disabled":""} name="partsSpendLimit" id="partsSpendLimit" class="shadowBox grayText" style="width: 100px;" maxlength="10" value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.partsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
									</p>
								</tags:fieldError>
							</c:if>
							
							<c:if test="${showBillingEstimate}">
								<tags:fieldError id="Billing Estimate" oldClass="">
									<p><label>
										Billing Estimate
									</label>
									<br>
									$ <input type="text" name="billingEstimate" id="billingEstimate" class="shadowBox grayText" style="width: 100px;" maxlength="10" value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.billingEstimate}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
									</input>
									</p>
								</tags:fieldError>
							</c:if>
		                </div>
		            </div>
					
					<div id="projectTypeBid" class="formFieldOffset soWizardPriceOption">
						<input name="orderType" id="radioBid" value="ZERO_PRICE_BID" class="antiRadioOffsets" type="radio" <s:if test="%{sowPricingTabDTO.orderType == 'ZERO_PRICE_BID'}">checked="checked" </s:if><s:if test="%{sowPricingTabDTO.draftOrder != true}">disabled="disabled" </s:if>/> I would like to <strong>Request Bids</strong>        
						<div class="soWizardPriceDetails">
							<s:if test="%{sowPricingTabDTO.sealedBidOrder == true}">
							
								<p><s:checkbox id="sealedbid" name="sealedBidInd" value="sealedBidInd" theme="simple" /> I would like this Service Order to receive Sealed Bid.</p>						
								
							</s:if>
							<p><s:checkbox id="checkShareLocation" name="shareContactInd" value="shareContactInd" theme="simple" /> I would like the Service Location Contact Information shared to the providers I select so they can communicate any questions.</p>
						</div>

                   	</div>
				
			
			
		</td>
	</tr>
	
	<c:if test="${groupOrderId != null}">
		<tr>
			<td>
				<div style="color : red">Please note that any changes you make to this order will affect all orders in the group.</div>
			</td>
		</tr>
		<tr width="150px">
			<td>
			<tags:fieldError id="Maximum Price for Labor" oldClass="">
					<label style="width: 100%">
						Group Maximum Price for Labor
					</label>
					<br>
					$ <input type="text" name="ogLaborSpendLimit" id="ogLaborSpendLimit" class="shadowBox grayText" style="width: 100px;" maxlength="10" value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.ogLaborSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
					</input>
					<br>
				</tags:fieldError>
			
				<c:if test="${showPartsSpendLimit}">
					<tags:fieldError id="Maximum Price for Parts" oldClass="">
						<label>
							Group Maximum Price for Parts
						</label>
						<br>
					$ <input type="text" name="ogPartsSpendLimit" id="ogPartsSpendLimit" class="shadowBox grayText" style="width: 100px;" maxlength="10" value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.ogPartsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
					</input>
					<br>
					</tags:fieldError>
				</c:if>
			</td>				
		</tr>
	</c:if>			
		
		
	
	
	
	</table>

</div>
