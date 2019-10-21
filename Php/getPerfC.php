<?php 
include_once 'cone.php';

//Setar os dados
$id_cliente= $_GET['id_cliente'];

//Verificar Usuario
$veri= $con->prepare("SELECT * FROM favorito WHERE id_cliente = '$id_cliente'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();

$veri= $con->prepare("SELECT * FROM agendar_servico WHERE id_cliente = '$id_cliente'");
$veri->execute();
$numero_de_cadastroa= $veri-> rowCount();
$json = '[';
for($i=0 ; $i<1 ; $i++){
	     $char ='"';
     	 $json .= 
	       '{
	        "favorito":"'.str_replace($char,'`',strip_tags($numero_de_cadastro)).'",
	     	"agendamento":"'.str_replace($char,'`',strip_tags($numero_de_cadastroa)).'"
        	},';
        }
        $json = substr($json,0,strlen($json)-1);
$json .= ']';
echo $json;
?>