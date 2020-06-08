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
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<link href="css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>
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

                        <div class="rightside">
                            <div class="topmenu text-right">
                                <a href="addJobDesc">Add New Job Description</a>
				
				
				
                                <div class="pagination">
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
							

                            <div class="questiontable">
                                <div class="questionheading">
                                    <div class="left">
                                        <h3>Job Descriptions Repository</h3>
                                    </div>
                                    <div class="right">
                                        <div class="searchdata">
                                            <input type="text" placeholder="Search" name="searchText" id="searchText">
                                            <i class="fa fa-search" id="search"></i>
                                        </div>

                                        

                                    </div>
                                </div>


                                <div class="questiontablelist" style="overflow-x:scroll;height:500px;">
                                    <table class="table">
                                        <thead>
                                            <tr>
												<th>No</th>
                                                <th>Job Description Title</th>
                                                <th  style="white-space:nowrap;">Detail</th>
												<th  style="white-space:nowrap;">Skills</th>
                                               
												<th  style="white-space:nowrap;">Update</th>
												<th  style="white-space:nowrap;">Delete</th>
												
												<th  style="white-space:nowrap;">Enter Email of User</th>
												<th  style="white-space:nowrap;">Download Report</th>
                                            </tr>
                                        </thead>
                                        <tbody>
					<tbody>
						                     
						                       <c:forEach  items="${descs}" var="des" varStatus="loop">   
						                      	<tr>

										<td>${loop.count}</td>		

												
						                      		<td><c:out value="${des.name}"></c:out>  </td>
										
						                      		<td> ${des.description}</td>
						                      		<td>${des.skillsDisplay}   </td>
						                      		
						                      		<td><a  href="addJobDesc?jid=${des.id}">Click </a>   </td>
						                      		<td><a  href="javascript:confirm('${deleteJobdesc.id}')">Click </a> </td>
													
													<td><input type="text" id="${des.id}" name="${des.id}"/></td>
					<td><a  href="javascript:downloadTestSpecificReport('${des.id}', document.getElementById('${des.id}').value)">Click </a> </td>
						                      	</tr>
						                      	</c:forEach>   
						                      </tbody>
                                           
                                        </tbody>
                                    </table>
                                </div>

                            </div>

                        </div>

                    </div>
                    <!-- /main -->
                </div>
            </div>

        </div>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>



        <script>
        
	    
	    $('#search').on('click',function(){
	    var text = document.getElementById("searchText").value;
		if(text.length != 0 && text.length > 2){
		window.location="searchjobDescriptions?searchText="+text;
		}
	    });
	    
	  
	
	
	
	
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
		
		function validateEmail(email) {
		  var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		  return re.test(email);
		}
		
		function downloadTestSpecificReport(id, email){
			if(email == null){
				notify("Information", "Enter an email to download the report");
			}
			else if(validateEmail(email)){
				var url = "verify?email="+encodeURIComponent(email)+"&jid="+id;
				//console.log('here url '+url);
				$.ajax({
						url : url,
						success : function(data) {
						console.log("SUCCESS: ", data);
							if(data == "not ok"){
								notify("Information", "No record of above user (email) having given the test exist! Try a valid email.");
							}
							else{
								window.location = "downloadTestSpecificReport?jid="+id+"&email="+encodeURIComponent(email);
							}
							
						},
						error : function(e) {
							console.log("ERROR: ", e);
							notify("Information", "Contact System Admin. Try Later!");
						}
					});
				
				
				
			}
			else{
				notify("Information", "Enter a valid email to download the report");
			}
		}
		
		function confirm(id) {
           (new PNotify({
		    title: 'Confirmation Needed',
		    text: 'Are you sure? Do you really want to delete this Job Description?',
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
		    window.location = "deleteJobdesc?jid="+id;
		}).on('pnotify.cancel', function() {
		   
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
