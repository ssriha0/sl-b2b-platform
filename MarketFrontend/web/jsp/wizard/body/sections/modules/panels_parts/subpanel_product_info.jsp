<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div style="width: 550px" class="nohrText">
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="wizard.parts.product.label.info" />
</div>

<table cellspacing="0">
	<tr>
		<td>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.label.manufacturer" />
		</td>
		<td>
			&nbsp;&nbsp;
		</td>
		<td>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.parts.product.label" />
		</td>
	</tr>
	<tr>
		<td>
			<tags:fieldError id="Part ${sowPartDTO.count} -->  Manufacturer"
				oldClass="paddingBtm">
				<s:textfield size="50" maxlength="100"
					name="parts[%{#sowPartDTO.index}].manufacturer"
					value="%{parts[#sowPartDTO.index].manufacturer}" theme="simple"
					cssStyle="width: 150px;" cssClass="shadowBox grayText" />
			</tags:fieldError>
		</td>
		<td>
			&nbsp;&nbsp;
		</td>
		<td>
			<tags:fieldError id="Part ${sowPartDTO.count} --> Product Line"
				oldClass="paddingBtm">
				<s:textfield size="50" maxlength="100"
					name="parts[%{#sowPartDTO.index}].productLine"
					value="%{parts[#sowPartDTO.index].productLine}" theme="simple"
					cssStyle="width: 400px;" cssClass="shadowBox grayText" />
			</tags:fieldError>
		</td>
	</tr>
</table>
<table cellspacing="0">
	<tr>
		<td>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.label.model.number" />
		</td>
		<td></td>
		<td>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.label.serial.number" />
		</td>
	</tr>
	<tr>
		<td>
			<tags:fieldError id="Part ${sowPartDTO.count} --> Model Number"
				oldClass="paddingBtm">
				<s:textfield size="50" maxlength="100"
					name="parts[%{#sowPartDTO.index}].modelNumber"
					value="%{parts[#sowPartDTO.index].modelNumber}" theme="simple"
					cssStyle="width: 150px;" cssClass="shadowBox grayText" />
			</tags:fieldError>
		</td>
		<td>
			&nbsp;&nbsp;
		</td>
		<td>
			<tags:fieldError id="Part ${sowPartDTO.count} --> Serial Number"
				oldClass="paddingBtm">
				<s:textfield size="50" maxlength="50"
					name="parts[%{#sowPartDTO.index}].serialNumber"
					value="%{parts[#sowPartDTO.index].serialNumber}" theme="simple"
					cssStyle="width: 150px;" cssClass="shadowBox grayText" />
			</tags:fieldError>
		</td>
	</tr>
</table>