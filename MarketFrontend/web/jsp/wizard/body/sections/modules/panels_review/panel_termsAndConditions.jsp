<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<div dojoType="dijit.TitlePane" title="ServiceLive Terms & Conditions"
	class="contentWellPane">
	<p>
		Please verify your acceptance of ServiceLive's <a href="/MarketFrontend/termsAndConditions_displayBuyerAgreement.action" onclick="window.open(this.href,'terms','width=1040,height=640,scrollbars,resizable'); return false;">Terms & Conditions</a>. You will be able to post this service order only upon acceptance
		
		<%-- ${reviewDTO.pleaseVerify} --%>
	</p>
	
<%-- <div class="inputArea" style="height:200px;">
		${reviewDTO.termsAndConditions}
	</div> --%>
	<p>
	<tags:fieldError id="Accept Terms And Conditions" >
		<span class="formFieldOffset"> 
			<s:radio list="acceptTermsAndConditionsMap" 
				 name="acceptTermsAndConditions"
				 id="acceptTermsAndConditions"
				 cssClass="antiRadioOffsets"
				 value="acceptTermsAndConditions" 
				 theme="simple"/> 
				 </br>
		</span>
	</tags:fieldError>
	</p>
	<p>
	
	</p>
	
	<p>
	<c:if test="${!reviewDTO.insPresent and not empty reviewDTO.providersList}"> 
		<tags:fieldError id="Accept Insurance Conditions" >
			<s:checkbox name="acceptInsuranceCondition" />	<b>One or more of the providers you have selected have reported they do not have General Liability and/or Workers' Compensation insurance. Neither ServiceLive nor any of its affilicated companies are liable for injury or damage caused by your provider. Prior to posting, you may select different providers.</b>
										
		</tags:fieldError>
 	</c:if>
	</p>
</div>
