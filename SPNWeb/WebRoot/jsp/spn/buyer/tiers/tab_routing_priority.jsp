<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />


<head>
    <meta name="decorator" content="routingspndecorator" />
	<title>ServiceLive - Select Provider Network - Create Networks</title>	
		
	<link rel="stylesheet" media="screen, projection" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" />		
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/routingPriority.css" />
	<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
	<link href="${staticContextPath}/css/font-awesome.min.css"	rel="stylesheet" />
	<link href="${staticContextPath}/css/font-awesome-ie7.min.css"	rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/toggle-btn.css"/>
	
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>		
	<script type="text/javascript">
	
		jQuery(document).ready(function($) {
	
			$('#fromSpnMonitor').val("false");
			var id ='${model.spnBeingEdited}';
			if(id!=-1){
				$('#spnId').val(id);
				setTimeout(function(){
					$('#spnId').trigger('change');
				},400);
				$('#fromSpnMonitor').val("true");
			}
			
			//on selecting an SPN from drop down
			$("#spnId").change( function(){
				$('#routingPriority').hide();
				$('#criteriaLevel').hide();
				$('#spnId').css('width','50%');
				$('#criteriaLevel').hide();
				$('#viewCriteriaDiv').hide();
				$("input:radio").attr("checked", false);
				var spnId = $('#spnId').val();				
				if(spnId != -1)
				{
					$("#loadDiv").jqm({modal : true,overlay : 0});
					$("#loadDiv").jqmShow();
					$('#routingPriority').load('spnReleaseTiers_buttonLoadTierAjax.action', {'spnId': spnId }, function(){
						$("#loadDiv").jqmHide();
						var action = $('#routingAction').val();
						var fromSpnMonitor = $('#fromSpnMonitor').val();
						$("#spnIdHidden").val(spnId);
						if('edit' == action){
							$('#spnId').css('width','50%');
							$('#criteriaLevel').show();
							$('#viewCriteriaDiv').hide();
							if(fromSpnMonitor=='true'){
								$('#mainDiv').hide();
								$('#editNtwrk').show();
								$('#priorityRouting').hide();
								$('#cancelEdit').hide();
							}else{
								$('#editNtwrk').hide();
							}
							$('#perfRadio').show();
							$('#perfDisplay').hide();
							$("input:radio").attr("checked", false);
						}
						else if('view' == action){
							var status = $('#priorityStatus').val();
							if('ACTIVE' == status){
								$('#inactvBtn').hide();
								$('#actvBtn').show();
							}
							else if('INACTIVE' == status){
								$('#actvBtn').hide();
								$('#inactvBtn').show();
							}
							var level = $('#routingLevel').val();
							if('FIRM' == level){
								$('#viewFirm').show();
								$('#viewProv').hide();
								$('.provfirm').html('Firm');
							}
							else if('PROVIDER' == level){
								$('#viewProv').show();
								$('#viewFirm').hide();
								$('.provfirm').html('Provider');
							}
							$('#editNtwrk').hide();
							if(fromSpnMonitor=='true'){
								$("#editConfirm1").jqm({modal : true});
								$("#editConfirm1").jqmShow();
								$('.icon-lightbulb').css('margin-left', '20px');
								
							}else{
								$('#spnId').css('width','35%');
								jQuery('#viewCriteriaDiv').show();
								jQuery('#routingPriority').show();
							}
						}
						$('#successSPN').html("&nbsp;&nbsp;"+$('#spnId option:selected').text());
						$('#editedSPN').html($('#spnId option:selected').text());
						$('.editedSPN').html($('#spnId option:selected').text()+'<br><br>');
					});
				}
			});
			
			$("input[type='radio']").click(function() {
				var id = jQuery("input:radio:checked").attr('id');
				if(id == 'firm' || id == 'editFirm'){
					$('.provfirm').html('Firm');
				}
				else if(id == 'prov' || id == 'editProv'){
					$('.provfirm').html('Provider');
				}				
				$('#routingPriority').show();
			});
			
			$("#changeCriteria").click(function() {
				jQuery("#tierDiv").hide();
				jQuery("#performanceDiv").show();
				jQuery("#perfTab").addClass('active');
				jQuery("#tierTab").removeClass('active');
				jQuery("#tierTab").addClass('disabled');
				jQuery("#perfRadio").show();
				jQuery("#perfDisplay").hide();
				$("input:radio").attr("checked", false);
				var level = "";
				level = jQuery("#changeLevel").html().trim();
				if(level == 'Firm'){
					$("#firm").attr("checked", true);
					$('.provfirm').html('Firm');
				}
				else{
					$("#prov").attr("checked", true);
					$('.provfirm').html('Provider');
				}				
			});
			
			$("#editChangeCriteria").click(function() {
				jQuery("#tierDiv").hide();
				jQuery("#performanceDiv").show();
				jQuery("#perfTab").addClass('active');
				jQuery("#tierTab").removeClass('active');
				jQuery("#tierTab").addClass('disabled');
				jQuery("#editPerfRadio").show();
				jQuery("#editPerfDisplay").hide();
				$("input:radio").attr("checked", false);
				var level = "";
				level = jQuery("#editChangeLevel").html().trim();
				if(level == 'Firm'){
					$("#editFirm").attr("checked", true);
					$('.provfirm').html('Firm');
				}
				else{
					$("#editProv").attr("checked", true);
					$('.provfirm').html('Provider');
				}				
			});
			
			
			$('.icon-remove').click(function(){	
				jQuery('#successDiv').hide();
			});
			
			jQuery(document).click(function(e) {
				var id = jQuery(e.target).attr("id");
	        	if (id != 'successDiv' && id != 'successSPN') {
	        		jQuery('#successDiv').hide();
	        	}
	    	});
			
			$('body').on("click", "input[name='view-on-off']", function(){
				var spnId = $('#spnId').val();	
				var status = $('#priorityStatus').val();
				if($(this).is(':checked'))  {
					var name1 = $(this).attr('name');  
					var value = $(this).next('label').attr("for");
					if(value == 'view-on'){
					    value = 'ACTIVE';
					}else{
					    value = 'INACTIVE';
					}
					if(status !== value){
						$("#loadDiv").jqm({modal : true,overlay : 0});
						$("#loadDiv").jqmShow();
						//$('input[name=' + name1 + ']:radio').next('label').removeClass('checked');
						//$(this).next('label').addClass('checked');
						$('#close').load('spnReleaseTiers_buttonAddTierAjax.action?updateStatus=true&routingStatus='+value,
						    {'spnId': spnId }, function(){
								$("#loadDiv").jqmHide();
								if('ACTIVE' == value){
									$('#inactvBtn').hide();
									$('#actvBtn').show();
								}
								else if('INACTIVE' == value){
									$('#actvBtn').hide();
									$('#inactvBtn').show();
								}
								$("#priorityStatus").val(value);
								jQuery('#successDiv').show();   
						});
					}
				}
			});
			
			$('#actId').click(function() {
				var status = $('#priorityStatus').val();
				var value = 'INACTIVE';
				
					var spnId = $('#spnId').val();	
					$("#loadDiv").jqm({modal : true,overlay : 0});
					$("#loadDiv").jqmShow();
					$('#close').load('spnReleaseTiers_buttonAddTierAjax.action?updateStatus=true&routingStatus='+value,
						{'spnId': spnId }, function(){
							$("#loadDiv").jqmHide();
							$('#actvBtn').hide();
							$('#inactvBtn').show();
							$("#priorityStatus").val(value);
							jQuery('#successDiv').show();   
					});
				
			});
			
			
			$('#inactId').click(function() {
				var status = $('#priorityStatus').val();
				var value = 'ACTIVE';
					var spnId = $('#spnId').val();	
					$("#loadDiv").jqm({modal : true,overlay : 0});
					$("#loadDiv").jqmShow();
					$('#close').load('spnReleaseTiers_buttonAddTierAjax.action?updateStatus=true&routingStatus='+value,
						{'spnId': spnId }, function(){
							$("#loadDiv").jqmHide();
							$('#actvBtn').show();
							$('#inactvBtn').hide();
							$("#priorityStatus").val(value);
							jQuery('#successDiv').show();   
					});
				
			});

			$('#cancelEdit').click(function(){
				var spnId = $('#spnIdHidden').val();	
				$("#loadDiv").jqm({modal : true,overlay : 0});
				$("#loadDiv").jqmShow();
				$('#routingPriority').load('spnReleaseTiers_buttonAddTierAjax.action?edit=false&spnId='+spnId, function(){
					$("#loadDiv").jqmHide();
					jQuery('#mainDiv').show();
					$('#editNtwrk').hide();
					var status = $('#priorityStatus').val();
					if('ACTIVE' == status){
						$('#inactvBtn').hide();
						$('#actvBtn').show();
					}
					else if('INACTIVE' == status){
						$('#actvBtn').hide();
						$('#inactvBtn').show();
					}
					var level = $('#routingLevel').val();
					if('FIRM' == level){
						$('#viewFirm').show();
						$('.provfirm').html('Firm');
						$('#viewProv').hide();
					}
					else if('PROVIDER' == level){
						$('#viewProv').show();
						$('.provfirm').html('Provider');
						$('#viewFirm').hide();
					}
					$('#spnId').css('width','35%');
					jQuery('#viewCriteriaDiv').show();
					jQuery('#routingPriority').show();
					var fromSpnMonitor = $('#fromSpnMonitor').val();
					if(fromSpnMonitor=='true'){
						$('#spnId').hide();
						$('.editedSPN').show();
					}
				});
			});
			
			
			$('.icon-remove').css('margin-top','-10px');
			
			
		});
		
		function saveRoutingPriority(){
			var formValues = $('#routingPriorityForm').serializeArray();
			$("#savePopup").jqmHide();
			$("#loadDiv").jqm({modal : true,overlay : 0});
			$("#loadDiv").jqmShow();
			jQuery('#routingPriority').load('spnReleaseTiers_buttonAddTierAjax.action?saveInd=true', formValues, function(data) {		
				$("#loadDiv").jqmHide();
				$('#spnId').css('width','35%');
				var status = $('#priorityStatus').val();
				if('ACTIVE' == status){
					$('#inactvBtn').hide();
					$('#actvBtn').show();
				}
				else if('INACTIVE' == status){
					$('#actvBtn').hide();
					$('#inactvBtn').show();
				}
				var level = $('#routingLevel').val();
				if('FIRM' == level){
					$('#viewFirm').show();
					$('.provfirm').html('Firm');
					$('#viewProv').hide();
				}
				else if('PROVIDER' == level){
					$('#viewProv').show();
					$('.provfirm').html('Provider');
					$('#viewFirm').hide();
				}
				jQuery('#successDiv').show();
				jQuery('#criteriaLevel').hide();
				$('#editNtwrk').hide();
				$('#mainDiv').show();
				jQuery('#viewCriteriaDiv').show();
				jQuery('#routingPriority').show();
				var fromSpnMonitor = $('#fromSpnMonitor').val();
				if(fromSpnMonitor=='true'){
					$('#spnId').hide();
					$('.editedSPN').show();
				}
			});
		}	
		
		function editRoutingPriority(){
			var spnId = $('#spnIdHidden').val();
			if($("#editConfirm").css("display")=="block"){
				$("#editConfirm").jqmHide();
			}
			if($("#editConfirm1").css("display")=="block"){
				$("#editConfirm1").jqmHide();
			}
			$("#loadDiv").jqm({modal : true,overlay : 0});
			$("#loadDiv").jqmShow();
			var seconds = new Date().getTime() / 1000;
			$('#routingPriority').load('spnReleaseTiers_buttonAddTierAjax.action?edit=true&spnId='+spnId+'&randomnum='+seconds, function(){
				$("#loadDiv").jqmHide();
				jQuery('#routingPriority').show();
				jQuery('#mainDiv').hide();
				var status = $('#priorityStatus').val();
				if('ACTIVE' == status){
					$('#editStatus').html('Active');
				}
				else if('INACTIVE' == status){
					$('#editStatus').html('Inactive');
				}
				var level = $('#routingLevel').val();
				if('FIRM' == level){
					$('#editFirm').attr('checked', 'true');
					$('.provfirm').html('Firm');
				}
				else if('PROVIDER' == level){
					$('#editProv').attr('checked', 'true');
					$('.provfirm').html('Provider');
				}
				jQuery('#editNtwrk').show();
				jQuery("#editPerfRadio").show();
				jQuery("#editPerfDisplay").hide();
			});
		}		
		
	</script>
