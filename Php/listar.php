<?php 
include_once 'cone.php';

//Setar os dados
$data = $_POST['data'];
//Verificar Usuario
$veri= $con->prepare("SELECT * FROM agendar WHERE data = '$data'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();
echo $numero_de_cadastro;
?>