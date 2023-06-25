<?php
    include '../koneksi.php';
    $judul = $_POST['judul'];
    $peminjam = $_POST['peminjam'];
    $denda = $_POST['denda'];
    $rent = $_POST['rent_at'];
    $return = $_POST['return_at'];
    $status = $_POST['status'];

    $query = "INSERT INTO pinjam (judul,peminjam,denda,rent_at,return_at,status) VALUES ('".$judul."','".$peminjam."','".$denda."','".$rent."','".$return."','".$status."')";
    $result = mysqli_query($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        $response["message"]="Success Insert New Book";
    }
    else{
        $response["message"]="Failed To Insert New Book";
    }

    echo json_encode($response);
    mysqli_close($conn);
?>
