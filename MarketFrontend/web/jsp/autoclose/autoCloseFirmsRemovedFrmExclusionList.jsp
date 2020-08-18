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
	$('#listedLink').removeClass('disabled');
	$('#firmsLink').addClass('disabled');
	$('#removedLink').addClass('disabled');
	$('#providersLink').removeClass('disabled');
	$('#listHead').html('Provider Firms Removed from Exclusion List');
	$('#addnMsg').html('(Previously excluded)');
});
</script>
					<c:if test="${fn:length(firmExclusionDTOList) > 0 }">
						<span class="right bold"> Showing 1 to ${fn:length(firmExclusionDTOList)} of ${resultSize} </span> <br>
					</c:if>	
					  <table style="margin-top: 1%;" cellpadding="0" cellspacing="0" >
					    <thead>
					      <tr class="scrollerTableHdr">
					        <td style="width: 10%;"><b> Add to Exclusion List</b></td>
					        <td colspan="2"><b> Provider Firm</b></td>
					        <td >&nbsp;&nbsp;&nbsp;&nbsp;</td>
					        <td style="padding-right: 3%;" class="right"><b> Date Removed to Exclusion List</b></td>
					      </tr>
					    </thead>
					    <tbody>
					    
					    <c:if test="${fn:length(firmExclusionDTOList) == 0 }">
					    	<tr><td colspan="5">No Firms Removed from Exclusion List</td></tr>
					    </c:if>
					    <c:if test="${fn:length(firmExclusionDTOList) > 0 }">					    					    
					    <c:forEach var="firm" items="${firmExclusionDTOList}">
						    
							<tr class="scrollerTableRow">
								<td style="padding: 15px;"><input class="autoCloseCheck"  type="checkbox" value="${firm.autoCloseRuleAssocId}" onclick="autoCloseCheck(this);"/></td>
								<td width="14%"><a href="javascript:void(0);" id="firmID${firm.providerFirm.id}"><u>${firm.providerFirm.businessName}</u></a></td>
								<td width="14%"> (ID# ${firm.providerFirm.id})</td>
								<td width="40%"><img src="${staticContextPath}/images/icons/comment.png" style="margin-right: 10px;"/>
									<c:if test="${fn:length(firm.existingReason) > maxComSize }">
										{${fn:substring(firm.existingReason,0,maxComSize)}}<a href="javascript:void(0);" class="plus">(+)</a>
									</c:if>
									<c:if test="${fn:length(firm.existingReason) <= maxComSize }">
										${firm.existingReason}
									</c:if>
									<div class="comLayer" style="padding: 5px;">
										Action: Removed<br>
										Date: ${firm.excludedDateFormatted}<br>
										Modified: ${firm.modifiedBy}<br>
										Comments: ${firm.existingReason}
									</div>
								</td>
								
								<td class="left" align="left">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${firm.excludedDateFormatted}</td>
							
							</tr>
							
							<tr class="reasonText" id="txt${firm.autoCloseRuleAssocId}" style="display: none;">
								<td colspan="4"><textarea id="comment${firm.autoCloseRuleAssocId}" class="shadowBox grayText" style="width: 600px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);" >Enter reason for adding to the exclusion list</textarea></td>
  						        <td style="float: left;"> 
  						        	<!-- <input type="image" id="addFirm${firm.autoCloseRuleAssocId}" src="${staticContextPath}/images/common/spacer.gif" width="35" height="20" style="background-image:url(${staticContextPath}/images/btn/add.gif);"class="btn20Bevel" />  -->
  						        	<input type="button" value="ADD TO EXCLUSION LIST" class="button action btn20Bevel" id="addFirm${firm.autoCloseRuleAssocId}" 
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
