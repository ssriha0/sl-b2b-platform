<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>		
<div class="tableWrapper resultsDiv" style="overflow-y: auto;margin-top: 10px;margin-bottom: 10px;width: auto;">
	<table id="spnMMSearchResultsTable" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<c:forEach items="${searchResultsList}" var="providerFirm">
				<c:if test="${providerFirm.autoCloseExcluded}">
					<tr class="warningBox" >
						<td colspan="4" style="border-top: #fc6 1px solid;border-left:  #fc6 1px solid;border-right:  #fc6 1px solid;">
							<div id="autocloseSearchError" >
								<c:choose>
								<c:when test="${searchCriteriaVO.searchByType ==  4}">
									<p class="warningMsg">&nbsp;&nbsp;&nbsp;<B>"${providerFirm.id}"</B> is already on the Provider Firm Exclusion List.</p>
								</c:when>
								<c:otherwise>
									<p class="warningMsg">&nbsp;&nbsp;&nbsp;<B>"${providerFirm.businessName}"</B> is already on the Provider Firm Exclusion List.</p>
								</c:otherwise>	
								</c:choose>							
							</div>						
						</td>
					</tr>
					<tr bgcolor="#ffffcc" style="margin-bottom: 5px;">
						<td width="5%" style="border-left: #fc6 1px solid;border-bottom: #fc6 1px solid;"></td>
						<td width="25%" style="border-bottom: #fc6 1px solid;">
							<a href="javascript:openFirm(${providerFirm.id});" id="firmID${providerFirm.id}" ><u>${providerFirm.businessName}</u></a>
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
					<td width="5%"><input class="autoCloseCheck" name="firmChk" type="checkbox" value="resFirm${providerFirm.id}" onclick="autoCloseCheck(this);"/></td>
					<td width="25%">
						<a href="javascript:openFirm(${providerFirm.id});" id="firmID${providerFirm.id}" ><u>${providerFirm.businessName}</u></a>
					</td>
					<td width="15%">	
						(ID #${providerFirm.id})
					</td>
					<td width="40%">
						ServiceLive Status: ${providerFirm.serviceLiveWorkFlowState.wfState}
					</td>
				</tr>
				<tr class="reasonText" id="txtresFirm${providerFirm.id}" style="display: none;" >
								<td colspan="4"><textarea name = "commentresFirm" id="commentresFirm${providerFirm.id}" class="shadowBox grayText" style="width: 600px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);" >Enter reason for excluding from auto close.</textarea>
								</td>
				</tr>				
				</c:if>				

			</c:forEach>
		</tbody>
	</table>
</div>
		<!-- <input type="image" id="resaddFirm" src="${staticContextPath}/images/common/spacer.gif" width="35" height="20" style="background-image:url(${staticContextPath}/images/btn/add.gif);" class="btn20Bevel" />  -->
		<table><tr><td width="15%"><input type="button" value="ADD SELECTED" class="button action btn20Bevel" id="resaddFirm" onclick="updateExclusionList(this);"/></td><td style="padding-top: 15px;"> To Auto Close Exclusion List </td></tr></table>
