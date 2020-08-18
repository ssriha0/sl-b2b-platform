



<div class="leftTileItem" id="dbTile_financeMgr">

	<div class="titleContainer">
	    <div class="titleBar"><h2>ServiceLive Wallet</h2> <a class="moreLink" href="${contextPath}/financeManagerController_execute.action">View &raquo;</a> </div>
	</div>
	
	<div class="contentContainer">
		<div class="content">
			<table class="balances" cellpadding="0" cellspacing="0">
				<tr>
					<td class="column1">
						Available Balance:
					</td>
					<td class="column2">
						<span id="available_balance">${dashboardDTO.availableBalanceFormat}</span>
					</td>
				</tr>
				<tr>
					<td class="column1 bottom">
						Current Balance:
					</td>
					<td class="column2 bottom">
						<span id="current_balance">${dashboardDTO.currentBalanceFormat}</span>
					</td>
				</tr>
			</table>

		</div>
	</div>
	<div class="shadowBottom"></div>
	
</div>
