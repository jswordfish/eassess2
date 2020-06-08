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
        
        <link href="css/font-awesome.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link href="css/responsive.css" rel="stylesheet" type="text/css">

	
	

	
	
	
	
	
	<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>
	<script type="text/javascript" src="scripts/custom.js"></script>
	
	 <link href="css/font-awesome.css" rel="stylesheet" type="text/css">
	<script>
	function goback(){
	window.location = "goback";
	}
	</script>
	
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
                    <div class="column col-sm-10 col-xs-11" id="main" style="overflow-x:scroll;overflow-y:scroll;">

                        <div class="rightside" >

                            

                            <div class="rightdivTenant" >
                                <h2class="heading">Create New Test Link</h2>
                                <div class="addqueform" >
                                     <form name="linkForm"  method="post" modelAttribute="link" action="saveTestLink">
                                       

                                       
					
					<div class="formfield">
                                            <div class="selectoptions">
                                             

                                                <div id="maindivforaddmore">
                                                    <div class="option">
                                                   <label>Select Test</label>
                                            
							<form:select id="testName" path="link.testName">
								<form:options items="${tests}"  />
							</form:select>
							<form:hidden path="link.id" />
                                                    </div>
                                                    <div class="option">
                                                        <label>Link Start Date and Time</label>
														<div class='input-group date' id='datetimepicker1'>
									<form:input path="link.stDate" class="form-control" required="true" />
															<span class="input-group-addon">
																<span class="glyphicon glyphicon-calendar"></span>
															</span>
														</div>
                                                    </div>
                                                    <div class="option">
                                                        <label>Link End Date and Time</label>
														<div class='input-group date' id='datetimepicker2'>
							<form:input path="link.edDate" class="form-control" required="true" />
															<span class="input-group-addon">
																<span class="glyphicon glyphicon-calendar"></span>
															</span>
														</div>
                                                    </div>
                                                    
						    
						     <div class="formfield savebtn">
						    <input class="save" type="submit" value="Save Test Link">
						
						    <input type="button" value="Cancel" onClick="location.href='listTestLinks';">
						</div>
                                                </div>
                                            </div>
				
                                            
                                        </div>

                                       
					
					

                                    </form>
                                </div>

                            </div>

                        </div>

                    </div>
                    <!-- /main -->
                    <!-- /main -->
                </div>
            </div>
   
        </div>
 

     

        <script>
            /* off-canvas sidebar toggle */
            $('[data-toggle=offcanvas]').click(function () {
                $('.row-offcanvas').toggleClass('active');
                $('.collapse').toggleClass('in').toggleClass('hidden-xs').toggleClass('visible-xs');
            });
        </script>

        	<!-- -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/momentjs/2.14.1/moment.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/js/bootstrap-datetimepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.37/css/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<!-- -->

        <script>
            $(function () {
				
				
                $('#datetimepicker1').datetimepicker({
					format: 'DD/MM/YYYY hh:mm a',
					//minDate:new Date()
					
				});
                
				var date = new Date();

				// add a day
				date.setDate(date.getDate() + 5);
				
                 $('#datetimepicker2').datetimepicker({
					format: 'DD/MM/YYYY hh:mm a',
					//minDate:date
				});
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
		
		function confirm(id) {
           (new PNotify({
		    title: 'Confirmation Needed',
		    text: 'Are you sure? Do you really want to delete this Q?',
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
		    window.location = "removeQuestion?qid="+id;
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
