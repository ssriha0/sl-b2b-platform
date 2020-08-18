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
<h2 class="jsifr">Found ${fn:length(searchPortalBuyerResultsVO)} Buyers </h2>
<div class="table-wrap">
	<table cellspacing="0" cellpadding="0" class="passwordReset" id="sort3">
	<thead>
	<tr>
	<!-- if permission -->
		<th id="administration" class="col1 first odd action">Administration</th>
		<!-- /if permission -->
		<th id="userId" class="col2 even textleft">ID</th>
		<th id="roleType" class="col3 even textleft">Role Type</th>
		<th id="lastName" class="col4 even textleft">Name</th>
		<th id="lastActivityDate" class="col5 even textleft">Last Activity</th>
		<th id="marketName" class="col6 even textleft">Market</th>
		<th id="state" class="col7 even textleft">State</th>
	</tr>
	</thead>
	<tbody id="Searchresult">
    </tbody>
    <tbody id="hiddenresult" style="display: none;">

	<!-- start loop -->
	<c:forEach items="${searchPortalBuyerResultsVO}" var="searchPortalBuyerResult">
		<tr class="result">
		<!-- if permission -->
			<td class="col1 first even action textleft">
			<!-- 
				<a id="#action${searchPortalBuyerResult.user.userId}" />
				<a class="admin-menu" href="#action${searchPortalBuyerResult.user.userId}">Take Action <span>&raquo;</span></a>
				<ul class="admin menu">					
					<li><a href="javascript:showBuyerDetails('${searchPortalBuyerResult.user.companyId}','${searchPortalBuyerResult.user.userId}','${searchPortalBuyerResult.user.roleTypeId}','${searchPortalBuyerResult.user.userName}','${searchPortalBuyerResult.user.businessName}','${searchPortalBuyerResult.location.city}', '${searchPortalBuyerResult.location.state}','${searchPortalBuyerResult.user.adminName}')">Adopt User</a></li>
				</ul>
				<li> <a id=${searchPortalServiceProviderResult.user.userId} href="#${searchPortalServiceProviderResult.user.userId}" 
															onclick="javascript:showResetModal(this, '${searchPortalBuyerResult.user.userId}');">Reset password</a> </li>
				 -->											
				
				<div id="tablePasswordMenu">										
					<div id="link1"><a id="#action${searchPortalBuyerResult.user.userId}" 
						          href="#action${searchPortalBuyerResult.user.userId}" onmouseover="pwdMenuMouseOver('l1${searchPortalBuyerResult.user.userId}')" onmouseout="pwdMenuMouseOut('l1${searchPortalBuyerResult.user.userId}')" > Take Action &gt;&gt;</a>
					</div>									       
					<div class="tablePasswordMenu" onmouseover="pwdMenuMouseOver('l1${searchPortalBuyerResult.user.userId}')" onmouseout="pwdMenuMouseOut('l1${searchPortalBuyerResult.user.userId}')" >
						<ul id='l1${searchPortalBuyerResult.user.userId}'>
														
						<li><a href="javascript:showBuyerDetails('${searchPortalBuyerResult.user.companyId}','${searchPortalBuyerResult.user.userId}','${searchPortalBuyerResult.user.roleTypeId}','${searchPortalBuyerResult.user.userName}','${searchPortalBuyerResult.user.businessName}','<c:out value="${fn:replace(searchPortalBuyerResult.location.city, '\\'', '\\\\\\'')}"/>', '${searchPortalBuyerResult.location.state}','${searchPortalBuyerResult.user.adminName}')">Adopt User</a></li>
						<!-- 
						<li><a id=${searchPortalBuyerResult.user.userId} href="#${searchPortalBuyerResult.user.userId}" 
											onclick="javascript:showResetModal(this, '${searchPortalBuyerResult.user.userId}');">Reset password</a> </li>
						-->
							<c:choose>
								<c:when test="${user.roleType == 'Admin'}">
								<c:choose>
									<c:when test="${passwordResetForSLAdmin == 'true'}">
										<li> <a id=${searchPortalServiceProviderResult.user.userId} href="javascript:void(0)" 
												onclick="javascript:showResetModal(this, '${searchPortalBuyerResult.user.userId}');">Reset password</a> </li>
									</c:when>		
								</c:choose>
								</c:when>
								<c:otherwise>
								<c:choose>
									<c:when test="${passwordResetForAllExternalUsers == 'true'}">
										<li> <a id=${searchPortalServiceProviderResult.user.userId} href="javascript:void(0)" 
												onclick="javascript:showResetModal(this, '${searchPortalBuyerResult.user.userId}');">Reset password</a> </li>
									</c:when>		
								</c:choose>
								</c:otherwise>
							</c:choose>						 	
						</ul>
						<c:choose>
							<c:when test="${searchPortalBuyerResult.user.lastName == 'UNKNOWN' }">
								<jsp:include page="/jsp/admin/passwordModal.jsp" flush="true">
						 			<jsp:param name="modalId" value="${searchPortalBuyerResult.user.userId}"/>
						 			<jsp:param name="name" value="${searchPortalBuyerResult.user.adminFName} ${searchPortalBuyerResult.user.adminLName}"/>
						 			<jsp:param name="email" value="${searchPortalBuyerResult.location.emailAddress}"/>
						 			<jsp:param name="zip" value="${searchPortalBuyerResult.location.zip}"/>
						 			<jsp:param name="phone" value="${searchPortalBuyerResult.location.phoneNumber}"/>
						 			<jsp:param name="action" value="resetPasswordBuyer"/>
								</jsp:include>
							</c:when>	
							<c:otherwise>					
								<jsp:include page="/jsp/admin/passwordModal.jsp" flush="true">
						 			<jsp:param name="modalId" value="${searchPortalBuyerResult.user.userId}"/>
						 			<jsp:param name="name" value="${searchPortalBuyerResult.user.firstName} ${searchPortalBuyerResult.user.lastName}"/>
						 			<jsp:param name="email" value="${searchPortalBuyerResult.location.buyerEmailAddress}"/>
						 			<jsp:param name="zip" value="${searchPortalBuyerResult.location.buyerZip}"/>
						 			<jsp:param name="phone" value="${searchPortalBuyerResult.location.buyerPhoneNumber}"/>
						 			<jsp:param name="action" value="resetPasswordBuyer"/>
								</jsp:include>
							</c:otherwise>
						</c:choose>	

     			   </div>
     			 </div>	
     			
			</td>
			<!-- /if permission -->
			<td class="col2 even textleft">${searchPortalBuyerResult.user.userId}</td>
			<td class="col3 even textleft">${searchPortalBuyerResult.user.roleType}</td>
			<td class="col4 even textleft">
				<c:if test="${searchPortalBuyerResult.user.lastName == 'UNKNOWN' }">
					${searchPortalBuyerResult.user.adminLName}, ${searchPortalBuyerResult.user.adminFName}
				</c:if>
				<c:if test="${searchPortalBuyerResult.user.lastName != 'UNKNOWN' }">
					${searchPortalBuyerResult.user.lastName}, ${searchPortalBuyerResult.user.firstName}
				</c:if>
			<br>
			<div><span>
				${searchPortalBuyerResult.user.businessName}</span>
			</div>
			<div><span>
				Company Id: ${searchPortalBuyerResult.user.companyId}</span>
			</div>
			</td>
			<td class="col5 even textleft">
				<fmt:formatDate value="${searchPortalBuyerResult.user.lastActivityDate}" pattern="yyyy-MM-dd" />

			</td>
			<td class="col6 even textleft">${searchPortalBuyerResult.location.marketName}</td>
			<td class="col7 even textleft">${searchPortalBuyerResult.location.state}</td>
		</tr>
	</c:forEach>
	<!-- end loop -->
	</tbody>
	</table>
</div>
<div id="Pagination" class="pagination">
</div>
<form id="hiddenBuyerForm" name="hiddenBuyerForm">
<input type="hidden" name="searchPortalBuyerVO.user.companyId" id="buyerResults.companyId" />
<input type="hidden" name="searchPortalBuyerVO.user.userId" id="buyerResults.userId" />
<input type="hidden" name="searchPortalBuyerVO.user.roleTypeId" id="buyerResults.roleTypeId" />
<input type="hidden" name="searchPortalBuyerVO.user.adminName" id="buyerResults.adminName" />
<input type="hidden" name="searchPortalBuyerVO.user.userName" id="buyerResults.userName" />
<input type="hidden" name="searchPortalBuyerVO.user.businessName" id="buyerResults.businessName" />
<input type="hidden" name="searchPortalBuyerVO.location.city" id="buyerResults.city" />
<input type="hidden" name="searchPortalBuyerVO.location.state" id="buyerResults.state" />
<input type="hidden" name="resourceId" id="buyerResults.resourceId" />

</form>