<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type"><title>Test Public APIs</title>


<style type="text/css">
div.message {
	background-color: yellow;
}

#container {
	width: 800px;
	background: #FFFFFF;
	margin: 0 auto;
	border: 1px solid #000000;
	text-align: left;
}
</style>

<%
String cookieOAuthName = "cookieOAuthName";
String cookieOAuthPass = "cookieOAuthPass";
Cookie cookies [] = request.getCookies ();
String oauthKey = null, oauthPass = null;
if (cookies != null) {
for (int i = 0; i < cookies.length; i++) {
  if (cookies [i].getName().equals (cookieOAuthName)) {
	  oauthKey = cookies[i].getValue();
    }
  if (cookies [i].getName().equals (cookieOAuthPass)) {
	  oauthPass = cookies[i].getValue();
    }
  }
}

if (oauthKey == null) 
	{
		oauthKey = "";
	}
else 
	{
		String oauthKeyVar=ESAPI.encoder().canonicalize(oauthKey);
		oauthKey=ESAPI.encoder().encodeForHTML(oauthKeyVar);
	}
if (oauthPass == null) 
	{
		oauthPass = "";
	}
else
	{
		String oauthPassVar=ESAPI.encoder().canonicalize(oauthPass);
		oauthPass=ESAPI.encoder().encodeForHTML(oauthPassVar);
	}
%>

<script type="text/javascript">
function SetCookie(cookieName,cookieValue) {
 var today = new Date();
 var expire = new Date();
 var nDays=100;
 expire.setTime(today.getTime() + 3600000*24*nDays);
 document.cookie = cookieName+"="+escape(cookieValue)
                 + ";expires="+expire.toGMTString();
// alert("Setting:" + cookieName + ":" + cookieValue);
}

function onChangeKey() {
   SetCookie("cookieOAuthName",  document.getElementById('key').value);
}

function onChangePass() {
	SetCookie("cookieOAuthPass",  document.getElementById('pass').value);
}
</script>

</head>

<body style="background-color: rgb(55, 55, 55);">
<center>
<div id="container">

<br />

<center>
<big><big><big><span style="font-weight: bold;">OAuth API Testing Tool</span></big></big></big>

</center>
<br></br>

<form name="myform" method="post" action="connetserver">
<table style="text-align: left; margin-left: auto; margin-right: auto; width: 650px;" border="0" cellpadding="1" cellspacing="1">
  <tbody>
		<tr>
			<td>Type</td>
			<td>
			  <select name="rType">
				<option value="GET">GET</option>
				<option value="POST">POST</option>
				<option value="PUT">PUT</option>
				<option value="DELETE">DELETE</option>
			  </select>
			</td>
		</tr>
		<tr>
		 <td style="width: 100px;">Server</td>
			 <td style="width: 321px;"><input name="server" size="53" value="http://172.22.16.65:8080/"></td>
			<td><small><small>http://172.22.16.65:8080/public/v1.1/wallet/balance</small></small></td>
		</tr>
		<tr>
			<td style="width: 100px;">User Key</td>
			<td style="width: 321px;"><input name="key" type="text" id="key" size="53" onchange="onChangeKey()" value="<%=oauthKey%>"></td>
		</tr>
		<tr>
			<td style="width: 100px;">User Secret</td>
			<td style="width: 321px;"><input name="pass" type="text" id="pass" size="53" onchange="onChangePass()" value="<%=oauthPass%>"></td>
		</tr>

		 <tr>
			<td style="width: 100px;">Parameter</td>
			<td style="width: 321px;">
			<textarea name="params" cols="40" rows="20"></textarea>
			<br>
			</td>
			<td>

			<small>Post Example:</small><br>
			<small><small>
			&lt;addFundsToWalletRequest accountType="Bank"&gt;<br>
			&nbsp;&nbsp; &lt;identification&gt;<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&lt;resourceId&gt;3075&lt;/resourceId&gt;<br>
			&nbsp;&nbsp; &lt;/identification&gt;<br>
			&nbsp;&nbsp;
			&lt;fundingSource&gt;1948363488&lt;/fundingSource&gt;<br>
			&nbsp;&nbsp;
			&lt;amount&gt;1999.00&lt;/amount&gt;<br>
			&nbsp;&nbsp; &lt;cvv&gt;111&lt;/cvv&gt;<br>
			&lt;/addFundsToWalletRequest&gt;
			</small></small>
			</td>
		</tr>

		<tr> <td style="width: 100px;"><INPUT TYPE=CHECKBOX NAME="proxy">Use Proxy<P></td>
		<td style="width: 321px;"></td>
		</tr>
		<tr align="center">
		<td></td>
		<td><br><br>
		       <div style="margin-left: 160px;">
		       <button name="button" type="submit" value="sendMsg">Submit</button>
			</div>
 			<br></br>
		</td></tr>
  </tbody>
</table>
</form>
</div>

</center>
