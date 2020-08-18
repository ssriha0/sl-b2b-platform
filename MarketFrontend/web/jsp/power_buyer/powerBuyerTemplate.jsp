<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="role" value="${roleType}"/>	
<c:set var="workflowTab" scope="page" value="<%=request.getParameter("workflowTab") %>" />	
<c:set var="currentMenu" scope="request" value="menuPowerBuyer"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>ServiceLive</title> 
<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<style type="text/css">
	@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
	@import	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
<link rel="stylesheet" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" type="text/css"></link>
<link rel="stylesheet" href="${staticContextPath}/css/dijitTabPane-serviceLive.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/CalendarControl.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_monitor.css"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/finance_mgr/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_provider_profile.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltipcss.css" />
<!--[if IE]>
     <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/scrolling_table.ie.css" />
<![endif]-->

<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/hideShow.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
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
	//dojo.require("newco.servicelive.SOMRealTimeManager");
	function myHandler(id,newValue){
		console.debug("onChange for id = " + id + ", value: " + newValue);
	}
</script>

<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/tooltip.js" ></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>

<script type="text/javascript" src="${staticContextPath}/javascript/soMonitorAdvSearchDriver.js"></script>

<script type="text/javascript">

	function selectForeignOwnedYes(){
		document.getElementById("percentOwned").style.display='block';
	}
	function selectForeignOwnedNo(){
		document.getElementById("percentOwned").style.display='none';
	}
	         
	function clearFilterValue (){
	 	var selIndex=document.getElementById('refType').selectedIndex;
	 	var refType =document.getElementById('refType').options[selIndex].value;
	 	var refValue =document.getElementById('refVal').value;
	 	if(refType==0){
	 		var parameters = "?refType="+refType+"&refVal="+refValue;
	 		window.location.href='/MarketFrontend/pbController_execute.action'+parameters;
	 	}else{
			document.getElementById('refVal').value='';
	 	}	
}
</script>
<script language="JavaScript" type="text/javascript">
	newco.jsutils.setGlobalContext('${contextPath}'); 
	var cb = function callBack( data ) {
		newco.jsutils._getResultsAsXML(data,_commonSOMgr.widgetId+"myIframe" );	
	}
	var _commonSOMgr = newco.rthelper.initNewSOMRTManager('${contextPath}/monitor/refreshTabs.action',10000,cb);
	_commonSOMgr.setSelectedWidget('<%=request.getParameter("workflowTab") != null ? "Search" : "Search" %>', false);       
	
	/*function sendData() {
		var selectedType = document.getElementById("searchType");
		var selectedTypeValue = selectedType.value;
		var index = selectedType.selectedIndex;
		if (index == 0) {
			alert("Please select 'Search By'");
			return;
		}
		document.getElementById("searchValue").value = dojo.trim(document.getElementById("searchValue").value);
		var searchValue = document.getElementById("searchValue").value;
		if (searchValue.length == 0) {
			alert("Please specify 'Search Term'");
			return;
		}
		
		var iFrameRef = null;
		var searchForm = null;
		iFrameRef = document.getElementById("SearchmyIframe");
		if(!dojo.isIE){
			searchForm = iFrameRef.contentDocument.getElementById("searchHandler");
		} else {
			searchForm = iFrameRef.contentWindow.document.getElementById("searchHandler");		
		}
		
		if(selectedTypeValue == '3') {
			searchValue = formatSOId(searchValue);	
		} else if(selectedTypeValue == '1') {
			if (searchValue.length == 12) {
				searchValue = formatPhone(searchValue);	
			}
			if (searchValue.length == 13) { //For the format (###)###-#### 
            	searchValue = formatPhoneForParenthesis(searchValue); 
            }	
		}
		searchForm.searchType.value = selectedTypeValue;
		searchForm.searchValue.value = searchValue;
		searchForm.submit();
	}*/
	
	function formatSOId(searchValue) {
		var success = true;
		var pattern1 = /(^\d{3}-\d{4}-\d{4}-\d{2}$)/;
	     if (!pattern1.test(searchValue)) 
	     {
		     var pattern2 = /^(\d{3})(\d{4})(\d{4})(\d{2})$/;
		     searchValue = searchValue.replace(/-/g, "");
		     searchValue = searchValue.replace(pattern2,"$1-$2-$3-$4");
         }
		return searchValue;
	}
	
	function formatPhone(searchValue) {
		var success = true;
		var patternhyphen = /(^\d{3}-\d{3}-\d{4}$)/;
		var patterndot = /(^\d{3}.\d{3}.\d{4}$)/;
		var pattern2 = /^(\d{3})(\d{3})(\d{4})$/;		
	     if (patternhyphen.test(searchValue)) 
	     {		     
		     searchValue = searchValue.replace(/-/g, "");
		     searchValue = searchValue.replace(pattern2,"$1$2$3");
         }
         if (patterndot.test(searchValue)) 
	     {		     
		     searchValue = searchValue.replace(/\./g, "");		     
		     searchValue = searchValue.replace(pattern2,"$1$2$3");
         }
		return searchValue;
	}
	//For the format (###)###-#### 
	function formatPhoneForParenthesis(searchValue) 
    { 
		var patternParenthesis = /(^\(\d{3}\)\d{3}-\d{4}$)/; 
        var pattern2 = /^(\d{3})(\d{3})(\d{4})$/; 
        if (patternParenthesis.test(searchValue)) 
        { 
        	searchValue = searchValue.replace(/\(/g, ""); 
            searchValue = searchValue.replace(/\)/g, ""); 
            searchValue = searchValue.replace(/-/g, ""); 
            searchValue = searchValue.replace(pattern2,"$1$2$3"); 
        } 
        return searchValue; 
    }
	function clearSearchData(tabName) {
		var searchType = document.getElementById("searchType");
		searchType.selectedIndex = 0;
		
		var searchValue = document.getElementById("searchValue");
		searchValue.value = "";		
		
		var status = document.getElementById("statusId"+tabName);
		status.selectedIndex = 0;
		
		var subStatus = document.getElementById("subStatusId"+tabName);
		subStatus.selectedIndex = 0;
		
	}
	
	function open_popup(page) {
		var id = document.getElementById('soID')
		page = page + id.value;				
    	window.open(page,'_blank','width=600,height=450,resizable=1,scrollbar=1');
   	}

	var p_topFilterList;
	var p_filterList;

	function getSelectedStatus(mySelStatusObj,mySelSubStatusObj,myiFrameWindow,tabName, filterId) {
		var selObj = document.getElementById(mySelStatusObj);
		var selectedStatusIndex = selObj.selectedIndex; 
		var subList;
		var subStatus = document.getElementById(mySelSubStatusObj);
        selectedStatus = selObj.options[selectedStatusIndex].value;
        if (selectedStatus != 0) {
			subStatus.disabled = false;
			subList = p_topFilterList[selectedStatus];
			var boolean = 'N';
			if (selectedStatus =='Today' || selectedStatus == 'Inactive') {
               	boolean ='Y';
			}
			if (boolean =='N' ) {	
				subStatus.options.length = 0;
				var o;
				if(subList.length>1) {
					o = new Option('Show All','0',false,false);
					subStatus.options[0]=o;
				}
				for(var i=0;i<subList.length;i++) {
					o = new Option(subList[i].val2,subList[i].val1,false,false);
					subStatus.options[i+1] = o;
				}
			}
		} else {
			subStatus.length=0;
			var f = new Option('Show All','0',false,false);
			subStatus.options[0]=f;
		}
		newco.jsutils.clearAllActionTiles();

		var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;
		iFrameWindow.doStatusSubmit(selectedStatus,null, filterId);
	}

	function getSelectedSubStatus(status, subStatus, myiFrameWindow, filterId) {
		var statusSelObj = document.getElementById(status); 
		var subStatusSelObj = document.getElementById(subStatus); 
		var selectedStatusIndex = statusSelObj.selectedIndex; 
		var selectedStatus = statusSelObj.options[selectedStatusIndex].value;
		var selectedSubStatusIndex = subStatusSelObj.selectedIndex; 
		var selectedSubStatus = subStatusSelObj.options[selectedSubStatusIndex].value; 	
		var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;

		newco.jsutils.clearAllActionTiles();
		iFrameWindow.doStatusSubmit(selectedStatus,selectedSubStatus, filterId);
	}
		
	function sortByColumn(tabName, sortColumnName, statusSortForm, filterId) {
		var statusSelObj = document.getElementById('statusId'+tabName); 
		var subStatusSelObj = document.getElementById('subStatusId'+tabName); 
		var selectedStatusIndex = statusSelObj.selectedIndex; 
		var selectedStatus = statusSelObj.options[selectedStatusIndex].value;
		var selectedSubStatusIndex = subStatusSelObj.selectedIndex; 
		var selectedSubStatus = subStatusSelObj.options[selectedSubStatusIndex].value; 	
		var iFrameWindow = document.getElementById(tabName+'myIframe').contentWindow;
		var iFrameWindowForm = iFrameWindow.document.getElementById(statusSortForm);

		iFrameWindowForm.action = "/MarketFrontend/monitor/PBWorkflowSearch.action?pbFilterId=" + filterId + "&fromWFM=true";

		iFrameWindowForm.status.value = selectedStatus;
		iFrameWindowForm.subStatus.value = selectedSubStatus;

		if(iFrameWindowForm.sortColumnName.value == sortColumnName) {
			if(iFrameWindowForm.sortOrder.value == 'ASC'){
				iFrameWindowForm.sortOrder.value = 'DESC';
			} else {
				iFrameWindowForm.sortOrder.value = 'ASC';
			}
		} else {
			iFrameWindowForm.sortOrder.value = 'ASC';
		}
		iFrameWindowForm.sortColumnName.value = sortColumnName;

		sortImageFlip(sortColumnName, iFrameWindowForm.sortOrder.value, tabName);

		newco.jsutils.clearAllActionTiles();

		iFrameWindow.newco.jsutils.displayModal('loadingMsg'+ _commonSOMgr.widgetId);
		iFrameWindowForm.submit();
	}

	function sortImageFlip(sortColumnName, sortOrder, currentTab) {
		if(sortOrder == 'ASC') {
			$('sortBy'+sortColumnName+currentTab).src = "${staticContextPath}/images/grid/arrow-up-white.gif";
		} else {
			$('sortBy'+sortColumnName+currentTab).src = "${staticContextPath}/images/grid/arrow-down-white.gif";
		}

		if(currentTab == 'Posted' || currentTab == 'Received') {
			$('sortBySpendLimit'+currentTab).style.display = "none";
			$('sortByTimeToAppointment'+currentTab).style.display = "none";
			$('sortByAgeOfOrder'+currentTab).style.display = "none";
		} else {
			$('sortByServiceDate'+currentTab).style.display = "none";
		}

		$('sortByStatus'+currentTab).style.display = "none";
		$('sortBySoId'+currentTab).style.display = "none";
		$('sortBy'+sortColumnName+currentTab).style.display = "block";
	}

	function getSummaryTabCount (tabId) {
		var widgetTitle = dijit.byId(tabId).title;
		var index1 = widgetTitle.indexOf('(');
		var index2 = widgetTitle.indexOf(')');
		var tabCount = widgetTitle.substring(index1+1,index2);
		return tabCount;
	}
