<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>ServiceLive - Order Injection Tool</title>
<%-- <s:head /> --%>
 <link href="jsp/soQueueing.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="wrapper">
<% String myenv = System.getProperty("environment"); %>
<s:include value="jsp/_header.jsp" />
	<div class="uploadFormCont">

	<s:fielderror />
	<s:form action="doUpload" method="post" enctype="multipart/form-data"  theme="simple"  cssClass="uploadForm">
		<fieldset>
			<% if(myenv == null || !myenv.equalsIgnoreCase("prod")) { %>

				<s:select label="Select Environment Queue"   name="environmentQue"
			    headerKey="-1"
			    headerValue="-- Please Select Environment Queue --"
			    list="#{
			    'DEV':'DEV',
			    'QA1':'QA1',
			    'QA2':'QA2',
			    'QA3':'QA3',
			    'QA4':'QA4',
			    'ENV1':'ENV1',
			    'ENV2':'ENV2',
			    'ENV3':'ENV3',
			    'ENV4':'ENV4'}"
			    />
		    <% }else { %>

				<s:select label="Select Environment Queue"  name="environmentQue"
			    headerKey="-1"
			    headerValue="-- Please Select Environment Queue--"
			    list="#{
			    'PROD':'PRODUCTION'}"
			    />
		    <% } %>
		    <s:select label="Select Correlation Id"    name="correlationId"
			    headerKey="-1"
			    headerValue="-- Please Select Correlation Id--"
			    list="#{
			    '1':'1',
			    '2':'2',
			    '3':'3',
			    '4':'4',
			    '5':'5',
			    '6':'6'}"
			    />
	    </fieldset>

	    <fieldset>
		    <s:file name="upload" label="File"/>
	    </fieldset>
       <s:submit value="Upload"/>
    </s:form>

   </div>
</div>
</body>
</html>