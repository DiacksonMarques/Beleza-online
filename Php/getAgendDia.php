<?php 

include_once 'cone.php';
$vari = $_GET['vari'];

$vari= explode(",",$vari);

$data =  $vari[0];
$id_centro_de_beleza=$vari[1];

$veri= $con->prepare("SELECT * FROM agendar_servico WHERE id_centro_de_beleza = '$id_centro_de_beleza' AND id_cliente>0 AND data='$data' ORDER BY data ASC");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();
$json = '[';
if($numero_de_cadastro>0){
while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
    $cli=$org['id_cliente'];
    $clie = $con-> prepare("SELECT * FROM cliente WHERE id='$cli'");
    $clie ->execute();
    while($pc = $clie->fetch(PDO::FETCH_ASSOC)){
	     $char ='"';
     	 $json .= 
	       '{
	        "id":"'.str_replace($char,'`',strip_tags($org['id'])).'",
	     	"cliente":"'.str_replace($char,'`',strip_tags($pc['nome'])).'",
	     	"hora_i":"'.str_replace($char,'`',strip_tags($org['hora_i'])).'",
	     	"hora_f":"'.str_replace($char,'`',strip_tags($org['hora_f'])).'"
        	},';
        }
}
        
}else{
    echo '[{ "id":"Não", "cliente":"ha", "hora_i":"nenhum", "hora_f":"serviço agendado"}';
}
$json = substr($json,0,strlen($json)-1);
$json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>