<?php 

include_once 'cone.php';

$vari = $_GET['vari'];

$vari= explode(",",$vari);

$data = $vari[0];
$id_centro_de_beleza = $vari[1];


$veri= $con->prepare("SELECT * FROM promocao WHERE id_centro_de_beleza ='$id_centro_de_beleza' AND data >='$data' ORDER BY titulo ASC");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();
$json = '[';
if($numero_de_cadastro>0){
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
}else{
    echo '[{ "id":"Não", "cliente":"ha", "hora_i":"promoções", "hora_f":"ativas"}';
}
$json = substr($json,0,strlen($json)-1);
$json .= ']';
echo $json;
//"id_centro_de_beleza":"'.str_replace($char,'`',strip_tags($org['id_centro_de_beleza'])).'", 
?>