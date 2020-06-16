<!DOCTYPE html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Student Result</title>

<spring:url value="/resources/assets/img/ico/favicon.png" var="c1" />

<link href="${c1}" rel="shortcut icon" />

<spring:url
	value="https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900"
	var="c2" />

<link href="${c2}" rel="stylesheet" type="text/css" />

<!-- Material Icons CSS -->
<spring:url value="/resources/assets/fonts/iconfont/material-icons.css"
	var="c3" />

<link href="${c3}" rel="stylesheet" type="text/css" />

<!-- FontAwesome CSS -->
<spring:url
	value="/resources/assets/fonts/font-awesome/css/font-awesome.min.css"
	var="c4" />

<link href="${c4}" rel="stylesheet" type="text/css" />

<!-- magnific-popup -->
<spring:url value="/resources/assets/magnific-popup/magnific-popup.css"
	var="c5" />

<link href="${c5}" rel="stylesheet" type="text/css" />

<!-- owl.carousel -->
<spring:url
	value="/resources/assets/owl.carousel/assets/owl.carousel.css" var="c6" />

<link href="${c6}" rel="stylesheet" type="text/css" />

<spring:url
	value="/resources/assets/owl.carousel/assets/owl.theme.default.min.css"
	var="c7" />

<link href="${c7}" rel="stylesheet" type="text/css" />
<!-- flexslider -->
<spring:url value="/resources/assets/flexSlider/flexslider.css" var="c8" />

<link href="${c8}" rel="stylesheet" type="text/css" />

<!-- materialize -->
<spring:url
	value="/resources/assets/materialize/css/materialize.min.css" var="c9" />

<link href="${c9}" rel="stylesheet" type="text/css" />

<!-- Bootstrap -->
<spring:url value="/resources/assets/bootstrap/css/bootstrap.min.css"
	var="c10" />

<link href="${c10}" rel="stylesheet" type="text/css" />

<!-- shortcodes -->
<spring:url value="/resources/assets/css/shortcodes/shortcodes.css"
	var="c11" />

<link href="${c11}" rel="stylesheet" type="text/css" />

<!-- Style CSS -->
<spring:url value="/resources/assets/style.css" var="c12" />

<link href="${c12}" rel="stylesheet" type="text/css" />

<!-- RS5.0 Main Stylesheet -->
<spring:url value="/resources/assets/revolution/css/settings.css"
	var="c13" />

<link href="${c13}" rel="stylesheet" type="text/css" />

<!-- RS5.0 Layers and Navigation Styles -->
<spring:url value="/resources/assets/revolution/css/layers.css"
	var="c14" />

<link href="${c14}" rel="stylesheet" type="text/css" />
<spring:url value="/resources/assets/revolution/css/navigation.css"
	var="c15" />

<link href="${c15}" rel="stylesheet" type="text/css" />
<spring:url value="/resources/assets/css/pnotify.custom.min.css"
	var="c16" />

