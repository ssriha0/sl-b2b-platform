
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
			<h2>Overall Status Monitor</h2>
		</div>
	</div>
	<div class="contentContainer">
		<div class="content">
			<p>
				<strong>Profile Changes:</strong>
				<span  class="lightBlue">
					${dashboardDTO.profileChanges}
				</span>
			</p>
			<p class="last">
				<strong>Issues:</strong>
				<span  class="lightBlue">
					${dashboardDTO.numIssues}
				</span>
			</p>
		</div>
	</div>
	<div class="shadowBottom"></div>
</div>
