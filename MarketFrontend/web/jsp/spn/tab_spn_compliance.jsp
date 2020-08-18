<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/toggle-btn.css"/>

<style>
<!--

-->
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
		background-color:#F1EBEB;

	width:25%;
}
table .firmHdr {
		background-color:#F1EBEB;
	
	width:25%;
}
table .marketHdr {
	background-color:#F1EBEB;

	width:25%;
}
table .stateHdr {
	background-color:#F1EBEB;

	width:15%;
}
table .statusHdr {
	background-color:#F1EBEB;

	width:5%;
}
tr.even td {
 background: none;
}
table td {
    border-bottom: 1px solid #CCCCCC;
    border-right: 1px solid #CCCCCC;
    border-left: 1px solid #CCCCCC;
    padding: 2px 1px 2px 3px;
    text-align: center;
}



.select-Complianceoptions1 {
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
.select-Complianceoptions{
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
.dataTables_info {
	width: 60%;
	float: left;
}

.dataTables_paginate {
	float: right;
	text-align: right;
}

.alignLeft {
padding-left:10px;
text-align: left;
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
		jQuery(document).ready(function() {
			
			/*var complianceType='${complianceType}';
            var filterForm = jQuery("#advanceFilterForm").serialize();
            jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="572px" height="160px"');

            jQuery('#monitor').load('spnMonitorAction_viewComplianceAjax.action?complianceType='+ complianceType, filterForm, function(data) {
            	  $('#monitor').show();
 	    	});*/
			
			
			
			 var complianceType='${complianceType}';
			 var filterForm = jQuery("#advanceFilterForm").serialize();
			 //var filterForm = $("#advanceFilterForm" + networkid).serialize();
			 var count='${count}';
			 
			 
			// for datatable server side
				
		
			
				if(count>0){
					
					
					if(complianceType=='providerCompliance')
						{
					
					$('#spn-monitor-temp').dataTable({
						 "bServerSide": true,
						    "sAjaxSource": "MarketFrontend/spnMonitorNetwork_viewComplianceTabAjaxAction.action?complianceType="+complianceType+"&date='+new Date().getTime()&"+filterForm+"",
						    "sDom":"Rfrtlip",
						   "sPaginationType": "full_numbers",
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
			         				 "sInfo":"&nbsp;&nbsp;&nbsp; Show  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; of _TOTAL_ results",
			         				 "sInfoEmpty":"&nbsp;&nbsp;&nbsp;",
				          			 "sEmptyTable":"<div style='background-color:#F2F2F2;width:100%;padding-top:5px;height:20px;display:block;text-align:center;'><b>No records matched your filter.</b> Try expanding your search criteria <a id='linkIcon' href='javascript:void(0)'><u>by defining a new filter.</u></a></div>" 
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
						    					  $('#spn-monitor-temp_info').show();
						    						$('#spn-monitor-temp_paginate').show();
						    						$('#lastUpdated').show();
						    						$('#spn-monitor-temp').show();
						    						$('#not_found').hide();
						    					  if($('#spn-monitor-temp').find("tr:not(.ui-widget-header)").length<11 ){
						    		
						    				
						    		
						    				
						    						if($('#spn-monitor-temp').find("tr:not(.ui-widget-header)").length>1)
						    							{
						    						$('#spn-monitor-temp_length').hide();
						    						var results=$('#spn-monitor-temp_info').html();
						    				
						    						results=results.replace('Show','');
						    						results=results.replace('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ','');
						    						results=results.replace('results','');
						    						results=results.replace('of','');

						    				
						    						
						    						var res="&nbsp;Showing"+results+"of"+results+"results";
						    						$('#spn-monitor-temp_info').html(res); 
						    						var trimedresult=$('#spn-monitor-temp_info').html();
						    					
						    						if(trimedresult=='&nbsp;Showing&nbsp;&nbsp;&nbsp;of&nbsp;&nbsp;&nbsp;results')
						    						{
						    							$('#spn-monitor-temp_info').hide();
						    							$('#spn-monitor-temp_paginate').hide();
						    							$('#lastUpdated').hide();
						    							$('#spn-monitor-temp').hide();
							    						$('#not_found').show();
						    						}
						    						
						    							}
						    						else
						    							{
						    							$('#spn-monitor-temp_length').hide();
						    							$('#spn-monitor-temp_info').html("");
						    							$('#lastUpdated').hide();
						    							$('#spn-monitor-temp').hide();
							    						$('#not_found').show();
						    							}
						    					  }
						    					  else
						    						  {
						    							$('#spn-monitor-temp_length').show();
						     
						    						  }
						    				  } 
				    });	
						}
					else
						{
						$('#spn-monitor-temp').dataTable({
							 "bServerSide": true,
							    "sAjaxSource": "MarketFrontend/spnMonitorNetwork_viewComplianceTabAjaxAction.action?complianceType="+complianceType+"&date='+new Date().getTime()&"+filterForm+"",
							    "sDom":"Rfrtlip",
							   "sPaginationType": "full_numbers",
							   "aaSorting": [[ 0, "desc" ]],
						      "aLengthMenu": [10,50,100,500,1000],
						      "oLanguage": {
							         "oPaginate": {
							            "sLast": ">>",
							            "sFirst": "<<",
							            "sNext": ">",
							            "sPrevious": "<"
							          },
						
				          "sLengthMenu":" _MENU_ ",
				          "sInfo":"&nbsp;&nbsp;&nbsp; Show  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; of _TOTAL_ results",
				          "sInfoEmpty":"&nbsp;&nbsp;&nbsp;",
				          "sEmptyTable":"<div style='background-color:#F2F2F2;width:100%;padding-top:5px;height:20px;display:block;text-align:center;'><b>No records matched your filter.</b> Try expanding your search criteria <a id='linkIcon' href='javascript:void(0)'><u>by defining a new filter.</u></a></div>" 
							         },
							         "aoColumns":[
							     				  {"sClass":"alignLeft"},
							    				  {"sClass":"alignLeft"},
							    				  {"sClass":"alignLeft"},
							    				  {"sClass":"alignCenter"}
							    				  ],
							    				  "bAutoWidth":false,
							    				  "fnDrawCallback":function(){
							    					  $('#spn-monitor-temp_info').show();
							    						$('#spn-monitor-temp_paginate').show();
						    							$('#lastUpdated').show();
						    							$('#spn-monitor-temp').show();
							    						$('#not_found').hide();
							    					  if($('#spn-monitor-temp').find("tr:not(.ui-widget-header)").length<11 ){
							    		
							    				
							    		
							    				
							    						if($('#spn-monitor-temp').find("tr:not(.ui-widget-header)").length>1)
							    							{
							    						$('#spn-monitor-temp_length').hide();
							    						var results=$('#spn-monitor-temp_info').html();
							    				
							    						results=results.replace('Show','');
							    						results=results.replace('&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ','');
							    						results=results.replace('results','');
							    						results=results.replace('of','');

							    				
							    						
							    						var res="&nbsp;Showing"+results+"of"+results+"results";
							    						$('#spn-monitor-temp_info').html(res); 
							    						var trimedresult=$('#spn-monitor-temp_info').html();
							    					
							    						if(trimedresult=='&nbsp;Showing&nbsp;&nbsp;&nbsp;of&nbsp;&nbsp;&nbsp;results')
							    						{
							    							$('#spn-monitor-temp_info').hide();
							    							$('#spn-monitor-temp_paginate').hide();
							    							$('#lastUpdated').hide();
							    							$('#spn-monitor-temp').hide();
								    						$('#not_found').show();
							    						}
							    						
							    							}
							    						else
							    							{
							    							$('#spn-monitor-temp_length').hide();
							    							$('#spn-monitor-temp_info').html("");
							    							$('#lastUpdated').hide();
							    							$('#spn-monitor-temp').hide();
								    						$('#not_found').show();
							    							}
							    					  }
							    					  else
							    						  {
							    							$('#spn-monitor-temp_length').show();
							     
							    						  }
							    				  } 
					    });	
						
						}
						
					
					
				}
				else
					{
					$('#spn-monitor-temp').dataTable({
						
					      "sPaginationType": "full_numbers",
					      "sDom":"Rfrtlip",
					      "bScrollInfinite":true,
					      "oLanguage": {
					      		 "sInfoEmpty":"No records to show"
					      }
				    });
						$('#spn-monitor-temp_info').hide();
						$('.dataTables_empty').hide();

						jQuery('#spn-monitor-temp_info').css({'background-color': '#bbb', 'height': '20px','width':'920px'});
						jQuery('#spn-monitor-temp_paginate').css({'background-color': '#bbb', 'height': '20px','margin-top':'-20px','width':'220px','border':'1px solid #ccc'});
						$('#lastUpdated').hide();


					}
			
		
			
			$("input[name='on-offButton']").click(function() {
				

				var value = $(this).attr("value");

				if($(this).is(':checked') && !$(this).next('label').hasClass('checked')) {

					
					
				var name1 = $(this).attr('name');

				$('input[name=' + name1 + ']:radio').next('label').removeClass('checked');

				$(this).next('label').addClass('checked');
				
				if(value=='Company'){
				 var complianceType='firmCompliance';
		          
	             jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');

	             jQuery('#monitor').load('spnMonitorAction_viewComplianceAjax.action?complianceType='+ complianceType, function(data) {
	             
	             	  $('#monitor').show();
	             		$('#labelon').addClass('checked');
	             		$('#labeloff').removeClass('checked');

	             });
				}
				else
					{
					var complianceType='providerCompliance';
		             var filterForm = jQuery("#advanceFilterForm").serialize();
		             jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');

		             jQuery('#monitor').load('spnMonitorAction_viewComplianceAjax.action?complianceType='+ complianceType,  function(data) {
		             	  $('#monitor').show();
		             	 $('#labeloff').addClass('checked');
		             		$('#labelon').removeClass('checked');
			 	    	});
					
					}
				}
				
				});
			jQuery('.select-Complianceoptions').hide();
			jQuery('body').unbind('click').click(function() {

				jQuery('.select-Complianceoptions').hide();

			});

			 

			jQuery('.select-Complianceoptions').unbind('click').click(function(event){

			event.stopPropagation();

			});
			
			jQuery('.pickedClick').unbind('click').click(function(event) {

				event.stopPropagation();

				openMultiSelect(this);

				});
			
			jQuery('#spn-monitor-temp_filter').css("height","50px");
			jQuery('#spn-monitor-temp_filter').css("background-color","#01A9DB");
			
			
			jQuery('#spn-monitor-temp_length').css("position","absolute");
			jQuery('#spn-monitor-temp_length').css("margin-left","50px");
			jQuery('#spn-monitor-temp_length').css("margin-top","-5px");
			
			
			jQuery('#spn-monitor-temp_info').css({'background-color': '#F1EBEB', 'height': '35px','width':'100%','text-align':'left','color':'grey','border':'1px solid #ccc'});
			jQuery('#spn-monitor-temp_paginate').css({'background-color': '#F1EBEB', 'height': '20px','margin-top':'-30px','width':'300px','border':'1px solid #ccc'});
			
	 	
		//$('#spn-monitor-temp_filter').css("color","#01A9DB");
		/*$('#spn-monitor-temp_filter').html("<span style='margin-left:-150px; color:#FFFFFF'>Requirements:</span>"+
			"sss<i class='icon-search'></i> <label>Search:<input type='text' aria-controls='spn-monitor-temp'></label>");*/
			
			
			 
			
	 	label = jQuery('#spn-monitor-temp_filter').children("label"); 
        label.css('color','#01A9DB'); 
        //label.html(label.find('input')); 
       // jQuery('#coverageFirmTable_info').hide(); 
       // jQuery('#coverageFirmTable_paginate').hide(); 
       // label.children("input").val("Search name, firm or title "); 
       
       label.children("input").css({'width':'25%','padding':'3px','text-indent':'26px','text-transform':'capitalize','margin-left':'620px','margin-top':'10px','border-radius': '25px','-webkit-border-radius': '25px',
        '-moz-border-radius': '25px','border':'none'});
       
       

        label.children("input"). 
                before("<span><label style='color:white;position:absolute;margin-top:-24px;margin-left:20px;font-weight:normal'>Requirements:</label>"+
                		"<span id='companyChecked' style='margin-left:70px;margin-top:8px;position:absolute' >"+
   			 "<img  src='/ServiceLiveWebUtil/images/common/company_checked.PNG' style='cursor: pointer;'></img>"+
   			"<img id='provider_unchecked' src='/ServiceLiveWebUtil/images/common/prov_unchecked.PNG' style='cursor: pointer;position:absolute;'></img>"+
   			"</span>"+
   			"<span id='providerChecked' style='margin-left:70px;margin-top:8px;position:absolute;display:none'>"+
   			 "<img id='company_unchecked' src='/ServiceLiveWebUtil/images/common/company_unchecked.PNG' style='cursor: pointer;position:absolute;margin-top:2px;'></img>"+
   			"<img  src='/ServiceLiveWebUtil/images/common/provider_checked.PNG' style='cursor: pointer;position:absolute;margin-left:70px;'></img>"+
   			 "</span></span>"+
        		"<i style='position: absolute; margin-left: 630px; margin-top: 12px;background-color:white;color:grey;font-size:15px;width:20px;' class='icon-search'></i>");
        
      //  label.children("input"). 
      //  after("<i id='clickIcon${spnId}' style='position: absolute; margin-left: -28px; font-size: 20px; margin-top: 12px;background-color:white;color:grey;width:20px;' class='icon-sort-down'></i>");
     //   after("<img id='clickIcon${spnId}' style='position: absolute; margin-left: -14px;margin-top: 11px;' src='/ServiceLiveWebUtil/images/common/arrow.png'>");

       // jQuery('#spn-monitor-temp_info').css({'background-color': '#F1EBEB', 'height': '35px','width':'920px'});
		//jQuery('#spn-monitor-temp_paginate').css({'background-color': '#F1EBEB', 'height': '30px','margin-top':'-30px','width':'220px','border':'1px solid #ccc'});
        
				  jQuery('#spn-monitor-temp_info').css({'background-color': '#F1EBEB', 'height': '35px','width':'100%','color':'grey','border':'1px solid #ccc','text-align':'left'});

		jQuery('#spn-monitor-temp_paginate').css({'background-color': '#F1EBEB', 'height': '30px','width':'350px','position':'relative','margin-left':'460px', 'margin-top': '-31px','padding-right':'5px','border':'0px'});

		
        jQuery('#popUp').hide();
        
        
     // to close the pop up on outside click of the advanced filter
      	 jQuery(document).click(function(e){ 
       		var click=$(e.target);
       	
       	
       		if(click.parents().is("#popUp")){
       			e.stopPropagation();
       		}else if(click.parents().is("#linkIcon")){
       			$('#popUp').focus();
				jQuery('.select-Complianceoptions').hide();
       			$('#popUp').show();
       			e.stopPropagation();
       		}else if(click.parents().is("#clickIcon")){
       			$('#popUp').focus();
    			jQuery('.select-Complianceoptions').hide();
       			$('#popUp').show();
       			e.stopPropagation();
       		}  else
       			{
    				jQuery('.select-Complianceoptions').hide();
       				$('#popUp').hide();
       			}
       		
       		
       	});
        jQuery('#companyCompliance').click(function(){
        	 var complianceType='firmCompliance';
          
             jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');

             jQuery('#monitor').load('spnMonitorAction_viewComplianceAjax.action?complianceType='+ complianceType, function(data) {
            	 $('#companyChecked').show();
           	  $('#providerChecked').hide();
	 	    	});	
    	});
        jQuery('#providerCompliance').click(function(){
        	 var complianceType='providerCompliance';
             var filterForm = jQuery("#advanceFilterForm").serialize();
             jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');

             jQuery('#monitor').load('spnMonitorAction_viewComplianceAjax.action?complianceType='+ complianceType,  function(data) {
            	 $('#providerChecked').show();
           	  $('#companyChecked').hide();
	 	    	});
    	});
        
        jQuery('#company_unchecked').click(function(){
       	 var complianceType='firmCompliance';
         
            jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');

            jQuery('#monitor').load('spnMonitorAction_viewComplianceAjax.action?complianceType='+ complianceType, function(data) {
            
            	  $('#companyChecked').show();
            	  $('#providerChecked').hide();

	 	    	});	
   	});
       jQuery('#provider_unchecked').click(function(){
       	 var complianceType='providerCompliance';
            var filterForm = jQuery("#advanceFilterForm").serialize();
            jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');

            jQuery('#monitor').load('spnMonitorAction_viewComplianceAjax.action?complianceType='+ complianceType,  function(data) {
            
            	  $('#providerChecked').show();
            	  $('#companyChecked').hide();

	 	    	});
   	});
       
        jQuery('#removeIcon').click(function(){
        	
			jQuery('.select-Complianceoptions').hide();
        	jQuery('#popUp').hide();
    	});
    	
    	
    	var reqCount = jQuery(".requirementCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		
		if (reqCount > 0) {
			jQuery('#allRequirement').html(reqCount + " selected");
			
		}
		else{
			jQuery('#allRequirement').html("All Requirements");
			}
		
		var statuscount = jQuery(".statusCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (statuscount > 0) {
			jQuery('#allStatus').html(statuscount + " selected");
			
		}
		else{
			jQuery('#allStatus').html("All Status");
			}
		
		var buyerCount = jQuery(".buyerCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (buyerCount > 0) {
			jQuery('#allBuyers').html(buyerCount + " selected");
			
		}
		else{
			jQuery('#allBuyers').html("All buyers");
			}
		
		var SPNCount = jQuery(".spnCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (SPNCount > 0) {
			jQuery('#allNetworks').html(SPNCount + " selected");
			
		}
		else{
			jQuery('#allNetworks').html("All networks");
			}
		
		var providerCount = jQuery(".providerCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (providerCount > 0) {
			jQuery('#allProviders').html(providerCount + " selected");
			
		}
		else{
			jQuery('#allProviders').html("All providers");
			}
		
		
		if(complianceType=='providerCompliance'){
			
			 
			 jQuery('#companyChecked').hide();
			 jQuery('#providerChecked').show();

		 }
		 else
			 {
			 jQuery('#providerChecked').hide();
			
			 jQuery('#companyChecked').show();
			
			 }
		
		});
		
		function openProviderProfile(resourceid)
		{	
		
			companyid=${SecurityContext.companyId};
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

if (jQuery('#'+'select'+myThis.id+':hidden').length > 0){

hidden = true;

}

jQuery('.select-Complianceoptions').hide();

if (hidden == true){

	jQuery('#'+'select'+myThis.id).show();

}

}
	

            					
                     function submitFilter(){        
                                var complianceType='${complianceType}';
                                var filterForm = jQuery("#advanceFilterForm").serialize();
                                jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="572px" height="160px"');

                                jQuery('#monitor').load('spnMonitorAction_viewComplianceAjax.action?complianceType='+ complianceType, filterForm, function(data) {
                                	  $('#monitor').show();
                	 	    	});
                        		
                     }
	
	function clickRequirement()
	{
		
		var count = jQuery(".requirementCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (count > 0) {
			jQuery('#allRequirement').html(count + " selected");
			
		}
		else{
			jQuery('#allRequirement').html("All Requirements");
			}
	}

	function clickStatus()
	{
	
		var count = jQuery(".statusCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (count > 0) {
			jQuery('#allStatus').html(count + " selected");
			
		}
		else{
			jQuery('#allStatus').html("All Status");
			}
	}
		
	
	function clickBuyers()
	{
	
		var count = jQuery(".buyerCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (count > 0) {
			jQuery('#allBuyers').html(count + " selected");
			
		}
		else{
			jQuery('#allBuyers').html("All buyers");
			}
	}
	
	function clickSPNs()
	{
		var count = jQuery(".spnCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (count > 0) {
			jQuery('#allNetworks').html(count + " selected");
			
		}
		else{
			jQuery('#allNetworks').html("All networks");
			}
	}
	
	function clickProviders()
	{
		var count = jQuery(".providerCheckbox[type='checkbox']:checked").length;
		/*if($(this).is(':checked') || count > 0){
			$("#applyFilterInd").val("1");
		}*/
		if (count > 0) {
			jQuery('#allProviders').html(count + " selected");
			
		}
		else{
			jQuery('#allProviders').html("All providers");
			}
	}
	
	</script>
</head>
<body>
<!-- DATATABLE -->
 <!--  <div id="ssssr" style="width: 850px; height:50px; background-color: #01A9DB;"><span style="margin-left:-850px; color:#FFFFFF">Requirements:</span>
sss<i class="icon-search"></i>
</div>-->


 <span  id='clickIcon'>  <i class="icon-sort-down" style="position: absolute; margin-left: 880px; font-size: 18px; margin-top: 32px;background-color:white;color:grey;width:20px;cursor: pointer;" ></i></span>
									
									
<div style="z-index: 20000; position: absolute; background-color: white; margin-left: 525px; margin-top: 65px; width: 400px;height:275px;border: 2px solid #CCCCCC;display:block;border-radius: 7px;" id="popUp${spnId}">	
	<div style="display:block;margin-top:-20px;margin-left:-125px;" class="arrowAddNoteHist" id="arrow_"></div>
	<form id="advanceFilterForm">

		<div style="background-color: #E7E5E5;border-radius: 7px 7px 0px 0px;margin-top:-1px;width:402px;margin-left:-1px;height:35px; border: 1px solid #CCCCCC;">
				<label style="position:absolute;margin-left:15px;margin-top:5px;"><b>Advanced Filter</b></label>
				<i class="icon-remove" style="margin-left: 380px;margin-top:5px;position:absolute;font-size:20px;cursor: pointer;" id="removeIcon"></i></div>
							<label style="margin-left: 15px;margin-top:15px;position:absolute;font-weight:normal;">Networks</label>
				
			<label style="margin-left: 215px;margin-top:16px;position:relative; font-weight:normal;">Requirement</label><div style="height:5px;"></div>
																			
									
			
			<span class="picked pickedClick" id="StateOptions"  style="display: block; width: 0px; margin-left: 95px; margin-top: -213px;position:absolute;">
					<label id="allNetworks" style="background-position: right center; width: 295px;width: 162px;position:absolute;margin-left:-80px;margin-top:210px;border:1px solid #9988FB;text-align:left;padding-left:4px;">
						All networks
					</label>
			</span>				
			<span class="picked pickedClick" id="RequirementOptions" style="display: block; width: 0px; margin-top:0px;margin-left: 295px;">
					<label id="allRequirement"  style="background-position: right center; width: 162px;position:absolute;margin-left:-80px;margin-top:-3px;border:1px solid #9988FB;text-align:left;padding-left:4px;">
						All Requirements
					</label>
			</span>	
			
			<div class="select-Complianceoptions" id="selectStateOptions" 
										 onmouseout="" style="margin-top: 12px; margin-left: 17px; width: 155px;">
										<c:if test="${null != spnList}">
										<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
										<c:forEach var="spn" items="${spnList}" varStatus="i">
											<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedSPNs}">
<c:forEach var="SelecSPN" items="${selectedSPNs}" varStatus="q">
<c:if test="${SelecSPN == spn}">
																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																					</c:forEach>
																			
																				</c:if>	
											
														<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 18%">
													 <input type="checkbox" style="float: left;"
													name="model.spnComplianceVO.selectedSPNs[${i.count}]" 	value="${spn}" id="${i.count}" onclick="clickSPNs()"
														class="spnCheckbox" ${checked} /></td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 82%">
														
												<span style="word-wrap:break-word;float:left;text-align:left;word-break:break-all;">${spn}</span></td>
													</tr>
													
													</div>
											
											
										</c:forEach>
										</table>
										</c:if>
									</div> 
				
			<div class="select-Complianceoptions" id="selectRequirementOptions"  
										 onmouseout="" style="margin-left: 215px; margin-top: 12px; width: 155px;">
											
										<c:if test="${null != requirementList}">
										<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
	<c:forEach var="req" items="${requirementList}" varStatus="i">
																<c:set var="checked" value="" ></c:set>
											<c:set var="reqValue" value="${req.value}" ></c:set>
																
																<c:if test="${null != selectedRequirements}">
<c:forEach var="Selecreq" items="${selectedRequirements}" varStatus="q">
<c:if test="${Selecreq == reqValue}">
																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																					</c:forEach>																				
										</c:if>
													<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%">
														<input type="checkbox" style="float: left;" onclick="clickRequirement();"
													name="model.spnComplianceVO.selectedRequirements[${i.count}]" 	value="${req.value}" id="requirement${i.count}"
														class="requirementCheckbox" ${checked} /></td>
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 85%">
									<span style="word-wrap:break-word;float:left;text-align:left;">${req.descr}</span></td>
													</tr>
														
													
											
										</c:forEach>
										</table>										
										</c:if>
									</div>		
								
			<label style="font-weight:normal;margin-top:30px;margin-left:15px;position:absolute;">Buyers</label>
							<c:choose>								
						       <c:when test="${complianceType=='providerCompliance'}">
								<label style="font-weight:normal;margin-left:15px;position:absolute;margin-top:95px;">Compliance Status</label>
								</c:when>
								<c:otherwise>
<label style="font-weight:normal;margin-left:215px;position:absolute;margin-top:30px;">Compliance Status</label>
								</c:otherwise>
							</c:choose>
						
					<c:if test="${complianceType=='providerCompliance'}">
<label style="position:absolute;margin-left:213px;margin-top:30px;font-weight:normal;">Providers</label>					
		</c:if>
		
		<span class="picked pickedClick" id="MarketsOptions" style="display: block; width: 0px; margin-left: 95px; margin-top: -155px;">
					<label id="allBuyers" style="background-position: right center; width: 162px;position:absolute;margin-left:-80px;margin-top:210px;border:1px solid #9988FB;text-align:left;padding-left:4px;">
						All Buyers
					</label>
			</span>	
			
																											<c:if test="${complianceType=='providerCompliance'}">
			
			<span class="picked pickedClick" id="ProviderOptions" style="display: block; width: 175px; margin-top:208px; margin-left: 215px;postion:absolute;border:1px solid #9988FB">
					<label id="allProviders" style="background-position: right center; width: 165px;text-align:left;padding-left:4px;">
						All providers
					</label>
			</span>	
			
			</c:if>		
			<span class="picked pickedClick" id="ComplianceStatusOptions"  style="
			<c:choose>
			<c:when test="${complianceType=='providerCompliance'}">
			width: 0px; margin-left: 95px; margin-top:-95px;</c:when>
			<c:otherwise>display: block; width: 0px; margin-left: 295px; margin-top:75px; </c:otherwise>
			</c:choose>
			">
					<label id="allStatus" style="background-position: right center; width: 162px;position:absolute;margin-left:-80px;margin-top:130px;border:1px solid #9988FB;text-align:left;padding-left:4px;">
						All Statuses
					</label>
			</span>					
							
									<div class="select-Complianceoptions" id="selectMarketsOptions" 
										 onmouseout="" style="
				<c:choose><c:when test="${complianceType=='providerCompliance'}">margin-top: 75px;</c:when><c:otherwise>margin-top: 146px; margin-left: 16px;</c:otherwise></c:choose>
										  margin-left:17px; width: 155px;">
										<c:if test="${null != buyerList}">
										<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
										<c:forEach var="buyer" items="${buyerList}" varStatus="i">
<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedBuyers}">
																<c:if test="${  fn:contains( selectedBuyers, buyer ) }">
																				
																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																		
																				</c:if>											
											<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%"><input type="checkbox" style="float: left;"
													name="model.spnComplianceVO.selectedBuyers[${i.count}]" 	value="${buyer}" id="${i.count}" onclick="clickBuyers()"
														class="buyerCheckbox" ${checked}/></td>
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 85%">
														
												<span style="word-wrap:break-word;text-align:left;float:left;">${buyer}</span></td>
													</tr>
													
												
											
											
										</c:forEach>
										</table>
										</c:if>
									</div>
									
							
									
									
																								<c:if test="${complianceType=='providerCompliance'}">
									
								  	<div class="select-Complianceoptions" id="selectProviderOptions" 
										 onmouseout="" style="margin-top: 80px;width:157px; margin-left: 215px; display: block;">
										<c:if test="${null != providerList}">
										<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
										<c:forEach var="provider" items="${providerList}" varStatus="i">
																					<c:set var="proName" value="${provider.providerFirstName} ${provider.providerLastName}" ></c:set>
										
											<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedProviders}">
												<c:if test="${  fn:contains( selectedProviders, proName ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																			
																				</c:if>	
											
													<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%">
														 <input type="checkbox" style="float: left;"
													name="model.spnComplianceVO.selectedProviders[${i.count}]" 	value="${proName}" id="${i.count}" onclick="clickProviders()"
														class="providerCheckbox" ${checked} /> </td>
														
														<td style="background-color:#33393C;border:0px;padding: 0px;width: 85%">
														
												<span style="word-wrap:break-word;word-break:break-all;float:left;text-align:left;">${proName}</span></td>
													</tr>
											
										</c:forEach>
										</table>
										</c:if>
									</div>
									</c:if>
									
									<c:if test="${complianceType=='firmCompliance'}">
								
								
								
																	<div class="select-Complianceoptions" id="selectComplianceStatusOptions" 
										 onmouseout="" style="margin-left: 220px;margin-top:145px;">
											<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">
											<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
																				<c:if test="${  fn:contains( selectedComplianceStatus, 'PF SPN CRED INCOMPLIANCE' ) }">

																			
																					<c:set var="checked" value="checked" ></c:set>
																					
																					</c:if>
																				
																				
																				
																				
																				</c:if>
														<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%">
														 <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[1]" 	value="PF SPN CRED INCOMPLIANCE" id="statusIncompliance" onclick="clickStatus()"
														class="statusCheckbox" ${checked} /> </td>
														<td style="background-color:#33393C;border:0px;padding-left: 5px;width: 85%;text-align:left;">
														
												<span style="float:left:text-align:left; word-wrap:break-word;word-break:break-all;">
														In compliance</span></td>
													</tr>
														<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
																
									<c:if test="${  fn:contains( selectedComplianceStatus, 'PF SPN CRED OVERRIDE' ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																		
																				</c:if>
														<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%">
														 <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[2]" 	value="PF SPN CRED OVERRIDE" id="statusBuyerOverride" onclick="clickStatus()"
														class="statusCheckbox" ${checked} /> </td>
														<td style="background-color:#33393C;border:0px;padding-left: 5px;width: 85%;text-align:left;"><span style="float:left:text-align:left; word-wrap:break-word;word-break:break-all;">In compliance due to buyer override
														</span></td>
													</tr>
														<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
					<c:if test="${  fn:contains( selectedComplianceStatus, 'PF SPN CRED OUTOFCOMPLIANCE' ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																			
																				</c:if>
													<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%">
													 <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[3]" 	value="PF SPN CRED OUTOFCOMPLIANCE" id="statusOutOfCompliance" onclick="clickStatus()"
														class="statusCheckbox" ${checked} /> </td>
														<td style="background-color:#33393C;border:0px;padding-left: 5px;width: 85%;text-align:left;"><span style="float:left:text-align:left; word-wrap:break-word;word-break:break-all;">Out of compliance
														</span></td>
													</tr>
													</table>
									
							</div>
		</c:if>
		
		<c:if test="${complianceType=='providerCompliance'}">
								
								
								
																	<div class="select-Complianceoptions" id="selectComplianceStatusOptions" 
										 onmouseout="" style="margin-left: 15px; width:155px;margin-top:145px;display: block;">
