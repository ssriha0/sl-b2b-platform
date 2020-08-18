<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
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
<s:form action="gridLoader" id="commonGridHandler" >
	<input name="tab" id="tab" value="<%=request.getAttribute("tab")%>" type="hidden"/>
	<input type="hidden" name="status" id="status" />
	<input type="hidden" name="subStatus" id="subStatus" />	
	<input type="hidden" name="serviceProName" id="serviceProName" />
	<input type="hidden" name="searchWords" id="searchWords" />  
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
		<ul class="browser filetree treeview-ico">
		<c:forEach var="dto" items="${soOrderList}" varStatus="dtoIndex">
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

		<c:if test="${showFullRow}">	
			<li>
			<table class="gridTable recdOrders" border="0" width="100%" cellpadding="0" cellspacing="0" id="soViewProvider">
		<!-- 	<tr onclick="collapseExpand(${cntr-1},'${staticContextPath}'),newco.jsutils.displayRightSideMenu('${dto.statusString}', '${dto.providerResponseId}', ${groupIndicator}, '${dto.parentGroupId}', false), 
				parent.newco.jsutils.setRowStateInfo('${cntr}','${dto.id}','${dto.routedResourceId}', false, ''),
				parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], ${groupIndicator}, groupInfoMap${theTab}['${dto.parentGroupId}']),
				parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}])"
				id="summaryRow_${cntr}">  -->
				<td class="col1">
					<div dojoType="dijit.Dialog" id="hiddenProviderList${dtoIndex.count}" title="Select a Member">
						<s:form action="soDetailsController.action?soId=%{dto.id}&resId=%{SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}&displayTab=Summary" target="_top">
				<br/>Choose which team member you would like to take the order:<br/><br/><br/>
						<select  name="bidderResourceId"id="bidderResourceId" class="dropdown-110">
											
							<c:forEach var="availableProviders" items="${dto.availableProviders}" varStatus="apIndex">
								<option value="${availableProviders.resourceId}">
								<c:if test="${availableProviders.providerLastName != null}">
								 	${availableProviders.providerLastName}
								 </c:if>						 
								 <c:if test="${availableProviders.providerFirstName != null}">
								 	,${availableProviders.providerFirstName}
								 </c:if>					
								</option>
							</c:forEach>
					</select>	<br/><br/>
						<div align="center">	<s:submit theme="simple" value="Continue" cssClass="button action"/><br/> </div>
				    		
					</s:form>
					</div>
					
				    
					<c:forEach var="availableProviders" items="${dto.availableProviders}" varStatus="apIndex">
						${availableProviders.providerLastName} , ${availableProviders.providerFirstName}<br/> 
             			ID# ${availableProviders.resourceId} <br/><br/>
             		</c:forEach>
             	</td>
			<td class="col2">
			
			 	<strong>SO# ${dto.id}</strong><br/>
						${dto.title}</strong>
				<br/>${dto.message}
			</td>
			<td class="col3">${dto.cityStateZip}<br/>
			(est. ${dto.distanceInMiles} miles)<br/>
			<a href="http://maps.google.com/maps?q=from:+${dto.resourceDispatchAddress}+to:+${dto.city}+${dto.state}+${dto.zip5}" onclick="window.open(this.href); return false;" target="_blank">Google Directions</a>
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
					<%-- <fmt:formatDate type="time" timeStyle="short" value="${dto.serviceTimeStart}" /> --%>							
				</c:otherwise></c:choose>
			</td>
			
			<td class="col5">
			
			<%-- <s:submit theme="simple" value="Place Bid" onClick="displayBidModalWindow(hiddenProviderList${dto.id});" cssClass="button action"/>   	
			<input cssClass="button action" type="submit"  value="Place Bid" onclick="displayBidModalWindow('hiddenProviderList${dtoIndex.count}');"/> --%>
			
			<s:form  onsubmit="return displayBidModalWindow('hiddenProviderList%{dtoIndex.count}');" target="_top">
					<div style="height:10px">&nbsp;</div>
					<c:choose><c:when test="${dto.currentBidPrice == null || dto.currentBidPrice == 0.0}">
						<s:submit theme="simple" value="Place Bid" cssClass="button action"/><br/>
					</c:when>
					<c:otherwise>								
						<s:submit theme="simple" value="Change Bid" cssClass="button white action"/><br/>
					</c:otherwise></c:choose>
			</s:form>

			
			
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
			 var newHtml = dHtml + '<a href="#" class="singleOrderLink" style="color:#666666;" onclick="collapseExpand(${cntr-1},\'${staticContextPath}\',true),newco.jsutils.displayRightSideMenu(\'${dto.statusString}\', \'${dto.providerResponseId}\', ${groupIndicator}, \'${dto.parentGroupId}\', false,false,false,false,\'${dto.statusString}\'), parent.newco.jsutils.setRowStateInfo(\'${cntr}\',\'${dto.id}\',\'${dto.routedResourceId}\', false, \'\'), parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], ${groupIndicator}, groupInfoMap${theTab}[\'${dto.parentGroupId}\']), parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}]);stopEventPropagation(event);return false;">${dto.title} - ${dto.id}</a><br/>';
			$("#group"+theId).html(newHtml);
			 });
			</script>
		</c:if>
	</c:when>
		
			
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

		
		
	<script language="JavaScript" type="text/javascript">		
				
			function doStatusSubmit(status, subStatus,serviceProName,marketName, buyerRoleId, searchWordsValue){
				//alert('Calling the doStatus submit');
				//if(buyerRoleId != null)
				//	alert('doStatusSubmit() buyerRoleId=' + buyerRoleId);
			
				newco.jsutils.Uvd('status',status) ;
				newco.jsutils.Uvd('subStatus',subStatus) ;
				newco.jsutils.Uvd('serviceProName',serviceProName) ; 
	            newco.jsutils.Uvd('marketName',marketName) ;			
				newco.jsutils.Uvd('resetSort','false') ;
				newco.jsutils.Uvd('buyerRoleId', buyerRoleId);
				newco.jsutils.Uvd('searchWords',searchWordsValue);
				newco.jsutils.displayModal('loadingMsg${theTab}');
				jQuery('#commonGridHandler').submit();
			}	
			
			
			function displayBidModalWindow(hiddenProviderListModal){
				newco.jsutils.displayModal(hiddenProviderListModal);
				return false;
			}
			
	</script>

	
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


	
		function stopEventPropagation(e){
			if(e.stopPropagation) {e.stopPropagation();}
			e.cancelBubble = true;
		}
	</script>	


</body>
</html>
