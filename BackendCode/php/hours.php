<?php
include 'connect.php';

$sql = 'select Time from Hours';
$result = mysqli_query($conn, $sql);

$hours = array();

if (mysqli_num_rows($result) > 0) {
	while($row = mysqli_fetch_assoc($result)) {
		array_push($hours, $row["Time"]);
	}
} else {
	echo "No rows retrieved";
}

$hoursFinal = array("hours"=>$hours);
echo json_encode($hoursFinal);

?>
