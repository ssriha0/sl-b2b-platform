<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		setupMultiSelect();
	});
</script>
<div class="picked pickedClick">
	<label>No Selections</label>
</div>
<div class="select-options">
	<c:forEach items="${lookupsVO.vendorCredCategoriesWithTypes}" var="vendorCredCatWithTypes">
		<c:set var="checked" value="" ></c:set>
		<c:forEach items="${approvalItems.selectedVendorCredCategories}" var="selVendorCredCat" >
			<c:if test="${vendorCredCatWithTypes.id == selVendorCredCat}">
				<c:set var="checked" value=" checked " ></c:set>
			</c:if>
		</c:forEach>
		<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedVendorCredCategories" value="${vendorCredCatWithTypes.id}" ${checked} /> ${vendorCredCatWithTypes.description}</div>
	</c:forEach>
</div>
