<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

    $barberID = $_POST['barberID'];

    $qry = $conn->prepare("DELETE FROM barber WHERE BarberID = " . $barberID . " ;");

    if($qry->execute()){
        echo "Delete Success"; }
    else{
       echo "Delete Failed";
     }


?>





