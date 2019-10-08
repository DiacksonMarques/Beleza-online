<?php 

include_once 'cone.php';

$id_centro_de_beleza = $_GET['id_centro_de_beleza'];

$veri= $con->prepare("SELECT * FROM servico WHERE id_centro_de_beleza = '$id_centro_de_beleza'  ORDER BY id ASC");
$veri->execute();

$json = '{"tipo_servico": [{ "tipo_servico":"--Selecione um serviço--" },';

while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
	$char ='"';
	$json .= 
	'{
		"tipo_servico":"'.str_replace($char,'`',strip_tags($org['nome']."   R$:".$org['valor'].",00")).'"
	},';
}

$json = substr($json,0,strlen($json)-1);

$json .= ']}';

echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>