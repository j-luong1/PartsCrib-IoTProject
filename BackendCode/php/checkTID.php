<?php
ini_set('display_errors', 1); ini_set('display_startup_errors', 1); error_reporting(E_ALL);

include 'connect.php';

$TID = "";

if(isset($_REQUEST["TID"])) {
	$TID = $_REQUEST["TID"];
	checkTID($TID, $conn);
}

function checkTID($TID, $conn) {
	$sql = 'select TID from Transaction where TID="'.$TID.'"';
	$result = mysqli_query($conn, $sql);

	if(mysqli_num_rows($result) > 0) {
		echo "1";
	} else {
		echo "0";
	}

}

?>
