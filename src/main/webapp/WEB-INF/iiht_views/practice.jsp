<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.assessment.data.*, java.text.*, java.util.*,com.assessment.web.dto.*"%>
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


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="scripts/custom.js"></script>
<script type="text/javascript" src="scripts/pnotify.custom.min.js"></script>

<script type="text/javascript" src="scripts/pnotify.nonblock.js"></script>
<script type="text/javascript" src="scripts/pnotify.buttons.js"></script>

<link href="css/pnotify.custom.min.css" media="all" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="scripts/html2canvas.js"></script>
<script src="scripts/src-min-noconflict/ace.js" type="text/javascript"
	charset="utf-8"></script>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<style>
.answers label label label:last-child {
	position: relative !important;
	left: -15px !important;
	padding-left: 18px !important;
}
</style>



<style>
body * {
	font-family: monospace !important
}
</style>


</head>
<body>

  <form name="testcodingForm" id="testcodingForm"  method="post" modelAttribute="program" action="savePracticeCode">
		<br>
		<a href="yakshacode?companyId=${program.companyId}&user=${program.user}" class="w3-bar-item w3-button" style="font-size:130%;color:blue">New Test Program</a>
		<div id="runsave" style="margin-left: 20%;">
			
					
					
			<label for="cars">Choose Your Language</label> 
			
			<form:select id="langid" path="program.langid" onchange="changeLanguage()">
						  
				 <form:options items="${list}" itemValue="langid" itemLabel="lang" />
			</form:select>
			<form:hidden path="program.id"  />
			
			<form:hidden path="program.lang"  />
			<form:hidden path="program.companyName"  />
			<form:hidden path="program.companyId"  />
			<form:hidden path="program.user"  />
		</div>
		<div class="w3-sidebar w3-light-grey w3-bar-block" style="width: 16%;background-color: #a2b9bc;">
			
			<p style="font-size:130%;color:grey;">Your Saved Programs - ${programs.size()}</p>
			 <c:forEach  items="${programs}" var="program" varStatus="loop">   
			 <a href="yakshacode?companyId=${program.companyId}&user=${program.user}&testid=${program.id}" class="w3-bar-item w3-button" style="font-size:100%;color:blue">${program.name}</a>
			 </c:forEach>
		
			
		</div>

		<div id="runsave" style="margin-left: 20%;">
			<label>Code</label>
							
		<form:textarea id="editor" path="program.code" wrap="physical" style="width:75%"/>
		<form:hidden path="program.code" id="codeOfEditor" wrap="physical"/>
			<br> <br>
			
			<form:input path="program.input"  style="width:75%" id="program.input" placeholder="Enter input"/>
			<br> <br>
			<form:textarea  style="overflow-y: scroll;rows:16;width:75%" path="program.output"   id="program.output" disabled="true"/>
			<br> <br>
			<a href="javascript:runCode();" align="center">Compile and RUN</a> 
			<a href="javascript:saveCode();" align="center">Save Code</a> 
			
			
			<br> <br>
			

		</div>

		<br>
	</form>
<script type="text/javascript">
function changeLanguage(){
	
	 var lid = document.getElementById("langid").value;
	 var plid = '${program.langid}';
		if('${program.id}' != ''){
			if(lid != plid){
				notify('Information', 'You can not change a language for a saved Program. Click New Practice Code to code in a different language'); 
				document.getElementById("langid").value = plid;
				return;
			}
		}
	//alert(lid);
	window.location="yakshacode?langid="+lid+"&companyId=${program.companyId}&user=${program.user}";
}
</script>

<script>
	    var editor = ace.edit("editor");
	    editor.setTheme("ace/theme/tomorrow_night_blue");
	   //editor.setTheme("ace/theme/theme-github");
	   //editor
	   var code = '${program.code}';
	   editor.setValue(code);
	   var textarea = document.getElementById('codeOfEditor');
 		edt = editor.getSession().getValue();
		textarea.value = edt;
	   
	   var language = '${current.lang}';
		if(language == 'Java'){
			editor.session.setMode("ace/mode/java");
		}
		else if(language == 'C/C++'){
			editor.session.setMode("ace/mode/c_cpp");
		}
		else if(language == 'C#'){
			editor.session.setMode("ace/mode/csharp");
		}
		else if(language == 'Python'){
			editor.session.setMode("ace/mode/python");
		}
		else if(language == 'PHP'){
			editor.session.setMode("ace/mode/php");
			
		}
		else if(language == 'Plain JavaScript'){
			editor.session.setMode("ace/mode/javascript");
		}
		else if(language == 'Clojure'){
			editor.session.setMode("ace/mode/clojure");
		}
		else if(language == 'Go'){
			editor.session.setMode("ace/mode/golang");
		}
		else if(language == 'Bash'){
			editor.session.setMode("ace/mode/batchfile");
		}
		else if(language == 'Objective-C'){
			editor.session.setMode("ace/mode/objectivec");
		}
		else if(language == 'Perl'){
			editor.session.setMode("ace/mode/perl");
		}
		else if(language == 'Rust'){
			editor.session.setMode("ace/mode/rust");
		}
		else if(language == 'Ruby'){
			editor.session.setMode("ace/mode/ruby");
		}
		else if(language == 'VB.NET'){
			editor.session.setMode("ace/mode/csharp");
		}
		
	    
		
		//editor.setValue('//start coding');
	    editor.setOptions({
	   enableBasicAutocompletion: true, // the editor completes the statement when you hit Ctrl + Space
	   enableLiveAutocompletion: true, // the editor completes the statement while you are typing
	   showPrintMargin: false, // hides the vertical limiting strip
	   maxLines: Infinity,
	   fontSize: "100%" // ensures that the editor fits in the environment
	});
	
	
	function saveCode(){
		var textarea = document.getElementById('codeOfEditor');
 		edt = editor.getSession().getValue();
		textarea.value = edt;
		document.testcodingForm.submit();
	}
	
	function runCode(){
	//editor
	//var editor = ace.edit("editor");
	var code = editor.getValue();
	var input = document.getElementById('program.input').value;
	var lang = '8';
	var language = '${current.langid}';
		
	
	var url = 'compile';
	var data = {}; 
	data.language = lang;
	data.code = code;
	data.stdin = input;
	//var json = "{language:"+lang+", code:"+code+", stdin:"+input+"}";
	document.getElementById('program.output').value = 'Executing your code...';	
	document.getElementById("program.output").focus();
	document.getElementById("program.output").scrollIntoView();
	dta = JSON.stringify(data);
	//dta = dta.slice(1,-1);
	//dat = "'"+dta+"'";
		$.ajax({
				type : 'POST',
				url : url,
				contentType: "application/json; charset=utf-8",
				data: dta,
				success : function(data) {
					
					document.getElementById('program.output').value = data;
					//document.getElementById('output').value = 'eee';
					console.log("SUCCESS: ", data);
				},
				error : function(e) {
					console.log("ERROR: ", e);
					
				}
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

	<style>
	
	
	
	.ace_editor div {
	    font: inherit!important;
		
	}
	
	  
	    
    #editor {
	    font-family:monospace;
		
	}
		
	.ace_editor {
	    font-family:monospace!important;
		width: 75%;
		height:600px;
	}
	
	editor.container.style.fontFamily = "monospace";
	</style>
	


</body>
</html>
