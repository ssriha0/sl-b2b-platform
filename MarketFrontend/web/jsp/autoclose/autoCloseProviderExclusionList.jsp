<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script type="text/javascript"
			src="${staticContextPath}/javascript/inhomeautoclose-driver.js"></script>
<script type="text/javascript">
jQuery(document).ready(function () {
	$('#listedLink').addClass('disabled');
	$('#firmsLink').removeClass('disabled');
	$('#removedLink').removeClass('disabled');
	$('#providersLink').addClass('disabled');
	$('#listHead').html('Provider Exclusion List');
	$('#addnMsg').html('');
});
</script>			
					<c:if test="${fn:length(firmExclusionDTOList) > 0 }">
						<span class="right bold"> Showing 1 to ${fn:length(firmExclusionDTOList)} of ${resultSize} </span> <br>
					</c:if>	
					  <table style="margin-top: 1%;" cellpadding="0" cellspacing="0" >
					    <thead>
					      <tr class="scrollerTableHdr" >
					        <td style="width: 10%; padding: 5px"><b> Remove from Exclusion List</b></td>
					        <td colspan="2"><b> Providers</b></td>
					        <td >&nbsp;&nbsp;&nbsp;&nbsp;</td>
					        <td style="padding-right: 3%;" class="right"><b> Date Added to Exclusion List</b></td>
					      </tr>
					    </thead>
					    
					    <tbody>
					    <c:if test="${fn:length(firmExclusionDTOList) == 0 }">
					    	<tr><td colspan="5">No Providers Added to Exclusion List</td></tr>
					    </c:if>
					    
						<c:if test="${fn:length(firmExclusionDTOList) > 0 }">					    					    
					    <c:forEach var="firm" items="${firmExclusionDTOList}" varStatus="rowCounter">
						<c:set var="count" value="${rowCounter.count}" />    
							<tr class="scrollerTableRow">
								
								<td style="padding: 15px;"><input class="autoCloseCheck" type="checkbox" 
								value="${firm.autoCloseRuleAssocId}" onclick="autoCloseCheck(this);"/></td>
								
								<td width="14%">
								<a href="javascript:openProvider(${firm.serviceProvider.id},${firm.serviceProvider.providerFirm.id});" id="provID${firm.serviceProvider.id}-${firm.serviceProvider.providerFirm.id}">
								<u>${firm.serviceProvider.contact.firstName} ${firm.serviceProvider.contact.lastName}</u>
								</a>
								</td>
								
								<td width="14%"> (ID # ${firm.serviceProvider.id})</td>
								
						<td width="40%">
						<img src="${staticContextPath}/images/icons/comment.png"
							style="margin-right: 10px;" />
						
									<c:if test="${fn:length(firm.existingReason) > maxComSize }">
										{${fn:substring(firm.existingReason,0,maxComSize)}}
										
						<c:if test="${not empty firm.providerAssocHistory}">		
								<a href="javascript:showCommentHistory(${count})" class="viewComHisLink" >
								<u>View Comments & History</u>
								</a>
											<div class="comHistLayer scroll"  id="comHistLayer${count}" style="display: none;">
												<c:forEach var="providerAssocHistory" items="${firm.providerAssocHistory}">
													<br>Action: 
													<c:if test="${providerAssocHistory.active}"> Removed</c:if>
													<c:if test="${!providerAssocHistory.active}"> Added</c:if>
													<br>
													Date: ${providerAssocHistory.modifiedDateFormatted}<br>
													Modified: ${providerAssocHistory.modifiedBy}<br>
													Comments: ${providerAssocHistory.autoCloseRuleProvExcludeReason}<br>
													-----------------											
												</c:forEach>
											</div>											
										</c:if>										
										<a href="javascript:void(0);" class="plus">(+)</a>
										<div class="comLayer" style="padding: 5px;">
											Action: Added<br>
											Date: ${firm.excludedDateFormatted}<br>
											Modified: ${firm.modifiedBy}<br>
											Comments: ${firm.existingReason}
										</div>										
									</c:if>
									<c:if test="${fn:length(firm.existingReason) <= maxComSize }">
										${firm.existingReason}
										<c:if test="${not empty firm.providerAssocHistory}">
											<a href="javascript:showCommentHistory(${count})" class="viewComHisLink"><u>View Comments & History</u></a>
											<div class="comHistLayer scroll"  id="comHistLayer${count}" style="display: none;">
												<c:forEach var="providerAssocHistory" items="${firm.providerAssocHistory}">
													<br>Action: 
													<c:if test="${providerAssocHistory.active}"> Removed</c:if>
													<c:if test="${!providerAssocHistory.active}"> Added</c:if>
													<br>
													Date: ${providerAssocHistory.modifiedDateFormatted}<br>
													Modified: ${providerAssocHistory.modifiedBy}<br>
													Comments: ${providerAssocHistory.autoCloseRuleProvExcludeReason}<br>
													-----------------											
												</c:forEach>
											</div>											
										</c:if>										
									</c:if>
								</td>
								
								<td class="left" align="left" > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${firm.excludedDateFormatted}</td>
							
							</tr>
							
							<tr class="reasonText" id="txt${firm.autoCloseRuleAssocId}" style="display: none;">
								<td colspan="4"><textarea id="comment${firm.autoCloseRuleAssocId}" class="shadowBox grayText" style="width: 600px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);" >Enter reason for removing from the exclusion list</textarea></td>
  						        <td style="float: left;"> 
  						        	<!-- <input type="image" id="removeProv${firm.autoCloseRuleAssocId}" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/remove.gif);"class="btn20Bevel" />  -->
  						        	<input type="button" value="REMOVE FROM EXCLUSION LIST" class="button action btn20Bevel" id="removeProv${firm.autoCloseRuleAssocId}" 
  						        		   onclick="updateExclusionList(this);"/>
  						        </td>								
							</tr>
							<tr><td colspan="5" style="border-top: solid 1px gray;"></td></tr>
												
					    </c:forEach>
						<c:if test="${(fn:length(firmExclusionDTOList) < 30) && (fn:length(firmExclusionDTOList) == resultSize)}">
					    	<tr><td colspan="5" style="background-color: #e4e4e4;text-align: center;"><a id="viewNext" class="disabled" href="javascript:void(0);"><u>View Next 30</u></a> | <a id="viewAll" class="disabled" href="javascript:void(0);"><u>View All</u></a></td></tr>
					    </c:if>						    
					    <c:if test="${(fn:length(firmExclusionDTOList) != resultSize) && (fn:length(firmExclusionDTOList) >= 30)}">
					    	<tr><td colspan="5" style="background-color: #e4e4e4;text-align: center;"><a id="viewNext" href="javascript:void(0);"><u>View Next 30</u></a> | <a id="viewAll" href="javascript:void(0);"><u>View All</u></a></td></tr>
					    </c:if>
					    </c:if>
					    </tbody>
					  </table>