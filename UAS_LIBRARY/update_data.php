<?php
    include 'koneksi.php';
    $id = $_POST['id'];
    $judul = $_POST['judul'];
    $kategori = $_POST['kategori'];
    $penerbit = $_POST['penerbit'];
    $pengarang = $_POST['pengarang'];
    $tahun = $_POST['tahun'];

    $query = "UPDATE buku SET judul='".$judul."', kategori='".$kategori."',penerbit='".$penerbit."',pengarang='".$pengarang."',tahun='".$tahun."' WHERE id='".$id."'";
    $result = mysqli_query ($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        $response['message']="Success Update";
    }
    else{
        $response['message']="Failed Update";
    }
    echo json_encode($response);
    mysqli_close($conn);
?>