<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>
	<div id="feedbackForm1" >
		<div id="modalTitleBlack"
			style="background: #58585A; border-bottom: 2px solid black; color: #FFFFFF; text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;border-radius: 10px 10px 0 0;-moz-border-radius: 10px 10px 0px 0px;">
			<div style="font-size: 17px; font-weight: bold;padding-top:3px;-moz-border-radius: 10px 10px 0px 0px;"> Send us your thoughts</div>
			<div style="float: right; padding: 5px; color: #CCCCCC;">
				<i class="icon-remove-circle" style="font-size: 20px; position: absolute; right: 5px; cursor: pointer; top: 5px;"></i>
			</div>
		</div>
		<div id="feedbackResponseMessage" class="errorBox clearfix" style="width: 514px;display: none;text-align: left;top:-6px;">
		</div>
		<div style="margin: 12px; font-size: 12px; height:300px;text-align:left">
			<div style="margin-bottom: 10px;">Whether you have an idea for a new feature, have tough time using the functionality, or just want to tell us
				what works for you, we'd love to hear about it!</div>
			<div style="margin-bottom: 10px;">If you have technical issues or need immediate help,
				please call 888-549-0640.</div>
			<div style="width: 400px; margin-bottom: 30px;">
				<div style="float: left; font-weight: bold;margin-left: 1px;">
					<strong>Screenshot</strong> (optional)
				</div>
			</div>
			<div style="float: left; font-weight: bold;margin-left: 1px;">
				<%-- <div class="fileinputs" style="float: right;margin-right:10px;">
					<input type="file" class="file" id="fbk_screenshot" style="size: 100px;"/>
					<div class="fakefile">
						<input />
						<img style="position: relative; float: left; top: 4px; left: 145px;" src="${staticContextPath }/images/widgets/tabClose.png" />
						<div style="display: block; position: relative; color: rgb(102, 102, 102); font-size: 11px; font-style: italic;" id="fbk_types">
						.png, .jpg or .gif, 5 MB max
						</div>
					</div>
				</div> --%>
				<div style="float: left; overflow-x: hidden;width: 220px;margin-right: 12px; ">
					<div style="border:1px solid #CCCCCC;height: 20px;">
					<!-- SL 18954 changing size of uploadFeedback to 31  -->
					<input size="31" type="file" id="uploadFeedback" name="uploadFeedback" class="fbk_file">
					</div>
					<input type="text"  style="position: relative; left: 199px; top: -21px;width: 20px; pointer-events: none;opacity:1; border-left: thick none;"></input>
					<i class="icon-camera" style="position: relative; left: 178px; top: -21px; pointer-events: none;opacity:1;border:none;"></i>
					<div
						style="display: block; position: relative;top:-15px; color: rgb(102, 102, 102); font-size: 11px; font-style: italic;font-weight: normal;"
						id="fbk_types">.png, .jpg ,.gif,.doc or .docx, 5 MB max</div>
				</div>
			</div>
			<div style="margin-top: 70px;">
				<div style="float: left; font-weight: bold;margin-left: -232px;margin-top:10px;">Comments <span style="color:#D22509;">*</span></div>
				<div>
					<textarea id="feedBackComments" name="feedbackComments"
						style="height: 76px; width: 478px; margin-top: 15px; font-family: Arial; color: #333333;"></textarea>
				</div>
			</div>
			<div style="margin-top: 10px;float:left;padding-bottom:10px;padding-right:12px;">
			<%--SL-18927 CR for Feedback Tab:Increasing the total character from 255 to 750  --%>
				<input id="comment_leftChars" type="text" value="750" size="1" name="comment_leftChars" readonly="readonly">
 characters remaining 
			</div>
			<div class="modal-footer"
				style="width: 99%; margin-left: -12px; height: 22px;border-radius: 0 0 10px 10px;-moz-border-radius: 0px 0px 10px 10px;">
				<div style="right: 12px; margin-bottom: 10px; float: right">
					<input type="button" value="Send Message" id="submitFeedbackbtn" class="feedBackActionButton feedBackActionButtonBackGround" style="font-size: 12px;" />
					<input type="hidden" value="false" id="clickedInd">
				</div>
			</div>
		</div>
	</div>
</body>
</html>