<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.web.constants.QuickLinksConstants"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="org.owasp.esapi.ESAPI"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="CAME_FROM_WORKFLOW_MONITOR"
	value="<%=Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR%>" />
<c:set var="BUYER_ROLEID"
	value="<%=new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID"
	value="<%=new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

<c:set var="role" value="${roleType}" />
<c:set var="displayTab"
	value="<%=request.getAttribute("displayTab")%>" />
<c:set var="hideInd" value="block" />
<c:set var="soDetailsTab" value="soDetailsTab" />
<c:set var="acceptButton" scope="page"
	value="<%=QuickLinksConstants.GIF_ACCEPT_SERVICE_ORDER%>" />
<html>

<style type="text/css">
.addEstbtncls {
	display:none;
	margin-left: 100px;
	height: 20px;
	width: 132px;
	font-size: 10px;
	float: right;
    background-image:url(${staticContextPath}/images/estimate/Add_estimate.png)
	}

.editEstbtncls{
	display:none;
	margin-left: 100px;
	height: 20px;
	font-size: 10px;
	width: 132px;
	float: right;
	background-image:url(${staticContextPath}/images/estimate/View_edit_estimate.png)
	}

.editEstbtncls:hover{
	 background-image: url(${staticContextPath}/images/estimate/view_edit_estimate_over.png);
}
.addEstbtncls:hover{
	 background-image: url(${staticContextPath}/images/estimate/Add_estimate_over.png);
}
.addEstbtncls:active{
background-image: url(${staticContextPath}/images/estimate/add_estimate_down.png);
}

.editEstbtncls:active{
background-image: url(${staticContextPath}/images/estimate/View_edit_estimate_down.png);
}
</style>
<body>


<c:forEach var="button" items="${linkList}">
	<form id="frmQuickLink" method="POST"
		style="border: 0; margin: 0; padding: 0;">
		<%
	String vulnSelectedsubtab=null;
	String cookieName = "Selectedsubtab";
	Cookie cookies [] = request.getCookies ();
	Cookie myCookie = null;
	if (cookies != null)
	{
	for (int i = 0; i < cookies.length; i++) 
	{
	if (cookies [i].getName().equals (cookieName))
	{
	myCookie = cookies[i];
	break;
	}
	}
	}
	if (myCookie != null) {
		String SelectedsubtabVar=(String)myCookie.getValue();
		String SelectedsubtabVarNew=ESAPI.encoder().canonicalize(SelectedsubtabVar);
		vulnSelectedsubtab=ESAPI.encoder().encodeForHTML(SelectedsubtabVarNew);
	}
		String hiddenVar=request.getParameter("hiddenInd");
		request.setAttribute("tab",hiddenVar);	
			
			%>
			
		<c:if test="${button.onClick != null}">
			<c:set var="SelectedsubtabV">
				<c:out value="<%=vulnSelectedsubtab%>" />
				</c:set>
			<c:set var="selectedTab">
				<c:out value="<%=request.getAttribute("tab")%>" />
				</c:set>
			<c:choose>
				<c:when test="${button.jsp == acceptButton}">
					<c:if test="${!priceModelBid}">
						<img width="131" height="${button.height}"
							class="${button.cssStyle}" onClick="${button.onClick}"
							style="cursor: pointer; background-image: url(${staticContextPath}/images/btn/${button.jsp});"
							src="${staticContextPath}/images/common/spacer.gif" />
					</c:if>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${button.jsp == 'manageDocsPhotos.gif'}">
							<c:choose>
								<c:when test="${selectedTab eq 'Summary'}">
									<a href="javascript:void(0);"> <img width="131"
											height="${button.height}" class="${button.cssStyle}"
											onClick="moveToDocuments('${soID}')"
											style="cursor: pointer; background-image: url(${staticContextPath}/images/btn/${button.jsp});"
											src="${staticContextPath}/images/common/spacer.gif" /> </a>
								</c:when>
								<c:otherwise>
									<a id="docLink" href="javascript:void(0);"> 
									        <img width="131" height="${button.height}"
											class="${button.cssStyle}"
											onClick="goToDocumentSection('${soID}')"
											style="cursor: pointer; background-image: url(${staticContextPath}/images/btn/${button.jsp});"
											src="${staticContextPath}/images/common/spacer.gif" /> 
									</a>	
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
							<c:when test="${button.jsp == 'acceptServiceOrderConditional.gif'}">
								<c:if
									test="${!(role == PROVIDER_ROLEID && THE_SERVICE_ORDER_STATUS_CODE == 110)}">
									<a href="#"> <img width="131" height="${button.height}"
											class="${button.cssStyle}" onClick="${button.onClick}"
											style="cursor: pointer; background-image: url(${staticContextPath}/images/btn/${button.jsp});"
											src="${staticContextPath}/images/common/spacer.gif" /> </a>
								</c:if>
							</c:when>
							<c:otherwise>
							<c:choose>
							<c:when test="${button.jsp == 'completeForPayment.gif'}">
							<c:if test="${viewOrderPricing==true}">
									<img width="131" height="${button.height}"
										class="${button.cssStyle}" onClick="${button.onClick}"
										style="cursor: pointer; background-image: url(${staticContextPath}/images/btn/${button.jsp});"
										src="${staticContextPath}/images/common/spacer.gif" /> 
								</c:if>
								</c:when>
								<c:otherwise>
								<c:choose>
								<c:when test="${button.jsp == 'viewOrderHistory.gif'}">
							<c:if test="${(viewOrderPricing==true && role == PROVIDER_ROLEID)||role==BUYER_ROLEID}">
							<a href="javascript:void(0);"> <img width="131" height="${button.height}"
											class="${button.cssStyle}" onClick="${button.onClick}"
											style="cursor: pointer; background-image: url(${staticContextPath}/images/btn/${button.jsp});"
											src="${staticContextPath}/images/common/spacer.gif" /> </a>
								
							</c:if>
							</c:when>
							<c:otherwise>
								<img width="131" height="${button.height}"
										class="${button.cssStyle}" onClick="${button.onClick}"
										style="cursor: pointer; background-image: url(${staticContextPath}/images/btn/${button.jsp});"
										src="${staticContextPath}/images/common/spacer.gif" />
									</c:otherwise>	
									</c:choose>
										</c:otherwise>
										</c:choose>
							</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:if>
	<!-- SL-21989: To enable check on frontend to disable the accept button as soon as accept reschedule request has been submitted and till response is received. Code change starts. --> 
	</form>
	<c:if test="${button.action != null}">
		<form id="foo" style="border: 0; margin: 0; padding: 0;"
			action="${contextPath}/${button.action}?soId=${SERVICE_ORDER_ID}" onsubmit="return fnButtonRestrict()">
			<input type="hidden" name="soId" value="${SERVICE_ORDER_ID}"/>
			<input type="image" width="131" height="${button.height}"
				class="${button.cssStyle}" id="btnRestriction"
				style="background-image: url(${staticContextPath}/images/btn/${button.jsp});"
				src="${staticContextPath}/images/common/spacer.gif" />
		</form>
	<!-- SL-21989: Code change ends. -->
	</c:if>
