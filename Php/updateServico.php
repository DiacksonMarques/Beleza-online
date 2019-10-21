<?php 
include_once 'cone.php';

//Setar os dados
$id =$_POST['id'];
$nome= $_POST['nome'];
$tipo_servico= $_POST['tipo_servico'];
$valor= $_POST['valor'];
$descricao = $_POST['descricao'];
$id_centro_de_beleza=$_POST['id_centro_de_beleza'];

    //Inserindo dados 
$add= $con->prepare("UPDATE servico SET id=:id, nome=:nome, tipo_servico=:tipo_servico, valor=:valor, descricao=:descricao, id_centro_de_beleza=:id_centro_de_beleza WHERE id = '$id'");
$add->bindParam(':id',$id);
$add->bindParam(':nome',$nome);
$add->bindParam(':tipo_servico',$tipo_servico);
$add->bindParam(':valor',$valor);
$add->bindParam(':descricao',$descricao);
$add->bindParam(':id_centro_de_beleza',$id_centro_de_beleza);
$add->execute();

if ($add) {
	echo "Update_Ok";
}else{
    echo "Update_Erro";
}
?>