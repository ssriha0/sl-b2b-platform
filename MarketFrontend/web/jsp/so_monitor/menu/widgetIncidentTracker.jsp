<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="theTab" scope="request" value="<%=request.getAttribute("tab")%>" />

<div dojoType="dijit.TitlePane" title="Incident Tracker" id="widget_inc_tracker_${theId}"
	style="padding-top: 1px; width: 249px;" open="false">
	<span class="dijitInfoNodeInner"><a href=""></a> </span>
	<div class="dijitReset incidentTable">
			<table border="0" cellspacing="0" cellpadding="0">
				<thead>
					<th class="first">Age</th>
					<th>Incident</th>
					<th>Service Order</th>
				</thead>
				<tr>
					<td colspan=3 id="incident_data${theTab}">&nbsp;
					</td>
				</tr>
			</table>
	</div>
</div>
