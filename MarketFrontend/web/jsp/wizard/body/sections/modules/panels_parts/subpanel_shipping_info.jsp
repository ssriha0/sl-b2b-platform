<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="subpanelHdr" id="shippingInfo${sowPartDTO.index}" onclick="togglePartsSubpanel(this);" 
	<c:if test="${hasShippingInfo}">
	style="background-image:url(${staticContextPath}/images/icons/arrowDown-darkgray.gif);  background-position:0 5px;"
	</c:if>>Shipping Information (if applicable)</h3>
<div class="subpanelBody" <c:if test="${hasShippingInfo}">
	style="display:block;"
	</c:if>>
	<table>
		<tr>
			<td colspan="5" align="left">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.text" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.label.carrier" />
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.label.track.number" />
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.shipping.label.ship.date" />
			</td>
			<td>
				&nbsp;&nbsp;
			</td>

		</tr>
		<tr>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> Shipping Carrier">
					<select name="parts[${sowPartDTO.index}].shippingCarrierId"
						id="parts[${sowPartDTO.index}].shippingCarrierId"
						style="width: 150px;"
						onchange="checkCarrierType('parts[${sowPartDTO.index}].shippingCarrierId','parts[${sowPartDTO.index}].otherShippingCarrier', 'partShippingStatus[${sowPartDTO.index}]')">
						<option value="">
							Select Shipping
						</option>
						<c:forEach var="lookupVO" items="${shippingCarrier}">
							<c:choose>
							<c:when
								test="${lookupVO.id == parts[sowPartDTO.index].shippingCarrierId }">
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
			<td>
				<s:textfield name="parts[%{#sowPartDTO.index}].otherShippingCarrier"
					id="parts[%{#sowPartDTO.index}].otherShippingCarrier"
					value="%{parts[#sowPartDTO.index].otherShippingCarrier}" size="30"
					maxlength="30" theme="simple"
					cssStyle=" width: 150px;%{parts[sowPartDTO.index].otherShippingCarrier != null && 
							                        parts[sowPartDTO.index].shippingCarrierId != null && 
							  						parts[sowPartDTO.index].otherShippingCarrier != '' ||
							  						parts[sowPartDTO.index].shippingCarrierId == '3' ? 'display:block;' : 'display:none;'}"
					cssClass="shadowBox grayText" />
			</td>
			<td>
				<tags:fieldError
					id="Part ${sowPartDTO.count} --> Shipping Tracking No">
					<s:textfield name="parts[%{#sowPartDTO.index}].shippingTrackingNo"
						id="parts[%{#sowPartDTO.index}].shippingTrackingNo"
						size="50" maxlength="50"
						value="%{parts[#sowPartDTO.index].shippingTrackingNo}"
						theme="simple" cssStyle="width: 130px;"
						cssClass="shadowBox grayText"/>
				</tags:fieldError>
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> Ship Date">
					<input type="text" dojoType="dijit.form.DateTextBox"
						style="width: 80px;" class="shadowBox"
						id="parts[<s:property value="%{#sowPartDTO.index}"/>].shipDate"
						name="parts[<s:property value="%{#sowPartDTO.index}"/>].shipDate"
						value="<s:property value="%{parts[#sowPartDTO.index].shipDate}"/>"
						constraints="{strict: 'true',datePattern:'MM/dd/yyyy'}"
						invalidMessage="* The date format is mm/dd/yyyy. Date field will not be saved if not corrected! "
						required="false" lang="en-us" />
						

				</tags:fieldError>
			</td>
			<td>
				<c:choose>
					<c:when test="${parts[sowPartDTO.index].enablePartShipStatus == true }">
						<input id="partShippingStatus[${sowPartDTO.index}]" type="button" class="grayButton" value="GET STATUS" 
							onclick="getTrackingInformation('parts[${sowPartDTO.index}].shippingCarrierId', 'parts[${sowPartDTO.index}].shippingTrackingNo')"/>
					</c:when>
					<c:otherwise>
						<input id="partShippingStatus[${sowPartDTO.index}]" type="button" class="grayButton" value="GET STATUS"  disabled="disabled" 
							onclick="getTrackingInformation('parts[${sowPartDTO.index}].shippingCarrierId', 'parts[${sowPartDTO.index}].shippingTrackingNo')"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</div>