<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>		
<script>


jQuery(document).ready(function (){
	//Clearing form values
	$("#cust-interest-yes").val("");	
	$("#cust-interest-no").val("");
	$("#custIndicator").val("");
});
function callCustomer(){
	 if(validateCall()==true){
		 
		   var leadStatus='${lmTabDTO.lead.leadStatus}';		  
	       var formValues = $('#callCustomer').serializeArray();
	       var leadId = '${lmTabDTO.lead.leadId}';
	       var url = 'leadsManagementController_callCustomer.action?oldStatus='+leadStatus+'&leadId='+leadId;
	       $.ajax({
	            url: url,
	            type: "POST",
	            data: formValues,
	            success: function(data) {
	            	$('#callWidget').hide();
	            	window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;  
	            },
	            error: function(xhr, status, err) {	            	
		        	$('#validateMsg').html("Unable to Call Customer");
		        	$('#validateMsg').show();
	            	//window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
	           }             
	        });
	     }
	 else {
		 $("#callCustSave").attr("disabled", false); 
	 }
	 
}
function validateCall(){
	$("#callCustSave").attr("disabled", true);
	var reason1 = document.getElementById('cust-avail-rsn');
	var reason1Text = reason1.options[reason1.selectedIndex].text;

	
	var reason2 = document.getElementById('cust-not-avail-rsn');
	var reason2Text = reason2.options[reason2.selectedIndex].text;

	
	var message1 = document.getElementById('avail-rsn-other').value;
	message1=$.trim(message1);
	var message2 = document.getElementById('not-avail-rsn-other').value;
	message2=$.trim(message2);
	var selectionYes = document.getElementById('cust-interest-yes').value;
	var selectionNo = document.getElementById('cust-interest-no').value;
	var custAvailIndicator=document.getElementById('custIndicator').value;
	
	
	if (custAvailIndicator == '') {
		 $('#validateMsg').html("Please select whether the customer was available or not");
		 $('#validateMsg').show();
		 return false;
	}
	 var selectionYesOrNo = "";
	 if(selectionYes != ''){
		 selectionYesOrNo = selectionYes;
	 }else if(selectionNo != ''){
		 selectionYesOrNo = selectionNo; 
	 }

	if(custAvailIndicator == 'Yes' &&  selectionYesOrNo == '' ){
		    $('#validateMsg').show();
	        $('#validateMsg').html("Please select whether the work is needed or not");
	        return false;
	}
	 
	if( custAvailIndicator == 'Yes' &&  (selectionYesOrNo == 'cust-interest-yes' || selectionYesOrNo == 'cust-interest-no' )){

		    if(reason1.value == ''){
			 	$('#validateMsg').show();
		        $('#validateMsg').html("Please select reason");
		        return false;
		    }else{
		    	if(reason1Text =='Other' && message1 == ''){
		    		$('#validateMsg').show();
		        	$('#validateMsg').html("Please provide comments");
		        	return false;
		    	}	
		    }    
	 }
	if( custAvailIndicator == 'No' ){

		 if(reason2.value == ''){
			 	$('#validateMsg').show();
		        $('#validateMsg').html("Please select reason");
		        return false;
		    }else{
		    	if(reason2Text =='Other' && message2 == ''){
		    		$('#validateMsg').show();
		        	$('#validateMsg').html("Please provide comments");
		        	return false;
		    	}	
		    }    
	 }
	return true;
	
}
//Setting reason properly
function valueForSelection(interestInd){
	if(interestInd == "yes"){
	  $("#cust-interest-yes").val("cust-interest-yes");	
	  $("#cust-interest-no").val("");	
	}else if(interestInd== "no"){
	  $("#cust-interest-no").val("cust-interest-no");
	  $("#cust-interest-yes").val("");	
	}else{
		$("#cust-interest-yes").val("");	
		$("#cust-interest-no").val("");
	}
	
}



function checkTrigger(indicator) {
   $('#custIndicator').val(indicator);
}

</script>

