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
	<div id="modalTitleBlack"
		style="background: #58585A; border-bottom: 2px solid black; color: #FFFFFF; text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;border-radius: 10px 10px 0 0;-moz-border-radius: 10px 10px 0px 0px;">
		<span style="font-size: 17px; font-weight: bold;margin-top: 4px; position: absolute;"> Send us your
			thoughts</span>
		<div style="float: right; padding: 5px; color: #CCCCCC;">
			<i class="icon-remove-circle"
				style="font-size: 20px; position: absolute; right: 5px; cursor: pointer; top: 5px;"></i>
		</div>
	</div>
	<div style="margin: 18px; font-size: 12px; text-align: left;">
		<div style="margin-bottom: 10px;">Thanks for sharing! Your
			feedback will help to determine future improvements and shape
			ServiceLive into a better system. We may not be able to respond to
			every request, but know that your comments are extremely valuable!</div>
		<div style="margin-top: 10px;">Remember, if you need
			immediate help, please call 888-549-0640.</div>
	</div>
	<div class="modal-footer" style="height: 15px; width: 94.2%; border-radius: 0 0 10px 10px;-moz-border-radius: 0px 0px 10px 10px;">
		<div style="right: 12px; margin-bottom: 10px; float: right">
			<input type="button" value="Close" id="closeFbkThanks" style="position: relative; margin-top: -5px;"
				class="actionButton"/>
		</div>
	</div>
</body>
</html>