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

<c:if test="${fn:length(searchResultsList) == 1 }" >

 <c:if test="${searchCriteriaVO.searchByType == 1 ||  searchCriteriaVO.searchByType ==  2}">
			         <div id="firmSearchResults" >
			         	<span class="bold">We found the following results. Select the firm(s) and reimbursement rate to add to Overridden List.</span><br>
<div class="tableWrapper resultsDiv" style="overflow-y: auto;margin-top: 10px;margin-bottom: 10px;width: auto;">
	<table id="spnMMSearchResultsTable" cellspacing="0" cellpadding="0" border="0" border-collapse="collapse">
		<tbody>
			<c:forEach items="${searchResultsList}" var="providerFirm">
				<c:if test="${providerFirm.autoCloseExcluded}">
					<tr class="warningBox" >
						<td colspan="4" style="border-top: #fc6 1px solid;border-left:  #fc6 1px solid;border-right:  #fc6 1px solid;">
							<div id="autocloseSearchError" >
								<c:choose>
								<c:when test="${searchCriteriaVO.searchByType ==  1}">
									<p class="warningMsg">&nbsp;&nbsp;&nbsp;<B>"${providerFirm.id}"</B> is already on the Provider Firm Overridden List.</p>
								</c:when>
								<c:otherwise>
									<p class="warningMsg">&nbsp;&nbsp;&nbsp;<B>"${providerFirm.businessName}"</B> is already on the Provider Firm Overridden List.</p>
								</c:otherwise>	
								</c:choose>							
							</div>						
						</td>
					</tr>
					<tr bgcolor="#ffffcc" style="margin-bottom: 5px;">
						<td width="5%" style="border-left: #fc6 1px solid;border-bottom: #fc6 1px solid;"></td>
						<td width="25%" style="border-bottom: #fc6 1px solid;">
							<a href="javascript:void(0);" id="firmID${providerFirm.id}" ><u>${providerFirm.businessName}</u></a>
						</td >
						<td width="15%" style="border-bottom: #fc6 1px solid;">	
							(ID #${providerFirm.id})
						</td>
						<td width="40%" style="border-bottom: #fc6 1px solid;border-right: #fc6 1px solid;">
							ServiceLive Status: ${providerFirm.serviceLiveWorkFlowState.wfState}
						</td>
					</tr>					
				</c:if>
				<c:if test="${!providerFirm.autoCloseExcluded}">
				
				<tr id="chkresFirm${providerFirm.id}">
					<td width="5%"><input class="autoCloseCheck" name="firmChk" type="checkbox" value="resFirm${providerFirm.id}" /></td>
					<td width="25%">
						<a href="javascript:void(0);" id="firmID${providerFirm.id}" ><u>${providerFirm.businessName}</u></a>
					</td>
					<td width="15%">	
						(ID #${providerFirm.id})
					</td>
					<td width="40%">
						ServiceLive Status: ${providerFirm.serviceLiveWorkFlowState.wfState}
					</td>
				</tr>
					
				<table>
				<tr id="selectresFirm${providerFirm.id}" style="display: block;">
					<c:if test="${not empty reimbursementRateList}">
						<td width="42%">Override the reimbursement rate from ${defaultReimursementRate} % to<select id="reimbursementRate${providerFirm.id}" name="reimbursementRate" style="width:112px;">
						<option value="-1">-Select One-</option>
							<c:forEach var="items" items="${reimbursementRateList}">
								<option value="${items}">${items}</option>
							</c:forEach>
					</select> for the above selected firms.
					</td>
				</c:if>
				</tr>
				<tr class="reasonText" id="txtresFirm1${providerFirm.id}" style="display: block;" >
								<td colspan="4"><textarea name = "commentresFirm" id="commentresFirm${providerFirm.id}" class="shadowBox grayText" style="width: 600px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);" >Enter reason for overriding the reimbursement rate.</textarea>
								</td>
				</tr>
			</table>
			<div><input type="button" class="button action btn20Bevel" value="ADD SELECTED" id="resaddFirm" onclick="updateFirmList(this)" /><span style="padding-top: 15px;"> To Reimbursement Override List </span></div>			
			</c:if>				
			</c:forEach>
		</tbody>
	</table>
</div>
</div>	
</c:if>
</c:if>

<c:if test="${fn:length(searchResultsList) > 1 }" >

<c:if test="${searchCriteriaVO.searchByType == 1 ||  searchCriteriaVO.searchByType ==  2}">
			         <div id="firmSearchResults" >
			         	<span class="bold">We found the following results. Select the firm(s) and reimbursement rate to add to Overridden List.</span><br>
<div class="tableWrapper resultsDiv" style="overflow-y: auto;margin-top: 10px;margin-bottom: 10px;width: auto;">
	<table id="spnMMSearchResultsTable" cellspacing="0" cellpadding="0" border="0" border-collapse="collapse">
		<tbody>
			<c:forEach items="${searchResultsList}" var="providerFirm">
				<c:if test="${providerFirm.autoCloseExcluded}">
					<tr class="warningBox" >
						<td colspan="4" style="border-top: #fc6 1px solid;border-left:  #fc6 1px solid;border-right:  #fc6 1px solid;">
							<div id="autocloseSearchError" >
								<c:choose>
								<c:when test="${searchCriteriaVO.searchByType ==  1}">
									<p class="warningMsg">&nbsp;&nbsp;&nbsp;<B>"${providerFirm.id}"</B> is already on the Provider Firm Overridden List.</p>
								</c:when>
								<c:otherwise>
									<p class="warningMsg">&nbsp;&nbsp;&nbsp;<B>"${providerFirm.businessName}"</B> is already on the Provider Firm Overridden List.</p>
								</c:otherwise>	
								</c:choose>							
							</div>						
						</td>
					</tr>
					<tr bgcolor="#ffffcc" style="margin-bottom: 5px;">
						<td width="5%" style="border-left: #fc6 1px solid;border-bottom: #fc6 1px solid;"></td>
						<td width="25%" style="border-bottom: #fc6 1px solid;">
							<a href="javascript:void(0);" id="firmID${providerFirm.id}" ><u>${providerFirm.businessName}</u></a>
						</td >
						<td width="15%" style="border-bottom: #fc6 1px solid;">	
							(ID #${providerFirm.id})
						</td>
						<td width="40%" style="border-bottom: #fc6 1px solid;border-right: #fc6 1px solid;">
							ServiceLive Status: ${providerFirm.serviceLiveWorkFlowState.wfState}
						</td>
					</tr>					
				</c:if>
				<c:if test="${!providerFirm.autoCloseExcluded}">
					<tr id="chkresFirm${providerFirm.id}">
					<td width="5%"><input class="autoCloseCheck" name="firmChk" type="checkbox" value="resFirm${providerFirm.id}" /></td>
					<td width="25%">
						<a href="javascript:void(0);" id="firmID${providerFirm.id}" ><u>${providerFirm.businessName}</u></a>
					</td>
					<td width="15%">	
						(ID #${providerFirm.id})
					</td>
					<td width="40%">
						ServiceLive Status: ${providerFirm.serviceLiveWorkFlowState.wfState}
					</td>
				</tr>
			</c:if>				
			</c:forEach>
		</tbody>
	</table>
</div>
	<table>
				<tr id="selectresFirm${providerFirm.id}" style="display: block;">
					<c:if test="${not empty reimbursementRateList}">
						<td width="42%">Override the reimbursement rate from ${defaultReimursementRate} % to<select id="reimbursementRate${providerFirm.id}" name="reimbursementRate" style="width:112px;">
						<option value="-1">-Select One-</option>
							<c:forEach var="items" items="${reimbursementRateList}">
								<option value="${items}">${items}</option>
							</c:forEach>
					</select> for the above selected firms.
					</td>
				</c:if>
				</tr>
				<tr class="reasonText" id="txtresFirm${providerFirm.id}" style="display: block;" >
								<td colspan="4"><textarea name = "commentresFirm" id="commentresFirm${providerFirm.id}" class="shadowBox grayText" style="width: 600px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);" >Enter reason for overriding the reimbursement rate.</textarea>
								</td>
				</tr>
	</table>
	<div><input type="button" class="button action btn20Bevel" value="ADD SELECTED" id="resaddFirm" onclick="updateFirmList(this);" /><span style="padding-top: 15px;"> To Reimbursement Override List </span></div>			


</div>	
</c:if>
</c:if>
</c:if>

