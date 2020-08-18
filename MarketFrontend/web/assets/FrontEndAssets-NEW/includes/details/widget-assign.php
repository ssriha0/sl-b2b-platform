			<!-- Assign Widget -->
			<?php 
			$modalID = 'assignWidget';
			$modalAria = 'assignWidgetTitle';

			if ( isset($assignedPro) ) { 
				$modalTitle = 'Reassign this Job';
			}
			else {
				$modalTitle = 'Assign a Provider';
			}
			$modalBtnText = 'Save';
			include 'includes/details/modal-header.php'; ?>
					<div class="alert alert-info">
						<dl>
							<dt><b>When:</b></dt>
							<dd><time datetime="2013-11-21T13:37:17">Tuesday Dec 3, 2013 8:00 AM-12:00 PM</time></dd>
							<dt><b>Where:</b></dt>
							<dd>Sudden Valley, CA 90645</dd>
			      		</dl>
					</div>
					
			      	<form>
			      		<div class="row">
			      			<div class="col-sm-4">
			      				<label for="provider">Assign this job to:</label>
			      			</div>
			      			<div class="col-sm-8">
			      				<select id="assigned-provider" name="assigned-provider" title="Select a provider for this job">
					      			<option <?php if (!( isset($assignedPro) )) { echo 'selected="selected" '; } ?> value=""></option>
					      			<option <?php if ( isset($assignedPro) ) { echo 'selected="selected" '; } ?> value="12345">Tim Taylor (about 3.5 miles away)</option><!-- Mile distance should be rounded to nearest .5 -->
					      			<option value="22345" >Paul Blart (about 5.5 miles away)</option>
					      			<option value="32345" >Mark Blart (about 8 miles away)</option>
					      			<option value="42345" >Eugene Blart Jr. (about 8 miles away)</option>
					      			<option value="52345" >Mordecai Blart (about 10 miles away)</option>
					      		</select>
			      			</div>
	                	</div><!-- /.row -->
	              	</form>
			<?php include 'includes/details/modal-footer.php'; ?>