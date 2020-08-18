<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>[Provider - Accepted]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
		<script type="text/javascript" src="../../js/prototype.js"></script>
		<script type="text/javascript" src="../../js/tooltip.js"></script>
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		<link rel="stylesheet" type="text/css" href="../../css/main.css" />
		<link rel="stylesheet" type="text/css" href="../../css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="../../css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="../../css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="../../css/tooltips.css" />
        <link rel="stylesheet" type="text/css" href="../../css/so_details.css">
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<script language="JavaScript" type="text/javascript">
			//_commonSOMgr = new SOMRealTimeManager('../../ajax/AjaxJSonManager.action',10000);
		</script>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="../../css/dijitTitlePane-serviceLive.css">
        <link rel="stylesheet" type="text/css" href="../../css/dijitCalendar-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="../../css/service_order_wizard.css" />
	</head>
	<body class="tundra">
<div align="center">
		<div class="serviceOrderAcceptFrame">
			<div class="serviceOrderAcceptHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/checkmark.gif" border="0" align="left"></a><a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="serviceOrderAcceptFrameBody">
				<b>Terms &amp; Conditions</b>
				<br/>
				<br/>
				<div align="center">
					<textarea class="serviceOrderAcceptTextarea shadowboxedTextarea">Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Integer placerat molestie elit. Nam quis tortor nec dui convallis luctus. Cras dictum pellentesque sem. Duis ornare felis eu velit. Proin porttitor. Morbi in tortor. Integer purus est, imperdiet sed, cursus ut, mattis id, odio. Maecenas nisi. Nunc vehicula leo id ligula. Aenean non ipsum eget purus varius tristique. Vivamus eu pede. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos.

Proin dictum orci in enim. Morbi sapien. Etiam faucibus pulvinar lectus. Morbi mollis ipsum a arcu. Praesent lacinia, risus sed pulvinar facilisis, nibh dui dictum mi, ac consectetuer magna tortor vitae pede. Cras mauris augue, sodales non, sollicitudin ut, gravida at, nisi. Maecenas nonummy volutpat libero. Quisque pretium ante sed purus malesuada blandit. Nam eu erat sit amet elit nonummy ultrices. Sed bibendum, nulla quis semper faucibus, tortor pede consequat ligula, et porttitor lorem diam eget dolor.
	
Aliquam purus elit, vulputate eget, porta et, tincidunt sit amet, nisl. Duis vitae tortor sed lorem egestas feugiat. Phasellus vestibulum scelerisque ligula. Morbi mattis luctus tellus. Proin non urna. Nunc ut nunc sit amet ipsum blandit viverra. Ut ac nisi. Morbi condimentum felis nec massa mattis suscipit. Vivamus blandit nunc ut turpis. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec ut nisl non ipsum pretium tristique. Ut auctor vehicula ipsum. Sed malesuada nisl in ligula. Maecenas laoreet convallis pede. Integer gravida elit vel elit. Etiam scelerisque, augue id hendrerit imperdiet, lorem ante rhoncus erat, non ultrices velit pede in ante. Donec quis quam.
	
Vestibulum sollicitudin purus vel justo volutpat consequat. Aenean vel mauris ut pede facilisis scelerisque. Curabitur tincidunt, massa quis suscipit eleifend, arcu mi ornare arcu, vitae sollicitudin massa justo at nibh. Suspendisse aliquet imperdiet est. Nunc ut mi vitae elit facilisis consequat. Sed bibendum. Morbi sit amet turpis. Nulla congue. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Suspendisse pharetra. Suspendisse ut diam eu quam feugiat scelerisque. Proin tincidunt sagittis velit. Praesent sem. Donec ac tellus. Mauris scelerisque diam quis dolor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus nibh velit, convallis sed, sodales posuere, aliquet et, erat. Suspendisse potenti. Cras ultrices iaculis orci.
	
