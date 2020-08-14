<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Techademy</title>
<link href='http://fonts.googleapis.com/css?family=Poppins:300,400,700'
	rel='stylesheet' type='text/css'>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/font-awesome.css" rel="stylesheet" type="text/css">
<!-- <link href="css/mini-event-calendar.css" rel="stylesheet" -->
<!-- 	type="text/css"> -->


<style>
body {
	background-color: #fff;
	height: auto;
}

.header {
	padding: 20px 0;
}

.logo {
	padding: 0 !important;
}

.logo h1 {
	color: #000;
	font-size: 20px;
	margin: 0;
}

.logo a {
	text-decoration: none;
}

.rightsideinfo {
	float: left;
	width: 100%;
}

.rightsideinfo ul {
	float: right;
}

.rightsideinfo ul li {
	display: inline-block;
	padding: 0 20px;
}

.rightsideinfo ul li.trybtn a {
	background-color: #3e75f9;
	padding: 10px 25px;
	color: #fff;
	border-radius: 25px;
	text-decoration: none;
}

.rightsideinfo ul li a {
	color: #000;
}

.rightsideinfo ul li img {
	width: 35px;
	border-radius: 50%;
	margin-left: 10px;
}

.employyeprofile {
	float: left;
	width: 100%;
}

.employyeprofile .leftsideinfo .image {
	float: left;
	width: 100%;
	padding-top: 50px;
}

.employyeprofile .leftsideinfo .image img {
	max-width: 100%;
	width: 180px;
}

.employyeprofile .leftsideinfo .image h5 {
	float: left;
	width: 100%;
	font-size: 22px;
	padding-top: 10px;
}

.employyeprofile .leftsideinfo .image h6 {
	float: left;
	width: 100%;
	font-size: 15px;
	margin-top: 0;
}

.leftsideinfo .charges {
	float: left;
	width: 100%;
	background-color: #e4f1fe;
	padding: 10px;
	border-radius: 5px;
	font-weight: bold;
	margin-top: 15px;
}

.leftsideinfo .userinfo {
	float: left;
	width: 100%;
	padding-top: 20px;
}

.leftsideinfo .userinfo h5 {
	color: #000;
	font-weight: bold;
}

.leftsideinfo .userinfo li {
	float: left;
	width: 100%;
	padding-bottom: 5px;
	font-size: 15px;
}

.leftsideinfo .userinfo li i {
	margin-right: 10px;
}

.leftsideinfo .userinfo li img {
	width: 20px;
	margin-right: 10px;
}

.employyeprofile .rightsideinformation {
	float: left;
	width: 100%;
	padding-left: 20px;
	box-shadow: -8px 10px 16px -15px #aaaaaa;
}

.rightsideinformation .employeecosts {
	float: left;
	width: 100%;
}

.rightsideinformation .heading {
	font-size: 22px;
	color: #3e75f9;
}

.employeecosts ul li {
	float: left;
	width: 100%;
	padding-bottom: 10px;
}

.employeecosts li .costcolor {
	float: left;
	width: 20px;
	height: 5px;
	background-color: red;
	margin-right: 20px;
	position: relative;
	top: 15px;
}

.employeecosts ul {
	float: left;
	width: 100%;
	padding-top: 50px;
	padding-left: 50px;
}

.employeecosts li .costinfo {
	float: left;
}

.employeecosts li .costinfo h6 {
	margin: 0;
	font-size: 15px;
	font-weight: bold;
}

.employeecosts table {
	float: left;
	width: 100%;
	margin-bottom: 30px;
}

.employeecosts table tr th {
	font-size: 15px;
}

.employeecosts table tr td span {
	float: left;
	width: 100%;
	font-size: 12px;
}

.employeecosts table tr td {
	padding-top: 10px;
	padding-bottom: 10px;
	font-size: 15px;
}

.employyeprofile ul {
	list-style: none;
	padding-left: 0;
}
</style>
<style>
.nav-link[data-toggle].collapsed:after {
	content: "\25B2";
}

