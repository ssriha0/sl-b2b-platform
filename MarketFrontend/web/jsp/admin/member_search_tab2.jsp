<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

					<div id="form" class="form_tab2">
						<div style="margin-right: 10px;" id="errorMessagesTop2" ></div>
						<form method="POST" id="searchPortalProviderFirm">
							<fieldset>
								<legend>User</legend>

								<div class="clearfix">
									<div class="left">
										<label>Firm ID</label>
										<input name="searchPortalProviderFirmVO.user.companyId" value="${searchPortalProviderFirmVO.user.companyId}" type="text" class="short text">
									</div>
									<div class="left">
										<label>User ID</label>
										<input name="searchPortalProviderFirmVO.user.userId" value="${searchPortalProviderFirmVO.user.userId}" type="text" class="short text">
									</div>
								</div>

								<label>Admin First Name or Last Name</label>
								<input name="searchPortalProviderFirmVO.user.fnameOrLname" value="${searchPortalProviderFirmVO.user.fnameOrLname}" type="text" class="text">


								<label>Username</label>
								<input name="searchPortalProviderFirmVO.user.userName" value="${searchPortalProviderFirmVO.user.userName}" type="text" class="text">

								<label>Provider Firm Name</label>
								<s:textfield name="searchPortalProviderFirmVO.user.businessName" value="%{#request['searchPortalProviderFirmVO'].user.businessName}" theme="simple"  cssClass="text" maxlength="25" />

								<label>Signup Date Between</label>
								<input name="searchPortalProviderFirmVO.user.fromSignUpDate" value="<fmt:formatDate value="${searchPortalProviderFirmVO.user.fromSignUpDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pfdate1">  <span class="ampersand">&</span>
								<input name="searchPortalProviderFirmVO.user.toSignUpDate" value="<fmt:formatDate value="${searchPortalProviderFirmVO.user.toSignUpDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pfdate2">

							</fieldset>

							<fieldset>
								<legend>Other</legend>
								<label>Service Order ID#</label>
								<s:textfield id="soId2" onkeyup="maskSOId('soId2');" onchange="maskSOId('soId2');" theme="simple" name="searchPortalProviderFirmVO.soId" value="%{#request['searchPortalProviderFirmVO'].soId}" cssClass="text serviceorder" maxlength="16" />

								<label>Primary Industry</label>
								<s:select name="searchPortalProviderFirmVO.primaryIndustryId" value="%{#request['searchPortalProviderFirmVO'].primaryIndustryId}" cssClass="select" headerKey="-1" headerValue="Select One"  theme="simple" list="primaryIndustryList" listKey="id" listValue="descr" />

								<label>Provider Firm Status</label>
								<s:select name="searchPortalProviderFirmVO.workflowStateId" value="%{#request['searchPortalProviderFirmVO'].workflowStateId}" cssClass="select" headerKey="-1" headerValue="Select One"  theme="simple" list="providerFirmStatusList" listKey="type" listValue="descr" />
								<label>Provider Firm Refferral</label>
								<s:select name="searchPortalProviderFirmVO.referralId" value="%{#request['searchPortalProviderFirmVO'].referralId}" cssClass="select" headerKey="-1" headerValue="Select One"  theme="simple" list="referralsList" listKey="id" listValue="descr" />
								
								<label>New SPN</label>
								<s:select name="searchPortalProviderFirmVO.spnetId" value="%{#request['searchPortalProviderFirmVO'].spnetId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="spnetList" listKey="id" listValue="descr" />

							</fieldset>

							<fieldset>
								<legend>Location</legend>

								<label>Phone Number</label>
								<input id="phoneId2" onkeyup="maskPhone('phoneId2');" onchange="maskPhone('phoneId2');" name="searchPortalProviderFirmVO.location.phoneNumber" value="${searchPortalProviderFirmVO.location.phoneNumber}" type="text" class="text phone" maxlength="12" />

								<label>Email Address</label>
								<input name="searchPortalProviderFirmVO.location.emailAddress" value="${searchPortalProviderFirmVO.location.emailAddress}" type="text" class="text email">

								<label>State</label>
								<s:select name="searchPortalProviderFirmVO.location.state" headerKey="-1" headerValue="Select State" cssClass="select" size="1" theme="simple" list="statesList" listKey="type" listValue="descr" />


								<div class="clearfix">
									<div class="left">
									<label>City</label>
									<s:textfield name="searchPortalProviderFirmVO.location.city" value="%{#request['searchPortalProviderFirmVO'].location.city}" theme="simple"  cssClass="short text" maxlength="25" />
									</div>


									<div class="left last">
									<label>ZIP</label>
									<input name="searchPortalProviderFirmVO.location.zip" value="${searchPortalProviderFirmVO.location.zip}" type="text" class="short text" maxLength="5">
									</div>

								</div>

								<label>Market</label>
								<s:select name="searchPortalProviderFirmVO.location.marketId" value="%{#request['searchPortalProviderFirmVO'].location.marketId}" cssClass="select" headerKey="-1" headerValue="Select One"  theme="simple" list="marketsList" listKey="type" listValue="descr" />

								<label>District</label>
								<s:select name="searchPortalProviderFirmVO.location.districtId" value="%{#request['searchPortalProviderFirmVO'].location.districtId}" cssClass="select" headerKey="-1" headerValue="Select One"  theme="simple" list="districtsList" listKey="type" listValue="descr" />

								<label>Region</label>
								<s:select name="searchPortalProviderFirmVO.location.regionId" value="%{#request['searchPortalProviderFirmVO'].location.regionId}" cssClass="select" headerKey="-1" headerValue="Select One"  theme="simple" list="regionsList" listKey="type" listValue="descr" />


							</fieldset>

						</form>
							<input class="action" type="submit"  value="Search" onclick="searchProviderFirm('','desc');" />
							<a class="cancel" href="#">Clear</a>
					</div>