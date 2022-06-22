<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$barbershopID = $_REQUEST['barbershopID']; 

  $qry = $conn->prepare("SELECT Comment , Stars FROM rating WHERE BarbershopID = " . $barbershopID);

    $qry->execute();
    $qry->bind_result($comment , $stars);
    
    $columns = array();

    while($qry->fetch()){
        $temp = array();
        $temp['comment'] = $comment;
        $temp['stars'] = $stars;
        array_push($columns, $temp);
    }

    echo json_encode($columns);


?>
