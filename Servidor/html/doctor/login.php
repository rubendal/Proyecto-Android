<?php

	if(isset($_POST["username"]) && isset($_POST["password"])){
		$mysqli = new mysqli("localhost", "android", "android11", "android");
		if ($mysqli->connect_errno) {
			echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
			die();
		}
		$query = "SELECT id,username FROM doctores WHERE username = '%s' AND password = '%s';";
		$query = sprintf($query, $mysqli->real_escape_string($_POST['username']), $mysqli->real_escape_string($_POST['password']));
		$res = $mysqli->query($query);
		if($row = $res->fetch_assoc()){
			echo $row['id'];
		}else{
			echo '-1';
		}
		
	}else{
		echo 'Aqui no hay nada';
	}
	
	
	
?>