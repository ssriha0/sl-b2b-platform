<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>ServiceLive - Order Injection Tool</title>
	<link href="/ServiceOrderQueueing/jsp/soQueueing.css" rel="stylesheet" type="text/css"/>
  </head>

  <body>
  	<div id="wrapper">
	  <s:include value="_header.jsp" />
	  <div class="content">
		<p><strong>An error has occurred.</strong></p>
		    File Name: <s:property value="uploadFileName" /><br />
		    Release: <s:property value="release" /><br />
		    ErrorMsg: <s:property value="errorMsg" /><br />
		 <p><a href="/ServiceOrderQueueing/index.jsp"><< Try again</a></p>
	   </div>
  	</div>
  </body>
</html>
