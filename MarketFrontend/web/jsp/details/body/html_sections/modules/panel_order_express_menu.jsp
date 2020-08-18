<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.web.constants.QuickLinksConstants"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<table cellspacing="0" cellpadding="0" border="0" width="100%"
	style="table-layout: fixed;">

	<tbody>
		<tr>
			<td width="45%"><fmt:message bundle="${serviceliveCopyBundle}"
					key="widget.label.soid" /></td>
			<td width="55%">${soID}</td>
		</tr>
		<tr>
			<td><fmt:message bundle="${serviceliveCopyBundle}"
					key="widget.label.title" /></td>
			<td style="word-wrap: break-word;">${title}</td>
		</tr>
		<tr>
			<td><fmt:message bundle="${serviceliveCopyBundle}"
					key="widget.label.status" /></td>
			<td>
				<c:if test="${THE_SERVICE_ORDER_STATUS_CODE != null}">
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="workflow.state.${SERVICE_ORDER_CRITERIA_KEY.roleId}.${THE_SERVICE_ORDER_STATUS_CODE}" />
				</c:if>
			</td>
		</tr>
	<c:if test="${(viewOrderPricing==true && roleType==1)  ||  roleType==3}">
			<c:if
				test="${priceModelBid == false || THE_SERVICE_ORDER_STATUS_CODE != 110}">
				<c:if test="${status != 'Pending Cancel'}">

					<tr>
						<td><fmt:message bundle="${serviceliveCopyBundle}"
								key="widget.label.posted.price" /></td>
						<td>
						<c:choose>
						<c:when test="${status == 'Deleted' || status == 'Voided'}">
								$0.00
						</c:when> <c:otherwise>
								$<fmt:formatNumber value="${spendLimit}" type="NUMBER"
									groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2" />
						</c:otherwise>
						</c:choose>
						</td>
					</tr>

				</c:if>
			</c:if>
			<c:if
				test="${finalPrice == null && (status == 'Deleted' || status == 'Voided')}">
				<tr>
					<td><b><fmt:message bundle="${serviceliveCopyBundle}"
								key="widget.label.final.price" /></b></td>
					<td>$0.00</td>
				</tr>
			</c:if>
			<c:if test="${finalPrice != null}">
				<tr>
					<td><b><fmt:message bundle="${serviceliveCopyBundle}"
								key="widget.label.final.price" /></b></td>
					<td><c:choose>
						<c:when test="${status == 'Deleted' || status == 'Voided'}">
								$0.00
							</c:when> <c:otherwise>
								$<fmt:formatNumber value="${finalPrice}" type="NUMBER"
								groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2" />
						</c:otherwise> </c:choose></td>
				</tr>
			</c:if>

			<c:if test="${status == 'Pending Cancel'}">

				<tr>

					<td><b>Cancel Amount: </b></td>
					<td><c:if test="${pendingCancelSubstatus == 'pendingReview'}">
					
							$<fmt:formatNumber value="${providerPrice}" type="NUMBER"
								groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2" />
						</c:if> <c:if test="${pendingCancelSubstatus == 'pendingResponse'}">
					
							$<fmt:formatNumber value="${buyerPrice}" type="NUMBER"
								groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2" />

						</c:if> <c:if test="${pendingCancelSubstatus == null}">
							$<fmt:formatNumber value="${buyerPrice}" type="NUMBER"
								groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2" />

						</c:if></td>
				</tr>
			</c:if>
		</c:if>

		<tr
			onclick="newco.jsutils.getBuyerWidgetDisplayValue(3, '110', 'name', 'id')">
			<td><fmt:message bundle="${serviceliveCopyBundle}"
					key="widget.label.buyer" /></td>
			<td><div id="soid${theTab}">${buyer}</div></td>
		</tr>		
		<!--This will manage if so accepted by firm level and un assigned.
		       We have to show the firm level details along with showing provider 
		       as unassigned  -->
		 <c:if test="${(THE_SERVICE_ORDER_STATUS_CODE == 150 || THE_SERVICE_ORDER_STATUS_CODE == 180 || THE_SERVICE_ORDER_STATUS_CODE == 155 ||
 		THE_SERVICE_ORDER_STATUS_CODE == 165 ||
		THE_SERVICE_ORDER_STATUS_CODE == 170 || THE_SERVICE_ORDER_STATUS_CODE == 120 ) &&  roleType==3}">
			<tr>
			     <td>
			       	<fmt:message bundle="${serviceliveCopyBundle}"key="widget.label.provider.firm" />
			    </td>
			     <td>
			         <a href="javascript:void(0);" onclick="openProviderFirmProfile('${acceptedVendorId}')">
			         	${firmName}(${acceptedVendorId})
			         </a>
		           <br>
					${firmPhoneNumber}
				 </td>
			</tr>
		</c:if>
		
		<tr>
			<td><fmt:message bundle="${serviceliveCopyBundle}"
					key="widget.label.provider" /></td>
			
			<c:choose>
			<c:when
				test="${THE_SERVICE_ORDER_STATUS_CODE > 110 && assignmentType=='FIRM'}">
				<td>
					<div style="float: left">Unassigned</div>
					<div style="float: left;padding-left: 3px;">
						<img src="${staticContextPath}/images/icons/icon-unassigned.png"
							title="Assign a provider prior to service start" style="height: 50%; width: 50%;">
					</div>
				</td>
			</c:when>
			<c:otherwise>
				<c:choose>
				<c:when test="${provider != 'N/A'} && ${fn:contains(provider,'(')}">
				<td style="word-wrap: break-word;">		
					<c:set var="provId" value="${fn:substringAfter(provider, '(')}"></c:set>
					<c:set var="provId"  value="${fn:substringBefore(provId, ')')}"></c:set>
					<c:choose>
					<c:when test="${roleType==3}">
						<a href="javascript:void(0);" onclick="openProviderProfile ('${provId}', '${acceptedVendorId}','/MarketFrontend/'); ">
							${fn:substringAfter(fn:substringBefore(provider,
							"("),",")}${fn:substringBefore(fn:substringBefore(provider,
							"("),",")}&nbsp;(User Id# ${fn:substringAfter(provider, "(")}</a>
					</c:when>
					<c:otherwise>
						${fn:substringAfter(fn:substringBefore(provider,
							"("),",")}${fn:substringBefore(fn:substringBefore(provider,
							"("),",")}&nbsp;(User Id# ${fn:substringAfter(provider, "(")}
					</c:otherwise>
					</c:choose>
						
					</td>
				</c:when>
				<c:otherwise>
					<td style="word-wrap: break-word;">${provider}</td>
				</c:otherwise>
				</c:choose>
			</c:otherwise>
			</c:choose>

		</tr>
		<!--  If the provider's phone number exists, display here (1) Main office (2) Alternate Number obtained from vendor's primary resource)-->
		<c:if test="${assignmentType=='PROVIDER'}">
			<c:if test="${not empty providerMainPhoneNumber}">
				<tr>
					<td></td>
					<td>${providerMainPhoneNumber}</td>
				</tr>
			</c:if>
			<c:if test="${not empty providerAlternatePhoneNumber}">
				<tr>
					<td></td>
					<td>${providerAlternatePhoneNumber}</td>
				</tr>
			</c:if>
		</c:if>
		<tr>
			<td><fmt:message bundle="${serviceliveCopyBundle}"
					key="widget.label.end.customer" /></td>
			<td>${endCustomer}</td>
		</tr>
		<tr>
			<td></td>
			<td>${endCustomerPrimaryPhoneNumber}</td>
		</tr>
		<tr>
			<td><fmt:message bundle="${serviceliveCopyBundle}"
					key="widget.label.location" /></td>
			<td style="word-wrap: break-word;">${location}<br /> <c:if
					test="${role == PROVIDER_ROLEID && assignmentType=='PROVIDER' ||assignmentType==null}">
					
					<c:choose>
					<c:when test="${not empty distanceInMiles}">
									(${distanceInMiles} miles) (Center zip to center zip)									
					</c:when>
					<c:otherwise>
									Distance varies by Provider
					</c:otherwise>
					</c:choose>
				</c:if>
			</td>
		</tr>
		<tr>
			<c:if test="${not empty appointmentDates}">
				<td><fmt:message bundle="${serviceliveCopyBundle}"
						key="widget.label.appt.dates" /></td>
				<td>${appointmentDates}</td>
			</c:if>
		</tr>
		<tr>
			<c:if test="${not empty serviceWindow}">
				<td><fmt:message bundle="${serviceliveCopyBundle}"
						key="widget.label.service.window" /></td>
				<td>${serviceWindow}</td>
			</c:if>
		</tr>
		<tr>
		<c:choose>
			<c:when test="${not empty recallProvider && recallProvider == true && not empty originalSoId}">
				<td><fmt:message bundle="${serviceliveCopyBundle}" key="widget.label.original.order.no" /></td>
				<td><a id="originalSOD" href="soDetailsController.action?soId=${originalSoId}"  target="_blank">${originalSoId}</a></td>
			</c:when>
			<c:when test="${not empty recallProvider && recallProvider == false && not empty originalSoId}">
				<td><fmt:message bundle="${serviceliveCopyBundle}" key="widget.label.original.order.no" /></td>
				<td><a id="originalSODRestricted" href="soDetailsWarrantyOrderSummary.action?soId=${originalSoId}" target="_blank">${originalSoId}</a></td>
			</c:when>
			<c:otherwise></c:otherwise>
		</c:choose>	
		</tr>
		<tr>
			<c:if test="${role == PROVIDER_ROLEID && not empty fromLoc}">
				<td colspan="2"><a href="javascript:void(0)"
					onClick="openGoogleMap('${THE_SERVICE_ORDER_STATUS_CODE}','${fromLoc}','${zip}','${toLocation}');">
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="widget.label.google.direction" />
				</a></td>
			</c:if>
		</tr>
	</tbody>
</table>
