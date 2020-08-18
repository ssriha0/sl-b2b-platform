<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dijit.form.Button");
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dojo.parser");
			dojo.require("dijit.layout.LinkPane");
			dojo.require("newco.jsutils");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}

			var contextPath = '${pageContext.request.contextPath}';
			newco.jsutils.setGlobalContext('${contextPath}');
			newco.jsutils.setGlobalStaticContext('${staticContextPath}');
			/*  <!-- page prov_reg_all_tabs.file --> */



		</script>
		<script type="text/javascript">
		function copyValue1()
		{
			document.getElementById("mailingStreet1").value = document.getElementById("businessStreet1").value;
			document.getElementById("mailingStreet2").value = document.getElementById("businessStreet2").value;
			document.getElementById("mailingAprt").value = document.getElementById("businessAprt").value;
			document.getElementById("mailingCity").value = document.getElementById("businessCity").value;
			document.getElementById("mailingState").value = document.getElementById("businessState").value;
			document.getElementById("mailingZip").value = document.getElementById("businessZip").value;
		}
		</script>
		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />


		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/utils.js"></script>

		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/calendar.js?random=20060118"></script>
		<script type="text/javascript">
			jQuery.noConflict();
			function openBucks(){
				newco.jsutils.displayModal('Bucks');
			}
			function closeBucks(){
				newco.jsutils.closeModal('Bucks');
			}
			function agreeBucks(){
				document.getElementById("acceptBucksTerms").checked="checked";
				newco.jsutils.closeModal('Bucks');
			}
			
			
			//Code added to correct update credential for providers in Auditor workflow.
			function updateCredentialNew()
			{	
				saveCredentialDetail();		
				if(document.getElementById("checkTrue").value=='true')
				{
					saveCredentilDetail();
				}else
				{
					return false;
				}
			}

      		function saveCredentialDetail()
      		{
				if(document.getElementById("isFirstClick").value=='true')
				{
					validateLegalBarNumber();
				}
				else
				{
					document.getElementById("checkTrue").value='true';
				}
      		}

      		function validateLegalBarNumber()
			{
      		  var errorString = '';
   			  document.getElementById("widgetWarningMessageID").style.display="none"; 
   			 
   			  if(document.getElementById("teamCredentialsDto.typeId").value=='3' && document.getElementById("teamCredentialsDto.categoryId").value=='24')
   			  {
	   				var credentialNumber = document.getElementById("teamCredentialsDto.credentialNumber").value;   				 
	            	var isNotValidValue='false';					
	            	var regEx = /^[0-9a-zA-Z\s-]*$/;	             	
	           		if(regEx.test(credentialNumber))
	           		{
	   					isNotValidValue = 'false';	
	   				}					
	   				else
	   				{
	           			isNotValidValue = 'true';
	          		}	
           
		           	if(credentialNumber == null || credentialNumber == '')
		           	{
		              isNotValidValue = 'false';	
		           	}         
		   			if(isNotValidValue=='true')
		   			{
		   				document.getElementById("checkTrue").value='false';
	   					errorString = errorString + 
	   			    	  'The credential number you provided includes symbols. Please verify you have entered the correct information before saving to your profile';	
	   					validationSuccess = true;					
		   										
		   				document.getElementById("warningMsgPara").innerHTML = errorString;
		   				document.getElementById("widgetWarningMessageID").style.display="";
		   				document.location.href = '#widgetWarningMessageID';
					}
		   			else
		   			{			
		   				document.getElementById("widgetWarningMessageID").style.display="none";
		            	document.getElementById("checkTrue").value='true';          				
		   			}
   				 	document.getElementById("isFirstClick").value='false';	   				    		
   			 }
   			 else{
   				document.getElementById("checkTrue").value='true';
   				document.getElementById("isFirstClick").value='false';	
   			 }
		}	
		</script>		
</head>
<body>
<div id="closeArea"></div>
<table width="100%">
<tr>
<td width="800px">
	<jsp:include page="audit_tm_credentials.jsp"></jsp:include>
	<div id="cred_area" style="margin-top: 150px;">
	<div id="mainTabContainer" style="height: 750px; width: 400px; margin: 0;">
	<!-- setting the audit log id to pass to the doload method in  powerAuditorWorkflowControllerAction-->
		<a style="height: 800px; width: 750px;" dojoType="dijit.layout.LinkPane" href="<s:url action="%{#request['contextPath']}/powerAuditorWorkflowControllerAction_doLoad.action?auditTimeLoggingId=%{#request['auditTimeLoggingId']}"/>"></a>
	</div>
	</div>

</td>
<td>
</td>
</tr>
</table>

<div dojoType="dijit.Dialog" id="widgetActionSuccess" title="Quick Action Response">
			<b>Action Completed Successfully</b><br>
			<div id="messageS"></div>
		</div>
		<div dojoType="dijit.Dialog" id="widgetActionError" title="Quick Action Response">
			<b>Action Failed please review</b><br>
			<div id="message"></div>
		</div>

</body>
</html>



