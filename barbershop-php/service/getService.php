<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$barbershopid = $_REQUEST['BarbershopID'];

    $qry = $conn->prepare("SELECT ServiceID , Name , Price FROM service WHERE BarbershopID = " . $barbershopid . " ;");

    $qry->execute();
    $qry->bind_result($serviceID,$name,$price);

    $service = array();

    while($qry->fetch()){
        $temp = array();
        $temp['serviceID'] = $serviceID;
        $temp['name'] = $name;
        $temp['price'] = $price;

        array_push($service, $temp);
    }

    echo json_encode($service);



?>