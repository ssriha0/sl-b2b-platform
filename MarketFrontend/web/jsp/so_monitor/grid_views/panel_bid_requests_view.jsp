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
<%
String sortColumnSession = (String)session.getAttribute("sortColumnName" + request.getAttribute("tab"));
String sortOrderSession = (String)session.getAttribute("sortOrder" + request.getAttribute("tab"));
String isPrimaryInd = (String)session.getAttribute("isPrimaryInd").toString();
Boolean canManageSO = (Boolean)session.getAttribute("canManageSO");
Boolean isSLAdmin = (Boolean)session.getAttribute("IS_ADMIN");
 %>


<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1"/>
<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false ,parseOnLoad: true"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/serviceLiveDojoBase.js" djConfig="isDebug: false ,parseOnLoad: true"></script>

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<style type="text/css">
#bidReqResultsTable {width:100%;}
#bidReqResultsTable td, #bidReqResultsTable th {padding:3px 0 0 3px; border-bottom: 1px solid #ccc;}

.FLOAT_LEFT {float:left;}
.FLOAT_RIGHT {float:right;}

.col1 {width:16%;}
.col2 {width:22%;}
.col3 {width:17%;}
.col4 {width:17%;}
.col5 {width:12%;}
.col6 {width:16%;}
#bidReqResultsTable th {height:0px; border:0; padding:0; margin:0;}
.headerSortUp{background: url(/ServiceLiveWebUtil/images/grid/arrow-up-white.gif) no-repeat 50% 0px;}
.headerSortDown{background: url(/ServiceLiveWebUtil/images/grid/arrow-down-white.gif) no-repeat 50% 0px;}
input.action {width:75px; border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px; background:transparent url(${staticContextPath}/images/common/button-action-bg.png) repeat scroll 0 0;
	border:2px solid #ccc; color:#222; cursor:pointer; display:block; font-family:Arial,Tahoma,sans-serif;
	font-size:1em; font-weight:bold; padding:2px 8px; text-align:center; text-transform:uppercase;}
