<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$('.selectOptionsSub2').hide();
		setupMultiSelect();	
	});
</script>

<div class="half">
	<label>
		Subcategories
	</label>
	<div class="picked pickedClick">
		<label>No Selection</label>
	</div>
	<div class="select-options selectOptionsSub2">
		<c:forEach items="${lookupsVO.subServices2}" var="subService2">
			<c:set var="checked" value="" ></c:set>
			<c:forEach items="${approvalItems.selectedSubServices2}" var="selectedSub2" >
				<c:if test="${subService2.id == selectedSub2}">
					<c:set var="checked" value=" checked " ></c:set>
				</c:if>
			</c:forEach>
			<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedSubServices2" value="${subService2.id}" ${checked} /> ${subService2.description} <br /></div>
		</c:forEach>
	</div>
</div>