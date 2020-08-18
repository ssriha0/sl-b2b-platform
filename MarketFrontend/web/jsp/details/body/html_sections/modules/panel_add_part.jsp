<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard.css" />
<script type="text/javascript">
jQuery(document).keypress(function (e) {
     if (e.which == 13) {
   	   searchParts();
       return false;
	 }
});
</script>
<style>
.add_part_head {
 padding-bottom:5px;		
 margin left:2px;
background:#58585A url(../images/widgets/titleBarBg.gif) no-repeat scroll right top;
background-position:right; 
font-weight:bolder;
height:12px;
padding:5px 4px 0;
border-color:#CDDDE9 #BFBFBF #BFBFBF;
border-style:solid;
position:relative;
z-index:1;
border-width:0;
font-family:Verdana,Arial,Helvetica,sans-serif;
font-size:10px;
padding-top:0;
color:#FFFFFF;
font-family:Verdana,Arial,Helvetica,sans-serif;
font-weight:bolder;
margin-top:0;
vertical-align:top;

}
</style>
</head>
<body>

	<div id="addPartDisplay" style="z-index: 3000; display: none;"
		class="jqmWindow jqmID1">
		<form id="addForm" name="addForm">
			<input type="hidden" name="editPartVO.soId"
				value="${SERVICE_ORDER_ID}" id="partSoId">			
				<tr>
				<div class="add_part_head"
					style="border-width: 1px; border-style: solid; border-color: rgb(128, 128, 128); position: relative; margin-left: -10px; margin-top: -10px; width: 102%; height: 18px;">
					Add Part <img style="float: right; cursor:pointer; height:16px; width:16px;"
						src="${staticContextPath }/images/widgets/tabClose.png" 
						onclick="closeAddPart();">
				</div>
			</tr>	
			<font>Add the part(s) needed to complete this service by entering the part number(s). Select the matching part(s) searched from Sears Parts Direct.</font> <br>
			<div id="partNumSearchDiv" style="z-index: 3000; display: block;">
			<div id="errorDivParts"
				style="margin-left: 5px;padding-left: 30px;width: 90%;border: 2px solid #F5A9A9;display:none;padding-top:2px;
				background: url('${staticContextPath}/images/icons/50.png') no-repeat scroll 10px 1px #FBE3E4;"></div>

			<table cellspacing="10px" width="100%" border="0px">
				<tr></tr>
				<tr></tr>
				<tr>
					<td width="30%"><div
							style="align: left; font-weight: bold; font-size: 11px;" >
							Enter Part No</div></td>

					<td width="10%">
						<div style="padding-bottom: 10px">
							<input type="text" style="width: 120px; "
								name="editPartVO.partNumber" maxlength="25" value=""
								class="shadowBox grayText" id="partNumber" /> 
								<input
								type="hidden" id="partNumberHidden"
								value="${editPartVO.partNumber}" name="editPartVO.partNumber" />
						</div>
					</td>
					<td width="10%"><input type="button" id="searchButton" class="newButton"
						style=" margin-top: -5px;"
						onclick="searchParts();" value="SEARCH" ></input></td>

				</tr>
				<tr>
				</tr>
			</table>
		</div>
		<div id="AddPartModal">
		
		</div>
	</div>
	</form>
</body>
</html>
