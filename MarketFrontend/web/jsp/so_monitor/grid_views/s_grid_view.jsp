<%@ page import="java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="viewOrderPricing" scope="session" value="<%=session.getAttribute("viewOrderPricing")%>"></c:set>	
<c:set var="theTab" value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />
<c:set var="SOcount" value="<%=request.getAttribute("searchCount")%>" />
<c:set var="fromFilterSave" value="<%=request.getAttribute("FromFilterSave")%>" />
<c:set var="wfFlag" value="<%=request.getAttribute("wfFlag") %>"/>		
<%
String sortColumnSession = (String)session.getAttribute("sortColumnName" + request.getAttribute("tab"));
String sortOrderSession = (String)session.getAttribute("sortOrder" + request.getAttribute("tab"));
String criteria = (String)session.getAttribute("selectedSearchForSession");
String isPrimaryInd = (String)session.getAttribute("isPrimaryInd").toString();
Boolean canManageSO = (Boolean)session.getAttribute("canManageSO");
Boolean dispatchInd = (Boolean)session.getAttribute("dispatchInd");
Boolean incSpendLimit = (Boolean)session.getAttribute("incSpendLimit");
Boolean taskLevelPriceInd = (Boolean)session.getAttribute("taskLevelPriceInd");
 %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
	<head>
		
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
			
			
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

			<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script><%-- ss: Re-enabled prototype - it's referenced in serviceLiveDojoBase.js --%>
			<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false ,parseOnLoad: true"></script>
			<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/serviceLiveDojoBase.js" djConfig="isDebug: false ,parseOnLoad: true"></script>
			
			<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
	<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/animatedcollapse.js"></script>

	<script type="text/javascript">	
		jQuery.noConflict();
		jQuery(document).ready(function($){
		
			var pendingReschedule='${pendingReschedule}';
			if(pendingReschedule == 'pendingReschedule')
			{
			
				$('#reschdRequest', parent.document).trigger("click");
				$('#pendingReschedule', parent.document).trigger("click");
				$('#pending', parent.document).trigger("click");
				$('#SearchSom', parent.document).trigger("click");
			
			}
			
			var count = '${SOcount}';
			
			var fromFilterSave = '${fromFilterSave}';
			
			var criteria = decodeURI(document.getElementById('selectedSearchCriteria').value);
			if(criteria == null | criteria == 'null' ){
				// do nothing 
			}else{		
				//parent.document.getElementById('searchSelections').innerHTML = criteria;
				parent.repopulateSearchCriteria(criteria);
				
			}
			if(count > 0){				
				//this.parent.document.getElementById('Search').title = 'Search('+ count +')');
			}			
			if(fromFilterSave == '1'){				
				parent.location.href='serviceOrderMonitor.action?displayTab=Search';
			}				
			//jQuery('#Search').title = 'Search('+count+')')
			
			var pendingCancelInd= parent.document.getElementById('pendingCancelInd').value;
			var cancelInd = parent.document.getElementById('cancelInd').value;
			parent.document.getElementById('cancelMessage').innerHTML="";
			
			if(pendingCancelInd=='success')
			{
			
			   var soId=parent.document.getElementById('soID').value;			
               setTimeout(function() {jQuery("."+'dd_'+soId).click();},1000);               
               parent.document.getElementById('pendingCancelInd').value='none';               
               if(cancelInd == 'agreedToCancel'){
               		parent.document.getElementById('cancelMessage').innerHTML="Your request was successfully submitted.";
				}
				else{
					parent.document.getElementById('cancelMessage').innerHTML ="";					
				}               
			}			
			   parent.document.getElementById('pendingCancelInd').value='none';
			   parent.document.getElementById('cancelInd').value= 'none';
			
		});
		</script>
		<script type="text/javascript">		
		dojo.require("newco.jsutils");
		parent.dojo.require("dijit.Dialog");
		parent.dojo.require("dojo.parser");
	</script>
		<script type="text/javascript">
		var theRole = '${role}';

		function doAction() {
				
				document.getElementById('searchHandler').submit();
		}
		
		function doStatusSubmit(status, subStatus){
			doStatusSubmit(status, subStatus, null);
		}
		
		function doStatusSubmit(status, subStatus, filterId){
			newco.jsutils.Uvd('status',status) ;
			newco.jsutils.Uvd('subStatus',subStatus) ;
			newco.jsutils.displayModal('loadingMsg');
			
			if (filterId != null && filterId!="" ) {
				doc = document.getElementById('searchHandler')
				doc.action="/MarketFrontend/monitor/PBWorkflowSearch.action?pbFilterId=" + filterId + "&fromWFM=${fromWFM}";
			}
			jQuery('#searchHandler').submit();
		}
		function setIncreaseSpendLimit(data,wfFlag){
		var soId = data.soId;
		parent.document.getElementById('IncSLSOid').value=soId;
		parent.document.getElementById('wfFlag').value=wfFlag;
		}

		
