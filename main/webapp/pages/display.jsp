
<%@page import="com.pivision.util.Config"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="headerInfo.jsp"></jsp:include>
<title>Display Page</title>
<script type="text/javascript">
$(document).ready(function(){
			
		$("#display").addClass("active");
		
		if("${MSG}"!="")
			alertX("${MSG}");
		
		
			 $('#macId').bind("cut copy",function(e) {
				e.preventDefault();
			});
			 $('#macId').mask("AA-AA-AA-AA-AA-AA", {placeholder: "__-__-__-__-__-__"});
			 $("[name='displayNamePart2']").mask("AAA", {placeholder: "___"});

			 $("#dataTable1").on("click",".delete",function() {
				var macId = $(this).attr("macId");
				$("#selectedMacIds").val(macId);
				$("[name=displayForm]").submit();
			});
			 
			 $("#dataTable1").on("click",".edit",function() {
				 var macId = $(this).attr("macId");
				 $('#editDisplayModal #dispName').text($("#dispName_"+macId).text());
				 $('#editDisplayModal [name=macId]').val(macId);
				 $('#editDisplayModal [name=duration]').val($("#dispDuration_"+macId).text());
				 $('#editDisplayModal [name=frequency]').val($("#dispFreq_"+macId).text());
				 
				 $('#editDisplayModal').modal({
					  keyboard: false
				})
			});
			 
			$("#dataTable1").on("click",".saveDisplayDtl",function() {
				 
				var macId = $(this).attr("macId");
				$("#selectedMacIds").val(macId);
				$("[name=displayForm]").attr("action","/PiVisionWeb/display/editDisplay");
				$("[name=displayForm]").submit(); 
			});
			 
			 
			
			$("#delAll").click(function(){
				if($(".chk:checked").length > 0){
					var macIds = [];
					$(".chk:checked").each(function(){
						//if($(this).is(":checked")){
							macIds.push($(this).attr("macId"));
						//}
					})
					var r = confirm("Do you want to delete selected Records ?");
					if(r){
						$("#selectedMacIds").val(macIds.toString());
						$("[name=displayForm]").submit();
					}
				}else{
					alertX("Please select some Records first.");
				}
					
			});
			
			$("#macId").blur(function(){
				var mac = $.trim($(this).val());
				if(mac.length == 17){
				$("#loader").show();
				$("#loader").html('<i class="fa fa-spinner fa-pulse blue"></i>');
				 $.ajax({
					  method: "GET",
					  url: "/PiVisionWeb/display/chkMacAvail",
					  data: { 
						  macId: mac
						  }
					}).done(function( msg ) {
						if(msg=="P"){
							$("#loader").html('<i class="fa fa-times red" aria-hidden="true"></i>');
						}else{
							$("#loader").html('<i class="fa fa-check green" aria-hidden="true"></i>');
						}
					});
				}
			});
			
			
	/* function getCode(stateName){
		var v = jQuery.grep(statecode, function( s ) {
			  return s.state === stateName;
			});
		return v[0].code;
	} */
	//Populate the LocationType dropdown
	
	$.getJSON( "./../pages/data.json", function( data ) {
		  $.each( data.locationTypes, function() {
			  $("[name='displayLocationType']").append("<option value='"+this.locationTypeDesc+"'>"
					  + this.locationTypeDesc +"</option>");
		  });
	});
	
	var api_key = '<%=Config.getKey("pincode-api-key")%>';
	$("[name=displayPin]").blur(function(){
		
		//$("#pinloader").show();
		//$("#pinloader").html('<i class="fa fa-spinner fa-pulse blue"></i>');
		
		var pin = $(this).val();
		var url = "https://data.gov.in/api/datastore/resource.json?"+
		  "resource_id=6176ee09-3d56-4a3b-8115-21841576b2f6&api-key="+api_key+
		  "&filters[pincode]="+pin+"&fields=pincode,id,officename,Taluk,Districtname,statename&limit=1";
		if(pin != ""){
		  if(pin.length==6){
			$.getJSON( url , function(data) {
			//Reset All
				//$("[name='landmark'] option").remove();
				populateDtl(null);
				if(data.count==0){
					alertX("Invali Pin");
					//populateDtl(null);
				}else if(data.count>0){
					 populateDtl(data.records[0]);
					 $("[name='displayLocation']").focus();
				}
			})
			}else{
				alertX("Invalid PIN");
				populateDtl(null);
			}
		}
	})		
	
	/* $("#pincode-dropdown").change(function(){
		var pinDtl = JSON.parse($(this).attr("dtlVal"));
		populateDtl(pinDtl);
	}); */
	
	function populateDtl(pinData){
		if(pinData == null){
			$("[name='displayName']").val("");
			//$("[name='displayLocation']").val("");
			$("[name='displayCity']").val("");
			$("[name='displayDistrict']").val("");
			$("[name='displayState']").val("");
		}else{
			//$("[name='displayLocation']").val(pinData.officename);
			$("[name='displayCity']").val(pinData.Taluk);
			$("[name='displayDistrict']").val(pinData.Districtname);
			$("[name='displayState']").val(pinData.statename);	
			
		}
	}
	
	
	 	$(".nameLogic").bind('change blur', function(){
		 
		 var mac = $("[name='macId']").val();
		 //alert(mac);
		 
		 $("[name='displayName']").val($("[name='displayCity']").val().substring(0,3).toUpperCase()
				 	+ $("[name='displayPin']").val().substring(3,6)+"-"
					+ $("[name='displayLocation']").val().replace(/\s/g, '').substring(0,8).toUpperCase()
					+ "-" + $("[name='displayLocationType']").val().toUpperCase()+"-"
					+ mac.substring(mac.length-4,mac.length).replace('-', '') );
		 	
	}); 
		
	 /* $("[name='displayNamePart2']").blur(function(){
		 $("[name='displayName']").val($("#displayNamePart1").text()+ $(this).val());
	 }); */

});
			
