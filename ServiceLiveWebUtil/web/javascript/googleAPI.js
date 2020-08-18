function openGoogleMap(status,fromLoc,zip,toLoc){
      if(status=='110'){
     	 toLocVal = zip;
      }else{
          toLocVal = toLoc;
      }
      var fromLocVal = escape(fromLoc);
      var toVal = escape(toLocVal);      
      window.open('http://maps.google.com/maps?f=q&hl=en&geocode=&q=from:+'+fromLocVal+'+to:+'+toVal);
 }
 