Nulla tortor nunc, malesuada non, posuere dignissim, hendrerit non, elit. Aenean bibendum urna in augue. Fusce tellus. Duis lacinia urna sit amet nulla. Fusce convallis, neque quis sodales convallis, urna nisl hendrerit orci, sit amet volutpat turpis neque sed est. Pellentesque viverra ullamcorper massa. Fusce egestas molestie nulla. Praesent congue consectetuer ante. Sed luctus venenatis turpis. Sed nonummy imperdiet arcu. Donec scelerisque. Suspendisse in neque eget sem ullamcorper fermentum. Vestibulum ut justo. Suspendisse consectetuer nunc sit amet ipsum. Duis non pede quis nunc scelerisque viverra. Mauris ut erat at ipsum consectetuer sagittis. Aliquam mattis odio sed odio. Nam metus dui, semper vel, euismod nec, condimentum ut, diam.
					</textarea>
				</div>
				<br/>
				<p>
					<input type="checkbox" class="shadowbox"/> <b>I accept these Terms &amp; Conditions</b>
				</p>
				<br/>
				<p>
					<a href="#"><img src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/accept.gif);" class="btnBevel" align="left" /></a><a href="#"><img src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);" class="btnBevel" align="right" /></a>
				</p>
			</div>
		</div>

		<br/>
		<br/>
		<br/>
		<br/>
		
		<div class="rejectServiceOrderFrame">
			<div class="rejectServiceOrderHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="rejectServiceOrderFrameBody">
				<b>Reason Code</b>
				<br/>
				<br/>
				<select class="rejectServiceOrderSelectBox">
					<option>Select Code</option>
				</select>
				<br/>
				<br/>
				<b>Comments</b>
				<br/>
				<br/>
					<textarea class="rejectServiceOrderTextarea shadowboxedTextarea"></textarea>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/accept.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
			</div>
		</div>
		
		<br/>
		<br/>
		<br/>
		<br/>
		
		<!-- Accept w/Conditions Step One -->
		<div class="acceptWithConditionsFrame" style="height: 110px;">
			<div class="acceptWithConditionsHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="acceptWithConditionsFrameBody">
				<b>Conditions</b>
				<br/>
				<br/>
				<select class="rejectServiceOrderSelectBox">
					<option>Select Code</option>
				</select>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
				<br/>
				<br/>
			</div>
		</div>
		
		<br/>
		<br/>
		<br/>
		<br/>
		<!-- Accept w/Conditions Step Two -->
		<div class="acceptWithConditionsFrame" style="height: 325px;">
			<div class="acceptWithConditionsHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="acceptWithConditionsFrameBody">
				<b>Conditions</b>
				<br/>
				<br/>
				<select class="rejectServiceOrderSelectBox">
					<option>Reschedule Service Date</option>
				</select>
				<br/>
				<br/>
				<table cellpadding="0px" cellspacing="0px">
					<tr>
						<td><strong>Date:</strong></td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo"/></td>
						<td width="2px"></td>
						<td>Specific</td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo" CHECKED/></td>
						<td width="2px"></td>
						<td>Range</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Service Window:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td colspan="3">Date</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
						</td>
						<td>&nbsp;to&nbsp;</td>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
    					</td>
					</tr>
					<tr>
						<td colspan="3">Time</td>
					</tr>
					<tr>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
						<td>&nbsp;to&nbsp;</td>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Offer Expiration:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
						</td>
						<td>&nbsp;at&nbsp;</td>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
					</tr>
				</table>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="132" height="22" style="background-image:url(${staticContextPath}/images/btn/acceptWithCond.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
				<br/>
				<br/>
			</div>
		</div>
		
		<br/>
		<br/>
		<br/>
		<br/>
		<!-- Accept w/Conditions Specific Date -->
		<div class="acceptWithConditionsFrame" style="height: 275px;">
			<div class="acceptWithConditionsHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="acceptWithConditionsFrameBody">
				<b>Conditions</b>
				<br/>
				<br/>
				<select class="rejectServiceOrderSelectBox">
					<option>Reschedule Service Date</option>
				</select>
				<br/>
				<br/>
				<table cellpadding="0px" cellspacing="0px">
					<tr>
						<td><strong>Date:</strong></td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo" CHECKED/></td>
						<td width="2px"></td>
						<td>Specific</td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo" /></td>
						<td width="2px"></td>
						<td>Range</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Requested Date / Time:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
						</td>
						<td>&nbsp;to&nbsp;</td>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Offer Expiration:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
						</td>
						<td>&nbsp;at&nbsp;</td>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
					</tr>
				</table>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="132" height="22" style="background-image:url(${staticContextPath}/images/btn/acceptWithCond.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
				<br/>
				<br/>
			</div>
		</div>
		
		
		<br/>
		<br/>
		<br/>
		<br/>
		<!-- Accept w/Conditions Increase Maximum Price -->
		<div class="acceptWithConditionsFrame" style="height: 280px;">
			<div class="acceptWithConditionsHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="acceptWithConditionsFrameBody">
				<b>Conditions</b>
				<br/>
				<br/>
				<select class="rejectServiceOrderSelectBox">
					<option>Reschedule Service date</option>
				</select>
				<br/>
				<br/>
				<table cellpadding="0px" cellspacing="0px">
					<tr>
						<td><strong>Date:</strong></td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo"/></td>
						<td width="2px"></td>
						<td>Specific</td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo" CHECKED/></td>
						<td width="2px"></td>
						<td>Range</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Amount:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td>
							&nbsp;Increase&nbsp;to&nbsp;</td>
						<td>
							$<input type="text" name="inc" size="15"/>
    					</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Offer Expiration:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
						</td>
						<td>&nbsp;at&nbsp;</td>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
					</tr>
				</table>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="132" height="22" style="background-image:url(${staticContextPath}/images/btn/acceptWithCond.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
				<br/>
				<br/>
			</div>
		</div>
		
		
		<br/>
		<br/>
		<br/>
		<br/>
		<!-- Accept w/Conditions Specific Date -->
		<div class="acceptWithConditionsFrame">
			<div class="acceptWithConditionsHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="acceptWithConditionsFrameBody">
				<b>Conditions</b>
				<br/>
				<br/>
				<select class="rejectServiceOrderSelectBox">
					<option>Service Date & Maximum Price</option>
				</select>
				<br/>
				<br/>
				<table cellpadding="0px" cellspacing="0px">
					<tr>
						<td><strong>Date:</strong></td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo" CHECKED/></td>
						<td width="2px"></td>
						<td>Specific</td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo" /></td>
						<td width="2px"></td>
						<td>Range</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Service Window:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td colspan="3">Date</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
						</td>
						<td>&nbsp;to&nbsp;</td>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
    					</td>
					</tr>
					<tr>
						<td colspan="3">Time</td>
					</tr>
					<tr>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
						<td>&nbsp;to&nbsp;</td>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Amount:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td>
							&nbsp;Increase&nbsp;to&nbsp;</td>
						<td>
							$<input type="text" name="inc" size="15"/>
    					</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Offer Expiration:</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
						</td>
						<td>&nbsp;at&nbsp;</td>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
					</tr>
				</table>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="132" height="22" style="background-image:url(${staticContextPath}/images/btn/acceptWithCond.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
				<br/>
				<br/>
			</div>
		</div>
		
		<div id="calendar" style="visibility: hidden; z-index: 100; position: absolute; left: 0px; top: 0px;" onClick="popUp(event,'calendar')">
    		<input id="calendar1" dojoType="dijit._Calendar" onChange="myHandler(this.id,arguments[0])" lang="en-us">
    	</div>
        <br/>
		<br/>
		<br/>
		<br/>
        <div class="rejectServiceOrderFrame">
			<div class="exitWithoutSavingHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="rejectServiceOrderFrameBody">
				Are you sure you want to exit without saving?
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/accept.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
			</div>
		</div>
		<br/>
		<br/>
		<br/>
		<br/>
		<div class="serviceOrderAcceptFrame">
			<div class="serviceOrderReleaseHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="serviceOrderAcceptFrameBody">
				If you can no longer complete this service order, you may release it to be re-routed to other providers.