</script>
<style type="text/css">
.status {
	font-size: 16px;
	padding-left: 15px
}

.form-group {
     margin-bottom: 0px;
}
.blue{
	color: blue;
}
.red{
	color: red;
}

.green{
color: green;
}

</style>
</head>
<body>
		
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:useBean id="now" class="java.util.Date" scope="page"/>
    <!-- Page Content -->
    <div class="container">

        <!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h3 class="page-header">Manage Display
                    <small></small>
                </h3>
               <!--  <ol class="breadcrumb">
                    <li><a href="/PiVisionWeb/user/accountDetails">Home</a>
                    </li>
                    <li class="active">Display</li>
                </ol> -->
            </div>
        </div>
        <!-- /.row -->

        <!-- Content Row -->
        <div class="row">
            <!-- Sidebar Column -->
			<div class="col-md-3">
				<div class="list-group">
					<a href="#"
						class="list-group-item" id="menuList_1">Displays List</a> <a
						href="#"
						class="list-group-item" id="menuList_2">Add Display</a> 
				</div>
			</div>
			<!-- Content Column -->
			<div class="col-md-9 menuListDetailsClass" id="menuList_Details_1">
				<form action="/PiVisionWeb/display/removeDisplay" method="post" name="displayForm">
					<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
					<input type="hidden" name="selectedMacIds" id="selectedMacIds">
					<div class="table-responsive">
						<table class="table table-bordered table-condensed table-hover compact" id="dataTable1">
							<thead>
								<tr>
									<th><span class="chkboxclass"><input type="checkbox" id="chkAll" > </span></th>
									<th><span>Display Name</span></th>
									<th><span>MAC Id</span></th>
									<!-- <th><span>Resolution</span></th> -->
									<th><span>Status</span></th>
									<th><span>Frequency</span></th>
									<th><span>Duration</span></th>
									<th><span>Last Pinged</span></th>
									<th>
										<div class="btn-group" role="group">
											<span class="dropdown-toggle" data-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false"> Action <span
												class="caret"></span>
											</span>
											<ul class="dropdown-menu dropdown-menu-right">
												<li><a href="#" id="delAll"><i class="fa fa-trash-o stop" aria-hidden="true"></i> Delete</a></li>
											</ul>
										</div>
		
		
									</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${not empty displayList}">
									<c:forEach var="display" items="${displayList}" varStatus="item">
										<tr>
											<%-- <td><span>${item.index+1 }</span></td> --%>
											<td><span class="chkboxclass"><input type="checkbox" class="chk" macId= "${display.macId}"> </span></td>
											<td><span id="dispName_${display.macId}">${display.displayName}</span></td>
											<td><span>${display.macId}</span></td>
											<%-- <td><span>${display.resolution}</span></td> --%>
											<td>
												<c:set value="${(now.time - display.lastPinged.time)/(1000*60)}" var="dateDiff"/>
												<c:if test="${dateDiff lt 60}">
													<c:if test="${display.imgRefreshSts == 'Y'}">	
														<span class="status" style="color: green;" title="Online"><i class="fa fa-heartbeat" aria-hidden="true"></i></span>
													</c:if>
													<c:if test="${display.imgRefreshSts != 'Y'}">	
														<span class="status" style="color: blue;" title="Display Not working"><i class="fa fa-heartbeat" aria-hidden="true"></i></span>
													</c:if>
												</c:if>
												<c:if test="${dateDiff ge 60}">
													<span class="status" style="color: red;" title="Offline"><i class="fa fa-heartbeat" aria-hidden="true"></i></span>
												</c:if>
											</td>
											<td><span id="dispFreq_${display.macId}">${display.frequency}</span> Times</td>
											<td><span id="dispDuration_${display.macId}">${display.duration}</span> Min</td>
											<td><span>${display.lastPinged}</span></td>
											<td>
												<span macId="${display.macId}" class="actionIcon delete"><i class="fa fa-times" aria-hidden="true"></i></span>
												<span macId="${display.macId}" class="actionIcon edit"><i class="fa fa-pencil" aria-hidden="true"></i></span>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</form>
			</div>

			<div class="col-md-9 menuListDetailsClass" id="menuList_Details_2">

				<div class="row">
					<div class="col-md-8">
						<form class="form-horizontal" role="form"
							action="/PiVisionWeb/display/addDisplay" method="post">
							<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
							<div class="form-group">

								<label for="inputEmail3" class="col-sm-3 control-label">
									Display Name </label>
								   <div class="col-sm-6">
									  <input type="text" name="displayName" class="form-control" required="required">
									  <br>
								</div>
							</div>

							<%-- <div class="form-group">

								<label for="inputEmail3" class="col-sm-3 control-label">
									Display Key </label>
								<div class="col-sm-6">
									<input type="text" name="displayKey" class="caps form-control" required="required"
										value="${displayForm.displayKey}" maxlength="6"> <br>
								</div>
							</div> --%>
							
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									MAC Id </label>
								<div class="col-sm-6">
									<input type="text" name="macId" id="macId"
										value="${displayForm.macId}" maxlength="17" required="required" pattern=".{17}" title="Invalid MAC"
										class="caps form-control nameLogic"><br/> 
								</div>
								<div class="col-sm-3" style="padding-left: 0">
									<span style="font-size: 20px;display: none;" id="loader" >
									</span>
								<br/>
								</div>
							</div>
							
							<div class="form-group">

								<label for="inputEmail3" class="col-sm-3 control-label">
									PIN </label>
								<div class="col-sm-3">
									<input type="number" name="displayPin" class="caps form-control nameLogic" required="required"  min="100000" max="999999"
										value="${displayForm.displayPin}" maxlength="6"> 
										<br>
								</div>
								<label for="inputEmail3" class="col-sm-2 control-label">
									Installation Location </label>
								<div class="col-sm-4">
									<input type="text" name="displayLocation" class="caps form-control nameLogic" placeholder="Location" 
									maxlength="15" required="required"
										value="">
									 <br>
								</div>
							</div>
							
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									Location Type </label>
								<div class="col-sm-6">
									<select class="form-control nameLogic" name="displayLocationType">
									</select><br/> 
								</div>
							</div>
							
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									Contact Name </label>
								<div class="col-sm-6">
									<input class="form-control" maxlength="40" name="partyName" required="required">
									<br/> 
								</div>
							</div>
							
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									Address </label>
								<div class="col-sm-6">
								<textarea rows="2" cols="20" class="form-control" maxlength="100" name="displayAddress"></textarea>
									<br/> 
								</div>
							</div>
							
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
								</label>
								<div class="col-sm-3">
									<label for="exampleInputEmail1">
										City
									</label>
									<input type="text" name="displayCity" class="caps form-control nameLogic" placeholder="City" required="required"> 
									<br>
								</div>
								<div class="col-sm-3">
									<label for="exampleInputEmail1">
										District
									</label>
									<input type="text" name="displayDistrict" class="caps form-control" placeholder="District" required="required"> 
									<br>
								</div>
								
								<div class="col-sm-3">
									<label for="exampleInputEmail1">
										State
									</label>
									<input type="text" name="displayState" class="caps form-control" placeholder="State" required="required">
									<input type="hidden" name="displayStateCode">
									<br>
								</div>
								
							</div>
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
								Configuration
								</label>
								<div class="col-sm-4">
									<label> Frequency </label>
									<div class="input-group">
									  <input type="number" name="frequency" min="2" max="12" class="form-control" required="required">
									  <span class="input-group-addon">times</span>
									</div>
									<br/>
								</div>
								<div class="col-sm-4">
									<label> Duration </label>
									<div class="input-group">
									  <input type="number" name="duration" min="2" max="30" class="form-control" required="required">
									  <span class="input-group-addon">(in Min)</span>
									</div>
									<br/>
								</div>
							</div>
							
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-3 control-label">
									Phone </label>
								<div class="col-sm-6">
									<div class="input-group">
									  <span class="input-group-addon">+91</span>
									  <input type="number" class="form-control" max="9999999999" name="partyPhone" required="required">
									</div>
									<br/> 
								</div>
							</div>
							
							<%-- <div class="form-group">

								<label for="inputEmail3" class="col-sm-3 control-label">
									Resolution </label>
								<div class="col-sm-6">
									<select name="resolution" class="form-control" >
										<option ${displayForm.resolution =="600*800"?"selected":""} value="600*800">600*800</option>
										<option ${displayForm.resolution =="8000*1000"?"selected":""} value="8000*1000">8000*1000</option>
										<option ${displayForm.resolution =="1000*1200"?"selected":""} value="1000*1200">1000*1200</option>
										<option ${displayForm.resolution =="1400*1600"?"selected":""} value="1400*1600">1400*1600</option>
									</select><br>
									<form:errors path="displaybean.resolution"
										cssClass="error errorBorder"></form:errors>


								</div>
							</div> --%>
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-9">

									<button type="submit" class="btn btn-success"><i class="fa fa-floppy-o" aria-hidden="true"></i> Save</button>
								</div>
							</div>
						</form>

					</div>
				</div>


			</div>

		</div>
			<!-- /.row -->


	<!-- Modal Section Start-->


	<div class="modal fade" id="editDisplayModal" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="dispName"></h4>
					</div>
					<div class="modal-body">
	
						<form action="/PiVisionWeb/display/editDisplay" method="post" 
							class="form-horizontal" role="form" >
							<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
							<input type="hidden" name="macId" >
						<div class="form-group">

							<div class="col-sm-6">
								<label> Frequency </label>
								<div class="input-group">
									<input type="number" name="frequency" min="2" max="30"
										class="form-control" required="required"> 
								</div>
								<br />
							</div>
							<div class="col-sm-6">
								<label> Duration </label>
								<div class="input-group">
									<input type="number" name="duration" min="2" max="30"
										class="form-control" required="required"> 
								</div>
								<br />
							</div>
						</div>
						<div class="form-group">

							<div class="col-sm-6">
								<label> Sotware Update </label>
								<div class="input-group">
									<input type="checkbox" name="softwareUpdate" class="form-control" > 
								</div>
								<br />
							</div>
							<div class="col-sm-6">
								
								<br />
							</div>
						</div>
	
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="submit" class="btn btn-success"><i class="fa fa-floppy-o" aria-hidden="true"></i> Save</button>
									<button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
								</div>
							</div>
						</form>
					</div>
				</div>
	
			</div>
		</div>


		<!--      Footer
   <footer class="footer">
      <div class="container">
        <span class="text-muted">Place sticky footer content here.</span>
      </div>
    </footer> -->

    </div>
    <!-- /.container -->

    <!-- jQuery -->

</body>
</html>