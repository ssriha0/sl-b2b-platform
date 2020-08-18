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
		<title>ServiceLive [Log In]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css" media="screen, projection">
		<!-- include any plugin javascripts and css here -->
		<script type="text/javascript">			
			
		function submitPageAction() {	
			document.resetPasswordAction.action="resetPasswordAction!validateAns.action";  
		}
			
		function cancelAction() {
			document.resetPasswordAction.action="resetPasswordAction!doCancelAnswer.action";
			document.resetPasswordAction.submit();
		}
		</script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js">
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
					<h3>Reset Password</h3>
					<p>
						Please provide the correct answer for the security question
						below. Upon verification, your username will be sent to the
						e-mail address we have on record for your account.
					</p>	
				</div>			
			</div>
			<div class="content">
				<div id="login-form">
				<s:form action="resetPasswordAction!validateAns" theme="simple">
				<fieldset>
					<p>Items marked with an asterisk (<span class="req">*</span>) are required.</p>
										
					<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
					
					  <p>						
						
						<label><span class="req">*</span> Security Question</label> 
						<span class="security-question">
							<s:property value="%{forgotUsernameDto.questionTxt}"></s:property>						
						</span>
						<input type="text" style="width: 200px;"
										name="forgotUsernameDto.questionTxtAnswer"
										class="text squestion" />
					 </p>
					 
                     <br>
                     <div style="width:20%;">
                     <div style="width:40%; float:left; padding-left:150px;">
                     	<a class="cancel" href="javascript:cancelAction();">Cancel</a></div>
						<div style="width:40%; float:right;margin-top:-10px"> 
							<input type="submit" class="button action submit" value="SUBMIT"
								onclick="javascript:submitPageAction()">	
						</div>									
			
				</fieldset>
					<input type="hidden" name="forgotUsernameDto.userName" value="${forgotUsernameDto.userName}"/>
					<input type="hidden" name="forgotUsernameDto.questionId" value="${forgotUsernameDto.questionId}"/>
					<input type="hidden" name="forgotUsernameDto.email" value="${forgotUsernameDto.email}"/>
				</s:form>
				</div>
			</div>
		</div>

	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="provider.hint"/>
	</jsp:include>
	</body>
</html>
