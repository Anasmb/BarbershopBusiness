<?php

    $conn = mysqli_connect("localhost", "root", '', "barbershop");

    if(mysqli_connect_errno()){
        die('Unable to connect to database ' . mysqli_connect_error());
    }
    
        $name = $_POST['Name'];
        $experience = $_POST['Experience'];
        $nationality = $_POST['Nationality'];
        $barbershopID = $_POST['BarbershopID'];
    
        $qry = $conn->prepare("INSERT INTO barber (Name, Experience, Nationality, BarbershopID) VALUES ('" . $name . "','" . $experience . "','" . $nationality . "','" . $barbershopID . "')");
    
        if($qry->execute()){
            echo "Add Success";
        }
        else{
            echo "Add Failed";
        }

?>
