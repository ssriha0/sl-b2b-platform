<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="role" value="${roleType}" />

<c:set var="RECEIVED" value="Received" /> 
<c:set var="noJs" value="true" scope="request"/> <%-- tell header not to insert any JS --%>
<c:set var="noCss" value="true" scope="request"/><%-- tell header not to insert any CSS --%>
<c:set var="currentMenu" scope="request" value="orderManagement" /> <%--to be consumed by the header nav to highlight the SOM tab --%>
<c:set var="provider" value="false"  scope="request"/><%-- ss: needed for presentation logic brevity --%>
<c:set var="sladmin" value="false"  scope="request"/>
<c:if test="${roleType == 1}"><c:set var="provider" value="true" scope="request" /></c:if>
<c:if test="${SecurityContext.slAdminInd}"><c:set var="sladmin" value="true" scope="request" /></c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8;no-cache" />
		<!--[if lte IE 8]
			<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<![endif]-->
		<title>ServiceLive: Order Management</title>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/CalendarControl.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/bulletinBoard/main.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/grid.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/orderManagement.css"/>
	<!-- Styles required for Feedback -->
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/feedback.css"/>
	
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.datepicker.css"/>
	<link href="${staticContextPath}/javascript/confirm.css" rel="stylesheet" type="text/css" />
	
	<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome-ie7.min.css" rel="stylesheet" />
	
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
	<script language="JavaScript"src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js" type="text/javascript"></script>
	<!-- Javascripts required for Feedback  -->
	<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/feedback-driver.js"></script>
	<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/plugins/ajaxfileupload.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
	
	<!-- esapi4js dependencies -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/lib/log4js.js"></script>
<!-- esapi4js core -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/esapi.js"></script>
<!-- esapi4js i18n resources -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/i18n/ESAPI_Standard_en_US.properties.js"></script>
<!-- esapi4js configuration -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/Base.esapi.properties.js"></script>

<script type="text/javascript" language="JavaScript">
    Base.esapi.properties.application.Name = "SL Application";
    // Initialize the api
    org.owasp.esapi.ESAPI.initialize();

</script>
    
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
	
    <style type="text/css">
        .boldClass{font-weight:bold;}
    </style>
<script type="text/javascript" >
	/*Function to Show and Hide thee Waiting/Refresh pop up*/
	function fnWaitForResponseShow(){
		jQuery("#overLay").show();
		jQuery("#waitPopUp").show();
	}
	function fnWaitForResponseClose(){
		jQuery("#overLay").hide();
		jQuery("#waitPopUp").hide();
	}
	/**function to get the name of the active tab.*/
	function getActiveTab(){
		var activeTab = "Inbox"; 
		$( ".tab" ).each(function() {
			  var active = $(this).hasClass("active");
			  if(active){
				  activeTab = $(this).prop("id");
				  activeTab = activeTab.substring(3);
			  }
		});
		return activeTab;
	}
	
	//get the total so count for a tab
	function getTotalTabCount(tab){
		var tab = formatTabName(tab);
		var totalActiveTabCount = $("#tab"+tab+" .tab-count span").html();
		return totalActiveTabCount;
	}
	
	function countAreaChars(areaId, limit, evnt) {
		var txt = $.trim($('#' + areaId).val());
		if (txt.length > limit) {
			txt = txt.substring(0, limit);
			$('#' + areaId).val(txt);
			if (!evnt)
				var evnt = window.event;
			evnt.cancelBubble = true;
			if (evnt.stopPropagation) {
				evnt.stopPropagation();
			}
		}
	}
	
	/*
	 *Function to check whether a date is a valid one.
	 * Can be replaaced if a function already exists
	 Input date should be mm/dd/yyyy format
	 */
	 function isDate(txtDate)
		{
			var currVal = txtDate;
			if(currVal === ''){
				return false;
			} 
			var rxDatePattern = /^(\d{1,2})(\/)(\d{1,2})(\/)(\d{4})$/; 
			var dtArray = currVal.match(rxDatePattern); 
			if (dtArray == null){
				return false;
			}  
			dtDay = dtArray[3];
			dtMonth= dtArray[1];
			dtYear = dtArray[5];
			if (dtMonth < 1 || dtMonth > 12)
				return false;
			else if (dtDay < 1 || dtDay> 31)
				return false;
			else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31)
				return false;
			else if (dtMonth == 2){
				var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
				if (dtDay> 29 || (dtDay ==29 && !isleap))
					return false;
			}
			return true;
		}

	function isLeapYear(year){
		var isleap = false;	
		isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
		return isleap;
	}
	
	function formatTabName(activeTab){
		activeTab = activeTab.replace(" ", "-");
		activeTab = activeTab.replace(" ", "-");
		activeTab =  activeTab.replace("-", "\\ ");
		activeTab =  activeTab.replace("-", "\\ ");
		return activeTab;
	}

	function viewLess(so_id){
		var displayedPage=parseInt(document.getElementById(so_id+"displayedPage").value);
		var totalCount = document.getElementsByName(so_id+"_reject_resource").length;	
		var totalPages = parseInt((totalCount/9))+(totalCount%9==0?0:1);
		var previousPage = displayedPage-1;
		if(displayedPage>1){
			document.getElementById(so_id+"page"+displayedPage).style.display= "none";
			document.getElementById(so_id+"displayedPage").value=previousPage;
			document.getElementById(so_id+"_viewMore").style.display ="";
			document.getElementById(so_id+"divider").style.display ="";
		}
		if(previousPage==1){
			document.getElementById(so_id+"_viewLess").style.display ="none";
			document.getElementById(so_id+"divider").style.display ="none";
		}
		var limit = previousPage*9;
	 	if(limit>=totalCount){limit=totalCount;}
		document.getElementById(so_id+"rejResDispCount").innerHTML=limit;		
	}
	
	function viewMore(so_id){
			var displayedPage=parseInt(document.getElementById(so_id+"displayedPage").value);
			var totalCount = document.getElementsByName(so_id+"_reject_resource").length;
			var totalPages = parseInt((totalCount/9))+(totalCount%9==0?0:1);
			var nextPage = displayedPage+1;
			if(displayedPage<totalPages){
				document.getElementById(so_id+"page"+nextPage).style.display= "";
				document.getElementById(so_id+"displayedPage").value=nextPage;
				document.getElementById(so_id+"_viewLess").style.display ="";
				document.getElementById(so_id+"divider").style.display ="";
			}
			if(nextPage==totalPages){
				document.getElementById(so_id+"_viewMore").style.display ="none";
				document.getElementById(so_id+"divider").style.display ="none";
			}
			var limit = nextPage*9;
		 	if(limit>=totalCount){limit=totalCount;}
			document.getElementById(so_id+"rejResDispCount").innerHTML=limit;		
		var limit = nextPage*9;
	 	if(limit>=totalCount){limit=totalCount;}
		document.getElementById(so_id+"rejResDispCount").innerHTML=limit;		
	}
	
	jQuery(document).ready(function(){
		//SLT-2235:New popup for existing firms to accept the new T&C of court notice
	   	<c:if test="${provider && termsLegalNoticeChecked && not isSLAdmin && isPrimaryResource}">	
	   		$("#header").css('z-index','');
			jQuery("#overLay").hide();
			jQuery("#waitPopUp").hide();
	    	var overlayNew = $('<div id="overlayNew"></div>');
	    	overlayNew.show();
	    	overlayNew.appendTo(document.body);
	    	$('.newTermsNoticePopUp').show();
	    	$('.headerSpanScrollDivTCPopup').show();
	    	$(document).on('click', ".reviewLink", function(){
	    			$('.newTermsNoticePopUp').hide();
	    			overlayNew.appendTo(document.body).remove();
	    			window.location.href='/MarketFrontend/allTabView.action?tabView=tab5';
	    			return false;
	    	});	
	   </c:if>
	
	});
	
	
	
	