</script>	

<c:import url="/jsp/so_monitor/wfm_monitor.jsp" />
</head>

<body class="tundra">	
    
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Power Buyer - Main Page"/>
	</jsp:include>	
	
	<div id="page_margins">
		<div id="page">
			<!-- START HEADER -->
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav"/>
				<tiles:insertDefinition name="newco.base.blue_nav"/>
				<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
			</div>
			<tiles:insertAttribute name="body"/>
			<tiles:insertAttribute name="footer"/>
		</div>
		
		<%-- ss: moved this out of wfm_monitor.jsp --%>
				<form id="formHandler">
					<input type="hidden" id="soID" name="selectedSO" />
					<input type="hidden" id="groupInd" name="groupInd" value="false"/>
					<input type="hidden" id="groupId" name="groupId"/>
					<input type="hidden" id="resId" name="resId" />
					<input type="hidden" id="selectedRowIndex" name="selectedRowIndex" />
					<input type="hidden" id="currentSpendLimit" name="currentSpendLimit" />
					<input type="hidden" id="currentLimitLabor" name="currentLimitLabor"/>
					<input type="hidden" id="currentLimitParts" name="currentLimitParts"/>
					<input type="hidden" id="totalSpendLimit" name="totalSpendLimit" />
					<input type="hidden" id="totalSpendLimitParts"
						name="totalSpendLimitParts" />
					<input type="hidden" id="increasedSpendLimitComment"
						name="increasedSpendLimitComment" />
					<input type="hidden" name="increasedSpendLimitReasonWidget" id="increasedSpendLimitReasonWidget"
																		value="" />	
					<input type="hidden" name="increasedSpendLimitReasonIdWidget" id="increasedSpendLimitReasonIdWidget"
																		value="" />														
					<input type="hidden" name="increasedSpendLimitNotesWidget" id="increasedSpendLimitNotesWidget"
																		value=""/>
					<input type="hidden" id="reasonId" name="reasonId" />
					<input type="hidden" id="requestFrom" name="requestFrom"
						value="SOM" />
					<input type="hidden" id="cancelComment" name="cancelComment" />
					<input type="hidden" id="subject" name="subject" />
					<input type="hidden" id="message" name="message" />
					<input type="hidden" id="theRole" name="theRole" value="${role}" />
					<input type="hidden" id="tab" name="tab" value="${tab}" />
					<input type="hidden" name="isInitialLoad" id="isInitialLoad" value="0" />
				</form>  
	</div>
	<script type="text/javascript" src="${staticContextPath}/javascript/CalendarControl.js"></script>
</body>	
</html>
