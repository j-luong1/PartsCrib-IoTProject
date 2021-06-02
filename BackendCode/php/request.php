<?php
ini_set('display_errors', 1); ini_set('display_startup_errors', 1); error_reporting(E_ALL);
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

include 'connect.php';

$request = json_decode($_POST['request'],true);
$uid = $request['uid'];
$items = $request['items'];
$TID = uniqid();
$counter =0;

while(true){
    $TID = uniqid();
    $sql = "SELECT TID from Transaction where TID=\"$TID\"";
    $result = mysqli_query($conn, $sql);
    
    if (mysqli_num_rows($result) > 0) {
         // output data of each row from the result set

     } else {
         break;
     }

    
}

//Transaction
//if TID exists, add item status A
if (isset($_REQUEST["TID"])) {
	$TID = $_REQUEST["TID"];
	$sql = "Insert into Transaction (TID,UID,SID,Quantity,Status) values " ;

	foreach($items as $i){
	    $sid = $i['sid'];
	    $quantity = $i['quantity'];
	    $sql.="(\"$TID\",\"$uid\",$sid,$quantity,\"A\"),";
	}

	$sql = substr($sql,0,strlen($sql) -1);

	if(mysqli_query($conn, $sql)){ 
	        $counter++;
	    } else { 
	    //    echo "ERROR: Could not able to execute $sql. "  
	      //                          . mysqli_error($link); 
	        exit();
	    }  

} 
//if TID is new, add item status P
else {

	$sql = "Insert into Transaction (TID,UID,SID,Quantity) values " ;
	
	foreach($items as $i){
	    $sid = $i['sid'];
	    $quantity = $i['quantity'];
	    $sql.="(\"$TID\",\"$uid\",$sid,$quantity),";
	}

	$sql = substr($sql,0,strlen($sql) -1);

	if(mysqli_query($conn, $sql)){ 
	        $counter++;
	} else { 
	    //    echo "ERROR: Could not able to execute $sql. "  
	      //                          . mysqli_error($link); 
	        exit();
	}  

}
    //Inventory
foreach($items as $i){
    $sid = $i['sid'];
    $quantity = $i['quantity'];
    $sql = "UPDATE Inventory SET Quantity=Quantity-$quantity WHERE SID=$sid" ;
    if(mysqli_query($conn, $sql)){ 
        
    } else { 
//        echo "ERROR: Could not able to execute $sql. "  
  //                              . mysqli_error($link); 
        exit();
    }  
}
$counter++;
if($counter==2){
    echo $TID;
}
    



?>
