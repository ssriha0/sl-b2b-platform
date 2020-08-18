<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js" type="text/javascript"></script>
<!-- START RIGHT SIDE MODULES -->
            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
                  <jsp:param name="PageName" value="ServiceOrderDetails.buyerCompletionDoc"/>
            </jsp:include>

<div id="rightsidemodules" dojoType="dijit.layout.ContentPane"
	href="soDetailsQuickLinks.action" preventcache="true" usecache="false" cachecontent="false"
	class="colRight255 clearfix"> </div>
<!-- END RIGHT SIDE MODULES -->
<div class="contentPane">
	<p><strong>
		The service provider has submitted the following completion
		documentation and is awaiting payment. Review and submit additional
		information below and then release funds.
		</strong>
	</p>

	<div dojoType="dijit.TitlePane" title="General Completion Information"
		class="contentWellPane" style="width: 700px;">
		<p>
			<s:actionerror />
		</p>
		<p>
			<strong>Resolution Comments </strong> ${resolutionComments}
		</p>
		<p>
			<tr>
				<td />
				<td align="left">
					Maximum Price for Parts:&nbsp ${partsSpendLimit}
					<br />
					Maximum Price for Labor:&nbsp ${laborSpendLimit}
					<br />
				</td>
			</tr>
			<strong>Total Maximum Price: ${totalSpendLimit} </strong>
			<br />
		</p>
		<p>
			<tr>
				<td align="left">
					Final Parts Price:&nbsp ${finalPartsPrice}
					<br />
				</td>
			</tr>
			<tr>
				<td aligh="left">
					Final Labor Price:&nbsp ${finalLaborPrice}
					<br />
				</td>
			</tr>
			<tr>
				<strong> Final Amount: &nbsp ${totalPrice} </strong>
				<br />
			</tr>
		</p>
	</div>
	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Closing Attachments"
		class="contentWellPane">
		<p>
			If you wish to add any photos or documents to the permanent record
			for this service order, you may do so below.
		</p>
		<p>
			<strong>Select file to upload:</strong>
			<br />
			<input type="text" class="shadowBox" style="width: 150px;" />
			<input type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="72" height="22"
				style="background-image: url(${staticContextPath}/images/btn/browse.gif);"
				class="btnBevel inlineBtn" />
		</p>
		<table class="docTableSOWhdr" cellpadding="0" cellspacing="0"
			style="margin: 10px 0 0 0;">
			<tr>
				<td class="column1">
					Select
				</td>
				<td class="column2">
					&nbsp;
				</td>
				<td class="column3">
					File Name
				</td>
				<td class="column4">
					File Size
				</td>
			</tr>
		</table>
		<div class="grayTableContainer">
			<table class="docTableSOW" cellpadding="0" cellspacing="0">
				<tr>
					<td class="column1">
						<input type="checkbox" />
					</td>
					<td class="column2">
						<img src="${staticContextPath}/images/icons/pdf.gif" />
					</td>
					<td class="column3">
						<strong>WindowSpecs.pdf</strong>
					</td>
					<td class="column4">
						160kb
					</td>
				</tr>
				<tr>
					<td class="column1">
						<input type="checkbox" />
					</td>
					<td class="column2">
						<img src="${staticContextPath}/images/icons/pdf.gif" />
					</td>
					<td class="column3">
						<strong>WindowSpecs2.pdf</strong>
					</td>
					<td class="column4">
						178kb
					</td>
				</tr>
			</table>
		</div>
		<p>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="49" height="20"
				style="background-image: url(${staticContextPath}/images/btn/viewOff.gif);" />
			<input type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="72" height="20"
				style="background-image: url(${staticContextPath}/images/btn/removeOff.gif);" />
		</p>
	</div>
	
	<!-- NEW MODULE/ WIDGET-->
	<c:if test="${hideAddNotes == false}">
	<form action="/MarketFrontend/soDetailsAddGeneralNote.action" method="post" id="buyerCompDocNoteForm">
		<div dojoType="dijit.TitlePane" title="Add Notes" class="contentWellPane" style="width: 665px;">

			<label style="display: none; color: red" id="subjectLabelMsg">
				Subject is required
			</label>
			<br>
			<label style="display: none; color: red" id="messageLabelMsg">
				Message is required
			</label>
			<br>

			<p>
				<strong>Subject</strong>
			</p>
			<input name="subject" id="subject" type="text" style="width: 627px;"
				class="shadowBox grayText" onfocus="clearTextbox(this)"
				value="[Subject]" />
			<p>
				<strong>Message</strong>
			</p>
			<textarea name="message" id="message" style="width: 627px;"
				class="shadowBox grayText" onfocus="clearTextbox(this)"
				value="[Message]">[Message]</textarea>
				
			<div class="clearfix">
				<div class="floatLeft">
					<p>
						<a href="#"
							onClick="#"> <img
								src="${staticContextPath}/images/common/spacer.gif" width="72"
								height="20"
								style="background-image: url(${staticContextPath}/images/btn/submit.gif);"
								class="btnBevel" /> </a>
					</p>
				</div>
				<div class="floatRight">
					<p>
						<a href="#" onClick="ClearFieldsSupportTab()"> <img
								src="${staticContextPath}/images/common/spacer.gif" width="72"
								height="20"
								style="background-image: url(${staticContextPath}/images/btn/cancel.gif);"
								class="btnBevel" "/> </a>
					</p>
				</div>
			</div>
		</div>
	</form>
	</c:if>
	
	
	
	
