<?php
    include 'koneksi.php';
    $img = $_POST['image'];
    $judul = $_POST['judul'];
    $kategori = $_POST['kategori'];
    $penerbit = $_POST['penerbit'];
    $pengarang = $_POST['pengarang'];
    $tahun = $_POST['tahun'];

    date_default_timezone_set('Asia/Jakarta');
    $path = 'images/'.date(format:"d-m-Y-his").'-'.rand(100, 10000).'.jpg';

    $query = "INSERT INTO buku (image,judul,kategori,penerbit,pengarang,tahun) VALUES ('".$path."','".$judul."','".$kategori."','".$penerbit."','".$pengarang."','".$tahun."')";
    $result = mysqli_query($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        file_put_contents($path, base64_decode($img));
        $response["message"]="Success Insert New Book";
    }
    else{
        $response["message"]="Failed To Insert New Book";
    }

    echo json_encode($response);
    mysqli_close($conn);
?>
