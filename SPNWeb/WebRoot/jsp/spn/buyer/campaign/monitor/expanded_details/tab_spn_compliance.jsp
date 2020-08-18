<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 
<head>
<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/toggle-btn.css"/>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<style>
<!--

-->

.zpicked {
    border: 1px solid #BBBBBB;
    display: block;
    margin: 0.5em 0;
    padding: 1px 0;
}
.zpicked label {
    background: url("${staticContextPath}/images/common/multiselect.gif") no-repeat scroll 163px 2px white;
    cursor: pointer;
    font-weight: normal;
    padding: 2px 5px;
}
.wider .zpicked label {
    background: url("${staticContextPath}/images/common/multiselect.gif") no-repeat scroll 276px 2px white;
}
.zpicked label:hover {
    background: url("${staticContextPath}/images/common/multiselect-hover.gif") no-repeat scroll 163px 2px white;
    cursor: pointer;
}
.wider .zpicked label:hover {
    background: url(".${staticContextPath}/images/common/multiselect-hover.gif") no-repeat scroll 276px 2px white;
    cursor: pointer;
}


.sorting {

background: url('${staticContextPath}/images/sort/sort_gen.gif') no-repeat center right;

}

.sorting_asc {

background: url('${staticContextPath}/images/sort/sort_desc.gif') no-repeat center right;

}

.sorting_desc {

background: url('${staticContextPath}/images/sort/sort_asc.gif') no-repeat center right;

}
table .tabledHdr {
	border-left:  1px solid #ccc;
	border-right: 1px solid #ccc;
		border-top:  1px solid #ccc;
	}
table .rqHdr {
	
	width:25%;
	background-color:#F1EBEB;
}
table .firmHdr {
	
	width: 325px;
	background-color:#F1EBEB;
}
table .marketHdr {
	width:20%;
	background-color:#F1EBEB;
}
table .stateHdr {
	width:12%;
	background-color:#F1EBEB;
}
table .statusHdr {
	width: 20%;
	background-color:#F1EBEB;
}
table td {
    border-bottom: 1px solid #CCCCCC;
    border-right: 1px solid #CCCCCC;
    border-left: 1px solid #CCCCCC;
    padding: 2px 1px 2px 3px;
    text-align: left;
}


table th {
background-color:#F1EBEB
}
.zzselect-Complianceoptions1 {
	-webkit-border-radius:5px;
	-moz-border-radius:5px;
	border-radius:5px;
	background:#33393C;
	color:  #FFF;
	float: right;
	padding: 10px;
	position: absolute;
	width:  auto;
	max-height: 350px;
	_height: 250px;
	overflow: auto;
	display: none;
	z-index:200;
	margin-left:70px;
	margin-top:24px;
	text-align:none;
}
.zselect-Complianceoptions{
	-webkit-border-radius:5px;
	-moz-border-radius:5px;
	border-radius:5px;
	background:#33393C;
	color:  #FFF;
	float: right;
	padding: 10px;
	position: absolute;
	width:  150px;
	max-height: 175px;
	_height: 250px;
	overflow: auto;
	display: block;
	z-index:200;
	text-align:none;
}

/*
.dataTables_wrapper {
	position: relative;
	clear: both;
	zoom: 1; 
}

.dataTables_processing {
	position: absolute;
	top: 50%;
	left: 50%;
	width: 250px;
	height: 30px;
	margin-left: -125px;
	margin-top: -15px;
	padding: 14px 0 2px 0;
	border: 1px solid #ddd;
	text-align: center;
	color: #999;
	font-size: 14px;
	background-color: white;
}

.dataTables_length {
	width: 40%;
	float: left;
}

.dataTables_filter {
	width: 50%;
	float: right;
	text-align: right;
}
*/
.dataTables_info {
	width: 60%;
	float: left;
}

.dataTables_paginate {
	float: right;
	text-align: right;
}

.alignLeft {
 background-color: white !important;
    padding-left: 10px !important;
    text-align: left !important;
}

.alignCenter {
 background-color: white !important;
  padding-left: 0px !important;
    text-align: Center !important;
}


/* Pagination nested */
.paginate_disabled_previous, .paginate_enabled_previous,
.paginate_disabled_next, .paginate_enabled_next {
	height: 19px;
	float: left;
	cursor: pointer;
	*cursor: hand;
	color: #111 !important;
}
.paginate_disabled_previous:hover, .paginate_enabled_previous:hover,
.paginate_disabled_next:hover, .paginate_enabled_next:hover {
	text-decoration: none !important;
}
.paginate_disabled_previous:active, .paginate_enabled_previous:active,
.paginate_disabled_next:active, .paginate_enabled_next:active {
	outline: none;
}

.paginate_disabled_previous,
.paginate_disabled_next {
	color: #666 !important;
}
.paginate_disabled_previous, .paginate_enabled_previous {
	padding-left: 23px;
}
.paginate_disabled_next, .paginate_enabled_next {
	padding-right: 23px;
	margin-left: 10px;
}