<table cellpadding="0" cellspacing="0" style="table-layout: fixed;">											<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
																				<c:if test="${  fn:contains( selectedComplianceStatus, 'SP SPN CRED INCOMPLIANCE' ) }">

																			
																					<c:set var="checked" value="checked" ></c:set>
																					
																					</c:if>
																				
																				
																				
																				
																				</c:if>
														<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%"> 
														
														<input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[1]" 	value="SP SPN CRED INCOMPLIANCE" id="statusIncompliance" onclick="clickStatus()"
														class="statusCheckbox" ${checked} /> </td>
<td style="background-color:#33393C;border:0px;padding-left: 5px;width: 85%;text-align:left;"><span style="float:left:text-align:left; word-wrap:break-word;word-break:break-all;">In compliance
														</span></td>
													</tr>
														<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
																
									<c:if test="${  fn:contains( selectedComplianceStatus, 'SP SPN CRED OVERRIDE' ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																		
																				</c:if>
														<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%"> <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[2]" 	value="SP SPN CRED OVERRIDE" id="statusBuyerOverride" onclick="clickStatus()"
														class="statusCheckbox" ${checked} /> </td>
<td style="background-color:#33393C;border:0px;padding-left: 5px;width: 85%;text-align:left;"><span style="float:left:text-align:left; word-wrap:break-word;word-break:break-all;">In compliance due to buyer override
														</span></td>
													</tr>
														<c:set var="checked" value="" ></c:set>
																<c:if test="${null != selectedComplianceStatus}">
					<c:if test="${  fn:contains( selectedComplianceStatus, 'SP SPN CRED OUTOFCOMPLIANCE' ) }">

																					<c:set var="checked" value="checked" ></c:set>
																					</c:if>
																			
																				</c:if>
														
