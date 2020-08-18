<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="darkGrayModuleHdr">
	Company Overview
</div>
<div class="grayModuleContent mainWellContent">
	<p>
		<s:property value="companyProfileDto.businessDesc" />
	</p>
</div>
