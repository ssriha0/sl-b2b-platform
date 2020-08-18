<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
  					<c:set var="modalID" value="assignWidget"/>
		  			<c:set var="modalAria" value="assignWidgetTitle"/>
		 		  	<c:set var="modalBtnText" value="Save"/>
  						<c:if test="${lmTabDTO.lead.resourceAssigned != null && lmTabDTO.lead.resourceAssigned != '0'}"> 
		 					<c:set var="modalTitle" value="Reassign this Job"/> 
						</c:if> 
						<c:if test="${lmTabDTO.lead.resourceAssigned == null || lmTabDTO.lead.resourceAssigned == '0'}"> 
		 					<c:set var="modalTitle" value="Assign a Provider"/> 
						</c:if> 
					
    			<div class="modal fade" id="${modalID}" tabindex="-1" role="dialog" aria-labelledby="${modalAria}" aria-hidden="true" >
			  		<div class="modal-dialog">
			    	<div class="modal-content">
			      	<div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="clearValues();">&times;</button>
			        <h3 class="modal-title" id="${modalAria}" >${modalTitle}</h3>
			      </div>
			      <div id="assignErrorMsg" class="buyerLeadError" style="display: none;"></div>
			      <div class="modal-body">
			        <div class="alert alert-info">
					     <dl>
						 	<dt><b>When:</b></dt>
							<dd><time>${lmTabDTO.lead.scheduledDate}&nbsp;${lmTabDTO.lead.scheduledStartTime}&nbsp;-&nbsp;${lmTabDTO.lead.scheduledEndTime}</time></dd>
							<dt><b>Where:</b></dt>
							<dd>${lmTabDTO.lead.city}&nbsp;${lmTabDTO.lead.street}&nbsp;,&nbsp;${lmTabDTO.lead.state}&nbsp;${lmTabDTO.lead.zip}</dd>
			      	     </dl>
			      	 </div>
			     	<c:if test="${providerList!= null}">
			      	<form id="providerWidget" name="providerWidget">
			      	   <div class="row">
			      			 <div class="col-sm-4">
				      	         <label for="provider">Assign this job to:</label>
				      	     </div>
			      			<div class="col-sm-8">
				      	       <select id="assignProviderWidget" name="assignProviderWidget" title="Select a provider to assign/reassign this job">
				      	          <option selected="selected" value=""> </option>
	                                  <!-- <div class="btn-group-vertical full" data-toggle="buttons"> -->
	                  	              <c:forEach items="${providerList}" var="provList">
	                    					<c:choose>
	                 	 							<c:when test="${lmTabDTO.lead.resourceAssigned != provList.resourceId}">
	                 	  								<option  value="${provList.resourceId}">${provList.resFirstName}&nbsp;${provList.resLastName}&nbsp;
	                 	  										<small>(about&nbsp;${provList.providerDistance}&nbsp;miles away)</small> </option>
	                    	
	                 	  						    </c:when>
	                 	                   </c:choose>
	                 	             </c:forEach>
	                            </select>
	                         </div>
	                	</div><!-- /.row -->
	              	</form>
	              </c:if> 
	             <c:if test="${providerList == null}">
	              	<font color="red">
	              	         There are no Eligible Providers for this Firm
	              	</font>
	             </c:if>
	              	
	              </div><!-- /.modal-body -->
	               <c:choose>
	              <c:when test="${providerList == null}">
	             	
	             	</c:when>
                  <c:when test="${lmTabDTO.lead.resourceAssigned != provList.resourceId && providerList == null}">
	                 	  	
	                 </c:when>
			      <c:otherwise>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" type="submit"  id="assignSave" onclick="saveProviderDetails()">${modalBtnText}</button>
			      </div>
			       </c:otherwise>
			      
			      </c:choose>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->	
	<script type="text/javascript">
	
	$('#assignWidget').on('hidden.bs.modal', function (e) {
		clearValues();
	});

	
	function getResourceId(resourceId){
		
		 var index = resourceId.indexOf('_');
		 var resourceValue = resourceId.substring(index+1);
		 if(resourceValue == ''){
		 	$("#resId").val("");
		 }
		 else{
			 $("#resId").val(resourceValue);
		 }
	
	 }
	function clearValues(){
		
	    $('#assignErrorMsg').html("");
		$('#assignErrorMsg').hide();
	    $('#assignErrorMsg').val("");
	    
	}
  		 function saveProviderDetails(){
  			$('#assignSave').attr('disabled',true);
  			var selectValue = document.getElementById('assignProviderWidget');
			var resourceId = selectValue.options[selectValue.selectedIndex].value;
  			
  			// alert(resourceId);
  			
  			// var resourceId=document.getElementById('resId').value;
          	var formValues = $('#providerWidget').serializeArray();
       		var leadId='${lmTabDTO.lead.leadId}';
       		
  			 if(resourceId == null || resourceId == ''){
  				
  				$('#assignErrorMsg').show();
  	            $('#assignErrorMsg').html("Please select a Provider");
  	            $('#assignSave').attr('disabled',false);
  			 }
  			 else{
  				
  				$('#sucessDiv').html("");
  				$('#sucessDiv').hide();
  				$('#assignErrorMsg').html("");
  				$('#assignErrorMsg').hide();
        		$.ajax({
			  		url: 'leadsManagementController_assignProvider.action?resourceId='+resourceId+'&leadId='+leadId,
			  		type: "POST",
			  		data: formValues,
			  		 dataType : "json",
			  		success: function(data) {  
			  			 $('#assignWidget').hide();
						window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
                },
                error: function(xhr, status, err) {
		  			$("#sucessDiv").html("<div  class='buyerLeadError'><p  style='text-align:right'> Error Occured </p></div>");
					$("#sucessDiv").show(); 
				}
				});
  			 }
		 }
    
  </script>    

  