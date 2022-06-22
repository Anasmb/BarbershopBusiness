<?php

$conn = mysqli_connect("localhost", "root", '', "barbershop");

if(mysqli_connect_errno()){
    die('Unable to connect to database ' . mysqli_connect_error());
}


if(empty($_REQUEST['barbershopID'])){ //get all the appointment info for a specific customer 

$customerID = $_REQUEST['customerID']; 

  $qry = $conn->prepare("SELECT AppointmentID , barbershop.BarbershopID , TotalPrice , Time , Date , Services , appointment.Status , CustomerAddress , ServiceLocation , BarbershopName , BarberName , barbershop.Address
   FROM appointment, barbershop WHERE CustomerID = " . $customerID . " && barbershop.BarbershopID = appointment.BarbershopID");

    $qry->execute();
    $qry->bind_result($appointmentID , $barbershopID , $totalPrice , $time , $date, $services , $status , $customerAddress , $serviceLocation , $barbershopName , $barberName , $barbershopAddress);
    
    $columns = array();

    while($qry->fetch()){
        $temp = array();
        $temp['appointmentID'] = $appointmentID;
        $temp['barbershopID'] = $barbershopID;
        $temp['totalPrice'] = $totalPrice;
        $temp['time'] = $time;
        $temp['date'] = $date;
        $temp['services'] = $services;
        $temp['status'] = $status;
        $temp['customerAddress'] = $customerAddress;
        $temp['serviceLocation'] = $serviceLocation;
        $temp['barbershopName'] = $barbershopName;
        $temp['barberName'] = $barberName;
        $temp['barbershopAddress'] = $barbershopAddress;
        array_push($columns, $temp);
    }

    echo json_encode($columns);
}
elseif(empty($_REQUEST['customerID'])){ // get all the appointment info for a specific barbershop

    $barbershopID = $_REQUEST['barbershopID']; 

    $qry = $conn->prepare("SELECT AppointmentID , TotalPrice , Time , Date , Services , Status , CustomerAddress , ServiceLocation , customers.name , BarberName
        FROM appointment ,customers WHERE BarbershopID = " . $barbershopID . " && customers.CustomerID = appointment.CustomerID");
 
     $qry->execute();
     $qry->bind_result($appointmentID , $totalPrice , $time , $date, $services , $status , $customerAddress , $serviceLocation , $customerName , $barberName );
     
     $columns = array();
 
     while($qry->fetch()){
         $temp = array();
         $temp['appointmentID'] = $appointmentID;
         $temp['totalPrice'] = $totalPrice;
         $temp['time'] = $time;
         $temp['date'] = $date;
         $temp['services'] = $services;
         $temp['status'] = $status;
         $temp['customerAddress'] = $customerAddress;
         $temp['serviceLocation'] = $serviceLocation;
         $temp['customerName'] = $customerName;
         $temp['barberName'] = $barberName;
         array_push($columns, $temp);
     }
 
     echo json_encode($columns);

}
else if(!empty($_REQUEST['customerID']) && !empty($_REQUEST['barbershopID'])){ // check if there is an appointment  for a specific customer and barbershop
 
    $barbershopID = $_REQUEST['barbershopID']; 
    $customerID = $_REQUEST['customerID']; 

    $qry = "SELECT * FROM appointment WHERE BarbershopID = " . $barbershopID . " && CustomerID = ". $customerID . " && Status = 'Finished' ";
 
    $results = mysqli_query($conn,$qry);

    if (mysqli_num_rows($results) != 0) { 
           echo "true";
      } 
      else if(mysqli_num_rows($results) == 0){
           echo "false";
      }
}



?>
