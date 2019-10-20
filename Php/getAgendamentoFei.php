<?php 

include_once 'cone.php';

$id_centro_de_beleza = $_GET['id_centro_de_beleza'];
$id_cliente=0;

$veri= $con->prepare("SELECT * FROM agendar_servico WHERE id_centro_de_beleza = '$id_centro_de_beleza' AND id_cliente = '$id_cliente' ORDER BY data ASC");
$veri->execute();
$json = '[';
while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
	     $char ='"';
     	 $json .= 
	       '{
	        "id":"'.str_replace($char,'`',strip_tags($org['id'])).'",
	     	"cliente":"'.str_replace($char,'`',strip_tags($org['cliente'])).'",
	     	"data":"'.str_replace($char,'`',strip_tags($org['data'])).'",
	     	"hora":"'.str_replace($char,'`',strip_tags($org['hora'])).'"
        	},';
}
$json = substr($json,0,strlen($json)-1);
$json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>