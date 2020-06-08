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
        
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="css/font-awesome.css" rel="stylesheet" type="text/css">
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link href="css/responsive.css" rel="stylesheet" type="text/css">
	<link href="css/pnotify.custom.min.css" rel="stylesheet" type="text/css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>
	<script type="text/javascript" src="scripts/custom.js"></script>
	
	 <link href="css/font-awesome.css" rel="stylesheet" type="text/css">
    </head>

    <body>

        <div class="maincontainer">            

            <div class="wrapper">
                <div class="row row-offcanvas row-offcanvas-left">
                    <!-- sidebar -->
		   <jsp:include page="side.jsp" /> 
                 
                    <!-- /sidebar -->

                   <div class="column col-sm-10 col-xs-11" id="main">
                        <div class="rightside">
                            <div class="topmenu text-right">
                               
                               
				<a href="signoff">Sign Off</a>
				
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
                                        <h3>Multi File Assessments Results</h3>
                                    </div>
                                    <div class="right">
                                       
                                        <div class="filter">
                                            <a href="javascript:notify('Information', 'Feature coming soon')"><img src="images/ic_sort.png">Sort</a>
                                            <a href="javascript:notify('Information', 'Feature coming soon')"><img src="images/ic_filter.png">Filter</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="questiontablelist" style="overflow-x:auto;">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                
                                                <th><img src="images/icon-selectionmode.png">Test Name</th>
                                                
                                                <th>Test Giver Name</th>
                                               
												<th>Test Giver Email</th>
												<th>Last Submitted</th>
												
												<th>Check Details</th>
												
                                            </tr>
                                        </thead>
                                         <tbody>
					<tbody>
						                     
						                       <c:forEach  items="${instances}" var="instance" >   
						                      	<tr>
										
												
						                      		<td>${instance.testName} </td>
										
						                      		<td> ${instance.uerFullName}</td>
										<td> ${instance.user}</td>
										<td> ${instance.lastDate}</td>
										
						                <td> <a href="#" onClick = "javascript:openResults('${instance.uerFullName}', '${instance.id}')">Open Results in new Window</a>  </td>
										
							
						                      	</tr>
						                      	</c:forEach>   
						                      </tbody>
                                           
                                        </tbody>
                                    </table>
                                </div>
                                <div>&nbsp;</div>
                                    <div>&nbsp;</div>
                                    <div>&nbsp;</div>
                                    <div>&nbsp;</div>
                                    <div>&nbsp;</div>
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
	
	
	
	function confirm(id) {
           (new PNotify({
		    title: 'Confirmation Needed',
		    text: 'Are you sure you want to delete this Schedule?',
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
		    window.location = "deleteSchedule?schId="+id;
		}).on('pnotify.cancel', function() {
		   
		});
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
		
	function openResults(fullName, instanceId){
		console.log('in openResults');
		var url = 'multiFileResults?instanceId='+instanceId+'&fullname='+encodeURI(fullName);
		var redirectWindow = window.open(url, '_blank');
		console.log('in openResults link open');
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
