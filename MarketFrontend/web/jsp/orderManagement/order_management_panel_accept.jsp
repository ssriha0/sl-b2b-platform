<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="xTimerSeconds" value="${xTimerSeconds}"/>

<div class="timerArea">
	<p id="acceptCountdownLabel" style="width:230px;font-weight:bold;">
		You will be able to accept this order in:</p>
	<div id="acceptCountdown">       
		<div id="countdownArea"></div>
		<img class="loadImage" src="${staticContextPath}/images/icons/animated/loadingIndicator.gif"></img>
	</div>
<div>

<input type="hidden" id="timerVal" value="${xTimerSeconds}"/>

<style>
.countdownTime {font-size: 2.5em; line-height:120%;}
.timerArea {position:absolute; margin-left:280px; margin-top:-60px;}
.loadImage {position:absolute;margin-top:-20px;margin-left:100px;}
</style>

<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/countdown/jquery.countdown.min.js"></script>			
<script type="text/javascript">

	function timerExpired(){
		$('#acceptOrder').show();
		$('#acceptCountdownLabel').hide();
		$('#acceptCountdown').hide();
	}
	    	
	function loadTimer(){
	    var xTimerSeconds = '${xTimerSeconds}';	
	
		if (xTimerSeconds > 0){	    	
		    $('#countdownArea').countdown({
				compact:true,
				until:+xTimerSeconds,
				format:'MS',
				layout: '<span class="countdownTime">{mn}:{snn}</span> minutes',
				onExpiry: timerExpired
			});
		} else{
			timerExpired();
		}
	}
	
</script>	
