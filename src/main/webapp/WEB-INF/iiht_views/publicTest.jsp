<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>IIHT</title>
        <link href='http://fonts.googleapis.com/css?family=Roboto:300,400,700' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Muli:300,400,700' rel='stylesheet' type='text/css'>
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="css/font-awesome.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="css/bootstrap_only_login_new.css">
        <link href="css/responsive.css" rel="stylesheet" type="text/css">
         <link href="css/font-awesome_new.css" rel="stylesheet" type="text/css">
         <link href="css/style_new.css" rel="stylesheet" type="text/css">
        <link href="css/responsive_new.css" rel="stylesheet" type="text/css">
        
        
       
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/custom.js"></script>

	<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>

<script type="text/javascript" src="scripts/pnotify.nonblock.js"></script>
<script type="text/javascript" src="scripts/pnotify.buttons.js"></script>
<style>
    .subbutton{
	background-color:#0082DC;
    	color:#fff;
    	border:none;
    	padding: 8px 20px;
   	 border-radius:4px;
	}
	
	.userform input[type=button] {
    -webkit-appearance: button;
    cursor: pointer;
    text-align: center;
    margin: 0 auto;
    width: 100px;
    float: none;
    display: block;
    color: #fff;
}

</style>


<%
    String cid=request.getParameter("companyId");
%>



