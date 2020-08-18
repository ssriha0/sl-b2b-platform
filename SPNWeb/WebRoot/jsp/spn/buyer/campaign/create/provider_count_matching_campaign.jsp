<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">
	jQuery(document).ready(function($) {
		updateCampaignCount();
	});
</script>

<h4>
	Providers Matching Campaign:
</h4>
<label>
	Firms
</label>
<s:hidden name="providersMatchingCampaign.providerFirmCounts" id="providersMatchingCampaign.providerFirmCounts" value="%{providersMatchingCampaign.providerFirmCounts}" />
<span>${providersMatchingCampaign.providerFirmCounts}</span>
<label>
	Providers
</label>
<s:hidden name="providersMatchingCampaign.providerCounts" id="providersMatchingCampaign.providerCounts" value="%{providersMatchingCampaign.providerCounts}" />
<span>${providersMatchingCampaign.providerCounts}</span>
<input type="submit" id="buttonUpdateCampaignCount" class="button action right" value="Update Results" onclick="return false;" />

