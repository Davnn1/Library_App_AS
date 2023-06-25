<?php
    include 'koneksi.php';
    $query = $_GET['query'];
    
    $sql = "SELECT * FROM buku WHERE judul LIKE '%$query%' OR id LIKE '%$query%' OR kategori LIKE '%$query%' OR penerbit LIKE '%$query%' OR pengarang LIKE '%$query%' OR tahun LIKE '%$query%'";
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