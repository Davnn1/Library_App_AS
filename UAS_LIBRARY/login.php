<?php
    include 'koneksi.php';
    
    $usernameOrEmail = $_POST['username'];
    $password = $_POST['password'];

    $response = array();

    $query = "SELECT * FROM user WHERE username='$usernameOrEmail' OR email='$usernameOrEmail'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) == 1) {
        $row = mysqli_fetch_assoc($result);
        $username = $row['username'];
        $email = $row['email'];

        $query = "SELECT * FROM user WHERE username='$username' AND password='$password'";
        $result = mysqli_query($conn, $query);
        if (mysqli_num_rows($result) == 1) {
            $query = "INSERT INTO login (username, email, password) VALUES ('$username', '$email', '$password')";
            $result = mysqli_query($conn, $query);

            if ($result) {
                $response["message"] = "Success";
            } 
        } 
        else {
            $response["message"] = "Failed";
        }
    } 
    else {
        $response["message"] = "Gagal";
    }

    echo json_encode($response);

    mysqli_close($conn);
?>
