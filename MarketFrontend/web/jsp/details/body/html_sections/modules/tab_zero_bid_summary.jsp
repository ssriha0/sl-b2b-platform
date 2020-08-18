<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

   <script type="text/javascript">
     
      jQuery('#serviceOrderBidWidgetDiv').load('soDetailsBid_execute.action');
    
     function expandSO(id,path){

	var divId="order"+id;
	var bodyId="order_menu_body"+id;
	jQuery("#"+divId+" p.menugroupso_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
	var ob=document.getElementById('soImage'+id).src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('soImage'+id).src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('soImage'+id).src=path+"/images/widgets/arrowRight.gif";
	}
} 
      </script>

<div class="soNote">	
<c:set var="SOStatusCode" value="<%=request.getSession().getAttribute("THE_SERVICE_ORDER_STATUS_CODE") %>"/>
<c:set var="statusReceived" scope="page"
                  value="<%= String.valueOf(OrderConstants.ROUTED_STATUS) %>" />
<div style="color: blue">
 <p>${msg}</p>
 <%session.setAttribute("msg",""); %>
</div>
   
    <div id="rightsidemodules" class="colRight255 clearfix" >
		<p id="serviceOrderBidWidgetDiv"
			style="color: #000; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 10px;">
		</p>
	</div>

<div class="contentPane">
<%-- Display Server Side Validation Messages when we are coming from other pages: Display from Session --%>
<c:set var="errList" value="${errList}" scope="session" />
<c:if test="${fn:length(errList) > 0}">
<div class="errorBox clearfix" style="width: 665px; overflow-y:hidden;visibility:visible">
	<c:forEach items="${errList}"  var="error" varStatus="errIndex">
		<p class="errorMsg">
		&nbsp;&nbsp;&nbsp;&nbsp;${error.fieldId} -  ${error.msg}
		</p>
	</c:forEach>
</div>

<% session.removeAttribute("errList"); %>
<br>
</c:if>

<c:if test="${fn:length(message) > 0}">
	<div class="errorBox clearfix" style="width: 665px; overflow-y:hidden;visibility:visible">
			<p class="errorMsg">
			<s:property value="errMessage" />
			&nbsp;&nbsp;&nbsp;&nbsp;${message}
			</p>
	</div>
	<%session.removeAttribute("message");%>
	<br>
</c:if>	

		
<c:if test="${not empty showGeneralInfo}">
	<jsp:include page="panel_general_information.jsp" />
</c:if>

<c:if test="${not empty showScopeOfWork}">
	<jsp:include page="panel_scope_of_work.jsp" />
</c:if>

<c:if test="${not empty showContactInfo}">
	<jsp:include page="panel_documents_and_photos.jsp" />
</c:if>

<c:if test="${not empty showBidNotes}">
	<jsp:include page="panel_bid_notes.jsp" />
</c:if>
