<?php
    $conn = mysqli_connect("localhost", "root", '', "barbershop");

    if(mysqli_connect_errno()){
        die('Unable to connect to database ' . mysqli_connect_error());
    }

       $phonenumber = $_POST['PhoneNumber'];
       $password = $_POST['Password'];

        $sql = "SELECT * FROM barbershop WHERE PhoneNumber = '" . $phonenumber . "'";
        $result = mysqli_query($conn, $sql);
        $row = mysqli_fetch_assoc($result);

        if (mysqli_num_rows($result) != 0) {
            $dbphonenumber = $row['PhoneNumber'];
            $dbpassword = $row['Password'];
            if ($phonenumber == $phonenumber && $dbpassword == $password) {
                echo "Login Success";
            } else{
                echo "Wrong Information";
            } 
        } 
        else{
            echo "User not found";
        } 
?>
