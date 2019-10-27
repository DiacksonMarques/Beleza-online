<?php 
include_once 'cone.php';

//Setar os dados
$titulo= $_POST['titulo'];
$descricao= $_POST['descricao'];
$data= $_POST['data'];
$id_centro_de_beleza= $_POST['id_centro_de_beleza'];
$img= 0;

    //Inserindo dados 
	$add= $con->prepare("INSERT INTO promocao SET titulo=:titulo, descricao=:descricao, data=:data, id_centro_de_beleza=:id_centro_de_beleza, img=:img");
	$add->bindParam(':titulo',$titulo);
	$add->bindParam(':descricao',$descricao);
	$add->bindParam(':data',$data);
	$add->bindParam(':id_centro_de_beleza',$id_centro_de_beleza);
	$add->bindParam(':img',$img);
	$add->execute();
	if ($add){
		echo "Registro_Ok";
	}else{
		echo "Registro_Erro";
	}
?>