<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Code Analysis Report</title>

<spring:url value="/resources/assets/img/ico/favicon.png" var="c1" />

<link href="${c1}" rel="shortcut icon" />

<spring:url value="https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900" var="c2" />

<link href="${c2}" rel="stylesheet" type="text/css" />

<!-- Material Icons CSS -->
<spring:url value="/resources/assets/fonts/iconfont/material-icons.css" var="c3" />

<link href="${c3}" rel="stylesheet" type="text/css" />

<!-- FontAwesome CSS -->
<spring:url value="/resources/assets/fonts/font-awesome/css/font-awesome.min.css" var="c4" />

<link href="${c4}" rel="stylesheet" type="text/css" />

<!-- magnific-popup -->
<spring:url value="/resources/assets/magnific-popup/magnific-popup.css" var="c5" />

<link href="${c5}" rel="stylesheet" type="text/css" />

<!-- owl.carousel -->
<spring:url value="/resources/assets/owl.carousel/assets/owl.carousel.css" var="c6" />

<link href="${c6}" rel="stylesheet" type="text/css" />

<spring:url value="/resources/assets/owl.carousel/assets/owl.theme.default.min.css" var="c7" />

<link href="${c7}" rel="stylesheet" type="text/css" />
<!-- flexslider -->
<spring:url value="/resources/assets/flexSlider/flexslider.css" var="c8" />

<link href="${c8}" rel="stylesheet" type="text/css" />

<!-- materialize -->
<spring:url value="/resources/assets/materialize/css/materialize.min.css" var="c9" />

<link href="${c9}" rel="stylesheet" type="text/css" />

<!-- Bootstrap -->
<spring:url value="/resources/assets/bootstrap/css/bootstrap.min.css" var="c10" />

<link href="${c10}" rel="stylesheet" type="text/css" />

<!-- shortcodes -->
<spring:url value="/resources/assets/css/shortcodes/shortcodes.css" var="c11" />

<link href="${c11}" rel="stylesheet" type="text/css" />

<!-- Style CSS -->
<spring:url value="/resources/assets/style.css" var="c12" />

<link href="${c12}" rel="stylesheet" type="text/css" />

<!-- RS5.0 Main Stylesheet -->
<spring:url value="/resources/assets/revolution/css/settings.css" var="c13" />

<link href="${c13}" rel="stylesheet" type="text/css" />

<!-- RS5.0 Layers and Navigation Styles -->
<spring:url value="/resources/assets/revolution/css/layers.css" var="c14" />

<link href="${c14}" rel="stylesheet" type="text/css" />
<spring:url value="/resources/assets/revolution/css/navigation.css" var="c15" />

<link href="${c15}" rel="stylesheet" type="text/css" />
<spring:url value="/resources/assets/css/pnotify.custom.min.css" var="c16" />

<link href="${c16}" rel="stylesheet" type="text/css" />
<style>
q {
	display: inline;
}

q:before {
	content: open-quote;
}

q:after {
	content: close-quote;
}
/* The Modal (background) */
.modal {
	display: none; /* Hidden by default */
	position: fixed; /* Stay in place */
	z-index: 1; /* Sit on top */
	left: 0;
	top: 0;
	width: 100%; /* Full width */
	height: 100%; /* Full height */
	overflow: auto; /* Enable scroll if needed */
	background-color: rgb(0, 0, 0); /* Fallback color */
	background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
}

/* Modal Content/Box */
.modal-content {
	background-color: #fefefe;
	margin: 15% auto; /* 15% from the top and centered */
	padding: 20px;
	border: 1px solid #888;
	width: 80%; /* Could be more or less, depending on screen size */
}

/* The Close Button */
.close {
	color: #aaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
}
</style>
</head>

