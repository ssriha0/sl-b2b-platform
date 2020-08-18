<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="showTags" scope="request" value="1" />		
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>Contact ServiceLive</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modules.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/contact.css">
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ContactUs"/>
	</jsp:include>
	<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	.ie7 .bannerDiv{margin-left:-950px;} 
		</style>
		
<script language="JavaScript" type="text/javascript">
	//To check for the special chars('<' and '>') - for XSS
	function checkSpecialKeys(e) {		
		var evtobj=window.event? event : e ;
		var keyVal =(window.event) ? event.keyCode : e.which;		
		if(evtobj.shiftKey){
			if (keyVal == 188 || keyVal == 190 || keyVal == 60 || keyVal == 62 ){//188 - <,190 - > --- In IE & 60 - <,62 - > --- in Mozilla
            	return false;
        	}else{
            	return true;
       		 }
		}       
     }
     function checkSpecialChars(e){
     	var pattern= new RegExp("[<>{}\\[\\]\\&()]");
     	var el = document.getElementById('comments');
	    if(el){
	        var commentsVal = el.value;
			var isMatch= commentsVal.match(pattern);
			if(isMatch == null){
				document.getElementById('commentsError').style.visibility = "hidden";
				return true;
			}else{
				if(document.getElementById('commentsError')){
					if(document.getElementById('commonError'))
						document.getElementById('commonError').style.visibility = "hidden";
					document.getElementById('commentsError').style.visibility = "visible";
					el.value = "";//If there is a match then clear the input.
					return false;
				}
			}		
		}
     }  
