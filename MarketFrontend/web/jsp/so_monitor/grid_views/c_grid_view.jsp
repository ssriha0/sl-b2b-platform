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
<c:set var="roleId" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />
<%
String sortColumnSession = (String)session.getAttribute("sortColumnName" + request.getAttribute("tab"));
String sortOrderSession = (String)session.getAttribute("sortOrder" + request.getAttribute("tab"));
String isPrimaryInd = (String)session.getAttribute("isPrimaryInd").toString();

Boolean incSpendLimit = (Boolean)session.getAttribute("incSpendLimit");
Boolean taskLevelPriceInd = (Boolean)session.getAttribute("taskLevelPriceInd");
 %>
		
		
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta http-equiv="content-type"	content="text/html; charset=ISO-8859-1">
			
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

		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
						djConfig="isDebug: false ,parseOnLoad: true"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/serviceLiveDojoBase.js"
						djConfig="isDebug: false ,parseOnLoad: true"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
	 	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
 		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/animatedcollapse.js"></script>
	
</head>
	<body class="tundra noBg">
	<s:form action="gridLoader" id="commonGridHandler">
		<input name="tab" id="tab" value="<%=request.getAttribute("tab")%>" type="hidden"/>
		<input type="hidden" name="status" id="status" />
		<input type="hidden" name="subStatus" id="subStatus" />	
		<input type="hidden" name="serviceProName" id="serviceProName" />
	    <input type="hidden" name="marketName" id="marketName" />
		<input type="hidden" name="sortColumnName" id="sortColumnName" value="<%= sortColumnSession %>" />
		<input type="hidden" name="sortOrder" id="sortOrder" value="<%=sortOrderSession %>" />	
		<input type="hidden" name="resetSort" id="resetSort" value="false" />
	</s:form>
	
		<c:set var="grpId" value="GSO_NONE"/>
		<c:set var="isPrimaryInd" value="<%=isPrimaryInd%>"/>
		<c:set var="grpStartInd" value="false"/>
		<c:set var="groupInd" value="false" scope="request" />
		<ul class="browser filetree treeview-ico">
		<c:forEach var="dto" items="${soOrderList}" varStatus="dtoIndex">
			<c:choose><c:when test="${dto.status > 110 && dto.assignmentType=='FIRM'}">
				<c:set value="true" var="unassigned"></c:set>
  			</c:when>
   			<c:otherwise>
   				<c:set value="false" var="unassigned"></c:set>
   			</c:otherwise></c:choose>
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
			
<div style="color: blue">
 <p>${msg}</p>
