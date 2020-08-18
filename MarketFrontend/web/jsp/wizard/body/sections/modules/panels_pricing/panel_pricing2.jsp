<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<s:iterator value="dtoList">


	<div dojoType="dijit.TitlePane" title="Pricing ${soId}"
		class="contentWellPane" open="false">
		<!--	<p>
		You can price your project at a fixed or hourly rate and set spending
		limits for labor and parts (if the provider is to supply parts). See
		your ServiceLive administrator or click 'add funds' if you need to
		increase funds in your account, or to pre-fund additional projects.
	</p>
    <div class="hrText">
		Pricing Type
	</div>
	<p class="formFieldOffset">
		<s:radio list="pricingRadioOptions" name="selectedRadioPricing" id="selectedRadioPricing" cssClass="antiRadioOffsets" value="%{selectedRadioPricing}"/>
		
		<s:select 	
	    	name="selectedDropdownPricing"
	   		headerKey="-1"
	        headerValue="Select One"
	     	cssStyle="width: 100px;" size="1"
	      	theme="simple"
			list="rateOptions"
			listKey="value"
			listValue="label"
		/>
		
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$		
		<s:textfield
			theme="simple"
			name="hourlyRate"
			id="hourlyRate"
			cssClass="shadowBox grayText" cssStyle="width: 100px;"
			value="%{hourlyRate}" onfocus="clearTextbox(this)" 
			maxlength="15"/>      
		
		/hr
	</p>
-->
		<tags:fieldError id="Maximum Price for Labor" oldClass="">
			<label>
				Maximum Price for Labor
			</label>
			<br>
			$<s:textfield name="laborSpendLimit" id="laborSpendLimit" cssClass="shadowBox grayText" cssStyle="width: 100px;" maxlength="10" />
			<br>
			<%-- 
			$<input type="text" name="laborSpendLimit" id="laborSpendLimit"
				class="shadowBox grayText" style="width: 100px;" maxlength="10"
				value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.laborSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
			</input>
			--%>

			<br>
		</tags:fieldError>

		<c:if test="${showPartsSpendLimit}">
			<tags:fieldError id="Maximum Price for Parts" oldClass="">
				<label>
					Maximum Price for Parts
				</label>
				<br>
				$<s:textfield name="partsSpendLimit" id="partsSpendLimit" cssClass="shadowBox grayText" cssStyle="width: 100px;" maxlength="10"/>
				<br/>
				<%-- 
				$ <input type="text" name="partsSpendLimit" id="partsSpendLimit"
					class="shadowBox grayText" style="width: 100px;" maxlength="10"
					value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.partsSpendLimit}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
				</input>				
				--%>
			</tags:fieldError>
		</c:if>

		<c:if test="${showBillingEstimate}">
			<tags:fieldError id="Billing Estimate" oldClass="">
				<label>
					Billing Estimate
				</label>
				<br>
				
			$<s:textfield name="billingEstimate" id="billingEstimate" cssClass="shadowBox grayText" cssStyle="width: 100px;" maxlength="10"/>
			<br/>
			<%-- 				
			$ <input type="text" name="billingEstimate" id="billingEstimate"
					class="shadowBox grayText" style="width: 100px;" maxlength="10"
					value=<c:catch><fmt:formatNumber value='${sowPricingTabDTO.billingEstimate}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:catch>>
				</input>
			--%>
			</tags:fieldError>
		</c:if>
	</div>
</s:iterator>