<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="role" value="${roleType}"/>	
<c:if test="${role == 3}">
<div id="widgetSOM_PendingCancel" name="widgetSOM_PendingCancel">
	<jsp:include page="widgetPendingCancel.jsp" />
</div>
<br>
</c:if>
<c:if test="${role == 1}">
<div id="widgetSOM_PendingCancelProvider" name="widgetSOM_PendingCancelProvider">
	<jsp:include page="widgetPendingCancelProvider.jsp" />
</div>
<br>
</c:if>


<div id="widgetSOM_OrderExpressMenu" name="widgetSOM_OrderExpressMenu">
	<jsp:include page="widgetOrderExpressMenu.jsp" />
</div>

<div id="widgetSOM_ManageDocuments" name="widgetSOM_ManageDocuments">
	<jsp:include page="widgetManageDocuments.jsp" />
</div>

<div id="widgetSOM_IncreaseSpendLimit" name="widgetSOM_IncreaseSpendLimit">
	<jsp:include page="widgetIncreaseSpendLimit.jsp" />
</div> 


<div id="widgetSOM_AddNote" name="widgetSOM_AddNote">
	<jsp:include page="widgetAddNote.jsp" />
</div>

<div id="widgetSOM_RejectServiceOrder" name="widgetSOM_RejectServiceOrder">
	<jsp:include page="widgetRejectServiceOrder.jsp" />
</div>

<tags:security actionName="incidentTracker">
<div id="widgetSOM_IncidentTracker" name="widgetSOM_IncidentTracker">
	<jsp:include page="widgetIncidentTracker.jsp" />
</div>
</tags:security>

<%-- Need a spacer between last widget/quicklink and the first button --%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
<img src="${staticContextPath}/images/common/spacer.gif"/>

	<div id="buttonSOM_AcceptRescheduleRequest" name="buttonSOM_AcceptRescheduleRequest">
		<input width="131" height="27" type="image" class="btn17" onClick="javascript: void(0)"
			style="background-image: url(${staticContextPath}/images/btn/acceptReschedReq.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>
	<div id="buttonSOM_AcceptWithConditions" name="buttonSOM_AcceptWithConditions">
		<input width="131" height="17" type="image" class="btn17" onClick="javascript: void(0)"
			style="background-image: url(${staticContextPath}/images/btn/acceptServiceOrderConditional.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />			
	</div>
		
	<div id="buttonSOM_AddViewNotes" name="buttonSOM_AddViewNotes">
		<input width="131" height="17" type="image" class="btn17" onClick="javascript: void(0)"
			style="background-image: url(${staticContextPath}/images/btn/addAndViewNotes.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>

	<div id="buttonSOM_DeleteDraft" name="buttonSOM_DeleteDraft">
		<input width="131" height="17" type="image" class="btn17" onClick="return fnSubmitSOMDeleteDraft()"
			style="background-image: url(${staticContextPath}/images/btn/deleteDraftOrder.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>

	<div id="buttonSOM_ManageDocsAndPhotos" name="buttonSOM_ManageDocsAndPhotos">
		<input width="131" height="27" type="image" class="btn27" onClick="javascript: void(0)"
			style="background-image: url(${staticContextPath}/images/btn/manageDocsPhotos.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>
	
	<div id="buttonSOM_VoidSO" name="buttonVoidSO">
		<a href="#" onClick="return fnSubmitVoidSO(), newco.jsutils.clearRightSideMenus();">
			<img src="${staticContextPath}/images/common/spacer.gif"  width="131" height="17" 
				 type="image" class="btn17" style="background-image: url(${staticContextPath}/images/btn/voidServiceOrder.gif);"/>
		</a>
	</div>

	<div id="buttonSOM_CopySO" name="buttonSOM_CopySO">
		<input width="131" height="17" type="image" class="btn17" onClick="javascript:fnCopyServiceOrder()"
			style="background-image: url(${staticContextPath}/images/btn/copyServiceOrder.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>

	<div id="buttonSOM_ViewPrintPDF" name="buttonSOM_ViewPrintPDF">
		<input width="131" height="17" type="image" class="btn17" onClick="javascript:open_popup('createPDF.action?soID=')"
			style="background-image: url(${staticContextPath}/images/btn/viewPrintPDF.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>
	
	<div id="buttonSOM_ReleaseSO" name="buttonSOM_ReleaseSO">
		<input width="131" height="17" type="image" class="btn17" onClick="javascript: void(0)"
			style="background-image: url(${staticContextPath}/images/btn/releaseServiceOrder.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>
	
	<div id="buttonSOM_AcceptSO" name="buttonSOM_AcceptSO">
		<input width="131" height="17" type="image" class="btn17" onClick="javascript: void(0)"
			style="background-image: url(${staticContextPath}/images/btn/acceptServiceOrder.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>
	
	<div id="buttonSOM_RejectSO" name="buttonSOM_RejectSO">
		<input width="131" height="17" type="image" class="btn17" onClick="javascript: void(0)"
			style="background-image: url(${staticContextPath}/images/btn/rejectServiceOrder.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>
	
	<div id="buttonSOM_WithdrawCondAccept" name="buttonSOM_WithdrawCondAccept">
		<input width="131" height="27" type="image" class="btn27" onClick="fnSubmitWithdrawCondOffer()"
			style="background-image: url( ${staticContextPath}/images/btn/withdrawCondAccept.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>

 <div id="buttonSOM_IncreaseSpendLimit" name="buttonSOM_IncreaseSpendLimit">
 	<input type="hidden" name="IncSLSOid" id="IncSLSOid"/>
 	
 	<input type="hidden" name="wfFlag" id="wfFlag"/>
		<input width="131" height="17" type="image" class="btn17" onClick="calculateSpendLimit(); "
			style="background-image: url( ${staticContextPath}/images/btn/increasePrice.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
</div>  

<div id="increaseSpendLimit" name="increaseSpendLimit"></div>