<body id="top" class="has-header-search">

		<!--header start-->
		<header id="header" class="tt-nav nav-border-bottom">
				<div class="header-sticky light-header ">
						<div class="container">
								<div id="materialize-menu" class="menuzord">
										<!--logo start-->
										<a href="javascript:void(0);" class="logo-brand"> <img class="retina"
												src="<%=request.getContextPath()%>/resources/images/Logo.png" alt="" />
										</a>
										<!--logo end-->
										<!--mega menu start-->
										<ul class="menuzord-menu pull-right">
											<li><a
														href="javascript:notify('Information', 'We will release the feature pretty soon! Please wait for our next release');">Dashboard</a></li>
												<li><a href="question_list">Question Bank</a></li>
												<li><a href="testlist">Tests</a></li>
												<li><a href="skills">Skills</a></li>
												<li><a href="showReports">Results</a></li>
												<li class="active"><a href="codingSessions">Code Analysis Reports</a></li>
												<li><a href="showSkillTags">Skill based Reports</a></li>
												<li><a href="showProfileParams">Recomm Setting</a></li>
												<li><a href="listUsers">Users</a></li>
										</ul>
										<!--mega menu end-->
								</div>
						</div>
				</div>
		</header>
		<!--header end-->


		<section>
				<div class="container">
						<div class="row mt-5">


								<div class="col-md-12">
										<div class="mt-10"></div>
										<div class="col-md-5">
												<h1 style="color: #b07c2a; font-size: 20px">
														<b>All Coding Tests Report</b>
												</h1>
										</div>
								</div>
								<div class="col-md-12">
										<div class="table-responsive">
												<table class="table table-striped">
														<thead style="background-color: #03a9f4;">
																<tr>
																		<th><b>No</b></th>
																		<th><b>First Name</b></th>
																		<th><b>Last Name</b></th>
																		<th><b>Email</b></th>
																		<th><b>Test Title</b></th>

																		<th><b>Overall Score </b></th>
																		<th><b>Passed </b></th>
																		<th><b>Coding Problem</b></th>
																		<th><b>Code by Programmer</b></th>

																		<th><b>Download Code Report</b></th>


																</tr>
														</thead>
														<tbody>

																<c:forEach items="${data}" var="session" varStatus="loop">
																		<tr>
																				<td>${loop.count}</td>

																				<td>${session.firstName}</td>

																				<td>${session.lastName}</td>
																				<td>${session.email}</td>
																				<td>${session.testName}</td>

																				<td>${session.overallScore}</td>
																				<td>${session.pass}</td>
																				<td><a href="javascript:void(0);"
																						onclick="showCode('${session.problemStatement}');">See Code </a></td>
																				<td><a href="javascript:void(0);"
																						onclick="showCode('${session.outputCode}');">See Code </a></td>
																				<td><c:if test="${session.analysisApplicable}">
																								<a href="downloadCodeAnalysis?id=${session.questionMapperInstanceId}">Get
																										Code Analysis Report </a>
																						</c:if></td>

																		</tr>
																</c:forEach>
														</tbody>
												</table>
										</div>

								</div>
						</div>
						<!-- /.row -->



				</div>
				<!-- /.container -->
		</section>



		<footer class="footer footer-four">
				<div class="secondary-footer brand-bg darken-2 text-center">
						<div class="container">
								<ul>
										<li><a href="javascript:void(0)">Dashboard</a></li>
										<li><a href="question_list">Question Bank</a></li>
										<li><a href="testlist">Tests</a></li>
										<li><a href="javascript:void(0)">Skills</a></li>
										<li><a href="showReports">Results</a></li>
										<li><a href="javascript:void(0)">Code Analysis Reports</a></li>
										<li><a href="javascript:void(0)">Skill based Reports</a></li>
										<li><a href="showProfileParams">Recomm Setting</a></li>
										<li><a href="listUsers">Users</a></li>
								</ul>
						</div>
				</div>
		</footer>



		<div id="myModal" class="modal">

				<!-- Modal content -->
				<div class="modal-content">
						<span class="close">&times;</span>
						<code id="code">Some text in the Modal..</code>
				</div>

		</div>

		<!-- jQuery -->

		<spring:url value="/resources/assets/js/jquery-2.1.3.min.js" var="mainJs1" />
		<script src="${mainJs1}"></script>
		<spring:url value="/resources/assets/bootstrap/js/bootstrap.min.js" var="mainJs2" />
		<script src="${mainJs2}"></script>
		<spring:url value="/resources/assets/materialize/js/materialize.min.js" var="mainJs3" />
		<script src="${mainJs3}"></script>
		<spring:url value="/resources/assets/js/menuzord.js" var="mainJs4" />
		<script src="${mainJs4}"></script>
		<spring:url value="/resources/assets/js/bootstrap-tabcollapse.min.js" var="mainJs5" />
		<script src="${mainJs5}"></script>
		<spring:url value="/resources/assets/js/jquery.easing.min.js" var="mainJs6" />
		<script src="${mainJs6}"></script>
		<spring:url value="/resources/assets/js/jquery.sticky.min.js" var="mainJs7" />
		<script src="${mainJs7}"></script>
		<spring:url value="/resources/assets/js/smoothscroll.min.js" var="mainJs8" />
		<script src="${mainJs8}"></script>
		<spring:url value="/resources/assets/js/jquery.stellar.min.js" var="mainJs9" />
		<script src="${mainJs9}"></script>
		<spring:url value="/resources/assets/js/jquery.inview.min.js" var="mainJs10" />
		<script src="${mainJs10}"></script>
		<spring:url value="/resources/assets/owl.carousel/owl.carousel.min.js" var="mainJs11" />
		<script src="${mainJs11}"></script>
		<spring:url value="/resources/assets/flexSlider/jquery.flexslider-min.js" var="mainJs12" />
		<script src="${mainJs12}"></script>
		<spring:url value="/resources/assets/magnific-popup/jquery.magnific-popup.min.js" var="mainJs13" />
		<script src="${mainJs13}"></script>
		<spring:url value="https://maps.googleapis.com/maps/api/js" var="mainJs14" />
		<script src="${mainJs14}"></script>
		<spring:url value="/resources/assets/js/scripts.js" var="mainJs15" />
		<script src="${mainJs15}"></script>
		<spring:url value="/resources/assets/scripts/custom.js" var="mainJs16" />
		<script src="${mainJs16}"></script>
		<spring:url value="/resources/assets/scripts/pnotify.custom.min.js" var="mainJs17" />
		<script src="${mainJs17}"></script>

		<script>
			// Get the <span> element that closes the modal
			var span = document.getElementsByClassName("close")[0];

			// When the user clicks on <span> (x), close the modal
			span.onclick = function() {
				var modal = document.getElementById('myModal');
				modal.style.display = "none";
			}

			function showCode(code) {
				var modal = document.getElementById('myModal');
				document.getElementById("code").innerHTML = code;
				modal.style.display = "block";
			}
		</script>

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