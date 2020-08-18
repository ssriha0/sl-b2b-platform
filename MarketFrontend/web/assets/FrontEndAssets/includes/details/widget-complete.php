			<!-- Complete Order Widget-->

			<?php 
			$modalID = 'completeWidget';
			$modalAria = 'completeWidgetTitle';
			$modalTitle = 'Mark This Job Completed';
			$modalBtnText = 'Submit';
			include 'includes/details/modal-header.php'; ?>

					<form>
						<label>When was the job completed?</label>

				      	<label for="completion-date">Date</label>
				      	<!-- Pre-fill with today's date using data-value param in format MM/DD/YYYY -->
				      	<input data-value="01/08/2014" type="date" class="datepicker" id="completion-date" name="completion-date">
						
						<label for="completion-time">Time</label>
						<!-- Pre-fill with time closest to CURRENT TIME, round to nearest quarter hour. 
						ex. Current Time 		Value 
							11:53-12:07			12:00 
							12:08-12:22 		12:15
							12:23-12:37			12:30
							12:38-12:52 		12:45 
						-->
				      	<input type="time" class="timepicker" id="completion-time" name="completion-time">

				      	<label for="provider">Provider that did the work</label>
				      	<!-- Here, if the provider has already been assigned, display that pro as preselected. If not, display blank. -->
				      	<select id="provider" name="provider" title="Assigned Provider">
				      		<?php if ( !(isset($assignedPro)) ) { echo '<option selected="selected" value=""></option>'; } ?>
			      			<option value="101" value="12345" id="ID-12345" <?php if ( isset($assignedPro) ) { echo ' selected="selected"'; } ?>>Tim Taylor</option>
			      			<option value="100" value="22345" id="ID-22345">Paul Blart</option>
			      			<option value="201" value="32345" id="ID-32345">Mark Blart</option>
			      			<option value="202" value="42345" id="ID-42345">Eugene Blart Jr.</option>
			      			<option value="203" value="52345" id="ID-52345">Mordecai Blart</option>
			      		</select>

			      		<label for="site-visits">Number of on-site visits</label>
				      	<input type="number" id="site-visits" name="site-visits">

				      	<label>Fees charged to customer</label>

				      	<label for="parts">Parts</label>
						<div class="input-group">						  
						  <span class="input-group-addon">$</span>
						  <input type="text" id="parts" name="parts" class="form-control">
						</div>

						<label for="parts">Labor</label>
						<div class="input-group">
						  <span class="input-group-addon">$</span>
						  <input type="text" id="labor" name="labor" class="form-control">
						</div>

						<div class="total-cost">
							Total: $<span class="amt">0.00</span>
						</div>

						<label for="comments">Comments</label>
				      	<textarea title="Comments on the resolution of the job" id="comments" name="comments"></textarea>
	                   
	              	</form>

			<?php include 'includes/details/modal-footer.php'; ?>