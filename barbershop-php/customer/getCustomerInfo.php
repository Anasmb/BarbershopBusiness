<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$phoneNumber = $_REQUEST['phoneNumber']; 


  $qry = $conn->prepare("SELECT * FROM customers WHERE PhoneNumber = " . $phoneNumber);

    $qry->execute();
    $qry->bind_result($customerID , $name , $email, $password , $phoneNumber);
    
    $columns = array();

    while($qry->fetch()){
        $temp = array();
        $temp['customerID'] = $customerID;
        $temp['name'] = $name;
        $temp['email'] = $email;
        $temp['password'] = $password;
        $temp['phonenumber'] = $phoneNumber;
        array_push($columns, $temp);
    }

    echo json_encode($columns);




?>
