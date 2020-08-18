
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<s:if test="hasActionErrors() or hasFieldErrors()">
 		<div style="display: none;" id="errorMessagesRes" >
          <%@ include file="../providerRegistration/message.jsp"%>
        </div>
</s:if>

<h2 class="jsifr">Found ${fn:length(SearchPortalServiceProviderResultsVO)} Service Providers </h2>
<div class="table-wrap">
	<table cellspacing="0" cellpadding="0" class="passwordReset" id="sort3">
	<thead>
	<tr>
	<!-- if permission -->
		<th id="administration" class="col1 first odd action">Administration</th>
		<!-- /if permission -->
		<th id="userId" class="col2 even textleft">ID</th>
		<th id="serviceProStatus" class="col3 even textleft">Status</th>
		<th id="firstName" class="col4 even textleft">Business</th>
		<th id="lastActivityDate" class="col5 even textleft">Last Activity</th>
		<th id="marketName" class="col6 even textleft">Market</th>
		 <th id="city" class="col7 even textleft">City/State</th>
	</tr>
	</thead>
	
	 <tbody id="Searchresult">
     </tbody>
     <tbody id="hiddenresult" style="display: none;">
	<!-- start loop -->
	<c:forEach items="${SearchPortalServiceProviderResultsVO}" var="searchPortalServiceProviderResult">
		<tr class="result">
		<!-- if permission -->
			<td class="col1 first even action textleft">
		
										<div id="tablePasswordMenu">										
									          <div id="link1"><a  id="#action${searchPortalServiceProviderResult.user.userId}" 
									          href="#action${searchPortalServiceProviderResult.user.userId}" onmouseover="pwdMenuMouseOver('l1${searchPortalServiceProviderResult.user.userId}')" onmouseout="pwdMenuMouseOut('l1${searchPortalServiceProviderResult.user.userId}')" > Take Action&gt;&gt;</a>
									          </div>									       
										      <div class="tablePasswordMenu" onmouseover="pwdMenuMouseOver('l1${searchPortalServiceProviderResult.user.userId}')" onmouseout="pwdMenuMouseOut('l1${searchPortalServiceProviderResult.user.userId}')" >
										        <ul id='l1${searchPortalServiceProviderResult.user.userId}'>
							         	    			 
						   							<li><a href="javascript:showEditProv('${searchPortalServiceProviderResult.user.companyId}', '${searchPortalServiceProviderResult.user.userId}')">Edit Profile</a></li>
													<li><a href="javascript:showProvDetails('${searchPortalServiceProviderResult.user.companyId}')">Adopt User</a></li>
																	
													<c:choose>
															<c:when test="${user.roleType == 'Admin'}">
																<c:choose>
																<c:when test="${passwordResetForSLAdmin == 'true'}">
																	<li> <a id=${searchPortalServiceProviderResult.user.userId} href="javascript:void(0)" 
															onclick="javascript:showResetModal(this, '${searchPortalServiceProviderResult.user.userId}');">Reset password</a> </li>
																</c:when>		
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																<c:when test="${passwordResetForAllExternalUsers == 'true'}">
																	<li> <a id=${searchPortalServiceProviderResult.user.userId} href="javascript:void(0)" 
															onclick="javascript:showResetModal(this, '${searchPortalServiceProviderResult.user.userId}');">Reset password</a> </li>
																</c:when>		
																</c:choose>
															</c:otherwise>
														</c:choose>
														
												<!-- SL-18330 Changes start -->	
												<!--Sl-20608 Changes starts-->
												<c:set var="userFirstName" value="${searchPortalServiceProviderResult.user.firstName}"/>
												<c:set var="userMiddleName" value="${searchPortalServiceProviderResult.user.middleName}"/>
												<c:set var="userLastName" value="${searchPortalServiceProviderResult.user.lastName}"/>
												<c:set var="singlequote" value="'"/>
												<c:set var="doublequote" value='"'/>
												
												<!-- change to escape single quotes starts-->
												<c:if test="${fn:contains(userFirstName, singlequote)}">
													<c:set var="replace" value="\\'" />
													<c:set var="userFirstName" value="${fn:replace(userFirstName, singlequote, replace)}"/>
												</c:if>
												
												<c:if test="${fn:contains(userMiddleName, singlequote)}">
													<c:set var="replace" value="\\'" />
													<c:set var="userMiddleName" value="${fn:replace(userMiddleName, singlequote, replace)}"/>
												</c:if>
												
												<c:if test="${fn:contains(userLastName, singlequote)}">
													<c:set var="replace" value="\\'" />
													<c:set var="userLastName" value="${fn:replace(userLastName, singlequote, replace)}"/>
												</c:if>
												
												<!-- change to escape single quotes ends-->
												
												<!-- change to escape Double quotes starts-->
												<c:if test="${fn:contains(userFirstName, doublequote)}">
													<c:set var="userFirstName" value="${fn:escapeXml(userFirstName)}"/>
												</c:if>
												<c:if test="${fn:contains(userMiddleName, doublequote)}">
													<c:set var="userMiddleName" value="${fn:escapeXml(userMiddleName)}"/>
												</c:if>
												<c:if test="${fn:contains(userLastName, doublequote)}">
													<c:set var="userLastName" value="${fn:escapeXml(userLastName)}"/>
												</c:if>
												
												<!-- change to escape Double quotes ends-->
												
												
												<c:choose>
												<c:when test="${permissionForProviderNameChange == 'true'}">
												   <!-- SL-20049 Changes start -->	
												   <!--   <li><a href="javascript:showProviderNameDetails('${searchPortalServiceProviderResult.user.userId}','${searchPortalServiceProviderResult.user.companyId}','${searchPortalServiceProviderResult.user.firstName}','${searchPortalServiceProviderResult.user.middleName}','${searchPortalServiceProviderResult.user.lastName}','${loginusername}')">Correct Pro Name</a></li> -->
												  

												  <li><a href="javascript:showProviderNameDetails('${searchPortalServiceProviderResult.user.userId}','${searchPortalServiceProviderResult.user.companyId}','${userFirstName}','${userMiddleName}','${userLastName}','${loginusername}','${searchPortalServiceProviderResult.slNameChange}')">Correct Pro Name</a></li>      
												<!-- SL-20049 Changes ends -->
												<!--Sl-20608 Changes ends-->
												 </c:when>		
												</c:choose>												
												<!-- SL-18330 Changes end -->
												
												</ul>

					<jsp:include page="/jsp/admin/passwordModal.jsp" flush="true">
			 			<jsp:param name="modalId" value="${searchPortalServiceProviderResult.user.userId}"/>
			 			<jsp:param name="name" value="${searchPortalServiceProviderResult.user.firstName} ${searchPortalServiceProviderResult.user.lastName}"/>
			 			<jsp:param name="email" value="${searchPortalServiceProviderResult.location.emailAddress}"/>
			 			<jsp:param name="zip" value="${searchPortalServiceProviderResult.location.zip}"/>
			 			<jsp:param name="phone" value="${searchPortalServiceProviderResult.location.phoneNumber}"/>
			 			<jsp:param name="action" value="resetPasswordProvider"/>
					</jsp:include>	
     			</td>
		
			<!-- /if permission -->
			<td class="col2 even textleft">${searchPortalServiceProviderResult.user.userId}</td>
			<td class="col3 even textleft">${searchPortalServiceProviderResult.serviceProStatus}</td>
			<td class="col4 even textleft">
			<a href="${contextPath}/providerProfileInfoAction_execute.action?resourceId=${searchPortalServiceProviderResult.user.userId}&popup=true">
			${searchPortalServiceProviderResult.user.firstName} ${searchPortalServiceProviderResult.user.lastName}</a>
			<div><span>
				${searchPortalServiceProviderResult.user.businessNameFormated}</span>
			</div>
			<div><span>
				Company Id: ${searchPortalServiceProviderResult.user.companyId}</span>
			</div>
			<div><span>
				${searchPortalServiceProviderResult.primaryIndustry}</span>
			</div>
			</td>
			<td class="col5 even textleft">
				<fmt:formatDate value="${searchPortalServiceProviderResult.user.lastActivityDate}" pattern="yyyy-MM-dd" />


			</td>
			<td class="col6 even textleft">${searchPortalServiceProviderResult.location.marketName}</td>
			<td class="col7 even textleft">${searchPortalServiceProviderResult.location.city}, ${searchPortalServiceProviderResult.location.state}</td>
			
			
		</tr>
	</c:forEach>
	<!-- end loop -->
			
	</tbody>
	</table>
</div>
<div id="Pagination" class="pagination">
</div>
<form id="hiddenProviderFirmForm" name="hiddenProviderFirmForm">
<input type="hidden" name="searchPortalProviderFirmVO.user.companyId" id="spfResults.companyId" />
<input type="hidden" name="searchPortalProviderFirmVO.user.userId" id="spfResults.userId" />
<input type="hidden" name="resourceId" id="spfResults.resourceId" />
</form>
