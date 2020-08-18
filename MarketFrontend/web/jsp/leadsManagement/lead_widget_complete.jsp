<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>		
			<!-- Complete Order Widget-->

	<!-- esapi4js dependencies -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/lib/log4js.js"></script>
<!-- esapi4js core -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/esapi.js"></script>
<!-- esapi4js i18n resources -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/i18n/ESAPI_Standard_en_US.properties.js"></script>
<!-- esapi4js configuration -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/Base.esapi.properties.js"></script>

<script type="text/javascript" language="JavaScript">
    Base.esapi.properties.application.Name = "SL Application";
    // Initialize the api
    org.owasp.esapi.ESAPI.initialize();

</script>			
<script type="text/javascript">
jQuery(document).ready(function (){
	//Clearing form values
	$("#completedDate").val("");	
	$("#newCompletedTime").val("");	
	$("#completedProvider").val("");
	$("#noOfOnsiteVisits").val("");
	$("#feeForParts").val("");
	$("#feeForLabour").val("");
	$("#completedComments").val("");
	var assignedProv = $("#assignedProv").val();
	$("#completedProvider").val(assignedProv);
	var totalFee = 0;  
	totalFee=fmtMoney(totalFee);
	$(".amt").html(totalFee);
});

//method is called on click of the submit button in complete lead order page
function submitCompleteLead(){
	if(validateCompleteLead()== true){
		$("#ErrorMessageDivForCompleteLead").hide();
		 
		 var formValues = $('#CompleteLead').serializeArray();
		 var leadId = '${lmTabDTO.lead.leadId}';
		 var url = 'leadsManagementController_completeLead.action?leadId='+leadId;
		 $.ajax({
            url: url,
            type: "POST",
            data: formValues,
            success: function(data) {
            	if(data.responseMessage == 'Invalid Completed DateTime'){
            		$("#ErrorMessageDivForCompleteLead").html("Completion Time cannot be greater than current time");
            		$("#ErrorMessageDivForCompleteLead").show();
            		$("#completeSave").attr("disabled", false);
            		
            	}else if(data.responseMessage=='Lead Completed Succesfully'){
            	    window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
            	}else{
            		$("#ErrorMessageDivForCompleteLead").html($ESAPI.encoder().encodeForHTML(data.responseMessage));
            		$("#ErrorMessageDivForCompleteLead").show();
            		$("#completeSave").attr("disabled", false);
            	}
            },
	        error: function(xhr, status, err) {	            	
		    	$('#ErrorMessageDivForCompleteLead').html("Unable To Complete the Lead");
		        $('#ErrorMessageDivForCompleteLead').show();
	         }  
        });
	}else{
		$("#completeSave").attr("disabled", false);
	}
}


