<?php 
include_once 'cone.php';

//Setar os dados
$nome= $_POST['nome'];
$id_centro_de_beleza= $_POST['id_centro_de_beleza'];
//Inserindo dados 
$add= $con->prepare("INSERT INTO funcionario SET nome=:nome, id_centro_de_beleza=:id_centro_de_beleza");
$add->bindParam(':nome',$nome);
$add->bindParam(':id_centro_de_beleza',$id_centro_de_beleza);
$add->execute();
if ($add) {
	echo "Registro_Ok";
}else{
	echo "Registro_Erro";
}

?>