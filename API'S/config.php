<?php
	$host="localhost";
	$username="root";
	$password="";
	$db="db_hf"

	mysql_connect($host,$username,$password) or die("gagal");
	mysql_select_db($db)or die("database tidak ditemukan");
	
?>
