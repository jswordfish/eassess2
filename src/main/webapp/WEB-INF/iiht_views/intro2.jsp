<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="scripts/custom.js"></script>
 <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">

	<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>

<script type="text/javascript" src="scripts/pnotify.nonblock.js"></script>
<script type="text/javascript" src="scripts/pnotify.buttons.js"></script>
<style>
h1 {
text-align: center;
font-family: Tahoma, Arial, sans-serif;
color: #06D85F;
margin: 80px 0;
}
.box {
width: 40%;
margin: 0 auto;
background: rgba(255, 255, 255, 0.2);
padding: 35px;
border: 2px solid #fff;
border-radius: 20px/50px;
background-clip: padding-box;
text-align: center;
}
.button {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 8px 19px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 4px 2px;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
  cursor: pointer;
}
.button1 {
  background-color:#ffffff9e;
  color: black;
  border: 2px solid #6b696982;
  border-radius: 6px;
}
.button1:hover {
  background-color: #ffcf009e;
  color: white;
}
/* .button { */
/* font-size: 1em; */
/* padding: 10px; */
/* color: #fff; */
/* border: 2px solid #06D85F; */
/* border-radius: 20px/50px; */
/* text-decoration: none; */
/* cursor: pointer; */
/* transition: all 0.3s ease-out; */
/* } */
/* .button:hover { */
/* background-color: #ff9900; */
/* background: #06D85F; */
/* } */
.overlay {
position: fixed;
top: 0;
bottom: 0;
left: 0;
right: 0;
background: rgba(0, 0, 0, 0.7);
transition: opacity 500ms;
visibility: hidden;
opacity: 0;
}
.overlay:target {
visibility: visible;
opacity: 1;
}
.popup {
margin: 335px auto;
padding: 20px;
background-color: rgb(255,255,162);
/* background: #fff; */
border-radius: 5px;
width: 30%;
position: relative;
transition: all 5s ease-in-out;
}
.popup h2 {
margin-top: 0;
color: #333;
font-family: Tahoma, Arial, sans-serif;
}
.popup .close {
position: absolute;
top: 20px;
right: 30px;
transition: all 200ms;
font-size: 30px;
font-weight: bold;
text-decoration: none;
color: #333;
}
.popup .close:hover {
color: #06D85F;
}
.popup .content {
max-height: 30%;
overflow: auto;
}
@media screen and (max-width: 700px) {
.box {
width: 70%;
}
.popup {
width: 70%;
}
}
.modal {
                text-align: center;
                padding: 0!important;
            }
            .modal:before {
                content: '';
                display: inline-block;
                height: 100%;
                vertical-align: middle;
                margin-right: -4px;
            }
            .modal-dialog {
                display: inline-block;
                text-align: left;
                vertical-align: middle;
                background-color: #0066cc;
                border-radius: 10px;
            }
            .modal-dialog .modal-content{
                background-color: #fff;
                border: none;
                border-radius: 10px;
            }
            .modal-dialog .modal-content p{
                color: #333;
                padding: 18px;
            }
            .modal-dialog .modal-content .button{
                border: none;
		text-decoration: none;
                background-color: #1383d9;
                padding: 12px 25px;
                color: #fff;
                border-radius: 5px;
                font-weight: normal;
            }
            .modal-body{
                padding: 0;
                padding-bottom: 20px;
                border: 5px solid #1383d9;
                border-radius: 10px;
            }
            .modal-body .header{
                background-color: #1383d9;
                color: #fff;
                padding: 15px;
                font-weight: bold;
                
            }
</style>
<script>
 $(document).ready(function () {
              var stime = encodeURIComponent('${startTime}');
//               var stime = encodeURI('MTU3OTcxNzgwMDAwMA%3D%3D');
			var eTime = encodeURIComponent('${endTime}');
// 			var eTime = encodeURI('MTU4MzQzMzAwMDAwMA%3D%3D');
			var userId = encodeURIComponent('${userId}');
			console.log('stime is '+stime);
			console.log('etime is '+eTime);
			console.log('userId is '+userId);
			console.log('inviteSent is '+${inviteSent});
// 			var url = "startTestSession2?startDate="+stime+"&endDate="+eTime+"&inviteSent=${inviteSent}&userId="+userId+"&companyId=${companyId};
			var url = "startTestSession2?&testId=${testId}&companyId=${companyId}&startDate="+stime+"&endDate="+eTime+"&userId="+userId+"&inviteSent="+${inviteSent};
			console.log(url);
			$("#ur").attr("src", url);
             });
	   
 </script>
</head>
<body style="text-align: center;">

         <iframe src="" id="ur" height="850px" width="100%" scrolling="yes"></iframe>

<div class="box">
<a class="button" href="#popup1" id="clc" style="display: none;">Let
me Pop up</a>
</div>






<div id="popup1" class="overlay">
			<div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-body text-center">
                        <div class="header">
                            Safe Mode Only
                        </div>
                        <p>
                            Yaksha work's on full screen mode. Please click
full screen below and resume the test.                        </p>
                      <!--   <button onclick="fullScreen()">FULLSCREEN</button> -->
			<a href="#" onclick="fullScreen()" class="button" >Full Screen</a>
                    </div>
                </div>
            </div>


<script>
        function sleep(ms) {
         return new Promise(resolve => setTimeout(resolve, ms));
        }
                                        var count = 0;
	var fullscreenheight;
	var count = 0;
		$(window).on('resize',  function () {
			//alert('resize called');
			console.log('resize called '+count);
			if(count != 0){
				if(count % 2  != 0){
					console.log('popup open'+count);
					$("#clc")[0].click();
					//$("#myModal").show();
				}
				else{
				console.log(' no popup shown '+count);
				}
				
				
			}
			else{
				console.log(' no popup shown first time'+count);
			}
			count++;
			
		});
		
		function resetCount(){
			count = 0;
			console.log(' reset called'+count);
		}
             $(document).ready(function () {
                 $("#popup1").modal({
                     show: false,
                     backdrop: 'static'
                 });
             });
            function fullScreen(){
				
				console.log('outer fullscreen called '+count);
		
 		//$("#cls").click();
           // $("#popup1").modal('hide');
            var elem = document.documentElement;
            if (elem.requestFullscreen) {
               elem.requestFullscreen();
             } else if (elem.mozRequestFullScreen) { /* Firefox */
               elem.mozRequestFullScreen();
             } else if (elem.webkitRequestFullscreen) { /* Chrome,
Safari and Opera */
              elem.webkitRequestFullscreen();
             } else if (elem.msRequestFullscreen) { /* IE/Edge */
               elem.msRequestFullscreen();
             }
		//$('#popup1').css('display','none');
            }
            var elem = document.documentElement;
            function openFullscreen() {
              if (elem.requestFullscreen) {
                elem.requestFullscreen();
              } else if (elem.mozRequestFullScreen) { /* Firefox */
                elem.mozRequestFullScreen();
              } else if (elem.webkitRequestFullscreen) { /* Chrome,
Safari & Opera */
                elem.webkitRequestFullscreen();
              } else if (elem.msRequestFullscreen) { /* IE/Edge */
                elem.msRequestFullscreen();
              }
            }
            function closeFullScreen() {
                $("#clc").attr("id","cc");
              if (document.exitFullscreen) {
                document.exitFullscreen();
              } else if (document.mozCancelFullScreen) {
                document.mozCancelFullScreen();
              } else if (document.webkitExitFullscreen) {
                document.webkitExitFullscreen();
              } else if (document.msExitFullscreen) {
                document.msExitFullscreen();
              }
            }
        </script>
</body>
</html>