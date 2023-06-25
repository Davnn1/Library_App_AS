<?php
    include 'koneksi.php';
    
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['pass'];

    $response = array();

    $checkQuery = "SELECT * FROM user WHERE username='$username'";
    $checkResult = mysqli_query($conn, $checkQuery);

    if (mysqli_num_rows($checkResult) > 0) {
        $response["message"] = "User";
    } else {
        $checkQuery = "SELECT * FROM user WHERE email='$email'";
        $checkResult = mysqli_query($conn, $checkQuery);

        if (mysqli_num_rows($checkResult) > 0) {
            $response["message"] = "Mail";
        } else {
            $insertQuery = "INSERT INTO user (username, email, password) VALUES ('$username', '$email', '$password')";
            $insertResult = mysqli_query($conn, $insertQuery);

            if ($insertResult) {
                $response["message"] = "Success";
            } else {
                $response["message"] = "Failed";
            }
        }
    }

    echo json_encode($response);

    mysqli_close($conn);
?>
