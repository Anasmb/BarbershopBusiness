<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

if(empty($_REQUEST['Hours'])){ 
   
    $qry = "SELECT Hours FROM barbershop WHERE BarbershopID = " . $_REQUEST['BarbershopID'] ;
    $result = mysqli_query($conn,$qry);
    $row = mysqli_fetch_assoc($result);
    echo $row['Hours'];

}
else{

    $barbershopid = $_POST['BarbershopID'];
    $hours = $_POST['Hours'];

    $qry = $conn->prepare("UPDATE barbershop SET Hours = '" . $hours . "' WHERE BarbershopID = " . $barbershopid . ";");

    if($qry->execute()){
        echo "Add Success";
    }
    else{
        echo "Add Failed";
    }

}
?>
