<?php

$codigoCompra = "";
$idOwner = "";
$idOferta = "";

if($_GET){
    $codigo = $_GET['codigo'];
    $idOwner = $_GET['idOwner'];
    $idOferta = $_GET['idOferta'];
} else if($_POST){
    $codigo = $_POST['codigo'];
    $idOwner = $_POST['idOwner'];
    $idOferta = $_POST['idOferta'];
}


$link = mysqli_connect('localhost', 'id3027023_aramosk', 'aramosk', 'id3027023_ofertas');
if (!$link) {
    die('No pudo conectar: ' . mysql_error());
}

mysqli_select_db($link, "id3027023_ofertas");
$q = "";
mysqli_query($link, "SET NAMES utf8");

if ($codigo != "" and $idOwner != "" and $idOferta != "") {
    $q=mysqli_query($link, "INSERT INTO compras(CODIGO, OWNER_ID, OFERTA_ID) VALUES ('".$codigo."', ".intval($idOwner).", ".intval($idOferta).")");
    if($q) {
        $output[] = Array('RESULT'=>'insertado');
    } else {
        $output[] = Array('RESULT'=>'error');
    }
} 
print(json_encode($output));
mysqli_close($link);
?>