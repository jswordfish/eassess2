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
<title>Modules</title>
<link href="./resources/assets/img/ico/favicon.png" rel="shortcut icon" />
<link href="https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900" rel="stylesheet"
		type="text/css" />
<link href="./resources/assets/fonts/iconfont/material-icons.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/magnific-popup/magnific-popup.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/owl.carousel/assets/owl.carousel.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/owl.carousel/assets/owl.theme.default.min.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/flexSlider/flexslider.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/materialize/css/materialize.min.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/css/shortcodes/shortcodes.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/style.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/revolution/css/settings.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/revolution/css/layers.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/revolution/css/navigation.css" rel="stylesheet" type="text/css" />
<link href="./resources/assets/css/pnotify.custom.min.css" rel="stylesheet" type="text/css" />
<link href="./resources/Multi Select/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />

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
						<li class="active"><a href="showLearnerAdminDashboard">Home</a></li>
								
								<li><a href="lmsModules">Manage Modules</a></li>
								
								<li><a href="lmsQuestion_list">Question Bank</a></li>
								
								<li><a href="lmsTests">Test Creation</a></li>
								
								<li><a href="showAllResults">Results - Learners</a></li>
								<li><a href="showManualReviewResults">Review Tests</a></li>
								
								<li><a href="signoff">Log Off</a></li>
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
					<div class="col-md-6"></div>
					<div class="col-md-3">
						<a href="lmsModule" class="btn waves-effect waves-light col-md-12"><i
							class="material-icons fa fa-plus-circle"></i> Add New Module</a>
					</div>

					<div class="col-md-2">
						<a href="signoff" class="btn waves-effect waves-light col-md-12"><i
							class="material-icons fa fa-sign-out"></i> Sign Off</a>
					</div>
				</div>
 
				<div class="col-md-12">
					<div class="mt-10"></div>
					<div class="col-md-5">
						<h1 style="color: #b07c2a;">
							<b>Modules Management</b>
						</h1>
					</div>
				<div class="col-md">
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
				<div class="col-md-12">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead style="background-color: #03a9f4;">
								<tr>
									<th><b>No</b></th>
                                                <th>Module Name</th>
                                                <th>Licenses</th>
												<th>No of Items</th>
																	   
												<th>Edit Module</th>
												<th>Delete Module</th>
								</tr>
							</thead>
							<tbody>
							<tbody>

							 <c:forEach  items="${modules}" var="module" varStatus="loop"> 
									<tr>


												<td>${loop.count}</td>		
						                      		<td><c:out value="${module.moduleName}"></c:out>  </td>
						                      		<td> ${module.licenseNames}</td>
						                      		<td>${module.items.size()}  </td>
						                      		
										<td><a  href="lmsModule?moduleId=${module.id}">Click to Edit</a> </td>
										<td><a  href="javascript:confirmDelete('${module.id}')">Click to Delete</a> </td>

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

		$(document).ready(function() {
			document.getElementById("dd").style.display = "block";
			document.getElementById("dd2").style.display = "block";
			document.getElementById("dd3").style.display = "block";
	
	//			$('select').material_select();
			$('#example-multiple-selected').multiselect();
		});
	function confirmDelete(id) {
		//id = btoa(id);
       (new PNotify({
	    title: 'Confirmation Needed',
	    text: 'Are you sure? Do you really want to delete this Module. This is a non-recoverable option?',
	    icon: 'glyphicon glyphicon-question-sign',
	    hide: false,
	    confirm: {
		confirm: true
	    },
	    buttons: {
		closer: false,
		sticker: false
	    },
	    history: {
		history: false
	    }
	})).get().on('pnotify.confirm', function() {
	    window.location = "lmsDeleteModule?moduleId="+id;
	}).on('pnotify.cancel', function() {
	   
	});
    }

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