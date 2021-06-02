<?php

include 'connect.php';

$username = "";
$date = "";
$tid = "";


if(isset($_REQUEST["TID"]) && isset($_REQUEST["username"])) {
	$username = $_REQUEST["username"];
	$tid = $_REQUEST["TID"];
	orderDetail($username, $tid, $conn);
}

else if(isset($_REQUEST['username']) && empty($tid)) {
	$username = $_REQUEST['username'];
	listOrders($username, $conn);
}

function listOrders($username, $conn) {
	$sql='select distinct TOut, TID
		from Transaction 
		where UID="'.$username.'" 
		ORDER BY TOut DESC';
	$result = mysqli_query($conn, $sql);

	$orderList = array();
	$TIDs	= array();

	if(mysqli_num_rows($result) > 0) {
		while($row = mysqli_fetch_assoc($result)) {
			array_push($orderList, $row["TOut"]);
			array_push($TIDs, $row["TID"]);
		}
	} else {
		echo "No rows retrieved";
	}
	
	$final = array("TOut"=>$orderList,"TID"=>$TIDs);
//	$tid = array("TID"=>$TIDs);
	echo json_encode($final);
}

function orderDetail($username, $tid, $conn) {

	$sql = 'select t1.Category, t2.TID, t1.SID, t1.Name, t2.Quantity from Inventory t1 INNER JOIN Transaction t2 ON t1.SID=t2.SID WHERE TID="'.$tid.'"';

	$result = mysqli_query($conn,$sql);

	$cat = array();
	$name = array();
	$sid = array();
	$quantity = array();

	if(mysqli_num_rows($result) > 0)
		while($row = mysqli_fetch_assoc($result)) {
//			array_push($item, $row["Category"].": ".$row["Name"]." - SID: ".$row["SID"]." Quantity: ".$row["Quantity"]);
			array_push($cat, $row["Category"]);
			array_push($name, $row["Name"]);
			array_push($sid, $row["SID"]);
			array_push($quantity, $row["Quantity"]);
		}

	$final = array("Category"=>$cat,"Name"=>$name,"SID"=>$sid,"Quantity"=>$quantity);
	echo json_encode($final);
}

?>
