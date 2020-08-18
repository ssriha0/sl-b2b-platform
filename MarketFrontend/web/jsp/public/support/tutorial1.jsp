<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive Tutorials</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true">
		</script>
		
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/data/ItemFileReadStore.js">
		</script>
		
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/parser.js">
		</script>
		
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojox/presentation/ImageSlideShow.js">
		</script>
		
		<script type="text/javascript">
			//dojo.require("dojo.data.ItemFileReadStore");
			//dojo.require("dojox.image.SlideShow");

			//dojo.require("dojo.parser");

			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<script type="text/javascript">
	
			dojo.addOnLoad(function(){
				//Initialize the first SlideShow with an ItemFileReadStore
				dojo.parser.parse(dojo.body());
				dijit.byId('slideshow1').setDataStore(imageItemStore,
					{ query: {}, count:8 },
					{
						imageThumbAttr: "thumb",
						imageLargeAttr: "large"
					}
				);
			});
		</script>
		
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/support.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dojo_slideshow.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript">
		</script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js">
		</script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</head>
	<body class="tundra noBg">
		<p>
			<img src="${staticContextPath}/images/support/hdrTut_buyersSL.gif" alt="img"
				title="How Buyers Use ServiceLive" height="17" width="202" />
		</p>
		<p class="paddingBtm">
			Buyers use ServiceLive to connect with service providers with very
			specific skills. This tutorial will help you get familiar with the
			basic workflow within ServiceLive. More detailed instruction can be
			found in the
			<a href="${contextPath}/jsp/public/support/tutorial1.jsp">create a service order tutorial.</a>
		</p>
		<div jsId="imageItemStore" dojoType="dojo.data.ItemFileReadStore"
			url="${contextPath}/jsp/public/support/tut1_images.jsp"></div>
		<div id="slideshow1" dojoType="dojox.image.SlideShow" imageWidth="715"
			imageHeight="280" fixedHeight="true" slideshowInterval="10"></div>
	</body>
</html>
