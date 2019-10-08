<?php 
include_once 'cone.php';

//Setar os dados
$nome= $_POST['nome'];
$cnpj= $_POST['cnpj'];
$descricao= $_POST['descricao'];
$usuario= $_POST['usuario'];
$senha= $_POST['senha'];
$tipo_usuario= "negocio";

//Verificar Usuario
$veri= $con->prepare("SELECT * FROM centrosdebeleza WHERE usuario = '$usuario'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();

if ($numero_de_cadastro>0) {
	echo "Usuario_Erro";
}else{
    //Inserindo dados 
    $add= $con->prepare("INSERT INTO tipo_usuarios SET usuario=:usuario, senha=:senha, tipo_usuario=:tipo_usuario");
	$add->bindParam(':usuario',$usuario);
	$add->bindParam(':senha',$senha);
	$add->bindParam(':tipo_usuario',$tipo_usuario);
	$add->execute();
	if ($add) {
	}else{
		echo "Registro_Erro";
	}
    //Inserindo dados 
	$add= $con->prepare("INSERT INTO centrosdebeleza SET nome=:nome, cnpj=:cnpj, descricao=:descricao, usuario=:usuario, senha=:senha");
	$add->bindParam(':nome',$nome);
	$add->bindParam(':cnpj',$cnpj);
	$add->bindParam(':descricao',$descricao);
	$add->bindParam(':usuario',$usuario);
	$add->bindParam(':senha',$senha);
	$add->execute();
	if ($add) {
		echo "Registro_Ok";
	}else{
		echo "Registro_Erro";
	}
}
?>