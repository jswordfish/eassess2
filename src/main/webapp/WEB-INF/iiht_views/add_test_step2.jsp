<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page
	import="com.assessment.data.*, java.text.*, java.util.*, com.assessment.web.dto.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Add Test</title>

<spring:url value="/resources/assets/img/ico/favicon.png" var="c1" />

<link href="${c1}" rel="shortcut icon" />

<spring:url
	value="https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900" var="c2" />

<link href="${c2}" rel="stylesheet" type="text/css" />

<!-- Material Icons CSS -->
<spring:url value="/resources/assets/fonts/iconfont/material-icons.css" var="c3" />

<link href="${c3}" rel="stylesheet" type="text/css" />

<!-- FontAwesome CSS -->
<spring:url value="/resources/assets/fonts/font-awesome/css/font-awesome.min.css"
	var="c4" />

<link href="${c4}" rel="stylesheet" type="text/css" />

<!-- magnific-popup -->
<spring:url value="/resources/assets/magnific-popup/magnific-popup.css" var="c5" />

<link href="${c5}" rel="stylesheet" type="text/css" />

<!-- owl.carousel -->
<spring:url value="/resources/assets/owl.carousel/assets/owl.carousel.css" var="c6" />

<link href="${c6}" rel="stylesheet" type="text/css" />

<spring:url value="/resources/assets/owl.carousel/assets/owl.theme.default.min.css"
	var="c7" />

<link href="${c7}" rel="stylesheet" type="text/css" />
<!-- flexslider -->
<spring:url value="/resources/assets/flexSlider/flexslider.css" var="c8" />

<link href="${c8}" rel="stylesheet" type="text/css" />

<!-- materialize -->
<spring:url value="/resources/assets/materialize/css/materialize.min.css" var="c9" />

<link href="${c9}" rel="stylesheet" type="text/css" />

<!-- Bootstrap -->
<spring:url value="/resources/assets/bootstrap/css/bootstrap.min.css" var="c10" />

<link href="${c10}" rel="stylesheet" type="text/css" />

<!-- shortcodes -->
<spring:url value="/resources/assets/css/shortcodes/shortcodes.css" var="c11" />

<link href="${c11}" rel="stylesheet" type="text/css" />

<!-- Style CSS -->
<spring:url value="/resources/assets/style.css" var="c12" />

<link href="${c12}" rel="stylesheet" type="text/css" />

<!-- RS5.0 Main Stylesheet -->
<spring:url value="/resources/assets/revolution/css/settings.css" var="c13" />

<link href="${c13}" rel="stylesheet" type="text/css" />

<!-- RS5.0 Layers and Navigation Styles -->
<spring:url value="/resources/assets/revolution/css/layers.css" var="c14" />

<link href="${c14}" rel="stylesheet" type="text/css" />
<spring:url value="/resources/assets/revolution/css/navigation.css" var="c15" />

<link href="${c15}" rel="stylesheet" type="text/css" />
<spring:url value="/resources/assets/css/pnotify.custom.min.css" var="c16" />

<!-- jQuery -->

<spring:url value="/resources/assets/js/jquery-2.1.3.min.js" var="mainJs1" />
<script src="${mainJs1}"></script>
<spring:url value="/resources/assets/bootstrap/js/bootstrap.min.js" var="mainJs2" />
<script src="${mainJs2}"></script>
<spring:url value="/resources/assets/materialize/js/materialize.min.js" var="mainJs3" />
<script src="${mainJs3}"></script>
<spring:url value="/resources/assets/js/menuzord.js" var="mainJs4" />
<script src="${mainJs4}"></script>
<spring:url value="/resources/assets/js/bootstrap-tabcollapse.min.js" var="mainJs5" />
<script src="${mainJs5}"></script>
<spring:url value="/resources/assets/js/jquery.easing.min.js" var="mainJs6" />
<script src="${mainJs6}"></script>
<spring:url value="/resources/assets/js/jquery.sticky.min.js" var="mainJs7" />
<script src="${mainJs7}"></script>
<spring:url value="/resources/assets/js/smoothscroll.min.js" var="mainJs8" />
<script src="${mainJs8}"></script>
<spring:url value="/resources/assets/js/jquery.stellar.min.js" var="mainJs9" />
<script src="${mainJs9}"></script>
<spring:url value="/resources/assets/js/jquery.inview.min.js" var="mainJs10" />
<script src="${mainJs10}"></script>
<spring:url value="/resources/assets/owl.carousel/owl.carousel.min.js" var="mainJs11" />
<script src="${mainJs11}"></script>
<spring:url value="/resources/assets/flexSlider/jquery.flexslider-min.js"
	var="mainJs12" />
