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
<title>Question List</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker3.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link href="./resources/assets/css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
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
<link href="./resources/assets/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css" />
<!-- magnific-popup -->
<link href="./resources/assets/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css" />
<!-- owl.carousel -->
<link href="./resources/assets/owl.carousel/assets/owl.carousel.css" rel="stylesheet"
	type="text/css" />
<link href="./resources/assets/owl.carousel/assets/owl.theme.default.min.css" rel="stylesheet"
	type="text/css" />
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
<style>
#myDropdown {
	min-width: 387px !important;
	height: 300px !important;
	overflow: auto !important;
}
</style>

<script>
	/* When the user clicks on the button,
	 toggle between hiding and showing the dropdown content */
	function myFunction() {
		document.getElementById("myDropdown").classList.toggle("show");
	}

	function filterFunction() {
		var input, filter, ul, li, a, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		div = document.getElementById("myDropdown");
		a = div.getElementsByTagName("a");
		for (i = 0; i < a.length; i++) {
			txtValue = a[i].textContent || a[i].innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				a[i].style.display = "";
			} else {
				a[i].style.display = "none";
			}
		}
	}
</script>
<style>
div.scrollmenu {
	background-color: #333;
	overflow: auto;
	white-space: nowrap;
}

.test {
	overflow: auto;
	white-space: nowrap;
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
						<li class="active"><a href="question_list">Question Bank</a></li>
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
			<div class="row mt-5">
				<div class="col-md-12">
					<div class="col-md-2"></div>
					<div class="col-md-2">
						<a href="addQuestion" class="btn waves-effect waves-light col-md-12"><i
							class="material-icons fa fa-plus-circle"></i>Add </a>
					</div>
					<div class="col-md-2">
						<a href="javascript:showFileDialog();" id="uploadQuestionsLink"
							class="btn waves-effect waves-light col-md-12"><i class="material-icons fa fa-upload"></i>Import
						</a>
					</div>

					<div class="col-md-2">
						<a href="verification" class="btn waves-effect waves-light col-md-12"><i
							class="material-icons fa fa-check"></i>Verify </a>
					</div>

					<div class="dropdown">
						<div class="col-md-2">
							<div class="dropdown">
								<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown"
									onclick="myFunction()">
									<i class="material-icons fa fa-download"></i>Download
								</button>
								<div class="dropdown-menu" id="myDropdown">
									<input type="text" placeholder="Search.." id="myInput" onkeyup="filterFunction()">
									<c:forEach items="${qu}" var="qualifier1">
										<a style="padding-left: 5px;" class="dropdown-item"
											href="<%=request.getContextPath()%>/downloadQuestion?qualifier1=${qualifier1}">${qualifier1}</a>
										<br />

									</c:forEach>
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-2">
						<a href="signoff" class="btn waves-effect waves-light col-md-12"><i
							class="material-icons fa fa-sign-out"></i>Sign Out </a>
					</div>

				</div>
				<%-- 								<form id="fileFormQuestions" method="POST" enctype="multipart/form-data"> --%>
				<!-- 										<input type="file" name="fileQuestions" id="fileQuestions" style="display: none" /> -->

				<%-- 								</form> --%>
				<form id="fileFormQuestions" method="POST" enctype="multipart/form-data">
					<input type="file" name="fileQuestions" id="fileQuestions" style="display: none" />
				</form>
				<div class="col-md-12">
					<div class="mt-10"></div>
					<div class="col-md-5">
						<h1 style="color: #b07c2a;">
							<b>Question Bank</b>
						</h1>
					</div>
					<div class="col-md-4">
						<div class="widget widget_search">

							<div class="search-form">
								<form action="searchQuestions" method="get">
									<input type="text" placeholder="Search a question" name="searchText" id="searchText">
									<button type="submit" id="search">
										<i class="fa fa-search"></i>
									</button>
								</form>
							</div>
						</div>
					</div>
					<div class="col-md-3">
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
			<div class="col-md-12">
				<div class="table-responsive">

					<input type="hidden" id="sort" value="asc">
					<table class="table table-striped" id="tbl">
						<thead style="background-color: #03a9f4;">
							<tr>
								<th>No</th>
								<th>Question</th>

								<th>Category</th>

								<th>Difficulty Level</th>

								<th>Updated On</th>

								<th>Update</th>
								<th>Delete</th>
							</tr>
						</thead>
						<tbody>
						<tbody>

							<c:forEach items="${qs}" var="question" varStatus="loop">
								<tr>

									<td>${loop.count}</td>


									<td><c:out value="${question.questionText}"></c:out></td>

									<td>${question.category}</td>
									<td><c:out value="${question.difficultyLevel.level}"></c:out></td>
									<td><c:out value="${question.updatedDate}"></c:out></td>
									<td><a href="addQuestion?qid=${question.id}">Click </a></td>
									<td><a href="javascript:confirm('${question.id}')">Click </a></td>
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
					<li><a href="codingSessions">Code Analysis Reports</a></li>
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

	<script>
		/* off-canvas sidebar toggle */
		$('[data-toggle=offcanvas]').click(
				function() {
					$('.row-offcanvas').toggleClass('active');
					$('.collapse').toggleClass('in').toggleClass('hidden-xs')
							.toggleClass('visible-xs');
				});

		$('#search').on('click', function() {
			var text = document.getElementById("searchText").value;
			if (text.length != 0) {
				window.location = "searchQuestions?searchText=" + text;
			}
		});

		var isXlsx = function(name) {
			return name.match(/xlsx$/i)
		};

		$("#btnfile").click(function() {
			$("#uploadfile").click();
		});

		function showFileDialog() {
			$("#fileQuestions	").click();
		}

		$(document)
				.ready(
						function() {
							document.getElementById("dd").style.display = "block";
							document.getElementById("dd2").style.display = "block";
							document.getElementById("dd3").style.display = "block";
							var file = $('[name="fileQuestions"]');
							var imgContainer = $('#imgContainer');

							$('#uploadLink').on('click', function() {
								// $("#file").click();

							});

							var fileU = document
									.getElementById('fileQuestions');
							fileU
									.addEventListener(
											"change",
											function() {
												if (fileU.files.length > 0) {
													var filename = $.trim(file
															.val());
													if (!(isXlsx(filename))) {
														notify('Error',
																'Please select an xlsx file to upload');
														return;
													}
													$
															.ajax(
																	{
																		xhr : function() {
																			var xhr = new window.XMLHttpRequest();
																			return xhr;
																		},
																		url : 'upload',
																		type : "POST",
																		data : new FormData(
																				document
																						.getElementById("fileFormQuestions")),
																		enctype : 'multipart/form-data',
																		processData : false,
																		contentType : false
																	})
															.done(
																	function(
																			data) {
																		notify(
																				'Success',
																				'File Upload Successful');
// 																		setTimeout(function(){}, 3000);																		
																		location.reload();
																	})
															.fail(
																	function(
																			jqXHR,
																			textStatus) {
																		notify(
																				'Failure',
																				'File Upload Failed. Please contact Administrator');
																	});
													document
															.getElementById('fileQuestions').value = null;
													return;
												}

											});

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

		function confirm(id) {
			(new PNotify({
				title : 'Confirmation Needed',
				text : 'Are you sure? Do you really want to delete this Q?',
				icon : 'glyphicon glyphicon-question-sign',
				hide : false,
				confirm : {
					confirm : true
				},
				buttons : {
					closer : false,
					sticker : false
				},
				history : {
					history : false
				}
			})).get().on('pnotify.confirm', function() {
				window.location = "removeQuestionFromList?qid=" + id;
			}).on('pnotify.cancel', function() {

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
