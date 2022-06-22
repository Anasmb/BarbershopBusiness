<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$stars = $_POST['stars']; 
$comment = $_POST['comment'];
$barbershopID = $_POST['barbershopID'];
$customerID = $_POST['customerID'];

$qry = $conn->prepare("INSERT INTO rating ( Comment , Stars , BarbershopID , CustomerID) 
                        VALUES ('" . $comment . "','" . $stars . "','" . $barbershopID . "','" . $customerID ."');");

if($qry->execute()){
    echo "Add Success";
}
else{
    echo "Add Failed";
}


?>
