<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
			



<div class="rightRailWidget">
	<div class="darkGrayModuleHdr" style="width: 246px;">
		Add Addtional SPN Criteria
	</div>
	<div class="grayModuleContent">
		

		<hr />
		<p>

			<strong>Resource Credentials</strong>
			<br />
			<s:select name="theCriteria.resourceCredential" id="rcredentials" headerKey="-1"
				headerValue="-- Select One --" list="%{credentialsList}"
				listKey="id" listValue="descr" size="1" theme="simple"
				cssStyle="width: 100%; background-color: #ffffff;"
				onchange="newco.jsutils.loadResourceSkillsAndCategories('rcredentials','spnBuyerCriteriaBuilderAction')"
				disabled="false" />
			<br />
			<s:select name="theCriteria.resourceCategory" id="credentialCategory"
				headerKey="-1" headerValue="-- Select One --"
				list="commonResourceList" listKey="id" listValue="descr" size="3"
				theme="simple" cssStyle="width: 100%; background-color: #ffffff;"
				disabled="false" />

		</p>

		<hr />
		<strong>Company Credentials</strong>
			<br />
			<s:select name="theCriteria.credentials" id="ccredentials" headerKey="-1"
				headerValue="-- Select One --" list="%{credentialsList}"
				listKey="id" listValue="descr" size="1" theme="simple"
				cssStyle="width: 100%; background-color: #ffffff;"
				onchange="newco.jsutils.loadCompanySkillsAndCategories('ccredentials','spnBuyerCriteriaBuilderAction')"
				disabled="false" />
				<br />
			<s:select name="theCriteria.credentialCategory" id="credentialCategory2"
				headerKey="-1" headerValue="-- Select One --"
				list="commonCompanyCredList" listKey="id" listValue="descr" size="3"
				theme="simple" cssStyle="width: 100%; background-color: #ffffff;"
				disabled="false" />
		<hr>
		
		<p>

			<strong>Languages</strong>
			<br />
			<s:select name="theCriteria.languages" id="languages" headerKey="-1"
				headerValue="-- Select One --" list="%{languagesList}" listKey="id"
				listValue="descr" theme="simple"
				cssStyle="width: 100%; background-color: #ffffff;"
				multiple="false" size="1" disabled="%{#attr['filtersDisabled']}" />
		</p>



		<hr />

		<p>
			<strong>Ratings (at least)</strong>
			<br />

			<s:select name="theCriteria.ratings" id="ratings" headerKey="-1"
				headerValue="-- Select One --" list="%{ratingsList}" listKey="key"
				listValue="value" size="1" theme="simple"
				cssStyle="width: 55%; background-color: #ffffff;"
				disabled="%{#attr['filtersDisabled']}" />
				<s:checkbox name="theCriteria.includeNonRated" />
				<span style="font-size:8px;">Include non-rated</span> <br>
		</p>
		<hr>
		<strong>Insurance</strong><br>
		Vehicle Liability Insurance<br>
		<s:checkbox name="theCriteria.vliInsurance" id="vliInsurance" 
					onclick="newco.jsutils.validateVal(this,'vliInsuranceVal')"/>
		<s:textfield name="theCriteria.vliInsuranceAmount" cssClass="shadowBox" size="10" disabled="true" id="vliInsuranceVal" 
					 onblur="newco.jsutils.isNumberChar(this,'vliInsuranceVal')"/>
		<br>
		Workers Compensation Insurance<br>
		<s:checkbox name="theCriteria.wciInsurance"  id="wciInsurance" onclick="newco.jsutils.validateVal(this,'wciInsuranceVal')"/>
		<s:textfield name="theCriteria.wciInsuranceAmount" cssClass="shadowBox" size="10" disabled="true" id="wciInsuranceVal"
					 onblur="newco.jsutils.isNumberChar(this,'wciInsuranceVal')"/> <br>
		Commercial General Liability Insurance<br>
		<s:checkbox name="theCriteria.cgliInsurance" id="cgliInsurance" onclick="newco.jsutils.validateVal(this,'cgliInsuranceVal')"/>
		<s:textfield name="theCriteria.cgliInsuranceAmount"  cssClass="shadowBox" size="10" disabled="true" id="cgliInsuranceVal"
					 onblur="newco.jsutils.isNumberChar(this,'cgliInsuranceVal')"/>
		<br /><hr>
		<strong>Service Orders Closed</strong><br>
		Minimun number of closed service orders the Provider has executed<br>
		<s:textfield name="theCriteria.totalNumOfServiceOrdersClosed" cssClass="shadowBox" size="10"/>
		<c:if test="${filtersDisabled == 'false'}">
			<input type="image"
				src="${staticContextPath}/images/common/spacer.gif"
				style="background-image: url(${staticContextPath}/images/btn/apply.gif); width:55px; height:20px;"
				class="btn20Bevel inlineBtn" 
				/>
		</c:if>
		
		
			

	</div>
</div>


