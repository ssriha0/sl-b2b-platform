<%@page import="java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	<jsp:param name="PageName" value="wizard.tabParts" />
</jsp:include>

<s:form action="soWizardPartsCreate_cancel" id="soWizardPartsCreate"
	name="sowPartForm" theme="simple">
	<input type="hidden" id="SERVICE_ORDER_ID" name="SERVICE_ORDER_ID" value="${SERVICE_ORDER_ID}" />
 	
 	<jsp:include page="validationMessages.jsp" /> 
 	<jsp:include page="panels_parts/panel_parts.jsp" />
 	
	<div class="clearfix">
		<div class="formNavButtons">

			<input type="button" id="previous"
				onclick="javascript:previousButton('soWizardPartsCreate_previous.action','soWizardPartsCreate','tab2');"
				class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/btn/previous.gif);width:75px; height:20px;"
				src="${staticContextPath}/images/common/spacer.gif" />
			<c:if test="${requestScope.entryTab!= 'today'}">

  <c:if test="${requestScope.THE_SERVICE_ORDER_STATUS_CODE != '110' && requestScope.THE_SERVICE_ORDER_STATUS_CODE  != '130'}">
			
				<input type="button" id="saveAsDraft"
					onclick="newco.jsutils.doFormSubmit('soWizardPartsCreate_saveAsDraft.action','soWizardPartsCreate')"
					class="btn20Bevel"
					style="background-image: url(${staticContextPath}/images/btn/saveAsDraft.gif); width:89px; height:20px;"
					src="${staticContextPath}/images/common/spacer.gif" />
</c:if>
			</c:if>
			
			<input type="button" id="next"
				onclick="javascript:nextButton('soWizardPartsCreate_next.action','soWizardPartsCreate','tab4');"
				class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/btn/next.gif); width:50px; height:20px;"
				src="${staticContextPath}/images/common/spacer.gif" />

			<s:if test="#session.showReviewBtn == 'true'">
				<s:submit type="input" cssClass="btn20Bevel" method="review"
					theme="simple" value="Go to review" />
			</s:if>
		</div>
		
		<div class="bottomRightLink">
			<a href="#"
				onclick="newco.jsutils.doFormSubmit('soWizardPartsCreate_cancel.action','soWizardPartsCreate')">
				<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.cancel" />
			</a>
		</div>
	</div>
</s:form>