</div>

		    <c:set var="grpStartInd" value="true"/>
				<!-- start order group -->
				<li class="closed soGroup">
					<span class="folder" onclick="newco.jsutils.displayRightSideMenu('${dto.statusString}', '${dto.providerResponseId}', true, '${dto.parentGroupId}', false,'${incSpendLimit}', '${dto.taskLevelPriceInd}',false,'${dto.nonFundedInd}'),
										parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${dto.id}','${dto.routedResourceId}', true, '${dto.parentGroupId}'),
										parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], true, groupInfoMap${theTab}['${dto.parentGroupId}']),setIncreaseSpendLimit(_dataGridList${theTab}[${dtoIndex.count}],'${theTab}')">
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="somOgId"><strong>${dto.parentGroupId}</strong>
	                                <c:if test="${isPrimaryInd}">
		                            <br/>
                                    Service Pro Name:<strong>${dto.providerName}</strong><br/>
                                    ID# <strong>${dto.resourceId}</strong>
	                                </c:if>
                                </td>
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
							<tr><td colspan="2" align="right">
								<c:choose><c:when test="${dto.statusString == 'Draft'}">
									<%-- 
									<a href="soWizardController.action?soId=${dto.soId}&groupOrderId=${dto.parentGroupId}&action=edit&tab=draft" target="_top">
										<img class="btn20Bevel" src="${staticContextPath}/images/common/spacer.gif" alt="Edit Grouped Order" width="155" height="20" 
													style="background-image: url( ${staticContextPath}/images/btn/edit_grouped_so.png);" onclick="javascript: void(0)" />
									</a>
									--%>
								</c:when>
								<c:otherwise>
									<a href="soDetailsController.action?groupId=${dto.parentGroupId}&displayTab=${theTab}" target="_top">
										<img class="btn20Bevel" src="${staticContextPath}/images/common/spacer.gif" alt="View Order" width="210" height="20" 
													style="background-image: url( ${staticContextPath}/images/btn/view_complete_grouped_so.png);" />
									</a>
								</c:otherwise></c:choose>
							</td></tr>
							<tr><td width="10">&nbsp;</td><td>
								<c:set var="groupId" value="${dto.parentGroupId}" scope="request" />
								<c:set var="groupInd" value="true" scope="request" />
								<c:set var="rowsostatus" value="${dto.status}" scope="request" />
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
							<table border="0" width="100%" cellpadding="0" cellspacing="0" style="word-wrap:break-word;table-layout: fixed">
								<tr onclick="clearMessage(),collapseExpand(${dtoIndex.count -1},'${staticContextPath}'),newco.jsutils.displayRightSideMenu('${dto.statusString}', '${dto.providerResponseId}', false, '${dto.parentGroupId}', false,'${incSpendLimit}', '${dto.taskLevelPriceInd}',false,'${dto.nonFundedInd}'), 
												parent.newco.jsutils.setRowStateInfo('${dtoIndex.count}','${dto.id}','${dto.routedResourceId}', false, ''),
												parent.newco.jsutils.updateOrderExpress(_dataGridList${theTab}[${dtoIndex.count}], false, null,null,null,'${viewOrderPricing}','${unassigned}'),
												parent.newco.jsutils.retrieveDocuments(_dataGridList${theTab}[${dtoIndex.count}]),setIncreaseSpendLimit(_dataGridList${theTab}[${dtoIndex.count}],'${theTab}')"
									id="summaryRow_${dtoIndex.count}" class="dd_${dto.id}" >
												
									<td class="sl_grid_col_1">
											<img id="img_${dtoIndex.count}" name="img_${dtoIndex.count}"
											src="${staticContextPath}/images/grid/right-arrow.gif" alt="Expand"/>
										
									</td>
									<td class="sl_grid_col_2">
									<fmt:message bundle="${serviceliveCopyBundle}"
													key="workflow.state.${roleId}.${dto.status}" />
											<c:set var="rowsostatus" value="${dto.status}" scope="request" />
									</td>
									<td class="sl_grid_col_3">
										<span style="white-space:nowrap">${dto.id}</span>
										<br/>
										<c:if test="${isPrimaryInd}">
											<br />
											<c:choose><c:when test="${unassigned =='true'}">
				                                        		Service Provider: <strong>Unassigned
												</strong>
											</c:when>
											<c:otherwise>
											Service Pro Name:<strong>
											${dto.providerName}</strong>
												<br />
											ID# <strong> ${dto.resourceId} </strong>
											</c:otherwise></c:choose>
											<br />
										</c:if> 
						<c:if test="${not empty dto.subStatus}">
						                	<strong><fmt:message bundle="${serviceliveCopyBundle}" key="substatus" /> </strong>
						                    <br />
						                    <fmt:message bundle="${serviceliveCopyBundle}"	key="workflow.substate.${dto.subStatus}" />
					                   </c:if>
									</td>
									<td class="sl_grid_col_4">
										<span class="grid_title_mo1" onMouseOver="this.className='grid_title_mo2';" onMouseOut="this.className='grid_title_mo1';" >${dto.title}</span>
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
										<div id="div_${dtoIndex.count}" style="visibility: hidden">
											<table border="0" cellpadding="0" cellspacing="0" width="100%">
														<tr>
															<td class="sl_grid_col_1">
																&nbsp;
															</td>
															<td colspan="2" class="sl_grid_col_2_sub">
																&nbsp;
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
															<td colspan="2" align="left">
																<c:choose><c:when test="${dto.statusString == 'Draft'}">
																	<!-- TODO ACTION -->
																	<a href="soWizardController.action?soId=${dto.id}&action=edit&tab=draft&displayTab=${theTab}" target="_top">
																		<img class="btn20Bevel" src="${staticContextPath}/images/common/spacer.gif" alt="Edit Order" width="115" height="20" 
																					style="background-image: url( ${staticContextPath}/images/btn/editServiceOrder.gif);" onclick="javascript: void(0)" />
																	</a>
																</c:when>
																<c:otherwise>
																	<a href="soDetailsController.action?soId=${dto.id}&displayTab=${theTab}" target="_top">
																		<img class="btn20Bevel" src="${staticContextPath}/images/common/spacer.gif" alt="View Order" width="164" height="20" 
																					style="background-image: url( ${staticContextPath}/images/buttons/btn_view_complete_service_order.gif);" />
																	</a>
																</c:otherwise></c:choose>
															</td>
														</tr>
														<tr>
															<td class="sl_grid_col_1 last">
																&nbsp;
															</td>
															<td colspan="5" class="last">
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
	
		</table>
	