function showResourceInRejectWidjet(resourceData,adminInd,groupInd,groupId,tab,role,priceModel){

	if(role==1){
		
			var resource_data = "<input type='hidden' id='groupID' value='"+groupId+"'/>";
			resource_data =resource_data+ "<input type='hidden' id='groupInd' value='"+groupInd+"'/>";
			resource_data = resource_data+ "<input type='hidden' id='priceModel' value='"+priceModel+"'/>";
			var errorObj=parent.document.getElementById("rejectServiceOrderResponseMessage"+tab);
			jQuery(errorObj).html("");
			var obj=parent.document.getElementById("rejectResources"+tab);
			jQuery(obj).html("");
		if(priceModel!='ZERO_PRICE_BID'){		
			
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
	}
}
	
<c:set var="wfFlag" value="<%=request.getAttribute("wfFlag") %>"/>	
<c:set var="wfmSodFlag" value="<%=request.getAttribute("wfmSodFlag") %>"/>	
<c:set var="soId" value="<%=request.getAttribute("soId") %>"/>		
<c:set var="pbFilterName" value="<%=request.getAttribute("pbFilterName") %>"/>	
<c:set var="statusCount" value="<%=new Integer(((ArrayList) request
							.getAttribute("serviceOrderStatusVOList")).size())%>"/> 
