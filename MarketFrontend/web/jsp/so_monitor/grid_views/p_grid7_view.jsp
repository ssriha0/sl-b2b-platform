<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils,org.owasp.esapi.ESAPI;"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="theTab" value="<%=request.getAttribute("tab")%>"/>
<c:set var="role" value="${roleType}"/>		
<c:set var="roleId" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />
<c:set var="provider" value="false"  scope="request"/><%-- ss: needed for presentation logic brevity --%>
<c:set var="sladmin" value="false"  scope="request"/>
<c:if test="${roleType == 1}"><c:set var="provider" value="true" scope="request" /></c:if>
<c:if test="${SecurityContext.slAdminInd}"><c:set var="sladmin" value="true" scope="request" /></c:if>
<%
String sortColumnSession = (String)session.getAttribute("sortColumnName" + request.getAttribute("tab"));
String sortOrderSession = (String)session.getAttribute("sortOrder" + request.getAttribute("tab"));
String isPrimaryInd = (String)session.getAttribute("isPrimaryInd").toString();
Boolean canManageSO = (Boolean)session.getAttribute("canManageSO");
Boolean dispatchInd = (Boolean)session.getAttribute("dispatchInd");
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/advanced_grid.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/sears_custom.css"/>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_details.css">
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltipcss.css" />

