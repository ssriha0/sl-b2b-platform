<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ page import="com.newco.marketplace.constants.Constants" %>
<%@page import="com.newco.marketplace.web.constants.QuickLinksConstants"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>	
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<head>
<style>

.tr2{
border-collapse:collapse;
border-color:#808080; 
border-width:1px;
border-style:solid; 
}
.mytable
{
border-collapse:collapse; 
border-color:#808080; 
border-style:solid; 
border-width:1px;
border-left:none;
border-right:none;
border-top:none;
}
.tr3
{
border-collapse:collapse; 
border-color:#808080; /*grey*/
border-style:solid; 
border-width:1px;
}
.tr1{
border-collapse:collapse;
border-color:#808080; 
border-width:1px;
border-style:solid; 
border-bottom:none;
}
.tr4
{
border-right: none;
}
.newtable
{
border-collapse:collapse; 
border-color:#808080; 
border-style:solid; 
border-width:1px;
border-top:none;
border-bottom:none;
border-right:none;
border-left:none;
padding:10px;
}
</style>
</head>
<body>

<c:set	var="soCloseDtoForAddon" value="${soCloseDtoComplete}" scope="request" />
<c:if test="${!empty soCloseDtoComplete.invoiceParts}">
<c:set	var="soCloseDto" value="${soCloseDtoComplete}" scope="request" />
</c:if>

		<table class="mytable" width="100%">
			<tbody>
					<tr style="background-color: silver;">
						<td class="tr2" align="center"><b><div>&nbsp;&nbsp;Service Order</div>
							Pricing</b></td>
						<td align="center" class="tr2"><b>Final Price</b></td>
						<td  class="tr2" align="center"><b><div>&nbsp;Est.&nbsp;Provider&nbsp;</div><b>Payment</b></b></td>
					</tr>
					<tr>
						<td style="border-top:none;" class="tr2" align="left">&nbsp;Labor&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;$<fmt:formatNumber value="${maximumLabor}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;$<fmt:formatNumber value="${maximumLabor-maximumLabor*0.1}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>&nbsp;&nbsp;</td>
					</tr>
					
					<tr id="partsCheck" class="pc">
						<td style="border-top:none;" class="tr2" align="left">&nbsp;Materials&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;$<fmt:formatNumber value="${maximumParts}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;$<fmt:formatNumber value="${maximumParts-maximumParts*0.1}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>&nbsp;&nbsp;</td>
					</tr>
	<c:if test="${not empty soCloseDto.invoiceParts}">  				
					<tr>
						<td style="border-top:none;" class="tr2" align="left">&nbsp;Parts&nbsp;</td>

		<c:set  var="totalFinalPayment" value="0"></c:set>
		<c:forEach items="${soCloseDto.invoiceParts}" var="parts">
														<c:if test="${parts.partStatus == 'Installed'}">
		
		<c:set  var="totalFinalPayment" value="${totalFinalPayment+parts.estProviderPartsPayment}"></c:set>
		</c:if>
		</c:forEach>

		<c:set  var="totalPayment" value="0"></c:set>	
		<c:forEach items="${soCloseDto.invoiceParts}" var="parts">
												<c:if test="${parts.partStatus == 'Installed'}">
		
		<c:set  var="totalPayment" value="${totalPayment+parts.finalPrice}"></c:set>
		</c:if>
		</c:forEach>

						<td style="border-top:none;" class="tr2" align="right">&nbsp;$<fmt:formatNumber value="${totalPayment}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;$<fmt:formatNumber value="${totalFinalPayment}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />&nbsp;&nbsp;</td>
					</tr>
		</c:if>	
     		<c:set	var="providerPaidTotal" value="${providerPaidTotal}" scope="request" />       	
		<c:if test="${providerPaidTotal>0.0}">    
					<tr>    				
               			<td style="border-top:none;" class="tr2" align="left">&nbsp;Add-On Services&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;$<fmt:formatNumber value="${providerPaidTotal}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;$<fmt:formatNumber value="${providerPaidTotal-providerPaidTotal*0.1}"  minFractionDigits="2" type="NUMBER" maxFractionDigits="2" />&nbsp;&nbsp;</td> 
					</tr>
		</c:if>
		    			       <c:set var="serviceLiveNetPayment" value="${(maximumLabor+maximumParts+providerPaidTotal+totalPayment)*0.9}"></c:set>
		                       <fmt:formatNumber var="serviceLiveNetPaymentFormat" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" value="${serviceLiveNetPayment}"></fmt:formatNumber>
		                       <c:set var="serviceLiveTotalPayment" value="${maximumLabor+maximumParts+providerPaidTotal+totalPayment}"></c:set>
		                       
				<tr>
						<td style="border-top:none;background-color: silver;" class="tr2" align="right"><b>&nbsp;&nbsp;Total&nbsp;</b>**&nbsp;&nbsp;</td>
						<td style="border-top:none;background-color: silver;" class="tr2" align="right"><b>$<fmt:formatNumber value="${serviceLiveTotalPayment}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>&nbsp;&nbsp;</b></td>
						<td style="border-top:none;" class="tr4" align="right"></td>
					</tr>
			<tr>
					<td></td>
					<td></td>
					<td></td>
					</tr>
					<tr>
						<td style="border-top:none;" class="tr2" align="right"><div style="table-layout:fixed; width:90px; overflow:hidden;"><b>ServiceLive &nbsp;&nbsp; Service Fee &nbsp;&nbsp;</b></div></td>
						<td style="border-top:none;" class="tr2" align="right"><b>-&nbsp;$<fmt:formatNumber value="${serviceLiveTotalPayment - serviceLiveNetPaymentFormat}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/></b>&nbsp;&nbsp;</td>
						<td class="tr4" align="right"></td>
					</tr>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					</tr>
				<tr>
						<td style="border-top:none;" class="tr2" align="right"><div style="table-layout:fixed; width:100px; overflow:hidden;"><b>Total Provider&nbsp;&nbsp; Payment</b>&nbsp;&nbsp;</div></td>
						<td style="border-top:none;" class="tr2" align="right"><b>
							$<fmt:formatNumber value="${serviceLiveNetPayment}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/></b>&nbsp;&nbsp;</td>
						<td class="tr4" align="right"></td>
					</tr>
				</tbody>
	</table>

		
<table class="newtable">
<tr>
<td>&nbsp;
</td>
<td align="justify">
** For Sears Home Services SPN Providers, the Maximum Price may be different than your Authorized Servicer's Rate Schedule
as the Buyer may have selected to increase the Maximum Price to fund(in whole or in part) the ServiceLive Service Fee.
</td>
<td>&nbsp;
</td>
</tr>
</table>
</body>