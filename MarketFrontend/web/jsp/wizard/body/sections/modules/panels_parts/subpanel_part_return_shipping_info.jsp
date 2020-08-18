<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="subpanelHdr" id="partReturnInfo${sowPartDTO.index}" onclick="togglePartsSubpanel(this);" 
<c:if test="${hasPartReturnShippingInfo}">
	style="background-image:url(${staticContextPath}/images/icons/arrowDown-darkgray.gif);  background-position:0 5px;"
	</c:if>>Part and Core Part Return Shipping Information (if applicable)</h3>
<div  class="subpanelBody" <c:if test="${hasPartReturnShippingInfo}"> style="display:block;"</c:if>>
	<table>

		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.label.return.carrier" />
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.label.return.track.number" />
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<tags:fieldError
					id="Part ${sowPartDTO.count} --> Part Return Carrier">
					<select name="parts[${sowPartDTO.index}].returnCarrierId"
						id="parts[${sowPartDTO.index}].returnCarrierId"
						style="width: 200px;"
						onchange="checkCarrierType('parts[${sowPartDTO.index}].returnCarrierId','parts[${sowPartDTO.index}].otherReturnCarrier', 'partReturnShippingStatus[${sowPartDTO.index}]')">
						<option value="">
							Select Shipping
						</option>
						<c:forEach var="lookupVO" items="${shippingCarrier}">
							<c:choose>
							<c:when
								test="${lookupVO.id == parts[sowPartDTO.index].returnCarrierId }">
								<option selected="selected" value="${lookupVO.id}">
									${lookupVO.descr}
								</option>
							</c:when>
							<c:otherwise>
								<option value="${lookupVO.id}">
									${lookupVO.descr}
								</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</tags:fieldError>
			</td>
			<td align="left">
				<s:textfield name="parts[%{#sowPartDTO.index}].otherReturnCarrier"
					size="30" id="parts[%{#sowPartDTO.index}].otherReturnCarrier"
					value="%{parts[#sowPartDTO.index].otherReturnCarrier}"
					maxlength="30" theme="simple"
					cssStyle=" width: 150px;%{parts[sowPartDTO.index].otherReturnCarrier!= null &&
							                         parts[sowPartDTO.index].returnCarrierId!= null && 
							  						parts[sowPartDTO.index].otherReturnCarrier != '' ||
							  						parts[sowPartDTO.index].returnCarrierId == '3' ? 'display:block;' : 'display:none;'}"
					cssClass="shadowBox grayText" />
			</td>
			<td>
				<tags:fieldError
							id="Part ${sowPartDTO.count} --> Part Return Tracking No">
					<s:textfield id="parts[%{#sowPartDTO.index}].returnTrackingNo"
						name="parts[%{#sowPartDTO.index}].returnTrackingNo"
						size="50" maxlength="50"
						value="%{parts[#sowPartDTO.index].returnTrackingNo}" theme="simple"
						cssStyle="width: 150px;" cssClass="shadowBox grayText" />
				</tags:fieldError>		
			</td>
			<td>
				<c:choose>
					<c:when test="${parts[sowPartDTO.index].enablePartRetShipStatus == true }">
						<input id="partReturnShippingStatus[${sowPartDTO.index}]" class="grayButton" type="button" value="GET STATUS" 
							onclick="getTrackingInformation('parts[${sowPartDTO.index}].returnCarrierId', 'parts[${sowPartDTO.index}].returnTrackingNo')"/>
					</c:when>
					<c:otherwise>
						<input id="partReturnShippingStatus[${sowPartDTO.index}]" class="grayButton" type="button" value="GET STATUS"  disabled="disabled"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="left">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.core.return.text" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.label.core.return.carrier" />
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.label.core.return.track.number" />
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<tags:fieldError
					id="Part ${sowPartDTO.count} --> Core Part Return Carrier">
					<select name="parts[${sowPartDTO.index}].coreReturnCarrierId"
						id="parts[${sowPartDTO.index}].coreReturnCarrierId"
						style="width: 200px;"
						onchange="checkCarrierType('parts[${sowPartDTO.index}].coreReturnCarrierId','parts[${sowPartDTO.index}].otherCoreReturnCarrier', 'corePartReturnShippingStatus[${sowPartDTO.index}]')">
						<option value="">
							Select Shipping
						</option>
						<c:forEach var="lookupVO" items="${shippingCarrier}">
							<c:choose>
							<c:when
								test="${lookupVO.id == parts[sowPartDTO.index].coreReturnCarrierId }">
								<option selected="selected" value="${lookupVO.id}">
									${lookupVO.descr}
								</option>
							</c:when>
							<c:otherwise>
								<option value="${lookupVO.id}">
									${lookupVO.descr}
								</option>
							</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</tags:fieldError>
			</td>
			<td align="left">
				<s:textfield
					name="parts[%{#sowPartDTO.index}].otherCoreReturnCarrier" size="30"
					id="parts[%{#sowPartDTO.index}].otherCoreReturnCarrier"
					value="%{parts[#sowPartDTO.index].otherCoreReturnCarrier}"
					maxlength="30" theme="simple"
					cssStyle=" width: 150px;%{parts[sowPartDTO.index].otherCoreReturnCarrier!= null &&
							                         parts[sowPartDTO.index].coreReturnCarrierId!= null && 
							  						parts[sowPartDTO.index].otherCoreReturnCarrier != '' ||
							  						parts[sowPartDTO.index].coreReturnCarrierId == '3' ? 'display:block;' : 'display:none;'}"
					cssClass="shadowBox grayText" />
			</td>
			<td>
				<tags:fieldError
						id="Part ${sowPartDTO.count} --> Part Core Return Tracking No">
					<s:textfield id="parts[%{#sowPartDTO.index}].coreReturnTrackingNo"
						name="parts[%{#sowPartDTO.index}].coreReturnTrackingNo"	
						size="50" maxlength="50"
						value="%{parts[#sowPartDTO.index].coreReturnTrackingNo}"
						theme="simple" cssStyle="width: 150px;"
						cssClass="shadowBox grayText" />
				</tags:fieldError>		
			</td>
			<td>
				<c:choose>
					<c:when test="${parts[sowPartDTO.index].enableCorePartRetShipStatus == true }">
						<input id="corePartReturnShippingStatus[${sowPartDTO.index}]" class="grayButton" type="button" value="GET STATUS" 
							onclick="getTrackingInformation('parts[${sowPartDTO.index}].coreReturnCarrierId', 'parts[${sowPartDTO.index}].coreReturnTrackingNo')"/>
					</c:when>
					<c:otherwise>
						<input id="corePartReturnShippingStatus[${sowPartDTO.index}]" class="grayButton" type="button" value="GET STATUS" disabled="disabled"/>
					</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
	</table>
</div>