</c:forEach>
<br />
<br />



<c:if test="${empty action}">
	<c:choose>
		<c:when
			test="${requestScope.cameFromWorkflowMonitor == CAME_FROM_WORKFLOW_MONITOR }">
			
			<c:choose>
			<c:when test="${SecurityContext.slAdminInd}">
				
				<form id="gotoWFMonitor"
					action="pbController_backToAdminWorkFlow.action">
					<input width="131" height="27" type="image" class="btn27"
						style="background-image: url(${staticContextPath}/images/btn/returnToWFMonitor.gif);"
						src="${staticContextPath}/images/common/spacer.gif" />
				</form>
			</c:when>
			<c:otherwise>
				<form id="gotoWFMonitor" action="pbController_execute.action">
					<input width="131" height="27" type="image" class="btn27"
						style="background-image: url(${staticContextPath}/images/btn/returnToWFMonitor.gif);"
						src="${staticContextPath}/images/common/spacer.gif" />
				</form>
			</c:otherwise>
			</c:choose>
		</c:when>
		<c:when
			test="${requestScope.cameFromOrderManagement != 'cameFromOrderManagement' }">
			<form id="gotoMonitor" action="serviceOrderMonitor.action?displayTab=${displayTab}">
				<input type="hidden" name="displayTab" value="${displayTab}" />
				<input width="131" height="27" type="image" class="btn27"
					style="background-image: url(${staticContextPath}/images/btn/returnSOM.gif);"
					src="${staticContextPath}/images/common/spacer.gif"/>
			</form>
		</c:when>
		<c:when
			test="${requestScope.cameFromOrderManagement == 'cameFromOrderManagement' }">	
			<form id="gotoOM" action="orderManagementController.action" style="padding-right: 0px;">
				<input type="hidden" name="omDisplayTab" value="${omDisplayTab}" />
			<input width="131" height="27" type="image" class="btn27"
					style="background-image: url(${staticContextPath}/images/order_management/returnOrderMgmt.gif);"
					src="${staticContextPath}/images/common/spacer.gif"/>
			</form>
			</c:when>
		<c:otherwise>
			<form id="gotoMonitor" action="serviceOrderMonitor.action?displayTab=${displayTab}">
				<input type="hidden" name="displayTab" value="${displayTab}" />
				<input width="131" height="27" type="image" class="btn27"
					style="background-image: url(${staticContextPath}/images/btn/returnSOM.gif);"
					src="${staticContextPath}/images/common/spacer.gif" />
			</form>
		</c:otherwise>
	</c:choose>
	
