
function alertX(msg) {
	//alert(msg);
	var len = msg.length;
	var size  = len*8;
	$("body #msgAlert").remove();
	var str = '<div class="alertX " id="msgAlert" style="width:'+size+'px;"></div>';
	$("body").append(str);
	$("#msgAlert").html(msg);
	$("#msgAlert").delay(7000).fadeOut(4000);
}

$(document).ready(function(){
	//Common For all page
	$(".hasDatepicker").datepicker();
	//Menu Script
	var linkKey = $("[name=linkNo]").first().val();	
	
	//$(".menuListDetailsClass").first().show();
	//$(".list-group-item").first().addClass("menuItemSelected");
	
	//alert("linkKey :: "+linkKey);
	$(".menuListDetailsClass").hide();
	$("#menuList_Details_"+linkKey).show();
	$("#menuList_"+linkKey).addClass("menuItemSelected");
	
	// SEt link value on click of menu item
	$(".list-group-item").click(function(){
		var id = this.id;
		var idKey = id.split("_")[1];
		
		$('[name=linkNo]').each(function(){
			$(this).val(idKey);
		});
		
		//Remove if present any where
		$(".list-group-item").removeClass("menuItemSelected");
		$(this).addClass("menuItemSelected");
	
		$(".menuListDetailsClass").hide();
		$("#menuList_Details_"+idKey).show();
		
		
	});
	

	
		//////////////////////////////////////////////////////
		//////////////////////////////////////////////////////
	
	
	
	 $(".errorHide").delay(4000).fadeOut(2000);
	 
	 
	 $(".errorBorder").hide();
	 $(".errorBorder").each(function(){
		var eId = this.id;
		var eleNm = eId.split(".")[0];
		
		$("[name="+eleNm+"]").css("border","1px solid red");
		$("[name="+eleNm+"]").attr("title",$(this).html());
	 });
	 
	 
	 $("a").keyup(function(e){
		 if($(this).val().length>15){
			 if($(this).width()<550){
			 var w = $(this).val().length*8;
			 $(this).width(w);
			 }
		 }
		 
	 });
	 
	 
	 $(".caps").keyup(function(){
		 $(this).val($(this).val().toUpperCase());
	 });
	 
	 $('.numOnly').keydown(function(event) {
         // Allow special chars + arrows 
         if (event.keyCode == 46 || event.keyCode == 8 || event.keyCode == 9 
             || event.keyCode == 27 || event.keyCode == 13 
             || (event.keyCode == 65 && event.ctrlKey === true) 
             || (event.keyCode >= 35 && event.keyCode <= 39)){
                 return;
         }else {
             // If it's not a number stop the keypress
             if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                 event.preventDefault(); 
             }   
         }
     });
	 
	 // Data Table Logic - Start
	 
	 $('[id*=dataTable]').each(function(){
			var tblId = $(this).attr("id");
			var str = "";
			var colNo = $("#" + tblId+" thead th").length;
			//alert("No of Cols : "+ c);
			if($("#"+tblId+" tbody tr").length==0){
				str = "<tr><td colspan='" + colNo + "'>No Records Present</td></tr>";
					$("#"+tblId+" tbody").append(str);
			} else{
			  var t = $("#" + tblId).DataTable( {
			        "columnDefs": [ {
			        	"stateSave": true,
			            "searchable": false,
			            "orderable": false,
			            "targets": [0,(colNo - 1)] // column number to be excluded
			        } ],
			        "order": [[ 1, 'asc' ]]
			    } );
			}
		});
	 
	 // Data Table Logic - End	 
	 
	 // ChkAll logic - Start
	 $("#chkAll").click(function(){
			if($(this).is(":checked")){
				$(".chk").prop('checked', true);
			}else{
				$(".chk").prop('checked', false);
			}
		})
		
		$(".chk").click(function(){
			var checkedLen = $(".chk:checked").length;
			var totalLen = $(".chk").length;
			if(checkedLen == totalLen){
				$("#chkAll").prop('checked', true);
			}else{
				$("#chkAll").prop('checked', false);
			}
		})
		//////////////
		
		$(document).on("click",".paginate_button",function(){
			var checkedLen = $(".chk:checked").length;
			var totalLen = $(".chk").length;
			if(checkedLen == totalLen){
				$("#chkAll").prop('checked', true);
			}else{
				$("#chkAll").prop('checked', false);
			}
		})
	 // ChkAll logic - End

		
});