			<!-- Cancel Order Widget-->
        	<a role="button" class="btn btn-danger" href="#" data-toggle="modal" data-target="#cancelWidget"><i class="icon-leadStatus-5"></i> Cancel Order</a>

			<?php 
			$modalID = 'cancelWidget';
			$modalAria = 'cancelWidgetTitle';
			$modalTitle = 'Cancel This Job';
			$modalBtnText = 'Submit';
			include 'includes/details/modal-header.php'; ?>
			      	<form>

				      	<label class="block">Who requested the cancelation?</label>

	                    <div class="btn-group btn-group-justified" data-toggle="buttons">
	                        <label class="btn btn-default" for="firm">
	                            <input type="radio" name="canceler" value="firm" id="firm"> Your firm
	                        </label>
	                        <label class="btn btn-default" for="customer">
	                            <input type="radio" name="canceler" value="customer" id="customer"> The customer
	                        </label>
	                    </div>

	                    <div class="cancel-reason">
	                    	<label for="cancel-reason">Select a reason for cancelation</label>
				      		<select id="cancel-reason" name="cancel-reason" title="Select a reason for cancelation">
				      			<!-- Open for debate, but my thinking here is to have 1 big list, 
				      			combining both Firm and Provider cancelation reasons. We'll just
				      			hide the ones that aren't applicable based on selection above. As
				      			for the option values, I wanted a numerical system so we could 
				      			a) easily differentiate between a Firm reason (100s) and a Customer reason (200s), and
				      			b) allow these value pairs to be db driven so we can add new options without a code change. 
				      			See Chet with questions. -->
				      			<option selected="selected" value=""></option>
				      			<!-- Firm Cancelation Reasons-->
				      			<option value="101" class="firm-rsn">Capacity issue</option>
				      			<option value="102" class="firm-rsn">Work out of scope</option>
				      			<option value="100" class="firm-rsn">Other problem</option>
				      			<!-- Customer Cancelation Reasons-->
				      			<option value="201" class="cust-rsn">Service no longer needed</option>
				      			<option value="202" class="cust-rsn">Customer hired someone else</option>
				      			<option value="203" class="cust-rsn">Quote too high</option>
				      			<option value="200" class="cust-rsn">Other</option>
				      		</select>
				      		<!-- if Other is selected -->
				      		<label for="cancel-reason-other">Please specify</label>
				      		<input type="text" name="cancel-reason-other" id="cancel-reason-other">
	                    </div>

	              	</form>
			<?php include 'includes/details/modal-footer.php'; ?>