<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<div class="error" id="errPlaceHolder_${spnMonitorVO.spnId}" style="display:none">Please upload files for incomplete documents to continue.</div>
<jsp:include page="spnMainMonitorTableExpandRequirementsMembershipCriteriaAndCredentials.jsp"></jsp:include>
<jsp:include page="spnMainMonitorTableExpandRequirementsSignReturn.jsp"></jsp:include>
<jsp:include page="spnMainMonitorTableExpandRequirementsElectronicAgree.jsp"></jsp:include>
<c:if test="${!spnMonitorVO.isAlias && (spnMonitorVO.providerFirmState=='PF INVITED TO SPN'||spnMonitorVO.providerFirmState=='PF SPN INTERESTED'||spnMonitorVO.providerFirmState=='PF APPLICANT INCOMPLETE'||spnMonitorVO.providerFirmState=='PF SPN NOT INTERESTED')}">																		
	<div class="clearfix" style="padding-top: 15px;">
	<form name="mainMonitor-${spnMonitorVO.spnId}" id="mainMonitor-${spnMonitorVO.spnId}" method="POST" action="spnSubmitBuyerAgreement.action">
	<input type="hidden" id="mainMonitor-spnId" name="spnId" value="${spnMonitorVO.spnId}"/>
	<input type="hidden" id="mainMonitor-auditRequired" name="auditRequired" value="${spnMonitorVO.auditRequired}"/>
	<input type="hidden" id="mainMonitor-firmId" name="firmId" value="${SecurityContext.companyId}"/>
	<table> 
		<tr>
			<p class="tr" >
				<td style="width:90%;text-align:right;" ><fmt:message bundle="${serviceliveCopyBundle}" key="spn.approval.process.msg"/></td>
				<td style="width:10%;">			
					<input type="button" class="action right" value="Submit" onClick="submitApplication('${spnMonitorVO.spnId}');">
				</td>
			</p>		
		</tr>
	</table>		
	</form>
	</div>
</c:if>	