
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />



<div dojoType="dijit.layout.LayoutContainer"
	style="width: 100%; height: 100%">

	<div dojoType="dijit.layout.ContentPane" layoutAlign="top"
		style=" padding-top: 20px; padding-left: 10px;"
		width="100%">
		<div class="gridFilter">
			Status Filter
			<select class="dropdown-80" "">
				<c:forEach var="option" items="${statusFilterList}">
					<option id="">
						${option}
					</option>
				</c:forEach>
			</select>
		</div>
		<div class="gridFilter">
			Sub-status Filter:
			<select>
				<c:forEach var="option" items="${subStatusFilterList}">
					<option id="">
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
			
		

	</div>
	

	<div dojoType="dijit.layout.ContentPane" layoutAlign="client"
		style=" padding-top: 20px; padding-left: 10px; padding-right: 10px; width:900px">
		<iframe id="iframeID" name="iframeID" src="/MarketFrontend/market/updateDataGrid.action" width="920px" height="400" FRAMEBORDER=0></iframe>
	</div>


</div>
