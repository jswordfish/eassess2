<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*,com.assessment.web.dto.*"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>IIHT</title>
<link href='https://fonts.googleapis.com/css?family=Roboto:300,400,700'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Muli:300,400,700'
	rel='stylesheet' type='text/css'>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" type="text/css">
<link href="css/responsive.css" rel="stylesheet" type="text/css">
<link href="css/font-awesome_new.css" rel="stylesheet" type="text/css">
<link href="css/style_new.css" rel="stylesheet" type="text/css">
<link href="css/responsive_new.css" rel="stylesheet" type="text/css">
<link href="css/style_testjourney.css" rel="stylesheet" type="text/css">


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		 <script src="eAssess/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/5.4.0/bootbox.js"></script>


<script type="text/javascript" src="scripts/custom.js"></script>

<script type="text/javascript" src="scripts/html2canvas.js"></script>
<script src="scripts/src-min-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
<!--===============================================================================================-->

<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/vendor/animate/animate.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="contact/css/util.css">
	<link rel="stylesheet" type="text/css" href="contact/css/main.css">
<!--===============================================================================================-->
 <link rel="shortcut icon" href="eAssess/assets/img/ico/favicon.png">
        <link href='https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900' rel='stylesheet' type='text/css'>
        <!-- Material Icons CSS -->
        <link href="eAssess/assets/fonts/iconfont/material-icons.css" rel="stylesheet">
        <!-- owl.carousel -->
        <link href="eAssess/assets/owl.carousel/assets/owl.carousel.css" rel="stylesheet">
        <link href="eAssess/assets/owl.carousel/assets/owl.theme.default.min.css" rel="stylesheet">
        <!-- FontAwesome CSS -->
        <link href="eAssess/assets/fonts/font-awesome/css/font-awesome.min.css" rel="stylesheet">
        <!-- materialize -->
        <link href="eAssess/assets/materialize/css/materialize.min.css" rel="stylesheet">
        <!-- Bootstrap -->
        <link href="eAssess/assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <!-- shortcodes -->
        <link href="eAssess/assets/css/shortcodes/shortcodes.css" rel="stylesheet">
        <link href="eAssess/assets/css/shortcodes/login.css" rel="stylesheet">
        <!-- Style CSS -->
        <link href="eAssess/assets/css/style.css" rel="stylesheet">
		
<style>
ul {
  list-style: none; /* Remove default bullets */
}

