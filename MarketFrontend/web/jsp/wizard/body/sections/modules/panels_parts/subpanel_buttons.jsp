<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<div id="addPartBtn" style="display: block;" class="clearfix">
	<p>
		<input type="button" id="addNewPart" class="grayButton left" value="ADD PART"
			onclick="javascript:previousButton('soWizardPartsCreate_addNewPart.action','soWizardPartsCreate','tab3');"/>
			
			<c:if test="${addNewPart}">
				<input type="button" id="cancelNewPart" class="grayButton left" value="CANCEL"
						onclick="javascript:deletePart('soWizardPartsCreate', '${contextPath}', ${sowPartDTO.index}, '${SERVICE_ORDER_ID}');"/>
			</c:if>
	</p>
</div>