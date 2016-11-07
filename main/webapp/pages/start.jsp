<!DOCTYPE html>
<html lang="en">

<head>

<title>Welcome to pivision</title>
<jsp:include page="headerInfo.jsp"></jsp:include>


<link href="./../css/i2.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->



<style type="text/css">
.modal-dialog {
	margin-top: 150px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		if ("${MSG}" != "") {
			bootbox.alert("${MSG}");
		}
	});
</script>
</head>

<body>

	<!-- Navigation -->
	<nav
		class="navbar navbar-default navbar-custom navbar-custom-dark bg-dark">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<!-- <button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button> -->
				<a href="/PiVisionWeb/user/accountDetails"
					style="text-decoration: none; font-variant: small-caps; color: white; font-size: 30px;">
					<span class="flaticon-home"></span>Pi Vision
				</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<!-- <ul class="nav navbar-nav navbar-right">
					<li><a href="#home" class="scroll">Home</a></li>
					<li><a href="#register_win_modal" class="scroll" role="button"
						data-toggle="modal">Sign Up</a></li>
				</ul> -->
			</div>
			<!-- /.navbar-collapse -->

		</div>
		<!-- /.container-fluid -->
	</nav>

	<section class="section home home-register bg-dark" id="home">
		<div class="container">
			<div class="row">
				<div class="col-sm-6 text-left">
					<div class="home-wrapper">

						<div class="text-tran-box text-tran-box-dark">
							<h1 class="text-transparent">Here is the best way to
								Advertise</h1>
						</div>
						<div class="clearfix"></div>

						<!-- <a href="http://vimeo.com/99025203"
							class="video-btn btn popup-video"><i class="pe-7s-play"></i>Watch
							Video</a> -->

					</div>
				</div>

				<div class="col-sm-4 col-sm-offset-2">
					<div class="home-wrapper">
						<form role="form" class="intro-form"
							action="/PiVisionWeb/user/verify" method="post">
							<h3 class="text-center">Log In Window</h3>
							<div class="form-group">
								<input type="text" class="form-control"
									placeholder="Email Address" name="emailId"
									value="${loginBean1.emailId}" required="required">
							</div>
							<div class="form-group">
								<input type="password" class="form-control" placeholder="Password"
									name="password" value="${loginBean1.password }"
									required="required">
							</div>
							<div class="form-group text-center">
								<button class="signIn btn btn-custom btn-sm">Log In</button>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</section>


	

	<div class="container">

		<hr>

		<!-- Footer -->
		<footer>
			<div class="row">
				<div class="col-lg-12">
					<p>Copyright &copy; <a class="_Gs" href="//www.tinyad.in" target="_blank ">tinyAD</a></p>
				</div>
			</div>
		</footer>

	</div>

</body>

</html>
