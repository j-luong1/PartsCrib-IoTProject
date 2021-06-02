<?php
ini_set('display_errors', 1); ini_set('display_startup_errors', 1); error_reporting(E_ALL);

include 'connect.php';

$request = json_decode($_POST['request'],true);
$items = $request['items'];

foreach($items as $i) {
	$sid = $i['sid'];
	$quantity = $i['quantity'];
	$sql = "SELECT Quantity from Inventory WHERE SID=$sid";

	$result = mysqli_query($conn, $sql);

	if(mysqli_num_rows($result) > 0) {
		while($row = mysqli_fetch_assoc($result)) {
			if ($row["Quantity"] < $quantity)
				echo "1";
		}
	} else {
		exit();
	}
}


?>