There's no cost to release a service order, but doing so violates the agreement you've made with your
buyer and may have a negative impact on your overall provider reputation. You must select a reason
code and add comments to release the order.
				<br/>
				<br/>
				<b>Reason Code</b>
				<br/>
				<br/>
				<select style="width:548px;">
					<option>Select Code</option>
				</select>
				<br/>
				<br/>
				<b>Comments</b>
				<br/>
				<br/>
					<textarea  style="width:548px;"></textarea>
				<br/>
				<br/>
				<br/>
				
				<p>
					<a href="#"><img src="${staticContextPath}/images/common/spacer.gif" width="65" height="20" style="background-image:url(${staticContextPath}/images/btn/release.gif);" class="btn20Bevel" align="left" /></a><a href="#"><img src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);" class="btnBevel" align="right" /></a>
				</p>
			</div>
		</div>
        <br/>
		<br/>
		<br/>
		<br/>
        <div class="serviceOrderAcceptFrame">
			<div class="serviceOrderVoidHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="serviceOrderAcceptFrameBody">
				You may void a service order that hasn't been accepted without affecting your buyer rating; however,
your posting fee is nonrefundable. Rather than voiding the order, consider changing the price, the terms
and conditions, or the schedule – or void it now, but reactivate it the next time you need service.
				<br/>
				<br/>
				<b>Reason</b>
				<br/>
				<br/>
					<textarea  style="width:548px;"></textarea>
				<br/>
				<br/>
				<br/>
				
				<p>
					<a href="#"><img src="${staticContextPath}/images/common/spacer.gif" width="47" height="20" style="background-image:url(${staticContextPath}/images/btn/void.gif);" class="btn20Bevel" align="left" /></a><a href="#"><img src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);" class="btnBevel" align="right" /></a>
				</p>
			</div>
		</div>
        <br/>
		<br/>
		<br/>
		<br/>
        <div class="serviceOrderAcceptFrame" style="width: 300px;">
			<div class="serviceOrderRescheduleHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="serviceOrderAcceptFrameBody"  style="width: 285px;">
				If you need to reschedule your visit, you may offer a fixed date or a range of dates for an