<tr><td style="background-color:#33393C;border:0px;padding: 3px 0px 0px 0px;vertical-align: baseline;width: 15%"> <input type="checkbox" 
													name="model.spnComplianceVO.selectedComplianceStatus[3]" 	value="SP SPN CRED OUTOFCOMPLIANCE" id="statusOutOfCompliance" onclick="clickStatus()"
														class="statusCheckbox" ${checked} /> </td>
<td style="background-color:#33393C;border:0px;padding-left: 5px;width: 85%;text-align:left;"><span style="float:left:text-align:left; word-wrap:break-word;word-break:break-all;">
Out of compliance
														</span></td>
													</tr>
									</table>
							</div>
		</c:if>
								<c:choose>
									<c:when test="${complianceType=='firmCompliance'}">
									
											<a id="companyCompliance"style="color:#00A0D2;margin-left:25px;margin-top: 175px;position:absolute;"><b style="margin-left:195px;margin-top:5px;position:absolute;cursor: pointer !important;"> Cancel</b></a>	
											</c:when>
											<c:otherwise>
<a style="color:#00A0D2;margin-left:220px;margin-top:130px;position:absolute;cursor: pointer !important;" id="providerCompliance"><b> Cancel</b></a>											
											</c:otherwise>
								</c:choose>			

															<input id="calculate" type="button" onclick="submitFilter();" value="Apply Filter" class="button action"
															style="display: block;text-transform: none;font-size:13px;
															<c:choose><c:when test="${complianceType=='firmCompliance'}">margin-top:180px;margin-left:285px;
															</c:when><c:otherwise>
															margin-top:130px;margin-left:285px;</c:otherwise></c:choose>
															"></img>
									
										
					</form>																				<!--   -->
					</div>	
				
					
					
					
	<div style="height: 20px;"></div>							
									
