<?php
include_once 'cone.php';

$pasta_upload = 'img/';

$server_ip = gethostbyname(gethostname());

$upload_url = 'http://'.$server_ip.'/AndroidImageUpload/'.$pasta_upload; 

$response = array(); 