ul li::before {
  content: "\2022";  /* Add content: \2022 is the CSS Code/unicode for a bullet */
  color: white; /* Change the color */
  font-weight: bold; /* If you want it to be bold */
  display: inline-block; /* Needed to add space between the bullet and the text */
  width: 1em; /* Also needed for space (tweak if needed) */
  margin-left: -1em; /* Also needed for space (tweak if needed) */
}
</style>
</head>
<body>
<!--header start-->
        <header id="header" class="tt-nav nav-border-bottom">

            <div class="header-sticky light-header ">

                <div class="container">
                    <div id="materialize-menu" class="menuzord">

                        <!--logo start-->
                        <a href="index.html" class="logo-brand">
                            <img class="retina" src="eAssess/images/Logo.png" alt=""/>
                        </a>
                        <!--logo end-->

                      
                        <!--mega menu end-->

                    </div>
                </div>
            </div>

        </header>
        <!--header end-->

	<div class="container-contact100">
		<div class="wrap-contact100" style="width:90%">
			 <form name="contactform" id="contactform" class="contact100-form validate-form" method="get"  action="login">
				<span class="contact100-form-title">
					<b>Send Us A Message </b>
				</span>

				<label class="label-input100" for="first-name"><b>Tell us your name *</b></label>
				<div class="wrap-input100 rs1-wrap-input100 validate-input" data-validate="Type first name">
					
					 <form:input path="prospect.firstName" name="firstName" 
									id="firstName"  required="true" placeholder="First Name" class="input100" />
					<span class="focus-input100"></span>
				</div>
				<div class="wrap-input100 rs2-wrap-input100 validate-input" data-validate="Type last name">
					<form:input path="prospect.lastName" name="lastName" 
									id="lastName"  required="true" placeholder="Last Name" class="input100" />
					<span class="focus-input100"></span>
				</div>

				<label class="label-input100" for="email">Enter your email *</label>
				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
					
					 <form:input path="prospect.email" name="email"
									id="email"  required="true"  placeholder="Eg. example@email.com" class="input100" />
					<span class="focus-input100"></span>
				</div>

				<label class="label-input100" for="phone">Enter phone number</label>
				<div class="wrap-input100">
					
					<form:input path="prospect.phone" name="phone" 
									id="phone"  required="true" placeholder="Eg. +1 800 000000" class="input100" />
					<span class="focus-input100"></span>
				</div>

				<label class="label-input100" for="message">Message *</label>
				<div class="wrap-input100 validate-input" data-validate = "Message is required">
					
					
					<form:textarea path="prospect.message" id="message" rows="5" cols="30" class="input100" placeholder="Write us a message" />
					<span class="focus-input100"></span>
				</div>

				<div class="container-contact100-form-btn">
					<button type="button" class="contact100-form-btn"  onClick="javascript:sendMessage();" >
						Send Message 
					</button>
					
					<button class="contact100-form-btn" style="background-color:red" onClick="javascript:goBack();">
						Go Back
					</button>
				</div>
				
			</form>

			<div class="contact100-more flex-col-c-m" style="background-image: url('contact/images/bg-01.jpg');">
				<div class="flex-w size1 p-b-47">
					<div class="txt1 p-r-25">
						<span class="lnr lnr-map-marker"></span>
					</div>

					<div class="flex-col size2">
						<span class="txt1 p-b-20">
							START YOUR FREE TRIAL NOW
						</span>

						<span class="txt2">
						
							<ul>
							  <li><b>Create MCQ/Descriptive/Coding Tests/Surveys as real life as can be</b></li>
							  <li><b>Share Modules, Tests & Meeting Links through a click</b></li>
							  <li><b>Separate Dashboards for Test Creators & Test Takers</b></li>
							  <li><b>Transparent Review System for Subjective Question based Tests</b> </li>
							  <li><b>Comprehensive Reports with detailed User Profile for each Test taker</b> </li>
							  <li><b>Deep Insights - Measure effectiveness of your Training Programs</b></li>
							 
							</ul>
							
						</span> 
							
						</span>
					</div>
				</div>

				<div class="dis-flex size1 p-b-47">
					<div class="txt1 p-r-25">
						<span class="lnr lnr-phone-handset"></span>
					</div>

					<div class="flex-col size2">
						<span class="txt1 p-b-20">
							Lets Talk
						</span>

						<span class="txt3">
							+91 9082634988
						</span>
					</div>
				</div>

				<div class="dis-flex size1 p-b-47">
					<div class="txt1 p-r-25">
						<span class="lnr lnr-envelope"></span>
					</div>

					<div class="flex-col size2">
						<span class="txt1 p-b-20">
							General Support
						</span>

						<span class="txt3">
							sales@eassess.in
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>



	 <footer class="footer footer-four">
            <div class="primary-footer brand-bg text-center">
                <div class="container">

                  

                  <hr class="mt-15">

                  <div class="row">
                    <div class="col-md-12 mt-20">
                      <div class="col-md-4">
                        <div class="footer-logo">
                            <img src="eAssess/images/Logo.png" alt="">
                          </div>
                      </div>
                      <div class="col-md-8">
                        <span class="copy-text">Copyright &copy; 2020 <a href="#">E-Assess</a> &nbsp; | &nbsp;  All Rights Reserved &nbsp;   </span>
                      </div>
                          
                    </div><!-- /.col-md-12 -->
                  </div><!-- /.row -->
                </div><!-- /.container -->
            </div><!-- /.primary-footer -->
        </footer>



	<script>
	/* function sendMessage(){
		document.getElementById("contactform").submit();
	} */
		function notify(msg){
			bootbox.alert({
				message: msg,
				size: 'large',
				backdrop: true
			});
		}	
	
		function sendMessage(){
	var data = {}; 
	data.email = document.getElementById('email').value;
	data.phone = document.getElementById('phone').value;
	data.firstName = document.getElementById('firstName').value;
	
	data.lastName = document.getElementById('lastName').value;
	data.message = document.getElementById('message').value;
	data.address = '';
	data.website = '';

	if(document.getElementById('email').value == null || document.getElementById('email').value.trim().length == 0 ){
			notify('Email can not be blank');
			return false;
		}
		
		if(document.getElementById('phone').value == null || document.getElementById('phone').value.trim().length == 0){
			notify('Phone can not be blank');
			return false;

		}
		
		if(document.getElementById('firstName').value == null || document.getElementById('firstName').value.trim().length == 0){
			notify('First Name can not be blank');
			return false;

		}
		
		if(document.getElementById('message').value == null  || document.getElementById('message').value.trim().length == 0){
			notify('Message can not be blank');
			return false;

		}
	dta = JSON.stringify(data);
		$.ajax({
				headers: {
					"Content-Type": "application/json; charset=utf-8"
					},
				type : 'POST',
				url : 'saveProspect',
				contentType: "application/json",
				data: dta,
				success : function(data) {
					console.log(data);
					//Your Message has been submitted. Now Click on Go Back button to go to Login Page
					bootbox.alert({
						message: "Your Message has been submitted. Now Click on Go Back button to go to Login Page",
						size: 'large',
						backdrop: true
					});
					//window.location = "login";
					return true;
					
				},
				error : function(e) {
						console.log(e);
						bootbox.alert({
							message: "It seems there has been a problem in sending a message. Please write to sales@eassess.in",
							size: 'large',
							backdrop: true
						});
						window.location = "login";		
						return true;
				}
			});
		}
		
		function goBack(){
			window.location.replace('login')	
		}
		
		function custom_alert( message, title ) {
			if ( !title )
				title = 'Alert';

			if ( !message )
				message = 'No Message to Display.';

			$('<div></div>').html( message ).dialog({
				title: title,
				resizable: false,
				modal: true,
				buttons: {
					'Ok': function()  {
						$( this ).dialog( 'close' );
					}
				}
			});
		}
	</script>


</body>
</html>
