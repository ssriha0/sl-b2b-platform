<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS" value="<%= new Integer(OrderConstants.ROUTED_STATUS)%>" />

<!-- NEW MODULE/ WIDGET-->
<c:forEach items="${serviceOrders}" var="summaryDTO">
<div dojoType="dijit.TitlePane" title="Parts: ${summaryDTO.id}" class="contentWellPane" open="false">
	<p>
		<c:if test="${not empty summaryDTO.partsList}">
		Please note the parts information below. Detailed pick-up location
		information is included if pick-up is required.
		</c:if>
		<c:if test="${empty summaryDTO.partsList}">
		Buyer has not specified parts for this service order. Either parts are not required or provider will supply them.
		</c:if>
	</p>
	<div><br/><br/></div>
	<!-- NEW NESTED MODULE -->
	<c:forEach var="part" items="${summaryDTO.partsList}">
	<div dojoType="dijit.TitlePane" title="${part.title}" id=""
		class="dijitTitlePaneSubTitle" >
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
			<tr>
				<td width="100">
					<strong>Manufacturer</strong>
				</td>
				<td width="100">
					${part.manufacturer}
				</td>
				<td width="100">
					<strong>Size</strong>
				</td>
				<td width="100">
					${part.size}
				</td>
			</tr>
			<tr>
				<td>
					<strong>Model Number</strong>
				</td>
				<td>
					${part.modelNumber}
				</td>
				<td>
					<strong>Weight</strong>
				</td>
				<td>
					${part.weight}
				</td>
			</tr>
			<tr>
				<td>
					<strong>Qty</strong>
				</td>
				<td>
					${part.qty}
				</td>
				<td></td>
				<td></td>
			</tr>
		</table>
		<strong>Description</strong>
		<p>
			${part.description}
		</p>
		<c:choose>
			<%-- Show the Shipping Information section to Buyer all the time and to Provider (only when an order is in Accept state and beyond states) --%>
			<c:when test="${roleType == BUYER_ROLEID || (roleType == PROVIDER_ROLEID && summaryDTO.status != ROUTED_STATUS)}"> <%-- Don't show the section for Posted Orders for Providers --%>		
				<p>
					<strong>Shipping Information</strong>
				</p>
				<hr class="noSpace" />
				<table cellpadding="0" cellspacing="0">
		
					<tr>
						<td width="200">
							<strong>Shipping Carrier</strong>
						</td>
						<td>
							${part.shippingCarrier}
						</td>
					</tr>
					<tr>
						<td width="200">
							<strong>Shipping Tracking Number</strong>
						</td>
						<td>
							${part.shippingTrackingNumber}
						</td>
					</tr>
					<tr>
						<td width="200">
							<strong>Shipping Date</strong>
						</td>
						<td>
							${part.shipDate}
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							&nbsp;
		
						</td>
					</tr>
		
		
					<tr>
						<td>
							<strong>Core Return Carrier</strong>
						</td>
						<td>
							${part.coreReturnCarrier}
						</td>
					</tr>
					<tr>
						<td>
							<strong>Core Return Tracking Number</strong>
						</td>
						<td>
							${part.coreReturnTrackingNumber}
						</td>
					</tr>
				</table>
				<p>
					<strong>Pick-up/Merchandise Location Information</strong>
				</p>
				<hr class="noSpace" />
				<c:choose>
					<c:when test="${part.contact != null}">
						<table cellpadding="0" cellspacing="0" class="noMargin">
							<tr>
								<td width="120">
									<p>
									    <c:if test="${(null == part.contact.streetAddress || '' == part.contact.streetAddress) && (null == part.contact.streetAddress2 || '' == part.contact.streetAddress2) && (null == part.contact.cityStateZip || '' == part.contact.cityStateZip)}">
											Product At Job Site
										</c:if>	
										<c:if test="${part.contact.individualName!=null}">
										${part.contact.individualName}
										<br />
										</c:if>
										<c:if test="${part.contact.companyName!=null}">
										${part.contact.companyName}
										<br />
										</c:if>
										<c:if test="${part.contact.streetAddress!=null}">
										${part.contact.streetAddress}
										<br />
										</c:if>
										<c:if test="${part.contact.streetAddress2!=null}">
										${part.contact.streetAddress2}
										<br />
										</c:if>
					    				<c:if test="${part.contact.cityStateZip!=null}">
										${part.contact.cityStateZip}
										</c:if>
									</p>
								</td>
								<td>
									<br />
									<br />
									<!--  <span class="mapThis" onmouseout="popUp(event,'mapThis')"
										onmouseover="popUp(event,'mapThis')"><img
											src="${staticContextPath}/images/icons/mapThis.gif"
											alt="Map This Location" class="inlineBtn" /> </span>-->
								</td>
							</tr>
						</table>
						<p>
							<c:if test="${not empty part.contact.phoneWork}">
								<strong>Work Phone</strong> ${part.contact.phoneWork}
							</c:if>						
						</p>
					</c:when><%-- WHEN for checking if part.contact is NULL--%>
				</c:choose>
			</c:when><%-- WHEN for checking whether shipping section should be displayed --%>
		</c:choose>		
	</div>
	</c:forEach>
</div>
</c:forEach>