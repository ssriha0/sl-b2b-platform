var globalVal="";
 function positionInfo(object) {

  var p_elm = object;

  this.getElementLeft = getElementLeft;
  function getElementLeft() {
    var x = 0;
    var elm;
    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    while (elm != null) {
      x+= elm.offsetLeft;
      elm = elm.offsetParent;
    }
    return parseInt(x);
  }

  this.getElementWidth = getElementWidth;
  function getElementWidth(){
    var elm;
    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    return parseInt(elm.offsetWidth);
  }

  this.getElementRight = getElementRight;
  function getElementRight(){
    return getElementLeft(p_elm) + getElementWidth(p_elm);
  }

  this.getElementTop = getElementTop;
  function getElementTop() {
    var y = 0;
    var elm;
    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    while (elm != null) {
      y+= elm.offsetTop;
      elm = elm.offsetParent;
    }
    return parseInt(y);
  }

  this.getElementHeight = getElementHeight;
  function getElementHeight(){
    var elm;
    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    return parseInt(elm.offsetHeight);
  }

  this.getElementBottom = getElementBottom;
  function getElementBottom(){
    return getElementTop(p_elm) + getElementHeight(p_elm);
  }
}

function CalendarControl() {

  var calendarId = 'CalendarControl';
  var currentYear = 0;
  var currentMonth = 0;
  var currentDay = 0;

  var selectedYear = 0;
  var selectedMonth = 0;
  var selectedDay = 0;

  var months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
  var dateField = null;

  function getProperty(p_property){
    var p_elm = calendarId;
    var elm = null;

    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    if (elm != null){
      if(elm.style){
        elm = elm.style;
        if(elm[p_property]){
          return elm[p_property];
        } else {
          return null;
        }
      } else {
        return null;
      }
    }
  }

  function setElementProperty(p_property, p_value, p_elmId){
    var p_elm = p_elmId;
    var elm = null;

    if(typeof(p_elm) == "object"){
      elm = p_elm;
    } else {
      elm = document.getElementById(p_elm);
    }
    if((elm != null) && (elm.style != null)){
      elm = elm.style;
      elm[ p_property ] = p_value;
    }
  }

  function setProperty(p_property, p_value) {
    setElementProperty(p_property, p_value, calendarId);
  }

  function getDaysInMonth(year, month) {
    return [31,((!(year % 4 ) && ( (year % 100 ) || !( year % 400 ) ))?29:28),31,30,31,30,31,31,30,31,30,31][month-1];
  }

  function getDayOfWeek(year, month, day) {
    var date = new Date(year,month-1,day)
    return date.getDay();
  }

  this.clearDate = clearDate;
  function clearDate() {
    dateField.value = '';
    hide();
  }

  this.setDate = setDate;

  function setDate(year, month, day) {
    if (dateField) {
   
      var yearVal=new String(year);
      yearVal=yearVal.substr(2);
       var dateVal=month+"/"+day+"/"+yearVal;
        if (month < 10) {month = "0" + month;}
      if (day < 10) {day = "0" + day;}
       var dateVal1=month+"/"+day+"/"+year;
        var dateString = year+"-"+month+"-"+day;
       
	document.getElementById(globalVal).value=dateString;
      dateField.value = dateVal;
      
      if((globalVal=='txtArrivalDate1')||(globalVal=='txtArrivalDate2')||(globalVal=='txtDepDate1')||(globalVal=='txtDepDate2')){
       dateField.value = dateVal1;
      }
     if(globalVal=='hidStartDate'||globalVal=='hidEndDate'){
       document.getElementById('searchValue').value=dateVal1;
      }
      hide();
    }
    return;
  }

  this.changeMonth = changeMonth;
  function changeMonth(change) {
    currentMonth += change;
    currentDay = 0;
    if(currentMonth > 12) {
      currentMonth = 1;
      currentYear++;
    } else if(currentMonth < 1) {
      currentMonth = 12;
      currentYear--;
    }

    calendar = document.getElementById(calendarId);
    calendar.innerHTML = calendarDrawTable();
  }

  this.changeYear = changeYear;
  function changeYear(change) {
    currentYear += change;
    currentDay = 0;
    calendar = document.getElementById(calendarId);
    calendar.innerHTML = calendarDrawTable();
  }

  function getCurrentYear() {
    var year = new Date().getYear();
    if(year < 1900) year += 1900;
    return year;
  }

  function getCurrentMonth() {
    return new Date().getMonth() + 1;
  } 

  function getCurrentDay() {
    return new Date().getDate();
  }

  function calendarDrawTable() {

    var dayOfMonth = 1;
    var validDay = 0;
    var previous= 0;
    var startDayOfWeek = getDayOfWeek(currentYear, currentMonth, dayOfMonth);
    var daysInMonth = getDaysInMonth(currentYear, currentMonth);
    var css_class = null; //CSS class for each day

    var table = "<table cellspacing='0' cellpadding='0' border='0'>";
    table = table + "<tr class='header'>";
    table = table + "  <td colspan='2' class='previous'><a href='javascript:changeCalendarControlMonth(-1);'>&lt;</a> <a href='javascript:changeCalendarControlYear(-1);'>&laquo;</a></td>";
    table = table + "  <td colspan='3' class='title'>" + months[currentMonth-1] + "<br>" + currentYear + "</td>";
    table = table + "  <td colspan='2' class='next'><a href='javascript:changeCalendarControlYear(1);'>&raquo;</a> <a href='javascript:changeCalendarControlMonth(1);'>&gt;</a></td>";
    table = table + "</tr>";
    table = table + "<tr><th>S</th><th>M</th><th>T</th><th>W</th><th>T</th><th>F</th><th>S</th></tr>";

    for(var week=0; week < 6; week++) {
      table = table + "<tr>";
      for(var dayOfWeek=0; dayOfWeek < 7; dayOfWeek++) {
        if(week == 0 && startDayOfWeek == dayOfWeek) {
          validDay = 1;
        } else if (validDay == 1 && dayOfMonth > daysInMonth) {
          validDay = 0;
        }
 if(globalVal == 'newStartDate' || globalVal == 'newEndDate') {

        var month = getCurrentMonth();
      	var day  = getCurrentDay();
     	var year = getCurrentYear();
 
		if(globalVal == 'newEndDate'){
		 	var start = document.getElementById('newStartDate').value;
		 
			if(start!=''){
			  var dateParts = start.split("-");
			  year = parseInt(dateParts[0],10);
			  month = parseInt(dateParts[1],10);
			  day = parseInt(dateParts[2],10);
		    }
		 
		}    
		   
		if(currentYear < year){	
			previous=1;
		}else{
			if(currentMonth < month && currentYear == year){
			previous=1;
		
			}else{
			 if(currentMonth == month && currentYear == year && dayOfMonth < day){
				previous=1;
			}
		   }
		}
 }

        if(validDay) {
          if (dayOfMonth == selectedDay && currentYear == selectedYear && currentMonth == selectedMonth) {
            css_class = 'current';
          } else if (dayOfWeek == 0 || dayOfWeek == 6) {
            css_class = 'weekend';
          } else {
            css_class = 'weekday';
          }
		if(previous){
			css_class = 'empty';
			table = table + "<td class='"+css_class+"'>&nbsp;"+dayOfMonth+"&nbsp;</td>";

		}else{
          table = table + "<td><a class='"+css_class+"' href=\"javascript:setCalendarControlDate("+currentYear+","+currentMonth+","+dayOfMonth+")\">"+dayOfMonth+"</a></td>";
          }
          dayOfMonth++;
        } 
        else {
      
          table = table + "<td class='empty'>&nbsp;</td>";
        }
        
        previous=0;
      }
      table = table + "</tr>";
    }

    table = table + "<tr class='header'><th colspan='7' style='padding: 3px;'><a href='javascript:clearCalendarControl();'>Clear</a> | <a href='javascript:hideCalendarControl();'>Close</a></td></tr>";
    table = table + "</table>";

    return table;
  }

  this.show = show;
  function show(field) {
    can_hide = 0;
  
    // If the calendar is visible and associated with
    // this field do not do anything.
    if (dateField == field) {
      return;
    } else {
      dateField = field;
    }

    if(dateField) {
      try {
        var dateString = new String(document.getElementById(globalVal).value);
        var dateParts = dateString.split("-");
        
        selectedYear = parseInt(dateParts[0],10);
        selectedMonth = parseInt(dateParts[1],10);
        selectedDay = parseInt(dateParts[2],10);
      } catch(e) {}
    }

    if (!(selectedYear && selectedMonth && selectedDay)) {
      selectedMonth = getCurrentMonth();
      selectedDay = getCurrentDay();
      selectedYear = getCurrentYear();
    }

    currentMonth = selectedMonth;
    currentDay = selectedDay;
    currentYear = selectedYear;

    if(document.getElementById){

      calendar = document.getElementById(calendarId);
      calendar.innerHTML = calendarDrawTable(currentYear, currentMonth);

      setProperty('display', 'block');

      var fieldPos = new positionInfo(dateField);
      var calendarPos = new positionInfo(calendarId);

      var x = fieldPos.getElementLeft();
      var y = fieldPos.getElementBottom();

      setProperty('left', x + "px");
      setProperty('top', y + "px");
 
      if (document.all) {
        setElementProperty('display', 'block', 'CalendarControlIFrame');
        setElementProperty('left', x + "px", 'CalendarControlIFrame');
        setElementProperty('top', y + "px", 'CalendarControlIFrame');
        setElementProperty('width', calendarPos.getElementWidth() + "px", 'CalendarControlIFrame');
        setElementProperty('height', calendarPos.getElementHeight() + "px", 'CalendarControlIFrame');
      }
    }
  }

  this.hide = hide;
  function hide() {
    if(dateField) {
      setProperty('display', 'none');
      setElementProperty('display', 'none', 'CalendarControlIFrame');
      dateField = null;
    }
  }

  this.visible = visible;
  function visible() {
    return dateField
  }

  this.can_hide = can_hide;
  var can_hide = 0;
}

