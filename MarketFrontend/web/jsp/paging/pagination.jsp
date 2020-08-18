<%@page import="com.newco.marketplace.vo.*, java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

   
<script type="text/javascript">
	function paginate(startIndex, endIndex, pageSize){
		document.getElementById('startIndex').value = startIndex;
		document.getElementById('endIndex').value = endIndex;
		document.getElementById('pageSize').value = pageSize;
		var myForm = document.getElementById('inactivegrid');
		myForm.submit();
		
		
	}
	</script>


	<s:form id="inactivegrid" action="<%=request.getAttribute("action") != null ? request.getAttribute("action") : "gridLoader.action"  %>">
		<input type="hidden" name="startIndex" id="startIndex"/>
		<input type="hidden" name="endIndex" id="endIndex" />
		<input type="hidden" name="pageSize" id="pageSize" />
		<input type="hidden" name="tab" id="tab" value="${tab}" />
	</s:form>
	<c:set var="myLength" value="${fn:length(paginationVO.currentResultSetBucket)}" />
	<%
		PaginationVO paginationVO =(PaginationVO) request.getAttribute("paginationVO");
		if (paginationVO!=null) {
			ArrayList currentResultSetBucket = paginationVO.getCurrentResultSetBucket();
			int myPageSize = paginationVO.getPageSize();
			int totalPaginets = paginationVO.getTotalPaginets();
			PaginetVO lastPaginet = paginationVO.getLastPaginet();
			if (currentResultSetBucket!=null && !currentResultSetBucket.isEmpty()) {
			if (totalPaginets>1)	{
	 %>	

	<c:if test="${myLength>0}">
	<tr>
	<td colspan="5">
	<div class="GADGET_PAGE">
		<div class="FLOAT_LEFT">
			&nbsp;&nbsp;&nbsp;&nbsp;Show
			<c:forEach var="pageSizeBuckets"
				items="${paginationVO.pageSizeBuckets}" varStatus="idx">
				<c:choose>
					<c:when test="${pageSizeBuckets.currentPaginet==true}">
						<b>${pageSizeBuckets.displayName}</b>
					</c:when>
					<c:otherwise>
						<a
							href="javascript:paginate(1, ${pageSizeBuckets.endIndex}, ${pageSizeBuckets.endIndex})"
							class="lightBlue"> ${pageSizeBuckets.displayName}</a>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${idx.last}">
					</c:when>
					<c:otherwise>
						<span class="PIPES">|</span>
					</c:otherwise>
				</c:choose>

			</c:forEach>
			Results per Page
		</div>
		
		<div class="FLOAT_RIGHT">
		<%
		if (paginationVO!=null) {
		PaginetVO nextPaginet = paginationVO.getNextPaginet();
		PaginetVO previousPaginet =  paginationVO.getPreviousPaginet();
		%>
			<%
					if (paginationVO.isPreviousIndicator() == true && paginationVO.getPreviousPaginet()!=null) {
			%>
			<a href="javascript:paginate(<%=previousPaginet.getStartIndex()%>,<%=previousPaginet.getEndIndex()%>,<%=myPageSize%>)"
				class="lightBlue">&lt; Previous
			</a>
			<%}%>
			<%
					if (currentResultSetBucket!=null ){ 
					for (int i = 0; i < currentResultSetBucket.size(); i++) {
					PaginetVO paginet = (PaginetVO) currentResultSetBucket.get(i);

					String displayName = paginet.getDisplayName();
					int startIndex = paginet.getStartIndex();
					int endIndex = paginet.getEndIndex();
			%>
			
			<% if(paginet.isCurrentPaginet()==true){ %>
				<b> <%=displayName%></b> 
			<%} else {%>
			<a	href="javascript:paginate(<%=startIndex%>,<%=endIndex%>,<%=myPageSize%>)"
				class="lightBlue"> <%=displayName%> </a>
			<%} %>
			<%
			if (i != currentResultSetBucket.size() - 1) {
			%>
			<span class="PIPES">|</span>
			<%}%>
			<%}
			out.println("of"); 
			}%> 
<%
			if (lastPaginet!=null) {
			%>
			<a
				href="javascript:paginate(<%=lastPaginet.getStartIndex()%>,<%=lastPaginet.getEndIndex()%>,<%=myPageSize%>)"
				class="lightBlue"> <%=lastPaginet.getDisplayName()%></a>
			<%}%>
			<%
			if (paginationVO.isNextIndicator() == true && paginationVO.getNextPaginet()!=null) {
			%>
			<a
				href="javascript:paginate(<%=nextPaginet.getStartIndex()%>,<%=nextPaginet.getEndIndex()%>,<%=myPageSize%>)"
				class="lightBlue"> Next &gt;</a>
			<%}%>
			<% } %>
			</td>
			</tr>
		</c:if>
		<%}}}%>
