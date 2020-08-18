<div class="quickLinks">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="theTab" value="<%=request.getAttribute("tab")%>" />
<c:set var="viewOrderPricing" scope="session" value="<%=session.getAttribute("viewOrderPricing")%>"></c:set>
<strong><c:if test="${groupInd}">Grouped </c:if>Order Quick Links:</strong>&nbsp;
<c:choose><c:when test="${groupInd}"><c:set var="idParam" value="groupId=${groupId}" /></c:when>
<c:otherwise><c:set var="idParam" value="soId=${soId}" /></c:otherwise></c:choose>
<c:set var="DRAFT" value="100"/>
<c:set var="DELETED" value="105"/>
<c:set var="POSTED" value="110"/>
<c:set var="RECEIVED" value="110"/>
<c:set var="ACCEPTED" value="150"/>
<c:set var="ACTIVE" value="155"/>
<c:set var="COMPLETED" value="160"/>
<c:set var="PENDINGCANCEL" value="165"/>
<c:set var="CANCELLED" value="120"/>
<c:set var="VOIDED" value="125"/>
<c:set var="CLOSED" value="180"/>
<c:set var="PROBLEM" value="170"/>
<c:set var="EXPIRED" value="130"/>

	<!-- PROVIDER -->	
	<c:if test="${roleType == 1}">
		<c:if test="${rowsostatus == ACCEPTED && !groupInd}">
		<c:if test="${viewOrderPricing==true}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
			 View Order History
			</a>
			|
			</c:if>
			
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if> 
		<c:if test="${rowsostatus == ACTIVE && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Complete for Payment&displayTab=${theTab}" target="_top">
			<c:if test="${viewOrderPricing}">Complete Service Order |</c:if>
			</a>
			<c:if test="${viewOrderPricing==true}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if> 
		<c:if test="${rowsostatus == CANCELLED && !groupInd}">
		<c:if test="${viewOrderPricing==true}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if> 
		<c:if test="${rowsostatus == CLOSED && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Completion Record&displayTab=${theTab}" target="_top">
				View Completion Record
			</a>
			|
			<c:if test="${viewOrderPricing==true}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if> 
		<c:if test="${rowsostatus == COMPLETED && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Completion Record&displayTab=${theTab}" target="_top">
				View Completion Record
			</a>
			|
			<c:if test="${viewOrderPricing==true}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>		
			|
			</c:if>
			<a href="soDetailsController.action?${idParam}&defaultTab=Support" target="_top">
				ServiceLive Support
			</a>
		</c:if> 
		<c:if test="${rowsostatus == PROBLEM && !groupInd}">
		<c:if test="${viewOrderPricing==true}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if> 
		<c:if test="${rowsostatus == RECEIVED}">
			<!-- ResponseId = 2 is Counter Offer -->
			<c:if test="${not empty groupInd and not groupInd}">
			<c:if test="${viewOrderPricing==true}">
			<a href="soDetailsController.action?${idParam}&resId=${rowRoutedResourceId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			</c:if>
			</c:if>
			<%-- Show this option to provider only at group or individual level, not for child order --%>
			<c:if test="${providerResponseId != 2 and (groupInd or empty groupId)}">
			<c:if test="${not empty groupInd and not groupInd}">|</c:if>
				<a href="soDetailsController.action?${idParam}&resId=${rowRoutedResourceId}&defaultTab=Summary&displayTab=${theTab}" target="_top">
					View Details to Accept
				</a>
			</c:if>
		</c:if>
		<c:if test="${rowsostatus == PENDINGCANCEL && !groupInd}">
		<c:if test="${viewOrderPricing==true}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if> 
	</c:if>
	<!--  BUYER -->
	<c:if test="${(roleType==2)||(roleType==3)}">
		<c:if test="${rowsostatus == ACCEPTED && !groupInd}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>
		<c:if test="${rowsostatus == ACTIVE && !groupInd}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == CANCELLED && !groupInd}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == CLOSED && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Completion Record&displayTab=${theTab}" target="_top">
				View Completion Record
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == COMPLETED && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Close and Pay&displayTab=${theTab}" target="_top">
				Close and Pay Service Order
			</a>
			|
			<a href="http://community.servicelive.com/docs/cso.pdf" target="_blank">
				How to Close
			</a>			
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<!-- For Draft Support link is different because it can't navigate to SOD and hence redirected to generic support page -->
		<c:if test="${rowsostatus == DRAFT}">
			<a href="soDetailsController.action?${idParam}&displayTab=${theTab}" target="_top">
				View Order Details
			</a>
			|
			<a href="/MarketFrontend/jsp/public/support/support_faq.jsp" target="_top">
				ServiceLive Support
			</a>
		</c:if>	
		<c:if test="${rowsostatus == EXPIRED}">
			<c:if test="${not empty groupInd and not groupInd}">
			<a href="soWizardController.action?soId=${soId}&action=edit&tab=today&displayTab=${theTab}" target="_top">
				Edit Order
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == PROBLEM && !groupInd}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == POSTED}">
			<c:if test="${not empty groupInd and not groupInd}">
			<a href="soWizardController.action?soId=${soId}&action=edit&tab=posted&displayTab=${theTab}" target="_top">
				Edit Service Order
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?${idParam}&defaultTab=Response Status&displayTab=${theTab}" target="_top">
				View Provider Responses
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${(rowsostatus == VOIDED || rowsostatus == DELETED) && !groupInd}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>
		<c:if test="${rowsostatus == PENDINGCANCEL && !groupInd}">
			<a href="soDetailsController.action?${idParam}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?${idParam}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if> 		
	</c:if> 
	<!-- SIMPLE BUYER -->
	<c:if test="${roleType == 5}">
		<c:if test="${rowsostatus == ACCEPTED && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>
		<c:if test="${rowsostatus == ACTIVE && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == CANCELLED && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == CLOSED && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Completion Record&displayTab=${theTab}" target="_top">
				View Completion Record
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == COMPLETED && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Close and Pay&displayTab=${theTab}" target="_top">
				Close and Pay Service Order
			</a>
			|
			<a href="http://community.servicelive.com/docs/cso.pdf" target="_blank">
				How to Close
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<!-- For Draft Support link is different because it can't navigate to SOD and hence redirected to generic support page -->
		<c:if test="${rowsostatus == DRAFT}">
			<a href="soDetailsController.action?soId=${soId}&displayTab=${theTab}" target="_top">
				View Order Details
			</a>
			|
			<a href="/MarketFrontend/jsp/public/support/support_faq.jsp" target="_top">
				ServiceLive Support
			</a>
		</c:if>	
		<c:if test="${rowsostatus == EXPIRED}">
			<c:if test="${not empty groupInd and not groupInd}">
			<a href="soWizardController.action?soId=${soId}&action=edit&tab=today&displayTab=${theTab}" target="_top">
				Edit Order
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == PROBLEM && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${rowsostatus == POSTED}">
			<c:if test="${not empty groupInd and not groupInd}">
			<a href="soWizardController.action?soId=${soId}&action=edit&tab=posted&displayTab=${theTab}" target="_top">
				Edit Service Order
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			</c:if>
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Response Status&displayTab=${theTab}" target="_top">
				View Provider Responses
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>		
		<c:if test="${(rowsostatus == VOIDED || rowsostatus == DELETED) && !groupInd}">
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Order History&displayTab=${theTab}" target="_top">
				View Order History
			</a>
			|
			<a href="soDetailsController.action?soId=${soId}&defaultTab=Support&displayTab=${theTab}" target="_top">
				ServiceLive Support
			</a>
		</c:if>
	</c:if> 
</div>
<c:if test="${groupInd}">
	<table border="0" cellpadding="0" cellspacing="0" class="groupSubHead">
		<tr>
			<td class="col1">Status</td>
			<td class="col2">Service Order #</td>
			<td class="col3">Title</td>
			<c:choose>
				<c:when test="${theTab == 'Posted' or theTab == 'Received'}">
					<td class="col4">Maximum Price</td>
					<td class="col5">Appt. Time</td>
					<td class="col6">Age of Order&nbsp;</td>
				</c:when>
				<c:otherwise>
					<td class="col4" width="50">&nbsp;</td>
					<td class="col5">Service Date</td>
					<td class="col6">Location&nbsp;&nbsp;</td>
				</c:otherwise>
			</c:choose>
		</tr>
	</table>	
</c:if>
