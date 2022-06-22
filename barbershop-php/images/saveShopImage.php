<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}

$image = $_POST['image'];
$barbershopID = $_POST['barbershopID'];

$qry = $conn->prepare("UPDATE barbershop SET Image = '" . $image . "'   WHERE BarbershopID = " . $barbershopID . ";");

if($qry->execute()){
    echo "Image Saved";
}
else{
    echo "Image not saved!";
}


?>
