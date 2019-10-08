<?php 

include_once 'cone.php';

$veri= $con->prepare("SELECT * FROM localizacao ORDER BY id ASC");
$veri->execute();

$json = '{"localizacao": [';

while ($org = $veri->fetch(PDO::FETCH_ASSOC)){
	$char ='"';
	$json .= 
	'{
		"lat":"'.str_replace($char,'`',strip_tags($org['lat'])).'", 
		"longe":"'.str_replace($char,'`',strip_tags($org['longe'])).'",
		"nome":"'.str_replace($char,'`',strip_tags($org['nome'])).'",
		"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'"
	},';
}

$json = substr($json,0,strlen($json)-1);

$json .= ']}';

echo $json;
?>