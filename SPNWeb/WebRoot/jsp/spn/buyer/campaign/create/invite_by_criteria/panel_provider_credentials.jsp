<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		clickProviderCredentials();		
	});
	
</script>

<h3 class="collapse c-cred" title="c-cred">
	<span>Provider Credentials</span><span class="plus c-cred"></span><span
		class="min c-cred"></span>
</h3>
<div class="toggle c-cred">
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
					<c:forEach items="${lookupsVO.vendorCredTypesList}" var="cenCrTyp">
						<c:set var="checked" value="" ></c:set>
						<c:forEach items="${approvalItems.selectedVendorCredTypes}" var="selVenCrTyp" >
							<c:if test="${cenCrTyp.id == selVenCrTyp}">
								<c:set var="checked" value=" checked " ></c:set>
							</c:if>
						</c:forEach>
						<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedVendorCredTypes" value="${cenCrTyp.id}" ${checked} /> ${cenCrTyp.description}</div>
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
					<c:forEach items="${lookupsVO.resCredTypesList}" var="resCrTyp">
						<c:set var="checked" value="" ></c:set>
						<c:forEach items="${approvalItems.selectedResCredTypes}" var="selResCr" >
							<c:if test="${resCrTyp.id == selResCr}">
								<c:set var="checked" value=" checked " ></c:set>
							</c:if>
						</c:forEach>
						<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedResCredTypes" value="${resCrTyp.id}" ${checked} /> ${resCrTyp.description}</div>
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





