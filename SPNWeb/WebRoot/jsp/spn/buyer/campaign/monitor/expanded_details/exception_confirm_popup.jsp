<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript">

</script>
</head>

<body>
<div id="confirmExc${spnId}" style="font-size: 13px;">
<a><img src="${staticContextPath }/images/widgets/tabClose.png"
					style="float:right; cursor: pointer;" onclick="closeConfirmExpModal(${spnId});" class="close" /></a>
						<h2 class="modal-title" style="font-family: Arial;text-align: left; ">
					<div id="title">Confirm Exception</div>
	</h2>
		<div id="confirmExcModalContent" class="modal-content" style="color: #666666;overflow-x: hidden;padding-bottom: 0px;">				
		
	<div style="margin-top: 10px; text-align: left; margin-left: 5px; color:black;">
					 
	<div style="margin-top: 10px; text-align: left;">Do you want to save this exception with the following settings?</div>

	<div style="margin-top: 10px; text-align: left;">Requirement : <span id="credentials_${spnId}" style="padding-left:43px;"></span></div>

	<div id="gracePeriod_${spnId}" style="display:none;">
	<div style=" text-align: left;">Grace Period : <span id="exceptionGraceValue_${spnId}" style="padding-left:40px;"></span></div>
	</div>
	<div id="states_${spnId}" style="display:none;word-wrap:break-word;">
	<div style=" text-align: left;">States Exempted : <span id="exceptionStateValue_${spnId}"style="padding-left:10px;"></span>
	</div>
	</div>
	<div id="expetionStatus_${spnId}">
	<div style="text-align: left;">Status : <span id="expStatus_${spnId}" style="padding-left:79px;"></span></div>
	</div>
	<div style="margin-top: 13px; text-align: left;"><b>Note:</b>&nbsp;Depending upon the time of day, it can take between 4-8 hours for these settings to go into effect.</div>

				</div>																		
				<br />
				</div>
	<div class="modal-footer" style="font-family: Arial;margin-top: 0px;">
						<span class="clearfix">
							<a class="cancel jqmClose left" href="#" style="text-decoration: underline;  text-transform: capitalize; float: left; color: black;">Cancel</a>
						</span>
						<span>
							<input id="confirmButton" class="actionButton" type="button" value="Continue" style="border:none;font-family: Verdana; font-size: 12px; font-weight: bold; font-style: normal; text-decoration: none;float: right; text-transform: capitalize;margin-top:-20px; float: right" onclick="saveExceptions(${spnId})"/>
							</span>
				</div>		
									
		</div>
</body>
</html>