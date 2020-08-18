<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive Audit</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
			
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
	selectedNav = function (){ $("mktplaceTools").addClass("selected"); } 
	window.addEvent('domready',selectedNav);
</script>
	</head>
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction};">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction};">
		</c:otherwise>
	</c:choose>
	
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin - Audit Main"/>
	</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					
					<div id="pageHeader">
						<div>
							<img
								src="${staticContextPath}/images/sl_admin/hdr_mktplace_auditableItems.gif"
								width="274" height="17" alt="Marketplace | Auditable Items"
								title="Marketplace | Auditable Items" />
						</div>
					</div>
				</div>
				<!-- BEGIN CENTER -->
				<div class="colLeft711">
					<div class="content">
						<p>
							Consectetuer adipiscing elit, sed diam nonummy nibh euismod
							tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi
							enim ad minim veniam, quis nostrud exerci tation ullamcorper
							suscipit lobortis nisl ut aliquip ex ea commodo consequat.
						</p>
						<h4>
							Insurance Policies
						</h4>
						<table cellspacing="0" cellpadding="0"
							class="scrollerTableHdr auditGridHdr">
							<tbody>
								<tr>
									<td class="column1">
										 
									</td>
									<td class="column2">
										Policy Type
									</td>
									<td class="column3">
										Carrier
									</td>
									<td class="column4">
										Expiration
									</td>
									<td class="column5">
										Documents
									</td>
									<td class="column6">
										Verified by
										<br />
										ServiceLive
									</td>

								</tr>
							</tbody>
						</table>
						<div class="grayTableContainer auditGridContainer">
							<table cellspacing="0" cellpadding="0"
								class="auditGrid gridTable">
								<tbody>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											General Liability
										</td>
										<td class="column3">
											Northwestern Mutual
										</td>
										<td class="column4">
											04/08/2008
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											Worker's Compensation
										</td>
										<td class="column3">
											The Hartford Company
										</td>
										<td class="column4">
											12/02/2007
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											Automobile
										</td>
										<td class="column3">
											Allstate
										</td>
										<td class="column4">
											05/13/2008
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<h4>
							Company Licenses & Credentials
						</h4>
						<table cellspacing="0" cellpadding="0"
							class="scrollerTableHdr auditGridHdr">
							<tbody>
								<tr>
									<td class="column1">
										 
									</td>
									<td class="column2">
										Credential Type
									</td>
									<td class="column3">
										Name
									</td>
									<td class="column4">
										Expiration
									</td>
									<td class="column5">
										Documents
									</td>
									<td class="column6">
										Verified by
										<br />
										ServiceLive
									</td>

								</tr>
							</tbody>
						</table>
						<div class="grayTableContainer auditGridContainer">
							<table cellspacing="0" cellpadding="0"
								class="auditGrid gridTable">
								<tbody>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											Certification
										</td>
										<td class="column3">
											Google Certified Partner
										</td>
										<td class="column4">
											04/08/2008
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											License
										</td>
										<td class="column3">
											Cable Management & Installation
										</td>
										<td class="column4">
											12/02/2007
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											Certification
										</td>
										<td class="column3">
											Microsoft Certified Partner
										</td>
										<td class="column4">
											05/13/2008
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<h4>
							Service Pro Licenses & Credentials
						</h4>
						<table cellspacing="0" cellpadding="0"
							class="scrollerTableHdr auditGridHdr">
							<tbody>
								<tr>
									<td class="column1">
										 
									</td>
									<td class="column2">
										Credential Type
									</td>
									<td class="column3">
										Name
									</td>
									<td class="column4">
										Expiration
									</td>
									<td class="column5">
										Documents
									</td>
									<td class="column6">
										Verified by
										<br />
										ServiceLive
									</td>

								</tr>
							</tbody>
						</table>
						<div class="grayTableContainer auditGridContainer">
							<table cellspacing="0" cellpadding="0"
								class="auditGrid gridTable">
								<tbody>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											Certification
										</td>
										<td class="column3">
											Electrician- State of Georgia
										</td>
										<td class="column4">
											04/08/2008
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											License
										</td>
										<td class="column3">
											Electrician - State of Washington
										</td>
										<td class="column4">
											12/02/2007
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											Certification
										</td>
										<td class="column3">
											Samsung Certified Installer
										</td>
										<td class="column4">
											05/13/2008
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<h4>
							Company Licenses & Credentials
						</h4>
						<table cellspacing="0" cellpadding="0"
							class="scrollerTableHdr auditGridHdr">
							<tbody>
								<tr>
									<td class="column1">
										 
									</td>
									<td class="column2">
										Name
									</td>
									<td class="column3">
									</td>
									<td class="column4">
										Expiration
									</td>
									<td class="column5">
										Documents
									</td>
									<td class="column6">
										Verified by
										<br />
										ServiceLive
									</td>

								</tr>
							</tbody>
						</table>
						<div class="grayTableContainer auditGridContainer">
							<table cellspacing="0" cellpadding="0"
								class="auditGrid gridTable">
								<tbody>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											John Taylor
										</td>
										<td class="column3"></td>
										<td class="column4">
											04/08/2008
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											Wyatt Knox
										</td>
										<td class="column3"></td>
										<td class="column4">
											12/02/2007
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input width="48" type="image" height="20"
												style="background-image: url(${staticContextPath}/images/btn/audit.gif);"
												src="${staticContextPath}/images/common/spacer.gif"
												class="btn20Bevel" />
										</td>
										<td class="column2">
											Sam Sneed
										</td>
										<td class="column3"></td>
										<td class="column4">
											05/13/2008
										</td>
										<td class="column5">
											<img alt="" src="${staticContextPath}/images/icons/pdf.gif" />
										</td>
										<td class="column6">
											<img alt=""
												src="${staticContextPath}/images/icons/greenCheckMark.gif" />
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="colRight255">
			</div>
			<div class="clearfix">
			</div>
		</div>
		<!-- END CENTER   -->
		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
	</body>
</html>
