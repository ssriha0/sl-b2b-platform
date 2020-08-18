

	
function show(itemID)
{
	document.getElementById(itemID).style.display="block"
}

function hide(itemID)
{
	document.getElementById(itemID).style.display="none"
}

function showDiv(divID)
{
	alert('show ' + divDI);
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id == divID.toString())
		{
	   		doc[i].style.display = "block";
	   	}
	}
}

function hideDiv(divID)
{
	alert('hide ' + divDI);
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id == divID.toString())
		{
	   		doc[i].style.display = "none";
	   	}
	}
}

function toggleAltContactDisabled(divID)
{
	alert('toggleAltContactDisabled');
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id == divID.toString())
		{
	   		if(doc[i].style.display == "block")
	   			doc[i].style.display == "none";
	   		else
	   			doc[i].style.display == "block";
	   	}
	}
}


function hideAllDivsExcept(showID)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id == showID.toString())
		{
	   		doc[i].style.display = "block";
	   	}
	   	else
	   	{
	   		doc[i].style.display = "none";
	   	}
	}
}

function showAllDivs(showID)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id == showID.toString())
		{
	   		doc[i].style.display = "block";
	   	}
	}
}

function hideMostAndDisplayOne(dontHideID, showID)
{
	hideAllDivsExcept(dontHideID);
	showAllDivs(showID);
}


function showAllDivs(showID)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id == showID.toString())
		{
	   		doc[i].style.display = "block";
	   	}
	}
}

function hideDivsEndingWith(hide)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id.indexOf(hide) > 0)
		{
	   		doc[i].style.display = "none";
	   	}
	}
}

function showDivsEndingWith(hide)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id.indexOf(hide) > 0)
		{
	   		doc[i].style.display = "block";
	   	}
	}
}

function showHideDivsEndingWith(show, hide)
{
	showDivsEndingWith(show);
	hideDivsEndingWith(hide);
}


function hideAllDivsThatEndWith(hide, show)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id.indexOf('_so_expanded') > 0)
		{
	   		doc[i].style.display = "none";
	   	}
	   	
		if(doc[i].id.indexOf('_so_collapsed') > 0)
		{
	   		doc[i].style.display = "block";
	   	}
		
	   	var expanded = show + "_so_expanded";	   		
	   	if(doc[i].id == expanded)
	   	{
	   		doc[i].style.display = "block";
	   	}
	   	var collapsed = show + "_so_collapsed";
	   	if(doc[i].id == collapsed)
	   	{
	   		doc[i].style.display = "none";
	   	}
	}
	
}

function hideActionWidgets(extension, show)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id.indexOf(extension) > 0)
		{
	   		doc[i].style.display = "none";
	   	}
	   	
		
	   	var expanded = show + extension;	   		
	   	if(doc[i].id == expanded)
	   	{
	   		doc[i].style.display = "block";
	   	}
	}
	
}

