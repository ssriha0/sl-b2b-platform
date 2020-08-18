<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<script type="text/javascript">	
	jQuery(document).ready(function($) {
		$('#viewAllLink').unbind('click').click(function() {
			var viewAll = $('#searchByViewAll').val();
			if (viewAll == 'false')
			{
				$('#viewAllLink').html('View 30');
				$('#searchByViewAll').val('true');
			}
			else
			{
				$('#viewAllLink').html('View All');
				$('#searchByViewAll').val('false');
			}
			
			submitSearch();
		});
		
		$('.filterSelect').change(function() {
			submitSearch();
		});
	});
</script> 

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:if test="${searchCriteriaVO.searchByType != -1 && searchCriteriaVO.searchByType != null}">
 
	<c:choose>
		<c:when test="${fn:length(searchResultsList) <= 0 && searchCriteriaVO.filterCriteria.stateCode eq '-1' &&  searchCriteriaVO.filterCriteria.spnId  < 0 }">
			<div id="spnMMSearchError" class="warningBox">
				<p class="warningMsg">No&nbsp;<span class="label"></span>&nbsp;match&nbsp;<span class="criteria"></span>. Please check the information and try again.</p>
			</div>
			<script type="text/javascript">jQuery(function($){
			    $('#spnMMSearchValidationError').hide();
				$('#spnMMSearchError > .warningMsg > .label').html($.trim($('#spnMMSearchBy > option:selected').html())+'s');
				$('#spnMMSearchError > .warningMsg').find('.criteria').html($('#spnMMSearchInput > input').val());
				
			});</script>
		</c:when>
		<c:otherwise>
		 <div id="spnMMFilterContainer">
					<div id="spnMMFilters">
						<strong>Filter By</strong>
						<label for="spnMMFiltersNetwork">Network</label> 
						<s:select id="spnList" name="spnList" headerKey="-1" headerValue="Select One" list="%{spnList}" 
							listKey="spnId" theme="simple" listValue="spnName" value="%{searchCriteriaVO.filterCriteria.spnId}" cssClass="spnMMFiltersNetwork filterSelect" />
						<label for="spnMMFiltersState">State</label> 
						<s:select id="stateList" name="stateList" headerKey="-1" headerValue="Select One" list="%{stateList}" listKey="id" cssClass="spnMMFiltersState filterSelect"
												theme="simple" listValue="description" value="%{searchCriteriaVO.filterCriteria.stateCode}"/>
					</div>
					<input type="hidden" id="searchByViewAll" value="%{searchCriteriaVO.isViewAll}" />
					
					<c:choose>
						<c:when test="${fn:length(searchResultsList) < searchCriteriaVO.maxResultsDisplayNumber }" >
							<div class="right showingCount"><strong>Showing ${fn:length(searchResultsList)}</strong></div>
						</c:when>
						<c:otherwise>
							<c:choose>
							<c:when test="${searchCriteriaVO.isViewAll}">
								<div class="right viewAllButton"><a href="#" id="viewAllLink">View ${searchCriteriaVO.maxResultsDisplayNumber}</a></div>
								<div class="right showingCount"><strong>Showing ${fn:length(searchResultsList)}</strong></div>
							</c:when>
							<c:when test="${!searchCriteriaVO.isViewAll}">
								<div class="right viewAllButton"><a href="#" id="viewAllLink">View All</a></div>
								<div class="right showingCount"><strong>Showing ${searchCriteriaVO.maxResultsDisplayNumber} of ${searchCriteriaVO.totalResultsCount}</strong></div>
							</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
		</div>
		    <c:if test="${fn:length(searchResultsList) > 0 }" >
			     <c:if test="${searchCriteriaVO.searchByType == 1 ||  searchCriteriaVO.searchByType ==  2}">
			         <jsp:include page="mmServiceProSearchResult.jsp" />	
				 </c:if>
				  <c:if test="${searchCriteriaVO.searchByType == 3 ||  searchCriteriaVO.searchByType ==  4}">
			         <jsp:include page="mmProviderFirmSearchResult.jsp" />	
				 </c:if>
			</c:if>
			
		</c:otherwise>
	</c:choose>
</c:if>

