<?php 

include_once 'cone.php';

$id = $_GET['id'];

$veri= $con->prepare("SELECT * FROM funcionario WHERE id = '$id'ORDER BY nome ASC");
$veri->execute();

while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
    if($org['id']=="$id"){
        $json = '[';
	     $char ='"';
     	 $json .= 
	       '{
	     	"id":"'.str_replace($char,'`',strip_tags($org['id'])).'",
	     	"nome":"'.str_replace($char,'`',strip_tags($org['nome'])).'",
	     	"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'"
        	},';
         }

        }
        $json = substr($json,0,strlen($json)-1);

        $json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>