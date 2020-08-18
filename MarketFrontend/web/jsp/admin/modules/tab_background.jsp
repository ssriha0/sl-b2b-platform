<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="paddingBtm">Wyatt Knox</h3>
<p>Since most service orders require a home visit, we require that all service providers undergo a background check. Just like you, we are committed to the safety of our service customers. </p>
<p>ServiceLive will work with a third party to review criminal, vehicle registration and property ownership records. There is normally a $45 processing fee for this service, but for a limited time, that fee will be waived. </p>
<p>Each background check usually takes 3 to 10 days, after which you will be provided with the service pro's overall rating. Information gathered during each background check will be kept confidential. </p>
<p><strong>IMPORTANT:</strong> You are legally required to gain approval from your service pro before we can begin the background check. Please explain that we are conducting the check out of concern for the safety of service customers and that the procedure is routine. </p>
<p>Our third party background check will review the following criteria:</p>
<ul style="padding: 0 0 10px 20px;">
<li>7 year period for all residences uncovered in the SSN and Employment History Search</li>
<li>Felony and Misdemeanor Convictions or Withheld Adjudications</li>
<li>Review a Compilation of Databases at the National and Federal Level </li>
<li>State Sex Offender Registry Searches </li>
<li>Driver's License Search (looking for valid DL and responsible driving) </li>
<li>SSN Verification</li></ul>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Request Background Check</div>
<div class="grayModuleContent mainWellContent clearfix">
	  	
	  	<p class="paddingBtm"><input type="radio" /> I would like to submit this service pro for a background check. I have explained the process and he/she has agreed to the check.</p>
		
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td width="261">
                    <p>
						Service Provider Email Address<br />
						<input type="text" style="width: 231px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Email Address]" /></p>
					</td>
					<td> <p>
						Confirm Service Provider Email Address<br />
						<input type="text" style="width: 231px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Email Address]" /></p>
					</td>
				</tr>
				<tr>
					<td> <p>
						Service Provider Alternate Email Address<br />
						<input type="text" style="width: 231px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Alternate Email Address]" /></p>
					</td>
					<td> <p>
						Confirm Service Provider Alternate Email Address<br />
						<input type="text" style="width: 231px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Alternate Email Address]" /></p>
					</td>
				</tr>
			</table>
			<br />
			<p>Send results to primary company contact?</p>
			<p><input type="radio" /> Yes <input type="radio" style="margin-left:20px;" /> No</p>
			<br />
		
		<p><input type="radio" /> I do not wish to submit this service provider for a background check at this time.</p>
	  <div class="clear"></div>
</div>
<div class="clearfix">
<div class="formNavButtons">
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="74" height="20" style="background-image:url(../../images/btn/previous.gif);"class="btn20Bevel" />
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="51" height="20" style="background-image:url(../../images/btn/save.gif);"class="btn20Bevel" /> 
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="51" height="20" style="background-image:url(../../images/btn/next.gif);"class="btn20Bevel" /> </div>
</div>



