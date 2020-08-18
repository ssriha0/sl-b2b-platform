<%@page import="com.newco.marketplace.vo.*, java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.owasp.esapi.ESAPI"%>

<script type="text/javascript">
	function paginate(startIndex, endIndex, pageSize){
		<%// String actionVar=request.getParameter("action");
			// String actionNew=ESAPI.encoder().canonicalize(actionVar);
			// String actionVuln=ESAPI.encoder().encodeForHTML(actionNew);
  		%>
		var myForm = document.getElementById('etmSearch');
		myForm.startIndex.value = startIndex;
		myForm.endIndex.value = endIndex;
		myForm.pageSize.value = pageSize;
		submitSearch();
//		myForm.action='${contextPath}/providerProfileFirmInfoAction_search.action' ;
//		myForm.submit();
	}
	</script>


	<form id="pagingForm" theme="simple">
		<input type="hidden" name="startIndexx" id="startIndexx"/>
		<input type="hidden" name="endIndexx" id="endIndexx" />
		<input type="hidden" name="pageSizex" id="pageSizex" />
	</form>
	
	<%
		PaginationVO paginationVO =(PaginationVO) request.getAttribute("paginationVO");
		if (paginationVO == null){
			paginationVO =(PaginationVO) session.getAttribute("paginationVO");
		}		
//		ArrayList resultSetList = (ArrayList)request.getAttribute("soOrderList") != null ? (ArrayList)request.getAttribute("soOrderList") : (ArrayList)request.getAttribute("soProviderSearchList");
//		List resultSetSearchList = (List) request.getAttribute("soSearchList");
		
		if (paginationVO!=null && paginationVO.getCurrentResultSetBucket() != null && paginationVO.getCurrentResultSetBucket().size() > 0) {
			ArrayList currentResultSetBucket = paginationVO.getCurrentResultSetBucket();
			request.setAttribute("myLength2",new Integer(currentResultSetBucket.size()));
			int myPageSize = paginationVO.getPageSize();
			int totalPaginets = paginationVO.getTotalPaginets();
			PaginetVO lastPaginet = paginationVO.getLastPaginet();
			if (currentResultSetBucket != null){
			if (totalPaginets>0)	{
	 %>	
	 <c:set var="myLength" value="<%=request.getAttribute("myLength2") %>" />
	<c:if test="${myLength > 0 }">
	<tr>
	<td colspan="2">
	<div class="GADGET_PAGE">
		<div class="FLOAT_LEFT">
			&nbsp;&nbsp;&nbsp;&nbsp;
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
		if (paginationVO!=null && totalPaginets>1) {
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
			</div>
			</div>
			</td>
			</tr>
		</c:if>
		<%}}}%>
