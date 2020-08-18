<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:if test='${!empty requestScope.addNewPartWarningMsg}'>
	<div class="warningBox" width= "100%" >
		<c:out value="${requestScope.addNewPartWarningMsg}" />
	</div>
</c:if>
<c:remove var="addNewPartWarningMsg" scope= "session" />


<div dojoType="dijit.TitlePane" title="Parts" class="contentWellPane">
	<div class="hrText">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="wizard.scopeofwork.cat.tasks.parts.materials" />
	</div>
	<fmt:message bundle="${serviceliveCopyBundle}"
	
		key="wizard.scopeofwork.cat.tasks.parts.description" />
		
		
	<p>
	
	
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.scopeofwork.cat.tasks.parts.supplied.by" />
		</label>
		<br />
				
		<span class="formFieldOffset">		
			<table>
				<tr>
					<td>
						 <s:radio list="buyerProviderMap" name="partsSuppliedBy"
						id="partsSuppliedBy" cssClass="antiRadioOffsets" value="partsSuppliedBy"
						onclick="fnShowOrHidePartsPanel();" theme="simple" />
					</td>			
				</tr>
			 </table>
		 </span> 
	</p>		
	<c:set var="hidepanel" scope="request" value="${partsSuppliedBy}" />
		<div>
			<br />
			<br />
		</div>
		<c:if test="${hidepanel==1}">
			<div id="PartsPanels" style="display: block;">
		</c:if>
		<c:if test="${hidepanel==3 || hidepanel==2}">
			<div id="PartsPanels" style="display: none;">
		</c:if>
				<s:iterator value="parts" status="sowPartDTO">
						<c:set var="panelTitle" >
							 Part ${sowPartDTO.count}
							 <c:if test="${!empty parts[sowPartDTO.index].manufacturer}">
							 	&nbsp;-&nbsp;${parts[sowPartDTO.index].manufacturer}
							 </c:if>
							 <c:if test="${!empty parts[sowPartDTO.index].modelNumber}">
							 	&nbsp;-&nbsp;${parts[sowPartDTO.index].modelNumber}
							 </c:if>
						</c:set>
			
						<div dojoType="dijit.TitlePane"
						title="${panelTitle}"
						class="contentWellPane" open="${sowPartDTO.last}">
						<div class="deletePart"><img class="deleteIcon" src="${staticContextPath}/images/icons/iconTrash-2.gif" 
								onclick="javascript:deletePart('soWizardPartsCreate', '${contextPath}', ${sowPartDTO.index}, '${SERVICE_ORDER_ID}');"></img>
							<span onclick="javascript:deletePart('soWizardPartsCreate', '${contextPath}', ${sowPartDTO.index}, '${SERVICE_ORDER_ID}');">Delete Part</span>
						</div>
						<jsp:include page="subpanel_product_info.jsp"></jsp:include>
						<br/>
						<hr>
						<br/>
						<jsp:include page="subpanel_part_info.jsp"></jsp:include>
						<jsp:include page="subpanel_more_part_info.jsp"></jsp:include>
						<jsp:include page="subpanel_part_pickup_info.jsp"></jsp:include>					
						<jsp:include page="subpanel_shipping_info.jsp"></jsp:include>
						<c:if test="${actionType != 'create'}">
							<jsp:include page="subpanel_part_return_shipping_info.jsp"></jsp:include>
						</c:if>
					</div>
					<c:if test="${sowPartDTO.last}">
						<jsp:include page="subpanel_buttons.jsp"></jsp:include>
					</c:if>
				</s:iterator>
			</div>
</div>

