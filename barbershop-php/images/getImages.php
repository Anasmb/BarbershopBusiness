<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$barbershopID = $_REQUEST['barbershopID']; 

  $qry = $conn->prepare("SELECT ImageID , Image FROM gallery WHERE BarbershopID = " . $barbershopID);

    $qry->execute();
    $qry->bind_result($imageID , $Image);
    
    $columns = array();

    while($qry->fetch()){
        $temp = array();
        $temp['ImageID'] = $imageID;
        $temp['Image'] = $Image;
        array_push($columns, $temp);
    }

    echo json_encode($columns);

?>
