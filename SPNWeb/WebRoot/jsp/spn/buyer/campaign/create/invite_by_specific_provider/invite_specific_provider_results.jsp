<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
			<style type="text/css">
				<!--
				a:hover {cursor:url("custom.cur"), pointer}
				-->
			</style>

			<script type="text/javascript">
			$(document).ready(function() {	
				$("#deleteAll").click(function(){
					$('#providerSearchResults').html('');
				    var seconds = new Date().getTime();    
				    $('#providerSearchResults').load('spnInviteProvider_deleteAllAjax.action?randomnum='+seconds);
				    $.post("spnInviteProvider_deleteAllAjax.action?randomnum="+seconds);
				 });
			});
			</script>	 
	</head>

	<body>
		
		<c:if test="${showDeleteButton}">
			<table>
				<tr>
					<td>				
 						<label>We found the following results. Select all that apply</label>
 					</td>
 					<td>
 						<a id="deleteAll" >
	 						<img src="${staticContextPath}/images/icons/iconTrash.gif" alt="trash icon"/>
	 						Delete All
 						</a>
 					</td>
 				</tr>
 			</table>
		</c:if>

		<c:if test='${errorString != null}'>
				<div class="warningBox" style="width:100%">
					${errorString}
				</div>
			<br>
		</c:if>
		
		<c:if test='${alreadyInvitedProviderFirmIds != null}'>
				<br>
				<c:forEach items="${alreadyInvitedProviderFirmIds}" var="lvBean">
					<div class="warningBox" style="width:100%">
						Provider Firm ${lvBean.label} already has a ${lvBean.value} status with this network
					</div>
					<br>
					<br>
				</c:forEach>
			<br>
		</c:if>

		<c:if test='${duplicateString != null}'>
				<br>
				<div class="warningBox" style="width:100%">
					${duplicateString}
				</div>
			<br>
		</c:if>
		
		<table border=1 width="100%">
			<c:forEach items="${providerFirmList}" var="firm">
				<tr>
					<td width="20px">
						<input
							class="firmId"
							type="checkbox"
							id="providerCheckboxList"
							name="providerCheckboxList"
							value="${firm.firmId}"
							class="firmId"
							checked="checked"/>
					</td>

					<td>
						${firm.firmName}
					</td>
					<td>
						(ID#${firm.firmId})
					</td>

					<td>
						ServiceLive Status: ${firm.statusString}
					</td>

					
				</tr>
			</c:forEach>
		</table>


	
	</body>