<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

    $serviceID = $_POST['serviceID'];

    $qry = $conn->prepare("DELETE FROM service WHERE ServiceID = " . $serviceID . " ;");

    if($qry->execute()){
        echo "Delete Success";     }
    else{
       echo "Delete Failed";
     }


?>





