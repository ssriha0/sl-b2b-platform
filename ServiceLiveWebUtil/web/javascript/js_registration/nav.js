startList = function() {
 $ES("li.parentNavItem").each(
 	function(el) {
 		el.addEvent("mouseenter", notifyNavOver.pass([el]));
 		el.addEvent("mouseleave", notifyNavLeave.pass([el]));
 	}
 )
}

function notifyNavOver(el) {
	// console.log("notifyNavOver");
	el.addClass("over");
}

function notifyNavLeave(el) {
	// console.log("notifyNavLeave");
	el.removeClass("over");
}

window.addEvent('domready',startList);
