<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="theTab" value="<%=request.getAttribute("tab")%>"/>
<c:set var="role" value="${roleType}"/>		

<%
String sortColumnSession = (String)session.getAttribute("sortColumnName" + request.getAttribute("tab"));
String sortOrderSession = (String)session.getAttribute("sortOrder" + request.getAttribute("tab"));
String priceModelSession = (String)session.getAttribute("priceModel" + request.getAttribute("tab"));

Boolean incSpendLimit = (Boolean)session.getAttribute("incSpendLimit");
Boolean taskLevelPriceInd = (Boolean)session.getAttribute("taskLevelPriceInd");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
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

	<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false ,parseOnLoad: true"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/serviceLiveDojoBase.js" djConfig="isDebug: false ,parseOnLoad: true"></script>
	
</head>
<body class="tundra noBg">

	<s:form action="gridLoader" id="commonGridHandler" >
		<input name="tab" id="tab" value="<%=request.getAttribute("tab")%>" type="hidden"/>
		<input type="hidden" name="status" id="status" />
		<input type="hidden" name="subStatus" id="subStatus" />
		<input type="hidden" name="priceModel" id="priceModel" value="<%=request.getAttribute("priceModel")%>" />		
		<input type="hidden" name="sortColumnName" id="sortColumnName" value="<%= sortColumnSession %>" />
		<input type="hidden" name="sortOrder" id="sortOrder" value="<%=sortOrderSession %>" />	
		<input type="hidden" name="resetSort" id="resetSort" value="false" />
	</s:form>