input.action:hover {background:transparent url(${staticContextPath}/images/common/button-action-hover.png) repeat scroll 0 0; color:#000000; }
input.white {background: none; padding: 2px 3px;}
input.white:hover {background: none; background-color:#f1f1f1; padding: 2px 3px;}
</style>
				

	</head>
<body class="tundra noBg" marginheight="0" marginwidth="0" topmargin="0">
	<c:set var="theTab" value="<%=request.getAttribute("tab")%>"/>
	<c:set var="isPrimaryInd" value="<%=isPrimaryInd%>"/>
	<c:set var="grpId" value="GSO_NONE"/>
	<c:set var="grpStartInd" value="false"/>
	<c:set var="groupInd" value="false" scope="request" />
	<c:set var="isSLAdmin" value="<%=isSLAdmin%>"/>
		
	<table id="bidReqResultsTable" border="0" cellpadding="0" cellspacing="0" width="650">
	<thead>
	<tr><th></th><th></th><th></th><th></th><th></th><th class=".col6"></th></tr>
	</thead>
	<tbody>
<c:forEach var="dto" items="${soOrderList}" varStatus="dtoIndex">
					<tr>
						<td class="col1"><strong>Provider:<span style="font-weight:normal;"><br/>${dto.providerName}<br />ID# ${dto.resourceId}</span></strong></td>
						<td class="col2">
							<strong>
							<a href="${contextPath}/monitor/soDetailsController.action?soId=${dto.id}&resId=${dto.routedResourceId}&displayTab=Summary" target="_top">
								${dto.title}
							</a>
							<br />
							SO # ${dto.id}</strong><br />
							${dto.mainServiceCategory}<c:if test="${dto.skillsRequired != null}">> ${dto.skillsRequired}</c:if>
						</td>
						<td class="col3">
							${dto.city}, ${dto.state}
							<br />
							${dto.zip5}
							<br />
							(est. ${dto.distanceInMiles} miles)
							<br />
							<a href="http://maps.google.com/maps?q=from:+${dto.resourceDispatchAddress}+to:+${dto.city}+${dto.state}+${dto.zip5}" onclick="window.open(this.href); return false;" target="_blank">Google Directions</a>
							<c:if test="${dto.commercialLocation == true}">
							<br/><br/>Commercial Location
							</c:if>
						</td>
						<td class="col4">
						
							<fmt:formatDate value="${dto.serviceDate1}" pattern="M/d/yy" />
							<br />
							${dto.serviceTimeStart} to ${dto.serviceTimeEnd}
							<br />
							(${dto.systemTimezone})
						</td>
						<td class="col5">
							<fmt:formatDate value="${dto.serviceDate1}" pattern="M/d/yy" />
							<c:if test="${dto.serviceTimeStart != null}">
								<br/>${dto.serviceTimeStart}
							</c:if>
						</td>
						
						<td class="col6">
							<c:if test="${dto.sealedBidInd != true || isSLAdmin ==true}">
								<c:if test="${dto.bids == 1}">
									<fmt:formatNumber type="currency" currencySymbol="$">${dto.lowBid}</fmt:formatNumber>
									<br />
								</c:if>
								<c:if test="${dto.bids > 1}">
									<fmt:formatNumber type="currency" currencySymbol="$">${dto.lowBid}</fmt:formatNumber> - 
									<fmt:formatNumber type="currency" currencySymbol="$">${dto.highBid}</fmt:formatNumber>
									<br />
								</c:if>
							</c:if>
							<c:if test="${dto.sealedBidInd == true}">
								<div>
									<strong>Sealed Bid</strong>
								</div>
							</c:if>
							
							<c:if test="${dto.currentBidPrice != null && dto.currentBidPrice > 0}">
							<span style="color:#888;font-size:0.9em;">
								<strong>Your Bid:</strong> <fmt:formatNumber type="currency" currencySymbol="$">${dto.currentBidPrice}</fmt:formatNumber><br>
							</span>
							</c:if>
							<form action="soDetailsController.action?soId=${dto.id}&resId=${dto.routedResourceId}&displayTab=Bid Requests" 
								id="soDetailsController" method="post" target="_top" onsubmit="return true;">
								<div style="height:5px">&nbsp;</div>
								<c:choose><c:when test="${dto.currentBidPrice == null || dto.currentBidPrice == 0.0}">
									<s:submit theme="simple" value="Place Bid" cssClass="button action"/><br/>
								</c:when>
								<c:otherwise>								
									<s:submit theme="simple" value="Change Bid" cssClass="button white action"/><br/>
								</c:otherwise></c:choose>
							</form>
							<c:if test="${dto.sealedBidInd != true || isSLAdmin ==true}">
								<strong>Bids:</strong> ${dto.bids}
							</c:if>
							<br />
							<strong>Comments:</strong> ${dto.noteOrQuestionCount}
							<br/>
							<a href="soDetailsController.action?soId=${dto.id}&resId=${dto.routedResourceId}&displayTab=Summary#tabs" target="_top">Add Comment</a>
						</td>
					</tr>
				</c:forEach>
				
		<c:choose>
			<c:when test="${empty soOrderList}">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="no_records_found" />
			</c:when>
			<c:otherwise>
				<jsp:include page="/jsp/paging/pagingsupport.jsp"/>
			</c:otherwise>			
		</c:choose>
				
				
	</tbody>
	</table>	
	
	<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>  
	<script type="text/javascript" src="${staticContextPath}/javascript/plugins/jquery.tablesorter.min.js"></script>
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
		
		
		function doStatusSubmit(status, subStatus,serviceProName,marketName){
			var dForm = document.getElementById('commonGridHandler');
			dForm.status.value= status;
			dForm.subStatus.value= subStatus;
			dForm.serviceProName.value= serviceProName;
			dForm.marketName.value= marketName;
			jQuery('#commonGridHandler').submit();
		}		
</script>

	
	
	<script type="text/javascript">
	$(document).ready(function(){
	
	$.tablesorter.addParser({ 
            id: 'dates', 
            is: function(s) { 
        	// return false so this parser is not auto detected 
            return false; 
            }, 
            format: function(s) { 
            // format your data for normalization 
             return s.toLowerCase().replace(/\//g,''); 
            }, 
            // set type, either numeric or text 
            type: 'text' 
        });
	
        
        
		$("#bidReqResultsTable").tablesorter(
		{headers: { 3: { sorter:'dates' }, 4: { sorter:'dates' }}},{sortList: [[0,0],[1,0],[2,0],[3,0],[4,0],[5,0]]}
		);
		
		$("#bidReqResultsTable").bind("sortEnd", function(){
			window.parent.sortingDone = true;

			var sortArrowClass;

			$("#bidReqResultsTable > thead > tr > th").each(function(i){
				if(jQuery(this).attr('class').indexOf('headerSortDown')>0){
					sortArrowClass = "headerSortDown";
				}else if(jQuery(this).attr('class').indexOf('headerSortUp')>0){
					sortArrowClass = "headerSortUp";
				}
			});
				window.parent.sortArrowClass = sortArrowClass;
				window.parent.addSortArrow();
			});

	});
	
	function clickColumn(arg){
	$("#bidReqResultsTable > thead > tr > th:eq("+arg+")").click();
	}
	</script>

<s:form action="gridLoader" id="commonGridHandler" >
	<input name="tab" id="tab" value="<%=request.getAttribute("tab")%>" type="hidden"/>
	<input type="hidden" name="status" id="status" value=""/>
	<input type="hidden" name="subStatus" id="subStatus" />	
	<input type="hidden" name="serviceProName" id="serviceProName" />
	<input type="hidden" name="marketName" id="marketName" />
	<input type="hidden" name="sortColumnName" id="sortColumnName" value="<%= sortColumnSession %>" />
	<input type="hidden" name="sortOrder" id="sortOrder" value="<%=sortOrderSession %>" />	
	<input type="hidden" name="resetSort" id="resetSort" value="false" />
</s:form>
</body>
</html>