<style type="text/css">
.col1 {width: 100px;}
.col2 {width: 180px;}
.col3 {width: 110px;}
.col4 {width: 100px;}
.col5 {width: 120px;}
.col6 {width: 55px;}
input.action {width: 85px; border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px; background:transparent url(${staticContextPath}/images/common/button-action-bg.png) repeat scroll 0 0; 
border:2px solid #D48B28; color:#333; cursor:pointer; display:block; font: bold 9px Verdana,sans-serif; padding:2px; text-align:center; text-transform:uppercase;}
input.action:hover {background:transparent url(${staticContextPath}/images/common/button-action-hover.png) repeat scroll 0 0; color:#000; }
#soViewProvider {width:665px;}
#soViewProvider td {border:0;}	
.activityAlert{color:#EF3223; font-weight:bold; margin:5px 0; line-height:100%;}
.col5 p {font:bold 12px verdana,sans-serif; margin-bottom:10px;}
.groupedOrderChild {font:bold 9px verdana, sans-serif; padding-left:15px; background: url(${staticContextPath}/images/icons/hierarchyIcon.gif) no-repeat 0 0}
.rejectServiceOrderFrame{width:95%;background-color:#FFF;padding-bottom:22px;text-align:left;}
.rejectServiceOrderFrameBody{width:250px;padding-top:8px;padding-left:8px;padding-right:8px;}
</style>

		 <script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script> 
	<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false ,parseOnLoad: true"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/serviceLiveDojoBase.js" djConfig="isDebug: false ,parseOnLoad: true"></script>
	
		<script type="text/javascript">
		    dojo.require("dojo.parser");
			dojo.require("dijit.Dialog");
			dojo.require("newco.jsutils");
			dojo.require("newco.servicelive.token.PToken");
			dojo.require("newco.servicelive.token.TabToken");
		</script>	

	
	
	</head>
<body class="tundra noBg">
<input type="hidden" name="resourceID" id="resourceID" value="${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}" />	

<s:form action="gridLoader" id="commonGridHandler" >
	<input name="tab" id="tab" value="<%=request.getAttribute("tab")%>" type="hidden"/>
	<input type="hidden" name="status" id="status" />
	<input type="hidden" name="subStatus" id="subStatus" />	
	<input type="hidden" name="serviceProName" id="serviceProName" />
	<input type="hidden" name="buyerRoleId" id="buyerRoleId" />
	<input type="hidden" name="marketName" id="marketName" />
	<input type="hidden" name="sortColumnName" id="sortColumnName" value="<%= sortColumnSession %>" />
	<input type="hidden" name="sortOrder" id="sortOrder" value="<%=sortOrderSession %>" />	
	<input type="hidden" name="resetSort" id="resetSort" value="false" />
</s:form>
	<c:set var="theTab" value="<%=request.getAttribute("tab")%>"/>
	<c:set var="isPrimaryInd" value="<%=isPrimaryInd%>"/>
		<%--c:set var="hash" value="#"/--%>
		<c:set var="grpId" value="GSO_NONE"/>
		<c:set var="grpStartInd" value="false"/>
		<c:set var="groupInd" value="false" scope="request" />
		<c:set var="cntr" value="1" />
		<input type="hidden" name="adminCheck" id="adminCheck" value="${isPrimaryInd}"/>
		<input type="hidden" name="dispatchInd" id="dispatchInd" value="${dispatchInd}"/>
		<ul class="browser filetree treeview-ico">
		<c:forEach var="dto" items="${soOrderList}" varStatus="dtoIndex">
		<c:set var="sorejectId" value="${dto.id}"/>
		
		<c:if test="${canManageSO == true}">
		<c:set var="windowClass" value="jqmWindowReject"  />
			<c:set var="frameClass" value="rejectServiceOrderFrame"  />	
			<c:set var="frameBodyClass" value="rejectServiceOrderFrameBody"  />
		</c:if>
		<c:if test="${isPrimaryInd== true || dispatchInd == true}">
		<c:set var="windowClass" value="jqmWindowRejectAdmin"  />	
			<c:set var="frameClass" value="rejectAdminServiceOrderFrame"  />
			<c:set var="frameBodyClass" value="rejectAdminServiceOrderFrameBody"  />
		</c:if>
		<div id="modal_${sorejectId}" style="display: none;" class="${windowClass}">
			<div class="modalHomepage"><div  style="float:left;">Reject Service Order</div> <a href="#" class="jqmClose" onclick="clearSelection('${sorejectId}')">Close</a> </div>
      			<div class="modalContent" style="padding-left: 20px;">
      
		<div  class="${frameClass}" >

			<div class="${frameBodyClass}">	
				<div style="display:none;"	id="${sorejectId}_reject_error" class="errorBox" >		
					<p class="errorMsg" id="${sorejectId}_reject_error_msg">
							
				</p>
				</div>
				<div id="${sorejectId}_rejectConfirmText" style="display:none;padding-top: 10px;padding-bottom: 5px">
						<img alt="" src="/ServiceLiveWebUtil/images/icons/incIconOn.gif"> Please click confirm below to permanently reject this order for the providers you selected.
				</div>

				<c:set var="pageNo" value="1"/>	
				<div style="padding-top: 10px;padding-bottom: 5px">	
					An asterisk ( <span style="color: red">*</span> ) indicates a required field.<br><br>
				</div>	
				<c:if test="${isPrimaryInd== true || dispatchInd == true}">

					
					
					<b>Step 1: Choose all providers rejecting this service order.<span style="color: red">*</span> </b>
					<div align="left" style="padding-bottom: 5px;padding-top: 5px">
						<a href="javascript:void(0);" onClick="checkAll('${sorejectId}')">Check All</a>&nbsp;|<a href="javascript:void(0);" onClick="clearAll('${sorejectId}')">Clear All</a>
					</div>
					<table>
					<tr>
					<table id="${sorejectId}page${pageNo}">
						<tr>
							<c:forEach var="resources" items="${dto.availableProviders}" varStatus="rowCounter">
							<c:set var="index" value="${rowCounter.count}"  />				
		  						<td style="width: 250px">
		  							<input type="checkbox" id="${sorejectId}_reject_resource_${resources.resourceId}"
									name="${sorejectId}_reject_resource"									
									/>		  	
									${resources.providerLastName}  
									${resources.providerFirstName} #${resources.resourceId}
									(${resources.distanceFromBuyer} Miles)&nbsp;   
								</td>
								<c:if test="${index % 9 == 0}">
									</tr></table>
									<c:set var="pageNo" value="${pageNo+1}"/>
									<table id="${sorejectId}page${pageNo}" style="display: none;"><tr>
								</c:if>
								<c:if test="${index % 3 == 0}">
									</tr><tr>
								</c:if>
							</c:forEach>
						</tr>
						</table>
						</tr>
					</table>	
					<input id="${sorejectId}displayedPage" type="hidden" value="1"/>
					<b>
					Showing <c:choose><c:when test="${fn:length(dto.availableProviders)>=9}">
								<span id="${sorejectId}rejResDispCount">9</span>
							</c:when> 
							<c:otherwise>${fn:length(dto.availableProviders)}</c:otherwise></c:choose>
							of ${fn:length(dto.availableProviders)}</b>
					<a href="javascript:void(0);" id="${sorejectId}viewMore" onclick="viewMore('${sorejectId}')" <c:if test="${index<=9}">style="display: none;"</c:if>>View More</a>
					<span id="${sorejectId}divider" style="display: none;"> | </span>
					<a href="javascript:void(0);" id="${sorejectId}viewLess" onclick="viewLess('${sorejectId}')" style="display: none;">View Less</a>
				</c:if>	
				<c:if test="${not empty so.parentGroupId}">
					<p class="error">
						<img src="${staticContextPath}/images/warning.png" />
						&nbsp;&nbsp;&nbsp;&nbsp; Note: All orders in this group would be
						rejected.
					</p>
				</c:if>
				</br>
				<p>
					<b><c:if test="${isPrimaryInd== true || dispatchInd == true}">Step 2:</c:if> Select a reason for rejecting the service order.<span style="color: red">*</span> </b>
				</p>
				<b>Reason Code</b>
				<br />
				<br />
				<table>
					<tr>
						<td>
							<select onchange="return checkAndMakeCommentMendatory(this,'${sorejectId}');" name="${sorejectId}_reasonCodeList" id="${sorejectId}_reasonCodeList">
							<option selected="selected">
								Select Code
							      </option>
					              <c:forEach var="luProviderRespReasonVO" items="${rejectCodes}">
						                <option value="${luProviderRespReasonVO.respReasonId}">
							               ${luProviderRespReasonVO.descr}
						                </option>
					              </c:forEach>
				          </select>
							<br />
							<br />
						</td>
				
						<td style="width: 50%">
							<input type="hidden" id="${sorejectId}_count" name="${sorejectId}_count" value="0"/>
							
						</td>
					</tr>		
				
				</table>
			<!-- Added for Vender Comment -->
			
		 	<table>
              <tr>
                 <td>
			   			<b>Step 3: Comment <span id="${sorejectId}_comment_optional" style="color: red">*</span> </b> 
			   			<label id="${sorejectId}_remaining_count">Remaining Length: 225</label> 
			   </td>
			  </tr>
			  <tr>
	      		   <td>
	      			<textarea id="${sorejectId}_vendor_resp_comment" rows="4" cols="50"
	      					onkeydown="return imposeMaxLength(event, this, 225);"
					        onchange="return imposeMaxLength(event, this, 225);"
					        onkeyup="return doOnChangeValue(event, this, 225,'${sorejectId}');"
					        onkeypress="return doOnChangeValue(event, this, 225,'${sorejectId}');"	
	      			            ></textarea>
	      		  </td>
			  </tr>
			</table>					
			
				<div id="${sorejectId}_rejButton">
					<input type="image"
						src="${staticContextPath}/images/common/spacer.gif" width="72"
						height="22" 
						style="background-image: url(${staticContextPath}/images/btn/reject.gif); float: right;"
						class="btnBevel" onclick="submitRejectSO('${sorejectId}')" />
				</div>
				<div id="${sorejectId}_rejRemoveButton" style="display:none">
					<input type="button" style="float: right" value="CONFIRM" class="button action btn20Bevel" onclick="submitRejectSO('${sorejectId}')" />
				</div>
				<div><a href="#" name="${sorejectId}" id="cancelLink" onclick="hideModal(this)" style="float:left">Cancel</a></div>
				<div style="clear: both;"></div>			
				</div>
			</div>
		</div>
		
		</div>
			<c:set var="showFullRow" value="true" />
			<c:set var="groupIndicator" value="false"/>
			<c:choose>
			<c:when test="${provider}">
			
			<%-- grouped order logic --%>
			<c:choose><c:when test="${not empty dto.parentGroupId}">
		   		<c:choose><c:when test="${dto.parentGroupId != grpId}">
		   			<c:set var="grpId" value="${dto.parentGroupId}" />
		   			<c:set var="grpStartInd" value="true"/>
		   			<c:set var="groupedOrder" value="true" />
		   			<c:set var="groupIndicator" value="true"/>
		    		<tags:monitorHelper serviceOrderDTO="${dto}" actionName="StripResourceIdFromGroupId"/>
				</c:when>
				<c:otherwise>
					<c:if test="${grpStartInd}"><c:set var="grpStartInd" value="false"/></c:if>
				</c:otherwise></c:choose>
				
			</c:when>
			<c:otherwise>
				<c:set var="groupedOrder" value="false" />
			</c:otherwise></c:choose>
			
			<c:if test="${groupedOrder && !grpStartInd}">
				<c:set var="showFullRow" value="false" />
			</c:if>

			<%--/grouped order logic --%>

<input type="hidden" id="${sorejectId}_groupId" value="${dto.parentGroupId}"/>
<input type="hidden" id="${sorejectId}_groupInd" value="${groupedOrder}"/>
		<c:if test="${showFullRow}">	
			<li>
			<table class="gridTable recdOrders" border="0" width="100%" cellpadding="0" cellspacing="0" id="soViewProvider">
			<tr onclick="collapseExpand(${cntr-1},'${staticContextPath}'),newco.jsutils.displayRightSideMenu('${dto.statusString}', '${dto.providerResponseId}', ${groupIndicator}, '${dto.parentGroupId}', false,false,false,false,'${dto.nonFundedInd}'), 
				parent.newco.jsutils.setRowStateInfo('${cntr}','${dto.id}','${dto.routedResourceId}', false, ''),
				parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], ${groupIndicator}, groupInfoMap${theTab}['${dto.parentGroupId}'],'${isPrimaryInd}','${dispatchInd}','${viewOrderPricing}'),
				parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}]),(_dataGridList${theTab}[${dtoIndex.count}],'${isPrimaryInd}','${groupIndicator}','${dto.parentGroupId}','${theTab}')"
				id="summaryRow_${cntr}">
				<c:choose><c:when test="${(canManageSO == true && isPrimaryInd == false)}">
				<td class="col1">${dto.providerName}<br/> 
             		ID# ${dto.resourceId} 
             	</td>
             	</c:when>
             	<c:otherwise>
             	<td class="col1">Multiple Providers</td>
             	</c:otherwise></c:choose>
		
			<td class="col2">
			<c:choose><c:when test="${groupedOrder}">
					<strong>SO# ${dto.parentGroupId}</strong><br/>
					<a href="soDetailsController.action?groupId=${dto.parentGroupId}&resId=${dto.routedResourceId}&displayTab=Summary" target="_top">${dto.parentGroupTitle}</a><br/>
				<p id="group${dto.parentGroupId}${dto.resourceId}" class="groupedOrderChild">
				<a href="#" class="singleOrderLink" style="color:#666666;" onclick="collapseExpand(${cntr-1},'${staticContextPath}',true),newco.jsutils.displayRightSideMenu('${dto.statusString}', '${dto.providerResponseId}', false, '${dto.id}', false,false,false,false,'${dto.nonFundedInd}'), 
				parent.newco.jsutils.setRowStateInfo('${cntr}','${dto.id}','${dto.routedResourceId}', false, ''),
				parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], false, groupInfoMap${theTab}['${dto.id}'],'${isPrimaryInd}','${dispatchInd}','${viewOrderPricing}'),
				parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}]); stopEventPropagation(event); return false; ">${dto.title} - ${dto.id}</a><br/>
				</p>
			</c:when>
			<c:otherwise>
			 	<strong>SO# ${dto.id}</strong><br/>
				<a href="soDetailsController.action?soId=${dto.id}&resId=${dto.routedResourceId}&displayTab=Summary" target="_top">${dto.title}</a>
			</c:otherwise></c:choose>

			<c:if test="${dto.mainServiceCategory != null}">
				<br/>${dto.mainServiceCategory}
			</c:if>
			
			<c:if test="${dto.buyerRoleId == 3 && dto.statusString != 'Received'}">
				<br/>Commercial Order 
			</c:if>
			
			</td>
			<td class="col3">${dto.cityStateZip}<br/>
			<c:choose><c:when test="${(canManageSO == true && isPrimaryInd == false) && role == '1'}">
			(est. ${dto.distanceInMiles} miles)<br/>
			<a href="http://maps.google.com/maps?q=from:+${dto.resourceDispatchAddress}+to:+${dto.city}+${dto.state}+${dto.zip5}" onclick="window.open(this.href); return false;" target="_blank">Google Directions</a>
			</c:when>
			<c:otherwise>
			<b>(Distance varies by provider)</b>
			</c:otherwise></c:choose>
			<c:if test="${dto.commercialLocation == true}">
			<br/><br/>Commercial Location
			</c:if>
			
			</td>
			
			<td class="col4">
				<c:choose><c:when test="${dto.serviceDate2 == null}">
					<strong>Specific Date:</strong><br/>
					${dto.serviceOrderDateString}				
				</c:when>			
				<c:when test="${dto.serviceDate1 == dto.serviceDate2}">
					<strong>Specific Date:</strong><br/>
					${dto.serviceOrderDateString}
				</c:when>
				<c:otherwise>
					<strong>Date Range:</strong><br/>
					<fmt:formatDate type="date" dateStyle="short" value="${dto.serviceDate1}" /> - <fmt:formatDate type="date" dateStyle="short" value="${dto.serviceDate2}" /><br/>
					<fmt:formatDate type="time" timeStyle="short" value="${dto.serviceTimeStart}" />								
				</c:otherwise></c:choose>
			</td>
			
			<td class="col5">
			<c:choose><c:when test="${groupedOrder}">
					<p><fmt:formatNumber value="${dto.groupSpendLimit}" type="currency" currencySymbol="$"></fmt:formatNumber></p>
				</c:when>
				<c:otherwise>
					<p><fmt:formatNumber value="${dto.spendLimitTotal}" type="currency" currencySymbol="$"></fmt:formatNumber></p>
				</c:otherwise></c:choose>
				
				<c:choose>
					<c:when test="${groupedOrder}">
						<form action="soDetailsController.action?groupId=${dto.parentGroupId}&resId=${dto.routedResourceId}&displayTab=Received" target="_top"
							id="soDetailsController" method="post">
							<s:submit theme="simple" value="view details" cssClass="button action"/>
						</form>
					</c:when>
					<c:otherwise>
						<form action="soDetailsController.action?soId=${dto.id}&resId=${dto.routedResourceId}&displayTab=Received" target="_top"
							id="soDetailsController" method="post">
							<s:submit theme="simple" value="view details" cssClass="button action"/>
						</form>
					</c:otherwise>
				</c:choose>
			<div>
				<strong>Comments:</strong> ${dto.noteOrQuestionCount}
				<br/>
				<a href="soDetailsController.action?soId=${dto.id}&resId=${dto.routedResourceId}&displayTab=Received#viewComments" target="_top">Add Comment</a>
			</div>
			
			<br/>
			<c:if test="${dto.providerResponseId == 2}">
				<div class="activityAlert">
					Pending Counter Offer
				</div>
			</c:if>
			
			
			<strong>Quick Links</strong><br/>
			<a href="#" name="rejectServiceOrderID" id="${dto.id}"  class="rejectLink" target="_top">Reject Order</a>
			</td>
			<td class="col6">${dto.ageOfOrder}</td>
			</tr>
			</table>
			
			<%--SS: this needs to be here even tho we don't expand or collapse rows for this view --%>	
		<div id="div_${cntr}" style="display:none;"><img id="img_${cntr}" name="img_${cntr}" src="${staticContextPath}/images/grid/right-arrow.gif" alt="Expand" /></div>
			<c:set var="cntr" value="${cntr+1}" />
			</li>
	</c:if>
		<%-- use jQuery to populate child orders --%>
		<c:if test="${!showFullRow && groupedOrder}">
			<script type="text/javascript">
			 jQuery(document).ready(function($){
			 var theId = "${grpId}";
			 var resId= "${dto.resourceId}";
			 var cntr = theId.indexOf("#");
			 theId = theId.substring("0",cntr)+resId;
		 	var dHtml = $("#group"+theId).html();
			 var newHtml = dHtml + '<a href="#" class="singleOrderLink" style="color:#666666;" onclick="collapseExpand(${cntr-1},\'${staticContextPath}\',true),newco.jsutils.displayRightSideMenu(\'${dto.statusString}\', \'${dto.providerResponseId}\', ${groupIndicator}, \'${dto.parentGroupId}\', false,false,false,false,\'${dto.nonFundedInd}\'), parent.newco.jsutils.setRowStateInfo(\'${cntr}\',\'${dto.id}\',\'${dto.routedResourceId}\', false, \'\'), parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], ${groupIndicator}, groupInfoMap${theTab}[\'${dto.parentGroupId}\']), parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}]);stopEventPropagation(event);return false;">${dto.title} - ${dto.id}</a><br/>';
			$("#group"+theId).html(newHtml);
			 });
			</script>
		</c:if>
	</c:when>
			
			<c:otherwise>
			<c:choose><c:when test="${not empty dto.parentGroupId}">
		   <c:if test="${dto.parentGroupId != grpId}">
		    <c:set var="grpId" value="${dto.parentGroupId}" />
		    <tags:monitorHelper serviceOrderDTO="${dto}" actionName="StripResourceIdFromGroupId"/>
			<c:if test="${grpStartInd}">
					</ul><%-- end service orders in group --%>
				</li><%-- end order group --%>
			</c:if>
		    <c:set var="grpStartInd" value="true"/>
			<div id="treecontrol${dtoIndex.index}"></div>
				<!-- start order group -->
				<li class="closed soGroup">
					<span class="folder" onclick="newco.jsutils.displayRightSideMenu('${dto.statusString}', '${dto.providerResponseId}', true, '${dto.parentGroupId}', false,false,false,false,'${dto.nonFundedInd}'),
										parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${dto.id}','${dto.availableProviders}', true, '${dto.parentGroupId}'),
										parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], true, groupInfoMap${theTab}['${dto.parentGroupId}'])">
						<table border="0" width="98%" cellpadding="0" cellspacing="0">
							<tr>
						      <td class="somOgId"><span><strong>${dto.parentGroupId}</strong></span>
                                  <c:if test="${(isPrimaryInd == true || canManageSO == true) && role == '1'}">	
                                     <br/> 
                                     Service Pro Name:<strong>${dto.providerName}</strong><br/> 
                                     ID# <strong>${dto.resourceId}</strong> 
                                  </c:if> 
                              <td>
								<td class="somOgTitlePosted"><span>${dto.parentGroupTitle}</span><br/>
									<strong><fmt:message bundle="${serviceliveCopyBundle}" key="widget.label.end.customer" /></strong> ${dto.endCustomerWidget}</td>
								<td class="somOgSL">${dto.groupSpendLimit}</td>
								<td class="somOgTime">${dto.timeToAppointment}</td>
								<td class="somOgLocation">${dto.ageOfOrder}</td>
							</tr>
						</table>
					</span>
					<!-- start service orders in group -->
					<ul>
						<li><table width="100%">
							<tr><td colspan="2" align="right">
								<a href="soDetailsController.action?groupId=${dto.parentGroupId}&resId=${dto.routedResourceId}&displayTab=${theTab}" target="_top">
									<img class="btn20Bevel" src="${staticContextPath}/images/common/spacer.gif" alt="View Order" width="210" height="20" 
												style="background-image: url( ${staticContextPath}/images/btn/view_complete_grouped_so.png);" />
								</a>
							</td></tr>
							<tr><td width="10">&nbsp;</td><td>
								<c:set var="groupId" value="${dto.parentGroupId}" scope="request" />
								<c:set var="groupInd" value="true" scope="request" />
								<c:set var="rowsostatus" value="${dto.status}" scope="request" />
								<c:set var="providerResponseId" value="${dto.providerResponseId}" scope="request" />  
								<c:set var="rowRoutedResourceId" value="${dto.routedResourceId}" scope="request" /> 
								<jsp:include flush="true" page="/jsp/so_monitor/soMonitorQuickLinks.jsp"/>
								<c:set var="groupInd" value="false" scope="request" />
								<c:remove var="groupId" scope="request" />
							</td></tr>
						</table></li>
		   </c:if>
		  </c:when>
		  <c:otherwise>
			<c:if test="${grpStartInd}">
					</ul>
					<!-- end service orders in group -->
				</li>
				<!-- end order group -->
		  	  <c:set var="grpStartInd" value="false"/>
			</c:if>
		  </c:otherwise></c:choose>
			<li>
			<span <c:if test="${not empty dto.parentGroupId}">class='file'</c:if>>
			<table class="gridTable recdOrders" border="0" width="100%" cellpadding="0" cellspacing="0">
			<tr onclick="collapseExpand(${dtoIndex.count -1},'${staticContextPath}'),newco.jsutils.displayRightSideMenu('${dto.statusString}', '${dto.providerResponseId}', false, '${dto.parentGroupId}', false,false,false,false,'${dto.nonFundedInd}'), 
				parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${dto.id}','${dto.routedResourceId}', false, ''),
				parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], false, null),
				parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}])"
				id="summaryRow_${dtoIndex.count}">
				
				<!-- Right Arrow -->
				<td class="column1">
					<img id="img_${dtoIndex.count}" name="img_${dtoIndex.count}"
						src="${staticContextPath}/images/grid/right-arrow.gif" alt="Expand" />
				</td>
				<!-- Status -->
				<td class="column2">
					${dto.statusString}
					<c:set var="rowsostatus" value="${dto.status}" scope="request" />  
					<c:set var="providerResponseId" value="${dto.providerResponseId}" scope="request" />  
				</td>
				<!-- SO # -->
				<td class="column3">
					<span style="white-space:nowrap">${dto.id}</span>
					 <c:if test="${(isPrimaryInd == true || canManageSO == true) && role == '1'}">	
						<br />
						Service Pro Name:<strong>${dto.providerName}</strong><br/> 
                        ID# <strong>${dto.resourceId}</strong> 
                        <br /> 
                     </c:if> 
					<c:if test="${not empty dto.subStatus}">
				        <strong><fmt:message bundle="${serviceliveCopyBundle}" key="substatus" /> </strong>
				        <br />
				        <fmt:message bundle="${serviceliveCopyBundle}"	key="workflow.substate.${dto.subStatus}" />
        			</c:if>
				</td>
				<!-- Title with href to SO detail -->
				<td class="column4">
					<a href="#">${dto.title}</a>
					<br />
					<c:if test="${not empty dto.endCustomerWidget && dto.endCustomerWidget!='N/A'}">
	                    <strong><fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.end.customer" />
	                    </strong>
	                    <br />
	                    ${dto.endCustomerWidget }
                    </c:if>	
				</td>
				
				<!-- Maximum Price -->
				<td class="column5"><label id="sCount_${dto.id}">${dto.spendLimitTotalCurrencyFormat}</label>
				<!-- Time to Appointment -->
				<td class="column6">${dto.timeToAppointment}</td>
				<!-- Age of order -->
				<td class="column7">${dto.ageOfOrder }</td>
			</tr>
			<tr class="selected">
				<td colspan="7" id="expandRow">
					<div id="div_${dtoIndex.count}">
						<table border="0" cellpadding="0" cellspacing="0" width="100%" >
							<tr>
								<td class="sl_grid_col_1">&nbsp;</td>
								<!-- Substatus -->
								<td colspan="2" class="sl_grid_col_2_sub">
									<strong>Resource:</strong>
									${dto.routedResourceName}
								</td>
								<!-- SO Description/Details -->
								<td class="sl_grid_col_4">
				    				<c:choose>
				    					<c:when test="${not empty dto.message && fn:length(dto.message) > 100}">
				    						${fn:substring(dto.message,0,100)} <strong>...</strong><br/><strong>... more info available</strong>
				    					</c:when>
				    					<c:when test="${not empty dto.message && fn:length(dto.message) <= 100}">
				    						${dto.message}
				    					</c:when>
				    					<c:otherwise>&nbsp;</c:otherwise>
				    				</c:choose>
								</td>
								<td colspan="3" class="align-center">
									<!-- 
									<a onclick="parent.newco.jsutils.detailView('${dto.id}','${dto.routedResourceId}','details')" 
										alt="view service order">
									 -->
									 <a href="soDetailsController.action?soId=${dto.id}&resId=${dto.routedResourceId}&displayTab=${theTab}" target="_top">
									<img class="btn20Bevel"
										src="${staticContextPath}/images/common/spacer.gif" alt="View Order" width="164" height="20"
										style="background-image: url( ${staticContextPath}/images/buttons/btn_view_complete_service_order.gif);" />
									</a>
									<c:set var="rowRoutedResourceId" value="${dto.routedResourceId}" scope="request" /> 
								</td>
							</tr>
							<tr>
								<td class="sl_grid_col_1 last">
									&nbsp;
								</td>
								<td class="last" colspan="6">
									<br />
									<c:set var="soId" value="${dto.id}" scope="request" /> 
									<c:set var="groupId" value="${dto.parentGroupId}" scope="request" />
									<jsp:include flush="true" page="/jsp/so_monitor/soMonitorQuickLinks.jsp"/>
									<c:remove var="groupId" scope="request" />
								</td>
							</tr>
						</table>			
					</div>
				</td>
			</tr>
			</table>
				</span>
			</li>
		</c:otherwise>
	</c:choose>

		</c:forEach>
		<c:if test="${grpStartInd}">
				</ul>
				<!-- end service orders in group -->
			</li>
	  		<c:set var="grpStartInd" value="false"/>
			<!-- end order group -->
		</c:if>
		</ul>
		<c:choose>
			<c:when test="${empty soOrderList}">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="no_records_found" />
			</c:when>
			<c:otherwise>
				<jsp:include page="/jsp/paging/pagingsupport.jsp"/>
			</c:otherwise>			
		</c:choose>
	<div dojoType="dijit.Dialog" id="widgetProcessing${theTab}">
			<b>Processing Request
			<div id="messageReq${theTab}"></div></b>
		</div>
		<div dojoType="dijit.Dialog" id="widgetActionSuccess${theTab}" title="Quick Action Response">
			<b>Action Completed Successfully</b><br/>
			<div id="messageS${theTab}"></div>
		</div>
		<div dojoType="dijit.Dialog" id="widgetActionError${theTab}" title="Quick Action Response">
			<b>Action Failed please review</b><br/>
			<div id="message${theTab}"></div>
		</div>
		<div dojoType="dijit.Dialog" id="loadingMsg${theTab}" title="Please Wait">
			We are refreshing your results.
		</div>

	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
	<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
 	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/animatedcollapse.js"></script>
	<script language="JavaScript" type="text/javascript">
		<c:choose><c:when test="${provider}">loadDivs(${cntr-1});</c:when>	
		<c:otherwise>loadDivs(${fn:length(soOrderList)});</c:otherwise></c:choose>
	
	</script>

		<!--  Include JSP Fragment that loads the data for the Order Express Widget -->
		<c:set var="pageData" value="${soOrderList}" />
		<%@ include file="/jsp/public/common/loadDataForOrderExpressWidget.jspf" %>
		
