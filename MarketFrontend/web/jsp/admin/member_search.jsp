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
		<title>ServiceLive - Search Portal</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/admin/search-portal.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/pagination.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/password_modal.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modalPassword.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/tablePassword.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css" media="screen, projection" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/memberSearchDatepicker.css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
	      
	      .grayText { color: grey;
				font-family: arial;
				font-style: italic;
				font-size: 13px; 
			}
		  .shadowBox{border:1px solid #cecece;padding:3px 2px;background:#fff url(../images/common/textboxBg.gif) repeat-x top left;font-size:10px;font-family:verdana;}
		  input.lockedField,.lockedField{background:#FF9;}
		</style>
		<!--  SL-18330 Changs for First/Last Provider Name change Start-->
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.tabs.css" media="screen, projection">
		
	</head>

<body id="search-portal">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">		
		<div id="sidebar" class="span-5">
			<jsp:include page="/jsp/admin/modules/sb_tools.jsp" />
			<jsp:include page="/jsp/admin/modules/sb_office.jsp" />
		</div>
		
		<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true"/>
		
		<div id="primary" class="span-19 last">			
			
			<h2 id="page-role">Administrator</h2>
			<h2 id="page-title">Search Portal</h2>
			
			
			
	        <div id="search">
	            <ul>
	                <li><a href="#search-1" onclick="changeTab();"><span>Service Provider</span></a></li>
	                <li><a href="#search-2" onclick="changeTab();"><span>Provider Firm</span></a></li>
	                <li><a href="#search-3" onclick="changeTab();"><span>Buyer</span></a></li>
	            </ul>
				<div id="search-1" class="clearfix">
					<jsp:include page="/jsp/admin/member_search_tab1.jsp" />
				</div>
				<div id="search-2" class="clearfix">
					<jsp:include page="/jsp/admin/member_search_tab2.jsp" />				
				</div>
				<div id="search-3" class="clearfix">
					<jsp:include page="/jsp/admin/member_search_tab3.jsp" />
				</div>
			</div>
			
			<div id="searchPortalResults"> </div>
			<div id="loadingArea" style="display: none;">
				<div style="text-align: center; padding-left: 50px; padding-right: 50px; padding-top: 50px;">
					Your results will load shortly...
				</div>
				<div style="text-align: center; margin-top: 10px; padding-bottom: 50px;">
					<img src="${staticContextPath}/images/simple/loading.gif" />
				</div>
			</div>

		</div>
	</div>
	
		<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="search.portal"/>
	</jsp:include>
	
	<%-- SL-18330 Changes start START--%>
	<div id="editProviderNames" class="jqmWindow">
	<div class="modal-header">
		<h5>Edit Provider Details</h5>
	</div>
	<div class="content clearfix" style="text-align: left;">		
		<div id="editProviderErrorMessages" class="error" style='display:none'></div>

			
	<form id="editProviderNamesForm" action="adminSearch_updateServiceProviderName.action" method="POST" enctype="multipart/form-data" data-ajax="false">
	    <p> Please update the Provider Name Details</p>
	  
	  
	   <table cellpadding="0" cellspacing="0" border="1">
	    
	  <div class="span-19 last">
	  						
								<div class="clearfix">
									<div class="left">
									<label>Vendor Id</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
									<input type="text" class="grayText" value="" id="userId1" style="border: 0px solid #000000; width:50px;" readonly/>
									</div>
								
									<div class="left last">
									<label>Resource Id</label><br/>
									<input type="text"  class="grayText" value="" id="resourceId1" style="border: 0px solid #000000; width:50px;" readonly/>
									</div>
								
								</div>
								
								<div class="clearfix">
									<div class="left">&nbsp;</div>
								</div>	
									
								<div class="clearfix">
									<div class="left">
									<label>First Name <span class="req">*</span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
									<input type="text"  class="shadowBox grayText lockedField" value="" id="firstName1" style="width:150px;"  onKeyDown="limitText(this,50);" 
onKeyUp="limitText(this,50);" />
									</div>
								
									<div class="left last">
									<label>Middle Name (Optional)</label><br/>
									<input type="text"  class="shadowBox grayText lockedField" value="" id="middleName1" style="width:150px;" onKeyDown="limitText(this,50);" 
onKeyUp="limitText(this,50);" />
									</div>
								
								</div>	
								
						<div class="clearfix">
									<div class="left">&nbsp;</div>
								</div>
										
							<div class="clearfix">
									<div class="left">
									<label>Last Name <span class="req">*</span></label><br/>
									<input type="text"  class="shadowBox grayText lockedField" value="" id="lastName1" style="width:150px;" onKeyDown="limitText(this,50);" 
onKeyUp="limitText(this,50);"/>
									</div>	
							</div>
									
							
								 <div class="clearfix">
									<div class="left">&nbsp;</div>
								</div>	  
	    
	    
		
	</form>
	</table>
	<div class="clearfix">
									<div class="left">
									<a href="#" class="jqmClose left" onclick="#" style="color:#8a1f11">Cancel</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</div>
								
									<div class="left last">
									<a href="#"  onclick="return validateProviderNamesDetails();" style="color:#8a1f11">Continue</a>
									</div>
								
								</div>	
								
		</div>							
	
	 
	</div>
	
	<%-- Changes end--%>
	
	
	<div id="editProviderNames_final" class="jqmWindow">
	<div class="modal-header">
		<h5>Edit Provider Details</h5>
	</div>
	<div class="content clearfix" style="text-align: left;">		
		<div id="editProviderErrorMessages_final" class="error" style='display:none'></div>

			
	<form id="editProviderNamesForm_final" action="adminSearch_updateServiceProviderName.action" method="POST" enctype="multipart/form-data" data-ajax="false">
	    <p> Please update the Provider Name Details</p>
	    
	    <table cellpadding="0" cellspacing="0" border="1">
	    
	  <div class="span-19 last">
	  						
	  							<div class="clearfix">
									<div class="left">
									<label>Vendor Id</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
									<input type="text" class="grayText" value="" id="userId12" style="border: 0px solid #000000; width:50px;" readonly/>
									</div>
								
									<div class="left last">
									<label>Resource Id</label><br/>
									<input type="text"  class="grayText" value="" id="resourceId12" style="border: 0px solid #000000; width:50px;" readonly/>
									</div>
								
								</div>
								
								<div class="clearfix">
									<div class="left">&nbsp;</div>
								</div>	
								
	  						
	  							<div class="clearfix">
									<div class="left">
									<label>Old Provider Name</label> <input type="text"  class="shadowBox grayText lockedField" value="" id="old_providername" style="width:200px;"  readonly/>
									</div>
								</div>
								
								<div class="clearfix">
									<div class="left">&nbsp;</div>
								</div>	
								
								<div class="clearfix">
									<div class="left">
									<label>New Provider Name</label><input type="text"  class="shadowBox grayText lockedField" value="" id="new_providername" style="width:200px;"  readonly/>
									</div>
								</div>
								
								<div class="clearfix">
									<div class="left">&nbsp;</div>
								</div>
		</div>
		</table>						
	    <input type="hidden" name="vendorId" id="vendorId" value="" />
		<input type="hidden" name="resourceId" id="resourceId" value="" />
		<input type="hidden" name="providerFirstName" id="providerFirstName" value="" />
		<input type="hidden" name="ProviderMiddleName" id="ProviderMiddleName" value=""/>
		<input type="hidden" name="ProviderLastName" id="ProviderLastName" value=""/>
		<input type="hidden" name="ProviderWholeName" id="ProviderWholeName" value=""/>
		<input type="hidden" name="modified_by" id="modified_by" value=""/>
	 </form>
	 
	 <div class="clearfix">
									<div class="left">
									<a href="#" class="jqmClose left" onclick="GoBackCalled();" style="color:#8a1f11">Go Back</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</div>
								
									<div class="left last">
									<input type="submit" class="button action right" id="save" value="Save" onclick="return submitproviderNameDetails();"/>
									</div>
								
								</div>	
								
		</div>
	 
	 
	 </div>
	 </div>
	 <!-- SL-18330 Changes END --> 
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.resetform.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery-form.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.tablesorter.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/plugins/jquery.pagination.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/pwdReset.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.mask.min.js"></script>
		<!-- SL-18330 Changs for First/Last Provider Name change  End-->
		
		<script type="text/javascript">
		
			function maskSOId(id)
			{
				soIdVal = document.getElementById(id).value;
				soIdVal = soIdVal.replace(/-/g, "");
				soIdVal = soIdVal.substring(0, 13);
				soIdValNew = "";
				
				for (x = 0; x < soIdVal.length; x++)
				{
					c = soIdVal.charAt(x);
					if (x == 3 || x == 7 || x == 11)
					{ 
						soIdValNew = soIdValNew + "-"; 
					}
					soIdValNew = soIdValNew + c; 	
				}
				
				document.getElementById(id).value = soIdValNew;
			}
			
			function maskPhone(id)
			{
				phoneVal = document.getElementById(id).value;
				phoneVal = phoneVal.replace(/-/g, "");
				phoneVal = phoneVal.substring(0, 10);
				phoneValNew = "";
				
				for (x = 0; x < phoneVal.length; x++)
				{
					c = phoneVal.charAt(x);
					if (x == 3 || x == 6)
					{ 
						phoneValNew = phoneValNew + "-"; 
					}
					phoneValNew = phoneValNew + c; 	
				}
				
				document.getElementById(id).value = phoneValNew;
			}
			
			jQuery(document).ready(function($) {
			
				///// ALL 3 settings
				$("a.cancel").click(function(){
					$("#searchPortalServiceProvider").clearForm();
					$("#searchPortalBuyer").clearForm();
					$("#searchPortalProviderFirm").clearForm();
					document.getElementById('searchPortalResults').innerHTML = '';
					document.getElementById('errorMessagesTop1').innerHTML = '';
					document.getElementById('errorMessagesTop2').innerHTML = '';
					document.getElementById('errorMessagesTop3').innerHTML = '';
					//$("#searchPortalBuyer").clearForm();
				});
				$('#search').tabs({active: ${tabInSession}});
				//$("input.phone").mask("(999) 999-9999");
				//$("input.serviceorder").mask("999-9999-9999-99", {placeholder:" "});
			
				////////////// TAB 1 settings
				$("input.pdate1").datepicker({
					changeMonth: true,
					changeYear: true,
					closeText: "Close",
					showButtonPanel: true
				}).keyup(function(e) {
				    if(e.keyCode == 8 || e.keyCode == 46) {
				        $.datepicker._clearDate(this);
				    }
				});
				$("input.pdate2").datepicker({
					changeMonth: true,
					changeYear: true,
					closeText: "Close",
					showButtonPanel: true
				}).keyup(function(e) {
				    if(e.keyCode == 8 || e.keyCode == 46) {
				        $.datepicker._clearDate(this);
				    }
				});
				$(".form_tab1").keypress(function (e) {
				      if (e.which == 13) {
				      	searchServiceProvider('','desc');
				        return false;
				      }
				});
			
				////////////// TAB 2 settings
				$("input.pfdate1").datepicker({
					changeMonth: true,
					changeYear: true,
					closeText: "Close",
					showButtonPanel: true
				}).keyup(function(e) {
				    if(e.keyCode == 8 || e.keyCode == 46) {
				        $.datepicker._clearDate(this);
				    }
				});
				$("input.pfdate2").datepicker({
					changeMonth: true,
					changeYear: true,
					closeText: "Close",
					showButtonPanel: true
				}).keyup(function(e) {
				    if(e.keyCode == 8 || e.keyCode == 46) {
				        $.datepicker._clearDate(this);
				    }
				});
				$(".form_tab2").keypress(function (e) {
				      if (e.which == 13) {
				      	searchProviderFirm('','desc');
				        return false;
				      }
				});

			
				////////////// TAB 3 settings
				$("input.bdate1").datepicker({
					changeMonth: true,
					changeYear: true,
					closeText: "Close",
					showButtonPanel: true
				}).keyup(function(e) {
				    if(e.keyCode == 8 || e.keyCode == 46) {
				        $.datepicker._clearDate(this);
				    }
				});
				$("input.bdate2").datepicker({
					changeMonth: true,
					changeYear: true,
					closeText: "Close",
					showButtonPanel: true
				}).keyup(function(e) {
				    if(e.keyCode == 8 || e.keyCode == 46) {
				        $.datepicker._clearDate(this);
				    }
				});
				$(".form_tab3").keypress(function (e) {
				      if (e.which == 13) {
				      	searchBuyer('','desc');
				        return false;
				      }
				});
				
				$('#editProviderNamesForm').ajaxForm({
		    	dataType: 'json',
		        success: processJsonResponse
		   		 });
			   
				$('#editProviderNames').jqm({modal: true, toTop: true});
				
				
				
				$('#editProviderNamesForm_final').ajaxForm({
		    	dataType: 'json',
		        success: processJsonResponse_final
		   		 });
			   
				$('#editProviderNames_final').jqm({modal: true, toTop: true});
			});
			
			function processJsonResponse(data)
		{
		    if (data.error == "") {	
				jQuery(document).ready(function($) {
					$('#editProviderNames').jqmHide();
			});
			} else {
				$('#editProviderErrorMessages').html(data.error);
				$('#editProviderErrorMessages').show();
			}
		}
		
		function showProviderNameDetails(userId,vendorId,firstName,middleName,lastName,userName,slNameChange)
		{
			jQuery(document).ready(function($) {
		
				$('#userId1').val(vendorId);
				$('#resourceId1').val(userId);
				$('#firstName1').val(firstName);
				$('#middleName1').val(middleName);
				$('#lastName1').val(lastName);	
				$('#modified_by').val(userName);	
				$('#ProviderWholeName').val($('#firstName1').val() +" "+ $('#middleName1').val() +" "+ $('#lastName1').val());
				
				$('#old_providername').val($('#firstName1').val() +","+ $('#middleName1').val() +","+ $('#lastName1').val());
				
// 				code change for SLT-1602
				
				if(slNameChange=="true"){
					$("#firstName1").attr('disabled','disabled');
					$("#lastName1").attr('disabled','disabled');
				}
				else if(slNameChange=="false"){					
					$("#firstName1").removeAttr('disabled');
					$("#lastName1").removeAttr('disabled');					
				}
				
				$('#editProviderNames').jqmShow();
				$('#firstName1').focus();
			});
		}
		
		function limitText(limitField, limitNum) {
		    if (limitField.value.length > limitNum) {
		        limitField.value = limitField.value.substring(0, limitNum);
		    }
		}
		
		function validateProviderNamesDetails()
		{
			jQuery(document).ready(function($) {
			 if($('#firstName1').val()==""){
			 	 $('#editProviderErrorMessages').html("Please enter a First Name.");
			 	 $('#editProviderErrorMessages').show();
				 return false;
			 }else if($('#lastName1').val()==""){
				 $('#editProviderErrorMessages').html("Please enter a Last Name.");
			 	 $('#editProviderErrorMessages').show();
				 return false;
			 }else if($.trim($('#firstName1').val())==""){
				 $('#editProviderErrorMessages').html("Please enter valid First Name.");
			 	 $('#editProviderErrorMessages').show();
			 	 $('#firstName1').val($.trim($('#firstName1').val()));
			 	 $('#firstName1').focus();
			 	 return false;
			 }else if($.trim($('#lastName1').val())==""){
				 $('#editProviderErrorMessages').html("Please enter valid Last Name.");
			 	 $('#editProviderErrorMessages').show();
			 	 $('#lastName1').val($.trim($('#lastName1').val()));
			 	 $('#lastName1').focus();
				 return false;
			 }	
			 $('#firstName1').val($.trim($('#firstName1').val()));
			 $('#middleName1').val($.trim($('#middleName1').val()));
			 $('#lastName1').val($.trim($('#lastName1').val()));
			 	
			 	 $('#userId12').val($('#userId1').val());
			 	 $('#resourceId12').val($('#resourceId1').val());
			 	 $('#new_providername').val($('#firstName1').val() +","+ $('#middleName1').val() +","+ $('#lastName1').val());
			    
			     $('#editProviderErrorMessages').html("");
		 	     $('#editProviderErrorMessages').hide();
		 	 	 $('#editProviderNames').jqmHide();
		 	 	 
			    $('#editProviderNames_final').jqmShow();
			    
			    });  
			    
		}
		
		function GoBackCalled()
		{
			jQuery(document).ready(function($) {
			
				$('#editProviderNames_final').jqmHide();
				$('#editProviderNames').jqmShow();
				
			});
		}
	   function submitproviderNameDetails()
	   {
	   			jQuery(document).ready(function($) {
	             $('#vendorId').val($('#userId1').val());
			 	 $('#resourceId').val($('#resourceId1').val());
			 	 $('#providerFirstName').val($('#firstName1').val());
			 	 $('#ProviderMiddleName').val($('#middleName1').val());
			 	 $('#ProviderLastName').val($('#lastName1').val());
			 	
			 	 document.getElementById("editProviderNamesForm_final").action="adminSearch_updateServiceProviderName.action"; 
			     document.getElementById("editProviderNamesForm_final").submit();
			    
			     });
	   }
		function processJsonResponse_final(data)
		{
		    if (data.error == "") {	
				jQuery(document).ready(function($) {
					$('#editProviderNames_final').jqmHide();
			});
			} else {
				$('#editProviderErrorMessages_final').html(data.error);
				$('#editProviderErrorMessages_final').show();
			}
		}
		
			function changeTab()
			{
				document.getElementById('searchPortalResults').innerHTML = '';
			}
			function initPagination() {
		    	jQuery.noConflict();
	 	        var num_entries = jQuery('#hiddenresult tr.result').length;
	 	        jQuery.noConflict();
	 	        // Create pagination element
	 	        if(num_entries > 0){
	 	        	jQuery("#Pagination").pagination(num_entries, {
	 	            	num_edge_entries: 2,
	 	            	num_display_entries: 3,
	 	            	callback: pageselectCallback,
	 	            	items_per_page:10
	 	            });
	 	        }
	 	    }
	 	    function pageselectCallback(page_index, jq){
	 	    	jQuery.noConflict();
	 	        var items_per_page ='10';
	 	        var newcontent = '';
	 	        var tdValues = new Array();
	 	        tdValues = jQuery('#hiddenresult tr');
	 	        var max_elem = Math.min((page_index+1) * items_per_page, tdValues.length);
	 	        // Iterate through a selection of the content and build an HTML string
	 	        for(var i=page_index*items_per_page;i<max_elem;i++)
	 	        {
	 	        	newcontent += " <tr class='result'>"+tdValues[i].innerHTML+"</tr>";
	 	        }
	 	        // Replace old content with new content
	 	        jQuery('#Searchresult').empty().append(newcontent);
	 	        jQuery("a.admin-menu").click(function(){
	 	        	jQuery(this).parent('td').children('ul').toggle();
	 	        });
	 	        return false;
	 	    }
			function searchBuyer(sortKey,sortOrder)
			{
				document.getElementById('searchPortalResults').innerHTML = document.getElementById('loadingArea').innerHTML;
				jQuery.noConflict();
	
				jQuery(document).ready(function($) {
					var queryString = $('#searchPortalBuyer').formSerialize();
					 $.post('adminSearch_searchBuyer.action?sortKey='+sortKey+'&sortOrder='+sortOrder, queryString, function(data) {
						if (data == "")
						{
							alert('no data');
						}
						else
						{
							document.getElementById('searchPortalResults').innerHTML = data;
							if (document.getElementById('errorMessagesRes') != null)
							{
								document.getElementById('errorMessagesTop3').innerHTML = document.getElementById('errorMessagesRes').innerHTML;
							}
							else
							{
								document.getElementById('errorMessagesTop3').innerHTML = "";
							}
							if(sortKey != ''){
                            	if(sortOrder == "asc"){
                                	var ctl = '#' + sortKey;
                                    $(ctl).addClass("headerSortDown");
                                }else{
                                	var ctl = '#' + sortKey;
                                    $(ctl).addClass("headerSortUp");
                                }
                           }
							jQuery(document).ready(function($) {
								 initPagination();
								 $("#sort3 th").click(function(){
								 	 var num_entries = jQuery('#hiddenresult tr.result').length;
								 	 if(num_entries >1 && this.id != 'administration'){ 
								 	 	sortKey = this.id;
								 	 	//Toggling the SortOrder on each click
                                        if(sortOrder == "asc"){
                                        	sortOrder = "desc";
                                        }else{
                                            sortOrder = "asc";
                                        }
                                      searchBuyer(sortKey,sortOrder);
                                     }
							});
							$("#sort3").tablesorter( {headers: { 0: { sorter: false}}});
						  });
						}
					}, "html");
				});
			}
			
			function searchProviderFirm(sortKey,sortOrder)
			{
				document.getElementById('searchPortalResults').innerHTML = document.getElementById('loadingArea').innerHTML;
				jQuery.noConflict();
	
				jQuery(document).ready(function($) {
					var queryString = $('#searchPortalProviderFirm').formSerialize();
					$.post('adminSearch_searchProviderFirm.action?sortKey='+sortKey+'&sortOrder='+sortOrder, queryString, function(data) {
						if (data == "")
						{
							alert('no data');
						}
						else
						{
							document.getElementById('searchPortalResults').innerHTML = data;
							if (document.getElementById('errorMessagesRes') != null)
							{
								document.getElementById('errorMessagesTop2').innerHTML = document.getElementById('errorMessagesRes').innerHTML;
							}
							else
							{
								document.getElementById('errorMessagesTop2').innerHTML = "";
							}
							if(sortKey != ''){
                            	if(sortOrder == "asc"){
                                	var ctl = '#' + sortKey;
                                    $(ctl).addClass("headerSortDown");
                                }else{
                                    var ctl = '#' + sortKey;
                                    $(ctl).addClass("headerSortUp");
                               }
                            }
							jQuery(document).ready(function($) {
								initPagination();
								$("#sort3 th").click(function(){
									var num_entries = jQuery('#hiddenresult tr.result').length;
									if(num_entries >1 && this.id != 'administration'){
									 sortKey = this.id;
									 //Toggling the SortOrder on each click
									 if(sortOrder == "asc"){
                                     	sortOrder = "desc";
                                     }else{
                                     	sortOrder = "asc";
                                     }
                                     searchProviderFirm(sortKey,sortOrder);
                                }
							});							
							$("#sort3").tablesorter( {headers: { 0: { sorter: false}}});
						 });
						}
					}, "html");
				});
			}
			
			function searchServiceProvider(sortKey,sortOrder)
			{
				document.getElementById('searchPortalResults').innerHTML = document.getElementById('loadingArea').innerHTML;
				jQuery.noConflict();
	
				jQuery(document).ready(function($) {
					var queryString = $('#searchPortalServiceProvider').formSerialize();
					$.post('adminSearch_searchServiceProvider.action?sortKey='+sortKey+'&sortOrder='+sortOrder, queryString, function(data) {
						if (data == "")
						{
							alert('no data');
						}
						else
						{
							document.getElementById('searchPortalResults').innerHTML = data;
							if (document.getElementById('errorMessagesRes') != null)
							{
								document.getElementById('errorMessagesTop1').innerHTML = document.getElementById('errorMessagesRes').innerHTML;
							}
							else
							{
								document.getElementById('errorMessagesTop1').innerHTML = "";
							}
							if(sortKey != ''){
	                            if(sortOrder == "asc"){
	                                var ctl = '#' + sortKey;
                                    $(ctl).addClass("headerSortDown");
                                }else{
                                    var ctl = '#' + sortKey;
                                    $(ctl).addClass("headerSortUp");
                                }
                            }
							jQuery(document).ready(function($) {
								initPagination();
								$("#sort3 th").click(function(){
									 var num_entries = jQuery('#hiddenresult tr.result').length;
                                     if(num_entries >1 && this.id != 'administration'){
                                     	sortKey = this.id;
                                     	//Toggling the SortOrder on each click
                                     	if(sortOrder == "asc"){
	                                     	sortOrder = "desc";
	                                 	}else{
                                         	sortOrder = "asc";
                                     	}
                                     	searchServiceProvider(sortKey,sortOrder);
                                     }
								});
								$("#sort3").tablesorter( {headers: { 0: { sorter: false}}});
							});
						}
					}, "html");
				});
			}
			
			function showBuyerDetails(buyerId,resourceId,roleId,userName,companyname,city,state,adminname)
			{
				document.getElementById('buyerResults.companyId').value = buyerId;
				document.getElementById('buyerResults.userId').value = resourceId;
				document.getElementById('buyerResults.roleTypeId').value = roleId;
				document.getElementById('buyerResults.adminName').value = adminname;
				document.getElementById('buyerResults.userName').value = userName;
				document.getElementById('buyerResults.businessName').value = companyname;
				document.getElementById('buyerResults.city').value = city;
				document.getElementById('buyerResults.state').value = state;
				document.hiddenBuyerForm.action="adminSearch_navigateToBuyerPage.action";
				document.hiddenBuyerForm.submit();
			}
			
			function showProvDetails(vendorId)
			{
				document.getElementById('spfResults.companyId').value = vendorId;
				document.hiddenProviderFirmForm.action="adminSearch_navigateToProviderPage.action";
				document.hiddenProviderFirmForm.submit();
			}
			
			function showEditProv(vendorId, userId) {
				document.getElementById('spfResults.userId').value = userId;
				document.getElementById('spfResults.resourceId').value = userId;
				showProvDetails(vendorId);
			}
			
			var popupid, rid;			
			
           
		   function submitResetPassword(method, id){
			if (method == 'resetPasswordBuyer') {	    	
				    document.getElementById('buyerResults.resourceId').value = id;		
					document.hiddenBuyerForm.action="adminSearch_searchServiceProvider!"+method+".action?userId="+id;
					document.hiddenBuyerForm.submit();
				} else {						   		
					document.getElementById('spfResults.userId').value = id;
					document.getElementById('spfResults.resourceId').value = id;
					document.hiddenProviderFirmForm.action="adminSearch_searchServiceProvider!"+method+".action?userId="+id;
					document.hiddenProviderFirmForm.submit();
				}
		   	//	var id = getSelectedUserId();
		   	//	document.getElementById('spfResults.userId').value = id;
			//	document.getElementById('spfResults.resourceId').value = id;
		   		
		   	//	alert(id);		   					
			//	if (method == 'resetPasswordProvider') {
			//		document.getElementById('searchPortalResults').innerHTML = document.getElementById('loadingArea').innerHTML;
			//		jQuery.noConflict();
			//		jQuery(document).ready(function($) {
			//		//var queryString = ("userId="+id).formSerialize();
			//		//$.post('adminSearch_resetpassword!resetPasswordProvider.action?', queryString, function(data) {
			//		alert("adminSearch_searchServiceProvider!"+method+".action?userId="+id);
			//		$.post("adminSearch_searchServiceProvider!"+method+".action?userId="+id+"resourceId="+id, "", function(data) {
			//		alert(data);
			//			if (data == "")
			//			{
			//				alert('no data');
			//			}
			//			else
			//			{
			//			}
			//		}, "html");
			//	});
			//	alert("before hide");
			//	hideResetModal();
				
			//} else {						   		
			//	alert("RESOURCE")
			//	document.getElementById('spfResults.userId').value = id;
			//	document.getElementById('spfResults.resourceId').value = id;
			//	document.hiddenProviderFirmForm.action="adminSearch_searchServiceProvider!"+method+".action?userId="+id;
			//	document.hiddenProviderFirmForm.submit();
			//}
				
			}		
   
			
		</script>
	</body>
</html>

