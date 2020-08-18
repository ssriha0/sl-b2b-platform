function setCookie(cookie_name,value,expiryDays)
{
	var expiryDate=new Date();
	expiryDate.setDate(expiryDate.getDate() + expiryDays);
	var cookie_value=escape(value) + ((expiryDays==null) ? "" : "; expires="+expiryDate.toUTCString());
	document.cookie=cookie_name + "=" + cookie_value;
}

function getCookie(cookie_name)
{
	var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++)
	{
		x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
		y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
		x=x.replace(/^\s+|\s+$/g,"");
		if (x==cookie_name)
		{
			return unescape(y);
		}
	}
}
