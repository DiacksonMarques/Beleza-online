<?php 
include_once 'cone.php';

//Setar os dados
$nome=$_POST['nome'];
$tipo_servico=$_POST['tipo_servico'];
$valor= $_POST['valor'];
$descricao=$_POST['descricao'];
$id_centro_de_beleza=$_POST['id_centro_de_beleza'];

//Verificar Usuario
$veri= $con->prepare("SELECT * FROM servico WHERE nome='$nome' AND id_centro_de_beleza='$id_centro_de_beleza'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();

if ($numero_de_cadastro>0){
    echo "Servico_Erro";
}else{
    
   //Inserindo dados 
$add= $con->prepare("INSERT INTO servico SET nome=:nome, tipo_servico=:tipo_servico, valor=:valor, descricao=:descricao, id_centro_de_beleza=:id_centro_de_beleza");
$add->bindParam(':nome',$nome);
$add->bindParam(':tipo_servico',$tipo_servico);
$add->bindParam(':valor',$valor);
$add->bindParam(':descricao',$descricao);
$add->bindParam(':id_centro_de_beleza',$id_centro_de_beleza);
$add->execute();

if ($add) {
	echo "Registro_Ok";
}else{
    echo "Servico_Erro";
}
}
?>