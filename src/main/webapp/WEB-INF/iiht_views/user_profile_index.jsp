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
<meta property="og:title" content="" />
<meta property="og:image" content="" />
<meta property="og:url" content="" />
<meta property="og:site_name" content="" />
<meta property="og:description" content="" />
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
#classModal {
	
}

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
/* body{ */
/* 	height:1000px; */
/* } */
</style>

</head>
<body>



	<div id="page">
		<nav class="colorlib-nav" role="navigation">
			<div class="upper-menu">
				<div class="container">
					<div class="row">
						<div class="col-xs-4">
							<p>
								<b>Welcome to eAssess</b>
							</p>
						</div>

					</div>
				</div>
			</div>
			<div class="top-menu">
				<div class="container">
					<div class="row">
						<div class="col-md-2">
							<div id="colorlib-logo">
								<a href="index.html">eAssess</a>
							</div>
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
				<div class="row">
					<c:forEach items="${licenses}" var="lic">
						<div class="col-md-3 text-center animate-box">
							<div class="services">
								<span class="icon"> <i class="flaticon-books"></i>
								</span>
								<div class="desc">
									<h3>
										<a href="javascript:showModulesForLicense('${lic.licenseName}')">${lic.licenseName}</a>
									</h3>
									<p>${lic.licenseDesc}</p>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>

		<div id="moduleshowid"></div>


		
		
		<div class="colorlib-classes colorlib-light-grey"  >		

			<div class="container">		
				<h2> Free Modules</h2>			
				<div class="row">		
				<c:forEach items="${freeModules}" var="freemod">
					<div class="col-md-3 animate-box">	
						<div class="classes">			
							<div class="classes-img" style="background-image: url(./resources/userprofile/images/elearning.jpg);">	
						
							<span class="price text-center"><small>${freemod.category == null?"Category NA":freemod.category}</small></span>	
							</div>		
							<div class="desc">			
								<h3><a href="javascript:showModule('${freemod.moduleName}')">${freemod.moduleName} (Click to Share)</a></h3>		
								<p>${freemod.moduleDescription}</p>	
								<p><a href="javascript:showItemsForFree('${freemod.moduleName}')" class="btn-learn">Click to Preview Items </a>
								</p>			
							</div>		
						</div>	
					</div>	
					</c:forEach>
				</div>	
			</div>
	</div>

		<!-- modal -->
		<div id="classModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="classInfo" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
						<h4 class="modal-title" id="classModalLabel">Share Module With Learners</h4>
					</div>
					<div class="modal-body">
						<div class="input-group">
							<span class="input-group-addon"> <a href="javascript:showFileDialog();" id="uploadUsers" style="width: 200px;">Click to Import Learners</a>
							</span> <input type="hidden" id="modName" name="modName" value="">

							<form id="fileFormUsers" method="POST" enctype="multipart/form-data">
								<input type="file" id="idusers" name="idusers" class="form-control" style="display: none">
							</form>

						</div>
						<div class="input-group">
							<span class="input-group-addon" style="width: 200px;"> <a href="javascript:loadUsersForClassifier();" id="uploadUsers">Click to Get Users</a></span>
							<form:select path="adminDto.classifierfull" style="class:form-control;margin-left: 30px;" id="classifierfull">

								<form:options items="${classifiers}" />
							</form:select>
						</div>
						<div id="usersTable">
							<!-- Dynamic table -->
						</div>
					</div>
					<div class="input-group">
						<span class="input-group-addon">Meeting ID</span> <input id="meetingid" type="text" class="form-control" name="msg" placeholder="Meeting ID (Optional)">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" onClick="javascript:shareModuleToStudents();">Share</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		<!-- end modal -->

		<!-- Module Items info Popup Modal to display-->
		<!-- Modules info Popup Modal to display-->
		<div class="modal fade recentcoursespopup" id="myModalModules" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content" style="padding: 10px; display: inline-block; width: 100%;">
					<button type="button" class="close" onClick="hideDialog()" id="closeModulesWin">&times;</button>
					<div id="modalbodyid"></div>
					<div class="modal-footer" style="margin-top: 10px; float: left; width: 100%;">

						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>
			</div>
		</div>
		<!--End Module popup -->
		<!--End Module popup -->

		<!-- Video popup -->
		<div class="modal fade" id="myModalvideo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-body">
						<iframe id="iframeYoutube" width="560" height="315" src="" frameborder="0" allowfullscreen></iframe>
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
			$(document).ready(function() {
				$("#myModalvideo").on("hidden.bs.modal", function() {
					$("#iframeYoutube").attr("src", "#");
				})
			})

			$("#myModalModules").modal("hide");

			function shareModuleToStudents() {
				var e = document.getElementById("classifierfull");
				var classifier = e.options[e.selectedIndex].value;
				classifier = encodeURIComponent(classifier);
				var moduleName = document.getElementById('modName').value;
				moduleName = encodeURIComponent(moduleName);
				//classifierfull
				var meetingid = document.getElementById("meetingid").value;
				var moduleId = '8799';
				var url = '';
				if (meetingid == null) {
					url = 'shareModule?&moduleName=' + moduleName
							+ '&classifier=' + classifier;
				} else {
					meetingid = encodeURIComponent(meetingid);
					url = 'shareModule?&moduleName=' + moduleName
							+ '&classifier=' + classifier + '&meetingid='
							+ meetingid;
				}

				$
						.ajax({
							xhr : function() {
								var xhr = new window.XMLHttpRequest();

								return xhr;
							},
							url : url,
							type : "GET",
							processData : false,
							contentType : false
						})
						.done(
								function(data) {
									if (data == 'ok') {
										notify('Success',
												'Module shared by email with users');
									} else {
										notify('Info',
												'Problem in Sharing the module');
									}
									$('#classModal').modal('hide');

								})
						.fail(
								function(jqXHR, textStatus) {
									notify('Failure',
											'File Upload Failed. Please contact Administrator');
									$('#classModal').modal('hide');
								});

			}

			function showModulesForLicense(licname) {
				licname = encodeURIComponent(licname);

				$.get("fetchModulesForAdminUserByLicense?licname=" + licname,
						function(data, status) {
							//  console.log(data);
							//$("#moduleshowid").empty();
							//$("#moduleshowid").append(data);
							//document.getElementById("moduleshowid").style.display = "";

							var div = document.getElementById('moduleshowid');
							div.innerHTML = "";
							div.innerHTML = data;
							div.style.display = "block";

						});
				$('#page').animate({
					scrollTop : 2000
				});
			}

			function showModule(modName) {
				modName = encodeURIComponent(modName);
				$('#classModal').modal('show')
				document.getElementById('modName').value = modName;
			}
			
			function showItemsForFree(modName) {
				modName = encodeURIComponent(modName);
				$.get("fetchModuleDataForPreviewForAdmin?mname=" + modName,
						function(data, status) {
							//  console.log(data);
							$("#modalbodyid").empty();
							$("#modalbodyid").append(data);
							$("#myModalModules").modal("show");
						});
			}

			function showItems(encModName) {
				$.get("fetchModuleDataForPreviewForAdmin?mname=" + encModName,
						function(data, status) {
							//  console.log(data);
							$("#modalbodyid").empty();
							$("#modalbodyid").append(data);
							$("#myModalModules").modal("show");
						});
			}

			function hideDialog() {
				$("#myModalModules").modal("hide");
			}

			function startTest(testUrl) {
				window.open(testUrl);
			}

			function showFileDialog() {
				$("#idusers").click();
			}

			var isXlsx = function(name) {
				return name.match(/xls|xlsx$/i)
			};

			$(document)
					.ready(
							function() {
								//document.getElementById("icsemodules").style.display = "block";
								var file = $('[name="idusers"]');
								var fileU = document.getElementById('idusers');
								fileU
										.addEventListener(
												"change",
												function() {
													if (fileU.files.length > 0) {
														var filename = $
																.trim(file
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
																			url : 'uploadLMSUsers',
																			type : "POST",
																			data : new FormData(
																					document
																							.getElementById("fileFormUsers")),
																			enctype : 'multipart/form-data',
																			processData : false,
																			contentType : false
																		})
																.done(
																		function(
																				data) {
																			if (data == 'ok') {
																				notify(
																						'Success',
																						'File Upload Successful. Sign off and Sign in again in case you dont find the institution - Grade - Division in the Get Users dropdown');
																			} else {
																				notify(
																						'Info',
																						'Problem in File Upload');
																			}
																			document
																					.getElementById('idusers').value = null;

																		})
																.fail(
																		function(
																				jqXHR,
																				textStatus) {
																			notify(
																					'Failure',
																					'File Upload Failed. Please contact Administrator');
																		});

													}
												});

							});

			function loadUsersForClassifier() {
				//notify('Info', 'Fetching Learners Data. Be patient');
				var e = document.getElementById("classifierfull");
				var classifier = e.options[e.selectedIndex].value;
				classifier = encodeURIComponent(classifier);
				$.ajax({
					xhr : function() {
						var xhr = new window.XMLHttpRequest();

						return xhr;
					},
					url : 'getHTMLTableForClassfier?classifier=' + classifier,
					type : "GET",
					processData : false,
					contentType : false
				}).done(function(data) {
					document.getElementById("usersTable").innerHTML = data;
					//notify('Info', 'Data fetched');

				}).fail(function(jqXHR, textStatus) {
					notify('Failure', 'Problem in fetching users data');
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

