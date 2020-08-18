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
<h2 class="jsifr">Found ${fn:length(searchPortalProviderFirmResultsVO)} Provider Firms </h2>


<div class="table-wrap">
	<table cellspacing="0" cellpadding="0" class="passwordReset" id="sort3">
	<thead>
	<tr>
	<!-- if permission -->
		<th id="administration" class="col1 first odd action">Administration</th>
		<!-- /if permission -->
		<th id="companyId" class="col2 even textleft">ID</th>
		<th id="proFirmStatus" class="col3 even textleft">Status</th>
		<th id="businessName" class="col4 even textleft">Business</th>
		<th id="lastActivityDate" class="col5 even textleft">Last Activity</th>
		<th id="marketName" class="col6 even textleft">Market</th>
		<th id="state" class="col7 even textleft">City/State</th>
	</tr>
	</thead>
	
<tbody id="Searchresult">
</tbody>
<tbody id="hiddenresult" style="display: none;">
	<!-- start loop -->
	<c:forEach items="${searchPortalProviderFirmResultsVO}" var="searchPortalProviderFirmResult">
		<tr class="result">
		<!-- if permission -->
		<!-- 
			<td class="col1 first even1 action">
				<a id="#action${searchPortalProviderFirmResult.user.userId}" />
				<a class="admin-menu" href="#action${searchPortalProviderFirmResult.user.userId}">Take Action <span>&raquo;</span></a>
				<ul class="admin menu">
					<li><a href="javascript:showEditProv('${searchPortalProviderFirmResult.user.companyId}', '-1')">Edit Profile</a></li>
					<li><a href="javascript:showProvDetails('${searchPortalProviderFirmResult.user.companyId}')">Adopt User</a></li>
					<li><a href="javascript:showProvDetails('${searchPortalProviderFirmResult.user.companyId}')">Reset2 Password</a></li>
				</ul>
			</td>
		 -->	

	<td class="col1  even1 action">
			<div id="tablePasswordMenu">				 
				      <div id="link1"><a d="#action${searchPortalProviderFirmResult.user.userId}" href="#" onmouseover="pwdMenuMouseOver('l1${searchPortalProviderFirmResult.user.companyId}')" onmouseout="pwdMenuMouseOut('l1${searchPortalProviderFirmResult.user.companyId}')"> Take Action &gt;&gt;</a></div>
				      <div class="tablePasswordMenu" onmouseover="pwdMenuMouseOver('l1${searchPortalProviderFirmResult.user.companyId}')" onmouseout="pwdMenuMouseOut('l1${searchPortalProviderFirmResult.user.companyId}')">
				        <ul id='l1${searchPortalProviderFirmResult.user.companyId}'>	
				        		<li> <a href="javascript:showEditProv('${searchPortalProviderFirmResult.user.companyId}', '-1')"> Edit Profile</a> </li>
	         	    			<li><a href="javascript:showProvDetails('${searchPortalProviderFirmResult.user.companyId}')">Adopt User</a></li>  
				       			<c:choose>
									<c:when test="${user.roleType == 'Admin'}">
										<c:choose>
											<c:when test="${passwordResetForSLAdmin == 'true'}">
												<li> <a id='${searchPortalProviderFirmResult.user.companyId}' href='javascript:void(0)' onClick="showResetModal(this, '${searchPortalProviderFirmResult.user.userId}');">Reset password</a> </li>
											</c:when>		
										</c:choose>
									</c:when>
									<c:otherwise>
									<c:choose>
										<c:when test="${passwordResetForAllExternalUsers == 'true'}">
											<li> <a id='${searchPortalProviderFirmResult.user.companyId}' href='javascript:void(0)' onClick="showResetModal(this, '${searchPortalProviderFirmResult.user.userId}');">Reset password</a> </li>	
										</c:when>		
									</c:choose>
									</c:otherwise>
								</c:choose>									
				        </ul>
			
				  	<jsp:include page="/jsp/admin/passwordModal.jsp" flush="true">
			 			<jsp:param name="modalId" value="${searchPortalProviderFirmResult.user.userId}"/>
			 			<jsp:param name="name" value="${searchPortalProviderFirmResult.user.firstName} ${searchPortalServiceProviderResult.user.lastName}"/>
			 			<jsp:param name="email" value="${searchPortalProviderFirmResult.location.emailAddress}"/>
			 			<jsp:param name="zip" value="${searchPortalProviderFirmResult.location.zip}"/>
			 			<jsp:param name="phone" value="${searchPortalProviderFirmResult.location.phoneNumber}"/>
			 			<jsp:param name="action" value="resetPasswordProvider"/>
					</jsp:include>
				
     			</td>
		
			<!-- /if permission -->
			<td class="col2 even1 textleft">${searchPortalProviderFirmResult.user.companyId}</td>
			<td class="col3 even1 textleft">${searchPortalProviderFirmResult.workflowState}</td>
			<td class=" even1 textleft" >
			${searchPortalProviderFirmResult.user.businessNameFormated}
			<div><span>
				${searchPortalProviderFirmResult.primaryIndustry}</span>
			</div>
			
			</td>
			<td class="col5 even1 textleft">
				<fmt:formatDate value="${searchPortalProviderFirmResult.user.lastActivityDate}" pattern="yyyy-MM-dd" />

			</td>
			<td class="col6 even1 textleft">${searchPortalProviderFirmResult.location.marketName}</td>
			<td class="col7 even textleft">${searchPortalProviderFirmResult.location.city}, ${searchPortalProviderFirmResult.location.state}</td>
			
			
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
