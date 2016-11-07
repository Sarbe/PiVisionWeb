
<%@page import="com.pivision.util.Constants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="headerInfo.jsp"></jsp:include>
<title>Schedule Page</title>

<script type="text/javascript">
	$(document).ready(function() {

			$("#schedule").addClass("active");

			if ("${MSG}" != "")
				alertX("${MSG}");
				
			$(".createSchedule").click(function() {
				var scheduleId = $(this).attr("schId");
				$("#editModalLabel").text("Create a new schedule" );
				
				$("#schedule_modal [name='scheduleId']").val("");
				$("#schedule_modal [name='scheduleName']").val("");
				$("#schedule_modal [name='fromDate']").val("");
				$("#schedule_modal [name='toDate']").val("");
				$("#schedule_modal [name='timeOfDay']").val("");
				$("#schedule_modal [name='presentationId']").val("");
				
				$("#schedule_modal #editSchedule").hide();
				$('#schedule_modal').modal({
					backdrop: 'static'
				})											
			});
			
			
			$("#dataTable1").on("click",".editSchedule",function() {
				var scheduleId = $(this).attr("schId");
				
				$("#editModalLabel").text("Edit scheduleId :: " + scheduleId );
				$("#schedule_modal [name='scheduleId']").val(scheduleId);
				
				$("#schedule_modal [name='scheduleName']").val($("#schName_"+scheduleId).text());
				$("#schedule_modal [name='fromDate']").val($("#schFromDate_"+scheduleId).text());
				$("#schedule_modal [name='toDate']").val($("#schToDate_"+scheduleId).text());
				//$("#schedule_modal [name='timeOfDay']").val($("#schTimeOfDay_"+scheduleId).text());
				$("#schedule_modal [name='presentationId']").val($("#schPstnId_"+scheduleId).attr("pstnId"));
				
				$('#schedule_modal').modal({
					  backdrop: 'static'
				})											
				//$("[name=scheduleListForm]").submit();
			});
			
			
			$("#dataTable1").on("click",".deleteSchedule",function() {
				var scheduleId = $(this).attr("schId");

				$("#selectedscheduleIds").val(scheduleId);
				bootbox.confirm("Are you sure to Delete the Schedule?", function(result) {
					if(result) {
						$("[name=scheduleListForm]").attr("action","/PiVisionWeb/schedule/removeSchedule");
						$("[name=scheduleListForm]").submit();
					}
				}); 
			
			});
			
			// Add Display to Schedule
			$("#dataTable1").on("click",".addDisplay",function() {
				var scheduleId = $(this).attr("schId");
				
				/* $("#active_display_"+scheduleId+" li[role=value]").each(function(){
					alert($(this).attr("value"));
				}) */
				
			 	$("#allDisplay  li span input").each(function(){
					$(this).prop("disabled",false);
					$(this).parent().removeClass("gray");
				}); 

				$("#active_display_"+scheduleId+" li[role=value]").each(function(){
					var actv_schd_val = $(this).attr("value");
				 	$("#allDisplay li span input").each(function(){
						if($(this).val() == actv_schd_val){
				 			$(this).prop("disabled",true);
				 			$(this).parent().addClass("gray");
						}
					});
				});
					
				//$('#allDisplay').SumoSelect({ csvDispCount: 3, search: true, searchText:'Search...' });
				
				var scheduleName = $("#schName_"+scheduleId).text();
				$("#publish_Schedule_Name").text(scheduleName);
				$("#schedule_publish_modal #publishScheduleId").val(scheduleId);
				
				$('#schedule_publish_modal').modal({
					  backdrop: 'static'
				}) 
				//$("[name=scheduleListForm]").submit();
			});
			
			// Publish Schedule
			$("#dataTable1").on("click",".publishSchedule",function() {
				var scheduleId = $(this).attr("schId");
				
				var schdList = $("#active_display_"+scheduleId+" li[role=value]");
				if(schdList.length > 0 ){
				
					var warning = false;
					
					schdList.each(function(){
						var sts = $.trim($(this).text().split(":")[1]);
						//alert("Status :: ('"+sts+"')");
						
						if( sts == '<%=Constants.SCHD_DEL_ACK %>' || sts == '<%=Constants.SCHD_STOPPED %>' ){
							warning = true;
						}
					})
					
					var r = true;
					if(warning){
						r = confirm("There are some Display with acknowledged/deleted status.\n"+
								"If published they will again be available for download.\n" +
								"Try to remove first from Active schedule details.\n\n"+
								"OR Still Do you want to Continue?");
					}else{
						r = confirm("Are you sure to publish the schedule?");
					}
					
					if(r){
						$("#selectedscheduleIds").val(scheduleId);
						$("[name=scheduleListForm]").attr("action","/PiVisionWeb/schedule/publishSchedule");
						$("[name=scheduleListForm]").submit();
					}
				}else{
					alertX("Assign some display before scheduling.");
				}
			});
			
			$("#dataTable1").on("click",".unPublishSchedule",function() {
				var scheduleId = $(this).attr("schId");
				$("#selectedscheduleIds").val(scheduleId);
				bootbox.confirm("Are you sure to Unpublish the Schedule?", function(result) {
					if(result) {
						$("[name=scheduleListForm]").attr("action","/PiVisionWeb/schedule/unpublishSchedule");
						$("[name=scheduleListForm]").submit();
					}
				}); 
			
			});
			
////////////////////////////////////////////////

			$("#dataTable1").on("click",".getImg",function() {
				var imgName = $(this).attr("imgName");
				var imgType = $(this).attr("imgType");
				$this = $(this);
				$.ajax({
				  method: "GET",
				  url: "/PiVisionWeb/presentation/getImage",
				  data: { 
					  imgFileName: imgName,
					  imgFileType: imgType
					  }
				}).done(function( imgData ) {
					//alert(imgData);
					var srcImg = "data:" + "image/jpg" + ";base64,"+ imgData;
					
					$("#lightBox .modal-body").html('<img class="lightBoxImg" src="'+ srcImg+'">');
					$("#lightBox").modal({
						  backdrop: 'static'
						})
					//$this.parent().html('<img id="showModal" alt="" width="50" height="25" src="'+srcImg+'">');
					//$this.remove();
				});
			
			});
			/* $("#dataTable1").on("click","#showModal",function() {
				var img = $(this).attr("src");
				$("#lightBox .modal-body").html('<img class="lightBoxImg" src="'+ img+'">');
				$("#lightBox").modal({
				  keyboard: false
				})
					
			}) */
			
			
			$("#dataTable2").on("click",".delete",function() {
				var actvSchIds = $(this).attr("actvSchIds");
				
				var warning = true;
				
				var sts = $("#actvStatus_"+ actvSchIds).text();
				if(sts == '<%=Constants.SCHD_STOPPED %>'){
					warning = confirm("The status is Delete. If deleted, Client display can no longer sync for delete.\n\n"
							+"Do you want to continue?");
				}else if(sts == '<%=Constants.SCHD_DOWNLOADED %>'){
					warning = confirm("The status is Downloaded. If deleted, Status will be changed to Delete for client Display to sync for delete.\n\n"
							+"Do you want to continue?");
				}
				if(warning){
					$("#activeScheduleIds").val(actvSchIds);
					$("[name=actvScheduleListForm]").submit();
				}
				
			});
			
			
			
			$("[name='fromDate']").datepicker({
				//minDate : 0,
				//maxDate : "+90D",
				//numberOfMonths : 2,
				dateFormat: 'yy-mm-dd',
				onSelect : function(selected) {
					$("[name='toDate']").datepicker("option", "minDate", selected);
				}
			});
			$("[name='toDate']").datepicker({
				//minDate : 0,
				//maxDate : "+90D",
				//numberOfMonths : 2,
				dateFormat: 'yy-mm-dd',
				onSelect : function(selected) {
					$("[name='fromDate']").datepicker("option", "maxDate", selected);
				}
			});
			
			
			
			// Multi Delete
			$("#delAll").click(function(){
				if($(".chk:checked").length > 0){
					var schdIds = [];
					$(".chk:checked").each(function(){
						//if($(this).is(":checked")){
							schdIds.push($(this).attr("schId"));
						//}
					})
					alert(schdIds.toString());
					var r = confirm("Do you want to delete selected Records ?");
					if(r){
						
						$("#selectedscheduleIds").val(schdIds.toString());
						$("[name=scheduleListForm]").attr("action","/PiVisionWeb/schedule/removeSchedule");
						$("[name=scheduleListForm]").submit();
					}
				}else{
					alertX("Please select some Records first.");
				}
					
			});
			
		//////////////
			
			
			
			
			//$('.styleSelect').SumoSelect({ search: true, searchText:'Enter here.' });

			/////////
			
			
			///
			
		});
