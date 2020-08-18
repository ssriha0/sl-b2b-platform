<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>
		</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true">
		</script>

		<script type="text/javascript">
			dojo.require("dojo.data.ItemFileReadStore");
			dojo.require("dojox.image.SlideShow");
			dojo.require("dojo.parser");

			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		
		<script type="text/javascript">	
			dojo.addOnLoad(function(){
				//Initialize the first SlideShow with an ItemFileReadStore
				dojo.parser.parse(dojo.body());
				dijit.byId('slideshow1').setDataStore(imageItemStore,
					{ query: {}, count:20 },
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
		
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/support.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dojo_image.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
<%--
		<script language="javascript">
			selectedNav = function (){ $("supportLink").removeAttribute("href"); } 
			window.addEvent('domready',selectedNav);
		</script>
--%>
	</head>
	
	
	<body class="tundra" >
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Support - Tutorial"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<div id="headerShort-rightRail">
					<tiles:insertDefinition name="newco.base.topnav"/> 					
					<tiles:insertDefinition name="newco.base.blue_nav"/>					
					<div id="pageHeader">
						<img src="${staticContextPath}/images/support/hdr_support.gif"
							alt="Support" title="Support" />
						<div>
							<a href="support_main.jsp">Main</a> |
							<a href="support_faq.jsp">FAQ</a> | Tutorials
						</div>
					</div>
					<div id="rightRail">
						<jsp:include page="/jsp/public/homepage/body/modules/stay_connected.jsp" />
					
						<div class="content">
						</div>
					</div>
				</div>
				<!-- BEGIN CENTER -->
				<div class="colLeft711" style="width: 720px;">
					<div class="content">
						<p>
							The tutorials on this page make a great training resource for you
							and your staff. Each tutorial can be completed on your own
							schedule at your own convenience. Check our webinar schedule for
							information on special training sessions.
						</p>
					</div>
					<div jsId="imageItemStore" dojoType="dojo.data.ItemFileReadStore"
						url="${staticContextPath}/images/support/images.js"></div>

					<div id="slideshow1" dojoType="dojox.image.SlideShow"
						imageWidth="715" imageHeight="275"
						titleTemplate="@title, step @current of @total" fixedHeight="true"
						slideshowInterval="10"></div>
				</div>
				<div class="clearfix"></div>
			</div>

			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />			
			
		</div>
	</body>
</html>