<script language="JavaScript" type="text/javascript">
	loadDivs(${fn:length(soOrderList)});
	</script>
		<!--  Include JSP Fragment that loads the data for the Order Express Widget -->
		<c:set var="pageData" value="${soOrderList}" />
		<%@ include file="/jsp/public/common/loadDataForOrderExpressWidget.jspf" %>

		
		<div dojoType="dijit.Dialog" id="widgetProcessing${theTab}">
			<b>Processing Request
			<div id="messageReq${theTab}"></div></b>
		</div>
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
		
		<%-- ss: removed omniture include, it's not doing anything the way it's coded
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	 		<jsp:param name="PageName" value="SOM - ${theTab}"/>
	 	</jsp:include> --%>
	 	
		<script type="text/javascript">		
		jQuery.noConflict();
		jQuery(document).ready(function($){	
			
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
		var theRole = '${role}';
		function doAction() {
			newco.jsutils.displayModal('loadingMsg${theTab}');
			jQuery('#commonGridHandler').submit();
		}
		
		function doStatusSubmit(status, subStatus,serviceProName,marketName){
			newco.jsutils.Uvd('status',status) ;
			newco.jsutils.Uvd('subStatus',subStatus) ;
			newco.jsutils.Uvd('serviceProName',serviceProName) ;
            newco.jsutils.Uvd('marketName',marketName) ;
			newco.jsutils.displayModal('loadingMsg${theTab}');
			newco.jsutils.Uvd('resetSort','false') ;
			jQuery('#commonGridHandler').submit();
		}
		
		function showCommonMsg( theState, theMsg )
		{
				newco.jsutils.displayActionTileModal('${theTab}',theState,theMsg);	
		}
		

		function setIncreaseSpendLimit(data,tab){
		var soId = data.soId;
		parent.document.getElementById('IncSLSOid').value=soId;
		parent.document.getElementById('tab').value=tab;
		}
	
		parent._commonSOMgr.startService();
		
		function clearMessage(){
		//This function is included to clear cancellation success message on selecting other SO.
		 parent.document.getElementById('cancelInd').value= 'none';
		 parent.document.getElementById('cancelMessage').innerHTML ="";	
		}
		
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
     
     //Reset the status and substatus
     parent.p_topFilterList=topFilterList;
	 parent.p_filterList=filterList;
	 //Resetting the status and sub-status list only on the tab change
     /*if(parent.wasOnTab != '${tab}'){
		parent.p_topFilterList=topFilterList;
		parent.p_filterList=filterList;		     
     } */ 
     	
	//Updating with the new tab
	parent.wasOnTab = '${tab}';

	</script>
	</body>
</html>
											