<c:set var="status" value="<%=request.getAttribute("serviceOrderStatusVOList")%>"/> 

	if ('${pbFilterId}' == ""){
		newco.jsutils.hideDivInParent('search_grid_pb_filter_name');
	}
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
	
		function clearMessage(){
		//This function is included to clear cancellation success message on selecting other SO.
		 parent.document.getElementById('cancelInd').value= 'none';
		 parent.document.getElementById('cancelMessage').innerHTML ="";	
		}
	</script>
	
	</head>

	<body class="tundra noBg">
		<s:form action="soSearch" id="searchHandler">
		    <input type="hidden" name="selectedSearchCriteria" id="selectedSearchCriteria" value="<%= criteria %>"/>
		    <input type="hidden" name="fromWFM" id="fromWFM" value="${fromWFM}" /> 		
			<input type="hidden" name="pageIndicator" id="pageIndicator" />
			<input type="hidden" name="searchType" id="searchType" value="${searchType}" />
			<input type="hidden" name="searchValue" id="searchValue" value="${searchValue}" />
			<input name="tab" id="tab" value="<%=request.getAttribute("tab")%>"
				type="hidden" />
			<input type="hidden" name="status" id="status" value=""/>
			<input type="hidden" name="subStatus" id="subStatus" value="" />
			<input type="hidden" name="sortColumnName" id="sortColumnName" value="<%= sortColumnSession %>" />
			<input type="hidden" name="sortOrder" id="sortOrder" value="<%=sortOrderSession %>" />
						
			<input type="hidden" name="stateVals" id="stateVals" value="" />
			<input type="hidden" name="skillVals" id="skillVals" value="" />
			<input type="hidden" name="marketVals" id="marketVals" value="" />
			<input type="hidden" name="statusVals" id="statusVals" value="" />			
			<input type="hidden" name="acceptanceVals" id="acceptanceVals" value="" />
			<input type="hidden" name="pricingVals" id="pricingVals" value="" />
			<input type="hidden" name="assignmetVals" id="assignmetVals" value="" />
			<input type="hidden" name="postingVals" id="postingVals" value="" />	
			
			<input type="hidden" name="pendingRescheduleVals" id="pendingRescheduleVals" value="" />
			<input type="hidden" name="closureMethodVals" id="closureMethodVals" value="" />			
					
			<input type="hidden" name="custRefVals" id="custRefVals" value="" />
			<input type="hidden" name="checkNumberVals" id="checkNumberVals" value="" />
			<input type="hidden" name="customerNameVals" id="customerNameVals" value="" />
			<input type="hidden" name="phoneVals" id="phoneVals" value="" />
			<input type="hidden" name="providerFirmIdVals" id="providerFirmIdVals" value="" />
			<input type="hidden" name="serviceOrderIdVals" id="serviceOrderIdVals" value="" />
			<input type="hidden" name="serviceProIdVals" id="serviceProIdVals" value="" />
			<input type="hidden" name="serviceProNameVals" id="serviceProNameVals" value="" />
			<input type="hidden" name="zipCodeVals" id="zipCodeVals" value="" />
			<input type="hidden" name="startDate" id="startDate" value="" />
			<input type="hidden" name="endDate" id="endDate" value="" />
			<input type="hidden" name="mainCatId" id="mainCatId" value="" />
			<input type="hidden" name="catAndSubCatId" id="catAndSubCatId" value="" />
			<input type="hidden" name="searchFilterName" id="searchFilterName" value=""/>
			<input type="hidden" name="selectedFilterName" id="selectedFilterName" value=""/>
			<input type="hidden" name="autocloseRuleVals" id="autocloseRuleVals" value="" />
			<input type="hidden" name="count" id="count" value="<%=request.getAttribute("searchCount")%>" /> 
			<c:if test="${wfFlag != null}">
				<input type="hidden" name="wfFlag" id="wfFlag" value="<%=request.getAttribute("wfFlag") %>" />
			</c:if>
			<c:if test="${wfmSodFlag!=null}">
				<input type="hidden" name="wfmSodFlag" id="wfmSodFlag" value="<%=request.getAttribute("wfmSodFlag") %>" />
				<input type="hidden" name="soId" id="soId" value="<%=request.getAttribute("soId") %>" />
			</c:if>
			<input type="hidden" name="isSearchClicked" id="isSearchClicked" value="0" />
		</s:form>
		<c:set var="grpId" value="GSO_NONE"/>
		<c:set var="grpStartInd" value="false"/>
		<c:set var="groupInd" value="false" scope="request" />
		<div id="resultSet">
<div style="color: blue">
 <p>${msg}</p>
