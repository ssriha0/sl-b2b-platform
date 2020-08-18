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
		<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/styles/plugins/public.css" media="screen, projection">
		<!-- include any plugin javascripts and css here -->
		<link rel="stylesheet" type="text/css" 
		href="${staticContextPath}/styles/plugins/modalPassword.css" />
		<link rel="stylesheet" type="text/css" 
		href="${staticContextPath}/styles/plugins/contextualHelp.css" />
	
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript">
			$(function() {
				// insert your javascript here
			});

			function passwordStrength(password) { 
				var desc = new Array(); 
				desc[0] = "Poor"; 
				desc[1] = "Poor"; 
				desc[2] = "Good"; 
				desc[3] = "Strong"; 
				desc[4] = "Strong"; 
				var score = 0; 
				if (password.length > 7) { 
					//if password has both lower and uppercase characters give 1 point for each. 
					if (password.match(/[a-z]/)) {
						if (password.match(/[A-Z]/)){
							score++;
						}
						score++;
					}	 
					//if password has at least one number give 1 point 
					if (password.match(/\d+/)) score++; 
					//if password has at least one special caracther give 1 point 
					if ( password.match(/.[!,@,#,$,%,^,&,*,?,_,~,-,(,)]/) ) score++;
					if (password.length > 8 && score < 3)score++;
				} 
				document.getElementById("passwordDescription").innerHTML = desc[score]; 
				document.getElementById("passwordStrength").className = "strength" + score;
			}						
		</script>
		<script>
			jQuery.noConflict();
			jQuery(document).ready(function($){
			$("input.passwordHelp").mouseover(function(){
					$('#passwordHelp.passwordContextualHelp').show();
					});
						$("input.passwordHelp").mouseout(function(){
					  $('#passwordHelp.passwordContextualHelp').hide();
				});
						$("input.passwordHelp2").mouseover(function(){
					$('#passwordHelp2.passwordContextualHelp2').show();
					});
						$("input.passwordHelp2").mouseout(function(){
					  $('#passwordHelp2.passwordContextualHelp2').hide();
				});
			});
			
		</script>
		</head>

<body id="newPassword">
<div id="wrap" class="container">
		<tiles:insertDefinition name="blueprint.base.header"/>
		<tiles:insertDefinition name="blueprint.base.navigation"/>
		<div id="content" class="span-24 clearfix">		
			<!-- Insert your page content here -->
			<div class="span-16 first last">
				<div class="clearfix">	
					<div class="page-title">
						<h2>Welcome to ServiceLive</h2>
						<h3>Create Your Password</h3>
						<p>Please create your account access information below.  Be sure to keep record of the information in a safe place.</p>	
					</div>			
				</div>
				<div id="content_right_header_text">
					<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
					</br>
				</div>
				<div class="content">
						<div id="login-form">
						<s:form action="savePasswordAction" theme="simple">
							<fieldset>
								<p>Items marked with an asterisk (<span class="req">*</span>) are required.</p>
								<p>
									<label><span class="req">*</span> Password </label> 
									
									<s:password name="password"  id="password" cssClass="passwordHelp text"
								cssStyle="width: 200px;" onclick="javascript:passwordMessage()" onkeyup="passwordStrength(this.value)"/>
								
<div class="passwordContextualHelp passwordContextualHelpDev"  id="passwordHelp">
 <h3>Password must be</h3>
  <ul>
  <li>8 characters minimum</li>
  <li>A combination of letters and numbers or letters and special characters such as - # @ ! + / - .</li>
  <li>Capitalization is recommended but not required.</li></ul>
   <p><b>Reset Password </b>must not match exactly the previous four passwords.</p>
</div>
								</p>
								<p>
									<label><span class="req">*</span> Confirm </label> 
									<s:password name="confirmPassword" cssClass=" text"
									cssStyle="width: 200px;" value=""
									onfocus="clearTextbox(this)"  />
								</p>
								<p> 
									<label for="passwordStrength">Password strength</label> 
									<div id="passwordDescription">Password not entered</div> 
									<div id="passwordStrength" class="strength0" style="width:210px"></div> 
								</p><br><br>
								
								
								<c:if test="${showSecretQuestion == true}">
									<p>
										<label><span class="req">*</span> Security Question</label> 
										<s:select name="secretQuestion" headerValue="Select One"
													headerKey="" cssStyle="width: 400px;" list="questionMap">
										</s:select>
									</p>
									<p>
										<label><span class="req">*</span> Answer</label> 
										<s:textfield name="secretAnswer" cssClass="shadowBox grayText"
													cssStyle="width: 200px;" value="" onfocus="clearTextbox(this)">
										</s:textfield>
									</p>
									
								</c:if>
								
								
								<input type="submit" class="button action submit" value="Log In">
							</fieldset>
						</s:form>	
						</div>
				</div>
			</div>
			<div class="span-8">
					<div id="diymsg">
						<h3>Did you know?</h3>
						<p>Create strong passwords you can remember by making an acronym from a famous saying, or using the name of something familiar to you, such as the title of a favorite book or movie and add numbers at the beginning, end, or in between.</p>
					</div>
			</div>
		</div>
		<tiles:insertDefinition name="blueprint.base.footer"/>

</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="provider.new.password"/>
	</jsp:include>
	</body>
</html>
