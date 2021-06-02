<?php
ini_set('display_errors', 1); ini_set('display_startup_errors', 1); error_reporting(E_ALL);

include 'connect.php';

$request = json_decode($_POST['request'],true);
$uid = $request['uid'];
$items = $request['items'];
$TID = uniqid();
$counter = 0;

$title = "Empty Title";
$course = "No Course";
$description = "No Description.";

while(true) {
	$TID = uniqid();
	$sql = "SELECT TID from ListHead where TID=\"$TID\"";
	$result = mysqli_query($conn, $sql);

	if (mysqli_num_rows($result) > 0) {
	} else { break; }
}

if (isset($_REQUEST["title"])) {
	$title = $_REQUEST["title"];
}

if (isset($_REQUEST["course"])) {
	$course = $_REQUEST["course"];
}

if (isset($_REQUEST["description"])) {
	$description = $_REQUEST["description"];
}

//add items to ListItems table
$sql = "Insert into ListItems (Name,TID,UID,SID,Quantity) values " ;

foreach($items as $i) {
	$sid = $i['sid'];
	$name = $i['name'];
	$quantity = $i['quantity'];
	$sql.="(\"$name\",\"$TID\",\"$uid\",$sid,$quantity),";
}

$sql = substr($sql,0,strlen($sql) -1);

if(mysqli_query($conn,$sql)) {
	$counter++;
} else { exit(); }

//add list to ListHead
$sql = 'Insert into ListHead (TID,UID,Title,Course,Description) values 
("'.$TID.'","'.$uid.'","'.$title.'","'.$course.'","'.$description.'")';
//$sql.= "(\"$TID\",\"$uid\",\"$title\",\"$course\",\"$description\"),";

$result = mysqli_query($conn, $sql);

if (mysqli_num_rows($result) > 0) {
	echo "Successfully added.";
} else {
	echo "No rows retrieved.";
}
?>
