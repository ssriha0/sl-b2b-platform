<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="paddingBtm">Wyatt Knox</h3>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Language Fluency</div>
<div class="grayModuleContent mainWellContent clearfix">
		<p>Speaking more than one language may provide a competitive advantage. Let us know if your service pro is fluent in any of the following:</p>
		<p><input type="checkbox" /> English - Service provider can speak, read and write in English.</p>
		<p>Check all others that apply.</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="140">
					<input type="checkbox" /> Spanish<br />
					<br />
					<input type="checkbox" /> French<br />
					<br />
					<input type="checkbox" /> Chinese<br />
					<br />
					<input type="checkbox" /> German
				</td>
				<td width="140">
					<input type="checkbox" /> Tagalog<br />
					<br />
					<input type="checkbox" /> Vietnamese<br />
					<br />
					<input type="checkbox" /> Italian<br />
					<br />
					<input type="checkbox" /> Korean
				</td>
				<td width="140">
					<input type="checkbox" /> Russian<br />
					<br />
					<input type="checkbox" /> Polish<br />
					<br />
					<input type="checkbox" /> Arabic<br />
					<br />
					<input type="checkbox" /> Portuguese
				</td>
				<td width="140">
					<input type="checkbox" /> Japanese<br />
					<br />
					<input type="checkbox" /> French Creole<br />
					<br />
					<input type="checkbox" /> Greek<br />
					<br />
					<input type="checkbox" /> Hindi
				</td>
				<td width="140">
					<input type="checkbox" /> Persian<br />
					<br />
					<input type="checkbox" /> Urdu<br />
					<br />
					<input type="checkbox" /> Cantonese<br />
					<br />
					<input type="checkbox" /> Hebrew
				</td>
			</tr>
		</table>
		<br clear="all" />
</div>
<div class="darkGrayModuleHdr">Service Template</div>
<div class="grayModuleContent mainWellContent clearfix">
		 <p>
		 	If you're creating a profile for a service pro with skills similar to one you've already registered, you can save time by using the existing profile as a template. Simply choose the person's name from the drop down list and the fields of this page will be populated.
		</p>
		<br clear="all" />
		<input type="radio" /> Set this service provider up with the same service skills identified in the profile for another service provider in my company.<br />
		<br />
		Select service provider profile to use as a base.<br />
		<br />
		<select class="grayText" onclick="changeDropdown(this)"  style="width:200px;"><option>Select One</option></select><br />
		<br />
		<input type="radio" /> I will build this service skill profile from scratch.
		 <br clear="all" />
