jQuery.noConflict();
jQuery(document).ready(function($){
	$("li.parentNavItem").mouseover(function(){
		$(this).addClass("over");
	});
	$("li.parentNavItem").mouseout(function(){
		$(this).removeClass("over");
	});
});