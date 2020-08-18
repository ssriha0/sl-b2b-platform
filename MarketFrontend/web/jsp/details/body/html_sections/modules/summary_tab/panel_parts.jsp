<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

 <script type="text/javascript">
function expandCompParts(path)
{
jQuery("#compparts p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#comppartsid").slideToggle(300);

var ob=document.getElementById('compPartsImg').src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('compPartsImg').src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('compPartsImg').src=path+"/images/widgets/arrowRight.gif";
}
}
</script>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	
	<div id="compparts" class="menugroup_list">
  <p class="menugroup_head" onclick="expandCompParts('${staticContextPath}')">&nbsp;<img id="compPartsImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Parts</p>
    <div class="menugroup_body" id="comppartsid">
		<p>
			If you received shipped parts and obtained a core return tracking
			number, enter the carrier and the number in the space below.
		</p>
		<table>
			<c:if test="${partCount > 0 }">
			<tr>
				<td width="160">
				<label>
						<strong>Manufacturer</strong>
				</td>
				<td width="120">
				<label>
						<strong>Parts</strong>
				</td>
				<td width="180">
				<label>
						<strong>Return Carrier</strong>
				</td>
				<td width="160">
				<label>
						<strong>Return Tracking Number</strong>
				</td>
			</tr>
			<tr>
			<td colspan="4"><hr /></td>
			
			</tr>
				<c:set var="i" value="0" />	
				<%-- <c:forEach var="part" items="${soCompleteDto.partList}"> --%>
				<s:iterator id="partList"  status="status">
					<s:hidden value="%{partList[#status.index].partId}"
						id="partList[%{#status.index}].partId"
						name="partList[%{#status.index}].partId" />
					<tr>
						<td width="160">
						<s:label value="%{partList[#status.index].manufacturer}" theme="simple"/> 
						</td>
						<td width="160">
						<s:label value="%{partList[#status.index].modelNumber}" theme="simple"/>
						</td>
						<td width="160">
						<s:select list="%{shippingCarrier}" headerKey="-1"
							headerValue="Select One" listKey="id" listValue="descr"
							theme="simple" cssStyle="width: 150px;" cssClass="grayText"							
							id="partList[%{#status.index}].coreReturnCarrierId" name="partList[%{#status.index}].coreReturnCarrierId"
							value="%{partList[#status.index].coreReturnCarrierId}"       
							/>
						</td>
						<td width="160">
							<s:textfield								
								id="partList[%{#status.index}].coreReturnTrackingNumber"
								name="partList[%{#status.index}].coreReturnTrackingNumber"								
								value="%{partList[#status.index].coreReturnTrackingNumber}"
								cssStyle="width: 100px;" cssClass="shadowBox grayText"
								theme="simple" />
						</td>
					</tr>
					<c:set var="i" value="${i + 1}" />
				</s:iterator>
			</c:if>
			<c:if test="${partCount == 0 }">
				<tr><td colspan="4"><label>No parts exist for this service order.</label></td></tr>
			</c:if>
			<input type="hidden" name="partIdCount" id="partIdCount" value="${partCount}" />	
		</table>
	</div>
</div>

