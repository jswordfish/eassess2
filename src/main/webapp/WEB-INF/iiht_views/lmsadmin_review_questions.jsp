<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*" %>
<html>
	<head>
	<meta charset="utf-8">
	<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
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
			<h3> Verify Answers</h3>
				<table class="table">
				<thead>
				  <tr>
				  <th>First Name</th>
					<th>Last Name </th>
					<th>Question Text</th>
					<th>Date & Time</th>
					<th>Test Name</th>
					<th>Checked Earlier</th>
					<th>Check this out</th>
					
				  </tr>
				</thead>
				<tbody>
					 <c:forEach  items="${answers}" var="ans" >  
					<tr class="${ans.style}">
						<td>${firstName}</td>
						
						<td>${lastName}</td>
						<td><textarea class="form-control" rows="3" style="width:500px"  readonly >${ans.questionText}</textarea></td>
						<td>${ans.timeOfAnswer}</td>
						<td>${ans.testName}</td>
						<td>${ans.markComplete != null && ans.markComplete == true? "Checked":"Not Checked"}</td>

						<td><a href="javascript:reviewAnswer('${ans.questionText}', '${ans.testName}', '${ans.id}', '${ans.subjectiveText}', '${ans.imageUploadUrl}', '${ans.videoUploadUrl}',  '${ans.questionMapper.question.type}');">Click to Assess</a></td>
					  </tr> 
					</c:forEach>
				</tbody>
				</table>
				
				<button type="button" class="btn btn-primary"  onClick="javascript:markTestAsComplete();">
          Mark Test As Assessed
        </button>
		
			<button type="button" class="btn btn-primary"  onClick="javascript:cancelShowManualReviewQuestions();">
          Cancel
        </button>
			</div>
			
		
			
		</div>

		
		
	
	
	

</div>
	
	<!--start modal popup -->
	<!-- modal -->	
		<div id="classModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="classInfo" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
          X
        </button>
        <h4 class="modal-title" id="classModalLabel">
             Mark Answer
            </h4>
      </div>
		<div class="modal-body">
			<p> Question Text </p>
			<input type="hidden" id="ansid" name = "ansid" />
			<input type="hidden" id="imageUrl" name = "imageUrl" />
			<input type="hidden" id="videoUrl" name = "videoUrl" />
			
			
			<textarea id="questionText" class="form-control" rows="5" style="width:800px"  readonly ></textarea>
			
			
			<a id="imagePopupid" href="javascript:openVideoPopup('imagetype')" style="display:none"> Click to see Image Uploaded by Learner </a>
			<a id="videoPopupid" href="javascript:openVideoPopup('videotype')" style="display:none"> Click to see Video Uploaded by Learner </a>
			<textarea id="answerText" class="form-control" rows="5" style="width:800px;margin-top:25px;margin-bottom:25px;"  readonly ></textarea>
			
			<textarea id="suggestionText" class="form-control" rows="3" style="width:800px;margin-top:25px;margin-bottom:25px;" placeholder="Enter your Comments as Reviewer" ></textarea>
		
				<div style="width:100%;">
				
				<input type="radio" id="fullMarks" style="margin-left:25px;" 
				 name="marks" value="100 % Correct">
				<label for="fullMarks">100 % Correct</label>

				<input type="radio" id="threefourMarks" style="margin-left:25px;" 
				 name="marks" value="75 % Correct">
				<label for="threefourMarks">75 % Correct</label>

				<input type="radio" id="halfMarks" style="margin-left:25px;" 
				 name="marks" value="50 % Correct">
				<label for="halfMarks">50 % Correct</label>
				
				<input type="radio" id="onefourthMarks" style="margin-left:25px;" 
				 name="marks" value="25 % Correct">
				<label for="onefourthMarks">25 % Correct</label>
				
				<input type="radio" id="zeroMarks" style="margin-left:25px;" 
				 name="marks" value="No Marks">
				<label for="zeroMarks">No Marks</label>
			  </div>
		</div>
      
	  
      <div class="modal-footer">
		
		
		<button type="button" class="btn btn-primary"  onClick="javascript:markAnswer();">
          Assign Marks
        </button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">
          Close
        </button>
      </div>
    </div>
  </div>
