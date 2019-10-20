<?php 
include_once 'cone.php';

//Setar os dados
$descricao= $_POST['descricao'];
$data= $_POST['data'];
$hora= $_POST['hora'];
$tipo= $_POST['tipo'];

//Verificar Usuario
$veri= $con->prepare("SELECT * FROM agendar WHERE data = '$data' AND hora='$hora'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();

if ($numero_de_cadastro>0) {
	echo "Usuario_Erro";
}else{
    //Inserindo dados 
	$add= $con->prepare("INSERT INTO agendar SET descricao=:descricao, data=:data, hora=:hora, tipo=:tipo");
	$add->bindParam(':descricao',$descricao);
	$add->bindParam(':data',$data);
	$add->bindParam(':hora',$hora);
	$add->bindParam(':tipo',$tipo);
	$add->execute();
	if ($add){
		echo "Registro_Ok";
	}else{
		echo "Registro_Erro";
	}
}
?>