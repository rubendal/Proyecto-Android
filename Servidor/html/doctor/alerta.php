<?php

	if(isset($_POST["paciente"]) && isset($_POST["alerta"])){
		$mysqli = new mysqli("localhost", "android", "android11", "android");
		if ($mysqli->connect_errno) {
			echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
			die();
		}
		$query = "INSERT INTO alerta(id_paciente,alerta) VALUES(%d,'%s');";
		$query = sprintf($query, $_POST['paciente'], $mysqli->real_escape_string($_POST['alerta']));
		if($mysqli->query($query)){
			echo '1';
		}else{
			echo '-1';
		}
		
	}else{
		echo 'Aqui no hay nada';
	}

?>
