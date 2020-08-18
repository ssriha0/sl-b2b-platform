<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="theTab" scope="request" value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />
<c:set var="providerRoleId" scope="page" 
value="<%=new Integer(OrderConstants.PROVIDER_ROLEID)%>" />


<c:if test="${role == providerRoleId}" >

<jsp:useBean id="SoRejectReasons" scope="session" class="com.newco.marketplace.dto.vo.serviceorder.ReasonCodeVO"></jsp:useBean>
<div dojoType="dijit.TitlePane" 
			 title="Reject Service Order" 
			 id="pane_3${theTab}" 
		 style="padding-top: 1px; width: 249px;"
		 open="false">
   <span class="dijitInfoNodeInner"><a href=""></a></span>
   	<div class="dijitReset">
  	<div class="dijitTitlePaneContentInner">
	<!-- nested divs because wipeIn()/wipeOut() doesn't work right on node w/padding etc.  Put padding on inner div. -->
	
	
	<table cellpadding="0" cellspacing="0" border="0">
		  <tr class="error">
    			<td class="errMsg alignRight">
    				<div id="rejectGroupedOrderResponseMessage${theTab}"></div>
    			</td>
    	  </tr>
    	  <tr>
    
    <div id="rejectResources${theTab}">
	</div>
	
	</tr>
		  <tr>
			<td class="labelLeft"><h4>Reason Code:</h4></td>
		  </tr>
		  <tr>
			<td class="labelLeft alignRight">
				<select id="rejectReasonId${theTab}" 
						name="rejectReasonId${theTab}" 
						onchange='newco.jsutils.fnHandleRejectSO()'>
						<option value="0">---Select---</option>
						<c:forEach items="${SoRejectReasons.arrRejectReason}" var="reasonLookupVo">
							<option value='${reasonLookupVo.id}'>${reasonLookupVo.descr}</option>
						</c:forEach>
				</select>
			</td>
		  </tr>
		  <tr>
		  	<td>&nbsp;</td>
		  </tr>
		  <tr class="error">
    			<td class="errMsg alignRight">
    				<div id="rejectServiceOrderResponseMessage${theTab}"></div>
    			</td>
    	  </tr>
   	 	  <tr>
   	 	  		<td>&nbsp;</td>
   	 	  </tr>
	 </table>
	 <br/>
		<ul class="titlePaneBtns">
			<li>
				<a href="javascript:void(0);" onclick="fnSubmitRejectSO('${contextPath}/market/serviceOrderRejectWidget.action','${theTab}');">
						<img src="${staticContextPath}/images/spacer.gif" width="72" height="22" 
							 style="background-image:url(${staticContextPath}/images/btn/reject.gif);" class="btnBevel" />
				</a>
			</li>
		</ul>
	  </div>
	</div>
</div>
</c:if>
