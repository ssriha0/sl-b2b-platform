	<div dojoType="dijit.layout.ContentPane" layoutAlign="top"
		style=" padding-top: 20px; padding-left: 10px;"
		width="100%">
		<div class="gridFilter">
			Status Filter
			
			<select class="dropdown-80" "">
					<option id="all" onclick="filterStatus('all')">
						Select
					</option>
				<c:forEach var="option" items="${statusFilterList}">
					<option id="${option}" onclick="filterStatus('${option}')">
						${option}
					</option>
				</c:forEach>
			</select>
		</div>
		<div class="gridFilter">
			Sub-status Filter:
			<select>
					<option id="all" onclick="filterSubStatus('all')">
						Select
					</option>
				<c:forEach var="option" items="${subStatusFilterList}">
					<option id="${option}" onclick="filterSubStatus('${option}')">
						${option}
					</option>
				</c:forEach>
			</select>
		</div>
		<div class="clear"></div>
		<div style="position: relative">
			<table class="grid-table" cellpadding="0" cellspacing="0" width="680px">
				<thead>
					<tr bgcolor="#cccccc">						
						<td class="column1">
							View<br>
							Details
						</td>
						<td class="column2">
							<a href="javascript:doSort('c_status')" class="arrowUp">Status</a>
						</td>
						<td class="column3">
							<a href="javascript:doSort('c_soNumber')" class="arrowUp">Service Order #</a>
						</td>
						<td class="column4">
							<a href="javascript:doSort('c_title')" class="arrowUp">Title</a>
						</td>
						<td>
							<a href="javascript:doSort('c_serviceDate')" class="arrowUp">Service Date</a>
						</td>
						<td>
							<a href="javascript:doSort('c_location')" class="arrowUp">Location</a>
						</td>						
					</tr>
				</thead>
				
			</table>
		</div>
