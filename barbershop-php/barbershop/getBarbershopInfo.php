<?php

  $conn = mysqli_connect("localhost", "root", '', "barbershop");

  if(mysqli_connect_errno()){
      die('Unable to connect to database ' . mysqli_connect_error());
  }

    $phoneNumber = $_REQUEST['PhoneNumber']; 

    $qry = $conn->prepare("SELECT * FROM barbershop WHERE PhoneNumber = " . $phoneNumber);

      $qry->execute();
      $qry->bind_result($barbershopID , $shopName, $phoneNumber , $email, $address , $password , $image , $hours , $status);
      
      $columns = array();

      while($qry->fetch()){
          $temp = array();
          $temp['BarbershopID'] = $barbershopID;
          $temp['ShopName'] = $shopName;
          $temp['Email'] = $email;
          $temp['Address'] = $address;
          $temp['Hours'] = $hours;
          $temp['Image'] = $image;
          $temp['Password'] = $password;
          array_push($columns, $temp);
      }

      echo json_encode($columns);

?>