<script language="JavaScript" type="text/javascript">		
		parent._commonSOMgr._clearPersistStateCache();
		//var theCount = 0;
		var theActualCount = 0;
		<c:forEach var="dto" items="${soOrderList}" varStatus="dtoIndex2">
			parent._commonSOMgr.addToken(new PToken('${dto.id}','${dto.spendLimitTotal}',${dtoIndex2.index}));
			//theCount++;
		</c:forEach>
		theActualCount = parent.getSummaryTabCount('Received');
		parent._commonSOMgr.addToken(new TabToken('Received',theActualCount));
		//parent._commonSOMgr.addToken(new TabToken('Received',theCount));
		parent._commonSOMgr.startService();
		
<c:set var="statusCount" value="<%= new Integer(((ArrayList)request.getAttribute("serviceOrderStatusVOList")).size())%>"/> 
<c:set var="status" value="<%=request.getAttribute("serviceOrderStatusVOList")%>"/> 
var statusSize = ${statusCount};
topFilterList = new Array();
filterList = new Array(statusSize);
    <c:forEach var="statusSubstatusList" items="${serviceOrderStatusVOList}" varStatus="outInx">
			filterList[${outInx.index}] = [
		<c:forEach var="substatusList" items="${statusSubstatusList.serviceOrderSubStatusVO}" varStatus="inInx">
    	 						<c:choose>
    	 							<c:when test="${inInx.last}">
                                          {val1:'${substatusList.subStatusId}',val2:'${substatusList.subStatusName}' }
                                    </c:when>
                                    <c:otherwise>
                                        {val1:'${substatusList.subStatusId}',val2:'${substatusList.subStatusName}' },
                                    </c:otherwise>
                                 </c:choose>
             </c:forEach>  
             	]   
               topFilterList['${statusSubstatusList.statusId}'] = filterList[${outInx.index}];
     </c:forEach>
