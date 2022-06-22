<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$barbershopid = $_REQUEST['BarbershopID'];

$qry = $conn->prepare("SELECT BarberID , Name , Experience , Nationality FROM barber WHERE BarbershopID = " . $barbershopid . " ;");

$qry->execute();
$qry->bind_result($barberID,$name,$experience,$nationality);

$barber = array();

while($qry->fetch()){
    $temp = array();
    $temp['barberID'] = $barberID;
    $temp['name'] = $name;
    $temp['experience'] = $experience;
    $temp['nationality'] = $nationality;

    array_push($barber, $temp);
}

echo json_encode($barber);

?>