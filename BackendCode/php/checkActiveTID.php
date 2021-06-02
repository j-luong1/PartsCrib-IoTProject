<?php
ini_set('display_errors', 1); ini_set('display_startup_errors', 1); error_reporting(E_ALL);

include 'connect.php';

$TID = "";

if (isset($_REQUEST["TID"])) {
	$TID = $_REQUEST["TID"];
}

$sql = 'SELECT Status from Transaction WHERE TID="'.$TID.'"';

$result = mysqli_query($conn,$sql);

if(mysqli_num_rows($result) > 0) {
	while($row = mysqli_fetch_assoc($result)) {
		if ($row["Status"]=="P" || $row["Status"]=="A" || $row["Status"]=="R") {
			echo "1";
			break;
		}
	}
}
?>
