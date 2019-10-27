<?php 
include_once 'cone.php';

//Setar os dados
$lat= $_POST['lat'];
$longe= $_POST['longe'];
$nome= $_POST['nome'];
$id_centro_de_beleza= $_POST['id_centro_de_beleza'];

//Verificar Usuario
$veri= $con->prepare("SELECT * FROM localizacao WHERE id_centro_de_beleza='$id_centro_de_beleza'");
$veri->execute();
$numero_de_cadastro= $veri-> rowCount();
if($numero_de_cadastro>0){
  echo "Loca_Erro";  
}else{
//Inserindo dados 
$add= $con->prepare("INSERT INTO localizacao SET lat=:lat, longe=:longe, nome=:nome, id_centro_de_beleza=:id_centro_de_beleza");
$add->bindParam(':lat',$lat);
$add->bindParam(':longe',$longe);
$add->bindParam(':nome',$nome);
$add->bindParam(':id_centro_de_beleza',$id_centro_de_beleza);
$add->execute();
if ($add) {
	echo "Registro_Ok";
}else{
	echo "Loca_Erro";
}
}
?>