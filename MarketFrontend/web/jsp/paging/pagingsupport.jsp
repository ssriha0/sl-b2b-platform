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

		document.getElementById('startIndex').value = startIndex;
		document.getElementById('endIndex').value = endIndex;
		document.getElementById('pageSize').value = pageSize;
		
		<% String actionVar=request.getParameter("action");
			 String actionNew=ESAPI.encoder().canonicalize(actionVar);
			 String actionVuln=ESAPI.encoder().encodeForHTML(actionNew);
  		   %>
		if (document.getElementById('tab') != null &&
		    document.getElementById('tab').value == 'Search') {
			document.getElementById('pagingForm').action='<%=actionVuln != null ? actionVuln : "searchGridLoader.action"%>' ;
		}
		else
		{
			//document.getElementById('pagingForm').action='<%=actionVuln != null ? actionVuln : "searchGridLoader.action"%>' ;
		}
		var myForm = document.getElementById('pagingForm');
		
		<c:choose>
					<c:when test="${requestScope.action != null && requestScope.action == 'searchGridLoader.action'}">
						parent.newco.jsutils.clearAllActionTiles();
					</c:when>
					<c:otherwise>
						
					</c:otherwise>
				</c:choose>
		myForm.submit();
		
		
	}
	</script>


	<form id="pagingForm" theme="simple"
			action="<%=request.getParameter("action") != null ? request.getParameter("action") : "gridLoader.action"  %>">
		<input type="hidden" name="startIndex" id="startIndex"/>
		<input type="hidden" name="endIndex" id="endIndex" />
		<input type="hidden" name="pageSize" id="pageSize" />
		<input type="hidden" name="resetSort" id="resetSort" value="false" />
		<input type="hidden" name="tab" id="tab" value=<%=request.getAttribute("tab")%> />
		<input type="hidden" name="soStatus" id="soStatus" value=<%=request.getAttribute("soStatus")%> /> 
		<input type="hidden" name="soSubStatus" id="soSubStatus" value=<%=request.getAttribute("soSubStatus")%> /> 
	    <input type="hidden" name="serviceProName" id="serviceProName" value=<%=request.getAttribute("serviceProName")%> /> 
        <input type="hidden" name="marketName" id="marketName" value=<%=request.getAttribute("marketName")%> /> 
        <input type="hidden" name="priceModel" id="priceModel" value="<%=request.getAttribute("priceModel")%>" />
		
		<c:if test="${not empty wfFlag}">
			<input type="hidden" name="wfFlag" id="wfFlag" value="<%=request.getAttribute("wfFlag") %>" />
		</c:if>
		<input type="hidden" name="pbFilterId" id="pbFilterId" value="${pbFilterId}" />
	</form>
	
	<%
		PaginationVO paginationVO =(PaginationVO) request.getAttribute("paginationVO");
		if (paginationVO == null){
			paginationVO =(PaginationVO) session.getAttribute("paginationVO");
		}		
		ArrayList resultSetList = (ArrayList)request.getAttribute("soOrderList") != null ? (ArrayList)request.getAttribute("soOrderList") : (ArrayList)request.getAttribute("soProviderSearchList");
		List resultSetSearchList = (List) request.getAttribute("soSearchList");
		
		if (paginationVO!=null && paginationVO.getCurrentResultSetBucket() != null && paginationVO.getCurrentResultSetBucket().size() > 0) {
			ArrayList currentResultSetBucket = paginationVO.getCurrentResultSetBucket();
			//System.out.println("OUT OUT "+currentResultSetBucket.size());
			request.setAttribute("myLength2",new Integer(currentResultSetBucket.size()));
			int myPageSize = paginationVO.getPageSize();
			int totalPaginets = paginationVO.getTotalPaginets();
			PaginetVO lastPaginet = paginationVO.getLastPaginet();
			if ( (resultSetList!=null && currentResultSetBucket!=null) || 
			     (null != resultSetSearchList && currentResultSetBucket!=null) ){
			if (totalPaginets>0)	{
	 %>	
	 <c:set var="myLength" value="<%=request.getAttribute("myLength2") %>" />
	<c:if test="${myLength > 0 }">
	<tr>
	<td colspan="7">
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
			</td>
			</tr>
		</c:if>
		<%}}}%>