</div>
 <div class="darkGrayModuleHdr">Skill Categories</div>
 <div class="grayModuleContent mainWellContent clearfix">
		<p>We've grouped skill sets into categories that can be easily browsed by buyers. Choose from the categories below, then click 'build service skills' to further define the service pro's capabilities.</p>
		<p>Check all that apply</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="240">
					<input type="checkbox" /> Home Electronics<br />
					<br />
                    <input type="checkbox" /> Handyman Services<br />
					<br />
					<input type="checkbox" /> Computer/Network Services<br />
					<br />
					 <input type="checkbox" /> Floors &amp; Patios<br />
					<br />
                     <input type="checkbox" /> General Plumbing &amp; Bathrooms<br />
					<br />
                     <input type="checkbox" /> Carpentry &amp; Woodworking<br />
					<br />
                     <input type="checkbox" /> Cabinets &amp; Countertops<br />
					<br />
                     <input type="checkbox" /> Heating &amp; Cooling<br />
					<br />
                     <input type="checkbox" /> Lawn &amp; Garden<br />
					<br />
                     <input type="checkbox" /> Walls &amp; Ceilings<br />
					<br />
				</td>
				<td>
					<input type="checkbox" /> Product Assembly<br />
					<br />
					<input type="checkbox" /> Home Appliances<br />
                    <br />
					<input type="checkbox" /> Roofing, Gutters, Windows &amp; Siding<br />
                    <br />
					<input type="checkbox" /> Automotive<br />
                    <br />
					<input type="checkbox" /> Interior Painting<br />
                    <br />
                    <input type="checkbox" /> Driveways<br />
                    <br />
                    <input type="checkbox" /> Electrical<br />
                    <br />
                    <input type="checkbox" /> Sheetrock/Drywall<br />
                    <br />
                    <input type="checkbox" /> Exterior Painting<br />
                    <br />
                    <input type="checkbox" /> Pool &amp Hot Tub Maintenance<br />
                    <br />
				</td>
			</tr>
		</table>
		<br clear="all" />
		<p>
			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="126" height="20" style="background-image:url(${staticContextPath}/images/btn/buildServiceSkills.gif);" class="btn20Bevel" />
			<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="159" height="20" style="background-image:url(${staticContextPath}/images/btn/addAdditionalCategories.gif);" class="btn20Bevel" />
		</p>
		<br clear="all" />
		<div dojoType="dijit.TitlePane" title="Home Appliances"
		id="" class="dijitTitlePaneSubTitle" open="false">
			<p>
			
			</p>
		</div>
		<div dojoType="dijit.TitlePane" title="Handyman Services"
		id="" class="dijitTitlePaneSubTitle" open="false">
			<p>
			
			</p>
		</div>
		<div dojoType="dijit.TitlePane" title="Home Electronics"
		id="" class="dijitTitlePaneSubTitle" open="true">
			<p>
			<table class="provReviewHdr" cellpadding="0" cellspacing="0"
		style="margin: 0; vertical-align: middle; width:635px;">
		<tr>
			
			<td  style="width:218px;padding-left:20px;">
				Select All
			</td>
			<td style="width:74px;">
				Delivery
			</td>
			<td style="width:94px;" >
				Installation
			</td>
			<td style="width:60px;" >
				Repair
			</td>
			<td style="width:90px;" >
				Maintenance
			</td>
			<td style="width:79px;" >
				Training
			</td>
			<td style="width: 20px;"></td>
		</tr>
	</table>
	<div class="grayTableContainer" style="width: 633px; height: 168px; background: #ffffff;">
		<table class="provReview" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width:218px;padding-left:20px;">
					<input type="checkbox" /> General Television
				</td>
				<td colspan="6">&nbsp;</td>
			</tr>
            <tr>
				<td style="width:244px;text-align:center;">
					Plasma TV
				</td>
				<td style="width:78px;text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="width:104px;text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="width:66px;text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="width:96px;text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="width:47px;text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="width: 20px;"></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					LCD TV
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					DLP TV
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Tube TV
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="padding-left:20px;">
					<input type="checkbox" /> TV Ceiling Mount
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="padding-left:20px;">
					<input type="checkbox" /> TV Wall Mount - Standard Surface
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="padding-left:20px;">
					<input type="checkbox" /> TV Wall Mount - Other Surfaces
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="padding-left:20px;">
					<input type="checkbox" /> General Home Theater
				</td>
				<td colspan="6">&nbsp;</td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Low Voltage Cabling
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Cable Management
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Integrate Existing Video Components
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Program Standard Universal Remote
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Program Pronto Remote
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Program Logitech Harmony Remote
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Program Home Theater PC Remote
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					In-wall or In-ceiling Speakers
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="padding-left:20px;">
					<input type="checkbox" /> Video Equipment
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="padding-left:20px;">
					<input type="checkbox" /> General Television
				</td>
				<td colspan="6">&nbsp;</td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Multi-room Video
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Multi-room Audio
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					General Security &amp; Surveillance
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
			<tr>
				<td  style="text-align:center;">
					Standard Lighting Controls
				</td>
				<td style="text-align:center;">
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td style="text-align:center;" >
					<input type="checkbox" />
				</td>
				<td></td>
			</tr>
       </table></div>
		</div>
</div>
<div class="clearfix">

  <div class="formNavButtons">
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="76" height="20" style="background-image:url(${staticContextPath}/images/btn/previous.gif);" class="btn20Bevel" />
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="53" height="20" style="background-image:url(${staticContextPath}/images/btn/save.gif);" class="btn20Bevel" />
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="59" height="20" style="background-image:url(${staticContextPath}/images/btn/next.gif);" class="btn20Bevel" />
</div>
<div class="bottomRightLink"><a href="index.htm">Cancel</a></div></div>