var calendarControl = new CalendarControl();

function showCalendarControl(textField,hidField) {
  // textField.onblur = hideCalendarControl;
globalVal=hidField;
 calendarControl.show(textField);
}

function assignDate(textField,hidField) {
  // textField.onblur = hideCalendarControl;
var dateVal=textField.value;

var hidValue=document.getElementById(hidField).value;

if(dateVal!=''){
var dateStringNew=new String(dateVal);
var dateParts = dateStringNew.split("/");
        
       var selectedMonth = parseInt(dateParts[0],10);
        var selectedDay =  parseInt(dateParts[1],10);
        var yearVal=dateParts[2];
       var selectedYear = parseInt(dateParts[2],10);
      
        var day='';
        var month='';
        var year='';
       
        day=selectedDay;
        month=selectedMonth;
        if((selectedYear>=28)&&(selectedYear<1000) ){
        year='19'+yearVal;
         textField.value=selectedMonth+'/'+selectedDay+'/'+yearVal;
        if((hidField=='txtArrivalDate1')||(hidField=='txtArrivalDate2')||(hidField=='txtDepDate1')||(hidField=='txtDepDate2')){
        if(selectedDay<10){
       day='0'+day;
       }
        if(selectedMonth<10){
       month='0'+month;
       }
       textField.value = month+'/'+day+'/'+year;
      }
        }else if((selectedYear<28)&&(selectedYear<1000) ){
        year='20'+yearVal;
        if((hidField=='txtArrivalDate1')||(hidField=='txtArrivalDate2')||(hidField=='txtDepDate1')||(hidField=='txtDepDate2')){
        if(selectedDay<10){
       day='0'+day;
       }
        if(selectedMonth<10){
       month='0'+month;
       }
       textField.value = month+'/'+day+'/'+year;
      }
         textField.value=selectedMonth+'/'+selectedDay+'/'+yearVal;
        }else if((selectedYear>=1000)&&(selectedYear<10000) ){
        year=new String(yearVal);
        year=year.substr(2);
        textField.value=selectedMonth+'/'+selectedDay+'/'+year;
        year=yearVal;
        if((hidField=='txtArrivalDate1')||(hidField=='txtArrivalDate2')||(hidField=='txtDepDate1')||(hidField=='txtDepDate2')){
        if(selectedDay<10){
       day='0'+day;
       }
        if(selectedMonth<10){
       month='0'+month;
       }
       textField.value = month+'/'+day+'/'+year;
      }
        }
       if(selectedDay<10){
       day='0'+day;
       }
        if(selectedMonth<10){
       month='0'+month;
       }
       
     document.getElementById(hidField).value= year+'-'+ month+'-'+day;

}else if (dateVal=='' && ((hidField=='newStartDate')||(hidField=='newEndDate'))){
 	document.getElementById(hidField).value="";
}
 calendarControl.show(textField);
}
function clearCalendarControl() {
  calendarControl.clearDate();
  if(globalVal == 'newEndDate'){
	document.getElementById('newEndDate').value="";
}
  if(globalVal == 'newStartDate'){
	document.getElementById('newStartDate').value="";
  }
}

function hideCalendarControl() {
  if (calendarControl.visible()) {
    calendarControl.hide();
  }
}

function setCalendarControlDate(year, month, day) {
  calendarControl.setDate(year, month, day);
}

function changeCalendarControlYear(change) {
  calendarControl.changeYear(change);
}

function changeCalendarControlMonth(change) {
  calendarControl.changeMonth(change);
}

document.write("<iframe id='CalendarControlIFrame' src='javascript:false;' frameBorder='0' scrolling='no'></iframe>");
document.write("<div id='CalendarControl'></div>");
