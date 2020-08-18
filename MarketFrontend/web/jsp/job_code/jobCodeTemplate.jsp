<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive [JobCode]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

  	<style type="text/css">
		@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
		@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
	</style>
	
	<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
	<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
	<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
	<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
	<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
	<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />					
	<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
	
	<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true">
	</script>
	<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js">
	</script>
	<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js">
	</script>

	<script type="text/javascript">
		    dojo.require("newco.jsutils");
		    dojo.require("dijit.Dialog");
			dojo.require("dojo.parser");
		    newco.jsutils.setGlobalContext('${contextPath}');
	</script>
	<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js">
	</script>
	<script>
		function confirmSkuTaskDelete(delIndex){
			 if (confirm("Are you sure you want to delete task?")) {
			 	var jobCodeRef = document.getElementById('jobCode');
			 	var categoryIdRef = document.getElementById('categoryId_'+delIndex);			
			 	var subCategoryIdRef = document.getElementById('subCategoryId_'+delIndex);			 	
			 	var skillIdRef = document.getElementById('skillId_'+delIndex);
			 	var jobCode = "";
			 	var catId = "";
			 	var subCatId = "";
			 	var skillId = "";
			 	if(jobCodeRef != null){
			 		jobCode = jobCodeRef.value;
			 	}			 	
			 	
			 	if(subCategoryIdRef != null){
			 		subCatId = subCategoryIdRef.value;			 		
			 	}
			 	if(categoryIdRef != null){
			 		catId = categoryIdRef.value;			 		
			 	}
			 				 	
			 	if(skillIdRef != null){
			 		skillId = skillIdRef.value;			 		
			 	}
			 	var url = 'jobCode_deleteSkuTask.action?sku='
									 		+jobCode+'&catId='+catId+'&subCatId='+subCatId+'&skillId='+skillId;
				var jobCodeForm = $('jobCodeForm');
				jobCodeForm.action=url;
				jobCodeForm.submit();					 		
			 }
		}
		
		function fnGetJobCodeDetails(theSpecCode){
			dojo.byId('jobCode').focus();
			jobCodeRef = $('jobCode')
			var jobCode = "";
			var focusSpec = false;
			if(jobCodeRef != null){
				jobCode = jobCodeRef.value;	
				
				if(jobCode == null || jobCode == ''){
					newco.jsutils.jobCodeErrorMsgHelper('Please enter job code',3000);
					theSpecCode.value = "";
					dojo.byId('jobCode').style.background = 'yellow';					
				}else{
					if(theSpecCode.value != null && theSpecCode.value != ''){
						window.location.href = 'jobCode_getJobCodeDetails.action?jobCode='+jobCode+'&specCode='+theSpecCode.value;
					}else{
						newco.jsutils.jobCodeErrorMsgHelper('Please enter  speciality Code',3000);
						dojo.byId('jobCode').style.background = 'white';	
						dojo.byId('specCode').style.background = 'yellow';			
					}	
				}
			}
		}
		
		function fnSaveJobCodeDetails(){
			var templateRef = $('templateList');			
			if(templateRef != null){
				if(templateRef.value == -1){					
					newco.jsutils.jobCodeErrorMsgHelper('Please select the template',3000);					
				}else{					
					var jobCodeForm = $('jobCodeForm');
					jobCodeForm.action="jobCode_saveJobCode.action";
					jobCodeForm.submit();
				}
			}
		}
		
		function fnReturnToDashboard(){
			window.location.href = 'dashboardAction.action';
		}
	</script>
  </head>
  
  	<body class="tundra">
  	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Job Code"/>
		</jsp:include>	
  	
		<div id="page_margins">
			<div id="page">

				<!-- START HEADER -->
				<!-- <div id="headerSPReg"> -->
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
				</div>

				<tiles:insertAttribute name="body" />

				<tiles:insertAttribute name="footer" />
			</div>
		</div>
	</body>

</html>
