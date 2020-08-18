<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <link href="${staticContextPath}/javascript/confirm.css"
            rel="stylesheet" type="text/css" />
        <script type="text/javascript"
            src="${staticContextPath}/javascript/formfields.js"></script>
        <script type="text/javascript"> 
        function enableCustomPoints(){
        	var rewardPoints = document.getElementById('rewardPoints');
            if(trimfield(rewardPoints.value) == '0' || trimfield(rewardPoints.value) == '') {
            	document.getElementById("customPointsToAddOrRevoke").style.display="block";	
            }else{
            	document.getElementById("customPointsToAddOrRevoke").style.display="none";
            }
        }
        function clearCommmentsText(){
        	var comments = document.getElementById('comments');
        	if(trimfield(comments.value) == 'enter text...'){
        		document.getElementById('comments').value='';
        	}
        }
        function reenterCommmentsText(){
        	var comments = document.getElementById('comments');
        	if(trimfield(comments.value) == ''){
        		document.getElementById('comments').value='enter text...';
        	}
        	
        }
        function isDecimal(n){
        	if(n == ""){
        		return false;
        	}
        	var strCheck="0123456789";
        	var i;
        	//for(i in n){
        	  for(var i=0; i < n.length; i++){
        	    //alert(strCheck.indexOf(n[i]));
        	    if( strCheck.indexOf(n[i]) == -1){
        		//if(n[i]=='.'){
        			return false;
        		}
        	}
			return true;
        }
        function validateUpdateRewardPoints()
        {	
            var reason   = document.getElementById('reasonCode');
            var selectedText = reason.options[reason.selectedIndex].text;
            var comments = document.getElementById('comments');
            var rewardPoints = document.getElementById('rewardPoints');
            var shopyourway = document.getElementsByName('shopyourway');
            var shopyourway_value;
            var currentSYWPoints = document.getElementById('currentSYWPointshid');
            for(var i = 0; i < shopyourway.length; i++){
                if(shopyourway[i].checked){
                	shopyourway_value = shopyourway[i].value;
                }
            }
           
            if(trimfield(reason.value) == "-1") 
			{
				if((trimfield(comments.value) == 'enter text...')||(trimfield(comments.value) == ''))
				{      
					$('#updateRewardPointsE1').show();
					$('#updateRewardPointsE1').html("Please provide Reason and Comments.");
					return false;
				}
				$('#updateRewardPointsE1').show();
				$('#updateRewardPointsE1').html("Please provide Reason.");
				
				return false;
			}
            else if((trimfield(comments.value) == 'enter text...')||(trimfield(comments.value) == ''))
			{
				$('#updateRewardPointsE1').show();
				$('#updateRewardPointsE1').html("Please provide Comments.");
				return false;
			}
            else if((trimfield(rewardPoints.value) == '-1'))
            {
                $('#updateRewardPointsE1').show();
				$('#updateRewardPointsE1').html("Please select Reward Points.");
				return false;
            }
			else{

					document.getElementById('reasonCodeDesc').value= selectedText;
					document.getElementById('pointsToAddOrRevoke').value= rewardPoints.value;
					document.getElementById('addOrRevoke').value= shopyourway_value;

					if(trimfield(rewardPoints.value) == '0' || trimfield(rewardPoints.value) == '') {
						var customPoints = document.getElementById('customPointsToAddOrRevoke');
						if(trimfield(customPoints.value) == '0' || trimfield(customPoints.value) == '' || trimfield(customPoints.value) <= 0 ){
							$('#updateRewardPointsE1').show();
							$('#updateRewardPointsE1').html("Please enter a value greater than zero."); 
							return false;
						}
						if(trimfield(customPoints.value) > 10000){
							$('#updateRewardPointsE1').show();
							$('#updateRewardPointsE1').html("Please enter a value less than or equal to 10000."); 
							return false;
						}

						if(isNaN(customPoints.value) ||  isDecimal(customPoints.value) == false)
						{   
							$('#updateRewardPointsE1').show();
							$('#updateRewardPointsE1').html("Please provide a integer value."); 
							return false;
						}
					}
					if(trimfield(shopyourway_value) == 1){
						return true;
					}
					
					
					if(trimfield(shopyourway_value) == 2){
						if(trimfield(rewardPoints.value) == '0' || trimfield(rewardPoints.value) == '') {
							if( trimfield(currentSYWPoints.value) - trimfield(customPoints.value) < 0 ){
									 $('#updateRewardPointsE1').show();
									 $('#updateRewardPointsE1').html("Not enough points to revoke."); 
									 return false;
							}
						}	
						if((trimfield(rewardPoints.value) != '0' || trimfield(rewardPoints.value) != '')){
							 if( trimfield(currentSYWPoints.value)-trimfield(rewardPoints.value) < 0 ){
									$('#updateRewardPointsE1').show();
									$('#updateRewardPointsE1').html("Not enough points to revoke."); 
									 return false;
							 }
						}	 
					 }
					     
 
					}
					
					return true;
				}	

        function trimfield(str) 
        { 
            return str.replace(/^\s+|\s+$/g,''); 
        }
        </script>

