<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:if test="${searchCriteriaVO.searchByType != -1 && searchCriteriaVO.searchByType != null}">
		<c:if test="${fn:length(searchResultsList) <= 0 }">
			<div id="autocloseSearchError" class="warningBox">
				<p class="warningMsg">&nbsp;&nbsp;&nbsp;No&nbsp;<span class="label"></span>&nbsp;match&nbsp;<span class="criteria"></span>. Please check the information and try again.</p>
			</div>
			<script type="text/javascript">jQuery(function($){
				$('#autocloseSearchError > .warningMsg > .label').html($.trim($('#autocloseSearchBy > option:selected').html())+'s');
				$('#autocloseSearchError > .warningMsg').find('.criteria').html("<B>\""+$('#autocloseSearchInput > input').val()+"\"</B>");
				
			});</script>
		</c:if>
		<c:if test="${fn:length(searchResultsList) == 30 }">
			<div id="autocloseSearchError" class="warningBox">
				<p class="warningMsg">&nbsp;&nbsp;&nbsp;We found more than <B>30</B> results. Please narrow your search.</p>
			</div>		
		</c:if>
		
			<c:if test="${fn:length(searchResultsList) > 10 }" ><script>$(".resultsDiv").height(310);</script></c:if>

		    <c:if test="${fn:length(searchResultsList) > 0 }" >

			     <c:if test="${searchCriteriaVO.searchByType == 1 ||  searchCriteriaVO.searchByType ==  2}">
			         <div id="provSearchResults" >
			         	<span class="bold">We found the following results. Select and add to Exclusion List.</span><br>
			         	<jsp:include page="autoCloseProvSearchResult.jsp" />
			         </div>
				 </c:if>
				  <c:if test="${searchCriteriaVO.searchByType == 3 ||  searchCriteriaVO.searchByType ==  4}">
			         <div id="firmSearchResults" >
			         	<span class="bold">We found the following results. Select and add to Exclusion List.</span><br>
			         	<jsp:include page="autoCloseFirmSearchResult.jsp" />
			         </div>	
				 </c:if>
			</c:if>

</c:if>

