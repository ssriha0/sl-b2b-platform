<%@page import="java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="wizard.tabProviders"/>
	</jsp:include>

<s:form action="soWizardProvidersCreate_next" id="soWizardProvidersCreate" theme="simple">
<input type="hidden" id="SERVICE_ORDER_ID" name="SERVICE_ORDER_ID" value="${SERVICE_ORDER_ID}" />
<jsp:include page="validationMessages.jsp" />
<jsp:include page="panels_providers/panel_providers.jsp" />

<div class="clearfix">
	<div class="formNavButtons">

		<input type="button" id="previous" 
				  		onclick="javascript:previousButton('soWizardProvidersCreate_previous.action','soWizardProvidersCreate','tab3');"
				  		class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/previous.gif);width:75px; height:20px;"
				  		src="${staticContextPath}/images/common/spacer.gif"
			/>	
					
		<c:if test="${entryTab != 'today'}">
<c:if test="${requestScope.status != '110' && requestScope.status != '130'}">
		
			<input type="button" id="saveAsDraft" 
				  		onclick="newco.jsutils.doFormSubmit('soWizardProvidersCreate_saveAsDraft.action','soWizardProvidersCreate')"
				  		class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/saveAsDraft.gif); width:89px; height:20px;"
				  		src="${staticContextPath}/images/common/spacer.gif"
			/>			
		</c:if>
		</c:if>	

<!-- 
		<s:submit type="input" src="%{#request['staticContextPath']}/images/common/spacer.gif"
			cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/saveAsTemplate.gif);width:106px; height:20px;"
			cssClass="btn20Bevel"
			method="saveAsTemplate"
			value="" />
 -->
		<input type="button" id="next" 
				  		onclick="javascript:nextButton('soWizardProvidersCreate_next.action','soWizardProvidersCreate','tab5');"
				  		class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/next.gif); width:50px; height:20px;"
				  		src="${staticContextPath}/images/common/spacer.gif"
			/>
			
		<s:if test="#session.showReviewBtn == 'true'">
			<s:submit type="input" 
				cssClass="btn20Bevel" 
				method="review"
				theme="simple"
				value="Go to review"/>
		</s:if>	
		<input type="hidden" id="routingPriorityAppliedInd" name="routingPriorityAppliedInd">
	</div>
	<div class="bottomRightLink">
	   <a href="#" onclick="newco.jsutils.doFormSubmit('soWizardProvidersCreate_cancel.action','soWizardProvidersCreate')">
		<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.cancel"/></a>
	</div>
</div>
</s:form>

