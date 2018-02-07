<?php

$link = mysqli_connect('localhost', 'id3027023_aramosk', 'aramosk', 'id3027023_ofertas');
if (!$link) {
    die('No pudo conectar: ' . mysql_error());
}

mysqli_select_db($link, "id3027023_ofertas");
$q = "";
mysqli_query($link, "SET NAMES utf8");

$q=mysqli_query($link, "SELECT * FROM ofertas");

while($e=mysqli_fetch_assoc($q)){
	$output[]=$e;
}
print(json_encode($output));
mysqli_close($link);
?>