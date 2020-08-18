<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script type="text/javascript">
$(document).ready(function() {
$("#view-lead-note-monitor_filter").hide();	
var leadId='${leadsDetails.slLeadId}';
var onNewTable=$('table #view-lead-note-monitor').dataTable({
	 "bServerSide": true,
	 "bRetrieve": true,
	  "sAjaxSource": "MarketFrontend/buyerLeadOrderSummaryController_viewNotes.action?leadId="+leadId,
	    "iDisplayLength":10,
	  "iDisplayStart":0,
	    "sDom":"Rfrtlip",
	    "sAjaxDataProp": "aaData",
	   "sPaginationType": "full_numbers",
   "bDestroy" : true,
   "aaSorting": [[ 4, "desc" ]],
     "jqueryUI": true,
     "bPaginate": false,
      "oLanguage": {
    		         "oPaginate": {
    		            "sLast": ">>",
    		            "sFirst": "<<",
    		            "sNext": "&nbsp;>&nbsp;",
    		            "sPrevious": "&nbsp;<&nbsp;"
    		            
    		          },
    		          "sLengthMenu":"_MENU_",
			           				 //"sInfo":" Showing _START_ to _END_  total _MENU_ records",
			           				 "sInfoEmpty":"&nbsp;&nbsp;&nbsp;",
			                			 "sEmptyTable":"<div style='background-color:white;width:100%;padding-top:5px;height:20px;display:block;text-align:center;'><b>No records matched your search.</b></div>" 
	         },
    		            "aoColumns":[
			    		     				  {"sClass":"odd"},
			    		    				  {"sClass":"even"},
			    		    				  {"sClass":"odd"},
			    		    				  {"sClass":"even"},
			    		    				  {"sClass":"odd"}
			    		    				  
		    				  ],
    		          
    	
        				 
	         
	    				  "bAutoWidth":false,
	    				  "fnDrawCallback":function(){
							  $("#viewNoteLoadingLogo").hide();	
		    				  $("#view-lead-note-monitor").show();
	    			$("#view-lead-note-monitor_filter").css("padding-left","75%");
					$("#view-lead-note-monitor_filter").css("padding-top","1.5%");
					$("#view-lead-note-monitor_filter").css("padding-bottom","1%");		
	    			//$("#view-notes-monitior_filter").css("padding-bottom","10px");
					//$("#view-notes-monitior_filter").css("padding-left","600px");
					//$("#view-notes-monitior_filter").css("padding-top","5px");
					$("#buyerLeadViewNoteData tr").css("word-break","break-all");
					//$(".viewNotesTable").css("border-left","none");
					$("#view-lead-note-monitor_info").css("padding-left","80%");
					$("#view-lead-note-monitor_info").css("padding-top","1.5%");
					$("#view-lead-note-monitor_info").css("padding-bottom","1%");
					$("#buyerLeadViewNoteData td").css("padding-bottom","1%");
					$("#buyerLeadViewNoteData td").css("padding-top","1%");
					$("#buyerLeadViewNoteData td").css("padding-left","1%");
					$("#view-lead-note-monitor_info").hide();
					$("#view-lead-note-monitor_wrapper").css("padding-bottom","2%");
					$( ".view_Notes_dataTables_Scroll" ).contents().unwrap();
		    		jQuery(".dataTable").wrap('<div class="view_Notes_dataTables_Scroll" />');
		    					 
	    					 
		}
});

});

</script>
<html>
<style type="text/css">
		
		.sorting_asc { 
			background: url('${staticContextPath}/images/grid/arrow-up-white.gif') no-repeat center left; 
		}
		.sorting_desc { 
			background: url('${staticContextPath}/images/grid/arrow-down-white.gif') no-repeat center left; 
		}
		input.buyerLeadDateField{width: 174px;position: relative;background-image: url('${staticContextPath}/images/icons/date.gif');background-repeat: no-repeat;background-position: right;}
	</style>
<head>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buyerLeadManagementStyle.css" />
</head>
  <div class="contentPane"> 
  <br>
  <p class="viewNotesInfo">Stay in contact with your service PROVIDER and keep an ongoing record of decisions about this lead. Use Lead notes as the primary means of communication between buyer and provider to minimize confusion and document any decisions made about the lead for future reference. 
  </p>
  <br>  
  
  <!-- <div class="viewNotesTable" id="orderHistContainer">-->
 <div id="viewNoteLoadingLogo" style="display: block;">
<img src="${staticContextPath}/images/loading.gif" width="442px" height="185px" style="position:relative;top:-15px;left:160px;">
</div>
  <table id="view-lead-note-monitor"  cellpadding="0" cellspacing="0" style="margin: 0; table-layout: fixed; width:100%;display:none;">
 	<thead>
 		<tr>
 					<th id="createdBy" class="col1 first odd"  style="width:18%;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;word-break: break-all;">
 						<p class="labelHdr">Created By</p>
 					</th>
 					<th id="category" class="col2 even" style="width: 10%;px;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;word-break: break-all;">
 						<p class="labelHdr">Category </p>
 					</th>
 					<th id="notes" class="col3 odd" style="width:34%;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;word-break: break-all;">
 						<p class="labelHdr">Notes </p>
 					</th>
 					<th id="leadAlert" class="col4 even" style="width:20%;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;word-break: break-all;">
 						<p class="labelHdr">Alert</p>
 					</th>
 					<th id="date&Time" class="col5 odd last" style="width:18%;cursor: pointer !important;text-align:left;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;word-break: break-all;">
 						<p class="labelHdr">Date & Time</p>
 					</th>
 					
 		</tr>
 	</thead>
 	<tbody id="buyerLeadViewNoteData" style="width:100%;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;">
  	</tbody>
</table>
<!--  </div>-->
</div>
</html>



