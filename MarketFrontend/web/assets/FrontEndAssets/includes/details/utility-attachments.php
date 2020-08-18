            <div class="tab-pane" id="attachments">
				<section>
					<!--<a href="#" class="btn btn-default full btn-add-attachment" role="button"><i class="glyphicon glyphicon-file"></i> Add Attachment</a>-->
					<div class="row" id="widget-add-attachment">

					  <form class="col-xs-12">
					    
						<input type="hidden" id="MAX_FILE_SIZE" name="MAX_FILE_SIZE" value="10485760" /><!-- set file size limit at 10 MB (discuss) -->
						<small class="pull-right">10 MB maximum file size</small>
					    <label for="fileselect"><i class="glyphicon glyphicon-paperclip"></i> Attach Files</label>
					    <input type="file" id="fileselect" name="fileselect[]" multiple>
						
					    <div class="row">
					    	<div class="col-sm-1">
								<button type="submit" class="btn btn-default full margin-top">Upload</button>
							</div>
							<div class="col-sm-11">
								<div class="progress progress-striped active">
								  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
								    
								  </div>
								</div>
							</div>
						</div>


					  </form>

					</div><!-- /#widget-add-attachment -->
				</section>
				<ul>
					<li class="file">
						<a href="#" class="update-target tooltip-target" data-placement="left" data-original-title="Delete File">
						<i class="glyphicon glyphicon-trash"></i>
						</a>
						<h3><a href="#">loblaw-quote-11-30-13.pdf</a></h3>
						<div class="secondary-info">
							<span class="pull-right">Posted by Bob F.</span>
							<b>56 KB</b> <time datetime="2013-11-30T13:37:17">Nov 30, 2013 1:37 PM</time>
						</div>  
					</li><!-- /.file -->

					<li class="file">
						<a href="#" class="update-target tooltip-target" data-placement="left" data-original-title="Delete File">
						  <i class="glyphicon glyphicon-trash"></i>
						</a>
						<h3><a href="#">loblaw_invoice.docx</a></h3>
						<div class="secondary-info">
							<span class="pull-right">Posted by Bob F.</span>
							<b>1.1 MB</b> <time datetime="2013-12-12T11:21:17">Dec 12, 2013 11:21 PM</time>
						</div>  
					</li><!-- /.file -->

					<li class="file">
						<a href="#" class="update-target tooltip-target" data-placement="left" data-original-title="Delete File">
						  <i class="glyphicon glyphicon-trash"></i>
						</a>
						<h3><a href="#">bathroom.jpg</a></h3>
						<div class="secondary-info">
							<span class="pull-right">Posted by Bob F.</span>
							<b>2.6 MB</b> <time datetime="2013-11-24T15:34:17">Nov 24, 2013 3:45 PM</time>
						</div>  
					</li><!-- /.file -->
				</ul>
            </div><!-- #attachments -->