</head>
<body>
	<div id="updatePointsInfo" class="maindiv">
		<form id="frmUpdateRewardPoints" name="frmUpdateRewardPoints">
			<div>
				<div id="modalTitleBlack" class="updaterevokepointheaderdiv">
					<span style="font-size: 12px; font-weight: bold;">Update
						Shop Your Way Membership Reward (Lead Id-${leadsDetails.slLeadId})</span>
					<div style="float: right; padding: 5px; color: #CCCCCC;">
						<i class="icon-remove-circle"
							style="font-size: 20px; position: absolute; right: 5px; cursor: pointer; top: 5px;"
							onclick="closeModal('updateReward');"></i>
					</div>
				</div>
			</div>
			<input id="leadId" type="hidden" name="leadId" value="${leadsDetails.slLeadId}"/> 
			<input id="shopYourWayId" type="hidden" name="shopYourWayId" value="${leadsDetails.membershipId}"/> 
			
			<input id="addOrRevoke" type="hidden" name="addOrRevoke" value=''/> 
			<input id="reasonCodeDesc" type="hidden" name="reasonCodeDesc" value=''/> 
			<input id="pointsToAddOrRevoke" type="hidden" name="pointsToAddOrRevoke" value=''/>
			<input id="currentSYWPointshid" type="hidden" name="currentSYWPointshid" value='${currentSYWPoints}'/>
			
			
			
			<div class="modalheaderoutline">
				<div style="width: 100%; height: 40%; border: 0px;padding-bottom: 10%;">
					<div style="width: 100%; padding-left: 5px; height: 10%; posi: fixed">
						<!-- 
							<div style="width: 100%;">
								<strong><br>Text to go</strong>
							</div>
							-->
						<br /> 
					    <div id="updateRewardPointsE1" class="errorBox" style="display: none;height:20px; padding-top:5px; padding-left:10px; width:95%;"></div>
					    <br />  
						<div>
							<span style="font-size: 12px; font-weight: bold;padding-left: 1%;padding-right: 1%;">
								Membership Id :</span> ${leadsDetails.membershipId}  <span
								style="font-size: 12px; font-weight: bold; padding-left: 5%;padding-right: 1%;">Customer Name
								:</span> ${leadsDetails.firstName}  ${leadsDetails.lastName}
						</div>
						

						<div style="padding-bottom:31%;">
							<div class="splitdiv1" style="width: 50%;">
								<div style="float: left;width: 35%;padding-top: 5%;">
								    <div style="font-size: 12px; font-weight: bold; width: 100%;text-align:right;font-family: arial;">
									<span style="font-size: 12px; font-weight: bold;padding-left: 1%;padding-right: 1%;">Reason for Update:</span>
									</div>
								</div>
								<div style="float: right; width: 65%;position: relative;left: 1%;padding-top: 5%;">
									<select name="reasonCode" id="reasonCode" style="width: 84%;">
										<option selected="selected" value="-1">Select
											One</option>
										<c:forEach items="${updateRewardPointsReasons}" var="updateRewardReasons">
											<option value="1">${updateRewardReasons}</option>
										</c:forEach>
									</select>
								</div>
								
								<div style="float: left;width: 35%;padding-top: 5%;">
								    <div style="font-size: 12px; font-weight: bold; width: 100%;text-align:right;font-family: arial;">
									<span style="font-size: 12px; font-weight: bold;padding-left: 1%;padding-right: 1%;">Comments:</span>
									</div>
								</div>
								<div style="float: right; width: 65%;left: 1%;position: relative;padding-top: 3%;">
									<textarea style="float: left; width: 80%;" name="comments"
										id="comments"
										onkeyup="countAreaChars(this.form.comments, this.form.comment_leftChars,    
  										750, event);"
										onkeydown="countAreaChars(this.form.comments,   
  										this.form.comment_leftChars, 750, event);" onclick="clearCommmentsText();" onblur="reenterCommmentsText();">enter text...
  										</textarea>
								</div>
								
								<div
									style="float: right; position: relative; width: 70%; height: 42px; left: 4%;padding-top: 2%;font-size: 11px;padding-top: 5%;">
									<input type="text" id="comment_leftChars"
										name="comment_leftChars" value="750" maxlength="3" size="3"
										readonly="readonly" onKeyPress="return false;" /> characters
									remaining<br />
								</div>
								
								<div style="float: left; width: 30%; padding-left: 15%;font-size: 12px;font-family: arial;">
									<input type="radio" name="shopyourway" id="addshopyourway" value="1" checked>Add
									Points
								</div>
								<c:if test="${leadsDetails.reward>0}">
								<div style="float: left; width: 30%; padding-left: 15%;font-size: 12px;font-family: arial;">
									<input type="radio" name="shopyourway" id="revokeshopyourway" value="2">Revoke
									Points
								</div>
								</c:if>
								
								<div style="float: left; width: 40%; padding-left: 16%;padding-top: 5%;">
									<select name="rewardPoints" id="rewardPoints" style="width: 100%;" onchange="enableCustomPoints();">
										<option selected="selected" value="-1">Select One</option>
										<c:forEach items="${updateRewardPointslst}" var="rewardPointsLst">
											<option value="${rewardPointsLst.key}">${rewardPointsLst.value}</option>
										</c:forEach>
										<option value="0">Other</option>
									</select>
								</div>
								<div style="float: left; width: 30%; padding-left: 5%;padding-top: 5%;">	
									<input type="text" id="customPointsToAddOrRevoke" name="customPointsToAddOrRevoke" maxlength=5
										value='' style="height: 18px; width: 80%;display:none;" />
								</div>

							</div>
							<div class="splitdiv2" style="width: 50%;padding-top: 15px;">
								<div id="contenttwo1" class="innerheaderdiv"><b>Membership
									Reward History</b></div>
								<c:if test="${not empty historylst}"> 
								<div style="float: left;overflow-x:hidden;height:190px;width:97%;font-size: 11px; border-bottom: 1px solid #CCCCCC;border-left: 1px solid #CCCCCC;border-right: 1px solid #CCCCCC;word-wrap: break-word;overflow: auto;">
											<c:forEach var="leadHistory" items="${historylst}"> 
											<div style="padding-left:2%;padding-top: 1%;">
										<fmt:formatDate pattern="MM/dd/yyyy" value='${leadHistory.createdDate}' />    
										   &nbsp;${leadHistory.chgComment}
										      </div>
											</c:forEach>
								</div>
								</c:if>
								<c:if test="${empty historylst}"> 
								<div style="float: left;height:200px;width:97%;font-size: 11px; border-bottom: 1px solid #CCCCCC;border-left: 1px solid #CCCCCC;border-right: 1px solid #CCCCCC;">
									<p style="padding-left:2%">No Records Found.</p>
				   				</div>
								</c:if>

									

							</div>
						</div>
						

					</div>
				</div>
             
				
				<div id="updateRewardPointsButtonDiv"
					class="submitButtonDiv" style="height: 62px;">
					<br /> <input type="button" id="updateRewardPointsButtonId"
						value="Submit" onclick="submitUpdateRewardPoints()"
						class="button action"
						style="float: right; height: 50%; width: 13%; position: relative; right: 20px;" />
				</div>
			</div>
		</form>
	</div>

	<div id="manageScopeDiv"></div>


</body>
</html>
