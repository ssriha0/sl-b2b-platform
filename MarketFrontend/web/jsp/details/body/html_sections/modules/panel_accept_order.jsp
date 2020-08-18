<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
        <c:set var="xTimerSeconds" value="${xTimerSeconds}"/>
        			<p id="acceptCountdownLabel" class="left" style="width:230px;font-weight:bold; margin-top:30px;">You will be able to accept this order in:</p>
				<div id="acceptCountdown" class="left">       
				<div id="countdownArea"></div>
				<img style="" src="${staticContextPath}/images/icons/animated/loadingIndicator.gif"></img>
				</div>
				
	    	<script type="text/javascript">
	    	jQuery(document).ready(function($){
				function timerExpired(){
					jQuery('#acceptButtonForCarOrders').hide();
					jQuery('#acceptButton').show();
					jQuery('#securityCode').keypress(function(event){processEnter(event);});
					jQuery('#acceptCountdownLabel').hide();
					jQuery('#acceptCountdown').hide();
				}
			});

	    	function loadTimer(){
          var xTimerSeconds = '${xTimerSeconds}';	

	    	if (xTimerSeconds > 0){	    	
        $('#countdownArea').countdown({
	    	compact:true,
	    	until:+xTimerSeconds,
	    	format:'MS',
	    	layout: '<span class="countdownTime">{mn}:{snn}</span> minutes',
	    	onExpiry: timerExpired});
	    	} else{
	    	timerExpired();
	    	}
		  	 
	    	}
	    	</script>	
