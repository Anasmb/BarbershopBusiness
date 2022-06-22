<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

if(empty($_REQUEST)){ 
    $qry = $conn->prepare("SELECT * FROM barbershop WHERE Status = 'shown'");
}
else{
    $qry = $conn->prepare("SELECT * FROM barbershop WHERE BarbershopID = " . $_REQUEST['BarbershopID']);
}

$qry->execute();
$qry->bind_result($barbershopID , $shopName , $phoneNumber, $email , $address , $password ,$image , $hours , $status);

$shops = array();

while($qry->fetch()){
    $temp = array();
    $temp['BarbershopID'] = $barbershopID;
    $temp['ShopName'] = $shopName;
    $temp['PhoneNumber'] = $phoneNumber;
    $temp['Email'] = $email;
    $temp['Address'] = $address;
    $temp['Image'] = $image;
    $temp['Hours'] = $hours;

    array_push($shops, $temp);
}

echo json_encode($shops);

?>