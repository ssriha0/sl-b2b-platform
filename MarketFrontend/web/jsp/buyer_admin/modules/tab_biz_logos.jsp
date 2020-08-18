<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="BuyerAdmin.companyProfile.bizLogos"/>
		</jsp:include>	

<h3 class="paddingBtm">
	[Papanek Inc.]
</h3>
<p class="paddingBtm">
	ServiceLive lets you upload logos that represent different company
	departments or business affiliates. If you upload more than one logo,
	buyers will be able to select the one that represents their business
	interest as they create their service order.
</p>

<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Logos on File
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>
		Select the logo you would like to set as the default logo for your
		company from the list below. If you would like to prevent a logo from
		being included on a service order, select it and click 'remove.'
	</p>
	<table class="scrollerTableHdr docMgrBuyerHdr" cellpadding="0"
		cellspacing="0">
		<tr>
			<td class="column1">
				Select
			</td>
			<td class="column2">
				&nbsp;
			</td>
			<td class="column3">
				<a class="sortGridColumnUp" href="">Company</a>
			</td>
			<td class="column4">
				<a class="sortGridColumnUp" href="">Logo ID</a>
			</td>
			<td class="column5">
				<a class="sortGridColumnUp" href="">File Size</a>
			</td>
		</tr>
	</table>
	<div class="grayTableContainer" style="width: 667px; height: 100px;">
		<table class="gridTable docMgrBuyer" cellpadding="0" cellspacing="0">
			<tr>
				<td class="column1">
					<input type="checkbox" />
				</td>
				<td class="column2">
					<img src="${staticContextPath}/images/icons/pdf.gif" />
				</td>
				<td class="column3">
					ABC Inc.
				</td>
				<td class="column4">
					ABCinc.jpg
				</td>
				<td class="column5">
					178kb
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
					ABC Home Improvement Services
				</td>
				<td class="column4">
					ABCinc.jpg
				</td>
				<td class="column5">
					178kb
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="clearfix">
	<div class="formNavButtons">
		<input width="100" type="image" height="20" class="btn20Bevel"
			style="background-image: url(${staticContextPath}/images/btn/setAsDefault.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
		<input width="72" type="image" height="20" class="btn20Bevel"
			style="background-image: url(${staticContextPath}/images/btn/remove.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>
</div>
<div class="darkGrayModuleHdr">
	Add Logo
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>
		Click 'add logo' to upload additional logos. Enter the name of the
		company or division the logo represents and click 'attach.' A maximum
		of (x) logos can be uploaded per company. Individual file size is
		limited to 2MB
	</p>
	<p>
		<label>
			Select file to upload
		</label>
		<br />
		<input type="text" class="shadowBox grayText" style="width: 300px;"
			value="[Select file]" onfocus="clearTextbox(this)" />
		&nbsp;&nbsp;
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
			height="22"
			style="background-image: url(${staticContextPath}/images/btn/browse.gif);"
			class="btnBevel uploadBtn" />
	</p>
	<p>
		<label>
			Company
		</label>
		<br>
		<input type="text" class="shadowBox grayText" style="width: 300px;"
			value="[Company]" onfocus="clearTextbox(this)" />
	</p>
	<div class="clearfix paddingBtm">
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
			height="20"
			style="background-image: url(${staticContextPath}/images/btn/attach.gif); margin-bottom: -6px"
			class="btn20Bevel inlineBtn" />
	</div>
	<div class="filetypes">
		<p>
			Preferred file types:
			<br />
			JPG | PDF | DOC | GIF
		</p>
	</div>
	<table class="docTablehdr scrollerTableHdr" cellpadding="0"
		cellspacing="0" style="width: 465px;">
		<tr>
			<td class="column1">
				Select
			</td>
			<td class="column2">
				<a class="sortGridColumnUp" href="">File Name</a>
			</td>
			<td class="column3">
				<a class="sortGridColumnUp" href="">File Size</a>
			</td>
		</tr>
	</table>
	<div class="grayTableContainer" id="docContainer">
		<table class="docTable gridTable" cellpadding="0" cellspacing="0">
			<tr>
				<td class="column1">
					<input type="checkbox" />
				</td>
				<td class="column2">
					NewLogo01.jpg
				</td>
				<td class="column3">
					160kb
				</td>
			</tr>
			<tr>
				<td class="column1">
					<input type="checkbox" />
				</td>
				<td class="column2">
					NewLogo02.jpg
				</td>
				<td class="column3">
					178kb
				</td>
			</tr>
		</table>
	</div>
	<div class="clearfix">
		<p>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
				height="20"
				style="background-image: url(${staticContextPath}/images/btn/viewOff.gif);"
				disabled="disabled" class="disabledBtn" />
			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
				height="20"
				style="background-image: url(${staticContextPath}/images/btn/removeOff.gif);"
				disabled="disabled" class="disabledBtn" />
			<!--
      <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/view.gif);" class="btnBevel" />
     
      <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/remove.gif);" class="btn20Bevel" />
      -->
		</p>
	</div>
</div>
<div class="clearfix">
	<div class="formNavButtons">
		<input width="49" type="image" height="20" class="btn20Bevel"
			style="background-image: url(${staticContextPath}/images/btn/save.gif);"
			src="${staticContextPath}/images/common/spacer.gif" />
	</div>
	<div class="bottomRightLink">
		<a href="">Cancel</a>
	</div>
</div>