$(document).ready(function() {
	
	function setStyleForOMTabs(){
		//to make Inbox selected by default and adding the refresh icon
		$("#tabInbox").addClass("inbox active");
		var activeTab = '${omDisplayTab}';
		activeTab = formatTabName(activeTab);
		//to set selected tab as default tab while returning from SOD
		$('.tab').removeClass("active");
		$("#tab"+activeTab).addClass("active");
		
		$("#tab"+activeTab+" .tab-count i").addClass("icon-repeat");
		//setting the icons
		$("#iconInbox").addClass("icon-inbox");
		$("#iconRespond").addClass("icon-reply");
		$("#iconSchedule").addClass("icon-calendar");
		$("#iconAssign\\ Provider").addClass("icon-user");
		$("#iconManage\\ Route").addClass("icon-truck");
		
		// R1	dding OR condition for revisit needed tab		
		$("#iconRevisit\\ Needed").addClass("icon-truck");
//
		$("#iconConfirm\\ Appt\\ window").addClass("icon-time");
		$("#iconPrint\\ Paperwork").addClass("icon-file-alt");
		$("#iconCurrent\\ Orders").addClass("icon-home");
		$("#iconJob\\ Done").addClass("icon-check");
		$("#iconResolve\\ Problem").addClass("icon-exclamation-sign");
		$("#iconCancellations").addClass("icon-remove-sign");
		$("#iconAwaiting\\ Payment").addClass("icon-money");
	}
	
	function setFormValues(criteria,sortOrder,activeTab){
		$("#displayTabFrm").val(activeTab);
		$("#criteriaFrm").val(criteria);
		$("#sortOrderFrm").val(sortOrder);
	}
	
	function loadMainTabData(omDisplayTab){
		$("#orderManagementFiltersAndGrid").load("loadTabData.action?omDisplayTab="+encodeURIComponent(omDisplayTab),function(){
			var count = $("#totalTabCountWithoutFilters").val();
			if(omDisplayTab=='Confirm Appt window'){
				$("#count_ConfirmApptwindow").html(count);
			}
			else{
				omDisplayTab = omDisplayTab.replace(" ","");
	 			$("#count_"+omDisplayTab).html(count);
			}
			fnWaitForResponseClose();
		});
	}
	
	setStyleForOMTabs();
	$(document).on('click', ".tab", function(){
		var selectedTab=this.id;
		if($(this).hasClass("active")){
			return;
		}
		var omDisplayTab = selectedTab.substring(3);
		fnWaitForResponseShow();
		$("#orderManagementFiltersAndGrid").load("loadTabData.action?omDisplayTab="+encodeURIComponent(omDisplayTab),function(){
			var count = $("#totalTabCountWithoutFilters").val();
			if(omDisplayTab=='Confirm Appt window'){
				$("#count_ConfirmApptwindow").html(count);
			}
			else{
				omDisplayTab = omDisplayTab.replace(" ","");
		 		$("#count_"+omDisplayTab).html(count);
			}
			//to remove the selection style from other tabs
			$('.tab').removeClass("active");
			$('.tab-count i').removeClass("icon-repeat");
				//giving the clicked tab selected styling i.e. with refresh icon
			$("#"+selectedTab.replace(/ /g,"\\ ")).addClass("active");
			$("#"+selectedTab.replace(/ /g,"\\ ")+" .tab-count i").addClass("icon-repeat");
			
			var sortOrder  = $("#sortOrderFrm").val();
			var targetTitleId = "hdr"+$("#criteriaFrm").val();
				$(".icon-sort-up").addClass("icon-sort");
				$(".icon-sort-down").addClass("icon-sort");
				$(".icon-sort").removeClass("icon-sort-up").removeClass("icon-sort-down");
				if(targetTitleId != 'undefined' || targetTitleId !=="" || targetTitleId !== "hdr"){
					if(sortOrder == 'asc'){
						$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-up");
						$("#"+targetTitleId+" .icon-sort-up").removeClass("icon-sort");
						$("#"+targetTitleId).prop("sortOrder", "desc");
					}else if(sortOrder == 'desc'){
						$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-down");
						$("#"+targetTitleId+" .icon-sort-down").removeClass("icon-sort");
						$("#"+targetTitleId).prop("sortOrder", "asc");
					}	
					$("table.omscrollerTableHdr .active").removeClass("active");
					$("#"+targetTitleId).parent().addClass("active");	
				}
			fnWaitForResponseClose();
		});
	});


	/*To refresh the currently active tab.*/
	$(document).on('click', ".icon-repeat", function(e){
		var selectedTab=$(this).parent().parent().prop('id');
		var omDisplayTab = selectedTab.substring(3);
		fnWaitForResponseShow();
		$("#"+selectedTab.replace(/ /g,"\\ ")+" .tab-count i").removeClass("icon-repeat").addClass("icon-spinner icon-spin  icon_refresh");
		$("#omMainTab").load("refreshCount.action?omDisplayTab="+encodeURIComponent(omDisplayTab), function(){
			setStyleForOMTabs();
			$(".tab").removeClass("active");
			$(".tab .tab-count i").removeClass("icon-repeat");
			$("#"+selectedTab.replace(/ /g,"\\ ")).addClass("active");
			$("#"+selectedTab.replace(/ /g,"\\ ")+" .tab-count i").addClass("icon-repeat");
			//fnWaitForResponseClose();
			//$("#"+selectedTab.replace(/ /g,"\\ ")).trigger('click');
			loadMainTabData(omDisplayTab);
		});
	});
	
	$("#custResponseDropdown").change(function(e){
        if(this.value == '1'){
			$("#scheduleTimeDiv").css("display","none");
			$("#rescheduleDiv").css("display","none");
			$("#etaDiv").css("display","block");
        }else if(this.value == '2'){
         	$("#scheduleTimeDiv").css("display","block");
         	$("#rescheduleDiv").css("display","none");
         	$("#etaDiv").css("display","block");
        }else if(this.value == '3'){
        	$("#scheduleTimeDiv").css("display","none");
        	$("#rescheduleDiv").css("display","block");
        	$("#etaDiv").css("display","none");

        }
	});
	
	$(document).on('click', ".omHdrSort", function(e){
	
		var targetTitleId = $(this).prop('id');
		var criteria  = targetTitleId.substring(3);
		var sortOrder  = $(this).prop("sortOrder");
		$(".omHdrSort").prop("sortOrder", "asc");
		//$("#currentOrderCount").val($("#hiddenFieldSOCount").val());
		$("table.omscrollerTableHdr .active").removeClass("active");
		$(this).parent().addClass("active");
		fnWaitForResponseShow();
		if(sortOrder == "" || sortOrder == null){
			sortOrder = "asc";
		}
		var activeTab = getActiveTab();
		setFormValues(criteria,sortOrder,activeTab);
		var filterForm = $("#filterForm").serialize();
		$.ajax({
        	url: 'serviceOrderListSort.action',
        	type: "POST",
        	data: filterForm,
			dataType : "html",
			success: function(data) {
				$(".icon-sort-up").addClass("icon-sort");
				$(".icon-sort-down").addClass("icon-sort");
				$(".icon-sort").removeClass("icon-sort-up").removeClass("icon-sort-down");
				if(targetTitleId != 'undefined' || targetTitleId !=="" || targetTitleId !== "hdr"){
					if(sortOrder == 'asc'){
						$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-up");
						$("#"+targetTitleId+" .icon-sort-up").removeClass("icon-sort");
						$("#"+targetTitleId).prop("sortOrder", "desc");
					}else{
						$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-down");
						$("#"+targetTitleId+" .icon-sort-down").removeClass("icon-sort");
						$("#"+targetTitleId).prop("sortOrder", "asc");
					}
				}
				fnWaitForResponseClose();
				$("#omSubDiv").html(data);
				//code to make lastName bold if customerInfo is sorted
				if(criteria == 'CustInfo'){
				      $(".lastNameBold").addClass("boldClass");
				 }
				var displayingCount = $("#currentOrderCountOnSort").val();
				$("#currentOrderCount").val(displayingCount);
				var totalNumOfOrders = $("#totalOrderCount").val();
				$("#XofYDisplay").html("Showing "+displayingCount+" of "+totalNumOfOrders);
			},
			error: function(xhr, status, err) {
				location.href ="${contextPath}/homepage.action";
	        }
	    });
	});

	/*Precall Model & Confirm Appointment Model*/
	$(document).on('click', "#viewServiceLocationNotesLink", function(e){
		if($('#serviceLocationNotesDiv').is(':visible')){
			$("#serviceLocationNotesDiv").css("display","none");
        	$("#viewServiceLocationNotesLinkrightArrow").css("display","block");
        	$("#viewServiceLocationNotesLinkdownArrow").css("display","none");
    		$("#serviceLocationNotesEditFlaghidden").val("false");
		}
		else{
        	$("#viewServiceLocationNotesLinkdownArrow").css("display","block");
        	$("#viewServiceLocationNotesLinkrightArrow").css("display","none");
			$("#slNotes").css("display","block");
			$("#serviceLocationNotesDiv").css("display","block");
			$("#viewLocationNotesLink").css("display","block");
			$('textarea[name="seviceLocationNotes"]').css('display', 'none');
		}
	});
	$(document).on('click', "#viewLocationNotesLink", function(e){
 		var slNotes = $("#originalSlNotes").prop("value"); 
		$("#seviceLocationNotes").prop("value",slNotes); 
		$('textarea[name="seviceLocationNotes"]').css('display', 'block').focus();
		$("#viewLocationNotesLink").css("display","none");
		$("#slNotes").css("display","none");
		$("#serviceLocationNotesEditFlaghidden").val("true");
	});
	$(document).on('click', "#viewSpecialIntructionsLink", function(e){
		if($('#splInstrDiv').is(':visible')){
			$("#splInstrDiv").css("display","none");
			$("#viewSpecialIntructionsLinkrightArrow").css("display","block");
        	$("#viewSpecialIntructionsLinkdownArrow").css("display","none");
    		$("#specialInstructionsEditFlaghidden").val("false");
		}
		else{
			$("#viewSpecialIntructionsLinkdownArrow").css("display","block");
        	$("#viewSpecialIntructionsLinkrightArrow").css("display","none");
			$('#splInstr').css("display","block");
			$('#splInstrDiv').css("display","block");
			$('textarea[name="specialIntructions"]').css('display', 'none');
			$("#viewIntructionsLink").css("display","block");		
		}
	});
	$(document).on('click', "#viewIntructionsLink", function(e){
		var splInstr = $("#originalSplInstr").prop("value");	
		$("#specialIntructions").prop("value",splInstr);
		$('#splInstr').css("display","none");
		$('textarea[name="specialIntructions"]').css('display', 'block').focus();
		$("#viewIntructionsLink").css("display","none");
		$("#specialInstructionsEditFlaghidden").val("true");
		
	});
	$(document).on('click', "#viewProductInfoLink", function(e){
		if($('#productInfoDiv').is(':hidden')) {
			$("#productInfoDiv").css("display","block");
			$("#viewProductInfoLinkrightArrow").css("display","none");
        	$("#viewProductInfoLinkdownArrow").css("display","block");
		}else{
			$("#viewProductInfoLinkdownArrow").css("display","none");
        	$("#viewProductInfoLinkrightArrow").css("display","block");
			$("#productInfoDiv").css("display","none");
		}
	});
	$(document).on('click', "#closePrecallModel", function(e){
		$("#preCallModal").jqmHide();
	});
	$(document).on('click', "#closeConfirmAppointmentModel", function(e){
		$("#confirmAppointmentModal").jqmHide();
	});
	$(document).on('click', "#closePrecallHistoryModel", function(e){
		$("#precallHistoryModel").jqmHide();
	});
	$(document).on('click', "input[name=custavailablechkbox]", function(e){
		var checked = 0;
		if($("#custavailablechkbox").is(':checked')) {
			checked =1;
		}
		else{
			checked =0;
		}
		 if(checked == '1'){
			 $("#reminderAppointmentToday_schedule").css("display","none");
				$(".accordionContent").hide();
				$("#timeDiv").show();
		         $('#confirmScheduleDiv').hide();
		   		 $("#assignProvidersDiv").css("display","block");
		    $("#reminderAppointmentToday").css("display","none");
		 	$("#custNotAvailableReasonDropDownDiv").css("display","block");
		 	$("#confirmOrderAccordion").css("display","none");
		 	$("#confirmServiceAccordion").css("display","none");
			$("#confirmScheduleAccordion").css("display","none");
			$("#custResponseDropdownDiv").css("display","none");
			$("#scheduleTimeDiv").css("display","none");
			$("#rescheduleDiv").css("display","none");
			$("#etaDiv").css("display","block");
			
			 if($("#resourceAssigned").val() == 'false' ){
			   $("#reassignProviderLink").css("display","none");
			   $("#assignProviderLink").css("display","block");
			 }else if($("#resourceAssigned").val() == 'true' ){
			   $("#reassignProviderLink").css("display","block");
			   $("#assignProviderLink").css("display","none");
			 }
			$("#assignProviderDropdown").css("display","none");
			$("#customerAvailableOrNothidden").prop("value",1);
			$("#assignedProviderDiv").show();
			var todayInd = $("#today").prop("value");
			var resourceAssignedInd=$("#resourceAvailableInd").prop("value");
			if(todayInd == 'true'){
				if(resourceAssignedInd == 'true')
					{
					$("#etaWarningAppointmentToday").css("display","block");
					$("#etaWarningAppointmentToday div#informationWindowHdr").css("display","none");
					}
				else
					{
					$("#reminderAppointmentToday div#informationWindowHdr").css("display","none");
			//		$("#confirmScheduleDiv").css("display","block");
					$("#reminderAppointmentToday").css("display","block");
					}
			}
			else{
				var source = $("#source").prop("value");
 		         if(source == 'PreCall'){
 					 $("#reminderAppointmentNotToday").css("display","block");
 			        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
 		         }
 		         else{
 					 $("#reminderAppointmentNotTodayConfirmAppt").css("display","block");
 			        $("#reminderAppointmentNotToday").css("display","none");
 		         }
				$("#reminderAppointmentToday").css("display","none");
				$("#etaWarningAppointmentToday").css("display","none");
			}
		   	
 			$("#custNotAvailableReasonDropdown").prop("value",-1);
 			$("#etaDropdown").prop("value",-1);
			$("#customerNotAvailableErrorMsg").css("display","none");
			$("#warningAppointmentToday").css("display","none");
			$("#assignWarningAppointmentToday").css("display","none");
		    $('#assignProvidersDiv').css("display","block");
		 }else{
	   		 	$(".accordionContent").hide();
				$("#timeDiv").hide();
	         	$('#confirmScheduleDiv').hide();
	         	$('#confirmOrderDiv').show();

	   		 	$("#assignProvidersDiv").css("display","none");
	   		 $("#reminderAppointmentToday_schedule").css("display","none");
		 	$("#custNotAvailableReasonDropDownDiv").css("display","none");
		 	$("#confirmOrderAccordion").css("display","block");
		 	$("#confirmServiceAccordion").css("display","block");
			$("#confirmScheduleAccordion").css("display","block");
			$("#scheduleTimeDiv").css("display","none");
			$("#rescheduleDiv").css("display","none");
			$("#etaDiv").css("display","none");
   		    $("#timeWindowDropdown").prop("value",-1);
   		    $("#assignProviderDropdown").css("display","none");
   		    if($("#resourceAssigned").val() == 'false' ){
			   $("#reassignProviderLink").css("display","none");
			   $("#assignProviderLink").css("display","block");
			 }else if($("#resourceAssigned").val() == 'true' ){
			   $("#reassignProviderLink").css("display","block");
			   $("#assignProviderLink").css("display","none");
			 }
   		 	$("#assignedProviderDiv").show();
   		 	var source = $("#source").prop("value");
	         if(source == 'PreCall'){
				 $("#reminderAppointmentNotToday").css("display","block");
			        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
	         }
	         else{
				 $("#reminderAppointmentNotTodayConfirmAppt").css("display","block");
			        $("#reminderAppointmentNotToday").css("display","none");
	         }
   		 	var todayInd = $("#today").prop("value");
			if(todayInd=='true'){
				 $("#reminderAppointmentToday").css("display","block");
				 $("#reminderAppointmentToday_schedule").css("display","block");
			    $("#reminderAppointmentNotToday").css("display","none");
		        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
			}
			else{
		        $("#reminderAppointmentToday").css("display","none");
				 $("#reminderAppointmentToday_schedule").css("display","none");
				 var source = $("#source").prop("value");
  		         if(source == 'PreCall'){
  					 $("#reminderAppointmentNotToday").css("display","block");
 			        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
  		         }
  		         else{
  					 $("#reminderAppointmentNotTodayConfirmAppt").css("display","block");
 			        $("#reminderAppointmentNotToday").css("display","none");

  		         }			
  		      }
 			$("#etaDropdown").prop("-1");
			$("#scheduleFromTime").val("-1");
			$("#scheduleToTime").val("-1");
			$("#preCallSpecificDate").prop("checked","checked");
			$("#precallSpecificRescheduleFromDate").val("");
			$("#precallSpecificTimeDropdown").val("-1");
			$("#precallRangeRescheduleFromDate").val("");
			$("#precallRangeRescheduleToDate").val("");
			$("#rescheduleRangeDateStartTimeDropdown").val("-1");
			$("#rescheduleRangeDateEndTimeDropdown").val("-1");
			$("#reasonForRescheduleDropdown").val("-1");
			$("#rescheduleCommentsTextarea").val("");
	   		 $('#assignProvidersDiv').css("display","none");
			
		    }	
  			$("#custResponseDropdownDiv").css("display","block");
			var slNotes = $("#slNotes").html();
  			var splInstr = $("#splInstr").html();
  			$("#seviceLocationNotes").prop("value",slNotes);
  			$("#specialIntructions").prop("value",splInstr);
  			$("#serviceLocationNotesDiv").css("display","none");
			$("#splInstrDiv").css("display","none");
			$("#serviceLocationNotesEditFlaghidden").prop("value","false");
			$("#specialInstructionsEditFlaghidden").prop("value","false");
			$("#customerNotAvailableErrorMsg").css("display","none");
			$("#warningAppointmentToday").css("display","none");
			$("#assignWarningAppointmentToday").css("display","none");
			$("#viewServiceLocationNotesLinkrightArrow").css("display","block");
     		$("#viewServiceLocationNotesLinkdownArrow").css("display","none");
     		$("#viewSpecialIntructionsLinkrightArrow").css("display","block");
     		$("#viewSpecialIntructionsLinkdownArrow").css("display","none");
     		$("#viewProductInfoLinkrightArrow").css("display","block");
     		$("#viewProductInfoLinkdownArrow").css("display","none");
 			$("#timeWindowDropdown").prop("value","-1");
			$("#preCallErrDiv").css("display","none");

	});
	
	
	        	
	$(document).on('click', "input[name=confirmCustomer]", function(e){    
	        var type = $('input:radio[name=confirmCustomer]:checked').val();
	        if(type =='1'){
                $('#selectTime').css('display','block');
                 $('#assignProviderMsg').css('display','block');
                 $('#warningMsg').html('Customer will receive a confirmation call prior to service start.');
                
	        }else{
	                $('#selectTime').css('display','none');
	                 $('#assignProviderMsg').css('display','block');
	                 $('#warningMsg').html('Customer will receive a confirmation call prior to service start.You may submit reschedule if customer prefers a different date.');
	        }
        
    }); 

	$(document).on('click', "input[name=confirmCustAvailability]", function(e){
        var type = $('input:radio[name=confirmCustAvailability]:checked').val();
        if(type =='1'){
        	 	$('#updateTimes').css('display','none');
        	 	$('#confirmETA').css('display','none');
                $('#confirmAvailableInfo').css('display','block');
                $('#availableDivConfirm').css('display','block');
                $('#notAvailableReasonDivConfirm').css('display','none');
                $('#confirmAssignProviderMsg').css('display','block');
                $('#apptDetailsConfirm').css('display','block');
                $('#submitSectionConfirmCall').css('display','block');
                $('#warningMsg').html('You may submit reschedule if customer prefers a different date.');
                $('#selectConfirmCustAction').css('display','block');
        }else{
        	 	$('#updateTimes').css('display','block');
        		$('#confirmCust').css('display','block');
                $('#confirmAvailableInfo').css('display','none');
                $('#availableDivConfirm').css('display','none');
                $('#notAvailableReasonDivConfirm').css('display','block');
                $('#submitSectionConfirmCall').css('display','block');
                $('#apptDetailsConfirm').css('display','block'); 
                $('#selectConfirmCustAction').css('display','none');
                $('#confirmSelectTime').css('display','none');
                $('#confirmETA').css('display','block');
                 var resourceAssigned = $("#resourceAssigned").val();
				 var todayInd = $("#todayInd").val();
                 if(resourceAssigned=='false' && todayInd=='true'){
                  $('#confirmAssignProviderMsg').css('display','block');
                  $('#warningMsg').html('The appointment is Today! You must assign a provider and select appointment time window.You may submit reschedule if customer is not reachable.');
                 }else{
                	$('#warningMsg').html("Call back the customer to confirm the appointment");	 
                	$('#confirmAssignProviderMsg').css('display','none');
                 }
                
        }
        }); 
        /* Precall End*/
       $(document).on('click', "input[name=confirmCustAction]", function(e){
        	var type = $('input:radio[name=confirmCustAction]:checked').val();
        	if(type =='1'){
                 $('#confirmSelectTime').css('display','none');
                 $('#confirmETA').css('display','block');
                
        	}else if(type =='2'){
 				$('#confirmSelectTime').css('display','block');    
 				$('#confirmETA').css('display','block');    
 				
 			}else if(type =='3'){
 				$('#confirmSelectTime').css('display','none');  
 				$('#confirmETA').css('display','none');
 			}
        }); 
         
         /**Collapse the child SO division*/
         $(document).on('click', ".expand_child", function(e){
     		var id = $(this).prop("id");
     		var soId = id.replace("child_tr_","");
     		if ($("#child_tilte_more_"+soId).is(':hidden')) {
     			$("#child_tr_"+soId+" .collapse_child").removeClass("icon-caret-right").addClass("icon-caret-down");
     			$("#child_tilte_more_"+soId).show();
     		}else{
     			$("#child_tr_"+soId+" .collapse_child").removeClass("icon-caret-down").addClass("icon-caret-right");
     			$("#child_tilte_more_"+soId).hide();
     		}
     	});

        $(document).on('click', ".dropdown", function(e){
        	 $(".dropdown-menu").hide();
        	 $(".btn-group").removeClass("open");
        	 $(this).parent().removeClass("open").addClass("open");
        	 $(this).parent().children(".dropdown-menu").show();
         });
         
        $(document).on('click', ".link", function(e){
        	 if($(this).parent().parent().hasClass("dropdown-menu")){
        		 $(this).parent().removeClass("open");
        		 $(".dropdown-menu").hide();
        	 }        	
         });
         
		function saveConfirmCall(){
			$("#errorDiv").css("display","none");
			var resourceAssigned = $("#resourceAssignedConfirm").val();
			var todayInd = $("#todayIndConfirm").val();
			var notAvailableReason = $("#notAvailableReasonConfirm").val();
			if(resourceAssigned=="true" && todayInd=="true" && notAvailableReason=="-1"){
				$("#errorDivConfirm").css("display","block");
			}
			return false;
		}  
		
		$("#assignToProvider").click(function(e){
			var routeMethod = $("#routeMethod").val();
			var groupInd = $("#groupInd").val();
			var soId = $("#accept_so_id").val();
			if('true' == routeMethod){
     			$('#loadTimerDiv').hide();	
     			$("#acceptOrder").show();
     		}else{
     			jQuery('#loadTimerDiv').load("loadTimer.action?assignee=typeFirm&soId="+soId+"&groupInd="+groupInd, 
     				function(){	
     					var val = $("#timerVal").val();
     					if(0 != val){
     						$("#acceptOrder").hide();
     				    	loadTimer();	
     						$('#loadTimerDiv').show(); 
     					}else{
     						$('#loadTimerDiv').hide();
     					}
     				});	
     		}
			if($('#assignToProvider').is(':checked')){
				$("#providerList").show();
			}else{
				$("#providerList").hide();
				$("#serviceProviders option[value='0']").prop("selected","selected");
			}
		});
	
		$("#rescheduleComments").keyup(function(){
			countAreaChars($(this).prop("id"), '250', this.event);
		});
		$("#rescheduleComments").keydown(function(){
			countAreaChars($(this).prop("id"), '250', this.event);
		});
		$("#rescheduleComments").blur(function(){
			countAreaChars($(this).prop("id"), '250', this.event);
		});
		$("#rescheduleComments").click(function(){
			countAreaChars($(this).prop("id"), '250', this.event);
		});
		
		$("#subject").keydown(function(){
			countAreaChars($(this).prop("id"), '100', this.event);
		});
		$("#subject").keyup(function(){
			countAreaChars($(this).prop("id"), '100', this.event);
		});
		$("#subject").blur(function(){
			countAreaChars($(this).prop("id"), '100', this.event);
		});
		$("#message").keydown(function(){
			countAreaChars($(this).prop("id"), '750', this.event);
		});
		$("#message").keyup(function(){
			countAreaChars($(this).prop("id"), '750', this.event);
		});
		$("#message").blur(function(){
			countAreaChars($(this).prop("id"), '750', this.event);
		});
		//reset filters
		jQuery('body').on("click", "#resetLink", function(){
			var isFilterOn = validateFilterSelection();
			$('input:checkbox').removeAttr('checked');
			hideAllFilter();
			$('#defaultValMarket').html("-Select-");
			$('#defaultValProvider').html("-Select-");
			$('#defaultValRoutedProvider').html("-Select-");
			$('#defaultApptDate').html("-Select-");
			$('#defaultValStatus').html("-Select-");
			$('#defaultValProvider').html("-Select-");
			$('#defaultJobDoneSubStatus').html("-Select-");
			$('#defaultCurrentOrdersSubStatus').html("-Select-");
			//R12.0 Sprint3 cancellations substatus
			$('#defaultCancellationsSubStatus').html("-Select-");
			//R12.0 Sprint4 substatus filter in revisit needed tab
			$('#defaultRevisitSubStatus').html("-Select-");
			
			$("option:selected").removeProp("selected");
			$(".selectedProCheckbox").hide();
			//$('#tdApptDate').width("22%");
	      	$('#selectApptDateOptionsErrorMessage').hide();
	      	$('#fromDate').val("");
	      	$('#toDate').val("");
	      /*	$('#fromDate').hide();
			$('#To').hide();
			$('#toDate').hide();*/
			$('#apptDateRangeDiv').hide();
			$('#selectApptDate').show();
	    	$("#toDate").datepicker('destroy'); //need to re-initialize the datepicker for field 2
			if(null != document.getElementById('selectedApptDate')){
				$('#selectedApptDate').val("");
			}
	    	if(null != document.getElementById('selectedStatus')){
	    		$('#selectedStatus').val("");
	    		$('#selectedFilterStatus').val("");
	    	}
	    	if(null != document.getElementById('selectedSubStatus')){
	    		$('#selectedSubStatus').val("");
	    		$('#selectedFilterSubStatus').val("");
	    	}
	    	if(null != document.getElementById('selectedScheduleStatus')){
	    		$('#selectedScheduleStatus').val("");
	    		$('#selectedFilterScheduleStatus').val("");
	    	}
	     		$("#resetFilterInd").prop("value",false);
	    	if(isFilterOn){
	    		$("#resetFilterInd").prop("value",true);
	    		var formData = $('#filterForm').serializeArray();
	    		$("#applyFilterInd").val("0");
	    		fnWaitForResponseShow();
	    		$('#orderManagementFiltersAndGrid').load('loadTabData.action', formData, function(){
	    			/* var criteria  = $("#criteriaFrm").val();
		    		if(criteria == 'CustInfo'){
						$(".lastNameBold").addClass("boldClass");
					} */
	    			fnWaitForResponseClose();
	    		});
	    	}
		});
});

	function openDatepicker(clickedId){

		$('#'+clickedId).datepicker({dateFormat:'mm/dd/yy', minDate: new Date(2008, 12 - 1, 1), yearRange: '-50:+50', numberOfMonths: 3}).datepicker( "show" );
	}
	
	function showApptDateRange(obj){
		var  selectedOption = $(obj).prop("id");
		//$(obj).parent().parent().parent().width("28%");
		$('#tdApptDate').width("300px");
		if (selectedOption == 'app_dt_5'){
			$('#selectApptDate').hide();
			$('#apptDateRangeDiv').show();
			/*$('#fromDate').show();
			$('#To').show();
			$('#toDate').show();*/
			}
	}
	
	function hideOtherFilters(){
		$("#selectProvidersOptions").hide();
		$("#selectMarketsOptions").hide();
		$("#selectJobDoneSubStatusOptions").hide();
		$("#selectCurrentOrdersSubStatusOptions").hide();
		//R12.0 Sprint3 cacelation substatus
		$("#selectCancellationsSubStatusOptions").hide();
		//R12.0 Sprint4 revisit substatus
		$("#selectRevisitSubStatusOptions").hide();
		var date1;
		var date2;
		var dateDisplay = document.getElementById('fromDate').style.display;
		var messageError= '<fmt:message bundle="${serviceliveCopyBundle}"
			key="error.ordermanagement.apptDate" />';   
		if(dateDisplay == "block"){
			date1 = $('#fromDate').val();
			date2 = $('#toDate').val();
			if(date1==null || date1==""||date2==null ||date2==""){
		      	document.getElementById('selectApptDateOptionsErrorMessage').style.display= "block";

				document.getElementById('selectApptDateOptionsErrorMessage').innerHTML = messageError;
			}
			else{
		      	document.getElementById('selectApptDateOptionsErrorMessage').style.display= "none";

				document.getElementById('selectApptDateOptionsErrorMessage').innerHTML = "";

			}
		}
		
		
	}
	
	function checkApptDateError(clickedIds){
		var date = $('#'+clickedIds).val();
		
		var width = $('#'+clickedIds).width();
		var pstn = $('#'+clickedIds).position();

		if(date==null || date==""){
		var messageError= '<fmt:message bundle="${serviceliveCopyBundle}"
			key="error.ordermanagement.apptDate" />';   
			if(clickedIds=='fromDate')
				{
			$('#selectApptDateOptionsErrorMessage').css({ position: "absolute",top: pstn.top-30,left: pstn.left});

			//  $("#selectApptDateOptionsErrorMessage").show();

			document.getElementById('selectApptDateOptionsErrorMessage').innerHTML = messageError;
				}
			
		}
		else{
			var date1;
			var date2;
			date1 = $('#fromDate').val();
			date2 = $('#toDate').val();
			if((date1!=null ||date1==" ")&&(date2!=null ||date2==" "))
				{
			var fromDt = txtToDate(date1);
			var toDt = txtToDate(date2);
				if(fromDt>toDt)
					{
					$('#selectApptDateOptionsErrorMessage').css({ position: "absolute",top: pstn.top-30,left: pstn.left});
					$("#selectApptDateOptionsErrorMessage").css("top",pstn.top-55);
					document.getElementById('selectApptDateOptionsErrorMessage').style.display= "block";
					}
					else
				{
	      	document.getElementById('selectApptDateOptionsErrorMessage').style.display= "none";

			document.getElementById('selectApptDateOptionsErrorMessage').innerHTML = "";
				}
				}
		}
	}
		
	function validateFilterDate(){
		var fromDate = $('#fromDate').val();
		var toDate   = $('#toDate').val();
		var message="";
		if(null == fromDate || fromDate == "" || null == toDate || toDate == ""){
			message= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.apptDate" />'; 
			if((null == fromDate || fromDate == "" )&& (null == toDate || toDate == ""))
				{     
				$('#resetLink').click();
				var formData = $('#filterForm').serializeArray();
	    		$("#applyFilterInd").val("0");
	    		fnWaitForResponseShow();
	    		$('#orderManagementFiltersAndGrid').load('loadTabData.action', formData, function(){
	    			fnWaitForResponseClose();
	    		});
				}
		}else{
			if(!isDate(fromDate)){
				message = '<p>Please provide a valid start Date in the format MM/DD/YYYY.</p>';
			}else if(!isDate(toDate)){
				message = '<p>Please provide a valid end Date in the format MM/DD/YYYY.</p>';
			}else{
				var msg = validateDateRange(fromDate, toDate);
				if(msg !== ""){
					message = "<p>"+msg+"</p>";
				}
			}
		}
		if(message!==""){
			$('#selectApptDateOptionsErrorMessage').html(message);
			$('#selectApptDateOptionsErrorMessage').show();
			return false;
		}
		return true;
	}
	
	function checkFilterSubmit(){
		if(validateFilterDate()){
			loadAfterAssignProvider();
		}
	}

	//display of so title on hovering the link
	function showTitle(obj){
		var id = $(obj).prop("id");
 		var uniquePart = id.replace("soTitle","");
		//var rowNum = $(obj).prop("rowNum");
		$('#soTitleDivs_'+uniquePart).show();
	}
	
	//display view Pdf button
	function displayPdfButton(){
		 var checkedSoIds=[];
		 var checkedSoIdsCount;
			// actual code
			 var checkedSoId;
		       $('.selectPdf:checked').each(function() {
		        	checkedSoId = this.value;
		            checkedSoIds.push(checkedSoId);
		       });
		
		 checkedSoIdsCount = checkedSoIds.length;
		 if(checkedSoIdsCount>0){
				$("#printPaperWork").css("display","block");
		 }else{
				$("#printPaperWork").css("display","none");
		 }
	}
	
	function hideTitle(obj){
		var id = $(obj).prop("id");
 		var uniquePart = id.replace("soTitle","");
		$('#soTitleDivs_'+uniquePart).hide();
	}
	
	
	//display of so substatus on hovering 
	function showSoSubStaus(obj){
		//var value=$(obj).html();
		//value=value.replace(/&nbsp;/g,'');
		//value=value.trim();
		var id = $(obj).prop("id");
 		var uniquePart = id.replace("soSubStaus","");
		//var rowNum = $(obj).prop("rowNum");
		var value=$('#soSubStausDivs_'+uniquePart).text();
		value=$.trim(value);
		var totalLength=value.length; 
		 
		if(value!='' && totalLength>12)
		{ 
		$('#soSubStausDivs_'+uniquePart).show();
		}
		
	}
	
	
	
	function hideSoSubStaus(obj){
		var id = $(obj).prop("id");
 		var uniquePart = id.replace("soSubStaus","");
		$('#soSubStausDivs_'+uniquePart).hide();
	}
	
	
	
	function showTitleChild(obj){
		var rowNum = $(obj).prop("rowNum");
		$('#soTitleDivs_child_'+rowNum).show();
	}
	
	function hideTitleChild(obj){
		var rowNum = $(obj).prop("rowNum");
		$('#soTitleDivs_child_'+rowNum).hide();
	}
		
	function showPrimary(obj){
		var rowNum = $(obj).prop("rowNum");
		//$('#primary_'+rowNum).css({backgroundColor: '#f7fc92',position: "absolute"});
		$('#primary_'+rowNum).show();
	}
	
	function hidePrimary(obj){
		var rowNum = $(obj).prop("rowNum");
		$('#primary_'+rowNum).hide();
	}

	function showAlternate(obj){
		var rowNum = $(obj).prop("rowNum");
		//$('#alternate_'+rowNum).css({backgroundColor: '#f7fc92',position: "absolute"});
		$('#alternate_'+rowNum).show();
	}

	function hideAlternate(obj){
		var rowNum = $(obj).prop("rowNum");
		$('#alternate_'+rowNum).hide();
	}
	

	/*On clicking Reject So button in Order management pop up*/
	function submitRejectSO(so_id){  
		if(!validateRejectForm(so_id)){
			return false;
		}
		$("#rejectOrder").css({"cursor":"wait"});
		var venderRejectCommentDesc=$("#vendor_resp_comment").prop("value");
		var rcId = $("#"+so_id+"_reasonCodeList option:selected").val();
		var checkedResources;
		var bidInd = $("#bidInd").prop("value");
		if(bidInd==0){
		var rejResources = document.getElementsByName(so_id+"_reject_resource");
			checkedResources = formatSelectedProviderIds(rejResources);
		}
		else{
			checkedResources = $("#resourceId").val()+",";
		}
		
		var groupInd = $("#groupInd").val();
		$.ajax({
        	url: 'rejectServiceOrder.action',
        	type: "POST",
        	data: {soId : so_id,rcId : rcId, rejectCommentDesc : venderRejectCommentDesc, resources : checkedResources, groupInd : groupInd},
			dataType : "json",
			success: function(data) {
				if(data.omErrors.length !== 0 ){
					$.each(data.omErrors, function(index,value){
						$("#"+so_id+"_reject_error_msg").append(value.msg);
					});	
						$("#"+so_id+"_reject_error").show();
				}else{
					$("#rejectOrder").jqmHide();
					$('.icon-repeat').trigger('click');
					$("#omSuccessDiv").html("<div  class='successBox'><p>"+$ESAPI.encoder().encodeForHTML(data.result)+"</p></div>");
					
					$("#omSuccessDiv").show();
				}	
			},
			error: function(xhr, status, err) {
				location.href ="${contextPath}/homepage.action";
	        }
	 	}); 
	}
	
	function validateRejectForm(so_id){
		var rejResources = document.getElementsByName(so_id+"_reject_resource");
		var checkedResources=formatSelectedProviderIds(rejResources);
		var bidInd = $("#bidInd").prop("value");
		if(bidInd==0 && checkedResources==''){
			$("#"+so_id+"_reject_error_msg").html("Please select 1 or more providers first");
			$("#"+so_id+"_reject_error").show();
			return false;
		}
		var selectedReasonCode = $("#"+so_id+"_reasonCodeList option:selected").val();
		if(selectedReasonCode == -1){
			$("#"+so_id+"_reject_error_msg").html("Please select reason to reject");
			$("#"+so_id+"_reject_error").show();
			return false;
		}
		venderRejectRreasonDesc=$("#vendor_resp_comment").val();
		
		var index = $("#"+so_id+"_reasonCodeList option:selected").index();
		
		if((index==3 || index==6 || index==7 || index==8)&& ($.trim($('#vendor_resp_comment').val())=="")) {
			$('#vendor_resp_comment').val($.trim($('#vendor_resp_comment').val()));     //Added
		    $("#"+so_id+"_reject_error_msg").html("Please enter comment for selected reason.");
			$("#"+so_id+"_reject_error").show();
		    return false;	
		}
		$('#vendor_resp_comment').val($.trim($('#vendor_resp_comment').val()));   	//Added
		return true;
	}

	//Added For Reject Reason Code				
  	function doOnChangeValue(Event, textArea, limit) {
	jQuery(document).ready(function($) {
	var remLength=limit;
	
	if (textArea.value.length > limit) {
	  //textArea.value=textArea.value.substring(0,limit); return;
		textArea.value = textArea.value.substring(0, 225);    // Added
	  return imposeMaxLength(Event, textArea, limit);
	  }
	else{
		 remLength = limit - textArea.value.length;
		 var displayMsg ="Remaining Length: "+remLength;
	 	 document.getElementById("remaining_count").style.display='';
		 document.getElementById("remaining_count").innerHTML=displayMsg;
	}

	}); 
    }


	function checkAndMakeCommentMendatory(Object){
	     var index=Object.selectedIndex;
	    jQuery(document).ready(function($) {
	    if(index==3 || index==6 || index==7 || index==8)
	    document.getElementById("comment_optional").style.display='';
	    else
	    document.getElementById("comment_optional").style.display='none';
	    });
	    }
	 
    function imposeMaxLength(Event, Object, MaxLen){
     return (Object.value.length < MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
    }

    function removeWhiteSpaceForComment() {
         var myTxtArea = document.getElementById('vendor_resp_comment');
         myTxtArea.value =""; 
    }
 //Ending changes For Reject Reason Code
	function formatSelectedProviderIds(rejResources){
		var checkedResources = "";
		for(var i = 0; i < rejResources.length; i++){
			if(rejResources[i].checked) {
				var val1= rejResources[i].id;			
				var resource_id=val1.substring(33,val1.length);
				checkedResources+=resource_id+",";
			}
		}
		return checkedResources;
	}
	
	function clearErrRejectForm(so_id){
		$("#"+so_id+"_reject_error").hide();
		$("#"+so_id+"_reject_error_msg").html("");
	}
	
	function validateFilterSelection(){
			var market = $("#defaultValMarket").html();
			var providers = $("#defaultValProvider").html();
			var routedProviders = $("#defaultValRoutedProvider").html();
			var status = $("#defaultValStatus").html();
			var subStatus =  $("#defaultSubValStatus").html();
			var scheduleStatus =  $("#defaultScheduleValStatus").html();
			var date = $("#defaultApptDate").html();
			var toDate = $.trim($("#toDate").val());
			var fromDate = $.trim($("#fromDate").val());
			//var prov = $("#defaultValProvider").val();
			var range =$("#selectedApptDate").val();
			var selectInd = $("#selectInd").val();
			var marketResetInd = $("#marketResetInd").val();
			var providerResetInd = $("#providerResetInd").val();
			var routedProviderResetInd = $("#routedProviderResetInd").val();
			var jobdonesubStatus = $("#defaultJobDoneSubStatus").html();
			var jobDoneSubStatusResetInd = $("#jobDoneSubStatusResetInd").val();
			var curOrdersubStatus = $("#defaultCurrentOrdersSubStatus").html();
			var curOrderSubStatusResetInd = $("#currentOrdersSubStatusResetInd").val();
			//R12.0 Sprint3 cancellation substatus
			var cancelsubStatus = $("#defaultCancellationsSubStatus").html();
			var cancelSubStatusResetInd = $("#cancellationsSubStatusResetInd").val();
			//R12.0 Sprint4 revisit substatus
			var revisitsubStatus = $("#defaultRevisitSubStatus").html();
			var revisitSubStatusResetInd = $("#revisitSubStatusResetInd").val();
			
			if((null != market && market !=='-Select-') || (null != providers && providers !== '-Select-') || (null != routedProviders && routedProviders !== '-Select-') || (null!= status && status !== '-Select-') || (null!=subStatus && subStatus !=="-Select-") || (null!= scheduleStatus && scheduleStatus !== "-Select-") ||( range !== "range" && date !== "-Select-") || (null != selectInd && 'true' == selectInd) || (null != jobdonesubStatus && jobdonesubStatus !=='-Select-') || (null != curOrdersubStatus && curOrdersubStatus !== '-Select-' ) ||(null != cancelsubStatus && cancelsubStatus !== '-Select-' )||(null != revisitsubStatus && revisitsubStatus !== '-Select-' )){ 
				if(range === 'range' && (fromDate ==="" || toDate ==="")){
					return false;
				}
				return true;
			}
			else if((marketResetInd ==1) ||(providerResetInd ==1) ||(routedProviderResetInd ==1) || (jobDoneSubStatusResetInd == 1) || (curOrderSubStatusResetInd==1) || (cancelSubStatusResetInd==1) || (revisitSubStatusResetInd==1)){
				return true;
			}
			else{
				/*When appt date rane is selected and a proper dates are entered, submit filter form*/
				if(range === 'range' && fromDate !=="" && toDate !==""){
					if(validateFilterDate()){
						return true;
					}
				 }
			}
			return false;
	}
	
	function reLoadAfterAssignProvider(){
		var formData = $('#filterForm').serializeArray();
		fnWaitForResponseShow();
		$('#orderManagementFiltersAndGrid').load('loadTabData.action', formData, function(){
			fnWaitForResponseClose();
		});
	}
	
	function loadAfterAssignProvider(){
		var formData = $('#filterForm').serializeArray();
		var count = $(".currentOrdersCheckbox[type='checkbox']:checked").length;
		var valid = validateFilterSelection();
		$("#applyFilterInd").val("0");
		if(!valid){
			return false;
		}
		fnWaitForResponseShow();
		var sortOrder  = $("#sortOrderFrm").val();
		var targetTitleId = "hdr"+$("#criteriaFrm").val();
		$('#orderManagementFiltersAndGrid').load('loadTabData.action', formData, function(){
			$(".icon-sort-up").addClass("icon-sort");
			$(".icon-sort-down").addClass("icon-sort");
			$(".icon-sort").removeClass("icon-sort-up").removeClass("icon-sort-down");
			if(targetTitleId != 'undefined' || targetTitleId !=="" || targetTitleId !== "hdr"){
				if(sortOrder == 'asc'){
					$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-up");
					$("#"+targetTitleId+" .icon-sort-up").removeClass("icon-sort");
					$("#"+targetTitleId).prop("sortOrder", "desc");
				}else if(sortOrder == 'desc'){
					$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-down");
					$("#"+targetTitleId+" .icon-sort-down").removeClass("icon-sort");
					$("#"+targetTitleId).prop("sortOrder", "asc");
				}	
				$("table.omscrollerTableHdr .active").removeClass("active");
				$("#"+targetTitleId).parent().addClass("active");
				var criteria  = targetTitleId.substring(3);
				if(criteria == 'CustInfo'){
					$(".lastNameBold").addClass("boldClass");
				}
			}
			fnWaitForResponseClose();			
		});
		
	}
	
	function fnApplyFilter(){
		var formData = $('#filterForm').serializeArray();
		$("#applyFilterInd").val("0");
		fnWaitForResponseShow();
		fnWaitForResponseShow();
		var sortOrder  = $("#sortOrderFrm").val();
		var targetTitleId = "hdr"+$("#criteriaFrm").val();
		$('#orderManagementFiltersAndGrid').load('loadTabData.action', formData, function(){
			$(".icon-sort-up").addClass("icon-sort");
			$(".icon-sort-down").addClass("icon-sort");
			$(".icon-sort").removeClass("icon-sort-up").removeClass("icon-sort-down");
			if(targetTitleId != 'undefined' || targetTitleId !=="" || targetTitleId !== "hdr"){
				if(sortOrder == 'asc'){
					$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-up");
					$("#"+targetTitleId+" .icon-sort-up").removeClass("icon-sort");
					$("#"+targetTitleId).prop("sortOrder", "desc");
				}else if(sortOrder == 'desc'){
					$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-down");
					$("#"+targetTitleId+" .icon-sort-down").removeClass("icon-sort");
					$("#"+targetTitleId).prop("sortOrder", "asc");
				}	
				$("table.omscrollerTableHdr .active").removeClass("active");
				$("#"+targetTitleId).parent().addClass("active");
			}
			fnWaitForResponseClose();
			var criteria  = targetTitleId.substring(3);
			if(criteria == 'CustInfo'){
				$(".lastNameBold").addClass("boldClass");
			}
		});
	}
	
	//precall save
	$(document).on('click', "#savePrecallBtn", function(){
		$("#confirmOrderErrDiv").hide();
		$("#confirmServiceErrDiv").hide();
		$("#rescheduleErrorMsg").html("");
		var warningMsg = "";
		$("#customerNotAvailableErrorMsg").html("");
		var customerNotAvailableWarningMsg = "";
		var assignedResourceId = $("#assignProviderDropdown").prop("value");
		if(assignedResourceId != "-1"){
			$("#assignResourceIdhidden").prop("value",assignedResourceId);
		}
		var custNotAvailableChecked;

		if($("#custavailablechkbox").is(':checked')) {
			custNotAvailableChecked =1;
		}
		else{
			custNotAvailableChecked =0;
		}
		$("#customerAvailableOrNothidden").prop("value",custNotAvailableChecked);
		var custNotAvailableReason = $("#custNotAvailableReasonDropdown").prop("value");
		if(custNotAvailableReason != "-1"){
			$("#customerNotAvailableReasonIdhidden").prop("value",custNotAvailableReason);
		}
		var eta = $("#etaDropdown").prop("value");
		if(eta != "-1"){
			$("#etahidden").prop("value",eta);
		}
		var todayInd = $("#today").prop("value");
		var resourceAssigned = $("#resourceAssigned").prop("value");
		var assignedProviderDropdownVal = $("#assignProviderDropdown").prop("value");
		if(custNotAvailableChecked == '1'){
			if(custNotAvailableReason == '-1'){
				var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.reason" />';
				customerNotAvailableWarningMsg += "<li>"+msg+"</li>";
				$("#assignWarningAppointmentToday div#informationWindowHdr").css("display","none");
				$("#etaWarningAppointmentToday div#informationWindowHdr").css("display","none");
				//$("#reminderAppointmentToday").hide();
				$("#warningAppointmentToday").hide();
				$("#assignWarningAppointmentToday").hide();
				$("#etaWarningAppointmentToday").hide();
			}
			if(todayInd=='true'){
				if( resourceAssigned !='true'&&  assignedProviderDropdownVal == '-1'){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.assign" />';
					customerNotAvailableWarningMsg += "<li>"+msg+"</li>";
					/* if(eta == -1){
						var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.eta" />';
						//customerNotAvailableWarningMsg += "<li>"+msg+"</li>";
						$("#warningAppointmentToday").show();
						$("#assignWarningAppointmentToday").hide();
						$("#etaWarningAppointmentToday").hide();
						$("#reminderAppointmentToday").hide();
					}else{ */
						$("#assignWarningAppointmentToday").show();
						$("#warningAppointmentToday").hide();
						$("#etaWarningAppointmentToday").hide();
						$("#reminderAppointmentToday").hide();
/* 					}
 */				}/* else if(eta == -1){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.eta" />';
					customerNotAvailableWarningMsg += "<li>"+msg+"</li>";
					$("#etaWarningAppointmentToday").show();
					$("#assignWarningAppointmentToday").hide();
					$("#warningAppointmentToday").hide();
					$("#reminderAppointmentToday").hide();
				} */
			}
			$("#timeWindowhidden").prop("value",0);
			
		}else{
			var serviceLocationNotesFlag  = $("#serviceLocationNotesEditFlaghidden").prop("value");
			var specialInstructionsFlag = $("#specialInstructionsEditFlaghidden").prop("value");
			var specialInstructions = $("#specialIntructions").prop("value");
			specialInstructions = jQuery.trim(specialInstructions);
			var serviceLocationNotes = $("#seviceLocationNotes").prop("value");
			serviceLocationNotes = jQuery.trim(serviceLocationNotes);
	
			if(serviceLocationNotesFlag == 'true'){
				if(serviceLocationNotes == "" || serviceLocationNotes == " "){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.sl.notes" />';
					warningMsg += "<li>"+msg+"</li>";
				}
				$("#serviceLocationNoteshidden").prop("value",serviceLocationNotes);
			}
			if(specialInstructionsFlag == 'true'){
				if(specialInstructions == "" || specialInstructions == " "){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.special.instructions" />';
					warningMsg += "<li>"+msg+"</li>";			}
				
				$("#specialInstructionshidden").prop("value",specialInstructions);
			}
			var timeWindowDropdownVal = $("#timeWindowDropdown").prop("value");

			if(timeWindowDropdownVal != "-1"){
				$("#timeWindowhidden").prop("value",timeWindowDropdownVal);
				
			}
			if(timeWindowDropdownVal == '2'){
				var scheduleFromTime = $("#scheduleFromTime").prop("value");
				var scheduleToTime = $("#scheduleToTime").prop("value"); 
			/* 	if(scheduleFromTime == '-1' || scheduleToTime == '-1' ){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.today.schedule.time" />';
					warningMsg += "<li>"+msg+"</li>";	
				} */
				var scheduleType = $("#scheduleTypeForPreCall").prop("value");
				if(null == scheduleFromTime || undefined == scheduleFromTime || '0' == scheduleFromTime || '-1' == scheduleFromTime){
					var  msg= 'Please select a start time';
					warningMsg += "<li>"+msg+"</li>";	
				}
				if(scheduleType == 'range'){
					if(null == scheduleToTime || undefined == scheduleToTime || '0' == scheduleToTime || '-1' == scheduleToTime){
						var  msg ='Please select an end time';
						warningMsg += "<li>"+msg+"</li>";	
					}
				}
				if(scheduleFromTime != "-1"){
					$("#fromTimehidden").prop("value",scheduleFromTime);
				}
				if(scheduleToTime != "-1"){
					$("#toTimehidden").prop("value",scheduleToTime);
				}
			}
			else if(timeWindowDropdownVal == '3'){
				var rescheduleRadio = $("input[type='radio'][name='precallRangeOfDates']:checked");
				$("#specificOrRangeValhidden").prop("value",rescheduleRadio.val());
	
				if(rescheduleRadio.val() == '1'){
					var rescheduleSpecificDateTxt = $("#precallSpecificRescheduleFromDate").prop("value");
					var rescheduleSpecificTimeDropdown = $("#precallSpecificTimeDropdown").prop("value");
					if(!isDate(rescheduleSpecificDateTxt)){
						var  msg= 'Please provide a valid Date in the format MM/DD/YYYY.';
						warningMsg += "<li>"+msg+"</li>";
					}
					if(rescheduleSpecificTimeDropdown == "-1"){
						var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.rescehdule.time" />';
						warningMsg += "<li>"+msg+"</li>";
					}
					$("#rescheduleFromDatehidden").prop("value",rescheduleSpecificDateTxt);
					$("#rescheduleFromTimehidden").prop("value",rescheduleSpecificTimeDropdown);
					$("#rangeOfDatesStr").prop("value",true);
				}else if(rescheduleRadio.val() == '2'){
					var rescheduleFromDateRangeTxt =  $("#precallRangeRescheduleFromDate").prop("value");
					var rescheduleToDateRangeTxt   =   $("#precallRangeRescheduleToDate").prop("value");
					var rescheduleRangeDateStartTimeDropdown = $("#rescheduleRangeDateStartTimeDropdown").prop("value");
					var rescheduleRangeDateEndTimeDropdown   = $("#rescheduleRangeDateEndTimeDropdown").prop("value");
					if(!isDate(rescheduleFromDateRangeTxt)){
						var  msg = 'Please provide a valid start Date in the format MM/DD/YYYY.';
						warningMsg += "<li>"+msg+"</li>";
					}
					else if(!isDate(rescheduleToDateRangeTxt)){
						var  msg= 'Please provide a valid end Date in the format MM/DD/YYYY.';
						warningMsg += "<li>"+msg+"</li>";
					}
					if(rescheduleRangeDateStartTimeDropdown == "-1" || rescheduleRangeDateEndTimeDropdown == "-1"){
						var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.rescehdule.time" />';
						warningMsg += "<li>"+msg+"</li>";
					}
					$("#rescheduleFromDatehidden").prop("value",rescheduleFromDateRangeTxt);
					$("#rescheduleToDatehidden").prop("value",rescheduleToDateRangeTxt);
					if(rescheduleRangeDateStartTimeDropdown != "-1"){
						$("#rescheduleFromTimehidden").prop("value",rescheduleRangeDateStartTimeDropdown);
					}
					if(rescheduleRangeDateEndTimeDropdown != "-1"){
						$("#rescheduleToTimehidden").prop("value",rescheduleRangeDateEndTimeDropdown);
					}
					$("#rangeOfDatesStr").prop("value",false);
				}
				var reasonForRescheduleDropdown =  $("#reasonForRescheduleDropdown").prop("value");
				var rescheduleCommentsTextarea =  $("#rescheduleCommentsTextarea").prop("value");
				rescheduleCommentsTextarea = jQuery.trim(rescheduleCommentsTextarea);
	
				if(reasonForRescheduleDropdown == "-1"){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.rescehdule.reason" />';
					warningMsg += "<li>"+msg+"</li>";
				} 
				if(rescheduleCommentsTextarea == ""){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.rescehdule.comments" />';
					warningMsg += "<li>"+msg+"</li>";
				}
				if(reasonForRescheduleDropdown != "-1"){ 
					$("#rescheduleReasonCodehidden").prop("value",reasonForRescheduleDropdown);
				}
				$("#rescheduleCommenthidden").prop("value",rescheduleCommentsTextarea);
			}
			var todayInd = $("#today").prop("value");
			var resourceAssigned = $("#resourceAssigned").prop("value");
			var assignedProviderDropdownVal = $("#assignProviderDropdown").prop("value");
			if(todayInd=='true'){
				if(timeWindowDropdownVal == -1){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.eta" />';
					warningMsg += "<li>"+msg+"</li>";	
				}
				else if( resourceAssigned !='true'&&  assignedProviderDropdownVal == '-1'){
					var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.precall.assign" />';
					warningMsg += "<li>"+msg+"</li>";
				}
			}
		}
		if(customerNotAvailableWarningMsg  != ""){
			customerNotAvailableWarningMsg = "<ul>"+customerNotAvailableWarningMsg+"</ul>";
			$("#customerNotAvailableErrorMsg").html("");
			$("#customerNotAvailableErrorMsg").html(customerNotAvailableWarningMsg);
			$("#customerNotAvailableErrorMsg").show();

		}else if(warningMsg  != ""){
			warningMsg = "<ul>"+warningMsg+"</ul>";
			$(".errorBox").html("");
			$(".errorBox").hide();
			setErrorMessageForPreCall(warningMsg);
			$(".modal-content").scrollTop(0);
			//$("#preCallErrDiv").html("");
			//$("#preCallErrDiv").html(warningMsg);
			
		}else{
			
			var formData = jQuery('#updatePrecallForm').serialize();
				$.ajax({
		        	url: 'savePrecallDetails.action',
		        	type: "POST",
		        	data: formData,
					dataType : "json",
					success: function(data) {
						//$("#precallErrorMsg").html("");
						$(".errorBox").html("");
						$(".errorBox").hide();
						if(data.omErrors.length !== 0 ){
							var errorMessage = "<ul>";
							$.each(data.omErrors, function(index,value){
								errorMessage += "<li>"+value.msg+"</li>";
							});	
							//$("#preCallErrDiv").html("</ul>");
							//$("#preCallErrDiv").show();
							errorMessage += "</ul>";
							setErrorMessageForPreCall(errorMessage);
							//TODO remove this once error message is moved to proper divisions.
							$(".modal-content").scrollTop(0);//Scroll to the error div
						}else{
							$("#preCallModal").jqmHide();
							$('.icon-repeat').trigger('click');
							$("#omSuccessDiv").html("<div  class='successBox'><p>"+data.result+"</p></div>");
							$("#omSuccessDiv").show();
						}	
					},
					error: function(xhr, status, err) {
						location.href ="${contextPath}/homepage.action";
			        }
			 	}); 
		}	
		
	});
	
	
	function getOpenDivInPreCall(){
		if (!$('#confirmOrderDiv').is(':hidden')) {
			 return "confirmOrderDivError";
		}else if(!$('#confirmServiceDiv').is(':hidden')){
			return "confirmServiceDivError";
		}else if(!$('#confirmScheduleDiv').is(':hidden')){
			return "confirmScheduleDivError";
		}else{
			return "preCallErrDiv";
		}
	}

		
	function setErrorMessageForPreCall(message){
		$(".errorBox").html("");
		$(".errorBox").hide();
		var errorDiv = getOpenDivInPreCall();
		$("#"+errorDiv).html(message);
		$("#"+errorDiv).show();
	}
	/**
	 *Validates date range
	 */
	function validateDateRange(fromDate,toDate){
		var dateMsg ="";
		//var curDate = new Date();
		var fromDt = txtToDate(fromDate);
		if(fromDt == null)
			return 'Please provide a valid Date Range in the format MM/DD/YYYY.';
		var toDt = txtToDate(toDate);
		if(toDt == null)
			return 'Please provide a valid Date Range in the format MM/DD/YYYY.';
		if(fromDt>toDt){
			dateMsg += 'Please provide a valid Date Range. Start Date should not be greater than End Date.';	
			$("#selectApptDateOptionsErrorMessage").css("top","85px");
		}
		/*else if(fromDt>curDate || toDt > curDate){
			dateMsg += '<p class="errorMsg">Please provide a valid Date Range. The Date Range should not extend beyond today\'s date.</br></p>';
		}*/
		return dateMsg;
	}

	/**
	 *Function to convert date in string to date object.
	 *Returns null when invalid format encountered.
	 */
	function txtToDate(txtDate){
		var rxDatePattern = /^(\d{1,2})(\/)(\d{1,2})(\/)(\d{4})$/; 
		var dtArray = txtDate.match(rxDatePattern); 
		if (dtArray == null){
			return null;
		} 
		var date = new Date(dtArray[5], dtArray[1]-1, dtArray[3]);
		return date;
	} 

	
	function validateRescheduleDate(byRange){
		var errMessage ="";
		var rescheduleFromDate = $("#rescheduleFromDate").val();
		var rescheduleToDate = $("#rescheduleToDate").val();
		if(!isDate(rescheduleFromDate)){
			return 'Please provide a valid start Date in the format MM/DD/YYYY.';
		}
		if(byRange ==1){
			if(!isDate(rescheduleToDate)){
				return 'Please provide a valid end Date in the format MM/DD/YYYY.';
			}
			errMessage = validateDateRange(rescheduleFromDate,rescheduleToDate);
		}
		return errMessage;
	}
	
	
	function displayChildOrders(flag, parent_id, soId, rowNum){
		if ($("#childs_"+rowNum).is(':hidden')) {
			$("#childs_"+rowNum).show();
			$("#"+rowNum+"_plus_icon").removeClass("icon-plus-sign").addClass("icon-minus-sign");
			$("#"+rowNum+"_close_icon").removeClass("icon-folder-close").addClass("icon-folder-open");
		}else{
			$("#childs_"+rowNum).hide();
			$("#"+rowNum+"_plus_icon").removeClass("icon-minus-sign").addClass("icon-plus-sign");
			$("#"+rowNum+"_close_icon").removeClass("icon-folder-open").addClass("icon-folder-close");
		}
		
	}
	
	function displayBidChildOrders(flag, parent_id, soId, rowNum){
		if ($("#bidChilds_"+rowNum).is(':hidden')) {
			$("#bidChilds_"+rowNum).show();
			$("#"+rowNum+"_plus_icon").removeClass("icon-plus-sign").addClass("icon-minus-sign");
			$("#"+rowNum+"_close_icon").removeClass("icon-folder-close").addClass("icon-folder-open");
		}else{
			$("#bidChilds_"+rowNum).hide();
			$("#"+rowNum+"_plus_icon").removeClass("icon-minus-sign").addClass("icon-plus-sign");
			$("#"+rowNum+"_close_icon").removeClass("icon-folder-open").addClass("icon-folder-close");
		}
	}
	
	
	
</script>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/modalVideo.css" />
	
<script type="text/javascript" src="${staticContextPath}/javascript/CalendarControl.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/animatedcollapse.js"></script>

<style>
body.acquity #mainNav{width:80%;}
.waitLayer{
			display: none;
			z-index: 999999999;
			height: 40px; 
			overflow: auto; 
			position: fixed;
			top: 250px;
			left: 45%;
			border-style:double;
			background-color: #EEEEEE;
			border-color: #BBBBBB;
			width: 125px;
			border-width: 4px;
			-webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			-moz-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);}
