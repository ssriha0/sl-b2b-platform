<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<head>
	<script type="text/javascript">
		jQuery(document).ready(function($) {
					    
		    jQuery("#prev").click( function(){
				jQuery("#tierDiv").hide();
				jQuery("#performanceDiv").show();
				jQuery("#perfTab").addClass('active');
				jQuery("#tierTab").removeClass('active');
				jQuery("#tierTab").addClass('disabled');
				jQuery("#perfRadio").show();
				jQuery("#perfDisplay").hide();
				jQuery("#editPerfRadio").show();
				jQuery("#editPerfDisplay").hide();
			});
			
			jQuery("#viewCoverage").click( function(){
				if('View Coverage' == jQuery("#viewCoverage").prop('value')){
					jQuery("#viewCoverage").prop('value','Hide Coverage');
				}
				else{
					jQuery("#viewCoverage").prop('value','View Coverage');
				}
				jQuery("#coverageDiv").toggle();
				var coverageInd = jQuery("#coverageInd").val();
				if(0 == coverageInd){
					$('#coverageDiv').html('<center><img src="${staticContextPath}/images/loading.gif" width="500px" height="200px"/></center>');
					jQuery("#coverageDiv").load("spnReleaseTiers_buttonAddTierAjax.action?coverage=true",function(){
						jQuery("#coverageInd").val("1");
						jQuery('.dataTables_length').hide();	
						jQuery('#tabPHdr2').trigger("click");
						jQuery('#tabPHdr2').trigger("click");
						jQuery('#tabFHdr2').trigger("click");
						jQuery('#tabFHdr2').trigger("click");
					});
				}
				else{
					jQuery('.dataTables_length').hide();	
					jQuery('#tabPHdr2').trigger("click");
					jQuery('#tabPHdr2').trigger("click");
					jQuery('#tabFHdr2').trigger("click");
					jQuery('#tabFHdr2').trigger("click");
				}				
			});	
			
			jQuery("#overflowText").click( function(){
				jQuery("#overflowDiv").show();
			});		
				
			jQuery("#overflowInfoLink").hover(function(){
				jQuery("#overflowInfo").show();
			});		
			
			jQuery(".icon-remove").click(function(){
				jQuery("#overflowInfo").hide();
			});		
			
			//to show market overflow confirmation popup
			jQuery("#overflowSelect").change(function(){
				if(jQuery("#overflowSelect").val() == 'true'){
					jQuery("#overflowInfo").hide();
					$("#coverageFilters").css('z-index','0');
					$("#overflowPopup").jqm({modal : true});
					$("#overflowPopup").jqmShow();
				}
				else{
					//here check whether save should be enabled 
					var count = 3;
					var ind1 = 0;
					var ind2 = 0;
					for(var i=1; i<=count; i++){
						if($('#member'+i).prop('value')>0){
						 	ind1 = ind1 + 1;
						}
						if($('#time'+i).prop('value')>0){
						 	ind2 = ind2 + 1;
						}
					}
					if(ind1 == ind2){
						jQuery('#saveDis').hide();
						jQuery('#savePriority').show();
					}
					else{
						jQuery('#savePriority').hide();
						jQuery('#saveDis').show();
					}
					$('#time4').val(0);
					$("#select4 option[value='Minutes']").prop("selected","selected");
					$("#overflowTime").hide();
				}
			});		
			
			jQuery("#overflowOK").click(function(){
				$("#overflowPopup").jqmHide();
				$("#overflowPopup").css('z-index','999999');
				$("#overflowTime").show();
				jQuery('#savePriority').hide();
				jQuery('#saveDis').show();
			});
			
			jQuery("#overflowCancel").click(function(){
				$("#overflowSelect option[value='false']").prop("selected","selected");
				$("#overflowPopup").jqmHide();
				$("#coverageFilters").css('z-index','999999');
				//here check whether save should be enabled 
				var count = 3;
				var ind1 = 0;
				var ind2 = 0;
				for(var i=1; i<=count; i++){
					if($('#member'+i).prop('value')>0){
					 	ind1 = ind1 + 1;
					}
					if($('#time'+i).prop('value')>0){
					 	ind2 = ind2 + 1;
					}
				}
				if(ind1 == ind2){
					jQuery('#saveDis').hide();
					jQuery('#savePriority').show();
				}
				else{
					jQuery('#savePriority').hide();
					jQuery('#saveDis').show();
				}
				$('#time4').val(0);
				$("#select4 option[value='Minutes']").prop("selected","selected");
			});
			
			//to show save confirmation pop up
			jQuery("#savePriority").click(function(){
				jQuery("#overflowInfo").hide();
				jQuery("#saveInd").val(true);
				$("#coverageFilters").css('z-index','0');
				$("#savePopup").jqm({modal : true});
				$("#savePopup").jqmShow();
				$("#priorityStatus").val("ACTIVE");
			});
			
			//restrict non-numeric inputs
			$('#member1').keypress(function (e){
	  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   				return false;
	 			}
	 		});
	 		
	 		$('#member2').keypress(function (e){
	  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   				return false;
	 			}
	 		});
	 		
	 		$('#member3').keypress(function (e){
	  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   				return false;
	 			}
	 		});
	 		
	 		$('#time1').keypress(function (e){
	  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   				return false;
	 			}
	 		});
	 		
	 		$('#time2').keypress(function (e){
	  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   				return false;
	 			}
	 		});
	 		
	 		$('#time3').keypress(function (e){
	  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   				return false;
	 			}
	 		});
	 		
			$('#time4').keypress(function (e){
	  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   				return false;
	 			}
	 		});
	 		
			$('body').on("click", "input[name='on-off']", function(){
				var id = $(this).prop("name");
				if($(this).is(':checked'))  {
						var name1 = $(this).prop('name');  
						$('input[name=' + name1 + ']:radio').next('label').removeClass('checked');
					    $(this).next('label').addClass('checked');
					    var value = $(this).next('label').prop("for");
					    if(value == 'on'){
					    	value = 'ACTIVE';
					    }else{
					    	value = 'INACTIVE';
					    }
					    $("#priorityStatus").val(value);   
				}
			});
			
			<c:if test='${fn:length(model.routingDTO.spnTiers) > 0 }'>
				$('#saveDis').hide();
				$('#savePriority').show();
			</c:if>
		});
		
		
		
	</script>
