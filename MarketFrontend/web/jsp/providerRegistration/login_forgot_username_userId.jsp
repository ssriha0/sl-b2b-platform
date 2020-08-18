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
			$(function() {
				// insert your javascript here
			});
			
		function submitPageAction(){
	   		document.forgetUsernameAction.action="forgetUsernameAction!sendEmail.action";
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
					<h3>Multiple User Names</h3>
					<p>Select your user name from the list below.</p>	
				</div>			
			</div>
			<div class="content">
				<div id="login-form">
				<s:form action="forgetUsernameAction" theme="simple">
				<s:hidden name="userName" value=""></s:hidden>
				<s:hidden name="pwdInd" value=""></s:hidden>
				<s:hidden name="questionTxt" value=""></s:hidden>
				<s:hidden name="answerTxt" value=""></s:hidden>
				<fieldset>
					<p>Items marked with an asterisk (<span class="req">*</span>) are required.</p>					
					<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true"/>
					
					  <p>						
						<label><span class="req">*</span> Your Name </label>
						<s:select name="selectedUserId" headerValue="-- Select One--"
                                     headerKey="-1:-1"
                                     listValue="displayName"
                                     listKey="resourceId + ':' + userId" 
                                     value="resourceId"                                 	                                      
                                     list="forgotUsernameDto.listUsers"                                     
                                     theme="simple" cssStyle="width: 250px;" cssClass="grayText">
                        </s:select>
					 </p>
                     <br>
                     
                     <div id="login-form-forget">
                     	<a class="cancel" href="javascript:cancelAction();">Cancel</a>
						<input type="submit" class="button action submit" value="SUBMIT"
								onclick="javascript:submitPageAction()">	</div>									
			
				</fieldset>
				</s:form>
				</div>
			</div>
		</div>

	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="forgot.userId.list"/>
	</jsp:include>
	</body>
</html>
