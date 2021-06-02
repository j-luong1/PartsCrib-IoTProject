<?php
    include 'connect.php';
    
    
   if (isset($_GET["category"])){
       //Jump to Function getItems
       $selectedCat = $_GET["category"];
       getItems($conn,$selectedCat);
   }
   
   else if (isset($_GET["item"])) {
	$selectedItem = $_GET["item"];
	getItemDetail($conn,$selectedItem);
   }
   
   else{
       //Jump to Function getCategories
       getCategories($conn);
   }
   
    //Close Connection
    mysqli_close($conn);
    
    
    
    //Get all items from the selected category
    function getItems($conn,$selectedCat){
        $sql='select Name,SID from Inventory where Category="'.$selectedCat.'"';
        
        $result = mysqli_query($conn, $sql);

        $itemList = array();
	$sidList = array();

         if (mysqli_num_rows($result) > 0) {
             // output data of each row from the result set
             while($row = mysqli_fetch_assoc($result))
             {
                //printf('Category: %s <br>',$row["Name"]);
                 array_push($itemList, $row["Name"]);
		 array_push($sidList, $row["SID"]);
             }
         } else {
             echo "No rows retreived";
         }

         $assocList = array("Items"=>$itemList,"SID"=>$sidList);
         echo json_encode($assocList);
        
    }
    
    function getItemDetail($conn,$selectedItem){
	$sql='select * from Inventory where SID="'.$selectedItem.'"';

        $result = mysqli_query($conn, $sql);

	$itemDetail = array();

         if (mysqli_num_rows($result) > 0) {
             // output data of each row from the result set
             while($row = mysqli_fetch_assoc($result))
             {
                //printf('Category: %s <br>',$row["Name"]);
//                 array_push($itemDetail, $row["Name"],$row["Category"],$row["Description"],$row["Quantity"]);
		  $itemDetail['SID'] = $row[SID];
		  $itemDetail['Category'] = $row[Category];
		  $itemDetail['Name'] = $row[Name];
		  $itemDetail['Description'] = $row[Description];
		  $itemDetail['Quantity'] = $row[Quantity];
		  $itemDetail['Image'] = $row[Image];
             }
         } else {
             echo "No rows retreived";
         }
	 
         $assocList = array("Name"=>$itemDetail);
         echo json_encode($itemDetail);



    }
    
    //Get all Categories that are avaiable
    function getCategories($conn){
        $sql="select distinct Category from Inventory";
   

        $result = mysqli_query($conn, $sql);

        $categoryList = array();

         if (mysqli_num_rows($result) > 0) {
             // output data of each row from the result set
             while($row = mysqli_fetch_assoc($result))
             {
                //printf('Category: %s <br>',$row["Category"]);
                 array_push($categoryList, $row["Category"]);
             }
         } else {
             echo "No rows retreived";
         }

         $assocList = array("Category"=>$categoryList);
         echo json_encode($assocList);
    }
 ?>