</script>
	</head>
	<body class="tundra acquity" id="fixaux">
	    
		<div id="page_margins">
			<div id="page">
				<div id="headerShort-rightRail">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />					
					<div id="pageHeader" style="z-index=-999">
						<img src="${staticContextPath}/images/contact/hdr_contactUs.gif"
							alt="Contact Us" title="Contact Us" />
					</div>
					<div id="rightRail">
						<%@ include
							file="/jsp/public/homepage/body/modules/corpHQ_top.jsp"%>
						<div class="content"></div>
					</div>
				</div>
				
				<!-- Display Server Side Validation Messages -->
				<s:if test="%{errors.size > 0}">
				<div id="commonError" class="errorBox clearfix" style="width: 675px; visibility:visible">
							<s:iterator  value="errors" status="error">
							<p class="errorMsg">
								&nbsp;&nbsp;&nbsp;&nbsp;${errors[error.index].fieldId} -  ${errors[error.index].msg}
							</p>
							</s:iterator>
				</div>
				<br>
				</s:if>
				<div id="commentsError" class="errorBox clearfix" style="width: 675px; visibility:hidden;">
							<p class="errorMsg">
								&nbsp;&nbsp;&nbsp;&nbsp;Comment - Enter Valid Comments 
							</p>
				</div>

				<!-- BEGIN CENTER -->
				<div class="colLeft711">
					<s:form action="contactUsAction_submit" id="contactUsAction_submit"
						theme="simple" enctype="multipart/form-data" method="POST">
				
					<div class="content">
						<p class="paddingBtm">
							Share your questions or comments with ServiceLive support. We'll
							respond to all e-mails as soon as possible. In the meantime, most
							questions can be answered by visiting our
							<a href="${contextPath}/jsp/public/support/support_faq.jsp">FAQ</a>
							page.
						</p>
						<table cellspacing="0" cellpadding="0">
							<tbody>
								<tr>
									<td width="250">
										<p>
											<label>
												First Name
											</label>
											<br />
											<c:set var="firstName" value="[First Name]" />
											<c:if test="${!empty contact.firstName}">
												<c:set var="firstName" value="${contact.firstName}" />
											</c:if>
											
											<s:textfield theme="simple" name="contact.firstName"
												id="contact.firstName" cssClass="shadowBox grayText"
												cssStyle="width: 200px;" value="%{#attr['firstName']}" 
												onfocus="clearTextbox(this)" maxlength="100" />
										</p>
									</td>
									<td width="200">
										<p>
											<label>
												Last Name
											</label>
											<br />
											<c:set var="lastName" value="[Last Name]" />
											<c:if test="${!empty contact.lastName}">
												<c:set var="lastName" value="${contact.lastName}" />
											</c:if>
											<s:textfield theme="simple" name="contact.lastName"
												id="contact.lastName" cssClass="shadowBox grayText"
												cssStyle="width: 195px;" value="%{#attr['lastName']}" 
												onfocus="clearTextbox(this)" maxlength="100" />
										</p>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											<label>
												E-mail Address
											</label>
											<br />
											<c:set var="email" value="[E-mail Address]" />
											<c:if test="${!empty contact.email}">
												<c:set var="email" value="${contact.email}" />
											</c:if>
											<s:textfield theme="simple" name="contact.email"
												id="contact.email" cssClass="shadowBox grayText"
												cssStyle="width: 445px;" value="%{#attr['email']}" 
												onfocus="clearTextbox(this)" maxlength="250" />
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											Phone Number
											<br />
											<c:set var="areaCd" value="###" />
											<c:set var="phnNbr1" value="###" />
											<c:set var="phnNbr2" value="####" />
											<c:if test="${!empty contact.phones}">
												<c:set var="areaCd" value="${contact.phones[0].areaCode}" />
												<c:set var="phnNbr1" value="${contact.phones[0].phonePart1}" />
												<c:set var="phnNbr2" value="${contact.phones[0].phonePart2}" />
											</c:if>
											
											<s:textfield theme="simple" name="contact.phones[0].areaCode"
												id="contact.phones[0].areaCode" cssClass="shadowBox grayText"
												cssStyle="width: 35px;" value="%{#attr['areaCd']}" 
												onfocus="clearTextbox(this)" maxlength="3" />
											-
											<s:textfield theme="simple" name="contact.phones[0].phonePart1"
												id="contact.phones[0].phonePart1" cssClass="shadowBox grayText"
												cssStyle="width: 35px;" value="%{#attr['phnNbr1']}" 
												onfocus="clearTextbox(this)" maxlength="3" />
											-
											<s:textfield theme="simple" name="contact.phones[0].phonePart2"
												id="contact.phones[0].phonePart2" cssClass="shadowBox grayText"
												cssStyle="width: 35px;" value="%{#attr['phnNbr2']}" 
												onfocus="clearTextbox(this)" maxlength="4" />
										</p>
									</td>
									<td>
										<p>
											<tags:fieldError id="Phone Type">
												<label>
													<fmt:message bundle="${serviceliveCopyBundle}"
														key="wizard.label.phone.type" />
												</label>
												<br />

												<%--
												<select name="selectedPhoneType">
													<option>Select One</option>
													<c:forEach items="${phoneTypes}" var="phone">
														<option value="${phone.id}">${phone.descr}</option>
													</c:forEach>
												</select>
												--%>


												<s:select name="selectedPhoneType" id="selectedPhoneType"
													headerKey="-1" headerValue="Select One"
													cssStyle="width: 190px;" size="1" theme="simple"
													list="%{phoneTypes}" listKey="id" listValue="descr" />


											</tags:fieldError>
										</p>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											Category
											<br />
											<select name="category" id="category" style="width: 100%; margin-bottom: 1px;">
												<option value="0" SELECTED>
													Select One
												</option>
												<c:forEach items="${categoryDropdownList}" var="dropdown">
													<option value="${dropdown.value}" 
													<c:if test="${category == dropdown.value}">
														SELECTED
													</c:if>
													>
													${dropdown.label}
													</option>
												</c:forEach>
											</select>
										</p>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<p>
											<label>
												Subject
											</label>
											<br />
											<c:set var="subjectValue" value="[Subject]" />
											<c:if test="${!empty subject}">
												<c:set var="subjectValue" value="${subject}" />
											</c:if>
											<s:textfield theme="simple" name="subject" value="%{#attr['subjectValue']}"
												id="subject" cssClass="shadowBox grayText"
												cssStyle="width: 445px;" maxlength="250" onfocus="clearTextbox(this)" />												
										</p>
									</td>
								</tr>
							</tbody>
						</table>
						<div class="clearfix" style="padding-top: 4px; width: 600px;">
							<div class="floatLeft">
								<label>
									Comments
								</label>
							</div>
							<div class="floatRight" align="right">
								1,000 characters Max
							</div>
						</div>
						<c:set var="commentsText" value="" />
						<c:if test="${!empty comments}">
							<c:set var="commentsText" value="${comments}" />
						</c:if>
						<textarea id="comments" name="comments" 
						class="shadowBox grayText" style="width: 593px; height: 100px;" onkeydown="CheckMaxLength(this, 999);" onkeypress="return checkSpecialKeys(event);">${commentsText}</textarea>
					</div>

						<div class="clearfix" style="width: 623px;">
							<div class="formNavButtons" style="width: 150px;">

								<s:submit type="button"
									cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/submit.gif);width:72px; height:22px;"
									cssClass="btnBevel" method="submit" theme="simple" value="" onclick="return checkSpecialChars(this);"/>

							</div>
							<div class="bottomRightLink">
								<a href="${contextPath}/dashboardAction.action">Cancel</a>
							</div>
						</div>
					</s:form>
				</div>

				<div class="clearfix"></div>
			</div>
			<!-- END CENTER   -->
			
			
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</body>
</html>