.refreshMsgIcon{
			float: right;
			font-size: 15px;
			padding: 3px;
			cursor: pointer;
			line-height: 20px;}
.refreshMsg{
			background-image: url(${staticContextPath}/images/ajax-loader.gif);
			background-position: 20px center;
			background-repeat: no-repeat;
			padding-left: 50px; 
			padding-top: 5px;
			height: 20px;
			}
/* SLT-2235*/
#overlayNew {
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: #FFFFFF;
			filter: alpha(opacity = 50);
			-moz-opacity: 0.7;
			-khtml-opacity: 0.7;
			opacity: 0.7;
			z-index: 299;
			display: block;
		} 
		.newTermsNoticePopUp{
			width: 500px;
			height: 120px;
			background-color: #f5b7b19e;
			color: #333;
			font-size: 11px;
			display: none;
			position:relative;
			padding-top: 20px;
    		padding-left: 28px;
    		padding-right: 27px;
    		margin-top: -350px;
    		margin-left: 250px;
    		box-shadow: 0 2px 5px #000;
    		z-index: 301;
    		text-align: left;
			
		}	
		.headingNewTCPopup {
			padding-bottom: 9px;
		}
		.headerSpanScrollDivTCPopup {
			color: black;
			font-size: 11px;
			margin-left: 60px;
		}
		/* SLT-2235 */	
