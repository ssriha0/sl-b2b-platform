<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="error" scope="session" value="<%=session.getAttribute("ErrorMessage")%>" />
<c:set var="totalCarRules" value="${fn:length(pendingRules) }" ></c:set>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8;no-cache" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/autoAcceptance.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/toggle-btn.css"/>	
	<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
	<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
	
	
	<!--[if lt IE 8]><link href="css/font-awesome-ie7.min.css" rel="stylesheet" /><![endif]-->	
	<style>
		.modal {
			    max-width: 100%;
			    width: 100%;
			    padding: 0px 0px;
		}
	</style>
	<script type="text/javascript">
	
	jQuery(document).ready(function($){
		
		$("input[type='checkbox']").click(function() { 
			var id = $(this).attr("name");
			var ids=id.substr(0, 3);
			if(ids=='son'){
				var ruleid = id.replace("son-off-",""); 
				var value = $("#son-"+ruleid).is(":checked"); 
				if(value == false){
					$("#"+ruleid+"_smodel").val(false);
				}else{
					$("#"+ruleid+"_smodel").val(true);
					 }		
			}
		});
			
		
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
						 }		
					
				}
				else{
					
				var ruleid = id.replace("on-off-",""); 
				if($(this).is(':checked'))  {
						var name1 = $(this).attr('name');        
						$('input[name=' + name1 + ']:radio').next('label').removeClass('checked');
					    $(this).next('label').addClass('checked');
				}
				var value = $(this).val();
				$("#"+ruleid+"_updated").val(true);
				if(value === 'Off'){
					$("#"+ruleid+"reason").val("Please enter the reason so that we can help you.");
					$("#"+ruleid+"_btn_div").parent('td').css({"background" :"none"});
					$("#"+ruleid+"_btn_div").parent('td').siblings("td").css({"background" :"none"});
					$("#"+ruleid+"_reasonDiv").show();
					$("#"+ruleid+"_model").val('OFF');
					$("#son-"+ruleid).attr('checked',true);
					$("#son-"+ruleid).attr('disabled',true);
					$("#soff-"+ruleid).attr('disabled',true);
					$("#"+ruleid+"_smodel").val(true);
					
					
				}else{
					$("#"+ruleid+"_status").show();
					$("#"+ruleid+"_reasonDiv").hide();
					$("#"+ruleid+"reason").val("Please enter the reason so that we can help you.");
					$("#"+ruleid+"_model").val('ON');
					$("#son-"+ruleid).attr('disabled',false);
					$("#soff-"+ruleid).attr('disabled',false);

					$("#"+ruleid+"_email_ind").show();
					
				}
				 			
				}
						
			});
						
		/*	$("div.onoff").click(function (e) {
				//var x = e.pageX;
				//var y = e.pageY;
				if($(this).children("input").val() == "ON")
				{
					$(this).parent("td").siblings("td").children("div").children("textarea").val("Please enter the reason so that we can help you.");
            		//$(this).siblings("div").css("left", x-230 + "px");
            		//$(this).siblings("div").css("top", y-90 + "px");
					$(this).parent("td").siblings("td").children(".reason").show();
					$(this).css({"background-position":"-8px -21px"});
					$(this).children("input").val("OFF");
				}
				else
				{
					$(this).parent("td").siblings("td").children(".reason").hide();
					$(this).parent("td").siblings("td").children("div").children("textarea").val("Please enter the reason so that we can help you.");
					$(this).css({"background-position":"top left"});
					$(this).children("input").val("ON");
				}
			}); */
			
		
			
		var nAgt = navigator.userAgent;
		if (nAgt.indexOf("Chrome")!=-1) {  
			$(".view").css("padding-top","0px");  
			$("#ruleNameChrome").css("padding-top","10px");  
		}  
			
		$(".resText").focus(function(){
			$(this).css("border-color","#BBBBBB");
	 		$(this).css("color","black");
			if("Please enter the reason so that we can help you." == $.trim($(this).val())){
				$(this).val("");
			}	
		});
		$(".resText").blur(function(){
	 		if($.trim($(this).val())==""){
	 			$(this).val("Please enter the reason so that we can help you.");
	 		}
		});
		
		$("#agree").click(function(){
			var ind = 0;
			$("#carRuleErrorDiv").hide();
			var count = ${totalCarRules};
			for(var i=1; i<=count; i++)
	 	    {
				var statusVal = $("input[name='model.status["+i+"]']").val();
				if("ON" != statusVal && "OFF" != statusVal){
					$("#carRuleErrorDiv").show();
	 	      		ind = 1;
	 	      		return;
				}
				//if(($("input[name='model.status["+i+"]']").val() == "OFF")){
				if(statusVal == "OFF"){
		 	      	var reason = $("textarea[name='model.reason["+i+"]']").val();
		 	      	reason = $.trim(reason);
		 	      	if("" === reason || "Please enter the reason so that we can help you." === reason){
		 	      	    ind = 1;
		 	      		$("textarea[name='model.reason["+i+"]']").val("Please enter the reason so that we can help you.");
		 	      		$("textarea[name='model.reason["+i+"]']").addClass("errorDiv");
		 	      	}	 	      	
		 	      }
	 	     	
 	    		//if(($("input[name='model.status["+i+"]']").val() == "OFF")
 	    		var oldVal = $("#"+i+"status").val();
 	    		if(statusVal == "OFF" && oldVal == "ON"){
	 	      		$("input[name='model.updated["+i+"]']").val(true);
	 	      	} //else if(($("input[name='model.status["+i+"]']").val() == "ON")
	 	      	 else if(statusVal == "ON" && oldVal == "OFF"){
	 	      			$("input[name='model.updated["+i+"]']").val(true);
	 	      	}
	 	    }
	 	    $(".errorDiv").css("border-color","red");
	 	    if(0 == ind){
	 	    	var formValues = $('#carNotification').serializeArray();
	 	    
	 	    	jQuery('#close').load('manageAutoOrderAcceptanceAction_saveRules.action', formValues, function(data) {
	 	    		jQuery('.successDiv').show();
	 	    		closeAutoModal();
	 	    		<c:if test="${indicator==1}">
	 	    		loadMemberOffer();
	 	    		</c:if>
	 	    	});
	 	    	
	 	    }
	 	});
	 	    
	});

	function closeAutoModal(){
		jQuery('#carNotificationDiv').hide();
		jQuery("#carNotificationDiv").fadeOut('slow'); 
		jQuery.modal.close(); 
    }
	
	function loadMemberOffer(){
				jQuery("#popup").modal({
								                onOpen: modalOpen,
								                onClose: modalOnClose,
								                persist: true,
								                containerCss: ({ width: "540px", height: "auto", marginLeft: "500px",marginTop: "100px" })
						            });
		}
		function modalOpen(dialog) {
		            dialog.overlay.fadeIn('fast', function() {
		            	dialog.container.fadeIn('fast', function() {
		            		dialog.data.hide().slideDown('slow');
		            	});
		        	});
		 		}
		  
		   		function modalOnClose(dialog) {
		       		dialog.data.fadeOut('fast', function() {
		            	dialog.container.slideUp('fast', function() {
		            		dialog.overlay.fadeOut('fast', function() {
		            			jQuery.modal.close(); 
		            		});
		          		});
       		});
       		}	
	
	function quickView(ruleId){
		jQuery(".ruleView").hide();
		jQuery('#'+ruleId+'ruleView').show();
		jQuery('#'+ruleId+'ruleView').html('<center><img src="${staticContextPath}/images/loading.gif" width="500px" height="160px"/></center>');
		jQuery('#'+ruleId+'ruleView').load("manageAutoOrderAcceptanceAction_displayQuickView.action?id="+ruleId);
	}
	
	function countAreaChars(areaName, limit, evnt) {
	 	var txt = jQuery.trim(jQuery('#' + areaName).val());
		if (txt.length > limit) {
			txt = txt.substring(0, limit);
			jQuery('#' + areaName).val(txt);
			if (!evnt)
				var evnt = window.event;
			evnt.cancelBubble = true;
			if (evnt.stopPropagation) {
				evnt.stopPropagation();
			}
		}
	}
	
	
	</script>
	

	</head>
	
	<body>
		<div class="modalheader"
		style="background: none repeat scroll 0% 0% rgb(88, 88, 90); color: #FFFFFF; text-align: left; height: 25px; padding-top: 10px; padding-right: 4px; padding-left: 15px;">
		 <b>Auto Acceptance Notification! Your response is required.</b>
		</div>
		<div style="margin-left: 40px;">
			<br>
			Your firm has been added in the below routing rules set by the ServiceLive Buyers. Please review the details and set the  
			Auto Acceptance<br />Status.
			<br><br>
			<div id="carRuleErrorDiv" style="display: none;color: red;font-size: 13px;margin-bottom: 8px;text-align: center;margin-right: 35px; ">Please set the Auto Acceptance status for all the rules.</div>
			<table id="notificationHeader" cellpadding="0" cellspacing="0" width="95%">
				<tr style="background-color: #D8D8D8;height: 25px;">
					<td class="font headFirstCol" style="width:10%"><b>Buyer</b></td>
					<td class="font topBorder" style="width:12%"><b>Rule Name</b></td>
					
					<td class="font topBorder cntrAlign" style="padding-top: 2px;padding-left: 25px;width:6%"><b>Updated On</b></td>
					<td class="font topBorder " style="width: 12%; padding-left: 5%;"><b>Auto Acceptance Status</b></td>
					<td class="font topBorder " style="width: 12%;padding-left: 5%;"><b>Comments</b></td>
					<td class="font headLastCol cntrAlign" style="padding-top: 2px;width:12%;"><b>Receive Opportunity Email/SMS</b></td>
					
				</tr>
			</table>
			
			<form id="carNotification" action="manageAutoOrderAcceptanceAction_saveRules.action" method="post">
			
			<div class="scroll">
				<table id="notificationBody" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed;">
					<c:forEach items="${pendingRules}" var="rules" varStatus ="status">
						<tr <c:if test="${status.count%2!=0}">style="background-color: #F2F2F2"</c:if>>
							<td class="font bodyFirstCol" style="width:12%;word-wrap:break-word;">								
								${rules.buyer}
							</td>
							<td class="font" style="width:14%;word-wrap:break-word;">								
								<img id="${rules.routingRuleId}view" class="view" src="${staticContextPath}/images/s_icons/magnifier.png" 
										onclick="quickView('${rules.routingRuleId}');" />
								${rules.ruleName}
							</td>
							
							<td id="ruleNameChrome" class="font" style="width:2%;">
								<c:if test="${rules.updatedInd == true}">
									<i class="icon-star" style="font-size: 15px; padding-left:9px;"></i><br>
									<span style="font-size:9px">Updated</span>
								</c:if>
								<div id="${rules.routingRuleId}ruleViewTop"  style="height:4px;"></div>
								<div id="${rules.routingRuleId}ruleView" class="ruleView" style="left: 235px;"></div>
								<input type="hidden" name="model.ruleNames[${status.count}]" value="${rules.ruleName}"/>
								<input type="hidden" name="model.ruleId[${status.count}]" value="${rules.routingRuleId}"/>
								<input type="hidden" name="model.buyerIds[${status.count}]" value="${rules.buyerId}"/>
							</td>
							<td class="font" style="width:12%;padding-left:50px;padding-top:15px;-webkit-logical-width:245px">								
								<fmt:formatDate value="${rules.createdDate}" pattern="MM-dd-yyyy" />
							</td>								
							
							<td style="width:15%;">
								<br>
								 <div class="btn-pill" id="${rules.routingRuleId}_btn_div" style="margin-left: 25px;-webkit-margin-start:-165px">
								   	<input type="radio" value="On" name="on-off-${rules.routingRuleId}" id="on-${rules.routingRuleId}" >
								    <label for="on-${rules.routingRuleId}" class="" style="font-weight: bold;font-size: 12px;"><b>On</b></label> 
								    
								    <input type="radio" value="Off" name="on-off-${rules.routingRuleId}" id="off-${rules.routingRuleId}" >
								    <label for="off-${rules.routingRuleId}" style="font-weight: bold;font-size: 12px;"><b>Off</b></label>
								   
								    <input id="${rules.routingRuleId}_model" type="hidden" value="11" name="model.status[${status.count}]" /> 
							    </div>
								<br>
						    </td>
							<td style="width:20px;padding-top:5px;padding-left:15px">
								<div id="${rules.routingRuleId}_reasonDiv" class="reason" >
									<textarea id="${rules.routingRuleId}reason" cols="20" style="height: 36px;width:136px;-webkit-margin-start:-175px" class="resText"	name="model.reason[${status.count}]"
											onkeyup="countAreaChars('${rules.routingRuleId}reason', '50', 'event');" 
											onkeydown="countAreaChars('${rules.routingRuleId}reason', '50', 'event');"
											onfocus="countAreaChars('${rules.routingRuleId}reason', '50', 'event');">Please enter the reason so that we can help you.</textarea>
								</div>
								
								<input type="hidden" id="${status.count}status" value="${rules.autoAcceptStatus}" />
								<input type="hidden" id="${rules.routingRuleId}_updated" name="model.updated[${status.count}]" value="false" />
							</td>
							<td style="width:9%;border-right:1px solid #9F9F9F;padding-top:15px">
							     <div id="${rules.routingRuleId}_email_ind" class="opportunitEmail" style="display: block;-webkit-margin-start: 20px" >
							        
							         <input type = "checkbox" value="${rules.opportunityEmailInd}" 
							         	name="son-off-${rules.routingRuleId}" id="son-${rules.routingRuleId}" disabled="true" checked="checked"> 

							        
							        <!-- <input type="radio" value="true" name="son-off-${rules.routingRuleId}" id="son-${rules.routingRuleId}" disabled="true" checked="checked">
								    <label for="on-${rules.opportunityEmailInd}" style="font-weight: bold;font-size: 12px;"><b>On</b></label> 
								    
								    <input type="radio" value="false" name="son-off-${rules.routingRuleId}" id="soff-${rules.routingRuleId}" disabled="true">
								    <label for="off-${rules.opportunityEmailInd}" style="font-weight: bold;font-size: 12px;"><b>Off</b></label>   --> 
								   
								     <input id="${rules.routingRuleId}_smodel" type="hidden" value="true" name="model.opportunityEmailIndList[${status.count}]" /> 
							     
							     </div>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			
			</form>
			
			<div class="topBorder" style="width:95%"></div>
			<br>
			
			<div id="note" class="note">
				&nbsp;By clicking AGREE button, you, on behalf of the provider firm, agreeing to Auto Accept the orders routed through the rules above. Please 
				&nbsp;note that you should select "OFF", if you wish not to Auto Accept the orders for that rule.
				<br><br>
			</div>
		</div>
		
		<br><br>
		<input id="agree" type="button" value="AGREE" class="button action submit" style="margin-left:725px;font-size:12px;"  />	
		
		<br><br><br>	
		<div id="close"></div>	
		
	</body>
</html>