function checkIfDouble(val,flag) {
	$("#ErrorMessageDivForCompleteLead").hide();
	var isNumeric = true;
    var validChars = '0123456789.';
    if ((val.indexOf(".")== 0)){
    	if(flag=="PARTS"){
    		 $("#ErrorMessageDivForCompleteLead").html("Please enter a valid material cost");
    		 $("#ErrorMessageDivForCompleteLead").show();
    		 document.getElementById("feeForParts").value = "";
    	}
		if(flag=="LABOUR"){
			 $("#ErrorMessageDivForCompleteLead").html("Please enter a valid labour cost");
			 $("#ErrorMessageDivForCompleteLead").show();
			 document.getElementById("feeForLabour").value = "";
    	}
    	return false;
    }
    var count = 0;
    for(var i = 0; i < val.length; i++) {
    	if(val.charAt(i) == "."){
        	count = count + 1;
        }
        if(count > 1){
	    	if(flag=="PARTS"){
	   			 $("#ErrorMessageDivForCompleteLead").html("Please enter a valid material cost");
	    		 $("#ErrorMessageDivForCompleteLead").show();
	    		 document.getElementById("feeForParts").value = "";
	   		}
			if(flag=="LABOUR"){
				 $("#ErrorMessageDivForCompleteLead").html("Please enter a valid labour cost");
				 $("#ErrorMessageDivForCompleteLead").show();
				 document.getElementById("feeForLabour").value = "";
	    	}
			return false;
        }
   	}
    
    for(var i = 0; i < val.length; i++) {
        if(validChars.indexOf(val.charAt(i)) == -1){
        	if(flag=="PARTS"){
      			 $("#ErrorMessageDivForCompleteLead").html("Please enter a valid material cost");
        		 $("#ErrorMessageDivForCompleteLead").show();
        		 document.getElementById("feeForParts").value = "";
      		}
   			if(flag=="LABOUR"){
	   			 $("#ErrorMessageDivForCompleteLead").html("Please enter a valid labour cost");
				 $("#ErrorMessageDivForCompleteLead").show();
				 document.getElementById("feeForLabour").value = "";
      		}
            return false;
    	}
    }
    return true;
}
//method does all the basic complete lead page validations
function validateCompleteLead(){
	$("#completeSave").attr("disabled", true);
	$("#ErrorMessageDivForCompleteLead").html("");
	var completedDate = $("#completedDate").val();
	var completedDateObj=new Date(completedDate);
	var newCompletedTime = $("#newCompletedTime").val();
	var completedProvider = $("#completedProvider").val();
	var noOfOnsiteVisits = $("#noOfOnsiteVisits").val();
	var feeForParts = $("#feeForParts").val();
	var feeForLabour = $("#feeForLabour").val();
	var completedComments = $("#completedComments").val();
	completedComments =$.trim(completedComments);
	//SL-20893 code changes for totalFee<0.0 validation --START
	var totalFee=feeForParts+feeForLabour;
    var feeTotal=0;
    feeTotal=fmtMoney(totalFee);
    //SL-20893 code changes for totalFee<0.0 validation --STOP
	if(completedDate == '' || completedDate == null){
		$("#ErrorMessageDivForCompleteLead").html("Please select a date");
		$("#ErrorMessageDivForCompleteLead").show();
		return false;
	}else if(completedProvider == '' || completedProvider == null){
		$("#ErrorMessageDivForCompleteLead").html("Please select a Provider");
		$("#ErrorMessageDivForCompleteLead").show();
		return false;
	}else if(noOfOnsiteVisits == '' || noOfOnsiteVisits == null){
		$("#ErrorMessageDivForCompleteLead").html("Please enter the number of visits");
		$("#ErrorMessageDivForCompleteLead").show();
		return false;
	}else if(isNaN(noOfOnsiteVisits)){
		$("#ErrorMessageDivForCompleteLead").html("Please enter valid value for Number of visits");
		$("#ErrorMessageDivForCompleteLead").show();
		return false;
	}else if(!isDouble(feeForParts)){
		$("#ErrorMessageDivForCompleteLead").html("Please enter valid value for material cost");
		$("#ErrorMessageDivForCompleteLead").show();
		return false;
	}else if(!isDouble(feeForLabour)){
		$("#ErrorMessageDivForCompleteLead").html("Please enter valid value for labour cost");
		$("#ErrorMessageDivForCompleteLead").show();
		return false;
	//SL-20893 code changes for totalFee<=0.0 validation --START	
	}else if((feeTotal< 0.00)||(feeTotal== 0.00)){
		$("#ErrorMessageDivForCompleteLead").html("Total fee should not be less than zero");
		$("#ErrorMessageDivForCompleteLead").show();
		return false;
	}
	//SL-20893 code changes for totalFee<=0.0 validation --STOP
	return true;
}
//method checks if the given parameter is of type double/float
function isDouble(val) {
		var isNumeric = true;
        var validChars = '0123456789.';
        if ((val.indexOf(".")== 0)){
        	return false;
        }
        for(var i = 0; i < val.length; i++) {
            if(validChars.indexOf(val.charAt(i)) == -1)
                return false;
        }
        return true;
 }
