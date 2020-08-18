			<!-- Schedule Widget -->
			<?php 
			$modalID = 'scheduleWidget';
			$modalAria = 'scheduleWidgetTitle';
			if ( $leadStatus == '3' ) { 
				$modalTitle = 'Update Appointment';
			}
			else {
				$modalTitle = 'Schedule a Service Appointment';
			}
			$modalBtnText = 'Save';
			include 'includes/details/modal-header.php'; ?>
			      	<form>
			      		<div class="row">
			      			<div class="col-sm-4">
				      			<label for="date">Date</label>
				      		</div>
				      		<div class="col-sm-8">
				      			<input type="date" class="datepicker" id="date" name="date">
							</div>
						</div><!-- /.row -->
						<div class="row padding-top">
			      			<div class="col-sm-4">
								<label for="startTime">Time Window</label>
				      		</div>
				      		<div class="col-sm-8">
				      			<div class="row">
				      				<div class="col-xs-6 hyphen">
				      					<input type="time" class="timepicker" id="startTime" name="startTime">
				      				</div>
				      				<div class="col-xs-6">
				      					<input type="time" class="timepicker" id="endTime" name="endTime">
				      				</div>
				      			</div><!-- /.row -->
				      		</div>
				      	</div><!-- /.row -->

				      	<div class="row padding-top">
			      			<div class="col-sm-4">
				      			<label>What is the reason for the site visit?</label>
				      		</div>
				      		<div class="col-sm-8">
			                    <div class="btn-group btn-group-justified" data-toggle="buttons">
			                        <label class="btn btn-default" for="estimation">
			                            <input type="radio" name="reason" value="estimation" id="estimation"> Estimation
			                        </label>
			                        <label class="btn btn-default" for="service">
			                            <input type="radio" name="reason" value="service" id="service"> Service
			                        </label>
			                    </div><!-- /.btn-group -->
			                </div>
			            </div><!-- /.row -->
	              	</form>
			<?php include 'includes/details/modal-footer.php'; ?>