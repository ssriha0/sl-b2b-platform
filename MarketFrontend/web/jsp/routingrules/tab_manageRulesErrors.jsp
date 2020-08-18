<%@ page language="java"
	import="java.util.*,com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="alertActive"
	value="<%=RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE%>" />

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/routingrules.css" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" type="text/css"></link>
<script type="text/javascript">var staticContextPath="${staticContextPath}";</script>
<script type="text/javascript">
$(document).on('click', ".removeAlertLink", function(){
var pageMngNum = ${pagingCriteria.currentIndex};
var routingid= $(this).prop("id");
var tabSelected=$('#carTabs').tabs('option','selected');
$.getJSON ('rrJson_removeErrors.action', {id:routingid}, function(data) {
});
if(tabSelected==0){
$("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
$("#manageRules").load("rrManageTab_display.action?pageNo="+pageMngNum);
}
else
{
$('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
$("#searchresults").load("routingRuleSearch_display.action?pageNo=1&sortCol=0&sortOrd=0&archive=1", function()
 {	
   $('#selectedItemSearch').show();
    if(archive==1)
         {
        $("#showArchiveRules").prop("checked",true);
          }
   });
}

});
</script>
 <tags:security actionName="routingRulesAction_view">
 	<c:set var="isReadOnly" value="true" scope="page" />
 </tags:security>
 <tags:security actionName="routingRulesAction_edit">
     <c:set var="isReadOnly" value="false" scope="page" />
 </tags:security>
<html>
	<head>
	<body>
	
	<c:if test="${removeAlert}">
		<tags:security actionName="routingRulesAction_edit">
		<a  id="${routingRuleId}" style="margin-left:0px;" class="removeAlertLink paginationLink" ><b style="color:#00A0D2;">Remove this Alert</b></a>
    	</tags:security>		
		</c:if>
		<div style="text-align:right;"> 
	<a id="${routingRuleId}" class="errorCancelLink paginationLink" ><b>Close </b></a>
	</div>
		<div id="ruleErrorTextView">
		
			<c:forEach items="${ruleErrorlist}" var="error">
				<c:if test="${ruleErrorlist[0] != error}">
        		<hr/>
    			</c:if>			
				<c:if test="${not empty error.errorMessage}">
				<p>${error.errorMessage}
				<p>
				<hr>
				</c:if>
				<c:if test="${not empty error.conflictRuleName}">
				<p><b>RuleName</b> 
				<p style="word-wrap: break-word;">${error.conflictRuleName}
				</c:if>
				<c:if test="${not empty error.routingRuleHdrId}">
				<p><b>ID#</b> 
				${error.routingRuleHdrId}
				</c:if>
				<br><br>**Conflicts**<p>
				<c:forEach items="${error.ruleErrorVo}" var="errordetails">
				<p style="word-wrap: break-word;"> 
				<c:if test="${errordetails.errorCause=='Conflicting jobcodes'}">
				<b>Job Codes :</b> </c:if>
				<c:if test="${errordetails.errorCause=='Conflicting zipcodes'}">
				<b>Zip Codes :</b> </c:if>
				<c:if test="${errordetails.errorCause=='Conflicting pickuplocations'}">
				<b>Pick Up Location Codes:</b> </c:if>
				<c:if test="${errordetails.errorCause=='Conflicting markets'}">
				<b>Markets :</b> </c:if>
				<c:if test="${errordetails.errorDescription!='null'}">
				${errordetails.errorDescription}</c:if>
				</p>
				</c:forEach>
				</c:forEach>	
		</div>
		
		</body>
		</html>