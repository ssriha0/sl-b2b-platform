<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div class="profileStatusDB">
	<div class="clearfix">
		<div class="profileType">
			Buyer Profile
		</div>
		<div class="statusIcon">
			<img src="${staticContextPath}/images/icons/incIcon.gif" width="12" height="12"
				alt="">
		</div>
	</div>
	<div class="divider"></div>

	<p align="center" style="padding-top: 15px;">

		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="127"
			height="20"
			style="background-image: url(${staticContextPath}/images/btn/submitRegistrationOff.gif)"
			disabled="disabled"
			title="Please Complete Your Registration to Submit"
			class="disabledBtn" />
		<!-- <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="127" height="20" style="background-image:url(${staticContextPath}/images/btn/submitRegistrationOff.gif)" disabled="disabled" title="Please Complete Your Registration to Submit" />
            <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="127" height="20" style="background-image:url(${staticContextPath}/images/btn/submitRegistration.gif);" class="btn20Bevel" />
            -->
	</p>
</div>
