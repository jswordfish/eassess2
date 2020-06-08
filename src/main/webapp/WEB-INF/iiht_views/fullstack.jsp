<html>

<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>

<style>
.id_description_iframe.GFO5DA1BOU {
    height: 34px;
    box-sizing: border-box;
    display: inline-flex;
    width: 100%;
    background-color: black;
    color: #e4e4e4;
	display:none;
}

.id_description_iframe.GFO5DA1BNU {
    float: left;
    height: inherit;
    white-space: nowrap;
    background-color: #242a3a;
    display: none;
}

.id_description_iframe.GFO5DA1BAN {
display: none;
}

#chenavmenu{
display: none; 
}
.iframesidebanner{
    position: absolute;
    left: 0;
    top: 1px;
    float: left;
    width: 200px;
    height: 800px;
    background-color: #282e44;
    z-index: 999;
    padding: 0px;
}
.iframesidebanner p{
    color: #fff;
    font-size: 16px;
    padding-top: 50px;
    text-align: center;
    padding: 5px;
    padding-top: 50px;
}
</style>

<script>
    
setTimeout(function() {
    $('.iframesidebanner').fadeOut('fast');
}, 180000); // <-- 15 sec    
    
    
$(".che-dashboard").hide();
function callfn(){
//var menu = document.getElementById("id_description_iframe.chenavmenu");
//menu.style.display = "none";

//r iframe = document.getElementById("id_description_iframe");
//var elmnt = iframe.contentWindow.document.getElementsByTagName("md-sidenav")[0];
 //lmnt.style.display = "none";
//ar elmnt = iframe.contentWindow.document.getElementsByTagName("butt")[0];
 //lmnt.style.display = "none";
 
 var head = $('id_description_iframe').contents().find("head");
 head.append($("<style type='text/css'>  .body {background-color: red;}  </style>"));
  /*head.append(
     $("<style type='text/css'>"+
       "#chenavbar {"+
        "display: none
       "} "+
       "#che-nav-bar {display:none} </style>")
   );*/

}

</script>
   
</head>

<body>


<div id="contentframe" style="position:fixed; top: 35">

    <div class="iframesidebanner">
        <p>Workspace loading in progress. Usage can commence in 3 minutes..</p>
    </div>
<iframe src="${url}" width="1400" height="800" id="id_description_iframe" name="id_description_iframe" ></iframe>



<div style="position:fixed; top: 20">
	</br>
<input type="label" value="IIHT" style="color:red;height:30;width:70" readOnly/>


</div> 
</div>
</body>

</html>


