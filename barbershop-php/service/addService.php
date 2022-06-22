<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

    $name = $_POST['Name'];
    $price = $_POST['Price'];
    $barbershopid = $_POST['BarbershopID'];

    $qry = $conn->prepare("INSERT INTO service ( Name , Price , BarbershopID ) VALUES ('" . $name . "','" . $price . "','" . $barbershopid . "');");

    if($qry->execute()){
        echo "Add Success";
    }
    else{
        echo "Add Failed";
    }


?>
