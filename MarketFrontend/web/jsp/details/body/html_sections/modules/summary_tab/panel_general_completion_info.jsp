<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />





	<div dojoType="dijit.TitlePane" title="General Completion Information"
		class="contentWellPane">
		<p>
			In the space provided, enter any comments that you feel are important
			for the resolution of this order. If this service order was based on
			an hourly rate, enter your hours and calculate your fee.
		</p>
		

		<c:set	var="soCompleteDto" value="${soCompleteDto}" />
			<c:set var="partCnt" value="${partCount}" />
			<table  width="600" align="center">
			<tr><td colspan="2">
			Resolution Comments<span class="req">*</span>
			<br />
			<%-- Fixing Sears00051299: Resolution Comments - Truncated (Mark J. 5/13/08) --%>	
			<textarea name="resComments" id="resComments" style="width: 627px;"  
			onkeydown="countAreaChars(this.form.resComments, this.form.resComments_leftChars, 998, event);"
			onkeyup="countAreaChars(this.form.resComments, this.form.resComments_leftChars, 998, event);">${soCompleteDto.resComments}</textarea>
			<input type="text" name="resComments_leftChars" readonly size="4" maxlength="4" > chars left
			</td></tr>
			<tr>
				<td colspan="2">
					<hr />
				</td>
			</tr>
			
			<tr>
			<td align="left">Maximum Price for Materials: 
				<fmt:formatNumber value="${soCompleteDto.partSpLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/> </td>
			<input type="hidden" name="partsSl" id="partsSl" 
				value='<fmt:formatNumber value="${soCompleteDto.partSpLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' />
			
				<td align ="left">
					<label style="display: block; float: left; width: 100px;">Final Parts Price<span class="req">*</span></label>
					<input type="text" style="width: 75px; float: left; margin-right: 10px;" class="shadowBox"  name="finalPartPrice" id="finalPartPrice" /> 
					<span>Example format: 10.00 for $10</span>
				</td>		                
			</tr>
			<tr>
			<td align ="left">Maximum Price for Labor: <fmt:formatNumber value="${soCompleteDto.laborSpLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/></td>
			<input type="hidden" name="laborSl" id="laborSl" 
				value='<fmt:formatNumber value="${soCompleteDto.laborSpLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' />
								                				
			<td align ="left">
				<label style="display: block; float: left; width: 100px;">Final Labor Price<span class="req">*</span></label>
				<input type="text" style="width: 75px; float: left; margin-right: 10px;" class="shadowBox" name="finalLaborPrice" id="finalLaborPrice" /> 				<span>Example format: 10.00 for $10</span>
			</td>		                				
			</tr>

			<!-- begin only display for upsell -->
			<tr id="upsolditems">
			<td align ="left">&nbsp;</td>				                				
			<td align ="left">
				<label style="display: block; float: left; width: 100px;">Add-on Services<span class="req"></span></label>
				$0.00
			</td>		                				
			</tr>
			<!-- end -->
			
			</table>

	</div>



	

