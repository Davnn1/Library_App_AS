<?php
include 'koneksi.php';

// Memeriksa apakah username dan password dikirimkan melalui POST
if (isset($_POST['username']) && isset($_POST['password']) && isset($_POST['newpassword'])) {
    $user = $_POST['username'];
    $pass = $_POST['password'];
    $newpass = $_POST['newpassword'];

    // Query untuk mengupdate password pengguna
    $query = "UPDATE user SET password='$newpass' WHERE username='$user' AND password='$pass'";
    $result = mysqli_query($conn, $query) or die('Error query: '.$query);

    // Memeriksa apakah ada baris yang terpengaruh (diubah) oleh query UPDATE
    if (mysqli_affected_rows($conn) > 0) {
        $response["message"] = "Success";
    } else {
        $response["message"] = "Failed";
    }
} else {
    $response["message"] = "Invalid request";
}

// Mengirimkan respons dalam format JSON
echo json_encode($response);

// Menutup koneksi database
mysqli_close($conn);
?>
