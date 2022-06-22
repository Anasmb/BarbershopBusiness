<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$customerID = $_POST['customerID']; 
$barbershopID = $_POST['barbershopID']; 

  $qry = "SELECT * FROM rating WHERE CustomerID = " . $customerID . " AND BarbershopID = " . $barbershopID;
  
  $results = mysqli_query($conn,$qry);

  if (mysqli_num_rows($results) != 0) {
         echo "true";
    } 
    else if(mysqli_num_rows($results) == 0){
         echo "false";
    }

?>