.paginate_disabled_previous {
	background: url('../images/back_disabled.png') no-repeat top left;
}

.paginate_enabled_previous {
	background: url('../images/back_enabled.png') no-repeat top left;
}
.paginate_enabled_previous:hover {
	background: url('../images/back_enabled_hover.png') no-repeat top left;
}

.paginate_disabled_next {
	background: url('../images/forward_disabled.png') no-repeat top right;
}

.paginate_enabled_next {
	background: url('../images/forward_enabled.png') no-repeat top right;
}
.paginate_enabled_next:hover {
	background: url('../images/forward_enabled_hover.png') no-repeat top right;
}






/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * DataTables sorting
 */


 


.example_alt_pagination div.dataTables_info {
	width: 40%;
}

.paging_full_numbers {
	width: 400px;
	height: 22px;
	line-height: 22px;
}

.paging_full_numbers a:active {
	outline: none
}

.paging_full_numbers a:hover {
	text-decoration: none;
}

.paging_full_numbers a.paginate_button,
 	.paging_full_numbers a.paginate_active {
	border: 1px solid #CDC7C7;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	padding: 2px 5px;
	margin: 0 0px;
	cursor: pointer;
	*cursor: hand;
	color: #333 !important;
}

.paging_full_numbers a.paginate_button {
	background-color: #FFFFFF;
}

.paging_full_numbers a.paginate_button:hover {
	background-color: #ccc;
	text-decoration: none !important;
}

.paging_full_numbers a.paginate_active {
	background-color: #989090;
}
/*for arrow style*/
.arrowAddNoteHist{
    			    border-color: transparent transparent #E7E5E5!important;
 					border: 10px solid; 
				    height: 0;
				    width:0;
				    left: 80%;
				    margin-top: -1%;
				    margin-left:-7.5%;
				    z-index: 99999; 
				    display: none;	
				    position: absolute;			     
		}


