<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

	<h4>
		Providers Matching
		<abbr title="Select Provider Network">SPN</abbr> Criteria:
	</h4>
						
	<label>
		Firms
	</label>
	<s:hidden name="providersMatchingSPN.providerFirmCounts" id="providersMatchingSPN.providerFirmCounts" value="%{providersMatchingSPN.providerFirmCounts}" />
	<span>${providerFirmCount}</span>
	<label>
		Providers
	</label>
	<s:hidden name="providersMatchingSPN.providerCounts" id="providersMatchingSPN.providerCounts" value="%{providersMatchingSPN.providerCounts}" />
	<span>${providerCount}</span>
