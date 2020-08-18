<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${spnHeader.spnId == '-1' || empty spnHeader.spnId}">
	<h4>
		Providers Matching
		<abbr title="Select Provider Network">SPN</abbr> Criteria:
	</h4>
						
	<label>
		Firms
	</label>
	<span>N/A</span>
	<label>
		Providers
	</label>
	<span>N/A</span>
</c:if>
<c:if test="${spnHeader.spnId != '-1' && not empty spnHeader.spnId}">
	<h4>
		Providers Matching
		<abbr title="Select Provider Network">SPN</abbr> Criteria:
	</h4>
						
	<label>
		Firms
	</label>
	<s:hidden name="providersMatchingSPN.providerFirmCounts" id="providersMatchingSPN.providerFirmCounts" value="%{providersMatchingSPN.providerFirmCounts}" />
	<span>${providersMatchingSPN.providerFirmCounts}</span>
	<label>
		Providers
	</label>
	<s:hidden name="providersMatchingSPN.providerCounts" id="providersMatchingSPN.providerCounts" value="%{providersMatchingSPN.providerCounts}" />
	<span>${providersMatchingSPN.providerCounts}</span>
</c:if>