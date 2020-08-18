<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="subpanelHdr" id="morePartInfo${sowPartDTO.index}" onclick="togglePartsSubpanel(this);"
<c:if test="${hasMorePartInfo}">
	style="background-image:url(${staticContextPath}/images/icons/arrowDown-darkgray.gif); background-position:0 5px;"
	</c:if>>More Part Information (OEM and Vendor Numbers, Part dimensions)</h3>
<div class="subpanelBody" <c:if test="${hasMorePartInfo}">
	style="display:block;"
	</c:if>>
	<table cellspacing="0">
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.addl.part.info" />
			</td>
		</tr>
		<tr>
			<td>
				<tags:fieldError
					id="Part ${sowPartDTO.count} --> Additional Part Information">
					<s:textarea name="parts[%{#sowPartDTO.index}].additionalPartInfo"
						value="%{parts[#sowPartDTO.index].additionalPartInfo}"
						theme="simple" cssStyle="width: 565px;"
						cssClass="shadowBox grayText"
						onkeydown="limitCharsTextarea(this,1000,'additionalPartDesclimitinfo');"
						onkeyup="limitCharsTextarea(this,1000,'additionalPartDesclimitinfo');" />
				</tags:fieldError>
				<div id="additionalPartDesclimitinfo">
					<span><i><b>1000</b>characters remaining</i> </span>
			</td>
			</div>
		</tr>
	</table>
	<table cellspacing="0">
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.manufacturer.part.number" />
			</td>
			<td></td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.vendor.part.number" />
			</td>
			<td></td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.alt.part.ref.1" />
			</td>
			<td></td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.alt.part.ref.2" />
			</td>

		</tr>
		<tr>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> OEM Number"
					oldClass="paddingBtm">
					<s:textfield size="50" maxlength="100"
						name="parts[%{#sowPartDTO.index}].manufacturerPartNumber"
						value="%{parts[#sowPartDTO.index].manufacturerPartNumber}"
						theme="simple" cssStyle="width: 170px;"
						cssClass="shadowBox grayText" />
				</tags:fieldError>
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
			<td>
				<tags:fieldError
					id="Part ${sowPartDTO.count} --> Vendor Part Number"
					oldClass="paddingBtm">
					<s:textfield size="50" maxlength="100"
						name="parts[%{#sowPartDTO.index}].vendorPartNumber"
						value="%{parts[#sowPartDTO.index].vendorPartNumber}"
						theme="simple" cssStyle="width: 170px;"
						cssClass="shadowBox grayText" />
				</tags:fieldError>
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> Alt Part Ref1"
					oldClass="paddingBtm">
					<s:textfield size="50" maxlength="100"
						name="parts[%{#sowPartDTO.index}].altPartRef1"
						value="%{parts[#sowPartDTO.index].altPartRef1}" theme="simple"
						cssStyle="width: 110px;" cssClass="shadowBox grayText" />
				</tags:fieldError>
			</td>
			<td>
				&nbsp;&nbsp;
			</td>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> Alt Part Ref2"
					oldClass="paddingBtm">
					<s:textfield size="50" maxlength="100"
						name="parts[%{#sowPartDTO.index}].altPartRef2"
						value="%{parts[#sowPartDTO.index].altPartRef2}" theme="simple"
						cssStyle="width: 110px;" cssClass="shadowBox grayText" />
				</tags:fieldError>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.std.measurement" />
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.length" />
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.width" />
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.height" />
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="wizard.label.weight" />
			</td>
		</tr>
		<tr>
			<td>
				<s:radio list="measurement"
						name="parts[%{#sowPartDTO.index}].standard"
						id="parts[%{#sowPartDTO.index}].standard"
						cssClass="measureUnitRadio"
						value="%{parts[#sowPartDTO.index].standard}" theme="simple" />
			</td>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> Length">
					<s:textfield name="parts[%{#sowPartDTO.index}].length" size="10"
						maxlength="10" value="%{parts[#sowPartDTO.index].length}"
						id="parts[%{#sowPartDTO.index}].length" theme="simple"
						cssStyle="width: 75px;" cssClass="shadowBox grayText" />
				</tags:fieldError>
			</td>
			<td>
				&nbsp;&nbsp;x&nbsp;&nbsp;
			</td>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> Width">
					<s:textfield name="parts[%{#sowPartDTO.index}].width" size="10"
						maxlength="10" value="%{parts[#sowPartDTO.index].width}"
						id="parts[%{#sowPartDTO.index}].width" theme="simple"
						cssStyle="width: 75px;" cssClass="shadowBox grayText" />
				</tags:fieldError>
			</td>
			<td>
				&nbsp;&nbsp;x&nbsp;&nbsp;
			</td>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> Height">
					<s:textfield name="parts[%{#sowPartDTO.index}].height" size="10"
						maxlength="10" value="%{parts[#sowPartDTO.index].height}"
						id="parts[%{#sowPartDTO.index}].height" theme="simple"
						cssStyle="width: 75px;" cssClass="shadowBox grayText" />
				</tags:fieldError>
			</td>
			<td>
				&nbsp;&nbsp;|&nbsp;&nbsp;
			</td>
			<td>
				<tags:fieldError id="Part ${sowPartDTO.count} --> Weight">
					<s:textfield name="parts[%{#sowPartDTO.index}].weight" size="10"
						maxlength="10" value="%{parts[#sowPartDTO.index].weight}"
						id="parts[%{#sowPartDTO.index}].weight" theme="simple"
						cssStyle="width: 75px;" cssClass="shadowBox grayText" />
				</tags:fieldError>
			</td>
		</tr>
	</table>
</div>