function calculateTotalCost(){
	var feeForParts = $("#feeForParts").val();
	var feeForLabour = $("#feeForLabour").val();
	var totalFee = '';
	feeForParts = parseFloat(feeForParts);
	feeForLabour = parseFloat(feeForLabour);
	if(feeForParts == ''){
		feeForParts = 0;
	}
	if(feeForLabour == ''){
		feeForLabour = 0;
	}
	
	if(isNaN(feeForParts) && !isNaN(feeForLabour)){
		totalFee = feeForLabour;
	}else if(!isNaN(feeForParts) && isNaN(feeForLabour)){
		totalFee = feeForParts;
	}else if(isNaN(feeForParts) && isNaN(feeForLabour)){
		totalFee = 0;
	}else if(!isNaN(feeForParts) && !isNaN(feeForLabour)){
		totalFee = feeForParts+feeForLabour;
	}
	totalFee=fmtMoney(totalFee);
	$(".amt").html(totalFee);
	
}
function isNumberKey(evt){
  if (!evt) evt = window.event;
    var charCode = (evt.which) ? evt.which : event.keyCode
      if(evt.shiftKey)
          return false;
      if (charCode > 31 && (charCode < 48 || charCode > 57)){
       switch(charCode){
         case 8:
         case 9:           
         case 37:
       		return true;
         default:
       		return false;
         }
      }
    return true;
   }
function isNumberKeyLaborParts(evt){
  if (!evt) evt = window.event;
    var charCode = (evt.which) ? evt.which : event.keyCode
      if(evt.shiftKey)
          return false;
      if (charCode > 31 && (charCode < 48 || charCode > 57)){
      switch(charCode){
         case 8:
         case 9:           
         case 37:
         //Added to handle .
         case 46:
       		return true;
         default:
       		return false;
         }
      }
    return true;
   }
function fmtMoney(mnt){
    mnt -= 0;
    mnt = (Math.round(mnt*100))/100;
    var x = (mnt == Math.floor(mnt)) ? mnt + '.00' 
              : ( (mnt*10 == Math.floor(mnt*10)) ? 
                       mnt + '0' : mnt);
                       
    if( x > 0 )
      return x;
         return "0.00";
}  

</script>	
<style type="text/css">
.datepickerComplete{
  background-color: #FFFFFF !important;
  cursor: default !important;
}
</style>
<c:set var="modalID" value="completeWidget"/>
<c:set var="modalAria" value="completeWidgetTitle"/>
<c:set var="modalBtnText" value="Submit"/>
<c:set var="modalTitle" value="Mark This Job Completed"/>

