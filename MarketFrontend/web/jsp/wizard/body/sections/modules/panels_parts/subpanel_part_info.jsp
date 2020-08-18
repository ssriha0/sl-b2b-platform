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
		key="wizard.parts.part.label.info" />
</div>

<table cellspacing="0">
	<tr>

		<td>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.label.order.number" />
		</td>
		<td></td>
		<td>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.label.purchase.order.number" />
		</td>
		<td></td>
		<td>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.label.part.status" />
		</td>
	</tr>
	<tr>
		<td>
			<tags:fieldError id="Part ${sowPartDTO.count} --> Order Number">
				<s:textfield name="parts[%{#sowPartDTO.index}].orderNumber"
					size="10" maxlength="100"
					value="%{parts[#sowPartDTO.index].orderNumber}" theme="simple"
					cssStyle="width: 90px;" cssClass="shadowBox grayText" />
			</tags:fieldError>
		</td>

		<td>
			&nbsp;&nbsp;
		</td>

		<td>
			<tags:fieldError id="Part ${sowPartDTO.count} --> PO Number">
				<s:textfield name="parts[%{#sowPartDTO.index}].purchaseOrderNumber"
					size="10" maxlength="100"
					value="%{parts[#sowPartDTO.index].purchaseOrderNumber}"
					theme="simple" cssStyle="width: 90px;"
					cssClass="shadowBox grayText" />
			</tags:fieldError>
		</td>
		<td>
			&nbsp;&nbsp;
		</td>
		<td>
			<select name="parts[${sowPartDTO.index}].partStatusId"
				id="parts[${sowPartDTO.index}].partStatusId" style="width: 250px;">
				<option value="0">Select One</option>
				<c:forEach var="lookupVO" items="${partStatus}">
					<c:choose>
					<c:when test="${lookupVO.id == parts[sowPartDTO.index].partStatusId}">
						<option selected="selected" value="${lookupVO.id}">
							${lookupVO.type}
						</option>
					</c:when>
					<c:otherwise>
						<option value="${lookupVO.id}">
							${lookupVO.type}
						</option>
					</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</td>
	</tr>
</table>
<table cellspacing="0">
	<tr>
		<td>
			<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.label.qty" />
			&nbsp;&nbsp;
			<font color="red">*</font>
		</td>
		<td>
			&nbsp;&nbsp;
		</td>
		<td>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.label.part.description" />
			&nbsp;&nbsp;
			<font color="red">*</font>
		</td>
	</tr>
	<tr>
		<td>
			<tags:fieldError id="Part ${sowPartDTO.count} --> Quantity"
				oldClass="paddingBtm">
				<s:textfield name="parts[%{#sowPartDTO.index}].quantity" size="2"
					maxlength="2" value="%{parts[#sowPartDTO.index].quantity}"
					theme="simple" cssStyle="width: 30px;"
					cssClass="shadowBox grayText" />
			</tags:fieldError>
		</td>
		<td>
			&nbsp;&nbsp;
		</td>
		<td>
			<tags:fieldError id="Part ${sowPartDTO.count} --> Part Description"
				oldClass="paddingBtm">
				<s:textarea name="parts[%{#sowPartDTO.index}].partDesc"
					value="%{parts[#sowPartDTO.index].partDesc}" theme="simple"
					cssStyle="width: 520px;" cssClass="shadowBox grayText"
					onkeydown="limitCharsTextarea(this, 750, 'partDesclimitinfo');"
					onkeyup="limitCharsTextarea(this, 750, 'partDesclimitinfo');" />
			</tags:fieldError>
			<div id="partDesclimitinfo">
				<span><i><b>750</b>characters remaining</i> </span>
			</div>
		</td>
	</tr>
</table>
