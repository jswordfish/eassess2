<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en" style="position: fixed;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
<!--  Start from here -->
<spring:url value="/resources/css/mystyle.css" var="mainCss" />

<spring:url value="/resources/assets/img/ico/favicon.png" var="c1" />
<link href="${c1}" rel="shortcut icon" />
<spring:url
	value="/resources/assets/fonts/font-awesome/css/font-awesome.min.css"
	var="c2" />
<link href="${c2}" rel="stylesheet" type='text/css' />

<spring:url
	value="/resources/assets/fonts/font-awesome/css/font-awesome.min.css"
	var="c3" />
<link href="${c3}" rel="stylesheet" type='text/css' />
<spring:url
	value="/resources/assets/materialize/css/materialize.min.css" var="c4" />
<link href="${c4}" rel="stylesheet" type='text/css' />
<spring:url value="/resources/assets/bootstrap/css/bootstrap.min.css"
	var="c5" />
<link href="${c5}" rel="stylesheet" type='text/css' />
<spring:url value="/resources/assets/css/shortcodes/login.css" var="c6" />
<link href="${c6}" rel="stylesheet" type='text/css' />
<spring:url value="/resources/assets/style.css" var="c7" />
<link href="${c7}" rel="stylesheet" type='text/css' />
<spring:url value="/resources/assets/css/pnotify.custom.min.css"
	var="c16" />
<link href="${c16}" rel="stylesheet" type="text/css" />

</head>
<body>
	 <div class="col-md-12 col-sm-12 col-xs-12 text-center">

		<section
			style="height: 100% !important; margin: 0px; background-image: url('<%=request.getContextPath()%>/resources/images/loginbg2.jpg');">
			<img src="<%=request.getContextPath()%>/resources/images/Logo.png"
				style="margin-top: -84px;">
			<div class="container">
				<div class="login-wrapper">
					<div class="card-wrapper"></div>
					<div class="card-wrapper">
						<h1 class="title">Login</h1>
						<a href="/AssesmentApp/OnetPage">Assessment Profiler</a>
						
						<form name="userloginform" class="userform" method="post"
							modelAttribute="user" action="authenticate">
							<div class="input-container">
								<form:input type="email" path="user.email" name="email"
									id="username" cssClass="form-control" required="true" />
								<label for="username">Username</label>
								<div class="bar"></div>
							</div>
							<div class="input-container">
								<form:password path="user.password" name="password"
									id="password" cssClass="form-control" required="true" />
								<label for="password">Password</label>
								<div class="bar"></div>
							</div>
							<div class="input-container">
								<form:input path="user.companyName" name="companyName"
									id="companyName" cssClass="form-control" required="true" />
								<label for="Company">Company</label>
								<div class="bar"></div>
							</div>
							<div class="button-container">
								<button type="submit" name="submit"
									class="btn-block waves-effect waves-light btn">Login</button>

							</div>
							<div class="footer">
								<a href="http://beforesubmit.com/qe-assess/login.html#">Forgot
									your password?</a>
							</div>
						</form>
						
					</div>
				</div>
			</div>

		</section>
	</div>
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
	<script type="text/javascript">
		function myFunction() {
			var x = document.getElementById("userpassword");
			if (x.type === "password") {
				x.type = "text";
			} else {
				x.type = "password";
			}
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