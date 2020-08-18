<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 
<head>


<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/toggle-btn.css"/>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
 <link href="${staticContextPath}/css/font-awesome.min.css"
	rel="stylesheet" />
<!--	<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>  -->
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
	<!--SL-19387
	To display Background Check details of resources
	 -->

<style>

#spn-auditor-background { table-layout:fixed;-ms-word-break: break-word; word-wrap: break-word;}
#spn-auditor-background td { overflow: hidden; }
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
#spn-auditor-background td {
    border-bottom: 1px solid #CCCCCC;
    border-right: 1px solid #CCCCCC;
    border-left: 1px solid #CCCCCC;
    padding: 2px 1px 2px 3px;
    text-align: left;
}


#spn-auditor-background th {
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
		
			 var filterForm = $("#advanceFilterForm").serialize();
			 var spnId=${spnId};
			 var stateCd='${stateCd}';
			 var status='${status}';
			 var providerFirmName = '${providerFirmName}';
			 var providerFirmNumber = '${providerFirmNumber}';
			 var marketId = '${marketId}';
			 var zipCode = '${zipCode}';
			 var districtId = '${districtId}';
			 
			 var count='${count}';
			 $("#export").hide();
			 $('#not_found').hide();
			// for datatable server side
			if(count>0){
			
				$('#spn-auditor-background').dataTable({			
			      "sPaginationType": "full_numbers",
			      "sDom":"Rfrtlip",
			      "bServerSide": true,
				  "sAjaxSource": "spn/spnAuditorSearchTab_searchBackgroundInformationAjax.action?spnId="+spnId+"&stateCd="+stateCd+"&status="+status+'&providerFirmName='+providerFirmName+'&providerFirmNumber='+providerFirmNumber+'&marketId='+marketId+'&zipCode='+zipCode+'&districtId='+districtId+"&date='+new Date().getTime()&"+filterForm+"",
			      "aLengthMenu": [10,50,100,500,1000],
			      "aaSorting": [[4, "asc" ]],
			      "oLanguage": {
				         "oPaginate": {
				            "sLast": ">>",
				            "sFirst": "<<",
				            "sNext": ">",
				            "sPrevious": "<"
				          },
				          "sLengthMenu":" _MENU_ ",
				          "sInfo":" &nbsp;Show&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; of _TOTAL_ results",
				          "sInfoEmpty":"&nbsp;&nbsp;&nbsp;",
				          "sEmptyTable":"<div style='background-color:#F2F2F2;width:100%;padding-top:5px;height:20px;display:block;text-align:center;'><b>No records matched your filter.</b> Try expanding your search criteria <a id='linkIcon${spnId}'><u>by defining a new filter.</u></a></div>"
				         },
				         "aoColumnDefs" : [ {
				        	    "bSortable" : false,
				        	    "aTargets" : [ 6 ]
				        	} ],      
				  "aoColumns":[
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"},
				  {"sClass":"alignLeft"}
				  ],
				  "bAutoWidth":false,
				  "fnDrawCallback":function(){
					  $('#spn-auditor-background'+'_info').show();
						$('#spn-auditor-background'+'_paginate').show();
						$('#lastUpdated').show();
						$('#spn-auditor-background').show();
						$('#not_found').hide();
						$("#export").show();
						$('.tabledHdr').removeClass('alignLeft');
						
					  if($('#spn-auditor-background').find("tr:not(.ui-widget-header)").length<11 ){
						  
		
				
			var a=	$('#spn-auditor-background').find("tr:not(.ui-widget-header)")
				
						if($('#spn-auditor-background').find("tr:not(.ui-widget-header)").length>1)
							{
						$('#spn-auditor-background'+'_length').hide();
						var results=$('#spn-auditor-background'+'_info').html();
					
						results=results.replace('&nbsp;Show','');
						results=results.replace('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ','');
						results=results.replace('results','');
						results=results.replace('of','');

					
						
						var res="&nbsp;Showing"+results+"of"+results+"results";
						$('#spn-auditor-background'+'_info').html(res); 
						var trimedresult=$('#spn-auditor-background'+'_info').html();
					
						if(trimedresult=='&nbsp;Showing&nbsp;&nbsp;&nbsp;of&nbsp;&nbsp;&nbsp;results')
						{
							$('#spn-auditor-background'+'_info').hide();
							$('#spn-auditor-background'+'_paginate').hide();
							$('#lastUpdated').hide();
							$('#spn-auditor-background').hide();
							$('#not_found').show();
							$("#export").hide();
						}
						
							}
						else
							{
							$('#spn-auditor-background'+'_length').hide();
							$('#spn-auditor-background'+'_info').html("");
							$('#lastUpdated').hide();
							$('#spn-auditor-background').hide();
							$('#not_found').show();
							$("#export").hide();

							}
					  }
					  else
						  {
							$('#spn-auditor-background'+'_length').show();
 
						  }
				  } 
		    });
				}
			else{
			$('#spn-auditor-background').dataTable({			
		      "sPaginationType": "full_numbers",
		      "sDom":"Rfrtlip",
		      "bScrollInfinite":true,
		      "oLanguage": {
		      		 "sInfoEmpty":"No records to show"
		      }
	    	});
			$('#spn-auditor-background'+'_info').hide();
			$('.dataTables_empty').hide();
			$('#lastUpdated').hide();
			$('#not_found').show();
			}


			
			$('.zpickedClick').unbind('click').click(function(event) {

				event.stopPropagation();

				openMultiSelect(this);

				});
			
		$('#spn-auditor-background'+'_filter').css("height","55px");
	 	$('#spn-auditor-background'+'_filter').css("background-color","#01A9DB");
		
		$('#spn-auditor-background'+'_length').css("position","absolute");
		$('#spn-auditor-background'+'_length').css("margin-left","55px");
		$('#spn-auditor-background'+'_length').css("margin-top","-5px");
		

			
			  jQuery('#spn-auditor-background'+'_info').css({'background-color': '#F1EBEB', 'height': '35px','width':'100%','color':'grey','border':'1px solid #ccc','text-align':'left'});
				jQuery('#spn-auditor-background'+'_paginate').css({'background-color': '#F1EBEB', 'height': '30px','width':'350px','border-bottom':'1px solid #ccc','position':'relative','margin-left':'460px', 'margin-top': '-31px','padding-right':'5px'});
		        
				
				
		 	label = jQuery('#spn-auditor-background'+'_filter').children("label"); 
	        label.css('color','#01A9DB'); 
	
	        label.children("input").css({'width':'25%','padding':'3px','margin-left':'520px','margin-top':'13px','border-radius': '25px','-webkit-border-radius': '25px',
	        '-moz-border-radius': '25px','border':'none','padding-left':'30px'});

	        label.children("input"). 
	                before("<i id='searchIcon${spnId}'style='position: absolute; margin-left: 530px; margin-top: 15px;background-color:white;color:grey;font-size:15px;width:20px;' class='icon-search'></i>");
	        
	        label.children("input"). 
	      after("");
		
		
		 $('#popUp').hide();
		
 
		
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
		
		
		function openMultiSelect(myThis)

		{
			alert(myThis.id);
			var hidden = false;

			if ($('#' + 'select' + myThis.id + ':hidden').length > 0) {
				
				alert("hidden");

				hidden = true;

			}


			if (hidden == true) {
				
				alert("hiddenH");

				$('#' + 'select' + myThis.id).show();

			}

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

		

		function submitFilter() {
			$('#firmError').hide();
			var firmIds=$("#providerInput").val();
			var firmArrayValue = $.trim(firmIds);
			var firmArray = firmArrayValue.split(/\s+/);
			if(firmArray.length > 10)
				{
				$('#firmError').html('Firm Ids cannot be greater than 10');
				$('#firmError').show();
				return false;
				}
			 else
				{
				var i;
				for (i = 0; i < firmArray.length; i++) {
				   if(isNaN(firmArray[i]))
					   {
					   $('#firmError').html('Firm Ids must be numeric');
						$('#firmError').show();
					   return false;
					   }
				}
				}
			
			 var spnId=${spnId};
			 var stateCd='${stateCd}';
			 var status='${status}';
			 var providerFirmName = '${providerFirmName}';
			 var providerFirmNumber = '${providerFirmNumber}';
			 var marketId = '${marketId}';
			 var zipCode = '${zipCode}';
			 var districtId = '${districtId}';
			 
			var filterForm = $("#advanceFilterForm").serialize();
			$('#loadingbg').html('<img src="${staticContextPath}/images/loading.gif" width="800px" height="400px">');

			$('#loadingbg').load('spnAuditorSearchTab_searchBackgroundInformationCountAjax.action?spnId='+spnId+'&stateCd='+stateCd+'&status='+status+'&providerFirmName='+providerFirmName+'&providerFirmNumber='+providerFirmNumber+'&marketId='+marketId+'&zipCode='+zipCode+'&districtId='+districtId+'&date='+ new Date().getTime(), filterForm,
					function(data) {
					
					});

		}
		


$('#spnAuditorBg').click(function(){
	 var spnId=${spnId};
	 var stateCd='${stateCd}';
	 var status='${status}';
	 
	 var providerFirmName = '${providerFirmName}';
	 var providerFirmNumber = '${providerFirmNumber}';
	 var marketId = '${marketId}';
	 var zipCode = '${zipCode}';
	 var districtId = '${districtId}';
	
    $('#loadingbg').html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="620px">');

    $('#loadingbg').load('spnAuditorSearchTab_searchBackgroundInformationCountAjax.action?spnId='+spnId+'&stateCd='+stateCd+'&status='+status+'&providerFirmName='+providerFirmName+'&providerFirmNumber='+providerFirmNumber+'&marketId='+marketId+'&zipCode='+zipCode+'&districtId='+districtId+'&date='+ new Date().getTime(), function(data) {
    	
    	  $('#loadingbg').show();
 	});
});


//to close the pop up on outside click of the advanced filter
	 jQuery(document).click(function(e){ 
		var click=$(e.target);
	
	
		if(click.parents().is("#popUp")){
	
			e.stopPropagation();
		}else if(click.parents().is("#linkIcon")){
	
			$('#popUp').focus();
			
			$('#popUp').show();
			
			
			var nAgt = navigator.userAgent;
			if (nAgt.indexOf("Chrome")!=-1) {  
				$("#providerInput").css("margin-left","-190px");  
			} 

			e.stopPropagation();
		}else if(click.parents().is("#clickIcon")){
	
		
			$('#popUp').focus();
			$('#popUp').show();
		

			var nAgt = navigator.userAgent;
			if (nAgt.indexOf("Chrome")!=-1) {  
				$("#providerInput").css("margin-left","-190px");  
			} 
			
			e.stopPropagation();
		} else
			{
			
			$('#popUp').hide();
		}
		
	});
	
	$('#removeIcon').click(function(){
	  
		$('#popUp').hide();
	});
	
	function submitExport()
	{
		
		var selectedFormat= $('#exportTypeSelected option:selected').val();
		var totalCount =${totalRecordCount};
			
		if(selectedFormat == -1){
			$('#exportError').show();
			$('#exportError').html('Please select a format');
		}
		else {
		if(totalCount>1000){
			$('#exportError').show();
			$('#exportError').html('The maximum number of records allowed for export is 1000. Kindly refine your search result using available advanced filters.');
		}
		else{
			$('#exportError').hide();
			location.href = 'spnAuditorSearchTab_exportBackgroundInformationAjax.action?selectedFormat='+selectedFormat;
	   	}
	   		}
	}	
	
	function openBackgroundHistory(vendorId,resourceId,backgroundState)
		{	
			if(backgroundState == 'Not Cleared'){
				var backgroundState = 'NotCleared';
			}
			jQuery("#displayHistoryDiv").load("spnAuditorSearchTab_displayBackgroundHistoryAjax.action?vendorId="+vendorId+ "&resourceId="+ resourceId+"&backgroundState="+backgroundState,
			function(data) {
    	   	jQuery("#displayHistoryDiv").modal({
               onOpen: modalOpenAddCustomer,
               onClose: modalOnClose,
               persist: false,
               containerCss: ({ width: "550px", marginLeft: "-50px",border:"none",height: "0px"})
           }); 
    	  
				jQuery("#displayHistoryDiv").addClass("jqmWindow");
				jQuery("#displayHistoryDiv").css("width", "850px");
				jQuery("#displayHistoryDiv").css("height", "auto");
				jQuery("#displayHistoryDiv").css("margin-left","-427px");
				jQuery("#displayHistoryDiv").css("top", "auto");
				jQuery("#displayHistoryDiv").css("zIndex", 1000);
				jQuery("#displayHistoryDiv").css("background-color","#FFFFFF");
				jQuery("#displayHistoryDiv").css("border-left-color","#696868");
  				jQuery("#displayHistoryDiv").css("border-right-color","#696868");
  				jQuery("#displayHistoryDiv").css("border-bottom-color","#696868");
  				jQuery("#displayHistoryDiv").css("border-top-color","#696868");
				jQuery("#displayHistoryDiv").jqm({
					modal : true
				});
				jQuery(window).scrollTop(0);
				
				
			});
		}
function modalOpenAddCustomer(dialog) {
            dialog.overlay.fadeIn('fast', function() {
            dialog.container.fadeIn('fast', function() {
            dialog.data.hide().slideDown('slow');
            });
        });

 }
  
     function modalOnClose(dialog) {
       dialog.data.fadeOut('slow', function() {
           dialog.container.slideUp('slow', function() {
               dialog.overlay.fadeOut('slow', function() {
                   jQuery.modal.close(); 
               });
           });
       });
    }
      
function closeModal(id) {
	jQuery("#" + id).jqmHide();
	jQuery("#modalOverlay").fadeOut('slow'); 
	jQuery.modal.close(); 
	}   
	</script>
</head>
<body>
<!-- DATATABLE -->

<!-- Advanced Filter for background check -->
<span  id='clickIcon'><i  style='position: absolute; margin-left: 804px; font-size: 18px; margin-top: 28px;background-color:white;color:grey;width:20px;cursor: pointer;' class='icon-sort-down'></i></span>
<div style="z-index: 20000; position: absolute; background-color: white; margin-left: 455px; margin-top: 55px; width: 420px;height:420px;border:2px solid #ccc;text-align:center;border-radius: 7px;-webkit-border-radius:7px;display:none" id="popUp">	
		<div style="display:block;margin-top:-20px;margin-left:-125px; border-color: transparent transparent #E7E5E5 !important;" class="arrowAddNoteHist" id="arrow_}"></div>

		<form id="advanceFilterForm"  >

		<div style="background-color: #F5F1F1;border-bottom:  1px solid #ccc;height:30px;border-radius: 7px 7px 0px 0px;-webkit-border-radius: 7px 7px 0px 0px">
		
		<label id="advancedFilter"style="margin-left:15px;margin-top:5px;position:absolute;"><b>Advanced Filter</b></label>
		<i class="icon-remove" style="margin-left: 175px;font-size:20px;font-weight:normal;position:absolute;cursor: pointer;" id="removeIcon"></i></div>
		<div id="firmError" style="display: block;color:red;margin-top:3px;font-size:12px;font-weight:bold"></div>
		
		<label id="sLCheckStatus" style="margin-left:4px;margin-top:5px;font-weight:normal;position:absolute;" class="bgIEAlign">Please use one or all of the options below to further filter the Background</label>
		<label style="margin-left:4px;margin-top:25px;font-weight:normal;position:absolute;" class="bgIEAlign"> Check results.</label>
		
		<label style="margin-left:20px;margin-top:50px;font-weight:normal;position:absolute;" id="sLCheckStatus" class="bgIEAlignment">ServiceLive Background Check Status</label>
		
		
		<select style="float: left; font-size: small; height: 25px;width:200px;margin-top:70px;margin-left:20px" name="model.backgroundFilterVO.selectedSLBackgroundStatus" id="sLCheckStatus">
												<option value="-1">
													Select All
												</option>
			
												<c:if test="${null != sLBackgroundStatusList && not empty sLBackgroundStatusList}">
													<c:forEach items="${sLBackgroundStatusList}" var="type">
																					<c:set var="selected" value="" ></c:set>
																					<c:set var="slBg" value="${type}" ></c:set>
																
																<c:if test="${null != selectedSLBackgroundStatus}">
																<c:if test="${  fn:contains( selectedSLBackgroundStatus, slBg ) }">
																					<c:set var="selected" value="selected" ></c:set>
																</c:if>																				
																</c:if>
																	
														<option value="${type}" ${selected}>
															${type}
														</option>
													</c:forEach>
												</c:if>
											</select>
		
		
		<label style="margin-left:20px;margin-top:105px;font-weight:normal;position:absolute;" id="reCertificationDue" class="bgIEAlignment">Re-Certification Due </label>
		
				<select style="float: left; font-size: small; height: 25px;width:200px;margin-top:125px;margin-left:-200px" name="model.backgroundFilterVO.selectedReCertification" id="reCertificationDue">
												
												<option value="-1">
													Select All
												</option>
												<c:if test="${null != reCertificationList && not empty reCertificationList}">
													<c:forEach items="${reCertificationList}" var="type">
														<c:set var="selected" value="" ></c:set>
																					<c:set var="slRe" value="${type}" ></c:set>
																
																<c:if test="${null != selectedReCertification}">
																<c:if test="${  fn:contains( selectedReCertification, slRe ) }">
																					<c:set var="selected" value="selected" ></c:set>
																</c:if>																				
																</c:if>
																	
														<option value="${type}" ${selected}>
															${type}
														</option>
													</c:forEach>
												</c:if>
											</select>
		
		
		<label id="systemAction" style="margin-left:20px;margin-top:160px;font-weight:normal;position:absolute;" class="bgIEAlignment">System Action </label>
		
		<select style="float: left; font-size: small; height: 25px;width:200px;margin-top:180px;margin-left:-200px" name="model.backgroundFilterVO.selectedSystemAction" id="systemAction">
												
												<option value="-1">
													Select All
												</option>
												<c:if test="${null != systemActionList && not empty systemActionList}">
													<c:forEach items="${systemActionList}" var="type">
														<c:set var="selected" value="" ></c:set>
																					<c:set var="slSys" value="${type}" ></c:set>
																
																<c:if test="${null != selectedSystemAction}">
																<c:if test="${  fn:contains( selectedSystemAction, slSys ) }">
																					<c:set var="selected" value="selected" ></c:set>
																</c:if>																				
																</c:if>
																	
														<option value="${type}" ${selected}>
															${type}
														</option>
													</c:forEach>
												</c:if>
											</select>
											
		<label style="margin-left:20px;margin-top:220px;font-weight:normal;position:absolute;" id="providerFirm" class="bgIEAlignment">Provider Firm ID (s) - Up to 10 firm ids</label>
		<input type="text" id="providerInput" name="model.backgroundFilterVO.selectedProviderFirmIds" style="margin-left:-300px;margin-top:240px;font-weight:normal;position:absolute;height:22px;width:300px" value="${selectedProviderFirmId}"/>
		<label style="margin-left:20px;margin-top:275px;font-weight:normal;position:absolute;" id="providerFirmLabel" class="bgIEAlignment">Separate multiple firms with space</label>
		<label id="providerFirmLabelInfo" style="margin-left:20px;margin-top:290px;font-weight:normal;position:absolute;" class="bgIEAlignment">Ex. 12345 23478 12876 98765 95438</label>
		
				<div id="testDiv" style="margin-left: 90px;margin-top:275px;">
									
			<a id="spnAuditorBg" style="color:#00A0D2;margin-left:-230px;position:absolute;cursor: pointer"><b> Clear Filter</b></a>	

			<input id="applyButton" type="button" onclick="submitFilter();" value="Apply Filter" class="button action"
															style="display: block;text-transform: none;font-size:13px; margin-left:185px;margin-top:330px"></input>								
									</div>
<div style="height:50px"></div>	

		</form>
</div>	


<!--End of  Advanced Filter for background check -->
		<div style="height: 15px;"></div>							
					

								
									
<table id="spn-auditor-background" border="0" cellpadding="0" cellspacing="0"
	style="width: 100%;text-align:left;display: block">
	<c:choose>
		<c:when test="${count >0}">
	 	<colgroup>
        <col width="20%"/>
        <col width="12%"/>
        <col width="11%"/>
        <col width="11%"/>
        <col width="12%"/>
        <col width="12%"/>
        <col width="22%"/>
		</colgroup>	
	<thead>
		<tr>
		
			<th id="Requirement" class="tabledHdr rqHdr" style="cursor: pointer !important;">
				

			Provider Firm
 
		</th>
			<th class="tabledHdr firmHdr" id="Firm" style="cursor: pointer !important;">
				Provider 
				</th>
			<th class="tabledHdr marketHdr" id="Market" style="cursor: pointer !important;">
				Background Check Status  
			</th>
			<th class="tabledHdr stateHdr" id="State" style="width: 68px;cursor: pointer !important;" >
				Last Certification Date
 
			</th>
			<th class="tabledHdr statusHdr" id="Status" style="width:250px;cursor: pointer !important;text-align:left;">
				Recertification Due Date
  
			</th>
			
			<th class="tabledHdr statusHdr" id="Status" style="width:250px;cursor: pointer !important;text-align:left;">
				Recertification Status
  
			</th>

			<th class="tabledHdr statusHdr" id="Status" style="width:250px;cursor: pointer !important;text-align:left;">
				System Action/Notice Sent On
  
			</th>	
		</tr>
	</thead>
	</c:when>
	<c:otherwise>									
	
		
		
		
		<thead>
		<tr>
		
			<th id="Requirement" class="tabledHdr rqHdr" style="display:none">
		</th>
			
		
		</tr>
	</thead>

	<tbody>
	</tbody>
	</c:otherwise>
	</c:choose>
	</table>

 	<div id="not_found" style="background-color:#F2F2F2;width:100%;padding-top:5px;height:20px;display:block;text-align:center;">
<b>No records matched your filter.</b> Try expanding your search criteria 
<span id="linkIcon"><a  style="cursor: pointer !important;" href="javascript:void(0)"><u>by defining a new filter.</u></a></span>

</div>


<!-- DATATABLE -->
<!-- SLIDER -->
	<div id="spacing" style="display: none;"><br></br></div>
	<div id="spacingIE" style="display: none;"></br></div>
<div style="height:10px"></div>
<div id ="export">
<label id ="export_data" style="font-weight:normal;display:inline-block;width: 150px;">Export(Max 1000 records)</label>
<select name="exportSelected" id="exportTypeSelected">
				<option value="-1">Select One</option>
				<option value="1">XLS</option>
				<option value="2">CSV Comma(,)</option>
				<option value="3">CSV Pipe(|)</option>
</select>

<input id="exportSubmit" onclick ="submitExport();" class="button default searchSubmit" type="submit" style="display:inline-block; width: 106px;margin-left: 10px;" value="Submit"/>

<span id="exportError" style="display:inline-block;color:red;margin-top:3px;font-size:12px;font-weight:bold;"></span>
</div>
<div id="displayHistoryDiv" style="display: none;"></div>
</body>

</html>
