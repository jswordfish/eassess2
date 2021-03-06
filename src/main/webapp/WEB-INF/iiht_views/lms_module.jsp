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
<title>Add Module</title>
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
<script>
	function goback() {
		window.location = "goback";
	}
</script>
<style>
.checkbox, .radio {
	color: black;
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
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="mb-30">
						<h2 class="section-title">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspAdd or Update Module</h2>
					</div>
                                       <form name="moduleForm" id="moduleForm" method="post" modelAttribute="module" action="lmsSaveModule">

						<div class="col-md-12">
							<div class="col-md-12">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Module Name</label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
							<form:input path="module.moduleName" name="moduleName" id="moduleName" required="true"/>
							<form:hidden path = "module.id"  />
										</div>
									</div>
								</div>
							</div>

							<div class="col-md-12">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Image URL (if applicable)</label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
										 <form:input path="module.imageUrl" name="imageUrl" id="imageUrl" />
										</div>
									</div>
								</div>
							</div>
							 
							 <div class="col-md-12">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Video URL (if applicable)</label>
									</div>
									<div class="col-md-8">
										<div class="input-field">
											     <form:input path="module.videoUrl" name="videoUrl" id="videoUrl" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row formfields">
									<div class="col-md-4">
										<label class="fieldtitle">Select Licenses (One or More)</label>
									</div>
									<div class="col-md-8">
										<div class="example">
											    <form:select path="module.lics" multiple="true" id="example-multiple-selected">
											<form:options items="${licenses}"/>
										</form:select>
										</div>
									</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row formfields">
									<div class="col-md-8">
										<label class="fieldtitle">Add/Edit Module Items</label>
										<a  href="javascript:addModuleItem()">Click to Add </a>
									</div>
								</div>
							</div>
							<div class="col-md-12">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead style="background-color: #03a9f4;">
								<tr>
								  <th><b>No</b></th>
                                                <th>Module Item Name</th>
                                                <th>Test Name</th>
												
																	   
												<th>Edit Module Item</th>
												<th>Remove Module Item</th>
								</tr>
							</thead>
							<tbody>
							<tbody>

							 <c:forEach  items="${module.items}" var="item" varStatus="loop">  
												
						                      	<tr>

										<td>${loop.count}</td>		
						                      		<td><c:out value="${item.itemName}"></c:out>  </td>
						                      		<td><c:out value="${item.testName}"></c:out>  </td>
						                      		
						                      		
										<td><a  href="javascript:editModuleItem(${item.id})">Edit </a> </td>
										<td><a  href="javascript:removeModuleItem(${item.id})">Remove </a> </td>
										
						                      	</tr>
						                      	</c:forEach>   
							</tbody>

						</table>
					</div>
							</div>
							
						</div>
						<div class="col-md-12">
						<div class="col-md-3"></div>
							<div class="col-md-6">
								<button  class="waves-effect waves-light btn submit-button mt-30">Save Module</button>
								<button type="button" class="waves-effect waves-light btn submit-button indigo mt-30"
									onClick="location.href='lmsModules';">Cancel</button>
							</div>
						</div>
					</form>

					<div class="col-md-3"></div>
				</div>

			</div>
		</div>
	</section>


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

// 			$('select').material_select();
			$('#example-multiple-selected').multiselect();
		});
		function addModuleItem(){
			document.getElementById("moduleForm").action = 'lmsModuleItem';
			document.getElementById("moduleForm").submit();
			
		}
		
		function editModuleItem(moduleItemId){
			
			document.getElementById("moduleForm").action = 'moduleitem?moduleItemId='+moduleItemId;
			document.getElementById("moduleForm").submit();
			
		}
		
		function removeModuleItem(moduleItemId){
			document.getElementById("moduleForm").action = 'removemoduleitem?moduleItemId='+moduleItemId;
			document.getElementById("moduleForm").submit();
			
		}

		 function notify(messageType, message){
			 var notification = 'Information';
				 $(function(){
					 new PNotify({
					 title: notification,
					 text: message,
					 type: messageType,
					 styling: 'bootstrap3',
					 hide: true
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