<!DOCTYPE HTML>
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

<link rel="stylesheet" href="./resources/userprofile/css/style2.css">
<!-- <script src="./resources/userprofile/js/modernizr-2.6.2.min.js"></script> -->

<!-- gulrez  css-->

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link href="./resources/assets/css/pnotify.custom.min.css" rel="stylesheet" type="text/css" />
<script src="./resources/assets/scripts/pnotify.custom.min.js"></script>

<style>
.wrapper {
	width: 100%;
	padding-left: 100px;
	padding-right: 100px;
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
							<p>
								<b style="color: gray;">Welcome to eAssess</b>
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
								<li><a href="lmsTests">Test Creation </a></li>
								<li><a href="lmsQuestion_list">Questions </a></li>
								<li><a href="about.html">About</a></li>
								<li><a href="contact.html">Contact</a></li>
								<li><a href="signoff">Log Off</a></li>
								
							</ul>
						</div>
					</div>
				</div>
			</div>
		</nav>

	</div>

	<div class="wrapper">
		<div class="container-fluid">
			<!-- Control the column width, and how they should appear on different devices -->
			<div class="row">
				<div class="col-sm-3">
					<h1 style="color: #b07c2a;">
						<b>Test Bank</b>
					</h1>
				</div>
				<div class="col-sm-3">
					<div class=" pt-3">
						<form action="lmsSearchTests" method="get">
							<input type="text" placeholder="Search a Test Name" name="searchText" id="searchText">
							<button type="submit" id="search">
								<i class="fa fa-search"></i>
							</button>
						</form>
					</div>
				</div>

				<div class="col-sm-3">
					<div class=" pt-2">
						<a href="lmsAddtest" class="btn btn-primary"><i class="material-icons fa fa-plus-circle"></i> Add New</a> <a href="addtest"
							class="btn btn-primary"><i class="material-icons fa fa-upload"></i>Import</a>
					</div>
				</div>

				<div class="col-sm-3">
					<div class=" pt-3">
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
			</div>
			<!-- Or let Bootstrap automatically handle the layout -->
			<div class="row">
				<div class="table-responsive testslisttable">
					<table class="table table-striped">
						<thead style="background-color: #03a9f4;">
							<tr>
								<th><input type="checkbox" id="chkall" name="chkall" class="filled-in" /> <label for="chkall"></label></th>
								<th class="title">Test Title</th>
								<th>Category</th>
								<th>Test Time In Minutes</th>
								<th>Pass Percentage</th>
								<th>Created By</th>
								<th>Last Update</th>
								<th>Expire Test</th>
								<th>Update Test</th>
								<th>Duplicate Test</th>
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
									<td><a href="lmsUpdateTest?testId=${test.id}">Click to Update</a></td>
									<td><a href="javascript:void(0);" class="testname" data-name="${test.testName}" data-toggle="modal"
										onClick="javascript:duplicateOpen('${test.testName}', '${test.companyId}')"><i class="fa fa-copy"></i></a></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>


	<!-- Duplicate Test Popup 2  -->
	<div class="modal fade" id="modalcopy" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header border-bottom-0">
					<h5 class="modal-title" id="exampleModalLabel">Create Duplicate Test</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form method="GET" action="duplicateTest">
					<div class="modal-body">
						<div class="form-group">
							<label for="existing_name">Existing test name</label> <input id="existing_name" value="Sample Test " type="text" class="form-control"
								disabled />
						</div>
						<div class="form-group">
							<label for="newTest">New Test Name</label> <input id="newTest" type="text" class="form-control" required />
						</div>
						<div class="form-group">
							<label for="newQual1"> Qualifier 1</label> <input id="newQual1" type="text" class="form-control" required />
						</div>
						<div class="form-group">
							<label for="newQual1"> Qualifier 2</label> <input id="newQual2" type="text" class="form-control" required />
						</div>
					</div>
					<div class="modal-footer border-top-0 d-flex justify-content-center">
						<input type="button" class="btn btn-success" value="Duplicate" onClick="javascript:dup()" /> <input class="btn btn-danger"
							type="button" data-dismiss="modal" value="Cancel" />


						<!-- 						<button type="submit" class="btn btn-success">Submit</button> -->
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
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
				window.location = "duplicateTest?existing_name="
						+ existing_name + "&newTest=" + newTest + "&newQual1="
						+ newQual1 + "&newQual2=" + newQual2;
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

		function shareOpen(testName, testPublicUrl, testId, uniqueId) {
			//       		 var date = new Date();
			//       		    date.setDate(date.getDate() +2);
			//       		$("#txt2Date").datepicker({dateFormat: 'dd-mm-yy'}).datepicker('setDate',date);
			var name = $(this).attr('data-name');
			console.log('here ' + testName);
			//             console.log('rand ' + uniqueId);
			// 			var
			var str = uniqueId;
			var uniqURL = testPublicUrl.concat('&uid=' + str);
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
				window.location = "sharePublicTest?existing_name1="
						+ existing_name1 + "&firstName=" + firstName
						+ "&lastName=" + lastName + "&userEmail=" + userEmail
						+ "&testId=" + testId + "&expId=" + expId;
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
			(new PNotify(
					{
						title : 'Confirmation Needed',
						text : 'Are you sure? Students having the link to this exam may no longer be able to take the exam',
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