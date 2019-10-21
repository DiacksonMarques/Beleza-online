<?php 
include_once 'cone.php';

//Setar os dados
$id =$_POST['id'];
$nome= $_POST['nome'];
$id_centro_de_beleza=$_POST['id_centro_de_beleza'];

    //Inserindo dados 
$add= $con->prepare("UPDATE funcionario SET id=:id, nome=:nome, id_centro_de_beleza=:id_centro_de_beleza WHERE id = '$id'");
$add->bindParam(':id',$id);
$add->bindParam(':nome',$nome);
$add->bindParam(':id_centro_de_beleza',$id_centro_de_beleza);
$add->execute();

if ($add) {
	echo "Update_Ok";
}else{
    echo "Update_Erro";
}
?>