<table id="spn-monitor-temp" border="0" cellpadding="0" cellspacing="0"
	style="width: 100%;display: block">
	<c:choose>
			<c:when test="${count >0}">
										
	<thead>
		<tr style="background-color: #bbb;">
		
			<th id="spnNetwork" class="tabledHdr rqHdr" style="cursor: pointer !important;">
				Select Provider Network 
			</th>
			<c:if test="${complianceType=='providerCompliance'}">
				<th id="Provider" class="tabledHdr firmHdr" style="width:50px;cursor: pointer !important;">
				Provider 
			</th>
			</c:if>
			<th id="Requirement"class="tabledHdr marketHdr" style="width:50px;cursor: pointer !important;">
				Requirement 
			</th>
			<th id="Buyer" class="tabledHdr stateHdr" style="width:50px;cursor: pointer !important;">
				Buyer 
			</th>
			<th id="Status" class="tabledHdr statusHdr" style="width:50px;cursor: pointer !important;">
				Requirement Status 
			</th>
		</tr>
	</thead>
	<tbody>
 
	
	</tbody>
</table> 
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
	</table>
	</c:otherwise>
</c:choose>
	<div id="not_found" style="background-color:#F2F2F2;width:100%;padding-top:5px;height:20px;display:block;text-align:center;">
<b>No records matched your filter.</b> Try expanding your search criteria 
<a id="linkIcon" style="cursor: pointer !important;" href="javascript:void(0)"><u>by defining a new filter.</u></a>

</div>
		
<br></br>
<!-- DATATABLE -->
<!-- SLIDER -->


<div style="height:5px;"></div>
<span id="lastUpdated" style="float:right;">Compliance status last updated: <fmt:formatDate value="${updatedDate}" pattern="hh:mm a MM/dd/yy" /></span>
<div style="height:5px;"></div>
</body>
</html>
