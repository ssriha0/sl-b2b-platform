
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/iehacks.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/dashboard.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/tooltips.css" />


<div class="leftTileItem" id="dbTile_overallStatusMonitor">
	<div class="titleContainer">
		<div class="titleBar">
			<h2>ServiceLive Status Monitor</h2>
		</div>
	</div>
	<div class="contentContainer">
		<div class="content">
			<p>
				<strong>Firm Registration Status</strong><br />
				<span  class="lightBlue">${providerFirmDetails.firmStatus}</span>
			</p>
			
			<p>
				<strong>Provider Registration Status</strong><br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>Approved:</strong>
				<span class="lightBlue"> ${providerFirmDetails.numTechniciansApproved}</span><br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<strong>Unapproved:</strong>
				<span class="redAlert"> ${providerFirmDetails.numTechniciansUnapproved}</span>
			</p>
			<p>
				<strong>Provider Background Checks</strong><br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not Started: ${providerFirmDetails.bcNotStarted}<br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pending Submission: ${providerFirmDetails.bcPendingSubmission}<br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;In Progress: ${providerFirmDetails.bcInProcess}<br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Not Cleared: ${providerFirmDetails.bcNotCleared}<br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Clear: ${providerFirmDetails.bcClear}<br />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Re-Certification Due:<span class="redAlert"> ${providerFirmDetails.bcRecertificationDue}</span>

			</p>
			
		</div>
	</div>
	<div class="shadowBottom"></div>
</div>