</c:if>



	
	<script type="text/javascript">

 jQuery(document).ready(
		function() {
			var estId=document.getElementById('estimateId').value;
			var isEstimate = document.getElementById('isEstimationRequest').value;
			var isProvider = document.getElementById('isProvider').value;
			var showEditEstimateBtn = document.getElementById('showEstimateBtn').value;
			
			
			if(isEstimate==='true' && isProvider==='true' && showEditEstimateBtn==='true'){
			
			if(estId){
				var editcols =  document.getElementsByClassName('editEstbtncls');
					for(i=0; i<editcols.length; i++) {
				editcols[i].style.display =    'block';
				}
				}else{
				var addcols =     document.getElementsByClassName('addEstbtncls');
				for(i=0; i<addcols.length; i++) {
				   addcols[i].style.display =    'block';
					}
					
					
				}
			}
	 

			
		}); 
/* function displayAddEstimateSOPopUp(soId,estimationId){ */
function displayAddEstimateSOPopUp(){
	//fnWaitForResponseShow();
var sostatus=${THE_SERVICE_ORDER_STATUS_CODE};
var divElem;
divElem= document.createElement("div");
divElem.id = "addEstimate";
divElem.style.cssText = "width: 850px; float: left;top: 100px; display:none; border: 1px solid #A8A8A8;font-family: Arial;";

    
document.body.appendChild(divElem);
	var estimationId=document.getElementById('estimateId').value;
	
	var isEstimate = document.getElementById('isEstimationRequest').value;  
	var soId = document.getElementById('soid').value;
	
	$("#addEstimate").load("displayAddEstimateSOPopUp.action?soId=" +soId+"&estimationId="+estimationId+"&sostatus="+sostatus+"&sodPage=true" , 
			function(data) {
				
				
							//fnWaitForResponseClose();
						/* 	$("#accept_so_id").val(soId);
							$("#groupInd").val(groupInd); */
							$("#addEstimate").addClass("jqmWindow");
							$("#addEstimate").css("width", "850px");
							$("#addEstimate").css("height", "auto");
							$("#addEstimate").css("marginLeft", "-450px");
							$("#addEstimate").css("margin-top", "153px"); 
							$("#addEstimate").css("top", "100px");
							/* $("#addEstimate").jqm({
								modal : true
							}); */
							$("#addEstimate").fadeIn('slow');
							$('#addEstimate').css('display', 'block');
							//$("#addEstimate").jqmShow();
			});
}

function fnWaitForResponseShow(){
		jQuery("#overLay").show();
		jQuery("#waitPopUp").show();
	}
	function fnWaitForResponseClose(){
		jQuery("#overLay").hide();
		jQuery("#waitPopUp").hide();
	}
	
// SL-21989: To enable check on frontend to disable the accept button as soon as accept reschedule request has been submitted and till response is received.
function fnButtonRestrict(){

  document.getElementById("btnRestriction").disabled = true
  return true;
}

</script>
<div>
<!-- <input type="button" name="editEstbtn" class="editEstbtncls" value="VIEW AND EDIT ESTIMATE" 
 onclick="displayAddEstimateSOPopUp()" style="cursor: pointer;"/>

<input type="button" name="addEstbtn" class="addEstbtncls" value="ADD ESTIMATE" 
onclick="displayAddEstimateSOPopUp()"  style="cursor: pointer;"/>
</div>
 -->
 <input width="131" height="27" type="image" class="editEstbtncls" 
 src="${staticContextPath}/images/common/spacer.gif" name="editEstbtn" alt="Submit" onclick="displayAddEstimateSOPopUp()">
 <input width="131" height="27" type="image" class="addEstbtncls" 
 src="${staticContextPath}/images/common/spacer.gif" name="addEstbtn" alt="Submit" onclick="displayAddEstimateSOPopUp()">

	
	<!--<div id="overLay" class="overLay" style="display: none; z-index: 1000; width: 100%; height: 100%; position: fixed; opacity: 0.2; filter: alpha(opacity =   20); 
		top: 0px; left: 0px; background-color: #E8E8E8; cursor: wait;"></div>
		
		<div id="waitPopUp" class="waitLayer">
		<div style="padding-left: 0px; padding-top: 5px; color: #222222;">
			<div class="refreshMsg">
				<span>Loading...</span>
			</div>
		</div>
	</div>-->


</body>

</html>