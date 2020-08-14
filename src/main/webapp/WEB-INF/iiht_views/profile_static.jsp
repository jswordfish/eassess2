<!DOCTYPE HTML>
<html>
<head>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<script type="text/javascript">
window.onload = function () {

var chart = new CanvasJS.Chart("chartContainer", {
	theme: "light1", // "light2", "dark1", "dark2"
	animationEnabled: true, // change to true		
	title:{
		text: "Skill Profile for Ayush"
	},
	data: [
	{
		// Change type to "bar", "area", "spline", "pie",etc.
		type: "column",
		dataPoints: [
			{ label: "Data Statistics%",  y: 10  },
			{ label: "Deep Learning%", y: 60  },
			{ label: "Basics%", y: 60  },
			{ label: "Continuous Delivery%",  y: 30  },
			{ label: "Information security%",  y: 80  },
			{ label: "Automation%",  y: 40  },
			{ label: "Bots Programming%",  y: 55  },
			{ label: "Cloud Programming%",  y: 65  },
			{ label: "ML Basics%",  y: 52  }
			
			
			
			
		]
	}
	]
});
chart.render();

}
</script>

<style>

.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    padding: 8px;
    line-height: 1.42857143;
    vertical-align: top;
    border-top: 1px solid #ddd;
    padding-left: 20px;
}
</style>
</head>
<body>
<div id="chartContainer" style="height: 470px; width: 100%;"></div>
<div class="container">
<table class="table table-bordered" style="width: 100%">
  <thead class="black white-text">
    <tr>
      <th scope="col">Skill</th>
      <th scope="col">Level 1</th>
      <th scope="col">Level 2</th>
      <th scope="col">Recommendations</th>
    </tr>
  </thead>
  <tbody>
    <tr style="background-color: #8080ff;">
      <td>Artificial Intelligence</td>
      <td>Data Statistics</td>
      <td>Combinatorics and basic set theory notation</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
    <tr style="background-color: #8080ff;">
      <td>Artificial Intelligence</td>
      <td>Data Statistics</td>
      <td>Probability definitions</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
    <tr style="background-color: #8080ff;">
      <td>Artificial Intelligence</td>
      <td>Data Statistics</td>
      <td>Bivariate distributions.</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
	
	<tr style="background-color: #80bfff;">
      <td>Artificial Intelligence</td>
      <td>Basics</td>
      <td>Reinforcement Learning</td>
      <td>Pretty good. Continue with the momentum and you can be the best.</td>
    </tr>
    <tr style="background-color: #80bfff;">
      <td>Artificial Intelligence</td>
      <td>Basics</td>
      <td>Natural Language Processing</td>
      <td>Pretty good. Continue with the momentum and you can be the best.</td>
    </tr>
    <tr style="background-color: #80bfff;">
      <td>Artificial Intelligence</td>
      <td>Basics</td>
      <td>Recommender Systems</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
	
	<tr style="background-color: #1ad1ff;">
      <td>Artificial Intelligence</td>
      <td>Continuous Delivery</td>
      <td>CD pipelines</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
    <tr style="background-color: #1ad1ff">
      <td>Artificial Intelligence</td>
      <td>Continuous Delivery</td>
      <td>Testing</td>
      <td>Pretty good. Continue with the momentum and you can be the best.</td>
    </tr>
    <tr style="background-color: #1ad1ff">
      <td>Artificial Intelligence</td>
      <td>Continuous Delivery</td>
      <td>Microservices</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
	
	
	
	<tr style="background-color: #1affff">
      <td>Artificial Intelligence</td>
      <td>Information Security</td>
      <td>Privacy</td>
      <td>Pretty good. Continue with the momentum and you can be the best.</td>
    </tr>
    <tr style="background-color: #1affff">
      <td>Artificial Intelligence</td>
      <td>Information Security</td>
      <td>Malvare</td>
      <td>Excellent Knowledge of  the topic. You are amongst the best!</td>
    </tr>
    <tr style="background-color: #1affff">
      <td>Artificial Intelligence</td>
      <td>Information Security</td>
      <td>Online Scams</td>
      <td>Pretty good. Continue with the momentum and you can be the best.</td>
    </tr>
	
	<tr style="background-color: #d9b3ff">
      <td>Artificial Intelligence</td>
      <td>Automation</td>
      <td>Building Automation</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
    <tr style="background-color: #d9b3ff">
      <td>Artificial Intelligence</td>
      <td>Automation </td>
      <td>Communications</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
    <tr style="background-color: #d9b3ff">
      <td>Artificial Intelligence</td>
      <td>Automation</td>
      <td>Control Systems</td>
      <td>On par with the average crowd. Can go much better. Need to work hard on understanding applicability of topics to real life problems.</td>
    </tr>
	
	<tr style="background-color: #4dff88">
      <td>Artificial Intelligence</td>
      <td>Bots Programming</td>
      <td>Limbo </td>
      <td>On par with the average crowd. Can go much better. Need to work hard on understanding applicability of topics to real life problems.</td>
    </tr>
    <tr style="background-color: #4dff88">
      <td>Artificial Intelligence</td>
      <td>Bots Programming </td>
      <td>python-rtmbot </td>
      <td>On par with the average crowd. Can go much better. Need to work hard on understanding applicability of topics to real life problems.</td>
    </tr>
    <tr style="background-color: #4dff88">
      <td>Artificial Intelligence</td>
      <td>Bots Programming</td>
      <td>Errbot </td>
      <td>On par with the average crowd. Can go much better. Need to work hard on understanding applicability of topics to real life problems.</td>
    </tr>
	
	<tr style="background-color: #b3ffb3">
      <td>Artificial Intelligence</td>
      <td>Cloud Programming</td>
      <td>Virtualisation</td>
      <td>Pretty good. Continue with the momentum and you can be the best.</td>
    </tr>
    <tr style="background-color: #b3ffb3">
      <td>Artificial Intelligence</td>
      <td>Cloud Programming </td>
      <td>Cloud cryptography</td>
      <td>On par with the average crowd. Can go much better. Need to work hard on understanding applicability of topics to real life problems.</td>
    </tr>
    <tr style="background-color: #b3ffb3">
      <td>Artificial Intelligence</td>
      <td>Cloud Programming</td>
      <td>Load balancing</td>
      <td>Pretty good. Continue with the momentum and you can be the best.</td>
    </tr>
	
	<tr style="background-color: #ffffb3">
      <td>Artificial Intelligence</td>
      <td>ML Basics</td>
      <td>Neural Net Time Series</td>
      <td>On par with the average crowd. Can go much better. Need to work hard on understanding applicability of topics to real life problems.</td>
    </tr>
    <tr style="background-color: #ffffb3">
      <td>Artificial Intelligence</td>
      <td>ML Basics </td>
      <td>Ensemble Learning</td>
      <td>Need to work on concepts. Need to work hard on fundamentals related to the topics.</td>
    </tr>
    <tr style="background-color: #ffffb3">
      <td>Artificial Intelligence</td>
      <td>ML Basics</td>
      <td>Imbalanced Learning</td>
      <td>On par with the average crowd. Can go much better. Need to work hard on understanding applicability of topics to real life problems.</td>
    </tr>
  </tbody>
</table>
</div>

<input type="button" class="btn btn-info" value="Input Button" onclick=" relocate_home()">

<script>
function relocate_home()
{
     location.href = "www.yoursite.com";
} 
</script>
<script src="https://canvasjs.com/assets/script/canvasjs.min.js"> </script>
</body>
</html>