</style>


	
	<script type="text/javascript">
		$(document).ready(function() {
		
			var networkid=${spnId};
			 var complianceType='${complianceType}';
			 var filterForm = $("#advanceFilterForm" + networkid).serialize();
		
			 var count='${count}';

			// for datatable server side
			if(count>0){
				$('#spn-monitor-temp'+networkid).dataTable({			
			      "sPaginationType": "full_numbers",
			      "sDom":"Rfrtlip",
			      "bServerSide": true,
				  "sAjaxSource": "spn/spnMonitorNetwork_viewComplianceTabAjax3.action?networkid="+networkid+"&complianceType="+complianceType+"&date='+new Date().getTime()&"+filterForm+"",
			      "aLengthMenu": [10,50,100,500,1000],
			      "aaSorting": [[ 1, "desc" ]],
			      "oLanguage": {
				         "oPaginate": {
				            "sLast": ">>",
				            "sFirst": "<<",
				            "sNext": ">",
				            "sPrevious": "<"
				          },
				          "sLengthMenu":" _MENU_ ",
				          "sInfo":" &nbsp;Show&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; of _TOTAL_ results",
				          "sInfoEmpty":"&nbsp;&nbsp;&nbsp;",
				          "sEmptyTable":"<div style='background-color:#F2F2F2;width:100%;padding-top:5px;height:20px;display:block;text-align:center;'><b>No records matched your filter.</b> Try expanding your search criteria <a id='linkIcon${spnId}'><u>by defining a new filter.</u></a></div>"
				         },
				  "aoColumns":[
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"},
				  {"sClass":"alignCenter"}
				  ],
				  "bAutoWidth":false,
				  "fnDrawCallback":function(){
					  $('#spn-monitor-temp'+networkid+'_info').show();
						$('#spn-monitor-temp'+networkid+'_paginate').show();
						$('#lastUpdated'+networkid).show();
						$('#spn-monitor-temp'+networkid).show();
						$('#not_found'+networkid).hide();

					  if($('#spn-monitor-temp'+networkid).find("tr:not(.ui-widget-header)").length<11 ){
		
				
			var a=	$('#spn-monitor-temp'+networkid).find("tr:not(.ui-widget-header)")
				
						if($('#spn-monitor-temp'+networkid).find("tr:not(.ui-widget-header)").length>1)
							{
						$('#spn-monitor-temp'+networkid+'_length').hide();
						var results=$('#spn-monitor-temp'+networkid+'_info').html();
					
						results=results.replace('&nbsp;Show','');
						results=results.replace('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ','');
						results=results.replace('results','');
						results=results.replace('of','');

					
						
						var res="&nbsp;Showing"+results+"of"+results+"results";
						$('#spn-monitor-temp'+networkid+'_info').html(res); 
						var trimedresult=$('#spn-monitor-temp'+networkid+'_info').html();
					
						if(trimedresult=='&nbsp;Showing&nbsp;&nbsp;&nbsp;of&nbsp;&nbsp;&nbsp;results')
						{
							$('#spn-monitor-temp'+networkid+'_info').hide();
							$('#spn-monitor-temp'+networkid+'_paginate').hide();
							$('#lastUpdated'+networkid).hide();
							$('#spn-monitor-temp'+networkid).hide();
							$('#not_found'+networkid).show();
						}
						
							}
						else
							{
							$('#spn-monitor-temp'+networkid+'_length').hide();
							$('#spn-monitor-temp'+networkid+'_info').html("");
							$('#lastUpdated'+networkid).hide();
							$('#spn-monitor-temp'+networkid).hide();
							$('#not_found'+networkid).show();

							}
					  }
					  else
						  {
							$('#spn-monitor-temp'+networkid+'_length').show();
 
						  }
				  } 
		    });
				}
			else{
			$('#spn-monitor-temp'+networkid).dataTable({			
		      "sPaginationType": "full_numbers",
		      "sDom":"Rfrtlip",
		      "bScrollInfinite":true,
		      "oLanguage": {
		      		 "sInfoEmpty":"No records to show"
		      }
	    	});
			$('#spn-monitor-temp'+networkid+'_info').hide();
			$('.dataTables_empty').hide();
			$('#lastUpdated'+networkid).hide();
			}

				

			
		
			
			$('.zselect-Complianceoptions').hide();
			

			

			$('.zselect-Complianceoptions').unbind('click').click(function(event){

			event.stopPropagation();

			});
			
			$('.zpickedClick').unbind('click').click(function(event) {

				event.stopPropagation();

				openMultiSelect(this);

				});
			
		$('#spn-monitor-temp'+networkid+'_filter').css("height","55px");
	 	$('#spn-monitor-temp'+networkid+'_filter').css("background-color","#01A9DB");
		
		$('#spn-monitor-temp'+networkid+'_length').css("position","absolute");
		$('#spn-monitor-temp'+networkid+'_length').css("margin-left","55px");
		$('#spn-monitor-temp'+networkid+'_length').css("margin-top","-5px");
		
		
		
		
	//	jQuery('#spn-monitor-temp'+networkid+'_info').css({'background-color': '#bbb', 'height': '35px','width':'100%','text-align':'left'});
	//	jQuery('#spn-monitor-temp'+networkid+'_paginate').css({'background-color': '#bbb', 'height': '20px','margin-top':'-30px','width':'300px'});
	 	
		
		  jQuery('#spn-monitor-temp'+networkid+'_info').css({'background-color': '#F1EBEB', 'height': '35px','width':'100%','color':'grey','border':'1px solid #ccc','text-align':'left'});
			jQuery('#spn-monitor-temp'+networkid+'_paginate').css({'background-color': '#F1EBEB', 'height': '30px','width':'350px','border-bottom':'1px solid #ccc','position':'relative','margin-left':'460px', 'margin-top': '-31px','padding-right':'5px'});
	        
		
		//$('#spn-monitor-temp_filter').css("color","#01A9DB");
		/*$('#spn-monitor-temp_filter').html("<span style='margin-left:-150px; color:#FFFFFF'>Requirements:</span>"+
			"sss<i class='icon-search'></i> <label>Search:<input type='text' aria-controls='spn-monitor-temp'></label>");*/
			
			
	 	label = jQuery('#spn-monitor-temp'+networkid+'_filter').children("label"); 
        label.css('color','#01A9DB'); 
        //label.html(label.find('input')); 
       // jQuery('#coverageFirmTable_info').hide(); 
       // jQuery('#coverageFirmTable_paginate').hide(); 
       // label.children("input").val("Search name, firm or title "); 
        label.children("input").css({'width':'25%','padding':'3px','margin-left':'520px','margin-top':'13px','border-radius': '25px','-webkit-border-radius': '25px',
        '-moz-border-radius': '25px','border':'none','padding-left':'30px'});

        label.children("input"). 
                before("<label style='color:white;margin-left:-50px;position:absolute;margin-top:10px;font-weight:normal'>Requirements:</label>"+
                		"<span id='companyChecked${spnId}' style='margin-left:70px;margin-top:8px;position:absolute' >"+
              			 "<img  src='/ServiceLiveWebUtil/images/common/company_checked.PNG' style='cursor: pointer;'></img>"+
                			"<img id='provider_unchecked${spnId}' src='/ServiceLiveWebUtil/images/common/prov_unchecked.PNG' style='cursor: pointer;position:absolute'></img>"+
                			"</span>"+
                			"<span id='providerChecked${spnId}' style='margin-left:70px;margin-top:8px;position:absolute;display:none'>"+
                			 "<img id='company_unchecked${spnId}' src='/ServiceLiveWebUtil/images/common/company_unchecked.PNG' style='cursor: pointer;position:absolute;margin-top:1px;'></img>"+
                			"<img  src='/ServiceLiveWebUtil/images/common/provider_checked.PNG' style='cursor: pointer;position:absolute;margin-top:-1px;margin-left:70px;'></img>"+
                			 "</span>"+
                							
                		"<i id='searchIcon${spnId}'style='position: absolute; margin-left: 530px; margin-top: 15px;background-color:white;color:grey;font-size:15px;width:20px;' class='icon-search'></i>");
        
        label.children("input"). 
      after("");
        
        
        $('#popUp'+networkid).hide();
    	
    	$('#company_unchecked'+networkid).click(function(){
    		var networkid=${spnId};
            var complianceType='firmCompliance';
            $('#spnCompliance-'+networkid).html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="620px">');

            $('#spnCompliance-'+networkid).load('spnMonitorNetwork_viewComplianceAjax.action?networkid=' + networkid+'&date='+ new Date().getTime()+'&complianceType='+ complianceType, function(data) {
            	
            	  $('#spnCompliance-'+networkid).show();
            	  $('#companyChecked'+networkid).show();
            	  $('#providerChecked'+networkid).hide();
 	    	});
    		
    	});
    	$('#provider_unchecked'+networkid).click(function(){
    		var networkid=${spnId};
            var complianceType='providerCompliance';
            $('#spnCompliance-'+networkid).html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="620px">');

            $('#spnCompliance-'+networkid).load('spnMonitorNetwork_viewComplianceAjax.action?networkid=' + networkid+'&date='+ new Date().getTime()+'&complianceType='+ complianceType, function(data) {
            	
            	  $('#spnCompliance-'+networkid).show();
            	  $('#providerChecked'+networkid).show();
            	  $('#companyChecked'+networkid).hide();
 	    	});
    	});
    	
    	
    	
    	$('#companyCompliance'+networkid).click(function(){
    		var networkid=${spnId};
            var complianceType='firmCompliance';
            $('#spnCompliance-'+networkid).html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="620px">');

            $('#spnCompliance-'+networkid).load('spnMonitorNetwork_viewComplianceAjax.action?networkid=' + networkid+'&date='+ new Date().getTime()+'&complianceType='+ complianceType, function(data) {
            	
            	  $('#spnCompliance-'+networkid).show();
 	    	});
    		
    	});
    	$('#providerCompliance'+networkid).click(function(){
    		var networkid=${spnId};
            var complianceType='providerCompliance';
            $('#spnCompliance-'+networkid).html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="620px">');

            $('#spnCompliance-'+networkid).load('spnMonitorNetwork_viewComplianceAjax.action?networkid=' + networkid+'&date='+ new Date().getTime()+'&complianceType='+ complianceType, function(data) {
            	
            	  $('#spnCompliance-'+networkid).show();
 	    	});
    	});
    	
    	
    	
    	
    	// to close the pop up on outside click of the advanced filter
   	 jQuery(document).click(function(e){ 
    		var click=$(e.target);
    	
    	
    		if(click.parents().is("#popUp"+networkid)){
    			e.stopPropagation();
    		}else if(click.parents().is("#linkIcon"+networkid)){
    			$('#popUp'+networkid).focus();
    			
    			$('#popUp'+networkid).show();
    			e.stopPropagation();
    		}else if(click.parents().is("#clickIcon"+networkid)){
    		
    			$('#popUp'+networkid).focus();
    			$('.zselect-Complianceoptions').hide();
    			$('#popUp'+networkid).show();

    			e.stopPropagation();
    		} else
    			{
    			$('.zselect-Complianceoptions').hide();
    			$('#popUp'+networkid).hide();
    		}
    		
    	});
   	
    	$('#removeIcon'+networkid).click(function(){
    	    			$('.zselect-Complianceoptions').hide();
    	
    		$('#popUp'+networkid).hide();
    	});
    	
    	
    	
    	var reqCount = $(".requirementCheckbox"+networkid+"[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		
		if (reqCount > 0) {
			$('#allRequirement'+networkid).html(reqCount + " selected");
			
		}
		else{
			$('#allRequirement'+networkid).html("All requirements");
			}
		
		var statuscount = $(".statusCheckbox"+networkid+"[type='checkbox']:checked").length;
		
		if (statuscount > 0) {
			$('#allStatus'+networkid).html(statuscount + " selected");
			
		}
		else{
			$('#allStatus'+networkid).html("All statuses");
			}
		
		// for checkboxes markets and states
		
		
		var marketcount = $(".marketCheckbox" + networkid
				+ "[type='checkbox']:checked").length;
		
		if (marketcount > 0) {
			$('#allMarkets' + networkid).html(marketcount + " selected");

		} else {
			$('#allMarkets' + networkid).html("All markets");
		}
	



		var stateCount = $(".stateCheckbox" + networkid
				+ "[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (stateCount > 0) {
			$('#allStates' + networkid).html(stateCount + " selected");

		} else {
			$('#allStates' + networkid).html("All states");
		}
		// end
		
		
		jQuery('#spn-monitor-temp'+networkid).css("text-align","left");
		
		//alert(jQuery('#spn-monitor-temp'+networkid+'_info').offset());
		

		
		
		
		
		
		
			$('#spacing'+networkid).show();
			$('#firmComplianceStatusCheckbox'+networkid).css("padding-left","5px");
			$('#providerComplianceStatusCheck'+networkid).css("padding-left","10px");
			$('#firmComplianceStatusCheckbox'+networkid).css("margin-top","0px");

		if(complianceType=='providerCompliance'){
			
			 
			 jQuery('#companyChecked'+networkid).hide();
			 jQuery('#providerChecked'+networkid).show();

		 }
		 else
			 {
			 jQuery('#providerChecked'+networkid).hide();
			
			 jQuery('#companyChecked'+networkid).show();
			
			 }
		
		});
		
		
		
		function openProviderProfile(resourceid, companyid)
		{	
			if (document.openProvURL != null)
			{
				document.openProvURL.close();
			}
			var project = '/MarketFrontend/'; // TODO get this as a parameter
			var url = project + "providerProfileInfoAction_execute.action?resourceId="+resourceid+ "&companyId="+ companyid+"&popup=true";
			newwindow=window.open(url,'_publicproviderprofile','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
			if (window.focus) {newwindow.focus()}
			document.openProvURL = newwindow;
		}


		function openProviderFirmProfile(vendorId) 
		{
			
			if (document.openProvURL != null)
			{
				document.openProvURL.close();
			}
			var url = "/MarketFrontend/providerProfileFirmInfoAction_execute.action?vendorId=" + vendorId + "&popup=true";
			newwindow=window.open(url,'_publicproviderprofile','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
			if (window.focus) {newwindow.focus()}
			document.openProvURL = newwindow;
		}
		
		

		function clickButtonChange(id) {
alert('a');
alert($(id).attr('value'));
			var value = id.substring(0, 2);
			alert('value ='+value);		
			id = 'label' + id;
			if (!$('#'+id).hasClass("checked")) {
alert('ok');
				var name1 = $('#' + id).attr('name');
		
			
				$('#' + id).removeClass('checked');
			
				if (value == 'on') {
					id = id.replace('on', 'off');
				} else {
					id = id.replace('off', 'on');
				}
			alert('id ='+id);
				$('#' + id).addClass('checked');

			}
			return false;
		}

		function sortIcon(id) {

			var element = $("#" + id).children("i");

			var value = $("#" + id).attr("aria-label");

			if (element.hasClass("icon-sort")) {

				if (value.indexOf('ascending') != -1) {

					element.removeClass("icon-sort");

					element.addClass("icon-sort-up");

				}

				else if (value.indexOf('descending') != -1) {

					element.removeClass("icon-sort");

					element.addClass("icon-sort-down");

				}

			}

			else if (element.hasClass("icon-sort-up")) {

				element.removeClass("icon-sort-up");

				element.addClass("icon-sort-down");

			}

			else if (element.hasClass("icon-sort-down")) {

				element.removeClass("icon-sort-down");

				element.addClass("icon-sort-up");

			}

		}

		//
		function openMultiSelect(myThis)

		{

			var hidden = false;

			if ($('#' + 'select' + myThis.id + ':hidden').length > 0) {

				hidden = true;

			}

			$('.zselect-Complianceoptions').hide();

			if (hidden == true) {

				$('#' + 'select' + myThis.id).show();

			}

		}

		function submitFilter() {
			var networkid = ${spnId};
			var complianceType = '${complianceType}';
			var filterForm = $("#advanceFilterForm" + networkid).serialize();
			$('#spnCompliance-' + networkid)
					.html(
							'<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="160px"');

			$('#spnCompliance-' + networkid).load(
					'spnMonitorNetwork_viewComplianceAjax.action?networkid='
							+ networkid + '&date=' + new Date().getTime()
							+ '&complianceType=' + complianceType, filterForm,
					function(data) {
						
						//$('#spnCompliance-' + networkid).show();
					});

		}

		function clickRequirement(networkid) {

			var count = $(".requirementCheckbox" + networkid
					+ "[type='checkbox']:checked").length;
			/*if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}*/
			if (count > 0) {
				$('#allRequirement' + networkid).html(count + " selected");

			} else {
				$('#allRequirement' + networkid).html("All requirements");
			}
		}

		function clickStatus(networkid) {

			var count = $(".statusCheckbox" + networkid
					+ "[type='checkbox']:checked").length;
			/*if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}*/
			if (count > 0) {
				$('#allStatus' + networkid).html(count + " selected");

			} else {
				$('#allStatus' + networkid).html("All statuses");
			}
		}

		function clickMarkets(networkid) {

			var count = $(".marketCheckbox" + networkid
					+ "[type='checkbox']:checked").length;
			/*if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}*/
			if (count > 0) {
				$('#allMarkets' + networkid).html(count + " selected");

			} else {
				$('#allMarkets' + networkid).html("All markets");
			}
		}

		function clickStates(networkid) {

			var count = $(".stateCheckbox" + networkid
					+ "[type='checkbox']:checked").length;
			/*if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}*/
			if (count > 0) {
				$('#allStates' + networkid).html(count + " selected");

			} else {
				$('#allStates' + networkid).html("All states");
			}
		}
	</script>
</head>
<body>
<!-- DATATABLE -->
 <!--  <div id="ssssr" style="width: 850px; height:50px; background-color: #01A9DB;"><span style="margin-left:-850px; color:#FFFFFF">Requirements:</span>
sss<i class="icon-search"></i>
</div>-->

<span  id='clickIcon${spnId}'><i  style='position: absolute; margin-left: 379px; font-size: 18px; margin-top: 28px;background-color:white;color:grey;width:20px;cursor: pointer;' class='icon-sort-down'></i></span>
<div style="z-index: 20000; position: absolute; background-color: white; margin-left: 455px; margin-top: 55px; width: 400px;height:275px;border:2px solid #ccc;text-align:center;border-radius: 7px;-webkit-border-radius:7px;" id="popUp${spnId}">	
		<div style="display:block;margin-top:-20px;margin-left:-125px; border-color: transparent transparent #E7E5E5 !important;" class="arrowAddNoteHist" id="arrow_${spnId}"></div>
	
	<form id="advanceFilterForm${spnId}"  >

			<div style="background-color: #F5F1F1;border-bottom:  1px solid #ccc;height:30px;border-radius: 7px 7px 0px 0px;-webkit-border-radius: 7px 7px 0px 0px">
<label id="advancedFilter${spnId}"style="margin-left:-176px;margin-top:5px;position:absolute;"><b>Advanced Filter</b></label>
				<i class="icon-remove" style="margin-left: 175px;font-size:20px;font-weight:normal;position:absolute;cursor: pointer;" id="removeIcon${spnId}"></i></div>
				
		<label id="RequirementLabel${spnId}" style="margin-left: -185px;margin-top:10px;font-weight:normal;position:absolute;">Requirement</label>
																			<label id="ComplianceStatus${spnId}" style="margin-left: 15px;margin-top:10px;font-weight:normal;position:absolute;">Compliance Status</label>
									
							
			<span class="zpicked zpickedClick" id="RequirementOptions${spnId}" style="display: block; margin-left: 96px;width: 0px; position:absolute;margin-top:35px;">
					<label id="allRequirement${spnId}"  style="background-position: right center; width: 162px;position:absolute;margin-left:-80px;margin-top:-3px;border:1px solid #9988FB;text-align:left;padding-left:4px;">
						All requirements
					</label>
			</span>	
			<span class="zpicked zpickedClick" id="ComplianceStatusOptions${spnId}"  style="display: block; width: 135px; margin-left: 295px;width: 0px;position:absolute;margin-top:45px;">
					<label id="allStatus${spnId}" style="background-position: right center; width: 162px;position:absolute;margin-left:-80px;margin-top:-13px;border:1px solid #9988FB;text-align:left;padding-left:4px;">
						All statuses
					</label>
			</span>		
			<div class="zselect-Complianceoptions" id="selectRequirementOptions${spnId}"  
										 onmouseout="" style="margin-left: 18px; margin-top: 59px; width: 155px; " >
											
										<c:if test="${null != requirementList}">
	<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
	<c:forEach var="req" items="${requirementList}" varStatus="i">
																<c:set var="checked" value="" ></c:set>
											<c:set var="reqValue" value="${req.value}" ></c:set>
																
																<c:if test="${null != selectedRequirements}">
<c:if test="${  fn:contains( selectedRequirements, reqValue ) }">
																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>																				
										</c:if>
										<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: top;width: 20%">
														
												<span style="float: left;">		 <input type="checkbox" style="float: left;"  onclick="clickRequirement('${spnId}');"
													name="model.spnComplianceVO.selectedRequirements[${i.count}]" 	value="${req.value}" id="requirement${i.count}"
														class="requirementCheckbox${spnId}" ${checked} /></span></td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 80%;text-align:left;">
														
												<span style="word-wrap:break-word;">${req.descr}</span></td>
													</tr>
													
												
											
										</c:forEach>									
									</table>
										</c:if>
									</div>		
														<c:if test="${complianceType=='firmCompliance'}">
								
								
								
																	<div class="zselect-Complianceoptions" id="selectComplianceStatusOptions${spnId}" 
										 onmouseout="" style="margin-left: 216px; margin-top: 59px;width:155px;">
								<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
							
											<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
																				<c:if test="${  fn:contains( selectedComplianceStatus, 'PF SPN CRED INCOMPLIANCE' ) }">

																			
																					<c:set var="checked" value="checked" ></c:set>
																					
																					</c:if>
																				
																				
																				
																				
																				</c:if>
													<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 20%">
<span style="float: left;"> <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[1]" 	value="PF SPN CRED INCOMPLIANCE" id="statusIncompliance" onclick="clickStatus('${spnId}')"
														class="statusCheckbox${spnId}" ${checked} /> </span></td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 80%;text-align:left;">
														
												<span style="word-wrap:break-word;">In compliance</span></td>
													</tr>
														<br>
														<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
																
									<c:if test="${  fn:contains( selectedComplianceStatus, 'PF SPN CRED OVERRIDE' ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																		
																				</c:if>
													<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 20%">
<span id="firmComplianceStatusCheckbox${spnId}" style="float: left;vertical-align: baseline;margin-top:-10px;position:absolute;"> <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[2]" style="margin-left:-16px;padding-top:0px;"	value="PF SPN CRED OVERRIDE" id="statusBuyerOverride" onclick="clickStatus('${spnId}')"
														class="statusCheckbox${spnId}" ${checked} /> </span></td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 80%;text-align:left;">
														
												<span style="word-wrap:break-word;">In compliance due to buyer override</span></td>
													</tr>


														<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
					<c:if test="${  fn:contains( selectedComplianceStatus, 'PF SPN CRED OUTOFCOMPLIANCE' ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																			
																				</c:if>
<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 20%">
<span style="float: left;"> <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[3]" 	value="PF SPN CRED OUTOFCOMPLIANCE" id="statusOutOfCompliance" onclick="clickStatus('${spnId}')"
														class="statusCheckbox${spnId}" ${checked} /> </span></td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 80%;text-align:left;">
														
												<span style="word-wrap:break-word;">Out of compliance</span></td>
													</tr>

						</table>
									
							</div>
		</c:if>
		
		<c:if test="${complianceType=='providerCompliance'}">
								
								
								
																	<div class="zselect-Complianceoptions" id="selectComplianceStatusOptions${spnId}" 
										 onmouseout="" style="margin-left:220px;margin-top:58px;">
										<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
											<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
																				<c:if test="${  fn:contains( selectedComplianceStatus, 'SP SPN CRED INCOMPLIANCE' ) }">

																			
																					<c:set var="checked" value="checked" ></c:set>
																					
																					</c:if>
																				
																				
																				
																				
																				</c:if>
														<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 20%">
<span style="float: left;"> <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[1]" 	value="SP SPN CRED INCOMPLIANCE" id="statusIncompliance" onclick="clickStatus('${spnId}')"
														class="statusCheckbox${spnId}" ${checked} /> </span></td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 80%;text-align:left;">
														
												<span style="word-wrap:break-word;">In compliance
														</span></td>
													</tr>
														<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
																
									<c:if test="${  fn:contains( selectedComplianceStatus, 'SP SPN CRED OVERRIDE' ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																		
																				</c:if>
														<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 20%">
<span id="providerComplianceStatusCheck${spnId}" style="float: center;padding-left:5px;"> <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[2]" style="margin-left:-16px;"	value="SP SPN CRED OVERRIDE" id="statusBuyerOverride" onclick="clickStatus('${spnId}')"
														class="statusCheckbox${spnId}" ${checked} /> </span></td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 80%;text-align:left;">
														
												<span style="word-wrap:break-word;">In compliance due to buyer override
														</span></td>
													</tr>
														<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
					<c:if test="${  fn:contains( selectedComplianceStatus, 'SP SPN CRED OUTOFCOMPLIANCE' ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																			
																				</c:if>
														<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 20%">
<span style="float: left;"> <input type="checkbox"  
													name="model.spnComplianceVO.selectedComplianceStatus[3]" 	value="SP SPN CRED OUTOFCOMPLIANCE" id="statusOutOfCompliance" onclick="clickStatus('${spnId}')"
														class="statusCheckbox${spnId}" ${checked} /> </span></td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 80%;text-align:left;">
														
												<span style="word-wrap:break-word;">Out of compliance</span></td>
													</tr>
													</table>
									
							</div>
		</c:if>
		
<label id="MarketsLabel${spnId}" style="margin-top: 85px; margin-left: -186px; position: absolute;font-weight:normal">Markets</label>
<label id="StatesLabel${spnId}" style="margin-top: 85px; margin-left: 15px; position: absolute;font-weight:normal">States</label>
		
		
		<span class="zpicked zpickedClick" id="MarketsOptions${spnId}" style="display: block; width: 0px; margin-left: 96px;margin-top: -100px;">
					<label id="allMarkets${spnId}" style="background-position: right center; width: 162px;position:absolute;margin-left:-80px;margin-top:210px;border:1px solid #9988FB;text-align:left;padding-left:4px;">
						All markets
					</label>
			</span>	
			<span class="zpicked zpickedClick" id="StateOptions${spnId}" style="display: block; width: 0px; margin-left: 295px; margin-top: -10px;">
					<label id="allStates${spnId}" style="background-position: right center; width: 295px;width: 162px;position:absolute;margin-left:-80px;margin-top:210px;border:1px solid #9988FB;text-align:left;padding-left:4px;">
						All states
					</label>
			</span>	
									
							
									<div class="zselect-Complianceoptions" id="selectMarketsOptions${spnId}" 
										 onmouseout="" style="margin-top: 226px; margin-left:18px; width: 155px;">
										<c:if test="${null != marketsList}">
										<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
										<c:forEach var="marketlists" items="${marketsList}" varStatus="i">
<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedMarkets}">
																	<c:if test="${  fn:contains( selectedMarkets, marketlists ) }">
																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																			
																				</c:if>											
												<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: top;width: 20%">
													 <input type="checkbox"  style="float: left;"
													name="model.spnComplianceVO.selectedMarkets[${i.count}]" 	value="${marketlists}" id="${i.count}" onclick="clickMarkets('${spnId}')"
														class="marketCheckbox${spnId}" ${checked}/> </td>
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 80%;text-align:left;">
														
												<span style="word-wrap:break-word;">
														 ${marketlists}</span></td>
													</tr>
														

											
											
										</c:forEach>
										</table>
										</c:if>
									</div>
									
							
									<div class="zselect-Complianceoptions" id="selectStateOptions${spnId}" 
										 onmouseout="" style="margin-top: 226px; margin-left: 216px; width: 152px;">
										<c:if test="${null != statesList}">
										<c:forEach var="state" items="${statesList}" varStatus="i">
											<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedStates}">
																<c:if test="${  fn:contains( selectedStates, state ) }">
					
																<c:set var="checked" value="checked" ></c:set>
																					
																					</c:if>
																		
																				</c:if>	
											
													<div style="clear:left;padding-top:5px;">
														<div style="float: left;"> <input type="checkbox" 
													name="model.spnComplianceVO.selectedStates[${i.count}]" 	value="${state}" id="${i.count}" onclick="clickStates('${spnId}')"
														class="stateCheckbox${spnId}" ${checked} /> </div><div style="float: left;padding-left:3px;">${state}
														</div>
													</div>
											
											
										</c:forEach>
										</c:if>
									</div>
									<div style="margin-left: 90px;margin-top:265px;">
												<c:choose>
												<c:when test="${complianceType=='firmCompliance'}">
									
											<a id="companyCompliance${spnId}"style="color:#00A0D2;margin-left:-230px;position:absolute;cursor: pointer"><b> Clear Filter</b></a>	
											</c:when>
											<c:otherwise>
			<a id="providerCompliance${spnId}"style="color:#00A0D2;margin-left:-230px;position:absolute;cursor: pointer"><b> Clear Filter</b></a>	
											
											</c:otherwise>			
												</c:choose>
	<input id="applyButton${spnId}" type="button" onclick="submitFilter();" value="Apply Filter" class="button action"
															style="display: block;text-transform: none;font-size:13px; margin-left:185px;"></input>								
									
									</div>
<div style="height:50px"></div>										
					</form>																			
					</div>	
				
					
		<div style="height: 15px;"></div>							
					

								
									
<table id="spn-monitor-temp${spnId}" border="0" cellpadding="0" cellspacing="0"
	style="width: 100%;text-align:left;display: block">
	<c:choose>
		<c:when test="${count >0}">
	<thead>
		<tr>
		
			<th id="Requirement${spnId}" class="tabledHdr rqHdr" style="cursor: pointer !important;">
				Requirement 
		</th>
			<c:choose>
			<c:when test="${complianceType=='firmCompliance'}">
			<th class="tabledHdr firmHdr" id="Firm${spnId}" style="cursor: pointer !important;">
				Firm  
				</th>
				</c:when>
				<c:otherwise>
							<th class="tabledHdr firmHdr" id="Provider${spnId}" style="cursor: pointer !important;">
					Provider  
					</th>
				</c:otherwise>
				</c:choose>
		
			<th class="tabledHdr marketHdr" id="Market${spnId}" style="cursor: pointer !important;">
				Market  
			</th>
			<th class="tabledHdr stateHdr" id="State${spnId}" style="width: 68px;cursor: pointer !important;" >
				State  
			</th>
			<th class="tabledHdr statusHdr" id="Status${spnId}" style="width:250px;cursor: pointer !important;text-align:left;">
				Requirement Status  
			</th>
		</tr>
	</thead>
	</c:when>
	<c:otherwise>									
	
		
		
		
		<thead>
		<tr>
		
			<th id="Requirement${spnId}" class="tabledHdr rqHdr" style="display:none">
		</th>
			
		
		</tr>
	</thead>
	
	<tbody>
	</tbody>
	</c:otherwise>
	</c:choose>
	</table>
	
		
	 
 	<div id="not_found${spnId}" style="background-color:#F2F2F2;width:100%;padding-top:5px;height:20px;display:block;text-align:center;">
<b>No records matched your filter.</b> Try expanding your search criteria 
<span id="linkIcon${spnId}"><a  style="cursor: pointer !important;" href="javascript:void(0)"><u>by defining a new filter.</u></a></span>

</div>


<!-- DATATABLE -->
<!-- SLIDER -->
	<div id="spacing${spnId}" style="display: none;"><br></br></div>
	<div id="spacingIE${spnId}" style="display: none;"></br></div>
<span id="lastUpdated${spnId}" style="float:right;">Compliance status last updated: <fmt:formatDate value="${updatedDate}" pattern="hh:mm a MM/dd/yy" /></span>

<div style="height:10px"></div>
</body>

</html>
