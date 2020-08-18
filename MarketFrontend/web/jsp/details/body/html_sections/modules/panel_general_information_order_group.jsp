<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS" value="<%= new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="role" value="${roleType}" />

<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR" value="<%= OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />
<script type="text/javascript">
function expandGroup(id,path){
var divId="groupInfo"+id;
var bodyId="group_menu_body"+id;
jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
var ob=document.getElementById('genImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('genImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('genImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}

jQuery("#buyerLogo").on('error', function(){
	jQuery(this).attr('src','/ServiceLiveWebUtil/images/pixel.gif');
});




</script>
	<c:set var="divName" value="groupInfo"/>
 <c:set var="divName" value="${divName}${summaryDTO.id}"/>
<div id="${divName}" class="menugroup_list">
<c:set var="bodyName" value="group_menu_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
 <c:set var="genImage" value="genImage"/>
    <c:set var="genImage" value="${genImage}${summaryDTO.id}"/>
  <p class="menugroup_head" onclick="expandGroup('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${genImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;General Information</p>

    <div id="${bodyName}" class="menugroup_body">
	<table cellpadding="0" cellspacing="0" class="noMargin">
		<tr>
			<td width="300">
				<p>
					<!--  <img src="${staticContextPath}/images/so_wizard/block150.gif" alt="image"
						title="Placeholder" />-->
					<c:choose>
					<%-- Show Buyer Logo to buyer all the time regardless of the order status. Show Buyer Logo to Provider only
					     after provider accepted the order (meaning Order Status is not Posted/Routed and is one of 
					     Accepted/Active/Complete/Cancelled/Voided/Closed/Problem/Expired). SO in Draft status is only visible
					     to Buyers.--%>
						<c:when test="${role == BUYER_ROLEID || (role == PROVIDER_ROLEID && firstServiceOrder.status != ROUTED_STATUS)}">
							<c:if test="${firstServiceOrder.logodocumentId != null}">	
								<img id="buyerLogo" src="${contextPath}/displayBuyerDocument.action?docId=${firstServiceOrder.logodocumentId}" alt="image" />	<br/>
							</c:if>
						</c:when>
						<c:when test="${role == PROVIDER_ROLEID && firstServiceOrder.status == ROUTED_STATUS}">
							<img src="${staticContextPath}/images/artwork/common/logo_notagline.png" alt="ServiceLive"/>
						</c:when>
					</c:choose>
				</p>
				<table cellpadding="0" cellspacing="0"
					class="adjustedTableRowPadding">
					<tr>
						<td width="120">
							<fmt:message bundle='${serviceliveCopyBundle}' key='details.so.id'/>
						</td>
						<td width="160">
							${groupOrderId}
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message bundle='${serviceliveCopyBundle}' key='details.primary.status'/> 
						</td>
						<td>
							<fmt:message bundle='${serviceliveCopyBundle}' key='workflow.state.${SERVICE_ORDER_CRITERIA_KEY.roleId}.${firstServiceOrder.status}'/>
						</td>
					</tr>
					
					<tr>
						<td>
							<%-- <fmt:message bundle='${serviceliveCopyBundle}' key='details.primary.substatus'/> --%>
						</td>
						<td>
							<%-- 
							<div id="subStatusString" style="display:block">
								<c:if test="${not empty firstServiceOrder.subStatus}">
									<fmt:message bundle="${serviceliveCopyBundle}"
										key="workflow.substate.${firstServiceOrder.subStatus}" />
								</c:if>
								<c:if test="${firstServiceOrder.showSubStatusChange==true}" >
									&nbsp;&nbsp;
									<input type="image" src="${staticContextPath}/images/common/spacer.gif"
										width="62" height="20"
										style="background-image: url(${staticContextPath}/images/btn/change.gif);"
										class="btn20Bevel inlineBtn" 
										onclick="displaySubStatuses()"/>
								</c:if>	
							</div>
							<div id="subStatusDropdown" style="display:none">
								<select style="width: 230px;" class="grayText" id="subStatusId" >
									<c:forEach var="subStatusVO" items="${SOStatusSubStatuses}" >									
										<option value="${subStatusVO.subStatusId}" <c:if test="${firstServiceOrder.subStatus==subStatusVO.subStatusId}" > selected </c:if>>
											${subStatusVO.subStatusName}
										</option>
									</c:forEach>
								</select>
								<br/><br/>
								<input type="image" src="${staticContextPath}/images/common/spacer.gif"
									width="70" height="20"
									style="background-image: url(${staticContextPath}/images/btn/submit.gif);"
									class="btn20Bevel inlineBtn" 
									onclick="submitSummarySubStatus()"/>
								&nbsp;&nbsp;&nbsp;
								<input type="image" src="${staticContextPath}/images/common/spacer.gif"
									width="70" height="20"
									style="background-image: url(${staticContextPath}/images/btn/cancel.gif);"
									class="btn20Bevel inlineBtn" 
									onclick="cancelSummarySubStatus()"/>
									
							</div>
							--%>
						</td>
					</tr>
				</table>
			</td>
			<td>
				<table cellpadding="0" cellspacing="0"
					class="adjustedTableRowPadding">
					<c:forEach var="so" items="${firstServiceOrder.statusAndDateList}">
						<tr>
							<td>
								<b>${so.label}</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								${so.value}
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td valign="top">
							<fmt:message bundle='${serviceliveCopyBundle}' key='details.appt.dates'/>
						</td>
						<td width="15px"></td>
						<td align="left">
							${firstServiceOrder.appointmentDates}
						</td>
					</tr>
					<tr>
						<td>
							<fmt:message bundle='${serviceliveCopyBundle}' key='details.service.window'/>
						</td>
						<td width="15px"></td>
						<td align="left">
							${firstServiceOrder.serviceWindow}
						</td>
					</tr>
					<tr>
						<td colspan="3">
							[ ${firstServiceOrder.serviceWindowComment} ]
						</td>
					</tr>
					<c:if test="${firstServiceOrder.rescheduleDates != null}">
					<tr>
						<td valign="top" class="darkRed">
							<fmt:message bundle='${serviceliveCopyBundle}' key='details.reschedule.dates'/>
						</td>
						<td width="15px"></td>
						<td align="left" class="darkRed">
							${firstServiceOrder.rescheduleDates}
						</td>
					</tr>
					<tr>
						<td class="darkRed">
							<fmt:message bundle='${serviceliveCopyBundle}' key='details.reschedule.window'/>
						</td>
						<td width="15px"></td>
						<td align="left" class="darkRed">
							${firstServiceOrder.rescheduleServiceWindow}
						</td>
					</tr>
					</c:if>	
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>

					<c:if test="${firstServiceOrder.continuationOrderID != null}">
						<tr>
							<td>
								<fmt:message bundle='${serviceliveCopyBundle}' key='details.continuation.soid'/>
							</td>
							<td width="15px">
							</td>
							<td align="left">
								<a href="#">${firstServiceOrder.continuationOrderID}</a>
							</td>
						</tr>
						<tr>
							<td>
								<fmt:message bundle='${serviceliveCopyBundle}' key='details.reason'/>
							</td>
							<td width="15px">
							</td>
							<td align="left">
								${firstServiceOrder.continuationReason}
							</td>
						</tr>
					</c:if>					
				</table>
			</td>
		</tr>
	</table>
	<p>
		<fmt:message bundle='${serviceliveCopyBundle}' key='details.title'/>
		<br />
		${firstServiceOrder.mainServiceCategory} Group Order
	</p>

	<p>
		<table cellpadding="0" cellspacing="0" class="contactInfoTable">
			<tr>
				<td class="column1">
					<p class="text11px">
						<strong>Service Location Information</strong>
						<br />
						${firstServiceOrder.locationContact.type}
						<br />
						<c:if
							test="${roleType == BUYER_ROLEID || roleType == SIMPLE_BUYER_ROLEID || (roleType == PROVIDER_ROLEID && firstServiceOrder.status != ROUTED_STATUS)}">						
						${firstServiceOrder.locationContact.individualName}
					</c:if>
						<br />
						<c:choose>
							<c:when
								test="${roleType == BUYER_ROLEID || roleType == SIMPLE_BUYER_ROLEID || (roleType == PROVIDER_ROLEID && (firstServiceOrder.status != ROUTED_STATUS && firstServiceOrder.status != CLOSED_STATUS))}">
							${firstServiceOrder.locationContact.streetAddress}&nbsp;&nbsp;
							<!--  <span class="mapThis" onmouseout="popUp(event,'mapThis')"
								onmouseover="popUp(event,'mapThis')"><img
									src="${staticContextPath}/images/icons/mapThis.gif"
									alt="Map This Location" class="inlineBtn" /> </span>-->
								<br />
								<c:if
									test="${not empty firstServiceOrder.locationContact.streetAddress2}">
								${firstServiceOrder.locationContact.streetAddress2}&nbsp;
							</c:if>
								<br />
							</c:when>
						</c:choose>
						${firstServiceOrder.locationContact.cityStateZip}
					</p>
				</td>

				<c:choose>
					<c:when
						test="${roleType == BUYER_ROLEID || roleType == SIMPLE_BUYER_ROLEID || (roleType == PROVIDER_ROLEID && firstServiceOrder.status != ROUTED_STATUS)}">
						<td class="column2">
							<p class="text11px">
								<c:choose>
									<c:when test="${firstServiceOrder.locationContact != null}">
										<c:if test="${not empty firstServiceOrder.locationContact.phoneWork}">
											<strong>Work Phone</strong>
											<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.phoneHome}">
											<strong>Home Phone</strong>
											<br />
										</c:if>
										<c:if
											test="${not empty firstServiceOrder.locationContact.phoneMobile}">
											<strong>Mobile Phone</strong>
											<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.pager}">
											<strong>Pager</strong>
											<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.other}">
											<strong>Other</strong>
											<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.fax}">
											<strong>Fax</strong>
											<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.email}">
											<strong>Email</strong>
											<br />
										</c:if>
									</c:when>
								</c:choose>
							</p>
						</td>

						<td class="column3">
							<p class="text11px">
								<c:choose>
									<c:when test="${firstServiceOrder.locationContact != null}">
										<c:if test="${not empty firstServiceOrder.locationContact.phoneWork}">
									${firstServiceOrder.locationContact.phoneWork}<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.phoneHome}">
									${firstServiceOrder.locationContact.phoneHome}<br />
										</c:if>
										<c:if
											test="${not empty firstServiceOrder.locationContact.phoneMobile}">
									${firstServiceOrder.locationContact.phoneMobile}<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.pager}">
									${firstServiceOrder.locationContact.pager}<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.other}">
									${firstServiceOrder.locationContact.other}<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.fax}">
									${firstServiceOrder.locationContact.fax}<br />
										</c:if>
										<c:if test="${not empty firstServiceOrder.locationContact.email}">
									${firstServiceOrder.locationContact.email}<br />
										</c:if>
									</c:when>
								</c:choose>
							</p>
						</td>
					</c:when>
				</c:choose>
			</tr>
		</table>
		<p>




	<c:if test="${groupOrderId == null}">
		<c:choose>
			<c:when
				test="${role == BUYER_ROLEID || (role == PROVIDER_ROLEID && firstServiceOrder.status != ROUTED_STATUS)}">
				<hr />
				<table cellpadding="0" cellspacing="0"
					class="adjustedTableRowPadding">
					<c:if test="${firstServiceOrder.selByerRefDTO != null}">
						<tr>
							<td colspan="2">
								<fmt:message bundle='${serviceliveCopyBundle}'
									key='details.buyer.ref' />
							</td>
						</tr>
						<tr>
							<td>

								<table cellpadding="0" cellspacing="0" class>
									<c:forEach var="byerRef"
										items="${firstServiceOrder.selByerRefDTO}"
										varStatus="rowCounter">
										<c:if test="${rowCounter.count % 2 == 1}">
											<tr>
												<td width="150">
													<strong>${byerRef.refType}</strong>
												</td>
												<td width="80">
													${byerRef.refValue}
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>

							</td>
							<td width="150"></td>
							<td>


								<table cellpadding="0" cellspacing="0" class>
									<c:forEach var="byerRef"
										items="${firstServiceOrder.selByerRefDTO}"
										varStatus="rowCounter">
										<c:if test="${rowCounter.count % 2 == 0}">
											<tr>
												<td width="150">
													<strong>${byerRef.refType}</strong>
												</td>
												<td width="80">
													${byerRef.refValue}
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>

							</td>
						</tr>

					</c:if>
				</table>
			</c:when>
		</c:choose>
	</c:if>		
</div>
