<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		clickMainServiceNetwork();
		$('body').click(function() {
			alert('here');
		});
	});
	
</script>

<div class="picked pickedClick">
	<label>No Selection</label>
</div>
<div class="select-options">
	<c:forEach items="${lookupsVO.allMainServices}" var="mainServices">
		<c:set var="checked" value="" ></c:set>
		<c:forEach items="${approvalItems.selectedMainServices}" var="selectedServices" >
			<c:if test="${mainServices.id == selectedServices}">
				<c:set var="checked" value=" checked " ></c:set>
			</c:if>
		</c:forEach>
		<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedMainServices" value="${mainServices.id}" ${checked} /> ${mainServices.description}</div>
	</c:forEach>
</div>