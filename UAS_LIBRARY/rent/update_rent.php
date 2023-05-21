<?php
include '../koneksi.php';

$id = $_POST['id'];
$judul = $_POST['judul'];
$peminjam = $_POST['peminjam'];
$denda = $_POST['denda'];
$rent = $_POST['rent_at'];
$return = $_POST['return_at'];
$status = $_POST['status'];

$query = "UPDATE pinjam SET denda='".$denda."', return_at='".$return."', status='".$status."' WHERE id='".$id."'";
$result = mysqli_query($conn, $query) or die('Error query: '.$query);

if ($result == 1) {
    $response['message'] = "Success Update";

    // Insert data into 'kembali' table
    $insertQuery = "INSERT INTO kembali (id, judul, peminjam, denda, rent_at, return_at, status) VALUES ('".$id."', '".$judul."', '".$peminjam."', '".$denda."', '".$rent."', '".$return."', '".$status."')";
    $insertResult = mysqli_query($conn, $insertQuery) or die('Error query: '.$insertQuery);

    if ($insertResult == 1) {
        // Delete data from 'pinjam' table
        $deleteQuery = "DELETE FROM pinjam WHERE id='".$id."'";
        $deleteResult = mysqli_query($conn, $deleteQuery) or die('Error query: '.$deleteQuery);

        if ($deleteResult == 1) {
            $response['message'] = " Update Success ";
        } else {
            $response['message'] .= " Update Failed ";
        }
    } else {
        $response['message'] .= " Failed to insert data into 'kembali' table";
    }
} else {
    $response['message'] = "Failed Update";
}

echo json_encode($response);
mysqli_close($conn);
?>
