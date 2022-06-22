<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

    $appointmentID = $_POST['appointmentID'];

    $qry = $conn->prepare("DELETE FROM appointment WHERE AppointmentID = " . $appointmentID . " ;");

    if($qry->execute()){
        echo "Delete Success"; }
    else{
       echo "Delete Failed";
     }


?>