<link href="${c16}" rel="stylesheet" type="text/css" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

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
						<li class="dropdown" id="dd"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Tests
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="testlist">All Tests</a></li>
								<li><a href="listTestLinks">Test Link Management</a></li>
								<li><a href="showAllSchedules">Test Schedule</a></li>
							</ul></li>
						<li><a href="modules">Module</a></li>
						<li><a href="licenses">License</a></li>
						<li class="dropdown active" id="dd2"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Report
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="showReports">Results</a></li>
								<li><a href="codingSessions">Code Reports</a></li>
								<li><a href="showSkillTags">Skill Reports</a></li>
							</ul></li>
						<li><a href="skills">Skills</a></li>
						<li><a href="showProfileParams">Recomm Setting</a></li>
						<li class="dropdown" id="dd3"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Users
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="listUsers">All Users</a></li>
								<li><a href="lmsAdmins">LMS Admin Users</a></li>
							</ul></li>
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
				<div align="center">
					<div class="mt-10"></div>
					<button type="submit" class="btn btn-success"
						onclick="window.location.href='/AssesmentApp/downloadTestReport'">Test
						Reports</button>
					<button type="submit" class="btn btn-success"
						onclick="window.location.href='/AssesmentApp/downloadUserReport'">All
						Tests</button>
					<button type="submit" class="btn btn-success"
						onclick="window.location.href='/AssesmentApp/frameset?__report=qs.rptdesign'">
						User Sessions Report</button>
					<button type="submit" class="btn btn-success"
						onclick="window.location.href='/AssesmentApp/frameset?__report=finalreport2new.rptdesign'">Report
						By Percentile</button>
					<button type="submit" class="btn btn-success"
						onclick="window.location.href='/AssesmentApp/frameset?__report=test.rptdesign'">Report
						By Testname</button>
					<button class="btn btn-success" onclick="javascript:shareOpen()">DownloadUserReport</button>
					<button class="btn btn-success" onclick="javascript:openDownload()">Download
						Report</button>
				</div>




				<div class="col-md-12">
					<div class="mt-10"></div>
					<div class="col-md-5">
						<h1 style="color: #b07c2a;">
							<b style="font-size: x-large;">${reportType}</b>
						</h1>
					</div>

					<div class="col-md-12">
						<div class="table-responsive">
							<table class="table table-striped">
								<thead style="background-color: #03a9f4;">
									<tr>
										<th><b>No</b></th>
										<th style="width: 20%"><b>Test Title</b></th>
										<th><b>Sections</b></th>
										<th style="width: 5%"><b>Sessions</b></th>
										<th><b>Passed </b></th>
										<th><b>Average Score</b></th>
										<th><b>Highest Score</b></th>
										<th><b>Top 3</b></th>
										<th style="width: 20%"><b>Contact</b></th>
										<th><b>Basic Report</b></th>
										<th><b>Full Reports</b></th>
									</tr>
								</thead>
								<tbody>
								<tbody>

									<c:forEach items="${testsessions}" var="session"
										varStatus="loop">
										<tr>
											<td>${loop.count}</td>

											<td><a
												href="downloadUserReportsForTest2?testName=${session.testName}">${session.testName}</a></td>

											<td>${session.sectionsInfo}</td>
											<td>${session.noOfSessions}</td>
											<td>${session.noOfPassResults}</td>

											<td>${session.averageScore}</td>
											<td>${session.highestScore}</td>
											<td>${session.topCandidates}</td>
											<td>${session.topCandidatesEmail}</td>
											<td><a
												href="downloadUserReportsForTest?testName=${session.testName}">Click
											</a></td>
											<td><a
												href="downloadUserReportsForTestWithExtraAttrs?testName=${session.testName}">Click
											</a></td>

										</tr>
									</c:forEach>
								</tbody>

							</table>
						</div>

					</div>
				</div>
				<!-- /.row -->

			</div>

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
					<li><a href="listUsers">Users</a></li>
				</ul>
			</div>
		</div>
	</footer>

	<script>
		function shareOpen() {
			$('#modalcopy').modal('hide');
			$('#modalshare').modal('show');
		}

		function openDownload() {
			console.log("test");
			$('#modalshare2').modal('show');
		}
	</script>

	<div id="modalshare2" class="modal fade modalcopy" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-body">
<%-- 					<form method="GET" action="downloadReportFilters"> --%>
<%-- 						Test Name: <form:select path="test.testName" class="form-control" --%>
<%-- 							onchange="Change()" id="name"> --%>
<!-- 							<option value="ALL">ALL</option> -->
<%-- 							<form:options items="${listTest}" itemValue="testName" --%>
<%-- 								itemLabel="testName" /> --%>
<%-- 						</form:select> --%>
<!-- 						<br> -->
<!-- 						User Name:<select id="slct" class="form-control" name="userName"> -->
<!-- 							<option>ALL</option> -->
<!-- 						</select>  -->
<!-- 						<br> -->
<!-- 						Start Date:<input type="date" name="startDate"/> -->
<!-- 						End Date:<input type="date" name="endDate"/> -->
<!-- 						<br> -->
<!-- 						Result:<select class="form-control" name="result"> -->
<!-- 							<option value="ALL">All</option> -->
<!-- 							<option value="true">Pass</option> -->
<!-- 							<option value="false">Fail</option> -->
<!-- 						</select>  -->
<!-- 						<br> -->
<!-- 						Percentage <input type="text" name="min" placeholder="Minimum percentage"/> -->
<!-- 								<input type="text" name="max" placeholder="Maximum percentage"/> -->
					
<!-- 						<input type="submit" value="Download" style="align-content: center;" /> -->
						
<%-- 					</form> --%>
				</div>
			</div>
		</div>
	</div>

	<div id="modalshare" class="modal fade modalcopy" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-body">
