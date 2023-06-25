<?php
    include 'koneksi.php';
    $query = $_GET['query'];
    
    $sql = "SELECT * FROM pinjam WHERE judul LIKE '%$query%' OR id LIKE '%$query%' OR peminjam LIKE '%$query%' OR denda LIKE '%$query%' OR rent_at LIKE '%$query%' OR return_at LIKE '%$query%'";
    $result = mysqli_query($conn, $sql);
    
    if (mysqli_num_rows($result) > 0) {
        $items = array();
        while ($row = mysqli_fetch_assoc($result)) {
            array_push($items, $row);
        }
        
        $response['message'] = "Data Found";
        $response['data'] = $items;
    } else {
        $response['message'] = "No Data found";
    }
    
    echo json_encode($response);
?>