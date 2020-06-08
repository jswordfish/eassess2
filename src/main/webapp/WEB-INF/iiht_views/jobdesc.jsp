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
	<script>
	function goback(){
	window.location = "goback";
	}
	</script>
	
	<style>
	.rightside .rightdivTenant{
	    width: 100%;
	    float: left;
	    background-color: #eef5fb;
	    padding-top: 12px;
	    padding-left: 12px;
	    padding-right: 50px;
	    /*padding-bottom: 50px;*/ 
	    padding-bottom: 0px; 
	}
	.rightside .rightdivTenant h3.heading{
	    font-size: 46px;
	    line-height: 22px;
	    color: rgb(51,51,51);
	    font-family: "Segoe UI";
	    font-weight: 400;
	    padding-bottom: 50px;
	}

	.rightside .rightdivTenant .addqueform{
	    float: left;
	    width: 100%;
	}
	.rightside .rightdivTenant .addqueform .formfield{
	    float: left;
	    width: 100%;
	    padding-top: 10px;
	    padding-bottom: 10px;
	}
	.rightside .rightdivTenant .addqueform label{
	    float: left;
	    width: 100%;
	    font-size: 12px;
	    color: rgb(0,0,0);
	    font-family: "Segoe UI";
	    padding-bottom: 9px;
	}
	
	.rightside .rightdivTenant .addqueform select{
	    background-color: #fff;
	    border: 2px solid #8f9396;
	    padding: 5px;
	    width: 45%;
	    font-size: 13px;
	    color: rgb(0,0,0);
	    font-family: "Segoe UI";
	}
	.rightside .rightdivTenant .addqueform select.quequery{
	    background-color: #bfc4ca;
	    border: none;
	}

	.rightside .rightdivTenant .addqueform .selectoptions{
	    width: 75%;
	}
	.rightside .rightdivTenant .addqueform .selectoptions span{
	    font-size: 15px;
	    color: rgb(0,0,0);
	    font-family: "Segoe UI";
	    padding-bottom: 15px;
	}
	.rightside .rightdivTenant .addqueform .selectoptions .option{
	    float: left;
	    width: 100%;
	    padding-bottom: 9px;
	}
	.rightside .rightdivTenant .addqueform .selectoptions .option .choice{
	    float: right;
	}
	.rightside .rightdivTenant .addqueform .selectoptions .option span{
	    width: 10%;
	    float: left;
	}
	.rightside .rightdivTenant .addqueform .selectoptions .option input[type="text"]{
	    border: 2px solid #8f9396;
	    padding: 5px;
	    color: rgb(0,0,0);
	    font-family: "Segoe UI";
	    width: 60%;
	}

	</style>
	
    </head>
    <body>

        <div class="maincontainer">            

            <div class="wrapper">
                <div class="row row-offcanvas row-offcanvas-left">
                    <!-- sidebar -->
                   <jsp:include page="side.jsp" /> 
                    <!-- /sidebar -->

                    <!-- main right col -->
                    <div class="column col-sm-10 col-xs-11" id="main" style="overflow-x:scroll;overflow-y:scroll;">

                        <div class="rightside" >

                            

                            <div class="rightdivTenant" >
                                <h2class="heading">Create New Job Description</h2>
                                <div class="addqueform" >
                                     <form:form name="jdForm"  method="post" modelAttribute="jobDescription" action="saveJobdesc">
                                       

                                       
					
					<div class="formfield">
                                            <div class="selectoptions">
                                             

                                                <div id="maindivforaddmore">
                                                    <div class="option">
                                                   <label>Job Description Title</label>
                                            
							<form:input path="name" name="name" id="name" required="true"/>
                                                    </div>
                                                    <div class="option">
                                                        <label>Description</label>
                                                       <form:textarea  path="description" name="description" id="description" required="true"/>
                                                    </div>
                                                    <div class="option">
                                                        <label>Select test</label>
                                            
														<form:select id="testName" path="testName">
															<form:options items="${tests}"  />
														</form:select>
                                                    </div>
                                                    
						    
												<div class="option">
                                                        <label>Select Skills and Associate Weight</label>
                                     <table class="table">    
									<thead>
                                            <tr>
												<th>Qualifier1</th>
                                                <th>Qualifier2</th>
                                                <th>Qualifier3</th>
												<th>Qualifier4</th>
                                               
												<th>Qualifier5</th>
												<th>Select for JD</th>
												<th>Weight</th>
												
												
                                            </tr>
                                        </thead>
										
										
									<c:forEach var="param" items="${jobDescription.params}" varStatus="status">
									<tr>
	<td><form:input path="params[${status.index}].qualifier1" style="width:100%;" value="${param.qualifier1}" readonly="true" /></td>
	<td><form:input path="params[${status.index}].qualifier2"  style="width:100%;" value="${param.qualifier2}" readonly="true" /></td>
	<td><form:input path="params[${status.index}].qualifier3" style="width:100%;" value="${param.qualifier3}" readonly="true" /></td>
	<td><form:input path="params[${status.index}].qualifier4" style="width:100%;" value="${param.qualifier4}" readonly="true" /></td>
	<td><form:input path="params[${status.index}].qualifier5" style="width:100%;" value="${param.qualifier5}" readonly="true" /></td>
	
	<td> <form:checkbox path="params[${status.index}].present" /> </td>
	<td><form:input  path="params[${status.index}].weight" value="${param.weight}"  /></td>
									</tr>
		
									</c:forEach>
									</table>
                                                    </div>
						    
						     <div class="formfield savebtn">
						    <input class="save" type="submit" value="Save">
						
						    <input type="button" value="Cancel" onClick="location.href='showjobDescriptions';">
						</div>
                                                </div>
                                            </div>
				
                                            
                                        </div>

                                       
					
					

                                    </form:form>
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
            /* off-canvas sidebar toggle */
            $('[data-toggle=offcanvas]').click(function () {
                $('.row-offcanvas').toggleClass('active');
                $('.collapse').toggleClass('in').toggleClass('hidden-xs').toggleClass('visible-xs');
            });
        </script>

        <script type="text/javascript">
            $(document).on('click', '#addanother', function () {
                var alphabet = nextString($("#maindivforaddmore").children().last().children().first().text());
                $("#maindivforaddmore").append("<div class='option'><span>" + alphabet + "</span><input type='text'><div class='choice'><input name='option' type='radio'><a href='javascript:void(0);' class='removenewdiv'>-</a></div></div>");
            });

            $(document).on('click', '.removenewdiv', function () {
                $(this).parent().parent().remove();
            });

            function nextString(str) {
                if (!str)
                    return 'A'  // return 'A' if str is empty or null

                let tail = ''
                let
                i = str.length - 1
                let
                char = str[i]
                // find the index of the first character from the right that is not a 'Z'
                while (char === 'Z' && i > 0) {
                    i--
                    char = str[i]
                    tail = 'A' + tail   // tail contains a string of 'A'
                }
                if (char === 'Z')   // the string was made only of 'Z'
                    return 'AA' + tail
                // increment the character that was not a 'Z'
                return str.slice(0, i) + String.fromCharCode(char.charCodeAt(0) + 1) + tail
            }

        </script>

        <script>
           
	    
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
