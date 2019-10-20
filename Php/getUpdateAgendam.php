<?php 

include_once 'cone.php';

$id = $_GET['id'];

$veri= $con->prepare("SELECT * FROM agendar_servico WHERE id = '$id'ORDER BY id ASC");
$veri->execute();

while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
    if($org['id']=="$id"){
        $json = '[';
	     $char ='"';
     	 $json .= 
	       '{
	     	"id":"'.str_replace($char,'`',strip_tags($org['id'])).'",
	     	"data":"'.str_replace($char,'`',strip_tags($org['data'])).'",
	     	"hora":"'.str_replace($char,'`',strip_tags($org['hora'])).'",
	     	"cliente":"'.str_replace($char,'`',strip_tags($org['cliente'])).'",
	     	"funcionario":"'.str_replace($char,'`',strip_tags($org['funcionario'])).'",
	     	"valor":"'.str_replace($char,'`',strip_tags($org['valor'])).'",
	     	"servico":"'.str_replace($char,'`',strip_tags($org['servico'])).'",
	     	"id_cliente":"'.str_replace($char,'`',strip_tags($org['id_cliente'])).'",
	     	"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'"
        	},';
         }

        }
        $json = substr($json,0,strlen($json)-1);

        $json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>