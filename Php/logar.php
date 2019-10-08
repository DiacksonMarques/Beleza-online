<?php
include_once 'cone.php';

//Setar os dados
$usuario=$_POST['usuario'];
$senha=$_POST['senha'];

//$usuario="Karen";
//$senha="Karen";;

//Verificar Usuario
$veri= $con->prepare("SELECT * FROM tipo_usuarios WHERE usuario = '$usuario' AND senha ='$senha'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();

if($numero_de_cadastro>0){
	while($org = $veri->fetch(PDO::FETCH_ASSOC)){
		if($org['tipo_usuario']=="cliente"){
		    $veric= $con->prepare("SELECT * FROM cliente WHERE usuario = '$usuario' AND senha ='$senha'");
            $veric->execute();
            $numero_de_cadastroc= $veric-> rowCount();
            if($numero_de_cadastroc>0){
                echo "Login_c,";
                while($orgcl = $veric->fetch(PDO::FETCH_ASSOC)){
                    echo $orgcl['id'];
                    echo ",";
                    echo $orgcl['nome'];
                }
            }
		}
		if($org['tipo_usuario']=="negocio"){
		    $verin= $con->prepare("SELECT * FROM centrosdebeleza WHERE usuario = '$usuario' AND senha ='$senha'");
            $verin->execute();
            $numero_de_cadastron= $verin-> rowCount();
            if($numero_de_cadastron>0){
                echo "Login_n,";
                while($orgne = $verin->fetch(PDO::FETCH_ASSOC)){
                    echo $orgne['id'];
                    echo ",";
                    echo $orgne['nome'];
                }
            }
		}
	}
}else{
	echo "Login_Erro";
}