</div>
	<!-- end modal -->	
	
	<!-- end modal popup -->
	
	
	
	<!-- Video popup it will open image as well -->
		<div class="modal fade" id="myModalvideo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document" style="width:800px">
                <div class="modal-content">
                    <div class="modal-body">
                        <iframe id="iframeYoutube" width="760" height="515"  src="" frameborder="0" allowfullscreen></iframe> 
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
	
	
	
	function openVideoPopup(type){
	var url = '';
			if(type == 'imagetype'){
				url = document.getElementById("imageUrl").value;
			}
			else{
				url = document.getElementById("videoUrl").value;
			}
		
		window.open(url, '_blank', 'location=yes,height=700,width=1400,scrollbars=auto,status=yes');
		
		//var iframe = document.getElementById("iframeYoutube");
		//var url1 = 'https://www.google.ie/gwt/x?u='+url;
		//iframe.src = url1;
				
		//$("#myModalvideo").modal("show");
	}
	
	function reviewAnswer(questionText, testName, ansid, answer, imageurl, videourl, type){
		document.getElementById("questionText").value = questionText;
		document.getElementById("classModalLabel").innerHTML  = ' Mark Answer for Test: '+testName;
		document.getElementById("ansid").value = ansid;
		
			if(type == 'IMAGE_UPLOAD_BY_USER'){
				document.getElementById("answerText").style.display = "none";
				document.getElementById("imagePopupid").style.display = "";
				document.getElementById("videoPopupid").style.display = "none";
				document.getElementById("imageUrl").value = imageurl;
				
			}
			else if(type == 'VIDEO_UPLOAD_BY_USER'){
				document.getElementById("answerText").style.display = "none";
				document.getElementById("imagePopupid").style.display = "none";
				document.getElementById("videoPopupid").style.display = "";
				document.getElementById("videoUrl").value = videourl;
			}
			else if(type == 'SUBJECTIVE_TEXT'){
				document.getElementById("answerText").style.display = "";
				document.getElementById("imagePopupid").style.display = "none";
				document.getElementById("videoPopupid").style.display = "none";
				document.getElementById("answerText").value = answer;
			}
		
		
		
		$('#classModal').modal('show');
	}
	
		function cancelShowManualReviewQuestions(){
			window.location = "cancelShowManualReviewQuestions";
		}
		
		function markAnswer(){
			var suggText = document.getElementById("suggestionText").value;
			suggText = encodeURIComponent(suggText);
			
			var id = document.getElementById("ansid").value;
			
			var ele = document.getElementsByName('marks'); 
            checked = false;
			markstr = "";
            for(i = 0; i < ele.length; i++) { 
                if(ele[i].checked) {
					markstr  = ele[i].value;
					markstr = encodeURIComponent(markstr);
					checked = true;
					break;
				}
               
            } 
			
			if(!checked){
				notify('Information', 'Select Marks for the answer!');
			}
			else{
				//id
				$.get("markAnswer?ansid="+id+"&suggestion="+suggText+"&marks="+markstr, function(data, status){
				 //  console.log(data);
					if(data == 'ok'){
						notify('Information', 'Marks Assigned');
						$('#classModal').modal('hide');
						var url = window.location;
						window.location.reload();
					}
					else{
						notify('Information', 'Problem in assigning Marks. Contact Admin');
						$('#classModal').modal('hide');
					}
				}); 
			}
		}
		
		function markTestAsComplete(){

			var sessid = '${sessionId}';
			
			
			window.location = 'markTestAsComplete?userSessionId='+sessid;
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

