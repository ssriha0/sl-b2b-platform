function showModal(obj, modelName) {
	passwordMenuButtonPosition = 0;
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

	showPopup(curtop, modelName);

}

function showPopup(topPos, modelName) {

	if (!passwordMenuButtonPosition) {
		passwordMenuButtonPosition = 0;
	}

	if (navigator.appName == "Microsoft Internet Explorer") {
		passwordMenuButtonPosition = document.documentElement.scrollTop
		+ (document.documentElement.clientHeight / 2);
	} else {
		passwordMenuButtonPosition = window.pageYOffset
		+ window.innerHeight / 2;
	}

	document.getElementById(modelName).style.top = passwordMenuButtonPosition
	+ "px";

	jQuery.noConflict();
	jQuery(document).ready(function($) {
		$("#" + modelName).jqm( {
			modal : true,
			toTop : true
		});
		$("#" + modelName).jqmShow();
	});
}

function hideModal(modelName){
	jQuery.noConflict();
	jQuery(document).ready(function($) {
		$('#' + modelName).jqmHide();
	});
}