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
		<table class="mytable" width="100%">
			<tbody>
					<tr style="background-color: silver;">
						<td class="tr2" align="center">
						<b><div>&nbsp;&nbsp;Service Order</div>
							Pricing</b></td>
						<td align="center" class="tr2"><b>Final Price</b></td>
<td  class="tr2" align="center"><b><div>&nbsp;Est.&nbsp;Provider&nbsp;</div><b>Payment</b></b></td>
					</tr>

					<tr id="laborCheck" class="laborPrice" style="display:none;">
						<td style="border-top:none;" class="tr2" align="left">&nbsp;Labor&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;<span  class="laborPricing" name="ps1"></span>&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;<span class="laborEstPrice" name="ps2"></span>&nbsp;&nbsp;</td>
					</tr>

					<tr id="partsCheck" class="pc" style="display:none;">
						<td style="border-top:none;" class="tr2" align="left">&nbsp;Materials&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;<span  class="partsPricing" name="pp1"></span>&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;<span  class="parstEstPricing" name="pep1"></span>&nbsp;&nbsp;</td>
					</tr>


					<tr id="partsinvoiceCheck" class="pVoice" style="display:none;">
						<td style="border-top:none;" class="tr2" align="left">&nbsp;Parts&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;<span  class="partsInPricing" id="p" name="pp1"></span>&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;<span  class="partsInEstPricing" name="pp1"></span>&nbsp;&nbsp;</td>
					</tr>
					<tr id="addOnCheck" class="addOnPrice" style="display:none;">
						<td style="border-top:none;" class="tr2" align="left">&nbsp;Add-On Services&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;<span id="cwe" class="addOnPricing" name="pp1"></span>&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr2" align="right">&nbsp;<span  class="addOnEstPricing" name="pp1"></span>&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td style="border-top:none;background-color: silver;" class="tr2" align="right"><b>&nbsp;&nbsp;Total&nbsp;</b>**&nbsp;&nbsp;</td>
						<td style="border-top:none;background-color: silver;" class="tr2" align="right"><b><span class="totalServiceOrderPrice" name="tsof"></span></b>&nbsp;&nbsp;</td>
						<td style="border-top:none;" class="tr4" align="right"></td>
					</tr>
					<tr>
					<td></td>
					<td></td>
					<td></td>
					</tr>
					<tr>
						<td style="border-top:none;" class="tr2" align="right"><div style="table-layout:fixed; width:90px; overflow:hidden;"><b>ServiceLive &nbsp;&nbsp; Service Fee &nbsp;&nbsp;</b></div></td>
						<td style="border-top:none;" class="tr2" align="right"><b><span name="slf" class="serviceLiveFee"></span></b>&nbsp;&nbsp;</td>
						<td class="tr4" align="right"></td>
					</tr>
					<tr>
					<td></td>
					<td></td>
					<td></td>
					</tr>
				        <tr>
						<td style="border-top:none;" class="tr2" align="right"><div style="table-layout:fixed; width:100px; overflow:hidden;"><b>Total Provider&nbsp;&nbsp; Payment</b>&nbsp;&nbsp;</div></td>
						<td style="border-top:none;" class="tr2" align="right"><b><span name="npf" class="netPaymentFee" id="finalTotalLabel"></span></b>&nbsp;&nbsp;</td>
						<td class="tr4" align="right"></td>
					</tr>
				</tbody>
	</table>
		
<table class="newtable" >
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













