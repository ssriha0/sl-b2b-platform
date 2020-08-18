<%@page import="com.newco.marketplace.vo.*, java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>

	<form id="pagingForm" theme="simple" method="post" name="financeManagerController_execute?defaultTab=History"
			action="<%=request.getParameter("action") != null ? request.getParameter("action") : "financeManagerController_execute.action?defaultTab=History"  %>">
		<input type="hidden" name="startIndex" id="startIndex"/>
		<input type="hidden" name="endIndex" id="endIndex" />
		<input type="hidden" name="pageSize" id="pageSize" />
		<input type="hidden" name="tab" id="tab" value=<%=request.getAttribute("tab")%> />
	</form>
	
	<%
		PaginationVO paginationVO =(PaginationVO) request.getAttribute("paginationVOFMOverview");
		if (paginationVO == null){
			paginationVO =(PaginationVO) session.getAttribute("paginationVOFMOverview");
		}		
		ArrayList resultSetList = null;
		if(request.getAttribute("overviewHistoryDTO") != null) 
			resultSetList = (ArrayList)request.getAttribute("overviewHistoryDTO");
		
		List resultSetSearchList = (List) request.getAttribute("soSearchList");
		
		if (paginationVO!=null && paginationVO.getCurrentResultSetBucket() != null && paginationVO.getCurrentResultSetBucket().size() > 0) {
			ArrayList currentResultSetBucket = paginationVO.getCurrentResultSetBucket();
			ArrayList paginetVO = paginationVO.getPageSizeBuckets();
			request.setAttribute("myLength2",new Integer(currentResultSetBucket.size()));
			int myPageSize = paginationVO.getPageSize();
			int totalPaginets = paginationVO.getTotalPaginets();
			PaginetVO lastPaginet = paginationVO.getLastPaginet();
			if ( currentResultSetBucket!=null ){
			if (totalPaginets>0)	{
	 %>	
	 <c:set var="myLength" value="<%=request.getAttribute("myLength2") %>" />
	<c:if test="${myLength > 0 }">
	<tr>
	<td colspan="7">
	<div class="GADGET_PAGE">
		<div class="FLOAT_LEFT">
			&nbsp;&nbsp;&nbsp;&nbsp;
		
				<%
					for (int i = 0; i < paginetVO.size(); i++) {
					PaginetVO paginet = (PaginetVO) paginetVO.get(i);
					String displayName = paginet.getDisplayName();
					boolean isCurrentPaginet=paginet.isCurrentPaginet();
					int startIndex = paginet.getStartIndex();
					int endIndex = paginet.getEndIndex();
					if(isCurrentPaginet){
			  %>
					<b><%=displayName%></b>
				<% }else{ %>	<a	href="javascript:paginate(1, <%=endIndex%>,<%=endIndex%>)"
							class="lightBlue"> <%=displayName%></a>
					<% }if(i!=paginetVO.size()-1){%>
					<span class="PIPES">|</span>
			<%}} %>
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
