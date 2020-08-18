<jsp:include page="/jsp/public/common/commonIncludes.jsp" />
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="pageTitle" scope="request" value="Edit Your Account" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title>ServiceLive : ${pageTitle}</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
	</head>
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction}">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction}">
		</c:otherwise>
	</c:choose>
	
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
       	 <jsp:param name="PageName" value="SSO - Edit Account"/>
	</jsp:include>
	

	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
			</div>

			<!-- START CENTER -->
			<div id="hpWrap" class="shaded clearfix">
				<div id="hpContent">
					<div id="hpIntro" class="clearfix">
						<h2>
							My Account Information
						</h2>
						<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />
						<c:if test="${message != null}">
							<p>
								${message}
							</p>
							<br>
						</c:if>
					</div>
					<form action="csoEditAccount_updateContactInfo.action" name="edit_account" theme="simple">
						<div id="hpJoin" class="hpDescribe clearfix">
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="required.field" />
							<br />

							<dl>
								<dt>
									<label>
										First Name
									</label>
								</dt>
								<dd>
									<s:property value="firstName" />
								</dd>
							</dl>
							<dl>
								<dt>
									<label>
										Last Name
									</label>
								</dt>
								<dd>
									<s:property value="lastName" />
								</dd>
							</dl>
							<dl>
								<dt>
									<label>
										Email Address
										<span class="req">*</span>
									</label>
								</dt>
								<dd>
									<tags:fieldError id="Email">
										<s:textfield name="email" id="email"
											cssClass="shadowBox blackText" cssStyle="width: 200px;"
											value="%{#request['email']}" maxlength="255" />
									</tags:fieldError>
								</dd>
							</dl>
							<dl>
								<dt>
									Primary Phone
								</dt>

								<dd>
									<s:textfield name="primaryPhone" id="primaryPhone"
										cssClass="shadowBox phone" cssStyle="width: 200px;"
										value="%{#request['primaryPhone']}" maxlength="30" />
									<!-- input type="text" name="primaryPhone" value="${primaryPhone}" class="shadowBox phone"  onblur="return formatPhone(this, event)" onkeyup="formatPhone(this)" onchange="formatPhone(this)"/-->
								</dd>
							</dl>
							<dl>
								<dt>
									Secondary Phone
								</dt>
								<dd>
									<s:textfield theme="simple" name="secondaryPhone"
										id="secondaryPhone" cssClass="shadowBox phone"
										cssStyle="width: 200px;" value="%{#request['secondaryPhone']}"
										maxlength="30" />
								</dd>
							</dl>
							<dl>
								<dt>
									Address
								</dt>
								<dd>
									<s:property value="address.streetNumber" />
								</dd>
								<dd>
									<s:property value="address.streetName" />
									<s:property value="address.aptNumber" />
								</dd>
								<dd>
									<s:property value="address.city" />
									,
									<s:property value="address.state" />
									<s:property value="address.zipCode" />
								</dd>
							</dl>
						</div>

						<div class="clearfix buttnNav">
							<input type="image"
								onClick="reset_edit_account('<s:property value="email"/>', '<s:property value="primaryPhone"/>', '<s:property value="secondaryPhone"/>')"
								src="${staticContextPath}/images/simple/button-reset.png" />
							<s:submit type='image' 
								src="%{#request['staticContextPath']}/images/simple/button-save.png"
								theme="simple"/>
						</div>
						<input id="contactId" type="hidden" name="contactId"
							value="<s:property value="contactId"/>" />
					</form>
					<a href="dashboardAction.action">Go to Dashboard</a>
				</div>


			</div>
			
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
			
		</div>
	</div>
	</body>
</html>

