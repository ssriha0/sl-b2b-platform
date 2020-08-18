<%@page import="java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%int randval = ((int)(Math.random() * 10000000));%>
<c:set var="randomNum" value="<%=""+randval%>"/>

<style type="text/css">
    blockquote {padding-left:15px;}
  </style>
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="wizard.tabReview"/>
	</jsp:include>

<p>
	Please review the summary below to make sure the information has been
	entered correctly. If you need to make changes, click on the tabs above
	to go to the section you wish to edit. If you have no changes, and are
	ready to publish to your selected providers, click 'post service
	order.'
</p>

<s:form action="soWizardReviewCreate_createAndRoute" id="soWizardReviewCreate" theme="simple">
<jsp:include page="validationMessages.jsp" />

<jsp:include page="panels_review/panel_generalInformation.jsp" />
<jsp:include page="panels_review/panel_scopeOfWork.jsp" />
<jsp:include page="panels_review/panel_contactInformation.jsp" />
<jsp:include page="panels_review/panel_manageDocumentsAndPhotos.jsp" />
<jsp:include page="panels_review/panel_parts.jsp" />
<jsp:include page="panels_review/panel_providers.jsp" />
<jsp:include page="panels_review/panel_pricing.jsp" />
<jsp:include page="panels_review/panel_termsAndConditions.jsp" />

<input type="hidden" id="SERVICE_ORDER_ID" name="SERVICE_ORDER_ID" value="${SERVICE_ORDER_ID}" />

<div class="clearfix">



	<div class="formNavButtons">

		<c:if test="${groupOrderId != null}">
			<div style="color : red">Please note that any changes you make to this order will affect all orders in the group.</div>
			<br/>
		</c:if>

	
			<input type="button" id="previous" 
				  		onclick="javascript:previousButton('soWizardReviewCreate_previous.action','soWizardReviewCreate','tab5');"
				  		class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/previous.gif);width:75px; height:20px;"
				  		src="${staticContextPath}/images/common/spacer.gif"
			/>	
				
    <c:if test="${requestScope.entryTab != 'today'}">
<c:if test="${requestScope.THE_SERVICE_ORDER_STATUS_CODE!= '110' && requestScope.THE_SERVICE_ORDER_STATUS_CODE != '130'}">
    
		<input type="button" id="saveAsDraft" 
				  		onclick="newco.jsutils.doFormSubmit('soWizardReviewCreate_saveAsDraft.action','soWizardReviewCreate')"
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
			value="" 
			theme="simple"/>
-->	
		<input type="button" id="createAndRoute" 
			  		onclick="javascript:this.disabled='true'; previousButton('soWizardReviewCreate_createAndRoute.action','soWizardReviewCreate','tab6');"
			  		class="btn20Bevel"
					style="background-image: url(${staticContextPath}/images/btn/postServiceOrder.gif);width:126px; height:20px;"
			  		src="${staticContextPath}/images/common/spacer.gif"
		/>	
		
		<c:if test="${showSaveAndAutoPost == 'true'}">
			<input type="button" id="saveAndAutoPost" 
				  		onclick="newco.jsutils.doFormSubmit('soWizardReviewCreate_saveAndAutoPost.action','soWizardReviewCreate')"
				  		class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/saveAndAutoPost.gif); width:113px; height:20px;"
				  		src="${staticContextPath}/images/common/spacer.gif"
			/>
		</c:if>
	</div>
	<div class="bottomRightLink">
	   <a href="#" onclick="newco.jsutils.doFormSubmit('soWizardReviewCreate_cancel.action','soWizardReviewCreate')">
		<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.cancel"/></a>
	</div>
	
	
</div>
<input type="hidden" name="reviewDTO.sessionId" value="${randomNum}" />
</s:form>

