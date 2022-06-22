<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$appointmentID = $_POST['appointmentID'];
$status = $_POST['status'];

$qry = $conn->prepare("UPDATE appointment SET status = '" . $status . "' WHERE AppointmentID = " . $appointmentID . ";");

if($qry->execute()){
    echo "Update Success";
}
else{
    echo "Update Failed";
}


?>
