<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h3 class="collapse c-cat" title="c-cat">
	<span>Specify Categories</span><span class="plus c-cat"></span><span
		class="min c-cat"></span>
</h3>
<div class="toggle c-cat">
	<fieldset>
		<div id="subCatDropDowns">
			<jsp:include page="/jsp/spn/buyer/campaign/create/invite_by_criteria/subcategories.jsp"></jsp:include>
		</div>
	</fieldset>
</div>