</div>
		<c:if test="${not empty searchCount && searchCount > 0  }">
			<div id="searchCount">
				<c:choose><c:when test="${not empty searchCount && searchCount <= 5000}">
					<b>${searchCount} Service Order(s) found.</b>
		</c:when>
				<c:otherwise>
					<div style="color: red">
					 	5000+ Service Orders found. Please narrow down your search.
					</div>
				</c:otherwise></c:choose>
			</div>
		</c:if>
		<c:if test="${not empty searchCount && searchCount == 0  }">
			<fmt:message bundle="${serviceliveCopyBundle}"
							key="no_records_found" />
		</c:if>		
		<ul class="browser filetree treeview-ico">
			<c:forEach var="dto" items="${soSearchList}" varStatus="dtoIndex">
					<c:choose><c:when test="${dto.status > 110 && dto.assignmentType=='FIRM'}">
						<c:set value="true" var="unassigned"></c:set>
		  			</c:when>
		   			<c:otherwise>
		   				<c:set value="false" var="unassigned"></c:set>
		   			</c:otherwise></c:choose>
				<c:choose><c:when test="${not empty dto.errorMsg}"><tr><td>${dto.errorMsg}</td></tr>
				</c:when>
				<c:otherwise>
				  <c:choose><c:when test="${not empty dto.parentGroupId}">
				   <c:if test="${dto.parentGroupId != grpId}">
				    <c:set var="grpId" value="${dto.parentGroupId}" />
				    <tags:monitorHelper serviceOrderDTO="${dto}" actionName="StripResourceIdFromGroupId"/>
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
							<span class="folder" onclick="newco.jsutils.displayRightSideMenu('${dto.statusString}', '${dto.providerResponseId}', true, '${dto.parentGroupId}', '${wfFlag==1}', '${incSpendLimit}', '${dto.taskLevelPriceInd}',false,'${dto.nonFundedInd}'),
												parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${dto.id}','${dto.routedResourceId}', true, '${dto.parentGroupId}'),
												parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], true, groupInfoMap${theTab}['${dto.parentGroupId}'],'${isPrimaryInd}', '${dispatchInd}'),setIncreaseSpendLimit(_dataGridList${theTab}[${dtoIndex.count}],'${wfFlag}')">
								<table border="0" width="98%" cellpadding="0" cellspacing="0">
									<tr>
										<td class="somOgId"><strong>${dto.parentGroupId}</strong></td>
										<td class="somOgTitle"><span>${dto.parentGroupTitle}</span><br/>
											<strong><fmt:message bundle="${serviceliveCopyBundle}" key="widget.label.end.customer" /></strong> ${dto.endCustomerWidget}</td>
										<td class="somOgTime">${dto.serviceOrderDateString}</td>
										<td class="somOgLocation">${dto.cityStateZip}</td>
									</tr>
								</table>
							</span>
							<!-- start service orders in group -->
							<ul>
								<li><table width="100%">
									<tr><td width="10">&nbsp;</td><td>
										<c:set var="groupId" value="${dto.parentGroupId}" scope="request" />
										<c:set var="groupInd" value="true" scope="request" />
										<c:set var="rowsostatus" value="${dto.status}" scope="request" />
										<c:set var="rowRoutedResourceId" value="${(role == 1 and dto.status == 110) ? dto.routedResourceId : SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}" scope="request" />
										<c:if test="${fromWFM != 'true'}">
											<jsp:include flush="true" page="/jsp/so_monitor/soMonitorQuickLinks.jsp"/>
										</c:if> 
										<c:if test="${fromWFM == 'true'}">
											<jsp:include flush="true" page="/jsp/so_monitor/wfmQuickLinks.jsp"/>
										</c:if> 
										<c:remove var="rowRoutedResourceId" scope="request" />
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
					<span class='file'>
					<table class="grid-table" cellpadding="0" cellspacing="0"  style="table-layout: fixed; width: 100%" >
					<c:choose> 
					  <c:when test="${not empty dto.parentGroupId}">
					    <tr
						onclick="clearMessage(),collapseExpand(${dtoIndex.count -1},'${staticContextPath}'),
								newco.jsutils.displayRightSideMenu('${dto.status}', '${dto.providerResponseId}', false, '${dto.parentGroupId}', false, '${incSpendLimit}', '${dto.taskLevelPriceInd}',false,'${dto.nonFundedInd}'),
								parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${dto.id}','${dto.routedResourceId}', false, ''),
								parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], false, null, '${isPrimaryInd}','${dispatchInd}'),setIncreaseSpendLimit(_dataGridList${theTab}[${dtoIndex.count}],'${theTab}')"
						id="summaryRow_${dtoIndex.count}">
					    
					    </c:when>
	                     <c:otherwise>
	                        <tr
						onclick="clearMessage(),collapseExpand(${dtoIndex.count -1},'${staticContextPath}'),
								newco.jsutils.displayRightSideMenu('${dto.status}', '${dto.providerResponseId}', false,'${dto.parentGroupId}', false, '${incSpendLimit}', '${dto.taskLevelPriceInd}','${viewOrderPricing}','${dto.nonFundedInd}'), 
								parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${dto.id}','${dto.routedResourceId}'),
								parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}],false,null,'${isPrimaryInd}','${dispatchInd}','${viewOrderPricing}','${unassigned}'),
								parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}]),setIncreaseSpendLimit(_dataGridList${theTab}[${dtoIndex.count}],'${theTab}')"
						id="summaryRow_${dtoIndex.count}" class="dd_${dto.id}"   >
					
						
						</c:otherwise>  
										
					</c:choose>
					
						<td class="sl_grid_col_1">
							<img id="img_${dtoIndex.count}" name="img_${dtoIndex.count}"
								src="${staticContextPath}/images/grid/right-arrow.gif" alt="Expand" />
								  <c:if test="${dto.claimedByResource != 'N/A' && wfFlag ==1}">
					                 <img id="imgPBClaim_${dtoIndex.count}" name="imgPBClaim_${dtoIndex.count}"
								     src="${staticContextPath}/images/grid/claim.gif" alt="Expand" />
								   </c:if>
								
								
						</td>
						<td class="sl_grid_col_2">
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="workflow.state.${role}.${dto.status}" />
							<c:set var="rowsostatus" value="${dto.status}" 
								scope="request" />
						</td>
						<td class="sl_grid_col_3">
							${dto.id}
							<br />
							<c:if test="${not empty dto.subStatus}">
                                      <strong><fmt:message bundle="${serviceliveCopyBundle}" key="substatus" /> </strong>
                                      <br />
                                      <fmt:message bundle="${serviceliveCopyBundle}"	key="workflow.substate.${dto.subStatus}" />
                            </c:if>
							
							
						</td>
						<td class="sl_grid_col_4" style="word-wrap: break-word;word-break: break-all;">
							<a style="color:#00A0D2;cursor:pointer;">${dto.title}</a>
						<br />
						  <c:if test="${not empty dto.endCustomerWidget && dto.endCustomerWidget!='N/A'}">
                                      <strong><fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.end.customer" />
                                       </strong>
                                       <br />
                                       ${dto.endCustomerWidget }
                           </c:if>
							
						   	
						</td>
						<td class="sl_grid_col_5">
							${dto.serviceOrderDateString}
						</td>
						<td class="sl_grid_col_6">
							<!--  <a href="#">-->${dto.cityStateZip}
						</td>
					</tr>
					<tr class="selected">
						<td colspan="6" id="expandRow">
							<div id="div_${dtoIndex.count}">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td class="sl_grid_col_1">
											&nbsp;
										</td>
										<td colspan="2" class="sl_grid_col_2_sub">	
											<c:choose><c:when test="${role==1 && dto.status == 110 }">
												<c:choose><c:when test="${(canManageSO == true && isPrimaryInd == false)}">
													<strong>Resource:</strong>
									            	${dto.routedResourceName}
									            </c:when>
									            <c:otherwise>
									         	<c:choose><c:when test="${(dto.priceModel =='ZERO_PRICE_BID')}">
													<strong>Resource:</strong>
									            	${dto.routedResourceName}
									            </c:when>
									            <c:otherwise>
									         		Multiple Providers
									         	</c:otherwise></c:choose>
									         	</c:otherwise></c:choose>
									        </c:when>
									        <c:otherwise>	
											&nbsp;	
											</c:otherwise></c:choose>							
										</td>
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
										   
							       <c:choose>	
							         	  
										<c:when test="${wfFlag == 1}">  
										      <td colspan="2" align="left">	
										      <c:if test="${dto.claimable}">
										      <a
														href="pbSearchTab_claimNext.action?queueId=${pbFilterId}&soId=${dto.id}&groupId=${dto.parentGroupId}&cameFromWorkflowMonitor=cameFromWorkflowMonitor"
														target="_top"> <img class="btn20Bevel"
															src="${staticContextPath}/images/common/spacer.gif"
															alt="Edit Order" width="125" height="20"
															style="background-image: url( ${staticContextPath}/images/grid/claim_service_order.gif);"
															onclick="javascript: void(0)" /> </a>
										     
										       </c:if>
										       </td>
										  </c:when>
                                          <c:otherwise>
                                          <td colspan="2" align="left">
											<c:choose>
												<c:when test="${dto.status == 100}">
													<a
														href="soWizardController.action?soId=${dto.id}&action=edit&displayTab=${theTab}"
														target="_top"> <img class="btn20Bevel"
															src="${staticContextPath}/images/common/spacer.gif"
															alt="Edit Order" width="115" height="20"
															style="background-image: url( ${staticContextPath}/images/btn/editServiceOrder.gif);"
															onclick="javascript: void(0)" /> </a>
												</c:when>
												<c:otherwise>
												<c:choose><c:when test="${role==1 && dto.status == 110 }">
													<a href="soDetailsController.action?soId=${dto.id}&resId=${dto.routedResourceId}&displayTab=${theTab}"
														target="_top"> <img class="btn20Bevel"
															src="${staticContextPath}/images/common/spacer.gif"
															alt="View Order" width="164" height="20"
															style="background-image: url( ${staticContextPath}/images/buttons/btn_view_complete_service_order.gif);" />
															</a>
															</c:when>
															<c:otherwise>
															<a href="soDetailsController.action?soId=${dto.id}&resId=${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}&displayTab=${theTab}"
														target="_top"> <img class="btn20Bevel"
															src="${staticContextPath}/images/common/spacer.gif"
															alt="View Order" width="164" height="20"
															style="background-image: url( ${staticContextPath}/images/buttons/btn_view_complete_service_order.gif);" />
															</a>
															</c:otherwise></c:choose>
												</c:otherwise>
											</c:choose>
											<c:set var="rowRoutedResourceId" value="${(role == 1 and dto.status == 110) ? dto.routedResourceId : SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}" scope="request" /> 
										</td>
										</c:otherwise>	
								</c:choose>  		
									</tr>
									<tr>
										<td class="sl_grid_col_1 last">
											&nbsp;
										</td>
										<td colspan="5" class="last">
											<br />
											<c:set var="soId" value="${dto.id}" scope="request" />
											<c:set var="groupId" value="${dto.parentGroupId}" scope="request" />
											<c:if test="${fromWFM != 'true'}">
												<jsp:include flush="true" page="/jsp/so_monitor/soMonitorQuickLinks.jsp"/>
											</c:if> 
											<c:if test="${fromWFM == 'true'}">
												<jsp:include flush="true" page="/jsp/so_monitor/wfmQuickLinks.jsp"/>
											</c:if> 
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
				</c:otherwise></c:choose>
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
				<c:when test="${empty soSearchList}">
					<c:if test="${not empty searchString}">
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="no_records_found" />
					</c:if>
				</c:when>
				<c:otherwise>
					<jsp:include page="/jsp/paging/pagingsupport.jsp"/>
				</c:otherwise>
			</c:choose>
		</table>


		<script language="JavaScript" type="text/javascript">
			loadDivs(${fn:length(soSearchList)});
			newco.jsutils.clearRightSideMenus();
		</script>	
		
		<!--  Include JSP Fragment that loads the data for the Order Express Widget -->
		<c:set var="pageData" value="${soSearchList}" />
		<%@ include file="/jsp/public/common/loadDataForOrderExpressWidget.jspf" %>

		<div dojoType="dijit.Dialog" id="widgetProcessing${theTab}">
			<b><fmt:message bundle="${serviceliveCopyBundle}"
					key="processing_request" />
				<div id="messageReq${theTab}"></div> </b>
		</div>
		<div dojoType="dijit.Dialog" id="widgetActionSuccess${theTab}"
			title="Quick Action Response">
			<b><fmt:message bundle="${serviceliveCopyBundle}"
					key="act_completed_success" /> </b>
			<br>
			<div id="messageS${theTab}"></div>
		</div>
		<div dojoType="dijit.Dialog" id="widgetActionError${theTab}"
			title="Quick Action Response">
			<b><fmt:message bundle="${serviceliveCopyBundle}"
					key="act_failed" /> </b>
			<br>
			<div id="message${theTab}"></div>
		</div>
		<div dojoType="dijit.Dialog" id="loadingMsg${theTab}"
			title="Please Wait">
			<fmt:message bundle="${serviceliveCopyBundle}" key="loading_data" />
		</div>
	</div>
	
	
	
	</body>
</html>
										
