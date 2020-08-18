<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>	
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<html>
<head>
	<script type="text/javascript">
	$(document).ready(function($){
		jQuery.noConflict();

		$("input[type='radio']").click(function() {
			var id = $(this).attr("name");
			var ids=id.substr(0, 3);
			if(ids=='son'){
				
				var ruleid = id.replace("son-off-",""); 
				var value = $(this).val();
				
				if(value === 'false'){
					
					$("#"+ruleid+"_smodel").val(false);
					
				}else{
					$("#"+ruleid+"_smodel").val(true);
					
				}}
				
			
	        if (ids=='on-'){
			var ruleid = id.replace("on-off-",""); 
			if($(this).is(':checked'))  {
					var name1 = $(this).attr('name');        
					$('input[name=' + name1 + ']:radio').next('label').removeClass('checked');
				    $(this).next('label').addClass('checked');
			}
			var value = $(this).val();
			
			if(value === 'Off'){
				$("#"+ruleid+"reason").val("Please enter the reason so that we can help you.");
				$(".carTable td:nth-child(even)").css({"background" :"#EEEEEE"});
				$(".carTable td:nth-child(odd)").css({"background" :"#FFFFFF"});
				$("#"+ruleid+"_btn_div").parent('td').css({"background" :"none"});
				$("#"+ruleid+"_btn_div").parent('td').siblings("td").css({"background" :"none"});
				$("#"+ruleid+"_btn_div").parent('td').parent('tr').addClass("highlight");
				$("#son-"+ruleid).attr('checked',true);
				$("#"+ruleid+"_smodel").val(true);
				
				$("#son-"+ruleid).attr('disabled',true);
				$("#soff-"+ruleid).attr('disabled',true);
				
				
				
				if($("#"+ruleid+"_init_val").val() == "ON"){
        			//$(this).siblings("div").show();
        			$("#"+ruleid+"_reasonDiv").show();
        			$("#"+ruleid+"_status").hide();
        			
        		}	
				$("#"+ruleid+"_model").val('OFF');
			}else{
				$("#"+ruleid+"_btn_div").parent('td').parent('tr').removeClass("highlight");
				$(".carTable td:nth-child(even)").css({"background" :"#EEEEEE"});
				$(".carTable td:nth-child(odd)").css({"background" :"#FFFFFF"});
				//$(this).siblings("div").hide();
				$("#"+ruleid+"_status").show();
				$("#"+ruleid+"_reasonDiv").hide();
				$("#"+ruleid+"reason").val("Please enter the reason so that we can help you.");
				$("#"+ruleid+"_model").val('ON');
				$("#"+ruleid+"_email_ind").show();
				$("#son-"+ruleid).attr('disabled',false);
				$("#soff-"+ruleid).attr('disabled',false);

				
				//$(this).siblings("input").val("ON");
			}}
				
			});
				
		$("#save").click(function(){
			var ind = 0;
			var count = ${ruleCount};
			for(var i=1; i<=count; i++)
	 	    {
	 	      if(($("input[name='model.status["+i+"]']").val() == "OFF") && ($("#"+i+"status").val() == "ON")){
	 	      	var reason = $("textarea[name='model.reason["+i+"]']").val();
	 	      	reason = $.trim(reason);
	 	      	if("" == reason || "Please enter the reason so that we can help you." == reason){
	 	      	    ind = 1;
	 	      		$("textarea[name='model.reason["+i+"]']").val("Please enter the reason so that we can help you.");
	 	      		$("textarea[name='model.reason["+i+"]']").addClass("errorDiv");
	 	      	}	 	      	
	 	      }
	 	    }
	 	    $(".errorDiv").css("border-color","red");
	 	    $(".errorDiv").css("color","red");
	 	    if(0 == ind){
	 	    	for(var i=1; i<=count; i++)
	 	    	{
	 	    		if(($("input[name='model.status["+i+"]']").val() == "OFF")
	 	      			&& ($("#"+i+"status").val() == "ON")){
	 	      			
	 	      			$("input[name='model.updated["+i+"]']").val(true);
	 	      		}
	 	      		else if(($("input[name='model.status["+i+"]']").val() == "ON")
	 	      			&& ($("#"+i+"status").val() == "OFF")){
	 	      			
	 	      			$("input[name='model.updated["+i+"]']").val(true);
	 	      		}
	 	      		else if(($("input[name='model.opportunityEmailIndList["+i+"]']").val() == "true")
		 	      			&& ($("#"+i+"opportunityEmailIndList").val() == "false")){
		 	      			
		 	      			$("input[name='model.updated["+i+"]']").val(true);
		 	      		}
		 	      		else if(($("input[name='model.opportunityEmailIndList["+i+"]']").val() == "false")
		 	      			&& ($("#"+i+"opportunityEmailIndList").val() == "true")){
		 	      			
		 	      			$("input[name='model.updated["+i+"]']").val(true);
		 	      			
		 	      		}
	 	    		
	 	    		
	 	    		
	 	      		}
	 	    	$("#carNotification").submit();
	 	    }
	 	});
	});
	</script>
