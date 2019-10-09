<?php 
include_once 'cone.php';

//Setar os dados
$data= $_POST['data'];
$hora=$_POST['hora'];
$cliente=$_POST['cliente'];
$funcionario= $_POST['funcionario'];
$valor=$_POST['valor'];
$servico=$_POST['servico'];
$id_cliente=$_POST['id_cliente'];
$id_centro_de_beleza=$_POST['id_centro_de_beleza'];

    //Inserindo dados 
$add= $con->prepare("INSERT INTO agendar_servico SET data=:data, hora=:hora, cliente=:cliente, funcionario=:funcionario, valor=:valor, servico=:servico, id_cliente=:id_cliente, id_centro_de_beleza=:id_centro_de_beleza");
$add->bindParam(':data',$data);
$add->bindParam(':hora',$hora);
$add->bindParam(':cliente',$cliente);
$add->bindParam(':funcionario',$funcionario);
$add->bindParam(':valor',$valor);
$add->bindParam(':servico',$servico);
$add->bindParam(':id_cliente',$id_cliente);
$add->bindParam(':id_centro_de_beleza',$id_centro_de_beleza);
$add->execute();

if ($add) {
	echo "Registro_Ok";
}else{
    echo "Agenda_Erro";
}
?>