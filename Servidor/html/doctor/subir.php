<?php

	if(isset($_POST["id_paciente"]) && isset($_POST["valor"]) && isset($_POST["pasos"]) && isset($_POST["timestamp"])){
		$mysqli = new mysqli("localhost", "android", "android11", "android");
		if ($mysqli->connect_errno) {
			echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
			die();
		}
		$query = "INSERT INTO valores(id_paciente,timestamp,valor,pasos) VALUES(%d,'%s',%d,%d);";
		$query = sprintf($query, $_POST["id_paciente"],$mysqli->real_escape_string($_POST['timestamp']),$_POST["valor"],$_POST["pasos"]);
		if($mysqli->query($query)){
			echo '1';
		}else{
			echo '-1';
		}
		
	}else{
		echo 'Aqui no hay nada';
	}

?>