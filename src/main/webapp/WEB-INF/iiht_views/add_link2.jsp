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
<title>Add User</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>
<script type="text/javascript" src="scripts/custom.js"></script>
<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="./resources/assets/img/ico/favicon.png" rel="shortcut icon" />
<link href="https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900" rel="stylesheet"
		type="text/css" />
<!-- Material Icons CSS -->
<link href="./resources/assets/fonts/iconfont/material-icons.css" rel="stylesheet" type="text/css" />
<!-- FontAwesome CSS -->
<link href="./resources/assets/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<!-- magnific-popup -->
<link href="./resources/assets/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css" />
<!-- owl.carousel -->
<link href="./resources/assets/owl.carousel/assets/owl.carousel.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/owl.carousel/assets/owl.theme.default.min.css" rel="stylesheet" type="text/css" />
<!-- flexslider -->
<link href="./resources/assets/flexSlider/flexslider.css" rel="stylesheet" type="text/css" />
<!-- materialize -->
<link href="./resources/assets/materialize/css/materialize.min.css" rel="stylesheet" type="text/css" />
<!-- Bootstrap -->
<link href="./resources/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- shortcodes -->
<link href="./resources/assets/css/shortcodes/shortcodes.css" rel="stylesheet" type="text/css" />
<!-- Style CSS -->
<link href="./resources/assets/style.css" rel="stylesheet" type="text/css" />
<!-- RS5.0 Main Stylesheet -->
<link href="./resources/assets/revolution/css/settings.css" rel="stylesheet" type="text/css" />
<!-- RS5.0 Layers and Navigation Styles -->
<link href="./resources/assets/revolution/css/layers.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/revolution/css/navigation.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/css/pnotify.custom.min.css" rel="stylesheet" type="text/css" />
<script>
	function goback() {
		window.location = "listTestLinks";
	}
</script>
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
						<li class="dropdown active" id="dd"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Tests
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="testlist">All Tests</a></li>
								<li><a href="listTestLinks">Test Link Management</a></li>
								<li><a href="showAllSchedules">Test Schedule</a></li>
							</ul></li>
						<li><a href="modules">Module</a></li>
						<li><a href="licenses">License</a></li>
						<li class="dropdown" id="dd2"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Report
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
						<div class="row">
								<div class="col-md-3"></div>
								<div class="col-md-6">
										<div class="mb-30">
												<h2 class="section-title">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspAdd New
														Link</h2>

										</div>
										<form name="tenantForm" method="post" modelAttribute="skill" action="saveTestLink">

												<div class="col-md-12">

														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select Test</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<form:select id="testName" path="link.testName">
																								<form:options items="${tests}" />
																						</form:select>
																						<form:hidden path="link.id" />
																				</div>
																		</div>
																</div>
														</div>
														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Link Start Date and Time</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<!--  -->
																						<div class='input-group date' id='datetimepicker1'>
																								<form:input path="link.stDate" required="true" class="form-control" id="startDate" />
																								<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
																								</span>
																						</div>
																						<!--  -->
																				</div>
																		</div>
																</div>
														</div>

														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Link End Date and Time</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<!--  -->
																						<div class='input-group date' id='datetimepicker2'>
																								<form:input path="link.edDate" required="true" class="form-control" id="endDate" />
																								<span class="input-group-addon"> <span class="glyphicon glyphicon-calendar"></span>
																								</span>
																						</div>
																						<!--  -->
																				</div>
																		</div>
																</div>
														</div>
												</div>
												<div class="col-md-12">
														<div class="col-md-8">
																<button type="submit" name="submit" class="waves-effect waves-light btn submit-button mt-30">Save</button>
																<button type="button" class="waves-effect waves-light btn submit-button indigo mt-30"
																		onclick="goback()">Cancel</button>
														</div>
												</div>
										</form>

										<div class="col-md-3"></div>
								</div>

						</div>
				</div>
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


		<!-- jQuery -->

		<script src="./resources/assets/js/jquery-2.1.3.min.js"></script>
		<script src="./resources/assets/bootstrap/js/bootstrap.min.js"></script>
		<script src="./resources/assets/materialize/js/materialize.min.js"></script>
		<script src="./resources/assets/js/menuzord.js"></script>
		<script src="./resources/assets/js/bootstrap-tabcollapse.min.js"></script>
		<script src="./resources/assets/js/jquery.easing.min.js"></script>
		<script src="./resources/assets/js/jquery.sticky.min.js"></script>
		<script src="./resources/assets/js/smoothscroll.min.js"></script>
		<script src="./resources/assets/js/jquery.stellar.min.js"></script>
		<script src="./resources/assets/js/jquery.inview.min.js"></script>
		<script src="./resources/assets/owl.carousel/owl.carousel.min.js"></script>
		<script src="./resources/assets/flexSlider/jquery.flexslider-min.js"></script>
		<script src="./resources/assets/magnific-popup/jquery.magnific-popup.min.js"></script>
		<script src="https://maps.googleapis.com/maps/api/js"></script>
		<script src="./resources/assets/js/scripts.js"></script>
		<script src="./resources/assets/scripts/custom.js"></script>
		<script src="./resources/assets/scripts/pnotify.custom.min.js"></script>
		<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
		<script
				src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>

		<script>
			$(function() {

				$('#datetimepicker1').datetimepicker({
					format : 'DD/MM/YYYY hh:mm a'
				//minDate:new Date()

				});

				$('#datetimepicker2').datetimepicker({
					format : 'DD/MM/YYYY hh:mm a'

				});
			});

			$(document).ready(function() {
				document.getElementById("dd").style.display = "block";
				document.getElementById("dd2").style.display = "block";
				document.getElementById("dd3").style.display = "block";
	
				$('select').material_select();
			});

		 
			  
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