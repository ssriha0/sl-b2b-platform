<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:if test="${alertmessage == true }">
<div style="background-color: #dddddd; font-weight: bold; font-size: 13px; padding: 5px; text-align: left;">
	ALERT-Provider Admin Action Required
</div>
<div style="padding: 5px; font-size: 12px; text-align: left;">
	Please notify your Provider Admin to complete the W9 Legal
	Tax Information in the Company Profile. Once this information is
	entered, you will be able to submit for payment on service orders.
</div>
<div style="padding: 5px; text-align: left;">
		<input type="button" onClick="hide_w9modal();" name="tellAdmin" value="Okay" style="width: 100px;" />
	</div>
</c:if>
<c:if test="${w9isExist == true}">
<script language="JavaScript" type="text/javascript">
	document.getElementById('w9isExist').value = true;	
</script>
</c:if>