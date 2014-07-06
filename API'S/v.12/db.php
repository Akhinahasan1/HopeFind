<?php
if (strpos(strtolower($_SERVER['SCRIPT_NAME']),strtolower(basename(__FILE__)))) { exit(); }

error_reporting(0);
session_start();

$server = "localhost";
$username = "hakiki_hakiki";
$password = "h4nh4n5h4kiki";
$database = "hakiki_kios";

mysql_connect($server,$username,$password) or die("Koneksi gagal");
mysql_select_db($database) or die("Database tidak bisa dibuka");

?>