<!-- Complete Order Widget-->
<c:set var="modalID" value="callWidget"/>
<c:set var="modalAria" value="callWidgetTitle"/>
<c:set var="modalBtnText" value="Submit"/>
<c:set var="modalTitle" value="Call ${lmTabDTO.lead.custFirstName}&nbsp;${lmTabDTO.lead.custLastName}"/>
<div class="modal fade" id="${modalID}" tabindex="-1" role="dialog" aria-labelledby="${modalAria}" aria-hidden="true" >
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="callClear();">&times;</button>
			        <h3 class="modal-title" id="${modalAria}" >${modalTitle}</h3>
			      </div>
			      <div class="modal-body">

					<div class="alert alert-info">
					
						<h4>${lmTabDTO.lead.custFirstName}&nbsp;${lmTabDTO.lead.custLastName}
						<br>
						<span class="label label-info pull-right">
						    <i class="glyphicon glyphicon-earphone"></i>  ${lmTabDTO.lead.formattedCustPhoneNo}
						 </span>
						</h4>
						<p>${lmTabDTO.lead.projectType}&nbsp;(${lmTabDTO.lead.skill})</p>
						<!-- <p>Lorem ipsom, here we may put tips on how to land the lead.</p> -->
					</div>

					<form id="callCustomer">
						<div id="validateMsg" class="buyerLeadError" style="display: none;"></div>
						    <div class="row">
			      			   <div class="col-sm-4">
						          <label>
						            Was the customer available?
						          </label>
						       </div>
						       <div class="col-sm-8">
					    	      <div class="btn-group btn-group-justified">
						            <a class="btn btn-default" href="#cust-avail" data-toggle="tab"  id="yes" onclick="checkTrigger('Yes');">Yes</a>
						            <a class="btn btn-default" href="#cust-not-avail" data-toggle="tab" id="no" onclick="checkTrigger('No');">No</a>
						           </div><!-- /.btn-group (serves as nav-tabs) -->
						       </div>
						    </div>

						<!-- Two different outcome categories -->
						<div class="tab-content form-panes">
  						  <!-- if the customer DOES answer -->
						  <div class="tab-pane" id="cust-avail">
                            <div class="row padding-top">
                                <div class="col-sm-4">
						  	         <label>
						  	             Do they still need the work done?
						  	         </label>
						  	    </div>
						  	    <div class="col-sm-8">
							        <div class="btn-group btn-group-justified" data-toggle="buttons">
		                                <label class="btn btn-default" for="cust-interest-yes" onclick="valueForSelection('yes');">
		                                   <input type="radio" name="cust-interest"   id="cust-interest-yes"> Yes
		                               </label>
		                               <label class="btn btn-default" for="cust-interest-no" onclick="valueForSelection('no');">
		                                   <input type="radio" name="cust-interest"  id="cust-interest-no"> No
		                               </label>
		                            </div><!-- /.btn-group -->
                                 </div>
                            </div><!-- /.row -->
                            
		                    <div class="cust-avail-rsn">
		                       <div class="row padding-top">
		                         <div class="col-sm-4">
		                    	    <label for="cust-avail-rsn">Outcome of the call</label>
		                    	 </div>
		                    	 <div class="col-sm-8">
					      		      <select id="cust-avail-rsn" name="callCustReasonCode" title="Select an outcome of what came from the call">
					      			  <!-- Similar setup here as with the Cancellation modal.
					      			       	There is one <select> object that contains reasons for both [Customer
					      					Available], and [Customer Not Available]. Then, based on the selection above
					      					we hide the ones that aren't applicable .
											The architecture of how to code the option values is undetermined, just that I'd
											like a solution that uses codes and is DB-driven, so new reasons can be added
											or language of existing reasons can be edited w/out changes to the code.
					      				-->
					      			<option selected="selected" value=""></option>
					      			<!-- Customer Interested Reasons-->
					      			<c:forEach items="${custInterested}" var="custInt">
					      			   <c:choose>
					      			     <c:when test="${custInt.reason == 'Other'}">
					      			         <option  value="${custInt.reasonCodeId}" class="won-rsn other">${custInt.reason}</option>
					      			     </c:when>
					      			     <c:otherwise>
					      			         <option  value="${custInt.reasonCodeId}" class="won-rsn">${custInt.reason}</option>
					      			     </c:otherwise>
					      			   </c:choose>
					      			</c:forEach>
					      			<!-- Customer Not Interested Reasons (same reasons as cancelation) -->
					      			<c:forEach items="${custNotInterested}" var="custNotInt">
					      			   <c:choose>
					      			      <c:when test="${custNotInt.reason == 'Other'}">
					      			          <option  value="${custNotInt.reasonCodeId}" class="lost-rsn other">${custNotInt.reason}</option>
					      			      </c:when>
					      			      <c:otherwise>
					      			         <option  value="${custNotInt.reasonCodeId}" class="lost-rsn">${custNotInt.reason}</option> <!-- ===///Status = CANCELLED\\\== -->
					      			      </c:otherwise>
					      			   </c:choose>
					      			</c:forEach>
					      		</select>
					      		  </div>
					      		 </div><!-- /.row -->
					      		  <div class="row padding-top">
							      	    <div class="col-sm-4">
					      		        <!-- if Other is selected -->
					      		        <label for="avail-rsn-other">Please specify</label>
					      		        </div>
							      	   <div class="col-sm-8">
					      		         <input type="text" name="callCustComments" id="avail-rsn-other" maxlength="2000">
					      		       </div>
					      		  </div><!-- /.row -->
		                    </div> <!-- /.cust-avail-rsn -->

						  </div> <!-- /#cust-avail -->

						  <!-- if the customer DOES NOT answer -->
						  <div class="tab-pane" id="cust-not-avail">
						    <div class="row padding-top">
							   <div class="col-sm-4">
						  	    <label for="cust-not-avail-rsn">Select an outcome</label>
						  	   </div>
						  	    <div class="col-sm-8">
						  	      <select id="cust-not-avail-rsn" name="callCustReasonCodeNotAvail" title="Select an outcome of the call">
				      			     <option selected="selected" value=""></option>
				      		            <c:forEach items="${custNotResponded}" var="custNotResp">
				      		              <c:choose>
				      		                 <c:when test="${custNotResp.reason == 'Other'}">
				      		                    <option  class="cust other" value="${custNotResp.reasonCodeId}">${custNotResp.reason}</option>
				      		                 </c:when>
				      		                 <c:otherwise>
				      			                <option value="${custNotResp.reasonCodeId}">${custNotResp.reason}</option>
				      			             </c:otherwise>
				      			          </c:choose>
				      			        </c:forEach>
				      		       </select>
				      		     </div>
						      </div> <!-- /.row -->
						      <div class="row padding-top">
							    <div class="col-sm-4">
				      		       <!-- if Other is selected -->
				      		        <label for="not-avail-rsn-other">Please specify</label>
				      		     </div>
						      	<div class="col-sm-8">
				      		           <input type="text" name="callCustNotAvailableComments" id="not-avail-rsn-other" maxlength="2000">
				      		    </div>
						     </div> <!-- /.row -->
						        </div> <!-- /#cust-not-avail -->
						       </div> <!-- /.tab-content -->
						<input type="hidden" id="custIndicator" value=""/>
						
	              	</form>

			</div><!-- /.modal-body -->
			      <div class="modal-footer">
			        <button type="button" id="callCustSave" class="btn btn-default" onclick="callCustomer();">${modalBtnText}</button>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			
<script type="text/javascript">
	
$('#callWidget').on('hidden.bs.modal', function (e) {
		callClear();
});

function callClear(){
    $("#cust-interest-yes").val("");   
    $("#cust-interest-no").val("");
    $("#validateMsg").val("");
    $("#validateMsg").hide();
    $("#yes").val("");
    $("#no").val("");
    $("#cust-avail-rsn").val("");
    $("#cust-not-avail-rsn").val("");
    $("#avail-rsn-other").val("");
    $("#not-avail-rsn-other").val("");
    $("#cust-avail").val("");
    $("#cust-not-avail").val("");
    $("#custIndicator").val("");
    if($("#yes").hasClass('active')){
    	$("#yes").removeClass('active');
    	$("#cust-avail").removeClass('active');
    	if($("label[for='cust-interest-yes']").hasClass('active')){
    		$("label[for='cust-interest-yes']").removeClass('active');
    	}
    	if($("label[for='cust-interest-no']").hasClass('active')){
    		$("label[for='cust-interest-no']").removeClass('active');
    	}
    	
    }
    else if($("#no").hasClass('active')){
    	$("#no").removeClass('active');
    	$("#cust-not-avail").removeClass('active');
    }
    
}
</script>