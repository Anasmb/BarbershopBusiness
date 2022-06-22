<?php
$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());}

$totalPrice = $_POST['totalPrice']; 
$discount = $_POST['discount']; 
$time = $_POST['time'];
$date = $_POST['date'];
$services = $_POST['services'];
$status = $_POST['status'];
$customerAddress = $_POST['customerAddress'];
$serviceLocation = $_POST['serviceLocation'];
$barbershopName = $_POST['barbershopName'];
$barberName = $_POST['barberName'];
$barbershopID = $_POST['barbershopID'];
$customerID = $_POST['customerID'];

$qry = $conn->prepare("INSERT INTO appointment ( TotalPrice , Discount , Time , Date , Services , Status , CustomerAddress , ServiceLocation , BarbershopName ,BarberName , BarbershopID , CustomerID) 
    VALUES ('" . $totalPrice . "','" . $discount . "','" . $time . "','" . $date . "','" . $services . "','" . $status . "','" . $customerAddress . "','" . $serviceLocation . "','" . $barbershopName . "','" . $barberName . "','" 
    . $barbershopID . "','" . $customerID ."');");

if($qry->execute()){  echo "Add Success"; 
}
else{ echo "Add Failed"; }

?>
