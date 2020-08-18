<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="page" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link rel="stylesheet" type="text/css" 	href="${staticContextPath}/css/service_order_wizard.css"/>
<link rel="stylesheet" type="text/css" 	href="${staticContextPath}/css/global.css"/>
	
<div class="hpDescribe clearfix">
	<h2>Service Pros</h2>	
	<fieldset>
		<legend>Search for Service Pros</legend>
		<form id="etmSearch">
		
		<input type="hidden" name="vendorId" id="vendorId" value="${vendorId}">
		<input type="hidden" name="popup" id="popup" value="${popup}">
		<input type="hidden" name="sortColumnName" id="sortColumnName" value="${sortColumnName}">
		<input type="hidden" name="sortOrder" id="sortOrder" value="${sortOrder}">
	
		<input type="hidden" name="startIndex" id="startIndex">
		<input type="hidden" name="endIndex" id="endIndex">
		<input type="hidden" name="pageSize" id="pageSize">
		<input type="hidden" name="search" id="search" value="N">

		<table width="90%">
		<tr>
			<td align="center">
				<table width="100%">
					<tr>
						<td>
							<strong>Find a Service Pro in this Firm by searching on first name, last name or user resource id.</strong>
						</td>
					</tr>
					<tr>
						<td>
							<div class="content" style="margin-left:12px;">
								<div class="lightGrayBox">
									<table width="100%" border=0>
										<tr>
											<td width="15%" style="padding-left: 10px">
												<strong>First or Last Name</strong>
												<br>
												<input type="text" name="flname" id="flname" class="shadowBox grayText" style="width: 100px;" value=<c:out value="${flname}" />>	
											</td>
											<td width="15%" style="padding-left: 10px">
												<strong>User ID</strong>
												<br>
												<input type="text" name="userId" id="userId" class="shadowBox grayText" style="width: 100px;" value=<c:out value="${userId}" />>	
											</td>
										</tr>
										<tr>
											<td style="padding-left: 15px" >
												<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="76px"
													height="20px" class="btnBevel inlineBtn" alt="Search"
													theme="simple"
													style="background-image: url(${staticContextPath}/images/btn/search.gif); "
												onclick="startSearch(); return false;"/>&nbsp;
											<a href='javascript:clear()'>Clear</a>
										</td>
										</tr>
										<tr>
											<td colspan=2 height=8></td>
										</tr>
									</table>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
	<c:if test="${not empty validationErrors}">
		<div class="errorBox clearfix" style="width: 69%; visibility: visible; margin-left: 23px;">
		<c:forEach items="${validationErrors}" var="error">
			<p class="errorMsg">
				&nbsp;&nbsp;&nbsp;&nbsp;${error.fieldId} -
				${error.msg}
			</p>		
		</c:forEach>
		</div>
		<br>
	</c:if>
	 </fieldset>	
		
	<div style="margin-left: 12px; width: 100%;">
	<div style="margin-bottom:5px; text-align:right;">
		<p style="padding-right:10px;"> <strong>${paginationVO.totalRecords}</strong> Provider(s) found</p>
	</div>
	<table class="provSearchHdr" cellpadding="0" cellspacing="0" border=0  style="margin-top:0; width:100%; margin-left: -12px">
	<tr>
		<td style="padding-left:12px;">
		<div style="float:left;">
		<a href="javascript: sortByColumn2('flName','${contextPath}/providerProfileFirmInfoAction_search.action');">
			Provider</a>&nbsp;
		</div>
		<img src=<c:choose><c:when test="${sortOrder == 'DESC'}">"${staticContextPath}/images/grid/arrow-down-white.gif"</c:when><c:otherwise>"${staticContextPath}/images/grid/arrow-up-white.gif"</c:otherwise></c:choose> 
			class="funky_img" border="0" float:left; margin:6px" id="sortByflName" />
		</td>
		<td>
		<div style="float:left;">Credentials</div>
		</td>	
	</tr>
	</table>
	</div>
	<div id="result" class="grayTableContainer" style="margin-left: 0px; width: 100%; height: 407px;">
	<table class="provSearch" cellpadding="0" cellspacing="0" border=0 style="width:100%">
		<c:choose>
			<c:when test="${empty validationErrors && not empty teamMembers}">
				<c:forEach items="${teamMembers}" var="teamMembers">
							<tr>
							<td style="width: 40%">
							<div class="credlist clearfix" style="margin-top: 5px; width:70%;">
								<iframe width="65x" scrolling="no" height="65px" marginwidth="0"
									marginheight="0" style="float: left; margin-right: 5px;"
									frameborder="0"
									src="${contextPath}/providerProfileInfoAction_loadPhoto.action?max_width=65&max_height=65&resourceId=${teamMembers.resourceId}&link=1&isPopup=${popup}"
									style="float: left;">
								</iframe>
								<h4>
									<a
										href="${contextPath}/providerProfileInfoAction_execute.action?resourceId=${teamMembers.resourceId}&popup=true">${teamMembers.firstName}
										<c:choose>
											<c:when test="${not empty teamMembers.lastName}">
												<c:out value="${fn:substring(teamMembers.lastName,0,1)}" />.
																	</c:when>
										</c:choose> </a>
								</h4>
								ID#${teamMembers.resourceId}
								<br />
								<br />
								${teamMembers.title}
								<br>
							</div>
							</td>
							<td style="width: 60%">
							<c:choose><c:when test="${not empty teamMembers.teamCredentials}">
								<c:forEach items="${teamMembers.teamCredentials}" var="credinfo">
									<div class="credlist">
										<h4><c:out value="${credinfo.licenseName}" />
										<c:if test="${credinfo.isVerified == true}" >
											<img alt="Verified Credential" src="${staticContextPath}/images/common/verified.png" />
											<c:if test="${credinfo.currentDocumentID != null && credinfo.currentDocumentID > 0}">
												<c:set var="docId" value="${credinfo.currentDocumentID}" />
												<a target="blank" href="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action?docId=${docId}">
													<img src="${staticContextPath}/images/images_registration/icons/pdf.gif" title="Click to view document" width="13px;" />
												</a>
											</c:if>
										</c:if>
										</h4>
										<c:out value="${credinfo.issuerName}" /><br/>
										<c:if test="${credinfo.isVerified == true}" >
											Date Verified: <fmt:formatDate value="${credinfo.modifiedDate}" pattern="MMMM dd, yyyy" />
											<br />
										</c:if>
										<fmt:formatDate value="${credinfo.issueDate}" pattern="yyyy" /> - <fmt:formatDate value="${credinfo.expirationDate}" pattern="yyyy" />
										<br />&nbsp;
									</div>
								</c:forEach>
								</c:when>
								<c:otherwise>
									There are no licenses or credentials on file for this service provider. 
								</c:otherwise>
							</c:choose>
							</td>
							</tr>
						
				</c:forEach>
			</c:when>
			<c:otherwise>
					<p>
						Currently, there are no team members on file.
					</p>
			</c:otherwise>
		</c:choose>
	</table>
	</div>
	
	<div><table><jsp:include page="/jsp/paging/pagingsupportforbp.jsp" /></table></div>

</div>