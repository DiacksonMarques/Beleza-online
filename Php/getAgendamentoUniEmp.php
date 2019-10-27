<?php 

include_once 'cone.php';

$id = $_GET['id'];

$veri= $con->prepare("SELECT * FROM agendar_servico WHERE id = '$id'ORDER BY id ASC");
$veri->execute();

$json = '[';
while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
    $cli=$org['id_cliente'];
    $clie = $con-> prepare("SELECT * FROM cliente WHERE id='$cli'");
    $clie ->execute();
    while($pc = $clie->fetch(PDO::FETCH_ASSOC)){
	     $char ='"';
     	 $json .= 
	       '{
	     	"id":"'.str_replace($char,'`',strip_tags($org['id'])).'",
	     	"data":"'.str_replace($char,'`',strip_tags($org['data'])).'",
	     	"hora_i":"'.str_replace($char,'`',strip_tags($org['hora_i'])).'",
	     	"hora_f":"'.str_replace($char,'`',strip_tags($org['hora_f'])).'",
	     	"funcionario":"'.str_replace($char,'`',strip_tags($org['funcionario'])).'",
	     	"valor":"'.str_replace($char,'`',strip_tags($org['valor'])).'",
	     	"servico":"'.str_replace($char,'`',strip_tags($org['servico'])).'",
	     	"cliente":"'.str_replace($char,'`',strip_tags($pc['nome'])).'",
	     	"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'"
        	},';

        }
}

        $json = substr($json,0,strlen($json)-1);

        $json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>