<?php 
include_once 'cone.php';

//Setar os dados
$id=$_POST['id'];


    //Deletando dados 
	$add= $con->prepare("DELETE FROM funcionario WHERE id=:id");
	$add->bindParam(':id',$id);
	$add->execute();
	if ($add){
		echo "Deletado_Ok";
	}else{
		echo "Deletado_Erro";
	}

?>