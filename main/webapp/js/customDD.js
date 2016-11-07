/**
 * Created By Sarbeswar
 */

$(document).ready(function(){
	
	$(document).on('click', '.dropdown-menu', function(e) {
	    if ($(this).hasClass('keep-open-on-click')) { e.stopPropagation(); }
	});
	$(".customDD").click(function(){
		var len = $(".customDD [type=checkbox]:checked").length;
		if(len == 0){
			$(".customDD [type=text]").attr("placeholder","Select ...");
		}else{
			$(".customDD [type=text]").val(len + " Selected");
		}
	});
	
})