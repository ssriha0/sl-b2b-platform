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
		<title>ServiceLive - Template Maintenance</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>	
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/template-maintenance.css" media="screen, projection">
		

		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			    dojo.require("newco.jsutils");
			    dojo.require("dijit.Dialog");
				dojo.require("dojo.parser");
			    newco.jsutils.setGlobalContext('${contextPath}');
		</script>
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
		</style>
		<script type="text/javascript">
			function fnReturnToDashboard(){
				window.location.href = 'dashboardAction.action';
			
			}
			
			function fnSetAddMode(theId){
				var templateForm = document.getElementById('templateForm');
				templateForm.editTemplate.value=false;
			    templateForm.action="templateMaintenance_execute.action";
				templateForm.submit();	 
			}		
	/*		function fnPartsDetails(theField){
			var selectedValue= theField.value;
				if(selectedValue==-1){
					alert("Select atleast one");
				}
				else{
					alert('No value');
				}
			} */
			function fnSetEditMode(theId){
				var editMode = document.getElementById('editTemplate');
				editMode.value = true;			
				theId.checked = "checked";			
			
				var buyerTemplate = document.getElementById('selectedBuyerSOTemplate');
				buyerTemplate.disabled = false;
			}
			
			function fnCheckSpnPercentage(theField){
				var value = theField.value;
				if( isNaN(value) ||  (value > 100 || value < 1)){
					alert("Percentage match must be between 1 & 100");
					theField.value = "";
					theField.focus();
				}
			}
			
			function fnGetTemplateDetails(theId){
		
				var val=document.getElementById("selectedBuyerSOTemplate").value;
				if(val!="-1"){
					fnSetEditMode("editSoTemplate");
					var selectedId = theId.selectedIndex;
					
					var addRadio = document.getElementById('addSoTemplate');
					window.location.href = 'templateMaintenance_getTemplateDetails.action?templateId='+theId[selectedId].value;
					
					if(addRadio != null){
						addRadio.disabled = 'disabled';
					}
				}
			}
			
			function fnSaveTemplate(isSave){
				var templateFormRef = document.getElementById('templateForm');
				var attachedDocRef = document.getElementById('attachedList');
				var templateName = document.getElementById('templateName');
				var mainCat = document.getElementById('mainServiceCategoryId');
				//changes for Sl-17955
				var buyerLogoId = document.getElementById('selectedBuyerLogo');
				//get the value for parts supplied by
				var partsSupplied = document.getElementById('partsSuppliedBy');
				//alert(partsSupplied.selectedIndex);
				var selectedSpn = document.getElementById('selectedSpn');
				var spnPercentageMatch = document.getElementById('spnPercentageMatch');
				var autoAcceptDays = document.getElementById('autoAcceptDays');
				var autoAcceptTimes = document.getElementById('autoAcceptTimes');
				document.getElementById("templateNameError").className = "";
				document.getElementById("mainCatError").className = "";
				document.getElementById("autoAcceptDaysError").className = "";
				document.getElementById("autoAcceptTimesError").className = "";
				document.getElementById("spnPercentageMatchError").className = "";
				document.getElementById("validationError").innerHTML="";
				document.getElementById("validationError").style.display="none";
				document.getElementById("message").style.display="none";
				document.getElementById("buyerLogoError").className="";
				document.getElementById("partsSuppBy").className="";
				
				var flag = false;
				var msg = "<p class='errorMsg'>";
	 
				if (templateName != null && templateName.value == '' ){ 
					flag = true;
					msg = msg + "Please enter Template Name <br/>";
					document.getElementById("templateNameError").className = "errorBox";
					
				}
				if(mainCat != null && mainCat.length > 0 
							&& mainCat.selectedIndex < 1){
					flag = true;
					msg = msg  + "Please select Main Service Category <br/>";
					document.getElementById("mainCatError").className = "errorBox";
				}
				//changes for Sl-17955 --START
				if(buyerLogoId != null && buyerLogoId.length > 0 
						&& buyerLogoId.selectedIndex < 1){
				flag = true;
				msg = msg  + "Please select Buyer Logo <br/>";
				document.getElementById("buyerLogoError").className = "errorBox";
				}
				//changes for Sl-17955 --END
				//Write the validation for parts supplied by
				if(null==partsSupplied ||partsSupplied.selectedIndex == 0 )
					{
					
					flag=true;
					msg=msg + "Please select Parts Supplied By <br/>";
					document.getElementById("partsSuppBy").className ="errorBox";
					}
				
				
				if(spnPercentageMatch != '' && 
						(spnPercentageMatch.value < 0 || spnPercentageMatch.value > 100)){
					flag = true;
					msg = msg  + "Percentage match must be between 1 & 100 <br/>";
					document.getElementById("spnPercentageMatchError").className = "errorBox";
					
				}	
				if(document.getElementById("autoAcceptYes").checked==true){
					if (autoAcceptDays != null && autoAcceptDays.value == '' ){ 
						flag = true;
						msg = msg + "Please enter Reschedule Limit <br/>";
						document.getElementById("autoAcceptDaysError").className = "errorBox";
						
					}
					
					if (autoAcceptTimes != null && autoAcceptTimes.value == '' ){ 
						flag = true;
						msg = msg + "Please enter Allowed # of reshcedules <br/>";
						document.getElementById("autoAcceptTimesError").className = "errorBox";
						
					}
						
					if(autoAcceptDays != '' && (autoAcceptDays.value < 1 || autoAcceptDays.value > 120))	
					{
						flag = true;
						msg = msg  + "Please enter a value for reschedule limit between 1 and 120<br/>";
						document.getElementById("autoAcceptDaysError").className = "errorBox";
					}
					if(autoAcceptTimes != '' && (autoAcceptTimes.value < 1 || autoAcceptTimes.value > 50))	
					{
						flag = true;
						msg = msg  + "Please enter a value for allowed number of reschedules between 1 and 50 <br/>";
						document.getElementById("autoAcceptTimesError").className = "errorBox";
					}
				}
				if(flag){
					msg = msg +"</p";
					document.getElementById("validationError").innerHTML=msg;
					document.getElementById("validationError").style.display="block";
					var loc = location.href;
					var index = loc.indexOf("#errorPosition");
					if(index!=-1){
						loc = loc.substring(0,index + 14);
						location.href = encodeURI(loc);
					}else{
						location.href = encodeURI(loc+"#errorPosition");
					}
					return false;
				}else{		
					if(attachedDocRef != null){				
						for(var i=0;i<attachedDocRef.length;i++){					
							attachedDocRef.options[i].selected = true;
						}
					}
					var url = "";
					if(isSave){
						url = '<s:url action='templateMaintenance_saveTemplate.action' />';
					}else{
						url = 'templateMaintenance_updateTemplate.action';
					}
					templateFormRef.action=encodeURI(url);
					templateFormRef.submit();			
				}	
			}
			
		</script>

	</head>

<body id="page-id">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">		

				
				<tiles:insertAttribute name="body" />

	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="cat:unnamed page"/>
	</jsp:include>
	</body>
</html>

