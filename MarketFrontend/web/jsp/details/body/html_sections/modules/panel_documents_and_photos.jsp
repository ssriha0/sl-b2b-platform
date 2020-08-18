
<!-- NEW MODULE/ WIDGET-->
<a id="documents_top" name="documents_top"></a>
<div dojoType="dijit.TitlePane" title="Documents & Photos" id=""
	class="contentWellPane">
	<p>
		Collaborate online. Share documents and photos relevant to your
		service order or post photos to show your project status.
	</p>
	<p class="paddingBtm">
		Select file to upload
		<br />
		<input type="text" class="shadowBox grayText" style="width: 200px;"
			onfocus="clearTextbox(this)" value="[Filename]" />
		<input type="image" src="${staticContextPath}/images/common/spacer.gif"
			width="72" height="22"
			style="background-image: url(${staticContextPath}/images/btn/browse.gif);"
			class="btnBevel inlineBtn" />
	</p>
	<p>
		Use the space below to describe the file and how it is relevant to
		your service order.
	</p>
	<p>
		Description
		<br />
		<textarea style="width: 660px" class="shadowBox grayText"
			onfocus="clearTextbox(this)">[Description] 
Photo of kitchen cabinet and current dishwasher installation.
</textarea>
	</p>
	<input type="image" src="${staticContextPath}/images/common/spacer.gif"
		width="61" height="20"
		style="background-image: url(${staticContextPath}/images/btn/attach.gif);"
		class="btn20Bevel" />
	<p>
		You can select a document to view or download to your hard drive.
		Files uploaded during this session may be removed before you navigate
		away from this page by clicking 'remove.'
	</p>
	<table class="docTableSOWhdr" cellpadding="0" cellspacing="0"
		style="margin-bottom: 0">
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
	<div class="grayTableContainer" style="height: 180px;">
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
					<p>
						Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
						Curabitur urna augue, pretium ac, suscipit eget, suscipit eget,
						ligula. Quisque sit amet nibh nec diam tempus gravida. Nunc
						sodales. Etiam cursus suscipit dolor. Vestibulum ornare congue
						libero.
					</p>
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
					<p>
						Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
						Curabitur urna augue, pretium ac, suscipit eget, suscipit eget,
						ligula. Quisque sit amet nibh nec diam tempus gravida. Nunc
						sodales. Etiam cursus suscipit dolor. Vestibulum ornare congue
						libero.
					</p>
				</td>
				<td class="column4">
					178kb
				</td>
			</tr>
		</table>
	</div>

	<ul class="titlePaneBtns">
		<li>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="49" height="20"
				style="background-image: url(${staticContextPath}/images/btn/viewOff.gif);" />
		</li>
		<li>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="72" height="20"
				style="background-image: url(${staticContextPath}/images/btn/downloadOff.gif);" />
		</li>
		<li>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="72" height="20"
				style="background-image: url(${staticContextPath}/images/btn/removeOff.gif);" />
		</li>
	</ul>
</div>
