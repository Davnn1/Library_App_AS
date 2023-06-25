<?php
    include 'koneksi.php';
    $id = $_POST['id'];
    
    $data = mysqli_query($conn, "SELECT * FROM buku WHERE id='$id'");
    $d = mysqli_fetch_array($data);
    unlink($d['image']);

    $query = "DELETE FROM buku WHERE (id='".$id."')";
    $result = mysqli_query($conn, $query) or die('Error query: '.$query);
    if($result == 1){
        $response["message"]="Success Delete";
        echo json_encode($response);
    }
    else{
        $response["message"]="Failed Delete";
        echo json_encode($response);
    }
    mysqli_close($conn);
?>