parent.p_topFilterList=topFilterList;
parent.p_filterList=filterList;
		
		
		function doStatusSubmit(status, subStatus,serviceProName,marketName, buyerRoleId){
			
			//if(buyerRoleId != null)
			//	alert('doStatusSubmit() buyerRoleId=' + buyerRoleId);
		
			newco.jsutils.Uvd('status',status) ;
			newco.jsutils.Uvd('subStatus',subStatus) ;
			newco.jsutils.Uvd('serviceProName',serviceProName) ; 
            newco.jsutils.Uvd('marketName',marketName) ;			
			newco.jsutils.Uvd('resetSort','false') ;
			newco.jsutils.Uvd('buyerRoleId', buyerRoleId);
			newco.jsutils.displayModal('loadingMsg${theTab}');
			jQuery('#commonGridHandler').submit();
		}

		//Added For Reject Reason Code
		function doOnChangeValue(Event, textArea, limit,so_id) {
			jQuery(document).ready(function($) {
			var remLength=limit;
			
			if (textArea.value.length > limit) {
			  //textArea.value=textArea.value.substring(0,limit); return;
			  textArea.value = textArea.value.substring(0, 225);       // Added
			  return imposeMaxLength(Event, textArea, limit);
			  }
			else{
				 remLength = limit - textArea.value.length;
				 var displayMsg ="Remaining Length: "+remLength;
			 	 document.getElementById(so_id+"_remaining_count").style.display='';
				 document.getElementById(so_id+"_remaining_count").innerHTML=displayMsg;
			}

			}); 
		    }
		 
		     function checkAndMakeCommentMendatory(Object,so_id){
		     var index=Object.selectedIndex;
		    jQuery(document).ready(function($) {
		    if(index==3 || index==6 || index==7 || index==8)
		    document.getElementById(so_id+"_comment_optional").style.display='';
		    else
		    document.getElementById(so_id+"_comment_optional").style.display='none';
		    });
		    }
		    function imposeMaxLength(Event, Object, MaxLen){
		     return (Object.value.length < MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
		    }

		    function removeWhiteSpaceForComment(so_id) {
		    
		         var myTxtArea = document.getElementById(so_id+"_vendor_resp_comment");
		         myTxtArea.value =""; 
		    
		    }
		 //Ending changes For Reject Reason Code
		 				
</script>

	<jsp:include page="popup_reject_service_order.jsp"></jsp:include>
	
	<script type="text/javascript">
	
		var theRole = '${role}';
		var rejectSoId;
		var routedResourceId;
		function doAction() {
			    newco.jsutils.displayModal('loadingMsg${theTab}');
				jQuery('#commonGridHandler').submit();
		}
		
		function showCommonMsg( theState, theMsg ){
			newco.jsutils.displayActionTileModal('${theTab}',theState,theMsg);	
		}
		function closeReject(so_id){
		
		$("#modal_"+so_id).jqmHide();
			
		}
		function checkAll(so_id){
         
         
         var rejResources = document.getElementsByName(so_id+"_reject_resource");
		 var totalCount = rejResources.length;	
		 var displayedPage=document.getElementById(so_id+"displayedPage").value;
		 var limit = displayedPage*9;
		 if(limit>=totalCount){limit=totalCount;}	
			for(var i = 0; i < limit; i++){
			
			rejResources[i].checked = true;
			
			}
          	            
          }
          
	 	function clearAll(so_id){
         
         
         var rejResources = document.getElementsByName(so_id+"_reject_resource");
         var totalCount = rejResources.length;	
		 var displayedPage=document.getElementById(so_id+"displayedPage").value;
		 var limit = displayedPage*9;
         if(limit>=totalCount){limit=totalCount;}			
		 for(var i = 0; i < limit; i++){
			rejResources[i].checked = false;
		 }         	   
          
        }
        
        function viewMore(so_id){
			var displayedPage=parseInt(document.getElementById(so_id+"displayedPage").value);
			var totalCount = document.getElementsByName(so_id+"_reject_resource").length;	
			var totalPages = parseInt((totalCount/9))+(totalCount%9==0?0:1);
			var nextPage = displayedPage+1;
			if(displayedPage<totalPages){
				document.getElementById(so_id+"page"+nextPage).style.display= "";
				document.getElementById(so_id+"displayedPage").value=nextPage;
				document.getElementById(so_id+"viewLess").style.display ="";
				document.getElementById(so_id+"divider").style.display ="";
			}
			if(nextPage==totalPages){
				document.getElementById(so_id+"viewMore").style.display ="none";
				document.getElementById(so_id+"divider").style.display ="none";
			}
			var limit = nextPage*9;
		 	if(limit>=totalCount){limit=totalCount;}
			document.getElementById(so_id+"rejResDispCount").innerHTML=limit;		
		}
		
		function viewLess(so_id){
			var displayedPage=parseInt(document.getElementById(so_id+"displayedPage").value);
			var totalCount = document.getElementsByName(so_id+"_reject_resource").length;	
			var totalPages = parseInt((totalCount/9))+(totalCount%9==0?0:1);
			var previousPage = displayedPage-1;
			if(displayedPage>1){
				document.getElementById(so_id+"page"+displayedPage).style.display= "none";
				document.getElementById(so_id+"displayedPage").value=previousPage;
				document.getElementById(so_id+"viewMore").style.display ="";
				document.getElementById(so_id+"divider").style.display ="";
			}
			if(previousPage==1){
				document.getElementById(so_id+"viewLess").style.display ="none";
				document.getElementById(so_id+"divider").style.display ="none";
			}
			var limit = previousPage*9;
		 	if(limit>=totalCount){limit=totalCount;}
			document.getElementById(so_id+"rejResDispCount").innerHTML=limit;		
		}
		function showResourceInRejectWidjet(resourceData,adminInd,groupInd,groupId,tab){

			var resource_data = "<input type='hidden' id='groupID' value='"+groupId+"'/>";
			resource_data =resource_data+ "<input type='hidden' id='groupInd' value='"+groupInd+"'/>";
		
			var errorObj=parent.document.getElementById("rejectServiceOrderResponseMessage"+tab);
			jQuery(errorObj).html("");
			if(adminInd=='true'){
	
	
				var buyerIndex = resourceData.theTitle;
				var resourceCount = resourceData.availableProviders.length;			
				resource_data = resource_data + "<div id='rejectWidgetResourcesPanel'"
				if(resourceCount>8){
					resource_data = resource_data + "style='height: 180px; overflow: auto' >";
				}else{
					resource_data = resource_data + ">";
				}
				resource_data =resource_data + "<table id='rejectTable' border='0' cellspacing='0' cellpadding='0'>";				
				var resourceIndex = 0;
					for(resourceIndex=0; resourceIndex<resourceData.availableProviders.length; ++resourceIndex) {						
						resource_data +="<tr id='row_"+resourceData.availableProviders[resourceIndex].resourceId+"'>";							
						resource_data += "<td> <input type='checkbox' id='resource_reject_"+resourceData.availableProviders[resourceIndex].resourceId+"' name='resource_reject' theme='simple' /></td><td> "+resourceData.availableProviders[resourceIndex].lastName+"</td><td> "+resourceData.availableProviders[resourceIndex].firstName+"</td><td> "+"("+resourceData.availableProviders[resourceIndex].resourceId+")"+"</td>";						
						resource_data += "</tr>";
					}
						
				resource_data += "</table> </div>";					
				resource_data +="<div align='right'><a href='javascript:void(0);' onClick='checkAllInRejectWidget()'>Check All</a> | <a href='javascript:void(0);' onClick='clearAllInRejectWidget()'>Clear All</a></div>";
			}
											
			var obj=parent.document.getElementById("rejectResources"+tab);
				
		    jQuery(obj).html(resource_data);
						
		}

	function clearSelection(so_id)
	{
			clearAll(so_id);
			document.getElementById(so_id+"_reasonCodeList").selectedIndex = 0;
			jQuery('.'+so_id+'_rejButton').css("display", "block");	
			jQuery('.'+so_id+'_rejRemoveButton').css("display", "none");
	}	
	
	var rejectCount=0; 	 
	function hideModal(obj){
		 var so_id =jQuery(obj).attr('name');			
		 jQuery("#modal_"+so_id).jqmHide();
		 clearSelection(so_id);
		} 
	jQuery(document).ready(function($){
		
 		$("#rejectSo").jqm({modal:true}); 		
		
		$(".rejectLink").click(function(e){
			var so_id =$(this).attr('id');		
			var x = e.pageX;
			var y = e.pageY;
			var adminCheck= document.getElementById("adminCheck").value;
			var dispatchInd = document.getElementById("dispatchInd").value;
			$("#modal_"+so_id).jqm({modal:true});
			$("#modal_"+so_id).css('top',y-80);
			if(adminCheck=='true' || dispatchInd == 'true'){
		
				$("#modal_"+so_id).css('left',x-200);
				document.getElementById(so_id+"_rejectConfirmText").style.display='none';
			}
			else{
		
				$("#modal_"+so_id).css('left',x-100);
			}
			document.getElementById(so_id+"_reject_error").style.display='none';
			document.getElementById(so_id+"_reject_error_msg").innerHTML="";
			document.getElementById(so_id+"_rejRemoveButton").style.display="none";
			document.getElementById(so_id+"_rejButton").style.display="";	

			rejectCount=0;
			$("#modal_"+so_id).jqmShow();
					
			rejectSoId = $(this).attr('id');		
		
		removeWhiteSpaceForComment(so_id);
		document.getElementById(so_id+"_remaining_count").innerHTML= "Max Length: 225";
		document.getElementById(so_id+"_comment_optional").style.display='none';
			return false;
		});
		
		
	}); 

		function stopEventPropagation(e){
			if(e.stopPropagation) {e.stopPropagation();}
			e.cancelBubble = true;
		}
		function displayRejButton(so_id){
		
		document.getElementById(so_id+"_rejRemoveButton").style.display="none";
			document.getElementById(so_id+"_rejButton").style.display="block";
		}
		
	function submitRejectSO(so_id){
		document.getElementById(so_id+"_reject_error").style.display='none';
		var adminCheck= document.getElementById("adminCheck").value;
		var groupInd= document.getElementById(so_id+"_groupInd").value;		
		var dispatchInd = document.getElementById("dispatchInd").value;
		if(groupInd=='true'){
		
			var groupId= document.getElementById(so_id+"_groupId").value;
		
		}		
	
		var selectedReasonCode = document.getElementById(so_id+"_reasonCodeList");
		var venderRejectRreasonDesc = document.getElementById(so_id+"_vendor_resp_comment").value;
		if(adminCheck == 'true' || dispatchInd == 'true'){
		var rejResources = document.getElementsByName(so_id+"_reject_resource");
		var checkedResources="";
		for(var i = 0; i < rejResources.length; i++){
			
			if(rejResources[i].checked) {
				var val1= rejResources[i].id;			
				var resource_id=val1.substring(33,val1.length);
				checkedResources=checkedResources+","+resource_id;
			}
		}
		
		}else{
			checkedResources= document.getElementById('resourceID').value;
		}
			
		if(checkedResources=='' && (adminCheck == 'true' || dispatchInd == 'true')){
			document.getElementById(so_id+"_reject_error").style.display='block';
			document.getElementById(so_id+"_reject_error_msg").innerHTML= "Please select 1 or more providers first";
			return false;
		}
		var index = selectedReasonCode.selectedIndex;
			
		if(index==0){
			document.getElementById(so_id+"_reject_error").style.display='block';
			document.getElementById(so_id+"_reject_error_msg").innerHTML= "Please select reason to reject";
			return false;
		}
		//Added for Reject Reson Code
		if((index==3 || index==6 || index==7 || index==8)&& (venderRejectRreasonDesc.replace(/^\s+|\s+$/g,"").length==0)) {
			document.getElementById(so_id+"_vendor_resp_comment").value='';		//Added
		    document.getElementById(so_id+"_reject_error").style.display='block';
			document.getElementById(so_id+"_reject_error_msg").innerHTML= "Please enter comment for selected reason.";
		    return false;	
		}
		document.getElementById(so_id+"_vendor_resp_comment").value = venderRejectRreasonDesc.replace(/^\s+|\s+$/g,"");  // Added
		
		//End of Reject Reson Code
		if(rejectCount==0 && (adminCheck=='true' || dispatchInd == 'true')){
			
			 document.getElementById(so_id+"_rejectConfirmText").style.display="block";
			 document.getElementById(so_id+"_rejButton").style.display="none";
			 document.getElementById(so_id+"_rejRemoveButton").style.display="block";
			 rejectCount= rejectCount+1;
			 return false;
		}
			 		
		var submitForm = document.getElementById('rejectForm');	
		var selectedVal = selectedReasonCode.value;
		submitForm.reasonId.value = selectedVal;		
		submitForm.soId.value = rejectSoId;
		submitForm.resId.value = checkedResources;
		submitForm.resId.value = checkedResources;	
		submitForm.reasonText.value	= venderRejectRreasonDesc;		
		if(groupInd=='true'){
   			//var grpId = ESAPI.encoder.canonicalize(groupId);
   			//groupId = ESAPI.encoder().encodeForHTML(grpId);
			submitForm.groupId.value = groupId;			
		}
			
		submitForm.submit();
			
	}
		
	</script>	
		
</body>
</html>
