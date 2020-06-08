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
<link href="./resources/assets/img/ico/favicon.png" rel="shortcut icon" />
<link href="https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900" rel="stylesheet"
		type="text/css" />
<!-- Material Icons CSS -->
<link href="./resources/assets/fonts/iconfont/material-icons.css" rel="stylesheet" type="text/css" />

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
<link href="./resources/Multi Select/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
<style>
.checkbox, .radio {
	color: black;
}
</style>
<script>
	function goback() {
		window.location = "goback";
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
												<li><a href="testlist">Tests</a></li>
												<li class="active"><a href="skills">Skills</a></li>
												<li><a href="showReports">Results</a></li>
												<li><a href="codingSessions">Code Analysis Reports</a></li>
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
						<div class="row">
								<div class="col-md-3"></div>
								<div class="col-md-6">
										<div class="mb-30">
												<h2 class="section-title">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
														Create\Update Schedule</h2>
										</div>
									 <form name="scheduleForm"  method="post" modelAttribute="schedule" action="saveTestSchedule">
										<form:hidden path="schedule.scheduleId" />
												<div class="col-md-12">


														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select Test</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<form:select path="schedule.testId" class="slct">
																								<form:options items="${tests}" itemValue="id" itemLabel="testName" />
																						</form:select>
																				</div>
																		</div>
																</div>
														</div>
														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select Users</label>
																		</div>
																		<div class="col-md-8">
																				<div class="example">
																						<form:select multiple="true" path="schedule.users" id="example-multiple-selected">
																								<c:forEach var="user" items="${users}">
																										<form:option value="${user.email}">
																												<c:out value="${user.firstName} ${user.lastName}" />
																										</form:option>
																								</c:forEach>
																						</form:select>
																				</div>
																		</div>
																</div>
														</div>
														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select year</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<select class="slct">
																								<option value="2022" selected>2022</option>
																								<option value="2021" selected>2021</option>
																								<option value="2020" selected>2020</option>
																						</select>
																				</div>
																		</div>
																</div>
														</div>
														
														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select Month</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<form:select path="schedule.month" class="slct">
																								<form:option value="1" label="Jan"/>
																								<form:option value="2" label="Feb"/>
																								<form:option value="3" label="Mar"/>
																								<form:option value="4" label="Aprl"/>
																								<form:option value="5" label="May"/>
																								
																								<form:option value="6" label="Jun"/>
																								<form:option value="7" label="Jul"/>
																								<form:option value="8" label="Aug"/>
																								<form:option value="9" label="Sep"/>
																								<form:option value="10" label="Oct"/>
																								
																								<form:option value="11" label="Nov"/>
																								<form:option value="12" label="Dec"/>
																						</form:select>
																				</div>
																		</div>
																</div>
														</div>
														
														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select Date</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<form:select path="schedule.date" class="slct">
																								<form:option value="1" label="1"/>
																								<form:option value="2" label="2"/>
																								<form:option value="3" label="3"/>
																								<form:option value="4" label="4"/>
																								<form:option value="5" label="5"/>
																								
																								<form:option value="6" label="6"/>
																								<form:option value="7" label="7"/>
																								<form:option value="8" label="8"/>
																								<form:option value="9" label="9"/>
																								<form:option value="10" label="10"/>
																								
																								<form:option value="11" label="11"/>
																								<form:option value="12" label="12"/>
																								<form:option value="13" label="13"/>
																								<form:option value="14" label="14"/>
																								<form:option value="15" label="15"/>
																								
																								<form:option value="16" label="16"/>
																								<form:option value="17" label="17"/>
																								<form:option value="18" label="18"/>
																								<form:option value="19" label="19"/>
																								<form:option value="20" label="20"/>
																								
																								<form:option value="21" label="21"/>
																								<form:option value="22" label="22"/>
																								<form:option value="23" label="23"/>
																								<form:option value="24" label="24"/>
																								<form:option value="25" label="25"/>
																								
																								<form:option value="26" label="26"/>
																								<form:option value="27" label="27"/>
																								<form:option value="28" label="28"/>
																								<form:option value="29" label="29"/>
																								<form:option value="30" label="30"/>
																								<form:option value="31" label="31"/>
																						</form:select>
																				</div>
																		</div>
																</div>
														</div>
														
														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select Hour</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<form:select path="schedule.hours" class="slct">
																									<form:option value="0" label="0"/>
																									<form:option value="1" label="1"/>
																									<form:option value="2" label="2"/>
																									<form:option value="3" label="3"/>
																									<form:option value="4" label="4"/>
																									<form:option value="5" label="5"/>
																									
																									<form:option value="6" label="6"/>
																									<form:option value="7" label="7"/>
																									<form:option value="8" label="8"/>
																									<form:option value="9" label="9"/>
																									<form:option value="10" label="10"/>
																									
																									<form:option value="11" label="11"/>
																									<form:option value="12" label="12"/>
																									
																									<form:option value="13" label="13"/>
																									<form:option value="14" label="14"/>
																									<form:option value="15" label="15"/>
																									<form:option value="16" label="16"/>
																									<form:option value="17" label="17"/>
																									<form:option value="18" label="18"/>
																									
																									<form:option value="19" label="19"/>
																									<form:option value="20" label="20"/>
																									<form:option value="21" label="21"/>
																									<form:option value="22" label="22"/>
																									<form:option value="23" label="23"/>

																						</form:select>
																				</div>
																		</div>
																</div>
														</div>
														
														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select Minutes</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<form:select path="schedule.minutes" class="slct">
																								<form:option value="0" label="0"/>
																								<form:option value="2" label="2"/>
																								<form:option value="4" label="4"/>
																								<form:option value="6" label="6"/>
																								<form:option value="8" label="8"/>
																								<form:option value="10" label="10"/>
																								<form:option value="12" label="12"/>
																								
																								<form:option value="15" label="15"/>
																								
																								<form:option value="18" label="18"/>
																								<form:option value="20" label="20"/>
																								<form:option value="23" label="23"/>
																								<form:option value="25" label="25"/>
																								<form:option value="28" label="28"/>
																								
																								<form:option value="30" label="30"/>
																								
																								<form:option value="33" label="33"/>
																								<form:option value="36" label="36"/>
																								<form:option value="40" label="40"/>
																								<form:option value="42" label="42"/>
																								
																								
																								<form:option value="45" label="45"/>
																								<form:option value="48" label="48"/>
																								<form:option value="50" label="50"/>
																								<form:option value="52" label="52"/>
																								<form:option value="54" label="54"/>
																								<form:option value="56" label="56"/>
																								<form:option value="59" label="59"/>
																 
																						</form:select>
																				</div>
																		</div>
																</div>
														</div>
														
														<div class="col-md-8">
																<div class="row formfields">
																		<div class="col-md-4">
																				<label class="fieldtitle">Select Seconds</label>
																		</div>
																		<div class="col-md-8">
																				<div class="input-field">
																						<form:select path="schedule.seconds" class="slct">
																									<form:option value="0" label="0"/>
																									<form:option value="15" label="15"/>
																									<form:option value="30" label="30"/>
																									<form:option value="45" label="45"/>
																									<form:option value="59" label="59"/>
																						</form:select>
																				</div>
																		</div>
																</div>
														</div>
														
												</div>
												<div class="col-md-12">
														<div class="col-md-8">
																<button type="submit" name="submit" class="waves-effect waves-light btn submit-button mt-30">Save</button>
																<button type="button" class="waves-effect waves-light btn submit-button indigo mt-30"
																		onclick="location.href='showAllSchedules';">Cancel</button>
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
		<script src="./resources/Multi Select/bootstrap-multiselect.js"></script>

		<script type="text/javascript">
			$(document).ready(function() {
				$('.slct').material_select();
				$('#example-multiple-selected').multiselect();
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