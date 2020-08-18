<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<head>

	
	<style type="text/css">
		.visualize .visualize-title {
    		color: #222222;
    		left: 150px;
    		font-size: 12px;
    	}
    	
    	.visualize {
		    background: none;
		    border: none;
		    margin: 60px 0 0 50px;
		}
		
		.visualize canvas {
		    background: none;
		    border: none;
		    margin-left: -60px;
		}
		
		.visualize ul.visualize-key {
		     background: none;
			 left: 215px;
			 padding: 0;
			 position: relative;
			 width: 50%;		   
		}
		
		.visualize ul.visualize-key .visualize-key-label {
		    color: #000000;
		    font-size: 10px;
		}
		
		th, td {
		    padding: 0 0 4px;
		}
		
		#tierTab a:after{
	 		border:none;
	 	}
	 	#tierTab a:before{
	 		border:none;
	 	}
	</style>

	<script type="text/javascript">
		jQuery(document).ready(function($) {
		
			jQuery("#perfTab").click( function(){
				jQuery("#tierDiv").hide();
				jQuery("#performanceDiv").show();
				jQuery("#perfTab").addClass('active');
				jQuery("#tierTab").removeClass('active');
				jQuery("#tierTab").addClass('disabled');
				jQuery("#perfRadio").show();
				jQuery("#perfDisplay").hide();
				jQuery("#editPerfRadio").show();
				jQuery("#editPerfDisplay").hide();
				visualize();
			});
			
			jQuery("#calculate").click( function(){
				var formValues = $('#routingPriorityForm').serializeArray();
				$("#calculateDiv").jqm({modal : true,overlay : 0});
				$("#calculateDiv").jqmShow();
	 	    	jQuery('#tierDiv').load('spnReleaseTiers_buttonAddTierAjax.action?calculate=true', formValues, function(data) {
	 	    		$("#calculateDiv").jqmHide();
	 	    		jQuery("#tierDiv").show();
					jQuery("#performanceDiv").hide();
					jQuery("#perfTab").removeClass('active');
					jQuery("#tierTab").removeClass('disabled');
					jQuery("#tierTab").addClass('active');
					jQuery("#perfRadio").hide();
					var checkedId = jQuery("input:radio:checked").prop('id');
					var checkedTitle = jQuery("#"+checkedId+"Span").prop('title');
					jQuery("#showLevel").html(jQuery("#"+checkedId+"Span").html());
					jQuery("#showLevel").prop('title',checkedTitle);
					var uncheckedId = jQuery("input:radio:not(:checked)").prop('id')
					var uncheckedTitle = jQuery("#"+uncheckedId+"Span").prop('title');
					jQuery("#changeLevel").html(jQuery("#"+uncheckedId+"Span").html());
					jQuery("#changeLevel").prop('title',uncheckedTitle);
					jQuery("#perfDisplay").show();
					if(checkedId == 'firm'){
						$('.provfirm').html('Firm');
					}
					else if(checkedId == 'prov'){
						$('.provfirm').html('Provider');
					}	
					
					jQuery("#editPerfRadio").hide();
					var editCheckedId = jQuery("input:radio:checked").prop('id');
					if(editCheckedId == 'editFirm'){
						$('.provfirm').html('Firm');
					}
					else if(editCheckedId == 'editProv'){
						$('.provfirm').html('Provider');
					}	
					var editCheckedTitle = jQuery("#"+editCheckedId+"Span").prop('title');
					jQuery("#editShowLevel").html(jQuery("#"+editCheckedId+"Span").html());
					jQuery("#editShowLevel").prop('title',editCheckedTitle);
					var editUncheckedId = "";
					if ($("#editFirm").is(":not(:checked)")) {
						editUncheckedId = 'editFirm';
					}
					else if ($("#editProv").is(":not(:checked)")) {
						editUncheckedId = 'editProv';
					}
					var editUncheckedTitle = jQuery("#"+editUncheckedId+"Span").prop('title');
					jQuery("#editChangeLevel").html(jQuery("#"+editUncheckedId+"Span").html());
					jQuery("#editChangeLevel").prop('title',editUncheckedTitle);
					jQuery("#editPerfDisplay").show();
					<c:choose>
					<c:when test="${model.routingDTO.spnHdr.marketPlaceOverFlow == 'true'}">
						$("#overflowSelect option[value='true']").prop("selected","selected");
						$('#overflowTime').show();
						$('#overflowDiv').show();
					</c:when>
					<c:otherwise>
						$("#overflowSelect option[value='false']").prop("selected","selected");
						$("#overflowDiv").hide();
					</c:otherwise>
					</c:choose>
	 	    	});				
			});	
			
			<c:if test='${fn:length(model.routingDTO.performanceCriteria) > 0 }'>
				$('#dataScope').show();
				$('#timeFrameDiv').show();
				$("#calculateDis").hide();
				$("#calculate").show();
				$("#warningDiv").css('margin-left','60%');
				$("#warningDiv").html('Calculate anytime the Performance Score criteria changes.');
				$('#editInd').val('true');
				
				<c:forEach items="${model.routingDTO.criteriaDTO}" var="criteria" varStatus="status">
					<c:forEach items="${model.routingDTO.performanceCriteria}" var="spnCriteria">
						<c:if test="${criteria.criteriaId == spnCriteria.criteriaId}">
							$("#visualize tbody").append("<tr id='vis${status.count}'><th>${criteria.criteriaName}</th><td>1</td></tr>");	
						</c:if>
					</c:forEach>
				</c:forEach>
				
				
				$("#perfChartDiv").show();	
				setTimeout(function(){
					$('#perfTab').trigger('click');
				 },400);
				
			</c:if>
			
		});
		
		//to show the description for criteria
		function showDescr(count){
			jQuery('#right'+count).hide();
			jQuery('#down'+count).show();
			jQuery('#descr'+count).show();			
		}
		
		function hideDescr(count){
			jQuery('#down'+count).hide();
			jQuery('#right'+count).show();
			jQuery('#descr'+count).hide();	
		}
		
		//to display save button
		function onInput(){
			var count = 3;
			var ind = 0;
			for(var i=1; i<=count; i++){
				if(($('#member'+i).prop('value')>0 && $('#time'+i).prop('value')<=0) || 
					($('#time'+i).prop('value')>0 && $('#member'+i).prop('value')<=0)){
					ind = 1;
					break;
				}
			}	
			//if ind = 0, check overflow also 
			if(1 == ind){
				jQuery('#savePriority').hide();
				jQuery('#saveDis').show();
			}
			else{
				for(var i=1; i<=count; i++){
					if(($('#member'+i).prop('value')== 0 && $('#time'+i).prop('value')==0)){
						ind = ind + 1;
					}
				}	
				if(3 == ind){
					jQuery('#savePriority').hide();
					jQuery('#saveDis').show();
				}
				else{
					if(jQuery("#overflowSelect").val() == 'true'){
						if(jQuery('#time4').prop('value')>0){
							jQuery('#savePriority').show();
							jQuery('#saveDis').hide();
						}
						else{
							jQuery('#savePriority').hide();
							jQuery('#saveDis').show();
						}				
					}
					else{
						jQuery('#savePriority').show();
						jQuery('#saveDis').hide();
					}	
				}
							
			}
		}
		
		//to show scope drop down
		function showScope(count){
			var counter = $("#criteriaCount").val();
			var ind = 0;
			var checkedInd = $("input:checkbox:checked").length;
			var indicator = 0;
			for(var i=1; i<=counter; i++)
	 	    {
	 	    	if($("#criteria"+i).prop("checked") == true){
	 	    		$("#dataScope").show();
	 	    		$("#perfChartDiv").show();
	 	    		ind = 1;
	 	    	}
	 	    	if($("#criteria"+i).prop("checked") == true){
	 	    		if($("#scope"+i).val() != '-1'){
	 	    			indicator = indicator + 1;
	 	    		}
	 	    	}
	 	    }
	 	    if(0 == ind){
	 	    	$("#timeFrameDiv option[value='-1']").prop("selected","selected");
	 	    	$("#timeFrameDiv").hide();
	 	    	$("#calculateDis").show();
				$("#calculate").hide();
	 	    	$("#warningDiv").css('margin-left','71%');
	 	    	$("#warningDiv").html('Select at least one criteria and choose its data scope');
				$("#dataScope").hide();
				$("#perfChartDiv").hide();
	 	    }
	 	    if(0 != checkedInd && checkedInd == indicator){
	 	    	$("#timeFrameDiv").show();
	 	    	$("#warningDiv").css('margin-left','67%');
	 	    	$("#warningDiv").html('Choose a time frame for calculating the performance criteria');
	 	    }
	 	    else{
	 	    	$("#timeFrameDiv option[value='-1']").prop("selected","selected");
	 	    	$("#timeFrameDiv").hide();
	 	    	$("#calculateDis").show();
				$("#calculate").hide();
	 	    	$("#warningDiv").css('margin-left','71%');
	 	    	$("#warningDiv").html('Select at least one criteria and choose its data scope');
	 	    }
			if($("#criteria"+count).prop("checked") == true){
				$("#scope"+count).show();
				var name = $("#critName"+count).text();
	 	    	$("#visualize tbody").append("<tr id='vis"+count+"'><th>"+name+"</th><td>1</td></tr>");
			}
			else{
				$("#scope"+count+" option[value='-1']").prop("selected","selected");
				$("#scope"+count).hide();
				var name = $("#critName"+count).text();
	 	    	$("#vis"+count).remove();
			}
			//for visualize
			if(0 != checkedInd){
				visualize();
			}
		}
		
		//to display the timeframe drop down
		function displayTimeframe(){	
			var counter = $("#criteriaCount").val();
			//var counter = 8;
			var checkedInd = $("input:checkbox:checked").length;
			var ind = 0;
			for(var i=1; i<=counter; i++)
	 	    {
	 	    	if($("#criteria"+i).prop("checked") == true){
	 	    		if($("#scope"+i).val() != '-1'){
	 	    			ind = ind + 1;
	 	    		}
	 	    		
	 	    	}
	 	    }
	 	    if(checkedInd == ind){
	 	    	$("#timeFrameDiv").show();
	 	    	$("#warningDiv").css('margin-left','67%');
	 	    	$("#warningDiv").html('Choose a time frame for calculating the performance criteria');
	 	    }
	 	    else{
	 	    	$("#timeFrameDiv option[value='-1']").prop("selected","selected");
	 	    	$("#timeFrameDiv").hide();
	 	    	$("#calculateDis").show();
				$("#calculate").hide();
	 	    	$("#warningDiv").css('margin-left','71%');
	 	    	$("#warningDiv").html('Select at least one criteria and choose its data scope');
	 	    }
		}
		
		//to display calculate scores button
		function displayCalculate(){
			if($("#timeframe").val() != '-1'){
				$("#calculateDis").hide();
				$("#calculate").show();
				$("#warningDiv").css('margin-left','60%');
				$("#warningDiv").html('Calculate anytime the Performance Score criteria changes.');
			}
			else{
				$("#calculateDis").show();
				$("#calculate").hide();
				$("#warningDiv").css('margin-left','67%');
	 	    	$("#warningDiv").html('Choose a time frame for calculating the performance criteria');
			}
		}
		
		//for visualise
		function visualize(){
			$('.visualize').remove();	
			$("#visualize")
				.visualize({type: 'pie', pieMargin: '10', height: '200px', width: '400px', 
							colors: ['#81DAF5','#58D3F7','#2ECCFA','#00BFFF','#01A9DB','#0489B1','#086A87','#0B4C5F','#0B3861']})
				.trigger('visualizeRefresh');	
			
			$('.visualize-label').css('margin-left', '-80px');
			if(1 == $("span.visualize-label").length){
				$('.visualize-label').css('margin-bottom', '50px');
			}
			if($('.visualize-label').css('margin-right') != 'undefined'){ 			
				$('.visualize-label').css('margin-right', '50px');	
			}	
		}
	
	</script>	
