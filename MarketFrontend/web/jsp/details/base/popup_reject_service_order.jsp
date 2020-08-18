<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>


<c:set var="groupedSO" scope="request"
	value="<%=request.getAttribute("THE_GROUP_ORDER")%>" />
<c:set var="groupedId" scope="request"
	value="<%=request.getAttribute("groupOrderId")%>" />
	
<div id="rejectSo" style="display: none">
	<div class="modalHomepage"><div  style="float:left;">Reject Service Order</div> <a href="#"  float:right>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a> </div>
	<input type="hidden" name="adminCheck" id="adminCheck" value="${isProviderAdmin}"/>
	<input type="hidden" name="dispatchCheck" id="dispatchCheck" value="${isDispatchInd}"/>
	<input type="hidden" name="resourceID" id="resourceID" value="${currentSO.resourceId}" />	
		
	<div class="modalheaderoutline">
	
	<c:if test="${groupedId!= null && groupedId!=''}">
		<c:set var="so" value="${groupedSO}"/>
		<c:set var="bidIndicator" value="NAME_PRICE"/>
	</c:if>
	<c:if test="${groupedId== null || groupedId==''}">
		<c:set var="so" value="${currentSO}"/>
		<c:set var="bidIndicator" value="${currentSO.priceModel}"/>
	</c:if>	
	<c:if test="${(isProviderAdmin== true || isDispatchInd == true) && bidIndicator != 'ZERO_PRICE_BID'}">	
		<c:set var="frameClass" value="rejectAdminServiceOrderFrame"  />
		<c:set var="frameBodyClass" value="rejectAdminServiceOrderFrameBody"  />	
	</c:if>
	<c:if test="${isProviderAdmin!= true && isDispatchInd != true}">
		<c:set var="frameClass" value="rejectServiceOrderFrame"  />	
		<c:set var="frameBodyClass" value="rejectServiceOrderFrameBody"  />	
	</c:if>
	
	<input type="hidden" name="bidInd" id="bidInd" value="${bidIndicator}" />
	<div class="${frameClass}">		
		<div class="${frameBodyClass}" style="margin-left: 5px;">
			
			
			<div style="display:none" id="reject_error" name="reject_error" class="errorBox">
				<p id="reject_error_msg" class="errorMsg">
				</p>
			</div>	
		
		<div id="rejectConfirmText" name="rejectConfirmText" class="messageText" style="display:none;" style="padding: 5px">
				<img alt="" src="/ServiceLiveWebUtil/images/icons/incIconOn.gif"> Please click confirm below to permanently reject this order for the providers you selected.
			<br>
		</div>		
		An asterisk ( <span style="color: red">*</span> ) indicates a required field.<br/>
				<c:if test="${(isProviderAdmin== true || isDispatchInd == true) && bidIndicator != 'ZERO_PRICE_BID'}">				
					<div>
					<b>Step 1: Choose all providers rejecting this service order.<span style="color: red">*</span> </b>
					</div>
					<div align="left">
						<a href='javascript:void(0);' onClick="checkAll()">Select All</a>&nbsp;| <a href='javascript:void(0);' onClick="clearAll()">Clear All</a>
					</div>
					<c:set var="pageNo" value="1"/>
					
					<table>
					<tr>
					<table id="page${pageNo}">
						<tr>
							<c:forEach var="resources" items="${so.routedResourcesForFirm}" varStatus="rowCounter">
								<c:set var="index" value="${rowCounter.count}" />				
			  					<td style="width: 250px;">
			  						<input type="checkbox" id="reject_resource_${resources.resourceId}" name="reject_resource"/>		  	
								 		${resources.providerLastName}${resources.providerFirstName} #${resources.resourceId} (${resources.distanceFromBuyer} Miles)
								</td>
								<c:choose>
								<c:when test="${index % 9 == 0}">
									</tr></table>
									<c:set var="pageNo" value="${pageNo+1}"/>
									<table id="page${pageNo}" style="display: none;"><tr>
								</c:when>
								<c:when test="${index % 3 == 0}">
									</tr><tr>
								</c:when>
								</c:choose>
							</c:forEach>
						</tr>
					</table>
					</tr>
					</table>
				<input id="displayedPage" type="hidden" value="1"/>
				<b style="padding-bottom: 5px">
				Showing <c:choose><c:when test="${fn:length(so.routedResourcesForFirm)>=9}">
							<span id="rejResDispCount">9</span>
						</c:when> 
						<c:otherwise>${fn:length(so.routedResourcesForFirm)}</c:otherwise></c:choose>
				of ${fn:length(so.routedResourcesForFirm)}</b>
				
				<a href="javascript:void(0);" id="viewMore" onclick="viewMore()" <c:if test="${index<=9}">style="display: none;"</c:if>>View More</a>
				<span id="divider" style="display: none;"> | </span>
				<a href="javascript:void(0);" id="viewLess" onclick="viewLess()" style="display: none;">View Less</a>
				</c:if>						
				<p>
					<b><c:if test="${(isProviderAdmin== true || isDispatchInd == true) && bidIndicator != 'ZERO_PRICE_BID'}">Step 2:</c:if>
					 Select a reason for rejecting the service order.<span style="color: red">*</span> </b>
					</p>
				<b>Reason Code</b>
				<br />
			<table class="reasonTableClass" id='reasTable'>
			<tr>
				<td>
				<select onchange="return checkAndMakeCommentMendatory(this);" name="reasonCodeList" id="reasonCodeList" class="reasonClass">
					<option selected="selected" value="0">
						Select Code
					</option>
					<c:forEach var="luProviderRespReasonVO" items="${rejectCodes}">
						<option value="${luProviderRespReasonVO.respReasonId}">
							${luProviderRespReasonVO.descr}
						</option>
					</c:forEach>
				</select>
				</td>
			</tr>
			</table>
			
			<!-- --Starts Reject Reason Code changes for vender comment---->
			<table class="reasonTableClass" id='reasTable'>
               <tr>
                 <td>
			   			<b>Step 3: Comment <span id="comment_optional" style="color: red">*</span> </b> 
			   			<label id="remaining_count">Remaining Length: 225</label> 
			   </td>
			  </tr>
			  <tr>
	      	     <td>
	      	      <textarea id="vendor_resp_comment" style="width:240px; height:65px;"
	      					onkeydown="return imposeMaxLength(event, this, 225);"
					        onchange="return imposeMaxLength(event, this, 225);"
					        onkeyup="return doOnChangeValue(event, this, 225);"
					        onkeypress="return doOnChangeValue(event, this, 225);"					        
	      	        > 
	      	      </textarea>
	      	    </td>
			  </tr>
			</table>
			<!-- --Ends Reject Reason Code changes for vender comment---->			
			<br>
			<c:if test="${groupedId!= null && groupedId!=''}">
					<p class="error" style="padding-bottom: 6px;padding-top: 6px">
						<img src="${staticContextPath}/images/warning.png" width="15" height="15" />
						&nbsp;&nbsp;&nbsp;&nbsp; Note: All orders in this group would be
						rejected.
					</p>
			</c:if>
				
				<div id="rejButton" name="rejButton" class="rejButtonClass">
					<input type="image"
						src="${staticContextPath}/images/common/spacer.gif" width="72"
						height="22" 
						style="background-image: url(${staticContextPath}/images/btn/reject.gif); float: right; margin-right: 20px;"
						class="btnBevel" onclick="submitRejectSO(this)" />
				</div>
				<div id="rejRemoveButton" name="rejRemoveButton" style="display:none;margin-right: 10px;" class="rejRemoveButtonClass">
					<input type="button" style="float: right" value="CONFIRM" class="button action btn20Bevel" onclick="submitRejectSO(this)" />
				</div>
						
					<a id="cancelLink" class="btnBevel simplemodal-close" float:left onClick="clearRejectSOSelection()">Cancel</a>
	
					<div style="clear: both;"></div>
			</div>
		</div>
	</div>
</div>
