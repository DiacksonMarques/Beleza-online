<?php 

include_once 'cone.php';

$id_cliente = $_GET['id_cliente'];

$veri= $con->prepare("SELECT * FROM agendar_servico WHERE id_cliente = '$id_cliente'ORDER BY id ASC");
$veri->execute();
$json = '[';
while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
    if($org['id_cliente']==$id_cliente){
         $verict= $con->prepare("SELECT * FROM centrosdebeleza WHERE id =".$org['id_centro_de_beleza']);
         $verict->execute();
         while ($orgct = $verict->fetch(PDO::FETCH_ASSOC)){
	     $char ='"';
     	 $json .= 
	       '{
	        "id":"'.str_replace($char,'`',strip_tags($org['id'])).'",
	     	"nome":"'.str_replace($char,'`',strip_tags($orgct['nome'])).'",
	     	"data":"'.str_replace($char,'`',strip_tags($org['data'])).'",
	     	"hora":"'.str_replace($char,'`',strip_tags($org['hora'])).'"
        	},';
         }

        }
    }
$json = substr($json,0,strlen($json)-1);
$json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>