.nav-link[data-toggle]:not (.collapsed ):after {
	content: "\25BC";
}
</style>
<script>
// 	$(document).ready(function() {
// 		var val = document.getElementById("#id123");
// 		console.log(val)
// 	});
</script>
</head>

<body>
	<div class="learnercourse">

		<div class="header">
			<div class="headertop">
				<div class="col-md-12">
					<div class="col-md-6 col-xs-6">
						<div class="logo">
							<a href="#"><h1>Employee Summary</h1></a>
						</div>
					</div>
					<div class="col-md-6">
						<div class="rightsideinfo">
							<ul>
								<li><a href="#">Jatin Sutaria <img
										src="images/user.png"></a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="employyeprofile">
			<div class="col-md-12">
				<div class="col-md-3 col-sm-6">
					<div class="leftsideinfo">
						<div class="image text-center">
							<img src="images/user.png">
							<h5>Jatin Sutaria</h5>
							<h6>Senior Architect</h6>
						</div>
						<div class="charges text-center">Total Weighted eLearning
							Score: 78%</div>
						<div class="userinfo">
							<h5>INFO</h5>
							<ul>
								<li><i class="fa fa-envelope"></i>
									jatin.sutaria@thev2technologies.com</li>
								<li><i class="fa fa-phone"></i> 0123456789</li>
								<li><i class="fa fa-map-marker"></i> Mumbai</li>
							</ul>
						</div>

						<table>
							<c:forEach var="entry" items="${mapList}" varStatus="count">
								<tr>
									<td><a href="javascript:getParam('${entry.key}');"><c:out
												value="${entry.key}" /> </a></td>
									<c:forEach var="entry2" items="${entry.value}"
										varStatus="count">
										<tr>
											<td><span> &nbsp&nbsp</span><a
												href="javascript:getParam('${entry.key}-${entry2.key}');">
													<c:out value="${entry2.key}" />
											</a></td>
											<c:forEach var="entry3" items="${entry2.value}"
												varStatus="count">
												<tr>
													<td><span> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span><a
														href="javascript:getParam('${entry.key}-${entry2.key}-${entry3.key}');"><c:out
																value="${entry3.key}" /></a></td>
													<c:forEach var="entry4" items="${entry3.value}"
														varStatus="count">
														<tr>
															<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a
																href="javascript:getParam('${entry.key}-${entry2.key}-${entry3.key}-${entry4.key}');"><c:out
																		value="${entry4.key}" /></a></td>

															<c:forEach var="entry5" items="${entry4.value}"
																varStatus="count">
																<tr>
																	<td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a
																		href="javascript:getParam('${entry.key}-${entry2.key}-${entry3.key}-${entry4.key}-${entry5}');"><c:out
																				value="${entry5}" /></a></td>

																</tr>
															</c:forEach>
														</tr>
													</c:forEach>
												</tr>
											</c:forEach>
										</tr>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>


						<ul>
							<li class="nav-item"><a
								class="nav-link collapsed text-truncate" href="#submenu1"
								data-toggle="collapse" data-target="#submenu1"> <span
									class="d-none d-sm-inline">Reports</span></a>
								<div class="collapse" id="submenu1" aria-expanded="false">
									<ul class="flex-column pl-2 nav">

										<li class="nav-item"><a class="nav-link collapsed py-1"
											href="#submenu1sub1" data-toggle="collapse"
											data-target="#submenu1sub1"><span>Customers</span></a>
											<div class="collapse" id="submenu1sub1" aria-expanded="false">

												<ul class="flex-column nav pl-4">
													<li class="nav-item"><a class="nav-link p-1" href="#">
															Daily </a></li>
													<li class="nav-item"><a class="nav-link p-1" href="#">
															Dashboard </a></li>
													<li class="nav-item"><a class="nav-link p-1" href="#">
															Charts </a></li>
													<li class="nav-item"><a class="nav-link p-1" href="#">
															Areas </a></li>
												</ul>
											</div></li>
									</ul>
								</div></li>
						</ul>


						<!-- 						<div class="userinfo"> -->
						<!-- 							<h5>Java</h5> -->
						<!-- 							<ul> -->
						<!-- 								<li><img src="images/user.png"> OOPS</li> -->
						<!-- 								<li><img src="images/user.png"> File Handling</li> -->
						<!-- 								<li><img src="images/user.png"> Networks and Multi -->
						<!-- 									Threading</li> -->
						<!-- 								<li><img src="images/user.png"> Design Patterns</li> -->
						<!-- 							</ul> -->
						<!-- 						</div> -->

					</div>
				</div>
				<%-- 				${mapList} --%>
				<%-- 				<c:forEach items="${mapList}" var="entry"> --%>
				<%-- 				${entry.key}:	<c:forEach items="${entry.value}" var="entry2"> --%>
				<%-- 					    <c:out value="${entry2}"/><br> --%>
				<%-- 					    <c:forEach items="${entry2.value}" var="entry3"> --%>
				<%-- 					    	<c:out value="${entry3}"></c:out> --%>
				<%-- 					    </c:forEach> --%>
				<%-- 					</c:forEach> --%>
				<%-- 				</c:forEach> --%>
				<div class="col-md-9 col-sm-8">
					<div class="rightsideinformation">
						<div class="employeecosts">
							<h4 class="heading">eProfiler Report</h4>
							<div class="col-md-4 col-sm-6">
								<ul>
									<li>
										<div class="costcolor" style="background-color: #3366cc;"></div>
										<div class="costinfo">
											<h6>100 %</h6>
											<p>Java</p>
										</div>
									</li>
									<li>
										<div class="costcolor" style="background-color: #dc3912;"></div>
										<div class="costinfo">
											<h6>50 %</h6>
											<p>Big Data</p>
										</div>
									</li>
									<li>
										<div class="costcolor" style="background-color: #ff9900;"></div>
										<div class="costinfo">
											<h6>22 %</h6>
											<p>Python</p>
										</div>
									</li>
									<li>
										<div class="costcolor" style="background-color: #109618;"></div>
										<div class="costinfo">
											<h6>77 %</h6>
											<p>Machine Learning</p>
										</div>
									</li>
									<li>
										<div class="costcolor" style="background-color: #990099;"></div>
										<div class="costinfo">
											<h6>88 %</h6>
											<p>Security</p>
										</div>
									</li>
								</ul>
							</div>
							<div class="col-md-8 col-sm-6">
								<div id="piechart"></div>
							</div>
						</div>
						<div class="employeecosts">
							<h4 class="heading">eProfiler Summary</h4>
							<table id="tbl">
								<tr>
									<th>Topic</th>
									<th>What your score means</th>

								</tr>

								<tr class="tr"></tr>
							</table>

						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<script src="scripts/jquery.min.js"></script>
	<script src="scripts/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>

	<script type="text/javascript">
		// Load google charts
		google.charts.load('current', {
			'packages' : [ 'corechart' ]
		});
		google.charts.setOnLoadCallback(drawChart);

		// Draw the chart and set the chart values
		function drawChart() {
			var data = google.visualization.arrayToDataTable([
					[ 'Topic', 'Score' ], [ 'Java', 100 ], [ 'Big Data', 50 ],
					[ 'Python', 22 ], [ 'Machine Learning', 77 ],
					[ 'Security', 88 ] ]);

			// Optional; add a title and set the width and height of the chart
			var options = {
				'title' : '',
				'width' : 600,
				'height' : 350
			};

			// Display the chart inside the <div> element with id="piechart"
			var chart = new google.visualization.PieChart(document
					.getElementById('piechart'));
			chart.draw(data, options);
		}

		function getParam(val) {
			console.log(val);
			var email='${email}';
// 			var test="first15@last15.com";
// 			alert(test)
			$.ajax({
				url : 'getRecom?param=' + val+'&email='+email,
				type : 'GET',
				success : function(data) {
				$(".tr").remove();
					for ( var item in data) {
						$('#tbl')
						.append("<tr class='tr'><td>"+item+"</td><td>"+data[item]+"</td></tr>");
					}
				},
			});

		}
	</script>

</body>
</html>
