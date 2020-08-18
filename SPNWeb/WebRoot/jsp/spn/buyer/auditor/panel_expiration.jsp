<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
<head>

<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/spn-auditor.css" media="screen, projection">
<script type="text/javascript">
	
	//when user clicks on view all
	function viewAll(provId, spnId){
		var id = provId+'_'+spnId;
		$('#displayDiv_'+id).hide();	
		$('#loadingDiv_'+id).show();
		$('#'+id).load('spnAuditorApplicantsTab_displayExpirationPanelAjax.action', { 'expandCriteriaVO.providerFirmId': provId, 'expandCriteriaVO.networkId': spnId },function(){
			$('#loadingDiv_'+id).hide();
			$('#displayDiv_'+id).show();
			$('#viewAll_'+id).hide();
			$('#defaultView_'+id).show();
			$('#spnLabel_'+id).hide();
			$('#spnList_'+id).show();
		});
	}
	
	//when user clicks on default view 
	function viewDefault(provId, spnId){
		var id = provId+'_'+spnId;
		$('#displayDiv_'+id).hide();	
		$('#loadingDiv_'+id).show();	
		$('#'+id).load('spnAuditorApplicantsTab_displayExpirationPanelAjax.action', { 'expandCriteriaVO.networkId': spnId, 'expandCriteriaVO.spnId': spnId, 'expandCriteriaVO.providerFirmId': provId },function(){
			$('#loadingDiv_'+id).hide();
			$('#displayDiv_'+id).show();
			$('#defaultView_'+id).hide();
			$('#viewAll_'+id).show();
			$('#spnList_'+id).hide();
			$('#spnLabel_'+id).show();
		});
	}
	
	//filtering based on spn & requirement type	
	function sort(provId, spnId){
		var id = provId+'_'+spnId;
		var requirement = $('#reqType_'+id).val();
		if('-1' == requirement){
			requirement = null;
		}
		if($('#spnLabel_'+id).is(':visible')){
			$('#dispSubDiv_'+id).hide();	
			$('#loadSubDiv_'+id).show();
			$('#'+id).load('spnAuditorApplicantsTab_displayExpirationPanelAjax.action', { 'expandCriteriaVO.networkId': spnId, 'expandCriteriaVO.spnId': spnId, 'expandCriteriaVO.providerFirmId': provId, 'expandCriteriaVO.requirementType': requirement},function(){
				$('#loadSubDiv_'+id).hide();
				$('#dispSubDiv_'+id).show();
				if(null == requirement){
					requirement = '-1';
				}
				$('#reqType_'+id).val(requirement);
			});	
		}
		else{
			var spn = $('#spnList_'+id).val();
			if('-1' == spn){
				spn = null;
			}	
			$('#displayDiv_'+id).hide();	
			$('#loadingDiv_'+id).show();
			$('#'+id).load('spnAuditorApplicantsTab_displayExpirationPanelAjax.action', { 'expandCriteriaVO.networkId': spnId, 'expandCriteriaVO.spnId': spn, 'expandCriteriaVO.providerFirmId': provId, 'expandCriteriaVO.requirementType': requirement},function(){
				$('#loadingDiv_'+id).hide();
				$('#displayDiv_'+id).show();
				$('#viewAll_'+id).hide();
				$('#defaultView_'+id).show();
				$('#spnLabel_'+id).hide();
				$('#spnList_'+id).show();
				if(null == spn){
					spn = '-1';
				}
				if(null == requirement){
					requirement = '-1';
				}
				$('#reqType_'+id).val(requirement);
				$('#spnList_'+id).val(spn);
			});	
		}
	}	
	
</script>

</head>