alternate visit. If the buyer does not accept your change, you will need to honor your
original agreement.
				<br/>
				<br/>
				<b>Current Appointment</b>
				<br/>
                <b>Date(s)</b> June 21, 2007 - June 30, 2007<br>
                <b>Service Window</b> 9 AM - 12 PM
				<br/>
                <br>
					<table cellpadding="0px" cellspacing="0px">
					<tr>
						<td colspan="4"><strong>Service Date Type:</strong></td>
						
                    </tr>
                    <tr>
						<td><input type="radio" name="dateSpecStepTwo" CHECKED/></td>
						
						<td>Range</td>
						<td width="10px"></td>
						<td><input type="radio" name="dateSpecStepTwo" /></td>
						<td width="2px"></td>
						<td>Fixed</td>
					</tr>
				</table>
				<br/>
				<br/>
				<strong>Service Window</strong>
				<table cellspacing="0px" style="background-color: #ededed;border:4px solid #ededed;line-height: 18px">
					<tr>
						<td colspan="3">Date</td>
					</tr>
					<tr>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
						</td>
						<td>&nbsp;to&nbsp;</td>
						<td>
							<input type="text" class="shadowBox" style="width: 90px; position: relative" onFocus="popUp(event,'calendar')" onBlur="popUp(event,'calendar')"/>
    					</td>
					</tr>
					<tr>
						<td colspan="3">Time</td>
					</tr>
					<tr>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
						<td>&nbsp;to&nbsp;</td>
						<td>
							<select>
								<option>12:00 AM</option>
							</select>
						</td>
					</tr>
				</table>
				<br/>
				<br/>
				
				<p>
					<a href="#"><img src="${staticContextPath}/images/common/spacer.gif" width="126" height="20" style="background-image:url(${staticContextPath}/images/btn/requestreschedule.gif);" class="btn20Bevel" align="left" /></a><a href="#"><img src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);" class="btnBevel" align="right" /></a>
				</p>
			</div>
		</div>
        <br/>
		<br/>
		<br/>
		<br/>
        <div class="rejectServiceOrderFrame" style="width:239px;">
			<div class="increaseSpendLimitHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="rejectServiceOrderFrameBody" style="width:235px;">
				Your current spend limit is shown below. Enter
the new amount, click 'calculate' and check to
make sure the new spend limit is correct, then
click 'submit.' If you wish, you may provide a
reason for the increase.
				<br/>
				<br/>
				<table>
                	<tr>
                    	<td><b>Current Maximum Price:</b></td>
                        <td>$</td>
                        <td align="center">59.00</td>
                     </tr>
                     <tr>
                    	<td>New Maximum Price for Labor:</td>
                        <td>$</td>
                        <td><input type="text" size="4"></td>
                     </tr>
                     <tr>
                    	<td>New Maximum Price for Materials:</td>
                        <td>$</td>
                        <td><input type="text" size="4"></td>
                     </tr>
                     <tr>
                    	<td><b>New Maximum Price:</b></td>
                        <td>$</td>
                        <td align="center">59.00</td>
                     </tr>
                </table>
                <br>
				<b>Reason</b>
				<br/>
					<textarea class="rejectServiceOrderTextarea shadowboxedTextarea"></textarea>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/calculate.gif);float:left;" class="btnBevel"/>
                <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/submit.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
			</div>
		</div>
		
		<br/>
		<br/>
		<br/>
		<br/>
        <div class="rejectServiceOrderFrame" style="width:239px;">
			<div class="increaseSpendLimitHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="rejectServiceOrderFrameBody" style="width:235px;">
				Your current spend limit is shown below. Enter
