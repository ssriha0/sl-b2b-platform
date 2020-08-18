<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@page import="com.newco.marketplace.vo.*,java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".goToPageNo").keypress(function(e){		
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
  			
	});
	
	$(".goTo").click(function(){
		var totalPages=$("#totalPages").val();		
	 	var pageNo = $('#goToPageNo').val();
	 	var totalRec = $('#totalRecords').val();
	 	
	 	totalRec = parseInt(totalRec);
	 	if(pageNo == null || pageNo == ""){
			 return false; 
			}
	 	
		pageNo = parseInt(pageNo);
	   	totalPages = parseInt(totalPages);
	   	if(pageNo > totalPages)
		{
			  pageNo=totalPages;
		}else if(pageNo < 1)
		{
			  pageNo=1;
		}
	   	var startIndex= ((pageNo-1) * 100)+1;
	   	var endIndex = startIndex + 99;
	   	if(endIndex>totalRec){
	   		endIndex = totalRec;
	   	}
	   	paginate(startIndex, endIndex, 100);
	});
		
});


	function paginate(startIndex, endIndex, pageSize) {
		var myForm = document.getElementById('pagingForm');
		document.getElementById('startIndex').value = startIndex;
		document.getElementById('endIndex').value = endIndex;
		document.getElementById('pageSize').value = pageSize;
		myForm.submit();
	}
	function callPaginate() {
		var pageNo = document.getElementById("goToPage").value;
		var totalRecords = document.getElementById("totalRecords").value;
		var startIndex = 1;
		var endIndex = 100;
		if (pageNo <= totalRecords) {
			if (totalRecords > 0) {
				startIndex = (pageNo * 100) - 99;
				endIndex = (pageNo * 100);
				paginate(startIndex, endIndex, 100);
			}
		}
	}
	
	function onlyNumbers(evt) {
		e = evt.which ? evt.which : evt.keyPress;
		if (e.which != 8 && e.which != 0 && (e.which<48 || e.which>57)) {
			return false;
		}
		return true;
	}
</script>
<style>
.paginationLink{cursor:pointer;}
input.button {
cursor:pointer;
} 
input.submit {
cursor:pointer;
}  
.reportPagination {
	    border-left: 0 none;
		border-right: 0 none;
		border-top: 0 none;
		font-size: 10px;
		padding: 10px;
		font-family: Verdana;
	}
</style>
<s:form id="pagingForm" theme="simple"
	action="fmReports_nextPage.action">
	<input type="hidden" name="startIndex" id="startIndex" />
	<input type="hidden" name="endIndex" id="endIndex" />
	<input type="hidden" name="pageSize" id="pageSize" />
	<input type="hidden" name="requestRandom" id="requestRandom" value="<%=request.getAttribute("requestRandom") %>" />
	<input type="hidden" name="reportType" id="reportType" value="<%=request.getAttribute("reportType") %>" />
</s:form>
<s:set name="so_prov" value="<%=OrderConstants.PROVIDER_SO_REPORT %>" />
<s:set name="rev_prov" value="<%=OrderConstants.PROVIDER_REV_REPORT %>" />
<s:set name="so_byr" value="<%=OrderConstants.BUYER_SO_REPORT %>" />
<s:set name="tax_byr" value="<%=OrderConstants.BUYER_TAXID_REPORT %>" />
<s:set name="admn_pay" value="<%=OrderConstants.ADMIN_PAYMENT_REPORT %>" />
<s:set name="misc_admn" value="<%=OrderConstants.MISC_REPORT %>" />

<s:set name="reportName" value="${fmReportTabDTO.reportName}"/>
<s:set name="paginationVO" value="paginationVO" />


