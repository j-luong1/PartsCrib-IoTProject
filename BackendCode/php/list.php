<?php
include 'connect.php';

if (isset($_GET["TID"])) {
	$TID = $_GET["TID"];
	listDetail($TID, $conn);
} else {
	showLists($conn);
}

mysqli_close($conn);

function showLists($conn) {
	$sql = 'SELECT * FROM ListHead ORDER BY Created DESC';
	$result = mysqli_query($conn, $sql);
	
	$listTID = array();
	$listInfo = array();
	
	if(mysqli_num_rows($result) > 0) {
		while($row = mysqli_fetch_assoc($result)) {
			array_push($listTID, $row["TID"]);
			array_push($listInfo, 
				$row["UID"] . " - " .
				$row["Course"] . ": " .
				$row["Title"]);
		}
	} else {
		echo "No rows retrieved";
	}

	$final = array("TID"=>$listTID,"lists"=>$listInfo);
	echo json_encode($final);
}

function listDetail($TID, $conn) {
	$sql = 'SELECT * FROM ListItems WHERE TID="'.$TID.'"';
	$result = mysqli_query($conn, $sql);

	$name = array();
	$sid = array();
	$quantity = array();

	if (mysqli_num_rows($result) > 0 ) {
		while($row = mysqli_fetch_assoc($result)) {

			array_push($name, $row["Name"]);
			array_push($sid, $row["SID"]);
			array_push($quantity, $row["Quantity"]);
		
		}
	} else {
		echo "No rows retrieved.";
	}
	
	$listDetail = array("TID"=>$TID,"name"=>$name,"sid"=>$sid,"quantity"=>$quantity);

	$sql = 'SELECT * FROM ListHead WHERE TID="'.$TID.'"';
	$result = mysqli_query($conn, $sql);

	if (mysqli_num_rows($result) > 0 ) {
		while($row = mysqli_fetch_assoc($result)) {
			$listDetail['UID'] = $row[UID];
			$listDetail['Title'] = $row[Title];
			$listDetail['Course'] = $row[Course];
			$listDetail['Description'] = $row[Description];
		}
	} else {
		echo "No rows retrieved.";
	}
	
	echo json_encode($listDetail);
}

?>
