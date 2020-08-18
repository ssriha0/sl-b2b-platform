<style>
	#mmh-form label { display: block; clear: left; margin: 2px 0;}
	#mmh-form label span { margin-right: 10px; color: #fff; display:block; float: left; clear: left; width: 150px; text-align: right; font-weight: bold;}
	#mmh-form input { font-size: 10px; }
</style>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div class="modalContent">
	<img src="${staticContextPath}/images/marketing/manage_my_home_logo.png"> 
</div>
	<div style="background: #C41F23; margin: 10px 0; padding: 10px;text-align: left;">
		<p style="background: #F8F8F8; border: 1px solid #DDD; padding: 10px; margin: 10px 30px;">
		Signup for ManageMyHome at the same time as ServiceLive and receive instant access to: 
			<br/>&nbsp;&nbsp;- Create a secure home inventory
			<br/>&nbsp;&nbsp;- Access and track Protection Agreements
			<br/>&nbsp;&nbsp;- Get a custom home maintenance plan
			<br/>&nbsp;&nbsp;- Plan and track your home projects
			<br/>&nbsp;&nbsp;- Get personalized expert advice
			<br/>&nbsp;&nbsp;.... and more!
		</p>
		
		<div id="mmh-form" style="padding: 10px;">
			<div>
				<label>
					<span>Zip code *</span>
					<input type="text" name="user.maintZipCode" size="5" maxlength="5" value="" id="maintZipCode" />			
				</label>
			</div>
			<div>
				<label>
					<span>Email address *</span>
					<input type="text" name="user.email" maxlength="50" value="" id="email" />
				</label>
			</div>
			<div>
				<label>
					<span>Password *</span>
					<input type="password" name="user.password" size="32" maxlength="32" value="" id="passwordID" />
				</label>
			</div>
			<div>
				<label>
					<span>Confirm password *</span>
					<input type="password" name="confirmPassword" maxlength="32" value="" id="confirmPassword" />
				</label>
			</div>
			<div>
				<label>
					<span>Create screen name</span>
					<input type="text" name="user.screenName" value="" id="userScreenName" />
				</label>
			</div>
		</div>
	</div>
<div class="modalContent">
	<input type="image" src="${staticContextPath}/images/simple/button-send.png" />
	<br /><a href="#" class="jqmClose" style="color: #333;">No thanks</a>
</div>
