<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="SO_NOTE_MESSAGE_MAX_LENGTH" value="<%= OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH%>" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theId" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />

<div dojoType="dijit.TitlePane" title="Add Note" id="widget_add_note_${theId}"
	style="padding-top: 1px; width: 249px;" open="false">
	<span class="dijitInfoNodeInner"><a href=""></a> </span>
	<!-- nested divs because wipeIn()/wipeOut() doesn't work right on node w/padding etc.  Put padding on inner div. -->
	<table cellpadding="0" cellspacing="0" border="0">
	<input type="hidden" name="radioSelection" id="radioSelection" value="" />
		<tr>
			<td nowrap="true" colspan = 2>
				<label style="display: none; color: red"
					id="${theId}subjectLabelMsg">
					Subject is required
				</label>
			</td>
		</tr>
		<tr>
			<td nowrap="true" colspan = 2>
				<label style="display: none; color: red"
					id="${theId}subjectCCValidateLabelMsg">
					Do not enter credit card number in Subject
				</label>
			</td>
		</tr>
		<tr>
			<td nowrap="true" colspan = 2>
				<label style="display: none; color: red"
					id="subjectLabelMsg${theId}">
					Message is required
				</label>
			</td>
		</tr>
		<tr>
			<td nowrap="true" colspan = 2>
				<label style="display: none; color: red"
					id="messageCCValidateLabelMsg${theId}">
					Do not enter credit card number in Message
				</label>
			</td>
		</tr>
		<tr class="error">
			<td colspan="2" class="errMsg alignRight">
				<div id="addNoteWidgetResponseMessage${theId}"></div>
			</td>
		</tr>
		<tr>
			<td class="" nowrap="true" colspan=2>
				<b>Subject</b>						
			</td>
		</tr>
		<tr>						
			<td class="" nowrap="true" colspan=2>
				<input type="text" name="subject" id="subject${theId}"
				onblur="captureNote()"
					style="width: 205px;" class="shadowBox grayText"
					onfocus="resetAddNoteSubject()" value="" />
			</td>
		</tr>
		<tr>
			<td class="" nowrap="true" colspan=2>
				<b>Message</b>						
			</td>
		</tr>
		<tr>
			<td class="" nowrap="true" colspan=2>
				<textarea style="width: 205px;" name="message"
				onblur="captureNote();"	id="message${theId}" class="shadowBox grayText" onkeydown="javascript:countWidgetTextAreaChars(${SO_NOTE_MESSAGE_MAX_LENGTH}, event,'${theId}');"
				onkeyup="javascript:countWidgetTextAreaChars(${SO_NOTE_MESSAGE_MAX_LENGTH}, event,'${theId}');"></textarea>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="true" colspan=2>
				<input type="text" name="message_leftChars" id="message_leftChars" style="border: 0px;background: #EEEEEE;width: 22px " readonly maxlength="4" value="${SO_NOTE_MESSAGE_MAX_LENGTH}"> &nbsp;<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
			</td>
		</tr>	
	</table>
	<div class="clearfix">
		<input type="radio"  name="radioId" id="radio${theId}" value="0"/>Send email alert
		<input type="radio"  name="radioId" id="radio${theId}" value="2" checked="checked"/>No email alert
	</div>
	<div class="clearfix">
		<input type="radio"  name="radioId"  id="radio${theId}" value="1"/>Private note <br/>&nbsp;&nbsp;(visible to your company and <br/>&nbsp;&nbsp; ServiceLive only)
	</div>	
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td align="left" width="50%">
				<a onClick="cancel('${theId}')"> 
					 <font class="red">Cancel</font> 
				</a>
			</td>
			<td align="right">
				<a href="#" id="addNoteButton" onclick="fnSubmitAddNote('${contextPath}/soAddNoteWidget.action?ss=${securityToken}',addNoteWidgetCallBackFunction,'formHandler','${theId}')">
					<img src="${staticContextPath}/images/spacer.gif" width="72"
						height="22"
						id="addNoteImage"
						style="background-image: url(${staticContextPath}/images/btn/submit.gif);"
						class="btnBevel" />
				</a>
			</td>
		</tr>
	</table>
</div>
<!-- begin the other widget for the assurant stuff here -->

	

	
