<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html> 
<head>
<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome-ie7.min.css" rel="stylesheet" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery.simplemodal.1.4.4.min.js"></script>

<style>
table .historyTable{
    border-collapse: collapse;
   
}
table.historyTable th{
	height: 30px;
	text-align: left;
}
table.historyTable td, table.historyTable th {
    border: 1px solid #CCCCCC;
}
table.historyTable td{
	padding: 5px;
	text-align: left;
}
</style>
</head>

<body>


<p style="float: left;margin-left: 5px;"><b>Background Check Certification History</b></p></br></br>
<div id="remove" style="padding: 5px; color: #000000;">
<i class="icon-remove"
style="font-size: 15px; position: absolute; right: 5px; cursor: pointer; top: 5px;"
onclick="closeModal('displayHistoryDiv');">Close</i></div>

<div id ="providerName" style="float: left;font-weight: bold;margin-left: 5px;">${providerName} (User Id# ${resourceId})</div></br>

<div id ="currentStatus" style="float: left;font-weight: normal;margin-left: 5px;">Current Status:<span style="font-weight: bold;">${backgroundState}</span></div></br>
<div id="bgHistory" style="overflow-y: auto; overflow-x: hidden;margin: 20px 10px 10px;height:300px;">
<table cellpadding="0" cellspacing="0" class="historyTable">
<thead>
<tr>
<th width="10%">Date</th>
<th width="17%">Background Check Status</th>
<th width="17%">Last Certification Date</th>
<th width="10%">Recertification Due Date</th>
<th width="13%">Recertification Status</th>
<th width="35%">Comment</th>
</tr>

<c:if test="${not empty formattedHistList}"> 
	<c:forEach var="items1" items="${formattedHistList}">     
      <tr>
        <td width="10%">${items1.fmtDisplayDate}</td>
        <td width="17%">${items1.backgroundStatus}</td> 
        <td width="17%">${items1.fmtVerificationDate}</td>
        <td width="10%">${items1.fmtReverificationDate}</td>
        <td width="13%">${items1.recertificationStatus}</td>
        <td width="35%">${items1.changingComments}</td>
      </tr> 
	</c:forEach> 
</c:if>
 
<c:if test="${empty formattedHistList}"> 
	   	<tr>
	   		<td colspan="6">
	   			<p>No Records Found.</p>
	   		</td>
	   	</tr>
</c:if>

</thead>
</table>
</div>
</body>

</html>
