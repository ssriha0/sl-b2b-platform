<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="subpanelHdr" id="partPickupInfo${sowPartDTO.index}" onclick="togglePartsSubpanel(this);" 
<c:if test="${hasPartPickUpInfo}">
	style="background-image:url(${staticContextPath}/images/icons/arrowDown-darkgray.gif); background-position:0 5px;"
	</c:if>>Part Pick Up Information (if applicable)</h3>
<div class="subpanelBody" <c:if test="${hasPartPickUpInfo}">
	style="display:block;"
	</c:if>>

		<div style="width: 550px">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="wizard.parts.parts.pickup.text" />
		</div>

	<table width="350" cellpadding="0" cellspacing="0"
		style="margin-top: 0px;">
		<tr>
			<td colspan="2">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.parts.pickup.location.name" />
				</label>
				<br />
				<input type="text"
					name="parts[${sowPartDTO.index}].pickupContactLocation.businessName"
					size="50" maxlength="100"
					value="${parts[sowPartDTO.index].pickupContactLocation.businessName}"
					style="width: 342px;"
					class="shadowBox grayText" />
			</td>
		</tr>
	</table>
	<table width="450" cellpadding="0" cellspacing="0">
		<tr>
			<td width="365">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.street.name" />
				</label>
				<br />

				<input type="text"
					name="parts[${sowPartDTO.index}].pickupContactLocation.streetName1"
					size="30" maxlength="50"
					value="${parts[sowPartDTO.index].pickupContactLocation.streetName1}"
					style="width: 342px;"
					class="shadowBox grayText" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="text"
					name="parts[${sowPartDTO.index}].pickupContactLocation.streetName2"
					size="30" maxlength="30"
					value="${parts[sowPartDTO.index].pickupContactLocation.streetName2}"
					style="width: 342px;"
					class="shadowBox grayText" />
			</td>
		</tr>
	</table>
	<table width="380" cellpadding="0" cellspacing="0">
		<tr>
			<td width="145">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.city" />
				</label>
				<br />
				<input type="text"
					name="parts[${sowPartDTO.index}].pickupContactLocation.city"
					size="30" maxlength="50"
					value="${parts[sowPartDTO.index].pickupContactLocation.city}"
					style="width: 130px;"
					class="shadowBox grayText" />
			</td>
			&nbsp;&nbsp;
			<td width="100">
				<label>
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="wizard.label.state" />
				</label>
				<br />
				<select
					name="parts[${sowPartDTO.index}].pickupContactLocation.state"
					id="parts[${sowPartDTO.index}].pickupContactLocation.state"
					style="width: 110px;">
					<option value="">
						Select State
					</option>
					<c:forEach var="lookupVO" items="${stateCodes}">
						<c:choose>
						<c:when
							test="${lookupVO.type == parts[sowPartDTO.index].pickupContactLocation.state }">
							<option selected="selected" value="${lookupVO.type}">
								${lookupVO.descr}
							</option>
						</c:when>
						<c:otherwise>
							<option value="${lookupVO.type}">
								${lookupVO.descr}
							</option>
						</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</td>
			&nbsp;&nbsp;
			<td width="40px">
				<tags:fieldError id="Part ${sowPartDTO.count} --> Zip">
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="wizard.label.zip" />
					</label>
					<br />
					<input type="text"
						name="parts[${sowPartDTO.index}].pickupContactLocation.zip"
						size="10" maxlength="5"
						value="${parts[sowPartDTO.index].pickupContactLocation.zip}"
						style="width: 40px;"
						class="shadowBox grayText" />
				</tags:fieldError>
			</td>
			&nbsp;&nbsp;

			<td width="130">
				<br />
				<tags:fieldError id="Zipcode">		
						- <input type="text"
						name="parts[${sowPartDTO.index}].pickupContactLocation.zip4"
						size="10" maxlength="4"
						value="${parts[sowPartDTO.index].pickupContactLocation.zip4}"
						style="width: 30px;"
						class="shadowBox grayText" />
				</tags:fieldError>
			</td>
		</tr>
	</table>
	<table>
		<tr>
			<td colspan="2">

			</td>


		</tr>

	</table>
	<c:forEach
		items="${parts[sowPartDTO.index].pickupContactLocation.phones}"
		varStatus="sOWPhoneDTO">
		<table width="450" cellpadding="0" cellspacing="0">
			<tr>
				<c:if test="${sOWPhoneDTO.index == 0}">
					<td width="175">
						<tags:fieldError id="Part ${sowPartDTO.count} --> phone">
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.label.phone.num" />
							</label>
							<br />
							<input type="text"
								name="parts[${sowPartDTO.index}].pickupContactLocation.phones[${sOWPhoneDTO.index}].areaCode"
								size="3" maxlength="3"
								value="${parts[sowPartDTO.index].pickupContactLocation.phones[sOWPhoneDTO.index].areaCode}"
								style="width: 30px;"
								class="shadowBox grayText" />	
								 	 
								-
								<input type="text"
								name="parts[${sowPartDTO.index}].pickupContactLocation.phones[${sOWPhoneDTO.index}].phonePart1"
								size="3" maxlength="3"
								value="${parts[sowPartDTO.index].pickupContactLocation.phones[sOWPhoneDTO.index].phonePart1}"
								style="width: 30px;"
								class="shadowBox grayText" />							
								-
								<input type="text"
								name="parts[${sowPartDTO.index}].pickupContactLocation.phones[${sOWPhoneDTO.index}].phonePart2"
								size="3" maxlength="4"
								value="${parts[sowPartDTO.index].pickupContactLocation.phones[sOWPhoneDTO.index].phonePart2}"
								style="width: 45px;"
								class="shadowBox grayText" />
						</tags:fieldError>
					<td width="75">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="wizard.label.ext" />
						</label>
						<br />
						<input type="text"
							name="parts[${sowPartDTO.index}].pickupContactLocation.phones[${sOWPhoneDTO.index}].ext"
							size="6" maxlength="6"
							value="${parts[sowPartDTO.index].pickupContactLocation.phones[sOWPhoneDTO.index].ext}"
							style="width: 55px;"
							class="shadowBox grayText" />
					</td>
					<td width="130">
						<tags:fieldError id="Part ${sowPartDTO.count} --> Phone Type">
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.label.phone.type" />
							</label>
							<br />
							<s:select
								name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr.sOWPhoneDTO.index}].phoneClassId"
								headerKey="-1" headerValue="Select One" cssStyle="width: 120px;"
								size="1" theme="simple" list="#session.phoneTypes" listKey="id"
								listValue="descr" />
						</tags:fieldError>
					</td>
				</c:if>
				<c:if test="${sOWPhoneDTO.index == 1}">
					<td width="175">
						<tags:fieldError id="Part ${sowPartDTO.count} --> Alternate_phone">
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.label.phone.alt" />
							</label>
							<br />
							<input type="text"
								name="parts[${sowPartDTO.index}].pickupContactLocation.phones[${sOWPhoneDTO.index}].areaCode"
								size="3" maxlength="3"
								value="${parts[sowPartDTO.index].pickupContactLocation.phones[sOWPhoneDTO.index].areaCode}"
								style="width: 30px;"
								class="shadowBox grayText" />	
								 	 
								-
								<input type="text"
								name="parts[${sowPartDTO.index}].pickupContactLocation.phones[${sOWPhoneDTO.index}].phonePart1"
								size="3" maxlength="3"
								value="${parts[sowPartDTO.index].pickupContactLocation.phones[sOWPhoneDTO.index].phonePart1}"
								style="width: 30px;"
								class="shadowBox grayText" />							
								-
								<input type="text"
								name="parts[${sowPartDTO.index}].pickupContactLocation.phones[${sOWPhoneDTO.index}].phonePart2"
								size="3" maxlength="4"
								value="${parts[sowPartDTO.index].pickupContactLocation.phones[sOWPhoneDTO.index].phonePart2}"
								style="width: 45px;"
								class="shadowBox grayText" />
						</tags:fieldError>
					</td>
					<td width="75">
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="wizard.label.ext" />
						</label>
						<input type="text"
							name="parts[${sowPartDTO.index}].pickupContactLocation.phones[${sOWPhoneDTO.index}].ext"
							size="6" maxlength="6"
							value="${parts[sowPartDTO.index].pickupContactLocation.phones[sOWPhoneDTO.index].ext}"
							style="width: 55px;"
							class="shadowBox grayText" />
					</td>
					<td width="130">
						<tags:fieldError
							id="Part ${sowPartDTO.count} --> Alternate Phone Type">
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="wizard.label.phone.type" />
							</label>
							<br />
							<s:select
								name="parts[%{#sowPartDTO.index}].pickupContactLocation.phones[%{#attr.sOWPhoneDTO.index}].phoneClassId"
								headerKey="-1" headerValue="Select One" cssStyle="width: 120px;"
								size="1" theme="simple" list="#session.phoneTypes" listKey="id"
								listValue="descr" />
						</tags:fieldError>
				</c:if>
			</tr>


		</table>
	</c:forEach>
	<table width="550px">
		<tr>
			<td colspan="3" align="left">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.parts.contact.information" />
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.firstname" />
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.lastname" />
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.email" />
			</td>
		</tr>
		<tr>
			<td width="183">
				<tags:fieldError id="Part ${sowPartDTO.count} --> First Name">
					<input type="text"
						name="parts[${sowPartDTO.index}].pickupContactLocation.firstName"
						size="50" maxlength="50"
						value="${parts[sowPartDTO.index].pickupContactLocation.firstName}"
						style="width: 160px;"
						class="shadowBox grayText" />
				</tags:fieldError>
			</td>
			<td width="183">
				<tags:fieldError id="Part ${sowPartDTO.count} --> Last Name">
					<input type="text"
						name="parts[${sowPartDTO.index}].pickupContactLocation.lastName"
						size="50" maxlength="50"
						value="${parts[sowPartDTO.index].pickupContactLocation.lastName}"
						style="width: 160px;"
						class="shadowBox grayText" />
				</tags:fieldError>
			</td>
			<td width="183">
				<tags:fieldError id="Part ${sowPartDTO.count} --> Email">
					<input type="text"
						name="parts[${sowPartDTO.index}].pickupContactLocation.email"
						maxlength="255"
						value="${parts[sowPartDTO.index].pickupContactLocation.email}"
						style="width: 160px;"
						class="shadowBox grayText" />
				</tags:fieldError>
			</td>
		</tr>
	</table>
</div>