</script>
<style type="text/css">
	.lightBoxImg {
		width: 100%;
		height: 100%
	}
</style>
</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<!-- Page Content -->
	<div class="container">

		<!-- Page Heading/Breadcrumbs -->
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					Manage Schedule <small></small>
				</h3>
				<!-- <ol class="breadcrumb">
					<li><a href="/PiVisionWeb/user/accountDetails">Home</a></li>
					<li class="active">Schedule</li>
				</ol> -->
			</div>
		</div>
		<!-- /.row -->

		<!-- Content Row -->
		<div class="row">
			<!-- Sidebar Column -->
			<div class="col-md-3">
				<div class="list-group">
					<a href="#" class="list-group-item" id="menuList_1">Schedule List</a> 
					<!-- <a href="#" class="list-group-item createSchedule">Create Schedule</a> -->
					<a href="#" class="list-group-item" id="menuList_2">Active Schedule</a>
					<a href="#" class="list-group-item createSchedule">Create Schedule</a>
				</div>
			</div>
			
			
			<!-- Content Column -->
			
			<div class="col-md-9 menuListDetailsClass" id="menuList_Details_1">
			<!-- <div class="information col-md-9">
				<ul>
					<li>Once unpublished. schedule cant be published for the same display until it is read by its target.</li>
					<li>The display dropdwon will show the present/to be read by target details.</li>
				</ul>
			</div> -->
			<!-- <div style="float: left;" class="col-md-12">
				<a href="#" class="createSchedule" style="float: right;font-size: 25px;"><i class="fa fa-plus-circle info" aria-hidden="true"></i></a>
			</div> -->
			<div class="col-md-12">
				<form action="/PiVisionWeb/schedule/modifySchedule" method="post"
					name="scheduleListForm">
					<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
					<input type="hidden" name="scheduleIds" id="selectedscheduleIds"> 
					<div class="table-responsive">
						<table class="table table-bordered table-condensed table-hover compact" id="dataTable1">
							<thead>
								<tr>
	<!-- 							<th><span>SL No.</span></th> -->
									<th><span class="chkboxclass"><input type="checkbox" id="chkAll"> </span></th>
									<th><span>Schd Id</span></th>
									<th><span>Schedule Name</span></th>
									<th><span>Start Date</span></th>
									<th><span>End Date</span></th>
									<!-- <th><span>Timing</span></th> -->
									<th><span>Presentation</span></th>
									<th><span>Display</span></th>
									<!-- <th><span>Created On</span></th> -->
									<th><div class="btn-group" role="group">
										<span class="dropdown-toggle" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false"> Action <span
											class="caret"></span>
										</span>
										<ul class="dropdown-menu dropdown-menu-right">
											<li><a href="#" id="delAll"><i class="fa fa-trash-o stop" aria-hidden="true"></i> Delete</a></li>
										</ul>
									</div></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty scheduleList}">
									<c:forEach var="schedule" items="${scheduleList}" varStatus="counter">
										<tr>
											<td>
												<c:if test="${empty schedule.activSschedules}">
													<span class="chkboxclass"><input type="checkbox" class="chk" schId="${schedule.scheduleId}">
												</span>
											</c:if>
											</td>
											<%-- <td>${counter.count}</td> --%>
											<td><span id="schId_${schedule.scheduleId}">${schedule.scheduleId}</span></td>
											<td><span id="schName_${schedule.scheduleId}">${schedule.scheduleName}</span></td>
											<td><span id="schFromDate_${schedule.scheduleId}">${schedule.from_date}</span></td>
											<td><span id="schToDate_${schedule.scheduleId}">${schedule.to_date}</span></td>
											<%-- <td><span id="schTimeOfDay_${schedule.scheduleId}">${schedule.timeOfDay}</span></td> --%>
											<td>
												<a class="getImg" imgName="${schedule.presentation.fileName}" imgType="${schedule.presentation.fileType}" style="cursor: pointer;">
													<span id="schPstnId_${schedule.scheduleId}" pstnId="${schedule.presentation.presentationId}">${schedule.presentation.presentationName}</span>
												</a>
											</td>
											<td>
												<c:if test="${not empty schedule.activSschedules}">
												<div class="dropup" >
												  <button class="btn-link dropdown-toggle" type="button" id="dropdownMenu1" style="font-size: 12px;"
												  	data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
												    	Display
												    <span class="caret"></span>
												  </button>
												  <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu1" style="font-size: 11px;" id="active_display_${schedule.scheduleId}">
												  <c:forEach var="activeSchedule" items="${schedule.activSschedules}" varStatus="counter">
													    <li role="value" value="${activeSchedule.display.macId}"><a href="#">${activeSchedule.display.displayName} : ${activeSchedule.actionFlag }</a></li>
													    <li role="separator" class="divider" style="margin: 2px 0;"></li>
												    </c:forEach>
												    
												  </ul>
												</div>
												
												
												<%-- <select class="form-control" style="height: 20px;font-size: 12px;width:auto;padding: 0" id="active_display_${schedule.scheduleId}">
													<c:forEach var="activeSchedule" items="${schedule.activSschedules}" varStatus="counter">
															<option value="${activeSchedule.display.macId}" >${activeSchedule.display.displayName}-${activeSchedule.actionFlag }</option>
													</c:forEach>
													</select> --%>
												</c:if>
	
											</td>
							
											<%-- <td><span>${schedule.lastAccessed}</span>
											<span>${schedule.published}</span>
											<input type="hidden" value="${schedule.presentation.presentationId}" > 
											</td> --%>
											<td>
											<span>
												<span class="actionIcon add addDisplay" title="" schId="${schedule.scheduleId}">
													<i class="fa fa-plus" aria-hidden="true"></i>
												</span>
												<c:if test="${schedule.published != 'Y'}">
													<span class="actionIcon delete deleteSchedule" title="Delete" schId="${schedule.scheduleId}">
														<i class="fa fa-times"aria-hidden="true"></i>
													</span> 
													<span class="actionIcon edit editSchedule" title="Edit" schId="${schedule.scheduleId}">
														<i class="fa fa-pencil" aria-hidden="true"></i>
													</span> 
													<span class="actionIcon publish publishSchedule" title="Publish" schId="${schedule.scheduleId}">
														<i class="fa fa-play-circle-o" aria-hidden="true"></i>
													</span> 
												</c:if>
												
												<c:if test="${schedule.published == 'Y'}">
													<span class="actionIcon stop unPublishSchedule" title="Stop" schId="${schedule.scheduleId}">
														<i class="fa fa-stop-circle-o" aria-hidden="true"></i>
													</span>
												</c:if>
												
												</span>
											</td>
										</tr>
									</c:forEach>
								</c:if>
	
							</tbody>
						</table>
					</div>
				</form>
				</div>
			</div>
			<div class="col-md-9 menuListDetailsClass" id="menuList_Details_2">
			
				<form action="/PiVisionWeb/schedule/removeActiveSchedule" method="post"
					name="actvScheduleListForm">
					<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
					<input type="hidden" name="activeScheduleIds" id="activeScheduleIds"> 
					<div class="table-responsive">
						<table class="table table-bordered table-condensed table-hover compact" id="dataTable2">
							<thead>
								<tr>
									<!-- <th style="padding: 4px 9px;"><span><input type="checkbox" id="chkAll"> </span></th> -->
									<th><span>SL No</span></th>
									<th><span>Actv Schd Id</span></th>
									<th><span>Schedule Name</span></th>
									<th><span>Presentation</span></th>
									<th><span>Display</span></th>
									<!-- <th><span>MAC ID</span></th> -->
									<!-- <th><span>Frequency</span></th> -->
									<th><span>Duration</span></th>
									<th><span>Status</span></th>
									<th><span>Action</span></th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty scheduleList}">
									<c:forEach var="schedule" items="${scheduleList}" varStatus="schdcounter">
										<c:if test="${not empty schedule.activSschedules}">
											<c:forEach var="activeSchedule" items="${schedule.activSschedules}" varStatus="actvcounter">
												<tr>
													<%-- <td style="padding: 4px 9px;"><span><input type="checkbox" class="chk" actvSchIds="${activeSchedule.activeScheduleId}"></span></td> --%>
													<td><%-- <span>${schdcounter.count }</span> --%></td>
													<td><span id="schId_${activeSchedule.activeScheduleId}">${activeSchedule.activeScheduleId}</span></td>
													<td><span>${schedule.scheduleName}</span></td>
													<td>
														<span>${schedule.presentation.presentationName}</span>
													</td>
													<td><span>${activeSchedule.display.displayName}</span></td>
													<%-- <td><span>${activeSchedule.display.macId}</span></td> --%>
													<%-- <td><span>${activeSchedule.frequency}</span></td> --%>
													<td><span>${activeSchedule.scheduleDuration}</span> Sec</td>
													<td><span id="actvStatus_${activeSchedule.activeScheduleId}">${activeSchedule.actionFlag}</span></td>
													<td>
														<span class="actionIcon delete deleteSchedule" title="Delete" actvSchIds="${activeSchedule.activeScheduleId}">
															<i class="fa fa-times"aria-hidden="true"></i>
														</span>		
														<%-- <c:if test="${activeSchedule.actionFlag != 'D'}">
															<span class="actionIcon delete deleteSchedule" title="Delete" actvSchIds="${activeSchedule.activeScheduleId}">
																<i class="fa fa-times"aria-hidden="true"></i>
															</span>			
														</c:if> --%>
																									
													</td>
												</tr>
											</c:forEach>
										</c:if>
									</c:forEach>
								</c:if>
	
							</tbody>
						</table>
					</div>
				</form>
				
			
			</div>
		</div>
		</div>
		<!-- /.row -->
		<!-- Modal Section Start-->

		<div class="modal fade" id="schedule_publish_modal" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="myModalLabel">Publish Schedule</h4>
					</div>
					<div class="modal-body">
	
	
						<form:form action="/PiVisionWeb/schedule/addDisplayToSchedule"
							method="post" name="pubSchedulePopupForm"
							cssClass="form-horizontal" role="form"
							modelAttribute="formBean">
							<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
							<form:hidden path="scheduleId" id="publishScheduleId" />
							<div class="form-group">
	
								<label for="" class="col-sm-3 control-label">
									Schedule Name </label>
								<div class="col-sm-5">
									<span id="publish_Schedule_Name"></span>
								</div>
							</div>
							<div class="form-group">
	
								<label for="" class="col-sm-3 control-label"> Select
									Display </label>
								<div class="col-sm-5">
									<c:if test="${not empty displayList}">
									
										<%-- <select name="macId" multiple="multiple"  class="form-control" required="required">
											<c:forEach var="display" items="${displayList}" varStatus="disCntr">
											<option value="${display.macId }">${display.displayName }</option>
											</c:forEach>
										</select> --%>
										
										
										
										<div class="dropdown customDD">
										  <input class="dropdown-toggle form-control" type="text" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" placeholder="Select ...">
										  <ul class="dropdown-menu keep-open-on-click" aria-labelledby="dropdownMenu1" id="allDisplay">
										    <c:forEach var="display" items="${displayList}" varStatus="disCntr">
												<li class="dropdown-header"><span><input type="checkbox" name="macId" value="${display.macId }">  ${display.displayName }</span></li>
												<%-- <li><a href="#"><i class="fa fa-check-square-o" aria-hidden="true"></i>  ${display.displayName }</a></li> --%>
												
												<li role="separator" class="divider"></li>
											</c:forEach>
										  </ul>
										</div>
										
									</c:if>
								</div>
							</div>
							
							<div class="form-group">
								
								<label for="" class="col-sm-3 control-label">
									Duration </label>
								<div class="col-sm-2">
									<input type="number" name="duration" class="form-control" min="2" max="30" value="${formBean.duration }" placeholder="in Sec" autocomplete="off">
								</div>
								
								<label for="" class="col-sm-3 control-label">
									Priority </label>
								<div class="col-sm-2">
									<input type="number" min="1" max="5" name="priority" class="form-control" value="${formBean.priority }" autocomplete="off">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-5">
									<!-- <input type="button" value="Publish" name="go" class="pub_submit"> -->
	
									<button type="submit" class="btn btn-info"><i class="fa fa-floppy-o" aria-hidden="true"></i> Save</button>
									<button type="button" class="btn btn-danger"
										data-dismiss="modal">Close</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
	
			</div>
		</div>



		<div class="modal fade" id="schedule_modal" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="editModalLabel">Create Schedule</h4>
					</div>
					<div class="modal-body">
	
						<form:form action="" method="post" name="formBean"
							cssClass="form-horizontal" role="form" modelAttribute="formBean">
							<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
							<input type="hidden" name="scheduleId" id="modifyScheduleId" value="">
	
							<div class="form-group">
								<label for="" class="col-sm-3 control-label">
									Schedule Name </label>
								<div class="col-sm-7">
									<input type="text" name="scheduleName" class="caps form-control" value="${formBean.scheduleName}" 
									required="required" autocomplete="off" autofocus="autofocus"/>
								</div>
							</div>
							
							<div class="form-group">
	
								<label for="" class="col-sm-3 control-label">
									Start Date </label>
								<div class="col-sm-3">
									<input type="text" name="fromDate" class="form-control" value="${formBean.fromDate}" 
									autocomplete="off" required="required">
								</div>
								
								<label for="" class="col-sm-1 control-label">
									End Date </label>
								<div class="col-sm-3">
									<input type="text" name="toDate" class="form-control"  value="${formBean.toDate}" 
									autocomplete="off" required="required">
								</div>
							</div>
							
							<div class="form-group">
	
								<label for="" class="col-sm-3 control-label">
									Schedule Hours </label>
								<div class="col-sm-5">
									<select name="timeOfDay" class="form-control" required="required">
										<option ${formBean.timeOfDay == "MORNING"? "selected":"" } value="MORNING">Morning Hours(9-12)</option>
										<option ${formBean.timeOfDay == "NOON"? "selected":"" } value="NOON">Noon Hours(12-15)</option>
										<option ${formBean.timeOfDay == "AFTERNOON"? "selected":"" } value="AFTERNOON">Afternoon Hours(15-18)</option>
										<option ${formBean.timeOfDay == "EVENING"? "selected":"" } value="EVENING">Evening Hours(18-22)</option>
									</select>
								</div>
								<!-- <div class="col-sm-2">
	
									<div class="checkbox">
										<label> <input type="checkbox" /> Freq
										</label>
									</div>
								</div>
								<div class="col-sm-2">
									<div class="checkbox">
										<label> <input type="checkbox" /> Duration
										</label>
									</div>
								</div> -->
							</div>
							
							
							<div class="form-group">
	
								<label for="" class="col-sm-3 control-label"> Select
									Presentation </label>
								<div class="col-sm-5">
									<form:select  id="" path="presentationId" name="presentationId" cssClass="form-control" required="required">
											<form:option value="">--select--</form:option>
											<form:options items="${presentationList}" itemLabel="presentationName" itemValue="presentationId" />
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="reset" class="btn btn-default"><i class="fa fa-refresh" aria-hidden="true"></i> Reset</button>
									<button type="submit" class="btn btn-success" formaction="/PiVisionWeb/schedule/saveScheduleDetails"><i class="fa fa-floppy-o" aria-hidden="true"></i> Save</button>
									<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
								</div>
							</div>
						</form:form>
					</div>
				</div>
	
			</div>
		</div>


	
		<!-- Image Lightbox - Start -->
		<div class="modal fade" id="lightBox" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body" style="padding: 0"></div>
				</div>
			</div>
		</div>
		<!-- Image Lightbox - End -->
	
	
	
	
	
	<!--      Footer
   <footer class="footer">
      <div class="container">
        <span class="text-muted">Place sticky footer content here.</span>
      </div>
    </footer> -->

	<!-- /.container -->

	<!-- jQuery -->

</body>
</html>