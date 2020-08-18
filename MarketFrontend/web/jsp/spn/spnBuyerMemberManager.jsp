<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
	<head>
		<title>ServiceLive [Buyer Member Manager]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
				djConfig="isDebug: false, parseOnLoad: true"></script>
				<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dojo.parser");
			 dojo.require("newco.jsutils");
			dojo.require("dijit.layout.ContentPane");
			
			//alert(newco.jsutils.getCurrentSelectedTab());
		</script>
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" src="${staticContextPath}/javascript/hideShow.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javasc364ript"
			src="${staticContextPath}/javascript/nav.js"></script>
			
		<script> 
		
		function submitForm(method, doc_id){
		
			var loadFormTest = document.getElementById('spnMemberManagerForm');
						
			//alert('submitForm() docId=' + doc_id);
			dojo.byId('documentSelection').value= doc_id;	
			
			if (method=='viewDocument'){
				loadFormTest.action = '${contextPath}' + "/BuyerDocumentView"; 
			} else {
							
				loadFormTest.action = '${contextPath}' + "/spnCampaignLandingAction_" + method + ".action";
			}
			try {
			
				loadFormTest.submit();
				} catch (error) {
				alert ('An error occurred while processing your document request. Check the filename and filepath.');
				}
			}
			
		function validateDocSelection(method, doc_id){
			var failedValidation = false;
			var exists = document.getElementById("documentSelection" + doc_id);

			if (exists){
				submitForm(method, exists.value);
			}else{
				document.getElementById('documentSelectionMsg').style.display = "inline";
				failedValidation = true;
				return;
			}
		}	
		newco.jsutils.setCurrentSelectedIframe(40);		
	</script>			
	</head>
	<body>
	    
		<div id="page_margins">
		
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="SPN - Buyer Member Manager"/>
		</jsp:include>	
		
			<div id="page">
				<!-- START HEADER -->
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					
					<div id="pageHeader">
						<h2>Company Profile</h2>
					</div>
				</div>
				<!-- END HEADER -->
				
				<!-- START TAB PANE -->
				<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
							   style="height: 565px; width: 711px">
				<div  id="40"
				      title="Members"
					  dojoType="dijit.layout.ContentPane"
					  href="${contextPath}/spnMemberManager_loadGridContainer.action?type=40&spnId=<%=request.getAttribute("spnId")%>"
					  selected="true"
					  refreshOnShow="false">
				</div>
				<div  id="20"
				      title="Applicants"
					  dojoType="dijit.layout.ContentPane"
					  href="${contextPath}/spnMemberManager_loadGridContainer.action?type=20&spnId=<%=request.getAttribute("spnId")%>"
					  selected="false"
					  refreshOnShow="false" >
				</div>
				<div  id="50"
				      title="Inactive"
					  dojoType="dijit.layout.ContentPane"
					  href="${contextPath}/spnMemberManager_loadGridContainer.action?type=50&spnId=<%=request.getAttribute("spnId")%>"
					  selected="false"
					  refreshOnShow="false">	
				</div>
	</div>
	<table align="center">
		<tr>
			
				<td align="center">
					
				<form>
						<input type="button" 
							   name="test0" 
							   value="Remove Selected Members" 
							   onclick="newco.jsutils.doButtonAction('remove')" 
							   id="remove"></form>
					
				</td>
				<td align="center">
				<form>
						<input type="button" 
							   name="test1" 
							   value="Approve Selected Members" 
							   onclick="newco.jsutils.doButtonAction('approve')" id="approve" disabled="disabled"></form>
				
				</td>
			</tr>
		</table>		
				<!-- END TAB PANE -->
				<div class="colRight255 clearfix"></div>
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->

		</div>
	</body>
</html>