<c:set var="theTab" value="<%=request.getAttribute("tab")%>"/>
<c:set var="grpId" value="GSO_NONE"/>
<c:set var="grpStartInd" value="false"/>
<c:set var="groupInd" value="false" scope="request" />
<ul class="browser filetree treeview-ico">
     <c:forEach var="buyer" items="${soOrderList}" varStatus="dtoIndex">
		  <c:choose><c:when test="${not empty buyer.parentGroupId}">
		   <c:if test="${buyer.parentGroupId != grpId}">
		    <c:set var="grpId" value="${buyer.parentGroupId}" />
		    <tags:monitorHelper serviceOrderDTO="${buyer}" actionName="StripResourceIdFromGroupId"/>
			<c:if test="${grpStartInd}">
					</ul>
					<!-- end service orders in group -->
				</li>
				<!-- end order group -->
			</c:if>
		    <c:set var="grpStartInd" value="true"/>
			<div id="treecontrol${dtoIndex.index}"></div>
				<!-- start order group -->
				<li class="closed soGroup">
					<span class="folder" onclick="newco.jsutils.displayRightSideMenu('${buyer.statusString}', '${buyer.providerResponseId}', true, '${buyer.parentGroupId}', false, '${incSpendLimit}', '${buyer.taskLevelPriceInd}'),
										parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${buyer.id}','${buyer.routedResourceId}', true, '${buyer.parentGroupId}'),
										parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], true, groupInfoMap${theTab}['${buyer.parentGroupId}']),setIncreaseSpendLimit(_dataGridList${theTab}[${dtoIndex.count}])">
						<table border="0" width="98%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="somOgId"><strong>${buyer.parentGroupId}</strong></td>
								<td class="somOgTitlePosted">
									<span>${buyer.parentGroupTitle}</span><br/>
									<strong><fmt:message bundle="${serviceliveCopyBundle}" key="widget.label.end.customer" /></strong> ${buyer.endCustomerWidget}
									<div class="blueIcon"><a href="#" style="cursor: help;" title="Your service order was routed to ${buyer.providersSentTo} providers."><label id="pCount_${buyer.id}">${buyer.providersSentTo}</label></a></div>
									<div class="yellowIcon"><a href="#"  style="cursor: help;" title="You have ${buyer.providersConditionalAccept} counter offers on your service order."><label id="cCount_${buyer.id}">${buyer.providersConditionalAccept}</label></a></div>
									<div class="redIcon"><a href="#"  style="cursor: help;" title="${buyer.providersDeclined} providers have declined your service order."><label id="rCount_${buyer.id}">${buyer.providersDeclined}</label></a></div>
									<c:if test="${buyer.providersConditionalAccept > 0}">
										<!-- <div><a href="http://community.servicelive.com/docs/pco.pdf" target="_blank">Help?</a></div> -->
									</c:if>
									<!-- <div class="responseBar"><img src="${staticContextPath}/images/response_bar_${buyer.responseCount}.GIF" /></div> -->
								</td>
								<td class="somOgSL">${buyer.groupSpendLimit}</td>
								<td class="somOgTime">${buyer.timeToAppointment}</td>
								<td class="somOgLocation">${buyer.ageOfOrder}</td>
							</tr>
						</table>
					</span>
					<!-- start service orders in group -->
					<ul>
						<li><table width="100%">
							<tr>
							    <td class="last" style="padding-right: 10px" align="right">
									<a href="soDetailsController.action?groupId=${buyer.parentGroupId}&displayTab=${theTab}" target="_top">
										<img class="btn20Bevel" src="${staticContextPath}/images/common/spacer.gif" alt="View Order" width="210" height="20" 
													style="background-image: url( ${staticContextPath}/images/btn/view_complete_grouped_so.png);" />
									</a>
								</td> 
							</tr>
							<tr>
								<td>
									<c:set var="groupId" value="${buyer.parentGroupId}" scope="request" />
									<c:set var="groupInd" value="true" scope="request" />
									<c:set var="rowsostatus" value="${buyer.status}" scope="request" />
									<jsp:include flush="true" page="/jsp/so_monitor/soMonitorQuickLinks.jsp"/>
									<c:set var="groupInd" value="false" scope="request" />
									<c:remove var="groupId" scope="request" /> 
								</td>
							</tr>
							</table>
						</li>
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
				<span <c:if test="${not empty buyer.parentGroupId}">class='file'</c:if>>
					<table class="gridTable recdOrders" border="0" width="100%" cellpadding="0" cellspacing="0" style="word-wrap:break-word;table-layout: fixed">
  <tr onclick="collapseExpand(${dtoIndex.count -1},'${staticContextPath}'),newco.jsutils.displayRightSideMenu('${buyer.statusString}', '${buyer.providerResponseId}', false, '${buyer.parentGroupId}', false, '${incSpendLimit}', '${buyer.taskLevelPriceInd}'), 
										parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${buyer.id}','${buyer.routedResourceId}', false, ''),
							  			parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], false, null),
							  			parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}])"
	  id="summaryRow_${dtoIndex.count}">
    <td class="column1" style="width:3%;">
    	
			<img id="img_${dtoIndex.count}" name="img_${dtoIndex.count}"
			  	  src="${staticContextPath}/images/grid/right-arrow.gif" alt="Expand"/>
	
    </td>
    <td class="column2" style="width:11%;">
    	${buyer.statusString}
    	<c:set var="rowsostatus" value="${buyer.status}" scope="request" /><s:if test="buyerBidOrdersEnabled"><br/>
	    	<c:choose><c:when test="${buyer.sealedBidInd}">
	    		<fmt:message bundle="${serviceliveCopyBundle}" key="workflow.pricemodel.sealed.bid" />
	    	</c:when>
			<c:otherwise>
				<fmt:message bundle="${serviceliveCopyBundle}" key="workflow.pricemodel.${buyer.priceModelType}" />
			</c:otherwise></c:choose>
    	</s:if>
    </td>
    <td class="column3" style="width:19%;">
    	<span style="white-space:nowrap">${buyer.id}</span>
		<br />
		<c:if test="${not empty buyer.subStatus}">
	        <strong><fmt:message bundle="${serviceliveCopyBundle}"
				key="substatus" /> </strong>
	        <br />
	        <fmt:message bundle="${serviceliveCopyBundle}"	key="workflow.substate.${buyer.subStatus}" />
        </c:if>
    </td>
    <td class="column4"  align="justify" style="width:34%;">
    	<span class="grid_title_mo1" onMouseOver="this.className='grid_title_mo2';" onMouseOut="this.className='grid_title_mo1';" >${buyer.title}</span>
    	<c:if test="${empty buyer.parentGroupId}">
	    	<br />
	        
	      <div class="blueIcon"><a href="#" style="cursor: help;" title="Your service order was routed to ${buyer.providersSentTo} providers."><label id="pCount_${buyer.id}">${buyer.providersSentTo}</label></a></div>
	      <c:if test="${buyer.priceModelType != 1}">
	      <div class="yellowIcon"><a href="#"  style="cursor: help;" title="You have ${buyer.providersConditionalAccept} counter offers on your service order."><label id="cCount_${buyer.id}">${buyer.providersConditionalAccept}<s:if test="buyerBidOrdersEnabled"> (${buyer.newBidCount} New)</s:if></label></a></div>
	      </c:if><c:if test="${buyer.priceModelType == 1}">
	      <div class="yellowIcon"><a href="#"  style="cursor: help;" title="You have ${buyer.providersConditionalAccept} bids on your service order."><label id="cCount_${buyer.id}">${buyer.providersConditionalAccept}<s:if test="buyerBidOrdersEnabled"> (${buyer.newBidCount} New)</s:if></label></a></div>
	      </c:if>
	      <s:if test="buyerBidOrdersEnabled">
	      <div class="commentsIcon"><a href="#" style="cursor: help;" title="Your service order contains ${buyer.noteOrQuestionCount} posts."><label id="commentCount_${buyer.id}">${buyer.noteOrQuestionCount} (${buyer.newNoteOrQuestionCount} New)</label></a></div>
	      </s:if>
	      <div class="redIcon"><a href="#"  style="cursor: help;" title="${buyer.providersDeclined} providers have declined your service order."><label id="rCount_${buyer.id}">${buyer.providersDeclined}</label></a></div>
	      <c:if test="${buyer.providersConditionalAccept > 0 && buyer.priceModelType != 1}">
	      	<div><a href="http://community.servicelive.com/docs/pco.pdf" target="_blank">Help?</a></div>
	      </c:if>
	       <!-- <div class="responseBar"><img src="${staticContextPath}/images/response_bar_${buyer.responseCount}.GIF" /></div> -->
	    </c:if>
	    <c:if test="${not empty buyer.endCustomerWidget && buyer.endCustomerWidget!='N/A'}">
    		<div style="clear:both"></div>
	        <strong>Customer: </strong> ${buyer.endCustomerWidget }
	    </c:if>
    </td>
    <td class="column5" style="width:14%;">
    <c:if test="${buyer.priceModelType != 1}">
    	<label id="sCount_${buyer.id}">${buyer.spendLimitTotalCurrencyFormat}</label>
    	<c:if test="${buyer.bids > 0 }">
    		<br />
    		(<fmt:formatNumber value="${buyer.lowBid}" type="currency"/> to <fmt:formatNumber value="${buyer.highBid}" type="currency"/>)
    	</c:if>
    </c:if>
    <c:if test="${buyer.priceModelType == 1}">
    	<c:if test="${buyer.bids > 0 }">
    		<fmt:formatNumber value="${buyer.lowBid}" type="currency"/> to <fmt:formatNumber value="${buyer.highBid}" type="currency"/>
    	</c:if>
    	<c:if test="${buyer.bids == 0 }">
    		No bids yet
    	</c:if>
    </c:if>
    </td>
    <td class="column6" style="width:13%;">${buyer.timeToAppointment}</td>
    <td class="column7" style="width:6%;">${buyer.ageOfOrder}</td>
  </tr>
 
  
  <!-- -->
  <tr class="selected">
  	<td colspan="7" id="expandRow">
  		<div id="div_${dtoIndex.count}" name="div_${dtoIndex.count}" >
  		<table border="0" cellpadding="0" cellspacing="0">
  			<tr class="last"> 
			    <td>&nbsp;</td>
				<td style="padding-left: 10px; padding-right: 10px"> 
    				<c:choose>
    					<c:when test="${not empty buyer.message && fn:length(buyer.message) > 100}">
							<strong>Description:</strong> 
    						${fn:substring(buyer.message,0,100)} <em>... more info available</em>
    					</c:when>
    					<c:when test="${not empty buyer.message && fn:length(buyer.message) <= 100}">
    						${buyer.message}
    					</c:when>
    					<c:otherwise>&nbsp;</c:otherwise>
    				</c:choose>
				</td>
			    <td style="padding-right: 10px; width: 170px">
			    	<a href="soDetailsController.action?soId=${buyer.id}&displayTab=${theTab}" target="_top">
						<img class="btn20Bevel" src="${staticContextPath}/images/common/spacer.gif" alt="View Order" width="164" height="20" border="0" style="background-image: url( ${staticContextPath}/images/buttons/btn_view_complete_service_order.gif);" />
					</a>
				</td> 
			</tr>
			<tr class="last">
			    <td class="last">&nbsp;</td>
			    <td class="last" colspan="6">
			    	<c:set var="soId" value="${buyer.id}" scope="request" /> 
					<c:set var="groupId" value="${buyer.parentGroupId}" scope="request" />
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
<div dojoType="dijit.Dialog" id="widgetActionSuccess${theTab}" title="Quick Action Response">
			<b>Action Completed Successfully</b><br>
			<div id="messageS${theTab}"></div>
		</div>
		<div dojoType="dijit.Dialog" id="widgetActionError${theTab}" title="Quick Action Response">
			<b>Action Failed please review</b><br>
			<div id="message${theTab}"></div>
		</div>
		<div dojoType="dijit.Dialog" id="loadingMsg${theTab}" title="Please Wait">
			We are refreshing your results.
		</div>
		
		
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/animatedcollapse.js"></script>
		<script language="JavaScript" type="text/javascript">
				loadDivs(${fn:length(soOrderList)});
		</script>
				<!--  Include JSP Fragment that loads the data for the Order Express Widget -->
				<c:set var="pageData" value="${soOrderList}" />
				<%@ include file="/jsp/public/common/loadDataForOrderExpressWidget.jspf" %>
						
		<script language="JavaScript" type="text/javascript">
				parent._commonSOMgr._clearPersistStateCache();
				var theActualCount = 0;
				<c:forEach var="buyer" items="${soOrderList}" varStatus="dtoIndex">
					parent._commonSOMgr.addToken(parent.newco.rthelper.createSOToken('${buyer.providersSentTo}','${buyer.providersConditionalAccept}','${buyer.providersDeclined}','${buyer.spendLimitTotal}',
						{routedCnt:${buyer.providersSentTo},acceptedCnt:${buyer.providersConditionalAccept},rejectedCnt:${buyer.providersDeclined},spendLimit:${buyer.spendLimitTotal}},'${buyer.id}',${dtoIndex.index}));
				</c:forEach>	
				theActualCount = parent.getSummaryTabCount('Posted');
				//alert ("the actual count : " +theActualCount);
				parent._commonSOMgr.addToken(new TabToken('Posted',theActualCount));
				//parent._commonSOMgr.addToken(new TabToken('Posted',theCount));
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
				
				
				function doStatusSubmit(status, subStatus){
					newco.jsutils.Uvd('status',status) ;
					newco.jsutils.Uvd('subStatus',subStatus) ;
					newco.jsutils.Uvd('resetSort','false') ;
					newco.jsutils.displayModal('loadingMsg${theTab}');
					jQuery('#commonGridHandler').submit();
				}
		
				function doPriceModelSubmit(priceModel){
					newco.jsutils.Uvd('priceModel',priceModel);
					newco.jsutils.Uvd('resetSort','false') ;
					newco.jsutils.displayModal('loadingMsg${theTab}');
					jQuery('#commonGridHandler').submit();
				}
		</script>
		
				<%-- ss: remove for now, it's not doing anything 
				<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 		<jsp:param name="PageName" value="SOM - ${theTab}"/>
			 	</jsp:include> --%>
			 	
		<script type="text/javascript">
			jQuery.noConflict();
			
				var theRole = '${role}';
				function doAction() {
					    newco.jsutils.displayModal('loadingMsg${theTab}');
						jQuery('#commonGridHandler').submit();
				}
				
				function showCommonMsg( theState, theMsg )
				{
					newco.jsutils.displayActionTileModal('${theTab}',theState,theMsg);	
				}
			</script>
	</body>
</html>
    			
