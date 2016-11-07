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
<title>Presentation Page</title>

	<script type="text/javascript">
	$(document).ready(function() {

		$("#presentation").addClass("active");

		if ("${MSG}" != "")
			alertX("${MSG}");

		

		/* $('[id*=dataTable]').each(function() {
			var tblId = $(this).attr("id");
			var str = "";
			if ($("#" + tblId + " tbody tr").length == 0) {
				str = '<tr><td colspan="6">No Records Present</td></tr>';
				$("#" + tblId + " tbody").append(str);
			} else {
				 var t = $("#" + tblId).DataTable( {
				        "columnDefs": [ {
				            "searchable": false,
				            "orderable": false,
				            "targets": [0,5] // colmn number to be excluded
				        } ],
				        "order": [[ 1, 'asc' ]]
				    } );
			}

		}); */
		
		$(".imgDtl").bind('blur change',function(){
			$('[name=presentationName]').val($('[name=location]').val()+"-"+$('[name=category]').val());
		})
		
		$("#dataTable1").on("click",".delete",function() {
			var pstnId = $(this).attr("pstnId");
			$("#presentationId").val(pstnId);
			$("[name=presentationForm]").submit();
		});
		
		$("#delAll").click(function(){
			if($(".chk:checked").length > 0){
				var pstnIds = [];
				$(".chk:checked").each(function(){
					//if($(this).is(":checked")){
						pstnIds.push($(this).attr("pstnId"));
					//}
				})
				var r = confirm("Do you want to delete selected Records ?");
				if(r){
					$("#presentationId").val(pstnIds.toString());
					$("[name=presentationForm]").submit();
				}
			}else{
				alertX("Please select some Records first.");
			}
		});

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
				var srcImg = "data:" + "image/"+ imgData.mimeType+ ";base64,"+ imgData.mediaString;
				
				
				$this.parent().html('<img id="showModal" alt="" width="50" height="25" src="'+srcImg+'">');
				$this.remove();
			});
		
		});
		$("#dataTable1").on("click","#showModal",function() {
			var srcImg = $(this).attr("src");
			$("#lightBox .modal-body").html('<img class="lightBoxImg" src="'+ srcImg +'">');
			$("#lightBox").modal({
			  keyboard: false
			})
				
		})		
		
		////////////END of script
	})
</script>
<style type="text/css">
	.form-group {
		margin-bottom: 0px;
	}
	
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
						Manage Presentations <small></small>
					</h3>
					<ol class="breadcrumb">
						<li><a href="/PiVisionWeb/user/accountDetails">Home</a></li>
						<li class="active">Presentations</li>
					</ol>
				</div>
			</div>
			<!-- /.row -->

			<!-- Content Row -->
			<div class="row">
				<!-- Sidebar Column -->
				<div class="col-md-3">
					<div class="list-group">
						<a href="#" class="list-group-item" id="menuList_1">List of
						Presentations</a> <a href="#" class="list-group-item" id="menuList_2">Upload Presentation</a>
					</div>
				</div>
				<!-- Content Column -->
				<div class="col-md-9 menuListDetailsClass table-responsive" id="menuList_Details_1">
					<form action="/PiVisionWeb/presentation/removePresentation" method="post" name="presentationForm">
						<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
						<input type="hidden" name="presentationId" id="presentationId">
						<div class="table-responsive">
							<table class="table table-bordered table-condensed table-hover compact" id="dataTable1" >
								<thead>
									<tr>
										<th><span class="chkboxclass"><input type="checkbox" id="chkAll" > </span></th>
										<th><span>Presentation Name</span></th>
										<th><span>Content Type</span></th>
										<th><span>Image</span></th>
										<th><span>Created On</span></th>
										<th>
	
											<div class="btn-group" role="group">
												<span class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> Action <span
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
									<c:if test="${not empty presentationList}">
										<c:forEach var="presentation" items="${presentationList}" varStatus="item">
											<tr>
												<%-- <td><span>${item.index+1 }</span></td> --%>
													<td><span class="chkboxclass"><input type="checkbox" class="chk" pstnId= "${presentation.presentationId}"> </span></td>
													<td><span>${presentation.presentationName}</span></td>
													<td><span>${presentation.contentType}</span></td>
													<td style="cursor: pointer;">
														<a class="getImg" imgName="${presentation.fileName}" imgType="${presentation.contentType}" style="font-size: 20px;">
															<i class="fa fa-picture-o" aria-hidden="true"></i>
														</a>
													</td>
													<td><span>${presentation.createdDate}</span></td>
													<td>
														<span class="actionIcon delete" pstnId="${presentation.presentationId}"><i class="fa fa-times" aria-hidden="true"></i></span>
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
							<form class="form-horizontal" role="form" action="/PiVisionWeb/presentation/uploadPresentation" method="post" enctype="multipart/form-data">
								<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">

								<div class="form-group">

									<label for="" class="col-sm-3 control-label">
									Upload Image </label>
									<div class="col-sm-8">
										<input type="file" name="imageFile" class="caps form-control" value="" required="required"> <br>
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail3" class="col-sm-3 control-label">
										Description </label>
									<div class="col-sm-4">
										<input type="text" name="location"  class="caps form-control imgDtl" placeholder="Location" required="required"
											maxlength="10"> <br>
									</div>
									<div class="col-sm-4">
										<input type="text" name="category" class="caps form-control imgDtl" placeholder="Category" required="required"
											maxlength="10"> <br>
									</div>
								</div>
								<div class="form-group">

									<label for="" class="col-sm-3 control-label">
									Presentation Name </label>
									<div class="col-sm-8">
										<input type="text" name="presentationName" class="caps form-control" required="required" 
										 value="${pageBean.presentationName}">
										<br>
										<form:errors path="pageBean.presentationName" cssClass="error errorBorder"></form:errors>
									</div>
								</div>

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
			<div class="modal fade" id="lightBox" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-body" style="padding: 0"></div>
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