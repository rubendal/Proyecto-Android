<?php

	if(isset($_POST['id_paciente'])){
		$mysqli = new mysqli("localhost", "android", "android11", "android");
		if ($mysqli->connect_errno) {
			echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
			die();
		}
		
		$query = "SELECT * FROM valores WHERE id_paciente = %d";
		$query = sprintf($query, $_POST['id_paciente']);
		$res = $mysqli->query($query);
		
		while($row=$res->fetch_assoc()){
			$output[]=$row;	
		}
		print(json_encode($output));
	}

?>