</head>

<body>
	<div style="padding: 10px;">
	
		<c:choose>
			<c:when test="${null == model.routingDTO.spnTiers || 0 == fn:length(model.routingDTO.spnTiers)}">
				At the time of service order routing, other than existing functionality ( consider zip code, skills, miles etc) 
				system will consider the Performance Score and Routing tiers setup, orders will be routed to the highest ranking <span class="provfirm"></span>s as defined below. 
				If everyone in one tier rejects the order, it will immediately be routed to the next tier.
			</c:when>
			<c:otherwise>
				Using the Performance Score, orders will be routed to the highest ranking <span class="provfirm"></span>s as defined below. 
				If everyone in one tier rejects the order, it will immediately be routed to the next tier.
			</c:otherwise>
		</c:choose>
		
		<br><br>
		<hr>
		
		<b style="color: #0099FF;">1.</b> First, route orders to the top &nbsp;
		<input type="text" id="member1" name="model.routingDTO.spnTiers[1].noOfMembers" value="${model.routingDTO.spnTiers[0].noOfMembers == null?'0':model.routingDTO.spnTiers[0].noOfMembers}" 
			style="width: 30px;font-size: 11px;" onfocus="onInput();" onclick="onInput();" onblur="resetValue(this);onInput();" onkeyup="onInput();" onkeydown="onInput();" maxlength="5"/> &nbsp;
		<span class="provfirm"></span>s in the service area and allow &nbsp;
		<input type="text" id="time1" name="model.routingDTO.spnTiers[1].time" value="${model.routingDTO.spnTiers[0].time == null?'0':model.routingDTO.spnTiers[0].time}" style="width: 30px;font-size: 11px;" 
			onfocus="onInput();" onclick="onInput();" onblur="resetValue(this);onInput();" onkeyup="onInput();" onkeydown="onInput();" maxlength="5"/> 
		<select id="select1" name="model.routingDTO.spnTiers[1].unit" style="width: 75px;font-size: 11px;padding: 0px">
			<option value="Days" <c:if test="${model.routingDTO.spnTiers[0].unit == 'Days'}">selected="selected"</c:if>>
				Days</option>
			<option value="Hours" <c:if test="${model.routingDTO.spnTiers[0].unit == 'Hours'}">selected="selected"</c:if>>
				Hours</option>
			<option value="Minutes" <c:if test="${model.routingDTO.spnTiers[0].unit == 'Minutes' || model.routingDTO.spnTiers[0].unit == null}">selected="selected"</c:if>>
				Minutes</option>
		</select>
		<span id="span1">before routing to the next group.</span>
		
		<br><br>
		<hr>
		
		<b style="color: #0099FF;">2.</b> If no one accepts, route to the next &nbsp;
		<input type="text" id="member2" name="model.routingDTO.spnTiers[2].noOfMembers" value="${model.routingDTO.spnTiers[1].noOfMembers == null?'0':model.routingDTO.spnTiers[1].noOfMembers}" style="width: 30px;font-size: 11px;" 
			onfocus="onInput();" onclick="onInput();" onblur="resetValue(this);onInput();" onkeyup="onInput();" onkeydown="onInput();" maxlength="5"/>  &nbsp;
		highest rated <span class="provfirm"></span>s available, and allow &nbsp;
		<input type="text" id="time2" name="model.routingDTO.spnTiers[2].time" value="${model.routingDTO.spnTiers[1].time == null?'0':model.routingDTO.spnTiers[1].time}" style="width: 30px;font-size: 11px;" 
			onfocus="onInput();" onclick="onInput();" onblur="resetValue(this);onInput();" onkeyup="onInput();" onkeydown="onInput();" maxlength="5"/> 
		<select id="select2" name="model.routingDTO.spnTiers[2].unit" style="width: 75px;font-size: 11px;padding: 0px">
			<option value="Days" <c:if test="${model.routingDTO.spnTiers[1].unit == 'Days'}">selected="selected"</c:if>>
				Days</option>
			<option value="Hours" <c:if test="${model.routingDTO.spnTiers[1].unit == 'Hours'}">selected="selected"</c:if>>
				Hours</option>
			<option value="Minutes" <c:if test="${model.routingDTO.spnTiers[1].unit == 'Minutes' || model.routingDTO.spnTiers[1].unit == null}">selected="selected"</c:if>>
				Minutes</option>
		</select>
		<span id="span2">before routing further.</span>
		
		<br><br>				
		<hr>
		
		<b style="color: #0099FF;">3.</b> Then if none still have accepted, route to the next &nbsp;
		<input type="text" id="member3" name="model.routingDTO.spnTiers[3].noOfMembers" value="${model.routingDTO.spnTiers[2].noOfMembers == null?'0':model.routingDTO.spnTiers[2].noOfMembers}" style="width: 30px;font-size: 11px;" 
			onfocus="onInput();" onclick="onInput();" onblur="resetValue(this);onInput();" onkeyup="onInput();" onkeydown="onInput();" maxlength="5"/> &nbsp;
		rated <span class="provfirm"></span>s available and wait &nbsp;
		<input type="text" id="time3" name="model.routingDTO.spnTiers[3].time" value="${model.routingDTO.spnTiers[2].time == null?'0':model.routingDTO.spnTiers[2].time}" style="width: 30px;font-size: 11px;" 
			onfocus="onInput();" onclick="onInput();" onblur="resetValue(this);onInput();" onkeyup="onInput();" onkeydown="onInput();" maxlength="5"/> 
		<select id="select3" name="model.routingDTO.spnTiers[3].unit" style="width: 75px;font-size: 11px;padding: 0px">
			<option value="Days" <c:if test="${model.routingDTO.spnTiers[2].unit == 'Days'}">selected="selected"</c:if>>
				Days</option>
			<option value="Hours" <c:if test="${model.routingDTO.spnTiers[2].unit == 'Hours'}">selected="selected"</c:if>>
				Hours</option>
			<option value="Minutes" <c:if test="${model.routingDTO.spnTiers[2].unit == 'Minutes' || model.routingDTO.spnTiers[2].unit == null}">selected="selected"</c:if>>
				Minutes</option>
		</select>
		<span id="span3">before routing further.</span>
		
		<br><br>				
		<hr>
		
		<b style="color: #0099FF;">4.</b> Finally, in the unlikely event that the order has not been accepted, route to all remaining available SPN members.&nbsp;
		<a id="overflowText" style="color: #0099FF;font-size: 11px;cursor: pointer;">What if no one accepts?</a> 
		<br><br>

		<div id="overflowDiv" style="display: none;padding: 10px">
		
			<b>Marketplace Overflow</b><br>
			<select id="overflowSelect" name="model.routingDTO.spnHdr.marketPlaceOverFlow" style="width: 60px;">
				<option value="false">No</option>
				<option value="true">Yes</option>
			</select>
			
			<span id="overflowInfoLink" style="font-size: 11px;cursor: pointer;">
				<img src="${staticContextPath}/images/icons/info.gif" width="16"
							height="16" border="0" style="vertical-align: absmiddle;cursor: pointer;" />
				&nbsp;What is Marketplace Overflow?
			</span>
			
			<div id="overflowInfo" class="overflowInfo">
				<div class="overflow">
					<b>Marketplace Overflow</b><i class="icon-remove" style="float: right; cursor: pointer;"></i><br>
				</div>
				<i style="margin-left:-20px;font-size:55px;position:absolute;color:#CCCCCC" class="icon-caret-left"></i>
				<div style="font-size: 11px; padding: 5px">
					If no SPN member accepts (or if all reject) an order, you can opt to route orders to the entire marketplace. 
					This is helpful when your network has lower market coverage.
				</div>
			</div>
			
			<div id="overflowTime" style="display:none;">
				How long before routing to Marketplace &nbsp;
				<input type="text" id="time4" name="model.routingDTO.spnTiers[4].time" value="${model.routingDTO.spnTiers[3].time == null?'0':model.routingDTO.spnTiers[3].time}" style="width: 30px;font-size: 11px;" 
					onfocus="onInput();" onclick="onInput();" onblur="resetValue(this);onInput();" onkeyup="onInput();" onkeydown="onInput();" maxlength="5"/> 
				<select id="select4" name="model.routingDTO.spnTiers[4].unit" style="width: 75px;font-size: 11px;padding: 0px">
					<option value="Days" <c:if test="${model.routingDTO.spnTiers[3].unit == 'Days'}">selected="selected"</c:if>>
						Days</option>
					<option value="Hours" <c:if test="${model.routingDTO.spnTiers[3].unit == 'Hours'}">selected="selected"</c:if>>
						Hours</option>
					<option value="Minutes" <c:if test="${model.routingDTO.spnTiers[3].unit == 'Minutes' || model.routingDTO.spnTiers[3].unit == null}">selected="selected"</c:if>>
						Minutes</option>
				</select>
			</div>
				
		</div>
		<hr>
		
		<div style="height: 50px;"></div>
				
		<input id="viewCoverage" type="button" value="View Coverage" class="button action"
			style="height: 30px;text-transform: none;font-size: 13px;width: 130px;margin-left: 85%;"/><br>
		
		<div  id="coverageDiv" style="display: none;" ></div>
		
	</div>
	
	<div style="height: 60px;background-color: #F2F2F2;padding: 10px;">
		<br>
		<input id="prev" type="button" value="&lt;&lt;Prev" class="button action"
			style="height: 30px;text-transform: none;font-size: 13px;width: 100px;"/>
					
		<input id="savePriority" type="button" value="SAVE" class="save button action"
			style="font-size: 13px;;display: none;margin-left: 800px;margin-top: -30px;position: absolute;"/>
						
		<input id="saveDis" type="button" value="SAVE" class="save button action"
			style="font-size: 13px;background: none;background-color: #F5DA81;color: #848484;cursor: auto; border: none;margin-top: -25px;"/>
						
	</div>	
	
	<input type="hidden" id="stateLabel" value="All States"></input>
	<input type="hidden" id="marketLabel" value="All Markets"></input>
	<input type="hidden" id="applyFilterInd" value="0"></input>
	<input type="hidden" id="resetInd" value="false"></input>
	<input type="hidden" id="marketInd" value="0"></input>
	<input type="hidden" id="coverageInd" value='0'></input>

</body>