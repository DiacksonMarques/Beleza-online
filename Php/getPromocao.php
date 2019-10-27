<?php 

include_once 'cone.php';

$id_centro_de_beleza = $_GET['id_centro_de_beleza'];

$veri= $con->prepare("SELECT * FROM promocao WHERE id_centro_de_beleza = '$id_centro_de_beleza'ORDER BY titulo ASC");
$veri->execute();
$json = '[';
while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
	     $char ='"';
     	 $json .= 
	       '{
	        "id":"'.str_replace($char,'`',strip_tags($org['id'])).'",
	     	"titulo":"'.str_replace($char,'`',strip_tags($org['titulo'])).'",
	     	"data":"'.str_replace($char,'`',strip_tags($org['data'])).'",
	     	"descricao":"'.str_replace($char,'`',strip_tags($org['descricao'])).'"
        	},';
         

        
    }
$json = substr($json,0,strlen($json)-1);
$json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>