<link href="css/pnotify.custom.min.css" media="all" rel="stylesheet" type="text/css">
			<script>
			function clickform(){
				var email = document.getElementById("username").value;
				var firstname = document.getElementById("firstName").value;
				var lastname = document.getElementById("lastName").value;
					if(!email || 0 === email.length){
						notify('Enter a valid email id');
						return;
					}
					
					if(!firstname || 0 === firstname.length){
						notify('First Name is mandatory');
						return;
					}
					
					if(!lastname || 0 === lastname.length){
						notify('Last Name is mandatory');
						return;
					}
				
				var companyId = '<%= request.getParameter("companyId") %>';
				var testName = document.getElementById("testName").value;
				var userDetails = {};
				userDetails.user = email;
				userDetails.testName = testName;
				userDetails.companyId = companyId;
				var url = "getotpfortest";
					$.ajax({
							url : url,
							type: "POST",
							//dataType: 'json',
							contentType: 'application/json',
							data: JSON.stringify(userDetails),
							//processData: false,
							success : function(data) {
								console.log("SUCCESS: ", data);
								if(data == "success"){
									document.getElementById("verify_otp").style.display = "";
									document.getElementById("login_otp").style.display = "none";
									document.getElementById("otpLabel").style.display = "";
									document.getElementById("otpLabelPass").style.display = "";
									notify('Check your inbox for OTP');
									//otpLabel
								}
								else{
									notify("Check your details. OTP generation failed. Try again or contact Test Admin");
								}
								
								
								//document.getElementById("no-"+sectionName).innerHTML = data;
								
							},
							error : function(e) {
								console.log("ERROR: ", e);
								
							}
						});	
					}
					
			function verifyOtp(){
				var email = encodeURIComponent(document.getElementById("username").value);
				var companyId = encodeURIComponent('<%= request.getParameter("companyId") %>');
				var testName = encodeURIComponent(document.getElementById("testName").value);
				var otp = document.getElementById("otpLabelPass").value;
				var firstname = document.getElementById("firstName").value;
				var lastname = document.getElementById("lastName").value;
				
					if(!email || 0 === email.length){
						notify('Enter a valid email id');
						return;
					}
					
					if(!firstname || 0 === firstname.length){
						notify('First Name is mandatory');
						return;
					}
					
					if(!lastname || 0 === lastname.length){
						notify('Last Name is mandatory');
						return;
					}
					if(!otp || 0 === otp.length){
						notify('OTP is mandatory');
						return;
					}
				
				var url = "validateotpfortest?otp="+otp+"&email="+email+"&companyId="+companyId+"&test="+testName;
						console.log('here url '+url);
						$.ajax({
						url : url,
						success : function(data) {
							console.log("SUCCESS: ", data);
							if(data == "success"){
								document.getElementById("verify_otp").style.display = "none";
									document.getElementById("login_otp").style.display = "none";
									document.getElementById("otpLabel").style.display = "none";
									document.getElementById("otpLabelPass").style.display = "none";
									document.getElementById("submitFormButton").style.display = "";
									notify('OTP validation successful! Now click to start test.');
							}
							else{
								notify("Invalid OTP Entered. Either enter a correct OTP or refresh the page and click to generate a new OTP");
							}
							
							
							//document.getElementById("no-"+sectionName).innerHTML = data;
							
						},
						error : function(e) {
							console.log("ERROR: ", e);
							
						}
					});	
			}
			</script>
    </head>
    <body>
	<div id="myModal" data-keyboard="false">
        <div class="header">
            <div class="col-md-12">
                <div class="col-md-6">
                    <div class="logo">
                        <a href="#"><img src="images/logoiiht.png"></a>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="userheader headerinfos">
                        <ul>
                            <li><i class="fa fa-envelope"></i>reachus@iiht.com</li>
                            <li><i class="fa fa-phone"></i>1800-123-321-5</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>


        <div class="creationtimeline">
            <div class="col-md-12">
                <div class="col-md-6 text-center">
                    <img src="images/creationtimeline.png">
                    <div class="col-md-12">
                        <div class="creationcontent">
                            <h3>Yaksha is ready to ask what you already know best!</h3>
                            <h2>Fillout data in the fields to your right. Lock, Stock, and Crack.
                           	    Employers want Air Worthy Employees. Yaksha is here to Engage,
  				    Evaluate & Enable you for it.</h2>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="starttestinfo loginformnew">
                        <h3>Sign In</h3>
                         <form name="testloginform" class="userform" id="testloginform" method="post" modelAttribute="testUserData" action="publicTestAuthenticate">
                          <form:hidden path="testUserData.testId" />
				 			<form:hidden path="testUserData.user.companyName" />
				 			<form:hidden path="testUserData.user.companyId" />	
							<form:hidden path="testUserData.startTime" value="${startTime}" />
							<form:hidden path="testUserData.endTime" value="${endTime}" />
                            <label>User Name</label>
                         <form:input  type="email" path="testUserData.user.email" name="email" id="username"  placeholder="Email" required="true"/>
                            <label>First Name</label>
						 <form:input path="testUserData.user.firstName" name="firstName" id="firstName"  placeholder="First Name" required="true"/>
                            <label>Last Name</label>
                         <form:input path="testUserData.user.lastName" name="lastName" id="lastName"  placeholder="Last Name" required="true"/>
                            <label>Test Name</label>
                       	 <form:input path="testUserData.testName" name="testName" id="testName"   required="true" disabled="true"/>
						 
						  <label id="otpLabel" style="display:none">Enter OTP</label>
                       	 <form:input path="testUserData.user.password" name="testName" id="otpLabelPass" style="display:none"/>
                          
                            <label>Company</label>
                             <form:input path="testUserData.user.companyName" name="companyName" id="companyName"  disabled="true" />
                           <input id="submitFormButton" type="button" class="subbutton" onclick="checkMandatoryAndSubmit()" value="SIGN IN" > 
							<a href="javascript:clickform();" id="login_otp" class="btn btn-secondary" style="display:none">Get OTP</a> 
							<a href="javascript:verifyOtp();" id="verify_otp" class="btn btn-secondary" style="display:none">Verify OTP</a> 
                        </form>
                    </div>
                </div>
            </div>
        </div>

         <div class="loginservices">
            <div class="col-md-12">
                <div class="item col-md-3">
                  <h3> <img src="images/serviceicon1.png">
                    Instructions</h3>


       		    <p>&#8226;  Test Results will be sent to you on Completion</p>
		    <p>&#8226; Click Submit for Submission of your Test </p>
		    <p>&#8226; System will auto Submit Test if Timer Expires	 </p>
                </div>
                <div class="item col-md-3">
                    <h3> <img src="images/serviceicon4.png">
                    Web Proctoring</h3>
                    <p><p>&#8226; Do not move mouse pointer to a different tab  </p>
		   <p>&#8226; Use F11 windows for Test if required   </p>
		   <p>&#8226; Non Compliance can result in your Test Declared Invalid   </p>
                </div>
                <div class="item col-md-3">
                   <h3> <img src="images/serviceicon3.png">
                    Tenants</h3>
                    <p>&#8226; Domain specific Users are advise to login using Corporate Credentials  </p>
		   <p>&#8226; Every User is directed to provide Login data for Individual Reporting   </p>
                </div>
                <div class="item col-md-3">
                    <h3> <img src="images/serviceicon2.png">
                    Yaksha</h3>
		          <p>&#8226; Multi Technology Assessments </p>
		          <p>&#8226; Test Cases Based Evaluation </p>
		          <p>&#8226; Weighted Adaptive Assessments	</p>
			
                </div>
            </div>
        </div>

        <div class="logincopyright">
            <div class="col-md-12">
                <p>Copyrigh Ã‚Â© 2018 IIHT. All Rights Reserved Ã¢â‚¬â€œ Privacy Policy For enterprise solutions</p>
            </div>
        </div>
		
	</div>

        <!-- <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script> -->
		<script>

		function checkMandatoryAndSubmit(){
				var email = document.getElementById("username").value;
				var firstname = document.getElementById("firstName").value;
				var lastname = document.getElementById("lastName").value;
					if(!email || 0 === email.length){
						notify('Enter a valid email id');
						return;
					}
					
					if(!firstname || 0 === firstname.length){
						notify('First Name is mandatory');
						return;
					}
					
					if(!lastname || 0 === lastname.length){
						notify('Last Name is mandatory');
						return;
					}
			
			fullScreen();
			$("#testloginform").submit();

		}


		function notify(text){
				 var notification = 'Information';
				 $(function(){
				 	new PNotify({
				 	title: notification,
					 text: text,
					 type: 'Information',
					 width: '20%',
					 hide: false,
					 buttons: {
            					closer: true,
            					sticker: false
       					 },
					 history: {
            					history: false
        				 }
					 });
				 
				 }); 	
			}
			
								// define a handler
			
			
			 function fullScreen(){
				 console.log('iner fullscreen called');
				// window.frames.resetCount();
				// window.frames.fullScreen();
				// parent.resetCount();  //enable it later for full screen to work
				 //parent.fullScreen(); //enable it later for full screen to work
            	// $("#cls").click()
            	// var elem = document.documentElement;
            	// if (elem.requestFullscreen) {
            	    // elem.requestFullscreen();
            	  // } else if (elem.mozRequestFullScreen) { /* Firefox */
            	    // elem.mozRequestFullScreen();
            	  // } else if (elem.webkitRequestFullscreen) { /* Chrome, Safari and Opera */
            	   // elem.webkitRequestFullscreen();
            	  // } else if (elem.msRequestFullscreen) { /* IE/Edge */
            	    // elem.msRequestFullscreen();
            	  // }
            	// } 
			}
			</script>

    </body>
</html>
