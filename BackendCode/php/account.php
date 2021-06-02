<?php

include 'connect.php';

$username = "";
$name = "";
$last = "";
$email = "";
$password = "";
$passwordNew = "";

//from html
if(isset($_REQUEST['username'])) {
	$username = $_REQUEST['username'];
}

if(isset($_REQUEST['password'])) {
	$password = $_REQUEST['password'];
}

if(isset($_REQUEST['email'])) {
	$email = $_REQUEST['email'];
}

if(isset($_REQUEST['name'])) {
	$name = $_REQUEST['name'];
}
if(isset($_REQUEST['last'])) {
	$last = $_REQUEST['last'];
}
if(isset($_REQUEST['password'])) {
	$password = $_REQUEST['password'];
}
if(isset($_REQUEST['passwordNew'])) {
	$passwordNew = $_REQUEST['passwordNew'];
}

//from app
/*
if(isset($_POST['username'])) {
	$username = $_POST['username'];
}

if(isset($_POST['password'])) {
	$password = $_POST['password'];
}

if(isset($_POST['email'])) {
	$email = $_POST['email'];
}

if(isset($_POST['name'])) {
	$name = $_POST['name'];
}
if(isset($_POST['last'])) {
	$name = $_POST['last'];
}
if(isset($_POST['password'])) {
	$password = $_POST['password'];
}

*/

//login function
if(!empty($username) && !empty($password) && empty($email) && empty($passwordNew)) {
	$keycode = `php hashPass.php $password`;
	$json_array = loginUser($username, $keycode, $conn);

	echo json_encode($json_array);
}

//register function
if(!empty($username) && !empty($password) && !empty($email) && !empty($name)) {
	$keycode = `php hashPass.php $password`;
//	echo $username. $email. $password. $uid. $keycode. $name;


	$json_array = registerUser($username, $name, $last, $email, $keycode, $conn);
	echo json_encode($json_array);
//	echo json_encode($json_array);
}

//change pw
if(!empty($password) && empty($username) && !empty($email)) {
	$json_array = pwChange($password, $email, $passwordNew,  $conn);
	echo json_encode($json_array);
}

function loginUser($username,$password, $conn) {

	$json = array();
	$success = loginExists($username,$password, $conn);

	if($success) {
		$json['success'] = 1;
		$json['message'] = "Successfully logged in.";
		$sql = 'select * from accountsJRC where ID="'.$username.'"';
		$result = mysqli_query($conn,$sql);
		if (mysqli_num_rows($result) > 0) {
			while ($row = mysqli_fetch_assoc($result)) {
				$json['uid'] = $row[UID];
				$json['id'] = $row[ID];
				$json['name'] = $row[NAME];
				$json['email'] = $row[EMAIL];
				$json['created'] = $row[CREATED];
				$json['admin'] = $row[ADMIN];
			}
		}
		mysqli_close($conn);
	} else {
		$json['success'] = 0;
		$json['message'] = "Failed to login.";
	}
	return $json;
}

function pwChange($pass, $email, $nPass, $conn) {
	$oldPass = `php hashPass.php $pass`;
	$newPass = `php hashPass.php $nPass`;

	$sql = 'select * from accountsJRC where EMAIL="'.$email.'" AND KEYCODE ="'.$oldPass.'"';
	$result = mysqli_query($conn,$sql);

	if(mysqli_num_rows($result) > 0) {
		$sql = 'UPDATE accountsJRC SET KEYCODE="'.$newPass.'" WHERE EMAIL="'.$email.'"';
		$result = mysqli_query($conn,$sql);
		mysqli_num_rows($result);
		$json['success'] = 1;
		$json['message'] = "Successfully changed password";
	} else {
		$json['success'] = 0;
		$json['message'] = "Current password incorrect.";
	}
	mysqli_close($conn);
	return $json;
}

function loginExists($username,$password,$conn) {
	$sql = 'select * from accountsJRC where ID="'.$username.'" AND KEYCODE="'.$password.'"';
	$result = mysqli_query($conn,$sql);

	if(mysqli_num_rows($result) > 0) {
		return true;
	}
	mysqli_close($conn);
	return false;
}

function registerUser($username, $name, $last, $email, $keycode, $conn) {
	$exists = userCheck($username, $email, $conn);
	$isValid = isValidEmail($email);
	$uid = `php id.php`;

	if($exists) {
		$json['success'] = 0;
		$json['message'] = "Error. Email or username exists.";
	} else if(!$isValid) {
		$json['success'] = 0;
		$json['message'] = "Email is invaid.";
	} else {
		while(!validUID) {
			$uid = `php id.php`;
		}
		$sql = 'insert into accountsJRC(UID, ID, NAME, LAST, EMAIL, CREATED, KEYCODE) values ("'.$uid.'","'.$username.'","'.$name.'","'.$last.'","'.$email.'",NOW(),"'.$keycode.'")';
		
		$insert = mysqli_query($conn, $sql);
		
		if($insert==1) {
			$json['success'] = 1;
			$json['message'] = "Successfully registered.";
		} else {
			$json['success'] = 0;
			$json['message'] = "Error in registration.";
		}
	}
	mysqli_close($conn);
	return $json;
}

function userCheck($username, $email, $conn) {
	$sql = 'select * from accountsJRC where ID="'.$username.'" AND EMAIL="'.$email.'"';
	$result = mysqli_query($conn,$sql);

	if(mysqli_num_rows($result) > 0) {
		return true;
	}
	return false;
}

function isValidEmail($email) {
	return filter_var($email, FILTER_VALIDATE_EMAIL) !== false;
}

function validUID($uid, $conn) {
	$sql = 'SELECT UID from accountsJRC where UID="'.$uid.'"';
	$result = mysqli_query($conn, $sql);
	
	if (mysqli_num_rows($result) > 0) {
		return false;
	}
	return true;
}

?>
