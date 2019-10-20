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
	//Deletando dados
	$exc= $con->prepare("DELETE FROM favorito WHERE id_cliente = '$id_cliente' AND id_centros_de_beleza='$id_centros_de_beleza'");
	$exc->bindParam(':id_cliente',$id_cliente);
	$exc->bindParam(':id_centros_de_beleza',$id_centros_de_beleza);
	$exc->execute();
	$exclu= $exc -> rowCount();
	if ($exclu>0) {
	    echo "Erro_Apagar";
	}else{
		echo "Apagar_Ok";
	}
}else{
    //Inserindo dados 
	$add= $con->prepare("INSERT INTO favorito SET id_cliente=:id_cliente, id_centros_de_beleza=:id_centros_de_beleza");
	$add->bindParam(':id_cliente',$id_cliente);
	$add->bindParam(':id_centros_de_beleza',$id_centros_de_beleza);
	$add->execute();
	if ($add) {
	    echo "Registro_Ok";
	}else{
		echo "Registro_Erro";
	}
}
?>