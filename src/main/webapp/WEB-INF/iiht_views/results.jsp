<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>eAssess</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="" />
<meta name="keywords" content="" />
<meta name="author" content="" />

<!-- Facebook and Twitter integration -->
<meta property="og:title" content="" />
<meta property="og:image" content="" />
<meta property="og:url" content="" />
<meta property="og:site_name" content="" />
<meta property="og:description" content="" />
<meta name="twitter:title" content="" />
<meta name="twitter:image" content="" />
<meta name="twitter:url" content="" />
<meta name="twitter:card" content="" />


<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700,800" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,700" rel="stylesheet">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Animate.css -->
<link rel="stylesheet" href="./resources/userprofile/css/animate.css">
<!-- Icomoon Icon Fonts-->
<link rel="stylesheet" href="./resources/userprofile/css/icomoon.css">
<!-- Bootstrap  -->
<link rel="stylesheet" href="./resources/userprofile/css/bootstrap.css">

<!-- Magnific Popup -->
<link rel="stylesheet" href="./resources/userprofile/css/magnific-popup.css">

<!-- Flexslider  -->
<link rel="stylesheet" href="./resources/userprofile/css/flexslider.css">

<!-- Owl Carousel -->
<link rel="stylesheet" href="./resources/userprofile/css/owl.carousel.min.css">
<link rel="stylesheet" href="./resources/userprofile/css/owl.theme.default.min.css">

<!-- Flaticons  -->
<link rel="stylesheet" href="./resources/userprofile/fonts/flaticon/font/flaticon.css">

<!-- Theme style  -->
<link rel="stylesheet" href="./resources/userprofile/css/style.css">

<!-- Modernizr JS -->
<script src="./resources/userprofile/js/modernizr-2.6.2.min.js"></script>


<link href="css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
<!-- jQuery -->
<script src="./resources/userprofile/js/jquery.min.js"></script>
<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>

<!-- jQuery Easing -->
<script src="./resources/userprofile/js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="./resources/userprofile/js/bootstrap.min.js"></script>
<!-- Waypoints -->
<script src="./resources/userprofile/js/jquery.waypoints.min.js"></script>
<!-- Stellar Parallax -->
<script src="./resources/userprofile/js/jquery.stellar.min.js"></script>
<!-- Flexslider -->
<script src="./resources/userprofile/js/jquery.flexslider-min.js"></script>
<!-- Owl carousel -->
<script src="./resources/userprofile/js/owl.carousel.min.js"></script>
<!-- Magnific Popup -->
<script src="./resources/userprofile/js/jquery.magnific-popup.min.js"></script>
<script src="./resources/userprofile/js/magnific-popup-options.js"></script>
<!-- Counters -->
<script src="./resources/userprofile/js/jquery.countTo.js"></script>
<!-- Main -->
<script src="./resources/userprofile/js/main.js"></script>

<style>
#classModal {
	
}

.modal-body {
	overflow-x: auto;
}

.js .animate-box {
	opacity: 100 !important;
}

.classes .classes-img {
	display: block;
	position: relative;
	height: 100px;
	background-size: unset;
}
</style>

</head>
<body>



	<div id="page">
		<nav class="colorlib-nav" role="navigation">
			<div class="upper-menu">
				<div class="container">
					<div class="row">
						<div class="col-xs-4">
							<p>
								<b>Welcome to eAssess</b>
							</p>
						</div>

					</div>
				</div>
			</div>
			<div class="top-menu">
				<div class="container">
					<div class="row">
						<div class="col-md-2">
							<div id="colorlib-logo">
								<a href="index.html">eAssess</a>
							</div>
						</div>
						<div class="col-md-10 text-right menu-1">
							<ul>
								<li class="active"><a href="showLearnerAdminDashboard">Home</a></li>
								
								<li><a href="lmsModules">Manage Modules</a></li>
								
								<li><a href="lmsQuestion_list">Question Bank</a></li>
								
								<li><a href="lmsTests">Test Creation</a></li>
								
								<li><a href="showAllResults">Results - Learners</a></li>
								<li><a href="showManualReviewResults">Review Tests</a></li>
								
								<li><a href="signoff">Log Off</a></li>

							</ul>
						</div>
						<div class="col-md-12">
							<div class="col-md-12">
								<div class="pagination" style="float: right;" id="pagination">

									<c:if test="${showPreviousPage}">
										<a href="${callingMethod}?page=${previousPage}${queryParam}"><i class="fa fa-arrow-left"></i></a>
									</c:if>

									<c:if test="${selectedPage != null &&  selectedPage > 0}">
										                                    ${selectedPage} / ${totalNumberOfPages}
										                                </c:if>

									<c:if test="${showNextPage}">
										<a href="${callingMethod}?page=${nextPage}${queryParam}"><i class="fa fa-arrow-right"></i></a>
									</c:if>

								</div>
							</div>
						</div>


					</div>
				</div>
			</div>
		</nav>
		<div class="container">
			<div class="row">
				<table id="classTable" class="table table-bordered">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">First Name</th>
							<th scope="col">Last name</th>
							<th scope="col">Institute</th>
							<th scope="col">Grade</th>
							<th scope="col">Classifier</th>
							<th scope="col">Show All Test</th>
							<th scope="col">See Profile</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listUser}" var="user" varStatus="loop">
							<c:choose>
								<c:when test="${loop.count%2==0}">
									<tr class="success">
								</c:when>
								<c:otherwise>
									<tr class="info">
								</c:otherwise>
							</c:choose>
							<td>${loop.count}</td>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td>${user.collegeName}</td>
							<td>${user.grade}</td>
							<td>${user.classifier}</td>
							<td><a href="allTestResults?email=${user.user}">Show All Test</a></td>
							<td><a href="user_profile_student_profile?email=${user.user}">See Profile </a></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>


			</div>
		</div>






		<!-- modal -->

		<!-- end modal -->

		<!-- Module Items info Popup Modal to display-->
		<!-- Modules info Popup Modal to display-->

		<!--End Module popup -->



		<div class="gototop js-top">
			<a href="#" class="js-gotop"><i class="icon-arrow-up2"></i></a>
		</div>

		<script>
			function notify(messageType, message) {
				var notification = 'Information';
				$(function() {
					new PNotify({
						title : notification,
						text : message,
						type : messageType,
						styling : 'bootstrap3',
						hide : true
					});
				});
			}
		</script>

		<c:if test="${msgtype != null}">
			<script>
				var notification = 'Information';
				$(function() {
					new PNotify({
						title : notification,
						text : '${message}',
						type : '${msgtype}',
						styling : 'bootstrap3',
						hide : true
					});
				});
			</script>
		</c:if>
</body>
</html>

