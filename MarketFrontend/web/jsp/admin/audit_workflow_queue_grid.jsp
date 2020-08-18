<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
function open_queue(qname)
{
	if (document.openProvURL != null)
	{
		document.openProvURL.close();
	}

   	var url = "powerAuditorWorkflowControllerAction_.action?PowerAuditorQName=" + qname;
	newwindow=window.open(url,'_openQueue','resizable=yes,scrollbars=yes,status=no,height=1050,width=1680');
	if (window.focus) {newwindow.focus()}
	document.openProvURL = newwindow;
}
</script>
<div class="table-wrap">
	<table id="auditorFlow" border="0" cellpadding="0" cellspacing="0" width="100%">
	<thead>
	<tr>
		<th class="col1 first odd">Queue Name</th>
		<th class="col2 even">Average Age</th>
		<th class="col3 odd">Queue Size</th>
		<th class="col4 even last">Actions</th>
	</tr>
	</thead>


	<c:set var="tmCred" value="Team Member Credential" />
	<c:set var="tmCredDisp" value="Service Provider Credential" />
	<c:set var="cCred" value="Company Credential" />
	<c:set var="cCredDisp" value="Provider Firm Credential" />
	<c:set var="cbLic" value="Company Business License" />
	<c:set var="cIns" value="Company Insurance" />
	<c:set var="ppPic" value="Provider Profile Picture" />
	<c:set var="tmbc" value="Team Member Background Check" />
	<c:set var="cPro" value="Company Profile" />
	<c:set var="tMem" value="Team Member" />
	
	<c:set var="tExc" value="10 Day Exception" />
	<c:set var="end" value="Endorsement" />
	<c:set var="cNo" value="Cancellation Notice" />
	
	<c:set var="iter" value="1" />
	<input type="hidden" value="${firmError}" id="firmError" name="firmError">
	<c:choose><c:when test="${not empty powerAuditorSearchResult}">
	<c:forEach items="${powerAuditorSearchResult}" var="searchResult">

		<c:choose>
		<c:when test="${iter == 1}">
			<tr>
			<c:set var="iter" value="0" />
		</c:when>
		<c:when test="${iter == 0}">
			<tr class="alt">
			<c:set var="iter" value="1" />
		</c:when>
		</c:choose>

		<td class="col1 first odd"><strong>
			<c:choose>
				<c:when test="${searchResult.workflowQueueName == tmCred}">
					${tmCredDisp }
				</c:when>
				<c:when test="${searchResult.workflowQueueName == cCred}">
					${cCredDisp }
				</c:when>
				<c:when test="${searchResult.workflowQueueName == tExc}">
					${tExc }
				</c:when>
				<c:when test="${searchResult.workflowQueueName == end}">
					${end }
				</c:when>
				<c:when test="${searchResult.workflowQueueName == cNo}">
					${cNo }
				</c:when>
			</c:choose>
		</strong></td>
		<td class="col2 even">
		<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value = "${searchResult.averageAge}" /> days</td>
		<td class="col3 odd">${searchResult.auditableItems}</td>
		<td class="col4 even last">
			<c:if test="${searchResult.auditableItems > 0}">
			<a href="#" onClick="open_queue('${searchResult.workflowQueueName}');">
			<c:choose>
				<c:when test="${searchResult.workflowQueueName == tmCred || searchResult.workflowQueueName == cCred || searchResult.workflowQueueName == tExc 
				|| searchResult.workflowQueueName == end ||searchResult.workflowQueueName == cNo}">
					Audit Credentials
				</c:when>
				<c:when test="${searchResult.workflowQueueName == cbLic}">
					Audit License
				</c:when>
				<c:when test="${searchResult.workflowQueueName == cIns}">
					Audit Policies
				</c:when>
				<c:when test="${searchResult.workflowQueueName == ppPic}">
					Audit Picture
				</c:when>
				<c:when test="${searchResult.workflowQueueName == tmbc}">
					Audit Background Check
				</c:when>
				<c:when test="${searchResult.workflowQueueName == cPro}">
					Audit Profile
				</c:when>
				<c:when test="${searchResult.workflowQueueName == tMem}">
					Audit Team Member
				</c:when>
			</c:choose>
			</a>
			</c:if>
		</td>
	</tr>
	</c:forEach>
	</c:when>
	<c:otherwise>
		<tr><td colspan=4>
		<div style="margin-top: 20px; margin-bottom: 20px; text-align: center;">There is no queue data for the selected filter criteria</div>
		</td></tr>
	</c:otherwise>
	</c:choose>
	</table>
</div>