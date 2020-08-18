var passwordMenuButtonPosition;
var popupid;

function showResetModal(obj, id) {
	passwordMenuButtonPosition = 0;
	popupid = id;
	var curtop = 0;
	if (obj.offsetParent) {
		while (1) {
			curtop += obj.offsetTop;
			if (!obj.offsetParent) {
				break;
			}
			obj = obj.offsetParent;
		}
	} else {
		if (obj.y) {
			curtop += obj.y;
		}
	}
	passwordMenuButtonPosition = curtop;
	PasswordReset(curtop, id);
}


function PasswordReset(topPos, id) {

	if (!passwordMenuButtonPosition) {
		passwordMenuButtonPosition = 0;
	}
		
	if (navigator.appName == "Microsoft Internet Explorer") {
		passwordMenuButtonPosition =  document.documentElement.scrollTop +  (document.documentElement.clientHeight/2);
	} else {
		passwordMenuButtonPosition =   window.pageYOffset +  window.innerHeight / 2;		
	}
	

	document.getElementById("modalPasswordReset" + id).style.top = passwordMenuButtonPosition + "px";
	
	jQuery.noConflict();
	jQuery(document).ready(function ($) {
		$("#modalPasswordReset" + id).jqm({modal:true, toTop:true});
		$("#modalPasswordReset" + id).jqmShow();
	});
}

function pwdMenuMouseOver(id) {
	jQuery.noConflict();
	jQuery(document).ready(function ($) {
		$("#" + id).show();
	});
}

function pwdMenuMouseOut(id) {
	jQuery.noConflict();
	jQuery(document).ready(function ($) {
		$("#" + id).hide();
	});
}

function hideResetModal(){
     jQuery.noConflict();
     jQuery(document).ready(function($) {
     	$('#modalPasswordReset'+popupid).jqmHide();
     });
}

function getSelectedUserId() {
  return popupid;
}

function setSelectedUserId(rid) {
  popupid = rid;
}
