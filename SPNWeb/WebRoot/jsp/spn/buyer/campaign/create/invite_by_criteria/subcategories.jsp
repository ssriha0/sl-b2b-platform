<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$('.selectOptionsSub').hide();
		setupMultiSelect();	
		setupSubCategoryCampaign();		
	});
</script>
<c:if test="${approvalItems.selectedMainServices == null}">
	Please select a main service to specify category
</c:if>
<c:forEach items="${lookupsVO.subServicesMap}" var="subServicesMap">
<div class="clearfix">
	<c:forEach items="${subServicesMap.value}" var="subServices">
		<c:if test="${subServices.key == '0'}">
			<c:forEach items="${subServices.value}" var="mainName">
				<c:set var="nameMainService" value="${mainName.description}" />
			</c:forEach>
		</c:if>
		<c:if test="${subServices.key == '1'}">
			<div class="half">
				<label>
					${nameMainService}
				</label>
				<div class="picked pickedClick">
					<label>No Selection</label>
				</div>
				<div class="select-options selectOptionsSub">	
						<c:forEach items="${subServices.value}" var="subService">
							<c:set var="checked" value="" ></c:set>
							<c:forEach items="${approvalItems.selectedSubServices1}" var="selectedSub1" >
								<c:if test="${subService.id == selectedSub1}">
									<c:set var="checked" value=" checked " ></c:set>
								</c:if>
							</c:forEach>
							<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedSubServices1" value="${subService.id}" ${checked} /> ${subService.description}</div>
						</c:forEach>
				</div>
			</div>
		</c:if>
		<c:if test="${subServices.key == '2'}">
			<div class="subServices2">
				<div class="half">
					<label>
						Subcategories
					</label>
					<div class="picked pickedClick">
						<label>No Selection</label>
					</div>
					<div class="select-options selectOptionsSub">
							<c:forEach items="${subServices.value}" var="subService">
								<c:set var="checked" value="" ></c:set>
								<c:forEach items="${approvalItems.selectedSubServices2}" var="selectedSub2" >
									<c:if test="${subService.id == selectedSub2}">
										<c:set var="checked" value=" checked " ></c:set>
									</c:if>
								</c:forEach>
								<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedSubServices2" value="${subService.id}" ${checked} /> ${subService.description} <br /></div>
							</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
	</c:forEach>
</div>
</c:forEach>