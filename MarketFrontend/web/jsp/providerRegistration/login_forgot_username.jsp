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
		<title>ServiceLive : Welcome to ServiceLive</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css" media="screen, projection">
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
		</style>
		<!-- include any plugin javascripts and css here -->
		<script type="text/javascript">
			$(function() {
				// insert your javascript here
			});
			
		function submitPageAction(){			
			document.forgetUsernameAction.action="forgetUsernameAction!validateEmail.action";
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
					<h3>Forgot Username</h3>
					<p>Please enter your information below to continue.</p>	
				</div>			
			</div>
			<div class="content">
				<div id="login-form">
				<s:form action="forgetUsernameAction!validateEmail" theme="simple" method="POST">
				<fieldset>
					<p>Items marked with an asterisk (<span class="req">*</span>) are required.</p>
										
					<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
					
					<p>						
						<label><span class="req">*</span> E-mail Address </label>
						<s:textfield name="forgotUsernameDto.email"
									value="%{forgotUsernameDto.email}" maxlength="100"
									onfocus="clearTextboxNew(this)" cssStyle="width: 250px;"
									cssClass="text"></s:textfield>
					 </p>
                     <br>
                     <div id="login-form-forget">
                     	<a class="cancel" href="javascript:cancelAction();">Cancel</a>						  
						 <input type="submit" class="button action submit" value="SUBMIT"
						        method="validateEmail"
								onsubmit="javascript:submitPageAction()">	
						<!-- 				
						 <s:submit value="SUBMIT"
								   method = "validateEmail"							
								   cssClass="button action submit" /> 
						 -->		   	
					</div>									
			
				</fieldset>
				</s:form>
				</div>
			</div>
		</div>

	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="forgot.userId"/>
	</jsp:include>
	</body>
</html>
