<?php
 try {
   $con= new PDO('mysql:localhost=host; dbname=id10018378_beleza_online',"id10018378_evo","1e2e3e4e5e");
   $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
   $con->exec('SET CHARACTER SET utf8'); 
 } catch (Exception $erro) {
 	echo "Erro na conexão:" . $erro->getMessage();
 }
?>