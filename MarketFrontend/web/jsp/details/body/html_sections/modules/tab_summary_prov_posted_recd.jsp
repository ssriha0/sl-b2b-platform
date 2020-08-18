<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
                  <jsp:param name="PageName" value="ServiceOrderDetails.summaryProvPostedRecieved"/>
            </jsp:include>
<style>
</style>
<%session.setAttribute(OrderConstants.DEFAULT_TAB, SODetailsUtils.ID_SUMMARY);%>
<!-- START RIGHT SIDE MODULES -->
<div id="rightsidemodules" dojoType="dijit.layout.ContentPane"
	href="soDetailsQuickLinks.action" preventcache="true" usecache="false" cachecontent="false" 
	class="colRight255 clearfix">
</div>
<!-- END RIGHT SIDE MODULES -->
<!-- NEW MODULE/ WIDGET-->
<style>
</style>
 <div style="color: blue">
 <p>${msg}</p>
 <%session.setAttribute("msg",""); %>
</div>
<div class="contentPane">
	<p>
		Congratulations! You've met the criteria outlined by the buyer for
		this service order. Remember, if you accept, you agree to complete the
		project according to the terms outlined below. Please read the order
		carefully. Use the Quick Links on the right to accept, reject or
		accept with conditions if you want to change the time or the price.
		Don't delay, as the first provider to accept will win the work.
	</p>
	<div dojoType="dijit.TitlePane" title="Service Order Details"
		class="contentWellPane">
		<table cellpadding="0" cellspacing="0" class="noMargin">
			<tr>
				<td width="300">
					<p>
						<img src="${staticContextPath}/images/so_wizard/block150.gif" alt="image"
							title="Placeholder" />
					</p>
					<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
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
								${summaryDTO.primaryStatus}
							</td>
						</tr>
						<tr>
							<td>
								<b>Substatus</b>
							</td>
							<td></td>
						</tr>

					</table>
				</td>
				<td>
					<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
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
								<b>Posted</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								June 5, 2007 10:57 PM
							</td>
						</tr>
						<tr>
							<td>
								[
								<b>Accepted</b> ]
							</td>
							<td width="15px"></td>
							<td align="left">
								[June 5, 2007 10:57 PM]
							</td>
						</tr>
						<tr>
							<td>
								[
								<b>Canceled</b> ]
							</td>
							<td width="15px"></td>
							<td align="left">
								[June 5, 2007 10:57 PM]
							</td>
						</tr>
						<tr>
							<td>
								[
								<b>Voided</b> ]
							</td>
							<td width="15px"></td>
							<td align="left">
								[June 5, 2007 10:57 PM]
							</td>
						</tr>
						<tr>
							<td>
								[
								<b>Completed</b> ]
							</td>
							<td width="15px"></td>
							<td align="left">
								[June 5, 2007 10:57 PM]
							</td>
						</tr>
						<tr>
							<td>
								[
								<b>Closed</b> ]
							</td>
							<td width="15px"></td>
							<td align="left">
								[June 5, 2007 10:57 PM]
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
							<td colspan="3">&nbsp;
								

							</td>
						</tr>
						<tr>
							<td valign="top">
								<b>Appointment&nbsp;Dates</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								June 5, 2007 - June 27, 2007
							</td>
						</tr>
						<tr>
							<td>
								<b>Service Window</b>
							</td>
							<td width="15px"></td>
							<td align="left">
								${summaryDTO.serviceWindow}
							</td>
						</tr>
						<tr>
							<td colspan="3">
								[ Do NOT contact the customer to confirm service time. ]
							</td>
						</tr>
						<tr>
							<td colspan="3">&nbsp;
								

							</td>
						</tr>
						
						<tr><td>[<b>Continuation Order ID#</b> ]</td>
	<td width="15px"></td>							<td align="left">[<a href="#">098765432109876</a> ]
	</td></tr> <tr><td>[<b>Reason</b> ]</td>
	<td width="15px"></td>							<td align="left">[Rework ]
	</td></tr></table>
				</td>
			</tr>
		</table>
		<p>
			<strong>Title</strong>
			<br />

			Installation for the Entertainment Room
		</p>
		<p>
			<strong>Overview</strong>
			<br />
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
			<br />
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Etiam
			risus. Maecenas volutpat. Nam euismod lectus et sem. Morbi id nisl.
			Sed id felis nec eros accumsan feugiat. Cras imperdiet consequat
			velit. Sed suscipit, massa eu pretium dictum, risus tellus luctus
			ipsum, facilisis commodo turpis arcu eu pede. Nulla facilisi.
		</p>
		<hr />
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
			<tr>
				<td width="130">
					<strong>Buyer Support Contact</strong>
				</td>
				<td width="80"></td>
			</tr>
			<tr>
				<td>
					<strong>Buyer ID#</strong>
				</td>
				<td>
					4554265
				</td>

			</tr>
			<tr>
				<td>
					<strong>Company ID#</strong>
				</td>
				<td>
					<a href="">4554265</a>
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



	</div>



	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Scope of Work"
		class="contentWellPane">
		<p>
			<strong>Service Location Information</strong>
			<br />
			Residential
			<br />
			Atlanta, GA 30060
		</p>
		<p>
			<strong>Special Location Notes</strong>
			<br />
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur
			urna augue, pretium ac, suscipit eget, suscipit eget, ligula. Quisque
			sit amet nibh nec diam tempus gravida. Nunc sodales. Etiam cursus
			suscipit dolor. Vestibulum ornare congue libero.
		</p>
		<div class="hrText">
			Job Information
		</div>

		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
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
			<tr>
				<td>
					<strong>Number of Tasks</strong>
				</td>
				<td>
					3
				</td>
			</tr>
		</table>
		<p>
			Parts will be the responsibility of the provider. See the pricing
			section below for spending information.
		</p>
		<p>
			[Parts will be provided by the buyer and require pick-up by the
			service provider. See the parts section below for more details.]
		</p>
		<p>
			[Parts will be at the service location. See the parts section below
			for more details.]
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
			<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
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
			<div class="hrText">
				Assessment Questions
			</div>

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

			<p>
				<strong>Task Comments</strong>
				<br />
				Nulla aliquet suscipit tortor. Nulla accum san nibh at eros. Nullam
				fringilla orci at tortor. Sed a sapien. Curabitur a velit. In
				malesuada tortor eget nulla. Ut commodo dolor a lorem. Nunc ante
				urna laoreet nisi, eget congue ante sapien non libero.
			</p>

		</div>

		<div class="hrText">
			General Scope of Work Comments
		</div>
		<p>
			Nulla aliquet suscipit tortor. Nulla accum san nibh at eros. Nullam
			fringilla orci at tortor. Sed a sapien. Curabitur a velit. In
			malesuada tortor eget nulla. Ut commodo dolor a lorem. Nunc ante urna
			laoreet nisi, eget congue ante sapien non libero.
		</p>
	</div>

	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Service Order Pricing" id=""
		class="contentWellPane">
		<p>
			The buyer set the pricing type and spend limits as outlined below.
		</p>
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
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
					Provider Selected
				</td>
			</tr>

		</table>
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
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
					<strong>Maximum Price for Materials</strong>
				</td>
				<td>
					$11.00
				</td>
			</tr>


		</table>

	</div>

	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Parts" class="contentWellPane">

		<p>
			Please note the parts detail information below. If pick-up is
			required, the pick-up location ZIP Code is shown. You will receive
			detailed pick-up location information after you accept this order.
		</p>

		<!-- NEW NESTED MODULE -->
		<div dojoType="dijit.TitlePane" title="Part 1 - Sony 454-A2A" id=""
			class="dijitTitlePaneSubTitle" open="false">

		</div>

		<div dojoType="dijit.TitlePane" title="Part 2 - LG SP-800" id=""
			class="dijitTitlePaneSubTitle">
			<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
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
			Description
			<p>
				Nullam fringilla orci at tortor. Sed a sapien. Phasellus eu nibh.
				Maecenas sem. Morbi tellus nulla, accumsan at, sagittis in. Maecenas
				sem. Morbi tellus nulla, accumsan at, sagittis in.
			</p>
			<p>
				<strong>Parts pick-up required in Atlanta, GA 30303</strong>
			</p>
		</div>

	</div>

	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Documents & Photos" id=""
		class="contentWellPane">
		<p>
			The buyer has submitted the following photos for you to review.
			Select a file and click 'view' to open or 'download' to save it to
			your computer.
		</p>
		<table class="docTableSOWhdr" cellpadding="0" cellspacing="0"
			style="margin-bottom: 0;">
			<tr>
				<td class="column1">
					Select
				</td>
				<td class="column2">&nbsp;
					


				</td>
				<td class="column3">
					<a class="sortGridColumnUp" href="">File Name</a>
				</td>
				<td class="column4">
					<a class="sortGridColumnUp" href="">File Size</a>
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
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
					height="20"
					style="background-image: url(${staticContextPath}/images/btn/view.gif);"
					class="btnBevel" />
			</li>
			<li>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
					height="20"
					style="background-image: url(${staticContextPath}/images/btn/download.gif);"
					class="btn20Bevel" />
			</li>

		</ul>
	</div>



	<div class="bottomRightLink">
		<a href="javascript:void(0)" onclick="toTop(0,0)">Back to Top</a>
	</div>
</div>
