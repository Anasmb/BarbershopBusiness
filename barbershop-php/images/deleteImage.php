<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

    $barbershopID = $_REQUEST['barbershopID']; 
    $imageID = $_REQUEST['imageID']; 

    $qry = $conn->prepare("DELETE FROM gallery WHERE BarbershopID = " . $barbershopID . " AND ImageID = " . $imageID);

    if($qry->execute()){
        echo "Delete Success"; }
    else{
       echo "Delete Failed";
     }


?>





