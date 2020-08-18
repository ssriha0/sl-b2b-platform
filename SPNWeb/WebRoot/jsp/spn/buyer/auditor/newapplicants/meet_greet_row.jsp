<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">
	$(document).ready(function() {
		var pfId = ${providerFirmId};
		var spnId = ${networkId};

		attach_edit_to_meetgreet_select(spnId, pfId);

	});
</script>

<c:set var="spnId" value="${networkId}" />
<s:iterator value="meetList" status="row">
<c:choose>
<c:when test="${status != null}">
	<tr id="meetRow_${row.index}">
		<td class="tc" rowspan="2">
			<c:choose>
				<c:when test="${status=='Approved'}">
					<img src="${staticContextPath}/images/common/status-green.png"
						alt="<s:property value='status'/>" />
					<br />
				</c:when>
				<c:when test="${status=='Pending Approval'}">
					<img src="${staticContextPath}/images/common/status-yellow.png"
						alt="<s:property value='status'/>" />
					<br />
				</c:when>
				<c:when test="${status=='Incomplete'}">
					<img src="${staticContextPath}/images/common/status-yellow.png"
						alt="<s:property value='status'/>" />
					<br />
				</c:when>
				<c:when test="${status=='Not Required'}">
					<img src="${staticContextPath}/images/common/status-green.png"
						alt="<s:property value='status'/>" />
					<br />
				</c:when>
				<c:when test="${status=='Need for Info'}">
					<img src="${staticContextPath}/images/common/status-yellow.png"
						alt="<s:property value='status'/>" />
					<br />
				</c:when>
				<c:otherwise>
					<img src="${staticContextPath}/images/common/status-red.png"
						alt="<s:property value='status'/>" />
					<br />
				</c:otherwise>
			</c:choose>

			<small> <s:property value="status" /> </small>
		</td>

		<td class="tl nb">
			<strong><s:property value="networkTitle" /> </strong>
		</td>


		<td class="nb">
			<small>
				<strong>
					<s:property value="name" />
				</strong>
				<s:if test="resourceID != null"> 
					#<s:property value="resourceID" />
				</s:if>
			</small>
			<br />
			<small> <fmt:formatDate value="${date}" pattern="MM-dd-yyyy" />

			</small>
		</td>
		<td class="carr" rowspan="2">
			<%-- Only Want to show 'select' button for first row --%>
			<c:if test="${row.index == 0}">
				<c:if test="${expandCriteriaVO.fromSearch == 1}">
					<input id="${spnId}_${providerFirmId}_selectbutton_meetgreet"
						type="submit" class="default meetSelectionButton hide row${row.index}"
						value="select" onclick="meetSelectionButtonClick(this,'${spnId}','${providerFirmId}')">
				</c:if>
				<c:if test="${expandCriteriaVO.fromSearch != 1}">
					<input id="<s:property value='networkId'/>" type="submit"
						class="default meetSelectionButton" value="select" onclick="meetSelectionButtonClick(this,'${spnId}','${providerFirmId}')">
				</c:if>
			</c:if>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<div class="comment">
				<strong>Comment:</strong>
				<s:property value="comments" />
			</div>
		</td>
	</tr>
</c:when>
<c:otherwise>
	<tr>
		<td colspan="4">
			No Meet & Greet required.
		</td>
	</tr>
</c:otherwise>
</c:choose>
</s:iterator>