<html>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
<title>Get Solr Search Report</title>

<style type="text/css" media="screen">
<!--
body {
	padding: 0;
	margin: 0;
	background-color: #666;
	color: #000;
	text-align: center;
}

#contents {
	margin-top: 10px;
	margin-bottom: 10px;
	margin-right: auto;
	margin-left: auto;
	width: 600px;
	padding: 10px;
	background-color: #FFF;
	color: #000;
	text-align: left;
}

h1 {
	color: #333;
	background-color: transparent;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 20px;
}

p {
	color: #333;
	background-color: transparent;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 0.8em;
}

.code {
	color: #339;
	background-color: transparent;
	font-family: times, serif;
	font-size: 0.9em;
	padding-left: 40px;
}
-->
</style>


</head>
<body>
<div id="contents">
<form method="get" action="getReport" name="report">
<dl style="text-align: center;">
	<dt><br>
	</dt>
	<h1>Solr Search Statistics</h1>
	<dd>
	<div align="center">
	<table style="text-align: center; width: 350px; height: 100px;"
		2="" border="0" cellspacing="2">
		<tbody>
			<tr align="left">
				<td style="vertical-align: top;"><input name="qtype" checked="checked"
					value="top" type="radio">Top searched queries<br>
				</td>
			</tr>
			<tr align="left">
				<td style="vertical-align: top;"><input name="qtype"
					value="bottom" type="radio">Queries with not results<br>
				</td>
			</tr>
			<tr align="left">
				<td style="vertical-align: top;"><input name="qtype"
					value="b2c" type="radio">Queries with not results from B2C<br>
				</td>
			</tr>
			<!-- 
			<tr>
				<td style="vertical-align: top; text-align: left;">
				   <input name="qtype" value="search" type="radio"> Search&nbsp;<input name="search"><br>
				</td>
				<br><br></br>
			</tr>
			 -->
			<tr>
				<td style="vertical-align: top; text-align: left;">
				<dl>
					<dt><input size="2" name="rcount" value="10"> Number of results</dt>
				</dl>
				</td>
			</tr>
		</tbody>
	</table>
	</div>
	<br>
	</dd>
</dl>
<div style="text-align: center;">
<input type="submit" value="Submit" />
<br>
</div>
</form>
</div>
</body>
</html>

