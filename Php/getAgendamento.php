<?php 

include_once 'cone.php';

$vari = $_GET['vari'];

$vari= explode(",",$vari);

$servico= $vari[0];
$data =  $vari[1];
$id_centro_de_beleza=$vari[2];

$veri= $con->prepare("SELECT * FROM agendar_servico WHERE id_centro_de_beleza = '$id_centro_de_beleza' AND servico = '$servico' AND data='$data' AND id_cliente = 0 ORDER BY id ASC");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();
$json = '[';
if($numero_de_cadastro>0){
    while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
	     $char ='"';
     	 $json .= 
	       '{
	        "id":"'.str_replace($char,'`',strip_tags($org['id'])).'",
	     	"servico":"'.str_replace($char,'`',strip_tags($org['servico'])).'",
	     	"hora_i":"'.str_replace($char,'`',strip_tags($org['hora_i'])).'",
	     	"hora_f":"'.str_replace($char,'`',strip_tags($org['hora_f'])).'",
	     	"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'"
        	},';
}
    
}else{
    echo '[{ "id":"Não", "servico":"Tem", "hora_i":"nenhum", "hora_f":"serviço", "id_centro_de_beleza":"cadastrado" }';
}
$json = substr($json,0,strlen($json)-1);
$json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>