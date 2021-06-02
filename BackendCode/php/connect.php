<?php
$servername = "localhost";
$username = "n01156096";
$password = "cat";
$database = "n01156096_a";

$conn = mysqli_connect($servername, $username, $password, $database);

if (!$conn) {
	die("Connection failed." . mysqli_connect_error());
}

?>
