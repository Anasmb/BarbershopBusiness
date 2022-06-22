<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$shopName = $_POST['shopName'];
$email = $_POST['email'];
$address = $_POST['address'];
$phoneNumber = $_POST['phoneNumber'];
$password = $_POST['password'];
$barbershopid = $_POST['BarbershopID'];

try{
    
    $qry = $conn->prepare("UPDATE barbershop SET ShopName = '" . $shopName . "', Email = '" . $email . "', Address = '" . $address .  "', PhoneNumber = '" . $phoneNumber . "', Password = '" . $password . "' WHERE BarbershopID = " . $barbershopid . ";");

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
