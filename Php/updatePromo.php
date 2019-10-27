<?php 
include_once 'cone.php';

//Setar os dados
$id = $_POST['id'];
$titulo = $_POST['titulo'];
$descricao = $_POST['descricao'];
$data = $_POST['data'];
$id_centro_de_beleza = $_POST['id_centro_de_beleza'];
$img= 0;

    //Inserindo dados 
$add= $con->prepare("UPDATE promocao SET id=:id, titulo=:titulo, descricao=:descricao, data=:data, id_centro_de_beleza=:id_centro_de_beleza, img=:img WHERE id = '$id'");
$add->bindParam(':id',$id);
$add->bindParam(':titulo',$titulo);
$add->bindParam(':descricao',$descricao);
$add->bindParam(':data',$data);
$add->bindParam(':id_centro_de_beleza',$id_centro_de_beleza);
$add->bindParam(':img',$img);
$add->execute();

if ($add) {
	echo "Update_Ok";
}else{
    echo "Update_Erro";
}
?>