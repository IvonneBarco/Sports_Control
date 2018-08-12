<?PHP

$hostname_localhost="localhost";
$database_localhost="sporte";
$username_localhost="root";
$password_localhost="";

$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

	$id = null;
	$lugar = $_POST["gps"];
	$entre = $_POST["entrenoprogramado_id"];
	$descrip = $_POST["descripcion"];
	$imagen = $_POST["imagen"];

	$sql_id ="SELECT id FROM entrenos ORDER BY id ASC";

	$res = mysqli_query($conexion,$sql_id);

	$id_img = 0;

	while($row = mysqli_fetch_array($res)){
		$id_img = $row['id'];
	}

	$id_img = $id_img+1;

	$path = "imagenes/$id_img.jpg";

	$url = "imagenes/".$id_img.".jpg";

	file_put_contents($path,base64_decode($imagen));
	$bytesArchivo=file_get_contents($path);


	//
	$hoy= getdate();

            $fecha = ($hoy['year']."-".$hoy['mon']."-".$hoy['mday']);
            $hinicio = ($hoy['hours'].":".$hoy['minutes'].":".$hoy['seconds']);
            $hfin = ($hoy['hours'].":".$hoy['minutes'].":".$hoy['seconds']);

	//

	$sql="INSERT INTO entrenos VALUES (?,?,?,?,?,?,?,?,?)";
	$stm=$conexion->prepare($sql);
	$stm->bind_param('issssssss',$id,$hinicio,$hfin,$fecha,$lugar,$entre,$descrip,$bytesArchivo,$url);


	if($stm->execute()){
		echo "registra";

	}else{
		echo "noRegistra";
	}

	mysqli_close($conexion);


?>
