<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<style></style>
<!-- START RIGHT SIDE MODULES -->
<div id="rightsidemodules" dojoType="dijit.layout.ContentPane"
	href="soDetailsQuickLinks.action" preventcache="true" usecache="false" cachecontent="false" class="colRight255 clearfix" >
</div>
<!-- END RIGHT SIDE MODULES -->
<!-- NEW MODULE/ WIDGET-->
<div class="contentPane">
	<div dojoType="dijit.TitlePane" title="Service Order Details"
		class="contentWellPane">
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="300">
					<p style="background-color: #cccccc; width: 240px; height: 150px;"></p>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td width="120">
								<b>Service&nbsp;Order&nbsp;#&nbsp;</b>
							</td>
							<td width="160">
								987654321
							</td>
						</tr>
						<tr>
							<td>
								<b>Primary&nbsp;Status&nbsp;</b>
							</td>
							<td>
								${dto.primaryStatus}
							</td>
						</tr>
						<tr>
							<td>
								<b>SubStatus&nbsp;</b>
							</td>
							<td>
								Part Shipped
							</td>
						</tr>

					</table>
				</td>
				<td>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<b>Created</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								June 5, 2007 10:57 PM
							</td>
						</tr>
						<tr>
							<td>
								<b>Sent</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								June 5, 2007 10:57 PM
							</td>
						</tr>
						<tr>
							<td>
								<b>Completed</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								June 5, 2007 10:57 PM
							</td>
						</tr>
						<tr>
							<td>
								<b>Closed</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								June 5, 2007 10:57 PM
							</td>
						</tr>
						<tr>
							<td>
								<b>Last Updated</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								June 5, 2007 10:57 PM
							</td>
						</tr>
						<tr>
							<td colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td valign="top">
								<b>Appointment&nbsp;Dates</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								June 5, 2007
								<br />
								June 27, 2007
							</td>
						</tr>
						<tr>
							<td>
								<b>Service Window</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								10 AM - 12 PM
							</td>
						</tr>
						<tr>
							<td colspan="3">
								Do NOT contact the customer to confirm service time.
							</td>
						</tr>
						<tr>
							<td colspan="3">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="3">
								<b>Continuation Order ID</b>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<p>
			<strong>Title</strong>
		</p>
		<p>
			Installation for the Entertainment Room
		</p>
		<p>
			<strong>Overview</strong>
		</p>
		<p>
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam
			risus. Maecenas volutpat. Nam euismod lectus et sem. Morbi id nisl.
			Sed id felis nec eros accumsan feugiat. Cras imperdiet consequat
			velit. Sed suscipit, massa eu pretium dictum, risus tellus luctus
			ipsum, facilisis commodo turpis arcu eu pede. Nulla facilisi. In
			lectus mauris, lacinia quis, pellentesque ut, adipiscing a, lectus.
			Etiam a mi. Nullam lacus ante, tristique a, accumsan vitae, sodales
			nec, eros. Integer sit amet diam. Nam gravida semper nulla. Donec
			suscipit magna vitae est. Nulla pulvinar felis a erat. Etiam ut
			massa.
		</p>
		<p>
			<strong>Buyer's Terms & Conditions</strong>
		</p>
		<p>
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam
			risus. Maecenas volutpat. Nam euismod lectus et sem. Morbi id nisl.
			Sed id felis nec eros accumsan feugiat. Cras imperdiet consequat
			velit. Sed suscipit, massa eu pretium dictum, risus tellus luctus
			ipsum, facilisis commodo turpis arcu eu pede. Nulla facilisi.
		</p>
		<p>
			<strong>Special Instructions</strong>
		</p>
		<p>
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam
			risus. Maecenas volutpat. Nam euismod lectus et sem. Morbi id nisl.
			Sed id felis nec eros accumsan feugiat. Cras imperdiet consequat
			velit. Sed suscipit, massa eu pretium dictum, risus tellus luctus
			ipsum, facilisis commodo turpis arcu eu pede. Nulla facilisi.
		</p>
		<hr />
		<strong>Provider References (optional)</strong>
		<p>
			<strong>DOS#</strong> 465465132
		</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="150">
					Reference Type
				</td>
				<td width="170">
					Reference Value
				</td>
				<td width="80"></td>
			<tr>
				<td>
					<p>
						<select style="width: 140px">
							<option>
								DOS #
							</option>
						</select>
				</td>
				<td>
					<p>
						<input type="text" class="shadowBox" style="width: 150px;" />
					</p>
				</td>
				<td>
					<p>
						<input type="image" src="${staticContextPath}/images/common/spacer.gif"
							width="72" height="20"
							style="background-image: url(${staticContextPath}/images/btn/view.gif);"
							class="btnBevel" />
					</p>
				</td>

			</tr>
			<tr>
				<td colspan="3">
					<input type="image" src="${staticContextPath}/images/common/spacer.gif"
						width="142" height="20"
						style="background-image: url(${staticContextPath}/images/btn/addAnotherReference.gif);"
						class="btn20Bevel" />
				</td>
		</table>
	</div>
	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Scope of Work"
		class="contentWellPane" open="false">
		<p class="text11px">
			<strong>Service Location Information</strong>
		</p>
		<p class="text11px">
			Residential
			<br />
			Barbara Haberman
			<br />
			1234 Abernathy St., Suite 145
			<br />
			Atlanta, GA 30060
		</p>
		<table cellpadding="0" cellspacing="0" style="font-size: 11px;">
			<tr>
				<td width="150" class="text11px">
					<strong>Work Phone</strong>
				</td>
				<td width="250" class="text11px">
					404-555-8747&nbsp;&nbsp;Ext. 22
				</td>
			</tr>
			<tr>
				<td class="text11px">
					<strong>Mobile Phone</strong>
				</td>
				<td class="text11px">
					404-555-2232
				</td>
			</tr>
			<tr>
				<td class="text11px">
					<strong>Email</strong>
				</td>
				<td class="text11px">
					contact@myaddress.com
				</td>
			</tr>
		</table>
		<p>
			<strong>Special Location Notes</strong>
		</p>
		<p>
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur
			urna augue, pretium ac, suscipit eget, suscipit eget, ligula. Quisque
			sit amet nibh nec diam tempus gravida. Nunc sodales. Etiam cursus
			suscipit dolor. Vestibulum ornare congue libero.
		</p>
		<br />
		<strong>Job Information</strong>
		<hr />
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="200">
					<strong>Main Service Category</strong>
				</td>
				<td width="300">
					Handyman Services
				</td>
			</tr>
			<tr>
				<td>
					<strong>Categories Required</strong>
				</td>
				<td>
					Dishwasher, TV Wall Mount - Standard
				</td>
			</tr>
			<tr>
				<td>
					<strong>Sub-Categories Required</strong>
				</td>
				<td>
					Dishwasher, TV Wall Mount - Standard
				</td>
			</tr>
			<tr>
				<td>
					<strong>Skills Required</strong>
				</td>
				<td>
					Install
				</td>
			</tr>

		</table>
		<p>
			Service provider will provide all parts for this order. The buyer has
			set a spend limit of $11.00.
		</p>
		<!-- NEW NESTED MODULE -->
		<div dojoType="dijit.TitlePane" title="Task 1 - Install Dishwasher"
			id="" class="dijitTitlePaneSubTitle" open="false">
			<p>
				Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur
				urna augue, pretium ac, suscipit eget, suscipit eget, ligula.
				Quisque sit amet nibh nec diam tempus gravida. Nunc sodales. Etiam
				cursus suscipit dolor. Vestibulum ornare congue libero.
			</p>
		</div>
		<!-- NEW NESTED MODULE -->
		<div dojoType="dijit.TitlePane" title="Task 2 - Install Disposal"
			id="" class="dijitTitlePaneSubTitle">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td width="200">
						<strong>Category</strong>
					</td>
					<td width="300">
						TV Wall Mount - Standard
					</td>
				</tr>
				<tr>
					<td>
						<strong>Sub-Category</strong>
					</td>
					<td>
						TV Wall Mount - Standard
					</td>
				</tr>
				<tr>
					<td>
						<strong>Skill</strong>
					</td>
					<td>
						Install
					</td>
				</tr>
			</table>
			<strong>Assessment Questions</strong>
			<hr />
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td width="400">
						Will the delivery involve any flights of stairs? If so, how many?
					</td>
					<td width="200">
						2
					</td>
				</tr>
				<tr>
					<td>
						How will the TV be mounted?
					</td>
					<td>
						Wall
					</td>
				</tr>
				<tr>
					<td>
						Did you purchase a mount for the TV?
					</td>
					<td>
						Yes
					</td>
				</tr>
				<tr>
					<td>
						Will the area be cleared to complete installation?
					</td>
					<td>
						Yes
					</td>
				</tr>
				<tr>
					<td>
						What room will the TV be mounted in?
					</td>
					<td>
						Living Room
					</td>
				</tr>
				<tr>
					<td>
						Is there an electrical outlet within 3 feet of where the TV will
						be installed?
					</td>
					<td>
						No
					</td>
				</tr>
				<tr>
					<td>
						Will other components need to be hooked up to the TV?
					</td>
					<td>
						Cable/Satellite Box
						<br />
						DVD
						<br />
						Game Console
					</td>
				</tr>
				<tr>
					<td>
						Is there an electrical outlet within 3 feet of where the TV will
						be installed?
					</td>
					<td>
						No
					</td>
				</tr>
			</table>
			<strong>Task Comments</strong>
			<p>
				Nulla aliquet suscipit tortor. Nulla accum san nibh at eros. Nullam
				fringilla orci at tortor. Sed a sapien. Curabitur a velit. In
				malesuada tortor eget nulla. Ut commodo dolor a lorem. Nunc ante
				urna laoreet nisi, eget congue ante sapien non libero.
			</p>
		</div>
		<strong>General Scope of Work Comments</strong>
		<hr />
		<p>
			Nulla aliquet suscipit tortor. Nulla accum san nibh at eros. Nullam
			fringilla orci at tortor. Sed a sapien. Curabitur a velit. In
			malesuada tortor eget nulla. Ut commodo dolor a lorem. Nunc ante urna
			laoreet nisi, eget congue ante sapien non libero.
		</p>
	</div>

	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Contact Information"
		class="contentWellPane">
		<p>
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur
			urna augue, pretium ac, suscipit eget, suscipit eget, ligula. Quisque
			sit amet nibh nec diam tempus gravida. Nunc sodales. Etiam cursus
			suscipit dolor. Vestibulum ornare congue libero.
		</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="50%">
					<p>
						<strong>Service Location Information</strong>
					</p>
					<p>
						Residential
						<br />
						Barbara Haberman
						<br />
						1234 Abernathy St., Suite 145
						<br />
						Atlanta, GA 30060
					</p>
					<p style="padding-top: 30px;">
						<strong>Buyer Support Contact</strong>
					</p>
					<p>
						Steven Loiski (ID# 4554265)
						<br />
						ABC, Inc. (ID# 45511111)
						<br />
						55 North Wiley Road
						<br />
						Atlanta, GA 30308
					</p>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td width="100">
								<strong>Work Phone</strong>
							</td>
							<td width="200">
								404-555-8747&nbsp;&nbsp;Ext. 22
							</td>
						</tr>
						<tr>
							<td>
								<strong>Mobile Phone</strong>
							</td>
							<td>
								404-555-2232
							</td>
						</tr>
						<tr>
							<td>
								<strong>Fax</strong>
							</td>
							<td>
								404-555-2232
							</td>
						</tr>
						<tr>
							<td>
								<strong>Email</strong>
							</td>
							<td>
								sloiski@abcinc.com
							</td>
						</tr>
						<tr>
							<td>
								<strong>Company Rating</strong>
							</td>
							<td>
								4.66/5.0
							</td>
						</tr>
					</table>
				</td>
				<td width="50%">

					<p>
						<strong>Customer Contact</strong>
					</p>
					<p>
						Barbara Haberman
					</p>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td width="100">
								<strong>Work Phone</strong>
							</td>
							<td width="200">
								404-555-8747&nbsp;&nbsp;Ext. 22
							</td>
						</tr>
						<tr>
							<td>
								<strong>Mobile Phone</strong>
							</td>
							<td>
								404-555-2232
							</td>
						</tr>
						<tr>
							<td>
								<strong>Email</strong>
							</td>
							<td>
								contact@myaddress.com
							</td>
						</tr>
					</table>
					<p>
						<strong>Service Provider</strong>
					</p>
					<p>
						Peyton Orr (ID# 5007105)
						<br />
						Painters, Inc. (ID# 3445345)
						<br />
						130 Industrial Blvd
						<br />
						Atlanta, GA 30314
					</p>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td width="150">
								<strong>Work Phone</strong>
							</td>
							<td width="200">
								404-555-8747&nbsp;&nbsp;Ext. 22
							</td>
						</tr>
						<tr>
							<td>
								<strong>Mobile Phone</strong>
							</td>
							<td>
								404-555-2232
							</td>
						</tr>
						<tr>
							<td>
								<strong>Fax</strong>
							</td>
							<td>
								404-555-2232
							</td>
						</tr>
						<tr>
							<td>
								<strong>Email</strong>
							</td>
							<td>
								contact@myaddress.com
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<strong>Overall</strong>
							</td>
						</tr>
						<tr>
							<td>
								<strong>Ratings </strong>
							</td>
							<td>
								4.66
							</td>
						</tr>
						<tr>
							<td>
								<strong>Service Order Count </strong>
							</td>
							<td>
								123
							</td>
						</tr>
						<tr>
							<td colspan="2">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<strong>Buyer </strong>
							</td>
						</tr>
						<tr>
							<td>
								<strong>Ratings</strong>
							</td>
							<td>
								4.81
							</td>
						</tr>
						<tr>
							<td>
								<strong>Service Order Count</strong>
							</td>
							<td>
								321
							</td>
						</tr>

						<tr>
							<td>
								<strong>Credentials</strong>
							</td>
							<td>
								Credential1
								<br />
								Credential2
								<br />
								License1
							</td>
						</tr>
						<tr>
							<td colspan="2">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<strong>Language(s)</strong>
							</td>
							<td>
								English, Spanish
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</div>
	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Documents & Photos" id=""
		class="contentWellPane">

		<p>
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur
			urna augue, pretium ac, suscipit eget, suscipit eget, ligula. Quisque
			sit amet nibh nec diam tempus gravida. Nunc sodales. Etiam cursus
			suscipit dolor. Vestibulum ornare congue libero.
		</p>

		<h4>
			Select file to attach:
		</h4>
		<p>
			<input type="text" class="shadowBox" style="width: 150px;" />
			<input type="image" src="${staticContextPath}/images/common/spacer.gif"
				width="72" height="22"
				style="background-image: url(${staticContextPath}/images/btn/upload.gif);"
				class="btnBevel inlineBtn" />
		</p>
		<h4>
			Description
		</h4>
		<textarea style="width: 660px" class="shadowBox"></textarea>


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
					width="72" height="20"
					style="background-image: url(${staticContextPath}/images/btn/view.gif);"
					class="btnBevel" />
			</li>
			<li>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif"
					width="72" height="20"
					style="background-image: url(${staticContextPath}/images/btn/download.gif);"
					class="btn20Bevel" />
			</li>
			<li>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif"
					width="72" height="20"
					style="background-image: url(${staticContextPath}/images/btn/remove.gif);"
					class="btn20Bevel" />
			</li>
		</ul>
	</div>

	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Parts" class="contentWellPane">
		<p>
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur
			urna augue, pretium ac, suscipit eget, suscipit eget, ligula. Quisque
			sit amet nibh nec diam tempus gravida. Nunc sodales. Etiam cursus
			suscipit dolor. Vestibulum ornare congue libero.
		</p>
		<!-- NEW NESTED MODULE -->
		<div dojoType="dijit.TitlePane" title="Part 1 - Sony 454-A2A" id=""
			class="dijitTitlePaneSubTitle" open="false">
		</div>
		<div dojoType="dijit.TitlePane" title="Part 2 - LG SP-800" id=""
			class="dijitTitlePaneSubTitle">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td width="100">
						<strong>Manufacturer</strong>
					</td>
					<td width="100">
						LG
					</td>
					<td width="100">
						<strong>Size</strong>
					</td>
					<td width="100">
						24"x32"x8"
					</td>
				</tr>
				<tr>
					<td>
						<strong>Model Number</strong>
					</td>
					<td>
						SP-800
					</td>
					<td>
						<strong>Weight</strong>
					</td>
					<td>
						32 lbs
					</td>
				</tr>
				<tr>
					<td>
						<strong>Qty</strong>
					</td>
					<td>
						1
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
			<strong>Description</strong>
			<p>
				Nullam fringilla orci at tortor. Sed a sapien. Phasellus eu nibh.
				Maecenas sem. Morbi tellus nulla, accumsan at, sagittis in. Maecenas
				sem. Morbi tellus nulla, accumsan at, sagittis in.
			</p>
			<p>
				<strong>Shopping Information</strong>
			</p>
			<hr />
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td width="200">
						<strong>Number of Shipping</strong>
					</td>
					<td>
						Carriers 2
					</td>
				</tr>
				<tr>
					<td>
						<strong>Shipping Carrier 1</strong>
					</td>
					<td>
						UPS
					</td>
				</tr>
				<tr>
					<td>
						<strong>Shipping Tracking Number 1</strong>
					</td>
					<td>
						1021324654657
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<strong>Shipping Carrier 2</strong>
					</td>
					<td>
						DHL
					</td>
				</tr>
				<tr>
					<td>
						<strong>Shipping Tracking Number 2</strong>
					</td>
					<td>
						1021324654657
					</td>
				</tr>
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<strong>Number of Core Return Carriers</strong>
					</td>
					<td>
						1
					</td>
				</tr>
				<tr>
					<td>
						<strong>Core Return Carrier</strong>
					</td>
					<td>
						FedEx
					</td>
				</tr>
				<tr>
					<td>
						<strong>Core Return Tracking Number</strong>
					</td>
					<td>
						1230945890385
					</td>
				</tr>
			</table>
			<p>
				<strong>Pick-up Location Information</strong>
			</p>
			<hr />
			<p>
				Bob Calendar
				<br />
				ABC Electronics, Inc.
				<br />
				123 Main Street
				<br />
				Atlanta, GA 30303
			</p>

		</div>
	</div>


	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Service Order Pricing"
		class="contentWellPane">
		<p>
			Nulla aliquet suscipit tortor. Nulla accum san nibh at eros. Nullam
			fringilla orci at tortor. Sed a sapien. Curabitur a velit. In
			malesuada tortor eget nulla. Ut commodo dolor a lorem. Nunc ante urna
			laoreet nisi, eget congue ante sapien non libero.
		</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="200">
					<strong>Pricing Type</strong>
				</td>
				<td width="300">
					Hourly
				</td>
			</tr>
			<tr>
				<td>
					<strong>Rate</strong>
				</td>
				<td>
					$4.50/hr
				</td>
			</tr>
			<tr>
				<td>
					<strong>Rate Type</strong>
				</td>
				<td>
					Buyer Selected
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="200">
					<strong>Maximum Price for Labor</strong>
				</td>
				<td width="300">
					$11.00
				</td>
			</tr>
			<tr>
				<td>
					<strong>Maximum Price for Parts</strong>
				</td>
				<td>
					$11.00
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<strong>Total Maximum Price</strong>
				</td>
				<td>
					$22.00
				</td>
			</tr>
		</table>
	</div>
	<div class="bottomRightLink">
		<a href="javascript:void(0)" onclick="toTop(0,0)">Back to Top</a>
	</div>
</div>
