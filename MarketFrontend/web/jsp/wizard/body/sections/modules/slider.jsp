<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	<head>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/tooltip.js"></script>
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_details.css">
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.Slider");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitCalendar-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_order_wizard.css" />
		<title>Dojo Slider Widget Demo</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
	</head>

	<body class="tundra">
		<div style="clear:both;" align="center">
			
			<h1>
				Slider
			</h1>
			Begining styling for the slider...
			<br/>
			<br/>
			<br/>
			<br/>
			<div dojoType="dijit.form.HorizontalSlider" name="horizontal1"
				onChange="dojo.byId('slider1input').value=parseInt(arguments[0]);"
				value="0"
				maximum="60"
				minimum="0"
				discreteValues="61"
				showButtons="false"
				intermediateChanges="true"
				style="width: 227px; height: 10px;"
				id="slider1">
				<div dojoType="dijit.form.HorizontalRule"
					container="bottomDecoration"
					count="5"
					style="height: 8px;"></div>
				<ol dojoType="dijit.form.HorizontalRuleLabels"
					container="bottomDecoration"
					count="5"
					class="customSliderTicks">
					<li>0</li>
					<li>15</li>
					<li>30</li>
					<li>45</li>
					<li>60</li>
				</ol>
			</div>
			<br/>
			<br/>
			<div style="display: inline;">
				<input readonly id="slider1input" size="4"/>
			</div>
			<br>
		</div>
	</body>
</html>