<script src="${mainJs12}"></script>
<spring:url value="/resources/assets/magnific-popup/jquery.magnific-popup.min.js"
	var="mainJs13" />
<script src="${mainJs13}"></script>
<spring:url value="https://maps.googleapis.com/maps/api/js" var="mainJs14" />
<script src="${mainJs14}"></script>
<spring:url value="/resources/assets/js/scripts.js" var="mainJs15" />
<script src="${mainJs15}"></script>
<spring:url value="/resources/assets/scripts/custom.js" var="mainJs16" />
<script src="${mainJs16}"></script>
<spring:url value="/resources/assets/scripts/pnotify.custom.min.js" var="mainJs17" />
<script src="${mainJs17}"></script>

<link href="${c16}" rel="stylesheet" type="text/css" />
<c:if test="${msgtype != null}">
	<script>
		var notification = 'Information';
		var mtp = "${msgtype}";
		var msg = "${message}";
		console.log(mtp + ' ' + msg);
		$(function() {
			new PNotify({
				title : notification,
				text : msg,
				type : mtp,
				styling : 'bootstrap3',
				hide : true
			});
		});
	</script>
</c:if>

<script>
	var point = false;
	var count = 0;
	function check(e, value) {

		//Check Charater
		debugger;
		if (count == 3)
			return false;
		var unicode = e.charCode ? e.charCode : e.keyCode;

		if (unicode == 46 && point == true)
			return false;
		if (unicode == 46 && point == false) {
			point = true;
		}
		if (unicode != 8)
			if ((unicode<48||unicode>57) && unicode != 46)
				return false;
		if (point == true)
			count++;
	}
	function checkLength() {
		var fieldVal = document.getElementById('txtF').value;
		//Suppose u want 3 number of character
		if (fieldVal <= 100) {
			return true;
		} else {
			var str = document.getElementById('txtF').value;
			str = str.substring(0, str.length - 1);
			document.getElementById('txtF').value = str;
		}
	}
