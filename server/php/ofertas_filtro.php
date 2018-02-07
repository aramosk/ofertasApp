<?php

if($_GET){
    $ciudad = $_GET['ciudad'];
    $titulo = $_GET['titulo'];
} else if($_POST){
    $ciudad = $_POST['ciudad'];
    $titulo = $_POST['titulo'];
}

$link = mysqli_connect('localhost', 'id3027023_aramosk', 'aramosk', 'id3027023_ofertas');
if (!$link) {
    die('No pudo conectar: ' . mysql_error());
}

mysqli_select_db($link,"id3027023_ofertas");
$q = "";
mysqli_query($link,"SET NAMES utf8");

if ($ciudad != "" and $titulo!= "") {
    $q=mysqli_query($link, "SELECT * FROM ofertas where (CIUDAD='".$ciudad."' AND TITULO like '%".$titulo."%')");
} else if ($titulo!= "") {
    $q=mysqli_query($link, "SELECT * FROM ofertas WHERE TITULO like '%".$titulo."%'");
} else if ($ciudad != "") {
    $q=mysqli_query($link, "SELECT * FROM ofertas WHERE CIUDAD='".$ciudad."'");
} else {
    $q=mysqli_query($link, "SELECT * FROM ofertas");
}

while($e=mysqli_fetch_assoc($q)){
    $output[]=$e;
}
 
print(json_encode($output));
mysqli_close($link);

?>