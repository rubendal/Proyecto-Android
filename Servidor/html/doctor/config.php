<?php

	if(isset($_POST["id"])){
		$mysqli = new mysqli("localhost", "android", "android11", "android");
		if ($mysqli->connect_errno) {
			echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
			die();
		}
		$query = "SELECT id FROM pacientes WHERE id = %d;";
		$query = sprintf($query, $_POST['id']);
		$res = $mysqli->query($query);
		if($row = $res->fetch_assoc()){
			echo '1';
		}else{
			echo '-1';
		}
		
	}else{
		echo 'Aqui no hay nada';
	}
	
	
	
?>