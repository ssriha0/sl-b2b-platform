<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="java.util.Calendar,java.text.SimpleDateFormat"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
Calendar calendar = Calendar.getInstance(request.getLocale());
SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />


<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="PowerBuyer.workflow"/>
	</jsp:include>	

	<div class="content">
		<h4>
			<div style="width:100%">
				<div style="width:50%;float:left">
				<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.grid.title" />
				</div>
				<div style="width:50%;float:left">
				Last refresh: 
				<%=formatter.format(calendar.getTime())%> &nbsp;
				  <a id="menuPowerBuyer" href="<s:url action='/MarketFrontend/pbController_execute.action' />" title="Refresh"><img src="${staticContextPath}/images/s_icons/arrow_refresh.png" alt="Refresh" width=16 height=16></a>
				</div>
				
				<div id="filterReference" style="width:100%">
				 Filter Reference:
				 
				<select id="refType" onchange="clearFilterValue();" name="refType">
						<option value="0">
							-- Clear Filter --
						</option>
					<c:if test="${isSlAdmin==true && isAdopted==false}">
						<c:if test="${searchByBuyerId==true}">
						  	<option value="2" selected="selected">
						</c:if>
					<c:if test="${searchByBuyerId==null||searchByBuyerId==false}">
							<option value="2">
					</c:if>
							Filter queues with specific buyer Id #
						</option>
					</c:if>
					<c:forEach var="refFields" items="${refFields}">
						<c:if test="${buyerRefTypeId==refFields.buyerRefTypeId && (searchByBuyerId == false || searchByBuyerId == null)}">
							<option value="${refFields.buyerRefTypeId}" selected="selected">
						</c:if>
						<c:if test="${buyerRefTypeId!=refFields.buyerRefTypeId||(buyerRefTypeId==2 && searchByBuyerId == true)}">
							<option value="${refFields.buyerRefTypeId}">
						</c:if>
							buyer #<c:out value="${refFields.buyerId}"></c:out>:<c:out value="${refFields.referenceType}"></c:out>
						</option>
					</c:forEach>	 
				</select>
					
           <!--   <s:select id="refType" onchange="javascript:newco.jsutils.clearFilterValue();"   name="refType" value="buyerRefTypeId"
                list="refFields" listKey="buyerRefTypeId" listValue="'buyer #' + buyerId + ': ' + referenceType"
                headerKey="0" headerValue="-- Clear Filter --"/>   --> 

				 Filter Value:
                 
				<input type="text" id="refVal" maxLength="20" name="refValue" value="${buyerRefValue}" />
				<a href="#" onclick="newco.jsutils.submitFilter()" alt="Submit">
					<img style='float: right;' src="${staticContextPath}/images/simple/button-filter.png" />
				</a>
				</div>
				
			</div>
		</h4>
		
		<p class="paddingBtm">
			<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.grid.desc" /> 
		</p>
		<c:if test="${PB_WF_MESSAGE != null}">
			<p class="errorMsg">
				<fmt:message bundle="${serviceliveCopyBundle}" key="${PB_WF_MESSAGE}" />
			</p>
			<%session.removeAttribute("PB_WF_MESSAGE");%>
		</c:if>

		<div class="grayTableContainer" style="width: 698px; height: 1000px;">
		<table cellpadding="0" cellspacing="0" class="globalTableLook">
			<tr>
				<th class="col1 first odd">
					<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.filter.name" />
				</th>
				<th class="col2 even">
					<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.filter.count1" />
				</th>
				<th class="col2 even">
					<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.filter.count2" />
				</th>
				<th class="col2 even">
					<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.filter.count3" />
				</th>
				<th class="col3 odd last">
					<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.filter.action" />
				</th>
			</tr>

			<c:forEach items="${filters}" var="filter">
			<tr>
				<td class="col1 first odd">
				<c:if test="${fn:length(filter.excBuyerList)>0}">
				<span class="wfmCallOutIcon"><img style='float: left;' src="${staticContextPath}/images/simple/blueBubble.png"/><span class="wfmCallOut" ><b>This queue excludes these buyer ids ${filter.excBuyerList}</b></span></span>
				</c:if>
				<c:if test="${fn:length(filter.excBuyerList)==0 && isSlAdmin==true && isAdopted==false}">
				<img style='float: left;' src="${staticContextPath}/images/simple/blankImage.png"/>
				</c:if>
								${filter.filterName}
				</td>
				<tags:security actionName="view_filtered_data">
					<c:set var="isPBAdmin" value="true" />
				</tags:security>
				<c:choose>
					<c:when test="${isPBAdmin}">
						<td class="col2 even">
							<a href="#" onclick="newco.jsutils.JumpToActionTabWithURL('Search','mainTabContainer', '/MarketFrontend/monitor/gridHolder.action?tab=Search&wfFlag=1&pbFilterId=${filter.filterId}&pbFilterName=${filter.filterName}&pbFilterOpt=u&refType=${buyerRefTypeId}&refVal=${buyerRefValue}&fromWFM=true')">
								${filter.count1}
							</a>	
						</td>
						<td class="col2 even">
							<a href="#" onclick="newco.jsutils.JumpToActionTabWithURL('Search','mainTabContainer', '/MarketFrontend/monitor/gridHolder.action?tab=Search&wfFlag=1&pbFilterId=${filter.filterId}&pbFilterName=${filter.filterName}&pbFilterOpt=a&refType=${buyerRefTypeId}&refVal=${buyerRefValue}&fromWFM=true')">
								${filter.count2}
							</a>	
						</td>
						<td class="col2 even">
							<a href="#" onclick="newco.jsutils.JumpToActionTabWithURL('Search','mainTabContainer', '/MarketFrontend/monitor/gridHolder.action?tab=Search&wfFlag=1&pbFilterId=${filter.filterId}&pbFilterName=${filter.filterName}&pbFilterOpt=c&refType=${buyerRefTypeId}&refVal=${buyerRefValue}&fromWFM=true')">
								${filter.count3}
							</a>	
						</td>
					</c:when>
					<c:otherwise>
						<td class="col2 even">
							${filter.count1}
						</td>
						<td class="col2 even">
							${filter.count2}
						</td>
						<td class="col2 even">
							${filter.count3}
						</td>
					</c:otherwise>
				</c:choose>
				<td class="col3 odd last">
				<c:choose>
					<c:when test="${filter.count2 > 0 && filter.filterId != 1 && filter.filterId != 73}">
						<a href="pbWorkflowTab_claimNext.action?filterId=${filter.filterId}&refType=${buyerRefTypeId}&refVal=${buyerRefValue}" target="_top">  
							<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.filter.claim.next" />
						</a>
					</c:when>
					<c:otherwise>
						<fmt:message bundle="${serviceliveCopyBundle}" key="pb.workflow.filter.claim.next" />
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
			</c:forEach>
			</table>
		</div>
	</div>