</style>
</head>
	<body class="tundra acquity simple">
	<!-- <body class="tundra acquity simple" onload="displayBanner();">
	<div id="bannerDiv" class="bannerDiv" style="display:none;margin-left: 0px;width: 100%;">
	     <span class="spanText" id="spanText"></span>
	     <a href="javascript:void(0);" onclick="removeBanner();" style="text-decoration: underline;"> Dismiss </a>
  </div> -->
		<div id="page_margins">
			<div id="page">
				<div id="header">
					<tiles:insertDefinition name="newco.base.orderManagementHeader" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				</div>
				
	<jsp:include page="wait_for_response_popup.jsp" />
	
	<div id="reschedule"></div>
	<div id="responseDiv" style="margin-left: 0px;" class="responseClass">
		<div id="omErrorDiv" class="" style="">
			<c:if test="${not empty omErrorMsg}">
				<div  class="errorBox">
					<p><c:out value="${omErrorMsg}"></c:out></p>
				</div>
				<c:remove var="omErrorMsg" scope="session" />
			</c:if>
		</div>
<c:if test="${omApiErrors!=null && fn:length(omApiErrors) > 0}">
			<br/>
				<div id="omApiErrorDiv" class="" style="">
				<div  class="errorBox">
				<ul>
				<c:forEach var="omApiError" items="${omApiErrors}">
				<c:if test="${omApiError!=null}">
				<li>
					${omApiError.msg}
				</li>
				</c:if>
				</c:forEach>
				</ul>
				</div>
				</div>
			<c:remove var="omApiErrors" scope="session" />
			</c:if>
		<div id="omSuccessDiv" style="">
			<c:choose><c:when test="${not empty omTabDTO.result}">
				 <div  class="successBox">
					<p><c:out value="${omTabDTO.result}"></c:out></p>
				</div>
			</c:when>
			<c:when test="${not empty omSuccessMsg}">
				<div  class="successBox">
					<p><c:out value="${omSuccessMsg}"></c:out></p>
				</div>
				<c:remove var="omSuccessMsg" scope="session" />
			</c:when>
			</c:choose>
		</div>
	</div>
	<div id="orderManagementTabs" style="width: 12%; float: left;margin-top: 35px;">
				<jsp:include page="order_management_tabs.jsp"/>
	</div>
	<div id="orderManagementFiltersAndGrid" style="width: 85%; float: right;">
				<jsp:include page="order_management_filtersandgrid.jsp"/>
	</div>

	<div id="orderManagementBlank" style="width: 85%; float: right;">
				<jsp:include page="order_management_blank.jsp"/>
	</div>
	
		<div id="preCallModal"></div>
	
	<div id="confirmCallDiv" style="width: 20%; float: left; display:none;">
	<jsp:include page="order_management_confirm_call.jsp"/> 
	</div>
		<div  class="jqmWindow modalDefineTe rms" id="videoModal" style="background-color:#000000;width:520px;">
		      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
		      <div class="modalContent" style="padding-left: 20px;">
		      </div>
			</div>
				<%-- <jsp:include page="/jsp/public/common/defaultFooter.jsp" /> --%>
			</div>
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="simple.dashboard"/>
		</jsp:include>
		</div>
		
	<!-- Feed back popup form -->	
	<div id="feedBackForm" class= "jqmWindow" style="display:none;"></div>
	<!-- Feed back thanks -->
	<div id="feedBackThanks" style="display:none;">
		<jsp:include page="/jsp/feedback/feedback_thanks_form.jsp"/>
	</div>
	<!-- SLT-2235:New notice for existing Firms to alert about the new T&C -->
			<div id="newTermsNotice" class="newTermsNoticePopUp">
				<div id="headingNewTC" class="headingNewTCPopup">
					<span class="headerSpanScrollDivTCPopup"><b>Important Notice of a Change in our Terms and Conditions</b></span>
					</br>
				</div>
				<a href='' class='reviewLink' style="margin-left: 20px; font-size: 11px;text-decoration: underline;color: black"><p><b>Click to review the important changes to the ServiceLive Bucks Agreement</br>Clause 2.6-Liens,Garnishments,and Other Notifications</b></p></a>
				</br></br>
				Email Support@servicelive.com if you have questions related to changes.										
			</div>
			<!--  SLT-2235:ends -->		
	</body>
	<script type="text/javascript">
		var _gaq = _gaq || [];
		_gaq.push(['_setAccount', 'UA-2653154-6']);
		_gaq.push(['_setDomainName', 'servicelive.com']);
		_gaq.push(['_setSiteSpeedSampleRate', 100]);  
		_gaq.push(['_trackPageview']);
		(function() { 
		var ga = document.createElement('script'); 
		ga.type = 'text/javascript'; 
		ga.async = true; 
		ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js'; 
		var s = document.getElementsByTagName('script')[0]; 
		s.parentNode.insertBefore(ga, s); })();
	</script>
</html>
