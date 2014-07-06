<?php
	require_once "config.php";
	$json = array();

	$kategori=$_POST['kategori'];
	$sql_query="SELECT * FROM tb_conten WHERE kategori_id=$kategori";
	$hasil= mysql_query($sql_query);

	while ($row=mysql_fetch_assoc($hasi)) {
		$konten[]=$row;
		# code...
	}
	if (is_array($konte)) {
		$json['datakonten']=$konten;
		echo json_encode($json);
		# code...
	}
?>