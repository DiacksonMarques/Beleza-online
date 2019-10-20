<?php 
include_once 'cone.php';

//Setar os dados
$id_cliente= $_POST['id_cliente'];
$id_centros_de_beleza= $_POST['id_centros_de_beleza'];


//Verificar Usuario
$veri= $con->prepare("SELECT * FROM favorito WHERE id_cliente = '$id_cliente' AND id_centros_de_beleza='$id_centros_de_beleza'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();

if ($numero_de_cadastro>0) {
	echo "Favorito_Sim";
}else{
    echo "Favorito_Nao";
}
?>