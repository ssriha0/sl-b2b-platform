<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Pricing" class="contentWellPane">
	<p>
		<s:if test="showPrices==true">
		Below is a summary of the spend limits you have placed on this service order.
		</s:if><s:else>
		You have requested that the providers who receive this service order submit a bid for pricing.
		</s:else>
		Click <strong>Previous</strong> button below to change the payment terms, if necessary.
	</p>
	<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">

		<c:if test="${not empty groupOrderId and fn:length(groupOrderId)>0 }">
		<tr>
			<td colspan=2>
					<h3>
						# ${SERVICE_ORDER_ID}
					</h3>
			</td>
		</tr>
	</c:if>

		<tr>
			<td width="240">
				<strong>Pricing Type</strong>
			</td>
			<td width="300">
				<c:choose>
				<c:when
					test="${reviewDTO.pricingType == 'Bid Request' && reviewDTO.sealedBidInd == true}">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="workflow.pricemodel.sealed.bid" />
				</c:when>
				<c:otherwise>
				${reviewDTO.pricingType}
				</c:otherwise>
				</c:choose>
			</td>
		</tr>

		<tr>
			<td colspan="2">
				&nbsp;

			</td>
		</tr>

		<s:if test="showPrices==true">
		<tr>
			<td width="250">
				<strong>Maximum Price for Labor</strong>
			</td>
			<td width="60">
					$
					<fmt:formatNumber value="${reviewDTO.laborSpendLimit - reviewDTO.prePaidPermitPrice}"
						type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</td>
		</tr>

		<tr>

			<td>
				<strong>Maximum Price for Parts</strong>
			</td>
			<td>
					$
					<fmt:formatNumber value="${reviewDTO.partsSpendLimit}"
						type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
			</td>
		</tr>
			
			<c:if test="${reviewDTO.taskLevelPriceInd == true}">
		<tr>
				<td>
					<strong>Maximum Price for Permits</strong>
			</td>
				<td>
				<c:choose>
				<c:when test = "${reviewDTO.prePaidPermitPrice == null ||reviewDTO.prePaidPermitPrice == 0.00}">
					$ 0.00
				</c:when>
				<c:when test= "${reviewDTO.prePaidPermitPrice > 0.00}">
					$	
					<fmt:formatNumber value="${reviewDTO.prePaidPermitPrice}"
						type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
				</c:when>		
				</c:choose>
				</td>
		</tr>
		</c:if>
		
		<tr>
				<td colspan="2">
					&nbsp;

				</td>
			</tr>
		</s:if>

		<tr>
			<td>
				<strong>Posting Fee</strong>
			</td>
			<td>
				${reviewDTO.accessFee}
			</td>
		</tr>
		<tr>
			<td>
				<strong>Total Amount Due</strong>
			</td>
			<td>
				<c:choose>
				<c:when test="${reviewDTO.pricingType == 'Bid Request'}">$0.00</c:when>
				<c:otherwise>${reviewDTO.totalAmountDue}</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				&nbsp;

			</td>
		</tr>

		<tr>
			<td colspan=2>
				&nbsp;
			</td>
		</tr>

		<c:if test="${not empty groupOrderId and fn:length(groupOrderId)>0 }">
			<tr>
				<td colspan=2>
					<h3>
						Order Group
					</h3>
				</td>
			</tr>

			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>

			<tr>
				<td width="250">
					<strong>Group Maximum Price for Labor</strong>
				</td>
				<td width="60">
					$
					<fmt:formatNumber value="${ogLaborSpendLimit}" type="NUMBER"
						minFractionDigits="2" maxFractionDigits="2" />
				</td>
			</tr>

			<tr>

				<td>
					<strong>Group Maximum Price for Parts</strong>
				</td>
				<td>
					$
					<fmt:formatNumber value="${ogPartsSpendLimit}" type="NUMBER"
						minFractionDigits="2" maxFractionDigits="2" />
				</td>
			</tr>

			<tr>
				<td colspan="2">
					&nbsp;

				</td>
			</tr>
		</c:if>
	</table>

</div>


