<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">
	function selectService()
	{
		jQuery.noConflict();
	
		var primarySkill = document.getElementById('ddservices').value;
		jQuery(document).ready(function($) {
			$("#ddcategory_fill").load("adminSearch_getSecondarySkill1.action?searchPortalServiceProviderVO.primaryVerticleId=" + primarySkill, function() {
				if (primarySkill == -1)
				{
					document.getElementById('ddcategory').disabled = true;
					document.getElementById('ddcategory').value = -1;
				}
				else
				{
					document.getElementById('ddcategory').disabled = false;
				}
				document.getElementById('ddsubcategory').disabled = true;
				document.getElementById('ddsubcategory').value = -1;
			});
		});
	}
	
	function selectCategory()
	{
		jQuery.noConflict();
	
		var categoryId = document.getElementById('ddcategory').value;
		jQuery(document).ready(function($) {
			$("#ddsubcategory_fill").load("adminSearch_getSecondarySkill2.action?searchPortalServiceProviderVO.secondarySkillId=" + categoryId , function() {
				if (categoryId == -1)
				{
					document.getElementById('ddsubcategory').disabled = true;
					document.getElementById('ddsubcategory').value = -1;
				}
				else
				{
					document.getElementById('ddsubcategory').disabled = false;
				}
			});
		});
	}
</script>
					
					<div id="form" class="form_tab1">
						<div style="margin-right: 10px;" id="errorMessagesTop1" ></div>
						<form method="POST" id="searchPortalServiceProvider">
							<fieldset>
								<legend>User</legend>
								
								<label>User ID</label>
								<input name="searchPortalServiceProviderVO.user.userId" value="${searchPortalServiceProviderVO.user.userId}" type="text" class="short text">
							
								<label>First Name or Last Name</label>
								<input name="searchPortalServiceProviderVO.user.fnameOrLname" value="${searchPortalServiceProviderVO.user.fnameOrLname}" type="text" class="text">


								<label>Username</label>
								<input name="searchPortalServiceProviderVO.user.userName" value="${searchPortalServiceProviderVO.user.userName}" type="text" class="text">

								<label>Provider Firm</label>
								<input name="searchPortalServiceProviderVO.user.businessName" value="${searchPortalServiceProviderVO.user.businessName}" type="text" class="text">
							
								<label>Signup Date Between</label>
								<input name="searchPortalServiceProviderVO.user.fromSignUpDate" value="<fmt:formatDate value="${searchPortalServiceProviderVO.user.fromSignUpDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pdate1">  <span class="ampersand">&</span> 
								<input name="searchPortalServiceProviderVO.user.toSignUpDate" value="<fmt:formatDate value="${searchPortalServiceProviderVO.user.toSignUpDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pdate2">
							
							</fieldset>

							<fieldset>
								<legend>Other</legend>
								<label>Service Order ID#</label>
								<s:textfield id="soId1" onkeyup="maskSOId('soId1');" onchange="maskSOId('soId1');" theme="simple" name="searchPortalServiceProviderVO.soId" value="%{#request['searchPortalServiceProviderVO'].soId}" cssClass="text serviceorder" maxlength="16" />								

								<label>Services</label>
								<s:select id="ddservices" onchange="selectService();" name="searchPortalServiceProviderVO.primaryVerticleId" value="%{#request['searchPortalServiceProviderVO'].primaryVerticleId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="primarySkillsList" listKey="type" listValue="descr" />

								<label>Category</label>
								<div id="ddcategory_fill">
									<jsp:include page="/jsp/admin/member_search_secondarySkill1.jsp" />
								</div>

								<label>Sub Category</label>
								<div id="ddsubcategory_fill">
									<jsp:include page="/jsp/admin/member_search_secondarySkill2.jsp" />
								</div>

								<label>Service Provider Status</label>
								<s:select name="searchPortalServiceProviderVO.workflowStateId" value="%{#request['searchPortalServiceProviderVO'].workflowStateId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="serviceProviderStatusList" listKey="id" listValue="descr" />


								<label>Background Check Status</label>
								<s:select name="searchPortalServiceProviderVO.bgCheckStateId" value="%{#request['searchPortalServiceProviderVO'].bgCheckStateId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="backgroundStatusList" listKey="type" listValue="descr" />


								<label>Select Provider Network</label>
								<s:select name="searchPortalServiceProviderVO.spnId" value="%{#request['searchPortalServiceProviderVO'].spnId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="providerNetworkList" listKey="type" listValue="descr" />

								<label>New SPN</label>
								<s:select name="searchPortalServiceProviderVO.spnetId" value="%{#request['searchPortalServiceProviderVO'].spnetId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="spnetList" listKey="id" listValue="descr" />

								
							</fieldset>

							<fieldset>
								<legend>Location</legend>
							
								<label>Phone Number</label>
								<input id="phoneId1" onkeyup="maskPhone('phoneId1');" onchange="maskPhone('phoneId1');" name="searchPortalServiceProviderVO.location.phoneNumber" value="${searchPortalServiceProviderVO.location.phoneNumber}" type="text" class="text phone" maxlength="12" />

								<label>Email Address</label>
								<input name="searchPortalServiceProviderVO.location.emailAddress" value="${searchPortalServiceProviderVO.location.emailAddress}" type="text" class="text email">

								<label>State</label>
								<s:select name="searchPortalServiceProviderVO.location.state" headerKey="-1" headerValue="Select One" cssClass="select" theme="simple" list="statesList" listKey="type" listValue="descr" />


								<div class="clearfix">
									<div class="left">
									<label>City</label>
									<s:textfield theme="simple" name="searchPortalServiceProviderVO.location.city" value="%{#request['searchPortalServiceProviderVO'].location.city}" cssClass="short text" maxlength="25" />
									</div>
								

									<div class="left last">
									<label>ZIP</label>
									<input name="searchPortalServiceProviderVO.location.zip" value="${searchPortalServiceProviderVO.location.zip}" type="text" class="short text" maxLength="5">
									</div>
								
								</div>

								<label>Market</label>
								<s:select name="searchPortalServiceProviderVO.location.marketId" value="%{#request['searchPortalServiceProviderVO'].location.marketId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="marketsList" listKey="type" listValue="descr" />



								<label>District</label>
								<s:select name="searchPortalServiceProviderVO.location.districtId" value="%{#request['searchPortalServiceProviderVO'].location.districtId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="districtsList" listKey="type" listValue="descr" />


								<label>Region</label>
								<s:select name="searchPortalServiceProviderVO.location.regionId" value="%{#request['searchPortalServiceProviderVO'].location.regionId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="regionsList" listKey="type" listValue="descr" />


							</fieldset>
	
						</form>
						<input class="action" type="button"  value="Search" onclick="searchServiceProvider('','desc');" />
						<a class="cancel" href="#">Clear</a>
					</div>
					