			<!-- ===///Status Behavior Instructions\\\== -->

			<!-- Complete Order Widget-->

			<?php 
			$modalID = 'callWidget';
			$modalAria = 'callWidgetTitle';
			$modalTitle = 'Call Bob Loblaw';
			$modalBtnText = 'Submit';
			include 'includes/details/modal-header.php'; ?>

					<div class="alert alert-info">
						<h4>Bob Loblaw<br> 847-434-9986</h4>
						<p>Install/repair water softener (Repair)</p>
						<p>Lorem ipsom, here we may put tips on how to land the lead.</p>
					</div>

					<form>

						<label>Was the customer available?</label>
						<ul class="nav nav-tabs">
						  <li><a href="#cust-avail" data-toggle="tab">Yes</a></li>
						  <li><a href="#cust-not-avail" data-toggle="tab">No</a></li>
						</ul><!-- /.nav-tabs -->

						<!-- Two different outcome categories -->
						<div class="tab-content">
  						  <!-- if the customer DOES answer -->
						  <div class="tab-pane" id="cust-avail">

						  	<label>Do they still need the work done?</label>
							<div class="btn-group btn-group-justified" data-toggle="buttons">
		                        <label class="btn btn-default" for="cust-interest-yes">
		                            <input type="radio" name="cust-interest" value="cust-interest-yes" id="cust-interest-yes"> Yes
		                        </label>
		                        <label class="btn btn-default" for="cust-interest-no">
		                            <input type="radio" name="cust-interest" value="cust-interest-no" id="cust-interest-no"> No
		                        </label>
		                    </div><!-- /.btn-group -->

		                    <div class="cust-avail-rsn">
		                    	<label for="cust-avail-rsn">Outcome of the call</label>
					      		<select id="cust-avail-rsn" name="cust-avail-rsn" title="Select an outcome of what came from the call">
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
					      			<option value="301" class="won-rsn">Scheduled appointment for site visit</option><!-- ===///Status = WORKING, displays Schedule Modal on load\\\== -->
					      			<option value="302" class="won-rsn">Call back needed</option><!-- ===///Status = WORKING\\\== -->
					      			<option value="300" class="won-rsn">Other</option><!-- ===///Status = WORKING\\\== -->
					      			<!-- Customer Not Interested Reasons (same reasons as cancelation) -->
					      			<option value="401" class="lost-rsn">Service no longer needed</option> <!-- ===///Status = CANCELLED\\\== -->
					      			<option value="402" class="lost-rsn">Customer hired someone else</option> <!-- ===///Status = CANCELLED\\\== -->
					      			<option value="403" class="lost-rsn">Quote too high</option> <!-- ===///Status = CANCELLED\\\== -->
					      			<option value="400" class="lost-rsn">Other</option> <!-- ===///Status = CANCELLED\\\== -->
					      		</select>
					      		<!-- if Other is selected -->
					      		<label for="avail-rsn-other">Please specify</label>
					      		<input type="text" name="avail-rsn-other" id="avail-rsn-other">
		                    </div> <!-- /.cust-avail-rsn -->

						  </div> <!-- /#cust-avail -->

						  <!-- if the customer DOES NOT answer -->
						  <div class="tab-pane" id="cust-not-avail">
						  	<label for="cust-not-avail-rsn">Select an outcome</label>
						  	<select id="cust-not-avail-rsn" name="cust-not-avail-rsn" title="Select an outcome of the call">
				      			<option selected="selected" value=""></option>
				      			<!-- Not sure how you want to populate the values, perhaps using DB-driven number codes? -->
				      			<option value="501">No answer, left a message</option><!-- ===///Status = WORKING\\\== -->
				      			<option value="502">No answer</option><!-- ===///Status = WORKING\\\== -->
				      			<option value="503">Wrong number</option><!-- ===///Status = WORKING\\\== -->
				      			<option value="500">Other</option><!-- ===///Status = WORKING\\\== -->
				      		</select>
				      		<!-- if Other is selected -->
				      		<label for="not-avail-rsn-other">Please specify</label>
				      		<input type="text" name="not-avail-rsn-other" id="not-avail-rsn-other">
						  </div> <!-- /#cust-not-avail -->
						</div> <!-- /.tab-content -->

	              	</form>

			<?php include 'includes/details/modal-footer.php'; ?>