</head> 
<body>
	<br>
	<form id="carNotification" action="manageAutoOrderAcceptanceAction_saveAndFetchRules.action" method="post">
		<div class="carBuyer">
			<c:forEach items="${carBuyer}" var="buyer" varStatus="status">
				${buyer.value}									
				<input type="hidden" name="model.buyerId" value="${buyer.key}" />
				<input type="hidden" name="model.buyer" value="${buyer.value}" />
			</c:forEach>
		</div>
		<div style="padding-top: 5px;">
			<p>Your firm has been added in the below routing rules set by the
				ServiceLive Buyers. Please review the details and set the Auto
				Acceptance Status.</p>
		</div>
		<table class="carTable" cellpadding="0" cellspacing="0"
			style="width: 99%; table-layout: fixed;">
			<tr style="height: 25px;">
				<th width="25%" class="odd-th"><div style="padding-left: 20px;" ><b>Rule Name</b></div></th>
				<th width="15%" style="text-align: center;" class="even-th"><b>Response
						Date</b></th>
				<th width="20%" style="padding-left: 5px;" class="odd-th">
					<div style="margin-left: 15px;text-align: center;width: 100px;"><b>Auto
						Acceptance Status</b></div>
				</th>
				<th width="30%" class="even-th"> <div style="margin-left: 25%;text-align: center;width: 100px;"><b>Comments</b></div></th> 
				<th width="15%" style="text-align: center;" class="odd-th"><b>Receive Opportunity Email/SMS</b></th>
			</tr>
			<c:forEach items="${ruleList}" var="rules" varStatus="status">
				<tr
					<%-- <c:if test="${status.count%2!=0}">style="background-color: #F2F2F2"</c:if>--%> >
					<td width="20%" style="word-wrap: break-word;"><img id="${rules.routingRuleId}view"
						class="view"
						src="${staticContextPath}/images/s_icons/magnifier.png"
						onclick="quickView('${rules.routingRuleId}');" />
						${rules.ruleName}
						<div id="${rules.routingRuleId}ruleView" class="ruleView"></div> <input
						type="hidden" name="model.ruleId[${status.count}]"
						value="${rules.routingRuleId}" /> <input type="hidden"
						name="model.ruleNames[${status.count}]" value="${rules.ruleName}" />
						<input type="hidden" name="model.buyerIds[${status.count}]"
						value="${rules.buyerId}" /></td>
					<td width="13%" style="text-align: center;"><fmt:formatDate
							value="${rules.createdDate}" pattern="MM-dd-yyyy" /></td>
					<td width="20%" style="word-wrap: break-word; padding-left: 2px;">
					  <%-- 
						<c:if test="${'ON' == rules.autoAcceptStatus}">
							<div id="${rules.routingRuleId}_onoff" class="onoff" style="">
								<input id="on" type="hidden" value="ON" />
							</div>
						</c:if> <c:if test="${'OFF' == rules.autoAcceptStatus}">
							<div id="${rules.routingRuleId}_onoff" class="onoff"
								style="background-position: -8px -21px;">
								<input id="off" type="hidden" value="OFF" />
							</div>
						</c:if> 
					--%>
						<div class="btn-pill" id="${rules.routingRuleId}_btn_div" style="margin-left: 25px;">
						<c:if test="${'ON' == rules.autoAcceptStatus}">
						   	<input type="radio" value="On" name="on-off-${rules.routingRuleId}" id="on-${rules.routingRuleId}">
						    <label for="on-${rules.routingRuleId}" class="checked">On</label> 
						    <input type="radio" value="Off" name="on-off-${rules.routingRuleId}" id="off-${rules.routingRuleId}">
						    <label for="off-${rules.routingRuleId}">Off</label>
						 </c:if>
						 <c:if test="${'OFF' == rules.autoAcceptStatus}">
						    <input type="radio" value="On" name="on-off-${rules.routingRuleId}" id="on-${rules.routingRuleId}">
						    <label for="on-${rules.routingRuleId}">On</label> 
						    <input type="radio" value="Off" name="on-off-${rules.routingRuleId}" id="off-${rules.routingRuleId}">
						    <label for="off-${rules.routingRuleId}" class="checked">Off</label>
						    
						 </c:if>
						 <input id="${rules.routingRuleId}_init_val" type="hidden" value="${rules.autoAcceptStatus}" />
						</div>
						
						<input id="${rules.routingRuleId}_model" type="hidden" value="${rules.autoAcceptStatus}"
						name="model.status[${status.count}]" /> 
					</td>
					<td style="word-wrap: break-word; padding-left: 4px;">
						<div id="${rules.routingRuleId}_reasonDiv" class="reason"
							style="position: relative; float: right;">
							<textarea id="${rules.routingRuleId}reason"
								class="resText" rows="3"
								name="model.reason[${status.count}]"
								onkeyup="countAreaChars('${rules.routingRuleId}reason', '50', 'event');"
								onkeydown="countAreaChars('${rules.routingRuleId}reason', '50', 'event');"
								onfocus="countAreaChars('${rules.routingRuleId}reason', '50', 'event');" style="width: 175px;">Please enter the reason so that we can help you.</textarea>
						</div>
						<div id="${rules.routingRuleId}_status" style="text-align:center; width: 200px;">
							<c:if test="${'OFF' == rules.autoAcceptStatus}">
													${rules.userName}: ${rules.turnOffReason}
												</c:if>
							<c:if
								test="${'ON' == rules.autoAcceptStatus && 'INACTIVE' == rules.ruleStatus}">
								<div style="float:left;">
									<i style="font-size: 20px; padding-left: 80px;"
										class="icon-warning-sign"></i>
									<div style="font-size: 10px; padding-left: 80px;">Inactive</div>
								</div>
								<div style="float:left;padding-top:5px;margin-top: 5px;">
								<span style="padding-left: 15px;">This rule is currently Inactive.</span>
								</div>
							</c:if>
						</div>
						<!--  <div class="btn-pill" id="" style="margin-left: 25px;">
						<input type="radio" value="On" name="on-off" id="on-">
						    <label for="on" class="checked">On</label> 
						    <input type="radio" value="Off" name="on-off" id="off">
						    <label for="off">Off</label>
						</div> -->
						 <input type="hidden" id="${status.count}status"
						value="${rules.autoAcceptStatus }" /> <input type="hidden"
						name="model.updated[${status.count}]" value="false" />
					</td>
					
					<td style="width:20%;border-right:1px solid #9F9F9F;padding-top:5px;padding-left:5%">
					<c:if
								test="${'ON' == rules.autoAcceptStatus}">
							     <div id="${rules.routingRuleId}_email_ind"  style="display: block;" >
							       	    
							      <c:if test="${true == rules.opportunityEmailInd}">
 									<input type="checkbox" value="${rules.opportunityEmailInd}" checked="checked"
							       	    name="son-off-${rules.routingRuleId}" id="son-${rules.routingRuleId}">							 
							     </c:if>
							     
							     <c:if test="${false == rules.opportunityEmailInd}">
 									<input type="checkbox" value="${rules.opportunityEmailInd}" 
							       	    name="son-off-${rules.routingRuleId}" id="son-${rules.routingRuleId}">						
							     </c:if>  
							     
					       		      
							     <!--<c:if test="${true == rules.opportunityEmailInd}">
								    <input type="radio" value="true" name="son-off-${rules.routingRuleId}" id="son-${rules.routingRuleId}" checked="checked">
								    <label for="on-${rules.opportunityEmailInd}" class="" style="font-weight: bold;font-size: 12px;"><b>On</b></label> 
								    <input type="radio" value="false" name="son-off-${rules.routingRuleId}" id="soff-${rules.routingRuleId}">
								    <label for="off-${rules.opportunityEmailInd}" style="font-weight: bold;font-size: 12px;"><b>Off</b></label>
								 </c:if>
								 <c:if test="${false == rules.opportunityEmailInd}">
							 		<input type="radio" value="true" name="son-off-${rules.routingRuleId}" id="son-${rules.routingRuleId}">
								    <label for="on-${rules.opportunityEmailInd}" class="" style="font-weight: bold;font-size: 12px;"><b>On</b></label> 
							        <input type="radio" value="false" checked="checked" name="son-off-${rules.routingRuleId}" id="soff-${rules.routingRuleId}">
								    <label for="off-${rules.opportunityEmailInd}" style="font-weight: bold;font-size: 12px;"><b>Off</b></label>
					              </c:if>  -->
								 <input id="${status.count}opportunityEmailIndList" type="hidden" value="${rules.opportunityEmailInd}" />
					
							     </div>
							     <input id="${rules.routingRuleId}_smodel" type="hidden" value="${rules.opportunityEmailInd}"
										name="model.opportunityEmailIndList[${status.count}]" />
							     
							     </c:if>
							     
							     <c:if
								test="${'OFF' == rules.autoAcceptStatus}">
								<div id="${rules.routingRuleId}_email_ind"  style="display: block" >
							       	  <input type="checkbox" value="${rules.opportunityEmailInd}" checked="checked"
							       	    name="son-off-${rules.routingRuleId}" id="son-${rules.routingRuleId}" disabled="true">
							        <!--<input type="radio" value="true" name="son-off-${rules.routingRuleId}" id="son-${rules.routingRuleId}" checked="checked" disabled="true">
								    <label for="on-${rules.opportunityEmailInd}" class="" style="font-weight: bold;font-size: 12px;"><b>On</b></label> 
								    
								    <input type="radio" value="false" name="son-off-${rules.routingRuleId}" id="soff-${rules.routingRuleId}" disabled="true">
								    <label for="off-${rules.opportunityEmailInd}" style="font-weight: bold;font-size: 12px;"><b>Off</b></label>  -->
								  <input id="${status.count}opportunityEmailIndList" type="hidden" value="${rules.opportunityEmailInd}"  /> 
								  								  
								</div>
								<input id="${rules.routingRuleId}_smodel" type="hidden" value="${rules.opportunityEmailInd}"
										name="model.opportunityEmailIndList[${status.count}]" />
								
								</c:if>
							</td>
					
				</tr>
			</c:forEach>
		</table>
	</form>
	<div style="margin-top:30px;padding-right: 50px;float: right;">
							<input id="save" type="button" value="SAVE" class="button action submit" style="width:70px;"/>
	</div>
</body>
</html>