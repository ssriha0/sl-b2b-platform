<?php include 'templates/header.php'; ?>

  <div id="leads-list">

      <header>
          <div class="col-sm-8 col-md-3">
              <h1>Active Leads</h1> <span class="badge tooltip-target" data-toggle="tooltip" data-placement="bottom" data-original-title="Active leads">6</span>
              <span class="glyphicon glyphicon-search visible-xs" data-toggle="collapse" data-target="#search-wrap"></span>
          </div>

          <div id="search-wrap" class="collapse col-sm-4">
              <input class="search form-control" placeholder="Search" type="search" />
              <span class="glyphicon glyphicon-search"></span>
          </div><!-- / #search-wrap -->

          <div class="filter-wrap col-md-5">
              <div class="filter btn-group btn-group-justified" data-toggle="buttons">
                  <label class="btn btn-default active" id="filter-all">
                      <input type="radio" name="options"> <i class="icon-all"></i> <span>All</span>
                  </label>
                  <label class="btn btn-default" id="filter-2">
                      <input type="radio" name="options"> <i class="icon-leadStatus-1"></i> <span>New</span>
                  </label>
                  <label class="btn btn-default" id="filter-3">
                   <input type="radio" name="options"> <i class="icon-leadStatus-2"></i> <span>Working</span>
                  </label>
                  <label class="btn btn-default" id="filter-4">
                      <input type="radio" name="options"> <i class="icon-leadStatus-3"></i> <span>Scheduled</span>
                  </label>
              </div><!-- / .filter -->
          </div>    
      </header>


      <div class="row list-body">
          <ul class="list-group list">
              <!-- set [data-leadid] to target a specific lead's details page -->
              <li class="list-group-item" data-leadid="12345">
                  <div class="row">
                      
                      <div class="col-xs-11 col-md-4">
                        <h2>
                          <span class="name">Bob Loblaw</span> <small class="leadid">Service Request #12345</small>
                        </h2> 
                        <div class="lead-info">
                          <span class="projectType">Install/repair water softener</span> | <span class="skill">Repair</span>
                        </div>
                        <div class="cust-info">
                          <span class="label label-info phone">847-123-4567</span> <span class="city">Palatine</span>, <span class="zip">60067</span>
                        </div>
                      </div>
                      <div class="service-details visible-md visible-lg col-md-6">
                        <h3>Service Details</h3> 
                        <!-- Only pull in first 230 characters of description; after that it should end at the nearest word or ' ' and insert ellipses '...' -->
                        <p>Replacing hardwood floor and fixing water damage</p>
                      </div>
                      <div class="visible-md visible-lg col-md-2">
                        <a href="lead-12345-details.html" class="btn btn-main" role="button">View Full Details</a>
                      </div>

                      <i class="glyphicon glyphicon-chevron-right hidden-md hidden-lg"></i>
                      <!-- Only appears on same day or next day orders -->
                      <div class="urgency">Urgent</div>
                      <div class="secondary-info">
                        <!-- DateTime is when the lead was received by the provider.
                             Uses microformat YYYY-MM-DDTHH:MM:SS, can also adjust for timezone if needed.
                             See http://microformats.org/wiki/datetime-design-pattern and http://html5doctor.com/the-time-element/ -->
                        <time class="timeago pull-right" datetime="2013-11-21T13:37:17">Nov 21, 2013 1:36 PM</time>
                        <!-- .leadStatus value must be lowercase for filters to work -->
                        <i class="icon-leadStatus-1"></i> <span class="leadStatus">new</span>
                      </div>
                  </div>
              </li>
              <li class="list-group-item" data-leadid="11111">
                  <div class="row">
                      <div class="col-xs-11 col-md-4">
                        <h2>
                          <span class="name">George Michael Bluth</span> <small class="leadid">Service Request #11111</small>
                        </h2> 
                        <div class="lead-info">
                          <span class="projectType">Fix pipe leak</span> | <span class="skill">Repair</span>
                        </div>
                        <div class="cust-info">
                          <span class="label label-info phone">847-544-4235</span> <span class="city">Hoffman Estates</span>, <span class="zip">61147</span>
                        </div>
                      </div>
                      <div class="service-details visible-md visible-lg col-md-6">
                        <h3>Service Details</h3> 
                        <p>I may need a window replacement</p>
                      </div>
                      <div class="visible-md visible-lg col-md-2">
                        <a href="lead-11111-details.html" class="btn btn-main" role="button">View Full Details</a>
                      </div>

                      <i class="glyphicon glyphicon-chevron-right hidden-md hidden-lg"></i>
                      <div class="secondary-info">
                        <time class="timeago pull-right" datetime="2013-11-20T13:37:17">Nov 20, 2013 1:36 PM</time>
                        <i class="icon-leadStatus-2"></i> <span class="leadStatus">working</span>
                      </div>
                  </div>
              </li>
              <li class="list-group-item" data-leadid="12345">
                  <div class="row">
                      
                      <div class="col-xs-11 col-md-4">
                        <h2>
                          <span class="name">Tobias Funke</span> <small class="leadid">Service Request #12345</small>
                        </h2> 
                        <div class="lead-info">
                          <span class="projectType">Install/repair faucet</span> | <span class="skill">Install</span>
                        </div>
                        <div class="cust-info">
                          <span class="label label-info phone">773-455-6969</span> <span class="city">Chicago</span>, <span class="zip">60657</span>
                        </div>
                      </div>
                      <div class="service-details visible-md visible-lg col-md-6">
                        <h3>Service Details</h3> 
                        <p>We are looking to get 2 quotes for some warranty work that needs to be done as soon as possible.  The details are as follows -- Approximately 1100 square feet of concrete flooring that needs to be ground back to the...</p>
                      </div>
                      <div class="visible-md visible-lg col-md-2">
                        <a href="lead-12345-details.html" class="btn btn-main" role="button">View Full Details</a>
                      </div>

                      <i class="glyphicon glyphicon-chevron-right hidden-md hidden-lg"></i>
                      <div class="urgency">Urgent</div>
                      <div class="secondary-info">
                        <time class="timeago pull-right" datetime="2013-11-19T15:37:17">Nov 19, 2013 3:37 PM</time>
                        <i class="icon-leadStatus-3"></i> <span class="leadStatus">scheduled</span>
                      </div>
                  </div>
              </li>
              <li class="list-group-item" data-leadid="12345">
                  <div class="row">
                      
                      <div class="col-xs-11 col-md-4">
                        <h2>
                          <span class="name">Michael Bluth</span> <small class="leadid">Service Request #12345</small>
                        </h2> 
                        <div class="lead-info">
                          <span class="projectType">Install/repair toilet</span> | <span class="skill">Install</span>
                        </div>
                        <div class="cust-info">
                          <span class="label label-info phone">847-876-0095</span> <span class="city">Palatine</span>, <span class="zip">60067</span>
                        </div>
                      </div>
                      <div class="service-details visible-md visible-lg col-md-6">
                        <h3>Service Details</h3> 
                        <p>Remove and replace bent bollard in loading dock</p>
                      </div>
                      <div class="visible-md visible-lg col-md-2">
                        <a href="lead-12345-details.html" class="btn btn-main" role="button">View Full Details</a>
                      </div>

                      <i class="glyphicon glyphicon-chevron-right hidden-md hidden-lg"></i>
                      <div class="secondary-info">
                        <time class="timeago pull-right" datetime="2013-11-15T15:37:17">Nov 15, 2013 3:37 PM</time>
                        <i class="icon-leadStatus-1"></i> <span class="leadStatus">new</span>
                      </div>
                  </div>
              </li>
              <li class="list-group-item" data-leadid="12345">
                  <div class="row">
                      
                      <div class="col-xs-11 col-md-4">
                        <h2>
                          <span class="name">Maggie Lizer</span> <small class="leadid">Service Request #12345</small>
                        </h2> 
                        <div class="lead-info">
                          <span class="projectType">Install/repair water heater</span> | <span class="skill">Install</span>
                        </div>
                        <div class="cust-info">
                          <span class="label label-info phone">847-776-9214</span> <span class="city">Rolling Meadows</span>, <span class="zip">60059</span>
                        </div>
                      </div>
                      <div class="service-details visible-md visible-lg col-md-6">
                        <h3>Service Details</h3> 
                        <p>We'd like to potentially extend a gas line that is protruding from our wall ( &amp; currently capped) into our fireplace. The gap between the line and the fireplace is about 12 inches. Then a hole would need to be drilled into </p>
                      </div>
                      <div class="visible-md visible-lg col-md-2">
                        <a href="lead-12345-details.html" class="btn btn-main" role="button">View Full Details</a>
                      </div>

                      <i class="glyphicon glyphicon-chevron-right hidden-md hidden-lg"></i>
                      <div class="secondary-info">
                        <time class="timeago pull-right" datetime="2013-11-14T15:37:17">Nov 14, 2013 3:37 PM</time>
                        <i class="icon-leadStatus-2"></i> <span class="leadStatus">working</span>
                      </div>
                  </div>
              </li>
              <li class="list-group-item" data-leadid="12345">
                  <div class="row">
                      
                      <div class="col-xs-11 col-md-4">
                        <h2>
                          <span class="name">Lucille Bluth</span> <small class="leadid">Service Request #12345</small>
                        </h2> 
                        <div class="lead-info">
                          <span class="projectType">Repair bathtub or shower</span> | <span class="skill">Repair</span>
                        </div>
                        <div class="cust-info">
                          <span class="label label-info phone">847-434-9986</span> <span class="city">Schaumburg</span>, <span class="zip">60076</span>
                        </div>
                      </div>
                      <div class="service-details visible-md visible-lg col-md-6">
                        <h3>Service Details</h3>
                        <!-- If Service Details field == '' , display this message with class .no-details --> 
                        <p class="no-details">Not specified.</p>
                      </div>
                      <div class="visible-md visible-lg col-md-2">
                        <a href="lead-12345-details.html" class="btn btn-main" role="button">View Full Details</a>
                      </div>

                      <i class="glyphicon glyphicon-chevron-right hidden-md hidden-lg"></i>
                      <div class="urgency">Urgent</div>
                      <div class="secondary-info">
                        <time class="timeago pull-right" datetime="2013-11-10T15:37:17">Nov 10, 2013 3:37 PM</time>
                        <i class="icon-leadStatus-3"></i> <span class="leadStatus">scheduled</span>
                      </div>
                  </div>
              </li>
              
          </ul>

          <div class="list-group-item" id="emptyView">
            <p>No search results found.</p>
          </div>
      </div><!-- /.row.list-body -->
  </div><!-- /#leads-list -->
  
  <div class="list-footer row">
      <div class="btn-group btn-group-justified">
        <a role="button" class="btn btn-primary active">Active Leads</a>
        <a role="button" class="btn btn-primary" href="leads-dashboard-inactive.php">Inactive Leads</a>
      </div>
  </div>



<?php include 'templates/footer.php'; ?>