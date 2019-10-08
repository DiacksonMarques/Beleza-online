<?php 
include_once 'cone.php';

//Setar os dados
$nome= $_POST['nome'];
$email= $_POST['email'];
$usuario= $_POST['usuario'];
$senha= $_POST['senha'];
$tipo_usuario= "cliente";


//Verificar email
if(filter_var($email, FILTER_VALIDATE_EMAIL)){
 $emailver= explode("@",$email);
 if($emailver[1]=="gmail.com"||$emailver[1]=="hotmail.com"|| $emailver[1]=="outlook.com"||$emailver[1]=="outlook.com.br"||$emailver[1]=="yahoo.com" || $emailver[1]=="yahoo.com.br"){
     
 }else{
     echo 'Email_Erro';
     exit();
 }
}
else{
 echo 'Email_Erro';
 exit();
}
//Verificar Usuario
$veri= $con->prepare("SELECT * FROM cliente WHERE usuario = '$usuario'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();

if ($numero_de_cadastro>0) {
	echo "Usuario_Erro";
}else{
    //Insrindo dados
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
	$add= $con->prepare("INSERT INTO cliente SET nome=:nome, email=:email, usuario=:usuario, senha=:senha");
	$add->bindParam(':nome',$nome);
	$add->bindParam(':email',$email);
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