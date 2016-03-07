<?php

	if(isset($_POST['id_doctor'])){
		$mysqli = new mysqli("localhost", "android", "android11", "android");
		if ($mysqli->connect_errno) {
			echo "Failed to connect to MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
			die();
		}
		
		$query = "SELECT * FROM pacientes WHERE id_doctor = %d";
		$query = sprintf($query, $_POST['id_doctor']);
		$res = $mysqli->query($query);
		
		while($row=$res->fetch_assoc()){
			$output[]=$row;	
		}
		print(json_encode($output));
	}

?>