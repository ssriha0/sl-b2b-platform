<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

<c:set var="eligibleProviders" value="${eligibleProviders}"></c:set>
<c:set var="countEligilbeProviders"
	value="${fn:length(eligibleProviders)}"></c:set>
<form id="rejectForm" target="_top" method="post" action="serviceOrderReject.action">
<input type="hidden" value="${vendorId}" name="vendorId" id="vendorId_rejectForm">
<input type="hidden" value="" name="soId">
<input type="hidden" value="" name="resId">
<input id="requestFrom" type="hidden" value="SOD" name="requestFrom">
<input id="reasonId" type="hidden" name="reasonId">
<input type="hidden" value="" name="groupId">
<input type="hidden" value="" name="groupInd" id="groupInd">
<input type="hidden" value="" name="resourceId" id="resourceId">
<input type="hidden" value="" name="bidInd" id="bidInd">

</form>
<div class="modalHomepage" style="height: 15px;">
	<div style="float: left;">Reject Service Order</div>
</div>
<script type="text/javascript">
function closeModal_orderManagemnt(id){
               if (navigator.userAgent.indexOf("MSIE") != -1) {
                  window.location.reload();
}
            else{
                  $("#" + id).jqmHide();
                }
}
function initRejecttionComment(){
			document.getElementById("remaining_count").style.display='';
			document.getElementById("remaining_count").innerHTML="Max Length: 225";
			removeWhiteSpaceForComment();
			document.getElementById("comment_optional").style.display='none';
}
window.onload = initRejecttionComment();
</script>

