<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$barbershopID = $_POST['barbershopID'];
$image = $_POST['image'];

$qry = $conn->prepare("INSERT INTO gallery ( Image , BarbershopID ) VALUES ('" . $image . "','" . $barbershopID . "');");

if($qry->execute()){
    echo "Add Success";
}
else{
    echo "Add Failed";
}


?>
