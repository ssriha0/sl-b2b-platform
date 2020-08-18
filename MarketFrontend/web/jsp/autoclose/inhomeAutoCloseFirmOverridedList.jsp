<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script type="text/javascript" src="${staticContextPath}/javascript/inhomeautoclose-driver.js"></script>

<script type="text/javascript">
jQuery(document).ready(function () {
	$('#listedLink').addClass('disabled');
	$('#firmsLink').addClass('disabled');
	$('#removedLink').removeClass('disabled');
	$('#listHead').html('Provider Firm Override List');
	$('#addnMsg').html('');
});
</script>
<style>
table .overrideTable{
      border-collapse: collapse;
}
table.overrideTable {
    border: 1px solid gray;
}
table.overrideTable th{
	height: 30px;
	text-align: left;
	background: #4CBCDF; 
	color: #fff; 
	font-weight: bold; 
	font-family: verdana;	
	font-size: 10px;
	vertical-align: 5px;
}
table.overrideTable td{
	padding: 5px;
	text-align: left;
}
</style>
					<c:if test="${fn:length(autoCloseFirmOverrideList) > 0 }">
						<span class="right bold"> Showing 1 to ${fn:length(autoCloseFirmOverrideList)} of ${resultSize} </span> <br>
					</c:if>	
					<table cellspacing="0" cellpadding="0" class="overrideTable" style="margin-top: 1%; table-layout: fixed;">
					    <thead>
					      <tr>
					      	<th width="8%"><b>Remove from Override List</b></th>
							<th width="15%" style="text-align: center;"><b> Provider Firm</b></th>
							<th width="7%">&nbsp;&nbsp;&nbsp;</th>
							<th width="9%"><b>Reimbursement Rate</b></th>
							<th width="33%"><b>Comments</b></th>
							<th width="14%"><b> Date Added to Override List</b></th>
							</tr>
					    </thead>

					    <tbody>
					    
					    <c:if test="${fn:length(autoCloseFirmOverrideList) == 0 }">
					    	<tr><td colspan="5">No Firms Added to Override List</td></tr>
					    </c:if>
					    
					    <c:if test="${fn:length(autoCloseFirmOverrideList) > 0 }">
					    <c:forEach var="firm" items="${autoCloseFirmOverrideList}" varStatus="rowCounter">
					    <c:set var="count" value="${rowCounter.count}" />
				<tr class="scrollerTableRow">
					<td width="5%">
						<input class="autoCloseCheck" type="checkbox"
							value="${firm.inhomeAutoCloseRuleFirmAssocId}" onclick="enableComments(this)" /></td>
					
					<td width="15%">
						<a href="javascript:void(0);" id="firmID${firm.providerFirm.id}">
						<u>${firm.providerFirm.businessName}</u>
						</a>
					</td>
					
					<td width="7%">
						(ID# ${firm.providerFirm.id})
					</td>
					
					<td width="7%">
						${firm.reimbursementRate} %
					</td>
					
					<td width="35%" style="word-wrap:break-word;">
						<img src="${staticContextPath}/images/icons/comment.png"
							style="margin-right: 10px;" />

									<c:if test="${fn:length(firm.existingOverrideReason) > maxComSize }">
										{${fn:substring(firm.existingOverrideReason,0,maxComSize)}}
										
										<c:if test="${not empty firm.firmAssocHistory}">
								<a href="javascript:void(0);" class="viewComHisLink"><u>View
										Comments & History</u>
								</a>
								<div class="comHistLayer scroll" id="comHistLayer${count}">
												<c:forEach var="firmHistory" items="${firm.firmAssocHistory}">
													<br>Action: 
													<c:if test="${firmHistory.active}"> Added</c:if>
													<c:if test="${!firmHistory.active}"> Removed</c:if>
													<br>
													Date: ${firmHistory.modifiedDateFormatted}<br>
													Modified: ${firmHistory.modifiedBy}<br>
													Comments: ${firmHistory.overridedReason}<br>
													-----------------											
												</c:forEach>
								</div>
							</c:if>

										<a href="javascript:void(0);" class="plus">(+)</a>
										<div class="comLayer" style="padding: 5px;">
								Action: Added
								<br>
								Date: ${firm.overrideDateFormatted}
								<br>
								Modified: ${firm.modifiedBy}
								<br>
											Comments: ${firm.existingOverrideReason}
							</div>
									</c:if>
									<c:if test="${fn:length(firm.existingOverrideReason) <= maxComSize }">
										${firm.existingOverrideReason}
										<c:if test="${not empty firm.firmAssocHistory}">
								<a href="javascript:showCommentHistory(${count})" class="viewComHisLink"><u>View
										Comments & History</u>
								</a>
								<div class="comHistLayer scroll" id="comHistLayer${count}" style="display: none;">
												<c:forEach var="firmHistory" items="${firm.firmAssocHistory}">
													<br>Action: 
													<c:if test="${firmHistory.active}"> Added</c:if>
													<c:if test="${!firmHistory.active}"> Removed</c:if>
													<br>
													Date: ${firmHistory.modifiedDateFormatted}<br>
													Modified: ${firmHistory.modifiedBy}<br>
													Comments: ${firmHistory.overridedReason}<br>
													-----------------											
												</c:forEach>
								</div>
									</c:if>
						</c:if>
								</td>
					
					<td width="14%">
						${firm.overrideDateFormatted}
					</td>
				
							</tr>
				
							<tr class="reasonText" id="txt${firm.inhomeAutoCloseRuleFirmAssocId}" style="display: none;" >
								<td colspan="4"><textarea id="comment${firm.inhomeAutoCloseRuleFirmAssocId}" class="shadowBox grayText" style="width: 600px;" maxlength="750" onkeydown="CheckMaxLength(this, 749);">Enter reason for removing from the override list</textarea></td>
  						        <td style="padding-left: 273px;"> 
  						        	<input type="button" value="REMOVE FROM OVERRIDE LIST" class="button action btn20Bevel" id="removeFirm${firm.inhomeAutoCloseRuleFirmAssocId}" onclick="updateFirmList(this)"/>
  						        </td>
							</tr>
							<tr><td colspan="6" style="border-top: solid 1px gray;"></td></tr>
											
					    </c:forEach>
						<c:if test="${(fn:length(autoCloseFirmOverrideList) < 30) && (fn:length(autoCloseFirmOverrideList) == resultSize)}">
					    	<tr><td colspan="6" style="background-color: #e4e4e4;text-align: center;"><a id="viewNext" class="disabled" href="javascript:void(0);"><u>View Next 30</u></a> | <a id="viewAll" class="disabled" href="javascript:void(0);"><u>View All</u></a></td></tr>
					    </c:if>						    
					    <c:if test="${(fn:length(autoCloseFirmOverrideList) != resultSize) && (fn:length(autoCloseFirmOverrideList) >= 30)}">
					    	<tr><td colspan="6" style="background-color: #e4e4e4;text-align: center;"><a id="viewNext" href="javascript:void(0);"><u>View Next 30</u></a> | <a id="viewAll" href="javascript:void(0);"><u>View All</u></a></td></tr>
					    </c:if>						    
					    </c:if>					    
					    </tbody>
					  </table>
