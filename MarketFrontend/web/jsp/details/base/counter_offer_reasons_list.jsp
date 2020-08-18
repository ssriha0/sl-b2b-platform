<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="table-wrap">
	<table cellspacing="0" cellpadding="0" style=width:320px;" >	 		
	    <tbody>
	    <tr><td colspan="2"><h4>Reason Code (select all that apply)</h4></td></th>	
			<!-- start loop -->
			<c:forEach items="${counterOfferReasonsList}" var="counterOfferReason">
				<tr>
					<td style="width:10%;"><input type="checkbox" id="${counterOfferReason.counterOfferReasonId}" name="counterOfferReasons" value="${counterOfferReason.counterOfferReasonId}" theme="simple" /></td>
					<td style="width:90%;">${counterOfferReason.counterOfferReasonDesc}</td>	  	
				</tr>
			</c:forEach>
			<!-- end loop -->				
		</tbody>
	</table>
	<s:hidden id="checkedCounterOfferReasons" name="checkedCounterOfferReasons" /> 
</div>
