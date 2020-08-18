
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="${staticContextPath}/javascript/confirm.css"
			rel="stylesheet" type="text/css" />
		<link href="${staticContextPath}/css/so_details.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript">	
			$("#selectProviders :checkbox").attr('checked', true);
			$('#selectProviders').hide();
			
			$('#allProviders').hide();
			$('#comment_leftChars').keypress(function(event) {
			  if ( event.which == 13 ) {
			     event.preventDefault();
			   }			   
			});
			
            
		</script>
	</head>
	<body>

		<div id="cancellationInfo">
			
			<form id="frmCancelLead" name="frmCancelLead">
				
								
				<div>
				<div id="modalTitleBlack"
					style="background: #58585A; border-bottom: 2px solid black; color: #FFFFFF; text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;">
					<span style="font-size: 12px; font-weight: bold;">Cancel Order (Service Request # ${leadsDetails.slLeadId})</span>
					<div style="float: right; padding: 5px; color: #CCCCCC;">
						<i class="icon-remove-circle"
							style="font-size: 20px; position: absolute; right: 5px; cursor: pointer; top: 5px;"
							onclick="closeModal('cancelLead');"></i>
					</div>
				</div>
				<div class="modalheaderoutline">
						<div class="rejectServiceOrderFrame"
							style="width: 516px; height: 360px;border: 0px">

							<div class="rejectServiceOrderFrameBody"
								style="width: 503px; padding-left: 5px; height: 500px; posi
								:fixed;font-size:12px;">
								<div style="width: 509px;">
								<br>Submitting the cancellation will send a cancellation notification to providers. Please check the order status at provider level before performing this action
								</div>
								<br />
								<br/>
								<div id="cancelE1" class="errorBox" style="display: none;height:20px; padding-top:5px; padding-left:10px; width:95%"></div>
								<br />
								
								<div style="float: left;
   								 width: 160px; padding-left: 3%; "><b>Reason for Cancellation:</b>
								<span class="req">*  </span>
								</div>
								<div style="float: right;
    								height: 35px;
    								position: relative;
    								right: 47px;
   									width: 283px;">
								<select name="reasonCode" id="reasonCode"  style="width: 281px;border:2px solid #CCCCCC">
									<option selected="selected" value="-1">
										Select One
									</option>
									<c:forEach items="${leadReasons}" var="reasons">
									<option value="${reasons.value}">${reasons.key}</option>
									</c:forEach>
								</select>
								</div>
								
								
								<br />
								<br />
								<div style="width: 150px;padding-left: 18%;">
   								 <input type="hidden" id="lId" value="${leadsDetails.slLeadId}" name="leadId"/>
								<b>Comments:</b>
								<span class="req">*</span>
								</div>
								<div style="float: right;width: 283px; ">
								<textarea style="float: right;
    								 position: relative;
   									 right: 47px;
   									 width: 272px;top: -10px;" name="comments" id="leadComments"
   									 onfocus="if(this.value==this.defaultValue)this.value='';" onblur="if(this.value=='')this.value=this.defaultValue;"
									onkeyup="countAreaChars(this.form.comments, this.form.comment_leftChars, 750, event);"
									onkeydown="countAreaChars(this.form.comments, this.form.comment_leftChars, 750, event);">enter text...</textarea>
								</div><br>
								<div style="float: right;
    								
    								position: relative;
   									 right: 59px;
   									 width: 272px;; height: 42px;top: 5px;">
								<input type="text" id="comment_leftChars" name="comment_leftChars" value="750"
									maxlength="3" size="3" readonly="readonly" onKeyPress="return false;"/>
								Characters remaining<br />
								</div>
								<br />
								
								<br />
								<div style="float: right;
   									 height: 35px;
   									 position: relative;
    								 right: 47px;
    								 width: 283px;">
								<select name="chkAllProviderInd" id="cancelType" style="width: 281px;border:2px solid #CCCCCC" onchange="changeFunc();">
									<option value="true">Cancel the lead from all providers</option>
									<option value="false">Cancel the lead from specific provider(s)</option>
								</select>
								</div>
								                                
								<div id="selectProviders" style="float: right;width: 281px; ">
                                    <c:forEach items="${providerDetails}" var="providerDetail">
									<c:if test="${providerDetail.firmStatus!='CANCELLED'}">
                                    <input type="checkbox" value="${providerDetail.providerId}" id="${providerDetail.providerId}" 
                                    name="providerList" onclick="enableRevokeMembership();"/>&nbsp; ${providerDetail.firmBusinessName} (${providerDetail.firmStatus})
									</c:if>
									<br/>
									</c:forEach>
                                </div>
<c:if test="${not empty leadsDetails.membershipId}">
                                <br />
                                <br /><br /><div style="float: right;
    										position: relative;
    										right: 10px;
   											width: 320px;" >
								<input type="checkbox"  value="true" id="chkMembershipPoints" name="revokePointsInd" />&nbsp;Revoke Shop Your Way Membership Points for this Lead<br/>
								</div></c:if>
							
							</div>
						</div>

					</div>
				</div>
			</form>
			<div id="cancelButtonDiv" style="background-color: #DEDDDD; height: 50px">
				<br />
				<input type="button" id="submitCancelLead" value="Submit" onclick="submitCancelLead()" class="button action"style="float: right; height: 50%; width: 15%; position: relative; right: 20px;" />			
    
			</div>
		</div>
				
	</body>
	<script type="text/javascript">
	function chkforrevokeIndicator()
            {
            	var count = $('#selectProviders input:checked').length;
            	var cancelType = document.getElementById("cancelType");
            	if(cancelType.value=="false")
            	{
            		 $('#chkMembershipPoints').prop('checked', false);
            		 $("#chkMembershipPoints").prop('disabled', true);
            	}
            	
            }
	function changeFunc(){
				var inputElems = document.getElementsByName("providerList");
				var cancelType = document.getElementById("cancelType").value;
				if(cancelType=="true"){
				$('#selectProviders').hide();
					$("#selectProviders :checkbox").attr('checked', true);
					$("#chkMembershipPoints").attr('disabled', false);
					}
				else{
					
					$("#selectProviders :checkbox").attr('checked', false);
					$('#selectProviders').show();
					$('#chkMembershipPoints').attr('checked', false);
            				$("#chkMembershipPoints").attr('disabled', true);
					}
			}		
	 function validateCancelLead()
            {   
                var message = document.getElementById('leadComments');
                var reasonCode = document.getElementById('reasonCode');
                var cancelType = document.getElementById('cancelType');
               	
                
                if(reasonCode.value==-1)
                {
                	$('#cancelE1').show();
                    $('#cancelE1').html("Please select a reason code");
                    return "false";
                }
                else if((trimfield(message.value) == 'enter text...')||(trimfield(message.value) == ''))
                        {
                        $('#cancelE1').show();
                        $('#cancelE1').html("Please provide comments");
                        return "false";
                 }
              
                else if(cancelType.value=="false")
                {
                		var count = $('#selectProviders input:checked').length;
                		if(count <= 0)
                		{
                		$('#cancelE1').show();
                        $('#cancelE1').html("Please select atleast one provider");
                        return "false";
                        
                }
                else
                {
                	$('#cancelE1').hide();
                	return "true";
                }
                   
            }
            function trimfield(str)
            {
                return str.replace(/^\s+|\s+$/g,'');
            }
           }
	 function enableRevokeMembership(el){
		 var unCheck = $('div #selectProviders').find('input:checkbox').length;
		var checked = $('div #selectProviders').find('input:checkbox:checked').length;
		if (unCheck == checked ){
        	 $("#chkMembershipPoints").attr('disabled', false);
         }
		else
		{
			$("#chkMembershipPoints").attr('disabled', true);
		}
			
		}		

	</script>


</html>
