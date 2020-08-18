<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

					<div id="form" class="form_tab3">
						<div style="margin-right: 10px;" id="errorMessagesTop3" ></div>
						<form method="POST" id="searchPortalBuyer">
						<div>
							<c:set var="checked_var" value="" />
							<c:if test="${searchPortalBuyerVO.isChildResultNeeded == true }">
								<c:set var="checked_var" value=" checked " />
							</c:if>
							<input type="checkbox" value="true" name="searchPortalBuyerVO.isChildResultNeeded" ${checked_var } /> Check to show Buyer Agents.
						</div>
							<fieldset>
								<legend>User</legend>
								
								<div class="clearfix">
									<div class="left">
										<label>User ID</label>
										<input name="searchPortalBuyerVO.user.userId" value="${searchPortalBuyerVO.user.userId}" type="text" class="short text" >
									</div>
									<div class="left">
										<label>Company ID</label>
										<s:textfield theme="simple" name="searchPortalBuyerVO.user.companyId" value="%{#request['searchPortalBuyerVO'].user.companyId}" cssClass="short text" maxlength="25" />
									</div>
								</div>
								
								<label>First Name or Last Name</label>
								<input name="searchPortalBuyerVO.user.fnameOrLname" value="${searchPortalBuyerVO.user.fnameOrLname}"  type="text" class="text">


								<label>Username</label>
								<input name="searchPortalBuyerVO.user.userName" value="${searchPortalBuyerVO.user.userName}"  type="text" class="text">

								<label>Business Name</label>
								<input name="searchPortalBuyerVO.user.businessName" value="${searchPortalBuyerVO.user.businessName}" type="text" class="text">
							
								<label>Signup Date Between</label>
								<input type="text" class="short text date bdate1" name="searchPortalBuyerVO.user.fromSignUpDate" value="<fmt:formatDate value="${searchPortalBuyerVO.user.fromSignUpDate}" pattern="MM/dd/yyyy" />">  
								<span class="ampersand">&</span> 
								<input type="text" class="short text date bdate2" name="searchPortalBuyerVO.user.toSignUpDate" value="<fmt:formatDate value="${searchPortalBuyerVO.user.toSignUpDate}" pattern="MM/dd/yyyy" />">
							
							</fieldset>

							<fieldset>
								<legend>Other</legend>
								<label>Service Order ID#</label>
								<s:textfield id="soId3" onkeyup="maskSOId('soId3');" onchange="maskSOId('soId3');" theme="simple" name="searchPortalBuyerVO.soId" cssClass="text serviceorder" value="%{#request['searchPortalBuyerVO'].soId}" maxlength="16" />								
							</fieldset>

							<fieldset>
								<legend>Location</legend>
							
								<label>Phone Number</label>
								<input id="phoneId3" onkeyup="maskPhone('phoneId3');" onchange="maskPhone('phoneId3');" name="searchPortalBuyerVO.location.phoneNumber" value="${searchPortalBuyerVO.location.phoneNumber}" type="text" class="text phone" maxlength="12" />

								<label>Email Address</label>
								<input name="searchPortalBuyerVO.location.emailAddress" value="${searchPortalBuyerVO.location.emailAddress}" type="text" class="text email">

								<label>State</label>
								<s:select name="searchPortalBuyerVO.location.state" headerKey="-1" headerValue="Select State" cssClass="select" size="1" theme="simple" list="statesList" listKey="type" listValue="descr" />
								

								<div class="clearfix">
									<div class="left">
									<label>City</label>
									<s:textfield theme="simple" name="searchPortalBuyerVO.location.city" value="%{#request['searchPortalBuyerVO'].location.city}" cssClass="short text" maxlength="25" />
									</div>
								
									<div class="left last">
									<label>ZIP</label>
									<input name="searchPortalBuyerVO.location.zip" value="${searchPortalBuyerVO.location.zip}" type="text" class="short text" maxLength="5">
									</div>
								
								</div>

							</fieldset>	
						</form>
							<input class="action" type="submit"  value="Search" onclick="searchBuyer('','desc');" />
							<a class="cancel" href="#">Clear</a>
					</div>