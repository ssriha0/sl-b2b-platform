			<div class="tab-pane active" id="history">
              <div class="table-responsive">
                <table>
                  <thead>
                    <tr>
                      <th>Date/Time</th>
                      <th>Description</th>
                      <th>User</th>
                    </tr>
                  </thead>
                  <tbody>
					<?php 
						if (isset($historyCompleted)) { 
							echo $historyCompleted; 
						}
						else if (isset($historyCancelled)) {
							echo $historyScheduled;
						}

						if (isset($historyScheduled)) { echo $historyScheduled; }
					?>
                  	<?php  ?>
                    <tr>
                      <td><time datetime="2013-11-21T13:37:17"><span>Dec 1, 2013</span> 9:15 AM</time></td>
                      <td>Substatus Changed: Customer unavailable</td>
                      <td>Bob F.</td>
                    </tr>
                    <tr>
                      <td><time datetime="2013-11-21T13:37:17"><span>Dec 1, 2013</span> 9:15 AM</time></td>
                      <td>Status Changed: <span class="label leadStatus-2"><i class="icon-leadStatus-2"></i> Working</span> Customer requested quote. Lorem ipsom dolor extra text to make this break to two lines.</td>
                      <td>Bob F.</td>
                    </tr>
                    <tr>
                      <td><time datetime="2013-11-21T13:37:17"><span>Dec 1, 2013</span> 8:45 AM</time></td>
                      <td>Lead was received</td>
                      <td>n/a</td>
                    </tr>
                  </tbody>
                </table>
              </div><!-- /.table-responsive -->

            </div><!-- #history -->