<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div class="colRight255 clearfix">
	<div style="padding: 20px 15px 0px 15px; line-height: 13px;">
		<img src="${staticContextPath}/images/txt_nameyourprice.gif" width="167"
			height="25" alt="Name your price We'll help you find your provider">
		<br>

		We’ve made it easy to search qualified service providers in your area
		to find the one you want to work with.
		<br>
		<br>
		<b>1. Set your scope of work</b>
		<br>
		Create a detailed service order that can be sent to qualified
		providers you choose with just a few mouse clicks.
		<br>
		<br>
		<b>2. Choose your providers</b>
		<br>
		Choose providers who should receive your order from a list of those
		qualified to do your job. Refine your search based on criteria that
		are important to you.
		<br>
		<br>
		<b>3. Set your terms</b>
		<br>
		Control your costs by setting spend limits for parts and labor.
		<br>
		<br>
		<b>4. Manage your project</b>
		<br>
		Get notification when a provider accepts your order and manage your
		project online.
	</div>
</div>

<div class="clear">
</div>

