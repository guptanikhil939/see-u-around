<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>See U Around</title>

<!-- Bootstrap Core CSS - Uses Bootswatch Flatly Theme: http://bootswatch.com/flatly/ -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="resources/css/freelancer.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="resources/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="http://fonts.googleapis.com/css?family=Montserrat:400,700"
	rel="stylesheet" type="text/css">
<link
	href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
	rel="stylesheet" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<style>
.googlePlay {
	background: black url("resources/img/playstore.png") no-repeat scroll
		center center/cover;
	border-radius: 10px;
	display: inline-block;
	height: 75px;
	width: 220px;
}
</style>
</head>

<body id="page-top" class="index">

	<!-- Navigation -->
	<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header page-scroll">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#page-top" id="brand">See U Around</a>
		</div>

		<div class="navbar-header page-scroll">
			<div>
				<p class="text-success" id="successMessage"></p>
			</div>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div>
			<ul id="bs-example-navbar-collapse-1"
				class="navbar-collapse nav navbar-nav navbar-right">
				<li class="hidden"><a href="#page-top"></a></li>
				<li class="page-scroll"><a href="#whatOption">WHAT IS SUA?</a></li>
				<li class="page-scroll"><a href="#ourUsersOption">OUR USERS</a></li>
				<li class="page-scroll"><a href="#ourTeamOption">OUR TEAM</a></li>
				<li class="page-scroll"><a href="#contactUsOption">CONTACT
						US</a></li>
				<li class="page-scroll"><a href="#faq">FAQ</a></li>
			</ul>
		</div>

		<div></div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>

	<!-- Header -->
	<header>
	<div class="container">
		<div class="row pull-right">
			<div class="col-lg-12">
				<a
					href="https://play.google.com/store/apps/details?id=com.seeuaround"><li
					class="googlePlay"></li></a>
			</div>
		</div>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
		<div>&nbsp;</div>
		<div class="col-lg-12">
			<div class="intro-text">
				<span class="name">See U Around</span> <span class="skills">Discover
					- Friends.Connections.Contacts.Around</span><br>
			</div>
		</div>
		<div class="col-lg-12">&nbsp;</div>
		<div class="col-lg-12">
			<button class="btn btn-success btn-default" id="">Watch
				Video</button>
		</div>
		<div>&nbsp;</div>
	</div>
	</header>
	<!-- Login Section -->
	<jsp:include page="what.jsp" />
	<!-- Contact Us Section -->
	<jsp:include page="ourUsers.jsp" />
	<!-- Contact Us Section -->
	<jsp:include page="ourTeam.jsp" />
	<!-- Contact Us Section -->
	<jsp:include page="contactUs.jsp" />
	<!-- Footer -->
	<jsp:include page="footer.jsp" />
	<!-- Scroll to Top Button (Only visible on small and extra-small screen sizes) -->
	<div class="scroll-top page-scroll visible-xs visible-sm">
		<a class="btn btn-primary" href="#page-top"> <i
			class="fa fa-chevron-up"></i>
		</a>
	</div>

	<!-- jQuery -->
	<script src="resources/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="resources/js/bootstrap.min.js"></script>

	<!-- Plugin JavaScript -->
	<script src="resources/js/jquery.easing.min.js"></script>
	<script src="resources/js/classie.js"></script>
	<script src="resources/js/cbpAnimatedHeader.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="resources/js/freelancer.js"></script>
</body>
</html>