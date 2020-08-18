			<!-- Schedule Widget -->
			<?php 
			$modalID = 'scheduleWidget';
			$modalAria = 'scheduleWidgetTitle';
			if ( $leadStatus == '3' ) { 
				$modalTitle = 'Reschedule Appointment';
			}
			else {
				$modalTitle = 'Schedule a Service Appointment';
			}
			$modalBtnText = 'Save';
			include 'includes/details/modal-header.php'; ?>
			      	<form>
				      	<label for="date">Date</label>
				      	<input type="date" class="datepicker" id="date" name="date">
						
						<label for="startTime">Time Window</label>
				      	<input type="time" class="timepicker half inline-block" id="startTime" name="startTime"> - 
				      	<input type="time" class="timepicker half inline-block" id="endTime" name="endTime">

				      	<label class="block">What is the reason for the site visit?</label>
	                    <div class="btn-group-vertical full" data-toggle="buttons">
	                        <label class="btn btn-default" for="estimation">
	                            <input type="radio" name="reason" value="estimation" id="estimation"> Estimation
	                        </label>
	                        <label class="btn btn-default" for="service">
	                            <input type="radio" name="reason" value="service" id="service"> Service
	                        </label>
	                    </div>
	              	</form>
			<?php include 'includes/details/modal-footer.php'; ?>