<div class="modal fade" id="${modalID}" tabindex="-1" role="dialog" aria-labelledby="${modalAria}" aria-hidden="true">
  <div class="modal-dialog">
	 <div class="modal-content">
		 <div class="modal-header">
			 <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="clearForm();">&times;</button>
			   <h3 class="modal-title" id="${modalAria}" >${modalTitle}</h3>
		 </div>
		 
		<div class="modal-body">
			<div id="ErrorMessageDivForCompleteLead" class="buyerLeadError" style="display: none;"> </div>
			<form id="CompleteLead">
			         <div class="row">
			      			<div class="col-sm-4">
						       <label><span class="required-info">*</span> When was the job completed?</label>
						    </div>
				      		<div class="col-sm-8">
				              <div class="row">
				      			  <div class="col-xs-6">
                                     <input  type="date" class="datepickerComplete" id="completedDate" name="completedDate" readonly="readonly">
				      	             <label for="completedDate" class="small align-right">Date</label>
				      	           </div>
				      	           <div class="col-xs-6">
						              <input type="time" class="timepicker" id="newCompletedTime" name="completedTime">
						              <label for="completedTime" class="small align-right">Time</label>
						           </div>
				      		  </div><!-- /.row -->
				      	   </div>
				    </div><!-- /.row -->
				    <div class="row padding-top">
							<div class="col-sm-4">
						            <label for="completedProvider"><span class="required-info">*</span>Provider that did the work</label>
						     </div>
						     <div class="col-sm-8">
				      	        <select id="completedProvider" name="completedProvider" title="Assigned Provider" readonly="readonly">
				      		        <c:if test="${providerList!= null}">
                                         <c:forEach items="${providerList}" var="provList">                                         	
                                         		<c:if test="${lmTabDTO.lead.resourceAssigned == provList.resourceId}">
                                         			<option selected="selected" value="${provList.resourceId}">${provList.resFirstName} ${provList.resLastName}</option>
                                         		</c:if> 
                                         		<c:if test="${lmTabDTO.lead.resourceAssigned != provList.resourceId}">                                  		
                                         			<option value="${provList.resourceId}">${provList.resFirstName} ${provList.resLastName}</option>
                                         		</c:if>
                                         </c:forEach>
                                    </c:if>
                                    <c:if test="${providerList== null}">
                                         <option value=" ">There are no Eligible Providers for this Firm</option>
                                    </c:if>
				      	        </select>
				      	    </div>		
				    </div><!-- /.row-->
				    <div class="row padding-top">
							<div class="col-sm-4">
				      	         <label for="noOfOnsiteVisits"><span class="required-info">*</span> Number of on-site visits</label>
				      	     </div>
							<div class="col-sm-8">
				      	          <input type="number" id="noOfOnsiteVisits" name="noOfOnsiteVisits" onkeypress="return isNumberKey(event);" maxlength="9">
				      	    </div>		
					</div><!-- /.row-->
					<div class="row padding-top">
							<div class="col-sm-4">
                                <label><span class="required-info">*</span>Fees charged to customer</label>
                            </div>
							<div class="col-sm-8">
							   <div class="row">
				      				 <div class="col-xs-6">
                                        <div class="input-group">						  
						                    <span class="input-group-addon">$</span>
						                    <input type="text" id="feeForParts" name="feeForParts" class="form-control" onkeypress="return isNumberKeyLaborParts(event);" onchange="checkIfDouble(this.value,'PARTS');calculateTotalCost();" maxlength="12">
						                </div>
						                    <label for="feeForParts" class="small align-right">Parts</label>
						                </div>
						             <div class="col-xs-6">
                                       <div class="input-group">
						                    <span class="input-group-addon">$</span>
						                    <input type="text" id="feeForLabour" name="feeForLabour" class="form-control" onkeypress="return isNumberKeyLaborParts(event);" onchange="checkIfDouble(this.value,'LABOUR');calculateTotalCost();" maxlength="12">
						               </div>
                                             <label for="feeForLabour" class="small align-right">Labor</label>
                                     </div>
							  </div><!-- /.row-->
						      <div class="row padding-top">
									<div class="col-xs-12 total-cost">
										<hr>
						                Total: $<span class="amt" >0.00</span>
									    <hr>
									</div>
							 </div><!-- /.row-->
						  </div>		
						</div><!-- /.row-->
                       <div class="row padding-top">
							<div class="col-sm-4">
						        <label for="completedComments">Comments</label>
						     </div>
							<div class="col-sm-8">
				      	        <textarea title="Comments on the resolution of the job" id="completedComments" name="completedComments" maxlength="2000"></textarea>
				      	    </div>		
					  </div><!-- /.row-->
	             </form>
	             <input id="assignedProv" type="hidden" value="${lmTabDTO.lead.resourceAssigned}"/>
			</div><!-- /.modal-body -->
			<div class="modal-footer">
			    <small class="required-info pull-left">* Required</small> 
			    <button type="button" id="completeSave" class="btn btn-default" type="submit"  onclick= "submitCompleteLead();">${modalBtnText}</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
function clearForm(){
    $("#completedDate").val("");   
    $("#newCompletedTime").val("");
    $("#completedProvider").val("");
    $("#noOfOnsiteVisits").val("");
    $("#feeForParts").val("");
    $("#feeForLabour").val("");
    $("#completedComments").val("");
    $("#ErrorMessageDivForCompleteLead").hide();
    var assignedProv = $("#assignedProv").val();
	$("#completedProvider").val(assignedProv);
	var totalFee = 0;  
	totalFee=fmtMoney(totalFee);
	$(".amt").html(totalFee);
}

$('#completeWidget').on('hidden.bs.modal', function (e) {
		clearForm();
	});
</script>