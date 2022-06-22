<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$name = $_POST['name'];
$email = $_POST['email'];
$phoneNumber = $_POST['phoneNumber'];
$password = $_POST['password'];
$customerID = $_POST['customerID'];

try{
    $qry = $conn->prepare("UPDATE customers SET Name = '" . $name . "' , Email = '" . $email . "' , Password = '" . $password . "' , PhoneNumber = '" . $phoneNumber . "' WHERE CustomerID = " . $customerID . " ;");

    if($qry->execute()){
        echo "Update Success";
    }
    
}
catch(Exception $e){
    if(str_contains($e, 'Duplicate entry')) {
       if(str_contains($e,'phone number')){
           echo "Phone number is already taken!";
       }
       else if(str_contains($e,'email')){
           echo "Email is already taken!";
       }
    }
}

?>