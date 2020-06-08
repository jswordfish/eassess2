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
        <link href="https://fonts.googleapis.com/css?family=Segoe+UI" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="css/font-awesome.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link href="css/responsive.css" rel="stylesheet" type="text/css">
		
		
    </head>
    <body>
        <div class="maincontainer">            
            <div class="wrapper">
                <div class="row row-offcanvas row-offcanvas-left">
                    <!-- sidebar -->
                     <%
					User user = (User) request.getSession().getAttribute("user");
					System.out.println("user is "+user.getEmail() );
						if(user == null){
								response.sendRedirect("login");
						}
					
					if(user.getUserType().getType().equals("LMS_ADMIN")){
						
						System.out.println("LMS_ADMIN true");
				  %>
					<jsp:include page="side_lms_admin.jsp" /> 
				   <%	  
					}
					else{
					%>
					<jsp:include page="side.jsp" /> 
					<%
					}
					%>
                    <!-- /sidebar -->
                    <!-- main right col -->
                    <div class="column col-sm-10 col-xs-11" id="main">
<!-- main contain -->
	<!-- main contain -->
<div class="rightside">
    <div class="rightdiv settest step1" style="width: 100%;padding-right: 15px;height: 550px;">

        <div class="teststeps">
            <div class="steps">
                <span>1</span>
                <label><img src="images/u1107.png">Set your test</label>
            </div>
            <div class="steps line">
                <img src="images/u1102.png">
            </div>
            <div class="steps">
                <span>2</span>
                <label><img src="images/u1105.png">Add Questions</label>
            </div>
            <div class="steps line">
                <img src="images/u1102.png">
            </div>
            <div class="steps">
                <span>3</span>
                <label><img src="images/u1114.png">Add Candidates</label>
            </div>
            <div class="steps line">
                <img src="images/u1102.png">
            </div>
            <div class="steps active">
                <span>4</span>
                <label><img src="images/u1106.png">Send Invitation</label>
            </div>
        </div>
<%

Test test = (Test) request.getSession().getAttribute("test");

%>

        <div class="invitecandidates">
            <div class="left">
                 <label>Invited Candidates - <%= test.getUsers().size() %></label>
                <ul>
                     <c:forEach var="user" items="${selectedusers}">
                    <li>${user.firstName} ${user.lastName}</li>
                </c:forEach>
                </ul>
            </div>
            <div class="right">
               <h4>Test: <%= test.getTestName() %>. Click on Section below to see a preview</h4>
                <p>Category: <%= test.getQualifier1() %> / <%= test.getQualifier2() %> /<%= test.getQualifier3() %> &nbsp;&nbsp;   Skills: Java  &nbsp;&nbsp;   </p>

                <div class="accordiansections">
		<c:forEach var="section"  items="${test.sectionDtos}" >
                    <button class="accordion">${section.sectionName}</button>
                    <div class="panel">
		    <%
		    int count = 1;
		    %>
			<c:forEach var="ques" varStatus="status" items="${section.questions}" >
				<div class="title">
				    <span><%= count %></span>
				    <p>Question - ${ques.questionText}  </p>
				</div>
                        <div class="options">
                            <ul>
                                <li style="${ques.choice1 == null || ques.choice1.length() == 0? 'display: none;':''}">Choice 1: &nbsp;&nbsp;  ${ques.choice1}</li>
                                <li style="${ques.choice2 == null || ques.choice2.length() == 0? 'display: none;':''}">Choice 2: &nbsp;&nbsp;  ${ques.choice2}</li>
                                <li style="${quest.choice3 == null || quest.choice3.length() == 0? 'display: none;':''}">Choice 3: &nbsp;&nbsp;  ${ques.choice3}</li>
                                <li style="${ques.choice4 == null || ques.choice4.trim().length() == 0? 'display: none;':''}">Choice 4:  &nbsp;&nbsp;  ${ques.choice4}</li>
                                <li style="${ques.choice5 == null || ques.choice5.trim().length() == 0? 'display: none;':''}">Choice 5:  &nbsp;&nbsp;  ${ques.choice5}</li>
				<li style="${ques.choice6 == null || ques.choice6.length() == 0? 'display: none;':''}">Choice 6:  &nbsp;&nbsp;  ${ques.choice6}</li>
                            </ul>
                            Answer: ${ques.rightChoices} 
                        </div>
			<%
				count ++;
			%>
			</c:forEach>
		    
                        
			
			 
                    </div>
		      </c:forEach>           

                   

                </div>
				<!-- add start and end date options -->
				<div class="option">
					<label>Link Start Date and Time</label>
					<div class='input-group date' id='datetimepicker1'>
					<input id="startDate" type="text" class="form-control" required="true"/>
<!-- <form:input path="test.qualifier4" class="form-control" required="true" /> -->
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
				<div class="option">
					<label>Link End Date and Time</label>
					<div class='input-group date' id='datetimepicker2'>
					<input id="endDate" type="text" class="form-control" required="true"/>
<!-- <form:input path="test.qualifier5" class="form-control" required="true" /> -->
						<span class="input-group-addon">
							<span class="glyphicon glyphicon-calendar"></span>
						</span>
					</div>
				</div>
				<!-- end date options -->

            </div>
        </div>


    </div>

    <div class="nextbuttons">
    
        <a class="cancelbtn" href="testlist">Cancel...</a>
        <a class="backbtn backstep3" href="showUsers">Back</a>
        <a class="nextbtn" href="javascript:shareTests()">Send Invitation</a>
    </div>

</div>

<script>
    
</script>

<!-- /main contain -->
<link href="css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
		
		<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>
	<script type="text/javascript" src="scripts/custom.js"></script>
	
	 <link href="css/font-awesome.css" rel="stylesheet" type="text/css">

<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

</div>
            </div>
        </div>
      
	  
	  <script>
	  
            $(function () {
				
				
                $('#datetimepicker1').datetimepicker({
					format: 'DD/MM/YYYY hh:mm a'
					//minDate:new Date()
					
				});
                
				
				
                 $('#datetimepicker2').datetimepicker({
					format: 'DD/MM/YYYY hh:mm a'
					
				});
            });

	  </script>
	  
	  <script>
	  
	 
	 function shareTests(){
		 console.log('in sharetests');
		var startDate = document.getElementById("startDate").value; 
		console.log('in sharetests '+startDate);
		var endDate = document.getElementById("endDate").value; 
		if(startDate == null || startDate == ''){
			notify('Information', 'Start Date and Time not present for the Test Link to be shared with the Candidate(s)');
			return;
		}
		
		if(endDate == null || startDate == ''){
			notify('Information', 'End Date and Time not present for the Test Link to be shared with the Candidate(s)');
			return;
		}
		startDate = encodeURI(startDate);
		endDate = encodeURI(endDate);
		window.location = "shareTestWithUsers?startDate="+startDate+"&endDate="+endDate;
	 }
	 
	 function notify(messageType, message){
		 var notification = 'Information';
		 //PNotify.prototype.options.styling = "jqueryui";
			 $(function(){
				 new PNotify({
				 title: notification,
				 text: message,
				 type: messageType,
				// width: '60%',
				// styling: 'bootstrap3',
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
      </script>
	  
	  <c:if test="${msgtype != null}">
		 <script>
	 var notification = 'Information';
	 $(function(){
		 new PNotify({
	         title: notification,
	         text: '${message}',
	         type: '${msgtype}',
	         //styling: 'bootstrap3',
	         hide: true
	     });
	 }); 	 
      </script>
</c:if>
	  
    </body>
</html>

