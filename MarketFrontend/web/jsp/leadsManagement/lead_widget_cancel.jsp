<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>		
			
			<!-- Cancel Order Widget-->

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



    $('#errorMessage').val("");
	
});
function submitCancel(){
	
	var retval=validateCancelLead();
	if(retval=="true"){
		$('#errorMessage').html("");
		 $('#errorMessage').hide();
		 $('.btn btn-default').hide();
       var formValues = $('#cancelForm').serializeArray();
       var leadId = '${lmTabDTO.lead.leadId}';
       var url = 'leadsManagementController_cancelLead.action?leadId='+leadId;
       $.ajax({
            url: url,
            type: "POST",
            dataType : 'json',
            data: formValues,
            success: function(data) {
            	 $('#cancelWidget').hide();
            	 if(data.responseMessage == 'Lead Cancelled Succesfully'){
	               window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
	               
            	 }else{
	                	$("#leadResp").html($ESAPI.encoder().encodeForHTML(data.responseMessage));
	                	$("#leadResp").show();
	                	}
            	  
            },
            error: function(xhr, status, err) {
            	$("#errorMessage").html("Unable To Cancel the Lead");
	            $("#errorMessage").show();            	
            	//window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;

           }             
        });
	} 
	else {
		$('#cancelLead').attr('disabled',false);
	}
	
}
function validateCancelLead()
{  
	 $('#cancelLead').attr('disabled',true);
	 $('#errorMessage').html("");
	 $('#errorMessage').hide();
    var message = document.getElementById('comments').value;
    var message1=$.trim(message);
    var reasonCode = document.getElementById('reasonCode').value;
   
   
		
	  if(reasonCode == '')
    {
    	$('#errorMessage').show();
        $('#errorMessage').html("Please select a reason");
        return "false";
    }
	
      else if ( ($( '#reasonCode option:selected' ).hasClass('other'))  && message1 == '')
     {
    	 
            $('#errorMessage').show();
            $('#errorMessage').html("Please provide comments");
            return "false";
     }
  
   
    else
    {
    	$('#errorMessage').hide();
    	return "true";
    }
      
}
	

</script>      	
<c:set var="modalID" value="cancelWidget"/>
<c:set var="modalAria" value="cancelWidgetTitle"/>
<c:set var="modalBtnText" value="Submit"/>
<c:set var="modalTitle" value="Mark This Lead Inactive"/>



<a role="button" class="btn btn-danger" href="#" data-toggle="modal" data-target="#cancelWidget"> Mark This Lead Inactive</a>
<small class="tooltip-target" data-toggle="tooltip" data-placement="top" data-original-title="Inactivate this lead if you no longer want it to appear in your &quot;Active Leads&quot; Dashboard.">What's this?</small>
<div class="modal fade" id="${modalID}" tabindex="-1" role="dialog" aria-labelledby="${modalAria}" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="clearCancelValues();">&times;</button>
			        <h3 class="modal-title" id="${modalAria}" >${modalTitle}</h3>
			      </div>
			      <div class="modal-body">
			      <div id="leadResp" style="margin-left:5px;width: 56%;margin-left:20px;display: none;" class="buyerLeadError" ></div>
			      		<form id="cancelForm">
			      		<div id="errorMessage" class="buyerLeadError" style="display: none;"></div>
			      		<!-- <div class="row">
			      			<div class="col-sm-4">
			      				<label class="block">Who requested the cancelation?</label>
			      			</div>
			      			<div class="col-sm-8">
			      				<div class="btn-group btn-group-justified" data-toggle="buttons">
			                        <label class="btn btn-default" for="firm"  onclick="setReason('firm');">
			                            <input type="radio" name="leadCancelInitiatedBy"  id="firm"> Your firm
			                        </label>
			                        <label class="btn btn-default" for="customer" onclick="setReason('customer');">
			                            <input type="radio" name="leadCancelInitiatedBy"  id="customer"> The customer
			                        </label>
			                    </div>
			      			</div>
			      		</div> /.row -->

			            <div class="cancel-reason">
	                    	<div class="row padding-top">
		                    	<div class="col-sm-4">
			                    	<label for="reasonCode">Select a reason</label>
								</div>
			                    <div class="col-sm-8">
						      		<select id="reasonCode" name="reasonCode" title="Select a reason for inactivating this lead">
						      			<option selected="selected" value=""></option>
				      			         <!--Cancelation Reasons-->
				      			         <c:forEach items="${leadProviderReasons}" var="providerReasons">
				      			            <c:choose>
				      				          <c:when test="${providerReasons.key == 'Other'}">
				      					        <option value="${providerReasons.value}" class="other">${providerReasons.key}</option>
				      				          </c:when>
				      				          <c:otherwise>
				      					        <option value="${providerReasons.value}" class="firm-rsn">${providerReasons.key}</option>
				      				           </c:otherwise>
								            </c:choose>
								        </c:forEach>
				      			    </select>
						      	</div>
						         </div><!-- /.row -->
						             <div class="row padding-top">
						      	         <div class="col-sm-4">
						      		         <!-- if Other is selected -->
						      		          <label for="cancel-reason-other">Please specify</label>
						      	         </div>
						      	     <div class="col-sm-8">
						      		          <input type="text" name="comments" id="comments">
						             </div>
						         </div><!-- /.row -->
	                        </div> <!-- /.cancel-reason -->
			           	</form>
	              	</div><!-- /.modal-body -->
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default cancelLead" type="submit" id=cancelLead onclick="submitCancel()">${modalBtnText}</button>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			
<script type="text/javascript">
$('#cancelWidget').on('hidden.bs.modal', function (e) {
	clearCancelValues();
});

function clearCancelValues(){
	$("#reasonCode").val("");
    $("#comments").val("");
    $('#errorMessage').html("");
	$('#errorMessage').hide();
    $('#errorMessage').val("");
}

</script>