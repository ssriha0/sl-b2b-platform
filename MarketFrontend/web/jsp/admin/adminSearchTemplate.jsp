<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="role" value="${roleType}" />
<c:set var="workflowTab" scope="page"
	value="<%=request.getParameter("workflowTab")%>" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive User Search</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />


		<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>




		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/so_monitor.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/global.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/finance_mgr/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css" />




		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/hideShow.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("newco.rthelper");
		    dojo.require("newco.jsutils");
			dojo.require("dijit.form.DateTextBox");		    
		</script>



		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="JavaScript" type="text/javascript"></script>


		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>

		<script language="JavaScript" type="text/javascript">
			function showProvDetails(vendorId, resourceId, ind, usrName, compName, fullName)
			{
				var loadSrchForm = document.getElementById('adminSearch');
				loadSrchForm.hidVendorId.value=vendorId;
				loadSrchForm.hidPrimaryInd.value=ind;
				loadSrchForm.hidResourceId.value=resourceId;
				loadSrchForm.hidUserName.value=usrName;
				loadSrchForm.hidCompanyName.value=compName;
				loadSrchForm.hidMemberName.value=fullName;
				
				loadSrchForm.action="adminSearch_navigateToProviderPage.action";
				loadSrchForm.submit();
			}
			
			function showBuyerDetails(buyerId, resourceId, usrName)
			{
				var loadSrchForm = document.getElementById('adminSearch');
				loadSrchForm.hidBuyerId.value = buyerId;
				loadSrchForm.hidResourceId.value = resourceId;
				loadSrchForm.hidUserName.value = usrName;
				
				loadSrchForm.action="adminSearch_navigateToBuyerPage.action";
				loadSrchForm.submit();
			}
			
			//Function will clear all the fields.
			function clearFields()
			{
				document.getElementById('companyId').value = "";
				document.getElementById('orderNumber').value = "";
				document.getElementById('businessName').value = "";
				document.getElementById('userId').value = "";
				document.getElementById('username').value = "";
				document.getElementById('city').value = "";
				document.getElementById('state').value = "-1";
				document.getElementById('zipPart1').value = "";
				document.getElementById('zipPart2').value = "";
				document.getElementById('phone').value = "";
				document.getElementById('email').value = "";
			}
			
			//Enables all the fields
			function enableFields()
			{	
				document.getElementById('companyId').disabled = false;
				document.getElementById('orderNumber').disabled = false;
				document.getElementById('businessName').disabled = false;
				document.getElementById('userId').disabled = false;
				document.getElementById('username').disabled = false;
				document.getElementById('city').disabled = false;
				document.getElementById('state').disabled = false;
				document.getElementById('zipPart1').disabled = false;
				document.getElementById('zipPart2').disabled = false;
				document.getElementById('phone').disabled = false;
				document.getElementById('email').disabled = false;
			}
			
			function prepareOnLoad()
			{							  
				var radioBuyer = document.getElementById("radioId0");
				if (radioBuyer.checked == true)
					 disablingBuyer();
			}
			
			//Invoked when Buyer radio button is selected 
			function prepare4Buyer()
			{
				//Clear all the fields and enable all the fields
				clearFields();
				//Enable all fields
				enableFields();
				//Disable the unwanted fields
				disablingBuyer();
			}
			
			//Invoked when the Provider radio button is selected
			function prepare4Provider()
			{
				//Clear all the fields and enable all the fields
				clearFields();
				//Enable all fields
				enableFields();
			}
			
			//Function will disable the Unwanted fields
			function disablingBuyer()
			{
				document.getElementById('orderNumber').disabled = true;
				document.getElementById('userId').disabled = true;
				document.getElementById('city').disabled = true;
				document.getElementById('state').value = "-1";
				document.getElementById('state').disabled = true;
				document.getElementById('zipPart1').disabled = true;
				document.getElementById('zipPart2').disabled = true;
			}
			
			</script>


	</head>

	<body class="tundra" onload="prepareOnLoad();">
	  
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




