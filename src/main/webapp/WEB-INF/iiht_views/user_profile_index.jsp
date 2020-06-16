<!DOCTYPE HTML>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*"%>
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
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->

	</head>
	<body>
		
	<div class="colorlib-loader"></div>

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
								<li class="active"><a href="index.html">Home</a></li>
								
								<li><a href="about.html">About</a></li>
								
								<li><a href="contact.html">Contact</a></li>
								
							</ul>
						</div>
					</div>
				</div>
			</div>
		</nav>
		
		
		<div id="colorlib-services">
			<div class="container">
				<div class="row">
					<div class="col-md-3 text-center animate-box">
						<div class="services">
							<span class="icon">
								<i class="flaticon-books"></i>
							</span>
							<div class="desc">
								<h3><a href="javascript:enableICSE()">ICSE</a></h3>
								<p>Separated they live in Bookmarksgrove right at the coast of the Semantics</p>
							</div>
						</div>
					</div>
					<div class="col-md-3 text-center animate-box">
						<div class="services">
							<span class="icon">
								<i class="flaticon-professor"></i>
							</span>
							<div class="desc">
							
								<h3><a href="javascript:enableCBSC()">CBSC</a></h3>
								<p>Separated they live in Bookmarksgrove right at the coast of the Semantics</p>
							</div>
						</div>
					</div>
					<div class="col-md-3 text-center animate-box">
						<div class="services">
							<span class="icon">
								<i class="flaticon-book"></i>
							</span>
							<div class="desc">
							<h3><a href="javascript:enableCompetitive()">Competitive Exams</a></h3>
								
								<p>Separated they live in Bookmarksgrove right at the coast of the Semantics</p>
							</div>
						</div>
					</div>
					<div class="col-md-3 text-center animate-box">
						<div class="services">
							<span class="icon">
								<i class="flaticon-diploma"></i>
							</span>
							<div class="desc">
								<h3><a href="javascript:enableTechnology()">Technology</a></h3>
								<p>Separated they live in Bookmarksgrove right at the coast of the Semantics</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="colorlib-classes colorlib-light-grey" id="icsemodules">
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-md-offset-2 text-center colorlib-heading animate-box">
						<h2>ICSE Modules</h2>
						
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-1.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 8</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-2.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 9</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-3.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 10</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					
				</div>
			</div>	
		</div>
		
		<div class="colorlib-classes colorlib-light-grey" style="display:none" id="cbscmodules">
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-md-offset-2 text-center colorlib-heading animate-box">
						<h2>CBSE Modules</h2>
						
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-1.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 7</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-2.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 8</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-3.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 9</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					
				</div>
			</div>	
		</div>
		
		<div class="colorlib-classes colorlib-light-grey" style="display:none" id="compmodules">
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-md-offset-2 text-center colorlib-heading animate-box">
						<h2>Competitive Exams Modules</h2>
						
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-1.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 7</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-2.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 8</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-3.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Grade 9</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					
				</div>
			</div>	
		</div>
		
		<div class="colorlib-classes colorlib-light-grey" style="display:none" id="technologymodules">
			<div class="container">
				<div class="row">
					<div class="col-md-8 col-md-offset-2 text-center colorlib-heading animate-box">
						<h2>Technology Modules</h2>
						
					</div>
				</div>
				<div class="row">
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-1.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Java</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-2.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Big Data</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					<div class="col-md-4 animate-box">
						<div class="classes">
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/classes-3.jpg);">
								<span class="price text-center"><small>$450</small></span>
							</div>
							<div class="desc">
								<h3><a href="#">Python</a></h3>
								<p>Pointing has no control about the blind texts it is an almost unorthographic life</p>
								<p><a href="#" class="btn-learn">Learn More <i class="icon-arrow-right3"></i></a></p>
							</div>
						</div>
					</div>
					
				</div>
			</div>	
		</div>

		

		
	</div>

	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up2"></i></a>
	</div>
	
	<script>
		function enableICSE(){
		document.getElementById("icsemodules").style.display = "";
		document.getElementById("compmodules").style.display = "none";
		document.getElementById("technologymodules").style.display = "none";
		document.getElementById("cbscmodules").style.display = "none";
		
		}
		
		function enableCBSC(){
		document.getElementById("icsemodules").style.display = "none";
		document.getElementById("compmodules").style.display = "none";
		document.getElementById("technologymodules").style.display = "none";
		document.getElementById("cbscmodules").style.display = "";
		}
		
		function enableCompetitive(){
		document.getElementById("icsemodules").style.display = "none";
		document.getElementById("compmodules").style.display = "";
		document.getElementById("technologymodules").style.display = "none";
		document.getElementById("cbscmodules").style.display = "none";
		}
		
		function enableTechnology(){
		document.getElementById("icsemodules").style.display = "none";
		document.getElementById("compmodules").style.display = "none";
		document.getElementById("technologymodules").style.display = "";
		document.getElementById("cbscmodules").style.display = "none";
		}
	
	</script>
	
	<!-- jQuery -->
	<script src="./resources/userprofile/js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="userprofile/js/jquery.easing.1.3.js"></script>
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

	</body>
</html>