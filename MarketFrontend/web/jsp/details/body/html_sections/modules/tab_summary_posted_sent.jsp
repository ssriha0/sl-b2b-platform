<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
                  <jsp:param name="PageName" value="ServiceOrderDetails.summaryPostedSent"/>
            </jsp:include>
<!-- START RIGHT SIDE MODULES -->
<%session.setAttribute(OrderConstants.DEFAULT_TAB, SODetailsUtils.ID_SUMMARY);%>
<div id="rightsidemodules" dojoType="dijit.layout.ContentPane"
	href="soDetailsQuickLinks.action" preventcache="true" usecache="false" cachecontent="false" 
	class="colRight255 clearfix">
</div>
<!-- END RIGHT SIDE MODULES -->
<!-- NEW MODULE/ WIDGET-->
<div class="contentPane">
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

						<tr>
							<td>
								[
								<b>Continuation Order ID#</b> ]
							</td>
							<td width="15px"></td>
							<td align="left"></td>
						</tr>
					</table>
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
		<p>
			<strong>Special Instructions</strong>
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
				<td colspan="2">
					<strong>Buyer References</strong>
				</td>
			</tr>
			<tr>
				<td width="150">
					<strong>DOS#</strong>
				</td>
				<td width="80">
					4566879
				</td>
			</tr>
			<tr>
				<td>
					<strong>MDO#</strong>
				</td>
				<td>
					4566879
				</td>
			</tr>
		</table>
	</div>
	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Scope of Work"
		class="contentWellPane">
		 <table cellpadding="0" cellspacing="0" class="contactInfoTable">
      <tr>
        <td class="column1"><p class="text11px"> <strong>Service Location Information</strong> <br />
            Residential <br />
            Barbara Haberman <br />
            1234 Abernathy St., Suite 145&nbsp;&nbsp; <span class="mapThis" onmouseout="popUp(event,'mapThis')"
							onmouseover="popUp(event,'mapThis')"><img
								src="${staticContextPath}/images/icons/mapThis.gif" alt="Map This Location"
								class="inlineBtn" /> </span> <br />
            Atlanta, GA 30060 </p></td>
        <td class="column2"><p class="text11px"> <strong>&nbsp; <br />
Work Phone<br />
            Mobile Phone<br />
            Fax<br />
            E-mail </strong> </p></td>
        <td class="column3"><p class="text11px">&nbsp; <br /> 404-555-8747&nbsp;&nbsp;Ext. 22 <br />
            404-555-2232 <br />
            404-555-2233 <br />
            contact@myaddress.com </p></td>
      </tr>
    </table>
		<p>
			<strong>Special Location Notes</strong>
			<br />
			Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Curabitur
			urna augue, pretium ac, suscipit eget, suscipit eget, ligula. Quisque
			sit amet nibh nec diam tempus gravida. Nunc sodales. Etiam cursus
			suscipit dolor. Vestibulum ornare congue libero.
		</p>
		<div class="hrText">
			Service Category & Tasks
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
	<div dojoType="dijit.TitlePane" title="Contact Information"
		class="contentWellPane">
		<p>
			All contact information is outlined below. The service location
			contact information includes the address for the job site. The
			primary contacts for the provider will be the service location
			contact and the buyer unless alternate contacts have been provided.
		</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="50%">
					<p>
						<strong>Service Location Information</strong>
						<br />
						Residential
						<br />
						Barbara Haberman
						<br />
						1234 Abernathy St., Suite 145
						<br />
						Atlanta, GA 30060
					</p>
					<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
						<tr>
							<td width="100">
								<strong>Home Phone</strong>
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
								contact@url.com
							</td>
						</tr>
					</table>
					<p>
						<strong>Buyer</strong>
						<br />
						Steven Loiski (ID# 4554265)
						<br />
						ABC, Inc. (<a href="">ID# 45511111</a>)
						<br />
						55 North Wiley Road
						<br />
						Atlanta, GA 30308
					</p>
					<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding" >					<tr><td width="100"><strong>Work Phone</strong></td>
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
								sloiski@peaveyliberator.com
							</td>
						</tr>
					</table>
					<p>
						<strong>Service Provider</strong>
						<br />
						Peyton Orr (<a href="">ID# 5007105</a>)
						<br />
						Painters, Inc. (<a href="">ID# 3445345</a>)
						<br />
						130 Industrial Blvd
						<br />
						Atlanta, GA 30314
					</p>
					<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
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
								contact@myaddress.com
							</td>
						</tr>
					</table>
				</td>
				<!-- column 2 -->
				<td width="50%">
					<p>
						<strong>Service Location Alternate Contact</strong>
						<br />
						Johnny Taylor
						<br />
						<br />
						<br />
						&nbsp;
					</p>
					<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding" >					<tr><td width="100"><strong>Work Phone</strong></td>
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
								jtaylor@myaddress.com
							</td>
						</tr>
					</table>
					<p>
						<strong>Buyer Support Contact</strong>
						<br />
						Rhett Butler
						<br />
						<br />
						<br />
						&nbsp;
					</p>
					<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding" >					<tr><td width="100"><strong>Work Phone</strong></td>
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
								rbutler@abcinc.com
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
			Use photos and documents to show your work area or to add additional
			information about your service order. Photos will be visible to all
			providers who receive your service order. Documents will only be
			visible to the provider who accepts your order.
		</p>
		<p class="paddingBtm">
			Select file to upload<br />
			<input type="text" class="shadowBox grayText" style="width: 200px;"onfocus="clearTextbox(this)" value="[Filename]" />
			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
				height="22"
				style="background-image: url(${staticContextPath}/images/btn/browse.gif);"
				class="btnBevel inlineBtn" />
		</p>
		<p>
			Use the space below to describe the file and how it is relevant to
			your service order.
		</p>
		<p>Description<br> <textarea style="width: 660px" class="shadowBox grayText" onfocus="clearTextbox(this)">[Description] 
Photo of kitchen cabinet and current dishwasher installation.
</textarea></p>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="61"
				height="20"
				style="background-image: url(${staticContextPath}/images/btn/attach.gif);"
				class="btn20Bevel" />
		
		<p>
			Select a document to view, download or remove from your service
			order. You will not be able to remove attachments after a service
			provider accepts your order.
		</p>
		<table class="docTableSOWhdr" cellpadding="0" cellspacing="0"
			style="margin-bottom: 0">
			<tr>
				<td class="column1">
					Select
				</td>
				<td class="column2">&nbsp;
					
				</td>
				<td class="column3">
					<a href="" class="sortGridColumnUp">File Name</a>
				</td>
				<td class="column4">
					<a href="" class="sortGridColumnUp">File Size</a>
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
		<p>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="49"
				height="20"
				style="background-image: url(${staticContextPath}/images/btn/viewOff.gif);" />

			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
				height="20"
				style="background-image: url(${staticContextPath}/images/btn/downloadOff.gif);" />

			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
				height="20"
				style="background-image: url(${staticContextPath}/images/btn/removeOff.gif);" />
		</p>
	</div>
	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Parts" class="contentWellPane">
		<p>
			Please note the parts information below. Detailed pick-up location
			information is included if pick-up is required.
		</p>
		<!-- NEW NESTED MODULE -->
		<div dojoType="dijit.TitlePane" title="Part 1 - Sony 454-A2A" id=""
			class="dijitTitlePaneSubTitle" open="false">
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
			<div class="hrText">
				Shipping Information
			</div>
			<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
				<tr>
					<td width="200">
						<strong>Shipping Carrier</strong>
					</td>
					<td>
						UPS
					</td>
				</tr>
				<tr>
					<td width="200">
						<strong>Shipping Tracking Number</strong>
					</td>
					<td>
						1021324654657
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;
						
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
			<div class="hrText">
				Pick-up Location Information
			</div>
			<table cellpadding="0" cellspacing="0" class="noMargin">
				<tr>
					<td width="120">
						<p>
							Bob Calendar
							<br />
							ABC Electronics, Inc.
							<br />
							123 Main Street
							<br />
							Atlanta, GA 30303
						</p>
					</td>
					<td>
						<br />
						<br />
						<span class="mapThis" onmouseout="popUp(event,'mapThis')"
							onmouseover="popUp(event,'mapThis')"><img
								src="${staticContextPath}/images/icons/mapThis.gif" alt="Map This Location"
								class="inlineBtn" /> </span>
					</td>
				</tr>
			</table>
			<p>
				<strong>Work Phone</strong> 404-404-4040
			</p>
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
			<div class="hrText">
				Shipping Information
			</div>
			<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
				<tr>
					<td width="200">
						<strong>Shipping Carrier</strong>
					</td>
					<td>
						UPS
					</td>
				</tr>
				<tr>
					<td width="200">
						<strong>Shipping Tracking Number</strong>
					</td>
					<td>
						1021324654657
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;
						
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
			<div class="hrText">
				Pick-up Location Information
			</div>
			<table cellpadding="0" cellspacing="0" class="noMargin">
				<tr>
					<td width="120">
						<p>
							Bob Calendar
							<br />
							ABC Electronics, Inc.
							<br />
							123 Main Street
							<br />
							Atlanta, GA 30303
						</p>
					</td>
					<td>
						<br />
						<br />
						<span class="mapThis" onmouseout="popUp(event,'mapThis')"
							onmouseover="popUp(event,'mapThis')"><img
								src="${staticContextPath}/images/icons/mapThis.gif" alt="Map This Location"
								class="inlineBtn" /> </span>
					</td>
				</tr>
			</table>
			<p>
				<strong>Work Phone</strong> 404-404-4040
			</p>
		</div>
	</div>
	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Service Order Pricing"
		class="contentWellPane">
		<p>
			A summary of the spend limits you have placed on this service order
			is below. Click 'change spend limit' to make any changes.
		</p>
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
			<tr>
				<td width="190">
					<strong>Maximum Price for Labor</strong>
				</td>
				<td width="10"></td>
				<td width="60" align="right">
					$48.00
				</td>
			</tr>
			<tr>
				<td>
					<strong>Maximum Price for Materials</strong>
				</td>
				<td></td>
				<td align="right">
					$11.00
				</td>
			</tr>
			<tr>
				<td>
					<strong>Access Fee</strong>
				</td>
				<td style="border-bottom: 1px solid #ccc;">
					+
				</td>
				<td style="border-bottom: 1px solid #ccc;" align="right">
					$10.00
				</td>
			</tr>

			<tr>
				<td>
					<strong>Maximum Price</strong>
				</td>
				<td></td>
				<td align="right">
					$69.00
				</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
			<tr>
				<td width="190">
					<strong>Maximum Price for Labor</strong>
				</td>
				<td width="10"></td>
				<td width="60" align="right">
					$48.00
				</td>
			</tr>
			<tr>
				<td>
					<strong>Maximum Price for Materials</strong>
				</td>
				<td></td>
				<td align="right">
					$11.00
				</td>
			</tr>
			<tr>
				<td>
					<strong>Access Fee</strong>
				</td>
				<td style="border-bottom: 1px solid #ccc;">
					+
				</td>
				<td style="border-bottom: 1px solid #ccc;" align="right">
					$10.00
				</td>
			</tr>
			<tr>
				<td>
					<strong>Maximum Price</strong>
				</td>
				<td></td>
				<td align="right">
					$69.00
				</td>
			</tr>
		</table>
		<p>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="130"
				height="20"
				style="background-image: url(${staticContextPath}/images/btn/increaseSpendLimit.gif);"
				class="btn20Bevel" />
		</p>
	</div>
	<div class="bottomRightLink">
		<a href="javascript:void(0)" onclick="toTop(0,0)">Back to Top</a>
	</div>
</div>
