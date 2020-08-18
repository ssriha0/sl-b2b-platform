<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
			<!-- START HEADER -->
			<div id="header">				
				<div id="logo">
					<a href="/MarketFrontend/" title="ServiceLive"><img src="${staticContextPath}/images/simple/simple.png" alt="ServiceLive"/></a>
				</div>
				<div id="topNav">
					<div class="smallText">
						Welcome, Rhett Butler! <em>#68</em> <img src="${staticContextPath}/images/common/full_star.gif" alt="" /><img src="${staticContextPath}/images/common/full_star.gif" alt="" /><img src="${staticContextPath}/images/common/full_star.gif" alt="" /><img src="${staticContextPath}/images/common/full_star.gif" alt="" /><img src="${staticContextPath}/images/common/full_star.gif" alt="" />
						<br /><a href="http://community.servicelive.com/" title="Community">Community</a> | 
						<a href="http://blog.servicelive.com" title="Blog">Blog</a> | 
						<a href="/MarketFrontend/doLogout.action" title="Logout">Logout</a>
					</div>
				</div>
				<!-- begin simple header -->				
				<div id="mysl" class="clearfix">
					<ul id="simpleMenu">
						<li class="first"><a href="/MarketFrontend/" title="home">Dashboard</a></li>
						<li class="hasChild"><a href="" title="services">Services</a>
							<ul class="childMenu">
								<li><a href="#">Service Order Monitor</a></li>
								<li><a href="#">Create New Service Order</a></li>
								<li><a href="#">Find Services</a></li>
							</ul>
						</li>
						<li class="hasChild"><a href="#" title="Funding">Funding</a>
							<ul class="childMenu">
								<li><a href="#">Balances</a></li>
								<li><a href="#">Manage Funding</a></li>
							</ul>
						</li>
						<li class="hasChild"><a href="#" title="account">Account</a>
							<ul class="childMenu">
								<li><a href="#">My Information</a></li>
								<li><a href="#">Manage Locations</a></li>
							</ul>							
						</li>
					</ul>
					<div class="main clearfix">
						<div id="newSo"><a href="#">Create New Service Order</a></div>
					</div>
					<div id="servicefinder">
						<input type="text" class="sf clearValue" value="Search For Services" />
						<img src="${staticContextPath}/images/simple/button-servicefinder.png" alt="" />
					</div>					
				</div>	
				<!-- end simple header -->
