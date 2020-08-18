<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Service Order Pricing"
	class="contentWellPane">
	<p>
		A summary of the maximum price you have placed on this service order is
		below. You may increase the spend limit using the button below.
	</p>
	<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
		<tr>
			<td width="200">
				<strong>Pricing Type</strong>
			</td>
			<td width="300">
				Hourly
			</td>
		</tr>
		<tr>
			<td>
				<strong>Rate</strong>
			</td>
			<td>
				$4.50/hr
			</td>
		</tr>
		<tr>
			<td>
				<strong>Rate Type</strong>
			</td>
			<td>
				Buyer Selected
			</td>
		</tr>
	</table>
	<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
		<tr>
			<td width="190">
				<strong>Maximum Price for Labor</strong>
			</td>
			<td width="10"></td>
			<td width="60" align="right">
			$<fmt:formatNumber value="${summaryDTO.laborSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>
			</td>
		</tr>
		<tr>
			<td>
				<strong>Maximum Price for Parts</strong>
			</td>
			<td></td>
			<td align="right">
				$<fmt:formatNumber value="${summaryDTO.partsSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>
			</td>
		</tr>
		<tr>
			<td>
				<strong>Posting Fee</strong>
			</td>
			<td style="border-bottom: 1px solid #ccc;">
				+
			</td>
			<td style="border-bottom: 1px solid #ccc;" align="right">
				${summaryDTO.accessFee}
			</td>
		</tr>

		<tr>
			<td>
				<strong>Total Maximum Price</strong>
			</td>
			<td></td>
			<td align="right">
				${summaryDTO.totalSpendLimit}
			</td>
		</tr>
	</table>
	<p>
		<input type="image" src="${staticContextPath}/images/common/spacer.gif"
			width="130" height="20"
			style="background-image: url(${staticContextPath}/images/btn/increaseSpendLimit.gif);"
			class="btn20Bevel" />
	</p>
</div>
