<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

    $name = $_POST['name'];
    $email =$_POST['email'];
    $password = $_POST['password'];
    $phonenumber = $_POST['phonenumber'];

    try{
        $qry = $conn->prepare("INSERT INTO  customers (Name, Email, Password, PhoneNumber) VALUES ('" . $name . "','" . $email . "','" . $password . "','" . $phonenumber . "')");

        if($qry->execute()){
            echo "Sign Up Success";
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
