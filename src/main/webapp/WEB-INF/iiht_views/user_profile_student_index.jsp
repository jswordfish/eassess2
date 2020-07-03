<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*, com.assessment.web.dto.*" %>
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
	
	<!-- FOR IE9 below -->
	<!--[if lt IE 9]>
	<script src="js/respond.min.js"></script>
	<![endif]-->
	<style>
	#classModal {}

	.modal-body {
	  overflow-x: auto;
	}
	
	.borderless td {
	border-width: 0 !important;
    border-bottom: 1px solid #003cff;
	}
	
	.borderless tr {
		border-bottom: 1pt solid #003cff;
	}

	</style>

	</head>
	<body>
		
	<div class="colorlib-loader"></div>

	<div id="page">
		<nav class="colorlib-nav" role="navigation">
			<div class="upper-menu">
				<div class="container">
					<div class="row">
						<div class="col-xs-4">
						<%
						User user = (User) request.getSession().getAttribute("user");
						
						%>
							<p><b>Welcome <%= user.getFirstName()+" "+user.getLastName() %></b></p>
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
								<li class="active"><a href="showLearnerUserDashboard">Home</a></li>
								
								<li><a href="showUserTests">My Tests</a></li>
								
								<li><a href="showUserTests">My Profile</a></li>
								<li><a href="signoff">Log Off</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</nav>
		
		
		
		

		<div id="colorlib-services">
			<div class="container">
				<div class="row">
					 <c:forEach  items="${moduledtos}" var="dto" >  
					<div class="col-md-3 text-center animate-box">
						<div class="services">
							<span class="icon">
								<i class="flaticon-books"></i>
							</span>
							<div class="desc">
								<h3><a href="javascript:showModuleDetails('${dto.module.moduleName}')">${dto.module.moduleName}</a></h3>
								<p>${dto.module.moduleDescription}</p>
							</div>
						</div>
					</div>
					</c:forEach>
			</div>
		</div>
		
		

		

		
	</div>
	
	<!-- Modules info Popup Modal to display-->
        <div class="modal fade recentcoursespopup" id="myModalModules" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content" style="padding: 10px;display: inline-block;width: 100%;">
		      <button type="button" class="close"  onClick="hideDialog()" id="closeModulesWin">&times;</button> 
                    <div  id="modalbodyid">
						
			
			
                    </div>
                    <div class="modal-footer" style="margin-top: 10px;float: left;width: 100%;">
		    
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
		<!--End Module popup -->
		<!-- Video popup -->
		<div class="modal fade" id="myModalvideo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <iframe id="iframeYoutube" width="560" height="315"  src="" frameborder="0" allowfullscreen></iframe> 
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
		<!-- End Video popup -->

	<div class="gototop js-top">
		<a href="#" class="js-gotop"><i class="icon-arrow-up2"></i></a>
	</div>
	
	<script>
		$(document).ready(function () {
				$("#myModalvideo").on("hidden.bs.modal", function () {
					$("#iframeYoutube").attr("src", "#");
				})
			})
	
	$("#myModalModules").modal("hide");
	
		function changeVideo(vId) {
				var iframe = document.getElementById("iframeYoutube");
				iframe.src = "https://www.youtube.com/embed/yUXGIbE0Uu4";
				
				$("#myModalvideo").modal("show");
				
			}
	
		function hideDialog(){
			  $("#myModalModules").modal("hide");
			}
	
		function showModuleDetails(mname){
			mname = encodeURIComponent(mname);
				$.get("fetchModuleData?mname="+mname, function(data, status){
				 //  console.log(data);
				$("#modalbodyid").empty();
				$("#modalbodyid").append(data);
					$("#myModalModules").modal("show");
				}); 
			}
			
			function startTest(testUrl){
				window.open(testUrl);
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