</head>

<body>

	<div class="tab-wrap">
	    <div id="perfTab" class="tab active">
	        <a href="javascript:void(0)">Performance Scores</a>
	    </div>
	    <div id="tierTab" class="tab disabled">
	        <a href="javascript:void(0)">Routing Tiers</a>
	    </div>
	</div>
	<br><br><br>
	
		<div id="performanceDiv">
			<div style="padding: 10px;">
			
				Choose one or more criteria to utilize in determining a <span class="provfirm"></span>'s Performance Score. 
				Each criteria will be weighed equally, so the more you choose the less impactful each criteria will be. 
				The Performance Score is an average of the rates of all selected criteria.<br><br>
				
				<b>Criteria<span style="color:#8A1F11;">*</span></b>
				<b id="dataScope" style="display: none;"
					title="Calculate these rates by looking only at the orders you've routed to this provider, or by looking at all orders routed to this provider through the ServiceLive marketplace. Statistically, a larger sample size will yield more accurate averages." >
						Data scope<span style="color:#8A1F11;">*</span></b>
				
				<hr id="frsthr" style="width: 60%;margin-top: 5px;">
				
				<div>
					<div style="float: left;">
						<table cellpadding="0" cellspacing="0" border="0" style="table-layout: fixed; width: 50%">
							<c:forEach items="${model.routingDTO.criteriaDTO}" var="criteria" varStatus="status">
								<c:set var='checked' value='' ></c:set>
								<c:set var='checkedInd' value='' ></c:set>
								<c:set var='scope' value='' ></c:set>
									<c:forEach items='${model.routingDTO.performanceCriteria}' var='spnCriteria' >
										<c:if test='${criteria.criteriaId == spnCriteria.criteriaId}'>
											<c:set var='checked' value=' checked ' ></c:set>
											<c:set var='checkedInd' value='true' ></c:set>
											<c:set var='scope' value='${spnCriteria.criteriaScope}' ></c:set>
										</c:if>
									</c:forEach>
								<tr>
									<td style="word-wrap: break-word;width: 60%">
										<input id="criteria${status.count}" type="checkbox" name="model.routingDTO.performanceCriteria[${status.count}].criteriaId" 
											value="${criteria.criteriaId }" style="cursor: pointer;" onclick="showScope('${status.count}');" ${checked}/>	
										<span id="critName${status.count}">${criteria.criteriaName }</span> 
											
										<span style="color: #0066FF;font-size: 10px;padding-left:5px;">
											<span id="right${status.count}" style="cursor: pointer;" onclick="showDescr('${status.count}');">
												details 
												<i style="font-size: 14px;" class="icon-caret-right"></i>
											</span>
											<span id="down${status.count}" style="cursor: pointer;display:none;" onclick="hideDescr('${status.count}');">
												details 
												<i style="font-size: 14px;" class="icon-caret-down"></i>
											</span>
										</span>	
									</td>
									<td>
										<div style="position:absolute;z-index:999;margin-top:-20px;">
										<select id="scope${status.count}" name="model.routingDTO.performanceCriteria[${status.count}].criteriaScope"
											style="width: 100%;<c:if test='${checkedInd != true}'>display: none;</c:if>" onchange="displayTimeframe();">
												<option value="-1">-Select-</option>
												<option value="SINGLE" <c:if test="${'SINGLE' == scope}">selected="selected"</c:if>>Only my orders</option>									
												<option value="ALL" <c:if test="${'ALL' == scope}">selected="selected"</c:if>>All orders from any buyer</option>
										</select>
										</div>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<div id="descr${status.count}" style="color: #777777;padding-left: 20px;font-size: 11px;display: none;width:50%">
											${criteria.description }
										</div>
									</td>
								</tr>
							</c:forEach>
						</table>
						<br>
					</div>
								
					<div id="perfChartDiv" class="perfChart">
						<!-- visualise -->
						<table id="visualize" style="display:none;">
							<caption><b>Performance Score Calculation</b></caption>
							<thead>
								<tr><td></td><th></th></tr>
							</thead>
							<tbody></tbody>	
						</table>				
						<!-- visualise -->
					</div>
				</div>
				
				<input type="hidden" id="criteriaCount" name="model.routingDTO.criteriaCount" value="${model.routingDTO.criteriaCount}"/>
				<input type="hidden" id="routingAction" value="edit"/>
				<input type="hidden" id="priorityStatus" name="model.routingDTO.spnHdr.routingPriorityStatus" value="${model.routingDTO.spnHdr.routingPriorityStatus}"/>
				<input type="hidden" id="spnIdHidden" name="model.routingDTO.spnHdr.spnId" value="${model.routingDTO.spnHdr.spnId}"/>
				<input type="hidden" id="routingLevel" value="${model.routingDTO.spnHdr.criteriaLevel}"/>
				<input type="hidden" id="editInd" value="false"/>

				<hr style="width: 60%;height: 1px" />
				<span style="color: #777777;margin-left: 22%;font-size: 11px;margin-top: -10px;position: absolute;">
					Performance data last updated : <fmt:formatDate value="${model.routingDTO.modifiedDate}" pattern="hh:mm a MM/dd/yy" />
				</span>
				
				<div style="height:50px;">
					<div id="timeFrameDiv" style="display: none;">
						<b>Time Frame<span style="color:#8A1F11;">*</span></b><br>
						<select id="timeframe" name="model.routingDTO.spnHdr.criteriaTimeframe"
							style="width:20%" onchange="displayCalculate();">
							<option value="-1">-Select-</option>
							<option value="90DAYS" <c:if test="${'90DAYS' == model.routingDTO.spnHdr.criteriaTimeframe}">selected="selected"</c:if>>Last 90 days</option>
							<option value="LIFETIME" <c:if test="${'LIFETIME' == model.routingDTO.spnHdr.criteriaTimeframe}">selected="selected"</c:if>>Lifetime</option>
						</select>
					</div>
				</div>
				<br>
				
			</div>
			
			<div style="height: 60px;background-color: #F2F2F2;padding: 10px;">
				<b style="color: #8A1F11;">Required*</b>
				
				<input id="calculateDis" type="button" value="Calculate Scores" class=" calculate button action"
					style="text-transform: none;font-size:13px;background: none;background-color: #F5DA81;color: #848484;cursor: auto; border: none;"/>
				
				<input id="calculate" type="button" value="Calculate Scores" class="calculate button action"
					style="display: none;text-transform: none;font-size:13px;"/>
					
				<div id="warningDiv" style="color: #777777;font-size: 11px;margin-left: 71%">
					Select at least one criteria and choose its data scope
				</div>
				
			</div>
			
		</div>
		
		<div id="tierDiv" style="display:none;"></div>
	
	<div id="overflowPopup" class="jqmWindow" style="display: none;margin-left: -250px;width: 450px;" >
	
		<img src="${staticContextPath}/images/icons/warning_sign.png" class="icon-warning-sign"
			style="position: absolute; font-size: 20px; margin-left: 20px; margin-top: 10px;">	
		<div style="margin-left: 10%; padding: 10px;">
			<b>Are you sure?</b>
			 Reminder that if no provider within your network <br>
			 accepts the order within the allotted time, the order will be <br>
			 routed to the general marketplace.
		</div>
		
		<div class="footerDiv">

			<input type="button" id="overflowOK" value="OK" class="edit button action" 
				style="text-transform: none;font-size: 12px;margin-left: 55%;">
				
			<input type="button" id="overflowCancel" value="Cancel" class="edit button action" 
				style="text-transform: none;font-size: 12px;margin-left: 75%;">
		</div>
		
	</div>
	
	<div id="savePopup" class="jqmWindow" style="display: none;margin-left: -250px;width: 450px;cursor: wait;" >
	
		<div style="padding: 20px;font-size: 13px;">
			Do you want Priority Routing to be active immediately? Click OK to <br>
			confirm, or select <b>Inactive</b>. You can always change this setting <br>later.<br>
			
			<div class="btn-pill" id="btn_div" style="margin-left:33%;">
				<input type="radio" value="ACTIVE" name="on-off" id="on">
				<label for="on" style="width:40px" class="checked">Active</label> 
				<input type="radio" value="INACTIVE" name="on-off" id="off">
				<label for="off" style="width: 45px;margin-left: 60px;margin-top: -28px;height: 20px;">Inactive</label>
			</div>
			
		</div>
			<div style="text-align: left;padding-left:20px;font-size:13px;"><b>Note:</b>&nbsp;This change will be effective momentarily.</div>
		
		<div class="footerDiv">
				
			<input type="button" id="savePriorityBtn" value="OK" class="button action" onclick="saveRoutingPriority();"
				style="height: 30px;text-transform: none;font-size: 12px;width: 80px;margin-top: 2px;position: absolute;margin-left: 75%;">
		</div>
		
	</div>
	
</body>
