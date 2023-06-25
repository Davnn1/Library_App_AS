<?php
    include 'koneksi.php';
    $id = $_POST['id'];
    $img = $_POST['image'];
    $judul = $_POST['judul'];
    $kategori = $_POST['kategori'];
    $penerbit = $_POST['penerbit'];
    $pengarang = $_POST['pengarang'];
    $tahun = $_POST['tahun'];

    date_default_timezone_set('Asia/Jakarta');
    $path = 'images/'.date(format:"d-m-Y-his").'-'.rand(100,10000).'.jpg';

    $data = mysqli_query($conn,"SELECT * FROM buku WHERE id='$id'");
    $d = mysqli_fetch_array($data);
    unlink($d['image']);

    $query = "UPDATE buku SET image='".$path."',judul='".$judul."', kategori='".$kategori."',penerbit='".$penerbit."',pengarang='".$pengarang."',tahun='".$tahun."' WHERE id='".$id."'";
    $result = mysqli_query ($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        file_put_contents($path, base64_decode($img));
        $response['message']="Success Update";
    }
    else{
        $response['message']="Failed Update";
    }

    echo json_encode($response);
    mysqli_close($conn);
?>