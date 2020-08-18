<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<input id="${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_origModDate" type="hidden" name="origModDate" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${expandCriteriaVO.orginalModifiedDate_Date}" />" />
<script type="text/javascript">
	$(document).ready(function() {
		var origModDateId = "#${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_origModDate";
		var origModDate = $(origModDateId).val();
		
		var editAreaId = "#${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_editbutton";
		$(editAreaId).children('[name=origModDate]').val(origModDate);
		$('#searchOrigModDate_${expandCriteriaVO.providerFirmId}_${expandCriteriaVO.spnId}').val(origModDate);
	});
</script>
