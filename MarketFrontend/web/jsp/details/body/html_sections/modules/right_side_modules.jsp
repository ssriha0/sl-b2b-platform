<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div dojoType="dijit.TitlePane" title="Order Quick Links" id="order_quick_links"
	 style="margin-left:4px;border-left: 1px solid #CCCCCC;border-right: 1px solid #CCCCCC;border-bottom: 1px solid #CCCCCC;" open="true">
	<span class="dijitInfoNodeInner"><a href=""></a> </span>
	<div class="dijitReset">
		<div class="dijitTitlePaneContentInner">
			<b>Service Order #:</b> 00000000000
			<br />
			<b>Title:</b> <span class="lightBlue">Maecenas Lobortis</span>
			<br />
			<b>Status:</b> Sent (1d 3h 24m 12s)
			<br />
			<b>Conditionally Accepted:</b> 4
			<br />
			<b>Rejected:</b> 10
			<br />
			<b>Maximum Price:</b> $500.00
			<br />
			<b>Buyer:</b> Bobby Jones
			<br />
			<b>Provider:</b> N/A
			<br />
			<b>End Customer:</b> Barbara Haberman<br />
			<b>Location:</b> 1234 Abernathy St., Suite 145, Atlanta, GA 30060
			<br />
			<br/>
			<hr />
			<br/>
			<input width="131" height="17" type="image" class="btn17" style="background-image: url(${staticContextPath}/images/btn/addAndViewNotes.gif);" src="${staticContextPath}/images/common/spacer.gif" />
			
			<input width="131" height="17" type="image" class="btn17" style="background-image: url(${staticContextPath}/images/btn/acceptServiceOrder.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
			<input width="131" height="17" type="image" class="btn17" style="background-image: url(${staticContextPath}/images/btn/acceptServiceOrderConditional.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
			<input width="131" height="17" type="image" class="btn17" style="background-image: url(${staticContextPath}/images/btn/rejectServiceOrder.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
			<input width="131" height="27" type="image" class="btn27" style="background-image: url(${staticContextPath}/images/btn/manageDocsPhotos.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
			<input width="131" height="17" type="image" class="btn17" style="background-image: url(${staticContextPath}/images/btn/serviceLiveSupport.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
			<input width="131" height="17" type="image" class="btn17" style="background-image: url(${staticContextPath}/images/btn/viewPrintPDF.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
			<input width="131" height="17" type="image" class="btn17" style="background-image: url(${staticContextPath}/images/btn/rateProvider.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
			<br/>
			<br/>
			<input width="131" height="27" type="image" class="btn27" style="background-image: url(${staticContextPath}/images/btn/returnSOM.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
			
			
		</div>
	</div>
</div>