<%	PaginationVO paginationVO = (PaginationVO) request.getAttribute("reportPagination");
	if (paginationVO == null) {
		paginationVO = (PaginationVO) session
				.getAttribute("reportPagination");
	}	
	Integer totalRecs = (Integer)request.getAttribute("totalRecords");
	int totalRecords = 0;
	if(totalRecs != null){
		totalRecords = totalRecs.intValue();
	}
	String reportType = (String)request.getAttribute("reportType");
	if(reportType != null && !"".equals(reportType)){
		request.setAttribute("reportType", reportType);
	}
	int startRec = 0;
	int endRec = 0;
	ArrayList resultSetList = null;
	if (paginationVO != null
			&& paginationVO.getCurrentResultSetBucket() != null
			&& paginationVO.getCurrentResultSetBucket().size() > 0) {
		ArrayList currentResultSetBucket = paginationVO
				.getCurrentResultSetBucket();

		ArrayList paginetVO = paginationVO.getPageSizeBuckets();
		request.setAttribute("bucketSize", new Integer(
				currentResultSetBucket.size()));
		int myPageSize = paginationVO.getPageSize();
		int totalPaginets = paginationVO.getTotalPaginets();
		PaginetVO initPaginet = paginationVO.getCurrentPaginet();
		startRec = initPaginet.getStartIndex();
		endRec = initPaginet.getEndIndex();
		//request.setAttribute("totalRecords", new Integer(totalRecords));
		PaginetVO lastPaginet = paginationVO.getLastPaginet();
		if (currentResultSetBucket != null) {
			if (totalPaginets > 0) {
				%>
				<c:set var="pageLength" value="<%=request.getAttribute("bucketSize")%>" />
				
				<input type="hidden" name="totalRecords" id="totalRecords"
												value="<%=totalRecords %>" />
				<input type="hidden" name="totalPages" id="totalPages" value="<%=totalPaginets %>">
				<table width=100%>
					<c:if test="${pageLength > 0 }">
						<tr>
							<td align="right" class="reportPagination">
								<div class="GADGET_PAGE">
				<%
				if (paginationVO != null && totalPaginets > 1) {
					PaginetVO nextPaginet = paginationVO.getNextPaginet();
					PaginetVO previousPaginet = paginationVO.getPreviousPaginet();
					if (paginationVO.isPreviousIndicator() == true && paginationVO.getPreviousPaginet() != null) {
				%>
								<a href="javascript:paginate(1,100,100)" class="paginationLink" style="text-decoration: underline; color: #00A0D2;">&lt;&lt;</a> 
								<a href="javascript:paginate(<%=previousPaginet.getStartIndex()%>,<%=previousPaginet.getEndIndex()%>,<%=myPageSize%>)"
									class="paginationLink" style="color: #00A0D2;text-decoration: none;">&lt; </a>
				<%
					}
				%>
										Page
				<%
					if (currentResultSetBucket != null) {
						for (int i = 0; i < currentResultSetBucket.size(); i++) {
							PaginetVO paginet = (PaginetVO) currentResultSetBucket.get(i);
							String displayName = paginet.getDisplayName();
							int startIndex = paginet.getStartIndex();
							int endIndex = paginet.getEndIndex();
							if (paginet.isCurrentPaginet() == true) {
								startRec = startIndex;
								endRec = endIndex;
				%>
					<b>			<%=displayName%> </b>
				<%
							} 		
						}
						out.println("of");
					}
					if (lastPaginet != null) {
				%>
					<b>	<%=lastPaginet.getDisplayName()%> </b>
				<%
					}
					if (paginationVO.isNextIndicator() == true && paginationVO.getNextPaginet() != null) {
				%>
					<a href="javascript:paginate(<%=nextPaginet.getStartIndex()%>,<%=nextPaginet.getEndIndex()%>,<%=myPageSize%>)"
										class="paginationLink" style="color: #00A0D2;text-decoration: none;"> &gt;</a> 
					<a href="javascript:paginate(<%=lastPaginet.getStartIndex()%>,<%=lastPaginet.getEndIndex()%>,<%=myPageSize%>)"
										class="paginationLink" style="text-decoration: underline; color: #00A0D2;"> &gt;&gt;</a>
				<%
					}
					
					if(totalPaginets > 1){
				%>	
					To Page &nbsp;<input type="text" id="goToPageNo"	class="goToPageNo" style="width: 40px" />
					<input type="submit" id="goTo" name="goTo" class="goTo" value=" GO "  style="cursor:pointer" />	
								
				<%
					}
				}						
				%>
				</div>
			</td>
		</tr>
		<tr>
			<td align="right" class="reportPagination">Showing <b> <%=startRec %> - <%=endRec %> </b>of <b><%=totalRecords %></b> total records
		</tr>
	</c:if>
<%
		}
 	}
}
	%>
</table>