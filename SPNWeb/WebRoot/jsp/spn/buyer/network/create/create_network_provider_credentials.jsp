<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		initShowHideProviderCredentials();
		clickProviderCredentials();
	});
	
</script>

<h5 class="collapse c-cred" title="c-cred">
	<span>Provider Credentials</span><span class="plus c-cred"></span><span
		class="min c-cred"></span>
</h5>
<div class="c-cred">
	<fieldset>
		<div class="clearfix">
			<div class="half multiselect">
				<label>
					Company Credential
				</label>
				<div class="picked pickedClick">
					<label>
						Select All that Apply
					</label>
				</div>
				<div class="select-options">
					<c:forEach items="${lookupsVO.vendorCredTypesList}" var="vendCredTypList">
						<c:set var="checked" value="" ></c:set>
						<c:forEach items="${approvalItems.selectedVendorCredTypes}" var="selectedVendCredTyp" >
							<c:if test="${vendCredTypList.id == selectedVendCredTyp}">
								<c:set var="checked" value=" checked " ></c:set>
							</c:if>
						</c:forEach>
						<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedVendorCredTypes" value="${vendCredTypList.id}" ${checked} /> ${vendCredTypList.description}</div>
					</c:forEach>
				</div>
			</div>
			<div class="half multiselect">
				<label>
					&nbsp;
				</label>
				<div id="vendorCredTypesWithCategories">
					<jsp:include
						page="/jsp/spn/common/checkbox_vendor_cred_categories_with_types.jsp"></jsp:include>
				</div>
			</div>
		</div>

		<div class="clearfix">

			<div class="half multiselect">
				<label>
					Resource Credential
				</label>
				<div class="picked pickedClick">
					<label>
						Select All that Apply
					</label>
				</div>
				<div class="select-options">
					<c:forEach items="${lookupsVO.resCredTypesList}" var="resCredTypList">
						<c:set var="checked" value="" ></c:set>
						<c:forEach items="${approvalItems.selectedResCredTypes}" var="selectedResCredTyp" >
							<c:if test="${resCredTypList.id == selectedResCredTyp}">
								<c:set var="checked" value=" checked " ></c:set>
							</c:if>
						</c:forEach>
						<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedResCredTypes" value="${resCredTypList.id}" ${checked} /> ${resCredTypList.description}</div>
					</c:forEach>
				</div>
			</div>


			<div class="half multiselect">
				<label>
					&nbsp;
				</label>
				<div id="resCredTypesWithCategories">
					<jsp:include
						page="/jsp/spn/common/checkbox_res_cred_categories_with_types.jsp"></jsp:include>
				</div>
			</div>
		</div>

	</fieldset>
</div>





