<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$('.tester').click(function() {
			alert('here i am testing');
		});
	});
</script>

<div class="clearfix catRow catRow${approvalItems.selectedMainServices[0]}">
	<div class="half">
		<label>
			Categories
		</label>
		<div class="picked pickedNewSub tester">
			<label>No Selection</label>
		</div>
		<div class="select-options">
			<s:checkboxlist cssClass="subServices1" name="approvalItems.selectedSubServices1" list="%{lookupsVO.subServices1}" listKey="id" 
				listValue="description" value="%{approvalItems.selectedSubServices2}" />
		</div>
	</div>

	<div class="subServices2">
		<div class="half">
			<label>
				Subcategories
			</label>
			<div class="picked">
				<label>No Selection</label>
			</div>
			<div class="select-options">
			</div>
		</div>
	</div>
</div>