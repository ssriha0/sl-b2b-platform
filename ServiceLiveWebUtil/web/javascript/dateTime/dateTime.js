
var hours;
var minutes;

var timeZone=-6; //set to time zone's offset from Universal Time
var timeZoneString='CST'; //set to time zone's label
var daylight='CDT'; //set to time zone's daylight or summertime label

// Return the two dates. first sunday of Nov and second sunday of March.
function isDST2(year){
	var t1,t2;
	// Get the date of the second sunday in March.
	for (var i_tem = 8; i_tem < 15; i_tem++){
		t1=new Date(year, 2, i_tem);
		if (t1.getDay()==0){
			break;
		}
	}
	// Get the date of the first Sunday in November.
	for (var i_tem = 1; i_tem < 8; i_tem++){
	t2=new Date(year, 10, i_tem);
		if (t2.getDay()==0){
			break;
		}
	}
	// Return the two dates as an array.
	return [t1.getDate(), t2.getDate()];
}

/*DST on (+1 hour): Starting in 2007, daylight time begins in the United States on the second Sunday in March and ends on the first Sunday in November. 
On the second Sunday in March, clocks are set ahead one hour at 2:00 a.m. local standard time, which becomes 3:00 a.m. local daylight time.
 On the first Sunday in November, clocks are set back one hour at 2:00 a.m. local daylight time, which becomes 1:00 a.m. local standard time. 

*/
function isDST(){
	var TimezoneOffset = timeZone; // adjust for time zone
	var localTime = new Date();
	var ms = localTime.getTime()
	+ (localTime.getTimezoneOffset() * 60000)
	+ TimezoneOffset * 3600000;
	var time = new Date(ms);
		if(time.getMonth()<2||time.getMonth()>10)
		return 0;
		else if(time.getMonth()>2&&time.getMonth()<10)
		return 1;
		else if (time.getMonth()==2&&time.getDate()>13)
		return 1;
		else if (time.getMonth()==2){
		if(time.getDate()>isDST2(time.getFullYear())[0])
		return 1;
		if(time.getDate()==isDST2(time.getFullYear())[0]&&(time.getHours()>1||(time.getHours()==1&&time.getMinutes()>58))){
		return 1;
		}
	return 0;
}
		if(time.getDate()<isDST2(time.getFullYear())[1]){
			return 1;
		}
	if(time.getDate()==isDST2(time.getFullYear())[1]&&(time.getHours()<1||(time.getHours()==1&&time.getMinutes()<59))){
		return 1;
	}
	return 0;
}

/**
This method computes the date after adding hrs and mins passed as input params.
Right now, it computes the CST, taking time of the client machine.
*/
function computeRequeueDateTime(hrs, mins){
// To convert the browser time to CST.
	var TimezoneOffset = timeZone+ isDST();
	//alert(TimezoneOffset);
	var localTime = new Date();
	var ms = localTime.getTime() 
             + (localTime.getTimezoneOffset() * 60000)
             + TimezoneOffset * 3600000;
	
	
	var now = new Date(ms);
	var h = (now.getHours() + parseInt(hrs));
	var m = (now.getMinutes()+ parseInt(mins));

	now.setHours(h);  // Update the hours
	now.setMinutes(m);  // Update the minutes
	
	var month = (now.getMonth()+1);
	var day = now.getDate();
	var year = now.getFullYear();
	hours = now.getHours(); 
	minutes = now.getMinutes();
	minutes = fixMinutesToInterval(minutes);
	
	var ap = "AM";
	
		if (hours   > 11 && hours < 24) { ap = "PM";        }
		if (hours   > 12) { hours = hours - 12; }
		if (hours   == 0) { hours = 12;        }
		
		
		if (minutes < 10){
			minutes = "0" + minutes;
		}
		if (hours < 10){
			hours = "0" + hours;
		}
		if (month < 10){
			month = "0" + month;
		}
		if (day < 10){
			day = "0" + day;
		}
	//alert(hours+'  ' +minutes);
		var time = hours + ":" + minutes + " "+ap;
		
	return month+'/'+day+'/'+year+'|~|'+time;
}

function getRequeueDateTime(hrs, mins){
	var now = new Date();
	var h = (now.getHours() + parseInt(hrs));
	var m = (now.getMinutes()+ parseInt(mins));

	now.setHours(h);  // Update the hours
	now.setMinutes(m);  // Update the minutes
	
	var month = (now.getMonth()+1);
	var day = now.getDate();
	var year = now.getFullYear();
	hours = now.getHours(); 
	minutes = now.getMinutes();
	minutes = fixMinutesToInterval(minutes);
	
	var ap = "AM";
		if (hours   > 11) { ap = "PM";        }
		if (hours   > 12) { hours = hours - 12; }
		if (hours   == 0) { hours = 12;        }
		
		if (minutes < 10){
			minutes = "0" + minutes;
		}
		if (hours < 10){
			hours = "0" + hours;
		}
		if (month < 10){
			month = "0" + month;
		}
		if (day < 10){
			day = "0" + day;
		}
		
		
		var time = hours + ":" + minutes + " "+ap;
		
	return month+'/'+day+'/'+year+'|~|'+time;
}



function fixMinutesToInterval(minutes){

		if(minutes >0 && minutes <= 15){
			minutes = 15;
		}
		if(minutes >15 && minutes <=30){
			minutes = 30;
		}

		if(minutes > 30 && minutes <=45){
			minutes = 45;
		}

		if(minutes > 45 && hours < 23){
			minutes = 0;
			hours=hours+1;
		}else if(minutes>45 && hours  >= 23){
			minutes = 45;
			
		}
		return minutes;
		
}







