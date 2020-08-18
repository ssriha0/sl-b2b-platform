			<!-- Cancel Order Widget-->
        	<a role="button" class="btn btn-danger" href="#" data-toggle="modal" data-target="#cancelWidget">Mark This Lead Inactive</a>
        	<small class="tooltip-target" data-toggle="tooltip" data-placement="top" data-original-title="Inactivate this lead if you no longer want it to appear in your &quot;Active Leads&quot; Dashboard.">What's this?</small>

			<?php 
			$modalID = 'cancelWidget';
			$modalAria = 'cancelWidgetTitle';
			$modalTitle = 'Mark This Lead Inactive';
			$modalBtnText = 'Submit';
			include 'includes/details/modal-header.php'; ?>
			      	<form>
			      		<!--<div class="row">
			      			<div class="col-sm-4">
			      				<label class="block">Who requested the cancelation?</label>
			      			</div>
			      			<div class="col-sm-8">
			      				<div class="btn-group btn-group-justified" data-toggle="buttons">
			                        <label class="btn btn-default" for="firm">
			                            <input type="radio" name="leadCancelInitiatedBy" value="2" id="firm"> Your firm
			                        </label>
			                        <label class="btn btn-default" for="customer">
			                            <input type="radio" name="leadCancelInitiatedBy" value="1" id="customer"> The customer
			                        </label>
			                    </div>
			      			</div>
			      		</div>  /.row -->

			            <div class="cancel-reason">
	                    	<div class="row padding-top">
		                    	<div class="col-sm-4">
			                    	<label for="reasonCode">Select a reason</label>
								</div>
			                    <div class="col-sm-8">
						      		<select id="reasonCode" name="reasonCode" title="Select a reason for inactivating this lead">
						      			<option selected="selected" value=""></option>
						      			<!-- Firm Cancelation Reasons-->
						      			<option value="101" class="firm-rsn">Scheduling issue</option>
						      			<option value="102" class="firm-rsn">Work out of scope</option>
						      			<!-- Customer Cancelation Reasons-->
						      			<option value="201" class="cust-rsn">Customer no longer needed service</option>
						      			<option value="202" class="cust-rsn">Customer only wanted a quote</option>
						      			<option value="203" class="cust-rsn">Our Firm lost the lead</option>
						      			<!-- Other-->
						      			<option value="000" class="other">Other</option>
					      			</select>
						      	</div>
						    </div><!-- /.row -->
						    <div class="row padding-top">
						      	<div class="col-sm-4">
						      		<!-- if Other is selected -->
						      		<label for="cancel-reason-other">Please specify</label>
						      	</div>
						      	<div class="col-sm-8">
						      		<input type="text" name="cancel-reason-other" id="cancel-reason-other">
						        </div>
						    </div><!-- /.row -->
	                    </div> <!-- /.cancel-reason -->
			            
	              	</form>
			<?php include 'includes/details/modal-footer.php'; ?>