<%-- 					<form method="GET" action="downloadUserReportPdf"> --%>
<%-- 						<form:select path="test.testName" class="form-control" --%>
<%-- 							onchange="Change1()" id="name1"> --%>
<!-- 							<option value="Select any option">Select Any Test Name</option> -->
<%-- 							<form:options items="${listTest}" itemValue="testName" --%>
<%-- 								itemLabel="testName" /> --%>
<%-- 						</form:select> --%>
<!-- 						<br> -->
<!-- 						<select id="slct1" class="form-control" name="userEmail"></select> -->
<!-- 						<input type="submit" value="Download" -->
<!-- 							style="align-content: center;" /> -->
<%-- 					</form> --%>
				</div>
			</div>
		</div>
	</div>




	<script>
		function Change() {
			var testN = $('#name').val();
			console.log(testN)
			$.ajax({
				url : "fetchEmail?testN=" + testN,
				type : 'GET',
				success : function(response) {
					console.log(response.listEmail.length)
					$('.opt').remove();
					for (i = 0; i < response.listEmail.length; i++) {
						console.log(response.listEmail[i]);
						$("#slct").append(
								"<option class='opt'>" + response.listEmail[i]
										+ "</option>");
					}
				},
			});
		}

		function Change1() {
			var testN = $('#name1').val();
			console.log(testN)
			$.ajax({
				url : "fetchEmail?testN=" + testN,
				type : 'GET',
				success : function(response) {
					console.log(response.listEmail.length)
					$('.opt').remove();
					for (i = 0; i < response.listEmail.length; i++) {
						console.log(response.listEmail[i]);
						$("#slct1").append(
								"<option class='opt'>" + response.listEmail[i]
										+ "</option>");
					}
				},
			});
		}
	</script>
	<!-- jQuery -->

	<spring:url value="/resources/assets/js/jquery-2.1.3.min.js"
		var="mainJs1" />
	<script src="${mainJs1}"></script>
	<spring:url value="/resources/assets/bootstrap/js/bootstrap.min.js"
		var="mainJs2" />
	<script src="${mainJs2}"></script>
	<spring:url value="/resources/assets/materialize/js/materialize.min.js"
		var="mainJs3" />
	<script src="${mainJs3}"></script>
	<spring:url value="/resources/assets/js/menuzord.js" var="mainJs4" />
	<script src="${mainJs4}"></script>
	<spring:url value="/resources/assets/js/bootstrap-tabcollapse.min.js"
		var="mainJs5" />
	<script src="${mainJs5}"></script>
	<spring:url value="/resources/assets/js/jquery.easing.min.js"
		var="mainJs6" />
	<script src="${mainJs6}"></script>
	<spring:url value="/resources/assets/js/jquery.sticky.min.js"
		var="mainJs7" />
	<script src="${mainJs7}"></script>
	<spring:url value="/resources/assets/js/smoothscroll.min.js"
		var="mainJs8" />
	<script src="${mainJs8}"></script>
	<spring:url value="/resources/assets/js/jquery.stellar.min.js"
		var="mainJs9" />
	<script src="${mainJs9}"></script>
	<spring:url value="/resources/assets/js/jquery.inview.min.js"
		var="mainJs10" />
	<script src="${mainJs10}"></script>
	<spring:url value="/resources/assets/owl.carousel/owl.carousel.min.js"
		var="mainJs11" />
	<script src="${mainJs11}"></script>
	<spring:url
		value="/resources/assets/flexSlider/jquery.flexslider-min.js"
		var="mainJs12" />
	<script src="${mainJs12}"></script>
	<spring:url
		value="/resources/assets/magnific-popup/jquery.magnific-popup.min.js"
		var="mainJs13" />
	<script src="${mainJs13}"></script>
	<spring:url value="https://maps.googleapis.com/maps/api/js"
		var="mainJs14" />
	<script src="${mainJs14}"></script>
	<spring:url value="/resources/assets/js/scripts.js" var="mainJs15" />
	<script src="${mainJs15}"></script>
	<spring:url value="/resources/assets/scripts/custom.js" var="mainJs16" />
	<script src="${mainJs16}"></script>
	<spring:url value="/resources/assets/scripts/pnotify.custom.min.js"
		var="mainJs17" />
	<script src="${mainJs17}"></script>
	<script>
		$(document).ready(function() {
			document.getElementById("dd").style.display = "block";
			document.getElementById("dd2").style.display = "block";
			document.getElementById("dd3").style.display = "block";

			$('.mdb-select').materialSelect();
		});
	</script>
	<script>
		$('#search').on('click', function() {
			var text = document.getElementById("searchText").value;
			if (text.length != 0) {
				window.location = "searchUsrs?searchText=" + text;
			}
		});
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
</body>

</html>