<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive [Security Verification]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css" media="screen, projection">
		<!-- include any plugin javascripts and css here -->
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
		<script type="text/javascript">			
			
		function submitPageAction(){		   
	   		document.forgetUsernameAction.action="forgetUsernameAction!doValidatePhoneAndZip.action";
	   		document.forgetUsernameAction.submit();	
		}
	
		function cancelAction(){
			document.forgetUsernameAction.action="forgetUsernameAction!doCancel.action";
			document.forgetUsernameAction.submit();
		}
		</script>
	</head>

<body id="login">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">		
		<!-- Insert your page content here -->
		<div class="span-16 first last">
			<div class="clearfix">	
				<div class="page-title">
					<h2>Welcome to ServiceLive</h2>
					<h3>Security Verification</h3>
					<p>Please enter your information below to verify your identification.</p>
				</div>			
			</div>
			<div class="content">
				<div id="login-form">
				<s:form action="forgetUsernameAction" theme="simple">
				<fieldset>
					<p>Items marked with an asterisk (<span class="req">*</span>) are required.</p>					
					<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
					
					<p>						
						<label><span class="req">*</span> Phone <span class="dim">(###) ### -</span> </label> 						
						<s:textfield name="userPhoneNumber" value="%{userPhoneNumber}"
										theme="simple" onfocus="clearTextboxNew(this)"
										cssStyle="width: 40px;" cssClass="text short" maxlength="4" />
						<span class="context">Last four digits of the primary phone number for this account.</span>
					 </p>
					 <p>					 
					 <label><span class="req">*</span> Zip code </label> 
					 <s:textfield name="userZipCode" value="%{userZipCode}"
										theme="simple" onfocus="clearTextboxNew(this)"
										cssStyle="width: 70px;" cssClass="text short" maxlength="5"/>
					 <span class="context">Zip code for the primary address on this account.</span>
					</p>
					<c:choose>			
                    <c:when test="${commercialUser == true}">
					  <p>
						<label><span class="req">*</span> Company Name </label>  
						 <s:textfield name="userCompanyName" value="%{userCompanyName}"
								theme="simple" onfocus="clearTextboxNew(this)"
								cssStyle="width: 70px;" cssClass="text short" />
						<span class="context">DBA or Company Name for this account.</span>						
					   </p>
					 </c:when>
					 </c:choose>	
                     <br>
                       <br>
                     <div id="login-form-forget">
                     	<a class="cancel" href="javascript:cancelAction();">Cancel</a>						  
						 <input type="submit" class="button action submit" value="SUBMIT"
						        method="validateEmail"
								onclick="javascript:submitPageAction()">	
			
				</fieldset>
				</s:form>
				</div>
			</div>
		</div>

	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="verification.zip.phone"/>
	</jsp:include>
	</body>
</html>