<body>
<div id="expiryDetails">

	<div class="clearfix titlebar">
			<div class="left">
				<strong><s:property value="businessName" /> </strong> #
				<s:property value="firmID" />
			</div>
	</div>
	
	<div class="tableWrap" id="displayDiv_${firmID}_${networkId}">
		<br>
		<div  style="border: 1px solid #CCCCCC">
			
			<table border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="15%" style="border-bottom: 0px;">				
						<input id="viewAll_${firmID}_${networkId}" class="button action submit view" type="button"
								onclick="viewAll('${firmID}','${networkId}');"
								style="text-transform: none;font-size: 12px;" value="View all"/>
						<input id="defaultView_${firmID}_${networkId}" class="button action submit view" type="button"
								onclick="viewDefault('${firmID}','${networkId}');"
								style="display: none;text-transform: none;font-size: 12px;" value="Default View"/>
					</td>
					<td style="border-bottom: 0px;">
						<div style="border: 1px solid #CCCCCC; width:99%;">
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td rowspan="2" style="font-size: 13px;border-bottom: 0px;">
									<b>Filter by :</b>
								</td>
								<td style="border-bottom: 0px;">
									<table border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td style="border-bottom: 0px;"><b>SPN :</b></td>
											<td style="border-bottom: 0px;">
												<div id="spnLabel_${firmID}_${networkId}" class="spn">
													<b><s:property value="networkName" /></b>
												</div>
												<select id="spnList_${firmID}_${networkId}" style="width: 450px;display: none;" onchange="sort('${firmID}','${networkId}');">
														<option value="-1">Select One</option>
														<c:forEach items="${providerSPN}" var="spn">
															<option value="${spn.value}";>${spn.key}</option>												
														</c:forEach>
												</select>
											</td>
										</tr>
										<tr>
											<td style="border-bottom: 0px;"><b>Requirement Type :</b></td>
											<td style="border-bottom: 0px;">
												<select id="reqType_${firmID}_${networkId}" style="width: 450px;" onchange="sort('${firmID}','${networkId}');">
													<option value="-1">Select One</option>
													<c:forEach items="${requirementTypes}" var="type">
														<option value="${type.value}";>${type.key}</option>												
													</c:forEach>
												</select>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table> 
						</div>
					</td>
				</tr>
			</table>
			
		<div id="dispSubDiv_${firmID}_${networkId}" class="scroll">
			<table class="exTable" cellpadding="0" cellspacing="0">
				<tr style="background-color: #EEEEEE;">
					<td class="extd" width="35%" style="border:0px;"><b>Requirement Type</b></td>
					<td class="extd" width="15%" style="border-bottom:0px;"><b>Expiration Date</b></td>
					<td class="extd" width="15%" style="border-bottom:0px;"><b>Expires in X Days</b></td>
					<td class="extd" width="20%" style="border-bottom:0px;"><b>System action</b></td>
					<td class="extd" width="15%" style="border-bottom:0px;"><b>Notice sent on</b></td>
				</tr>
				<c:forEach items="${expirationDetails}" var="details">
					<tr <c:if test="${details.expiresIn <= 7}">style="color:red"</c:if>>
						<td class="extd" style="border-left:0px;">${details.requirementType}</td>
						<td class="extd">
							<fmt:formatDate value="${details.expirationDate}" pattern="MM/dd/yyyy" />
						</td>
						<td class="extd">
							<c:choose>
								<c:when test="${details.expiresIn <= 0}">
									Reached Expiration Date
								</c:when>
								<c:otherwise>${details.expiresIn} Days</c:otherwise>
							</c:choose>								
						</td>
						<td class="extd">${details.action}</td>
						<td class="extd">
							<fmt:formatDate value="${details.noticeSentOn}" pattern="MM/dd/yyyy" />
						</td>
					</tr>	
				</c:forEach>	
			</table>
		</div>
		
		<div id="loadSubDiv_${firmID}_${networkId}" class="load">
			<img src="${staticContextPath}/images/simple/searchloading.gif" />
		</div>		
		
			<br>
		</div>
		
		<br>
	</div>
	
	<div id="loadingDiv_${firmID}_${networkId}" class="load">
			<img src="${staticContextPath}/images/simple/searchloading.gif" />
	</div>
	
</div>

</body>
</html>