</head>

<body>

	<div class="content">
		<h2 id="page-role">Administrator Office</h2>
			
		<h2 id="page-title">Select Provider Network (SPN)</h2>

		<h3>Network Release Priorities</h3>

		<div id="tabs">
			<c:import url="/jsp/spn/common_tabs.jsp">
				<c:param name="tabName" value="spnRoutingTiers" />
			</c:import>		

			<div id="tab-content" class="clearfix" style="width: 97%;margin-left: 3px;">
				<h3>
					Routing Priorities
				</h3>
				<p>
					Set up routing priorities on an SPN to route your orders to higher performing SPN Providers in your network first.
				</p>
			</div>
			
		<form id="routingPriorityForm" onsubmit="return false" method="post">	
			
			<div style="width: 97%;margin-left: 3px;">
				<div id="successDiv">
					<img src="${staticContextPath}/images/common/success.png" />
					<b>Success!</b> Priority Routing settings have been configured for <b id="successSPN"></b>.
					<i class="icon-remove" style="margin-top: 10px;float: right; cursor: pointer;"></i>
				</div>
					
				<div class="clearfix networkDiv">
					<div id="mainDiv">			
						<b>Network :</b>					
						<br>					
						 <s:select id="spnId" name="spnId" headerKey="-1" cssStyle="width: 50%"
								headerValue="-Select One-" list="%{spnList}" listKey="value"
								theme="simple" listValue="label" value="%{spnHeader.spnId}" />	
						
						<div class="editedSPN"></div>
								
						<div id="criteriaLevel" class="criteriaDiv">
							<b>Calculate performance scores on:</b>	<br>
							<div id="perfRadio">
								<input id="firm" type="radio" name="model.routingDTO.spnHdr.criteriaLevel" value="FIRM"/>	
									<span id="firmSpan" style="border-bottom: 1px dashed black" title="Rank on entire firm performance(includes any providers that do not happen to be part of this SPN)">
										Firm</span>
								<span style="padding-left:20px;"><input id="prov" type="radio" name="model.routingDTO.spnHdr.criteriaLevel" value="PROVIDER"/></span>	
									<span id="provSpan" style="border-bottom: 1px dashed black" title="Rank on individual provider's performance">
										Provider</span>
							</div>
							<div id="perfDisplay" style="display:none">
								<span id="showLevel" title="" style="border-bottom: 1px dashed black"></span> 
								<span id="changeCriteria" class="changeLevel">Change to 
									<span id="changeLevel" title="" style="border-bottom: 1px dashed black"></span>
								</span>
							</div>
						</div>	
						
						<div id="viewCriteriaDiv" style="display: none;">
							<div style="margin-left:350px;margin-top:-50px;position:absolute;">
								<b>Calculate performance scores on:</b>	<br>
									<span id="viewFirm" style="border-bottom: 1px dashed black" title="Rank on entire firm performance(includes any providers that do not happen to be part of this SPN)">
										Firm</span>
									<span id="viewProv" style="border-bottom: 1px dashed black" title="Rank on individual provider's performance">
										Provider</span>
							</div>
							<div style="position:absolute;margin-left:580px;margin-top:-50px">
								<b>Priority Routing:</b><br>
								<div class="btn-pill" id="view_btn_div">
									<div id="actvBtn" style="display:none;">
										<input type="radio" value="ACTIVE" name="view-on-off" id="view-on">
										<label for="view-on" style="width:40px;margin-left: -5px;" class="checked">Active</label> 
										<input type="radio" value="INACTIVE" name="view-on-off" id="view-off">
										<label id="actId" for="view-off" style="width: 45px;margin-left: 55px;margin-top: -26px;height: 18px;">Inactive</label>		   
									</div>
									 <div id="inactvBtn"  style="display:none;">
										<input type="radio" value="ACTIVE" name="view-on-off" id="view-on">
										<label id="inactId" 	for="view-on" style="width:40px;margin-left: -5px;">Active</label> 
										<input type="radio" value="INACTIVE" name="view-on-off" id="view-off">
										<label for="view-off" style="width: 45px;margin-left: 55px;margin-top: -26px;height: 18px;" class="checked" >Inactive</label>						    
									</div>
								</div>
							</div>
							<input id="editPriority" type="button" value="Edit Routing Settings" class="button action"/>
							
						</div>
					</div>
					
					<div id="editNtwrk" style="display: none;">
						<div class="editNtwrkDiv">
							<b>Network :</b>					
							<br>	
							<div id="editedSPN"></div>
						</div>
						
						<div id="editCriteriaLevel" class="editCriteriaDiv">
							<b>Calculate performance scores on:</b>	<br>
								<div id="editPerfRadio">
									<input id="editFirm" type="radio" name="model.routingDTO.spnHdr.criteriaLevel" value="FIRM"/>	
										<span id="editFirmSpan" style="border-bottom: 1px dashed black" title="Rank on entire firm performance(includes any providers that do not happen to be part of this SPN)">
											Firm</span>
									<span style="padding-left:20px;"><input id="editProv" type="radio" name="model.routingDTO.spnHdr.criteriaLevel" value="PROVIDER" /></span>	
										<span id="editProvSpan" style="border-bottom: 1px dashed black" title="Rank on individual provider's performance">
											Provider</span>
								</div>	
								<div id="editPerfDisplay" style="display:none">
									<span id="editShowLevel" title="" style="border-bottom: 1px dashed black"></span> 
									<span id="editChangeCriteria" class="changeLevel">Change to 
										<span id="editChangeLevel" title="" style="border-bottom: 1px dashed black"></span>
									</span>
								</div>
						</div>
						
						<div style="margin-left:600px;margin-top:-45px;position:absolute;" id="priorityRouting">
							<b>Priority Routing:</b><br>
							<span id="editStatus"></span>
						</div>
						
						<b id="cancelEdit">Cancel</b>
						
					</div>
											
				</div>
	
			</div>
			
			
			<div id="routingPriority" class="routingDiv"></div>
		</form>
	
		</div>
	</div>
	<div id="loadDiv" style="display: none;" class="jqmWindow">
		<div style="padding: 10px;text-align: center;">
			<span>Please wait...</span>
			<div>
				<img src="${staticContextPath}/images/simple/searchloading.gif" />
			</div>
			<!--<div class="clearfix">
				<a class="cancel jqmClose left" href="#">Cancel</a>
			</div>-->
		</div>
	</div>
	
	<div id="calculateDiv" style="display: none;" class="jqmWindow">
		<div style="padding: 10px;text-align: center;">
			<span>Calculating scores, please wait...</span>
			<div>
				<img src="${staticContextPath}/images/simple/searchloading.gif" />
			</div>
			<!--<div class="clearfix">
				<a class="cancel jqmClose left" href="#">Cancel</a>
			</div>-->
		</div>
	</div>
	
	<div id="editConfirm1" class="jqmWindow" style="display: none;margin-left: -250px;width: 450px;" >
		<img src="${staticContextPath}/images/s_icons/lightbulb.png" class="icon-lightbulb"
			style="position: absolute; font-size: 20px; margin-left: 20px; margin-top: 10px;background-color: #FFFF33;">	
		<div style="margin-left: 10%; padding: 10px;">
			<b>Please Note!</b>
			You'll need to complete both steps &amp; click <b>Save</b><br>
			on the <b>Routing Tiers</b> tab for changes to take effect.<br><br>
		</div>
		<div class="footerDiv">
			<input type="button" id="editOK" value="OK" class="edit button action" onclick="editRoutingPriority()"
			 	style="text-transform: none;font-size: 12px;">
		</div>
	</div>
	
	<div id="close"></div>
	
	<input type="hidden" id="fromSpnMonitor" name="fromSpnMonitor" value="false"/>
</body>
