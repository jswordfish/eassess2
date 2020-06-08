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


<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="scripts/custom.js"></script>
<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>

<script type="text/javascript" src="scripts/pnotify.nonblock.js"></script>
<script type="text/javascript" src="scripts/pnotify.buttons.js"></script>

<link href="css/pnotify.custom.min.css" media="all" rel="stylesheet" type="text/css">
<script type="text/javascript" src="scripts/html2canvas.js"></script>
<script src="scripts/src-min-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>

	<a id="gift-close" href="javascript:fullScreen();"><iclass="fa fa-flag-checkered"></i>full screen</a>


	
	<div class="mainwrapper">
		<!--header-->

		

		<tiles:insertAttribute name="body" />

		

	</div>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/mini-event-calendar.js"></script>
	<script src="js/script.js"></script>
	<script src="js/dashboard.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.13.0/moment.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>

<script>

	 
		function fullScreen(){
			
				console.log('in fullscreen');
				var elem = document.getElementById("bodyid");
				if (elem.requestFullscreen) {
					elem.requestFullscreen();
				  } else if (elem.mozRequestFullScreen) { /* Firefox */
					elem.mozRequestFullScreen();
				  } else if (elem.webkitRequestFullscreen) { /* Chrome, Safari and Opera */
				   elem.webkitRequestFullscreen();
				  } else if (elem.msRequestFullscreen) { /* IE/Edge */
					elem.msRequestFullscreen();
				  }
			}
	
		} 
</script>
	

</body>
</html>