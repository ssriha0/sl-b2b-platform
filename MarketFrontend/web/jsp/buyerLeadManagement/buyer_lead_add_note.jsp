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
        <script type="text/javascript"
            src="${staticContextPath}/javascript/formfields.js"></script>
        <script type="text/javascript"> 
        	$('#emailChecked').hide();
        	$('#emailCheckedAllProviders').show();
            $('#comment_leftChars').keypress(function(event) {
              if ( event.which == 13 ) {
                 event.preventDefault();
               }              
            });
            $('#reqCancelAmount').keypress(function(event) {
              if ( event.which == 13 ) {
                 event.preventDefault();
               }              
            });
            function showEmailAlertOptions() {
                $('#emailChecked').show();
            }
            function hideEmailAlertOptions() {
                $('#emailChecked').hide();
            }
            var checkAlertAllProviders=true;
            function hideEmailAlertAllProviders() {
            	if(checkAlertAllProviders==true){
            		$('#emailCheckedAllProviders').hide();
					checkAlertAllProviders=false;
            	}
            	else{
            		$('#emailCheckedAllProviders').show();
					checkAlertAllProviders=true;
            	}
            }
            function validateAddNotes()
            {	
                var subject = document.getElementById('addNoteSubject');
                var message = document.getElementById('addNoteMessage');
                    if(trimfield(subject.value) == '-1') 
						{
						if((trimfield(message.value) == 'enter text...')||(trimfield(message.value) == ''))
							{      
							$('#addNoteE1').show();
							$('#addNoteE1').html("Please provide Subject and Message");
							return false;
							}
						$('#addNoteE1').show();
						$('#addNoteE1').html("Please provide Subject");
						return false;
						}
                    else if((trimfield(message.value) == 'enter text...')||(trimfield(message.value) == ''))
						{
						$('#addNoteE1').show();
						$('#addNoteE1').html("Please provide Message");
						return false;
						}
					else	
						return true;
            }
            function trimfield(str) 
            { 
                return str.replace(/^\s+|\s+$/g,''); 
            }
        </script>
        <style>
        /*Move to common CSS*/
		div.submitButtonDiv {
			background-color: #DEDDDD;
			height: 50px;
		}
		div.maindiv {
			border: 1px solid #a1a1a1;
			width: 100%;
		}
		div.splitdiv1 {
			width: 100%;
			float: left;
		}
		div.splitdiv2 {
			width: 50%;
			float: left;
		}
		</style>
    </head>
    <body>

        <div id="cancellationInfo" class="maindiv">
            <form id="frmCancelFromSOM" name="frmCancelFromSOM">
                <div>
					<div id="modalTitleBlack"
						style="background: #58585A; border-bottom: 2px solid black; color: #FFFFFF; text-align: left; height: 25px; padding-left: 8px; padding-top: 10px;">
						<span style="font-size: 12px; font-weight: bold;">Add Note
							(Service Request #- ${leadsDetails.slLeadId})</span>
						<div style="float: left; padding: 5px; color: #CCCCCC;">
							<i class="icon-remove-circle"
								style="font-size: 20px; position: absolute; right: 5px; cursor: pointer; top: 5px;"
								onclick="closeModal('addLeadNote');"></i>
						</div>
					</div>
                </div>
               	<input id="leadId" type="hidden" name="leadId" value="${leadsDetails.slLeadId}"/>
               	<input id="phoneNo" type="hidden" name="phoneNo" value="${leadsDetails.phoneNo}"/>
               	<input id="phoneNo" type="hidden" name="firstName" value="${leadsDetails.firstName}"/>
               	<input id="phoneNo" type="hidden" name="lastName" value="${leadsDetails.lastName}"/>   
				<div class="modalheaderoutline">
                	<div style="width: 100%; height: 40%; border: 0px">
						<div style="width: 100%; padding-left: 2%; height: 10%; padding-top: 3%">
						 <div id="addNoteE1" class="errorBox" style="display: none;height:20px; padding-top:5px; padding-left:10px; width:94%"></div>
							<br/>
						 	<div>
								<div class="splitdiv1" style="width:100%;">
									<div style="float: left; padding-left:1%; font-size: 12px; font-weight: bold;width:14%;">
										<span style="font-size: 12px; font-weight: bold;padding-left: 1%;padding-right: 5%;float: right;">Subject : </span>
									</div>
									<div style="float: right; width: 82%">
										<select name="addNoteSubject" id="addNoteSubject"
		                                    style="width: 50%;border:2px solid #CCCCCC">
		                                    <option selected="selected" value="-1">
												Select One
											</option>
											<c:forEach items="${leadCategory}" var="reasons">
												<option value="${reasons}">${reasons}</option>
											</c:forEach>
		                                </select>
									</div>
									<br />
                                	<br />
                                	<div style="float: left; padding-left:1%; font-size: 12px; font-weight: bold;width:14%;">
										<span style="font-size: 12px; font-weight: bold;padding-left: 1%;padding-right: 5%;float: right;">Message: </span>
									</div>
									<div style="float: right; width: 82%; font-size: 12px">
										<textarea style="width: 80%" name="addNoteMessage" id="addNoteMessage" onfocus="if(this.value==this.defaultValue)this.value='';" onblur="if(this.value=='')this.value=this.defaultValue;"
                                    	onkeyup="countAreaChars(this.form.addNoteMessage, this.form.comment_leftChars, 750, event);"
                                    	onkeydown="countAreaChars(this.form.addNoteMessage, this.form.comment_leftChars, 750, event);">enter text...</textarea><br/><br/>
                                		<input type="text" id="comment_leftChars" name="comment_leftChars" value="750"
                                    	maxlength="3" size="3" readonly="readonly" onKeyPress="return false;"/> Characters remaining
									</div>
									<br /><br />
                                	<br /><br />
									<br /><br />
									<br /><br />
                                 	<div style="float: right; width: 82%; font-size: 12px">
										 <input type="checkbox" name="checkPrivate" id="checkPrivate" value="true" onclick="hideEmailAlertAllProviders()"/> Mark as Private (not visible to Providers)
									</div>
									<br /><br />
                                	<div style="float: right; width: 82%; font-size: 12px">
                                	<input type="radio" checked="checked" name="emailAlertInd" id="noEmailAlert" value=false onclick="hideEmailAlertOptions()"/> No email alert
                                	<br/>
									<br/>
                                	<input type="radio" id="emailAlertChecked" name="emailAlertInd" value="true" onclick="showEmailAlertOptions()" style="padding-top: 1%"/> Send email alert
                                	</div>
                                	<div id="emailChecked" style="float: right; width: 75%; padding-top: 2%; font-size: 12px">
	                                	<input type="checkbox" checked="checked" id="checkSupport" value="true" name="checkSupport" /> ServiceLive Support
										<br/>
										<c:if test="${leadsDetails.leadStatus!='unmatched'}">
										<div id="emailCheckedAllProviders" style="padding-top: 5px; float: left">
											<input type="checkbox" checked="checked" id="checkAllProviders" value="true" name="checkAllProviders" /> All Providers
										</div>
										</c:if>
                                	</div>
                                	
                                	
 								</div>
							</div>	

                    </div>
               </div>
                <br /> <br /> <br /> <br /> <br /> <br /><br /> <br />
				<br /><br /><br /> <br /> <br /><br /><br />
				<br /><br /><br /> <br/> <br/>
            <div id="cancelButtonDiv" class="submitButtonDiv">
                <br />
                <input type="button" id="addNoteLead" value="Submit" onclick="submitAddNotes()" class="button action" style="float: right; height: 30px; width: 10%; position: relative; right: 20px;" />                   
            </div>
              </div>
 			</form>
        </div>  
    </body>
</html>

