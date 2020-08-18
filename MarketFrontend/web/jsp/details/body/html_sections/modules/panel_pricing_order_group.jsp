<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="BUYER_PROVIDES_PART"
	value="<%=OrderConstants.SOW_SOW_BUYER_PROVIDES_PART%>" />
<c:set var="PROVIDER_PROVIDES_PART"
	value="<%=OrderConstants.SOW_SOW_PROVIDER_PROVIDES_PART%>" />
<c:set var="PARTS_NOT_REQUIRED"
	value="<%=OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED%>" />

<c:set var="BUYER_ROLEID"
	value="<%=new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID"
	value="<%=new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS"
	value="<%=new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="CLOSED_STATUS"
	value="<%=new Integer(OrderConstants.CLOSED_STATUS)%>" />
<c:set var="SIMPLE_BUYER_ROLEID" value="<%=new Integer(OrderConstants.SIMPLE_BUYER_ROLEID)%>" />


<c:set var="role" value="${roleType}" />



<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR"
	value="<%=OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />

<c:forEach items="${serviceOrders}" var="so">
	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Pricing: ${so.id}" 
		class="contentWellPane" open="false" >
		
		
		<p>
			<table>
				<tr>
					<td>
						Maximum Price for Parts:
					</td>
					<td>
						$
						${so.partsSpendLimit}
					</td>
				</tr>
				<tr>
					<td>
						Maximum Price for Labor:
					</td>
					<td>
						$
						${so.laborSpendLimit}					
					</td>
				</tr>
				<tr>
					<td>
						Maximum Price Total:
					</td>
					<td>
						$
						${so.totalSpendLimit}
					</td>
				</tr>
			</table>		
		</p>
		<br />
		
	</div>
</c:forEach>