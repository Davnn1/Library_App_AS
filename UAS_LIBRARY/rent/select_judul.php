<?php
include '../koneksi.php';

$result = mysqli_query($conn, "SELECT judul FROM buku");
if(mysqli_num_rows($result) > 0) {
    $response['message'] = "Judul inserted";
    $response['data'] = array();

    while($row = mysqli_fetch_assoc($result)){
        $response['data'][] = $row;
    }
} else {
    $response['message'] = "No Judul inserted";
}

echo json_encode($response);
?>