<div class="modalContent" style="padding-left: 10px;">
 <c:if test="${omApiErrors!=null && fn:length(omApiErrors) > 0}">
			<br/>
				<div id="omApiErrorDiv" class="" style="padding-bottom: 5px;">
				<div  class="errorBox">
				<ul>
				<c:forEach var="omApiError" items="${omApiErrors}">
				<c:if test="${omApiError!=null}">
				<li>
					${omApiError.msg}
				</li>
				</c:if>
				</c:forEach>
				</ul>
				</div>
				</div>
			<c:remove var="omApiErrors" scope="session" />
			</c:if>
	<div class="rejectServiceOrderFrame">
		<div class="rejectServiceOrderFrameBody">
			<div id="${sorejectId}_reject_error" class="errorBox" style="display: none;">
				<p id="${sorejectId}_reject_error_msg" class="errorMsg"></p>
			</div>
			<div id="${sorejectId}_rejectConfirmText"
				style="display: none; float: right; padding-top: 10px; padding-bottom: 5px">
				<img alt="" src="/ServiceLiveWebUtil/images/icons/incIconOn.gif" />
				Please click confirm below to permanently reject this order for the
				providers you selected.
			</div>
			
			<c:set var="pageNo" value="1" />
			An asterisk ( <span style="color: red">*</span> ) indicates a
			required field.<br /> <br /> 
			<div id="notBid1">
			<b>Step 1: Choose all providers
				rejecting this service order.<span style="color: red">*</span>
			</b>
			<div align="left" style="padding-bottom: 5px; padding-top: 5px">
				<a href="javascript:void(0);" onclick="checkAll('${sorejectId}')">Select
					All</a>&nbsp;|<a href="javascript:void(0);"
					onclick="clearAll('${sorejectId}')">Clear All</a>
			</div>

			<table>
				<tr>
					<c:forEach var="provider" items="${eligibleProviders}"
						varStatus="rowCounter">
						<c:set var="index" value="${rowCounter.count}" />
						<td style="width: 33%; border-bottom: none;">
							<div style="float: left;">
								<input type="checkbox"
									id="${sorejectId}_reject_resource_${provider.resourceId}"
									name="${sorejectId}_reject_resource" class="rejectSoCheckbox" />
								${provider.providerLastName} ${provider.providerFirstName}
								#${provider.resourceId}
								(${provider.distancefromSOLocation} Miles)&nbsp;
						</td>
						<c:if test="${index % 9 == 0}">
				</tr>
			</table>
			<c:set var="pageNo" value="${pageNo+1}" />
			<table id="${sorejectId}page${pageNo}" style="display: none;">
				<tr>
					</c:if>
					<c:if test="${index % 3 == 0}">
				</tr>
				<tr>
					</c:if>
					</c:forEach>
				</tr>
			</table>
			<input id="${sorejectId}displayedPage" type="hidden" value="1" /> <b>Showing
				<c:choose><c:when test="${countEligilbeProviders >= 9}">
					<span id="${sorejectId}rejResDispCount">9</span>
				</c:when> <c:otherwise>${countEligilbeProviders}</c:otherwise></c:choose> of
				${countEligilbeProviders}
			</b> <a href="javascript:void(0);" id="${sorejectId}_viewMore"
				onclick="viewMore('${sorejectId}')"
				<c:if test="${countEligilbeProviders<=9}">style="display: none;"</c:if>>View
				More</a> <span id="${sorejectId}divider" style="display: none;">
				| </span> <a href="javascript:void(0);" id="${sorejectId}_viewLess"
				onclick="viewLess('${sorejectId}')" style="display: none;">View
				Less</a>
			</div>
			<c:if test="${not empty so.parentGroupId}">
				<p class="error">
					<img src="${staticContextPath}/images/warning.png" />
					&nbsp;&nbsp;&nbsp;&nbsp; Note: All orders in this group would be
					rejected.
				</p>
			</c:if>
			<div id="notBid2">
			<div style="padding-top: 8px;">
				<b>Step 2: Select a reason for rejecting the service order.<span style="color: red">*</span>
				</b>
			</div>
			</div>
			<div id="bid" style="display: none;">
			<b>Select a reason for rejecting the service order.<span style="color: red">*</span>
				</b>
			</div>
			<div style="padding-top: 5px;">
				<b>Reason Code</b>
			</div>
			<div style="padding: 5px 5px 5px 0px;">
				<select onchange="return checkAndMakeCommentMendatory(this);" name="${sorejectId}_reasonCodeList"
					id="${sorejectId}_reasonCodeList">
					<option value="-1" selected="selected">-Select Code-</option>
					<c:forEach var="varReasonCode" items="${reasonCodes}">
						<option value="${varReasonCode.reasonCodeId}">
							${varReasonCode.reasonCode}</option>
					</c:forEach>
				</select>
			</div>
			
		<!-- --Starts Reject Reason Code changes for vender comment---->
			<div style="padding: 5px 5px 5px 0px;">
			<table id='reasonTable'>
               <tr>
                 <td>
			   			<b>Step 3: Comment <span id="comment_optional" style="color: red">*</span> </b> 
			   			<label id="remaining_count">Remaining Length: 225</label> 
			   </td>
			  </tr>
			  <tr>
	      	     <td>
	      	     <div>
	      	      <textarea style="width:400px; height:65px;" id="vendor_resp_comment" 
	      					onkeydown="return imposeMaxLength(event, this, 225);"
					        onchange="return imposeMaxLength(event, this, 225);"
					        onkeyup="return doOnChangeValue(event, this, 225);"
					        onkeypress="return doOnChangeValue(event, this, 225);"	
	      	        > 
	      	      </textarea></div>
	      	    </td>
			  </tr>
			</table>
			</div>
			<!-- Ends Reject Reason Code changes for vender comment-->	
			
			<input type="hidden" id="${sorejectId}_count"
				name="${sorejectId}_count" value="0" />
				
			<p style="padding-bottom: 6px;padding-top: 6px;display: none;" class="error" id="rejectGrpMsg">
				<img width="15" height="15" src="/ServiceLiveWebUtil/images/warning.png">
				&nbsp;&nbsp;&nbsp;&nbsp; Note: All orders in this group would be rejected.
			</p>
			<div style="padding: 10px 0px 10px 0px;">
				<div id="rejButton">
					<input type="button" value="REJECT"
						style="float: right; width: 100px;" class="actionButton"
						onclick="submitRejectSO('${sorejectId}')" />
				</div>
				<div id="${sorejectId}_rejRemoveButton" style="display: none">
					<input type="button" style="float: right" value="CONFIRM"
						class="actionButton" onclick="submitRejectSO('${sorejectId}')" />
				</div>
				<div>
					<a href="#" name="${sorejectId}" id="cancelLink"
						onclick="closeModal_orderManagemnt('rejectOrder');"
						style="float: left; text-decoration: underline; color: red;">Cancel</a>
				</div>
				<div style="clear: both;"></div>
			</div>
		</div>
	</div>
</div>