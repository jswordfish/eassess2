<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*" %>
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
	<meta property="og:title" content=""/>
	<meta property="og:image" content=""/>
	<meta property="og:url" content=""/>
	<meta property="og:site_name" content=""/>
	<meta property="og:description" content=""/>
	<meta name="twitter:title" content="" />
	<meta name="twitter:image" content="" />
	<meta name="twitter:url" content="" />
	<meta name="twitter:card" content="" />

	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700,800" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Playfair+Display:400,700" rel="stylesheet">
	
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
	
	
	<link href="./resources/css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
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
	
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->
	<style>
	#classModal {}

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
							<p><b>Welcome to eAssess</b></p>
						</div>
						
					</div>
				</div>
			</div>
			<div class="top-menu">
				<div class="container">
					<div class="row">
						<div class="col-md-2">
							<div id="colorlib-logo"><a href="index.html">eAssess</a></div>
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
					</div>
				</div>
			</div>
		</nav>
		
		
		
		

		<div id="colorlib-services">
		
		
		
		
			<div class="container">
			
			 <div class="input-group">
			<span class="input-group-addon">Select Classifier & Status</span>
			<select  style="class:form-control;margin-left: 30px;" id="classifierfull" >
				<c:forEach  items="${classifiers}" var="cls" >  
					 <option value="${cls}"  ${(classifierall !=null && classifierall == cls)?"selected":""} >${cls}</option>
				</c:forEach>			
			</select>
			
			<select  style="class:form-control;margin-left: 30px;" id="statuses" >
				<c:forEach  items="${statuses}" var="sts" >  
		<option value="${sts}" ${(status !=null && status == sts)?"selected":""} >${sts}</option>
				</c:forEach>			
			</select>
	<button type="button" class="btn btn-primary"  onClick="javascript:fetchResultsByClassifierAndStatus();">
          Fetch Results
        </button>
			
		</div>
				<table class="table">
				<thead>
				  <tr>
				    <th>First Name</th>
				    <th>Last Name</th>
					<th>Email</th>
					<th>Test Name</th>
					<th>Date & Time</th>
					<th>Total Marks</th>
					<th>Total Marks Recieved</th>
					<th>Average Score </th>
					<th>Weighted Score</th>
					<th>Checked Earlier?</th>
					<th>Check Test </th>
				  </tr>
				</thead>
				<tbody>
					 <c:forEach  items="${sessions}" var="sess" >  
					<tr class="${sess.style}">
					<td>${sess.firstName == null?"NA":sess.firstName}</td>
					<td>${sess.lastName == null?"NA":sess.lastName}</td>
					<td>${sess.user}</td>
						<td>${sess.testName}</td>
						<td>${sess.dateofTest}</td>
						<td>${sess.totalMarks}</td>
						
						<td>${sess.totalMarksRecieved}</td>
						<td>${sess.percentageMarksRecieved}</td>
						<td>${sess.weightedScorePercentage}</td>
						<th>${sess.markComplete == true?"Yes":"No"}</th>
						<td><a href="javascript:checkTest('${sess.testName}', '${sess.user}');">Get Details</a></td>
					  </tr> 
					</c:forEach>
				</tbody>
				</table>
			</div>
		</div>

		
		
	
	
	

</div>
	<!-- end modal -->	
	
	

	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up2"></i></a>
	</div>
	
	<script>
		function fetchResultsByClassifierAndStatus(){
			var e = document.getElementById("classifierfull");
			var strUser = e.options[e.selectedIndex].value;
			strUser = encodeURIComponent(strUser);
			
			var e1 = document.getElementById("statuses");
			var strUser1 = e1.options[e1.selectedIndex].value;
			strUser1 = encodeURIComponent(strUser1);
			
			window.location="showManualReviewResults?status="+strUser1+"&classifierall="+strUser;
		}
		
	function checkTest(testName, email){
		testName = encodeURIComponent(testName);
		email = encodeURIComponent(email);
		window.location = 'showManualReviewQuestions?testName='+testName+'&email='+email;
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
		 $(function(){
			 new PNotify({
			 title: notification,
			 text: '${message}',
			 type: '${msgtype}',
			 styling: 'bootstrap3',
			 hide: true
		     });
		 }); 	 
	      </script>
	</c:if>

	</body>
</html>