the new amount, click 'calculate' and check to
make sure the new spend limit is correct, then
click 'submit.' If you wish, you may provide a
reason for the increase.
				<br/>
				<br/>
				<table>
                	<tr>
                    	<td><b>Current Maximum Price:</b></td>
                        <td>$</td>
                        <td align="center">59.00</td>
                     </tr>
                     <tr>
                    	<td>New Maximum Price for Labor:</td>
                        <td>$</td>
                        <td><input type="text" size="4"></td>
                     </tr>                    
                     <tr>
                    	<td><b>New Maximum Price:</b></td>
                        <td>$</td>
                        <td align="center">59.00</td>
                     </tr>
                </table>
                <br>
				<b>Reason</b>
				<br/>
					<textarea class="rejectServiceOrderTextarea shadowboxedTextarea"></textarea>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/calculate.gif);float:left;" class="btnBevel"/>
                <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/submit.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
			</div>
		</div>
		
		<br/>
		<br/>
		<br/>
		<br/>
        <div class="rejectServiceOrderFrame" style="width:239px;">
			<div class="increaseSpendLimitHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="rejectServiceOrderFrameBody" style="width:235px;">
				Your current spend limit is shown below. Enter
the new amount, click 'calculate' and check to
make sure the new spend limit is correct, then
click 'submit.' If you wish, you may provide a
reason for the increase.
				<br/>
				<br/>
				<table>
                	<tr>
                    	<td><b>Current Maximum Price:</b></td>
                        <td>$</td>
                        <td align="center">59.00</td>
                     </tr>
                       <tr>
                    	<td>New Provider Hourly Rate:</td>
                        <td>$</td>
                            <td><input type="text" size="4"> /hr</td>
                     </tr>
                     <tr>
                    	<td>New Maximum Price for Labor:</td>
                        <td>$</td>
                        <td><input type="text" size="4"></td>
                     </tr>
                     <tr>
                    	<td>New Maximum Price for Materials:</td>
                        <td>$</td>
                        <td><input type="text" size="4"></td>
                     </tr>
                     <tr>
                    	<td><b>New Maximum Price:</b></td>
                        <td>$</td>
                        <td align="center">59.00</td>
                     </tr>
                </table>
                <br>
				<b>Reason</b>
				<br/>
					<textarea class="rejectServiceOrderTextarea shadowboxedTextarea"></textarea>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/calculate.gif);float:left;" class="btnBevel"/>
                <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/submit.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
			</div>
		</div>
		
		<br/>
		<br/>
		<br/>
		<br/>
        <div class="rejectServiceOrderFrame" style="width:239px;">
			<div class="increaseSpendLimitHdr">
				<a href="#"><img src="${staticContextPath}/images/so_details/info_blue_bar.gif" border="0" align="right"></a>
			</div>
			<div class="rejectServiceOrderFrameBody" style="width:235px;">
				Your current spend limit is shown below. Enter
the new amount, click 'calculate' and check to
make sure the new spend limit is correct, then
click 'submit.' If you wish, you may provide a
reason for the increase.
				<br/>
				<br/>
				<table>
                	<tr>
                    	<td><b>Current Maximum Price:</b></td>
                        <td>$</td>
                        <td align="center">59.00</td>
                     </tr>
                      <tr>
                    	<td>New Provider Hourly Rate:</td>
                        <td>$</td>
                            <td><input type="text" size="4"> /hr</td>
                     </tr>
                     <tr>
                    	<td>New Maximum Price for Labor:</td>
                        <td>$</td>
                        <td><input type="text" size="4"></td>
                     </tr>
                     <tr>
                    	<td><b>New Maximum Price:</b></td>
                        <td>$</td>
                        <td align="center">59.00</td>
                     </tr>
                </table>
                <br>
				<b>Reason</b>
				<br/>
					<textarea class="rejectServiceOrderTextarea shadowboxedTextarea"></textarea>
				<br/>
				<br/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/calculate.gif);float:left;" class="btnBevel"/>
                <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="22" style="background-image:url(${staticContextPath}/images/btn/submit.gif);float:left;" class="btnBevel"/>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="55" height="22" style="background-image:url(${staticContextPath}/images/btn/cancel.gif);float:right;padding-right:18px;" class="btnBevel"/>
				<div style="clear:both;"></div>
			</div>
		</div>
		
		<br/>
		<br/>
		<br/>
		<br/>
    </div>
    	
    
 <script>
 
 function placeCalendar(left,top) {
	var cal = document.getElementById('calendar')
	cal.style.visibility = 'visible';
	cal.style.left = left;
	cal.style.top = top;
 }
 
 </script>
	</body>
</html>
