
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
<title>Account Page</title>

<script type="text/javascript">
	$(document).ready(function() {

		$("#account").addClass("active");

		if ("${MSG}" != "")
			alertX("${MSG}");

		// Showing no result message
		$('[id*=dataTable]').each(function() {
			var tblId = $(this).attr("id");
			var str = "";
			if ($("#" + tblId + " tbody tr").length == 0) {
				str = '<tr><td colspan="6">No Records Present</td></tr>';
				$("#" + tblId + " tbody").append(str);
			} else {
				$("#" + tblId).dataTable({
					"paging" : false
				});
			}
		});
		
		
		$('[type=password]').hover(function () {
		    $(this).attr('type', 'text');
		}, function () {
		    $(this).attr('type', 'password');
		});

	});
</script>
<style type="text/css">

.block{
padding: 10px;
margin-top: 20px;
border-bottom: 1px solid #DACBD8;
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
					Manage Display <small></small>
				</h3>
<!-- 				<ol class="breadcrumb">
					<li><a href="/PiVisionWeb/user/accountDetails">Home</a></li>
					<li class="active">Account</li>
				</ol> -->
			</div>
		</div>
		<!-- /.row -->

		<!-- Content Row -->
		<div class="row">
			<!-- Sidebar Column -->
			<div class="col-md-3">
				<div class="list-group">
					<a href="#" class="list-group-item" id="menuList_1">Account Details</a> 
					<a href="#" class="list-group-item" id="menuList_2">Edit Profile</a>
					<!-- <a href="#" class="list-group-item">Logout</a> -->
				</div>
			</div>
			<!-- Content Column -->
			<div class="col-md-9 menuListDetailsClass" id="menuList_Details_1">
				<%-- <div style="overflow: auto; height: 400px;">
					<form action="" method="post">
						<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">

						<div style="text-align: center;">
							<h1 style="color: #555;">Welcome to Pivision</h1>


						</div>

						<div style="text-align: center; padding: 20px;" class="homeMenu">
							<table align="center" style="border-spacing: 10px;">
								<tr>
									<td style="border: 0px solid rgb(24, 118, 232);"><a
										href="/PiVisionWeb/user/accountDetails"> <span
											class="flaticon-account menuLabel"></span><br> <span>Account</span>
									</a></td>
									<td style="border: 0px solid rgb(24, 118, 232);"><a
										href="/PiVisionWeb/display/showAllDisplay"> <span
											class="flaticon-display menuLabel"></span><br> <span>Display</span>
									</a></td>
								</tr>
								<tr>
									<td style="border: 0px solid rgb(24, 118, 232);"><a
										href="/PiVisionWeb/presentation/showAllPresentation"> <span
											class="flaticon-presentation menuLabel"></span><br> <span>Presentation</span>
									</a></td>
									<td style="border: 0px solid rgb(24, 118, 232);"><a
										href="/PiVisionWeb/schedule/showAllSchedule"> <span
											class="flaticon-schedule menuLabel"></span><br> <span>Schedule</span>
									</a></td>
								</tr>
							</table>


						</div>




					</form>
				</div>
 --%>
				<div class="col-md-12">
					<div class="page-header">
						<h1>
							Welcome User - ${userBean.userId} <small></small>
						</h1>
					</div>
					<c:if test="${userBean.role.roleText == 'ADMIN'}">
						<h4>
							To Create User. <a href="#register_win_modal" data-toggle="modal">Click Here</a>
						</h4>
					</c:if>

					<div class="alert alert-danger alert-dismissable">

						<h4>Login Details</h4>
						<strong></strong> You have accessed your account last on ${userBean.lastLogon } <a
							href="/PiVisionWeb/display/showAllDisplay" class="alert-link"></a>
					</div>
					<div class="alert alert-success alert-dismissable">

						<h4>Display Details</h4>
						<strong></strong> YOu have added 15 dispaly. To see details <a
							href="/PiVisionWeb/display/showAllDisplay" class="alert-link">Click here</a>
					</div>
					<div class="alert alert-dismissable alert-info">

						<h4>Presentation Details</h4>
						<strong></strong> You have 20 presentation. To see details <a
							href="/PiVisionWeb/presentation/showAllPresentation" class="alert-link">Click here</a>
					</div>
					<div class="alert alert-dismissable alert-warning">

						<h4>Schedule Details</h4>
						<strong></strong> You have 20 Schedule. To see details <a
							href="/PiVisionWeb/schedule/showAllSchedule" class="alert-link">Click here</a>
					</div>
				</div>
			</div>



			<div class="col-md-9 menuListDetailsClass" id="menuList_Details_2">

				<div class="row">
					<div class="col-md-12 block">
						<form class="form-horizontal" role="form"
							action="/PiVisionWeb/user/updatePassword" method="post">
							<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
							<div class="form-group">

								<label for="inputEmail3" class="col-sm-2 control-label">
									Current Password </label>
								<div class="col-sm-4">
									<input type="password" maxlength="20" name="password" autocomplete="off"
										class="form-control">
								</div>


								<label for="inputEmail3" class="col-sm-2 control-label">
									New Password </label>
								<div class="col-sm-4">
									<input type="password" maxlength="20" name="cnfrmPassword" autocomplete="off"
										class="form-control"><span></span>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-5">

									<button type="submit" class="btn btn-success"><i class="fa fa-floppy-o" aria-hidden="true"></i> Change
										password</button>
								</div>
							</div>
						</form>

					</div>
					<hr>

					<div class="col-md-12 block">
						<form class="form-horizontal" role="form"
							action="/PiVisionWeb/user/updateAddress" method="post">
							<input type="hidden" name="linkNo" id="linkNo" value="${LINK_NO}">
							<div class="form-group">

								<label for="inputEmail3" class="col-sm-2 control-label">
									First Name</label>
								<div class="col-sm-4">

									<input type="text" maxlength="20" name="firstName"
										value="${userBean.userAddressbooks.firstName}"
										class="form-control">
									<form:errors path="accountBean.firstName"
										cssClass="error errorBorder" />

								</div>

								<label for="inputEmail3" class="col-sm-2 control-label">
									Last Name </label>
								<div class="col-sm-4">
									<input type="text" maxlength="20" name="lastName"
										value="${userBean.userAddressbooks.lastName}"
										class="form-control">
									<form:errors path="accountBean.lastName"
										cssClass="error errorBorder" />
								</div>
							</div>

							<div class="form-group">

								<label for="inputEmail3" class="col-sm-2 control-label">
									Building</label>
								<div class="col-sm-4">

									<input type="text" maxlength="20" name="buildingName"
										value="${userBean.userAddressbooks.buildingName}"
										class="form-control">
									<form:errors path="accountBean.buildingName"
										cssClass="error errorBorder" />

								</div>

								<label for="inputEmail3" class="col-sm-2 control-label">
									City </label>
								<div class="col-sm-4">
									<input type="text" maxlength="20" name="city"
										value="${userBean.userAddressbooks.city}"
										class="form-control">
									<form:errors path="accountBean.city"
										cssClass="error errorBorder" />
								</div>
							</div>
							
							<div class="form-group">

								<label for="inputEmail3" class="col-sm-2 control-label">
									Street1</label>
								<div class="col-sm-4">

									<input type="text" maxlength="20" name="street1"
										value="${userBean.userAddressbooks.street1}"
										class="form-control">
									<form:errors path="accountBean.street1"
										cssClass="error errorBorder" />

								</div>

								<label for="inputEmail3" class="col-sm-2 control-label">
									Street2 </label>
								<div class="col-sm-4">
									<input type="text" maxlength="20" name="street2"
										value="${userBean.userAddressbooks.street2}"
										class="form-control">
									<form:errors path="accountBean.street2"
										cssClass="error errorBorder" />
								</div>
							</div>
							
							<div class="form-group">

								<label for="inputEmail3" class="col-sm-2 control-label">
									Pin code</label>
								<div class="col-sm-4">

									<input type="number" maxlength="6" name="pinCode"
										value="${userBean.userAddressbooks.pinCode}"
										class="form-control">
									<form:errors path="accountBean.pinCode"
										cssClass="error errorBorder" />

								</div>

								<label for="inputEmail3" class="col-sm-2 control-label">
									State </label>
								<div class="col-sm-4">
									<input type="text" maxlength="20" name="state"
										value="${userBean.userAddressbooks.state}"
										class="form-control">
									<form:errors path="accountBean.state"
										cssClass="error errorBorder" />
								</div>
							</div>
							
							<div class="form-group">

								<label for="inputEmail3" class="col-sm-2 control-label">
									Company</label>
								<div class="col-sm-4">

									<input type="text" maxlength="20" name="company"
										value="${userBean.userAddressbooks.company}"
										class="form-control">
									<form:errors path="accountBean.company"
										cssClass="error errorBorder" />

								</div>

								<label for="inputEmail3" class="col-sm-2 control-label">
									Country </label>
								<div class="col-sm-4">
									<input type="text" maxlength="20" name="country"
										value="${userBean.userAddressbooks.country}"
										class="form-control">
									<form:errors path="accountBean.country"
										cssClass="error errorBorder" />
								</div>
							</div>


							<div class="form-group">

								<label for="inputEmail3" class="col-sm-2 control-label">
									Phone </label>
								<div class="col-sm-4">

									<input type="number" maxlength="10" name="phone"  
										value="${userBean.userAddressbooks.phone}"
										class="form-control">
									<form:errors path="accountBean.phone"
										cssClass="error errorBorder" />

								</div>

							</div>

							
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-5">

									<button type="submit" class="btn btn-success"><i class="fa fa-floppy-o" aria-hidden="true"></i> Update</button>
								</div>
							</div>
						</form>

					</div>


				</div>


			</div>

		</div>
		<!-- /.row -->


		<!-- Modal Section Start-->

		<div class="modal fade" id="register_win_modal" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<form role="form" action="/PiVisionWeb/user/create" method="post">
					<div class="modal-content">
						<div class="modal-header">

							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">×</button>
							<h4 class="modal-title" id="myModalLabel">Create new user</h4>
						</div>
						<div class="modal-body">


							<div class="form-group">
								<input type="email" value="${loginBean.emailId}" name="emailId"
									class="form-control input-sm" placeholder="Email Address"
									autocomplete="off" required="required" maxlength="40">
							</div>
							<div class="form-group">
								<input type="password" name="password"
									class="form-control input-sm" placeholder="Password"
									autocomplete="off" required="required" maxlength="40">
							</div>
							<div class="form-group">
								<select name="roleId" class="form-control">
									<option value="1">ADMIN</option>
									<option value="2">USER</option>
								</select>
							</div>
						</div>
						<div class="modal-footer">
								<input type="submit" value="Create" class="btn btn-info">
							<!-- <button type="submit" class="btn btn-default"
								data-dismiss="modal">Create</button> -->
						</div>
					</div>
				</form>
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