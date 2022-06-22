<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

  $qry = $conn->prepare("SELECT * FROM offer");

    $qry->execute();
    $qry->bind_result($offerID , $header , $description, $discount , $target);
    
    $columns = array();

    while($qry->fetch()){
        $temp = array();
        $temp['header'] = $header;
        $temp['description'] = $description;
        $temp['discount'] = $discount;
        $temp['target'] = $target;
        array_push($columns, $temp);
    }

    echo json_encode($columns);


?>
