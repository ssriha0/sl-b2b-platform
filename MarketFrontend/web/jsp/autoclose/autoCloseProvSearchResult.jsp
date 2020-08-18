<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="tableWrapper resultsDiv" style="overflow-y: auto;margin-top: 10px;margin-bottom: 10px;width: auto;">
		<table cellspacing="0" cellpadding="0" border="0" id="spnMMSearchResultsTable">
		<tbody>
		<c:forEach items="${searchResultsList}" var="serviceProvider">
			<c:if test="${serviceProvider.autoCloseExcluded}">
				<tr class="warningBox">
					<td colspan="4" style="border-top: #fc6 1px solid;border-left:  #fc6 1px solid;border-right:  #fc6 1px solid;">
					<div id="autocloseSearchError" style="padding-bottom: 15px;">
						<c:choose>
						<c:when test="${searchCriteriaVO.searchByType ==  2}">
							<p class="warningMsg">&nbsp;&nbsp;&nbsp;<B>"${serviceProvider.id}"</B> is already on the Provider Exclusion List.</p>
						</c:when>
						<c:otherwise>
							<p class="warningMsg">&nbsp;&nbsp;&nbsp;<B>"<c:out value="${serviceProvider.contact.firstName}"/> <c:out value="${serviceProvider.contact.lastName}"/>"</B> is already on the Provider Exclusion List.</p>
						</c:otherwise>
						</c:choose>
					</div>						
					</td>
				</tr>
				<tr bgcolor="#ffffcc" style="margin-bottom: 5px;">
					<td width="5%" style="border-left: #fc6 1px solid;border-bottom: #fc6 1px solid;"></td>
					<td width="25%" style="border-bottom: #fc6 1px solid;">
						<a href="javascript:openProvider(${serviceProvider.id},${serviceProvider.providerFirm.id});" id="provID${serviceProvider.id}-${serviceProvider.providerFirm.id}"><u><c:out value="${serviceProvider.contact.firstName}"/> <c:out value="${serviceProvider.contact.lastName}"/></u></a>
					</td>
					<td width="15%" style="border-bottom: #fc6 1px solid;">		
							(ID #${serviceProvider.id})
						<!-- <br/>Company&nbsp;ID&nbsp;#${serviceProvider.providerFirm.id}  -->
					</td>
					<td width="40%" style="border-bottom: #fc6 1px solid;border-right: #fc6 1px solid;">
						<!-- ${serviceProvider.providerFirm.serviceLiveWorkFlowState.wfState} / --> ServiceLive Status: ${serviceProvider.serviceLiveWorkFlowId.wfState}
					</td>
				</tr>				
			</c:if>
			<c:if test="${!serviceProvider.autoCloseExcluded}">
				<tr id="chkresProv${serviceProvider.id}">
					<td width="5%"><input class="autoCloseCheck" name="provChk" type="checkbox" value="resProv${serviceProvider.id}" onclick="autoCloseCheck(this);"/></td>
					<td width="25%">
						<a href="javascript:openProvider(${serviceProvider.id},${serviceProvider.providerFirm.id});" id="provID${serviceProvider.id}-${serviceProvider.providerFirm.id}"><u><c:out value="${serviceProvider.contact.firstName}"/> <c:out value="${serviceProvider.contact.lastName}"/></u></a>
					</td>
					<td width="15%">		
							(ID #${serviceProvider.id})
						<!-- <br/>Company&nbsp;ID&nbsp;#${serviceProvider.providerFirm.id}  -->
					</td>
					<td width="40%">
						<!-- ${serviceProvider.providerFirm.serviceLiveWorkFlowState.wfState} / --> ServiceLive Status: ${serviceProvider.serviceLiveWorkFlowId.wfState}
					</td>
				</tr>
				<tr class="reasonText" id="txtresProv${serviceProvider.id}" style="display: none;" >
								<td colspan="4"><textarea name = "commentresProv" id="commentresProv${serviceProvider.id}"" class="shadowBox grayText" style="width: 600px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);" >Enter reason for excluding from auto close.</textarea></td>
				</tr>			
			</c:if>

		</c:forEach>
		</tbody>
	</table>
</div>
		<!-- <input type="image" id="resaddProv" src="${staticContextPath}/images/common/spacer.gif" width="35" height="20" style="background-image:url(${staticContextPath}/images/btn/add.gif);"class="btn20Bevel" />  -->
		<table>
			<tr>
				<td width="15%"><input type="button" value="ADD SELECTED" class="button action btn20Bevel" id="resaddProv" onclick="updateExclusionList(this);"/></td>
				<td style="padding-top: 15px;"> To Auto Close Exclusion List </td>
			</tr>
		</table> 