</script>
</head>
<body id="top" class="has-header-search">

	<!--header start-->
	<header id="header" class="tt-nav nav-border-bottom">
		<div class="header-sticky light-header ">
			<div class="container">
				<div id="materialize-menu" class="menuzord menuzord-responsive">
					<a href="javascript:void(0)" class="showhide" style="display: none;"><em></em><em></em><em></em></a>
					<!--logo start-->
					<a href="javascript:void(0);" class="logo-brand"> <img class="retina"
						src="<%=request.getContextPath()%>/resources/images/Logo.png" alt="" />
					</a>
					<!--logo end-->
					<ul class="menuzord-menu pull-right">
						<li><a
							href="javascript:notify('Information', 'We will release the feature pretty soon! Please wait for our next release');">Dashboard</a></li>
						<li><a href="question_list">Question Bank</a></li>
						<li class="active"><a href="testlist">Tests</a></li>
						<li><a href="skills">Skills</a></li>
						<li><a href="showReports">Results</a></li>
						<li><a href="codingSessions">Code Analysis Reports</a></li>
						<li><a href="showSkillTags">Skill based Reports</a></li>
						<li><a href="showProfileParams">Recomm Setting</a></li>
						<li><a href="listUsers">Users</a></li>
					</ul>
					<!--mega menu end-->
				</div>
			</div>
		</div>
	</header>
	<!--header end-->


	<section>
		<div class="container">
			<div class="row no-gutter">
				<div class="col-md-2 mb-20">
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem">
							<span>1</span>
							<div class="intro-header">
								<i class="fa fa-cog"></i>
								<h2 class="white-text">Set Your Test</h2>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem active">
							<span>2</span>
							<div class="intro-header">
								<i class="fa fa-question-circle"></i>
								<h2 class="white-text">Add Questions</h2>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-6 text-center">
						<div class="stepsitem">
							<span>3</span>
							<div class="intro-header">
								<i class="fa fa-share-alt"></i>
								<h2 class="white-text">Share Test</h2>
							</div>
						</div>
					</div>
				</div>


				<div class="addteststeps2 col-md-10">
					<div class="col-md-12">
						<div class="step2sections">
							<label style="font-size: 20px; color: #333;">Sections</label>



							<c:forEach items="${test.sectionDtos}" var="section">
								<div class="quesectiondiv">
									<div class="quesection">

										${section.sectionName}- ${section.noOfQuestions} <a
											href="javascript:removeSection('${section.sectionName}');" id="delete"><i
											class="fa fa-trash"></i></a> <a id='update'
											onclick="highlight('${section.sectionName}');"><i class='fa fa-edit'></i></a>
									</div>
								</div>
							</c:forEach>
							<!--  new added end-->
							<!-- 																		Java Section <a id="delete"><i class="fa fa-trash"></i></a> <a id="update"><i -->
							<!-- 																				class="fa fa-edit"></i></a> -->



							<label class="addquestion"><span>+</span> Add Section </label>
						</div>


						<div class="col-md-12 text-right">
							<a href="testlist"
								class="waves-effect waves-light btn submit-button indigo mt-20 mb-20">Cancel</a>
							<a class="waves-effect waves-light btn cyan mt-20 mb-20"
								href="gobackStep1Test">Back</a> <a
								class="waves-effect waves-light btn mt-20 mb-20" href="addteststep3">Next</a>
						</div>
					</div>

				</div>
			</div>
		</div>
	</section>



	<footer class="footer footer-four">
		<div class="secondary-footer brand-bg darken-2 text-center">
			<div class="container">
				<ul>
					<li><a
						href="javascript:notify('Information', 'We will release the feature pretty soon! Pease wait for our next release');">Dashboard</a></li>
					<li><a href="question_list">Question Bank</a></li>
					<li><a href="testlist">Tests</a></li>
					<li><a href="javascript:void(0)">Skills</a></li>
					<li><a href="showReports">Results</a></li>
					<li><a href="javascript:void(0)">Code Analysis Reports</a></li>
					<li><a href="javascript:void(0)">Skill based Reports</a></li>
					<li><a href="listUsers">Users</a></li>
				</ul>
			</div>
		</div>
	</footer>



	<!-- Duplicate Test Popup -->
	<div id="modalsection" class="modal fade modalsection" role="dialog">
		<div class="modal-dialog" style="width: 900px;">
			<!-- Modal content-->
			<div class="modal-content" style="display: inline-block;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title">Add Section</h4>
				</div>
				<div class="modal-body">
					<div class="col-md-12 mb-20">
						<a href="addteststep2" class="btn waves-effect waves-light">Show All Parent
							Categories</a> <a href="javascript:showSelected();" id="showSelected"
							class="btn waves-effect waves-light green"> Show Selected</a>
						<!--  <a
														href="http://beforesubmit.com/qe-assess/addtest_step2.html#"
														class="btn waves-effect waves-light green">Show Selected</a>  -->
						<a href="removeAllQuestions" class="btn waves-effect waves-light red">Clear
							All</a>
						<!-- 														<a href="javascript:saveSection();" class="btn waves-effect waves-light">Save Section</a> -->
					</div>

					<div class="col-md-12 mb-20">
						<div class="col-md-3" style="padding: 0;">
							<div class="widget widget_search">
								<input type="text" class="form-control" id="sectionTopic"
									value="${sectionDto.sectionName}" placeholder="Enter Section Name">
							</div>
						</div>
						<div class="col-md-4">
							<div class="widget widget_search">

								<div class="search-form">
									<form action="searchQuestions2" method="get">
										<input type="text" placeholder="Search a question" name="searchText"
											id="searchText">
										<button type="submit" id="search">
											<i class="fa fa-search"></i>
										</button>
									</form>
								</div>
							</div>
						</div>
						<div class="col-md-9">
							<label style="font-size: 16px; color: #202020; font-weight: normal;"
								class="active">Percent of Qs to be asked - </label> <input type="number"
								onkeypress="return check(event, value)" oninput="checkLength()" id="txtF"
								placeholder="Enter percentage" style="width: 15%;" value="${sectionDto.percentQuestionsAsked}">
						</div>
					</div>

					<div class="col-md-12">
						<div class="table-responsive ">
							<table class="table table-striped">
								<thead>
									<tr>
										<th>Question</th>
										<th>Category</th>
										<th>Level</th>
										<th>Points</th>
										<th>Select</th>
									</tr>
								</thead>

								<tbody>
									<%
										int count = 0;
									%>
									<c:forEach items="${qs}" var="ques">
										<tr id="${ques.id}" bgcolor="${ques.selected? '':'transparent'}">


											<td><c:out value="${ques.questionText}"></c:out></td>

											<td>${ques.testCategory}</td>
											<td><c:out value="${ques.difficultyLevel.level}"></c:out></td>

											<td><input type="text" placeholder="Marks, if Correct" value="1  "
												disabled style="width: 100%;"></td>

											<td id="${ques.id}-add" style="${ques.selected? 'display: none;':''}"><a
												href="javascript:addQ('${ques.id}', 'Core Java');">Click to Add</a></td>
											<td id="${ques.id}-remove" style="${ques.selected? '':'display: none;'}"><a
												href="javascript:removeQ('${ques.id}', 'Core Java');">Click to Remove</a></td>
										</tr>
										<%
											count++;
										%>
									</c:forEach>
								</tbody>
							</table>
						</div>


						<a data-dismiss="modal"
							class="waves-effect waves-light btn submit-button indigo mt-20 mb-20">Cancel</a>
						<a href="javascript:saveSection();" id="savesection"
							class="waves-effect waves-light btn mt-20 mb-20">Save</a>

					</div>
				</div>
			</div>
		</div>
	</div>



	<script type="text/javascript">
		$(document).on('click', '.addquestion', function() {
			window.location = "addNewSection";
		});

		function saveSection() {
			var name = document.getElementById('sectionTopic').value;
			var txtFValue = document.getElementById('txtF').value;
			if (name.trim().length == 0) {
				notify('Information',
						'Please enter a meaningful name for your section before saving. ');

			} else {
				window.location = 'saveSection?sectionTopic=' + name
						+ '&percentage=' + txtFValue;

			}

		}

		function addQ(qid, sectionName) {

			//window.location = "addQuestionToSection?sectionName="+sectionName+"&questionId="+qid;
			var url = "addQuestionToSectionAjax?sectionName=" + sectionName
					+ "&questionId=" + qid;
			console.log('here url ' + url);
			$.ajax({
				url : url,
				success : function(data) {
					console.log("SUCCESS: ", data);
					var tr = document.getElementById(qid);
					var tds = tr.getElementsByTagName("td");
					var tdadd = document.getElementById(qid + "-add");
					var tdremove = document.getElementById(qid + "-remove");
					console.log(tr);
					console.log(tds);
					console.log(tdadd);
					tr.style.backgroundColor = '#33FFF9';
					tdadd.style.display = "none";
					tdremove.style.display = "";

					//document.getElementById("no-"+sectionName).innerHTML = data;

				},
				error : function(e) {
					console.log("ERROR: ", e);

				}
			});

		}

		function removeQ(qid, sectionName) {
			//window.location = "removeQuestionToSection?sectionName="+sectionName+"&questionId="+qid;
			var url = "removeQuestionToSectionAjax?sectionName=" + sectionName
					+ "&questionId=" + qid;
			console.log('here url ' + url);
			$.ajax({
				url : url,
				success : function(data) {
					console.log("SUCCESS: ", data);
					var tr = document.getElementById(qid);
					var tds = tr.getElementsByTagName("td");
					var tdadd = document.getElementById(qid + "-add");
					var tdremove = document.getElementById(qid + "-remove");
					console.log(tr);
					console.log(tds);
					console.log(tdadd);
					tr.style.backgroundColor = 'transparent';
					tdadd.style.display = "";
					tdremove.style.display = "none";

					//document.getElementById("no-"+sectionName).innerHTML = data;

				},
				error : function(e) {
					console.log("ERROR: ", e);

				}
			});
		}

		function showSelected() {
			//$.ajax({url: "showSectionsQuestions", success: function(result){
			//		notify("Success", "All Questions Selected so far..");
			//	    }});
			window.location = "showSectionsQuestions";
		}

		function highlight(sectionName) {
			window.location = "goToSection?sectionName=" + sectionName;
		}

		function removeSection(sectionName) {
			(new PNotify({
				title : 'Confirmation Needed',
				text : 'Are you sure you want to delete the section '
						+ sectionName,
				icon : 'glyphicon glyphicon-question-sign',
				hide : false,
				confirm : {
					confirm : true
				},
				buttons : {
					closer : false,
					sticker : false
				},
				history : {
					history : false
				}
			})).get().on('pnotify.confirm', function() {
				window.location = "removeSection?sectionName=" + sectionName;
			}).on('pnotify.cancel', function() {

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

		$('#search').on('click', function() {
			var text = document.getElementById("searchText").value;
			if (text.length != 0) {
				window.location = "searchQs?searchText=" + text;
			}
		});

		$(function() {
			$(".addimage").on('click', function(e) {
				e.preventDefault();
				$("#addimage").trigger('click');
			});
			$(".addaudio").on('click', function(e) {
				e.preventDefault();
				$("#addaudio").trigger('click');
			});
			$(".addvideo").on('click', function(e) {
				e.preventDefault();
				$("#addvideo").trigger('click');
			});
		});

		$('#addimage').change(function() {
			var file = $('#addimage')[0].files[0].name;
			$('.queimage').text('Image: ' + file);
		});
		$('#addaudio').change(function() {
			var file = $('#addaudio')[0].files[0].name;
			$('.queaudio').text('Audio: ' + file);
		});
		$('#addvideo').change(function() {
			var file = $('#addvideo')[0].files[0].name;
			$('.quevideo').text('Video: ' + file);
		});

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
	<div class="hiddendiv common"></div>
</body>
</html>