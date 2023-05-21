<?php
    include 'koneksi.php';

    $mulai = $_POST['awal'];
    $selesai = $_POST['akhir'];

    $query = "SELECT * FROM kembali WHERE return_at BETWEEN '$mulai' AND '$selesai'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $items = array();
        while ($row = mysqli_fetch_assoc($result)) {
            array_push($items, $row);
        }

        $response['message'] = "Data Found";
        $response['data'] = $items;
    } else {
        $response['message'] = "No Data found";
        $response['data'] = array();
    }

    echo json_encode($response);

?>
