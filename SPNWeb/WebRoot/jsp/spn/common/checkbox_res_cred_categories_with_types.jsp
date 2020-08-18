<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		setupMultiSelect();
	});
</script>
<div class="picked pickedClick">
	<label>No Selection</label>
</div>
<div class="select-options">
	<c:forEach items="${lookupsVO.resCredCategoriesWithTypes}" var="resCredCatWithTypes">
		<c:set var="checked" value="" ></c:set>
		<c:forEach items="${approvalItems.selectedResCredCategories}" var="selResCredCat" >
			<c:if test="${resCredCatWithTypes.id == selResCredCat}">
				<c:set var="checked" value=" checked " ></c:set>
			</c:if>
		</c:forEach>
		<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedResCredCategories" value="${resCredCatWithTypes.id}" ${checked} /> ${resCredCatWithTypes.description}</div>
	</c:forEach>
</div>
