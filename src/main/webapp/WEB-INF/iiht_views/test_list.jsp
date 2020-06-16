<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.assessment.data.*, java.text.*, java.util.*"%>
<jsp:useBean id="random" class="java.util.Random" scope="application" />
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Tests</title>

<spring:url value="/resources/assets/img/ico/favicon.png" var="c1" />

<link href="${c1}" rel="shortcut icon" />

<spring:url value="https://fonts.googleapis.com/css?family=Raleway:400,300,500,700,900" var="c2" />

<link href="${c2}" rel="stylesheet" type="text/css" />

<!-- Material Icons CSS -->
<spring:url value="/resources/assets/fonts/iconfont/material-icons.css" var="c3" />

<link href="${c3}" rel="stylesheet" type="text/css" />

<!-- FontAwesome CSS -->
<spring:url value="/resources/assets/fonts/font-awesome/css/font-awesome.min.css" var="c4" />

<link href="${c4}" rel="stylesheet" type="text/css" />

<!-- magnific-popup -->
<spring:url value="/resources/assets/magnific-popup/magnific-popup.css" var="c5" />

<link href="${c5}" rel="stylesheet" type="text/css" />

<!-- owl.carousel -->
<spring:url value="/resources/assets/owl.carousel/assets/owl.carousel.css" var="c6" />

<link href="${c6}" rel="stylesheet" type="text/css" />

<spring:url value="/resources/assets/owl.carousel/assets/owl.theme.default.min.css" var="c7" />

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

<link href="${c16}" rel="stylesheet" type="text/css" />
<script src="./resources/scripts/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>

