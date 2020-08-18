<html>
<%@ page import="java.util.regex.*"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.owasp.esapi.ESAPI"%>
<head>

<script type="text/javascript">
 
 <%
	 //To check for the special chars('<' and '>') - for XSS
	 String pattern="[<>{}\\[\\]\\&()]";
	 Pattern p = Pattern.compile(pattern);
	 String errorMessage = "";
	 String latiVar=(String)request.getParameter("latitude");
	 String latiNew=ESAPI.encoder().canonicalize(latiVar);
	 String latiEnc=ESAPI.encoder().encodeForHTML(latiNew);
	 if(p.matcher(latiEnc).find()){
	 	errorMessage = "Error:Invalid latitude";
	 	latiEnc = latiEnc.replaceAll(pattern," ");
	 } 
	 String longiVar=(String)request.getParameter("longitude");
	 String longiNew=ESAPI.encoder().canonicalize(longiVar);
	 String longiEnc=ESAPI.encoder().encodeForHTML(longiNew);
	 if(p.matcher(longiEnc).find()){
	 	errorMessage = "Error:Invalid longitude";
	 	longiEnc = longiEnc.replaceAll(pattern," ");
	 } 
	 String rangeVar=(String)request.getParameter("range");
	 String rangeNew=ESAPI.encoder().canonicalize(rangeVar);
	 String rangeEnc=ESAPI.encoder().encodeForHTML(rangeNew);
	 if(p.matcher(rangeEnc).find()){
	 	errorMessage = "Error:Invalid range";
	 	rangeEnc = rangeEnc.replaceAll(pattern," ");
	 } 
	 String keyVar=(String)request.getParameter("key");
	 String keyNew=ESAPI.encoder().canonicalize(keyVar);
	String keyEnc=ESAPI.encoder().encodeForHTML(keyNew);
	  if(p.matcher(keyEnc).find()){
	 	errorMessage = "Error:Invalid key";
	 	keyEnc = keyEnc.replaceAll(pattern," ");
	 }  
 %>
 </script>
<script type="text/javascript" src="https://maps.google.com/maps?file=api&v=2&key=<%=keyEnc%>"></script>

<style type="text/css">
v\:* {
	behavior:url(#default#VML);
}
</style>

</head>
<body onunload="GUnload()">
<%=errorMessage %>
<div id="map" style="width: 269px; height: 217px;"></div><div></div>

<script type="text/javascript">
validateAndDraw();
function validateAndDraw(){
	//Calling the draw() method only if there is no error.
	<%if(errorMessage.equals("")){%>
		draw(<%=latiEnc%>, <%=longiEnc%>, <%=rangeEnc%>);
	<%}%>
}
function draw(lat, long, radius_mi, pnt)
{
	var map = new GMap2(document.getElementById("map"));
	var start = new GLatLng(lat, long);
	map.setCenter(start, 10);
	map.addControl(new GSmallMapControl());
	//map.addControl(new GScaleControl(256));
	
	var bounds = new GLatLngBounds();

	map.clearOverlays();
	bounds = new GLatLngBounds();
	var givenRad = radius_mi*1;
	var givenQuality = 360*1;
	var centre = pnt || map.getCenter()
	drawCircle(centre, givenRad, givenQuality, map, bounds);
	fit(map, bounds);
}
	
function drawCircle(center, radius, nodes, map, bounds, liColor, liWidth, liOpa, fillColor, fillOpa)
{
	radius = radius * 1.609344;
	var latConv = center.distanceFrom(new GLatLng(center.lat()+0.1, center.lng()))/100;
	var lngConv = center.distanceFrom(new GLatLng(center.lat(), center.lng()+0.1))/100;

	//Loop 
	var points = [];
	var step = parseInt(360/nodes)||10;
	for(var i=0; i<=360; i+=step)
	{
	var pint = new GLatLng(center.lat() + (radius/latConv * Math.cos(i * Math.PI/180)), center.lng() + 
	(radius/lngConv * Math.sin(i * Math.PI/180)));
	points.push(pint);
	bounds.extend(pint); //this is for fit function
	}
	fillColor = fillColor||liColor||"#0055ff";
	liWidth = liWidth||2;
	var poly = new GPolygon(points,liColor,liWidth,liOpa,fillColor,fillOpa);
	map.addOverlay(poly);
}

function fit(map, bounds)
{
	map.panTo(bounds.getCenter()); 
	map.setZoom(map.getBoundsZoomLevel(bounds));
}

</script>


</body>
</html>


