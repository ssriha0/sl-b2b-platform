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
					<dl>
						<dt><b>When:</b></dt>
						<dd><time datetime="2013-11-21T13:37:17">Tuesday Dec 3, 2013 8:00 AM-12:00 PM</time></dd>
						<dt><b>Where:</b></dt>
						<dd>Sudden Valley, CA 90645</dd>
			      	</dl>
			      	<form>
				      	<label for="provider">Assign this job to:</label>
	                    <div class="btn-group-vertical full" data-toggle="buttons">
	                        <label class="btn btn-default<?php if ( isset($assignedPro) ) { echo '  active'; } ?>" for="ID-12345">
	                            <input type="radio" name="provider" value="12345" id="ID-12345"<?php if ( isset($assignedPro) ) { echo ' checked'; } ?>> Tim Taylor <small>about 3.5 miles away</small> <!-- Mile distance should be rounded to nearest .5 -->
	                        </label>
	                        <label class="btn btn-default" for="ID-12345">
	                            <input type="radio" name="provider" value="12345" id="ID-12345"> Paul Blart <small>about 5.5 miles away</small>
	                        </label>
	                        <label class="btn btn-default" for="ID-22345">
	                            <input type="radio" name="provider" value="22345" id="ID-22345"> Mark Blart <small>about 8 miles away</small>
	                        </label>
	                        <label class="btn btn-default" for="ID-32345">
	                            <input type="radio" name="provider" value="32345" id="ID-32345"> Eugene Blart Jr. <small>about 8 miles away</small>
	                        </label>
	                        <label class="btn btn-default" for="ID-42345">
	                            <input type="radio" name="provider" value="42345" id="ID-42345"> Mordecai Blart <small>about 10 miles away</small>
	                        </label>
	                    </div>
	              	</form>
			<?php include 'includes/details/modal-footer.php'; ?>