<body id="top" class="has-header-search">

	<!--header start-->
	<header id="header" class="tt-nav nav-border-bottom">
		<div class="header-sticky light-header ">
			<div class="container">
				<div id="materialize-menu" class="menuzord">
					<!--logo start-->
					<a href="javascript:void(0);" class="logo-brand"> <img class="retina"
						src="<%=request.getContextPath()%>/resources/images/Logo.png" alt="" />
					</a>
					<!--logo end-->
					<!--mega menu start-->
					<ul class="menuzord-menu pull-right">
						<li><a
							href="javascript:notify('Information', 'We will release the feature pretty soon! Please wait for our next release');">Dashboard</a></li>
						<li><a href="question_list">Question Bank</a></li>
						<li class="dropdown active" id="dd"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Tests
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="testlist">All Tests</a></li>
								<li><a href="listTestLinks">Test Link Management</a></li>
								<li><a href="showAllSchedules">Test Schedule</a></li>
							</ul></li>
						<li><a href="modules">Module</a></li>
						<li><a href="licenses">License</a></li>
						<li class="dropdown" id="dd2"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Report
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="showReports">Results</a></li>
								<li><a href="codingSessions">Code Reports</a></li>
								<li><a href="showSkillTags">Skill Reports</a></li>
							</ul></li>
						<li><a href="skills">Skills</a></li>
						<li><a href="showProfileParams">Recomm Setting</a></li>
						<li class="dropdown" id="dd3"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Users
								<b class="caret"></b>
						</a>
							<ul class="dropdown-menu">
								<li><a href="listUsers">All Users</a></li>
								<li><a href="lmsAdmins">LMS Admin Users</a></li>
							</ul></li>
					</ul>
					<!--mega menu end-->
				</div>
			</div>
		</div>
	</header>
	<!--header end-->


	<section>
		<div class="container">
			<div class="row mt-5">
				<div class="col-md-12">
					<div class="col-md-6"></div>
					<div class="col-md-2">
						<a href="addtest" class="btn waves-effect waves-light col-md-12"><i
							class="material-icons fa fa-plus-circle"></i> Add New</a>
					</div>
					<div class="col-md-2">
						<a
							href="javascript:notify('Information', 'Feature to upload Tests coming soon. Right now we have capability to upload Users and Questions.');"
							class="btn waves-effect waves-light col-md-12"><i class="material-icons fa fa-upload"></i>
							Import</a>
					</div>
					<div class="col-md-2">
						<a href="signoff" class="btn waves-effect waves-light col-md-12"><i
							class="material-icons fa fa-sign-out"></i> Sign Off</a>
					</div>
				</div>

				<div class="col-md-12">
					<div class="col-md-12">
						<div class="pagination" style="float: right;">
							<c:if test="${showPreviousPage}">
								<a href="${callingMethod}?page=${previousPage}${queryParam}"><i class="fa fa-arrow-left"></i></a>
							</c:if>

							<c:if test="${selectedPage != null &&  selectedPage > 0}">
                                    ${selectedPage} / ${totalNumberOfPages}
                                </c:if>

							<c:if test="${showNextPage}">
								<a href="${callingMethod}?page=${nextPage}${queryParam}"><i class="fa fa-arrow-right"></i></a>
							</c:if>
						</div>
					</div>
				</div>

				<div class="col-md-12">
					<div class="mt-10"></div>
					<div class="col-md-5">
						<h1 style="color: #b07c2a;">
							<b>Test Bank</b>
						</h1>
					</div>
					<div class="col-md-4">
						<div class="widget widget_search">

							<div class="search-form">
								<form action="searchTests" method="get">
									<input type="text" placeholder="Search a question" name="searchText" id="searchText">
									<button type="submit" id="search">
										<i class="fa fa-search"></i>
									</button>
								</form>
							</div>
						</div>
					</div>
					<div class="col-md-3" style="padding: 0;">
						<div class="mt-10"></div>
						<div class="col-md-4"></div>
						<div class="col-md-4" style="padding-left: 0;">
							<a href="javascript:notify('Information', 'Feature coming soon')"> <i
								class="fa fa-sort-amount-asc"></i> <span>Sort</span>
							</a>
						</div>
						<div class="col-md-4" style="padding: 0;">
							<a href="javascript:notify('Information', 'Feature coming soon')"> <i
								class="fa fa-filter"></i> <span>Filter</span>
							</a>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="table-responsive testslisttable">
						<table class="table table-striped">
							<thead style="background-color: #03a9f4;">
								<tr>
									<th><input type="checkbox" id="chkall" name="chkall" class="filled-in" /> <label
										for="chkall"></label></th>
									<th class="title">Test Title</th>
									<th>Category</th>
									<th>Test Time In Minutes</th>
									<th>Pass Percentage</th>
									<th>Created By</th>
									<th>Last Update</th>
									<th>Expire Test</th>
									<th>Update Test</th>
									<th>Duplicate Test</th>
									<th>Share Test</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${tests}" var="test">
									<tr>
										<td><input type="checkbox" class="filled-in" /> <label for="chkall"></label></td>

										<td><c:out value="${test.testName}"></c:out></td>
										<td>${test.category}</td>
										<td>${test.testTimeInMinutes}</td>
										<td>${test.passPercent}</td>
										<td><c:out value="${test.cDate}"></c:out></td>
										<td><c:out value="${test.uDate}"></c:out></td>
										<td><a onClick="confirm(${test.id}); return false;" href="#">Click to Expire</a></td>
										<td><a href="updateTest?testId=${test.id}">Click to Update</a></td>
										<td><a href="javascript:void(0);" class="testname" data-name="${test.testName}"
											data-toggle="modal"
											onClick="javascript:duplicateOpen('${test.testName}', '${test.companyId}')"><i
												class="fa fa-copy"></i></a></td>
										<td><a href="javascript:void(0);" class="testname" data-name="${test.testName}"
											data-toggle="modal"
											onClick="javascript:shareOpen('${test.testName}', '${test.publicUrl}', '${test.id}','${random.nextInt()}')"><i
												class="fa fa-share-alt"></i></a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

				</div>
			</div>
			<!-- /.row -->

		</div>
		<!-- /.container -->
	</section>



	<footer class="footer footer-four">
		<div class="secondary-footer brand-bg darken-2 text-center">
			<div class="container">
				<ul>
					<li><a href="javascript:void(0)">Dashboard</a></li>
					<li><a href="question_list">Question Bank</a></li>
					<li><a href="testlist">Tests</a></li>
					<li><a href="javascript:void(0)">Skills</a></li>
					<li><a href="showReports">Results</a></li>
					<li><a href="javascript:void(0)">Code Analysis Reports</a></li>
					<li><a href="javascript:void(0)">Skill based Reports</a></li>
					<li><a href="showProfileParams">Recomm Setting</a></li>
					<li><a href="listUsers">Users</a></li>
				</ul>
			</div>
		</div>
	</footer>





	<!-- Duplicate Test Popup -->
	<div id="modalcopy" class="modal fade modalcopy" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Duplicate Test</h4>
				</div>
				<div class="modal-body">
					<form method="GET" action="duplicateTest">
						<label>Existing test name</label> <input id="existing_name" value="Sample Test " type="text"
							disabled /> <label>New Test Name</label> <input id="newTest" type="text" required /> <label>Qualifier
							1</label> <input id="newQual1" type="text" required /> <label>Qualifier 2</label> <input
							id="newQual2" type="text" />
						<div class="buttons text-center" style="padding-top: 20px;">
							<input type="button" class="waves-effect waves-light btn" value="Duplicate"
								onClick="javascript:dup()" /> <input class="waves-effect waves-light btn" type="button"
								data-dismiss="modal" value="Cancel" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	<!-- Share Test Popup -->
	<div id="modalshare" class="modal fade modalcopy" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Share Test</h4>
				</div>
				<div class="modal-body">

					<div role="tabpanel">
						<!-- Nav tabs -->
						<ul class="nav nav-tabs" role="tablist">
							<li role="presentation" class="active"><a href="#uploadTab" aria-controls="uploadTab"
								role="tab" data-toggle="tab">Single Share</a></li>
							<li role="presentation"><a href="#browseTab" aria-controls="browseTab" role="tab"
								data-toggle="tab">Bulk Share</a></li>
						</ul>
						<!-- Tab panes -->
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="uploadTab">

								<form method="POST" action="sharePublicTest" enctype="multipart/form-data">
									<div>
										<label>Existing test name</label> <input id="existing_name1" type="text"
											readonly="readonly" name="existing_name1" />
									</div>
									<div>
										<label>First Name</label> <input id="firstName" type="text" name="firstName" />
									</div>
									<div>
										<label>Last name</label> <input id="lastName" type="text" name="lastName" /></a>
									</div>
									<div>
										<label>Email Id</label> <input id="userEmail" type="text" name="userEmail" />
									</div>
									<div>
										<label>Public Test URL</label> <input id="publicTestUrl" type="text" readonly="readonly" />
									</div>
									<div>
										<label>Expire Date</label> <input id="txt2Date" name="expId" />
									</div>

									<input type="hidden" name="testId" id="testId" value="" />
									<div class="buttons text-center" style="padding-top: 20px;">
										<input class="waves-effect waves-light btn" type="button" value="Copy in your Clipboard"
											onClick="javascript:copyUrlInClipBoard()" /> <input class="waves-effect waves-light btn"
											type="submit" value="Share" /> <input class="waves-effect waves-light btn" type="button"
											value="Close" data-dismiss="modal" />
									</div>
								</form>

							</div>
							<div role="tabpanel" class="tab-pane" id="browseTab">

								<form method="POST" action="shareBulkPublicTest" enctype="multipart/form-data">
									<div>
										<label>Existing test name</label> <input id="existing_name2" type="text"
											readonly="readonly" name="existing_name1" />
									</div>
									<div>
										<input id="publicTestUrl2" type="hidden" readonly="readonly" />
									</div>
									<div>
										<label>Bulk Users</label><input type="file" name="file">
									</div>
									<div>
										<label>Expire Date</label> <input id="txt2Date2" name="expId" />
									</div>

									<input type="hidden" name="testId" id="testId2" value="" />
									<div class="buttons text-center" style="padding-top: 20px;">
										<input class="waves-effect waves-light btn" type="button" value="Copy in your Clipboard"
											onClick="javascript:copyUrlInClipBoard()" /> <input class="waves-effect waves-light btn"
											type="submit" value="Share" /> <input class="waves-effect waves-light btn" type="button"
											value="Close" data-dismiss="modal" />
									</div>
								</form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->

	<script src="./resources/assets/js/jquery-2.1.3.min.js"></script>
	<script src="./resources/assets/bootstrap/js/bootstrap.min.js"></script>
	<script src="./resources/assets/materialize/js/materialize.min.js"></script>
	<script src="./resources/assets/js/menuzord.js"></script>
	<script src="./resources/assets/js/bootstrap-tabcollapse.min.js"></script>
	<script src="./resources/assets/js/jquery.easing.min.js"></script>
	<script src="./resources/assets/js/jquery.sticky.min.js"></script>
	<script src="./resources/assets/js/smoothscroll.min.js"></script>
	<script src="./resources/assets/js/jquery.stellar.min.js"></script>
	<script src="./resources/assets/js/jquery.inview.min.js"></script>
	<script src="./resources/assets/owl.carousel/owl.carousel.min.js"></script>
	<script src="./resources/assets/flexSlider/jquery.flexslider-min.js"></script>
	<script src="./resources/assets/magnific-popup/jquery.magnific-popup.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js"></script>
	<script src="./resources/assets/js/scripts.js"></script>
	<script src="./resources/assets/scripts/custom.js"></script>
	<script src="./resources/assets/scripts/pnotify.custom.min.js"></script>
	<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
	<script src="./resources/Multi Select/bootstrap-multiselect.js"></script>

	<script>

	$(document).ready(function() {
		document.getElementById("dd").style.display = "block";
		document.getElementById("dd2").style.display = "block";
		document.getElementById("dd3").style.display = "block";

		 var date = new Date();
		    date.setDate(date.getDate() +2);
		$("#txt2Date").datepicker({dateFormat: 'dd-mm-yy'}).datepicker('setDate',date);
		$("#txt2Date2").datepicker({dateFormat: 'dd-mm-yy'}).datepicker('setDate',date);
	});
	
        function dup() {
            var existing_name = document.getElementById("existing_name").value;
            var newTest = document.getElementById("newTest").value;
            var newQual1 = document.getElementById("newQual1").value;
            var newQual2 = document.getElementById("newQual2").value;
            if (newTest == '' || newTest == null) {
                notify('Info', 'Enter a name for the new Test');
            } else if (newQual1 == '' || newQual1 == null) {
                notify('Info', 'Enter a Qualifier name for the new Test');
            } else {
                window.location = "duplicateTest?existing_name=" + existing_name + "&newTest=" + newTest + "&newQual1=" + newQual1 + "&newQual2=" + newQual2;
            }

        }

        function duplicateOpen(testName, tenantId) {
            var name = $(this).attr('data-name');
            console.log('here ' + testName);
            console.log(tenantId);
            document.getElementById("existing_name").value = testName;
            $('#modalcopy').modal('show');
            $('#modalshare').modal('hide');
        }

        function shareOpen(testName, testPublicUrl, testId,uniqueId) {
//       		 var date = new Date();
//       		    date.setDate(date.getDate() +2);
//       		$("#txt2Date").datepicker({dateFormat: 'dd-mm-yy'}).datepicker('setDate',date);
            var name = $(this).attr('data-name');
            console.log('here ' + testName);
//             console.log('rand ' + uniqueId);
// 			var
			var str = uniqueId;
			var uniqURL = testPublicUrl.concat('&uid='+str);
			console.log(uniqURL)
			document.getElementById("existing_name1").value = testName;
            document.getElementById("publicTestUrl").value = testPublicUrl;
            document.getElementById("testId").value = testId;
        	document.getElementById("existing_name2").value = testName;
            document.getElementById("publicTestUrl2").value = testPublicUrl;
            document.getElementById("testId2").value = testId;
            $('#modalcopy').modal('hide');
            $('#modalshare').modal('show');
        }
      
        function copyUrlInClipBoard() {
            el = document.createElement('textarea');
            el.value = document.getElementById("publicTestUrl").value;
            document.body.appendChild(el);
            el.select();
            document.execCommand('copy');
            document.body.removeChild(el);
            //$('#modalshare').modal('hide');
        }

        function copyUrlClose() {
            $('#modalshare').modal('hide');
        }

        function shareTest() {
//       		var uemail = document.getElementById("uemail").value;
            var existing_name1 = document.getElementById("existing_name1").value;
            var firstName = document.getElementById("firstName").value;
            var lastName = document.getElementById("lastName").value;
            var userEmail = document.getElementById("userEmail").value;
            var testId = document.getElementById("testId").value;
            var expId = document.getElementById("txt2Date").value;
            if (firstName == '' || firstName == null) {
                notify('Info', 'First Name can not be blank');
            } else if (lastName == '' || lastName == null) {
                notify('Info', 'Last Name can not be blank');
            } else if (userEmail == '' || userEmail == null) {
                notify('Info', 'Email can not be blank');
            } else if (!validateEmail(userEmail)) {
                notify('Info', 'Enter a valid email');
            } else {
                window.location = "sharePublicTest?existing_name1=" + existing_name1 + "&firstName=" + firstName + "&lastName=" + lastName + "&userEmail=" + userEmail + "&testId=" + testId+"&expId=" + expId;
            }

        }

        function validateEmail(email) {
            var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return re.test(email);
        }

        $('#search').on('click', function() {
            var text = document.getElementById("searchText").value;
            if (text.length != 0) {
                window.location = "searchTests?searchText=" + text;
            }
        });



        function confirm(id) {
            (new PNotify({
                title: 'Confirmation Needed',
                text: 'Are you sure? Students having the link to this exam may no longer be able to take the exam',
                icon: 'glyphicon glyphicon-question-sign',
                hide: false,
                confirm: {
                    confirm: true
                },
                buttons: {
                    closer: false,
                    sticker: false
                },
                history: {
                    history: false
                }
            })).get().on('pnotify.confirm', function() {
                window.location = "retireTest?testId=" + id;
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
