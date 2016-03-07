<?php

	if(isset($_POST["nombre"]) && isset($_POST["telefono"]) && isset($_POST["edad"]) && isset($_POST["genero"])&& isset($_POST["contacto"])&& isset($_POST["telefono_contacto"])&& isset($_POST["id_doctor"])){
		$mysqli = new mysqli("localhost", "android", "android11", "android");
		if ($mysqli->connect_errno) {
			echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
			die();
		}
		$query = "INSERT INTO pacientes(nombre,telefono,edad,genero,contacto_emergencia,telefono_emergencia,id_doctor) VALUES('%s','%s',%d,%d,'%s','%s',%d);";
		$query = sprintf($query, $mysqli->real_escape_string($_POST['nombre']), $mysqli->real_escape_string($_POST['telefono']), $_POST["edad"], $_POST["genero"],$mysqli->real_escape_string($_POST['contacto']),$mysqli->real_escape_string($_POST['telefono_contacto']), $_POST['id_doctor']);
		if($mysqli->query($query)){
			echo '1';
		}else{
			echo '-1';
		}
		
	}else{
		echo 'Aqui no hay nada';
	}

?>