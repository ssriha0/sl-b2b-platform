<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="currentMenu" scope="request" value="exploreMktplace"/>	
<c:set var="role" value="${roleType}" />
<c:set var="workflowTab" scope="page"
	value="<%=request.getParameter("workflowTab")%>" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>Explore the ServiceLive Marketplace</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />


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
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_order_wizard.css" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: false"></script>
		<script type="text/javascript">
		    dojo.require("newco.jsutils");
		</script>
	</head>
	<body class="tundra">
	  
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="ETM - Search"/>
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
	
		<script type="text/javascript"
			src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/hideShow.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	
		<script language="javascript">
	           
		function sendETMSearch()
		{			
			// Get form data from model-driven dto
			var skillTreeMainCat = -1;
			var marketReadySelection ;
			var zipCd = -1;
			
			if($("skillTreeMainCat") != null)
				skillTreeMainCat = $("skillTreeMainCat").value;

			

			if($("zipCd") != null)
				zipCd = $("zipCd").value;
			
			// Get iframe object
			var iFrameRef = null;										
			getIFrameDoc().getElementById('skillTreeMainCat').value= skillTreeMainCat;

		//	alert("My market is set at"+iFrameDoc.getElementById('marketReadySelection').value);				
			getIFrameDoc().getElementById('zipCd').value= zipCd;
			// Call a local javascript function that calls submit()
			iFrameRef = document.getElementById("etmResultsIframeID");
			iFrameRef.contentWindow.doAction();
		}		


		function sendETMApply()
		{			
			// Get form data from model-driven dto
			var skillTreeMainCat = -1;
			var marketReadySelection = 1;
			var zipCd = -1;
			
			if($("skillTreeMainCat") != null)
				skillTreeMainCat = $("skillTreeMainCat").value;

			if($("marketReadySelection") != null)
				marketReadySelection = $("marketReadySelection").value;

			if($("zipCd") != null)
				zipCd = $("zipCd").value;
			
			alert(skillTreeMainCat + ' ' + marketReadySelection + ' ' + zipCd);
			
			
			// Get iframe object
			var iFrameRef = null;										
			iFrameRef = document.getElementById("etmResultsIframeID");
			if(iFrameRef == null)
				return;
			
			// Get form object;
			var searchForm = null;			
			if(!dojo.isIE)
			{
				searchForm = iFrameRef.contentDocument.getElementById("etmSearchResults");
			}
			else
			{
				searchForm = iFrameRef.contentWindow.document.getElementById("etmSearchResults");		
			}
			if(searchForm == null)
				return;
			
			// Set the 3 form fields in hidden fields.
			searchForm.skillTreeMainCat.value= skillTreeMainCat;			
			//searchForm.marketReadySelection.value= marketReadySelection;				
			searchForm.zipCd.value= zipCd;
			
			// Call a local javascript function that calls submit()
			iFrameRef.contentWindow.doAction();
		}
		
		function setMarketFlag( comp ) {
		getIFrameDoc().getElementById('marketReadySelection').value = comp.value;
			//alert('debug 1 '+iFrameDoc.getElementById('marketReadySelection').value );
		}
		
		function handleOnLoadEvent() {
		
			//alert('debug 1 '+comp.value);
			getIFrameDoc().getElementById('marketReadySelection').value = 1;
		}
		
		function getIFrameDoc () {
					iFrameRef = document.getElementById("etmResultsIframeID");
			if(iFrameRef == null)
				return;
			iFrameDoc = iFrameRef.contentWindow.document;
			return iFrameDoc;
		}		
		</script>
	</body>



</html>




